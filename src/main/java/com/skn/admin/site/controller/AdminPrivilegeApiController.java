package com.skn.admin.site.controller;

import com.skn.admin.environment.dto.Admin;
import com.skn.admin.site.dto.adminprivilege.request.AdminPrivilegeReqeust;
import com.skn.admin.site.service.AdminPrivilegeService;
import com.skn.admin.config.annotation.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/site/admin-privilege")
public class AdminPrivilegeApiController {
	private final AdminPrivilegeService adminPrivilegeService;

	@Operation("관리자 권한 등록")
	@PostMapping
	public ResponseEntity insertAdminPrivilege(
		@Valid @RequestBody AdminPrivilegeReqeust.Insert _req,
		Authentication authentication
	) {
		Admin admin = (Admin) authentication.getPrincipal();

		_req.setAdmin(admin.getAdminIndex(), admin.getAdminId());

		return adminPrivilegeService.insertAdminPrivilege(_req);
	}

	@Operation("관리자 권한 수정")
	@PostMapping("/update/{uid}")
	public ResponseEntity updateAdminPrivilege(
		@Valid @RequestBody AdminPrivilegeReqeust.Update _req,
		Authentication authentication
	) {
		Admin admin = (Admin) authentication.getPrincipal();

		_req.setAdmin(admin.getAdminIndex(), admin.getAdminId());

		return adminPrivilegeService.updateAdminPrivilege(_req);
	}

	@Operation("관리자 권한 삭제")
	@PostMapping("/delete/{uid}")
	public ResponseEntity<Map<String, String>> deleteAdminPrivilege(
		@PathVariable String uid,
		Authentication authentication
	) {
		Admin admin = (Admin) authentication.getPrincipal();
		return adminPrivilegeService.deleteAdminPrivilege(uid);
	}
}
