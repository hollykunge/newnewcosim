package com.casic.cloud.model.crowdsourcing.crowdsourcingAgreement;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_crowdsourcing_agreement Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 16:59:12
 */
public class CrowdsourcingAgreement extends BaseModel
{
	// 表单ID
	protected Long  id;
	// 表单code
	protected String  code;
	// 制单人ID
	protected Long  operaterId;
	// 制单人名称
	protected String  operaterName;
	// 制单企业ID
	protected Long  operaterEnterpId;
	// 制单企业名称
	protected String  operaterEnterpName;
	// 制单日期
	protected java.util.Date  operateDate;
	// 发布状态
	protected String  publishStatus;
	// 接包方签署状态
	protected String  signedState;
	// 签署意见说明
	protected String  signedOpinion;
	// 来源众包需求表ID
	protected Long  sourceformCrowdsourcingId;
	// 来源众包需求表CODE
	protected String  sourceformCrowdsourcingCode;
	// 接包方组织ID
	protected Long  receiveOrgId;
	// 接包方组织名称
	protected String  receiveOrgName;
	// 接包人ID
	protected Long  receiverId;
	// 接包人名称
	protected String  receiverName;
	// 协议价格
	protected Double  agreePrice;
	// 币种
	protected String  currency;
	// 研发任务完成时间
	protected java.util.Date  completeTime;
	// 研发物品ID
	protected Long  materialId;
	// 研发物品名称
	protected String  materialName;
	// 研发物品CODE
	protected String  materialCode;
	// 研发物品分类
	protected String  materialType;
	// 研发物品bom层级
	protected String  materialBomLevel;
	//cloud_crowdsourcing_agreement_detail列表
	protected List<CrowdsourcingAgreementDetail> crowdsourcingAgreementDetailList=new ArrayList<CrowdsourcingAgreementDetail>();
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
	 * 返回 表单code
	 * @return
	 */
	public String getCode() 
	{
		return this.code;
	}
	public void setOperaterId(Long operaterId) 
	{
		this.operaterId = operaterId;
	}
	/**
	 * 返回 制单人ID
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
	 * 返回 制单人名称
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
	 * 返回 制单企业ID
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
	 * 返回 制单企业名称
	 * @return
	 */
	public String getOperaterEnterpName() 
	{
		return this.operaterEnterpName;
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
	public void setPublishStatus(String publishStatus) 
	{
		this.publishStatus = publishStatus;
	}
	/**
	 * 返回 发布状态
	 * @return
	 */
	public String getPublishStatus() 
	{
		return this.publishStatus;
	}
	public void setSignedState(String signedState) 
	{
		this.signedState = signedState;
	}
	/**
	 * 返回 接包方签署状态
	 * @return
	 */
	public String getSignedState() 
	{
		return this.signedState;
	}
	public void setSignedOpinion(String signedOpinion) 
	{
		this.signedOpinion = signedOpinion;
	}
	/**
	 * 返回 签署意见说明
	 * @return
	 */
	public String getSignedOpinion() 
	{
		return this.signedOpinion;
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
	public void setReceiveOrgId(Long receiveOrgId) 
	{
		this.receiveOrgId = receiveOrgId;
	}
	/**
	 * 返回 接包方组织ID
	 * @return
	 */
	public Long getReceiveOrgId() 
	{
		return this.receiveOrgId;
	}
	public void setReceiveOrgName(String receiveOrgName) 
	{
		this.receiveOrgName = receiveOrgName;
	}
	/**
	 * 返回 接包方组织名称
	 * @return
	 */
	public String getReceiveOrgName() 
	{
		return this.receiveOrgName;
	}
	public void setReceiverId(Long receiverId) 
	{
		this.receiverId = receiverId;
	}
	/**
	 * 返回 接包人ID
	 * @return
	 */
	public Long getReceiverId() 
	{
		return this.receiverId;
	}
	public void setReceiverName(String receiverName) 
	{
		this.receiverName = receiverName;
	}
	/**
	 * 返回 接包人名称
	 * @return
	 */
	public String getReceiverName() 
	{
		return this.receiverName;
	}
	public void setAgreePrice(Double agreePrice) 
	{
		this.agreePrice = agreePrice;
	}
	/**
	 * 返回 协议价格
	 * @return
	 */
	public Double getAgreePrice() 
	{
		return this.agreePrice;
	}
	public void setCurrency(String currency) 
	{
		this.currency = currency;
	}
	/**
	 * 返回 币种
	 * @return
	 */
	public String getCurrency() 
	{
		return this.currency;
	}
	public void setCompleteTime(java.util.Date completeTime) 
	{
		this.completeTime = completeTime;
	}
	/**
	 * 返回 研发任务完成时间
	 * @return
	 */
	public java.util.Date getCompleteTime() 
	{
		return this.completeTime;
	}
	public void setMaterialId(Long materialId) 
	{
		this.materialId = materialId;
	}
	/**
	 * 返回 研发物品ID
	 * @return
	 */
	public Long getMaterialId() 
	{
		return this.materialId;
	}
	public void setMaterialName(String materialName) 
	{
		this.materialName = materialName;
	}
	/**
	 * 返回 研发物品名称
	 * @return
	 */
	public String getMaterialName() 
	{
		return this.materialName;
	}
	public void setMaterialCode(String materialCode) 
	{
		this.materialCode = materialCode;
	}
	/**
	 * 返回 研发物品CODE
	 * @return
	 */
	public String getMaterialCode() 
	{
		return this.materialCode;
	}
	public void setMaterialType(String materialType) 
	{
		this.materialType = materialType;
	}
	/**
	 * 返回 研发物品分类
	 * @return
	 */
	public String getMaterialType() 
	{
		return this.materialType;
	}
	public void setMaterialBomLevel(String materialBomLevel) 
	{
		this.materialBomLevel = materialBomLevel;
	}
	/**
	 * 返回 研发物品bom层级
	 * @return
	 */
	public String getMaterialBomLevel() 
	{
		return this.materialBomLevel;
	}
	public void setCrowdsourcingAgreementDetailList(List<CrowdsourcingAgreementDetail> crowdsourcingAgreementDetailList) 
	{
		this.crowdsourcingAgreementDetailList = crowdsourcingAgreementDetailList;
	}
	/**
	 * 返回 cloud_crowdsourcing_agreement_detail列表
	 * @return
	 */
	public List<CrowdsourcingAgreementDetail> getCrowdsourcingAgreementDetailList() 
	{
		return this.crowdsourcingAgreementDetailList;
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
		if (!(object instanceof CrowdsourcingAgreement)) 
		{
			return false;
		}
		CrowdsourcingAgreement rhs = (CrowdsourcingAgreement) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.code, rhs.code)
		.append(this.operaterId, rhs.operaterId)
		.append(this.operaterName, rhs.operaterName)
		.append(this.operaterEnterpId, rhs.operaterEnterpId)
		.append(this.operaterEnterpName, rhs.operaterEnterpName)
		.append(this.operateDate, rhs.operateDate)
		.append(this.publishStatus, rhs.publishStatus)
		.append(this.signedState, rhs.signedState)
		.append(this.signedOpinion, rhs.signedOpinion)
		.append(this.sourceformCrowdsourcingId, rhs.sourceformCrowdsourcingId)
		.append(this.sourceformCrowdsourcingCode, rhs.sourceformCrowdsourcingCode)
		.append(this.receiveOrgId, rhs.receiveOrgId)
		.append(this.receiveOrgName, rhs.receiveOrgName)
		.append(this.receiverId, rhs.receiverId)
		.append(this.receiverName, rhs.receiverName)
		.append(this.agreePrice, rhs.agreePrice)
		.append(this.currency, rhs.currency)
		.append(this.completeTime, rhs.completeTime)
		.append(this.materialId, rhs.materialId)
		.append(this.materialName, rhs.materialName)
		.append(this.materialCode, rhs.materialCode)
		.append(this.materialType, rhs.materialType)
		.append(this.materialBomLevel, rhs.materialBomLevel)
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
		.append(this.operaterId) 
		.append(this.operaterName) 
		.append(this.operaterEnterpId) 
		.append(this.operaterEnterpName) 
		.append(this.operateDate) 
		.append(this.publishStatus) 
		.append(this.signedState) 
		.append(this.signedOpinion) 
		.append(this.sourceformCrowdsourcingId) 
		.append(this.sourceformCrowdsourcingCode) 
		.append(this.receiveOrgId) 
		.append(this.receiveOrgName) 
		.append(this.receiverId) 
		.append(this.receiverName) 
		.append(this.agreePrice) 
		.append(this.currency) 
		.append(this.completeTime) 
		.append(this.materialId) 
		.append(this.materialName) 
		.append(this.materialCode) 
		.append(this.materialType) 
		.append(this.materialBomLevel) 
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
		.append("operaterId", this.operaterId) 
		.append("operaterName", this.operaterName) 
		.append("operaterEnterpId", this.operaterEnterpId) 
		.append("operaterEnterpName", this.operaterEnterpName) 
		.append("operateDate", this.operateDate) 
		.append("publishStatus", this.publishStatus) 
		.append("signedState", this.signedState) 
		.append("signedOpinion", this.signedOpinion) 
		.append("sourceformCrowdsourcingId", this.sourceformCrowdsourcingId) 
		.append("sourceformCrowdsourcingCode", this.sourceformCrowdsourcingCode) 
		.append("receiveOrgId", this.receiveOrgId) 
		.append("receiveOrgName", this.receiveOrgName) 
		.append("receiverId", this.receiverId) 
		.append("receiverName", this.receiverName) 
		.append("agreePrice", this.agreePrice) 
		.append("currency", this.currency) 
		.append("completeTime", this.completeTime) 
		.append("materialId", this.materialId) 
		.append("materialName", this.materialName) 
		.append("materialCode", this.materialCode) 
		.append("materialType", this.materialType) 
		.append("materialBomLevel", this.materialBomLevel) 
		.toString();
	}
   
  

}