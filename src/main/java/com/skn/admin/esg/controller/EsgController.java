package com.skn.admin.esg.controller;

import com.skn.admin.base.etc.Page;
import com.skn.admin.company.dto.GlobalNetwork;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.esg.dto.ReportsAndPolicies;
import com.skn.admin.esg.dto.request.ReportsAndPoliciesSearchParam;
import com.skn.admin.esg.service.ReportsAndPoliciesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import static com.skn.admin.base.etc.Page.PAGE_BLOCK_SIZE;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EsgController {
    private final ReportsAndPoliciesService service;


    /**
     * 보고서 및 정책 - 목록
     * @param _searchParam
     * @param currentPage
     * @param pageListSize
     * @param model
     * @return
     */
    @Operation("보고서 및 정책 목록")
    @GetMapping({"/esg/reports-and-policies-list"})
    public String reportsAndPoliciesList(@ModelAttribute ReportsAndPoliciesSearchParam _searchParam,
            @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
            Model model)
    {
        // 검색 조건에 따른 전체 레코드 수 조회
        int totalCount = service.selectReportsAndPoliciesListCount(_searchParam);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        // 검색 파라미터를 페이지 객체와 함께 생성
        ReportsAndPoliciesSearchParam searchParam = ReportsAndPoliciesSearchParam.of(_searchParam, page);

        // 목록 데이터를 뷰에 넘김
        model.addAttribute("list", service.selectReportsAndPoliciesList(searchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("searchParam", _searchParam);

        return "/esg/reports-and-policies-list";
    }

    /**
     * 보고서 및 정책 - 등록 폼
     * @param model
     * @return
     */
    @Operation("보고서 및 정책 등록 폼")
    @GetMapping({"/esg/reports-and-policies-reg-form"})
    public String reportsAndPoliciesRegForm(Model model) {
        model.addAttribute("uid", RandomStringUtils.randomAlphanumeric(16));
        model.addAttribute("viewType", "");
        return "/esg/reports-and-policies-form";
    }

    /**
     * 보고서 및 정책 - 수정 폼
     * @param model
     * @return
     */
    @Operation("보고서 및 정책 수정 폼")
    @GetMapping({"/esg/reports-and-policies-mod-form/{uid}"})
    public String reportsAndPoliciesModForm(Model model, @PathVariable String uid) {
        ReportsAndPolicies detail = service.selectReportsAndPolicies(uid);
        if(detail == null)
            return "/404";
        model.addAttribute("detail", detail);
        return "/esg/reports-and-policies-form";
    }

    /**
     * 보고서 및 정책 - 상세
     * @param model
     * @return
     */
    @Operation("보고서 및 정책 상세")
    @GetMapping({"/esg/reports-and-policies/{uid}"})
    public String reportsAndPoliciesView(Model model, @PathVariable String uid) {
        ReportsAndPolicies detail = service.selectReportsAndPolicies(uid);
        if(detail == null)
            return "/404";
        model.addAttribute("detail", detail);
        return "/esg/reports-and-policies-view";
    }
}
