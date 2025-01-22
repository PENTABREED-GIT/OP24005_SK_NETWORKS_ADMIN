package com.skn.admin.site.dto;

import com.skn.admin.base.dto.BaseDTO;
import com.skn.admin.site.dto.adminprivilege.AdminPrivilege;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminGroup extends BaseDTO {
//    private Integer adminGroupLogIndex;
    private Integer adminGroupIndex;
    private String groupName;
    private String description;
    private String adminId;
    private Integer adminIndex;
    private String isSuper;
    private String isUse;
    private String isMain;
    List<AdminMenu> adminGroupMenuList;
    List<AdminGroupPermission> adminGroupPermissionList;

    private AdminGroup(
        String uid,
        String groupName,
        String description,
        String adminId,
        Integer adminIndex,
        String isSuper,
        String isUse,
        String isMain
    ) {
        this.uid = uid;
        this.groupName = groupName;
        this.description = description;
        this.adminId = adminId;
        this.adminIndex = adminIndex;
        this.isSuper = initIsSuper(isSuper);
        this.isUse = isUse;
        this.isMain = isMain;
    }
    public static AdminGroup of(AdminPrivilege adminPrivilege) {
        return new AdminGroup(
            adminPrivilege.getUid(),
            adminPrivilege.getGroupName(),
            adminPrivilege.getDescription(),
            adminPrivilege.getAdminId(),
            adminPrivilege.getAdminIndex(),
            adminPrivilege.getIsSuper(),
            adminPrivilege.getIsUse(),
            adminPrivilege.getIsMain()
        );
    }

    public String initIsSuper(String isSuper) {
        return StringUtils.hasText(isSuper) ? isSuper : "N";
    }
}
