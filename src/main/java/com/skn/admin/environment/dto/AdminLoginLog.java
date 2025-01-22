package com.skn.admin.environment.dto;

import com.skn.admin.base.dto.BaseDTO;
import com.skn.admin.util.NTUtil;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLoginLog extends BaseDTO  {

    private String adminId;
    private String ip;
    private String loginDate;
    private String menu;
    private String result;
    private Integer adminIndex;

    public String getResult(){
        if (NTUtil.isEmpty(this.result)) {
            return "로그인";
        }
        return result;
    }
}
