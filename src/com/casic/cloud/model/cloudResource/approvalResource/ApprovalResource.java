package com.casic.cloud.model.cloudResource.approvalResource;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_resource_approval Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-27 10:27:37
 */
public class ApprovalResource extends BaseModel
{
	// id
	protected Long  id;
	// 企业ID
	protected Long  enpId;
	// 人员ID
	protected Long  userId;
	// 资源ID
	protected Long  resId;
	// 资源名
	protected String  resName;
	// 申请日期
	protected java.util.Date  applyDate;
	// 审批日期
	protected java.util.Date  approvalData;
	// 状态
	protected int  state;
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 id
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setEnpId(Long enpId) 
	{
		this.enpId = enpId;
	}
	/**
	 * 返回 企业ID
	 * @return
	 */
	public Long getEnpId() 
	{
		return this.enpId;
	}
	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}
	/**
	 * 返回 人员ID
	 * @return
	 */
	public Long getUserId() 
	{
		return this.userId;
	}
	public void setResId(Long resId) 
	{
		this.resId = resId;
	}
	/**
	 * 返回 资源ID
	 * @return
	 */
	public Long getResId() 
	{
		return this.resId;
	}
	public void setResName(String resName) 
	{
		this.resName = resName;
	}
	/**
	 * 返回 资源名
	 * @return
	 */
	public String getResName() 
	{
		return this.resName;
	}
	public void setApplyDate(java.util.Date applyDate) 
	{
		this.applyDate = applyDate;
	}
	/**
	 * 返回 申请日期
	 * @return
	 */
	public java.util.Date getApplyDate() 
	{
		return this.applyDate;
	}
	public void setApprovalData(java.util.Date approvalData) 
	{
		this.approvalData = approvalData;
	}
	/**
	 * 返回 审批日期
	 * @return
	 */
	public java.util.Date getApprovalData() 
	{
		return this.approvalData;
	}
	public void setState(int state) 
	{
		this.state = state;
	}
	/**
	 * 返回 状态
	 * @return
	 */
	public int getState() 
	{
		return this.state;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ApprovalResource)) 
		{
			return false;
		}
		ApprovalResource rhs = (ApprovalResource) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.enpId, rhs.enpId)
		.append(this.userId, rhs.userId)
		.append(this.resId, rhs.resId)
		.append(this.resName, rhs.resName)
		.append(this.applyDate, rhs.applyDate)
		.append(this.approvalData, rhs.approvalData)
		.append(this.state, rhs.state)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.enpId) 
		.append(this.userId) 
		.append(this.resId) 
		.append(this.resName) 
		.append(this.applyDate) 
		.append(this.approvalData) 
		.append(this.state) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("enpId", this.enpId) 
		.append("userId", this.userId) 
		.append("resId", this.resId) 
		.append("resName", this.resName) 
		.append("applyDate", this.applyDate) 
		.append("approvalData", this.approvalData) 
		.append("state", this.state) 
		.toString();
	}
   
  

}