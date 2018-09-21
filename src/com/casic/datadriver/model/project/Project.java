/*
 * 
 */
package com.casic.datadriver.model.project;

import com.casic.datadriver.model.task.TaskInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Class Project.
 */
public class Project {

	public static final Short STATUS_RUNNING		=1;
	public static final Short STATUS_STOP			=2;
	public static final Short STATUS_EXCEPTION		=3;
	public static final Short STATUS_UNSTART        =4;

	public static final Short unstart   =  2;
	public static final Short start   =  0;
	public static final Short complete   =  1;

	/** The dd project id. */
	private Long ddProjectId;

	/** The dd project name. */
	private String ddProjectName;

	/** The dd project responsible units. */
	private String ddProjectResponsibleUnits;

	/** The dd project phase id. */
	private Short ddProjectPhaseId;

	/** The dd project create datatime. */
	private String ddProjectCreateDatatime;

	/** The dd project description. */
	private String ddProjectDescription;

	/** The dd project owner system id. */
	private Long ddProjectOwnerSystemId;

	/** The dd project creator id. */
	private Long ddProjectCreatorId;

	/** The dd project fixed pattern. */
	private Boolean ddProjectFixedPattern;

	/** The dd project responsible person id. */
	private Long ddProjectResponsiblePersonId;

	/** The dd project type. */
	private String ddProjectType;

	/** The dd project belong model. */
	private String ddProjectBelongModel;

	/** The dd project secret level. */
	private String ddProjectSecretLevel;

	/** The dd project change person id. */
	private Long ddProjectChangePersonId;

	/** The dd project priority. */
	private Long ddProjectPriority;

	/** The dd project remark. */
	private String ddProjectRemark;

	/** The dd project state. */
	private Short ddProjectState;

	/** The dd project plan start date. */
	private String ddProjectPlanStartDate;

	/** The dd project complete date. */
	private String ddProjectCompleteDate;

	/** The dd project actual start date. */
	private String ddProjectActualStartDate;

	/** The dd project actual complete data. */
	private String ddProjectActualCompleteData;

	/** The dd project current stage. */
	private String ddProjectCurrentStage;

	/** The dd project schedule state. */
	private String ddProjectScheduleState;

	/** The dd project phase name. */
	private String ddProjectPhaseName;


    //项目管理中的任务列表20161202
    protected List<TaskInfo> taskInfoList=new ArrayList<TaskInfo>();

	/**
	 * Gets the dd project id.
	 *
	 * @return the dd project id
	 */
	public Long getDdProjectId() {
		return ddProjectId;
	}

	/**
	 * Sets the dd project id.
	 *
	 * @param ddProjectId
	 *            the new dd project id
	 */
	public void setDdProjectId(Long ddProjectId) {
		this.ddProjectId = ddProjectId;
	}

	/**
	 * Gets the dd project name.
	 *
	 * @return the dd project name
	 */
	public String getDdProjectName() {
		return ddProjectName;
	}

	/**
	 * Sets the dd project name.
	 *
	 * @param ddProjectName
	 *            the new dd project name
	 */
	public void setDdProjectName(String ddProjectName) {
		this.ddProjectName = ddProjectName;
	}

	/**
	 * Gets the dd project responsible units.
	 *
	 * @return the dd project responsible units
	 */
	public String getDdProjectResponsibleUnits() {
		return ddProjectResponsibleUnits;
	}

	/**
	 * Sets the dd project responsible units.
	 *
	 * @param ddProjectResponsibleUnits
	 *            the new dd project responsible units
	 */
	public void setDdProjectResponsibleUnits(String ddProjectResponsibleUnits) {
		this.ddProjectResponsibleUnits = ddProjectResponsibleUnits;
	}

	/**
	 * Gets the dd project phase id.
	 *
	 * @return the dd project phase id
	 */
	public Short getDdProjectPhaseId() {
		return ddProjectPhaseId;
	}

	/**
	 * Sets the dd project phase id.
	 *
	 * @param ddProjectPhaseId
	 *            the new dd project phase id
	 */
	public void setDdProjectPhaseId(Short ddProjectPhaseId) {
		this.ddProjectPhaseId = ddProjectPhaseId;
	}

