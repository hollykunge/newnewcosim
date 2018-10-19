package com.casic.datadriver.model.task;

import com.hotent.core.model.BaseModel;

public class TaskStart  extends BaseModel
{
    public static final Long checkpanel		=2L;
    public static final Long completepanel	    =3L;
    public static final Long createpanel		=0L;
    public static final Long publishpanel	    =1L;

    private Long ddTaskStartId;

    private Long ddProjectStartId;

    private Long ddTaskId;

    private Long actInstId;

    private Long ddTaskStatus;

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

    public Long getDdTaskStatus() {
        return ddTaskStatus;
    }

    public void setDdTaskStatus(Long ddTaskStatus) {
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