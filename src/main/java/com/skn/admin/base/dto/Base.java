package com.skn.admin.base.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Base implements Serializable {
    private Integer adminIndex;
    private String adminId;
    private String regIp;
    private String updIp;
}
