package com.casic.datadriver.model.project;

import com.hotent.core.model.BaseModel;

import java.util.Date;

public class ProjectStart  extends BaseModel {

    public static final Short STATUS_RUNNING		=1;
    public static final Short STATUS_STOP			=2;
    public static final Short STATUS_EXCEPTION		=-1;

    private Long ddProjectStartId;

    private Long ddProjectId;

    private Short ddProjectStartStatus;

    private Date ddProjectStartTime;

    private Date ddProjectEndTime;

    private Long ddCreateBy;

    public Long getDdProjectStartId() {
        return ddProjectStartId;
    }

    public void setDdProjectStartId(Long ddProjectStartId) {
        this.ddProjectStartId = ddProjectStartId;
    }

    public Long getDdProjectId() {
        return ddProjectId;
    }

    public void setDdProjectId(Long ddProjectId) {
        this.ddProjectId = ddProjectId;
    }

    public Short getDdProjectStartStatus() {
        return ddProjectStartStatus;
    }

    public void setDdProjectStartStatus(Short ddProjectStartStatus) {
        this.ddProjectStartStatus = ddProjectStartStatus;
    }

    public Date getDdProjectStartTime() {
        return ddProjectStartTime;
    }

    public void setDdProjectStartTime(Date ddProjectStartTime) {
        this.ddProjectStartTime = ddProjectStartTime;
    }

    public Date getDdProjectEndTime() {
        return ddProjectEndTime;
    }

    public void setDdProjectEndTime(Date ddProjectEndTime) {
        this.ddProjectEndTime = ddProjectEndTime;
    }

    public Long getDdCreateBy() {
        return ddCreateBy;
    }

    public void setDdCreateBy(Long ddCreateBy) {
        this.ddCreateBy = ddCreateBy;
    }
}