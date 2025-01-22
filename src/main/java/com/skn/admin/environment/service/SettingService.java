package com.skn.admin.environment.service;

import com.skn.admin.environment.dto.Setting;
import com.skn.admin.environment.mapper.SettingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingMapper settingMapper;

    @Transactional(readOnly = true)
    public Setting selectSetting() {
        Setting setting = new Setting();
        try {
            setting = settingMapper.selectSetting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setting;
    }

    public int insertSetting(Map map) {
        int result = 0;
        result = settingMapper.insertSetting(map);
        return result;
    }
}
