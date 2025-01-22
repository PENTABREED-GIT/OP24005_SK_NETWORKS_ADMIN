package com.skn.admin.common.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommonMapper {
    String getNextSeq(String menu);
    List<Map> selectCountry(Map map);
    List<Map> selectUserCountryList(Map map);
}
