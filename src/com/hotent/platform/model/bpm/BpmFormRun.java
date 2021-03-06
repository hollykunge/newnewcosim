package com.hotent.platform.model.bpm;

import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:流程表单运行情况 Model对象
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2012-05-21 16:28:39
 */
public class BpmFormRun extends BaseModel
{	
	
	/**
	 * 表单类型：节点类型
	 */
	public static final short SETTYPE_NODE							=	0;
	/**
	 * 表单类型：开始类型
	 */
	public static final short SETTYPE_START							=	1;
	/**
	 * 表单类型：全类型
	 */
	public static final short SETTYPE_GOBAL							=	2;
	
	// 主键
	protected Long  id;
	// 表单定义ID
	protected Long  formdefId;
	// 表单定义key
	protected Long  formdefKey;
	// 流程实例ID
	protected String  actInstanceId;
	// ACTDEFID
	protected String  actDefId;
	// 流程节点id
	protected String  actNodeId;
	// 流程运行ID
	protected Long  runId;
	// 表单类型0,任务节点1,开始表单2,全局表单
	protected Short  setType=0;
	// FORMTYPE
	protected Short  formType=-1;
	// FORMURL
	protected String  formUrl;
	
	

   
   	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFormdefId() {
		return formdefId;
	}

	public void setFormdefId(Long formdefId) {
		this.formdefId = formdefId;
	}

	public Long getFormdefKey() {
		return formdefKey;
	}

	public void setFormdefKey(Long formdefKey) {
		this.formdefKey = formdefKey;
	}

	public String getActInstanceId() {
		return actInstanceId;
	}

	public void setActInstanceId(String actInstanceId) {
		this.actInstanceId = actInstanceId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActNodeId() {
		return actNodeId;
	}

	public void setActNodeId(String actNodeId) {
		this.actNodeId = actNodeId;
	}

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Short getSetType() {
		return setType;
	}

	public void setSetType(Short setType) {
		this.setType = setType;
	}

	public Short getFormType() {
		return formType;
	}

	public void setFormType(Short formType) {
		this.formType = formType;
	}

	public String getFormUrl() {
		return formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BpmFormRun)) 
		{
			return false;
		}
		BpmFormRun rhs = (BpmFormRun) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		
		.toString();
	}
   
  

}