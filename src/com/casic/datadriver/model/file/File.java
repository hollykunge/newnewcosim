package com.casic.datadriver.model.file;

public class File {
    private Integer ddFileId;

    private String ddFileName;

    private String ddFileSvrname;

    private String ddFileDescription;

    private Integer ddFileType;

    private Integer ddFileKeyword;

    public Integer getDdFileId() {
        return ddFileId;
    }

    public void setDdFileId(Integer ddFileId) {
        this.ddFileId = ddFileId;
    }

    public String getDdFileName() {
        return ddFileName;
    }

    public void setDdFileName(String ddFileName) {
        this.ddFileName = ddFileName;
    }

    public String getDdFileSvrname() {
        return ddFileSvrname;
    }

    public void setDdFileSvrname(String ddFileSvrname) {
        this.ddFileSvrname = ddFileSvrname;
    }

    public String getDdFileDescription() {
        return ddFileDescription;
    }

    public void setDdFileDescription(String ddFileDescription) {
        this.ddFileDescription = ddFileDescription;
    }

    public Integer getDdFileType() {
        return ddFileType;
    }

    public void setDdFileType(Integer ddFileType) {
        this.ddFileType = ddFileType;
    }

    public Integer getDdFileKeyword() {
        return ddFileKeyword;
    }

    public void setDdFileKeyword(Integer ddFileKeyword) {
        this.ddFileKeyword = ddFileKeyword;
    }
}