package com.skn.admin.log.controller;

import com.skn.admin.account.dto.AdminGroup;
import com.skn.admin.base.etc.Page;
import com.skn.admin.business.dto.request.BusinessContentsSearchParam;
import com.skn.admin.common.service.InitService;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.log.dto.request.ActionLogSearchParam;
import com.skn.admin.log.dto.request.LoginLogSearchParam;
import com.skn.admin.log.mapper.LogMapper;
import com.skn.admin.log.service.LogService;
import com.skn.admin.log.dto.response.ResponseErrorLogDtoList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static com.skn.admin.base.etc.Page.PAGE_BLOCK_SIZE;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;
    private final InitService initService;
    private final LogMapper logMapper;


    /****************************************************** 접속로그 **********************************************/
    @Operation("접속로그 리스트 보기")
    @GetMapping("/log/login-log")
    public String loginLogList (@ModelAttribute LoginLogSearchParam _searchParam,
                                @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
                                @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
                                Model model) {

        int totalCount = logService.selectLoginLogListCount(_searchParam);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        LoginLogSearchParam  loginLogSearchParam = LoginLogSearchParam.of(_searchParam, page);

        model.addAttribute("loginLogList",logService.selectLoginLogList(loginLogSearchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("searchParam", _searchParam);

        return "/log/login-log-list";
    }

    /****************************************************** 이용로그 **********************************************/
    @Operation("이용로그 리스트 보기")
    @GetMapping("/log/usage-log")
    public String useLogList (@ModelAttribute ActionLogSearchParam _searchParam,
                                    @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
                                    @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
                                    Model model) {

        int totalCount = logService.selectUseLogListCount(_searchParam);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        ActionLogSearchParam  actionLogSearchParam = ActionLogSearchParam.of(_searchParam, page);

        model.addAttribute("actionLogList",logService.selectUseLogList(actionLogSearchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("searchParam", _searchParam);

        return "/log/usage-log-list";
    }

    /**
     * 관리자 그룹 권한 로그 목록
     * @param searchMap
     * @param currentPage
     * @param pageListSize
     * @param model
     * @return
     */
    @Operation("관리자 그룹 권한 로그 목록")
    @GetMapping("/log/admin-group-log")
    public String adminGroupLogList (@RequestParam Map<String, String> searchMap,
                              @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
                              @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
                              Model model) {

        int totalCount = logService.getAdminGroupLogTotalCount(searchMap);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        model.addAttribute("list",logService.getAdminGroupLogList(searchMap));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("search", searchMap);

        return "/log/admin-group-log-list";
    }

    /**
     * 관리자 그룹 권한 로그 조회
     * @param adminGroupLogIndex
     * @param model
     * @return
     */
    @Operation("관리자 그룹 권한 로그 조회")
    @GetMapping("/log/admin-group-log/{adminGroupLogIndex}")
    public String adminGroupLogView (@PathVariable Integer adminGroupLogIndex, Model model) {
        AdminGroup adminGroup = logService.getAdmingGroupLog(adminGroupLogIndex);
        model.addAttribute("adminGroup", adminGroup);
        model.addAttribute("adminGroupPermissionList", adminGroup.getAdminGroupPermissionList());

        return "/log/admin-group-log-view";
    }
}