	/**
	 * Gets the dd project create datatime.
	 *
	 * @return the dd project create datatime
	 */
	public String getDdProjectCreateDatatime() {
		return ddProjectCreateDatatime;
	}

	/**
	 * Sets the dd project create datatime.
	 *
	 * @param ddProjectCreateDatatime
	 *            the new dd project create datatime
	 */
	public void setDdProjectCreateDatatime(String ddProjectCreateDatatime) {
		this.ddProjectCreateDatatime = ddProjectCreateDatatime;
	}

	/**
	 * Gets the dd project description.
	 *
	 * @return the dd project description
	 */
	public String getDdProjectDescription() {
		return ddProjectDescription;
	}

	/**
	 * Sets the dd project description.
	 *
	 * @param ddProjectDescription
	 *            the new dd project description
	 */
	public void setDdProjectDescription(String ddProjectDescription) {
		this.ddProjectDescription = ddProjectDescription;
	}

	/**
	 * Gets the dd project owner system id.
	 *
	 * @return the dd project owner system id
	 */
	public Long getDdProjectOwnerSystemId() {
		return ddProjectOwnerSystemId;
	}

	/**
	 * Sets the dd project owner system id.
	 *
	 * @param ddProjectOwnerSystemId
	 *            the new dd project owner system id
	 */
	public void setDdProjectOwnerSystemId(Long ddProjectOwnerSystemId) {
		this.ddProjectOwnerSystemId = ddProjectOwnerSystemId;
	}

	/**
	 * Gets the dd project creator id.
	 *
	 * @return the dd project creator id
	 */
	public Long getDdProjectCreatorId() {
		return ddProjectCreatorId;
	}

	/**
	 * Sets the dd project creator id.
	 *
	 * @param ddProjectCreatorId
	 *            the new dd project creator id
	 */
	public void setDdProjectCreatorId(Long ddProjectCreatorId) {
		this.ddProjectCreatorId = ddProjectCreatorId;
	}

	/**
	 * Gets the dd project fixed pattern.
	 *
	 * @return the dd project fixed pattern
	 */
	public Boolean getDdProjectFixedPattern() {
		return ddProjectFixedPattern;
	}

	/**
	 * Sets the dd project fixed pattern.
	 *
	 * @param ddProjectFixedPattern
	 *            the new dd project fixed pattern
	 */
	public void setDdProjectFixedPattern(Boolean ddProjectFixedPattern) {
		this.ddProjectFixedPattern = ddProjectFixedPattern;
	}

	/**
	 * Gets the dd project responsible person id.
	 *
	 * @return the dd project responsible person id
	 */
	public Long getDdProjectResponsiblePersonId() {
		return ddProjectResponsiblePersonId;
	}

	/**
	 * Sets the dd project responsible person id.
	 *
	 * @param ddProjectResponsiblePersonId
	 *            the new dd project responsible person id
	 */
	public void setDdProjectResponsiblePersonId(Long ddProjectResponsiblePersonId) {
		this.ddProjectResponsiblePersonId = ddProjectResponsiblePersonId;
	}

	/**
	 * Gets the dd project type.
	 *
	 * @return the dd project type
	 */
	public String getDdProjectType() {
		return ddProjectType;
	}

	/**
	 * Sets the dd project type.
	 *
	 * @param ddProjectType
	 *            the new dd project type
	 */
	public void setDdProjectType(String ddProjectType) {
		this.ddProjectType = ddProjectType;
	}

	/**
	 * Gets the dd project belong model.
	 *
	 * @return the dd project belong model
	 */
	public String getDdProjectBelongModel() {
		return ddProjectBelongModel;
	}

	/**
	 * Sets the dd project belong model.
	 *
	 * @param ddProjectBelongModel
	 *            the new dd project belong model
	 */
	public void setDdProjectBelongModel(String ddProjectBelongModel) {
		this.ddProjectBelongModel = ddProjectBelongModel;
	}

	/**
	 * Gets the dd project secret level.
	 *
	 * @return the dd project secret level
	 */
	public String getDdProjectSecretLevel() {
		return ddProjectSecretLevel;
	}

