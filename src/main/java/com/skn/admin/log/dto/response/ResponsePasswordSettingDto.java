package com.skn.admin.log.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponsePasswordSettingDto {
    private final String passwordNoticeIsUse;
    private final String passwordNoticePeriod;
    private final String passwordNoticePeriodNext;
    private final String logoutNoticeIsUse;
    private final String logoutPeriod;

    private static ResponsePasswordSettingDto of (
            String passwordNoticeIsUse,
            String passwordNoticePeriod,
            String passwordNoticePeriodNext,
            String logoutNoticeIsUse,
            String logoutPeriod
    ) {
        return new ResponsePasswordSettingDto(
                passwordNoticeIsUse,
                passwordNoticePeriod,
                passwordNoticePeriodNext,
                logoutNoticeIsUse,
                logoutPeriod
        );
    }

    public static ResponsePasswordSettingDto toDTO (
            ResponsePasswordSettingDto responsePasswordSettingDto
    ) {
        return ResponsePasswordSettingDto.of(
                responsePasswordSettingDto.getPasswordNoticeIsUse(),
                responsePasswordSettingDto.getPasswordNoticePeriod(),
                responsePasswordSettingDto.getPasswordNoticePeriodNext(),
                responsePasswordSettingDto.getLogoutNoticeIsUse(),
                responsePasswordSettingDto.getLogoutPeriod()
        );
    }

    public static ResponsePasswordSettingDto toEmpty() {
        return ResponsePasswordSettingDto.of(
                "",
                "",
                "",
                "",
                ""
        );
    }
}
