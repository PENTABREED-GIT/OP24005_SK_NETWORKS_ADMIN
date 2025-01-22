package com.skn.admin.ir.dto;

import com.skn.admin.base.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuditReport extends BaseDTO {
    private Integer auditReportIndex;
    private String year;
    private String isOpenBadge;
    public String getIsOpenBadge() {
        if("Y".equals(this.isOpen))
            return "<span class=\"badge py-2 px-3 fs-7 badge-light-primary\">노출</span>";
        return "<span class=\"badge py-2 px-3 fs-7 badge-light\">비노출</span>";
    }

}

