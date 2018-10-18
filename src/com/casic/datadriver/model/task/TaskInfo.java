package com.casic.datadriver.model.task;

import com.casic.datadriver.model.data.PrivateData;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskInfo extends BaseModel {


    public static final Short checkpanel		=2;
    public static final Short completepanel	    =3;
    public static final Short createpanel		=0;
    public static final Short publishpanel	    =1;

    private Long ddTaskId;

    private String ddTaskName;

    private String ddTaskDescription;

    private Long ddTaskCreatorId;

    private Long ddTaskResponsiblePerson;

    private Long ddTaskFixedPattern;

    private String ddTaskType;

    private String ddTaskChildType;

    private Short ddTaskPriority;

    private Long ddTaskMilestone;

    private String ddTaskEstimateTime;

    private String ddTaskPlanStartTime;

    private String ddTaskPlanEndTime;

    private Long ddTaskCompleteRate;

    private Long ddTaskCompleteState;

    private Long ddTaskResourceId;

    private String ddTaskActualTime;

    private String ddTaskPlanTime;

    private Date ddTaskActualStartTime;

    private Date ddTaskActualEndTime;

    private Long ddTaskPlanDuration;

    private Long ddTaskProjectId;

    private String ddTaskPerson;

    private String ddTaskProjectName;

    private Short ddTaskState;

    private String  ddSecretLevel;

    public String getDdSecretLevel() {
        return ddSecretLevel;
    }

    public void setDdSecretLevel(String ddSecretLevel) {
        this.ddSecretLevel = ddSecretLevel;
    }

    public Short getDdTaskState() {
        return ddTaskState;
    }

    public void setDdTaskState(Short ddTaskState) {
        this.ddTaskState = ddTaskState;
    }



    //任务管理中的私有数据管理
    protected List<PrivateData> privateDataList= new ArrayList<PrivateData>();

    public String getDdTaskPerson() {
        return ddTaskPerson;
    }

    public void setDdTaskPerson(String ddTaskPerson) {
        this.ddTaskPerson = ddTaskPerson;
    }

    public Long getDdTaskId() {
        return ddTaskId;
    }

    public void setDdTaskId(Long ddTaskId) {
        this.ddTaskId = ddTaskId;
    }

    public String getDdTaskName() {
        return ddTaskName;
    }

    public void setDdTaskName(String ddTaskName) {
        this.ddTaskName = ddTaskName;
    }

    public String getDdTaskDescription() {
        return ddTaskDescription;
    }

    public void setDdTaskDescription(String ddTaskDescription) {
        this.ddTaskDescription = ddTaskDescription;
    }

    public Long getDdTaskCreatorId() {
        return ddTaskCreatorId;
    }

    public void setDdTaskCreatorId(Long ddTaskCreatorId) {
        this.ddTaskCreatorId = ddTaskCreatorId;
    }

    public Long getDdTaskResponsiblePerson() {
        return ddTaskResponsiblePerson;
    }

    public void setDdTaskResponsiblePerson(Long ddTaskResponsiblePerson) {
        this.ddTaskResponsiblePerson = ddTaskResponsiblePerson;
    }

    public Long getDdTaskFixedPattern() {
        return ddTaskFixedPattern;
    }

    public void setDdTaskFixedPattern(Long ddTaskFixedPattern) {
        this.ddTaskFixedPattern = ddTaskFixedPattern;
    }

    public String getDdTaskType() {
        return ddTaskType;
    }

    public void setDdTaskType(String ddTaskType) {
        this.ddTaskType = ddTaskType;
    }

    public String getDdTaskChildType() {
        return ddTaskChildType;
    }

    public void setDdTaskChildType(String ddTaskChildType) {
        this.ddTaskChildType = ddTaskChildType;
    }

    public Short getDdTaskPriority() {
        return ddTaskPriority;
    }

    public void setDdTaskPriority(Short ddTaskPriority) {
        this.ddTaskPriority = ddTaskPriority;
    }

    public Long getDdTaskMilestone() {
        return ddTaskMilestone;
    }

    public void setDdTaskMilestone(Long ddTaskMilestone) {
        this.ddTaskMilestone = ddTaskMilestone;
    }

    public String getDdTaskEstimateTime() {
        return ddTaskEstimateTime;
    }

    public void setDdTaskEstimateTime(String ddTaskEstimateTime) {
        this.ddTaskEstimateTime = ddTaskEstimateTime;
    }

    public String getDdTaskPlanStartTime() {
        return ddTaskPlanStartTime;
    }

    public void setDdTaskPlanStartTime(String ddTaskPlanStartTime) {
        this.ddTaskPlanStartTime = ddTaskPlanStartTime;
    }

    public String getDdTaskPlanEndTime() {
        return ddTaskPlanEndTime;
    }

    public void setDdTaskPlanEndTime(String ddTaskPlanEndTime) {
        this.ddTaskPlanEndTime = ddTaskPlanEndTime;
    }

    public Long getDdTaskCompleteRate() {
        return ddTaskCompleteRate;
    }

    public void setDdTaskCompleteRate(Long ddTaskCompleteRate) {
        this.ddTaskCompleteRate = ddTaskCompleteRate;
    }

    public Long getDdTaskCompleteState() {
        return ddTaskCompleteState;
    }

    public void setDdTaskCompleteState(Long ddTaskCompleteState) {
        this.ddTaskCompleteState = ddTaskCompleteState;
    }

    public Long getDdTaskResourceId() {
        return ddTaskResourceId;
    }

    public void setDdTaskResourceId(Long ddTaskResourceId) {
        this.ddTaskResourceId = ddTaskResourceId;
    }

    public String getDdTaskActualTime() {
        return ddTaskActualTime;
    }

    public void setDdTaskActualTime(String ddTaskActualTime) {
        this.ddTaskActualTime = ddTaskActualTime;
    }

    public String getDdTaskPlanTime() {
        return ddTaskPlanTime;
    }

    public void setDdTaskPlanTime(String ddTaskPlanTime) {
        this.ddTaskPlanTime = ddTaskPlanTime;
    }

    public Date getDdTaskActualStartTime() {
        return ddTaskActualStartTime;
    }

    public void setDdTaskActualStartTime(Date ddTaskActualStartTime) {
        this.ddTaskActualStartTime = ddTaskActualStartTime;
    }

    public Date getDdTaskActualEndTime() {
        return ddTaskActualEndTime;
    }

    public void setDdTaskActualEndTime(Date ddTaskActualEndTime) {
        this.ddTaskActualEndTime = ddTaskActualEndTime;
    }

    public Long getDdTaskPlanDuration() {
        return ddTaskPlanDuration;
    }

    public void setDdTaskPlanDuration(Long ddTaskPlanDuration) {
        this.ddTaskPlanDuration = ddTaskPlanDuration;
    }

    public Long getDdTaskProjectId() {
        return ddTaskProjectId;
    }

    public void setDdTaskProjectId(Long ddTaskProjectId) {
        this.ddTaskProjectId = ddTaskProjectId;
    }

    public void setPrivateDataList(List<PrivateData> privateDataList)
    {
        this.privateDataList = privateDataList;
    }

    public List<PrivateData> getPrivateDataList()
    {
        return this.privateDataList;
    }

    public String getDdTaskProjectName() {
        return ddTaskProjectName;
    }

    public void setDdTaskProjectName(String ddTaskProjectName) {
        this.ddTaskProjectName = ddTaskProjectName;
    }

    /**
     * @see Object#equals(Object)
     */
    public boolean equals(Object object)
    {
        if (!(object instanceof TaskInfo))
        {
            return false;
        }
        TaskInfo ti = (TaskInfo) object;
        return new EqualsBuilder()
                .append(this.ddTaskActualEndTime, ti.ddTaskActualEndTime)
                .append(this.ddTaskActualStartTime, ti.ddTaskActualStartTime)
                .append(this.ddTaskActualTime, ti.ddTaskActualTime)
                .append(this.ddTaskChildType, ti.ddTaskChildType)
                .append(this.ddTaskCompleteRate, ti.ddTaskCompleteRate)
                .append(this.ddTaskCompleteState, ti.ddTaskCompleteState)
                .append(this.ddTaskCreatorId, ti.ddTaskCreatorId)
                .append(this.ddTaskDescription, ti.ddTaskDescription)
                .append(this.ddTaskEstimateTime, ti.ddTaskEstimateTime)
                .append(this.ddTaskFixedPattern, ti.ddTaskFixedPattern)
                .append(this.ddTaskId, ti.ddTaskId)
                .append(this.ddTaskMilestone, ti.ddTaskMilestone)
                .append(this.ddTaskName, ti.ddTaskName)
                .append(this.ddTaskPerson, ti.ddTaskPerson)
                .append(this.ddTaskPlanDuration, ti.ddTaskPlanDuration)
                .append(this.ddTaskPlanEndTime, ti.ddTaskPlanEndTime)
                .append(this.ddTaskPlanStartTime, ti.ddTaskPlanStartTime)
                .append(this.ddTaskPlanTime, ti.ddTaskPlanTime)
                .append(this.ddTaskPriority, ti.ddTaskPriority)
                .append(this.ddTaskProjectId, ti.ddTaskProjectId)
                .append(this.ddTaskProjectName, ti.ddTaskProjectName)
                .append(this.ddTaskResourceId, ti.ddTaskResourceId)
                .append(this.ddTaskResponsiblePerson, ti.ddTaskResponsiblePerson)
                .append(this.ddTaskState, ti.ddTaskState)
                .append(this.ddTaskType, ti.ddTaskType)
                .append(this.ddSecretLevel,ti.ddSecretLevel)
                .isEquals();
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
                .append(this.ddTaskActualEndTime)
                .append(this.ddTaskActualStartTime)
                .append(this.ddTaskActualTime)
                .append(this.ddTaskChildType)
                .append(this.ddTaskCompleteRate)
                .append(this.ddTaskCompleteState)
                .append(this.ddTaskCreatorId)
                .append(this.ddTaskDescription)
                .append(this.ddTaskEstimateTime)
                .append(this.ddTaskFixedPattern)
                .append(this.ddTaskId)
                .append(this.ddTaskMilestone)
                .append(this.ddTaskName)
                .append(this.ddTaskPerson)
                .append(this.ddTaskPlanDuration)
                .append(this.ddTaskPlanEndTime)
                .append(this.ddTaskPlanStartTime)
                .append(this.ddTaskPlanTime)
                .append(this.ddTaskPriority)
                .append(this.ddTaskProjectId)
                .append(this.ddTaskProjectName)
                .append(this.ddTaskResourceId)
                .append(this.ddTaskResponsiblePerson)
                .append(this.ddTaskState)
                .append(this.ddTaskType)
                .append(this.ddSecretLevel)
                .toHashCode();
    }

    /**
     * @see Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this)
                .append(this.ddTaskActualEndTime)
                .append(this.ddTaskActualStartTime)
                .append(this.ddTaskActualTime)
                .append(this.ddTaskChildType)
                .append(this.ddTaskCompleteRate)
                .append(this.ddTaskCompleteState)
                .append(this.ddTaskCreatorId)
                .append(this.ddTaskDescription)
                .append(this.ddTaskEstimateTime)
                .append(this.ddTaskFixedPattern)
                .append(this.ddTaskId)
                .append(this.ddTaskMilestone)
                .append(this.ddTaskName)
                .append(this.ddTaskPerson)
                .append(this.ddTaskPlanDuration)
                .append(this.ddTaskPlanEndTime)
                .append(this.ddTaskPlanStartTime)
                .append(this.ddTaskPlanTime)
                .append(this.ddTaskPriority)
                .append(this.ddTaskProjectId)
                .append(this.ddTaskProjectName)
                .append(this.ddTaskResourceId)
                .append(this.ddTaskResponsiblePerson)
                .append(this.ddTaskState)
                .append(this.ddTaskType)
                .append(this.ddSecretLevel)
                .toString();
    }
}