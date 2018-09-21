package com.hotent.platform.model.biz;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:业务环节 Model对象
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 */
public class BizDefSegment extends BaseModel
{
	// 业务环节ID
	protected Long  bizDefSegmentId;
	// 业务定义id
	protected Long  bizDefId;
	// 业务环节编号
	protected String  bizDefSegmentNo;
	// 业务环节名称
	protected String  segmentName;
	// 描述
	protected String  segmentDescription;
	// 默认流程定义Key
	protected String  actDefKey;
	// 状态
	protected Short  status;
	// 序号
	protected Long  sortOrder;
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
	public void setBizDefId(Long bizDefId) 
	{
		this.bizDefId = bizDefId;
	}
	/**
	 * 返回 业务定义id
	 * @return
	 */
	public Long getBizDefId() 
	{
		return this.bizDefId;
	}
	public void setBizDefSegmentNo(String bizDefSegmentNo) 
	{
		this.bizDefSegmentNo = bizDefSegmentNo;
	}
	/**
	 * 返回 业务环节编号
	 * @return
	 */
	public String getBizDefSegmentNo() 
	{
		return this.bizDefSegmentNo;
	}
	public void setSegmentName(String segmentName) 
	{
		this.segmentName = segmentName;
	}
	/**
	 * 返回 业务环节名称
	 * @return
	 */
	public String getSegmentName() 
	{
		return this.segmentName;
	}
	public void setSegmentDescription(String segmentDescription) 
	{
		this.segmentDescription = segmentDescription;
	}
	/**
	 * 返回 描述
	 * @return
	 */
	public String getSegmentDescription() 
	{
		return this.segmentDescription;
	}

	public void setActDefKey(String actDefKey) 
	{
		this.actDefKey = actDefKey;
	}
	/**
	 * 返回 默认流程定义Key
	 * @return
	 */
	public String getActDefKey() 
	{
		return this.actDefKey;
	}
	public void setStatus(Short status) 
	{
		this.status = status;
	}
	/**
	 * 返回 状态
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
		if (!(object instanceof BizDefSegment)) 
		{
			return false;
		}
		BizDefSegment rhs = (BizDefSegment) object;
		return new EqualsBuilder()
		.append(this.bizDefSegmentId, rhs.bizDefSegmentId)
		.append(this.bizDefId, rhs.bizDefId)
		.append(this.bizDefSegmentNo, rhs.bizDefSegmentNo)
		.append(this.segmentName, rhs.segmentName)
		.append(this.segmentDescription, rhs.segmentDescription)
		.append(this.actDefKey, rhs.actDefKey)
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
		.append(this.bizDefSegmentId) 
		.append(this.bizDefId) 
		.append(this.bizDefSegmentNo) 
		.append(this.segmentName) 
		.append(this.segmentDescription) 
		.append(this.actDefKey) 
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
		.append("bizDefSegmentId", this.bizDefSegmentId) 
		.append("bizDefId", this.bizDefId) 
		.append("bizDefSegmentNo", this.bizDefSegmentNo) 
		.append("segmentName", this.segmentName) 
		.append("segmentDescription", this.segmentDescription) 
		.append("actDefKey", this.actDefKey) 
		.append("status", this.status) 
		.append("sortOrder", this.sortOrder) 
		.toString();
	}
   
  

}