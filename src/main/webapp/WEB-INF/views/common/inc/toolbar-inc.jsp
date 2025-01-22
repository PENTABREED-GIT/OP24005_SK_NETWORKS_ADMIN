<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--begin::Toolbar-->
<div class="toolbar" id="kt_toolbar">
    <!--begin::Container-->
    <div id="kt_toolbar_container" class="container-fluid d-flex flex-stack">
        <!--begin::Page title-->
        <div data-kt-place="true" data-kt-place-mode="prepend" data-kt-place-parent="{default: '#kt_content_container', 'lg': '#kt_toolbar_container'}" class="page-title d-flex align-items-center me-3">
            <!--begin::Title-->
            <h1 class="d-flex align-items-center text-dark fw-bolder my-1" id="templeatPageTitle" style="font-size: 1.5rem;">${PAGE_INFO.menuName}</h1>

            <!--end::Title-->
            <!--begin::Separator-->
            <span class="h-20px border-gray-200 border-start mx-4"></span>
            <!--end::Separator-->
            <!--begin::Breadcrumb-->
            <ul class="breadcrumb breadcrumb-separatorless fw-bold fs-7 my-1" id="toolbar_breadcrumb">
                <!--begin::Item-->
                <li class="breadcrumb-item text-muted">
                    <a href="/" class="text-muted text-hover-warning">Home</a>
                </li>

                <li class="breadcrumb-item">
                    <span class="menu-separator">></span>
                </li>

                <li class="breadcrumb-item" >
                    <span>${PAGE_INFO.location}</span>
                </li>
            </ul>

        </div>
        <!--end::Page title-->
    </div>
    <!--end::Container-->
</div>
<!--end::Toolbar-->

<style>

</style>