package com.skn.admin.log.dto.request;

import com.skn.admin.base.dto.SearchParam;
import com.skn.admin.base.etc.Page;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 검색 파라미터용 클래스
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class LoginLogSearchParam extends SearchParam {
    private String logIndex;
    private LocalDate loginDate;
    private String ip;
    private String type;
    private String result;
    private String adminId;
    private String adminIndex;
    private String adminName;
    private int pageListSize = 30;
    private int offset = 0;

    public LoginLogSearchParam(LocalDate startDate, LocalDate endDate, String searchType, String searchWord, String isOpen, String lang, String type, int pageListSize, int offset) {
        super(startDate, endDate, searchType, searchWord, isOpen, lang);
        this.type = type;
        this.pageListSize = pageListSize;
        this.offset = offset;
    }

    public static LoginLogSearchParam of(LoginLogSearchParam _param, Page page) {
        return new LoginLogSearchParam(
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
