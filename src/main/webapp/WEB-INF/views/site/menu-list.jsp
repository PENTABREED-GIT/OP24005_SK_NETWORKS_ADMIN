<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/common/inc/page-header-inc.jsp" %>
</head>
<body id="kt_body" class="header-fixed header-tablet-and-mobile-fixed toolbar-enabled toolbar-fixed toolbar-tablet-and-mobile-fixed aside-enabled aside-fixed" style="--kt-toolbar-height:55px;--kt-toolbar-height-tablet-and-mobile:55px">

<div class="d-flex flex-column flex-root">
    <div class="page d-flex flex-row flex-column-fluid">
        <%@ include file="/WEB-INF/views/common/inc/body-aside-inc.jsp" %>
        <div class="wrapper d-flex flex-column flex-row-fluid" id="kt_wrapper">
            <%@ include file="/WEB-INF/views/common/inc/body-header-inc.jsp" %>
            <div class="content d-flex flex-column flex-column-fluid" id="kt_content">
                <%@ include file="/WEB-INF/views/common/inc/toolbar-inc.jsp" %>
                <div class="container-fluid" id="kt_post">
                    <div id="kt_content_container" class="card-body">
                        <div class="card">
                            <div class="card-header border-0 pt-6">
                                <table class="table align-middle table-row-dashed fs-6 gy-5" id="List">
                                    <colgroup>
                                        <col width="34%"/>
                                        <col width="33%"/>
                                        <col width="33%"/>
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th style="color: #000000;font-weight: bold;">1 Depth</th>
                                        <th style="color: #000000;font-weight: bold;">2 Depth</th>
                                        <th style="color: #000000;font-weight: bold;">3 Depth</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>
                                            <select
                                                    name="menuIndex1"
                                                    id="menuIndex1"
                                                    size="2"
                                                    style="width:100%;height:300px;"
                                                    onchange="loadMenu(this)"
                                                    data-depth = '1'
                                            >
                                                <c:forEach items="${depth1List}" var="menu" varStatus="status">
                                                    <option
                                                            data-uid="<c:out value='${menu.uid}'/>"
                                                            data-menuIndex="<c:out value='${menu.menuIndex}'/>"
                                                            data-rootIndex="<c:out value='${menu.rootIndex}'/>"
                                                            data-parentIndex="<c:out value='${menu.parentIndex}'/>"
                                                            data-is-display="<c:out value='${menu.isDisplay}'/>"
                                                            data-depth="<c:out value='${menu.depth}'/>"
                                                            value="<c:out value='${menu.menuIndex }'/>"
                                                    >
                                                        <c:out value="${menu.menuName }"/>(<c:out value="${menu.isDisplay }"/>)
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            <br><br>
                                            <div class="col-lg-10 btnGroup">
                                                <button type="button"
                                                        data-depth="1"
                                                        data-post-type="post"
                                                        class="btn btn-primary justify-content-center me-3"
                                                        onclick="postModalHandler(this)"
                                                >
                                                    <span class="menu-icon">
                                                        <i class="bi bi-plus fs-2"></i>
                                                    </span>추가
                                                </button>
                                                <button type="button"
                                                        data-depth="1"
                                                        data-post-type="put"
                                                        class="btn btn-success justify-content-center me-3"
                                                        onclick="putModalHandler(this)"
                                                >
                                                    <span class="menu-icon">
                                                        <i class="bi bi-pencil-square fs-2"></i>
                                                    </span>수정
                                                </button>
                                                <button type="button" class="btn btn-danger justify-content-center" data-depth="1" data-post-type="delete" onclick="deleteButton('1')">
                                                    <span class="menu-icon ">
                                                        <i class="bi bi-trash fs-2"></i>
                                                    </span>삭제
                                                </button>
                                            </div>
                                        </td>
                                        <td>
                                            <select
                                                    name="menuIndex2"
                                                    id="menuIndex2"
                                                    size="2"
                                                    style="width:100%;height:300px;"
                                                    data-depth = '2'
                                                    onchange="loadMenu(this)"
                                                    disabled
                                            >
                                            </select>
                                            <br><br>
                                            <div class="col-lg-10 btnGroup">
                                                <button type="button"
                                                        data-depth="2"
                                                        data-post-type="post"
                                                        class="btn btn-primary justify-content-center me-3"
                                                        onclick="postModalHandler(this)"
                                                >
                                                    <span class="menu-icon">
                                                        <i class="bi bi-plus fs-2"></i>
                                                    </span>추가
                                                </button>
                                                <button type="button"
                                                        data-depth="2"
                                                        data-post-type="put"
                                                        class="btn btn-success justify-content-center me-3"
                                                        onclick="putModalHandler(this)"
                                                >
                                                    <span class="menu-icon">
                                                        <i class="bi bi-pencil-square fs-2"></i>
                                                    </span>수정
                                                </button>
                                                <button type="button" class="btn btn-danger justify-content-center" data-depth="2"
                                                        data-post-type="delete" onclick="deleteButton('2')">
                                                    <span class="menu-icon ">
                                                        <i class="bi bi-trash fs-2"></i>
                                                    </span>삭제
                                                </button>
                                            </div>
                                        </td>
                                        <td>
                                            <select
                                                    name="menuIndex3"
                                                    id="menuIndex3"
                                                    size="2"
                                                    style="width:100%;height:300px;"
                                                    data-depth = '3'
                                                    disabled
                                            ></select>
                                            <br><br>
                                            <div class="col-lg-10 btnGroup">
                                                <button type="button"
                                                        data-depth="3"
                                                        data-post-type="post"
                                                        class="btn btn-primary justify-content-center me-3"
                                                        onclick="postModalHandler(this)"
                                                >
                                                    <span class="menu-icon">
                                                        <i class="bi bi-plus fs-2"></i>
                                                    </span>추가
                                                </button>
                                                <button type="button"
                                                        data-depth="3"
                                                        data-post-type="put"
                                                        class="btn btn-success justify-content-center me-3"
                                                        onclick="putModalHandler(this)"
                                                >
                                                    <span class="menu-icon">
                                                        <i class="bi bi-pencil-square fs-2"></i>
                                                    </span>수정
                                                </button>
                                                <button type="button" class="btn btn-danger justify-content-center" data-depth="3"
                                                        data-post-type="delete" onclick="deleteButton('3')">
                                                    <span class="menu-icon ">
                                                        <i class="bi bi-trash fs-2"></i>
                                                    </span>삭제
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@ include file="/WEB-INF/views/common/inc/footer-inc.jsp" %>
        </div>
    </div>
