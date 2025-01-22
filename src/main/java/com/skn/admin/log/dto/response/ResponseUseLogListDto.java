package com.skn.admin.log.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseUseLogListDto {
    private final String uid;
    private final String detail;
    private final String actionType;
    private final String adminId;
    private final String adminName;
    private final String ip;
    private final String regDate;

    private static ResponseUseLogListDto of(
            String uid,
            String detail,
            String actionType,
            String adminId,
            String adminName,
            String ip,
            String regDate
    ) {
        return new ResponseUseLogListDto(
                uid,
                detail,
                actionType,
                adminId,
                adminName,
                ip,
                regDate
        );
    }

    public static ResponseUseLogListDto toDTO (
            String uid,
            String detail,
            String actionType,
            String adminId,
            String adminName,
            String ip,
            String regDate
    ) {
        return ResponseUseLogListDto.of(
                uid,
                detail,
                actionType,
                adminId,
                adminName,
                ip,
                regDate
        );
    }

    public static ResponseUseLogListDto toDTO (
            ResponseUseLogListDto responseUseLogListDto
    ) {
        return ResponseUseLogListDto.of(
                responseUseLogListDto.getUid(),
                responseUseLogListDto.getDetail(),
                responseUseLogListDto.getActionType(),
                responseUseLogListDto.getAdminId(),
                responseUseLogListDto.getAdminName(),
                responseUseLogListDto.getIp(),
                responseUseLogListDto.getRegDate()
        );
    }

    public static ResponseUseLogListDto toEmpty() {
        return ResponseUseLogListDto.of(
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
