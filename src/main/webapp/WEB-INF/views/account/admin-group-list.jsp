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
<link rel="stylesheet" href="/assets/css/icon/flag-icons.min.css"/>
<div class="d-flex flex-column flex-root">
    <div class="page d-flex flex-row flex-column-fluid">
        <%@ include file="/WEB-INF/views/common/inc/body-aside-inc.jsp" %>
        <div class="wrapper d-flex flex-column flex-row-fluid" id="kt_wrapper">
            <%@ include file="/WEB-INF/views/common/inc/body-header-inc.jsp" %>
            <div class="content d-flex flex-column flex-column-fluid" id="kt_content">
                <%@ include file="/WEB-INF/views/common/inc/toolbar-inc.jsp" %>

                <div id="kt_content_container" class="container-fluid">
                    <div class="card mb-7">
                        <form class="form" id="adminGroupSearchForm">
                            <input type="hidden" value="<c:out value='${page.currentPage < 1 ? 1 : page.currentPage}'/>" name="page" id="page"/>
                            <input type="hidden" value="<c:out value='${page.pageListSize < 1 ? 30 : page.pageListSize}'/>" name="pageListSize"/>
                            <input type="hidden" name="uid" value="">
                            <div class="card-body">

                                <div class="row g-8">
                                    <div class="col-lg-2 fv-row">
                                        <label class="fs-6 form-label fw-bold text-dark" for="isUse">
                                            사용여부
                                        </label>
                                        <select id="isUse" name="isUse" class="form-select form-select-solid" data-control="select2" data-placeholder="전체" data-hide-search="true">
                                            <option value="전체">전체</option>
                                            <option value="Y" ${search.isUse eq 'Y' ? 'selected' : ''}>Y</option>
                                            <option value="N" ${search.isUse eq 'N' ? 'selected' : ''}>N</option>
                                        </select>
                                    </div>


                                    <div class="col-lg-10">
                                        <div class="row g-8">
                                            <div class="col-lg-12">
                                                <label class="fs-6 form-label fw-bold text-dark">검색어</label>
                                                <input
                                                        type="text"
                                                        id="searchWord"
                                                        name="searchWord"
                                                        class="form-control"
                                                        placeholder="검색어 입력"
                                                        maxlength="100"
                                                        value="<c:out value="${search.searchWord}"/>"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="card-footer">
                                <div class="d-flex justify-content-end" data-kt-customer-table-toolbar="base">
                                    <div>
                                        <a href="/site/admin-group" class="btn btn-light">
                                            <i class="fas bi-arrow-repeat fs-2 me-2"></i>
                                            초기화
                                        </a>
                                        <button id="searchBtn" type="submit" class="btn btn-dark">
                                            검색
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="card">
                        <div class="card-header border-0 pt-6L">
                            <div class="card-title">
                                <div class="row d-flex align-items-center justify-content-between">
                                    <div class="col d-flex align-items-center justify-content-between">
                                        <h3 class="fs-6 fw-bold mt-3 mb-3"
                                        style="flex-grow: 1; min-width: 0; white-space: nowrap;">총 <span
                                        class="fw-bolder">${totalCount}</span> 개</h3>
                                    </div>
                                    <div class="col">
                                        <select
                                            id="perPage"
                                            name="perPage"
                                            class="form-select form-select-solid"
                                            data-control="select2"
                                            data-hide-search="true"
                                            onchange="changePerPage(this)"
                                            style="margin-left: 10px;"
                                        >
                                            <option
                                                value="10"
                                                <c:if test="${page.pageListSize eq '10'}">
                                                    selected
                                                </c:if>>
                                            10
                                            </option>
                                            <option
                                                value="30"
                                                <c:if test="${empty page.pageListSize || page.pageListSize eq '30'}">
                                                    selected
                                                </c:if>>
                                            30
                                            </option>
                                            <option
                                                value="50"
                                                <c:if test="${page.pageListSize eq '50'}">
                                                    selected
                                                </c:if>>
                                            50
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="card-body pt-0 table-responsive">
                            <!--begin::Table-->
                            <table class="table table-row-bordered gy-5" id="faqCommonTable">
                                <colgroup>
                                    <col width="7%">
                                    <col width="30%">
                                    <col width="">
                                    <col width="7%">
                                    <col width="14%">
                                </colgroup>
                                <!--begin::Table head-->
                                <thead>
                                <!--begin::Table row-->
                                <tr class="fw-semibold fs-6 text-muted">
                                    <th>번호</th>
                                    <th>관리자 그룹명</th>
                                    <th>권한설명</th>
                                    <th>사용여부</th>
                                    <th>작성일시</th>
                                </tr>
                                <!--end::Table row-->
                                </thead>
                                <!--end::Table head-->
                                <!--begin::Table body-->
                                <tbody>
                                <c:if test="${not empty list}">
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                        <tr>
                                            <td class="text-dark fw-bold">
                                                    ${((totalCount - (page.pageListSize * (page.currentPage - 1))) - status.index)}
                                            </td>
                                            <td class="text-dark fw-bold text-hover-warning hoverable"
                                                data-uid="${list.uid}"
                                                onclick="loadView(this)">
                                                <c:out value="${list.groupName}"/>
                                            </td>
                                            <td class="text-hover-warning hoverable"
                                                data-uid="${list.uid}"
                                                onclick="loadView(this)">
                                                <c:out value="${list.description}"/>
                                            </td>
                                            <td class="text-dark fw-bold">
                                                <c:out value="${list.isUse}"/>
                                            </td>
                                            <td class="text-dark fw-bold">
                                                <c:out value="${list.regDate}"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
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
                            <c:if test="${empty list}">
                                <div class="min-h-md-100px d-flex justify-content-center" style="line-height:10;">
                                    데이터가 없습니다.
                                </div>
                            </c:if>
                        </div>
                        <div class="card-footer d-flex justify-content-end">
                            <div>
                                <a href="/site/admin-group-reg-form" class="btn btn-info">
                                    등록
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <%--                    </div>--%>
            </div>
            <%@ include file="/WEB-INF/views/common/inc/footer-inc.jsp" %>
        </div>
    </div>
</div>
<div class="modal fade" id="modalLayout" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered mw-900px" id="modalContent">

    </div>
</div>
<link rel="stylesheet" href="/assets/css/flatpickr/flatpickr.min.css">
<script src="/assets/js/flatpickr/flatpickr.js"></script>
<script src="/assets/js/flatpickr/ko.js"></script>
<script type="text/javascript">
    function search() {
        document.getElementById('adminGroupSearchForm').submit();
    }
    const updateUrlParam = (url, paramName, paramValue) => {
        const urlObject = new URL(url);
        urlObject.searchParams.set(paramName, paramValue);
        return urlObject.toString();
    }

    const changePerPage = (select) => {
        const pageListSize = select.value;
        let params = updateUrlParam(window.location.href, 'page', 1);
        let returnUrl = updateUrlParam(params, 'pageListSize', pageListSize);
        location.href = returnUrl;
    }

    const loadView = (element) => {
        const uid = element.getAttribute('data-uid');
        const url = '/site/admin-group/' + uid;
        window.location.href = url + location.search;
    }
</script>

</body>
</html>
