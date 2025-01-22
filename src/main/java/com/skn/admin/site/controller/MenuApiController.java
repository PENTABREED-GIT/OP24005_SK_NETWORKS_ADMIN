package com.skn.admin.site.controller;

import com.skn.admin.config.annotation.Operation;
import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.site.dto.AdminMenu;
import com.skn.admin.site.dto.menu.request.MenuRequest;
import com.skn.admin.site.service.AdminMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/site/menu")
public class MenuApiController {
	private final AdminMenuService adminmenuService;

	@Operation("메뉴 등록")
	@PostMapping
	public APIDataResponse<AdminMenu> insertMenu(
		@RequestBody MenuRequest.Insert _req,
		Authentication authentication
	) {
		Admin admin = (Admin) authentication.getPrincipal();

		_req.setAdmin(admin.getAdminIndex(), admin.getAdminId());

		return APIDataResponse.of(adminmenuService.insertMenu(_req));
	}

	@Operation("메뉴 수정")
	@PostMapping("/update/{uid}")
	public APIDataResponse<AdminMenu> updateMenu(
		@PathVariable String uid,
		@RequestBody MenuRequest.Update _req,
		Authentication authentication
	) {
		Admin admin = (Admin) authentication.getPrincipal();

		_req.setAdmin(admin.getAdminIndex(), admin.getAdminId());

		return APIDataResponse.of(adminmenuService.updateMenu(uid, _req));
	}

	@Operation("메뉴 삭제")
	@PostMapping("/delete/{uid}")
	public APIDataResponse<String> deleteMenu(
		@PathVariable String uid
	) {
		adminmenuService.deleteMenu(uid);

		return APIDataResponse.of(Boolean.toString(true));
	}
}
