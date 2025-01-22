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
                <div class="post d-flex flex-column-fluid">
                    <input type="hidden" name="uid" id="uid" value="<c:out value='${adminRole.uid}'/>"/>
                    <input type="hidden" name="isUse" id="isUse" value ="<c:out value="${adminRole.isUse}" />"/>
                    <div class="container-fluid">
                    <!-- 본문 내용 START -->
                        <form>
                            <div class="card mb-5 mb-xl-10">
                                <div class="card-header border-0 pt-5">
                                    <div class="text-gray-900 fs-2 me-1 form-label fw-bolder text-dark">
                                        관리자 권한 관리
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-lg-6 row mb-8">
                                            <div class="col-lg-2">
                                                <label class="col-lg-10 fw-bolder text-dark pt-3">작성자</label>
                                            </div>
                                            <div class="col-lg-10">
                                                <div class="col-lg-12 text-dark pt-3">
                                                    <c:out value="${adminRole.adminId}"/>(<c:out value="${adminRole.department}"/>)
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 row mb-8">
                                            <div class="col-lg-2">
                                                <label class="col-lg-10 fw-bolder text-dark pt-3">작성일시</label>
                                            </div>
                                            <div class="col-lg-10">
                                                <div class="col-lg-12 text-dark pt-3">
                                                    <c:choose>
                                                        <c:when test="${adminRole.modDate ne null and adminRole.modDate != ''}">
                                                            <c:out value="${adminRole.modDate}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${adminRole.regDate}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-1  d-flex align-items-center">
                                                <label class="col-lg-10 fw-bolder text-dark pt-3">관리자 그룹명</label>
                                            </div>

                                            <div class="col-lg-11">
                                                <div class="col-lg-6 text-dark pt-3">
                                                    <c:out value="${adminRole.groupName}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-1  d-flex align-items-center">
                                                <label class="col-lg-10 fw-bolder text-dark pt-3">권한 설명</label>
                                            </div>
                                            <div class="col-lg-11">
                                                <div class="col-lg-12 text-dark pt-3">
                                                    <c:out value="${adminRole.description}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-1  d-flex align-items-center">
                                                <label class="col-lg-12 fw-bolder text-dark pt-3">사용 여부</label>
                                            </div>
                                            <div class="col-lg-2 pt-3">
                                                <c:out value="${adminRole.isUse}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <div class="card mb-5 mb-xl-10">
                            <div class="card-header border-0 pt-5">
                                <div class="text-gray-900 fs-2 me-1 form-label fw-bolder text-dark">
                                    권한 설정
                                </div>
                            </div>
                            <div class="card-body">

                                <div class="row mb-15">
                                    <div class="col-lg-12">
                                        <div class="row">
                                            <div class="col-lg-2">
                                                <div class="form-check form-check-custom form-check-solid ">
                                                    <input class="form-check-input" type="checkbox" value="" id="isMain" name="isMain" disabled ${adminRole.isMain == 'Y'  ? 'checked' : ''}/>
                                                    <label class="form-check-label form-label fw-bolder text-dark pt-3" for="isMain">
                                                        메인 관리
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <hr>
                                <c:forEach items="${menuList}" var="menu">
                                    <div class="row mb-8">
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <div class="col-lg-2">
                                                    <div class="form-check form-check-custom form-check-solid">
                                                        <input
                                                                class="form-check-input"
                                                                data-flag-depth2="${menu.menuIndex}"
                                                                type="checkbox"
                                                                value=""
                                                                id="flexCheckChecked-${menu.menuIndex}"
                                                                disabled
                                                                ${not empty adminRole and menu.isCheck == 'Y' ? 'checked' : (empty adminRole ? 'checked' : '')}
                                                        />
                                                        <label class="form-check-label form-label fw-bolder text-dark pt-3" for="flexCheckChecked-${menu.menuIndex}">
                                                                ${menu.menuName}
                                                        </label>
                                                    </div>
                                                </div>
                                                <c:if test="${not empty menu.children}">
                                                    <c:forEach items="${menu.children}" var="item" varStatus="status">
                                                        <div class="col-lg custom-col-lg">
                                                            <div class="form-check form-check-custom form-check-solid mb-3">
                                                                <input
                                                                        class="form-check-input"
                                                                        data-flag-depth2="${menu.menuIndex}"
                                                                        data-flag-depth3="${item.menuIndex}"
                                                                        type="checkbox"
                                                                        value=""
                                                                        id="flexCheckChecked-${item.menuIndex}"
                                                                        disabled
                                                                    ${not empty adminRole and item.isCheck == 'Y' ? 'checked' : (empty adminRole ? 'checked' : '')}
                                                                />
                                                                <label class="form-check-label form-label fw-bolder text-dark pt-3" for="flexCheckChecked-${item.menuIndex}">
                                                                        ${item.menuName}
                                                                </label>
                                                            </div>
                                                            <c:if test="${not empty item.children}">
                                                                <c:forEach items="${item.children}" var="subItem" varStatus="subStatus">
                                                                    <div class="row form-check-custom form-check-solid mb-3">
                                                                        <div class="col-lg-12">
                                                                            <div class="form-check form-check-custom form-check-warning form-check-solid">
                                                                                <input
                                                                                        class="form-check-input"
                                                                                        data-flag-depth2="${menu.menuIndex}"
                                                                                        data-flag-depth3="${item.menuIndex}"
                                                                                        type="checkbox"
                                                                                        value=""
                                                                                        id="flexCheckDefault-${subItem.menuIndex}"
                                                                                        disabled
                                                                                    ${not empty adminRole and subItem.isCheck == 'Y' ? 'checked' : (empty adminRole ? 'checked' : '')}
                                                                                />
                                                                                <label class="form-check-label" for="flexCheckDefault-${subItem.menuIndex}">
                                                                                        ${subItem.menuName}
                                                                                </label>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </c:if>
                                                        </div>
                                                    </c:forEach>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                    <hr>
                                </c:forEach>

                            </div>
                            <div class="card-footer d-flex justify-content-end">
                                <div class="col d-flex justify-content-start">
                                    <a class="btn btn-dark" href="/site/admin-privilege">목록</a>
                                </div>
                                <div class="col d-flex justify-content-end">
                                    <a class="btn btn-warning" href="javascript: ;" id="modifyBtn">수정</a>
                                </div>
                            </div>
                        </div>
                        <!-- 본문 내용 END -->
                    </div>
                </div>
            </div>
            <%@ include file="/WEB-INF/views/common/inc/footer-inc.jsp" %>
        </div>
    </div>
</div>

</body>
<link rel="stylesheet" href="/assets/css/flatpickr/flatpickr.min.css">
<script src="/assets/js/flatpickr/flatpickr.js"></script>
<script src="/assets/js/flatpickr/ko.js"></script>
<script type="text/javascript">
    const modify = () => {
        const uid = document.getElementById('uid');
        window.location.href = '/site/admin-privilege-mod-form/${uid}';
    };
    const remove = async () => {
        let uid = document.getElementById('uid').value;
        Swal.fire({
            title: '등록하신 게시물을 삭제 하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
            cancelButtonText: '취소'
        })
        .then(async (result) => {
            if (result.isConfirmed) {
                const response = await fetch('/api/v1/site/admin-privilege/delete/${uid}', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify()
                });

                const res = await response.json();
                if (res) {
                    location.href = '/site/admin-privilege'
                }
            }
        })
    }
    document.addEventListener('DOMContentLoaded', function () {
        if (document.getElementById('deleteBtn')) {
            document.getElementById('deleteBtn').addEventListener('click', () => remove());
        }
        document.getElementById('modifyBtn').addEventListener('click', () => modify());
    })
</script>

<style>
    .custom-col-lg {
        flex: 0 0 12.5%;
        max-width: 12.5%;
    }
</style>