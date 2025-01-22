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
                            <div class="card-body pb-3">
                                <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                    <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">성명</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row col-form-label">
                                        <c:out value="${detail.name}"/>
                                    </div>
                                </div>
                                <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                    <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">이메일</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row col-form-label">
                                        <c:out value="${detail.getEmailDecrypted()}"/>
                                    </div>
                                </div>
                                <div class="py-3 row border-bottom border-gray-200">
                                    <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">문의종류</label>
                                    <div class="col-sm-9 col-md-10 col-xl-10 col-xxl-9 fv-row col-form-label">
                                        <c:choose>
                                            <c:when test="${detail.classification eq 'proposal'}">제안</c:when>
                                            <c:otherwise>문의</c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="py-3 row border-bottom border-gray-200">
                                    <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">사업장명</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 col-form-label">
                                        <c:out value="${detail.etcInfo}"/>
                                    </div>
                                </div>
                                <div class="py-3 row border-bottom border-gray-200">
                                    <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">제목</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 col-form-label">
                                        <c:out value="${detail.title}"/>
                                    </div>
                                </div>
                                <div class="py-3 row border-bottom border-bottom-dashed border-white">
                                    <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">문의내용</label>
                                    <div class="col-sm-9 col-md-10 col-xl-10 col-xxl-9 fv-row fr-view col-form-label">
                                        <% pageContext.setAttribute("CRLF", "\r\n"); %>
                                        <% pageContext.setAttribute("LF", "\n"); %>
                                        ${fn:replace(fn:replace(fn:escapeXml(detail.content), CRLF, '<br/>'), LF, '<br/>')}
                                    </div>
                                </div>
                            <!--end::Card body-->
                            <div class="card-footer d-flex justify-content-end">
                                <div class="col d-flex justify-content-start">
                                    <a class="btn btn-dark" href="javascript: location.href='/customer/inquiry-list'+location.search;">목록</a>
                                </div>
                                <div class="col d-flex justify-content-end">
                                    <a class="btn btn-info me-2" href="javascript: location.href='/customer/answer-reg-form/${uid}'">답변하기</a>
                                        <%--<a class="btn btn-danger me-2" href="javascript: deleteData('${uid}');">삭제</a>--%>
                                        <%--<a class="btn btn-warning" href="javascript: location.href='/customer/inquiry-mod-form/${uid}'+location.search;">수정</a>--%>
                                </div>
                            </div>
                        </div>

                        <c:forEach items="${answerList}" var="answer" varStatus="status">
                            <div class="card mb-7">
                            <!--begin::Card body-->
                            <div class="card-body pb-3">
                                <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                    <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">답변자</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row col-form-label">
                                        <c:out value="${answer.adminName}"/>
                                    </div>
                                </div>
                                <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                    <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">답변일</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row col-form-label">
                                        <c:out value="${answer.regDate}"/>
                                    </div>
                                </div>
                                <div class="py-3 row border-bottom border-gray-200">
                                    <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">답변 제목</label>
                                    <div class="col-sm-9 col-md-10 col-xl-10 col-xxl-9 fv-row col-form-label">
                                        <c:out value="${answer.answerTitle}"/>
                                    </div>
                                </div>
                                <div class="py-3 row">
                                    <label class="col-sm-3 col-md-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">답변 내용</label>
                                    <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 col-form-label">
                                        ${fn:replace(fn:replace(fn:escapeXml(answer.answerContent), CRLF, '<br/>'), LF, '<br/>')}
                                    </div>
                                </div>
                            </div>
                            <!--end::Card body-->
                        </div>
                        </c:forEach>
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
                const response = await fetch('/api/v1/news-room/press/delete/' + uid, {
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
                        window.location.href = '/news-room/press-list' + location.search;
                    });
                }
            }
        });
    }
</script>
</html>
