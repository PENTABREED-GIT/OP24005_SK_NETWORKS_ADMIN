package com.skn.admin.newsroom.mapper;

import com.skn.admin.newsroom.dto.Press;
import com.skn.admin.newsroom.dto.request.PressSearchParam;

import java.util.List;

public interface PressMapper {
    List<Press> selectPressList(PressSearchParam param);
    int selectPressListCount(PressSearchParam param);
    int insertPress(Press data);
    int deletePress(Press data);
    int updatePress(Press data);
    Press selectPress(String uid);
}
