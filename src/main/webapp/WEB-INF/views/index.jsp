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
<%--                            <div class="card mb-8">--%>
<%--                                <div class="card-body">--%>
<%--                                    <a href="list.jsp" class="btn btn-dark">SAMPLE LIST</a>--%>
<%--                                    <a href="form.jsp" class="btn btn-info">SAMPLE FORM</a>--%>
<%--                                    <a href="view.jsp" class="btn btn-dark">SAMPLE VIEW</a>--%>
<%--                                </div>--%>
<%--                                <div class="card-body">--%>
<%--                                    <button type="button" class="btn btn-primary">포인트</button>--%>
<%--                                    <button type="button" class="btn btn-info">등록</button>--%>
<%--                                    <button type="button" class="btn btn-dark">목록/조회/검색</button>--%>
<%--                                    <button type="button" class="btn btn-warning">수정</button>--%>
<%--                                    <button type="button" class="btn btn-danger" onclick="swal2()">삭제</button>--%>
<%--                                    <button type="button" class="btn btn-light">초기화/취소</button>--%>
<%--                                    <button type="button" class="btn btn-success">저장</button>--%>
<%--                                    <button type="button" class="btn btn-secondary">기타동작</button>--%>
<%--                                </div>--%>
<%--                                <div class="card-footer d-flex justify-content-end">--%>
<%--                                    <div class="col d-flex justify-content-start">--%>
<%--                                        <button type="button" class="btn btn-dark">목록</button>--%>
<%--                                    </div>--%>
<%--                                    <div class="col d-flex justify-content-end">--%>
<%--                                        <button type="button" class="btn btn-light me-2">취소</button>--%>
<%--                                        <button type="button" class="btn btn-success" onclick="swal1()">저장</button>--%>
<%--                                        <script type="text/javascript">--%>
<%--                                            function swal1() {--%>
<%--                                                Swal.fire({--%>
<%--                                                    title: '등록 완료',--%>
<%--                                                    text: '등록되었습니다',--%>
<%--                                                    icon: 'success',--%>
<%--                                                    buttonsStyling: !1,--%>
<%--                                                    confirmButtonText: '확인',--%>
<%--                                                    customClass: {confirmButton: 'btn btn-primary'}--%>
<%--                                                });--%>
<%--                                            }--%>
<%--                                            function swal2() {--%>
<%--                                                Swal.fire({--%>
<%--                                                    title: '게시물 삭제',--%>
<%--                                                    text: '등록하신 게시물을 삭제 하시겠습니까?',--%>
<%--                                                    icon: 'warning',--%>
<%--                                                    showCancelButton: true,--%>
<%--                                                    confirmButtonColor: '#3085d6',--%>
<%--                                                    cancelButtonColor: '#d33',--%>
<%--                                                    confirmButtonText: '확인',--%>
<%--                                                    cancelButtonText: '취소'--%>
<%--                                                });--%>
<%--                                            }--%>
<%--                                        </script>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>
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
</html>
