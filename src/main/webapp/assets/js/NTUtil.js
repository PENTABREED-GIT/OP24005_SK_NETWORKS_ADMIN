class NTElementCollection extends Array {
    ready(cb) {
        const isReady = this.some(e => {
            return e.readyState != null && e.readyState != 'loading'
        });

        if (isReady) {
            cb();
        } else {
            this.concat('DOMContentLoaded', cb);
        }

        return this;
    }

    on(event, cbOrSelector, cb) {
        if (typeof cbOrSelector === "function") {
            this.forEach(e => e.addEventListener(event, cbOrSelector));
        } else {
            this.forEach(elem => {
                elem.addEventListener(event, e => {
                    if (e.target.matches(cbOrSelector)) cb(e);
                });
            });
        }
        return this;
    }

    next() {
        return this.map(e => e.nextElementSibling).filter(e => e != null);
    }

    prev() {
        return this.map(e => e.previousElementSibling).filter(e => e != null);
    }

    removeClass(className) {
        this.forEach(e => e.classList.remove(className));
        return this;
    }

    addClass(className) {
        this.forEach(e => e.classList.add(className));
        return this;
    }

    css(property, value) {
        this.forEach(e => (e.style[property] = value));
        return this;
    }

    html(value) {
        let returnValue = '';
        this.forEach(el => {
            if (typeof value === 'string' || value instanceof String) {
                el.innerHTML = value;
            } else {
                returnValue += el.innerHTML;
            }
        });

        return returnValue;
    }

    text(value) {
        let returnValue = '';
        this.forEach(el => {
            if (typeof value === 'string' || value instanceof String) {
                el.innerText = value;
            } else {
                returnValue += el.innerText;
            }
        });

        return returnValue;
    }

    val(value) {
        let returnValue = '';
        this.forEach(el => {
            if (typeof value === 'string' || value instanceof String) {
                el.value = value;
            } else {
                returnValue += el.value;
            }
        });

        return returnValue;
    }

    hide() {
        this.forEach(el => {
            el.style.display = 'none';
        });
    }

    show() {
        this.forEach(el => {
            el.style.display = '';
        });
    }

    remove() {
        this.forEach(el => {
            el.remove();
        });
    }
}

class NTAjaxPromise {
    constructor(promise) {
        this.promise = promise;
    }

    done(cb) {
        this.promise = this.promise.then(data => {
            cb(data);
            return data;
        })
        return this;
    }

    fail(cb) {
        this.promise = this.promise.catch(cb);
        return this;
    }

    always(cb) {
        this.promise = this.promise.finally(cb);
        return this;
    }
}

