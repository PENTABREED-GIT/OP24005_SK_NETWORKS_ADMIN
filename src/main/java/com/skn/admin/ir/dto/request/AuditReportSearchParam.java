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
public class AuditReportSearchParam extends SearchParam {
    private String year;
    private int pageListSize = 30;
    private int offset = 0;

    public AuditReportSearchParam(LocalDate startDate, LocalDate endDate, String searchType, String searchWord, String isOpen, String lang, String year, int pageListSize, int offset) {
        super(startDate, endDate, searchType, searchWord, isOpen, lang);
        this.year = year;
        this.pageListSize = pageListSize;
        this.offset = offset;
    }

    public static AuditReportSearchParam of(AuditReportSearchParam _param, Page page) {
        return new AuditReportSearchParam(
                _param.getStartDate(),
                _param.getEndDate(),
                _param.getSearchType(),
                _param.getSearchWord(),
                _param.getIsOpen(),
                _param.getLang(),
                _param.getYear(),
                page.getPageListSize(),
                page.getOffset()
        );
    }
}