</div>

<script src="/assets/plugins/custom/datatables/datatables.bundle.js"></script>
<script src="/assets/js/list.js"></script>
<script src="/assets/js/flatpickr/ko.js"></script>
<script type="text/javascript">
    let isSubmitting = false;

    let depth1List = [];
    let depth2List = [];
    let depth3List = [];
    /************************************************************* INIT ***********************************************/
    document.addEventListener('DOMContentLoaded', function() {

        let json1 = '${depth1Json}';
        let json2 = '${depth2Json}';
        let json3 = '${depth3Json}';

        json1 = json1.replace(/[\u0000-\u001F\u007F-\u009F]/g, "");
        json2 = json2.replace(/[\u0000-\u001F\u007F-\u009F]/g, "");
        json3 = json3.replace(/[\u0000-\u001F\u007F-\u009F]/g, "");
        depth1List = JSON.parse(json1);
        depth2List = JSON.parse(json2);
        depth3List = JSON.parse(json3);

        attachModalEventListeners();

        // 메뉴버튼 모두 disabled처리
        document.querySelectorAll('button[data-post-type]').forEach((btn) => {btn.disabled = true;})
        document.querySelector('button[data-post-type="post"]').disabled = false;
    });


    /*********************************************************** LOAD 영역 ********************************************/
    let loadMenuTimeout;

    async function loadMenu(element) {
        clearTimeout(loadMenuTimeout);


        loadMenuTimeout = setTimeout(async () => {
            let depth = element.getAttribute('data-depth');
            let menuIndex2 = document.getElementById('menuIndex2');
            let menuIndex3 = document.getElementById('menuIndex3');

            let menuIndex2Option = '';
            let menuIndex3Option = '';

            if (depth === '1') {
                for (let i = 0; i < depth2List.length; i++) {
                    if (depth2List[i].parentIndex === parseInt(element.value)) {
                        menuIndex2Option += '<option data-uid="' + depth2List[i].uid + '" data-menuIndex="' + depth2List[i].menuIndex + '" data-rootIndex="' + depth2List[i].rootIndex + '" data-parentIndex="' + depth2List[i].parentIndex + '" data-depth="' + depth2List[i].depth + '" data-is-display="' + depth2List[i].isDisplay + '"  value="' + depth2List[i].menuIndex + '">' + depth2List[i].menuName + '(' + depth2List[i].isDisplay + ')</option>';
                    }
                }
                menuIndex2.innerHTML = menuIndex2Option;
                menuIndex2.disabled = false;
                menuIndex3.innerHTML = '';
                menuIndex3.disabled = true;
            } else if (depth === '2') {
                for (let i = 0; i < depth3List.length; i++) {
                    if (depth3List[i].parentIndex === parseInt(element.value)) {
                        menuIndex3Option += '<option data-uid="' + depth3List[i].uid + '" data-menuIndex="' + depth3List[i].menuIndex + '" data-rootIndex="' + depth3List[i].rootIndex + '" data-parentIndex="' + depth3List[i].parentIndex + '" data-depth="' + depth3List[i].depth + '" data-is-display="' + depth3List[i].isDisplay + '" value="' + depth3List[i].menuIndex + '">' + depth3List[i].menuName + '(' + depth3List[i].isDisplay + ')</option>';
                    }
                }
                menuIndex3.innerHTML = menuIndex3Option;
                menuIndex3.disabled = false;
            }
        }, 300);
    }
    /******************************************************** MakeData 영역 ********************************************/
    const makeData = (uid) => {
        let params = {
            uid: uid ? uid : '',
            menuIndex: document.getElementById('menuIndex').value,
            menuName: document.getElementById('menuName').value,
            parentIndex: document.getElementById('parentIndex').value ? document.getElementById('parentIndex').value : 0,
            depth: document.getElementById('depth').value,
            menuCode: document.getElementById('menuCode').value,
            sortOrder: document.getElementById('sortOrder').value,
            url: document.getElementById('url').value,
            urlString : document.getElementById('urlString').value,
            rootIndex: document.getElementById('rootIndex').value ? document.getElementById('rootIndex').value : 0,
            isDisplay: document.querySelector('input[name="isDisplay"]:checked') ? 'Y' : 'N',
            description: document.getElementById('description').value,
            icon: document.getElementById('icon').value,
        };
        return params;
    };
    /*************************************** start: Valid ********************************************/
    // 유효성 체크 통합
    const regExp = {
        exp1: /[ㄱ-힣]/, // 한글 불가용
        exp2: /^[0-9]+$/, // 숫자만
    };
    // 메세지 관리
    const validMessage = {
        form: {
            menuName: {elem: null, msg: '* 메뉴명을 입력해주세요.'},
            menuCode: {elem: null, msg: '* 메뉴 코드를 입력해주세요.'},
            sortOrder: {elem: null, msg: '* 정렬 순서를 입력해주세요.'},
            url: {elem: null, msg: '* URL을 입력해주세요.'},
            urlString: {elem: null, msg: '*  URL 경로를 입력해주세요.'},
            icon:{elem: null, msg: '*  URL 경로를 입력해주세요.'}
        },
    };
    // 에러메세지 제작
    const setMessage = (elem, isValid) => {
        const name = elem.name;

        // 에러 메세지 생성
        if (!isValid) {
            elem.disabled = true;
            if (!validMessage.form[name].elem) {
                validMessage.form[name].elem = document.createElement('small');
                validMessage.form[name].elem.style.color = 'red';
                elem.parentNode.insertBefore(validMessage.form[name].elem, elem.nextSibling);
                validMessage.form[name].elem.textContent = validMessage.form[name].msg;
            }
            elem.disabled = false;
            return
        }
        // 메세지 제거
        if (validMessage.form[name].elem) {
            validMessage.form[name].elem.remove();
            validMessage.form[name].elem = null;
        }
    }

    const clearInputIfInvalid = (inputEvent) => {
        const elem = inputEvent.target
        const inputValue = elem.value
        setMessage(elem, true);
        if (elem.name === 'url' || elem.name === 'urlString' || elem.name === 'menuCode' || elem.name === 'icon') {
            elem.value = inputValue.replace(new RegExp(regExp.exp1, 'g'), '');
        }
        if (elem.name === 'sortOrder') {
            if(!regExp.exp2.test(inputValue)) {
                elem.value = inputValue.replace(/[^0-9]/g, '');
            }
        }
    };

    const validForm = async () => {
        const menuName = document.querySelector('input[name="menuName"]');
        const menuCode = document.querySelector('input[name="menuCode"]');
        const sortOrder = document.querySelector('input[name="sortOrder"]');
        const url = document.querySelector('input[name="url"]');
        const urlString = document.querySelector('textarea[name="urlString"]');
        let menuNameBol = true;
        let menuCodeBol = true;
        let sortOrderBol = true;
        let urlBol = true;
        let urlStringBol = true;

        if (!menuName.value) {
            validMessage.form.menuName.msg = '* 메뉴명을 입력해주세요.';
            menuNameBol = false;
        } else if(menuName.value.length < 2) {
            validMessage.form.menuName.msg = '* 메뉴명은 2자 이상 입력해주세요.';
            menuNameBol = false;
        }
        if (!menuCode.value) menuCodeBol = false;
        if (!sortOrder.value) sortOrderBol = false;
        if (!url.value) urlBol = false;
        if (!urlString.value) urlStringBol = false;
        // 메세지 세팅
        setMessage(menuName, menuNameBol);
        setMessage(menuCode, menuCodeBol);
        setMessage(sortOrder, sortOrderBol);
        setMessage(url, urlBol);
        setMessage(urlString, urlStringBol);

        return menuNameBol && menuCodeBol && sortOrderBol && urlBol && urlStringBol;
    }
    /*************************************** end : Valid ********************************************/
    /*********************************************************** POST 영역 ********************************************/
    async function saveMenu() {
        if (isSubmitting) {
            return;
        }
        isSubmitting = true;
        let data = makeData(null);
        if(!await validForm()) {
            isSubmitting = false;
            return false;
        }

        try {
            const response = await fetch('/api/v1/site/menu', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data)
            });
            const result = await response.json();
    // console.log(result);
            if (result.result == true) {
                Swal.fire({
                    title: '등록 완료',
                    text: '등록되었습니다.',
                    icon: 'success',
                    confirmButtonText: '확인'
                }).then((result) => {
                    if (result.isConfirmed) {
                        location.reload();
                    }
                })
            } else {
                Swal.fire({
                    title: '등록 실패',
                    text: '등록에 실패하였습니다.',
                    icon: 'error',
                    confirmButtonText: '확인'
                })
            }
        } catch (Exception) {
            console.error(Exception);
        } finally {
            isSubmitting = false;
        }

    }


    /*********************************************************** PUT 영역 ********************************************/

    async function updateMenu(element) {
        if (isSubmitting) {
            return;
        }
        isSubmitting = true;
        let uid = element.getAttribute('data-uid');
        let data = makeData(uid);

        if(!await validForm()) {
            isSubmitting = false;
            return false;
        }

        try {
            const response = await fetch('/api/v1/site/menu/update/' + uid, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data)
            });

            const result = await response.json();
            // console.log(result);
            if (result.result == true) {
                Swal.fire({
                    title: '수정 완료',
                    text: '등록되었습니다.',
                    icon: 'success',
                    confirmButtonText: '확인'
                }).then((result) => {
                    if (result.isConfirmed) {
                        location.reload();
                    }
                })
            } else {
                Swal.fire({
                    title: '수정 실패',
                    text: '수정에 실패하였습니다.',
                    icon: 'error',
                    confirmButtonText: '확인'
                })
            }
        } catch (Exception) {
            console.error(Exception);
        } finally {
            isSubmitting = false;
        }

    }

    /*********************************************************** DETELE 영역 ********************************************/
    let isDelete = false;
    async function deleteButton(depth) {
        if (isDelete) {
            return;
        }
        isDelete = true;
        Swal.fire({
            title: '삭제 처리 확인',
            text: '등록하신 메뉴를 삭제 하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '예, 삭제 처리합니다',
            cancelButtonText: '아니요, 취소합니다'
        }).then(async (result) => {

            if (result.isConfirmed) {
                let uid = document.getElementById('menuIndex' + depth).options[document.getElementById('menuIndex' + depth).selectedIndex].dataset.uid;
//                let menuIndex = document.getElementById('menuIndex' + depth).value;
                try {
                    const response = await fetch('/api/v1/site/menu/delete/' + uid, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    });

                    const result = await response.json();

                    if (result.result == true) {
                        Swal.fire({
                            title: '삭제 처리 완료',
                            text: '삭제 처리가 완료되었습니다.',
                            icon: 'success',
                            confirmButtonText: '확인'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                location.href = '/site/menu';
                            }
                        })
                    } else {
                        Swal.fire({
                            title: '삭제 처리 실패',
                            text: '삭제 처리에 실패하였습니다.',
                            icon: 'error',
                            confirmButtonText: '확인'
                        })
                    }

                } catch (Exception) {
                    console.error(Exception);
                } finally {
                    isSubmitting = false;
                }
            } else {
                isDelete = false;
            }
        });
    }


    /*********************************************************** Modal 영역 ********************************************/


    function attachModalEventListeners() {
        const postModalElements = document.querySelectorAll('.postModal:not(.modal-attached)');
        const putModalElements = document.querySelectorAll('.putModal:not(.modal-attached)');
        postModalElements.forEach(function (element) {
            element.classList.add('modal-attached');
            element.addEventListener('click', postModalHandler);
        });

        putModalElements.forEach(function (element) {
            element.classList.add('modal-attached');
            element.addEventListener('click', putModalHandler);
        });
    }

    function postModalHandler(element) {
        const modalLayout = document.createElement('div');
        modalLayout.classList.add('modal', 'fade');
        modalLayout.id = 'postModalLayout';
        modalLayout.tabIndex = '-1';
        modalLayout.setAttribute('aria-hidden', 'true');

        const modalDialog = document.createElement('div');
        modalDialog.classList.add('modal-dialog', 'modal-dialog-centered', 'mw-650px');
        modalDialog.id = 'modalContent';

        modalLayout.appendChild(modalDialog);
        document.body.appendChild(modalLayout);
        let modal = new bootstrap.Modal(modalLayout, {
            keyboard: false
        });

        modalLayout.addEventListener('hidden.bs.modal', function() {
            modalLayout.remove();
        });

        let depth = element.getAttribute('data-depth');
        let newDepth = '';
        // console.log(depth);
        if (depth !== '1')
            newDepth = parseInt(depth) - 1;
        let selectElement = document.getElementById('menuIndex' + newDepth);
        // console.log('menuIndex' + newDepth)
        let selectedOption = selectElement?.options[selectElement.selectedIndex] ?? null;

        let uid = selectedOption?.getAttribute('data-uid') ?? '';
        let parentIndex = selectedOption?.getAttribute('data-menuIndex') ?? '';
        let menuIndex = selectedOption?.getAttribute('data-menuIndex') ?? '';
        let rootIndex = selectedOption?.getAttribute('data-rootIndex') ?? '';

        if (depth === '2') {
            rootIndex = parentIndex;
        }

        // console.log(parentIndex + ' ' + menuIndex + ' ' + rootIndex + ' ' + depth);
        NTUtil.ajax({
            url: '/site/menu-modal?type=post&uid=' + uid + '&parentIndex=' + parentIndex + '&menuIndex=' + menuIndex + '&rootIndex=' + rootIndex + '&depth=' + depth,
            method: 'GET',
            success: function (data) {
                modalDialog.innerHTML = '';
                modalDialog.insertAdjacentHTML('beforeend', data);
                $(modalDialog).find('select').select2({
                    minimumResultsForSearch: Infinity
                });
                modal.show();
            },
        })
    }

    function putModalHandler(element) {
        const modalLayout = document.createElement('div');
        modalLayout.classList.add('modal', 'fade');
        modalLayout.id = 'putModalLayout';
        modalLayout.tabIndex = '-1';
        modalLayout.setAttribute('aria-hidden', 'true');

        const modalDialog = document.createElement('div');
        modalDialog.classList.add('modal-dialog', 'modal-dialog-centered', 'mw-650px');
        modalDialog.id = 'modalContent';

        modalLayout.appendChild(modalDialog);
        document.body.appendChild(modalLayout);
        let modal = new bootstrap.Modal(modalLayout, {
            keyboard: false
        });


        modalLayout.addEventListener('hidden.bs.modal', function() {
            modalLayout.remove();
        });
        // console.log(element, typeof element);
        let depth = element.getAttribute('data-depth');

        let selectElement = document.getElementById('menuIndex' + depth);

        let selectedOption = selectElement?.options[selectElement.selectedIndex] ?? null;

//        console.log('put====', selectedOption);

        let uid = selectedOption?.getAttribute('data-uid') ?? '';
        let parentIndex = selectedOption?.getAttribute('data-parentIndex') ?? '';
        let menuIndex = selectedOption?.getAttribute('data-menuIndex') ?? '';
        let rootIndex = selectedOption?.getAttribute('data-rootIndex') ?? '';

//       console.log('Depth:', depth);
//        console.log('Looking for ID:', 'menuIndex' + depth);
//        console.log('Element:', document.getElementById('menuIndex' + depth));
        NTUtil.ajax({
            url: '/site/menu-modal?type=put&uid=' + uid + '&parentIndex=' + parentIndex + '&menuIndex=' + menuIndex + '&rootIndex=' + rootIndex + '&depth=' + depth,
            method: 'GET',
            success: function (data) {
                modalDialog.innerHTML = '';
                modalDialog.insertAdjacentHTML('beforeend', data);
                $(modalDialog).find('select').select2({
                    minimumResultsForSearch: Infinity
                });
                modal.show();
            }
        })
    }

    function closePostModal() {
        let modal = document.getElementById('postModalLayout');
        modal.remove();
        closeModalBackdrop();
    }

    function closePutModal() {
        let modal = document.getElementById('putModalLayout');
        modal.remove();
        closeModalBackdrop();
    }

    function closeModalBackdrop() {
        let backdrops = document.querySelectorAll('.modal-backdrop');

        if (!document.querySelector('.modal.show')) {

            backdrops.forEach(backdrop => backdrop.remove());
        }
    }

    // 수정 삭제, 비활성화 로직 추가
    const menuBtns = document.querySelectorAll('button[data-post-type]');
    const postBtns = document.querySelectorAll('button[data-post-type="post"]');
    const modifyBtns = document.querySelectorAll('button[data-post-type="put"]');
    const deleteBtns = document.querySelectorAll('button[data-post-type="delete"]');

    document.getElementById("menuIndex1").addEventListener('change', (e) => { btnChecker(e); })
    document.getElementById("menuIndex2").addEventListener('change', (e) => { btnChecker(e); })
    document.getElementById("menuIndex3").addEventListener('change', (e) => { btnChecker(e); })

    const btnChecker = (e) => {
        const selectedOption = e.target.options[e.target.selectedIndex];
        const isDisplay = selectedOption.dataset.isDisplay == 'Y';
        const depth = e.target.dataset.depth;
        postBtns.forEach((btn) => {
            btn.disabled = (parseInt(depth) + 1 < parseInt(btn.dataset.depth));
        })
        modifyBtns.forEach((btn) => {
            btn.disabled = (depth != btn.dataset.depth);
        })
        deleteBtns.forEach((btn) => {
            btn.disabled = isDisplay ? true : (depth != btn.dataset.depth);
        })
    }
</script>
</body>
</html>
<style>
    select#menuIndex1 {
        width: 100%;
        height: 200px;
        border: 1px solid #000;
        background-color: #fff;
    }

    select#menuIndex1 option:checked {
        background-color: #FFFF66;
        color: #000000;
        font-weight: bold;
    }

    select#menuIndex1 option {
        padding: 10px;
    }

    select#menuIndex2 {
        width: 100%;
        height: 200px;
        border: 1px solid #000;
        background-color: #fff;
    }

    select#menuIndex2 option:checked {
        background-color: #FFFF66;
        color: #000000;
        font-weight: bold;
    }

    select#menuIndex2 option {
        padding: 10px;
    }

    select#menuIndex3 {
        width: 100%;
        height: 200px;
        border: 1px solid #000;
        background-color: #fff;
    }

    select#menuIndex3 option:checked {
        background-color: #FFFF66;
        color: #000000;
        font-weight: bold;
    }

    select#menuIndex3 option {
        padding: 10px;
    }

    .btnGroup .btn-primary:disabled, .btnGroup .btn-success:disabled, .btnGroup .btn-danger:disabled {
        background-color: #B5B5C3 !important;
    }
</style>
<!--end::Javascript-->
