package com.casic.datadriver.model.task;

public class ProTaskDependance {
    private Long ddDepenganceId;

    private Long ddProjectId;

    private Long ddTaskId;

    private Long ddPrepositionTaskId;

    public Long getDdDepenganceId() {
        return ddDepenganceId;
    }

    public void setDdDepenganceId(Long ddDepenganceId) {
        this.ddDepenganceId = ddDepenganceId;
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

    public Long getDdPrepositionTaskId() {
        return ddPrepositionTaskId;
    }

    public void setDdPrepositionTaskId(Long ddPrepositionTaskId) {
        this.ddPrepositionTaskId = ddPrepositionTaskId;
    }
}