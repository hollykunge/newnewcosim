package com.casic.cloud.model.crowdsourcing.crowdsourcingAgreement;

import java.util.ArrayList;
import java.util.List;

import com.casic.cloud.model.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequireDetail;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_crowdsourcing_agreement_detail Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 16:59:12
 */
public class CrowdsourcingAgreementDetail extends BaseModel
{
	// ID
	protected Long  id;
	// 附件名
	protected String  attachmentName;
	// 附件分类
	protected String  attachmentType;
	// 附件说明
	protected String  attachmentInfo;
	// 上传者ID
	protected Long  uploadId;
	// 上传者姓名
	protected String  uploadName;
	// 附件管理ID
	protected Long  attachmentManageId;
	// 操作权限
	protected String  operatingAuthority;
	// 父表ID
	protected Long  agreementId;
	
	
	public Long getAgreementId() {
		return agreementId;
	}


	public void setAgreementId(Long agreementId) {
		this.agreementId = agreementId;
	}


	public CrowdsourcingAgreementDetail(CrowdsourcingRequireDetail crowdsourcingRequireDetail) {
		super();
		this.id = id;
		this.attachmentName = crowdsourcingRequireDetail.getAttachmentName();
		this.attachmentType = crowdsourcingRequireDetail.getAttachmentType();
		this.attachmentInfo = crowdsourcingRequireDetail.getAttachmentInfo();
		this.uploadId = crowdsourcingRequireDetail.getUploadId();
		this.uploadName = crowdsourcingRequireDetail.getUploadName();
		this.attachmentManageId = crowdsourcingRequireDetail.getAttachmentManageId();
		this.operatingAuthority = crowdsourcingRequireDetail.getOperatingAuthority();
	}
	
	
	public CrowdsourcingAgreementDetail() {
		super();
		// TODO Auto-generated constructor stub
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
	public void setAttachmentName(String attachmentName) 
	{
		this.attachmentName = attachmentName;
	}
	/**
	 * 返回 附件名
	 * @return
	 */
	public String getAttachmentName() 
	{
		return this.attachmentName;
	}
	public void setAttachmentType(String attachmentType) 
	{
		this.attachmentType = attachmentType;
	}
	/**
	 * 返回 附件分类
	 * @return
	 */
	public String getAttachmentType() 
	{
		return this.attachmentType;
	}
	public void setAttachmentInfo(String attachmentInfo) 
	{
		this.attachmentInfo = attachmentInfo;
	}
	/**
	 * 返回 附件说明
	 * @return
	 */
	public String getAttachmentInfo() 
	{
		return this.attachmentInfo;
	}
	public void setUploadId(Long uploadId) 
	{
		this.uploadId = uploadId;
	}
	/**
	 * 返回 上传者ID
	 * @return
	 */
	public Long getUploadId() 
	{
		return this.uploadId;
	}
	public void setUploadName(String uploadName) 
	{
		this.uploadName = uploadName;
	}
	/**
	 * 返回 上传者姓名
	 * @return
	 */
	public String getUploadName() 
	{
		return this.uploadName;
	}
	public void setAttachmentManageId(Long attachmentManageId) 
	{
		this.attachmentManageId = attachmentManageId;
	}
	/**
	 * 返回 附件管理ID
	 * @return
	 */
	public Long getAttachmentManageId() 
	{
		return this.attachmentManageId;
	}
	public void setOperatingAuthority(String operatingAuthority) 
	{
		this.operatingAuthority = operatingAuthority;
	}
	/**
	 * 返回 操作权限
	 * @return
	 */
	public String getOperatingAuthority() 
	{
		return this.operatingAuthority;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CrowdsourcingAgreementDetail)) 
		{
			return false;
		}
		CrowdsourcingAgreementDetail rhs = (CrowdsourcingAgreementDetail) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.attachmentName, rhs.attachmentName)
		.append(this.attachmentType, rhs.attachmentType)
		.append(this.attachmentInfo, rhs.attachmentInfo)
		.append(this.uploadId, rhs.uploadId)
		.append(this.uploadName, rhs.uploadName)
		.append(this.attachmentManageId, rhs.attachmentManageId)
		.append(this.operatingAuthority, rhs.operatingAuthority)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.attachmentName) 
		.append(this.attachmentType) 
		.append(this.attachmentInfo) 
		.append(this.uploadId) 
		.append(this.uploadName) 
		.append(this.attachmentManageId) 
		.append(this.operatingAuthority) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("attachmentName", this.attachmentName) 
		.append("attachmentType", this.attachmentType) 
		.append("attachmentInfo", this.attachmentInfo) 
		.append("uploadId", this.uploadId) 
		.append("uploadName", this.uploadName) 
		.append("attachmentManageId", this.attachmentManageId) 
		.append("operatingAuthority", this.operatingAuthority) 
		.toString();
	}
   
  

}