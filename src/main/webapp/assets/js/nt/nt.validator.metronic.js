/**
 * NTValidatorMetronic
 *
 * @description metronic 에서 사용할 수 있는 Form Validator Class
 * @author jinstech
 * @version 0.1
 * @copyright ninetree
 * @date 2024.09.25
 * @example
 * <pre>
 * const validator = new NTValidatorMetronic(document.form);
 * </pre>
 */
class NTValidatorMetronic extends Array {
    isValid = true;
    constructor(form) {
        super();
        this.form = form;
        const validForm = new FormData(this.form);
        validForm.forEach((value, key) => {
            let tempElement = this.form.querySelector(`[name=${key}]`);
            if (tempElement.type === 'text') {
                tempElement.addEventListener('input', this.checkElement.bind(this));
                tempElement.insertAdjacentHTML('afterend', `<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback" data-nt-validator-message-container></div>`);
            }
        });
    }

    valid() {
        const validForm = new FormData(this.form);
        let firstElement;
        validForm.forEach((value, key) => {
            let tempElement = this.form.querySelector(`[name=${key}]`);
            if (!this.isValidElement(tempElement)) {
                if (!firstElement) firstElement = tempElement;
            }
        });

        if (firstElement) firstElement.focus();
        return this.isValid;
    }

    checkElement(event) {
        const targetElement = event.target;
        this.isValidElement(targetElement);
    }

    isValidElement(formElement) {
        let isElementValid = true;
        // 1) input text 인 경우 required, empty 검증
        if (formElement.type === 'text') {
            if (formElement.required && formElement.value.trim() === '') {
                this.isValid = false;
                this.addError(formElement, 'empty');

                isElementValid = false;
            } else {
                if (formElement.classList.contains('is-invalid')) this.removeError(formElement, 'empty');
            }
        }
        // 2) pattern 검증
        // 3) 그룹 검증

        return isElementValid;
    }

    addError(formElement, messageType) {
        this.removeError(formElement, messageType);
        // TODO :: 동일 에러가 이미 나와 있는 경우 메시지 중복 생성 안되게 해야 함.
        // TODO :: empty 이외의 검증 로직 추가에 따라 메시지 구현 해야 함.
        formElement.classList.add('is-invalid');
        let errorMessage = formElement.getAttribute(`nt-validator-message-${messageType}`)? formElement.getAttribute(`nt-validator-message-${messageType}`):formElement.placeholder;
        let nextElement = formElement.nextElementSibling;
        while (nextElement) {
            if (nextElement.hasAttribute('data-nt-validator-message-container')) {
                nextElement.innerHTML += `<div data-field="${formElement.name}" data-nt-validator="${messageType}">${errorMessage}</div>`;
                break;
            }
            nextElement = nextElement.nextElementSibling;
        }
    }

    removeError(formElement, messageType) {
        formElement.classList.remove('is-invalid');
        let nextElement = formElement.nextElementSibling;

        // 다음 요소들을 순차적으로 확인
        while (nextElement) {
            // data-nt-validator-message-container attribute가 있는지 확인
            if (nextElement.hasAttribute('data-nt-validator-message-container')) {
                nextElement.childNodes.forEach((element) => {
                    if (element.dataset.field === formElement.name && element.dataset.ntValidator === messageType) {
                        element.remove();
                    }
                });
                break;
            }
            // 다음 형제 요소로 이동
            nextElement = nextElement.nextElementSibling;
        }

    }
}