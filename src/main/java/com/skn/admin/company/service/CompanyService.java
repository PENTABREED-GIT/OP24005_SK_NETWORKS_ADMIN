package com.skn.admin.company.service;

import com.skn.admin.company.dto.GlobalNetwork;
import com.skn.admin.company.dto.request.GlobalNetworkSearchParam;
import com.skn.admin.company.mapper.GlobalNetworkMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final GlobalNetworkMapper globalNetworkMapper;

    public List<GlobalNetwork> selectGlobalNetworkList(GlobalNetworkSearchParam param) {
        return globalNetworkMapper.selectGlobalNetworkList(param);
    }

    public int selectGlobalNetworkListCount(GlobalNetworkSearchParam param) {
        return globalNetworkMapper.selectGlobalNetworkListCount(param);
    }

    @Transactional
    public int insertGlobalNetwork(GlobalNetwork data) {
        // 기본 정보 insert
        int result = globalNetworkMapper.insertGlobalNetwork(data);
        return result;
    }

    public GlobalNetwork selectGlobalNetwork(String uid) {
        GlobalNetwork data = globalNetworkMapper.selectGlobalNetwork(uid);
        return data;
    }

    public int updateGlobalNetwork(GlobalNetwork data) {
        return globalNetworkMapper.updateGlobalNetwork(data);
    }

    public int deleteGlobalNetwork(GlobalNetwork data) {

        return globalNetworkMapper.deleteGlobalNetwork(data);
    }

}
