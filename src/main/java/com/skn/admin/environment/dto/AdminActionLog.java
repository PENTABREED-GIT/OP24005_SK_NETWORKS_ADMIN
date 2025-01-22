package com.skn.admin.environment.dto;

import com.skn.admin.base.dto.BaseDTO;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminActionLog extends BaseDTO  {
    private String adminActionLogIndex;
    private String uid;
    private String adminId;
    private String url;
    private String url2;
    private String downloadReason;
    private String menuIndex;
    private String data;
    private String ip;
    private String actionType;
    private String adminName;
    private String location2;
    private String actionTarget;
    private String actionName;
    private Integer adminIndex;

    private String detail;
}
