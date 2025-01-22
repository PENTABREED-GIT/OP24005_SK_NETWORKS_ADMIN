package com.skn.admin.newsroom.controller;

import com.skn.admin.base.etc.Page;
import com.skn.admin.business.service.BusinessAreaService;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.newsroom.dto.Press;
import com.skn.admin.newsroom.dto.Video;
import com.skn.admin.newsroom.dto.request.PressSearchParam;
import com.skn.admin.newsroom.dto.request.VideoSearchParam;
import com.skn.admin.newsroom.service.PressService;
import com.skn.admin.newsroom.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.ResourceBundle;

import static com.skn.admin.base.etc.Page.PAGE_BLOCK_SIZE;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NewsRoomController {
    private final BusinessAreaService businessAreaService;
    private final VideoService videoService;
        /**
     * 영상 라이브러리- 목록
     * @param _searchParam
     * @param currentPage
     * @param pageListSize
     * @param model
     * @return
     */
    @Operation("영상 라이브러리 목록")
    @GetMapping({"/news-room/video-list"})
    public String videoList(@ModelAttribute VideoSearchParam _searchParam,
            @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
            Model model)
    {
        // 검색 조건에 따른 전체 레코드 수 조회
        int totalCount = videoService.selectVideoListCount(_searchParam);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        // 검색 파라미터를 페이지 객체와 함께 생성
        VideoSearchParam searchParam = VideoSearchParam.of(_searchParam, page);

        // 목록 데이터를 뷰에 넘김
        model.addAttribute("brandList", Arrays.asList(Video.Brand.values()));
        model.addAttribute("list", videoService.selectVideoList(searchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("searchParam", _searchParam);

        return "/news-room/video-list";
    }

    /**
     * 영상 라이브러리 - 등록 폼
     * @param model
     * @return
     */
    @Operation("영상 라이브러리 등록 폼")
    @GetMapping({"/news-room/video-reg-form"})
    public String videoRegForm(Model model, String classification) {
        model.addAttribute("brandList", Arrays.asList(Video.Brand.values()));
        model.addAttribute("uid", RandomStringUtils.randomAlphanumeric(16));
        model.addAttribute("viewType", "");
        return "/news-room/video-form";
    }

    /**
     * 영상 라이브러리 - 수정 폼
     * @param model
     * @return
     */
    @Operation("영상 라이브러리 수정 폼")
    @GetMapping({"/news-room/video-mod-form/{uid}"})
    public String videoModForm(Model model, @PathVariable String uid) {
        Video detail = videoService.selectVideo(uid);
        if(detail == null)
            return "/404";
        model.addAttribute("brandList", Arrays.asList(Video.Brand.values()));
        model.addAttribute("detail", detail);

        return "/news-room/video-form";
    }

    /**
     * 영상 라이브러리 - 상세
     * @param model
     * @return
     */
    @Operation("영상 라이브러리 상세")
    @GetMapping({"/news-room/video/{uid}"})
    public String videoView(Model model, @PathVariable String uid) {
        Video detail = videoService.selectVideo(uid);
        if(detail == null)
            return "/404";

        model.addAttribute("detail", detail);

        return "/news-room/video-view";
    }

    /**************************************************************************************************/
    private final PressService pressService;
        /**
     * 프레스 센터- 목록
     * @param _searchParam
     * @param currentPage
     * @param pageListSize
     * @param model
     * @return
     */
    @Operation("프레스 센터 목록")
    @GetMapping({"/news-room/press-list"})
    public String pressList(@ModelAttribute PressSearchParam _searchParam,
            @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
            Model model)
    {
        // 검색 조건에 따른 전체 레코드 수 조회
        int totalCount = pressService.selectPressListCount(_searchParam);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        // 검색 파라미터를 페이지 객체와 함께 생성
        PressSearchParam searchParam = PressSearchParam.of(_searchParam, page);

        // 목록 데이터를 뷰에 넘김
        model.addAttribute("businessAreaList", businessAreaService.selectBusinessAreaList());
        model.addAttribute("list", pressService.selectPressList(searchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("searchParam", _searchParam);

        return "/news-room/press-list";
    }

    /**
     * 프레스 센터 - 등록 폼
     * @param model
     * @return
     */
    @Operation("프레스 센터 등록 폼")
    @GetMapping({"/news-room/press-reg-form"})
    public String pressRegForm(Model model) {
        model.addAttribute("businessAreaList", businessAreaService.selectBusinessAreaList());

        String uid = RandomStringUtils.randomAlphanumeric(16);
        model.addAttribute("uid", uid);
        model.addAttribute("viewType", "");

        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
        model.addAttribute("froalaKey", resourceBundle.getString("froala.key"));
        model.addAttribute("editorUploadPath", "/press/"+uid+"/editor");
        return "/news-room/press-form";
    }

    /**
     * 프레스 센터 - 수정 폼
     * @param model
     * @return
     */
    @Operation("프레스 센터 수정 폼")
    @GetMapping({"/news-room/press-mod-form/{uid}"})
    public String pressModForm(Model model, @PathVariable String uid) {
        Press detail = pressService.selectPress(uid);
        if(detail == null)
            return "/404";

        model.addAttribute("businessAreaList", businessAreaService.selectBusinessAreaList());
        model.addAttribute("detail", detail);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
        model.addAttribute("froalaKey", resourceBundle.getString("froala.key"));
        model.addAttribute("editorUploadPath", "/press/"+uid+"/editor");
        return "/news-room/press-form";
    }

    /**
     * 프레스 센터 - 상세
     * @param model
     * @return
     */
    @Operation("프레스 센터 상세")
    @GetMapping({"/news-room/press/{uid}"})
    public String pressView(Model model, @PathVariable String uid) {
        Press detail = pressService.selectPress(uid);
        if(detail == null)
            return "/404";

        model.addAttribute("detail", detail);
        return "/news-room/press-view";
    }
}
