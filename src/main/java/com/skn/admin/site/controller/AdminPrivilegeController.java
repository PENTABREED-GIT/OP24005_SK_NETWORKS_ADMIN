package com.skn.admin.site.controller;

import com.skn.admin.base.etc.Page;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.site.dto.adminprivilege.request.AdminPrivilegeSearchParam;
import com.skn.admin.site.service.AdminPrivilegeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

import static com.skn.admin.base.etc.Page.PAGE_BLOCK_SIZE;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/site")
public class AdminPrivilegeController {
	private final AdminPrivilegeService adminPrivilegeService;

	@Operation("관리자 권한 리스트")
	@GetMapping("/admin-privilege")
	public ModelAndView adminPrivilegeList(
		@ModelAttribute AdminPrivilegeSearchParam _param,
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "perPage", defaultValue = "30") int size,
		Model model
	) {
		ModelAndView mv = new ModelAndView("site/admin-privilege-list");

		adminPrivilegeService.getAdminPrivilegeList(_param, new Page(PAGE_BLOCK_SIZE, page, size), model);

		return mv;
	}

	@Operation("관리자 권한 등록")
	@GetMapping("/admin-privilege-reg-form")
	public ModelAndView adminPrivilegeRegForm(
		Authentication authentication,
		Model model
	) {
		Admin admin = (Admin) authentication.getPrincipal();
		String uid = UUID.randomUUID().toString().replace("-", "").substring(1, 21);
		ModelAndView mv = new ModelAndView("site/admin-privilege-form");

		adminPrivilegeService.getAdminPrivilege(model, null);

		model.addAttribute("uid", uid)
			.addAttribute("admin", admin);

		return mv;
	}

	@Operation("관리자 권한 수정")
	@GetMapping("/admin-privilege-mod-form/{uid}")
	public ModelAndView adminPrivilegeModForm(
		@PathVariable String uid,
		Model model
	) {
		ModelAndView mv = new ModelAndView("site/admin-privilege-form");
		adminPrivilegeService.getAdminPrivilege(model, uid);

		return mv;
	}

	@Operation("관리자 권한 상세")
	@GetMapping("/admin-privilege/{uid}")
	public ModelAndView adminPrivilegeView(
		@PathVariable String uid,
		Model model
	) {
		ModelAndView mv = new ModelAndView("site/admin-privilege-view");
		adminPrivilegeService.getAdminPrivilege(model, uid);

		return mv;
	}
}
