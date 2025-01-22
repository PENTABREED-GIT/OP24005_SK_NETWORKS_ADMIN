package com.skn.admin.file.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileInfo {
    private String uid = "";
    private String fileIndex = "";
    private String fileName = "";
    private String fileExtension = "";
    private String fileSize = "";
    private String description;
    private String parentUid = "";
    private String parentIndex = "";
    private String parentTable = "";
    private String parentType = "";
    private String isSecure = "";
    private String sortOrder = "";
    private String modDate = "";
    private String regDate = "";
    private String tempKey = "";
    private String downloadCount = "";
    private String fileType = "";
    private String filePath = "";
    private String type = "";

    public String getSaveFilePath() {
        String fileType = "";
        if("Y".equals(this.getIsSecure())) {
            fileType = "secure";
        } else {
            fileType = "public";
        }
        String filePath = "/" + fileType + "/" + this.getParentTable().toLowerCase().replace("_","-") + "/" + this.getParentUid() + "/" + this.uid+"."+ this.fileExtension;
        return filePath;
    }

    public FileInfo setOriginalFilePath() {
        String fileType = "Y".equals(this.getIsSecure()) ? "secure" : "public";

        if (this.parentTable == null || this.parentTable.isEmpty() ||
                this.parentUid == null || this.parentUid.isEmpty() ||
                this.uid == null || this.uid.isEmpty() ||
                this.fileExtension == null || this.fileExtension.isEmpty()) {
            this.filePath = "";
        } else {
            this.filePath = "/" + fileType + "/" + this.parentTable.replaceAll("_", "-").toLowerCase() + "/" + this.parentUid + "/" + this.uid + "." + this.fileExtension;
        }

        return this;
    }

    public String getConversionPdfPath() {
        String filePath = "/" + this.getParentTable() + "/" + this.getParentUid() + "/" + this.getUid() + "." + "pdf";
        return filePath;
    }

    public String getConversionPdfDirectory() {
        String filePath = "/" + this.getParentTable() + "/" + this.getParentUid() + "/";
        return filePath;
    }

    private String imageExt = "jpg;jpeg;gif;bmp;png;apng;webp;";
    private String docExt = "docx;xls;xlsx;ppt;pptx;pdf;txt;";

    public String getFileType() {
        if(imageExt.indexOf(this.getFileExtension().toLowerCase()) >= 0 )
        {
            this.setFileType("image");
        } else if(docExt.indexOf(this.getFileExtension().toLowerCase()) >= 0) {
            this.setFileType("file");
        }
        return fileType;
    }

    public String getSavedFileName() {
        return this.getUid() + "." + this.getFileExtension();
    }

    public String getFileDirectory() {
        return this.getParentTable() + "/" + this.getParentUid() + "/";
    }
}