let NTUtil = {
    getElement: function (param) {
        if (typeof param === 'string' || param instanceof String) {
            return new NTElementCollection(...document.querySelectorAll(param));
        } else {
            return new NTElementCollection(param);
        }
    },

    ajax: function ({
                        url, method, data = {}, success = () => {
        }, dataType
                    }) {
        let queryString;
        let requestUrl = url;
        let bodyData = data;

        method = method.toUpperCase();
        if (method === 'GET') {
            if (typeof data === 'string' || data instanceof String) {
                queryString = data;
            } else if (typeof data === 'object') {
                if (data.keys === undefined) {
                    queryString = Object.entries(data).map(([key, value]) => {
                        return `${key}=${value}`;
                    }).join('&');
                } else {
                    queryString = this.serialize(data);
                }
            }

            requestUrl = `${url}`; //?${queryString}
            bodyData = null;
        }
        // console.log('queryString::: ' + queryString);
        return new NTAjaxPromise(
            fetch(requestUrl, {
                method: method,
                headers: {
                    // 'Content-Type' : 'application/json',
                    'X-CSRF-TOKEN': this.getCsrfToken()
                },
                body: bodyData
            }).then(response => {
                const contentType = response.headers.get("content-type");

                if (contentType && contentType.indexOf("application/json") !== -1) {
                    return response.json();
                } else {
                    return response.text();
                }
            }).then(data => {
                success(data);
                return data;
            })
        );
    },

    getCsrfToken: function () {
        if (document.querySelector('meta[name=csrf-token]') === null) {
            return '';
        } else {
            return document.querySelector('meta[name=csrf-token]').getAttribute('content');
        }

    },

    serializeArray: function (formData) {
        let obj = {};
        for (let key of formData.keys()) {
            obj[key] = formData.get(key);
        }
        return obj;
    },

    serialize: function (formData) {
        let data = this.serializeArray(formData);
        let queryString = Object.entries(data).map(([key, value]) => {
            return `${key}=${value}`;
        }).join('&');

        return queryString;
    },

    isJsonString: function (data) {
        try {
            let json = JSON.parse(data);
            return (typeof json === 'object');
        } catch {
            return false;
        }
    },

    jsonToQueryString: function (data) {
        const queryString = Object.entries(data).map(([key, value]) => {
            return `${key}=${value}`;
        }).join('&');

        return queryString;
    },

    isValidPassword: function (data, adminId) {
        let num = data.search(/[0-9]/g);
        let eng = data.search(/[a-z]/ig);
        let spe = data.search(/[()`~!@@#$%^&*_|₩₩₩'₩";:₩/?]/gi);

        if(data.length==0 && document.getElementById('pwConfirm').value.length==0) {return true;}

        if ((num < 0 && eng < 0) || (eng < 0 && spe < 0) || (spe < 0 && num < 0)) {
            return false;
        }

        if (data.length < 8) { // 비밀번호 8자리 이상이어야 함
            return false;
        }
        if (data.search(/\s/) !== -1) { // 비밀번호에 공백은 없어야 함
            return false;
        }
        // 4자리 이상 중복된 문자열, 숫자, 특수문자는 올 수 없음
        if (/(\w)\1\1\1/.test(data) || /(\W)\1\1\1/.test(data)) {
            return false;
        }

        //  아이디에 포함된 4자리 이상의 문자열은 비밀번호에 포함될 수 없음
        let validId = adminId;
        let validPw = data;
        let tmp = "";
        let cnt = 0;
        for(var i=0; i<validId.length-3; i++){
            tmp = validId.charAt(i) + validId.charAt(i+1) + validId.charAt(i+2) + validId.charAt(i+3);
            if(validPw.indexOf(tmp) > -1){
                cnt = cnt + 1;
            }
        }
        if(cnt > 0){
            return false;
        }
        return true;
    },
    isValidPhoneNo: function (value, type) {
        if (type == 'global') {
            let b = (/^\+[1-9]{1,2}-?[0-9]{0,3}[0-9\-]{1,13}[0-9]$/).test(value);
            if (!b)
                return false;
            let tArr = value.match(/[0-9]/g);
            if (tArr == null)
                return false;
            if (tArr.length > 15)
                return false;

            return true;
            /* 국내전용 전화번호 형식 체크
             * 번호(2~4자리)-번호(3~4자리)-번호(4자리) 형식 (Ex. 02-736-0226)
             */
        } else if (type == 'ko') {
            return (/^0[1-9][0-9]{0,2}-[0-9]{3,4}-[0-9]{4}$/).test(value);
        } else if (type == 'ko-mobile') {
            return (/^01(0|1|6|7|8|9)-[0-9]{3,4}-[0-9]{4}$/).test(value);
        }
    },
    analyticsExcelDownload: function (url, dataUid) {

        let dataUidInput = document.createElement("input");
        if("N" !== dataUid) {
            dataUidInput.type = 'hidden';
            dataUidInput.value = dataUid;
            dataUidInput.name = 'dataUid';
        }

        let form;
        if ( url == "User") {
            document.getElementById("userForm").append(dataUidInput);
            form = document.userForm;
        } else {
            document.getElementById("analyticsForm").append(dataUidInput);
            form = document.analyticsForm;
        }

        form.action = "/ExcelDownload/"+url;
        form.submit();
        form.action = "";
        dataUidInput.remove();
    }
};
