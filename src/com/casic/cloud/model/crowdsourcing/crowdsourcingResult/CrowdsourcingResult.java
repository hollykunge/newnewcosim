package com.casic.cloud.model.crowdsourcing.crowdsourcingResult;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_crowdsourcing_result Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:15:19
 */
public class CrowdsourcingResult extends BaseModel
{
	// 表单ID
	protected Long  id;
	// 表单CODE
	protected String  code;
	// 制单日期
	protected java.util.Date  operateDate;
	// 提交人ID
	protected Long  operaterId;
	// 提交人名称
	protected String  operaterName;
	// 提交人企业ID
	protected Long  operaterEnterpId;
	// 提交人企业名称
	protected String  operaterEnterpName;
	// 来源众包需求表ID
	protected Long  sourceformCrowdsourcingId;
	// 来源众包需求表CODE
	protected String  sourceformCrowdsourcingCode;
	// 来源合同ID
	protected Long  sourceformAgreementId;
	// 来源合同CODE
	protected String  sourceformAgreementCode;
	// 状态
	protected String  state;
	// 审核类型
	protected String  auditType;
	// 审核意见
	protected String  auditOpinion;
	// 结果附件IDs
	protected String  resultAttachmentIds;
	// 结果包名称
	protected String  resultName;
	// 结果包说明
	protected String  resultInfo;
	//cloud_crowdsourcing_result_detail列表
	protected List<CrowdsourcingResultDetail> crowdsourcingResultDetailList=new ArrayList<CrowdsourcingResultDetail>();
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 表单ID
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setCode(String code) 
	{
		this.code = code;
	}
	/**
	 * 返回 表单CODE
	 * @return
	 */
	public String getCode() 
	{
		return this.code;
	}
	public void setOperateDate(java.util.Date operateDate) 
	{
		this.operateDate = operateDate;
	}
	/**
	 * 返回 制单日期
	 * @return
	 */
	public java.util.Date getOperateDate() 
	{
		return this.operateDate;
	}
	public void setOperaterId(Long operaterId) 
	{
		this.operaterId = operaterId;
	}
	/**
	 * 返回 提交人ID
	 * @return
	 */
	public Long getOperaterId() 
	{
		return this.operaterId;
	}
	public void setOperaterName(String operaterName) 
	{
		this.operaterName = operaterName;
	}
	/**
	 * 返回 提交人名称
	 * @return
	 */
	public String getOperaterName() 
	{
		return this.operaterName;
	}
	public void setOperaterEnterpId(Long operaterEnterpId) 
	{
		this.operaterEnterpId = operaterEnterpId;
	}
	/**
	 * 返回 提交人企业ID
	 * @return
	 */
	public Long getOperaterEnterpId() 
	{
		return this.operaterEnterpId;
	}
	public void setOperaterEnterpName(String operaterEnterpName) 
	{
		this.operaterEnterpName = operaterEnterpName;
	}
	/**
	 * 返回 提交人企业名称
	 * @return
	 */
	public String getOperaterEnterpName() 
	{
		return this.operaterEnterpName;
	}
	public void setSourceformCrowdsourcingId(Long sourceformCrowdsourcingId) 
	{
		this.sourceformCrowdsourcingId = sourceformCrowdsourcingId;
	}
	/**
	 * 返回 来源众包需求表ID
	 * @return
	 */
	public Long getSourceformCrowdsourcingId() 
	{
		return this.sourceformCrowdsourcingId;
	}
	public void setSourceformCrowdsourcingCode(String sourceformCrowdsourcingCode) 
	{
		this.sourceformCrowdsourcingCode = sourceformCrowdsourcingCode;
	}
	/**
	 * 返回 来源众包需求表CODE
	 * @return
	 */
	public String getSourceformCrowdsourcingCode() 
	{
		return this.sourceformCrowdsourcingCode;
	}
	public void setSourceformAgreementId(Long sourceformAgreementId) 
	{
		this.sourceformAgreementId = sourceformAgreementId;
	}
	/**
	 * 返回 来源合同ID
	 * @return
	 */
	public Long getSourceformAgreementId() 
	{
		return this.sourceformAgreementId;
	}
	public void setSourceformAgreementCode(String sourceformAgreementCode) 
	{
		this.sourceformAgreementCode = sourceformAgreementCode;
	}
	/**
	 * 返回 来源合同CODE
	 * @return
	 */
	public String getSourceformAgreementCode() 
	{
		return this.sourceformAgreementCode;
	}
	public void setState(String state) 
	{
		this.state = state;
	}
	/**
	 * 返回 状态
	 * @return
	 */
	public String getState() 
	{
		return this.state;
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
	public void setCrowdsourcingResultDetailList(List<CrowdsourcingResultDetail> crowdsourcingResultDetailList) 
	{
		this.crowdsourcingResultDetailList = crowdsourcingResultDetailList;
	}
	/**
	 * 返回 cloud_crowdsourcing_result_detail列表
	 * @return
	 */
	public List<CrowdsourcingResultDetail> getCrowdsourcingResultDetailList() 
	{
		return this.crowdsourcingResultDetailList;
	}

	protected Long runid;
	protected Short runStatus=0;
	protected Long actInstId;
	
	public Long getRunid() {
		return runid;
	}
	public void setRunid(Long runid) {
		this.runid = runid;
	}
	public Short getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(Short runStatus) {
		this.runStatus = runStatus;
	}
	
	public Long getActInstId() {
		return actInstId;
	}
	public void setActInstId(Long actInstId) {
		this.actInstId = actInstId;
	}

   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CrowdsourcingResult)) 
		{
			return false;
		}
		CrowdsourcingResult rhs = (CrowdsourcingResult) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.code, rhs.code)
		.append(this.operateDate, rhs.operateDate)
		.append(this.operaterId, rhs.operaterId)
		.append(this.operaterName, rhs.operaterName)
		.append(this.operaterEnterpId, rhs.operaterEnterpId)
		.append(this.operaterEnterpName, rhs.operaterEnterpName)
		.append(this.sourceformCrowdsourcingId, rhs.sourceformCrowdsourcingId)
		.append(this.sourceformCrowdsourcingCode, rhs.sourceformCrowdsourcingCode)
		.append(this.sourceformAgreementId, rhs.sourceformAgreementId)
		.append(this.sourceformAgreementCode, rhs.sourceformAgreementCode)
		.append(this.state, rhs.state)
		.append(this.auditType, rhs.auditType)
		.append(this.auditOpinion, rhs.auditOpinion)
		.append(this.resultAttachmentIds, rhs.resultAttachmentIds)
		.append(this.resultName, rhs.resultName)
		.append(this.resultInfo, rhs.resultInfo)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.code) 
		.append(this.operateDate) 
		.append(this.operaterId) 
		.append(this.operaterName) 
		.append(this.operaterEnterpId) 
		.append(this.operaterEnterpName) 
		.append(this.sourceformCrowdsourcingId) 
		.append(this.sourceformCrowdsourcingCode) 
		.append(this.sourceformAgreementId) 
		.append(this.sourceformAgreementCode) 
		.append(this.state) 
		.append(this.auditType) 
		.append(this.auditOpinion) 
		.append(this.resultAttachmentIds) 
		.append(this.resultName) 
		.append(this.resultInfo) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("code", this.code) 
		.append("operateDate", this.operateDate) 
		.append("operaterId", this.operaterId) 
		.append("operaterName", this.operaterName) 
		.append("operaterEnterpId", this.operaterEnterpId) 
		.append("operaterEnterpName", this.operaterEnterpName) 
		.append("sourceformCrowdsourcingId", this.sourceformCrowdsourcingId) 
		.append("sourceformCrowdsourcingCode", this.sourceformCrowdsourcingCode) 
		.append("sourceformAgreementId", this.sourceformAgreementId) 
		.append("sourceformAgreementCode", this.sourceformAgreementCode) 
		.append("state", this.state) 
		.append("auditType", this.auditType) 
		.append("auditOpinion", this.auditOpinion) 
		.append("resultAttachmentIds", this.resultAttachmentIds) 
		.append("resultName", this.resultName) 
		.append("resultInfo", this.resultInfo) 
		.toString();
	}
   
  

}