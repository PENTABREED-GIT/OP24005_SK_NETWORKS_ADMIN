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
                    <div id="kt_content_container" class="container-fluid container-lg">
                        <!-- 본문 내용 START -->
                        <form>
                            <div class="card mb-5 mb-xl-10">
                                <div class="card-header border-0 pt-5">
                                    <div class="text-gray-900 fs-2 me-1 form-label fw-bolder text-dark">
                                        비밀번호 변경 안내 설정
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-2  d-flex justify-content-center me-10">
                                                <label class="col-lg-10 form-label fw-bolder text-dark pt-3  d-flex justify-content-center required">사용여부</label>
                                            </div>
                                            <div class="col-lg-9 pt-1">

                                                <div class="col-lg-4 form-check form-switch form-check-custom form-check-solid">
                                                    <input
                                                            id="passwordNoticeIsUse"
                                                            class="form-check-input"
                                                            type="checkbox"
                                                            <c:if test="${setting.passwordNoticeIsUse == 'Y'}">checked</c:if>
                                                    />
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-2  d-flex justify-content-center align-items-center me-10">
                                                <label class="col-lg-10 form-label fw-bolder text-dark pt-3 d-flex justify-content-center required">비밀번호 안내 주기</label>
                                            </div>

                                            <div class="col-lg-9">
                                                <div class="form-label fw-bold text-dark">
                                                    비밀번호 최종 변경일 기준
                                                    <div class="col-sm-2 d-inline-block">
                                                        <select name="passwordNoticePeriod"
                                                                id="passwordNoticePeriod"
                                                                class="form-select form-select-solid"
                                                                data-control="select2"
                                                                data-hide-search="true"
                                                                data-placeholder="전체"
                                                        >
                                                            <option></option>
                                                            <option value="30" ${setting.passwordNoticePeriod eq '30' ? 'selected' : ''}>30</option>
                                                            <option value="60" ${setting.passwordNoticePeriod eq '60' ? 'selected' : ''}>60</option>
                                                            <option value="90" ${setting.passwordNoticePeriod eq '90' ? 'selected' : ''}>90</option>
                                                            <option value="120" ${setting.passwordNoticePeriod eq '120' ? 'selected' : ''}>120</option>
                                                            <option value="150" ${setting.passwordNoticePeriod eq '150' ? 'selected' : ''}>150</option>
                                                            <option value="180" ${setting.passwordNoticePeriod eq '180' ? 'selected' : ''}>180</option>
                                                            <option value="365" ${setting.passwordNoticePeriod eq '365' ? 'selected' : ''}}>365</option>
                                                        </select>
                                                    </div>
                                                    일 마다 로그인 시 안내
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-2  d-flex justify-content-center align-items-center me-10">
                                                <label class="col-lg-10 form-label fw-bolder text-dark pt-3 d-flex justify-content-center required">다음 변경 안내 주기</label>
                                            </div>
                                            <div class="col-lg-9">
                                                <div class="form-label fw-bold text-dark">
                                                    [다음에 변경하기] 클릭 시
                                                    <div class="col-sm-2 d-inline-block">
                                                        <select name="passwordNoticePeriodNext"
                                                                id="passwordNoticePeriodNext"
                                                                class="form-select form-select-solid"
                                                                data-control="select2"
                                                                data-hide-search="true"
                                                                data-placeholder="전체"
                                                        >
                                                            <option></option>
                                                            <option value="30" ${setting.passwordNoticePeriodNext eq '30' ? 'selected' : ''}>30</option>
                                                            <option value="60" ${setting.passwordNoticePeriodNext eq '60' ? 'selected' : ''}>60</option>
                                                            <option value="90" ${setting.passwordNoticePeriodNext eq '90' ? 'selected' : ''}>90</option>
                                                            <option value="120" ${setting.passwordNoticePeriodNext eq '120' ? 'selected' : ''}>120</option>
                                                            <option value="150" ${setting.passwordNoticePeriodNext eq '150' ? 'selected' : ''}>150</option>
                                                            <option value="180" ${setting.passwordNoticePeriodNext eq '180' ? 'selected' : ''}>180</option>
                                                            <option value="365" ${setting.passwordNoticePeriodNext eq '365' ? 'selected' : ''}>365</option>
                                                        </select>
                                                    </div>
                                                    일 마다 로그인 시 재안내
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card mb-5 mb-xl-10">
                                <div class="card-header border-0 pt-5">
                                    <div class="text-gray-900 fs-2 me-1 form-label fw-bolder text-dark">
                                        비밀번호 변경 안내 설정
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-2  d-flex justify-content-center me-10">
                                                <label class="col-lg-10 form-label fw-bolder text-dark pt-3  d-flex justify-content-center required">사용여부</label>
                                            </div>
                                            <div class="col-lg-9 pt-1">

                                                <div class="col-lg-4 form-check form-switch form-check-custom form-check-solid">
                                                    <input
                                                            id="logoutNoticeIsUse"
                                                            class="form-check-input"
                                                            type="checkbox"
                                                           <c:if test="${setting.logoutNoticeIsUse == 'Y'}">checked</c:if>
                                                    />
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mb-8">
                                        <div class="col-lg-12 row">
                                            <div class="col-lg-2  d-flex align-items-center justify-content-center me-10">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3 d-flex justify-content-center required">관리자 자동 로그아웃 설정</label>
                                            </div>
                                            <div class="col-lg-9">
                                                <div class="form-label fw-bold text-dark">
                                                    번호 로그인 후
                                                    <div class="col-sm-2 d-inline-block">
                                                        <select name="logoutPeriod"
                                                                id="logoutPeriod"
                                                                class="form-select form-select-solid"
                                                                data-control="select2"
                                                                data-hide-search="true"
                                                        >
                                                            <option value="30" ${setting.logoutPeriod eq '30' ? 'selected' : ''}>30</option>
                                                            <option value="60" ${setting.logoutPeriod eq '60' ? 'selected' : ''}>60</option>
                                                            <option value="90" ${setting.logoutPeriod eq '90' ? 'selected' : ''}>90</option>
                                                            <option value="120" ${setting.logoutPeriod eq '120' ? 'selected' : ''}>120</option>
                                                            <option value="150" ${setting.logoutPeriod eq '150' ? 'selected' : '' }>150</option>
                                                            <option value="180" ${setting.logoutPeriod eq '180' ? 'selected' : ''}>180</option>
                                                        </select>
                                                    </div>
                                                    분 간 클릭이 없을 경우 자동 로그아웃
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <small class="form-text text-muted">
                                            * 본 기능을 사용하지 않을 경우, 최대 유지 시간은 24시간입니다.
                                        </small>
                                    </div>

                                </div>
                                <div class="card-footer" id="footerIdx">
                                    <div class="row">
                                        <div id="editGroup" class="col-12 d-flex justify-content-end">
                                            <button type="button" class="btn btn-success" onclick="update()">
                                                저장
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
<link rel="stylesheet" href="/assets/css/flatpickr/flatpickr.min.css">
<script src="/assets/js/flatpickr/flatpickr.js"></script>
<script src="/assets/js/flatpickr/ko.js"></script>
<script type="text/javascript">

    /***************************************************** INIT ***************************************/




    /**************************************************** UTILS ***************************************/

    window.onbeforeunload = function() {
        if (isModified) {
            return "변경 사항을 저장하지 않았습니다. 정말 지금 나가시겠습니까?";
        }
    };



    function formatDate(strDate) {
        let date = new Date(strDate);
        let year = date.getFullYear();
        let month = ("0" + (date.getMonth() + 1)).slice(-2);
        let day = ("0" + date.getDate()).slice(-2);
        let hour = ("0" + date.getHours()).slice(-2);
        let minute = ("0" + date.getMinutes()).slice(-2);
        let second = ("0" + date.getSeconds()).slice(-2);

        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }

    function onInputChange() {
        isModified = true;
    }

    /******************************************************************* PUT ************************************************/

    let isModified = false;

    async function update() {
        let passwordNoticeIsUse = document.getElementById('passwordNoticeIsUse').checked ? 'Y' : 'N';
        let passwordNoticePeriod = document.getElementById('passwordNoticePeriod').value;
        let passwordNoticePeriodNext = document.getElementById('passwordNoticePeriodNext').value;
        let logoutNoticeIsUse = document.getElementById('logoutNoticeIsUse').checked ? 'Y' : 'N';
        let logoutPeriod = document.getElementById('logoutPeriod').value;

        let data = {
            passwordNoticeIsUse: passwordNoticeIsUse,
            passwordNoticePeriod: passwordNoticePeriod,
            passwordNoticePeriodNext: passwordNoticePeriodNext,
            logoutNoticeIsUse: logoutNoticeIsUse,
            logoutPeriod: logoutPeriod
        }

        try {
            const response = await fetch('/api/v1/setting/update', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data)
            });

            const result = await response.json();
            if (result.result === false) {
                const errorMsg = result.message.split('internal error - ')[1];
                Swal.fire({
                    title: '수정 실패',
                    text: errorMsg,
                    icon: 'error',
                    confirmButtonText: '확인'
                })
            } else if (result.result == true) {
                if (result.data.includes("Validation error: ")) {
                    let errorMsg = result.data.split('Validation error:')[1];
                    Swal.fire({
                        title: '수정 실패',
                        text: errorMsg,
                        icon: 'error',
                        confirmButtonText: '확인'
                    })
                } else if (result.data == 'true') {
                    Swal.fire({
                        title: '수정 완료',
                        text: '수정이 완료되었습니다.',
                        icon: 'success',
                        confirmButtonText: '확인'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            location.reload();
                        }
                    })
                } else if (result.data == 'false') {
                    Swal.fire({
                        title: '수정 실패',
                        text: '교과서 주문수량은 필수 입력값입니다.',
                        icon: 'error',
                        confirmButtonText: '확인'
                    })
                }
            }
        } catch (Exception) {
            console.error(Exception);
        }
    }



    /**************************************************************** DELETE ***************************************************************/




</script>