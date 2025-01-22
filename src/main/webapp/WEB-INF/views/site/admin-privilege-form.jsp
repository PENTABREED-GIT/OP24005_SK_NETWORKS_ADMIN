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
        <div class="wrapper d-flex flex-column flex-row-fluid">
            <%@ include file="/WEB-INF/views/common/inc/body-header-inc.jsp" %>
            <div class="content d-flex flex-column flex-column-fluid">
                <c:set value="샘플 등록/수정" var="menuTitle"/>
                <%@ include file="/WEB-INF/views/common/inc/toolbar-inc.jsp" %>
                <div id="kt_content_container">
                    <input type="hidden" name="idx" id="idx" value="${adminRole != null ? adminRole.adminGroupIndex : null}"/>
                    <input type="hidden" name="uid" id="uid" value="${adminRole != null ? adminRole.uid : uid}"/>

                    <div class="container-fluid">
                        <!-- 본문 내용 START -->
                        <form>
                            <div class="card mb-5 mb-xl-10">
                                <div class="card-header border-0 pt-5">
                                    <div class="text-gray-900 fs-2 me-1 form-label fw-bolder text-dark">
                                        관리자 권한 관리
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-lg-6 row mb-8">
                                            <div class="col-lg-2">
                                                <label class="col-lg-10 form-label fw-bolder text-dark pt-3">작성자</label>
                                            </div>
                                            <div class="col-lg-10">
                                                <div id="userType" class="col-lg-4 form-label text-dark pt-3">
                                                    <c:choose>
                                                        <c:when test="${adminRole ne null}">
                                                            <c:out value="${adminRole.adminId}"/>(<c:out value="${adminRole.department}"/>)
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${admin.adminId}"/>(<c:out value="${admin.department}"/>)
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                        <c:if test="${adminRole != null}">
                                            <div class="col-lg-6 row mb-8">
                                                <div class="col-lg-2">
                                                    <label class="col-lg-10 fw-bolder text-dark pt-3">작성일시</label>
                                                </div>
                                                <div class="col-lg-10">
                                                    <div class="col-lg-12 text-dark pt-3">
                                                        <c:choose>
                                                            <c:when test="${adminRole.modDate ne null and adminRole.modDate != ''}">
                                                                <c:out value="${adminRole.modDate}"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:out value="${adminRole.regDate}"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-1 d-flex align-items-center">
                                                <label class="col-lg-10 form-label fw-bolder text-dark pt-3 required">관리자 그룹명</label>
                                            </div>

                                            <div class="col-lg-11">
                                                    <div class="col-lg-6 form-label text-dark">
                                                        <input type="text"  class="form-control" id="groupName" name="groupName" value="${adminRole.groupName}" maxlength="20" required oninput="clearInputIfInvalid(this)">
                                                    </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-1 d-flex align-items-center">
                                                <label class="col-lg-10 form-label fw-bolder text-dark required pt-3">권한 설명</label>
                                            </div>
                                            <div class="col-lg-11">
                                                    <div class="col-lg-12 form-label text-dark">
                                                        <input type="text"  class="form-control" id="description" name="description" value="${adminRole.description}" maxlength="100" required oninput="clearInputIfInvalid(this)">
                                                    </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-1 d-flex align-items-center">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3">사용 여부</label>
                                            </div>
                                            <div class="col-lg-2 row">
                                                <div class="col">
                                                    <div class="form-check form-check-custom form-check-solid">
                                                        <input class="form-check-input"
                                                                type="radio"
                                                                name="isUse"
                                                                value="N"
                                                                ${empty adminRole || adminRole.isUse == 'N'  ? 'checked' : ''}
                                                                id="isNField1"
                                                                maxlength="1"/>
                                                        <label class="form-check-label form-label fw-bolder text-dark col-lg-12 pt-3" for="isNField1">
                                                            N
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="col">
                                                    <div class="form-check form-check-custom form-check-solid">
                                                        <input class="form-check-input"
                                                                type="radio"
                                                                name="isUse"
                                                                value="Y"
                                                                ${adminRole.isUse == 'Y'  ? 'checked' : ''}
                                                                id="isYField1"
                                                                maxlength="1"/>
                                                        <label class="form-check-label form-label fw-bolder text-dark col-lg-12 pt-3" for="isYField1">
                                                            Y
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </form>
                        <div class="card mb-5 mb-xl-10">
                            <div class="card-header border-0 pt-5">
                                <div class="text-gray-900 fs-2 me-1 form-label fw-bolder text-dark">
                                    권한 설정
                                </div>
                            </div>
                            <div class="card-body">

                                <div class="row mb-15">
                                    <div class="col-lg-12">
                                        <div class="row">
                                            <div class="col-lg-2">
                                                <div class="form-check form-check-custom form-check-solid ">
                                                    <input class="form-check-input" type="checkbox" value="" id="isMain" name="isMain" ${adminRole.isMain == 'Y' or empty adminRole ? 'checked' : ''}/>
                                                    <label class="form-check-label form-label fw-bolder text-dark pt-3" for="isMain">
                                                        메인 관리
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <hr>
                                <c:forEach items="${menuList}" var="menu">
                                    <div class="row mb-8">
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <div class="col-lg-2">
                                                    <div class="form-check form-check-custom form-check-solid">
                                                        <input
                                                                class="form-check-input"
                                                                data-flag-depth2="${menu.menuIndex}"
                                                                type="checkbox"
                                                                value=""
                                                                id="flexCheckChecked-${menu.menuIndex}"
                                                                onclick="depth2MenuClick(this)"
                                                            ${not empty adminRole and menu.isCheck == 'Y' ? 'checked' : (empty adminRole ? 'checked' : '')}
                                                        />
                                                        <label class="form-check-label form-label fw-bolder text-dark pt-3" for="flexCheckChecked-${menu.menuIndex}">
                                                                ${menu.menuName}
                                                        </label>
                                                    </div>
                                                </div>
                                                <c:if test="${not empty menu.children}">
                                                    <c:forEach items="${menu.children}" var="item" varStatus="status">
                                                        <div class="col-lg custom-col-lg">
                                                            <div class="form-check form-check-custom form-check-solid mb-3">
                                                                <input
                                                                        class="form-check-input"
                                                                        data-flag-depth2="${menu.menuIndex}"
                                                                        data-flag-depth3="${item.menuIndex}"
                                                                        type="checkbox"
                                                                        value=""
                                                                        id="flexCheckChecked-${item.menuIndex}"
                                                                        onclick="depth3MenuClick(this)"
                                                                    ${not empty adminRole and item.isCheck == 'Y' ? 'checked' : (empty adminRole ? 'checked' : '')}
                                                                />
                                                                <label class="form-check-label form-label fw-bolder text-dark pt-3" for="flexCheckChecked-${item.menuIndex}">
                                                                        ${item.menuName}
                                                                </label>
                                                            </div>
                                                            <c:if test="${not empty item.children}">
                                                                <c:forEach items="${item.children}" var="subItem" varStatus="subStatus">
                                                                    <div class="row form-check-custom form-check-solid mb-3">
                                                                        <div class="col-lg-12">
                                                                            <div class="form-check form-check-custom form-check-solid">
                                                                                <input
                                                                                        class="form-check-input"
                                                                                        data-flag-depth2="${menu.menuIndex}"
                                                                                        data-flag-depth3="${item.menuIndex}"
                                                                                        type="checkbox"
                                                                                        value=""
                                                                                        id="flexCheckDefault-${subItem.menuIndex}"
                                                                                    ${not empty adminRole and subItem.isCheck == 'Y' ? 'checked' : (empty adminRole ? 'checked' : '')}
                                                                                />
                                                                                <label class="form-check-label" for="flexCheckDefault-${subItem.menuIndex}">
                                                                                        ${subItem.menuName}
                                                                                </label>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </c:if>
                                                        </div>
                                                    </c:forEach>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                    <hr>
                                </c:forEach>

                            </div>
                            <div class="card-footer" id="footerIdx">
                                <div class="row">
                                    <div id="listGroup" class="col-3">
                                    </div>
                                        <div class="col-9 d-flex justify-content-end">
                                            <button type="button" class="btn btn-light me-2" onclick="history.back()">취소</button>
                                            <c:choose>
                                                <c:when test="${not empty adminRole}">
                                                    <button type="button" class="btn btn-success" id="modifyBtn">
                                                    저장
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button type="button" class="btn btn-success" id="saveBtn">
                                                    저장
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                </div>
                            </div>
                        </div>
                        <!-- 본문 내용 END -->
                    </div>
                </div>
            </div>
            <%@ include file="/WEB-INF/views/common/inc/footer-inc.jsp" %>
        </div>
    </div>
