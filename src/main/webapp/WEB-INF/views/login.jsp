<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--
Product Name: Metronic - #1 Selling Bootstrap 5 HTML Multi-demo Admin Dashboard ThemeAuthor: KeenThemes
Purchase: https://1.envato.market/EA4JPWebsite: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Dribbble: www.dribbble.com/keenthemes
Like: www.facebook.com/keenthemes
License: For each use you must have a valid license purchased only from above link in order to legally use the theme for your project.-->
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
        <!-- 이전 코드 생략 -->


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
                <h1 class="d-flex align-items-center text-dark fw-bolder my-1 fs-1">
<%--                    <img alt="mainLogo" src="/assets/images/logos/logo-2.svg" style="height:25px;"/>--%>
                    <img alt="Logo" src="assets/images/logos/logo-1.png" class="h-45px"/>
                </h1>
            </a>
            <!--end::Logo-->
            <!--begin::Wrapper-->
            <div class="w-lg-500px bg-white rounded shadow-sm p-10 p-lg-15 mx-auto">
                <!--begin::Form-->
                <form class="form w-100" id="pwdForm" name="pwdForm" onsubmit="return false;" style="display:none;">
                    <div class="mb-10 fs-5" id="over180days">비밀번호를 사용하신지 <span class="fw-bolder"><span id="loginDay"> </span>일이 넘었습니다. <div>비밀번호를 변경해주세요.</div> </div>
                    <!--begin::Input group-->
                    <div class="fv-row mb-10">
                        <!--begin::Wrapper-->
                        <div class="d-flex flex-stack mb-2">
                            <!--begin::Label-->
                            <label class="form-label fw-bolder text-dark fs-6 mb-0" for="password">현재 비밀번호</label>
                            <!--end::Label-->
                            <!--begin::Link-->
                            <%--                            <a href="#" class="link-primary fs-6 fw-bolder">아이디 찾기</a>--%>
                            <!--end::Link-->
                        </div>
                        <!--end::Wrapper-->
                        <!--begin::Input-->
                        <input class="form-control form-control-lg form-control-solid" type="password" id="password" name="password" autocomplete="off"/>
                        <!--end::Input-->
                        <input type="hidden" id="adminUid">
                    </div>
                    <!--end::Input group-->
                    <!--begin::Input group-->
                    <div class="fv-row mb-10">
                        <!--begin::Wrapper-->
                        <div class="d-flex flex-stack mb-2">
                            <!--begin::Label-->
                            <label class="form-label fw-bolder text-dark fs-6 mb-0" for="adminId">새 비밀번호</label>
                            <!--end::Label-->
                            <!--begin::Link-->
                            <%--                            <a href="#" class="link-primary fs-6 fw-bolder">아이디 찾기</a>--%>
                            <!--end::Link-->
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
                            <label class="form-label fw-bolder text-dark fs-6 mb-0" for="pwConfirm">새 비밀번호 확인</label>
                            <!--end::Label-->
                            <!--begin::Link-->
                            <%--                            <a href="#" class="link-primary fs-6 fw-bolder">비밀번호 찾기</a>--%>
                            <!--end::Link-->
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
                            <span class="text-danger">영어/숫자/특수문자를, 조합하여 8글자이상 사용하세요.</span>
                        </li>
                    </ul>
                    <!--end::List-->
                    <!--begin::Actions-->
                    <div class="text-center">
                        <!--begin::Submit button-->
                        <button type="button" id="pwdModNextBtn" class="btn btn-lg btn-bg-secondary w-40 mb-5"
                                style="margin-right: 20px;display:none;">
                            <span class="indicator-label">다음에 변경</span>
                            <span class="indicator-progress">변경중...
							    <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                            </span>
                        </button>
                        <a href="/Login">
                            <button type="button" id="goLoginPage" class="btn btn-lg btn-bg-secondary w-40 mb-5" style="margin-right: 20px;">
                                <span class="indicator-label">취소</span>
                                <span class="indicator-progress">변경중...
							    <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                            </span>
                            </button>
                        </a>
                        <button type="button" id="pwdModBtn" class="btn btn-lg btn-primary w-40 mb-5">
                            <span class="indicator-label">변경</span>
                            <span class="indicator-progress">변경중...
							    <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                            </span>
                        </button>
                    </div>
                    <!--end::Actions-->
                </form>
                <!--end::Form-->

                <form class="form w-100" id="loginForm" name="loginForm" onsubmit="return false;">
                    <!--begin::Heading-->
                    <%--                    <div class="text-center mb-10">--%>
                    <%--                        <!--begin::Title-->--%>
                    <%--                        <h1 class="text-dark mb-3">Sign In to Metronic</h1>--%>
                    <%--                        <!--end::Title-->--%>
                    <%--                        <!--begin::Link-->--%>
                    <%--                        <div class="text-gray-400 fw-bold fs-4">New Here?--%>
                    <%--                            <a href="authentication/flows/basic/sign-up.html" class="link-primary fw-bolder">Create an Account</a></div>--%>
                    <%--                        <!--end::Link-->--%>
                    <%--                    </div>--%>
                    <!--begin::Heading-->
                    <!--begin::Input group-->
                    <div class="fv-row mb-10">
                        <!--begin::Wrapper-->
                        <div class="d-flex flex-stack mb-2">
                            <!--begin::Label-->
                            <label class="form-label fw-bolder text-dark fs-6 mb-0" for="adminId">ID</label>
                            <!--end::Label-->
                            <!--begin::Link-->
                            <%--                            <a href="#" class="link-primary fs-6 fw-bolder">아이디 찾기</a>--%>
                            <!--end::Link-->
                        </div>
                        <!--end::Wrapper-->
                        <!--begin::Input-->
                        <input class="form-control form-control-lg form-control-solid" type="text" id="adminId" name="adminId" autocomplete="off"/>
                        <!--end::Input-->
                    </div>
                    <!--end::Input group-->
                    <!--begin::Input group-->
                    <div class="fv-row mb-10">
                        <!--begin::Wrapper-->
                        <div class="d-flex flex-stack mb-2">
                            <!--begin::Label-->
                            <label class="form-label fw-bolder text-dark fs-6 mb-0" for="adminPw">Password</label>
                            <!--end::Label-->
                            <!--begin::Link-->
                            <%--                            <a href="#" class="link-primary fs-6 fw-bolder">비밀번호 찾기</a>--%>
                            <!--end::Link-->
                        </div>
                        <!--end::Wrapper-->
                        <!--begin::Input-->
                        <input class="form-control form-control-lg form-control-solid" type="password" id="adminPw" name="adminPw" autocomplete="off"/>
                        <!--end::Input-->
                    </div>
                    <%--<div class="fv-row mb-10">
                        <div class="fw-bolder fs-6 text-end"><a href="/account/password-mod-form">Find ID / Password</a></div>
                    </div>--%>
                    <!--end::Input group-->
                    <!--begin::Actions-->
                    <div class="text-center">
                        <!--begin::Submit button-->
                        <button type="button" id="submitBtn" class="btn btn-lg btn-primary w-100 mb-5">
                            <span class="indicator-label">로그인</span>
                            <span class="indicator-progress">Please wait...
							    <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                            </span>
                        </button>
                    </div>
                    <!--end::Actions-->
                </form>
                <!--begin::List-->
                <ul class="fs-6 fw-bold mb-6" id="subText">
                    <li>
                        <span class="text-gray-700">본 사이트는 SK Networks 임직원 및 인가된 사용자만 사용할 수 있으며, 사용자 접속 모니터링 및 로그를 기록하고 있습니다.</span>
                    </li>
                    <li class="my-2">
                        <span class="text-danger">불법 사용 시 법령에 의해 민·형사 상 책임을 질 수 있습니다.</span>
                    </li>
                    <li>
                        <span class="text-gray-700">비밀번호 5회 오류 시 계정 사용이 제한됩니다.</span>
                    </li>
                </ul>
                <!--end::List-->
                <!--end::Form-->
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

        pwdfv = FormValidation.formValidation(pwdForm, {
            fields: {
                password: {
                    validators: {
                        identical: {
                            compare: function () {
                                return document.getElementById("adminPw").value;
                            },
                            message: 'Please enter the same password.'
                        }
                    }
                },
                newPassword: {
                    validators: {
                        callback: {
                            message: 'Please follow the password format and enter it.',
                            callback: function (input, input) {
                                return NTUtil.isValidPassword(document.getElementById("newPassword").value, document.getElementById("adminId").value)
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
                            message: 'Please enter the same password.'
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
                url: "/admin/ModifyNextDateAJX",
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
                    let uid = document.querySelector('#adminUid').value;
                    NTUtil.ajax({
                        url: '/api/v1/account/password/' + uid,
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

        const submitBtn = document.getElementById('submitBtn');
        const form = document.getElementById('loginForm');

        fv = FormValidation.formValidation(form, {
            fields: {
                adminId: {
                    validators: {
                        notEmpty: {
                            message: 'Enter your ID'
                        }
                    }
                },
                adminPw: {
                    validators: {
                        notEmpty: {
                            message: 'Enter Your Password'
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

        document.getElementById('adminPw').addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                submitBtn.click();
            }
        });

        submitBtn.addEventListener('click', function () {

            submitBtn.disabled = true;
            submitBtn.setAttribute('data-kt-indicator', 'on')
            fv.validate().then(async function (status) {
                if (status === 'Valid') {
                    let formData = new FormData(document.getElementById('loginForm'));

                    await axios.post('/api/v1/login', formData, {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        responseType: 'json',
                    })
                    .then((res) => {
                        let errorResult = res.data.result;
                        if (res.data.resultCode === 'SUCCESS') {
                            const alertMsg = '[최근 로그인 정보]\n\n - 일시 :' + res.data.lastLoginDate + '\n - IP :' + res.data.lastIp +'\n\n [확인] 선택 시 다른 PC에서의 연결을 종료하고 로그인 합니다.';
                            alert(alertMsg);
                            location.href = '/';
                        } else if (errorResult === 'LOCKED') {
                            Swal.fire({
                                html: "<div style='text-align: left'>" + res.data.message + "</div>",
                                icon: 'error',
                                buttonsStyling: !1,
                                confirmButtonText: 'OK',
                                customClass: {confirmButton: 'btn btn-primary'}
                            }).then(() => {
                                location.href = '/login';
                            });
                        } else if (errorResult === 'MOD PASSWORD') {
                            document.querySelector('#adminUid').value = res.data.uid;
                            Swal.fire({
                                html: "<div style='text-align: left'>비밀번호가 변경된지 180일이 지나, 비밀번호를 변경하여야 합니다.</div>",
                                icon: 'error',
                                buttonsStyling: !1,
                                confirmButtonText: 'OK',
                                customClass: {confirmButton: 'btn btn-primary'}
                            }).then(() => {
                                document.querySelector('#goLoginPage').style.display = 'none';
                                document.querySelector('#loginDay').textContent = '180';
                                document.querySelector('#loginForm').style.display = 'none';
                                document.querySelector('#pwdForm').style.display = '';
                                document.querySelector('#subText').style.display = 'none';
                            });
                        } else {
                            Swal.fire({
                                html: "아이디 또는 비밀번호를 잘못 입력했습니다.",
                                icon: 'error',
                                buttonsStyling: !1,
                                confirmButtonText: 'OK',
                                customClass: {confirmButton: 'btn btn-primary'}
                            }).then(() => {
                                location.href = '/login';
                            });
                        }
                    })
                    .catch((error) => {
                        console.error('FAIL', error);
                        Swal.fire({
                            html: "로그인 실패",
                            icon: 'error',
                            buttonsStyling: !1,
                            confirmButtonText: 'OK',
                            customClass: {confirmButton: 'btn btn-primary'}
                        }).then(() => {
                            submitBtn.disabled = false;
                            submitBtn.removeAttribute('data-kt-indicator');
                        });
                    })
                } else {
                    submitBtn.disabled = false;
                    submitBtn.removeAttribute('data-kt-indicator');
                }
            });
        });

    });

</script>
<!--end::Page Custom Javascript-->
<!--end::Javascript-->

</html>