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
                        <form name="searchForm" action="" method="get">
                            <!--begin::Card-->
                            <div class="card mb-7">
                                <!--begin::Card body-->
                                <div class="card-body">
                                    <!--begin::Compact form-->
                                    <!--begin::Advance form-->
                                    <div id="kt_advanced_search_form" >
                                        <!--begin::Row-->
                                        <div class="row g-8">
                                            <!--begin::Col-->
                                            <div class="col-xl-4">
                                                <label class="fs-6 form-label fw-bolder">회사명</label>
                                                <div class="input-group input-group-fluid">
                                                    <input type="text" class="form-control form-control-solid" name="companyName" placeholder="검색어 입력" value="${searchParam.companyName}"/>
                                                </div>
                                            </div>
                                            <!--end::Col-->
                                            <!--begin::Col-->
                                            <div class="col-xl-3">
                                                <label class="fs-6 form-label fw-bolder">구분</label>
                                                <!--begin::Radio group-->
                                                <div class="nav-group nav-group-fluid">
                                                    <!--begin::Option-->
                                                    <label>
                                                        <input type="radio" class="btn-check" name="classification" value=""<c:if test="${searchParam.classification != 'DOMESTIC' and searchParam.classification != 'OVERSEAS'}"> checked</c:if>/>
                                                        <span class="btn btn-sm btn-color-muted btn-active btn-active-primary fw-bold px-4">전체</span>
                                                    </label>
                                                    <!--end::Option-->
                                                    <!--begin::Option-->
                                                    <label>
                                                        <input type="radio" class="btn-check" name="classification" value="DOMESTIC"<c:if test="${searchParam.classification == 'DOMESTIC'}"> checked</c:if>/>
                                                        <span class="btn btn-sm btn-color-muted btn-active btn-active-primary fw-bold px-4">국내</span>
                                                    </label>
                                                    <!--end::Option-->
                                                    <!--begin::Option-->
                                                    <label>
                                                        <input type="radio" class="btn-check" name="classification" value="OVERSEAS"<c:if test="${searchParam.classification == 'OVERSEAS'}"> checked</c:if>/>
                                                        <span class="btn btn-sm btn-color-muted btn-active btn-active-primary fw-bold px-4">해외</span>
                                                    </label>
                                                    <!--end::Option-->
                                                </div>
                                                <!--end::Radio group-->
                                            </div>
                                            <!--end::Col-->
                                            <!--begin::Col-->
                                            <div class="col-xl-2">
                                                <label class="fs-6 form-label fw-bolder">지역</label>
                                                <!--begin::Select-->
                                                <select class="form-select form-select-solid" id="regionNameKo" name="regionNameKo">
                                                    <option value="">전체</option>
                                                <c:forEach var="region" items="${regionList}">
                                                    <option value="${region.ko}" <c:if test="${searchParam.regionNameKo eq region.ko}">selected</c:if>>${region.ko}</option>
                                                </c:forEach>
                                                </select>
                                                <!--end::Select-->
                                            </div>
                                            <!--end::Col-->
                                            <!--begin::Col-->
                                            <div class="col-xl-3">
                                                <label class="fs-6 form-label fw-bolder">노출여부</label>
                                                <!--begin::Radio group-->
                                                <div class="nav-group nav-group-fluid">
                                                    <!--begin::Option-->
                                                    <label>
                                                        <input type="radio" class="btn-check" name="isOpen" value=""<c:if test="${searchParam.isOpen != 'Y' and searchParam.isOpen != 'Y'}"> checked</c:if>/>
                                                        <span class="btn btn-sm btn-color-muted btn-active btn-active-primary fw-bold px-4">전체</span>
                                                    </label>
                                                    <!--end::Option-->
                                                    <!--begin::Option-->
                                                    <label>
                                                        <input type="radio" class="btn-check" name="isOpen" value="Y"<c:if test="${searchParam.isOpen == 'Y'}"> checked</c:if>/>
                                                        <span class="btn btn-sm btn-color-muted btn-active btn-active-primary fw-bold px-4">노출</span>
                                                    </label>
                                                    <!--end::Option-->

                                                    <!--begin::Option-->
                                                    <label>
                                                        <input type="radio" class="btn-check" name="isOpen" value="N"<c:if test="${searchParam.isOpen == 'N'}"> checked</c:if>/>
                                                        <span class="btn btn-sm btn-color-muted btn-active btn-active-primary fw-bold px-4">비노출</span>
                                                    </label>
                                                    <!--end::Option-->
                                                </div>
                                                <!--end::Radio group-->
                                            </div>
                                            <!--end::Col-->
                                        </div>
                                        <!--end::Row-->
                                    </div>
                                    <!--end::Advance form-->
                                </div>
                                <!--end::Card body-->
                                <div class="card-footer d-flex justify-content-end">
                                    <a class="btn btn-light me-3" href="/company/global-network-list"><i class="bi bi-arrow-clockwise"></i>초기화</a>
                                    <button type="submit" class="btn btn-dark"><i class="bi bi-search"></i>검색</button>
                                </div>
                            </div>
                            <!--end::Card-->
                            <input type="hidden" name="isKo" value="<c:if test="${searchParam.isEn ne 'Y'}">Y</c:if>" />
                            <input type="hidden" name="isEn" value="<c:out value="${searchParam.isEn}"/>" />
                        </form>
                        <!--end::Form-->
                        <div class="card">
                            <div class="card-body">
                                <ul class="nav nav-tabs border-bottom border-primary w-100" role="tablist">
                                    <li class="nav-item" role="presentation">
                                        <a class="fw-bolder border border-primary border-bottom-0 btn btn-lg btn-active-primary rounded-bottom-0<c:if test="${searchParam.isEn ne 'Y'}"> active</c:if>" href="javascript:tab('ko');" aria-selected="true" role="tab">국문</a>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <a class="fw-bolder border border-primary border-bottom-0 btn btn-lg btn-active-primary rounded-bottom-0<c:if test="${searchParam.isEn eq 'Y'}"> active</c:if> " href="javascript:tab('en');" aria-selected="false" role="tab" tabindex="-1">영문</a>
                                    </li>
                                </ul>
                                <c:choose>
                                <c:when test="${not empty list}">
                                <div class="table-responsive">

                                    <!--begin::Table-->
                                    <table class="table table-row-bordered gy-4 table-hover">
                                        <!--begin::Table head-->
                                        <thead>
                                            <!--begin::Table row-->
                                            <tr class="fw-bold fs-6 text-bolder text-gray-800 border-bottom border-gray-400 text-muted text-md-center">
                                                <th class="min-w-70px mw-sm-10px w-sm-70px">번호</th>
                                                <th class="min-w-100px">구분</th>
                                                <th class="min-w-200px">지역</th>
                                                <th class="min-w-300px">회사명</th>
                                                <th class="min-w-100px">노출여부</th>
                                            </tr>
                                            <!--end::Table row-->
                                        </thead>
                                        <!--end::Table head-->
                                        <!--begin::Table body-->
                                        <tbody>
                                    <c:forEach items="${list}" var="item" varStatus="status">
                                            <tr data-nt-action="goView"  data-uid="${item.uid}" class="text-md-center hoverable" onclick="view('${item.uid}');">
                                                <td class="text-dark fw-bold text-md-center">
                                                    ${((totalCount - (page.pageListSize * (page.currentPage - 1))) - status.index)}
                                                </td>
                                                <td class="text-dark fw-bold">
                                                    <c:out value="${item.classificationName}"/>
                                                </td>
                                                <td class="text-dark fw-bold">
                                                    <c:out value="${item.regionNameKo}"/>
                                                </td>
                                                <c:choose>
                                                    <c:when test="${(empty param.isEn and param.isKo eq 'Y') or (empty param.isEn and empty param.isKo)}">
                                                        <td class="text-dark fw-bold text-md-center">
                                                            <c:out value="${item.companyNameKo}"/>
                                                        </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="text-dark fw-bold text-md-center">
                                                            <c:out value="${item.companyNameEn}"/>
                                                        </td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td class="text-dark fw-bold text-md-center">
                                                    ${item.isOpenBadge}
                                                </td>
                                            </tr>
                                    </c:forEach>
                                        </tbody>
                                        <!--end::Table body-->
                                    </table>
                                    <!--end::Table-->
                                    <div class="pt-3 d-flex justify-content-end">
                                    <jsp:include page="/WEB-INF/views/common/inc/paging-inc.jsp" flush="true" >
                                        <jsp:param name="currentPage" value="${ page.currentPage}"/>
                                        <jsp:param name="startOfPageBlock" value="${ page.startOfPageBlock}"/>
                                        <jsp:param name="endOfPageBlock" value="${ page.endOfPageBlock}"/>
                                        <jsp:param name="totalPage" value="${ page.totalPage}"/>
                                        <jsp:param name="totalCount" value="${ page.totalCount}"/>
                                        <jsp:param name="listFunction" value="global.changePage"/>
                                    </jsp:include>
                                    </div>
                                </div>

                                </c:when>
                                <c:otherwise>
                                <div class="min-h-md-100px d-flex justify-content-center" style="line-height:10;">
                                    데이터가 없습니다.
                                </div>
                                </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="card-footer d-flex justify-content-end">
                                <a class="btn btn-info" href="javascript: location.href='global-network-reg-form'+location.search;">등록</a>
                            </div>
                        </div>
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
    function tab(lang) {
        let f = document.searchForm;
        if(lang == 'en') {
            f.isKo.value = '';
            f.isEn.value = 'Y';
        } else {
            f.isKo.value = 'Y';
            f.isEn.value = '';
        }
        f.submit();
    }

    function view(uid) {
        location.href = '/company/global-network/'+uid+location.search;
    }
</script>
</html>
