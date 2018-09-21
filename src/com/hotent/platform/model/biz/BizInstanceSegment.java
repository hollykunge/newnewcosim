package com.hotent.platform.model.biz;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:业务环节实例 Model对象
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 */
public class BizInstanceSegment extends BaseModel
{
	public static final Short STATUS_RUNNING		=1;
	public static final Short STATUS_STOP			=2;
	public static final Short STATUS_EXCEPTION		=-1;
	// 业务环节实例ID
	protected Long  bizInstanceSegmentId;
	// 业务实例ID
	protected Long  bizInstanceId;
	// 业务环节ID
	protected Long  bizDefSegmentId;
	// 流程实例ID
	protected Long  actInstId;
	// 状态:
	protected Short  status;
	// 序号
	protected Long  sortOrder;
	public void setBizInstanceSegmentId(Long bizInstanceSegmentId) 
	{
		this.bizInstanceSegmentId = bizInstanceSegmentId;
	}
	/**
	 * 返回 业务环节实例ID
	 * @return
	 */
	public Long getBizInstanceSegmentId() 
	{
		return this.bizInstanceSegmentId;
	}
	public void setBizInstanceId(Long bizInstanceId) 
	{
		this.bizInstanceId = bizInstanceId;
	}
	/**
	 * 返回 业务实例ID
	 * @return
	 */
	public Long getBizInstanceId() 
	{
		return this.bizInstanceId;
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
	public void setActInstId(Long actInstId) 
	{
		this.actInstId = actInstId;
	}
	/**
	 * 返回 流程实例ID
	 * @return
	 */
	public Long getActInstId() 
	{
		return this.actInstId;
	}
	public void setStatus(Short status) 
	{
		this.status = status;
	}
	/**
	 * 返回 状态:
	 * @return
	 */
	public Short getStatus() 
	{
		return this.status;
	}
	public void setSortOrder(Long sortOrder) 
	{
		this.sortOrder = sortOrder;
	}
	/**
	 * 返回 序号
	 * @return
	 */
	public Long getSortOrder() 
	{
		return this.sortOrder;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BizInstanceSegment)) 
		{
			return false;
		}
		BizInstanceSegment rhs = (BizInstanceSegment) object;
		return new EqualsBuilder()
		.append(this.bizInstanceSegmentId, rhs.bizInstanceSegmentId)
		.append(this.bizInstanceId, rhs.bizInstanceId)
		.append(this.bizDefSegmentId, rhs.bizDefSegmentId)
		.append(this.actInstId, rhs.actInstId)
		.append(this.status, rhs.status)
		.append(this.sortOrder, rhs.sortOrder)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.bizInstanceSegmentId) 
		.append(this.bizInstanceId) 
		.append(this.bizDefSegmentId) 
		.append(this.actInstId) 
		.append(this.status) 
		.append(this.sortOrder) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("bizInstanceSegmentId", this.bizInstanceSegmentId) 
		.append("bizInstanceId", this.bizInstanceId) 
		.append("bizDefSegmentId", this.bizDefSegmentId) 
		.append("actInstId", this.actInstId) 
		.append("status", this.status) 
		.append("sortOrder", this.sortOrder) 
		.toString();
	}
   
  

}