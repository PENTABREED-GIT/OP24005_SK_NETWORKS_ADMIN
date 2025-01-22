package com.skn.admin.environment.dto;

import com.skn.admin.base.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Setting extends BaseDTO {
    private String settingIndex;
    private String isUsePw;
    private String pwPeriod;
    private String pwPeriodNext;
    private String isUseLifetime;
    private String lifetime;
    private String isUseIp;
    private String siteIp;
    private String siteIpAll;
    private Integer adminIndex;
    private String crdate;

}
