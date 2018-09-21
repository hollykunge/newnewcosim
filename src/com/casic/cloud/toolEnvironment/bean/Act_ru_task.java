package com.casic.cloud.toolEnvironment.bean;

import java.io.Serializable;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Act_ru_task implements Serializable{
	private Long ID_;
	private Integer REV_;
	private Long EXECUTION_ID_;
	private Long PROC_INST_ID_;
	private String PROC_DEF_ID_;
	private String NAME_;
	private Long PARENT_TASK_ID_;
	private String DESCRIPTION_;
	private String TASK_DEF_KEY_;
	private Long OWNER_;
	private Long ASSIGNEE_;
	private String DELEGATION_;
	private Integer PRIORITY_;
	private Timestamp CREATE_TIME_;
	private Timestamp DUE_DATE_;
	@Id
	@GeneratedValue
	public Long getID_() {
		return ID_;
	}
	public void setID_(Long iD_) {
		ID_ = iD_;
	}
	public Integer getREV_() {
		return REV_;
	}
	public void setREV_(Integer rEV_) {
		REV_ = rEV_;
	}
	public Long getEXECUTION_ID_() {
		return EXECUTION_ID_;
	}
	public void setEXECUTION_ID_(Long eXECUTION_ID_) {
		EXECUTION_ID_ = eXECUTION_ID_;
	}
	public Long getPROC_INST_ID_() {
		return PROC_INST_ID_;
	}
	public void setPROC_INST_ID_(Long pROC_INST_ID_) {
		PROC_INST_ID_ = pROC_INST_ID_;
	}
	public String getPROC_DEF_ID_() {
		return PROC_DEF_ID_;
	}
	public void setPROC_DEF_ID_(String pROC_DEF_ID_) {
		PROC_DEF_ID_ = pROC_DEF_ID_;
	}
	public String getNAME_() {
		return NAME_;
	}
	public void setNAME_(String nAME_) {
		NAME_ = nAME_;
	}
	public Long getPARENT_TASK_ID_() {
		return PARENT_TASK_ID_;
	}
	public void setPARENT_TASK_ID_(Long pARENT_TASK_ID_) {
		PARENT_TASK_ID_ = pARENT_TASK_ID_;
	}
	public String getDESCRIPTION_() {
		return DESCRIPTION_;
	}
	public void setDESCRIPTION_(String dESCRIPTION_) {
		DESCRIPTION_ = dESCRIPTION_;
	}
	public String getTASK_DEF_KEY_() {
		return TASK_DEF_KEY_;
	}
	public void setTASK_DEF_KEY_(String tASK_DEF_KEY_) {
		TASK_DEF_KEY_ = tASK_DEF_KEY_;
	}
	public Long getOWNER_() {
		return OWNER_;
	}
	public void setOWNER_(Long oWNER_) {
		OWNER_ = oWNER_;
	}
	public Long getASSIGNEE_() {
		return ASSIGNEE_;
	}
	public void setASSIGNEE_(Long aSSIGNEE_) {
		ASSIGNEE_ = aSSIGNEE_;
	}
	public String getDELEGATION_() {
		return DELEGATION_;
	}
	public void setDELEGATION_(String dELEGATION_) {
		DELEGATION_ = dELEGATION_;
	}
	public Integer getPRIORITY_() {
		return PRIORITY_;
	}
	public void setPRIORITY_(Integer pRIORITY_) {
		PRIORITY_ = pRIORITY_;
	}
	public Timestamp getCREATE_TIME_() {
		return CREATE_TIME_;
	}
	public void setCREATE_TIME_(Timestamp cREATE_TIME_) {
		CREATE_TIME_ = cREATE_TIME_;
	}
	public Timestamp getDUE_DATE_() {
		return DUE_DATE_;
	}
	public void setDUE_DATE_(Timestamp dUE_DATE_) {
		DUE_DATE_ = dUE_DATE_;
	}
	
		
	
	

}
