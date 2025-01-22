package com.skn.admin.log.dto.request;

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
public class ActionLogSearchParam extends SearchParam {
    private String ip;
    private LocalDate loginDate;
    private String type;
    private String actionType;
    private String adminId;
    private String adminIndex;
    private String adminName;
    private int pageListSize = 30;
    private int offset = 0;

    public ActionLogSearchParam(LocalDate startDate, LocalDate endDate, String searchType, String searchWord, String isOpen, String lang, String type, int pageListSize, int offset) {
        super(startDate, endDate, searchType, searchWord, isOpen, lang);
        this.type = type;
        this.pageListSize = pageListSize;
        this.offset = offset;
    }

    public static ActionLogSearchParam of(ActionLogSearchParam _param, Page page) {
        return new ActionLogSearchParam(
                _param.getStartDate(),
                _param.getEndDate(),
                _param.getSearchType(),
                _param.getSearchWord(),
                _param.getIsOpen(),
                _param.getLang(),
                _param.getType(),
                page.getPageListSize(),
                page.getOffset()
        );
    }
}
