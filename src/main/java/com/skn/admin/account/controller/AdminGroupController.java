package com.skn.admin.account.controller;

import com.skn.admin.account.dto.AdminGroup;
import com.skn.admin.account.service.AdminGroupService;
import com.skn.admin.base.etc.Page;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.environment.dto.Admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

import static com.skn.admin.base.etc.Page.PAGE_BLOCK_SIZE;

/**
 * 관리자 그룹 관리 컨트롤러
 * @author 유경진
 * @version 1.0.0
 * <pre>
 *
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminGroupController {
    private final AdminGroupService adminGroupService;

    @Operation("관리자 그룹 목록")
    @GetMapping("/site/admin-group")
    public String adminGroupList(@ModelAttribute AdminGroup.AdminGroupSearchParam searchParam,
            @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
            Model model) {
        int totalCount = adminGroupService.getAdminGroupTotalCount(searchParam);
        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        model.addAttribute("list", adminGroupService.getAdminGroupList(searchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("search", searchParam);
        model.addAttribute("page", page);

        return "account/admin-group-list";
    }

    /**
     * 관리자 권한 조회
     * @param authentication
     * @param uid
     * @param model
     * @return
     */
    @Operation("관리자 권한 조회")
    @GetMapping("/site/admin-group/{uid}")
    public String adminGroupView(Authentication authentication, @PathVariable String uid, Model model) {
        AdminGroup adminGroup = adminGroupService.getAdminGroup(uid);
        model.addAttribute("adminGroup", adminGroup);
        model.addAttribute("adminGroupPermissionList", adminGroup.getAdminGroupPermissionList());

        return "account/admin-group-view";
    }

    @Operation("관리자 권한 등록 폼")
    @GetMapping("/site/admin-group-reg-form")
    public String adminGroupRegForm(Authentication authentication, Model model) {
        model.addAttribute("adminGroupPermissionList", adminGroupService.getAdminGroupPermissionList(null));

        return "account/admin-group-form";
    }

    @Operation("관리자 권한 등록 폼")
    @GetMapping("/site/admin-group-mod-form/{uid}")
    public String adminGroupModForm(Authentication authentication, @PathVariable String uid, Model model) {
        AdminGroup adminGroup = adminGroupService.getAdminGroup(uid);
        model.addAttribute("adminGroup", adminGroup);
        model.addAttribute("adminGroupPermissionList", adminGroup.getAdminGroupPermissionList());

        return "account/admin-group-form";
    }
}
