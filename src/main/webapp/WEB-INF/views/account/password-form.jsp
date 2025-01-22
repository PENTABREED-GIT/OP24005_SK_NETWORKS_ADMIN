<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 비밀번호 변경 폼 --%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<!--begin::Head-->
<head>
    <base href="../../../">
    <meta charset="utf-8"/>
    <title>SK Networks 관리자</title>
    <meta name="description"
          content="Metronic admin dashboard live demo. Check out all the features of the admin panel. A large number of settings, additional services and widgets."/>
    <meta name="keywords"
          content="Metronic, bootstrap, bootstrap 5, Angular 11, VueJs, React, Laravel, admin themes, web design, figma, web development, ree admin themes, bootstrap admin, bootstrap dashboard"/>
    <%--    <link rel="canonical" href="Https://preview.keenthemes.com/metronic8" />--%>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta http-equiv="Content-Security-Policy" content="default-src * self blob: data: gap:; style-src * self 'unsafe-inline' blob: data: gap:; script-src * 'self' 'unsafe-eval' 'unsafe-inline' blob: data: gap:; object-src * 'self' blob: data: gap:; img-src * self 'unsafe-inline' blob: data: gap:; connect-src self * 'unsafe-inline' blob: data: gap:; frame-src * self blob: data: gap:;">
    <link rel="shortcut icon" href="assets/images/logos/favicon.ico"/>
    <!--begin::Fonts-->
    <link rel="stylesheet" href="/assets/css/font/fonts.css" />
    <!--end::Fonts-->
    <!--begin::Global Stylesheets Bundle(used by all pages)-->
    <link href="/assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css"/>
    <link href="/assets/css/style.bundle.css" rel="stylesheet" type="text/css"/>
    <!--end::Global Stylesheets Bundle-->
    <script src="/assets/js/custom/axios.min.js"></script>
    <meta name="csrf-token" content="${_csrf.token}">
</head>
<!--end::Head-->
<!--begin::Body-->
<body id="kt_body" class="bg-active-opacity-25" oncontextmenu="return false;">
<!--begin::Main-->
<div class="d-flex flex-column flex-root">
    <!--begin::Authentication - Sign-in -->
    <div class="d-flex flex-column flex-column-fluid bgi-position-y-bottom position-x-center bgi-no-repeat bgi-size-contain bgi-attachment-fixed"
         style="/*background-image: url(assets/media/illustrations/progress-hd.png)*/">
        <!--begin::Content-->
        <div class="d-flex flex-center flex-column flex-column-fluid p-10 pb-lg-20">
            <!--begin::Logo-->
            <a href="#" class="mb-12">
                <%--                <img alt="Logo" src="assets/images/logos/logo-1.png" class="h-45px"/>--%>
                <h1 class="d-flex align-items-center text-dark fw-bolder my-1 fs-1">Admin</h1>
            </a>
            <!--end::Logo-->
            <!--begin::Wrapper-->
            <div class="w-lg-500px bg-white rounded shadow-sm p-10 p-lg-15 mx-auto">
                <form class="form w-100" id="pwdForm" name="pwdForm" onsubmit="return false;">
                    <!--begin::Input group-->
                    <div class="fv-row mb-10">
                        <!--begin::Wrapper-->
                        <div class="d-flex flex-stack mb-2">
                            <!--begin::Label-->
                            <label class="form-label fw-bolder text-dark fs-6 mb-0" for="password">Password</label>
                            <!--end::Label-->
                        </div>
                        <!--end::Wrapper-->
                        <!--begin::Input-->
                        <input class="form-control form-control-lg form-control-solid" type="password" id="password" name="password" autocomplete="off"/>
                        <!--end::Input-->
                    </div>
                    <!--end::Input group-->
                    <!--begin::Input group-->
                    <div class="fv-row mb-10">
                        <!--begin::Wrapper-->
                        <div class="d-flex flex-stack mb-2">
                            <!--begin::Label-->
                            <label class="form-label fw-bolder text-dark fs-6 mb-0" for="newPassword">New Password</label>
                            <!--end::Label-->
                        </div>
                        <!--end::Wrapper-->
                        <!--begin::Input-->
                        <input class="form-control form-control-lg form-control-solid" type="password" id="newPassword" name="newPassword" autocomplete="off"/>
                        <!--end::Input-->
                    </div>
                    <!--end::Input group-->
                    <!--begin::Input group-->
                    <div class="fv-row mb-10">
                        <!--begin::Wrapper-->
                        <div class="d-flex flex-stack mb-2">
                            <!--begin::Label-->
                            <label class="form-label fw-bolder text-dark fs-6 mb-0" for="pwConfirm">Checked New Password</label>
                            <!--end::Label-->
                        </div>
                        <!--end::Wrapper-->
                        <!--begin::Input-->
                        <input class="form-control form-control-lg form-control-solid" type="password" id="pwConfirm"
                               name="pwConfirm" autocomplete="off"/>
                        <!--end::Input-->
                    </div>
                    <!--end::Input group-->
                    <!--begin::List-->
                    <ul class="fs-6 fw-bold mb-6">
                        <li class="my-2">
                            <span class="text-danger">영문/숫자/특수문자 중 2가지 이상 조합, 11~16자 이내 설정</span>
                        </li>
                    </ul>
                    <!--end::List-->
                    <!--begin::Actions-->
                    <div class="text-center">
                        <!--begin::Submit button-->
                        <button type="button" id="pwdModNextBtn" class="btn btn-lg btn-bg-secondary w-40 mb-5"
                                style="margin-right: 20px;">
                            <span class="indicator-label">다음에 변경하기</span>
                            <span class="indicator-progress">저장 중...
							    <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                            </span>
                        </button>
                        <a href="/Login">
                            <button type="button" id="goLoginPage" class="btn btn-lg btn-bg-secondary w-40 mb-5" style="margin-right: 20px; display:none">
                                <span class="indicator-label">취소</span>
                                <span class="indicator-progress">저장 중...
							    <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                            </span>
                            </button>
                        </a>
                        <button type="button" id="pwdModBtn" class="btn btn-lg btn-primary w-40 mb-5">
                            <span class="indicator-label">비밀번호 변경하기</span>
                            <span class="indicator-progress">저장 중...
							    <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                            </span>
                        </button>
                    </div>
                    <!--end::Actions-->
                </form>
            </div>
            <!--end::Wrapper-->
        </div>
        <!--end::Content-->
        <!--begin::Footer-->
        <div class="d-flex flex-center flex-column-auto p-10">
        </div>
        <!--end::Footer-->
    </div>
    <!--end::Authentication - Sign-in-->
