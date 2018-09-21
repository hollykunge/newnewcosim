package com.casic.datadriver.model.model;

import com.hotent.core.model.BaseModel;

import java.util.List;

public class Model extends BaseModel {
    private Long ddModeltypeId;

    private String name;

    private Integer ddModelVersion;

    private Long ddModelPid;

    private String ddModelBz1;

    private Long ddModelBz2;

    private List<Model> children;

    public List<Model> getChildren() {
        return children;
    }
    public void setChildren( List<Model> children) {
        this.children = children;
    }

    public Long getDdModeltypeId() {
        return ddModeltypeId;
    }

    public void setDdModeltypeId(Long ddModeltypeId) {
        this.ddModeltypeId = ddModeltypeId;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getDdModelVersion() {
        return ddModelVersion;
    }

    public void setDdModelVersion(Integer ddModelVersion) {
        this.ddModelVersion = ddModelVersion;
    }

    public Long getDdModelPid() {
        return ddModelPid;
    }

    public void setDdModelPid(Long ddModelPid) {
        this.ddModelPid = ddModelPid;
    }

    public String getDdModelBz1() {
        return ddModelBz1;
    }

    public void setDdModelBz1(String ddModelBz1) {
        this.ddModelBz1 = ddModelBz1 == null ? null : ddModelBz1.trim();
    }

    public Long getDdModelBz2() {
        return ddModelBz2;
    }

    public void setDdModelBz2(Long ddModelBz2) {
        this.ddModelBz2 = ddModelBz2;
    }
}