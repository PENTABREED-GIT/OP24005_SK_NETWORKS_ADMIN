<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/common/inc/page-header-inc.jsp" %>

</head>
<!--begin::Body-->
<body id="kt_body" class="header-fixed header-tablet-and-mobile-fixed toolbar-enabled toolbar-fixed toolbar-tablet-and-mobile-fixed aside-enabled aside-fixed" style="--kt-toolbar-height:55px;--kt-toolbar-height-tablet-and-mobile:55px">
<!--begin::Main-->
<!--begin::Root-->
<div class="d-flex flex-column flex-root">
    <!--begin::Page-->
    <div class="page d-flex flex-row flex-column-fluid">
        <%@ include file="/WEB-INF/views/common/inc/body-aside-inc.jsp" %>
        <!--begin::Wrapper-->
        <div class="wrapper d-flex flex-column flex-row-fluid" id="kt_wrapper">
            <%@ include file="/WEB-INF/views/common/inc/body-header-inc.jsp" %>
            <!--begin::Content-->
            <div class="content d-flex flex-column flex-column-fluid" id="kt_content">
                <%@ include file="/WEB-INF/views/common/inc/toolbar-inc.jsp" %>
                <!--begin::Post-->
                <div class="post d-flex flex-column-fluid" id="kt_post">
                    <!--begin::Container-->
                    <div id="kt_content_container" class="container-fluid">
                        <!--begin::Form-->
                        <form name="detailForm" method="post">
                            <input type="hidden" name="uid" value="${uid}"/>
                            <input type="hidden" name="irScheduleIndex" value="${detail.irScheduleIndex}"/>
                            <!--begin::Card-->
                            <div class="card mb-7">
                                <!--begin::Card body-->
                                <div class="card-body">
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px" for="irScheduleType">일정구분</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-200px" for="irScheduleType">일정구분</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <input type="text" class="form-control mw-400px" id="irScheduleType" name="irScheduleType" placeholder="일정구분" required maxlength="25" value="<c:out value="${detail.irScheduleType}" />">
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px" for="title">행사명</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-200px" for="title">행사명</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <input type="text" class="form-control" id="title" name="title" placeholder="행사명" required maxlength="100" value="<c:out value="${detail.title}" />">
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end required fw-bolder fw-bolder w-xxl-200px">행사지역</label>
                                        <label class="d-block d-sm-none  required fw-bolder fw-bolder w-xxl-200px">행사지역</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <div class="form-check form-check-inline col-form-label">
                                                <input class="form-check-input" type="radio" name="classification" value="DOMESTIC" id="classification_d" placeholder="구분" required<c:if test="${detail.classification eq 'DOMESTIC'}"> checked</c:if>/>
                                                <label class="form-check-label" for="classification_d">국내</label>
                                            </div>
                                            <div class="form-check form-check-inline col-form-label">
                                                <input class="form-check-input" type="radio" name="classification" value="OVERSEAS" id="classification_o" placeholder="구분" required<c:if test="${detail.classification eq 'OVERSEAS'}"> checked</c:if>/>
                                                <label class="form-check-label" for="classification_o">해외</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200 border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end required fw-bolder fw-bolder w-xxl-200px">게시언어</label>
                                        <label class="d-block d-sm-none  required fw-bolder fw-bolder w-xxl-200px">게시언어</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <div class="form-check form-check-inline col-form-label">
                                                <input class="form-check-input" type="radio" name="lang" placeholder="게시언어" value="KO" id="LANG_KO"<c:if test="${detail.lang eq 'KO'}"> checked</c:if> required/>
                                                <label class="form-check-label" for="LANG_KO">국문</label>
                                            </div>
                                            <div class="form-check form-check-inline col-form-label">
                                                <input class="form-check-input" type="radio" name="lang" placeholder="게시언어" value="EN" id="LANG_EN"<c:if test="${detail.lang eq 'EN'}"> checked</c:if> required/>
                                                <label class="form-check-label" for="LANG_EN">영문</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px" for="isOpen">노출여부</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-200px" for="isOpen">노출여부</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <div class="form-check col-form-label form-switch form-check-custom form-check-solid">
                                                <input class="form-check-input" type="checkbox" name="isOpen" value="Y" id="isOpen"<c:if test="${detail.isOpen eq 'Y'}"> checked</c:if>/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="py-3 row">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px" for="openDate">행사시간</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-200px" for="openDate">행사시간</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <div class="input-group input-group-fluid">
                                                <span class="input-group-text"><i class="bi bi-calendar-check text-primary"></i></span>
                                                <input type="text" class="form-control mw-200px flatpickr-input" id="openDate" name="openDate" placeholder="일자" autocomplete="off" required value="<c:out value="${detail.openDate}" />">
                                                <span class="input-group-text"><i class="bi bi-clock text-primary"></i></span>
                                                <input type="text" class="form-control mw-200px flatpickr-input" id="openTime" name="openTime" placeholder="시간" autocomplete="off" required value="<c:out value="${detail.openTime}" />">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--end::Card body-->
                                <div class="card-footer d-flex justify-content-end">
                                    <div class="col d-flex justify-content-start">
                                        <a class="btn btn-dark" href="javascript: location.href='/ir/ir-schedule-list'+location.search;">목록</a>
                                    </div>
                                    <div class="col d-flex justify-content-end">
                                        <a type="button" class="btn btn-light me-2" onclick="history.back();">취소</a>
                                        <button type="button" class="btn btn-success" onclick="validate()">저장</button>
                                    </div>
                                </div>
                            </div>
                            <!--end::Card-->
                        </form>
                        <!--end::Form-->
                    </div>
                    <!--end::Container-->
                </div>
                <!--end::Post-->
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
<!--end::Body-->
<script type="text/javascript">
    const uid = document.querySelector('input[name=uid]').value;

    // validation
    // default required fields
    let fields = {};
    document.querySelectorAll('input:required,select:required').forEach(function(el) {
        let msg = el.placeholder;
        if(el.tagName == 'SELECT')
                msg = el.ariaLabel;
            msg += ' 은(는) 필수 입력 값입니다.';

        fields[el.name] = {validators: {notEmpty: {message: msg}}};
    });

    // validator
    let formValidationOption = {
        fields: fields,
        plugins: {
            trigger: new FormValidation.plugins.Trigger(),
            bootstrap: new FormValidation.plugins.Bootstrap5({
                rowSelector: '.fv-row',
                eleInvalidClass: 'is-invalid',
                eleValidClass: ''
            }),
            //tachyons: new FormValidation.plugins.Tachyons(),
            submitButton: new FormValidation.plugins.SubmitButton()

        }
    };

    let validator = FormValidation.formValidation(document.detailForm, formValidationOption);

    // validate before submit
    function validate() {
        validator
            .validate()
            .then(function(status) {
                if(status == 'Valid') {
                    submit();
                }
            });
    }

    // submit form
    async function submit() {
        let detailUrl = '/ir/ir-schedule';
        let url = '/api/v1'+detailUrl;
        let method = 'POST';
        let message = '등록되었습니다.';

        // 수정 모드일 때 변수 조정
        if (document.querySelector('input[name=irScheduleIndex]').value !== '') {
            url += '/update/' + uid;
            message = '수정되었습니다.'
        }
        // formdata to json type
        let formDataObj = {};
        const formData = new FormData(document.detailForm);
        formData.forEach((value, key) => {
            formDataObj[key] = value;
        });

        // submit
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formDataObj)
        });

        // response
        if(response.ok) { // success
            const res = await response.json();
            console.log(res);
            if(res.result) {
                Swal.fire({
                    title: '등록 완료',
                    text: message,
                    icon: 'success',
                    buttonsStyling: !1,
                    confirmButtonText: '확인',
                    customClass: {confirmButton: 'btn btn-primary'}
                }).then(() => {
                    window.location.href = detailUrl+'/' + uid+location.search;
                });
            }
        } else { // fail
            Swal.fire({
                title: '등록 실패',
                text: '등록 처리 중 오류가 발생하였습니다.',
                icon: 'error',
                buttonsStyling: !1,
                confirmButtonText: '확인',
                customClass: {confirmButton: 'btn btn-light'}
            });
        }
    }
</script>
<link rel="stylesheet" href="/assets/css/flatpickr/flatpickr.min.css">
<script src="/assets/js/flatpickr/flatpickr.js"></script>
<script src="/assets/js/flatpickr/ko.js"></script>
<script type="text/javascript">
    flatpickr(document.detailForm.openDate, {
        dateFormat: 'Y.m.d',
        locale: 'ko',
    });
    flatpickr(document.detailForm.openTime, {
        enableTime: true,
        noCalendar: true,
        locale: 'ko',
        dateFormat: "H:i",
    });
</script>
</html>
