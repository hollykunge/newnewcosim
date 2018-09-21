package com.casic.datadriver.publicClass;

import java.util.Date;

public class ProTaskAllocation {
    private Integer ddAllocationId;

    private Integer ddProjectId;

    private Integer ddTaskId;

    private Integer ddResourceGroupId;

    private Integer ddResponsoblePersonId;

    private Date ddStartTime;

    private Date ddEndTime;

    private String ddTotalTime;

    private Integer ddDestributionRate;

    public Integer getDdAllocationId() {
        return ddAllocationId;
    }

    public void setDdAllocationId(Integer ddAllocationId) {
        this.ddAllocationId = ddAllocationId;
    }

    public Integer getDdProjectId() {
        return ddProjectId;
    }

    public void setDdProjectId(Integer ddProjectId) {
        this.ddProjectId = ddProjectId;
    }

    public Integer getDdTaskId() {
        return ddTaskId;
    }

    public void setDdTaskId(Integer ddTaskId) {
        this.ddTaskId = ddTaskId;
    }

    public Integer getDdResourceGroupId() {
        return ddResourceGroupId;
    }

    public void setDdResourceGroupId(Integer ddResourceGroupId) {
        this.ddResourceGroupId = ddResourceGroupId;
    }

    public Integer getDdResponsoblePersonId() {
        return ddResponsoblePersonId;
    }

    public void setDdResponsoblePersonId(Integer ddResponsoblePersonId) {
        this.ddResponsoblePersonId = ddResponsoblePersonId;
    }

    public Date getDdStartTime() {
        return ddStartTime;
    }

    public void setDdStartTime(Date ddStartTime) {
        this.ddStartTime = ddStartTime;
    }

    public Date getDdEndTime() {
        return ddEndTime;
    }

    public void setDdEndTime(Date ddEndTime) {
        this.ddEndTime = ddEndTime;
    }

    public String getDdTotalTime() {
        return ddTotalTime;
    }

    public void setDdTotalTime(String ddTotalTime) {
        this.ddTotalTime = ddTotalTime;
    }

    public Integer getDdDestributionRate() {
        return ddDestributionRate;
    }

    public void setDdDestributionRate(Integer ddDestributionRate) {
        this.ddDestributionRate = ddDestributionRate;
    }
}