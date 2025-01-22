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
                            <!--begin::Card-->
                            <div class="card mb-7">
                                <!--begin::Card body-->
                                <div class="card-header">
                                    <h3 class="card-title fw-bolder">기본정보</h3>
                                </div>
                                <div class="card-body" id="base">
                                    <input type="hidden" name="uid" value="${uid}"/>
                                    <input type="hidden" name="businessContentsIndex" value="${detail.businessContentsIndex}"/>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">사업분야</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">사업분야</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <select class="form-select mw-300px" aria-label="사업분야 선택" name="businessAreaIndex" required>
                                                <option value="">사업분야 선택</option>
                                            <c:forEach var="businessArea" items="${businessAreaList}">
                                                <option value="${businessArea.businessAreaIndex}" <c:if test="${detail.businessAreaIndex eq businessArea.businessAreaIndex}">selected</c:if>>${businessArea.nameKo}</option>
                                            </c:forEach>
                                            </select>
                                        </div>

                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px" for="title">게시글 제목</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-200px" for="title">게시글 제목</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <input type="text" class="form-control" id="title" name="title" placeholder="게시글 제목" required maxlength="50" minlength="1" value="<c:out value="${detail.title}" />">
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200 border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder fw-bolder w-xxl-200px">게시언어</label>
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
                                    <div class="py-3 row">
                                        <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px" for="isOpen">노출여부</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-200px" for="isOpen">노출여부</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <div class="form-check col-form-label form-switch form-check-custom form-check-solid">
                                                <input class="form-check-input" type="checkbox" name="isOpen" value="Y" id="isOpen"<c:if test="${detail.isOpen eq 'Y'}"> checked</c:if>/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--end::Card body-->
                            </div>
                            <!--end::Card-->
                            <!--begin::Card-->
                            <div class="card mb-7">
                                <!--begin::Card body-->
                                <div class="card-header">
                                    <h3 class="card-title fw-bolder">부가정보</h3>
                                </div>
                                <script type="text/javascript">
                                    let ntdrop = [];
                                </script>
                                <c:choose>
                                    <c:when test="${not empty addList}">
                                        <c:forEach var="add" items="${addList}" varStatus="addStatus" begin="0">
                                <div class="card-body" id="add${addStatus.index}">
                                    <input type="" name="detailUid" value="${add.uid}"/>
                                    <input type="hidden" name="businessContentsDetailIndex" value="${add.businessContentsDetailIndex}"/>
                                    <div class="card-header" style="min-height: 50px;">
                                        <h3 class="card-title fw-bolder"># ${addStatus.index+1}
                                            <c:if test="${not addStatus.first}">
                                            <div class="card-toolbar" style="margin-left: 20px;"><button type="button" class="btn btn-sm btn-danger" onclick="resetContents('${addStatus.index}');">콘텐츠 삭제</button></div>
                                            </c:if>
                                        </h3>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-150px" for="detailTitle${addStatus.index}">Title</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-150px" for="detailTitle${addStatus.index}">Title</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <textarea class="form-control" data-add-index="${addStatus.index}" id="detailTitle${addStatus.index}" name="detailTitle" placeholder="Title"<c:if test="${addStatus.first}"> required</c:if> maxlength="150"><c:out value="${add.detailTitle}" /></textarea>
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-150px" for="detailContent${addStatus.index}">내용</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-150px" for="detailContent${addStatus.index}">내용</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <textarea class="form-control" id="detailContent${addStatus.index}" name="detailContent" placeholder="내용" <c:if test="${addStatus.first}"> required</c:if> maxlength="500"><c:out value="${add.detailContent}" /></textarea>
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 col-xl-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-150px" for="additionalContent${addStatus.index}">부가 내용</label>
                                        <label class="d-block d-sm-none fw-bolder w-xxl-150px" for="additionalContent${addStatus.index}">부가 내용</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <input type="text" class="form-control" id="additionalContent${addStatus.index}" name="detailAdditionalContent" placeholder="부가 내용" maxlength="250" value="<c:out value="${add.detailAdditionalContent}" />">
                                        </div>
                                    </div>
                                    <div class="py-3 row">
                                        <label class="col-sm-3 col-md-2 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-150px" for="dz${addStatus.index}">이미지</label>
                                        <label class="d-block d-sm-none required fw-bolder w-xxl-150px" for="dz${addStatus.index}">이미지</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row" id="dz${addStatus.index}Container">
                                            <!-- 파일 업로드 섹션 -->
                                            <jsp:include page="/file-form-inc" flush="false">
                                                <jsp:param name="fileFormType" value="Y" />
                                                <jsp:param name="viewType" value="${viewType}" />
                                                <jsp:param name="isImage" value="true" />
                                                <jsp:param name="hasOrder" value="false" />
                                                <jsp:param name="parentTable" value="BUSINESS_CONTENTS_DETAIL"/>
                                                <jsp:param name="parentUid" value="${add.uid}"/>
                                                <jsp:param name="parentType" value="FILE"/>
                                                <jsp:param name="maxFileCount" value="2"/>
                                                <jsp:param name="isSecure" value="N"/>
                                                <jsp:param name="DZID" value="dz${addStatus.index}"/>
                                                <jsp:param name="dropzoneIndex" value="${addStatus.index}"/>
                                                <jsp:param name="description" value="파일 크기는 1mb 이하, jpg, jpeg, bmp, gif, png, webp 형식의 파일만 가능합니다."/>
                                            </jsp:include>
                                            <script type="text/javascript">

                                            // 첨부파일 드롭존 설정
                                            ntdrop[${addStatus.index}] = global.genDropzone('#dz${addStatus.index}', {
                                                params: {
                                                    'ParentTable': 'BUSINESS_CONTENTS_DETAIL',
                                                    'ParentType': 'FILE',
                                                    'ParentUid': '${add.uid}',
                                                },
                                                hasOrder: false,
                                                isSecure: 'N',
                                                maxFileCount: 2,
                                                acceptedExt: ['.jpg', '.jpeg', '.png', 'webp', 'gif', 'apng', 'bmp'],
                                                required: 'Y',
                                                errorMsg : '이미지를 등록해주세요.',
                                                containerId : 'dz${addStatus.index}Container',
                                                dropzoneIdx : 'dz${addStatus.index}',
                                                maxFileSize : 53,
                                                dropzoneIndex : ${addStatus.index},
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
                                </div>
                                    <!--end::Card body-->
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach begin="0" end="3" varStatus="addStatus">
                                <div class="card-body" id="add${addStatus.index}">
                                    <input type="hidden" name="uid" value="${addUidList[addStatus.index]}"/>
                                    <div class="card-header" style="min-height: 50px;">
                                        <h3 class="card-title fw-bolder"># ${addStatus.index+1}
                                            <c:if test="${not addStatus.first}">
                                            <div class="card-toolbar" style="margin-left: 20px;"><button type="button" class="btn btn-sm btn-danger" onclick="resetContents('${addStatus.index}');">콘텐츠 삭제</button></div>
                                            </c:if>
                                        </h3>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-150px" for="detailTitle${addStatus.index}">Title</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-150px" for="detailTitle${addStatus.index}">Title</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <textarea class="form-control" data-add-index="${addStatus.index}" id="detailTitle${addStatus.index}" name="detailTitle" placeholder="Title"<c:if test="${addStatus.first}"> required</c:if> maxlength="150"><c:out value="${detail.title}"/></textarea>
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-150px" for="detailContent${addStatus.index}">내용</label>
                                        <label class="d-block d-sm-none  required fw-bolder w-xxl-150px" for="detailContent${addStatus.index}">내용</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <textarea class="form-control" id="detailContent${addStatus.index}" name="detailContent" placeholder="내용" <c:if test="${addStatus.first}"> required</c:if> maxlength="500"><c:out value="${detail.content}" /></textarea>
                                        </div>
                                    </div>
                                    <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                        <label class="col-sm-3 col-md-2 col-xl-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-150px" for="additionalContent${addStatus.index}">부가 내용</label>
                                        <label class="d-block d-sm-none fw-bolder w-xxl-150px" for="additionalContent${addStatus.index}">부가 내용</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                            <input type="text" class="form-control" id="additionalContent${addStatus.index}" name="detailAdditionalContent" placeholder="부가 내용" maxlength="250" value="<c:out value="${detail.additionalContent}" />">
                                        </div>
                                    </div>
                                    <div class="py-3 row">
                                        <label class="col-sm-3 col-md-2 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-150px" for="dz${addStatus.index}">이미지</label>
                                        <label class="d-block d-sm-none required fw-bolder w-xxl-150px" for="dz${addStatus.index}">이미지</label>
                                        <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row" id="dz${addStatus.index}Container">
                                            <!-- 파일 업로드 섹션 -->
                                            <jsp:include page="/file-form-inc" flush="false">
                                                <jsp:param name="fileFormType" value="Y" />
                                                <jsp:param name="viewType" value="${viewType}" />
                                                <jsp:param name="isImage" value="true" />
                                                <jsp:param name="hasOrder" value="false" />
                                                <jsp:param name="parentTable" value="BUSINESS_CONTENTS_DETAIL"/>
                                                <jsp:param name="parentUid" value="${addUidList[addStatus.index]}"/>
                                                <jsp:param name="parentType" value="FILE"/>
                                                <jsp:param name="maxFileCount" value="2"/>
                                                <jsp:param name="isSecure" value="N"/>
                                                <jsp:param name="DZID" value="dz${addStatus.index}"/>
                                                <jsp:param name="dropzoneIndex" value="${addStatus.index}"/>
                                                <jsp:param name="description" value="파일 크기는 1mb 이하, jpg, jpeg, bmp, gif, png, webp 형식의 파일만 가능합니다."/>
                                            </jsp:include>
                                            <script type="text/javascript">

                                            // 첨부파일 드롭존 설정
                                            ntdrop[${addStatus.index}] = global.genDropzone('#dz${addStatus.index}', {
                                                params: {
                                                    'ParentTable': 'BUSINESS_CONTENTS_DETAIL',
                                                    'ParentType': 'FILE',
                                                    'ParentUid': '${addUidList[addStatus.index]}',
                                                },
                                                hasOrder: false,
                                                isSecure: 'N',
                                                maxFileCount: 2,
                                                acceptedExt: ['.jpg', '.jpeg', '.png', 'webp', 'gif', 'apng', 'bmp'],
                                                required: 'Y',
                                                errorMsg : '이미지를 등록해주세요.',
                                                containerId : 'dz${addStatus.index}Container',
                                                dropzoneIdx : 'dz${addStatus.index}',
                                                maxFileSize : 53,
                                                dropzoneIndex : ${addStatus.index},
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
                                    <!--end::Card body-->
                                        </c:forEach>
                                </div>
                                    </c:otherwise>
                                </c:choose>
                                <div class="card-footer d-flex justify-content-end">
                                    <div class="col d-flex justify-content-start">
                                        <a class="btn btn-dark" href="javascript: location.href='/business/business-contents-list'+location.search;">목록</a>
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
    function resetContents(idx) {
        const addEl = document.getElementById('add'+idx);
        addEl.querySelectorAll('input[type=text], textarea').forEach(function(obj) {
            // 필요시에만
            try {
                obj.value = '';
                validator.validateField(obj.id);
            } catch(e){}
        });

        // file
        // 필요시에만
        try {
            ntdrop[idx].$dropzoneContainer[0].querySelector('a').click();
        } catch(e) {
            console.log(e);
        }
    }

    const uid = document.querySelector('input[name=uid]').value;

    // validation
    // default required fields
    let fields = {};
    document.querySelectorAll('input:required,select:required,textarea:required').forEach(function(el) {
        let msg = el.placeholder;
        if(el.tagName == 'SELECT')
                msg = el.ariaLabel;
            msg += ' 은(는) 필수 입력 값입니다.';
        fields[el.name] = {
            selector: '[name='+el.name+']:required'
            , validators: {notEmpty: {message: msg}}
        };
    });

    // custom fields
    // 부가내용
    FormValidation.validators.checkLang = function() {
        return {
            validate: function(obj) {
                let thisEl = obj.element;
                let areaEl = thisEl.closest('.card-body');
                let titleEl = areaEl.querySelector('[name=detailTitle]');
                let contentEl = areaEl.querySelector('[name=detailContent]');
                let additionalContentEl = areaEl.querySelector('[name=detailAdditionalContent]');

                let t = titleEl.value;
                let c = contentEl.value;
                let ac = additionalContentEl.value;

                // 비필수 부가내용들 중, 필드 내 하나라도 채워져있으면 유효성 체크 진행
                if(thisEl.name == 'detailTitle') {
                    if(t == '') {
                        if(c != '' || ac != '') {

                            return { valid: false };
                        }
                    }
                } else if(thisEl.name == 'detailContent') {
                    if(c == '') {
                        if(t != '' || ac != '') {
                            return { valid: false };
                        }
                    }
                } else {
                    validator.validateField(titleEl.id);
                    validator.validateField(contentEl.id);
                }

                return {valid: true};

            }
        }
    };
    document.querySelectorAll('[name=detailTitle]:not(:required), [name=detailContent]:not(:required), [name=detailAdditionalContent]').forEach(function(el) {
        let msg = el.placeholder;
        msg += ' 은(는) 필수 입력 값입니다.';
        let id=el.id;
        fields[el.id] = {
            selector: '#'+id,
            validators: {checkLang: {message: msg}}
        };

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
                    document.querySelectorAll('[name=detailTitle]:not(:required)').forEach(function(el) {
                        if(el.value == '')
                            ntdrop[el.dataset['addIndex']].options.required='N'
                        else
                            ntdrop[el.dataset['addIndex']].options.required='Y'
                    });

                    if(dropzoneCheck() === true) {
                        uploadByDropzone(0);
                    }

                }
            });
    }

    // submit form
    async function saveFormData() {
        let detailUrl = '/business/business-contents';
        let url = '/api/v1'+detailUrl;
        let method = 'POST';
        let message = '등록되었습니다.';
        let isModForm = false;

        // 수정 모드일 때 변수 조정
        if (document.querySelector('input[name=businessContentsIndex]').value !== '') {
            isModForm = true;
            url += '/update/' + uid;
            message = '수정되었습니다.'
        }
        // formdata to json type
        let formDataObj = {};
        formDataObj.businessContentsDetailList = [];
        // 기본정보
        const baseFormAreaEl =  document.getElementById('base');
        baseFormAreaEl.querySelectorAll('input[type=hidden], input[type=text], input[type=radio]:checked, input[type=checkbox]:checked, textarea, select').forEach(function(el) {
            formDataObj[el.name] = el.value;
        });
        // 부가정보
        document.detailForm.querySelectorAll('textarea[name=detailTitle]').forEach(function(titleEl) {
            let title = titleEl.value.trim();
            let areaEl = titleEl.closest('.card-body');
            let content = areaEl.querySelector('[name=detailContent]').value.trim();
            if(title == '' || content == '')
                return;

            let businessContentsDetail = {
                uid: areaEl.querySelector('[name=detailUid]').value
                , detailTitle: title
                , detailContent: content
                , detailAdditionalContent: areaEl.querySelector('[name=detailAdditionalContent]').value
            };
            if(isModForm)
                businessContentsDetail['businessContentsDetailIndex'] = areaEl.querySelector('[name=businessContentsDetailIndex]').value;

            formDataObj.businessContentsDetailList.push(businessContentsDetail);
        });

        if (document.querySelector('input[name=businessContentsIndex]').value !== '') {
            let descriptionObj = {};
            if (document.querySelectorAll('[id^="dropzoneImagedz"] [name="description"]').length > 0) {
                let uids = document.querySelectorAll('[name="detailUid"]');
                let fileUids = document.querySelectorAll('[id^="dropzoneImagedz"] [name="fileUid"]');
                let descriptions = document.querySelectorAll('[id^="dropzoneImagedz"] [name="description"]');
                descriptionObj['uid'] = [];
                descriptionObj['fileUid'] = [];
                descriptionObj['description'] = [];
                descriptionObj['type'] = 'business';

                descriptions.forEach(description => {
                    descriptionObj['description'].push(description.value);
                });

                uids.forEach(uid => {
                    descriptionObj['uid'].push(uid.value);
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