	/**
	 * Sets the dd project secret level.
	 *
	 * @param ddProjectSecretLevel
	 *            the new dd project secret level
	 */
	public void setDdProjectSecretLevel(String ddProjectSecretLevel) {
		this.ddProjectSecretLevel = ddProjectSecretLevel;
	}

	/**
	 * Gets the dd project change person id.
	 *
	 * @return the dd project change person id
	 */
	public Long getDdProjectChangePersonId() {
		return ddProjectChangePersonId;
	}

	/**
	 * Sets the dd project change person id.
	 *
	 * @param ddProjectChangePersonId
	 *            the new dd project change person id
	 */
	public void setDdProjectChangePersonId(Long ddProjectChangePersonId) {
		this.ddProjectChangePersonId = ddProjectChangePersonId;
	}

	/**
	 * Gets the dd project priority.
	 *
	 * @return the dd project priority
	 */
	public Long getDdProjectPriority() {
		return ddProjectPriority;
	}

	/**
	 * Sets the dd project priority.
	 *
	 * @param ddProjectPriority
	 *            the new dd project priority
	 */
	public void setDdProjectPriority(Long ddProjectPriority) {
		this.ddProjectPriority = ddProjectPriority;
	}

	/**
	 * Gets the dd project remark.
	 *
	 * @return the dd project remark
	 */
	public String getDdProjectRemark() {
		return ddProjectRemark;
	}

	/**
	 * Sets the dd project remark.
	 *
	 * @param ddProjectRemark
	 *            the new dd project remark
	 */
	public void setDdProjectRemark(String ddProjectRemark) {
		this.ddProjectRemark = ddProjectRemark;
	}

	/**
	 * Gets the dd project state.
	 *
	 * @return the dd project state
	 */
	public Short getDdProjectState() {
		return ddProjectState;
	}

	/**
	 * Sets the dd project state.
	 *
	 * @param ddProjectState
	 *            the new dd project state
	 */
	public void setDdProjectState(Short ddProjectState) {
		this.ddProjectState = ddProjectState;
	}

	/**
	 * Gets the dd project plan start date.
	 *
	 * @return the dd project plan start date
	 */
	public String getDdProjectPlanStartDate() {
		return ddProjectPlanStartDate;
	}

	/**
	 * Sets the dd project plan start date.
	 *
	 * @param ddProjectPlanStartDate
	 *            the new dd project plan start date
	 */
	public void setDdProjectPlanStartDate(String ddProjectPlanStartDate) {
		this.ddProjectPlanStartDate = ddProjectPlanStartDate;
	}

	/**
	 * Gets the dd project complete date.
	 *
	 * @return the dd project complete date
	 */
	public String getDdProjectCompleteDate() {
		return ddProjectCompleteDate;
	}

	/**
	 * Sets the dd project complete date.
	 *
	 * @param ddProjectCompleteDate
	 *            the new dd project complete date
	 */
	public void setDdProjectCompleteDate(String ddProjectCompleteDate) {
		this.ddProjectCompleteDate = ddProjectCompleteDate;
	}

	/**
	 * Gets the dd project actual start date.
	 *
	 * @return the dd project actual start date
	 */
	public String getDdProjectActualStartDate() {
		return ddProjectActualStartDate;
	}

	/**
	 * Sets the dd project actual start date.
	 *
	 * @param ddProjectActualStartDate
	 *            the new dd project actual start date
	 */
	public void setDdProjectActualStartDate(String ddProjectActualStartDate) {
		this.ddProjectActualStartDate = ddProjectActualStartDate;
	}

	/**
	 * Gets the dd project actual complete data.
	 *
	 * @return the dd project actual complete data
	 */
	public String getDdProjectActualCompleteData() {
		return ddProjectActualCompleteData;
	}

	/**
	 * Sets the dd project actual complete data.
	 *
	 * @param ddProjectActualCompleteData
	 *            the new dd project actual complete data
	 */
	public void setDdProjectActualCompleteData(String ddProjectActualCompleteData) {
		this.ddProjectActualCompleteData = ddProjectActualCompleteData;
	}

	/**
	 * Gets the dd project current stage.
	 *
	 * @return the dd project current stage
	 */
	public String getDdProjectCurrentStage() {
		return ddProjectCurrentStage;
	}

