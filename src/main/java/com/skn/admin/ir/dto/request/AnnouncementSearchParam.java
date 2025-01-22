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
public class AnnouncementSearchParam extends SearchParam {
    private String isTop;
    private int pageListSize = 30;
    private int offset = 0;

    public AnnouncementSearchParam(LocalDate startDate, LocalDate endDate, String searchType, String searchWord, String isOpen, String lang, String isTop, int pageListSize, int offset) {
        super(startDate, endDate, searchType, searchWord, isOpen, lang);
        this.isTop = isTop;
        this.pageListSize = pageListSize;
        this.offset = offset;
    }

    public static AnnouncementSearchParam of(AnnouncementSearchParam _param, Page page) {
        return new AnnouncementSearchParam(
                _param.getStartDate(),
                _param.getEndDate(),
                _param.getSearchType(),
                _param.getSearchWord(),
                _param.getIsOpen(),
                _param.getLang(),
                _param.getIsTop(),
                page.getPageListSize(),
                page.getOffset()
        );
    }
}
