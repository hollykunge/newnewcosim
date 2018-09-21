package com.hotent.platform.model.biz;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:BIZ_SEG_PROC_LINK Model对象
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 */
public class BizSegProcLink extends BaseModel
{
	// 关联ID
	protected Long  bizSegProLinkId;
	// 业务环节ID
	protected Long  bizDefSegmentId;
	// 企业ID（即组织）
	protected Long  orgId;
	// 流程定义Key
	protected String  actDefKey;
	public void setBizSegProLinkId(Long bizSegProLinkId) 
	{
		this.bizSegProLinkId = bizSegProLinkId;
	}
	/**
	 * 返回 关联ID
	 * @return
	 */
	public Long getBizSegProLinkId() 
	{
		return this.bizSegProLinkId;
	}
	public void setBizDefSegmentId(Long bizDefSegmentId) 
	{
		this.bizDefSegmentId = bizDefSegmentId;
	}
	/**
	 * 返回 业务环节ID
	 * @return
	 */
	public Long getBizDefSegmentId() 
	{
		return this.bizDefSegmentId;
	}
	public void setOrgId(Long orgId) 
	{
		this.orgId = orgId;
	}
	/**
	 * 返回 企业ID（即组织）
	 * @return
	 */
	public Long getOrgId() 
	{
		return this.orgId;
	}
	public void setActDefKey(String actDefKey) 
	{
		this.actDefKey = actDefKey;
	}
	/**
	 * 返回 流程定义Key
	 * @return
	 */
	public String getActDefKey() 
	{
		return this.actDefKey;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BizSegProcLink)) 
		{
			return false;
		}
		BizSegProcLink rhs = (BizSegProcLink) object;
		return new EqualsBuilder()
		.append(this.bizSegProLinkId, rhs.bizSegProLinkId)
		.append(this.bizDefSegmentId, rhs.bizDefSegmentId)
		.append(this.orgId, rhs.orgId)
		.append(this.actDefKey, rhs.actDefKey)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.bizSegProLinkId) 
		.append(this.bizDefSegmentId) 
		.append(this.orgId) 
		.append(this.actDefKey) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("bizSegProLinkId", this.bizSegProLinkId) 
		.append("bizDefSegmentId", this.bizDefSegmentId) 
		.append("orgId", this.orgId) 
		.append("actDefKey", this.actDefKey) 
		.toString();
	}
   
  

}