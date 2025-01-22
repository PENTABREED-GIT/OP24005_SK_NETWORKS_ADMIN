package com.skn.admin.environment.controller;

import com.skn.admin.config.annotation.Operation;
import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.environment.service.AdminService;
import com.skn.admin.util.NTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/environment")
@ResponseBody
public class AdminApiController {

    private final AdminService adminService;


    @Operation("계정 아이디..?")
    @GetMapping("/admin/id/{adminId}")
    public ResponseEntity duplicateAdminIdCheck(@PathVariable String adminId) {
        return new ResponseEntity<>(adminService.duplicateAdminIdCheck(adminId), HttpStatus.OK);
    }

    @Operation("계정 이메일...?")
    @GetMapping("/admin/email/{email}")
    public ResponseEntity duplicateAdminEmailCheck(@PathVariable String email) {
        return new ResponseEntity<>(adminService.duplicateAdminEmailCheck(email), HttpStatus.OK);
    }

    @Operation("계정 생성")
    @PostMapping("/admin")
    public ResponseEntity saveAdmin(@ModelAttribute Admin admin, HttpServletRequest request, Authentication authentication) {
        String ip = NTUtil.getClientIp(request);
        Admin account = (Admin) authentication.getPrincipal();
        return new ResponseEntity(adminService.insertAdmin(admin, ip, account),HttpStatus.OK);
    }

    @Operation("계정 수정")
    @PostMapping("/admin/update/{uid}")
    public ResponseEntity modifyAdmin(@ModelAttribute Admin admin, HttpServletRequest request, @PathVariable String uid, Authentication authentication) {
        String ip = NTUtil.getClientIp(request);
        Admin account = (Admin) authentication.getPrincipal();
        return new ResponseEntity(adminService.updateAdmin(admin, ip, uid, account),HttpStatus.OK);
    }

    @Operation("계정 삭제")
    @PostMapping("/admin/delete/{uid}")
    public APIDataResponse<String> deleteAdmin(
            @PathVariable("uid") String uid
    ) {
        adminService.deleteAdmin(uid);
        return APIDataResponse.of(Boolean.toString(true));
    }
}
