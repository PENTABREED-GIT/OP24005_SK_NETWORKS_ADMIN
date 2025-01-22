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
public class LoginLog extends BaseDTO {
    private String logIndex;
    private LocalDate loginDate;
    private String ip;
    private String result;
}
