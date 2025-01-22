package com.skn.admin.customer.dto.request;

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
public class InquirySearchParam extends SearchParam {
    private int pageListSize = 30;
    private int offset = 0;
    private String dateType;
    private String status;
    private String classification;


    public InquirySearchParam(LocalDate startDate, LocalDate endDate, String searchType, String searchWord, String isOpen, String lang, String dateType, String status, String classification, int pageListSize, int offset) {
        super(startDate, endDate, searchType, searchWord, isOpen, lang);
        this.dateType = dateType;
        this.status = status;
        this.classification = classification;
        this.pageListSize = pageListSize;
        this.offset = offset;
    }

    public static InquirySearchParam of(InquirySearchParam _param, Page page) {
        return new InquirySearchParam(
                _param.getStartDate(),
                _param.getEndDate(),
                _param.getSearchType(),
                _param.getSearchWord(),
                _param.getIsOpen(),
                _param.getLang(),
                _param.getDateType(),
                _param.getStatus(),
                _param.getClassification(),
                page.getPageListSize(),
                page.getOffset()

        );
    }
}