	/**
	 * Sets the dd project current stage.
	 *
	 * @param ddProjectCurrentStage
	 *            the new dd project current stage
	 */
	public void setDdProjectCurrentStage(String ddProjectCurrentStage) {
		this.ddProjectCurrentStage = ddProjectCurrentStage;
	}

	/**
	 * Gets the dd project schedule state.
	 *
	 * @return the dd project schedule state
	 */
	public String getDdProjectScheduleState() {
		return ddProjectScheduleState;
	}

	/**
	 * Sets the dd project schedule state.
	 *
	 * @param ddProjectScheduleState
	 *            the new dd project schedule state
	 */
	public void setDdProjectScheduleState(String ddProjectScheduleState) {
		this.ddProjectScheduleState = ddProjectScheduleState;
	}
	/**
	 * Gets the dd project phase id.
	 *
	 * @return the dd project phase name
	 */
	public String getDdProjectPhaseName() {
		return ddProjectPhaseName;
	}

	/**
	 * Sets the dd project phase id.
	 *
	 * @param ddProjectPhaseName
	 *            the new dd project phase name
	 */
	public void setDdProjectPhaseName(String ddProjectPhaseName) {
		this.ddProjectPhaseName = ddProjectPhaseName;
	}



    public void setTaskInfoList(List<TaskInfo> taskInfoList)
    {
        this.taskInfoList = taskInfoList;
    }
    /**
     * 返回 taskinfolist列表20161202
     * @return
     */
    public List<TaskInfo> getTaskInfoList()
    {
        return this.taskInfoList;
    }

	/**
	 * 获取第一个业务环节
	 * @return
	 */
	public TaskInfo getFirstDefSegment(){
		if(taskInfoList==null){
			return null;
		}
		for(TaskInfo task:taskInfoList){
            if(1==1){
//			if(task.getDdTaskId()==1){
				return task;
			}
		}
		return null;
	}
    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Project [ddProjectId=");
		builder.append(ddProjectId);
		builder.append(", ddProjectName=");
		builder.append(ddProjectName);
		builder.append(", ddProjectResponsibleUnits=");
		builder.append(ddProjectResponsibleUnits);
		builder.append(", ddProjectPhaseId=");
		builder.append(ddProjectPhaseId);
		builder.append(", ddProjectCreateDatatime=");
		builder.append(ddProjectCreateDatatime);
		builder.append(", ddProjectDescription=");
		builder.append(ddProjectDescription);
		builder.append(", ddProjectOwnerSystemId=");
		builder.append(ddProjectOwnerSystemId);
		builder.append(", ddProjectCreatorId=");
		builder.append(ddProjectCreatorId);
		builder.append(", ddProjectFixedPattern=");
		builder.append(ddProjectFixedPattern);
		builder.append(", ddProjectResponsiblePersonId=");
		builder.append(ddProjectResponsiblePersonId);
		builder.append(", ddProjectType=");
		builder.append(ddProjectType);
		builder.append(", ddProjectBelongModel=");
		builder.append(ddProjectBelongModel);
		builder.append(", ddProjectSecretLevel=");
		builder.append(ddProjectSecretLevel);
		builder.append(", ddProjectChangePersonId=");
		builder.append(ddProjectChangePersonId);
		builder.append(", ddProjectPriority=");
		builder.append(ddProjectPriority);
		builder.append(", ddProjectRemark=");
		builder.append(ddProjectRemark);
		builder.append(", ddProjectState=");
		builder.append(ddProjectState);
		builder.append(", ddProjectPlanStartDate=");
		builder.append(ddProjectPlanStartDate);
		builder.append(", ddProjectCompleteDate=");
		builder.append(ddProjectCompleteDate);
		builder.append(", ddProjectActualStartDate=");
		builder.append(ddProjectActualStartDate);
		builder.append(", ddProjectActualCompleteData=");
		builder.append(ddProjectActualCompleteData);
		builder.append(", ddProjectCurrentStage=");
		builder.append(ddProjectCurrentStage);
		builder.append(", ddProjectScheduleState=");
		builder.append(ddProjectScheduleState);
		builder.append(", ddProjectPhaseName=");
		builder.append(ddProjectPhaseName);
		builder.append("]");
		return builder.toString();
	}
}