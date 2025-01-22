package com.skn.admin.business.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessContentsDetail {
    private Integer businessContentsDetailIndex;
    private String uid;
    private Integer businessContentsIndex;
    private String detailTitle;
    private String detailContent;
    private String detailAdditionalContent;
}
