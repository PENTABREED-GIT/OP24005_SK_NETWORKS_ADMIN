<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 개인정보수정 --%>
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
                <div class="post d-flex flex-column-fluid" id="kt_post">
                    <!--begin::Container-->
                    <div id="kt_content_container" class="container-fluid">
                        <!--begin::form-->
                        <form class="form" id="accountForm" name="accountForm" method="post" onsubmit="return false;">
                            <input type="hidden" name="isDuplicatedId" id="isDuplicatedId" value="${!empty admin ? 'N' : ''}"/>
                            <input type="hidden" name="isDuplicatedEmail" id="isDuplicatedEmail"
                                   value="${!empty admin ? 'N' : ''}"/>
                            <input type="hidden" name="uid" id="uid" value="${admin.uid}"/>
                            <input type="hidden" name="pwModDate" id="pwModDate" value="${admin.pwModDate}"/>

                            <!--begin::card-->
                            <div class="card mb-5">
                                <!--begin::card-Body-->
                                <div class="card-body">
                                    <!--begin::Input group-->
                                    <div class="row mb-6">
                                        <!--begin::Col-->
                                        <div class="col-md-6 mb-1 fv-row fv-plugins-icon-container">
                                            <!--begin::Label-->
                                            <label class="fs-5 fw-bold mb-2 required">관리자 ID</label>
                                            <!--end::Label-->
                                            <!--begin::row-->
                                            <div class="row fv-row fv-plugins-icon-container">
                                                <!--begin::Col-->
                                                <div class="col-sm-7">
                                                    <!--begin::Input-->
                                                    <input type="text" name="adminId" id="adminId"
                                                           class="form-control form-control-solid" placeholder="관리자 ID"
<%--                                                           value="${admin.adminId}" ${!empty admin ? 'readonly' : ''} maxlength="20"--%>
                                                           autocomplete="off">
                                                    <!--end::Input-->
                                                    <div id="messageForAdminId" class="text-primary" style="display: none;">사용가능한
                                                        아이디입니다.
                                                    </div>
                                                </div>
                                                <!--end::Col-->
<%--                                                <c:if test="${empty admin}">--%>
                                                    <!--begin::Col-->
                                                    <div class="col-sm-5">
                                                        <button class="btn btn-dark" onclick="checkDuplicatedId();">
                                                            <i class="las la-check"></i>중복확인
                                                        </button>
                                                    </div>
                                                    <!--end::Col-->
