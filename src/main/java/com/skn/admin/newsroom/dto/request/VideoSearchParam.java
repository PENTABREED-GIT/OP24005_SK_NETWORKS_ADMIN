package com.skn.admin.newsroom.dto.request;

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
public class VideoSearchParam extends SearchParam {
    private int pageListSize = 30;
    private int offset = 0;
    private String brand;

    public VideoSearchParam(LocalDate startDate, LocalDate endDate, String searchType, String searchWord, String isOpen, String lang, String brand, int pageListSize, int offset) {
        super(startDate, endDate, searchType, searchWord, isOpen, lang);
        this.brand = brand;
        this.pageListSize = pageListSize;
        this.offset = offset;
    }

    public static VideoSearchParam of(VideoSearchParam _param, Page page) {
        return new VideoSearchParam(
                _param.getStartDate(),
                _param.getEndDate(),
                _param.getSearchType(),
                _param.getSearchWord(),
                _param.getIsOpen(),
                _param.getLang(),
                _param.getBrand(),
                page.getPageListSize(),
                page.getOffset()
        );
    }
}
