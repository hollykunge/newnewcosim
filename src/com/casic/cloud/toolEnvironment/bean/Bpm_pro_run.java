package com.casic.cloud.toolEnvironment.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Bpm_pro_run implements Serializable{
	private Long runId;
	private Long defId;
	private String processName;
	private String subject;
	private Long creatorId;
	private String creator;
	private Timestamp createtime;
	private String busDescp;
	private Byte status;
	private String actInstId;
	private String actDefId;
	private String businessKey;
	private String businessUrl;
	private Timestamp endTime;
	private Long duration;
	private String pkName;
	private String tableName;
	private Long PARENTID;
	private Long startOrgId;
	private String startOrgName;
	private Long tableId;
	@Id
	@GeneratedValue
	public Long getRunId() {
		return runId;
	}
	public void setRunId(Long runId) {
		this.runId = runId;
	}
	public Long getDefId() {
		return defId;
	}
	public void setDefId(Long defId) {
		this.defId = defId;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getBusDescp() {
		return busDescp;
	}
	public void setBusDescp(String busDescp) {
		this.busDescp = busDescp;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getActInstId() {
		return actInstId;
	}
	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}
	public String getActDefId() {
		return actDefId;
	}
	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getBusinessUrl() {
		return businessUrl;
	}
	public void setBusinessUrl(String businessUrl) {
		this.businessUrl = businessUrl;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Long getPARENTID() {
		return PARENTID;
	}
	public void setPARENTID(Long pARENTID) {
		PARENTID = pARENTID;
	}
	public Long getStartOrgId() {
		return startOrgId;
	}
	public void setStartOrgId(Long startOrgId) {
		this.startOrgId = startOrgId;
	}
	public String getStartOrgName() {
		return startOrgName;
	}
	public void setStartOrgName(String startOrgName) {
		this.startOrgName = startOrgName;
	}
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	
		

}
