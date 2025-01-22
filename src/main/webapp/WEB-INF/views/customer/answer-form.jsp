<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                        <form name="answerForm" method="post">
                            <input type="hidden" name="uid" value="<c:out value="${uid}"/>"/>
                            <input type="hidden" name="inquiryIndex" value="<c:out value="${detail.inquiryIndex}"/>"/>
                            <!--begin::Card-->
                            <div class="card mb-7">
                                <!--begin::Card body-->
                                <div class="card-body">
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px required">제목</label>
                                        <div class="col-sm-9 col-md-10 col-xl-10 col-xxl-9 fv-row">
                                            <input type="text" id="answerTitle" name="answerTitle" class="form-control" value="Re: <c:out value="${detail.title}"/>" required/>
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">이메일</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <input type="text" id="email" name="email" class="form-control" value="<c:out value="${detail.getEmailDecrypted()}"/>" disabled required/>
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">답변 작성일</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <jsp:useBean id="now" class="java.util.Date" scope="page" />
                                            <fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />
                                            <input type="text" name="answerDate" class="form-control" value="<c:out value="${today}"/>" disabled required/>
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-white">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px" for="questionContent">질문 내용</label>
                                        <label class="d-block d-sm-none fw-bolder w-xxl-200px" for="questionContent">질문 내용</label>
                                        <div class="col-sm-9 col-md-10 col-xl-10 col-xxl-9 fv-row">
                                            <textarea class="form-control" rows="7" id="questionContent" minlength="1" name="questionContent" placeholder="고객 문의" disabled><c:out value="${detail.content}"/></textarea>
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-white">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px required" for="answerContent">내용</label>
                                        <label class="d-block d-sm-none fw-bolder w-xxl-200px" for="answerContent">내용</label>
                                        <div class="col-sm-9 col-md-10 col-xl-10 col-xxl-9 fv-row">
                                            <textarea class="form-control" rows="10" id="answerContent" minlength="1" name="answerContent" placeholder="고객 문의 답변" required></textarea>
                                        </div>
                                    </div>
