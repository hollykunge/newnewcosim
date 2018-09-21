package com.casic.datadriver.model.data;

import java.util.Date;

public class ProjectStart {
    private Integer ddProjectStartId;

    private Integer ddProjectId;

    private Short ddProjectStartStatus;

    private Date ddProjectStartTime;

    private Date ddProjectEndTime;

    private Long ddCreateBy;

    public Integer getDdProjectStartId() {
        return ddProjectStartId;
    }

    public void setDdProjectStartId(Integer ddProjectStartId) {
        this.ddProjectStartId = ddProjectStartId;
    }

    public Integer getDdProjectId() {
        return ddProjectId;
    }

    public void setDdProjectId(Integer ddProjectId) {
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