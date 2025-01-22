package com.skn.admin.sample.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class DecodeValue {

    private Integer inqIdx;
    private long inqParIdx;
    private long inqCouIdx;
    
    private int inqRepIdx;

    private String inqTit;
    private String inqCon;
    private String inqNam;
    private String inqEma;
    private String inqIp;
    private String inqTyp;
    private String inqCouCod;
    private String inqOffNam;
    private String inqTel;

    private String inqAgree1;
    
    private String inqReg;
    private String inqUpd;
	
}
