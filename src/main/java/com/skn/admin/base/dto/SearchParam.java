package com.skn.admin.base.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public abstract class SearchParam {
    private LocalDate startDate;
    private LocalDate endDate;
    private String searchType;
    private String searchWord;
    private String isOpen;
    private String lang;

    private int pageListSize = 30;
    private int offset = 0;

    public SearchParam(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SearchParam(LocalDate startDate, LocalDate endDate, String searchType, String searchWord, String isOpen, String lang) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.searchType = searchType;
        this.searchWord = searchWord;
        this.isOpen = isOpen;
        this.lang = lang;
    }

    private static LocalDate initDate(LocalDate date) {
        return date != null ? date : LocalDate.now();
    }
}
