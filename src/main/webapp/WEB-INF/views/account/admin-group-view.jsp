<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/common/inc/page-header-inc.jsp" %>
</head>
<body id="kt_body" class="header-fixed header-tablet-and-mobile-fixed toolbar-enabled toolbar-fixed toolbar-tablet-and-mobile-fixed aside-enabled aside-fixed" style="--kt-toolbar-height:55px;--kt-toolbar-height-tablet-and-mobile:55px">
<div class="d-flex flex-column flex-root">
    <div class="page d-flex flex-row flex-column-fluid">
        <%@ include file="/WEB-INF/views/common/inc/body-aside-inc.jsp" %>
        <div class="wrapper d-flex flex-column flex-row-fluid">
            <%@ include file="/WEB-INF/views/common/inc/body-header-inc.jsp" %>
            <div class="content d-flex flex-column flex-column-fluid">
                <c:set value="샘플 등록/수정" var="menuTitle"/>
                <%@ include file="/WEB-INF/views/common/inc/toolbar-inc.jsp" %>
                <div id="kt_content_container" class="container-fluid">
                    <form name="adminGroupForm">
                    <div class="container-fluid">
                        <!-- 본문 내용 START -->
                            <div class="card mb-7">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                            <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">작성자</label>
                                            <label class="d-block d-sm-none  required fw-bolder w-xxl-200px" for="userType">작성자</label>
                                            <div id="userType" class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row align-content-center">
                                                <c:choose>
                                                    <c:when test="${adminGroup ne null}">
                                                        <c:out value="${adminGroup.adminId}"/>(<c:out value="${adminGroup.department}"/>)
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${sessionScope.ADMIN.adminId}"/>(<c:out value="${sessionScope.ADMIN.department}"/>)
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                            <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">등록일시</label>
                                            <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">등록일시</label>
                                            <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row align-content-center">
                                                <c:out value="${adminGroup.regDate}"/>
                                            </div>
                                        </div>
                                        <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                            <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end fw-bolder w-xxl-200px">수정일시</label>
                                            <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">수정일시</label>
                                            <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row align-content-center">
                                                <c:out value="${adminGroup.modDate}"/>
                                            </div>
                                        </div>
                                        <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                            <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">관리자 그룹명</label>
                                            <label class="d-block d-sm-none fw-bolder w-xxl-200px">관리자 그룹명</label>
                                            <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row align-content-center">
                                                <c:out value="${adminGroup.groupName}"/>
                                            </div>
                                        </div>
                                        <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                            <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">권한 설명</label>
                                            <label class="d-block d-sm-none fw-bolder w-xxl-200px">권한 설명</label>
                                            <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row align-content-center">
                                                <c:out value="${adminGroup.description}"/>
                                            </div>
                                        </div>
                                        <div class="py-3 row border-bottom border-bottom-dashed border-gray-200">
                                            <label class="col-sm-3 col-md-3 col-xl-2 d-none d-sm-block col-form-label text-end required fw-bolder w-xxl-200px">사용 여부</label>
                                            <label class="d-block d-sm-none  required fw-bolder w-xxl-200px">사용 여부</label>
                                            <div class="col-sm-9 col-md-9 col-xl-10 col-xxl-9 fv-row align-content-center">
                                                <c:out value="${adminGroup.isUse}"/>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>


                        <div class="card mb-5 mb-xl-10">
                            <div class="card-header border-0 pt-5">
                                <div class="text-gray-900 fs-2 me-1 form-label fw-bolder text-dark">
                                    권한 설정
                                </div>
                            </div>
                            <div class="card-body">
                                <c:set var="prevDepth" value=""/>

                                <c:forEach items="${adminGroupPermissionList}" var="menu">
                                    <c:set var="marginLeft" value="${(menu.depth - 1) * 25}"/>
                                    <c:if test="${prevDepth ne '' && prevDepth ne '1' && menu.depth eq '1'}">
                                        <hr>
                                    </c:if>
                                    <div class="row mb-8">
                                        <div class="col-lg-12">
                                            <label class="form-check form-check-custom form-check-solid me-10" style="margin-left: ${marginLeft}px;" >
                                                <input class="form-check-input h-20px w-20px" type="checkbox" name="menuIndex" value="${menu.menuIndex}" data-depth="${menu.depth}" data-parent-index="${menu.parentIndex}"
                                                       onclick="selectMenu(this)" disabled
                                                        <c:if test="${menu.adminGroupIndex ne null}">checked</c:if>
                                                />
                                                <span class="form-check-label fw-bolder">
                                                    ${menu.location2}
                                                </span>
                                            </label>
                                        </div>
                                    </div>
                                    <c:set var="prevDepth" value="${menu.depth}"/>
                                </c:forEach>

                            </div>
                            <div class="card-footer d-flex justify-content-end">
                                <div class="col d-flex justify-content-start">
                                    <button type="button" class="btn btn-dark" onclick="location.href='/site/admin-group' + location.search">목록</button>
                                </div>
                                <div class="col d-flex justify-content-end">
                                    <button type="button" class="btn btn-warning" onclick="location.href='/site/admin-group-mod-form/${adminGroup.uid}'">수정</button>
                                </div>
                            </div>
                        </div>
                        <!-- 본문 내용 END -->
                    </div>
                    </form>
                </div>
            </div>
            <%@ include file="/WEB-INF/views/common/inc/footer-inc.jsp" %>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">

</script>
