package com.casic.datadriver.model.flow;

public class ProjectProcessAssocia {
    private Long ddPrcessAssociationId;

    private Long ddPrcessId;

    private Integer ddPrcessKeyword;

    private Long ddProjectId;

    private Long ddTaskId;

    public Long getDdPrcessAssociationId() {
        return ddPrcessAssociationId;
    }

    public void setDdPrcessAssociationId(Long ddPrcessAssociationId) {
        this.ddPrcessAssociationId = ddPrcessAssociationId;
    }

    public Long getDdPrcessId() {
        return ddPrcessId;
    }

    public void setDdPrcessId(Long ddPrcessId) {
        this.ddPrcessId = ddPrcessId;
    }

    public Integer getDdPrcessKeyword() {
        return ddPrcessKeyword;
    }

    public void setDdPrcessKeyword(Integer ddPrcessKeyword) {
        this.ddPrcessKeyword = ddPrcessKeyword;
    }

    public Long getDdProjectId() {
        return ddProjectId;
    }

    public void setDdProjectId(Long ddProjectId) {
        this.ddProjectId = ddProjectId;
    }

    public Long getDdTaskId() {
        return ddTaskId;
    }

    public void setDdTaskId(Long ddTaskId) {
        this.ddTaskId = ddTaskId;
    }
}