<%--                                                </c:if>--%>
                                            </div>
                                            <!--end::row-->
                                        </div>
                                        <!--end::Col-->

                                        <!--begin::Col-->
                                        <div class="col-md-6 fv-row fv-plugins-icon-container">
                                            <!--begin::Label-->
                                            <label class="fs-5 fw-bold mb-2 required">관리자명</label>
                                            <!--end::Label-->
                                            <!--begin::Input-->
                                            <input type="text" name="adminName" class="form-control form-control-solid"
                                                   placeholder="관리자명" <%--value="${admin.adminName}"--%> maxlength="20">
                                            <!--end::Input-->
                                            <!--begin::Hint-->
                                            <div class="form-text">예) 홍길동</div>
                                            <!--end::Hint-->
                                        </div>
                                        <!--end::Col-->
                                    </div>
                                    <!--end::Input group-->

                                    <!--begin::Input group-->
                                    <div class="row mb-6">
                                        <!--begin::Col-->
                                        <div class="col-md-6 fv-row fv-plugins-icon-container">
                                            <!--begin::Label-->
                                            <label class="fs-5 fw-bold mb-2">소속</label>
                                            <!--end::Label-->
                                            <!--begin::Input-->
                                            <input type="text" name="department" class="form-control form-control-solid"
                                                   placeholder="소속" <%--value="<c:out value="${admin.department}"/>"--%> >
                                            <!--end::Input-->
                                            <!--begin::Hint-->
                                            <div class="form-text">예) 디지털전략팀</div>
                                            <!--end::Hint-->
                                        </div>
                                        <!--end::Col-->

                                        <!--begin::Col-->
                                        <div class="col-md-6 fv-row fv-plugins-icon-container">
                                            <!--begin::Label-->
                                            <label class="fs-5 fw-bold mb-2">직책/직무</label>
                                            <!--end::Label-->
                                            <!--begin::Input-->
                                            <input type="text" name="duty" class="form-control form-control-solid" placeholder="직책/직무"
                                                   <%--value="${admin.duty}"--%>>
                                            <!--end::Input-->
                                            <!--begin::Hint-->
                                            <div class="form-text">예) 팀장</div>
                                            <!--end::Hint-->
                                        </div>
                                        <!--end::Col-->
                                    </div>
                                    <!--end::Input group-->

                                    <!--begin::Input group-->
                                    <div class="row mb-6">
                                        <!--begin::Col-->
                                        <div class="col-md-6 fv-row fv-plugins-icon-container">
                                            <!--begin::Label-->
                                            <label class="fs-5 fw-bold mb-2">연락처</label>
                                            <!--end::Label-->
                                            <!--begin::Input-->
                                            <input type="text" name="phoneNo" class="form-control form-control-solid" placeholder="연락처" value="${admin.phoneNoDecrypted}" minlength="8">
                                            <!--end::Input-->
                                            <!--begin::Hint-->
                                            <div class="form-text">예) 010-1234-5678</div>
                                            <!--end::Hint-->
                                        </div>
                                        <!--end::Col-->

                                        <!--begin::Col-->
                                        <div class="col-md-6 fv-row fv-plugins-icon-container">
                                            <!--begin::Label-->
                                            <label class="fs-5 fw-bold mb-2 required">e-mail</label>
                                            <!--end::Label-->
                                            <!--begin::row-->
                                            <div class="row fv-row fv-plugins-icon-container">
                                                <!--begin::Col-->
                                                <div class="col-sm-7">
                                                    <!--begin::Input-->
                                                    <input type="email" name="email" class="form-control form-control-solid"
                                                           placeholder="e-mail" value="${admin.emailDecrypted}" autocomplete="off"/>
                                                    <!--end::Input-->
                                                    <div id="messageForEmail" class="text-primary" style="display: none;">사용가능한
                                                        이메일입니다.
                                                    </div>
                                                </div>
                                                <!--end::Col-->
                                                <!--begin::Col-->
                                                <div class="col-sm-5">
                                                    <button class="btn btn-dark" onclick="checkDuplicatedEmail();">
                                                        <i class="las la-check"></i>중복확인
                                                    </button>
                                                </div>
                                                <!--end::Col-->
                                            </div>
                                            <!--end::row-->
                                        </div>
                                        <!--end::Col-->
                                    </div>
                                    <!--end::Input group-->

                                    <!--begin::Input group-->
                                    <div class="row mb-6">
                                        <!--begin::Col-->
                                        <div class="col-md-6 fv-row fv-plugins-icon-container" id="pwDiv" data-kt-password-meter="true">
                                            <!--begin::Label-->
                                            <label class="fs-5 fw-bold mb-2 required">비밀번호</label>
                                            <!--end::Label-->
                                            <!--begin::Input wrapper-->
                                            <div class="position-relative mb-3">
                                                <input class="form-control form-control-lg form-control-solid" type="password"
                                                       name="adminPw" id="adminPw" autocomplete="off" minlength="8" maxlength="32">
                                                <span class="btn btn-sm btn-icon position-absolute translate-middle top-50 end-0 me-n2"
                                                      data-kt-password-meter-control="visibility">
                                    <i class="bi bi-eye-slash fs-2"></i>
                                    <i class="bi bi-eye fs-2 d-none"></i>
                                </span>
                                            </div>
                                            <!--end::Input wrapper-->
                                            <!--begin::Meter-->
                                            <div class="d-flex align-items-center mb-3" data-kt-password-meter-control="highlight">
                                                <div class="flex-grow-1 bg-secondary bg-active-success rounded h-5px me-2"></div>
                                                <div class="flex-grow-1 bg-secondary bg-active-success rounded h-5px me-2"></div>
                                                <div class="flex-grow-1 bg-secondary bg-active-success rounded h-5px me-2"></div>
                                                <div class="flex-grow-1 bg-secondary bg-active-success rounded h-5px"></div>
                                            </div>
                                            <!--end::Meter-->
                                            <!--begin::Hint-->
                                            <c:if test="${admin != null}">
                                                <div class="form-text text-info">Use only to change your password.</div>
                                            </c:if>
                                            <div class="form-text text-info">Password must be at least 8 character and contain number and symbols <br>
                                                <strong>(More than three spaces are stable.)</strong></div>
                                            <!--end::Hint-->
                                        </div>
                                        <!--end::Col-->

                                        <!--begin::Col-->
                                        <div class="col-md-6 fv-row fv-plugins-icon-container" id="pwDiv2">
                                            <!--begin::Label-->
                                            <label class="fs-5 fw-bold mb-2 required" for="pwConfirm">Verify Password</label>
                                            <!--end::Label-->
                                            <!--begin::Input-->
                                            <input class="form-control form-control-lg form-control-solid" type="password" id="pwConfirm" name="pwConfirm" autocomplete="off" minlength="8" maxlength="32"/>
                                            <!--end::Input-->
                                        </div>
                                        <span class="fw-bolder fs-5" id="pwNotice">비밀번호 변경 후, 24시간이 지나지 않았으므로 다시 변경할 수 없습니다.</span>

                                        <!--end::Col-->
                                    </div>
                                    <!--end::Input group-->
                                </div>
                                <!--end::card-Body-->
                                <!--begin::Card-footer-->
                                <div class="card-footer d-flex justify-content-center py-6 px-9">
                                    <a href="/environment/AccountList" data-kt-element="apps-submit"
                                       class="btn btn-flex btn-light btn-active-primary fw-bolder me-2">취소</a>
                                    <!--begin::Button-->
                                    <button type="button" id="saveBtn" class="btn btn-primary">
                                        <span class="indicator-label">Save</span>
                                        <span class="indicator-progress">Please wait...
                                        <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                                    </span>
                                    </button>
                                    <!--end::Button-->
                                </div>
                                <!--end::Card-footer-->
                            </div>
                            <!--end::card-->
                        </form>
                        <!--end::form-->
                    </div>
                    <!--end::Container-->
                </div>
            </div>
            <!--end::Content-->
            <%@ include file="/WEB-INF/views/common/inc/footer-inc.jsp" %>
        </div>
        <!--end::Wrapper-->
    </div>
    <!--end::Page-->
