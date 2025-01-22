package com.skn.admin.site.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.site.service.AdminMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/site")
public class MenuController {
	private final AdminMenuService adminMenuService;
	@Operation("메뉴 리스트")
	@GetMapping("/menu")
	public ModelAndView menuList(
		Model model
	) throws JsonProcessingException  {
		ModelAndView mv = new ModelAndView("site/menu-list");

		adminMenuService.selectMenuList(model);

		return mv;
	}

	@Operation("메뉴 모달창 띄우기")
	@GetMapping(value = "/menu-modal")
	public ModelAndView menuModal(
		@RequestParam Map<String, Object> reqMap,
		Model model
	) {
		String depth = reqMap.get("depth").toString();
		adminMenuService.selectMenuInInfo(
			reqMap.get("type").toString(),
			reqMap.get("uid").toString(),
			model
		);
		model.addAttribute("depth", depth);
		model.addAttribute("menuIndex" , reqMap.get("menuIndex").toString());
		model.addAttribute("parentIndex" , reqMap.get("parentIndex").toString());
		model.addAttribute("rootIndex" , reqMap.get("rootIndex").toString());
		ModelAndView mv = new ModelAndView("site/component/menuModal");
		return mv;

	}
}
