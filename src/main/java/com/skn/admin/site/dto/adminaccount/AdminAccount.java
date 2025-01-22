package com.skn.admin.site.dto.adminaccount;

import com.skn.admin.base.dto.BaseDTO;
import com.skn.admin.site.dto.adminaccount.request.AdminAccountReqeust;
import com.skn.admin.util.KISA_SHA256;
import com.skn.admin.util.NTCryptoUtil;
import com.skn.admin.util.NTUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminAccount extends BaseDTO {
//    private String adminName;
//    private String department;
    private String adminPw;
    private String phoneNo;
    private String email;
    private String duty;
    private Integer adminGroupIndex;
    private String isUse;
    private String isSleep;
    private String ip;
    private String groupName;
    private String lastLoginDate;
    private String lastLoginIp;
    private LocalDate pwModDate;
    private LocalDate nextPwModDate;
    private Integer regAdminIndex;
    private String regAdminId;

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
    // 기록을 위한 값
    private String adminGroupName;

    public AdminAccount(AdminAccountReqeust.Insert req, String pwPeriod, String pwPeriodNext) {
        this.uid = req.getUid();
        this.adminGroupIndex = req.getAdminGroupIndex();
        this.adminId = req.getAdminId();
        this.adminPw = KISA_SHA256.Encrpyt(req.getAdminPw());
        this.adminName = req.getAdminName();
        this.phoneNo = NTCryptoUtil.encrypt(req.getPhoneNo(), "PHONE");
        this.department = req.getDepartment();
        this.duty = req.getDuty();
        this.email = NTCryptoUtil.encrypt(req.getEmail(), "EMAIL");
        this.adminType = req.getAdminType();
        this.isUse = req.getIsUse();
        this.regAdminId = req.getRegAdminId();
        this.regAdminIndex = req.getRegAdminIndex();
        this.pwModDate = setPwPeriod(pwPeriod);
        this.nextPwModDate = setPwPeriod(pwPeriodNext);
    }

    public AdminAccount(AdminAccountReqeust.Update req, String pwPeriod, String pwPeriodNext) {
        this.uid = req.getUid();
        this.adminGroupIndex = req.getAdminGroupIndex();
        this.adminId = req.getAdminId();
        this.adminPw = KISA_SHA256.Encrpyt(req.getAdminPw());
        this.adminName = req.getAdminName();
        this.phoneNo = NTCryptoUtil.encrypt(req.getPhoneNo(), "PHONE");
        this.department = req.getDepartment();
        this.duty = req.getDuty();
        this.email = NTCryptoUtil.encrypt(req.getEmail(), "EMAIL");
        this.adminType = req.getAdminType();
        this.isUse = req.getIsUse();
        this.regAdminId = req.getRegAdminId();
        this.regAdminIndex = req.getRegAdminIndex();
        this.pwModDate = setPwPeriod(pwPeriod);
        this.nextPwModDate = setPwPeriod(pwPeriodNext);
    }

    public String getEmailDecrypted() {
        if (!NTUtil.isEmpty(this.getEmail()))  this.emailDecrypted = NTCryptoUtil.decrypt(this.getEmail(), "EMAIL");

        return emailDecrypted;
    }

    public String getPhoneNoDecrypted() {
        if (!NTUtil.isEmpty(this.getPhoneNo()))  this.phoneNoDecrypted = NTCryptoUtil.decrypt(this.getPhoneNo(), "PHONE");

        return phoneNoDecrypted;
    }

    public LocalDate setPwPeriod(String period) {
        LocalDate now = LocalDate.now();
        Period periodDate = Period.ofDays(Integer.parseInt(period));
        return now.plus(periodDate);
    }
//    public void setIsUse(String val) {
//        this.isUse = (NTUtil.isNull(val, "N"));
//    }
//
//    public String getIsUse(String val) {
//        return NTUtil.isNull(this.isUse, "N");
//    }
//
//    public String getIsUseString() {
//        if(this.isUse.equals("Y"))
//        {
//            return "Active";
//        } else {
//            return "Blocked";
//        }
//    }
//
//    public long getPeriodModDate(){
//        long calDay = 0;
//        // 비밀번호 변경 시점 확인
//        try {
//            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date today = new Date();
//            Date lastModDate = transFormat.parse(getPwModDate());
//
//            long calDate = today.getTime() - lastModDate.getTime();
//            calDay = calDate/(24*60*60*1000);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return calDay;
//    }
//
    public boolean isNextModPeriodOver(){
        boolean result = false;
        try {
            LocalDate today = LocalDate.now();
            LocalDate nextModDate = this.nextPwModDate;

            long calDate = ChronoUnit.DAYS.between(today, nextModDate);
            if(calDate > 0 ) result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Boolean isInactiveForSixMonths(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String lastLoginDate = this.getLastLoginDate();
        if(!StringUtils.hasText(lastLoginDate)) {
            return false;
        }
        LocalDateTime date = LocalDateTime.parse(lastLoginDate, formatter);
        LocalDateTime oneHundredEightyDaysAgo = LocalDateTime.now().minus(180, ChronoUnit.DAYS);
        return date.isBefore(oneHundredEightyDaysAgo);
    }

    public AdminAccount decryptInfo() {
        this.emailDecrypted = this.email != null ? NTCryptoUtil.decrypt(this.email, "EMAIL") : null;
        this.phoneNoDecrypted = this.phoneNo != null ? NTCryptoUtil.decrypt(this.phoneNo, "PHONE") : null;

        return this;
    }
}