</div>
<!--end::Root-->
<!--end::Main-->
</body>
</html>
<!--begin::Javascript-->
<!--begin::Page Custom Javascript(used by this page)-->
<script type="text/javascript">
    let fv;

    document.addEventListener('DOMContentLoaded', function (e) {
        const saveBtn = document.getElementById('saveBtn');
        const form = document.getElementById('accountForm');
        fv = FormValidation.formValidation(form, {
            fields: {
                adminId: {
                    validators: {
                        notEmpty: {
                            message: '아이디를 입력해주세요.'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_-]+$/i,
                            message: '영문, 숫자, _, - 만 사용 가능합니다.'
                        },
                        callback: {
                            message: '아이디 중복확인을 해주세요.',
                            callback: function (input) {
                                if (input.value.length <= 0) return true;
                                return document.getElementById('isDuplicatedId').value === 'N';

                            }
                        }
                    }
                },
                adminName: {
                    validators: {
                        notEmpty: {
                            message: '관리자명을 입력해주세요.'
                        },
                    }
                },
                phoneNo: {
                    validators: {
                        callback: {
                            message: '전화번호 형식을 지켜서 입력해주세요.',
                            callback: function (input) {
                                if (input.value.length > 0) {
                                    return NTUtil.isValidPhoneNo(input.value, 'ko');
                                }
                                return true;
                            }
                        }
                    },

                },
                email: {
                    validators: {
                        notEmpty: {
                            message: 'Please enter your e-mail.'
                        },
                        emailAddress: {
                            message: 'Please enter a valid email.'
                        },
                        callback: {
                            message: 'Check for duplicate email.',
                            callback: function (input) {
                                if (input.value.length <= 0) return true;
                                return document.getElementById('isDuplicatedEmail').value === 'N';

                            }
                        }
                    },

                },
                adminPw: {
                    validators: {
                        callback: {
                            callback: function (input) {
                                if (input.value.trim().length <= 0) {
                                    return true;
                                }
                                // return NTUtil.isValidPassword(document.getElementById("adminPw").value, document.getElementById("adminId").value);
                                return NTUtil.isValidPassword(input.value);
                            },
                            message: 'Please follow the password format and enter it.'
                        }
                    }
                },
                pwConfirm: {
                    validators: {
                        identical: {
                            compare: function () {
                                return form.querySelector('[name="adminPw"]').value;
                            },
                            message: 'Passwords do not match.'
                        }
                    }
                },
            },
            plugins: {
                trigger: new FormValidation.plugins.Trigger(),
                bootstrap: new FormValidation.plugins.Bootstrap5({
                    rowSelector: ".fv-row",
                    eleInvalidClass: "",
                    eleValidClass: ""
                }),
                declarative: new FormValidation.plugins.Declarative({
                    html5Input: true,
                })
            }
        });

        document.querySelector('#accountForm input[name=adminId]').addEventListener('input', (e) => {
            document.getElementById('isDuplicatedId').value = '';
            NTUtil.getElement('#messageForAdminId').next().show();
            document.getElementById('messageForAdminId').style.display = 'none';
        });
        document.querySelector('#accountForm input[name=email]').addEventListener('input', (e) => {
            document.getElementById('isDuplicatedEmail').value = '';
            NTUtil.getElement('#messageForEmail').next().show();
            document.getElementById('messageForEmail').style.display = 'none';
        });

        saveBtn.addEventListener('click', function () {
            saveBtn.disabled = true;
            saveBtn.setAttribute('data-kt-indicator', 'on')
            fv.validate().then(function (status) {
                if (status === 'Valid') {
                    if(!isValidPW(document.getElementById('adminPw').value)) {
                        return false;
                    }
                    let formData = new FormData(document.getElementById('accountForm'));
                    NTUtil.ajax(
                        {
                            url: 'UpdateAccountAJX',
                            method: 'POST',
                            data: formData,
                            success: function (response) {
                                if (response.resultCode === 'SUCCESS') {
                                    location.reload();
                                } else {
                                    Swal.fire({
                                        title: "Error!",
                                        text: "An error has occurred while processing",
                                        icon: "error",
                                        buttonsStyling: !1,
                                        confirmButtonText: "OK",
                                        customClass: {confirmButton: "btn btn-primary"}
                                    }).then(() => {
                                        saveBtn.disabled = false;
                                        saveBtn.removeAttribute('data-kt-indicator');
                                    });
                                }
                            }
                        }
                    ).fail((e) => {
                            Swal.fire({
                                title: "Error!",
                                text: "An error has occurred while processing",
                                icon: "error",
                                buttonsStyling: !1,
                                confirmButtonText: "OK",
                                customClass: {confirmButton: "btn btn-primary"}
                            }).then(() => {
                                saveBtn.disabled = false;
                                saveBtn.removeAttribute('data-kt-indicator');
                            });

                            console.error('FAIL', e);
                        }
                    );
                } else {
                    // saveBtn.disabled = false;
                    // saveBtn.removeAttribute('data-kt-indicator');
                    resetBtn("saveBtn");
                }
            });
        });
    });

    function isValidPW(param) {
        let data = param;
        let num = data.search(/[0-9]/g);
        let eng = data.search(/[a-z]/ig);
        let spe = data.search(/[()`~!@@#$%^&*_|₩₩₩'₩";:₩/?]/gi);

        if(data.length == 0 && document.getElementById('pwConfirm').value.length == 0) {
            return true;
        }

        if (num < 0 || eng < 0 || spe < 0) {
            Swal.fire({
                title: "Error!",
                html: "Please set a password using all <strong>letters, numbers, and special characters</strong>.",
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "OK",
                customClass: {confirmButton: "btn btn-primary"}
            }).then(() => {
                resetBtn("saveBtn");
            });
            return false;
        }

        if (data.length < 8) { // 비밀번호 8자리 이상이어야 함
            Swal.fire({
                title: "Error!",
                html: "Please set a password of <strong>8 or more digits</strong>.",
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "OK",
                customClass: {confirmButton: "btn btn-primary"}
            }).then(() => {
                resetBtn("saveBtn");
            });
            return false;
        }
        if (data.search(/\s/) !== -1) {
            Swal.fire({
                title: "Error!",
                html: "Passwords cannot contain <strong>spaces</strong>.",
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "OK",
                customClass: {confirmButton: "btn btn-primary"}
            }).then(() => {
                resetBtn("saveBtn");
            });
            return false;
        }
        if (/(\w)\1\1\1/.test(data) || /(\W)\1\1\1/.test(data)) {
            Swal.fire({
                title: "Error!",
                html: "The same letter, number, or special character of 4 or more <strong>cannot be duplicated</strong>.",
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "OK",
                customClass: {confirmButton: "btn btn-primary"}
            }).then(() => {
                resetBtn("saveBtn");
            });
            return false;
        }

        let validId = document.getElementById('adminId').value;
        let validPw = document.getElementById('adminPw').value;
        let tmp = "";
        let cnt = 0;
        for(var i=0; i<validId.length-3; i++){
            tmp = validId.charAt(i) + validId.charAt(i+1) + validId.charAt(i+2) + validId.charAt(i+3);
            if(validPw.indexOf(tmp) > -1){
                cnt = cnt + 1;
            }
        }
        if(cnt > 0){
            Swal.fire({
                title: "Error!",
                html: "The password contains <strong>4 or more digits of ID</strong>. \n Please check the password setting guide.",
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "OK",
                customClass: {confirmButton: "btn btn-primary"}
            }).then(() => {
                resetBtn("saveBtn");
            });
            return false;
        }

        if(data != document.getElementById('pwConfirm').value) {
            resetBtn("saveBtn");
            return false;
        }
        return true;
    }

    function checkDuplicatedId() {
        const adminId = document.querySelector('#accountForm input[name=adminId]').value;

        if (adminId.length <= 0) {
            Swal.fire({
                text: "아이디를 입력하세요.",
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "OK",
                customClass: {confirmButton: "btn btn-primary"}
            });
            return;
        }

        let formData = new FormData();
        formData.append('adminId', adminId);
        NTUtil.ajax(
            {
                url: 'IsDuplicatedIDAJX',
                method: 'POST',
                data: formData,
                success: function (response) {
                    if (response.resultCode === 'SUCCESS') {
                        document.getElementById('messageForAdminId').style.display = 'inline';
                        document.getElementById('isDuplicatedId').value = 'N';

                        document.getElementById("messageForAdminId").innerText = '사용 가능한 아이디입니다.';
                        document.getElementById("messageForAdminId").style['display'] = 'inline';
                        document.getElementById("messageForAdminId").classList.remove('text-danger');
                        document.getElementById("messageForAdminId").classList.add('text-primary');

                        NTUtil.getElement('#messageForAdminId').next().hide();
                    } else {
                        document.getElementById('messageForAdminId').style.display = 'inline';
                        document.getElementById('isDuplicatedId').value = 'Y';

                        document.getElementById("messageForAdminId").innerText = 'This ID is in use.';
                        document.getElementById("messageForAdminId").style['display'] = 'inline';
                        document.getElementById("messageForAdminId").classList.remove('text-primary');
                        document.getElementById("messageForAdminId").classList.add('text-danger');
                    }
                }
            }
        ).fail((e) => {
                Swal.fire({
                    title: "Error!",
                    text: "An error has occurred while processing",
                    icon: "error",
                    buttonsStyling: !1,
                    confirmButtonText: "OK",
                    customClass: {confirmButton: "btn btn-primary"}
                });

                console.error('FAIL', e);
            }
        );
    }

    function checkDuplicatedEmail() {
        const email = document.querySelector('#accountForm input[name=email]').value;

        if (email.length <= 0) {
            Swal.fire({
                text: "이메일를 입력하세요.",
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "OK",
                customClass: {confirmButton: "btn btn-primary"}
            });
            return;
        }

        let formData = new FormData();
        formData.append('email', email);
        NTUtil.ajax(
            {
                url: '/environment/IsDuplicatedEmailAJX',
                method: 'POST',
                data: formData,
                success: function (response) {
                    if (response.resultCode === 'SUCCESS') {
                        document.getElementById('messageForEmail').style.display = 'inline';
                        document.getElementById('isDuplicatedEmail').value = 'N';

                        document.getElementById("messageForEmail").innerText = '사용 가능한 이메일입니다.';
                        document.getElementById("messageForEmail").style['display'] = 'inline';
                        document.getElementById("messageForEmail").classList.remove('text-danger');
                        document.getElementById("messageForEmail").classList.add('text-primary');

                        NTUtil.getElement('#messageForEmail').next().hide();
                    } else {
                        document.getElementById('messageForEmail').style.display = 'inline';
                        document.getElementById('isDuplicatedEmail').value = 'Y';

                        document.getElementById("messageForEmail").innerText = 'This email is in use.';
                        document.getElementById("messageForEmail").style['display'] = 'inline';
                        document.getElementById("messageForEmail").classList.remove('text-primary');
                        document.getElementById("messageForEmail").classList.add('text-danger');
                    }
                }
            }
        ).fail((e) => {
                Swal.fire({
                    title: "Error!",
                    text: "An error has occurred while processing",
                    icon: "error",
                    buttonsStyling: !1,
                    confirmButtonText: "OK",
                    customClass: {confirmButton: "btn btn-primary"}
                });

                console.error('FAIL', e);
            }
        );
    }

    let date1 = new Date(document.getElementById('pwModDate').value);
    let now = new Date();

    if ((now - date1) > 86400000) {
        document.querySelector('#pwNotice').style.display = 'none';
    } else {
        // document.querySelector('#pwDiv').style.display = 'none';
        // document.querySelector('#pwDiv2').style.display = 'none';
        document.querySelector('#adminPw').setAttribute("readonly", true);
        document.querySelector('#pwConfirm').setAttribute("readonly", true);
        document.querySelector('#pwNotice').style.display = '';
    }

    function resetBtn(btnId) {
        const targetBtn = document.getElementById(btnId);
        targetBtn.disabled = false;
        targetBtn.removeAttribute('data-kt-indicator');
    }
</script>
<!--end::Page Custom Javascript-->
<!--end::Javascript-->