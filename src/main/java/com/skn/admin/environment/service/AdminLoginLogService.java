package com.skn.admin.environment.service;

import com.skn.admin.environment.dto.Admin;
import com.skn.admin.environment.dto.AdminLoginLog;
import com.skn.admin.environment.mapper.AdminLoginLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminLoginLogService {
    private final AdminLoginLogMapper adminLoginLogMapper;

    public List<AdminLoginLog> selectLoginLogList(Map<String, Object> map, Authentication authentication) {
        List loginLogList = null;
        Admin admin = (Admin) authentication.getPrincipal();

        if(!admin.getAdminGroupIndex().equals("1")) {
            map.put("searchAdminId",admin.getAdminId());
        }

        loginLogList = adminLoginLogMapper.selectLoginLogList(map);
        return loginLogList;
    }

    public int selectLoginLogCount(Map<String, Object> map){
        int totalCount = 0;
        totalCount = adminLoginLogMapper.selectLoginLogCount(map);
        return totalCount;
    }
    public void insertLoginLog(AdminLoginLog adminLoginLog) {
        adminLoginLogMapper.insertLoginLog(adminLoginLog);
    }
}
