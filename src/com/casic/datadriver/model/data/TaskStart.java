package com.casic.datadriver.model.data;

public class TaskStart {
    private Integer ddTaskStartId;

    private Integer ddProjectStartId;

    private Integer ddTaskId;

    private Integer actInstId;

    private Short ddTaskStatus;

    private Integer sortOrder;

    public Integer getDdTaskStartId() {
        return ddTaskStartId;
    }

    public void setDdTaskStartId(Integer ddTaskStartId) {
        this.ddTaskStartId = ddTaskStartId;
    }

    public Integer getDdProjectStartId() {
        return ddProjectStartId;
    }

    public void setDdProjectStartId(Integer ddProjectStartId) {
        this.ddProjectStartId = ddProjectStartId;
    }

    public Integer getDdTaskId() {
        return ddTaskId;
    }

    public void setDdTaskId(Integer ddTaskId) {
        this.ddTaskId = ddTaskId;
    }

    public Integer getActInstId() {
        return actInstId;
    }

    public void setActInstId(Integer actInstId) {
        this.actInstId = actInstId;
    }

    public Short getDdTaskStatus() {
        return ddTaskStatus;
    }

    public void setDdTaskStatus(Short ddTaskStatus) {
        this.ddTaskStatus = ddTaskStatus;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}