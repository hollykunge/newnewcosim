package com.casic.cloud.model.crowdsourcing.crowdsourcingResult;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_crowdsourcing_result_detail Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-11 12:50:28
 */
public class CrowdsourcingResultDetail extends BaseModel
{
	// ID
	protected Long  id;
	// 结果包名称
	protected String  resultName;
	// 结果包说明
	protected String  resultInfo;
	// 审核类型
	protected String  auditType;
	// 审核意见
	protected String  auditOpinion;
	// 结果附件IDs
	protected String  resultAttachmentIds;
	// 提交时间
	protected java.util.Date  submitTime;
	// 审核时间
	protected java.util.Date  auditTime;
	
	// 父表ID
	protected Long  resultId;
	
	
	
	public Long getResultId() {
		return resultId;
	}
	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 ID
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setResultName(String resultName) 
	{
		this.resultName = resultName;
	}
	/**
	 * 返回 结果包名称
	 * @return
	 */
	public String getResultName() 
	{
		return this.resultName;
	}
	public void setResultInfo(String resultInfo) 
	{
		this.resultInfo = resultInfo;
	}
	/**
	 * 返回 结果包说明
	 * @return
	 */
	public String getResultInfo() 
	{
		return this.resultInfo;
	}
	public void setAuditType(String auditType) 
	{
		this.auditType = auditType;
	}
	/**
	 * 返回 审核类型
	 * @return
	 */
	public String getAuditType() 
	{
		return this.auditType;
	}
	public void setAuditOpinion(String auditOpinion) 
	{
		this.auditOpinion = auditOpinion;
	}
	/**
	 * 返回 审核意见
	 * @return
	 */
	public String getAuditOpinion() 
	{
		return this.auditOpinion;
	}
	public void setResultAttachmentIds(String resultAttachmentIds) 
	{
		this.resultAttachmentIds = resultAttachmentIds;
	}
	/**
	 * 返回 结果附件IDs
	 * @return
	 */
	public String getResultAttachmentIds() 
	{
		return this.resultAttachmentIds;
	}
	public void setSubmitTime(java.util.Date submitTime) 
	{
		this.submitTime = submitTime;
	}
	/**
	 * 返回 提交时间
	 * @return
	 */
	public java.util.Date getSubmitTime() 
	{
		return this.submitTime;
	}
	public void setAuditTime(java.util.Date auditTime) 
	{
		this.auditTime = auditTime;
	}
	/**
	 * 返回 审核时间
	 * @return
	 */
	public java.util.Date getAuditTime() 
	{
		return this.auditTime;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CrowdsourcingResultDetail)) 
		{
			return false;
		}
		CrowdsourcingResultDetail rhs = (CrowdsourcingResultDetail) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.resultName, rhs.resultName)
		.append(this.resultInfo, rhs.resultInfo)
		.append(this.auditType, rhs.auditType)
		.append(this.auditOpinion, rhs.auditOpinion)
		.append(this.resultAttachmentIds, rhs.resultAttachmentIds)
		.append(this.submitTime, rhs.submitTime)
		.append(this.auditTime, rhs.auditTime)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.resultName) 
		.append(this.resultInfo) 
		.append(this.auditType) 
		.append(this.auditOpinion) 
		.append(this.resultAttachmentIds) 
		.append(this.submitTime) 
		.append(this.auditTime) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("resultName", this.resultName) 
		.append("resultInfo", this.resultInfo) 
		.append("auditType", this.auditType) 
		.append("auditOpinion", this.auditOpinion) 
		.append("resultAttachmentIds", this.resultAttachmentIds) 
		.append("submitTime", this.submitTime) 
		.append("auditTime", this.auditTime) 
		.toString();
	}
   
  

}