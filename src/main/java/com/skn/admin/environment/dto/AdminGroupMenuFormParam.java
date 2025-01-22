package com.skn.admin.environment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AdminGroupMenuFormParam {
    private String uid;
    private Integer adminGroupIndex;
    private String groupName;
    private String description;
    private String adminId;
    private int adminIndex;
    private String isSuper;
    private String isUse;
    private String modDate;
    private String regDate;
    List<String> menuIndex;

}
