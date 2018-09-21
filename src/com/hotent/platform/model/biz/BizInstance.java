package com.hotent.platform.model.biz;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:业务实例 Model对象
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 */
public class BizInstance extends BaseModel
{
	public static final Short STATUS_RUNNING		=1;
	public static final Short STATUS_STOP			=2;
	public static final Short STATUS_EXCEPTION		=-1;
	// 业务实例Id
	protected Long  bizInstanceId;
	// 业务定义Id
	protected Long  bizDefId;
	// 状态:
	protected Short  status;
	// 开始时间
	protected java.util.Date  startTime;
	// 结束时间
	protected java.util.Date  endTime;
	// 创建人
	protected Long  createBy;
	
	//流程实例环节
	protected List<BizInstanceSegment> bizInstanceSegmentList = new ArrayList<BizInstanceSegment>();
	
	public void setBizInstanceId(Long bizInstanceId) 
	{
		this.bizInstanceId = bizInstanceId;
	}
	/**
	 * 返回 业务实例Id
	 * @return
	 */
	public Long getBizInstanceId() 
	{
		return this.bizInstanceId;
	}
	public void setBizDefId(Long bizDefId) 
	{
		this.bizDefId = bizDefId;
	}
	/**
	 * 返回 业务定义Id
	 * @return
	 */
	public Long getBizDefId() 
	{
		return this.bizDefId;
	}
	public void setStatus(Short status) 
	{
		this.status = status;
	}
	public List<BizInstanceSegment> getBizInstanceSegmentList() {
		return bizInstanceSegmentList;
	}
	public void setBizInstanceSegmentList(List<BizInstanceSegment> bizInstanceSegmentList) {
		this.bizInstanceSegmentList = bizInstanceSegmentList;
	}
	/**
	 * 返回 状态:
	 * @return
	 */
	public Short getStatus() 
	{
		return this.status;
	}
	public void setStartTime(java.util.Date startTime) 
	{
		this.startTime = startTime;
	}
	/**
	 * 返回 开始时间
	 * @return
	 */
	public java.util.Date getStartTime() 
	{
		return this.startTime;
	}
	public void setEndTime(java.util.Date endTime) 
	{
		this.endTime = endTime;
	}
	/**
	 * 返回 结束时间
	 * @return
	 */
	public java.util.Date getEndTime() 
	{
		return this.endTime;
	}
	public void setCreateBy(Long createBy) 
	{
		this.createBy = createBy;
	}
	/**
	 * 返回 创建人
	 * @return
	 */
	public Long getCreateBy() 
	{
		return this.createBy;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BizInstance)) 
		{
			return false;
		}
		BizInstance rhs = (BizInstance) object;
		return new EqualsBuilder()
		.append(this.bizInstanceId, rhs.bizInstanceId)
		.append(this.bizDefId, rhs.bizDefId)
		.append(this.status, rhs.status)
		.append(this.startTime, rhs.startTime)
		.append(this.endTime, rhs.endTime)
		.append(this.createBy, rhs.createBy)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.bizInstanceId) 
		.append(this.bizDefId) 
		.append(this.status) 
		.append(this.startTime) 
		.append(this.endTime) 
		.append(this.createBy) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("bizInstanceId", this.bizInstanceId) 
		.append("bizDefId", this.bizDefId) 
		.append("status", this.status) 
		.append("startTime", this.startTime) 
		.append("endTime", this.endTime) 
		.append("createBy", this.createBy) 
		.toString();
	}
   
  

}