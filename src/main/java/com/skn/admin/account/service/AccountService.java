package com.skn.admin.account.service;

import com.skn.admin.account.dto.Account;
import com.skn.admin.account.mapper.AccountMapper;
import com.skn.admin.base.dto.BaseDTO;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.environment.dto.AdminActionLog;
import com.skn.admin.environment.dto.Setting;
import com.skn.admin.environment.mapper.AdminActionLogMapper;
import com.skn.admin.environment.service.AdminService;
import com.skn.admin.environment.service.SettingService;
import com.skn.admin.util.KISA_SHA256;
import com.skn.admin.util.NTCryptoUtil;
import com.skn.admin.util.NTResult;
import com.skn.admin.util.NTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountMapper accountMapper;
    private final AdminService adminService;
    private final SettingService settingService;
    private final AdminActionLogMapper adminActionLogMapper;

    public int updatePasswordById(Map<String, String> param) {
        return accountMapper.updatePasswordById(param);
    }

    public void updateNextPwModDate(String adminId) {

        Setting setting = settingService.selectSetting();

        accountMapper.updatePasswordNextTime(adminId, setting.getPwPeriodNext());
    }


	public NTResult updateAccountByAdminId(String adminId, String ip, String url, Map<String, String> reqMap) {
        NTResult result = new NTResult();
        Admin admin = adminService.selectAdminById(adminId);
        if (ObjectUtils.isEmpty(admin)) {
            return result;
        }
        Integer adminIndex = admin.getAdminIndex();
        String adminName = admin.getAdminName();
        String email = (!NTUtil.isEmpty(reqMap.get("email"))) ? NTCryptoUtil.encrypt(reqMap.get("email"), "EMAIL") : admin.getEmail();
        String adminPw = null;
        if(!NTUtil.isEmpty(reqMap.get("adminPw"))) {
            adminPw = KISA_SHA256.Encrpyt(admin.getAdminPw());
        } else {
            if(reqMap.get("adminPw").equals(reqMap.get("pwConfirm"))) {
                adminPw = reqMap.get("adminPw");
            } else {
                return result;
            }
        }

        int effected = accountMapper.updateAccount(Account.builder()
                        .adminId(adminId)
                        .adminName(adminName)
                        .adminPw(adminPw)
                        .duty(reqMap.get("duty"))
                        .department(reqMap.get("department"))
                        .email(email)
                        .ip(ip)
                    .build());
        if(effected > 0) {
            adminActionLogMapper.insertAdminActionLog(AdminActionLog.builder()
                            .adminId(adminId)
                            .url(url)
                            .ip(ip)
                            .actionType("PUT")
                            .actionTarget(adminId)
                            .actionName("Modify Admin Account")
                            .adminIndex(adminIndex)
                    .build()
            );
            result.setSuccess();
        }
        return result;
	}

    public void updateLogInInfo(Map<String, String> loginMap) {
        accountMapper.updateLogInInfo(loginMap);
    }

    public String selectAdminPassword(Map map) {
        return accountMapper.selectAdminPassword(map);
    }

    public NTResult updatePasswordByUid(String uid, String ip, String pwPeriod, String currentPassword, String newPassword) {
        NTResult result = new NTResult();
        Map map = new HashMap();
        map.put("adminUid", uid);
        String pwOrigin = accountMapper.selectAdminPassword(map);

        if (pwOrigin.equals(KISA_SHA256.Encrpyt(newPassword))) {
            result.setFail();
            result.setMsg("Same as previous password.");
            return result;
        }

        Map modPwMap = new HashMap();
        modPwMap.put("adminUid", uid);
        modPwMap.put("pwPeriod", pwPeriod);
        modPwMap.put("ip", ip);
        modPwMap.put("modPwd", KISA_SHA256.Encrpyt(newPassword));

        int rs = accountMapper.updatePasswordByUid(modPwMap);

        if (rs >= 0) {
            result.setSuccess();
            result.setMsg("");
        } else {
            result.setFail();
            result.setMsg("Password update fail");
        }
        return result;
    }
}
