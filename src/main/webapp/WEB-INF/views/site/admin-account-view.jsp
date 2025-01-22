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
                    <input type="hidden" name="uid" id="uid" value="<c:out value='${adminAccount.uid}'/>"/>
                    <input type="hidden" name="idx" id="idx" value="<c:out value='${adminAccount.adminIndex}'/>"/>
                    <div class="container-fluid">
                        <!-- 본문 내용 START -->
                        <form>
                            <div class="card mb-5 mb-xl-10">
                                <div class="card-header border-0 pt-5">
                                    <div class="text-gray-900 fs-2 me-1 form-label fw-bolder text-dark">
                                        관리자 계정 관리
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-1">
                                                <label class="col-lg-10 form-label fw-bolder text-dark pt-3">관리자 ID</label>
                                            </div>
                                            <div class="col-lg-11">
                                                <div class="col-lg-12 text-dark pt-3">
                                                    <c:out value="${adminAccount.adminId}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-1">
                                                <label class="col-lg-10 form-label fw-bolder text-dark pt-3">관리자 이름</label>
                                            </div>
                                            <div class="col-lg-11">
                                                <div class="col-lg-12 text-dark pt-3">
                                                    <c:out value="${adminAccount.adminName}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-6 row mb-8">
                                            <div class="col-lg-2">
                                                <label class="col-lg-10 fw-bolder text-dark pt-3">휴대폰 번호</label>
                                            </div>
                                            <div class="col-lg-10">
                                                <div class="col-lg-12 text-dark pt-3">
                                                    <c:out value="${adminAccount.getPhoneNoDecrypted()}"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 row mb-8">
                                            <div class="col-lg-2">
                                                <label class="col-lg-10 fw-bolder text-dark pt-3">이메일 주소</label>
                                            </div>
                                            <div class="col-lg-10">
                                                <div class="col-lg-12 text-dark pt-3">
                                                    <c:out value="${adminAccount.getEmailDecrypted()}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-6 row mb-8">
                                            <div class="col-lg-2">
                                                <label class="col-lg-10 fw-bolder text-dark pt-3">소속</label>
                                            </div>
                                            <div class="col-lg-10">
                                                <div class="col-lg-12 text-dark pt-3">
                                                    <c:out value="${adminAccount.department}"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 row mb-8">
                                            <div class="col-lg-2">
                                                <label class="col-lg-10 fw-bolder text-dark pt-3">직책/직무</label>
                                            </div>
                                            <div class="col-lg-10">
                                                <div class="col-lg-12 text-dark pt-3">
                                                    <c:out value="${adminAccount.duty}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-1">
                                                <label class="col-lg-10 form-label fw-bolder text-dark pt-3">관리자 그룹</label>
                                            </div>
                                            <div class="col-lg-11">
                                                <div class="col-lg-12 text-dark pt-3">
                                                    <c:out value="${adminAccount.adminGroupName}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-1 ">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3">비밀번호 상태</label>
                                            </div>
                                            <div class="col-lg-11">
                                                <div class="col-lg-12 text-dark pt-3">
                                                    <c:choose>
                                                        <c:when test="${adminAccount.isSleep eq 'Y'}">사용제한(휴면상태)</c:when>
                                                        <c:when test="${adminAccount.isUse eq 'Y'}">정상</c:when>
                                                        <c:when test="${adminAccount.isUse eq 'N' and adminAccount.loginFailCount == 5}">사용제한(비밀번호 5회 실패)</c:when>
                                                        <c:when test="${adminAccount.isUse eq 'N'}">사용제한(사용중지)</c:when>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-1">
                                                <div class="col-lg-12">
                                                    <label class="form-label fw-bolder text-dark pt-3">최근 접속일시</label>
                                                </div>
                                            </div>

                                            <div class="col-lg-11">
                                                <div class="col-lg-12 text-dark pt-3">
                                                    <div>${adminAccount.lastLoginDate}</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-1 ">
                                                <label class="col-lg-10 form-label fw-bolder text-dark pt-3">사용여부</label>
                                            </div>
                                            <div class="col-lg-11 pt-3 row">
                                                <div class="col-lg-12">
                                                    <c:out value="${adminAccount.isUse}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-footer" id="footerIdx">
                                    <div class="row">
                                        <div id="listGroup" class="col-3">
                                            <a href="/site/admin-account" class="btn btn-light-primary" style="background-color: #343a40; color: #FFFFFF">목록</a>
<%--                                            <button type="button" class="btn btn-light-primary" style="background-color: #343a40; color: #FFFFFF" onclick="history.back()">목록</button>--%>
                                        </div>
                                        <div class="col-9 d-flex justify-content-end">
                                            <c:choose>
                                                <c:when test="${adminAccount.isUse == 'N'}">
                                                    <button type="button" class="btn btn-danger me-2" id="deleteBtn">
                                                        삭제
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                </c:otherwise>
                                            </c:choose>
                                            <button type="button" class="btn btn-warning" id="modifyBtn">
                                                수정
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <!-- 본문 내용 END -->
                    </div>
                </div>
            </div>
            <%@ include file="/WEB-INF/views/common/inc/footer-inc.jsp" %>
        </div>
    </div>
</div>

</body>
<script src="/assets/js/flatpickr/ko.js"></script>
<script type="text/javascript">
    const remove = async () => {
        let uid = document.getElementById('uid').value;
        Swal.fire({
            title: '등록하신 계정을 삭제 하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
            cancelButtonText: '취소'
        })
        .then(async (result) => {
            if (result.isConfirmed) {
                const response = await fetch('/api/v1/site/admin-account/delete/${uid}', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify()
                });

                const res = await response.json();
                if (res) {
                    location.href = '/site/admin-account'
                }
            }
        })
    }
    const modify = () => {
        const uid = document.getElementById('uid');
        window.location.href = '/site/admin-account-mod-form/${uid}';
    };

    document.addEventListener('DOMContentLoaded', function () {
        if(document.getElementById('deleteBtn')) {
            document.getElementById('deleteBtn').addEventListener('click', () => remove());
        }
        document.getElementById('modifyBtn').addEventListener('click', () => modify());
    })
    </script>