package com.skn.admin.account.controller;


import com.skn.admin.account.service.AccountService;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.environment.service.AdminService;
import com.skn.admin.environment.service.SettingService;
import com.skn.admin.util.KISA_SHA256;
import com.skn.admin.util.NTResult;
import com.skn.admin.util.NTUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AdminService adminService;

    @Resource
    private SettingService settingService;
    @Resource
    private AccountService accountService;

    /**
     * 로그인 페이지
     * @return
     */
    @Operation(description = "로그인 페이지")
    @GetMapping({"/login"})
    public String login() {
        return "login";
    }


    @PostMapping(value = "/admin/ModifyPasswordAJX")
    @ResponseBody
    public Map<String, Object> pwModProc(@RequestParam Map<String, String> reqMap, HttpServletRequest request, HttpSession session) {
        NTResult result = new NTResult();
        Map map = new HashMap();
        Admin admin = (Admin)session.getAttribute("TEMP_ADMIN");
        if (admin == null) {
            result.setFail();
            return result.getAsMap();
        }

        map.put("adminId", admin.getAdminId());
        String pwOrigin = accountService.selectAdminPassword(map);
        String pwConfirm = KISA_SHA256.Encrpyt(reqMap.get("password"));
        if (!pwOrigin.equals(pwConfirm)) {
            result.setFail();
            result.setMsg("Passwords do not match.");
            return result.getAsMap();
        }

        if (pwOrigin.equals(KISA_SHA256.Encrpyt(reqMap.get("newPassword")))) {
            result.setFail();
            result.setMsg("Same as previous password.");
            return result.getAsMap();
        }

        Map modPwMap = new HashMap();
        modPwMap.put("adminId", admin.getAdminId());
        modPwMap.put("adminPW", KISA_SHA256.Encrpyt(reqMap.get("newPassword")));

        int rs = accountService.updatePasswordById(modPwMap);

        if (rs <= 0) {
            session.setAttribute("ADMIN", session.getAttribute("TEMP_ADMIN"));
        }
        return result.getAsMap();
    }

    /**
     * 관리자 수정 페이지
     * @return
     */
    @Operation(description = "관리자 수정")
    @GetMapping("/account/account-mod-form")
    public String accountModForm(Model model, HttpSession session) {
        String result = "";
        String sessionToken = NTUtil.isNull(session.getAttribute("adminSessionToken"), "");

        if("".equals(sessionToken)) {
            result = "/account/account-form";
        }
        return result;
    }
}
