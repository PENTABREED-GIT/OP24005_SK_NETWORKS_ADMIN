package com.skn.admin.log.service;


import com.skn.admin.log.dto.response.ResponsePasswordSettingDto;
import com.skn.admin.log.mapper.SettingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("log.service.settingService")
@RequiredArgsConstructor
public class SettingService {

    @Qualifier("log.mapper.settingMapper")
    private final SettingMapper settingMapper;

    /************************************************** READ Object ******************************************/
    @Transactional(readOnly = true)
    public ResponsePasswordSettingDto selectPasswordSetting() {
        ResponsePasswordSettingDto responsePasswordSettingDto = ResponsePasswordSettingDto.toEmpty();
        try {
            responsePasswordSettingDto = settingMapper.selectSetting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responsePasswordSettingDto;
    }

}