</div>

</body>
<link rel="stylesheet" href="/assets/css/flatpickr/flatpickr.min.css">
<script src="/assets/js/flatpickr/flatpickr.js"></script>
<script src="/assets/js/flatpickr/ko.js"></script>
<script type="text/javascript">
    window.onbeforeunload = function() {
        if (isModified) {
            return "변경 사항을 저장하지 않았습니다. 정말 지금 나가시겠습니까?";
        }
    };

    // data 생성
    const makeData = () => {
        let params = {
            uid: document.getElementById('uid').value,
            adminGroupIndex: document.getElementById('idx').value,
            groupName: document.getElementById('groupName').value,
            description: document.getElementById('description').value,
            isUse: document.querySelector('input[name="isUse"]:checked').value,
            isMain: document.querySelector('input[name="isMain"]:checked') ? 'Y' : 'N',
            menus: collectMenuStates()
        };
        return params;
    };

    /*************************************** start: Valid ********************************************/
    // 유효성 체크 통합
    const validMessage = {
        groupName: {elem: null, msg: '* 관리자 그룹명을 입력해주세요.'},
        description: {elem: null, msg: '* 권한 설명을 입력해주세요.'},
    };

    // 에러메세지 제작
    const setMessage = (elem, isValid) => {
        const name = elem.name;

        // 에러 메세지 생성
        if (!isValid) {
            elem.disabled = true;
            if (!validMessage[name].elem) {
                validMessage[name].elem = document.createElement('small');
                validMessage[name].elem.style.color = 'red';
                elem.parentNode.insertBefore(validMessage[name].elem, elem.nextSibling);
                validMessage[name].elem.textContent = validMessage[name].msg;
            }
            elem.disabled = false;
            return
        }
        // 메세지 제거
        if (validMessage[name].elem) {
            validMessage[name].elem.remove();
            validMessage[name].elem = null;
        }
    }
    const clearInputIfInvalid = (elem) => {
        setMessage(elem, true);
    };
    const isValidInput = (value, type) => {
        if (type === 'groupName')
            return value.length > 1 && value.length < 21;
        if (type === 'description')
            return value.length > 1 && value.length < 101;
        return true;
    }
    const validForm = async () => {
        let groupName = document.getElementById('groupName');
        let description = document.getElementById('description');

        let groupNameBol = true;
        let descriptionBol = true;

        if (!groupName.value) {
            validMessage.groupName.msg = '* 관리자 그룹명을 입력해주세요.';
            groupNameBol = false;
        } else if(groupName.value.length < 2) {
            validMessage.groupName.msg = '* 관리자 그룹명은 2자 이상 입력해주세요.';
            groupNameBol = false;
        }
        if (!description.value) descriptionBol = false;

        // 메세지 세팅
        setMessage(groupName, groupNameBol);
        setMessage(description, descriptionBol);

        return groupNameBol && descriptionBol;
    }

    /*************************************** end : Valid ********************************************/
    /********************************************************************** POST ************************************************/
    let isSubmitting = false;
    const saveGroupPermission = async () => {
        if (isSubmitting) {
            return;
        }
        isSubmitting = true;

        // 전송 데이터 생성
        let data = makeData();
        if(!await validForm()) {
            isSubmitting = false;
            return false;
        }

        try {
            const response = await fetch('/api/v1/site/admin-privilege', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data)
            });

            const result = await response.json();

            if (result?.result === false) {
                const errorMsg = result.message.split('internal error - ')[1];
                Swal.fire({
                    title: '저장 실패',
                    text: errorMsg,
                    icon: 'error',
                    confirmButtonText: '확인'
                })
            } else if (response.ok == true) {
                if (result.uid) {
                    Swal.fire({
                        title: '저장 완료',
                        text: '저장이 완료되었습니다.',
                        icon: 'success',
                        buttonsStyling: !1,
                        confirmButtonText: '확인',
                        customClass: {confirmButton: 'btn btn-primary'}
                    }).then((result) => {
                        if (result.isConfirmed) {
                            location.href = '/site/admin-privilege';
                        }
                    })
                }
            }
        } catch (Exception) {
            console.error(Exception);
        } finally {
            isSubmitting = false;
        }
    }

    const collectMenuStates = () => {
        const menuStates = [];

        document.querySelectorAll('[data-flag-depth2]').forEach((menu, idx) => {
            const menuId = menu.getAttribute('id').split('-');
            const menuIndex = menuId.length === 2 ? menuId[1] : '';
            const isChecked = menu.checked ? 'Y' : 'N';
            menuStates[idx] = { menuIndex: menuIndex, permission: isChecked };
        });
        return menuStates;
    }

    /******************************************************************* PUT ************************************************/

    let isModified = false;

    async function modify(element) {
        if (isSubmitting) {
            return;
        }
        isSubmitting = true;
        // 전송 데이터 생성
        let data = makeData();
        if(!await validForm()) {
            isSubmitting = false;
            return false;
        }

        try {
            let uid = document.getElementById('uid').value;
            const response = await fetch('/api/v1/site/admin-privilege/update/' + uid, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data)
            });

            const result = await response.json();
            if (result?.result === false) {
                const errorMsg = result.message.split('internal error - ')[1];
                Swal.fire({
                    title: '수정 실패',
                    html: errorMsg,
                    icon: 'error',
                    confirmButtonText: '확인'
                })
            } else if (response.ok == true) {
                let uid = result.uid;
                if (uid) {
                    Swal.fire({
                        title: '수정 완료',
                        text: '등록되었습니다.',
                        icon: 'success',
                        buttonsStyling: !1,
                        confirmButtonText: '확인',
                        customClass: {confirmButton: 'btn btn-primary'}
                    }).then((result) => {
                        if (result.isConfirmed) {
                            location.href = '/site/admin-privilege/' + uid;
                        }
                    })
                } else if (result.data.includes("Validation error: ")) {
                    let errorMsg = result.data.split('Validation error:')[1];
                    Swal.fire({
                        title: '수정 실패',
                        text: errorMsg,
                        icon: 'error',
                        confirmButtonText: '확인'
                    })
                }
            }
        } catch (e) {
            console.error(e);
        } finally {
            isSubmitting = false;
        }

    }

    const depth2MenuClick =(element) => {
        let isChecked = element.checked;
        let depth2Uid = element.getAttribute('data-flag-depth2');
        let checkboxes = document.querySelectorAll('input[type="checkbox"][data-flag-depth2="' + depth2Uid + '"]');

        if (isChecked) {
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = true;
            });
        } else {
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = false;
            });
        }
    }

    const depth3MenuClick = (element) => {
        let isChecked = element.checked;

        let depth3Uid = element.getAttribute('data-flag-depth3');
        let checkboxes = document.querySelectorAll('input[type="checkbox"][data-flag-depth3="' + depth3Uid + '"]');

        if (isChecked) {
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = true;
            });
        } else {
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = false;
            });
        }
    }

    const save = () => {
        let chk = document.getElementById('idx').value;
        chk ? modify() : saveGroupPermission();
    }
    document.addEventListener('DOMContentLoaded', function () {
        if(document.getElementById('idx').value) {
            document.getElementById('modifyBtn').addEventListener('click', () => save());
        } else {
            document.getElementById('saveBtn').addEventListener('click', () => save());
        }
    });
</script>

<style>
    .custom-col-lg {
        flex: 0 0 12.5%;
        max-width: 12.5%;
    }



</style>