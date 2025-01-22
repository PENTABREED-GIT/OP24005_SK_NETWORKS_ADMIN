package com.skn.admin.environment.service;

import com.skn.admin.environment.dto.AdminActionLog;
import com.skn.admin.environment.mapper.AdminActionLogMapper;
import com.skn.admin.util.NTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminActionLogService {

    private final AdminActionLogMapper adminActionLogMapper;
    public List selectActionLogList(Map<String, Object> map) {
        List actionLogList = null;
        actionLogList = adminActionLogMapper.selectActionLogList(map);
        return actionLogList;
    }
    public int selectActionCount(Map<String, Object> map){
        int totalCount = 0;
        totalCount = adminActionLogMapper.selectActionLogCount(map);
        return totalCount;
    }

    public int insertAdminActionLog(AdminActionLog log) {
        String uid = NTUtil.isNull(log.getUid(), "");
        if("".equals(uid)) { //log uid값은 필수값
            log.setUid(RandomStringUtils.randomAlphanumeric(16));
        }
        return adminActionLogMapper.insertAdminActionLog(log);
    }

    public String selectDownloadReason(String uid) {
        String newUid = uid;
        char lastChar = newUid.charAt(uid.length() - 1);
        if (lastChar == '?') {
            newUid = uid.substring(0, uid.length() - 1);
        }
        return adminActionLogMapper.selectDownloadReasonByUid(newUid);
    }
}
