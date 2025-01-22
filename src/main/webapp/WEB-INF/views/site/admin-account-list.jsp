<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/common/inc/page-header-inc.jsp" %>
</head>

<body id="kt_body"
      class="header-fixed header-tablet-and-mobile-fixed toolbar-enabled toolbar-fixed toolbar-tablet-and-mobile-fixed aside-enabled aside-fixed"
      style="--kt-toolbar-height:55px;--kt-toolbar-height-tablet-and-mobile:55px">
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
                        <form class="form" id="adminAccountForm">
                            <input type="hidden" value="<c:out value='${page.currentPage < 1 ? 1 : page.currentPage}'/>" name="page" id="page"/>
                            <input type="hidden" value="<c:out value='${page.pageListSize < 1 ? 30 : page.pageListSize}'/>" name="perPage"/>
                            <div class="card-body">
                                <div class="row g-8">
                                    <div class="col-xxl-3 fv-row">
                                        <label class="fs-6 form-label fw-bolder text-dark">기간</label>
                                        <div class="row fv-row fv-plugins-icon-container">
                                            <div class="col-6">
                                                <div class="input-group">
                                                    <input
                                                        id="startDate"
                                                        name="startDate"
                                                        class="form-control flatpickr-input"
                                                        placeholder="시작일"
                                                        autocomplete="off"
                                                        value="${search.startDate}"
                                                    />
                                                    <span class="input-group-text" id="basic-addon3">
                                                    <i class="bi bi-calendar-check fs-1"></i>
                                                    </span>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <div class="input-group">
                                                    <input
                                                        id="endDate"
                                                        name="endDate"
                                                        class="form-control flatpickr-input"
                                                        placeholder="종료일"
                                                        autocomplete="off"
                                                        value="${search.endDate}"
                                                    />
                                                    <span class="input-group-text" id="basic-addon3">
                                                    <i class="bi bi-calendar-check fs-1"></i>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-4">
                                        <div class="row">
                                            <div class="col-lg-4">
                                                <label class="fs-6 form-label fw-bolder text-dark">소속</label>
                                                <select
                                                    id="department"
                                                    name="department"
                                                    class="form-select form-select-solid"
                                                    data-control="select2"
                                                    data-hide-search="true"
                                                    data-placeholder="전체"
                                                >
                                                    <option value="전체">전체</option>
                                                    <c:forEach items="${departmentList}" var="department">
                                                        <option value="${department}" <c:if test="${search.department eq department}">selected</c:if> ><c:out value="${department}"/></option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-lg-4">
                                                <label class="fs-6 form-label fw-bolder text-dark">관리자 그룹</label>
                                                <select
                                                    id="adminGroupIndex"
                                                    name="adminGroupIndex"
                                                    class="form-select form-select-solid"
                                                    data-control="select2"
                                                    data-hide-search="true"
                                                    data-placeholder="전체"
                                                >
                                                    <option value="전체">전체</option>
                                                    <c:forEach items="${adminGroupList}" var="adminGroup">
                                                        <option value="<c:out value='${adminGroup.adminGroupIndex}'/>" <c:if test="${search.adminGroupIndex eq adminGroup.adminGroupIndex}">selected</c:if>><c:out value="${adminGroup.groupName}"/></option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-lg-4">
                                                <label class="fs-6 form-label fw-bolder text-dark">노출여부</label>
                                                <select
                                                    id="isUse"
                                                    name="isUse"
                                                    class="form-select form-select-solid"
                                                    data-control="select2"
                                                    data-hide-search="true"
                                                    data-placeholder="전체"
                                                >
                                                    <option value="전체">전체</option>
                                                    <option value="Y" ${search.isUse eq 'Y' ? 'selected' : ''}>Y</option>
                                                    <option value="N" ${search.isUse eq 'N' ? 'selected' : ''}>N</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-5">
                                        <div class="row g-8">
                                            <div class="col-lg-12">
                                                <label class="fs-6 form-label fw-bolder text-dark">검색어</label>
                                                <input
                                                    type="text"
                                                    id="searchValue"
                                                    name="searchValue"
                                                    class="form-control"
                                                    placeholder="검색어 입력"
                                                    maxlength="100"
                                                    value="<c:out value="${search.searchValue}"/>"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="card-footer">
                                <div class="d-flex justify-content-end" data-kt-customer-table-toolbar="base">
                                    <div>
                                        <a href="/site/admin-account" class="btn btn-light">
                                            <i class="fas bi-arrow-repeat fs-2 me-2"></i>
                                            초기화
                                        </a>
                                        <button type="button" class="btn btn-dark" id="searchBtn">
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
                                            class="fw-bolder">${listNum}</span> 개</h3>
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
                                    <col width="14%">
                                    <col width="14%">
                                    <col width="22%">
                                    <col width="22%">
                                    <col width="14%">
                                    <col width="7%">
                                </colgroup>
                                <!--begin::Table head-->
                                <thead>
                                <!--begin::Table row-->
                                <tr class="fw-semibold fs-6 text-muted">
                                    <th class="min-w-70px">No.</th>
                                    <th class="min-w-120px">관리자 ID</th>
                                    <th class="min-w-70px">관리자 이름</th>
                                    <th class="min-w-70px">소속</th>
                                    <th class="min-w-60px">관리자 그룹</th>
                                    <th class="min-w-60px">최근 접속일시</th>
                                    <th class="min-w-60px">사용여부</th>
                                </tr>
                                <!--end::Table row-->
                                </thead>
                                <!--end::Table head-->
                                <!--begin::Table body-->
                                <tbody>
                                <c:if test="${not empty adminAccountList}">
                                    <c:forEach items="${adminAccountList}" var="list" varStatus="status">
                                        <tr>
                                            <td class="text-dark fw-bold">
                                                ${((listNum - (page.pageListSize * (page.currentPage - 1))) - status.index)}
                                            </td>
                                            <td class="text-dark text-hover-warning fw-bold hoverable"
                                                data-uid="${list.uid}"
                                                name="name"
                                                onclick="loadView(this)">
                                                <c:out value="${list.adminId}"/>
                                            </td>
                                            <td class="text-dark text-hover-warning fw-bold hoverable"
                                                data-uid="${list.uid}"
                                                onclick="loadView(this)">
                                                <c:out value="${list.adminName}"/>
                                            </td>
                                            <td class="text-dark fw-bold">
                                                <c:out value="${list.department}"/>
                                            </td>
                                            <td class="text-dark fw-bold">
                                                <c:forEach items="${adminGroupList}" var="adminGroup">
                                                    <c:if test="${list.adminGroupIndex eq adminGroup.adminGroupIndex}"><c:out value="${adminGroup.groupName}"/></c:if>
                                                </c:forEach>
                                            </td>
                                            <td class="text-dark fw-bold">
                                                <c:out value="${list.lastLoginDate}"/>
                                            </td>
                                            <td class="text-dark fw-bold">
                                                <c:out value="${list.isUse}"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty adminAccountList}">
                                    <tr>
                                        <td class="center" colspan="7">데이터가 없습니다.</td>
                                    </tr>
                                </c:if>
                                </tbody>
                                <!--end::Table body-->
                            </table>
                            <!--end::Table-->
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="d-flex justify-content-center flex-grow-1">
                                    <%@ include file="/WEB-INF/views/common/inc/paging-inc.jsp" %>
                                </div>
                                <div>
                                    <a href="/site/admin-account-reg-form" class="btn btn-m"
                                       style=" color: #fff; background-color: #343a40;">
                                        등록
                                    </a>
                                </div>
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
<link rel="stylesheet" href="/assets/css/flatpickr/flatpickr.min.css">
<script src="/assets/js/flatpickr/flatpickr.js"></script>
<script src="/assets/js/flatpickr/ko.js"></script>
<script type="text/javascript">
    function initializeFlatpickr() {
        const startDateInput = document.getElementById('startDate');
        const endDateInput = document.getElementById('endDate');

        startFlatpickr = flatpickr(startDateInput, {
            dateFormat: 'Y-m-d',
            locale: 'ko',
            onChange: function (selectedDates) {
                if (selectedDates.length > 0) {
                    const maxDate = new Date(selectedDates[0]);
                    maxDate.setFullYear(maxDate.getFullYear() + 1);
                    endFlatpickr.set('minDate', selectedDates[0]);
                    endFlatpickr.set('maxDate', maxDate);
                }
            }
        });

        endFlatpickr = flatpickr(endDateInput, {
            dateFormat: 'Y-m-d',
            locale: 'ko',
            onChange: function (selectedDates) {
                if (selectedDates.length > 0) {
                    const minDate = new Date(selectedDates[0]);
                    minDate.setFullYear(minDate.getFullYear() - 1);
                    startFlatpickr.set('minDate', minDate);
                    startFlatpickr.set('maxDate', selectedDates[0]);
                }
            }
        });
    }

    document.addEventListener('DOMContentLoaded', function () {
        const targetNode = document.querySelectorAll('.form-select');
        targetNode.forEach((node) => {
            node.style.backgroundColor = '#ffffff';
            node.style.backgroundClip = 'padding-box';
            node.style.border = '1px solid #E4E6EF';
        });
        initializeFlatpickr();
        document.getElementById('searchBtn').addEventListener('click', () => search());

        document.getElementById('searchValue').addEventListener('keydown', (evt) => {
            let searchValue = document.getElementById('searchValue').value.trim();
            if(evt.key == "Enter" && searchValue) {
                search()
            }
        });
    })
    const search = () => {
        let searchValue = document.getElementById('searchValue').value.trim();
        document.getElementById('searchValue').value = searchValue;

//        if (!searchValue) {
//            Swal.fire({
//                title: '검색어를 입력해주세요.',
//                text: '',
//                icon: 'error',
//                confirmButtonText: '확인'
//            })
//            return false;
//        }

        document.getElementById('adminAccountForm').submit();
    }

    const updateUrlParam = (url, paramName, paramValue) => {
        const urlObject = new URL(url);
        urlObject.searchParams.set(paramName, paramValue);
        return urlObject.toString();
    }

    const changePage = (page) => {
        document.getElementById("page").value = page;
        let returnUrl = updateUrlParam(window.location.href, 'page', page);
        location.href = returnUrl;
    }

    const changePerPage = (select) => {
        const perPage = select.value;
        let params = updateUrlParam(window.location.href, 'page', 1);
        let returnUrl = updateUrlParam(params, 'perPage', perPage);
        location.href = returnUrl;
    }

    const loadView = (element) => {
        const uid = element.getAttribute('data-uid');
        const url = '/site/admin-account/' + uid;
        window.location.href = url;
    }

</script>

</body>
</html>
