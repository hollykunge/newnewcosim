package com.casic.datadriver.model.data;

import java.util.Date;

public class TaskVerMap {
    private Long ddTaskVerId;

    private Date ddVersionTime;

    private Long ddTaskId;

    private Integer ddVersionNum;

    private String ddVersionDescription;

    private Long ddReserved1;

    private String ddReserved2;

    public Long getDdTaskVerId() {
        return ddTaskVerId;
    }

    public void setDdTaskVerId(Long ddTaskVerId) {
        this.ddTaskVerId = ddTaskVerId;
    }

    public Date getDdVersionTime() {
        return ddVersionTime;
    }

    public void setDdVersionTime(Date ddVersionTime) {
        this.ddVersionTime = ddVersionTime;
    }

    public Long getDdTaskId() {
        return ddTaskId;
    }

    public void setDdTaskId(Long ddTaskId) {
        this.ddTaskId = ddTaskId;
    }

    public Integer getDdVersionNum() {
        return ddVersionNum;
    }

    public void setDdVersionNum(Integer ddVersionNum) {
        this.ddVersionNum = ddVersionNum;
    }

    public String getDdVersionDescription() {
        return ddVersionDescription;
    }

    public void setDdVersionDescription(String ddVersionDescription) {
        this.ddVersionDescription = ddVersionDescription;
    }

    public Long getDdReserved1() {
        return ddReserved1;
    }

    public void setDdReserved1(Long ddReserved1) {
        this.ddReserved1 = ddReserved1;
    }

    public String getDdReserved2() {
        return ddReserved2;
    }

    public void setDdReserved2(String ddReserved2) {
        this.ddReserved2 = ddReserved2;
    }
}