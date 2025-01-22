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
                <form class="form">
                <div id="kt_content_container" class="container-fluid">
                    <div class="card mb-7">
                            <div class="card-body">

                                <div class="row g-8">
                                    <div class="col-xxl-3 fv-row">
                                        <label class="fs-6 form-label fw-bolder text-dark">기간</label>
                                        <div class="row fv-row fv-plugins-icon-container">
                                            <div class="col-6">
                                                <div class="input-group">
                                                    <input
                                                            id="sdate"
                                                            name="sdate"
                                                            type="text"
                                                            class="form-control flatpickr-input"
                                                            placeholder="시작일"
                                                            autocomplete="off"
                                                            value="<c:out value="${searchParam.startDate}"/>"
                                                    />
                                                    <span class="input-group-text" id="basic-addon3">
                                                    <i class="bi bi-calendar-check fs-1"></i>
                                                    </span>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <div class="input-group">
                                                    <input
                                                            id="edate"
                                                            name="edate"
                                                            type="text"
                                                            class="form-control flatpickr-input"
                                                            placeholder="종료일"
                                                            autocomplete="off"
                                                            value="<c:out value="${searchParam.endDate}"/>"
                                                    />
                                                    <span class="input-group-text" id="basic-addon3">
                                                    <i class="bi bi-calendar-check fs-1"></i>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-2">
                                        <label class="fs-6 form-label fw-bolder text-dark">구분</label>
                                        <select
                                                id="actionType"
                                                name="actionType"
                                                class="form-select form-select-solid"
                                                data-control="select2"
                                                data-hide-search="true"
                                                data-placeholder="전체"
                                        >
                                            <option></option>
                                            <option value='POST' ${searchParam.actionType eq 'POST' ? 'selected' : ''}>등록</option>
                                            <option value='GET' ${searchParam.actionType eq 'GET' ? 'selected' : ''}>조회</option>
                                            <option value='PUT'   ${searchParam.actionType eq 'PUT' || param.actionType eq 'PATCH' ? 'selected' : ''}>수정</option>
                                            <option value='DOWNLOAD' ${searchParam.actionType eq 'DOWNLOAD' ? 'selected' : ''}>다운로드</option>
                                        </select>
                                    </div>

                                    <div class="col-lg-7">
                                        <div class="row g-8">
                                            <div class="col-lg-12">
                                                <label class="fs-6 form-label fw-bolder text-dark">검색어</label>
                                                <input
                                                        type="text"
                                                        id="search"
                                                        name="search"
                                                        class="form-control"
                                                        placeholder="검색어 입력"
                                                        maxlength="100"
                                                        value="<c:out value="${searchParam.searchWord}"/>"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="card-footer">
                                <div class="d-flex justify-content-end" data-kt-customer-table-toolbar="base">
                                    <div>
                                        <a href="/log/usage-log" class="btn btn-light">
                                            <i class="fas bi-arrow-repeat fs-2 me-2"></i>
                                            초기화
                                        </a>
                                        <button type="submit" class="btn btn-dark">
                                            검색
                                        </button>
                                    </div>
                                </div>
                            </div>

                    </div>

                    <div class="card">

                        <div class="card-body pt-0 table-responsive">
                            <!--begin::Table-->
                            <table class="table table-row-bordered gy-5" id="faqCommonTable">
                                <!--begin::Table head-->
                                <thead>
                                <!--begin::Table row-->
                                <tr class="fw-semibold fs-6 text-muted">
                                    <th class="col-xxl-1">No.</th>
                                    <th class="col-xxl-3">메뉴</th>
                                    <th class="col-xxl-1">구분</th>
                                    <th class="col-xxl-1">ID</th>
                                    <th class="col-xxl-1">이름</th>
                                    <th class="col-xxl-1">IP</th>
                                    <th class="col-xxl-2">발생일자</th>
                                </tr>
                                <!--end::Table row-->
                                </thead>
                                <!--end::Table head-->
                                <!--begin::Table body-->
                                <tbody>
                                <c:if test="${not empty actionLogList}">
                                    <c:forEach items="${actionLogList}" var="list" varStatus="status">
                                        <tr>
                                            <td class="text-dark fw-bold">
                                                    ${((totalCount - (page.pageListSize * (page.currentPage - 1))) - status.index)}
                                            </td>
                                            <td class="text-dark fw-bold text-hover-warning hoverable"
                                                data-uid="${list.uid}"
                                            >
                                                <c:out value="${list.detail}"/>
                                            </td>
                                            <td class="text-dark text-hover-warning fw-bold hoverable"
                                                data-uid="${list.uid}"
                                            >
                                                <c:out value="${list.actionType}"/>
                                            </td>
                                            <td class="text-dark text-hover-warning fw-bold hoverable"
                                                data-uid="${list.uid}"
                                            >
                                                <c:out value="${list.adminId}"/>
                                            </td>
                                            <td class="text-dark text-hover-warning fw-bold hoverable"
                                                data-uid="${list.uid}"
                                            >
                                                <c:out value="${list.adminName}"/>
                                            </td>
                                            <td class="text-dark text-hover-warning fw-bold hoverable"
                                                data-uid="${list.uid}"
                                            >
                                                <c:out value="${list.ip}"/>
                                            </td>
                                            <td class="text-dark text-hover-warning fw-bold hoverable"
                                                data-uid="${list.uid}"
                                            >
                                                <c:out value="${list.regDate}"/>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty actionLogList}">
                                    <tr>
                                        <td colspan="8" class="text-center text-dark fw-bold">
                                            데이터가 없습니다.
                                        </td>
                                    </tr>
                                </c:if>
                                </tbody>
                                <!--end::Table body-->
                            </table>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="d-flex justify-content-center flex-grow-1">
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
                            <!--end::Table-->
                        </div>
                    </div>
                </div>
                </form>
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
    function initializeFlatpickr() {
        const today = new Date();
        const oneMonthBefore = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());

        const startDateInput = document.getElementById('sdate');
        const endDateInput = document.getElementById('edate');
        const initialStartDate = startDateInput.value;
        const initialEndDate = endDateInput.value;

        startFlatpickr = flatpickr(startDateInput, {
            dateFormat: 'Y-m-d',
            locale: 'ko',
            defaultDate: initialStartDate || oneMonthBefore,
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
            defaultDate: initialEndDate || today,
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
        initializeFlatpickr();
    })


    function fetchUserList(page) {
        const sdate = document.getElementById('sdate').value;
        const edate = document.getElementById('edate').value;
        const actionType = document.getElementById('actionType').value;
        const search = document.getElementById('search').value;
        let queryParams = '?perPage=' + document.getElementById('perPage').value;

        if (sdate != null || sdate !== undefined) {
            queryParams += '&sdate=' + sdate;
        }

        if (edate != null || edate !== undefined) {
            queryParams += '&edate=' + edate;
        }

        if (actionType != null || actionType !== undefined) {
            queryParams += '&actionType=' + actionType;
        }


        if (search != null || search !== undefined) {
            queryParams += '&search=' + search;
        }

        location.href = '/log/usage-log' + queryParams;
    }

    function changePage(page) {
        const sdate = document.getElementById('sdate').value;
        const edate = document.getElementById('edate').value;
        const actionType = document.getElementById('actionType').value;
        const search = document.getElementById('search').value;


        let queryParams = '?page=' + page + '&perPage=' + document.getElementById('perPage').value;

        if (sdate != null || sdate !== undefined) {
            queryParams += '&sdate=' + sdate;
        }

        if (edate != null || edate !== undefined) {
            queryParams += '&edate=' + edate;
        }

        if (actionType != null || actionType !== undefined) {
            queryParams += '&actionType=' + actionType;
        }


        if (search != null || search !== undefined) {
            queryParams += '&search=' + search;
        }

        location.href = '/log/usage-log' + queryParams;
    }



</script>

</body>
</html>
