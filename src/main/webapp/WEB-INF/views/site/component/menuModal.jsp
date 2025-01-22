<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<body>
<div class="modal-content">
    <input type="hidden" name="menuIndex" id="menuIndex" value="${menuIndex}"/>
    <input type="hidden" name="rootIndex" id="rootIndex" value="${rootIndex}"/>
    <input type="hidden" name="parentIndex" id="parentIndex" value="${parentIndex}"/>
    <input type="hidden" name="depth" id="depth" value="${depth}" />
    <div class="modal-header" id="modal_new_menu_header">
        <c:if test="${empty list.uid}">
            <h2>메뉴 추가</h2>
        </c:if>
        <c:if test="${not empty list.uid}">
            <h2>메뉴 수정</h2>
        </c:if>
        <div class="btn btn-sm btn-icon btn-active-color-primary" data-bs-dismiss="modal">
            <i class="bi bi-x" style="font-size: 16px;"></i>
        </div>
    </div>
    <div class="modal-body py-10 px-lg-17">
        <div
                class="scroll-y me-n7 pe-7"
                id="modal_new_menu_scroll"
                data-kt-scroll="true"
                data-kt-scroll-activate="{default: false, lg: true}"
                data-kt-scroll-max-height="auto"
                data-kt-scroll-dependencies="#modal_new_menu_header"
                data-kt-scroll-wrappers="#modal_new_menu_scroll"
                data-kt-scroll-offset="300px">
            <div class="d-flex flex-column mb-5 fv-row">
                <label class="required fs-5 fw-bold mb-2">
                    메뉴명
                </label>
                <input
                        class="form-control form-control-solid"
                        placeholder=""
                        id="menuName"
                        name="menuName"
                        maxlength="30"
                        type="text"
                        value="${list.menuName}"
                        oninput="clearInputIfInvalid(event)"
                />
            </div>
            <div class="d-flex flex-column mb-5 fv-row">
                <label class="fs-5 fw-bold mb-2">
                    메뉴설명
                </label>
                <input
                        class="form-control form-control-solid"
                        placeholder=""
                        id="description"
                        name="description"
                        maxlength="100"
                        type="text"
                        value="${list.description}"
                />
            </div>
            <div class="d-flex flex-column mb-5 fv-row">
                <label class="required fs-5 fw-bold mb-2">
                    URL
                </label>
                <input
                        class="form-control form-control-solid"
                        placeholder=""
                        id="url"
                        name="url"
                        maxlength="180"
                        type="text"
                        value="${list.url}"
                        oninput="clearInputIfInvalid(event)"
                />
            </div>
            <div class="d-flex flex-column mb-5 fv-row">
                <label class="required fs-5 fw-bold mb-2">
                    포함 URL
                </label>
                <textarea class="form-control form-control-solid"
                          rows="5"
                          id="urlString"
                          name="urlString"
                          placeholder="separator ;"
                          maxlength="4000"
                          oninput="clearInputIfInvalid(event)"
                          >${list.urlString}</textarea>
            </div>
            <div class="row g-9 mb-5">
                <div class="col-md-6 fv-row">
                    <label class="required fs-5 fw-bold mb-2">
                        메뉴 코드
                    </label>
                    <input
                            class="form-control form-control-solid"
                            placeholder=""
                            id="menuCode"
                            name="menuCode"
                            maxlength="50"
                            type="text"
                            value="${list.menuCode}"
                            oninput="clearInputIfInvalid(event)"
                    />
                </div>
                <div class="col-md-6 fv-row">
                    <label
                            class="required fs-5 fw-bold mb-2"
                            for="sortOrder">
                        정렬 순서
                    </label>
                    <input
                            type="text"
                            class="form-control form-control-solid"
                            placeholder=""
                            id="sortOrder"
                            name="sortOrder"
                            max="999"
                            maxlength="3"
                            value="${list.sortOrder}"
                            oninput="clearInputIfInvalid(event)"
                    />
                </div>
            </div>
            <div class="row g-9 mb-5">
                <div class="col-md-6 fv-row">
                    <label class="fs-5 fw-bold mb-2">
                        노출여부
                    </label>
                    <label class="form-check form-switch form-check-custom form-check-warning form-check-solid">
                        <input
                                class="form-check-input"
                                name="isDisplay"
                                id="isDisplay"
                                value="Y"
                                type="checkbox"
                        ${list.isDisplay eq 'Y' ? 'checked' : ''}
                        />
                    </label>
                </div>
                <div class="col-md-6 fv-row">
                    <label
                            class="fs-5 fw-bold mb-2"
                            for="icon"
                            data-bs-toggle="popover"
                            data-bs-placement="top"
                            title="Enter the name of the icon"
                            data-bs-custom-class="popover-dark"
                            data-bs-trigger="hover"
                            data-bs-content="This is the name of the icon on the left menu. Click to see the icon name."
                            data-bs-html="true" onclick="window.open('https://icons8.com/line-awesome');">
                        아이콘
                    </label>
                    <label class="form-check form-switch form-check-custom form-check-solid">
                        <input
                                type="text"
                                class="form-control form-control-solid"
                                placeholder=""
                                name="icon"
                                id="icon"
                                maxlength="50"
                                value="${list.icon}"
                                oninput="clearInputIfInvalid(event)"
                        />
                    </label>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer flex-center">
        <button
                type="button"
                id="modal_new_menu_cancel"
                class="btn btn-light me-3"
                <c:if test="${not empty list.uid}">
                    onclick="closePutModal()"
                </c:if>
                <c:if test="${empty list.uid}">
                    onclick="closePostModal()"
                </c:if>
                >
            취소
        </button>
        <c:if test="${empty list.uid}">
            <button
                    type="button"
                    id="modal_new_menu_submit"
                    class="btn btn-warning"
                    onclick="saveMenu();"
            >

                <span class="indicator-label">저장</span>
                <span class="indicator-progress">저장중...
                                    <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                                </span>
            </button>
        </c:if>
        <c:if test="${not empty list.uid}">
            <button
                    data-uid="${list.uid}"
                    type="button"
                    id="modal_new_menu_submit"
                    class="btn btn-warning"
                    onclick="updateMenu(this);"
            >
                <span class="indicator-label">수정</span>
                <span class="indicator-progress">수정중...
            <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
            </span>
            </button>
        </c:if>

    </div>
</div>
</body>
</html>
