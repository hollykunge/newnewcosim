package com.casic.datadriver.model.data;

import com.hotent.core.model.BaseModel;

public class OrderDataRelation extends BaseModel {
    private Long ddOrderDataId;

    private Long ddOrderType;

    private Long ddDataId;

    private Long ddTaskId;

    private Long ddOpreationLevel;

    private String ddDataName;

    public Long getDdProjectId() {
        return ddProjectId;
    }

    public void setDdProjectId(Long ddProjectId) {
        this.ddProjectId = ddProjectId;
    }

    private Long ddProjectId;

    public Long getDdOrderDataId() {
        return ddOrderDataId;
    }

    public void setDdOrderDataId(Long ddOrderDataId) {
        this.ddOrderDataId = ddOrderDataId;
    }

    public Long getDdOrderType() {
        return ddOrderType;
    }

    public void setDdOrderType(Long ddOrderType) {
        this.ddOrderType = ddOrderType;
    }

    public Long getDdDataId() {
        return ddDataId;
    }

    public void setDdDataId(Long ddDataId) {
        this.ddDataId = ddDataId;
    }

    public Long getDdTaskId() {
        return ddTaskId;
    }

    public void setDdTaskId(Long ddTaskId) {
        this.ddTaskId = ddTaskId;
    }

    public Long getDdOpreationLevel() {
        return ddOpreationLevel;
    }

    public void setDdOpreationLevel(Long ddOpreationLevel) {
        this.ddOpreationLevel = ddOpreationLevel;
    }

    public String getDdDataName() {
        return ddDataName;
    }

    public void setDdDataName(String ddDataName) {
        this.ddDataName = ddDataName;
    }

}