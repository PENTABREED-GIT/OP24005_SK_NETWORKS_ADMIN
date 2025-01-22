package com.skn.admin.ir.dto;

import com.skn.admin.base.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IrSchedule extends BaseDTO {
    private Integer irScheduleIndex;
    private String irScheduleType;
    private String title;
    private String classification;
    private String openDate;
    private String openTime;
    private LocalDateTime openDateTime;
    private String isOpenBadge;
    public String getIsOpenBadge() {
        if("Y".equals(this.isOpen))
            return "<span class=\"badge py-2 px-3 fs-7 badge-light-primary\">노출</span>";
        return "<span class=\"badge py-2 px-3 fs-7 badge-light\">비노출</span>";
    }

    public LocalDateTime getOpenDateTime() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
            return LocalDateTime.parse(this.openDate+" "+this.openTime, formatter);
        } catch(Exception e) {
            return null;
        }
    }

    public String getOpenDate() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        // 지정한 형식으로 날짜 출력
        String formattedDate = this.openDateTime.format(formatter);
        return formattedDate;
            //return this.openDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")).toString();
        } catch(Exception e) {
            return "";
        }
    }

    public String getOpenTime() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // 지정한 형식으로 날짜 출력
        String formattedDate = this.openDateTime.format(formatter);
        return formattedDate;
            //return this.openDateTime.format(DateTimeFormatter.ofPattern("HH:mm")).toString();
        } catch(Exception e) {
            return "";
        }
    }

    public String getClassificationName() {
        if("DOMESTIC".equals(this.classification))
            return "국내";
        if("OVERSEAS".equals(this.classification))
            return "해외";
        return this.classification;
    }
}

