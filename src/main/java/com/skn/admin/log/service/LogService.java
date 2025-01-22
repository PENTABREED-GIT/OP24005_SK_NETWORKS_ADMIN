package com.skn.admin.log.service;

import com.skn.admin.account.dto.AdminGroup;
import com.skn.admin.base.etc.Page;
import com.skn.admin.config.api.constant.ErrorCode;
import com.skn.admin.config.api.exception.GeneralException;
import com.skn.admin.customer.dto.Inquiry;
import com.skn.admin.customer.dto.request.InquirySearchParam;
import com.skn.admin.log.dto.ActionLog;
import com.skn.admin.log.dto.LoginLog;
import com.skn.admin.log.dto.request.ActionLogSearchParam;
import com.skn.admin.log.dto.request.LoginLogSearchParam;
import com.skn.admin.log.dto.response.*;
import com.skn.admin.log.mapper.LogMapper;
import com.skn.admin.util.NTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogService {

    private final LogMapper logMapper;

    @Transactional(readOnly = true)
    public List<ResponseErrorLogDtoList> selectErrorLogList(Map<String, Object> reqMap) {
        List<ResponseErrorLogDtoList> errorLogList = Collections.emptyList();

        try {
            errorLogList = logMapper.selectErrorLogList(reqMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return errorLogList;
    }

    @Transactional(readOnly = true)
    public Integer selectErrorLogListCount(Map<String, Object> reqMap) {
        Integer count = 0;

        try {
            count = logMapper.selectErrorLogListCount(reqMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return count;
    }

    public int selectLoginLogListCount(LoginLogSearchParam loginLogSearchParam) {
        return logMapper.selectLoginLogListCount(loginLogSearchParam);
    }

    public List<LoginLog> selectLoginLogList(LoginLogSearchParam loginLogSearchParam) {
        return logMapper.selectLoginLogList(loginLogSearchParam);
    }

    @Transactional
    public int selectUseLogListCount(ActionLogSearchParam actionLogSearchParam) {
        return logMapper.selectUseLogListCount(actionLogSearchParam);
    }

    public List<ActionLog> selectUseLogList(ActionLogSearchParam actionLogSearchParam) {
        return logMapper.selectUseLogList(actionLogSearchParam);
    }

    public List<AdminGroup> getAdminGroupLogList(Map<String, String> searchMap) {
        return logMapper.selectAdminGroupLogList(searchMap);
    }

    public Integer getAdminGroupLogTotalCount(Map<String, String> searchMap) {
        return logMapper.selectAdminGroupLogTotalCount(searchMap);
    }

    public AdminGroup getAdmingGroupLog(Integer adminGroupLogIndex) {
        AdminGroup adminGroup = logMapper.selectAdminGroupLog(adminGroupLogIndex);
        adminGroup.setAdminGroupPermissionList(logMapper.selectAdminGroupPermissionLogList(adminGroupLogIndex));
        return adminGroup;
    }
}
