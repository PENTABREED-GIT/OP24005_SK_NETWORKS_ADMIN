<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
    String froalaKey = resourceBundle.getString("froala.key");
%>
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
                <%@ include file="/WEB-INF/views/common/inc/toolbar-inc.jsp" %>
                <div id="kt_content_container">
                    <input type="hidden" name="uid" id="uid" value="${adminAccount != null ? adminAccount.uid : uid}"/>
                    <input type="hidden" name="idx" id="idx" value="${adminAccount != null ? adminAccount.adminIndex : null}"/>
                    <c:set value="false" var="currentAdminYn"/>
                    <c:if test="${adminAccount != null and ADMIN_UID eq adminAccount.uid}">
                        <c:set value="true" var="currentAdminYn"/>
                    </c:if>
                    <input type="hidden" name="currentAdminYn" id="currentAdminYn" value="<c:out value="${currentAdminYn}"/>"/>
                    <input type="hidden" name="lastAdminGroupIndex" id="lastAdminGroupIndex" value="${adminAccount != null ? adminAccount.adminGroupIndex : null}"/>
                    <input type="hidden" name="adminType" id="adminType" value="<c:out value='${adminAccount != null ? adminAccount.adminType :"G"}'/>"/>
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
                                        <div class="col-lg-6 row">
                                            <div class="col-lg-3  d-flex align-items-center justify-content-center">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3 required">관리자 ID</label>
                                            </div>
                                            <div class="col-lg-9 row">
                                                <div class="col-lg-8 form-label text-dark pt-3">
                                                    <input type="text" class="form-control" id="adminId" name="adminId" placeholder="관리자 ID 입력" value="${adminAccount.adminId}" maxlength="12" required <c:if test="${not empty adminAccount}">readonly style="background-color:#F5F8FA;"</c:if>>
                                                </div>
                                                <c:if test="${empty adminAccount}">
                                                <div class="col-lg-4 form-label text-dark pt-3">
                                                    <button type="button" class="btn btn-primary" onclick="idDuplicated()">중복확인</button>
                                                </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="row mb-8">
                                    <div class="col-lg-12 row">
                                        <div class="col-lg-6 row">
                                            <div class="col-lg-3  d-flex align-items-center justify-content-center">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3 required ">관리자 이름</label>
                                            </div>
                                            <div class="col-lg-9 row">
                                                <div class="col-lg-8 form-label text-dark pt-3">
                                                    <input type="text"  class="form-control" id="adminName" name="adminName" placeholder="관리자명 입력" value="${adminAccount.adminName}" maxlength="20" required autocomplete="off">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-8">
                                    <div class="col-lg-12 row">
                                        <div class="col-lg-6 row">
                                            <div class="col-lg-3  d-flex align-items-center justify-content-center">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3 required">휴대폰 번호</label>
                                            </div>
                                            <div class="col-lg-9 row">
                                                <div class="col-lg-8 form-label text-dark pt-3">
                                                    <input type="text"  class="form-control" id="phoneNo" name="phoneNo" placeholder="휴대폰 번호 입력(-없이 10~11자리)" value="${adminAccount.getPhoneNoDecrypted()}" maxlength="11" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 row">
                                            <div class="col-lg-3 d-flex align-items-center">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3 required">이메일 주소</label>
                                            </div>
                                            <div class="col-lg-9 row">
                                                <div class="col-lg-8 form-label text-dark pt-3">
                                                    <input type="text"  class="form-control" id="email" name="email" placeholder="이메일 주소" value="${adminAccount.getEmailDecrypted()}" maxlength="30" required>
                                                </div>
                                                <div class="col-lg-4 form-label text-dark pt-3">
                                                    <button type="button" class="btn btn-primary" onclick="emailDuplicated()">중복확인</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-8">
                                    <div class="col-lg-12 row">
                                        <div class="col-lg-6 row">
                                            <div class="col-lg-3  d-flex align-items-center justify-content-center">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3 required">소속</label>
                                            </div>
                                            <div class="col-lg-9 row">
                                                <div class="col-lg-8 form-label text-dark pt-3">
                                                    <input type="text"  class="form-control" id="department" name="department" placeholder="소속 입력(ex. 팀명)" value="${adminAccount.department}" maxlength="20" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 row">
                                            <div class="col-lg-3  d-flex align-items-center justify-content-center">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3">직책/직무</label>
                                            </div>
                                            <div class="col-lg-9 row">
                                                <div class="col-lg-8 form-label text-dark pt-3">
                                                    <input type="text"  class="form-control" id="duty" name="duty" placeholder="직책/직무 입력(ex. 팀장)" value="${adminAccount.duty}" maxlength="20" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-8">
                                    <div class="col-lg-12 row">
                                        <div class="col-lg-6 row">
                                            <div class="col-lg-3  d-flex align-items-center justify-content-center">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3 required">관리자 그룹</label>
                                            </div>
                                            <div class="col-lg-9 row">
                                                <div class="col-lg-8">
                                                    <select
                                                        name="adminGroupIndex"
                                                        class="form-select form-select-solid"
                                                        data-control="select2"
                                                        data-hide-search="true"
                                                        data-placeholder="선택"
                                                        onchange="setAdminType(this)"
                                                    >
                                                        <option value="">선택</option>
                                                        <c:forEach var="adminGroup" items="${adminGroupList}">
                                                            <c:choose>
                                                                <c:when test='${currentAdminYn and adminAccount.adminType ne "S"}'>
                                                                    <c:if test="${adminAccount.adminGroupIndex eq adminGroup.adminGroupIndex}">
                                                                        <option data-admin-type="<c:if test='${adminGroup.isSuper eq "Y"}'>S</c:if><c:if test='${adminGroup.isSuper ne "Y"}'>G</c:if>" value="<c:out value='${adminGroup.adminGroupIndex}'/>" <c:if test="${adminAccount.adminGroupIndex eq adminGroup.adminGroupIndex}">selected</c:if>><c:out value="${adminGroup.groupName}"/></option>
                                                                    </c:if>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option data-admin-type="<c:if test='${adminGroup.isSuper eq "Y"}'>S</c:if><c:if test='${adminGroup.isSuper ne "Y"}'>G</c:if>" value="<c:out value='${adminGroup.adminGroupIndex}'/>" <c:if test="${adminAccount.adminGroupIndex eq adminGroup.adminGroupIndex}">selected</c:if>><c:out value="${adminGroup.groupName}"/></option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mb-6">
                                    <div class="col-lg-12 row">
                                        <div class="col-lg-6 row fv-plugins-icon-container" data-kt-password-meter="true">
                                            <div class="col-lg-3  d-flex align-items-center justify-content-center">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3 required">비밀번호</label>
                                            </div>
                                            <div class="col-lg-9">
                                                <div class="col-lg-12 form-label text-dark pt-3">
                                                    <div class="position-relative mb-3">
                                                        <input class="form-control" placeholder="비밀번호 입력(8~12자 영문/숫자/특수문자)" type="password" name="adminPw" id="adminPw" autocomplete="new-password" autocomplete="off" minlength="8" maxlength="20">
                                                        <span class="btn btn-sm btn-icon position-absolute translate-middle top-50 end-0 me-n2" data-kt-password-meter-control="visibility">
                                                            <i class="bi bi-eye-slash fs-2"></i>
                                                            <i class="bi bi-eye fs-2 d-none"></i>
                                                        </span>
                                                    </div>
                                                    <div class="d-flex align-items-center mb-3" data-kt-password-meter-control="highlight">
                                                        <div class="flex-grow-1 bg-secondary bg-active-success rounded h-5px me-2"></div>
                                                        <div class="flex-grow-1 bg-secondary bg-active-success rounded h-5px me-2"></div>
                                                        <div class="flex-grow-1 bg-secondary bg-active-success rounded h-5px me-2"></div>
                                                        <div class="flex-grow-1 bg-secondary bg-active-success rounded h-5px"></div>
                                                    </div>
                                                    <small class="form-text text-muted" data-target="adminPw">
                                                        * 안정도 3칸 이상 되어야 저장 가능합니다.
                                                    </small>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 row">
                                            <div class="col-lg-3  d-flex align-items-center">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3 required">비밀번호 확인</label>
                                            </div>
                                            <div class="col-lg-9">
                                                <div class="col-lg-12 form-label text-dark pt-9">
                                                    <div class="position-relative mb-3">
                                                        <input class="form-control" type="password" id="pwConfirm" placeholder="비밀번호 확인" name="pwConfirm" autocomplete="new-password" minlength="8" maxlength="20"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row mb-8">
                                    <div class="col-lg-12 row">
                                        <div class="col-lg-6 row">
                                            <div class="col-lg-3  d-flex align-items-center">
                                                <label class="col-lg-12 form-label fw-bolder text-dark pt-3">사용 여부</label>
                                            </div>
                                            <div class="col-lg-4 row">
                                                <div class="col">
                                                    <div class="form-check form-check-custom form-check-solid">
                                                        <input class="form-check-input" type="radio" name="isUse" value="N" ${adminAccount.isUse eq 'N' ? 'checked' : ''} id="isNField1" maxlength="1"/>
                                                        <label class="form-check-label form-label fw-bolder text-dark col-lg-12 pt-3" for="isNField1">
                                                            N
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="col">
                                                    <div class="form-check form-check-custom form-check-solid">
                                                        <input class="form-check-input" type="radio" name="isUse" value="Y" ${empty adminAccount || adminAccount.isUse eq 'Y' ? 'checked' : ''} id="isYField1" maxlength="1"/>
                                                        <label class="form-check-label form-label fw-bolder text-dark col-lg-12 pt-3" for="isYField1">
                                                            Y
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer" id="footerIdx">
                                <div class="row">
                                    <div id="listGroup" class="col-3">
                                    </div>
                                    <div class="col-9 d-flex justify-content-end">
                                        <button type="button" class="btn btn-light me-2" onclick="history.back()">취소</button>
                                        <c:choose>
                                            <c:when test="${not empty adminAccount}">
                                                <button type="button" class="btn btn-success" id="modifyBtn">
                                                저장
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="button" class="btn btn-success" id="saveBtn">
                                                저장
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
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
    let froalaKey = <%= froalaKey %>;

    $('.contents').froalaEditor({
        key: froalaKey,
        toolbarButtons: ['fullscreen', 'bold', 'italic', 'underline', 'strikeThrough', 'subscript', 'superscript', '|', 'fontFamily', 'fontSize', 'color', 'inlineClass', 'inlineStyle', 'paragraphStyle', 'lineHeight', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', 'quote', '-', 'insertLink', 'insertImage', 'insertTable', '|', 'specialCharacters', 'insertHR', 'selectAll', 'clearFormatting', '|', 'print', 'getPDF', 'spellChecker', 'help', 'html', '|', 'undo', 'redo'],
        imageUploadURL: '/froalaeditor/upload/Image',
        imageUploadParams: {editorUploadPath: '${editorUploadPath}'},
        imageDefaultWidth: 0,
        language: 'en',
        videoUpload: false,
        fileUpload: false,
        height: 300,
        requestHeaders: {
            'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').getAttribute('content')
        }
    });
    let isSubmitting = false;
    let isDuplicateAdminId = false;
    let isDuplicateEmail = false;
    let isModified = false;
    let isPasswordModified = false;

    const getPasswordStrength = (password) => {
        let strength = 0;

        if (password.length >= 8) strength++;
        if (/[a-zA-Z]/.test(password) && /\d/.test(password)) strength++;
        if (/[^a-zA-Z\d]/.test(password)) strength++;
        return strength;
    }

    // data 생성
    const makeData = () => {
        let params = {
            uid: document.getElementById('uid').value,
            adminId: document.getElementById('adminId').value,
            adminName: document.getElementById('adminName').value,
            phoneNo: document.getElementById('phoneNo').value,
            email: document.getElementById('email').value,
            department: document.getElementById('department').value,
            duty: document.getElementById('duty').value,
            adminGroupIndex: document.querySelector('[name="adminGroupIndex"]').value,
            adminType: document.getElementById('adminType').value,
            adminPw: document.getElementById('adminPw').value,
            isUse: document.querySelector('input[name="isUse"]:checked').value,
        };
        return params;
    };
    /*************************************** start: Valid ********************************************/
    // 유효성 체크 통합
    const regExp = {
        exp1: /^[^a-zA-Z0-9ㄱ-힣]*$/, // 한글, 영문, 숫자
        exp2: /^[^a-zA-Zㄱ-힣]*$/, // 한글, 영어
        exp3: /^[^a-zA-Z0-9]*$/, // 영문, 숫자
        exp4: /^[a-zA-Z0-9@.\-_+]*$/, // 영문, 숫자, 특수문자
        exp5: /^[0-9]+$/, // 전화번호, 숫자만
    }
    const validMessage = {
        adminId: {elem: null, msg: '* 아이디를 입력해주세요.'},
        adminName: {elem: null, msg: '* 이름을 입력해주세요.'},
        adminGroupIndex: {elem: null, msg: '* 관리자 그룹을 선택해주세요.'},
        department: {elem: null, msg: '* 소속을 입력해주세요.'},
        email: {elem: null, msg: '* 이메일 주소를 입력해주세요.'},
        phoneNo: {elem: null, msg: '* 휴대폰 번호를 입력해주세요.'},
        adminPw: {elem: null, msg: '* 비밀번호를 입력해주세요.'},
        pwConfirm: {elem: null, msg: '* 비밀번호가 일치하지 않습니다.'},
        duty: {elem: null, msg: ''},
    };
    // 에러메세지 제작
    const setMessage = (elem, isValid, color) => {
        const name = elem.name;
        // 에러 메세지 생성
        if (!isValid) {
            elem.disabled = true;
            if (!validMessage[name].elem) {
                validMessage[name].elem = document.createElement('small');
                validMessage[name].elem.style.color = color ? color : 'red';
                if (elem.name == 'adminPw') {
                    let target = document.querySelector('[data-target="adminPw"]');
                    target.classList.remove('text-muted');
                    target.style.color = color ? color : 'red';
                    target.innerText = validMessage[name].msg;
                } else if(name == 'adminGroupIndex') {
                    elem.parentNode.insertBefore(validMessage[name].elem, null);
                } else {
                    elem.parentNode.insertBefore(validMessage[name].elem, elem.nextSibling);
                }
                validMessage[name].elem.textContent = validMessage[name].msg;
            }
            elem.disabled = false;
            return
        }
        // 메세지 제거
        if (validMessage[name].elem) {
            validMessage[name].elem.remove();
            validMessage[name].elem = null;
        }
    }
    // input event
    const clearInputIfInvalid = (inputEvent) => {
        const elem = inputEvent.target
        const inputValue = elem.value
        setMessage(elem, true);
        // 중복확인 초기화
        if(elem.name === 'adminId') {
            elem.value = inputValue.replace(new RegExp(regExp.exp3, 'g'), '');
            isDuplicateAdminId = false;
        }
        if(elem.name === 'email') {
            elem.value = inputValue.replace(/[^a-zA-Z0-9@.\-_+]/g, '');
            isDuplicateEmail = false;
        }
        if(elem.name === 'adminGroupIndex' || elem.name === 'isUse') {
            isModified = false;
        }
        if (elem.name === 'adminName') {
            elem.value = inputValue.replace(/[^a-zA-Zㄱ-힣]/g, '');
            isModified = false;
        }
        if (elem.name === 'phoneNo') {
            elem.value = inputValue.replace(/[^0-9]/g, '');
            isModified = false;
        }
        if (elem.name === 'duty' || elem.name === 'department') {
            elem.value = inputValue.replace(/[^a-zA-Z0-9ㄱ-힣]/g, '');
            isModified = false;
        }
    }
    // 패스워드용
    const checkPasswordInvalid = (inputEvent) => {
        const elem = inputEvent.target
        const inputValue = elem.value
        isModified = false;
        isPasswordModified = false;
        elem.value = inputValue.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/, '');
        let pwStatus = document.querySelector('[data-kt-password-meter-control="highlight"]');
        let target = document.querySelector('[data-target="adminPw"]');
        if (!inputEvent.target.value) {
            target.classList.remove('text-muted');
            target.style.color = 'red';
            target.innerText ='* 비밀번호를 입력해주세요.';
        } else if (pwStatus.querySelectorAll('.active').length < 2) {
            target.classList.remove('text-muted');
            target.style.color = 'red';
            target.innerText ='* 안정도 3칸 이상 되어야 비밀번호 저장이 가능합니다.';
        } else {
            target.classList.remove('text-muted');
            target.style.color = '#009EF7';
            target.innerText ='* 사용 가능한 비밀번호 입니다.';
            validMessage.adminPw.msg = '* 사용 가능한 비밀번호 입니다.';
        }
    }

    let adminId = document.getElementById('adminId');
    let adminName = document.getElementById('adminName');
    let phoneNo = document.getElementById('phoneNo');
    let email = document.getElementById('email');
    let department =  document.getElementById('department');
    let duty = document.getElementById('duty');
    let adminPw = document.getElementById('adminPw');
    let pwConfirm = document.getElementById('pwConfirm');
    let isUse = document.querySelector('input[name="isUse"]:checked');
    let adminGroupIndex = document.querySelector('[name="adminGroupIndex"]')

    adminId.addEventListener('input', (evt) => { clearInputIfInvalid(evt);});
    adminName.addEventListener('input', (evt) => { clearInputIfInvalid(evt); });
    phoneNo.addEventListener('input', (evt) => { clearInputIfInvalid(evt); });
    email.addEventListener('input', (evt) => { clearInputIfInvalid(evt); });
    department.addEventListener('input', (evt) => { clearInputIfInvalid(evt); });
    duty.addEventListener('input', (evt) => { clearInputIfInvalid(evt); });
    adminPw.addEventListener('input', (evt) => { checkPasswordInvalid(evt) });
    pwConfirm.addEventListener('input', (evt) => { clearInputIfInvalid(evt) });
    isUse.addEventListener('input', (evt) => { clearInputIfInvalid(evt); });

    const validForm = async (type) => {
        let adminIdBol = true;
        let adminNameBol = true;
        let phoneNoBol = true;
        let emailBol = true;
        let departmentBol = true;
        let adminGroupIndexBol = true;
        let adminPwBol = true;
        let pwConfirmBol = true;
        let pwStatus = document.querySelector('[data-kt-password-meter-control="highlight"]');

        if (!adminId.value) {
            validMessage.adminId.msg = '* 아이디를 입력해주세요.';
            adminIdBol = false;
        } else if (adminId.value.length < 4) {
            validMessage.adminId.msg = '* 아이디는 4자 이상 입력해주세요.';
            adminIdBol = false;
        } else if (!isDuplicateAdminId) {
            validMessage.adminId.msg = '* 아이디 중복확인은 필수입니다.';
            adminIdBol = false;
        }
        if (!adminName.value) adminNameBol = false;
        if (!phoneNo.value) phoneNoBol = false;
        let emailValid = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!email.value) {
            validMessage.email.msg = '* 이메일 주소를 입력해주세요.';
            emailBol = false;
        } else if (!emailValid.test(String(email.value).toLowerCase())) {
            validMessage.email.msg = '* 올바른 이메일 주소를 입력해주세요.';
            emailBol = false;
        } else if (!isDuplicateEmail) {
            validMessage.email.msg = '* 이메일 주소 중복확인은 필수입니다.';
            emailBol = false;
        }
        if (!department.value) departmentBol = false;


        if (type == 'modify') { // 수정일시 비밀번호를 재입력하지 않아도 저장 가능하게끔 수정.
            if (!isPasswordModified ) {
                if (!adminPw.value) {
                    validMessage.adminPw.msg = '* 비밀번호를 입력해주세요.';
                    adminPwBol = false;
                } else if (pwStatus.querySelectorAll('.active').length < 2) {
                    target.classList.remove('text-muted');
                    target.style.color = 'red';
                    target.innerText ='* 안정도 3칸 이상 되어야 비밀번호 저장이 가능합니다.';
                }
            }
        } else {
            if (!adminPw.value) {
                validMessage.adminPw.msg = '* 비밀번호를 입력해주세요.';
                adminPwBol = false;
            } else if (pwStatus.querySelectorAll('.active').length < 2) {
                target.classList.remove('text-muted');
                target.style.color = 'red';
                target.innerText ='* 안정도 3칸 이상 되어야 비밀번호 저장이 가능합니다.';
            }
        }
        if (pwConfirm.value != adminPw.value) pwConfirmBol = false;
        if (!adminGroupIndex.value) adminGroupIndexBol = false;
        // 메세지 세팅
        setMessage(adminId, adminIdBol);
        setMessage(adminName, adminNameBol);
        setMessage(phoneNo, phoneNoBol);
        setMessage(email, emailBol);
        setMessage(department, departmentBol);
        setMessage(adminPw, adminPwBol);
        setMessage(pwConfirm, pwConfirmBol);
        setMessage(adminGroupIndex, adminGroupIndexBol);

        return adminIdBol && adminNameBol && phoneNoBol
            && emailBol && departmentBol && adminPwBol
            && pwConfirmBol && adminGroupIndexBol;
    }

    // 관리자 타입 설정
    const setAdminType = (event) => {
        setMessage(adminGroupIndex, true);
        isModified = true;

        const elem = document.getElementById('adminType');
        const adminType = event.selectedOptions[0].dataset.adminType;
        elem.value = adminType
    }
    // 저장
    const saveAdminAccount = async () => {
        if (isSubmitting) {
            return;
        }
        isSubmitting = true;

        // 전송 데이터 생성
        let data = makeData();
        if(!await validForm()) {
            isSubmitting = false;
            return false;
        }

        try {
            const response = await fetch('/api/v1/site/admin-account', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data)
            });

            const res = await response.json();

            if (res) {
                if(!res.uid) {
                    Swal.fire({
                        title: '등록에 실패하였습니다.',
                        icon: 'error',
                        confirmButtonText: '확인'
                    });
                    return false;
                }
                Swal.fire({
                    title: '등록',
                    text: '등록 되었습니다.',
                    icon: 'success',
                    confirmButtonText: '확인'
                }).then((result) => {
                    if (result.isConfirmed) {
                        isModified = false;
                        location.href = '/site/admin-account';
                    }
                })
            }
        } catch (Exception) {
            console.error(Exception);
        } finally {
            isSubmitting = false;
        }
    }
    const modify = async () => {
        if (isSubmitting) {
            return;
        }
        isSubmitting = true;

        // 전송 데이터 생성
        let data = makeData();

        if(!await validForm('modify')) {
            isSubmitting = false;
            return false;
        }

        try {
            let uid = document.getElementById('uid').value;
            const response = await fetch('/api/v1/site/admin-account/update/' + uid, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data)
            });

            const res = await response.json();

            if (res) {
                if (!res.result) {
                    Swal.fire({
                        title: '',
                        text: '잠시 후 시도해주세요.',
                        icon: 'error',
                        confirmButtonText: '확인'
                    })
                }
                Swal.fire({
                    title: '수정 완료',
                    text: '수정이 완료되었습니다.',
                    icon: 'success',
                    confirmButtonText: '확인'
                }).then((result) => {
                    // 스스로 변경시 분기 처리
                    let check = document.getElementById('currentAdminYn').value === 'true';
                    if (result.isConfirmed && res.uid) {
                        isModified = false;
                        location.href = check ? '/logout' : '/site/admin-account/' + res.uid;
                    }
                })
            }
        } catch (Exception) {
            console.error(Exception);
        } finally {
            isSubmitting = false;
        }
    }
    document.addEventListener('DOMContentLoaded', function () {
        const targetNode = document.querySelectorAll('.form-select');
        targetNode.forEach((node) => {
            node.style.backgroundColor = '#ffffff';
            node.style.backgroundClip = 'padding-box';
            node.style.border = '1px solid #E4E6EF';
        });
        if(document.getElementById('idx').value) {
            isDuplicateAdminId = true;
            isDuplicateEmail = true;
            isPasswordModified = true;
            document.getElementById('modifyBtn').addEventListener('click', () => modify());
        } else {
            document.getElementById('saveBtn').addEventListener('click', () => saveAdminAccount());
        }
    });
    window.onbeforeunload = function() {
        if (isModified) {
            return "변경 사항을 저장하지 않았습니다. 정말 지금 나가시겠습니까?";
        }
    };
    // 중복확인
    const idDuplicated = async () => {
        if (isSubmitting) {
            return;
        }

        isDuplicateAdminId = false;
        let adminId = document.getElementById('adminId');
        setMessage(adminId, true); // 임시 초기화처리
        if(!adminId.value.trim()) {
            validMessage.adminId.msg = '* 아이디를 입력해주세요.';
            setMessage(adminId, false);
            return false;
        } else if (adminId.value.trim().length < 4) {
            validMessage.adminId.msg = '* 아이디는 4자 이상 입력해주세요.';
            setMessage(adminId, false);
            return false;
        }

        try {
            const response = await fetch('/api/v1/site/admin-account/check-duplicate-admin-id?adminId=' + adminId.value, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            const result = await response.json();

            if (result.data) {
                validMessage.adminId.msg = '* 이미 사용중인 아이디입니다. 다른 아이디를 입력하시기 바랍니다.';
                setMessage(adminId, false);
            } else {
                isDuplicateAdminId = true;
                validMessage.adminId.msg = '* 사용 가능한 아이디입니다.';
                setMessage(adminId, false, '#009EF7');
            }
        } catch (Exception) {
            console.error(Exception);
        } finally {
            isSubmitting = false;
        }
    }

    const emailDuplicated = async () => {
        if (isSubmitting) {
            return;
        }
        isDuplicateEmail = false;
        const emailVal = email.value.trim();
        let emailValid = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        setMessage(email, true); // 임시 초기화처리
        if(!emailVal) {
            validMessage.email.msg = '* 이메일 주소를 입력해주세요.';
            setMessage(email, false);
            return false;
        } else if (!emailValid.test(String(emailVal).toLowerCase())) {
            validMessage.email.msg = '* 올바른 이메일 주소를 입력해주세요.';
            setMessage(email, false);
            return false;
        }
        try {
            const response = await fetch('/api/v1/site/admin-account/check-duplicate-email?email=' + emailVal, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            const result = await response.json();
            if (result.data) {
                validMessage.email.msg = '* 이미 사용중인 이메일 주소입니다. 다른 이메일 주소를 입력하시기 바랍니다.';
                setMessage(email, false);
            } else {
                isDuplicateEmail = true;
                validMessage.email.msg = '* 사용 가능한 이메일입니다.';
                setMessage(email, false, '#009EF7');
            }
        } catch (Exception) {
            console.error(Exception);
        } finally {
            isSubmitting = false;
        }
    }
</script>

<style>
    @keyframes slide-in {
        from {
            transform: translateX(-100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }

    .slide-in-animation {
        animation: slide-in 0.5s forwards;
    }

</style>