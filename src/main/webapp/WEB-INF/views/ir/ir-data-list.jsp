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
                                            <div class="col-xxl-4 col-xl-6">
                                                <label class="fs-6 form-label fw-bolder">키워드</label>
                                                <div class="input-group input-group-fluid">
                                                    <!--begin::Select-->
                                                    <span class="input-group-select">
                                                        <select class="form-select form-select-solid" name="searchType">
                                                            <option value="TITLE"<c:if test="${searchParam.searchType ne 'ADMINNAME'}"> selected</c:if>>제목</option>
                                                            <option value="IRSCHEDULETYPE"<c:if test="${searchParam.searchType eq 'IRSCHEDULETYPE'}"> selected</c:if>>구분</option>
                                                            <option value="ADMINNAME"<c:if test="${searchParam.searchType eq 'ADMINNAME'}"> selected</c:if>>최종작업자</option>
                                                        </select>
                                                    </span>
                                                    <!--end::Select-->
                                                    <input type="text" class="form-control form-control-solid" name="searchWord" placeholder="검색어 입력" value="<c:out value="${searchParam.searchWord}" />"/>
                                                </div>
                                            </div>
                                            <!--end::Col-->
                                            <!--begin::Col-->
                                            <div class="col-xxl-4 col-xl-6">
                                                <label class="fs-6 form-label fw-bolder text-dark" for="startDate">등록일</label>
                                                <div class="input-group input-group-fluid">
                                                    <!--begin::Select-->
                                                    <span class="input-group-text border-0"><i class="bi bi-calendar-check text-primary"></i></span>
                                                    <input type="text" id="startDate" name="startDate" class="form-control form-control-solid flatpickr-input" placeholder="시작일" autocomplete="off" value="<c:out value="${searchParam.startDate}"/>"/>
                                                    <span class="input-group-text border-0"><button type="button" class="border-0 bg-light" onclick="global.skn.list.clearStartDate();"><i class="bi bi-x-lg text-primary"></i></button></span>
                                                    <span class="input-group-text border-0" id="basic-addon3"><i class="bi bi-calendar-check text-primary"></i></span>
                                                    <input id="endDate" name="endDate" type="text" class="form-control flatpickr-input form-control-solid" placeholder="종료일" autocomplete="off" value="<c:out value="${searchParam.endDate}"/>" />
                                                    <span class="input-group-text border-0"><button type="button" class="border-0 bg-light" onclick="global.skn.list.clearEndDate();"><i class="bi bi-x-lg text-primary"></i></button></span>
                                                    <!--end::Select-->
                                                </div>
                                            </div>
                                            <!--end::Col-->
                                            <!--begin::Col-->
                                            <div class="col-xxl-2 col-xl-6 col-md-6">
                                                <label class="fs-6 form-label fw-bolder">자료 언어</label>
                                                <!--begin::Radio group-->
                                                <div class="nav-group nav-group-fluid">
                                                    <!--begin::Option-->
                                                    <label>
                                                        <input type="radio" class="btn-check" name="dataLang" value=""<c:if test="${searchParam.dataLang != 'KO' and searchParam.dataLang != 'EN'}"> checked</c:if>/>
                                                        <span class="btn btn-sm btn-color-muted btn-active btn-active-primary fw-bold px-4">전체</span>
                                                    </label>
                                                    <!--end::Option-->
                                                    <!--begin::Option-->
                                                    <label>
                                                        <input type="radio" class="btn-check" name="dataLang" value="KO"<c:if test="${searchParam.dataLang == 'KO'}"> checked</c:if>/>
                                                        <span class="btn btn-sm btn-color-muted btn-active btn-active-primary fw-bold px-4">국문</span>
                                                    </label>
                                                    <!--end::Option-->
                                                    <!--begin::Option-->
                                                    <label>
                                                        <input type="radio" class="btn-check" name="dataLang" value="EN"<c:if test="${searchParam.dataLang == 'EN'}"> checked</c:if>/>
                                                        <span class="btn btn-sm btn-color-muted btn-active btn-active-primary fw-bold px-4">영문</span>
                                                    </label>
                                                    <!--end::Option-->
                                                </div>
                                                <!--end::Radio group-->
                                            </div>
                                            <!--end::Col-->
                                            <!--begin::Col-->
                                            <div class="col-xxl-2 col-xl-6 col-md-6">
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
                                    <c:choose>
                                    <c:when test="${searchParam.classification eq 'RESULT'}">
                                    <a class="btn btn-light me-3" href="/ir/management-report-list"><i class="bi bi-arrow-clockwise"></i>초기화</a>
                                    </c:when>
                                    <c:otherwise>
                                    <a class="btn btn-light me-3" href="/ir/ir-data-list"><i class="bi bi-arrow-clockwise"></i>초기화</a>
                                    </c:otherwise>
                                    </c:choose>
                                    <button type="submit" class="btn btn-dark"><i class="bi bi-search"></i>검색</button>
                                </div>
                            </div>
                            <!--end::Card-->
                            <input type="hidden" name="lang" value="<c:choose><c:when test="${searchParam.lang ne 'EN'}">KO</c:when><c:otherwise>EN</c:otherwise></c:choose>" />
                        </form>
                        <!--end::Form-->
                        <div class="card">
                            <div class="card-body">
                                <ul class="nav nav-tabs border-bottom border-primary w-100" role="tablist">
                                    <li class="nav-item" role="presentation">
                                        <a class="fw-bolder border border-primary border-bottom-0 btn btn-lg btn-active-primary rounded-bottom-0<c:if test="${searchParam.lang ne 'EN'}"> active</c:if>" href="javascript:global.skn.list.langTab('KO');" aria-selected="true" role="tab">국문</a>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <a class="fw-bolder border border-primary border-bottom-0 btn btn-lg btn-active-primary rounded-bottom-0<c:if test="${searchParam.lang eq 'EN'}"> active</c:if> " href="javascript:global.skn.list.langTab('EN');" aria-selected="false" role="tab" tabindex="-1">영문</a>
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
                                                <th class="min-w-100px">자료 언어</th>
                                                <th class="min-w-300px">제목</th>
                                                <th class="min-w-100px">노출여부</th>
                                                <th class="min-w-150px">등록일</th>
                                                <th class="min-w-100px">최종작업자</th>
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
                                                <td class="text-dark fw-bold"><c:out value="${item.dataLangName}"/></td>
                                                <td class="text-dark fw-bold text-md-start"><c:out value="${item.title}"/></td>
                                                <td class="text-dark fw-bold text-md-center">${item.isOpenBadge}</td>
                                                <td class="text-dark fw-bold text-md-center"><c:out value="${item.regDate}"/></td>
                                                <td class="text-dark fw-bold text-md-center"><c:out value="${item.adminName}"/></td>
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
                                <c:choose>
                                <c:when test="${searchParam.classification eq 'RESULT'}">
                                <a class="btn btn-info" href="javascript: location.href='management-report-reg-form'+location.search;">등록</a>
                                </c:when>
                                <c:otherwise>
                                <a class="btn btn-info" href="javascript: location.href='ir-data-reg-form'+location.search;">등록</a>
                                </c:otherwise>
                                </c:choose>

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
<link rel="stylesheet" href="/assets/css/flatpickr/flatpickr.min.css">
<script src="/assets/js/flatpickr/flatpickr.js"></script>
<script src="/assets/js/flatpickr/ko.js"></script>
<script type="text/javascript">
    function view(uid) {
        <c:choose>
        <c:when test="${searchParam.classification eq 'RESULT'}">
        location.href = '/ir/management-report/'+uid+location.search;
        </c:when>
        <c:otherwise>
        location.href = '/ir/ir-data/'+uid+location.search;
        </c:otherwise>
        </c:choose>

    }

    // search form
    const searchFormEl = document.searchForm;

    // bind date picker - search form
    global.skn.list.bindDatepicker(searchFormEl.startDate, searchFormEl.endDate);
</script>
</html>
