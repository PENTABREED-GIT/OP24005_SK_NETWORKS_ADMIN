package com.skn.admin.log.mapper;

import com.skn.admin.log.dto.request.RequestSettingDto;
import com.skn.admin.log.dto.response.ResponsePasswordSettingDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component("log.mapper.settingMapper")
public interface SettingMapper {

    /************************************************** SELECT **************************************************/
    @Select("SELECT " +
            "IS_USE_PW AS passwordNoticeIsUse," +
            " PW_PERIOD AS passwordNoticePeriod," +
            " PW_PERIOD_NEXT AS passwordNoticePeriodNext," +
            " IS_USE_LIFETIME AS logoutNoticeIsUse," +
            " LIFETIME AS logoutPeriod " +
            "FROM SETTING " +
            "ORDER BY SETTING_INDEX DESC LIMIT 1")
    ResponsePasswordSettingDto selectSetting();


    /************************************************** INSERT **************************************************/



    /************************************************** UPDATE **************************************************/
    @Insert("INSERT INTO SETTING (" +
            "IS_USE_PW," +
            " PW_PERIOD," +
            " PW_PERIOD_NEXT," +
            " IS_USE_LIFETIME," +
            " LIFETIME," +
            "ADMIN_INDEX, " +
            "CRDATE" +
            ") VALUES (" +
            "#{passwordNoticeIsUse}," +
            " #{passwordNoticePeriod}," +
            " #{passwordNoticePeriodNext}," +
            " #{logoutNoticeIsUse}," +
            "#{logoutPeriod}," +
            "#{adminIndex}," +
            "#{crDate}" +
            ")"
    )
    void insertSetting(RequestSettingDto convertRequestSettingDto);



    /************************************************** DELETE **************************************************/
}
