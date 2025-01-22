package com.skn.admin.log.controller;

import com.skn.admin.config.annotation.Operation;

import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.log.dto.request.RequestSettingDto;
import com.skn.admin.log.service.SettingApiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("log.controller.settingApiController")
@AllArgsConstructor
@RequestMapping("/api/v1/setting")
public class SettingApiController {

    @Qualifier("log.service.settingApiService")
    private final SettingApiService settingApiService;

    /******************************************** 접속환경 **************************************************/
    @Operation("접속환경 수정")
    @PostMapping("/update")
    public APIDataResponse<String> updateSetting(
            HttpServletRequest request,
            @Valid @RequestBody RequestSettingDto requestSettingDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return APIDataResponse.of(Boolean.toString(false));
        }

        settingApiService.updateSetting(requestSettingDto, request);


        return APIDataResponse.of(Boolean.toString(true));
    }

}
