package com.skn.admin.ir.dto.request;

import com.skn.admin.base.dto.SearchParam;
import com.skn.admin.base.etc.Page;
import lombok.*;

import java.time.LocalDate;

/**
 * 검색 파라미터용 클래스
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class IrDataSearchParam extends SearchParam {
    private int pageListSize = 30;
    private int offset = 0;
    private String classification;
    private String dataLang;

    public IrDataSearchParam(LocalDate startDate, LocalDate endDate, String searchType, String searchWord, String isOpen, String lang, String classification, String dataLang, int pageListSize, int offset) {
        super(startDate, endDate, searchType, searchWord, isOpen, lang);
        this.classification = classification;
        this.dataLang = dataLang;
        this.pageListSize = pageListSize;
        this.offset = offset;
    }

    public static IrDataSearchParam of(IrDataSearchParam _param, Page page) {
        return new IrDataSearchParam(
                _param.getStartDate(),
                _param.getEndDate(),
                _param.getSearchType(),
                _param.getSearchWord(),
                _param.getIsOpen(),
                _param.getLang(),
                _param.getClassification(),
                _param.getDataLang(),
                page.getPageListSize(),
                page.getOffset()
        );
    }
}
