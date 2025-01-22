package com.skn.admin.business.dto.request;

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
public class BusinessContentsSearchParam extends SearchParam {
    private String businessAreaIndex;
    private int pageListSize = 30;
    private int offset = 0;

    public BusinessContentsSearchParam(LocalDate startDate, LocalDate endDate, String searchType, String searchWord, String isOpen, String lang, String businessAreaIndex, int pageListSize, int offset) {
        super(startDate, endDate, searchType, searchWord, isOpen, lang);
        this.businessAreaIndex = businessAreaIndex;
        this.pageListSize = pageListSize;
        this.offset = offset;
    }

    public static BusinessContentsSearchParam of(BusinessContentsSearchParam _param, Page page) {
        return new BusinessContentsSearchParam(
                _param.getStartDate(),
                _param.getEndDate(),
                _param.getSearchType(),
                _param.getSearchWord(),
                _param.getIsOpen(),
                _param.getLang(),
                _param.getBusinessAreaIndex(),
                page.getPageListSize(),
                page.getOffset()
        );
    }
}
