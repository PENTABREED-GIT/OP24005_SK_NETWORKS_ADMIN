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
                <div id="kt_content_container" class="container-fluid">
                    <form name="adminGroupForm">
                        <input type="hidden" name="uid" value="${adminGroup.uid}"/>
                        <input type="hidden" name="adminGroupIndex" value="${adminGroup.adminGroupIndex}"/>
                    <div class="container-fluid">
                        <!-- 본문 내용 START -->
                            <div class="card mb-7">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                            <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">작성자</label>
                                            <label class="d-block d-sm-none  required fw-bolder w-xxl-200px" for="userType">작성자</label>
                                            <div id="userType" class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row align-content-center">
                                                <c:choose>
                                                    <c:when test="${adminGroup ne null}">
                                                        <c:out value="${adminGroup.adminId}"/>(<c:out value="${adminGroup.department}"/>)
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${sessionScope.ADMIN.adminId}"/>(<c:out value="${sessionScope.ADMIN.department}"/>)
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <c:if test="${adminGroup ne null}">
                                            <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                                <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">작성일시</label>
                                                <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">작성일시</label>
                                                <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row align-content-center">
                                                    <c:choose>
                                                        <c:when test="${adminGroup.modDate ne null and adminGroup.modDate ne ''}">
                                                            <c:out value="${adminGroup.modDate}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${adminGroup.regDate}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </c:if>
                                        <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                            <label for="groupName" class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">관리자 그룹명</label>
                                            <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">관리자 그룹명</label>
                                            <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                                <input type="text" placeholder="관리자 그룹명을 입력해 주세요." class="form-control mw-300px" id="groupName" name="groupName" value="${adminGroup.groupName}" maxlength="20" required
                                                       >
<%--                                                <div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"><div data-field="contentsTitle" data-validator="notEmpty">관리자 그룹명을 입력해 주세요.</div></div>--%>
                                            </div>
                                        </div>
                                        <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                            <label for="description" class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">권한 설명</label>
                                            <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">권한 설명</label>
                                            <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                                <input type="text" placeholder="관리자 설명을 입력해 주세요." class="form-control" id="description" name="description" value="${adminGroup.description}" maxlength="100" required >
                                            </div>
                                        </div>
                                        <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                            <label for="isUse" class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">사용 여부</label>
                                            <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">사용 여부</label>
                                            <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                                <div class="form-check col-form-label form-switch form-check-custom form-check-solid">
                                                    <input class="form-check-input" type="checkbox" name="isUse" value="Y" id="isUse" ${empty adminGroup || adminGroup.isUse eq 'Y'  ? 'checked' : ''}>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>


                        <div class="card mb-5 mb-xl-10">
                            <div class="card-header border-0 pt-5">
                                <div class="text-gray-900 fs-2 me-1 form-label fw-bolder text-dark">
                                    권한 설정
                                </div>
                            </div>
                            <div class="card-body">
                                <c:set var="prevDepth" value=""/>

                                <c:forEach items="${adminGroupPermissionList}" var="menu">
                                    <c:set var="marginLeft" value="${(menu.depth - 1) * 25}"/>
                                    <c:if test="${prevDepth ne '' && prevDepth ne '1' && menu.depth eq '1'}">
                                        <hr>
                                    </c:if>
                                    <div class="row mb-8">
                                        <div class="col-lg-12">
                                            <label class="form-check form-check-custom form-check-solid me-10" style="margin-left: ${marginLeft}px;" >
                                                <input class="form-check-input h-20px w-20px" type="checkbox" name="menuIndex" value="${menu.menuIndex}" data-depth="${menu.depth}" data-parent-index="${menu.parentIndex}"
                                                       onclick="selectMenu(this)"
                                                       <c:if test="${menu.adminGroupIndex ne null}">checked</c:if>
                                                />
                                                <span class="form-check-label fw-bolder">
                                                    ${menu.location2}
                                                </span>
                                            </label>
                                        </div>
                                    </div>
                                    <c:set var="prevDepth" value="${menu.depth}"/>
                                </c:forEach>

                            </div>
                            <div class="card-footer" id="footerIdx">
                                <div class="row">
                                    <div id="listGroup" class="col-3">
                                    </div>
                                        <div class="col-9 d-flex justify-content-end">
                                            <button type="button" class="btn btn-light me-2" onclick="history.back()">취소</button>
                                            <button type="button" class="btn btn-success" id="saveBtn" onclick="saveAdminGroup();">저장</button>
                                        </div>
                                </div>
                            </div>
                        </div>
                        <!-- 본문 내용 END -->
                    </div>
                    </form>
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
            } else if (response.ok === true) {
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
            const response = await fetch('/api/v1/site/admin-group/update/' + uid, {
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
                            location.href = '/site/admin-group/' + uid;
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

    let validator;
    let isProcessing = false;
    document.addEventListener("DOMContentLoaded", () => {
        validator = new NTValidatorMetronic(document.adminGroupForm);
    });

    function selectMenu() {

    }

    async function saveAdminGroup() {
        debugger;
        if (isProcessing) return;
        isProcessing = true;

        const isValid = validator.valid();
        if (!isValid) {
            isProcessing = false;
            return;
        }

        // 전송 데이터 생성
        let adminGroup = {};
        adminGroup.uid = document.adminGroupForm.uid.value;
        adminGroup.adminGroupIndex = document.adminGroupForm.adminGroupIndex.value;
        adminGroup.groupName = document.adminGroupForm.groupName.value;
        adminGroup.description = document.adminGroupForm.description.value;
        adminGroup.isUse = document.adminGroupForm.isUse.checked? 'Y':'N';

        adminGroup.adminGroupPermissionList = [];
        document.adminGroupForm.querySelectorAll('input[name=menuIndex]:checked').forEach((el) => {
            let adminMenu = {};
            adminMenu.menuIndex = el.value;
            adminGroup.adminGroupPermissionList.push(adminMenu);
        });
        let url = '/api/v1/account/admin-group';
        let mode = 'REGISTER';
        if (adminGroup.adminGroupIndex !== '') {
            mode = 'MODIFY';
            url += '/update/' + adminGroup.uid;
        }
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(adminGroup)
        });

        const result = await response.json();
        if (result?.result === false) {
            Swal.fire({
                title: '오류',
                html: '저장 처리 도중 오류가 발생했습니다.',
                icon: 'error',
                confirmButtonText: '확인'
            })
            isProcessing = false;
            return;
        }

        Swal.fire({
            title: '저장 완료',
            text: '관리자 그룹이 저장 되었습니다.',
            icon: 'success',
            buttonsStyling: !1,
            confirmButtonText: '확인',
            customClass: {confirmButton: 'btn btn-primary'}
        }).then(() => {
            if (mode === 'MODIFY') {
                location.href = '/site/admin-group/' + adminGroup.uid;
            } else {
                location.href = '/site/admin-group';
            }
        });
    }
</script>
