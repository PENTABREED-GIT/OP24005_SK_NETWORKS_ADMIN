package com.skn.admin.environment.service;

import com.skn.admin.config.api.constant.ErrorCode;
import com.skn.admin.config.api.exception.GeneralException;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.environment.dto.AdminActionLog;
import com.skn.admin.environment.dto.Setting;
import com.skn.admin.environment.mapper.AdminMapper;
import com.skn.admin.util.KISA_SHA256;
import com.skn.admin.util.NTCryptoUtil;
import com.skn.admin.util.NTResult;
import com.skn.admin.util.NTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;
    private final AdminActionLogService adminActionLogService;
    private final SettingService settingService;

    public NTResult duplicateAdminIdCheck(String adminId) {
        NTResult ntResult = new NTResult();

        int adminIdCount = adminMapper.selectAdminIdCount(adminId);
        if(adminIdCount > 1) {
            ntResult.setFail();
        }

        if(adminIdCount == 0) {
            ntResult.setSuccess();
        }

        return ntResult;
    }

    public NTResult duplicateAdminEmailCheck(String adminEmail) {
        NTResult ntResult = new NTResult();
        String email = NTCryptoUtil.encrypt(adminEmail, "EMAIL");
        int adminEmailCount = adminMapper.selectAdminEmailCount(email);
        if(adminEmailCount > 1) {
            ntResult.setFail();
        }

        if(adminEmailCount == 0) {
            ntResult.setSuccess();
        }

        return ntResult;
    }

    public NTResult insertAdmin(
            Admin admin,
            String ip,
            Admin account
    ) {
        NTResult ntResult = new NTResult();
        admin.setUid(RandomStringUtils.randomAlphanumeric(16));

        Setting setting = settingService.selectSetting();

        //계정생성시, 기본으로 다음 비밀번호변경일을 셋팅
        String pwPeriod = NTUtil.isNull(setting.getPwPeriod(), "90");
        if(!"".equals(pwPeriod)) {
            admin.setNextPwModDate(pwPeriod);
        }

        if(!NTUtil.isEmpty(admin.getEmail())) {
            admin.setEmail(NTCryptoUtil.encrypt(admin.getEmail(), "EMAIL"));
        }

        if(!NTUtil.isEmpty(admin.getAdminPw())) {
            admin.setAdminPw(KISA_SHA256.Encrpyt(admin.getAdminPw()));
        }

        admin.setIsUse(NTUtil.isNull(admin.getIsUse(), "N"));
        try {
            int effected = adminMapper.insertAdmin(admin);
            if (effected > 0) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("uid", admin.getUid());
                adminActionLogService.insertAdminActionLog(AdminActionLog.builder()
                            .adminIndex(account.getAdminIndex())
                            .adminId(account.getAdminId())
                            .url("/api/v1/environment/admin")
                            .ip(ip)
                            .actionType("POST")
                            .actionTarget(admin.getAdminId())
                            .actionName("계정 생성")
                        .build()
                );
                ntResult.setData(dataMap);
                ntResult.setSuccess();
            } else {
                ntResult.setFail();
                ntResult.setResult("DATABASE ERROR");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ntResult;
    }

    public NTResult updateAdmin(
            Admin admin,
            String ip,
            String uid,
            Admin account
    ) {
        NTResult ntResult = new NTResult();
        if(!NTUtil.isEmpty(admin.getEmail())) {
            admin.setEmail(NTCryptoUtil.encrypt(admin.getEmail(), "EMAIL"));
        }

        if(!NTUtil.isEmpty(admin.getAdminPw())) {
            admin.setAdminPw(KISA_SHA256.Encrpyt(admin.getAdminPw()));
        }

        admin.setIsUse(NTUtil.isNull(admin.getIsUse(), "N"));

        try {

            int effected = adminMapper.updateAdmin(admin);
            if (effected > 0) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("uid", admin.getUid());

                adminActionLogService.insertAdminActionLog(AdminActionLog.builder()
                                .adminIndex(account.getAdminIndex())
                                .adminId(account.getAdminId())
                                .url("/api/v1/environment/admin/")
                                .ip(ip)
                                .actionType("PUT")
                                .actionTarget(admin.getAdminId())
                                .actionName("계정 수정")
                        .build()
                );
                ntResult.setData(dataMap);
                ntResult.setSuccess();
            } else {
                ntResult.setFail();
                ntResult.setResult("DATABASE ERROR");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ntResult;
    }

    public Admin findAdmin(String uid) {
        return adminMapper.selectAdmin(uid);
    }

    public Admin selectAdminById(String id) {
        return adminMapper.selectAdminById(id);
    }

    public int selectAdminTotalCount(Map<String, Object> reqMap) {
        return adminMapper.selectAdminTotalCount(reqMap);
    }

    public List<Admin> selectAdminList(Map<String, Object> reqMap) {
        return adminMapper.selectAdminList(reqMap);
    }

    public int insertAdminSession(Map map) {
        return adminMapper.insertAdminSession(map);
    }

    public String selectAdminSessionToken(Map map) {
        String sessionToken = null;
        try {
            sessionToken = adminMapper.selectAdminSessionToken(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionToken;
    }

    @Transactional
    public void deleteAdmin(String uid) {
        try {
            adminMapper.deleteAdmin(uid);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.INTERNAL_ERROR);
        }
    }
}
