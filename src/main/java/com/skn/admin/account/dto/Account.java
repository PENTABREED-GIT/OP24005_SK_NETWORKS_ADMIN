package com.skn.admin.account.dto;


import com.skn.admin.base.dto.BaseDTO;
import com.skn.admin.util.NTUtil;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseDTO {
    private String adminId;
    private String adminName;
    private String adminPw;
    private String email;
    private String department;
    private String duty;
    private String adminGroupIndex;
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

    private String oldAdminGroupIndex; // 기존 관리자 그룹
    private String oldAdminGroupName; // 기존 관리자 그룹
    private String newAdminGroupName; // 변경 후 관리자 그룹
    private String newAdminGroupIndex; // 변경 후 관리자 그룹
    private String crAdminName; // 변경 대상 관리자 이름
    private String crAdminId; // 변경 대상 관리자 아이디
    private Integer crAdminIndex; // 변경 대상 관리자 인덱스

    public void setIsUse(String val) {
        this.isUse = (NTUtil.isNull(val, "N"));
    }

    public String getIsUse(String val) {
        return NTUtil.isNull(this.isUse, "N");
    }
}
