package com.casic.datadriver.model.flow;

import java.util.Date;

public class ProcessFlow {
    private Long ddProcessId;

    private String ddProcessName;

    private Long ddProcessType;

    private Long ddProcessCreatorId;

    private String ddProcessDescription;

    private Date ddProcessCreatetime;

    private Date ddProcessUpdatetime;

    private Long ddProcessCreateby;

    private Long ddProcessUpdateby;

    private Integer ddProcessStatus;

    private byte[] ddProcessFigure;

    private String ddProcessXml;

    public byte[] getDdProcessFigure() {
        return ddProcessFigure;
    }

    public void setDdProcessFigure(byte[] ddProcessFigure) {
        this.ddProcessFigure = ddProcessFigure;
    }

    public String getDdProcessXml() {
        return ddProcessXml;
    }

    public void setDdProcessXml(String ddProcessXml) {
        this.ddProcessXml = ddProcessXml;
    }

    public Long getDdProcessId() {
        return ddProcessId;
    }

    public void setDdProcessId(Long ddProcessId) {
        this.ddProcessId = ddProcessId;
    }

    public String getDdProcessName() {
        return ddProcessName;
    }

    public void setDdProcessName(String ddProcessName) {
        this.ddProcessName = ddProcessName;
    }

    public Long getDdProcessType() {
        return ddProcessType;
    }

    public void setDdProcessType(Long ddProcessType) {
        this.ddProcessType = ddProcessType;
    }

    public Long getDdProcessCreatorId() {
        return ddProcessCreatorId;
    }

    public void setDdProcessCreatorId(Long ddProcessCreatorId) {
        this.ddProcessCreatorId = ddProcessCreatorId;
    }

    public String getDdProcessDescription() {
        return ddProcessDescription;
    }

    public void setDdProcessDescription(String ddProcessDescription) {
        this.ddProcessDescription = ddProcessDescription;
    }

    public Date getDdProcessCreatetime() {
        return ddProcessCreatetime;
    }

    public void setDdProcessCreatetime(Date ddProcessCreatetime) {
        this.ddProcessCreatetime = ddProcessCreatetime;
    }

    public Date getDdProcessUpdatetime() {
        return ddProcessUpdatetime;
    }

    public void setDdProcessUpdatetime(Date ddProcessUpdatetime) {
        this.ddProcessUpdatetime = ddProcessUpdatetime;
    }

    public Long getDdProcessCreateby() {
        return ddProcessCreateby;
    }

    public void setDdProcessCreateby(Long ddProcessCreateby) {
        this.ddProcessCreateby = ddProcessCreateby;
    }

    public Long getDdProcessUpdateby() {
        return ddProcessUpdateby;
    }

    public void setDdProcessUpdateby(Long ddProcessUpdateby) {
        this.ddProcessUpdateby = ddProcessUpdateby;
    }

    public Integer getDdProcessStatus() {
        return ddProcessStatus;
    }

    public void setDdProcessStatus(Integer ddProcessStatus) {
        this.ddProcessStatus = ddProcessStatus;
    }
}