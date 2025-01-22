package com.skn.admin.business.dto;

import com.skn.admin.base.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessArea {
    private String uid;
    private Integer businessAreaIndex;
    private String nameKo;
    private String nameEn;
}

