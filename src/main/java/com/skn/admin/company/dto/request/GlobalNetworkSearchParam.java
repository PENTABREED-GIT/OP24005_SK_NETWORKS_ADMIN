package com.skn.admin.company.dto.request;

import com.skn.admin.base.etc.Page;
import lombok.*;

/**
 * 검색 파라미터용 클래스
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class GlobalNetworkSearchParam {

    private String isEn;
    private String companyName;
    private String classification;
    private String regionNameKo;
    private String isOpen;
    private int pageListSize = 30;
    private int offset = 0;


    public static GlobalNetworkSearchParam of(GlobalNetworkSearchParam _param, Page page) {
        return new GlobalNetworkSearchParam(
            _param.getIsEn(),
            _param.getCompanyName(),
            _param.getClassification(),
            _param.getRegionNameKo(),
            _param.getIsOpen(),
            page.getPageListSize(),
            page.getOffset()
        );
    }
}
