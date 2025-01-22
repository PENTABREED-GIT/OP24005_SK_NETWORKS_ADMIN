package com.skn.admin.log.dto.request;


import com.skn.admin.environment.dto.Admin;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
public class RequestSettingDto {
    @Pattern(regexp = "^[YN]$", message = "Must be 'Y' or 'N'")
    private final String passwordNoticeIsUse;
    @Pattern(regexp = "^[0-9]{1,3}$", message = "Must be a valid period in days")
    private final String passwordNoticePeriod;
    @Pattern(regexp = "^[0-9]{1,3}$", message = "Must be a valid period in days")
    private final String passwordNoticePeriodNext;
    @Pattern(regexp = "^[YN]$", message = "Must be 'Y' or 'N'")
    private final String logoutNoticeIsUse;
    @Pattern(regexp = "^[0-9]{1,3}$", message = "Must be a valid period in minutes")
    private final String logoutPeriod;
    private final Integer adminIndex;
    private final String crDate;

    private static RequestSettingDto of (
            String passwordNoticeIsUse,
            String passwordNoticePeriod,
            String passwordNoticePeriodNext,
            String logoutNoticeIsUse,
            String logoutPeriod,
            Integer adminIndex,
            String crDate

    ) {
        return new RequestSettingDto(
                passwordNoticeIsUse,
                passwordNoticePeriod,
                passwordNoticePeriodNext,
                logoutNoticeIsUse,
                logoutPeriod,
                adminIndex,
                crDate
        );
    }

    public static RequestSettingDto toDTO (
            RequestSettingDto requestSettingDto
    ) {
        return RequestSettingDto.of(
                requestSettingDto.getPasswordNoticeIsUse(),
                requestSettingDto.getPasswordNoticePeriod(),
                requestSettingDto.getPasswordNoticePeriodNext(),
                requestSettingDto.getLogoutNoticeIsUse(),
                requestSettingDto.getLogoutPeriod(),
                requestSettingDto.getAdminIndex(),
                requestSettingDto.getCrDate()
        );
    }

    public static RequestSettingDto toDTO (
            RequestSettingDto requestSettingDto,
            Admin admin
    ) {
        String date = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .format(LocalDateTime.now())
                .toString();

        return RequestSettingDto.of(
                requestSettingDto.getPasswordNoticeIsUse(),
                requestSettingDto.getPasswordNoticePeriod(),
                requestSettingDto.getPasswordNoticePeriodNext(),
                requestSettingDto.getLogoutNoticeIsUse(),
                requestSettingDto.getLogoutPeriod(),
                admin.getAdminIndex(),
                date
        );
    }

    public static RequestSettingDto toEmpty() {
        return RequestSettingDto.of(
                "",
                "",
                "",
                "",
                "",
                null,
                ""
        );
    }
}
