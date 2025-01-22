package com.skn.admin.environment.controller;

import com.skn.admin.environment.dto.Admin;
import com.skn.admin.environment.service.AdminActionLogService;
import com.skn.admin.environment.service.AdminLoginLogService;
import com.skn.admin.base.etc.Page;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.skn.admin.base.etc.Page.PAGE_BLOCK_SIZE;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminLogController {
    private final AdminLoginLogService adminLoginLogService;
    private final AdminActionLogService adminActionLogService;
    /**
     * 접속로그
     * @return
     */
    @GetMapping("/environment/admin-login-log-list")
    public String AdminLoginLogList(
            @RequestParam Map<String, Object> reqMap,
            Model model,
            Authentication authentication,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "perPage", defaultValue = "30") int size
    ) {
        //이용 로그
        Page paging = new Page(PAGE_BLOCK_SIZE, page, size);
        reqMap.put("limit", paging.getPageListSize());
        reqMap.put("offset", paging.getOffset());
        Integer listNum = adminLoginLogService.selectLoginLogCount(reqMap);

        model.addAttribute("listNum", listNum);
        model.addAttribute("page", paging.setPage(listNum));
        model.addAttribute("search", reqMap);

        Admin admin = (Admin) authentication.getPrincipal();
        if(!admin.getAdminGroupIndex().equals("1")) {
            reqMap.put("searchAdminId",admin.getAdminId());
        }

        model.addAttribute("loginLogList", adminLoginLogService.selectLoginLogList(reqMap, authentication));

        return "/environment/AdminLoginLogList";
    }

    /**
     * 이용로그
     * @return
     */
    @GetMapping("/environment/admin-action-log-list")
    public String AdminActionLogList(
            @RequestParam Map<String, Object> reqMap,
            Model model,
            HttpSession session,
            Authentication authentication,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "perPage", defaultValue = "30") int size
    ) {
        //이용 로그
        Page paging = new Page(PAGE_BLOCK_SIZE, page, size);
        reqMap.put("limit", paging.getPageListSize());
        reqMap.put("offset", paging.getOffset());
        Integer listNum = adminActionLogService.selectActionCount(reqMap);


        model.addAttribute("listNum", listNum);
        model.addAttribute("page", paging.setPage(listNum));
        model.addAttribute("search", reqMap);

        Admin admin = (Admin) authentication.getPrincipal();
        if(!admin.getAdminGroupIndex().equals("1")) {
            reqMap.put("searchAdminId",admin.getAdminId());
        }

        List actionLogList = adminActionLogService.selectActionLogList(reqMap);
        model.addAttribute("adminActionLogList", actionLogList );

        return "/environment/AdminActionLogList";
    }
}
