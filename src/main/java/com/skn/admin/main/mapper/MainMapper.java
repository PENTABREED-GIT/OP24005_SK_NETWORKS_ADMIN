package com.skn.admin.main.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MainMapper {
    Map selectTestList();
}
