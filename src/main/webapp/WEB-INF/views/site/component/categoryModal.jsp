<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">

<body>
<div class="modal-content">
    <input type="hidden" name="categoryIndex" id="categoryIndex" value="${list.categoryIndex}"/>
    <input type="hidden" name="parentCategoryIndex" id="parentCategoryIndex" value="${parentCategoryIndex}"/>
    <input type="hidden" name="depth" id="depth" value="${depth}" />
    <div class="modal-header" id="modal_new_menu_header">
        <c:if test="${empty list.categoryIndex}">
            <h2>카테고리 추가</h2>
        </c:if>
        <c:if test="${not empty list.categoryIndex}">
            <h2>카테고리 수정</h2>
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
                    카테고리명
                </label>
                <input
                        class="form-control form-control-solid"
                        placeholder=""
                        id="categoryName"
                        name="categoryName"
                        maxlength="30"
                        type="text"
                        value="${list.categoryName}"
                        oninput="clearInputIfInvalid(event)"
                />
            </div>
            <div class="d-flex flex-column mb-5 fv-row">
                <label class="fs-5 fw-bold mb-2">
                    카테고리 설명
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
            <div class="row g-9 mb-5">
                <div class="col-md-6 fv-row">
                    <label class="required fs-5 fw-bold mb-2">
                        카테고리 코드
                    </label>
                    <input
                            class="form-control form-control-solid"
                            placeholder=""
                            id="categoryCode"
                            name="categoryCode"
                            maxlength="50"
                            type="text"
                            value="${list.categoryCode}"
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
                            type="number"
                            class="form-control form-control-solid"
                            placeholder=""
                            id="sortOrder"
                            name="sortOrder"
                            max="10000"
                            maxlength="5"
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
                                name="isOpen"
                                id="isOpen"
                                value="Y"
                                type="checkbox"
                        ${list.isOpen eq 'Y' ? 'checked' : ''}
                        />
                    </label>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer d-flex justify-content-end">
        <c:if test="${empty list.categoryIndex}">
            <button
                    type="button"
                    id="modal_new_menu_submit"
                    class="btn btn-m btn-warning"
                    onclick="saveCategory();"
            >

                <span class="indicator-label">저장</span>
                <span class="indicator-progress">저장중...
                                    <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                                </span>
            </button>
        </c:if>
        <c:if test="${not empty list.categoryIndex}">
            <button
                    data-category-index="${list.categoryIndex}"
                    type="button"
                    id="modal_new_menu_submit"
                    class="btn btn-warning"
                    onclick="updateCategory(this);"
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
