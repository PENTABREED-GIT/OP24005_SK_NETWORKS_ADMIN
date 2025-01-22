package com.skn.admin.ir.dto;

import com.skn.admin.base.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IrData extends BaseDTO {
    private Integer irDataIndex;
    private String dataLang;
    private String title;
    private String classification;
    private String isOpenBadge;
    public String getIsOpenBadge() {
        if("Y".equals(this.isOpen))
            return "<span class=\"badge py-2 px-3 fs-7 badge-light-primary\">노출</span>";
        return "<span class=\"badge py-2 px-3 fs-7 badge-light\">비노출</span>";
    }
    public String getDataLangName() {
        if("KO".equals(this.dataLang))
            return "국문";
        if("EN".equals(this.dataLang))
            return "영문";
        return this.dataLang;
    }
}

