package com.skn.admin.account.dto;

import com.skn.admin.base.dto.BaseDTO;
import com.skn.admin.base.dto.SearchParam;
import com.skn.admin.site.dto.AdminMenu;
import lombok.*;

import java.util.List;

/**
 * 관리자 그룹 엔티티 클래스
 * @author 유경진
 * @version 1.0.0
 * <pre>
 *
 * </pre>
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminGroup extends BaseDTO  {
    private Integer adminGroupLogIndex;
    private Integer adminGroupIndex;
    private String groupName;
    private String description;
    private String adminId;
    private Integer adminIndex;
    private String isSuper;
    private String isUse;
    private String isMain;

    private List<AdminGroupPermission> adminGroupPermissionList;

    @Getter
    @Setter
    public static class AdminGroupSearchParam extends SearchParam {
        private String isUse;
        private String searchWord;

        public void setIsUse(String isUse) {
            if ("전체".equals(isUse)) {
                this.isUse = "";
            } else {
                this.isUse = isUse;
            }
        }
    }
}
