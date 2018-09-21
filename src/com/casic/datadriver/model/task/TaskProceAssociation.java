package com.casic.datadriver.model.task;

public class TaskProceAssociation {
    private Integer ddTaskProcessAssociationId;

    private Integer ddTaskId;

    private Integer ddProcessId;

    public Integer getDdTaskProcessAssociationId() {
        return ddTaskProcessAssociationId;
    }

    public void setDdTaskProcessAssociationId(Integer ddTaskProcessAssociationId) {
        this.ddTaskProcessAssociationId = ddTaskProcessAssociationId;
    }

    public Integer getDdTaskId() {
        return ddTaskId;
    }

    public void setDdTaskId(Integer ddTaskId) {
        this.ddTaskId = ddTaskId;
    }

    public Integer getDdProcessId() {
        return ddProcessId;
    }

    public void setDdProcessId(Integer ddProcessId) {
        this.ddProcessId = ddProcessId;
    }
}