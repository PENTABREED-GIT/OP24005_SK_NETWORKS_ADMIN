package com.skn.admin.customer.controller;

import com.skn.admin.base.etc.Page;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.customer.dto.Inquiry;
import com.skn.admin.customer.dto.request.InquirySearchParam;
import com.skn.admin.customer.service.InquiryService;
import com.skn.admin.environment.dto.Admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static com.skn.admin.base.etc.Page.PAGE_BLOCK_SIZE;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final InquiryService service;


    /**
     * 고객문의 - 목록
     * @param _searchParam
     * @param currentPage
     * @param pageListSize
     * @param model
     * @return
     */
    @Operation("고객문의 목록")
    @GetMapping({"/inquiry-list"})
    public String inquiryList(@ModelAttribute InquirySearchParam _searchParam,
            @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
            Model model)
    {
        // 검색 조건에 따른 전체 레코드 수 조회
        int totalCount = service.selectInquiryListCount(_searchParam);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        // 검색 파라미터를 페이지 객체와 함께 생성
        InquirySearchParam searchParam = InquirySearchParam.of(_searchParam, page);

        // 목록 데이터를 뷰에 넘김
        model.addAttribute("statusList", Arrays.asList(Inquiry.Status.values()));
        model.addAttribute("inquiryList", service.selectInquiryList(searchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("searchParam", _searchParam);

        return "/customer/inquiry-list";
    }

    /**
     * 고객문의 - 답변 등록 폼
     * @param model
     * @return
     */
    @Operation("고객문의 답변 등록 폼")
    @GetMapping({"/answer-reg-form/{uid}"})
    public String answerRegForm(Model model, @PathVariable String uid) {
        model.addAttribute("statusList", Arrays.asList(Inquiry.Status.values()));
        model.addAttribute("uid", uid);
        model.addAttribute("viewType", "");
        model.addAttribute("detail", service.selectInquiry(uid));
        model.addAttribute("answerList", service.selectInquiryAnswer(uid));
        return "/customer/answer-form";
    }

    /**
     * 고객문의 - 수정 폼
     * @param model
     * @return
     */
    @GetMapping({"/inquiry-mod-form/{uid}"})
    public String inquiryModForm(Model model, @PathVariable String uid) {
        Inquiry detail = service.selectInquiry(uid);
        if(detail == null)
            return "/404";
        model.addAttribute("statusList", Arrays.asList(Inquiry.Status.values()));
        model.addAttribute("detail", detail);
        model.addAttribute("answerList", service.selectInquiryAnswer(uid));
        return "/customer/inquiry-form";
    }

    /**
     * 고객문의 - 상세
     * @param model
     * @return
     */
    @Operation("고객문의 상세")
    @GetMapping({"/inquiry/{uid}"})
    public String inquiryView(Model model, @PathVariable String uid, Authentication authentication) {
        Inquiry detail = service.selectInquiry(uid);
        if(detail == null)
            return "/404";
        model.addAttribute("detail", detail);
        model.addAttribute("answerList", service.selectInquiryAnswer(uid));
        if ("READY".equals(detail.getStatus())) {
            Admin admin = (Admin) authentication.getPrincipal();
            detail.setAdminId(admin.getAdminId());
            detail.setAdminIndex(admin.getAdminIndex());
            detail.setAdminName(admin.getAdminName());
            detail.setStatus("ING");
            service.updateInquiryStatus(detail);
        }

        return "/customer/inquiry-view";
    }
}
