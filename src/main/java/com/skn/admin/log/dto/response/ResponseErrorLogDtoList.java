package com.skn.admin.log.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseErrorLogDtoList {
    private String errorLogIndex;
    private String code;
    private String title;
    private String page;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String option5;
    private String text;
    private String ip;
    private String regDate;

    private static ResponseErrorLogDtoList of (
            String errorLogIndex,
            String code,
            String title,
            String page,
            String option1,
            String option2,
            String option3,
            String option4,
            String option5,
            String text,
            String ip,
            String regDate
    ) {

        return new ResponseErrorLogDtoList(
                errorLogIndex,
                code,
                title,
                page,
                option1,
                option2,
                option3,
                option4,
                option5,
                text,
                ip,
                regDate
        );
    }

    public static ResponseErrorLogDtoList toDTO (
            String errorLogIndex,
            String code,
            String title,
            String page,
            String option1,
            String option2,
            String option3,
            String option4,
            String option5,
            String text,
            String ip,
            String regDate
    ) {

        return of(
                errorLogIndex,
                code,
                title,
                page,
                option1,
                option2,
                option3,
                option4,
                option5,
                text,
                ip,
                regDate
        );
    }

    public static ResponseErrorLogDtoList toEmpty() {

        return of(
                "",
                "",
                "",
                "",
                "",
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
