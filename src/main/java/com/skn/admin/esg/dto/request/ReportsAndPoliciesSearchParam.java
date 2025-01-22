package com.skn.admin.esg.dto.request;

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
public class ReportsAndPoliciesSearchParam extends SearchParam {
    private int pageListSize = 30;
    private int offset = 0;

    public ReportsAndPoliciesSearchParam(LocalDate startDate, LocalDate endDate, String searchType, String searchWord, String isOpen, String lang, int pageListSize, int offset) {
        super(startDate, endDate, searchType, searchWord, isOpen, lang);
        this.pageListSize = pageListSize;
        this.offset = offset;
    }

    public static ReportsAndPoliciesSearchParam of(ReportsAndPoliciesSearchParam _param, Page page) {
        return new ReportsAndPoliciesSearchParam(
                _param.getStartDate(),
                _param.getEndDate(),
                _param.getSearchType(),
                _param.getSearchWord(),
                _param.getIsOpen(),
                _param.getLang(),
                page.getPageListSize(),
                page.getOffset()
        );
    }
}
