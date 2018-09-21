package com.hotent.platform.model.bpm;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;

/**
 * 对象功能:流程实例扩展Model对象 开发公司: 开发人员:csx 创建时间:2011-12-03 09:33:06
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
public class ProcessRun extends BaseModel  implements Cloneable {
	/** 挂起状态 */
	public static final Short STATUS_SUSPEND = 0;
	/** 运行状态 */
	public static final Short STATUS_RUNNING = 1;
	/** 结束状态 */
	public static final Short STATUS_FINISH = 2;
	/** 人工状态 */
	public static final Short STATUS_MANUAL_FINISH = 3;
	
	/**不追回*/
	public static final Short RECOVER_NO = 0;
	/**追回*/
	public static final Short RECOVER = 1;

	// runId
	protected Long runId;
	// 流程定义ID
	protected Long defId;
	// 流程实例标题
	protected String subject;
	// 创建人ID
	protected Long creatorId;
	// 创建人
	protected String creator;
	// 创建时间
	protected Date createtime;
	// 业务表单简述
	protected String busDescp;
	// 状态
	protected Short status;
	// ACT流程实例ID
	protected String actInstId;
	// ACT流程定义ID
	protected String actDefId;
	// businessKey
	protected String businessKey;
	// businessUrl
	protected String businessUrl;

	// 结束时间
	protected Date endTime;
	// 执行持续时间总长（毫秒)
	protected Long duration;

	// 流程定义名称
	protected String processName;
	// 主键名称
	protected String pkName = "";
	// 表名
	protected String tableName = "";
	protected Long tableId = null;
	// 父流程运行的ID。
	protected Long parentId = 0L;
	// 发起人所在组织名称
	protected String startOrgName = "";
	// 发起人所在组织Id。
	protected Long startOrgId = 0L;
	// 追回状态
	protected Short recover = RECOVER_NO;

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	/**
	 * 返回 runId
	 * 
	 * @return
	 */
	public Long getRunId() {
		return runId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	/**
	 * 返回 流程定义ID
	 * 
	 * @return
	 */
	public Long getDefId() {
		return defId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 返回 流程实例标题
	 * 
	 * @return
	 */
	public String getSubject() {
		return subject;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * 返回 创建人ID
	 * 
	 * @return
	 */
	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * 返回 创建人
	 * 
	 * @return
	 */
	public String getCreator() {
		return creator;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * 返回 创建时间
	 * 
	 * @return
	 */
	public Date getCreatetime() {
		return createtime;
	}

	public void setBusDescp(String busDescp) {
		this.busDescp = busDescp;
	}

	/**
	 * 返回 业务表单简述
	 * 
	 * @return
	 */
	public String getBusDescp() {
		return busDescp;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	/**
	 * 返回 状态
	 * 
	 * @return
	 */
	public Short getStatus() {
		return status;
	}

	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}

	/**
	 * 返回 ACT流程实例ID
	 * 
	 * @return
	 */
	public String getActInstId() {
		return actInstId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	/**
	 * 返回 ACT流程定义ID
	 * 
	 * @return
	 */
	public String getActDefId() {
		return actDefId;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	/**
	 * 返回 businessKey
	 * 
	 * @return
	 */
	public String getBusinessKey() {
		return businessKey;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBusinessUrl() {
		return businessUrl;
	}

	public void setBusinessUrl(String businessUrl) {
		this.businessUrl = businessUrl;
	}

	// public String getPkName() {
	// if(StringUtil.isEmpty(pkName) ){
	// return TableModel.PK_COLUMN_NAME;
	// }
	// return pkName;
	// }
	// public void setPkName(String pkName) {
	// this.pkName = pkName;
	// }
	 public String getTableName() {
	 return tableName;
	 }
	 public void setTableName(String tableName) {
	 this.tableName = tableName;
	 }
	 
	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProcessRun)) {
			return false;
		}
		ProcessRun rhs = (ProcessRun) object;
		return new EqualsBuilder().append(this.runId, rhs.runId)
				.append(this.defId, rhs.defId)
				.append(this.subject, rhs.subject)
				.append(this.creatorId, rhs.creatorId)
				.append(this.creator, rhs.creator)
				.append(this.createtime, rhs.createtime)
				.append(this.busDescp, rhs.busDescp)
				.append(this.status, rhs.status)
				.append(this.actInstId, rhs.actInstId)
				.append(this.actDefId, rhs.actDefId)
				.append(this.businessKey, rhs.businessKey)
				.append(this.businessUrl, rhs.businessUrl).isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.runId)
				.append(this.defId).append(this.subject).append(this.creatorId)
				.append(this.creator).append(this.createtime)
				.append(this.busDescp).append(this.status)
				.append(this.actInstId).append(this.actDefId)
				.append(this.businessKey).append(this.businessUrl).toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("runId", this.runId)
				.append("defId", this.defId).append("subject", this.subject)
				.append("creatorId", this.creatorId)
				.append("creator", this.creator)
				.append("createtime", this.createtime)
				.append("busDescp", this.busDescp)
				.append("status", this.status)
				.append("actInstId", this.actInstId)
				.append("actDefId", this.actDefId)
				.append("businessKey", this.businessKey)
				.append("businessUrl", this.businessUrl).toString();
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getStartOrgName() {
		return startOrgName;
	}

	public void setStartOrgName(String startOrgName) {
		this.startOrgName = startOrgName;
	}

	public Long getStartOrgId() {
		return startOrgId;
	}

	public void setStartOrgId(Long startOrgId) {
		this.startOrgId = startOrgId;
	}

	public Short getRecover() {
		return recover;
	}

	public void setRecover(Short recover) {
		this.recover = recover;
	}
	
	public Object clone() {
		ProcessRun obj = null;
		try {
			obj = (ProcessRun) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

}