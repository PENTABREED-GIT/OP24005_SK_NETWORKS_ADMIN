package com.skn.admin.file.dto;

import lombok.Getter;


import java.util.ArrayList;

@Getter
public class FileInit {

    private String parentUid;
    private ArrayList<String> isSecure;
    private ArrayList<String> description;
    private ArrayList<String> sortOrder;
    private ArrayList<String> parentTable;
    private ArrayList<String> parentType;
    private String uid;

    private ArrayList<String> deleteUid;



    public FileInit setIsSecure(ArrayList<String> isSecure) {
        this.isSecure = isSecure;
        return this;
    }

    public FileInit setDescription(ArrayList<String> description) {
        this.description = description;
        return this;
    }

    public FileInit setSortOrder(ArrayList<String> sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public FileInit setParentTable(ArrayList<String> parentTable) {
        this.parentTable = parentTable;
        return this;
    }

    public FileInit setParentType(ArrayList<String> parentType) {
        this.parentType = parentType;
        return this;
    }

    public FileInit setParentUid(String uid) {
        this.parentUid = uid;
        return this;
    }

    public FileInit setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public FileInit setDeleteUid(ArrayList<String> deleteUid) {
        this.deleteUid = deleteUid;
        return this;
    }
}
