package com.skn.admin.site.controller;

import com.skn.admin.config.annotation.Operation;
import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.site.dto.adminaccount.request.AdminAccountReqeust;
import com.skn.admin.site.service.AdminAccountService;
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
@RequestMapping("/api/v1/site/admin-account")
public class AdminAccountApiController {
	private final AdminAccountService adminAccountService;

	@Operation("관리자 ID 중복 확인")
	@GetMapping("/check-duplicate-admin-id")
	public APIDataResponse<Boolean> checkDuplicateAdminId(
		@RequestParam String adminId
	) {
		if (adminId == null || adminId.isEmpty()) {
			return APIDataResponse.of(false);
		}

		return APIDataResponse.of(adminAccountService.checkDuplicateAdminId(adminId));
	}

	@Operation("관리자 E-mail 중복 확인")
	@GetMapping("/check-duplicate-email")
	public APIDataResponse<Boolean> checkDuplicateEmail(
		@RequestParam String email
	) {
		if (email == null || email.isEmpty()) {
			return APIDataResponse.of(false);
		}

		return APIDataResponse.of(adminAccountService.checkDuplicateEmail(email));
	}

	@Operation("관리자 계정 등록")
	@PostMapping
	public ResponseEntity insertAdminAccount(
		@Valid @RequestBody AdminAccountReqeust.Insert _req,
		Authentication authentication
	) {
		Admin admin = (Admin) authentication.getPrincipal();

		_req.setAdmin(admin.getAdminIndex(), admin.getAdminId());

		return adminAccountService.insertAdminAccount(_req);
	}

	@Operation("관리자 계정 수정")
	@PostMapping("/update/{uid}")
	public ResponseEntity updateAdminAccount(
		@Valid @RequestBody AdminAccountReqeust.Update _req,
		Authentication authentication
	) {
		Admin admin = (Admin) authentication.getPrincipal();

		_req.setAdmin(admin.getAdminIndex(), admin.getAdminId());

		return adminAccountService.updateAdminAccount(_req);
	}

	@Operation("관리자 계정 삭제")
	@PostMapping("/delete/{uid}")
	public ResponseEntity<Map<String, String>> deleteAdminAccount(
		@PathVariable String uid,
		Authentication authentication
	) {
		Admin admin = (Admin) authentication.getPrincipal();
		return adminAccountService.deleteAdminAccount(uid);
	}
}
