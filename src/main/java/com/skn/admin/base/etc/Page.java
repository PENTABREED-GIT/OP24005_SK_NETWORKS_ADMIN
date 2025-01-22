package com.skn.admin.base.etc;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Page {

    public static final int PAGE_BLOCK_SIZE = 5;
    private int currentPage;        // 현재 페이지

    private int offset;             // 보여줄 데이터 목록 시작 오프셋
    private int pageBlockSize;      // 페이지 블럭 크기

    private int totalPage;          // 총 페이지

    private int pageListSize;       // 페이지 당 목록 크기
    private int startOfPageBlock;   // 페이지 블럭의 시작 페이지
    private int endOfPageBlock;     // 페이지 블럭의 마지막 페이지
    private int totalCount;         // 총 목록 개수 (전체 레코드 카운트)

    public Page (
        int pageBlockSize,
        int currentPage,
        int pageListSize
    ) {
        this.currentPage = currentPage;
        this.pageBlockSize = pageBlockSize;
        this.pageListSize = pageListSize;
        this.offset = (this.currentPage - 1) * pageListSize;
        this.startOfPageBlock = setStartOfPageBlock();
    }

    public Page (
        int pageBlockSize,
        int currentPage,
        int pageListSize,
        int totalCount
    ) {
        this.currentPage = currentPage;
        this.pageBlockSize = pageBlockSize;
        this.pageListSize = pageListSize;
        this.offset = (this.currentPage - 1) * pageListSize;
        this.startOfPageBlock = setStartOfPageBlock();
        this.totalCount = totalCount;
    }

    /**
     * 총 페이지와 페이지 블럭의 마지막 페이지를 세팅
     *
     * @param totalCount    전체 수량
     * @return
     */
    public Page setPage(int totalCount) {
        this.totalPage = (int) Math.ceil((double) totalCount / this.pageListSize);
        this.endOfPageBlock = setEndOfPageBlock();
        return this;
    }

    /**
     * 페이지 블럭의 첫 페이지 번호를 조회
     *
     * @return
     */
    private int setStartOfPageBlock() {
        return (this.currentPage - 1) / pageBlockSize * pageBlockSize + 1;
    }

    /**
     * 페이지 블럭의 마지막 페이지 번호를 조회
     *
     * @return
     */
    private int setEndOfPageBlock() {
        return Math.min((this.currentPage - 1) / pageBlockSize * pageBlockSize + pageBlockSize, this.totalPage);
    }
}

