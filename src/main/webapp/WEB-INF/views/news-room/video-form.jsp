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
                            <input type="hidden" name="videoIndex" value="${detail.videoIndex}"/>
                            <!--begin::Card-->
                            <div class="card mb-7">
                                <!--begin::Card body-->
                                <div class="card-body">
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">브랜드</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">브랜드</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <select class="form-select mw-300px" aria-label="브랜드 선택" name="brand" required>
                                                <option value="">브랜드 선택</option>
                                                <c:forEach var="brand" items="${brandList}">
                                                    <option value="${brand.name}" <c:if test="${detail.brand eq brand.name}">selected</c:if>>${brand.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px" for="title">제목</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-200px" for="title">제목</label>
                                        <div class="col-sm-9 col-md-10 col-xl-10 col-xxl-9 fv-row">
                                            <input type="text" class="form-control" id="title" name="title" placeholder="제목" required maxlength="100" value="<c:out value="${detail.title}" />">
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
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px required" for="dz0">썸네일 이미지</label>
                                        <label class="d-block d-sm-none  fw-bolder w-xxl-200px required" for="dz0">썸네일 이미지</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <div class="row form-check col-form-label form-check-custom mw-1000px" id="dz0Container">
                                                <!-- 파일 업로드 섹션 -->
                                                <jsp:include page="/file-form-inc" flush="false">
                                                    <jsp:param name="viewType" value="${viewType}" />
                                                    <jsp:param name="parentTable" value="VIDEO" />
                                                    <jsp:param name="parentUid" value="${detail.uid}" />
                                                    <jsp:param name="parentType" value="FILE" />
                                                    <jsp:param name="hasOrder" value="false" />
                                                    <jsp:param name="maxFileCount" value="2"/>
                                                    <jsp:param name="isSecure" value="N"/>
                                                    <jsp:param name="DZID" value="dz0"/>
                                                    <jsp:param name="dropzoneIndex" value="0"/>
                                                </jsp:include>
                                            </div>
                                            <script type="text/javascript">
                                                let ntdrop = [];
                                            // 첨부파일 드롭존 설정
                                            ntdrop[0] = global.genDropzone('#dz0', {
                                                params: {
                                                    'ParentTable': 'VIDEO',
                                                    'ParentType': 'FILE',
                                                    'ParentUid': '${uid}',
                                                },
                                                hasOrder: false,
                                                isSecure: 'N',
                                                maxFileCount: 2,
                                                acceptedExt: ['.jpg', '.jpeg', '.png', 'webp', 'gif', 'apng', 'bmp'],
                                                required: 'Y',
                                                errorMsg : '이미지를 등록해주세요.',
                                                containerId : 'dz0Container',
                                                dropzoneIdx : 'dz0',
                                                maxFileSize : 53,
                                                dropzoneIndex : 0,
                                                messages : {
                                                    empty : '이미지를 등록해주세요.',
                                                    accept: '* 파일용량 및 이미지 파일 유형을 다시 확인해주세요.',
                                                    count: '',
                                                    size: '* 파일용량 및 이미지 파일 유형을 다시 확인해주세요.'
                                                },
                                                description: true
                                            });
                                            </script>
                                        </div>
                                    </div>

                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px" for="videoUrl">동영상 URL</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-200px" for="videoUrl">동영상 URL</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <input type="url" class="form-control" id="videoUrl" name="videoUrl" placeholder="동영상 URL" required maxlength="255" value="<c:out value="${detail.videoUrl}" />">
                                        </div>
                                    </div>
                                    <div class="py-3 row">
                                        <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px" for="videoScript">영상 스크립트</label>
                                        <label class="d-block d-sm-none fw-bolder required w-xxl-200px" for="videoScript">영상 스크립트</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <textarea class="form-control" rows="5" id="videoScript" minlength="1" maxlength="1000" name="videoScript" placeholder="영상 스크립트" required>${detail.videoScript}</textarea>
                                        </div>
                                    </div>
                                </div>
                                <!--end::Card body-->
                                <div class="card-footer d-flex justify-content-end">
                                    <div class="col d-flex justify-content-start">
                                        <a class="btn btn-dark" href="javascript: location.href='/news-room/video-list'+location.search;">목록</a>
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
<script src="/assets/js/file.js"></script>
<script type="text/javascript">
    const uid = document.querySelector('input[name=uid]').value;

    // validation
    // default required fields
    let fields = {};
    document.querySelectorAll('input:required,select:required,textarea:required').forEach(function(el) {
        let msg = el.placeholder;
        if(el.tagName == 'SELECT')
                msg = el.ariaLabel;
            msg += ' 은(는) 필수 입력 값입니다.';

        fields[el.name] = {validators: {notEmpty: {message: msg}}};
    });

    // custom fields

    fields['videoUrl'] = {
        selector: 'input[name=videoUrl]',
        validators: {
            notEmpty: {
                message: '동영상 URL은 필수 입력 값입니다.'
            },
            uri: {
                message: 'URL 형식 값을 입력하세요.'
            }
        }
    };

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
    async function validate() {
        validator
            .validate()
            .then(function(status) {
                if(status == 'Valid') {
                    if(dropzoneCheck() === true)
                        uploadByDropzone(0);
                }
            });
    }

    // submit form
    async function saveFormData() {
        let detailUrl = '/news-room/video';
        let url = '/api/v1'+detailUrl;
        let method = 'POST';
        let message = '등록되었습니다.';

        // 수정 모드일 때 변수 조정
        if (document.querySelector('input[name=videoIndex]').value !== '') {
            url += '/update/' + uid;
            message = '수정되었습니다.'
        }
        // formdata to json type
        let formDataObj = {};
        const formData = new FormData(document.detailForm);
        formData.forEach((value, key) => {
            formDataObj[key] = value;
        });

        if (document.querySelector('input[name=videoIndex]').value !== '') {
            let descriptionObj = {};
            if (document.querySelectorAll('[id^="dropzoneImagedz"] [name="description"]').length > 0) {
                let fileUids = document.querySelectorAll('[id^="dropzoneImagedz"] [name="fileUid"]');
                let descriptions = document.querySelectorAll('[id^="dropzoneImagedz"] [name="description"]');
                descriptionObj['description'] = [];
                descriptionObj['fileUid'] = [];

                descriptions.forEach(description => {
                    descriptionObj['description'].push(description.value);
                });

                fileUids.forEach(fileUid => {
                    descriptionObj['fileUid'].push(fileUid.value);
                });

                // 파일 UID와 설명 길이 확인
                if (fileUids.length !== descriptions.length) {
                    return; // 오류 발생 시 함수 종료
                }

                await fetch('/api/v1/file/file-update/update/description/' + uid, {
                    method: method,
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(descriptionObj)
                });
            }
        }

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
</html>
