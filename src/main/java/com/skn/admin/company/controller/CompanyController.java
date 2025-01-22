package com.skn.admin.company.controller;

import com.skn.admin.base.etc.Page;
import com.skn.admin.company.dto.GlobalNetwork;
import com.skn.admin.company.dto.request.GlobalNetworkSearchParam;
import com.skn.admin.company.service.CompanyService;
import com.skn.admin.config.annotation.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static com.skn.admin.base.etc.Page.PAGE_BLOCK_SIZE;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService service;


    /**
     * 글로벌 네트워크 - 목록
     * @param _searchParam
     * @param currentPage
     * @param pageListSize
     * @param model
     * @return
     */
    @Operation("글로벌 네트워크 목록")
    @GetMapping({"/company/global-network-list"})
    public String globalNetworkList(@ModelAttribute GlobalNetworkSearchParam _searchParam,
            @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
            Model model)
    {
        // 검색 조건에 따른 전체 레코드 수 조회
        int totalCount = service.selectGlobalNetworkListCount(_searchParam);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        // 검색 파라미터를 페이지 객체와 함께 생성
        GlobalNetworkSearchParam searchParam = GlobalNetworkSearchParam.of(_searchParam, page);

        // 목록 데이터를 뷰에 넘김
        model.addAttribute("regionList", Arrays.asList(GlobalNetwork.Region.values()));
        model.addAttribute("list", service.selectGlobalNetworkList(searchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("searchParam", _searchParam);

        return "/company/global-network-list";
    }

    /**
     * 글로벌 네트워크 - 등록 폼
     * @param model
     * @return
     */
    @Operation("글로벌 네트워크 등록 폼")
    @GetMapping({"/company/global-network-reg-form"})
    public String globalNetworkRegForm(Model model) {
        model.addAttribute("uid", RandomStringUtils.randomAlphanumeric(16));
        model.addAttribute("regionList", Arrays.asList(GlobalNetwork.Region.values()));
        return "/company/global-network-form";
    }

    /**
     * 글로벌 네트워크 - 수정 폼
     * @param model
     * @return
     */
    @Operation("글로벌 네트워크 수정 폼")
    @GetMapping({"/company/global-network-mod-form/{uid}"})
    public String globalNetworkModForm(Model model, @PathVariable String uid) {
        GlobalNetwork detail = service.selectGlobalNetwork(uid);
        if(detail == null)
            return "/404";
        model.addAttribute("detail", detail);
        model.addAttribute("regionList", Arrays.asList(GlobalNetwork.Region.values()));
        return "/company/global-network-form";
    }

    /**
     * 글로벌 네트워크 - 상세
     * @param model
     * @return
     */
    @Operation("글로벌 네트워크 상세")
    @GetMapping({"/company/global-network/{uid}"})
    public String globalNetworkView(Model model, @PathVariable String uid) {
        GlobalNetwork detail = service.selectGlobalNetwork(uid);
        if(detail == null)
            return "/404";
        model.addAttribute("detail", detail);
        return "/company/global-network-view";
    }
}
