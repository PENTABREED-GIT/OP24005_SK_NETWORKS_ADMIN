package com.skn.admin.account.dto;

import com.skn.admin.site.dto.AdminMenu;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminGroupPermission extends AdminMenu {
    private Integer adminGroupIndex;
    private String regDate;
    private String modDate;
}