</div>
<!--end::Main-->
</body>
<!--end::Body-->

<!--begin::Javascript-->
<!--begin::Global Javascript Bundle(used by all pages)-->
<script src="/assets/plugins/global/plugins.bundle.js"></script>
<script src="/assets/js/scripts.bundle.js"></script>
<script src="/assets/js/NTUtil.js"></script>
<!--end::Global Javascript Bundle-->
<!--begin::Page Custom Javascript(used by this page)-->
<script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function (e) {
        const pwdModBtn = document.getElementById("pwdModBtn");
        const pwdForm = document.pwdForm;
        const pwdModNextBtn = document.getElementById("pwdModNextBtn");
        const uid = "";

        pwdfv = FormValidation.formValidation(pwdForm, {
            fields: {
                password: {
                    validators: {
                        identical: {
                            compare: function () {
                                return document.getElementById("adminPw").value;
                            },
                            message: '동일한 비밀번호를 입력해주세요.'
                        }
                    }
                },
                newPassword: {
                    validators: {
                        callback: {
                            message: '비밀번호 형식을 지켜서 입력해주세요.',
                            callback: function (input) {
                                return NTUtil.isValidPassword(document.getElementById("newPassword").value)
                            }
                        }
                    }
                },
                pwConfirm: {
                    validators: {
                        identical: {
                            compare: function () {
                                return document.getElementById("newPassword").value;
                            },
                            message: '동일한 비밀번호를 입력해주세요.'
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
                })
            }
        });

        function modifyNextTime() {
            pwdModNextBtn.disable = true;
            pwdModNextBtn.setAttribute('data-kt-indicator', 'on');

            NTUtil.ajax({
                url: "/api/v1/account/password/next/update/" + uid,
                method: 'POST',
                success: function (resp) {
                    if (resp.resultCode === "SUCCESS") {
                        location.href = "/";
                    } else {
                        Swal.fire({
                            text: '처리 도중 오류가 발생하였습니다.',
                            icon: 'error',
                            buttonsStyling: !1,
                            confirmButtonText: '확인',
                            customClass: {confirmButton: 'btn btn-primary'}
                        }).then(() => {
                            pwdModNextBtn.disabled = false;
                            pwdModNextBtn.removeAttribute('data-kt-indicator');
                        });
                    }
                }
            });
        }

        function modifyPassword() {
            pwdModBtn.disabled = true;
            pwdModBtn.setAttribute('data-kt-indicator', 'on');
            pwdfv.validate().then(function (status) {

                let pwdModData = new FormData(document.getElementById('pwdForm'))
                if (status === "Valid") {
                    NTUtil.ajax({
                        url: "/api/v1/account/password/update/" + uid,
                        method: 'POST',
                        data: pwdModData,
                        success: function (resp) {
                            if (resp.resultCode == "SUCCESS") {
                                location.href = "/";
                            } else {
                                let message = '';
                                if (resp.resultMsg === null || resp.resultMsg === '' || resp.resultMsg === undefined) {
                                    message = "처리 도중 오류가 발생하였습니다.";
                                } else {
                                    message = resp.resultMsg;
                                }

                                Swal.fire({
                                    html: message,
                                    icon: 'error',
                                    buttonsStyling: !1,
                                    confirmButtonText: 'OK',
                                    customClass: {confirmButton: 'btn btn-primary'}
                                }).then(() => {
                                    pwdModBtn.disabled = false;
                                    pwdModBtn.removeAttribute('data-kt-indicator');
                                });

                            }
                        }
                    });
                } else {
                    pwdModBtn.disabled = false;
                    pwdModBtn.removeAttribute('data-kt-indicator');
                }
            })

        }
        pwdModBtn.addEventListener("click", modifyPassword);
        pwdModNextBtn.addEventListener("click", modifyNextTime);

    });
</script>
<!--end::Page Custom Javascript-->
<!--end::Javascript-->
</html>
