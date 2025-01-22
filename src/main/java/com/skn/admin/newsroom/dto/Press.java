package com.skn.admin.newsroom.dto;

import com.skn.admin.base.dto.BaseDTO;
import com.skn.admin.business.dto.BusinessArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Press extends BaseDTO {
    private Integer pressIndex;
    private String isPress="N";
    private String isIrNews="N";
    private String isBusiness="N";
    private String title;
    private String content;
    private String isOpenBadge;
    private Integer businessAreaIndex;
    private String businessAreaNameKo;
    private String businessAreaNameEn;
    public String getIsOpenBadge() {
        if("Y".equals(this.isOpen))
            return "<span class=\"badge py-2 px-3 fs-7 badge-light-primary\">노출</span>";
        return "<span class=\"badge py-2 px-3 fs-7 badge-light\">비노출</span>";
    }

}

