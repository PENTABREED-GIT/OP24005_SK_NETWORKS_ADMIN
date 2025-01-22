package com.skn.admin.site.controller;

import com.skn.admin.base.etc.Page;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.site.dto.adminaccount.request.AdminAccountSearchParam;
import com.skn.admin.site.service.AdminAccountService;
import jakarta.servlet.http.HttpSession;
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
public class AdminAccountController {
	private final AdminAccountService adminAccountService;

	@Operation("관리자 계정 리스트")
	@GetMapping("/admin-account")
	public ModelAndView adminAccountList(
		@ModelAttribute AdminAccountSearchParam _param,
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "perPage", defaultValue = "30") int size,
		Model model
	) {
		ModelAndView mv = new ModelAndView("site/admin-account-list");

		adminAccountService.getAdminAccountList(_param, new Page(PAGE_BLOCK_SIZE, page, size), model);

		return mv;
	}

	@Operation("관리자 계정 등록")
	@GetMapping("/admin-account-reg-form")
	public ModelAndView adminAccountRegForm(
		HttpSession session,
		Authentication authentication,
		Model model
	) {
		String uid = UUID.randomUUID().toString().replace("-", "").substring(1, 21);
		ModelAndView mv = new ModelAndView("site/admin-account-form");

		model.addAttribute("uid", uid)
			.addAttribute("adminGroupList", adminAccountService.selectAdminGroupList("Y"));

		return mv;
	}

	@Operation("관리자 계정 수정")
	@GetMapping("/admin-account-mod-form/{uid}")
	public ModelAndView adminAccountModForm(
		@PathVariable String uid,
		Model model
	) {
		ModelAndView mv = new ModelAndView("site/admin-account-form");

		model.addAttribute("adminAccount", adminAccountService.selectAdminAccount(uid))
			.addAttribute("adminGroupList", adminAccountService.selectAdminGroupList("Y"));

		return mv;
	}

	@Operation("관리자 계정 상세")
	@GetMapping("/admin-account/{uid}")
	public ModelAndView adminAccountView(
		@PathVariable String uid,
		Model model
	) {
		ModelAndView mv = new ModelAndView("site/admin-account-view");

		model.addAttribute("adminAccount", adminAccountService.selectAdminAccount(uid))
			.addAttribute("adminGroupList", adminAccountService.selectAdminGroupList(null));

		return mv;
	}
}
