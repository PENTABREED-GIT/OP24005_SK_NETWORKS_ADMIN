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
                        <!--begin::Card-->
                        <div class="card mb-7">
                            <!--begin::Card body-->
                            <div class="card-body">
                                <div class="py-3 row border-bottom border-gray-200 border-bottom border-gray-200">
                                    <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">게시글 제목</label>
                                    <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">게시글 제목</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 col-form-label">
                                        ${detail.reportsAndPoliciesTitle}
                                    </div>
                                </div>
                                <div class="py-3 row border-bottom border-gray-200">
                                    <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder fw-bolder w-xxl-200px">게시언어</label>
                                    <label class="d-block d-sm-none  required fw-bolder fw-bolder w-xxl-200px">게시언어</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 col-form-label">
                                        <c:out value="${detail.langName}"/>
                                    </div>
                                </div>
                                <div class="py-3 row border-bottom border-gray-200">
                                    <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">노출여부</label>
                                    <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">노출여부</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 col-form-label">
                                        <c:out value="${detail.isOpenName}"/>
                                    </div>
                                </div>
                                <div class="py-3 row border-bottom border-gray-200">
                                    <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">일자</label>
                                    <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">일자</label>
                                      <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 col-form-label">
                                        <c:out value="${detail.openDate}"/>
                                    </div>
                                </div>
                                <div class="py-3 row border-bottom border-gray-200">
                                    <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">Title</label>
                                    <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">Title</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 col-form-label">
                                        <c:out value="${detail.title}"/>
                                    </div>
                                </div>
                                <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                    <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end  fw-bolder w-xxl-200px">국문 PDF</label>
                                    <label class="d-block d-sm-none   fw-bolder w-xxl-200px">국문 PDF</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                        <div class="row form-check col-form-label form-check-custom mw-1000px" id="dz0Container">
                                            <!-- 파일 업로드 섹션 -->
                                            <jsp:include page="/file-form-inc" flush="false">
                                                <jsp:param name="parentTable" value="REPORTS_AND_POLICIES" />
                                                <jsp:param name="parentUid" value="${detail.uid}" />
                                                <jsp:param name="parentType" value="PDF_KO" />
                                                <jsp:param name="viewType" value="view" />
                                                <jsp:param name="hasOrder" value="false" />
                                                <jsp:param name="maxFileCount" value="2"/>
                                                <jsp:param name="isSecure" value="N"/>
                                                <jsp:param name="DZID" value="dz0"/>
                                                <jsp:param name="description" value="PDF 형식의 파일만 가능합니다."/>
                                            </jsp:include>
                                        </div>

                                    </div>
                                </div>
                                <div class="py-3 row">
                                    <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end  fw-bolder w-xxl-200px">영문 PDF</label>
                                    <label class="d-block d-sm-none   fw-bolder w-xxl-200px">영문 PDF</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row">
                                        <div class="row form-check col-form-label form-check-custom mw-1000px" id="dz1Container">
                                            <!-- 파일 업로드 섹션 -->
                                            <jsp:include page="/file-form-inc" flush="false">
                                                <jsp:param name="parentTable" value="REPORTS_AND_POLICIES" />
                                                <jsp:param name="parentUid" value="${detail.uid}" />
                                                <jsp:param name="parentType" value="PDF_EN" />
                                                <jsp:param name="viewType" value="view" />
                                                <jsp:param name="hasOrder" value="false" />
                                                <jsp:param name="maxFileCount" value="2"/>
                                                <jsp:param name="isSecure" value="N"/>
                                                <jsp:param name="DZID" value="dz1"/>
                                                <jsp:param name="description" value="PDF 형식의 파일만 가능합니다."/>
                                            </jsp:include>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <!--end::Card body-->
                            <div class="card-footer d-flex justify-content-end">
                                <div class="col d-flex justify-content-start">
                                    <a class="btn btn-dark" href="javascript: location.href='/esg/reports-and-policies-list'+location.search;">목록</a>
                                </div>
                                <div class="col d-flex justify-content-end">
                                    <a class="btn btn-danger me-2" href="javascript: deleteData('${uid}');">삭제</a>
                                    <a class="btn btn-warning" href="javascript: location.href='/esg/reports-and-policies-mod-form/${uid}'+location.search;">수정</a>
                                </div>
                            </div>
                        </div>
                        <!--end::Card-->
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
    function deleteData(uid) {
        Swal.fire({
            title: '게시물 삭제',
            text: '게시물을 삭제 하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: '확인',
            cancelButtonText: '취소',
            customClass: {confirmButton: 'btn btn-primary', cancelButton: 'btn btn-light'}
        }).then(async (result) => {
            if (result.isConfirmed) {
                const response = await fetch('/api/v1/esg/reports-and-policies/delete/' + uid, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
                const res = await response.json();
                if(res.result) {
                    Swal.fire({
                        text: '삭제되었습니다.',
                        icon: 'success',
                        buttonsStyling: !1,
                        confirmButtonText: '확인',
                        customClass: {confirmButton: 'btn btn-primary'}
                    }).then(() => {
                        window.location.href = '/esg/reports-and-policies-list' + location.search;
                    });
                }
            }
        });
    }
</script>
</html>
