package com.skn.admin.account.controller;

import com.skn.admin.account.dto.AdminGroup;
import com.skn.admin.account.service.AdminGroupService;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.environment.dto.Admin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/account/admin-group")
public class AdminGroupApiController {
    private final AdminGroupService adminGroupService;

    @Operation("관리자 권한 등록")
    @PostMapping
    public ResponseEntity insertAdminGroup(@Valid @RequestBody AdminGroup adminGroup, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        adminGroup.setAdminId(admin.getAdminId());
        adminGroup.setAdminIndex(admin.getAdminIndex());

        return adminGroupService.registerAdminGroup(adminGroup);
    }

    @Operation("관리자 권한 등록")
    @PostMapping("/update/{uid}")
    public ResponseEntity updateAdminGroup(@Valid @RequestBody AdminGroup adminGroup, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        adminGroup.setAdminId(admin.getAdminId());
        adminGroup.setAdminIndex(admin.getAdminIndex());

        return adminGroupService.modifyAdminGroup(adminGroup);
    }
}
