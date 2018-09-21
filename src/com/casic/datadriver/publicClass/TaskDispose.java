package com.casic.datadriver.publicClass;

import java.util.Date;

public class TaskDispose {
    private Integer ddTaskDisposeId;

    private Integer ddTaskId;

    private Integer ddSubmitePersonId;

    private Date ddSubmiteTime;

    private Integer ddApprovalPersonId;

    private Integer ddState;

    private Date ddChangeTime;

    private Integer ddChangePersonId;

    private String ddChangeReason;

    public Integer getDdTaskDisposeId() {
        return ddTaskDisposeId;
    }

    public void setDdTaskDisposeId(Integer ddTaskDisposeId) {
        this.ddTaskDisposeId = ddTaskDisposeId;
    }

    public Integer getDdTaskId() {
        return ddTaskId;
    }

    public void setDdTaskId(Integer ddTaskId) {
        this.ddTaskId = ddTaskId;
    }

    public Integer getDdSubmitePersonId() {
        return ddSubmitePersonId;
    }

    public void setDdSubmitePersonId(Integer ddSubmitePersonId) {
        this.ddSubmitePersonId = ddSubmitePersonId;
    }

    public Date getDdSubmiteTime() {
        return ddSubmiteTime;
    }

    public void setDdSubmiteTime(Date ddSubmiteTime) {
        this.ddSubmiteTime = ddSubmiteTime;
    }

    public Integer getDdApprovalPersonId() {
        return ddApprovalPersonId;
    }

    public void setDdApprovalPersonId(Integer ddApprovalPersonId) {
        this.ddApprovalPersonId = ddApprovalPersonId;
    }

    public Integer getDdState() {
        return ddState;
    }

    public void setDdState(Integer ddState) {
        this.ddState = ddState;
    }

    public Date getDdChangeTime() {
        return ddChangeTime;
    }

    public void setDdChangeTime(Date ddChangeTime) {
        this.ddChangeTime = ddChangeTime;
    }

    public Integer getDdChangePersonId() {
        return ddChangePersonId;
    }

    public void setDdChangePersonId(Integer ddChangePersonId) {
        this.ddChangePersonId = ddChangePersonId;
    }

    public String getDdChangeReason() {
        return ddChangeReason;
    }

    public void setDdChangeReason(String ddChangeReason) {
        this.ddChangeReason = ddChangeReason;
    }
}