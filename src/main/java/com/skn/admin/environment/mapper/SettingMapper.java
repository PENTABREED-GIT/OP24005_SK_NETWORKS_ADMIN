package com.skn.admin.environment.mapper;

import com.skn.admin.environment.dto.Setting;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface SettingMapper {
    Setting selectSetting();

    int insertSetting(Map map);
}
