package com.skn.admin.environment.controller;

import com.skn.admin.config.annotation.Operation;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.environment.service.AdminService;
import com.skn.admin.base.etc.Page;
import com.skn.admin.site.service.AdminGroupService;
import com.skn.admin.util.NTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skn.admin.base.etc.Page.PAGE_BLOCK_SIZE;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/environment")
public class AdminController {
    private final AdminService adminService;
    private final AdminGroupService adminGroupService;
    /**
     * 관리자 계정 리스트
     * @return
     */
    @Operation("관리자 정보 불러오기")
    @GetMapping("/admin-list")
    public String adminList(
            @RequestParam Map<String, Object> reqMap,
            Model model,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "perPage", defaultValue = "30") int size
    ) {
        Page paging = new Page(PAGE_BLOCK_SIZE, page, size);
        reqMap.put("limit", paging.getPageListSize());
        reqMap.put("offset", paging.getOffset());

        Integer listNum = adminService.selectAdminTotalCount(reqMap);


        model.addAttribute("adminList", adminService.selectAdminList(reqMap));
        model.addAttribute("listNum", listNum);
        model.addAttribute("page", paging.setPage(listNum));
        model.addAttribute("search", reqMap);

        Map map = new HashMap<>();
        map.put("isUse", "Y");
        List adminGroupList = adminGroupService.selectAdminGroupInfoList(map);
        model.addAttribute("adminGroupList", adminGroupList);

        return "/environment/AdminList";
    }
    /**
     * 관리자 계정 등록 상세
     * @return
     */
    @GetMapping(value={"/admin-form", "/admin-form/{uid}"})
    public String adminForm(
            @RequestParam Map<String, String> reqMap,
            Model model,
            @PathVariable(required = false) String uid,
            HttpServletRequest httpServletRequest
    ) {
        Map map = new HashMap<>();

        if(!NTUtil.isEmpty(uid)) {
            Admin admin = adminService.findAdmin(uid);
            Admin account = (Admin) httpServletRequest.getSession().getAttribute("ADMIN");
            model.addAttribute("account", account.getAdminId());
            model.addAttribute("admin", admin);
        }

        map.put("isUse", "Y");
        model.addAttribute("groupList", adminGroupService.selectAdminGroupInfoList(map));
        return "/environment/AdminForm";
    }
}
