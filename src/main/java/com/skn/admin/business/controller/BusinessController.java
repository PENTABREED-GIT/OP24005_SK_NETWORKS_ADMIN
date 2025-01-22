package com.skn.admin.business.controller;

import com.skn.admin.base.etc.Page;
import com.skn.admin.business.dto.BusinessArea;
import com.skn.admin.business.dto.BusinessContents;
import com.skn.admin.business.dto.BusinessContentsDetail;
import com.skn.admin.business.dto.request.BusinessContentsSearchParam;
import com.skn.admin.business.service.BusinessAreaService;
import com.skn.admin.business.service.BusinessContentsService;
import com.skn.admin.config.annotation.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.skn.admin.base.etc.Page.PAGE_BLOCK_SIZE;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessAreaService areaService;
    private final BusinessContentsService service;


    /**
     * 사업별 콘텐츠 - 목록
     * @param _searchParam
     * @param currentPage
     * @param pageListSize
     * @param model
     * @return
     */
    @Operation("사업별 콘텐츠 목록")
    @GetMapping({"/business/business-contents-list"})
    public String businessContentsList(@ModelAttribute BusinessContentsSearchParam _searchParam,
            @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
            Model model)
    {
        // 검색 조건에 따른 전체 레코드 수 조회
        int totalCount = service.selectBusinessContentsListCount(_searchParam);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        // 검색 파라미터를 페이지 객체와 함께 생성
        BusinessContentsSearchParam searchParam = BusinessContentsSearchParam.of(_searchParam, page);

        // 목록 데이터를 뷰에 넘김
        model.addAttribute("businessAreaList", areaService.selectBusinessAreaList());
        model.addAttribute("list", service.selectBusinessContentsList(searchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("searchParam", _searchParam);

        return "/business/business-contents-list";
    }

    /**
     * 사업별 콘텐츠 - 등록 폼
     * @param model
     * @return
     */
    @Operation("사업별 콘텐츠 등록 폼")
    @GetMapping({"/business/business-contents-reg-form"})
    public String businessContentsRegForm(Model model) {
        model.addAttribute("uid", RandomStringUtils.randomAlphanumeric(16));
        List<String> addUidList = new ArrayList<String>();
        for(int i=0; i<4; i++)
            addUidList.add(RandomStringUtils.randomAlphanumeric(16));
        model.addAttribute("addUidList", addUidList);
        model.addAttribute("businessAreaList", areaService.selectBusinessAreaList());
        model.addAttribute("viewType", "");
        return "/business/business-contents-form";
    }

    /**
     * 사업별 콘텐츠 - 수정 폼
     * @param model
     * @return
     */
    @Operation("사업별 콘텐츠 수정 폼")
    @GetMapping({"/business/business-contents-mod-form/{uid}"})
    public String businessContentsModForm(Model model, @PathVariable String uid) {
        BusinessContents detail = service.selectBusinessContents(uid);
        model.addAttribute("detail", detail);
        model.addAttribute("businessAreaList", areaService.selectBusinessAreaList());
        List<BusinessContentsDetail> addList = service.selectBusinessContentsDetail(detail.getBusinessContentsIndex().toString());
        for(int i=addList.size(); i<4; i++) {
            BusinessContentsDetail add = new BusinessContentsDetail();
            add.setUid(RandomStringUtils.randomAlphanumeric(16));
            addList.add(add);
        }
        model.addAttribute("addList", addList);
        return "/business/business-contents-form";
    }

    /**
     * 사업별 콘텐츠 - 상세
     * @param model
     * @return
     */
    @Operation("사업별 콘텐츠 상세")
    @GetMapping({"/business/business-contents/{uid}"})
    public String businessContentsView(Model model, @PathVariable String uid) {
        BusinessContents detail = service.selectBusinessContents(uid);
        model.addAttribute("detail", detail);
        model.addAttribute("addList", service.selectBusinessContentsDetail(detail.getBusinessContentsIndex().toString()));
        return "/business/business-contents-view";
    }
}
