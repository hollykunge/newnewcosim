package com.casic.datadriver.model.file;

public class FileAssociation {
    private Integer ddFileRassociationId;

    private Integer ddFileId;

    private Integer ddProjectId;

    private Integer ddTaskId;

    public Integer getDdFileRassociationId() {
        return ddFileRassociationId;
    }

    public void setDdFileRassociationId(Integer ddFileRassociationId) {
        this.ddFileRassociationId = ddFileRassociationId;
    }

    public Integer getDdFileId() {
        return ddFileId;
    }

    public void setDdFileId(Integer ddFileId) {
        this.ddFileId = ddFileId;
    }

    public Integer getDdProjectId() {
        return ddProjectId;
    }

    public void setDdProjectId(Integer ddProjectId) {
        this.ddProjectId = ddProjectId;
    }

    public Integer getDdTaskId() {
        return ddTaskId;
    }

    public void setDdTaskId(Integer ddTaskId) {
        this.ddTaskId = ddTaskId;
    }
}