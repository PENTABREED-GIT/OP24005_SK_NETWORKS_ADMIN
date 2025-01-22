package com.skn.admin.log.dto;

import com.skn.admin.base.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionLog extends BaseDTO {
    private String detail;
    private String actionType;
    private String ip;
    private LocalDate loginDate;
}