<%--                                    <script type="text/javascript">--%>
<%--                                        $(document.answerForm.content).froalaEditor({--%>
<%--                                            key: ${froalaKey},--%>
<%--                                            toolbarButtons: ['fullscreen', 'bold', 'italic', 'underline', 'strikeThrough', 'subscript', 'superscript', '|', 'fontFamily', 'fontSize', 'color', 'inlineClass', 'inlineStyle', 'paragraphStyle', 'lineHeight', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', 'quote', '-', 'insertLink', 'insertImage', 'insertTable', '|', 'specialCharacters', 'insertHR', 'selectAll', 'clearFormatting', '|', 'print', 'getPDF', 'spellChecker', 'help', 'html', '|', 'undo', 'redo'],--%>
<%--                                            imageUploadURL: '/froalaeditor/upload/Image',--%>
<%--                                            imageUploadParams: {editorUploadPath: '${editorUploadPath}'},--%>
<%--                                            imageDefaultWidth: 0,--%>
<%--                                            language: 'en',--%>
<%--                                            videoUpload: false,--%>
<%--                                            fileUpload: false,--%>
<%--                                            height: 300,--%>
<%--                                            requestHeaders: {--%>
<%--                                                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').getAttribute('content')--%>
<%--                                            }--%>
<%--                                        });--%>
<%--                                    </script>--%>
<%--                                    <div class="py-3 row">--%>
<%--                                        <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px" for="dz0">이미지</label>--%>
<%--                                        <label class="d-block d-sm-none  fw-bolder w-xxl-200px" for="dz0">이미지</label>--%>
<%--                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">--%>
<%--                                            <div class="row form-check col-form-label form-check-custom mw-1000px" id="dz0Container">--%>
<%--                                                <!-- 파일 업로드 섹션 -->--%>
<%--                                                <jsp:include page="/file-form-inc" flush="false">--%>
<%--                                                    <jsp:param name="viewType" value="${viewType}" />--%>
<%--                                                    <jsp:param name="parentTable" value="PRESS" />--%>
<%--                                                    <jsp:param name="parentUid" value="${detail.uid}" />--%>
<%--                                                    <jsp:param name="parentType" value="FILE" />--%>
<%--                                                    <jsp:param name="hasOrder" value="false" />--%>
<%--                                                    <jsp:param name="maxFileCount" value="2"/>--%>
<%--                                                    <jsp:param name="isSecure" value="N"/>--%>
<%--                                                    <jsp:param name="DZID" value="dz0"/>--%>
<%--                                                    <jsp:param name="dropzoneIndex" value="0"/>--%>
<%--                                                </jsp:include>--%>
<%--                                            </div>--%>
<%--                                            <script type="text/javascript">--%>
<%--                                                let ntdrop = [];--%>
<%--                                            // 첨부파일 드롭존 설정--%>
<%--                                            ntdrop[0] = global.genDropzone('#dz0', {--%>
<%--                                                params: {--%>
<%--                                                    'ParentTable': 'PRESS',--%>
<%--                                                    'ParentType': 'FILE',--%>
<%--                                                    'ParentUid': '${uid}',--%>
<%--                                                },--%>
<%--                                                hasOrder: false,--%>
<%--                                                isSecure: 'N',--%>
<%--                                                maxFileCount: 2,--%>
<%--                                                acceptedExt: ['.jpg', '.jpeg', '.png', 'webp', 'gif', 'apng', 'bmp'],--%>
<%--                                                required: 'N',--%>
<%--                                                errorMsg : '이미지를 등록해주세요.',--%>
<%--                                                containerId : 'dz0Container',--%>
<%--                                                dropzoneIdx : 'dz0',--%>
<%--                                                maxFileSize : 53,--%>
<%--                                                dropzoneIndex : 0,--%>
<%--                                                messages : {--%>
<%--                                                    empty : '이미지를 등록해주세요.',--%>
<%--                                                    accept: '* 파일용량 및 이미지 파일 유형을 다시 확인해주세요.',--%>
<%--                                                    count: '',--%>
<%--                                                    size: '* 파일용량 및 이미지 파일 유형을 다시 확인해주세요.'--%>
<%--                                                },--%>
<%--                                                description: true--%>
<%--                                            });--%>
<%--                                            </script>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
                                </div>
                                <!--end::Card body-->
                                <div class="card-footer d-flex justify-content-center">
                                    <div class="col d-flex justify-content-start">
                                        <a class="btn btn-primary" onclick="goList();">목록</a>
                                    </div>
                                    <div class="col d-flex justify-content-end">
                                        <a class="btn btn-success me-2" onclick="validate();">답변 완료</a>
                                        <a class="btn btn-secondary" onclick="noAnswerRequired();">답변 불필요</a>
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
<script src="/assets/js/file.js"></script>
<script type="text/javascript">
    const uid = document.querySelector('input[name=uid]').value;

    // validation
    // default required fields
    let fields = {};
    document.querySelectorAll('textarea:required,input:required').forEach(function(el) {
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

    let validator = FormValidation.formValidation(document.answerForm, formValidationOption);

    // validate before submit
    async function validate() {
        validator
            .validate()
            .then(function(status) {
                if(status == 'Valid') {
                    Swal.fire({
                        title: '알림',
                        text: '작성하신 내용으로 고객문의 답변 메일을 회신 하시겠습니까?',
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: '회신',
                        cancelButtonText: '취소',
                        customClass: {confirmButton: 'btn btn-primary', cancelButton: 'btn btn-light-dark'}
                    }).then((result) => {
                        if (result.isConfirmed) {
                            saveFormData();
                        }
                    });
                } else {
                    Swal.fire({
                        title: '등록 실패',
                        text: '필수 입력 항목 중 입력되지 않은 항목이 존재합니다. 입력 후 답변완료 하시기 바랍니다.',
                        icon: 'warning',
                        buttonsStyling: !1,
                        confirmButtonText: '확인',
                        customClass: {confirmButton: 'btn btn-primary'}
                    }).then(() => {
                        return false;
                    });
                }
            });
    }

    // submit form
    async function saveFormData() {
        let detailUrl = '/customer/inquiry';
        let url = '/api/v1'+detailUrl;
        let method = 'POST';
        let message = '등록되었습니다.';

        // // 수정 모드일 때 변수 조정
        // if (document.querySelector('input[name=uid]').value !== '') {
        //     method = 'PUT';
        //     url += '/' + uid;
        //     message = '수정되었습니다.'
        // }
        // formdata to json type
        let formDataObj = {};
        formDataObj['email'] = document.getElementById('email').value;
        const formData = new FormData(document.answerForm);
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
            if('SEND FAIL' != res.data) {
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
            } else {
                Swal.fire({
                    title: '메일 발송 실패',
                    text: '고객 문의 답변 발송에 실패하였습니다.',
                    icon: 'error',
                    buttonsStyling: !1,
                    confirmButtonText: '확인',
                    customClass: {confirmButton: 'btn btn-primary'}
                }).then(() => {
                    return;
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

    function goList() {
        if (document.getElementById('answerContent').value == '') {
            location.href = '/customer/inquiry-list' + location.search;
        } else {
            Swal.fire({
                title: '목록 돌아가기',
                text: '입력된 항목이 저장되지 않습니다. \n목록으로 돌아가시겠습니까?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: '확인',
                cancelButtonText: '취소',
                customClass: {confirmButton: 'btn btn-primary', cancelButton: 'btn btn-light-dark'}
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = '/customer/inquiry-list' + location.search;
                }
            });
        }
    }

    function noAnswerRequired() {
        Swal.fire({
            title: '알림',
            text: '답변 불필요 선택 시 해당 고객문의에 대해 답변 등록 및 회신이 불가능해집니다. 답변 불필요 처리를 진행하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: '확인',
            cancelButtonText: '취소',
            customClass: {confirmButton: 'btn btn-primary', cancelButton: 'btn btn-light-dark'}
        }).then(async (result) => {
            if (result.isConfirmed) {
                let url = '/api/v1/customer/inquiry-no-required/' + uid;
                let method = 'POST';

                let formDataObj = {};
                const formData = new FormData(document.answerForm);
                formData.forEach((value, key) => {
                    formDataObj[key] = value;
                });

                const response = await fetch(url, {
                    method: method,
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(formDataObj)
                });

                if (response.ok) { // success
                    const res = await response.json();
                    console.log(res);
                    if (res.data == 'NOANSWERDONE') {
                        location.href = '/customer/inquiry-list' + location.search;
                    } else {
                        Swal.fire({
                            title: '알림',
                            text: '답변 불필요 처리가 정상적으로 되지 않았습니다.',
                            icon: 'error',
                            buttonsStyling: !1,
                            confirmButtonText: '확인',
                            customClass: {confirmButton: 'btn btn-primary'}
                        }).then(() => {
                            return false;
                        });
                    }
                }
            }
        });
    }
</script>
</html>
