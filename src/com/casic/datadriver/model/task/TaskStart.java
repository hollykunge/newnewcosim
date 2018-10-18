package com.casic.datadriver.model.task;

import com.hotent.core.model.BaseModel;

public class TaskStart  extends BaseModel
{
    public static final Short checkpanel		=2;
    public static final Short completepanel	    =3;
    public static final Short createpanel		=0;
    public static final Short publishpanel	    =1;

    private Long ddTaskStartId;

    private Long ddProjectStartId;

    private Long ddTaskId;

    private Long actInstId;

    private Short ddTaskStatus;

    private Long sortOrder;

    private Long ddTaskResponcePerson;

    public Long getDdTaskStartId() {
        return ddTaskStartId;
    }

    public void setDdTaskStartId(Long ddTaskStartId) {
        this.ddTaskStartId = ddTaskStartId;
    }

    public Long getDdProjectStartId() {
        return ddProjectStartId;
    }

    public void setDdProjectStartId(Long ddProjectStartId) {
        this.ddProjectStartId = ddProjectStartId;
    }

    public Long getDdTaskId() {
        return ddTaskId;
    }

    public void setDdTaskId(Long ddTaskId) {
        this.ddTaskId = ddTaskId;
    }

    public Long getActInstId() {
        return actInstId;
    }

    public void setActInstId(Long actInstId) {
        this.actInstId = actInstId;
    }

    public Short getDdTaskStatus() {
        return ddTaskStatus;
    }

    public void setDdTaskStatus(Short ddTaskStatus) {
        this.ddTaskStatus = ddTaskStatus;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Long getDdTaskResponcePerson() {
        return ddTaskResponcePerson;
    }

    public void setDdTaskResponcePerson(Long ddTaskResponcePerson) {
        this.ddTaskResponcePerson = ddTaskResponcePerson;
    }
}