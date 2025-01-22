package com.skn.admin.log.service;

import com.skn.admin.environment.dto.Admin;
import com.skn.admin.log.dto.request.RequestSettingDto;
import com.skn.admin.log.mapper.SettingMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("log.service.settingApiService")
@Slf4j
@AllArgsConstructor
public class SettingApiService {

    @Qualifier("log.mapper.settingMapper")
    private final SettingMapper settingMapper;

    /************************************************** UPDATE ******************************************/

    @Transactional(rollbackFor = Exception.class)
    public void updateSetting(
            RequestSettingDto requestSettingDto,
            HttpServletRequest httpServletRequest
    ) {
        Admin admin = (Admin) httpServletRequest.getSession().getAttribute("ADMIN");
        try {
            RequestSettingDto convertRequestSettingDto = RequestSettingDto.toDTO(requestSettingDto, admin);
            settingMapper.insertSetting(convertRequestSettingDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
