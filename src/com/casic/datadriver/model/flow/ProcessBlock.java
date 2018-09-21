package com.casic.datadriver.model.flow;
import java.util.Date;

public class ProcessBlock {
    private Long ddProcessblockId;

    private Long ddProcessId;

    private Long ddBlockId;

    private Long ddTaskId;

    public Long getDdProcessblockId() {
        return ddProcessblockId;
    }

    public void setDdProcessblockId(Long ddProcessblockId) {
        this.ddProcessblockId = ddProcessblockId;
    }

    public Long getDdProcessId() {
        return ddProcessId;
    }

    public void setDdProcessId(Long ddProcessId) {
        this.ddProcessId = ddProcessId;
    }

    public Long getDdBlockId() {
        return ddBlockId;
    }

    public void setDdBlockId(Long ddBlockId) {
        this.ddBlockId = ddBlockId;
    }

    public Long getDdTaskId() {
        return ddTaskId;
    }

    public void setDdTaskId(Long ddTaskId) {
        this.ddTaskId = ddTaskId;
    }
}