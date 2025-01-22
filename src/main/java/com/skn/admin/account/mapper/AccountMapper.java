package com.skn.admin.account.mapper;


import com.skn.admin.account.dto.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AccountMapper {
    Account login(Map map);
    int updatePasswordById(Map<String, String> param);
    void updatePasswordNextTime(String adminId, String pwPeriodNext);

    int updateAccount(Account account);

    int updateLoginFailCount(Map map);

    int updateUseYN(Map map);

    void updateLogInInfo(Map<String, String> loginMap);

    String selectAdminPassword(Map map);

    int updatePasswordByUid(Map modPwMap);
}
