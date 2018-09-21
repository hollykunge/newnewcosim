package com.casic.datadriver.model.major;

import com.hotent.core.model.BaseModel;

import java.util.List;

public class Major  extends BaseModel {
    private Long ddMajorId;

    private Short ddLevel;

    private String name;

    private String ddMajorDesp;

    private Long ddMajorParentId;

    private List<Major>  children;

    public List<Major> getChildren() {
        return children;
    }
    public void setChildren( List<Major> children) {
        this.children = children;
    }


    public Long getDdMajorId() {
        return ddMajorId;
    }

    public void setDdMajorId(Long ddMajorId) {
        this.ddMajorId = ddMajorId;
    }

    public Short getDdLevel() {
        return ddLevel;
    }

    public void setDdLevel(Short ddLevel) {
        this.ddLevel = ddLevel;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getDdMajorDesp() {
        return ddMajorDesp;
    }

    public void setDdMajorDesp(String ddMajorDesp) {
        this.ddMajorDesp = ddMajorDesp;
    }

    public Long getDdMajorParentId() {
        return ddMajorParentId;
    }

    public void setDdMajorParentId(Long ddMajorParentId) {
        this.ddMajorParentId = ddMajorParentId;
    }
}