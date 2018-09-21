package com.casic.datadriver.model.task;

import java.util.Date;

public class TaskResRelation {
    private Long ddTaskResponceRelationId;

    private Long ddTaskId;

    private Long ddResponcePersonId;

    private String ddTaskName;

    private String ddResponcePersonName;

    private Date ddResponceTime;

    private Short ddAuthority;


    public Long getDdTaskResponceRelationId() {
        return ddTaskResponceRelationId;
    }

    public void setDdTaskResponceRelationId(Long ddTaskResponceRelationId) {
        this.ddTaskResponceRelationId = ddTaskResponceRelationId;
    }

    public Long getDdTaskId() {
        return ddTaskId;
    }

    public void setDdTaskId(Long ddTaskId) {
        this.ddTaskId = ddTaskId;
    }

    public Long getDdResponcePersonId() {
        return ddResponcePersonId;
    }

    public void setDdResponcePersonId(Long ddResponcePersonId) {
        this.ddResponcePersonId = ddResponcePersonId;
    }

    public String getDdTaskName() {
        return ddTaskName;
    }

    public void setDdTaskName(String ddTaskName) {
        this.ddTaskName = ddTaskName;
    }

    public String getDdResponcePersonName() {
        return ddResponcePersonName;
    }

    public void setDdResponcePersonName(String ddResponcePersonName) {
        this.ddResponcePersonName = ddResponcePersonName;
    }

    public Date getDdResponceTime() {
        return ddResponceTime;
    }

    public void setDdResponceTime(Date ddResponceTime) {
        this.ddResponceTime = ddResponceTime;
    }

    public Short getDdAuthority() {
        return ddAuthority;
    }

    public void setDdAuthority(Short ddAuthority) {
        this.ddAuthority = ddAuthority;
    }
}