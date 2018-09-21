package com.casic.datadriver.model.data;

import java.util.Date;

public class DataSubscribtion {
    private Integer ddDataId;

    private Integer ddSubscribePersonId;

    private Date ddSubscribeTime;

    private Integer ddApprovePersonId;

    private Integer ddApproveState;

    public Integer getDdDataId() {
        return ddDataId;
    }

    public void setDdDataId(Integer ddDataId) {
        this.ddDataId = ddDataId;
    }

    public Integer getDdSubscribePersonId() {
        return ddSubscribePersonId;
    }

    public void setDdSubscribePersonId(Integer ddSubscribePersonId) {
        this.ddSubscribePersonId = ddSubscribePersonId;
    }

    public Date getDdSubscribeTime() {
        return ddSubscribeTime;
    }

    public void setDdSubscribeTime(Date ddSubscribeTime) {
        this.ddSubscribeTime = ddSubscribeTime;
    }

    public Integer getDdApprovePersonId() {
        return ddApprovePersonId;
    }

    public void setDdApprovePersonId(Integer ddApprovePersonId) {
        this.ddApprovePersonId = ddApprovePersonId;
    }

    public Integer getDdApproveState() {
        return ddApproveState;
    }

    public void setDdApproveState(Integer ddApproveState) {
        this.ddApproveState = ddApproveState;
    }
}