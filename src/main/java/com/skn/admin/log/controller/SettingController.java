package com.skn.admin.log.controller;

import com.skn.admin.config.annotation.Operation;
import com.skn.admin.log.dto.response.ResponsePasswordSettingDto;
import com.skn.admin.log.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@Controller("log.controller.SettingController")
@RequiredArgsConstructor
public class SettingController {

    @Qualifier("log.service.settingService")
    private final SettingService settingService;

    /******************************************************* 접속환경 ***********************************************/
    @Operation("접속환경 불러오기")
    @GetMapping("/log/setting")
    public ModelAndView setting(
            @RequestParam Map<String, Object> reqMap,
            Model model
    ) {

        ModelAndView mv = new ModelAndView("log/setting");

        ResponsePasswordSettingDto responsePasswordSettingDto = settingService.selectPasswordSetting();

        model.addAttribute("setting", responsePasswordSettingDto);


        return mv;
    }
}
