package com.skn.admin.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseFileDto {

    private String uid;
    private String fileIndex;
    private String fileName;
    private String fileExtension;
    private String fileSize;
    private String description;
    private String parentUid;
    private String parentTable;
    private String parentType;
    private String isSecure;
    private String sortOrder;
    private String modDate;
    private String regDate;
    private String downloadCount;
    private String fileType;
    private String filePath;

    public static ResponseFileDto of (
            String uid,
            String fileIndex,
            String fileName,
            String fileExtension,
            String fileSize,
            String description,
            String parentUid,
            String parentTable,
            String parentType,
            String isSecure,
            String sortOrder,
            String modDate,
            String regDate,
            String downloadCount,
            String fileType,
            String filePath
    ) {
        return new ResponseFileDto(
                uid,
                fileIndex,
                fileName,
                fileExtension,
                fileSize,
                description,
                parentUid,
                parentTable,
                parentType,
                isSecure,
                sortOrder,
                modDate,
                regDate,
                downloadCount,
                fileType,
                filePath
        );
    }

    public static ResponseFileDto toEmpty() {
        return ResponseFileDto.of(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );
    }
}

