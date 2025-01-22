package com.skn.admin.log.mapper;

import com.skn.admin.log.dto.response.ResponsePasswordSettingDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LogManagementMapper {



    @Select("SELECT LOCATION2 FROM VW_ADMIN_MENU WHERE URL_STRING LIKE CONCAT('%', #{url}, '%') ORDER BY MENU_INDEX DESC LIMIT 1")
    String getKoMenuByUrl(String url);

    @Select("SELECT " +
            "IS_USE_PW AS passwordNoticeIsUse," +
            " PW_PERIOD AS passwordNoticePeriod," +
            " PW_PERIOD_NEXT AS passwordNoticePeriodNext," +
            " IS_USE_LIFETIME AS logoutNoticeIsUse," +
            " LIFETIME AS logoutPeriod " +
            "FROM SETTING " +
            "ORDER BY SETTING_INDEX DESC LIMIT 1")
    ResponsePasswordSettingDto selectSetting();


    /******************************************** CREATE ********************************************/

    /******************************************** UPDATE ********************************************/

    /******************************************** DELETE ********************************************/
}
