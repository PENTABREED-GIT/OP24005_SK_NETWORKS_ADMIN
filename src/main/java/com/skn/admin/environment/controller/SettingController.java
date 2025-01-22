package com.skn.admin.environment.controller;

import com.skn.admin.environment.service.SettingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/environment")
public class SettingController {
    private final SettingService settingService;
    /**
     * 접속환경 목록
     * @return
     */
    @GetMapping("/setting")
    public String setting(Model model, HttpSession session, Authentication authentication) {
        model.addAttribute("setting", settingService.selectSetting());
        return "/environment/Setting";
    }
}
