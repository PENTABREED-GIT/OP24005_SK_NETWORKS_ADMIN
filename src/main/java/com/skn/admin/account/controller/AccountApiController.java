package com.skn.admin.account.controller;


import com.skn.admin.account.service.AccountService;
import com.skn.admin.config.security.AuthenticationUserDetailService;
import com.skn.admin.config.security.password.KisaSha256PasswordEncoder;
import com.skn.admin.config.security.user.AdminAdaptor;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.environment.dto.Setting;
import com.skn.admin.environment.service.AdminService;
import com.skn.admin.util.NTResult;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AccountApiController {
    private final AccountService accountService;
    private final AdminService adminService;
    private final KisaSha256PasswordEncoder passwordEncoder;
    private final AuthenticationUserDetailService userDetailService;

    @Operation(description = "로그인")
    @PostMapping("/login")
    public ResponseEntity login(@RequestParam Map<String, String> reqMap, HttpServletRequest request, HttpSession session) {
        String hi = "";
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Operation(description = "패스워드 180일 미변경시")
    @PostMapping("/account/password/{uid}")
    public ResponseEntity changePassword(@PathVariable String uid, @RequestParam Map<String, String> reqMap, HttpServletRequest request, HttpSession session) throws UnknownHostException {
        NTResult result = new NTResult();

        String ip = InetAddress.getLocalHost().getHostAddress().toString();
        Setting setting = (Setting) request.getSession().getAttribute("setting");
        String pwPeriod = setting.getPwPeriod();
        String currentPassword = reqMap.get("password");
        String newPassword = reqMap.get("newPassword");

        try {
            result = accountService.updatePasswordByUid(uid, ip, pwPeriod, currentPassword, newPassword);

            if("SUCCESS".equals(result.getCode())) {
                result.setSuccess();
            } else {
                result.setFail();
            }
            result.setMsg(result.getMsg());
        } catch (Exception e) {
            result.setFail();
        }
        return ResponseEntity.ok(result.getAsMap());
    }

    @Operation(description = "패스워드 ....?")
    @PostMapping("/account/password/next/update/{uid}")
    public ResponseEntity changePwNext(@PathVariable String uid, HttpSession session) {
        NTResult result = new NTResult();
        String adminId = (String) session.getAttribute("adminId");
        if (StringUtils.isEmpty(adminId)) {
            result.setFail();
            return ResponseEntity.ok(result.getAsMap());
        }

        Admin admin = adminService.selectAdminById(adminId);
        if (ObjectUtils.isEmpty(admin)) {
            result.setFail();
            return ResponseEntity.ok(result.getAsMap());
        }

        accountService.updateNextPwModDate(uid);

        setAuthenticationManager(uid);
        result.setSuccess();
        return ResponseEntity.ok(result.getAsMap());
    }

    @Operation(description = "회원가입신청")
    @PostMapping("/account/{uid}")
    public ResponseEntity signup(@PathVariable String uid, @RequestBody String userId) { //임시 userId DTO추가예정
        return ResponseEntity.ok().body("회원가입신청완료");
    }

    protected void setAuthenticationManager(String adminId) {
        AdminAdaptor adminAdaptor = (AdminAdaptor) userDetailService.loadUserByUsername(adminId);
        Admin admin = adminAdaptor.getAdmin();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(admin, admin.getAdminPw(), adminAdaptor.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
