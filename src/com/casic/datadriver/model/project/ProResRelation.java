package com.casic.datadriver.model.project;

import java.util.Date;

public class ProResRelation {
    private Long ddProjectResponceRelationId;

    private Long ddProjectId;

    private Long ddResponcePersonId;

    private String ddProjectName;

    private String ddResponcePersonName;

    private Date ddResponceTime;

    private Short ddAuthority;

    public Long getDdProjectResponceRelationId() {
        return ddProjectResponceRelationId;
    }

    public void setDdProjectResponceRelationId(Long ddProjectResponceRelationId) {
        this.ddProjectResponceRelationId = ddProjectResponceRelationId;
    }




    public Long getDdProjectId() {
        return ddProjectId;
    }

    public void setDdProjectId(Long ddProjectId) {
        this.ddProjectId = ddProjectId;
    }

    public Long getDdResponcePersonId() {
        return ddResponcePersonId;
    }

    public void setDdResponcePersonId(Long ddResponcePersonId) {
        this.ddResponcePersonId = ddResponcePersonId;
    }

    public String getDdProjectName() {
        return ddProjectName;
    }

    public void setDdProjectName(String ddProjectName) {
        this.ddProjectName = ddProjectName;
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