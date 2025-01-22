package com.skn.admin.log.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseLoginLogListDto {
    private final String logIndex;
    private final String adminIndex;
    private final String adminId;
    private final String adminName;
    private final String loginDate;
    private final String ip;
    private final String result;


    private static ResponseLoginLogListDto of (
        String logIndex,
        String adminIndex,
        String adminId,
        String adminName,
        String loginDate,
        String ip,
        String result
    ) {
        return new ResponseLoginLogListDto(
            logIndex,
            adminIndex,
            adminId,
            adminName,
            loginDate,
            ip,
            result
        );
    }

    public static ResponseLoginLogListDto toDTO (
        String logIndex,
        String adminIndex,
        String adminId,
        String adminName,
        String loginDate,
        String ip,
        String result
    ) {
        return ResponseLoginLogListDto.of(
            logIndex,
            adminIndex,
            adminId,
            adminName,
            loginDate,
            ip,
            result
        );
    }

    public static ResponseLoginLogListDto toDTO (
        ResponseLoginLogListDto responseLoginLogDto
    ) {
        return ResponseLoginLogListDto.of(
            responseLoginLogDto.getLogIndex(),
            responseLoginLogDto.getAdminIndex(),
            responseLoginLogDto.getAdminId(),
            responseLoginLogDto.getAdminName(),
            responseLoginLogDto.getLoginDate(),
            responseLoginLogDto.getIp(),
            responseLoginLogDto.getResult()
        );
    }

    public static ResponseLoginLogListDto toEmpty() {
        return ResponseLoginLogListDto.of(
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        );
    }
}
