package com.skn.admin.environment.dto;

import com.skn.admin.base.dto.BaseDTO;
import com.skn.admin.util.NTCryptoUtil;
import com.skn.admin.util.NTUtil;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin extends BaseDTO {
    private String adminName;
    private String adminPw;
    private String email;
    private String department;
    private String duty;
    private Integer adminGroupIndex;
    private String isUse;
    private String isSleep;
    private String ip;
    private String groupName;
    private String lastLoginDate;
    private String lastLoginIp;
    private String pwModDate;
    private String nextPwModDate;
    private Integer regAdminIndex;
    private String regAdminId;
    private String regAdminName;

    private String emailDecrypted;
    private String phoneNoDecrypted;

    private String adminType;

    private String isOverModDate;
    private String isFirstLogin;
    private int loginFailCount;

    private Integer oldAdminGroupIndex; // 기존 관리자 그룹
    private String oldAdminGroupName; // 기존 관리자 그룹
    private String newAdminGroupName; // 변경 후 관리자 그룹
    private Integer newAdminGroupIndex; // 변경 후 관리자 그룹
    private String crAdminName; // 변경 대상 관리자 이름
    private String crAdminId; // 변경 대상 관리자 아이디
    private Integer crAdminIndex; // 변경 대상 관리자 인덱스

    private String adminNameAndDepartment;

    public String getAdminNameAndDepartment() {
        return this.adminName + "(" + this.department + ")";
    }

    public String getEmailDecrypted() {
        if (!NTUtil.isEmpty(this.getEmail()))  this.emailDecrypted = NTCryptoUtil.decrypt(this.getEmail(), "EMAIL");

        return emailDecrypted;
    }

    public void setIsUse(String val) {
        this.isUse = (NTUtil.isNull(val, "N"));
    }

    public String getIsUse(String val) {
        return NTUtil.isNull(this.isUse, "N");
    }

    public String getIsUseString() {
        if(this.isUse.equals("Y"))
        {
            return "Active";
        } else {
            return "Blocked";
        }
    }

    public long getPeriodModDate(){
        long calDay = 0;
        // 비밀번호 변경 시점 확인
        try {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date today = new Date();
            Date lastModDate = transFormat.parse(getPwModDate());

            long calDate = today.getTime() - lastModDate.getTime();
            calDay = calDate/(24*60*60*1000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return calDay;
    }

    public boolean isNextModPeriodOver(){
        boolean result = false;
        try {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date today = new Date();
            Date nextModDate = transFormat.parse(getNextPwModDate());

            long calDate = today.getTime() - nextModDate.getTime();
            if(calDate > 0 ) result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getAdminNameWithDept(){
        String adminNameWithDept = adminName + "(" + department + ")";
        return adminNameWithDept;
    }
}
