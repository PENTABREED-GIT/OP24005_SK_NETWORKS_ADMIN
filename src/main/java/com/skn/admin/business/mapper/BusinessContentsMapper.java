package com.skn.admin.business.mapper;

import com.skn.admin.business.dto.BusinessContents;
import com.skn.admin.business.dto.BusinessContentsDetail;
import com.skn.admin.business.dto.request.BusinessContentsSearchParam;

import java.util.List;
import java.util.Map;

public interface BusinessContentsMapper {
    List<BusinessContents> selectBusinessContentsList(BusinessContentsSearchParam param);
    int selectBusinessContentsListCount(BusinessContentsSearchParam param);
    int insertBusinessContents(BusinessContents data);
    int deleteBusinessContents(BusinessContents data);
    int updateBusinessContents(BusinessContents data);
    BusinessContents selectBusinessContents(String uid);

    int insertBusinessContentsDetail(BusinessContentsDetail data);
    List<BusinessContentsDetail> selectBusinessContentsDetail(String businessContentsIndex);

    int updateBusinessContentsDetail(BusinessContentsDetail data);
    List<String> selectBusinessContentsDetailUidList(Map param);
    int deleteBusinessContentsDetail(Map param);
}
