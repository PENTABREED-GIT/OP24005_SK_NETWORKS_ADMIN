package com.skn.admin.base.dto;

import lombok.*;


@Setter
@Getter
public class BaseDTO {
    /**
     * UID
     * <pre>
     *     - 데이터베이스 내 테이블에서 레코드 접근을 위한 필드도 INDEX
     * </pre>
     */
    public String uid;
    public String idx;
    public String adminId;
    public Integer adminIndex;
    public String adminName;
    public Integer regAdminIndex;
    public Integer modAdminIndex;
    public String modDate;
    public String regDate;
    public String rowNumber;
    public String fileName;
    public String fileUrl;
    public String department; // 소속팀

    public String isOpen="N";
    public String lang;

    private int test;

    public String getLangName() {
        if("KO".equals(this.lang))
            return "국문";
        if("EN".equals(this.lang))
            return "영문";
        return this.lang;
    }
    
    public String getIsOpenName() {
        if("Y".equals(this.isOpen))
            return "노출";
        if("N".equals(this.isOpen))
            return "비노출";
        return this.isOpen;
    }
}
