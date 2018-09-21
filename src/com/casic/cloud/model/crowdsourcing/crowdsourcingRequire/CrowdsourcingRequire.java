package com.casic.cloud.model.crowdsourcing.crowdsourcingRequire;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_crowdsourcing_require Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:06:02
 */
public class CrowdsourcingRequire extends BaseModel
{
	// 需求ID
	protected Long  id;
	// 需求编号
	protected String  code;
	// 需求名称
	protected String  requireName;
	// 需求分类
	protected String  requireType;
	// 需求详细描述
	protected String  requireDescription;
	// 状态
	protected String  status;
	// 发布模式
	protected String  publishMode;
	// 创建时间
	protected java.util.Date  operateDate;
	// 创建人ID
	protected Long  operaterId;
	// 创建人姓名
	protected String  operaterName;
	// 所属企业ID
	protected Long  enterpriseId;
	// 所属企业名称
	protected String  enterpriseName;
	// 流程ID
	protected Long  runId;
	// 保证金
	protected Double  bail;
	// 币种
	protected String  currency;
	// 入围审核要求列表
	protected String  finalists;
	// 公告截止日期
	protected java.util.Date  announcementDeadline;
	// 要求完成时间
	protected java.util.Date  requiredCompleteTime;
	// 附件详情（多个）
	protected String  attachmentDetail;
	// 研发物品ID
	protected Long  materialId;
	// 研发物品CODE
	protected String  materialCode;
	// 研发物品名称
	protected String  materialName;
	// 研发物品类型
	protected String  materialType;
	// 研发物品BOM层级
	protected String  materialBomLevel;
	// 受邀研发组织ID
	protected Long  invitedOrgId;
	// 受邀研发组织名称
	protected String  invitedOrgName;
	// 受邀研发人员IDs
	protected String  invitedUserIds;
	// 受邀所有研发人员姓名
	protected String  invitedUserNames;
	// 受邀研发组IDs
	protected String  invitedGroupIds;
	// 共享文件夹路径
	protected String  sharedPath;
	// 研发资源路径
	protected String  resourcePath;
	//cloud_crowdsourcing_require_detail列表
	protected List<CrowdsourcingRequireDetail> crowdsourcingRequireDetailList=new ArrayList<CrowdsourcingRequireDetail>();
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 需求ID
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
	 * 返回 需求编号
	 * @return
	 */
	public String getCode() 
	{
		return this.code;
	}
	public void setRequireName(String requireName) 
	{
		this.requireName = requireName;
	}
	/**
	 * 返回 需求名称
	 * @return
	 */
	public String getRequireName() 
	{
		return this.requireName;
	}
	public void setRequireType(String requireType) 
	{
		this.requireType = requireType;
	}
	/**
	 * 返回 需求分类
	 * @return
	 */
	public String getRequireType() 
	{
		return this.requireType;
	}
	public void setRequireDescription(String requireDescription) 
	{
		this.requireDescription = requireDescription;
	}
	/**
	 * 返回 需求详细描述
	 * @return
	 */
	public String getRequireDescription() 
	{
		return this.requireDescription;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}
	/**
	 * 返回 状态
	 * @return
	 */
	public String getStatus() 
	{
		return this.status;
	}
	public void setPublishMode(String publishMode) 
	{
		this.publishMode = publishMode;
	}
	/**
	 * 返回 发布模式
	 * @return
	 */
	public String getPublishMode() 
	{
		return this.publishMode;
	}
	public void setOperateDate(java.util.Date operateDate) 
	{
		this.operateDate = operateDate;
	}
	/**
	 * 返回 创建时间
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
	 * 返回 创建人ID
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
	 * 返回 创建人姓名
	 * @return
	 */
	public String getOperaterName() 
	{
		return this.operaterName;
	}
	public void setEnterpriseId(Long enterpriseId) 
	{
		this.enterpriseId = enterpriseId;
	}
	/**
	 * 返回 所属企业ID
	 * @return
	 */
	public Long getEnterpriseId() 
	{
		return this.enterpriseId;
	}
	public void setEnterpriseName(String enterpriseName) 
	{
		this.enterpriseName = enterpriseName;
	}
	/**
	 * 返回 所属企业名称
	 * @return
	 */
	public String getEnterpriseName() 
	{
		return this.enterpriseName;
	}
	public void setRunId(Long runId) 
	{
		this.runId = runId;
	}
	/**
	 * 返回 流程ID
	 * @return
	 */
	public Long getRunId() 
	{
		return this.runId;
	}
	public void setBail(Double bail) 
	{
		this.bail = bail;
	}
	/**
	 * 返回 保证金
	 * @return
	 */
	public Double getBail() 
	{
		return this.bail;
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
	public void setFinalists(String finalists) 
	{
		this.finalists = finalists;
	}
	/**
	 * 返回 入围审核要求列表
	 * @return
	 */
	public String getFinalists() 
	{
		return this.finalists;
	}
	public void setAnnouncementDeadline(java.util.Date announcementDeadline) 
	{
		this.announcementDeadline = announcementDeadline;
	}
	/**
	 * 返回 公告截止日期
	 * @return
	 */
	public java.util.Date getAnnouncementDeadline() 
	{
		return this.announcementDeadline;
	}
	public void setRequiredCompleteTime(java.util.Date requiredCompleteTime) 
	{
		this.requiredCompleteTime = requiredCompleteTime;
	}
	/**
	 * 返回 要求完成时间
	 * @return
	 */
	public java.util.Date getRequiredCompleteTime() 
	{
		return this.requiredCompleteTime;
	}
	public void setAttachmentDetail(String attachmentDetail) 
	{
		this.attachmentDetail = attachmentDetail;
	}
	/**
	 * 返回 附件详情（多个）
	 * @return
	 */
	public String getAttachmentDetail() 
	{
		return this.attachmentDetail;
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
	public void setMaterialType(String materialType) 
	{
		this.materialType = materialType;
	}
	/**
	 * 返回 研发物品类型
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
	 * 返回 研发物品BOM层级
	 * @return
	 */
	public String getMaterialBomLevel() 
	{
		return this.materialBomLevel;
	}
	public void setInvitedOrgId(Long invitedOrgId) 
	{
		this.invitedOrgId = invitedOrgId;
	}
	/**
	 * 返回 受邀研发组织ID
	 * @return
	 */
	public Long getInvitedOrgId() 
	{
		return this.invitedOrgId;
	}
	public void setInvitedOrgName(String invitedOrgName) 
	{
		this.invitedOrgName = invitedOrgName;
	}
	/**
	 * 返回 受邀研发组织名称
	 * @return
	 */
	public String getInvitedOrgName() 
	{
		return this.invitedOrgName;
	}
	public void setInvitedUserIds(String invitedUserIds) 
	{
		this.invitedUserIds = invitedUserIds;
	}
	/**
	 * 返回 受邀研发人员IDs
	 * @return
	 */
	public String getInvitedUserIds() 
	{
		return this.invitedUserIds;
	}
	public void setInvitedUserNames(String invitedUserNames) 
	{
		this.invitedUserNames = invitedUserNames;
	}
	/**
	 * 返回 受邀所有研发人员姓名
	 * @return
	 */
	public String getInvitedUserNames() 
	{
		return this.invitedUserNames;
	}
	public void setInvitedGroupIds(String invitedGroupIds) 
	{
		this.invitedGroupIds = invitedGroupIds;
	}
	/**
	 * 返回 受邀研发组IDs
	 * @return
	 */
	public String getInvitedGroupIds() 
	{
		return this.invitedGroupIds;
	}
	public void setSharedPath(String sharedPath) 
	{
		this.sharedPath = sharedPath;
	}
	/**
	 * 返回 共享文件夹路径
	 * @return
	 */
	public String getSharedPath() 
	{
		return this.sharedPath;
	}
	public void setResourcePath(String resourcePath) 
	{
		this.resourcePath = resourcePath;
	}
	/**
	 * 返回 研发资源路径
	 * @return
	 */
	public String getResourcePath() 
	{
		return this.resourcePath;
	}
	public void setCrowdsourcingRequireDetailList(List<CrowdsourcingRequireDetail> crowdsourcingRequireDetailList) 
	{
		this.crowdsourcingRequireDetailList = crowdsourcingRequireDetailList;
	}
	/**
	 * 返回 cloud_crowdsourcing_require_detail列表
	 * @return
	 */
	public List<CrowdsourcingRequireDetail> getCrowdsourcingRequireDetailList() 
	{
		return this.crowdsourcingRequireDetailList;
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
		if (!(object instanceof CrowdsourcingRequire)) 
		{
			return false;
		}
		CrowdsourcingRequire rhs = (CrowdsourcingRequire) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.code, rhs.code)
		.append(this.requireName, rhs.requireName)
		.append(this.requireType, rhs.requireType)
		.append(this.requireDescription, rhs.requireDescription)
		.append(this.status, rhs.status)
		.append(this.publishMode, rhs.publishMode)
		.append(this.operateDate, rhs.operateDate)
		.append(this.operaterId, rhs.operaterId)
		.append(this.operaterName, rhs.operaterName)
		.append(this.enterpriseId, rhs.enterpriseId)
		.append(this.enterpriseName, rhs.enterpriseName)
		.append(this.runId, rhs.runId)
		.append(this.bail, rhs.bail)
		.append(this.currency, rhs.currency)
		.append(this.finalists, rhs.finalists)
		.append(this.announcementDeadline, rhs.announcementDeadline)
		.append(this.requiredCompleteTime, rhs.requiredCompleteTime)
		.append(this.attachmentDetail, rhs.attachmentDetail)
		.append(this.materialId, rhs.materialId)
		.append(this.materialCode, rhs.materialCode)
		.append(this.materialName, rhs.materialName)
		.append(this.materialType, rhs.materialType)
		.append(this.materialBomLevel, rhs.materialBomLevel)
		.append(this.invitedOrgId, rhs.invitedOrgId)
		.append(this.invitedOrgName, rhs.invitedOrgName)
		.append(this.invitedUserIds, rhs.invitedUserIds)
		.append(this.invitedUserNames, rhs.invitedUserNames)
		.append(this.invitedGroupIds, rhs.invitedGroupIds)
		.append(this.sharedPath, rhs.sharedPath)
		.append(this.resourcePath, rhs.resourcePath)
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
		.append(this.requireName) 
		.append(this.requireType) 
		.append(this.requireDescription) 
		.append(this.status) 
		.append(this.publishMode) 
		.append(this.operateDate) 
		.append(this.operaterId) 
		.append(this.operaterName) 
		.append(this.enterpriseId) 
		.append(this.enterpriseName) 
		.append(this.runId) 
		.append(this.bail) 
		.append(this.currency) 
		.append(this.finalists) 
		.append(this.announcementDeadline) 
		.append(this.requiredCompleteTime) 
		.append(this.attachmentDetail) 
		.append(this.materialId) 
		.append(this.materialCode) 
		.append(this.materialName) 
		.append(this.materialType) 
		.append(this.materialBomLevel) 
		.append(this.invitedOrgId) 
		.append(this.invitedOrgName) 
		.append(this.invitedUserIds) 
		.append(this.invitedUserNames) 
		.append(this.invitedGroupIds) 
		.append(this.sharedPath) 
		.append(this.resourcePath) 
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
		.append("requireName", this.requireName) 
		.append("requireType", this.requireType) 
		.append("requireDescription", this.requireDescription) 
		.append("status", this.status) 
		.append("publishMode", this.publishMode) 
		.append("operateDate", this.operateDate) 
		.append("operaterId", this.operaterId) 
		.append("operaterName", this.operaterName) 
		.append("enterpriseId", this.enterpriseId) 
		.append("enterpriseName", this.enterpriseName) 
		.append("runId", this.runId) 
		.append("bail", this.bail) 
		.append("currency", this.currency) 
		.append("finalists", this.finalists) 
		.append("announcementDeadline", this.announcementDeadline) 
		.append("requiredCompleteTime", this.requiredCompleteTime) 
		.append("attachmentDetail", this.attachmentDetail) 
		.append("materialId", this.materialId) 
		.append("materialCode", this.materialCode) 
		.append("materialName", this.materialName) 
		.append("materialType", this.materialType) 
		.append("materialBomLevel", this.materialBomLevel) 
		.append("invitedOrgId", this.invitedOrgId) 
		.append("invitedOrgName", this.invitedOrgName) 
		.append("invitedUserIds", this.invitedUserIds) 
		.append("invitedUserNames", this.invitedUserNames) 
		.append("invitedGroupIds", this.invitedGroupIds) 
		.append("sharedPath", this.sharedPath) 
		.append("resourcePath", this.resourcePath) 
		.toString();
	}
   
  

}