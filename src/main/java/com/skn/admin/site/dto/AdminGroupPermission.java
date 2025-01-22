package com.skn.admin.site.dto;

import com.skn.admin.site.dto.adminprivilege.AdminPrivilege;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminGroupPermission {
    private Integer adminGroupIndex;
    private Integer menuIndex;
    private String adminId;
    private Integer adminIndex;
    private String permission;
    private String regDate;
    private String modDate;

    public AdminGroupPermission(
        Integer adminGroupIndex,
        Integer menuIndex,
        String adminId,
        Integer adminIndex,
        String permission
    ) {
        this.adminGroupIndex = adminGroupIndex;
        this.menuIndex = menuIndex;
        this.adminId = adminId;
        this.adminIndex = adminIndex;
        this.permission = permission;
    }

    public static AdminGroupPermission of(AdminPrivilege adminPrivilege, Integer menuIndex, String permission) {
        return new AdminGroupPermission(
            adminPrivilege.getAdminGroupIndex(),
            menuIndex,
            adminPrivilege.getAdminId(),
            adminPrivilege.getAdminIndex(),
            permission
        );
    }
}
