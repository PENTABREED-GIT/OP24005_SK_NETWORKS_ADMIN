package com.skn.admin.company.mapper;

import com.skn.admin.company.dto.GlobalNetwork;
import com.skn.admin.company.dto.request.GlobalNetworkSearchParam;

import java.util.List;

public interface GlobalNetworkMapper {
    List<GlobalNetwork> selectGlobalNetworkList(GlobalNetworkSearchParam param);
    int selectGlobalNetworkListCount(GlobalNetworkSearchParam param);
    int insertGlobalNetwork(GlobalNetwork data);
    int deleteGlobalNetwork(GlobalNetwork data);
    int updateGlobalNetwork(GlobalNetwork data);
    GlobalNetwork selectGlobalNetwork(String uid);
}
