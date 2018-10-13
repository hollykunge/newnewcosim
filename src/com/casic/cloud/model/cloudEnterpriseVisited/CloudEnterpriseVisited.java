package com.casic.cloud.model.cloudEnterpriseVisited;

import java.util.ArrayList;
import java.util.List;

import com.casic.cloud.model.config.info.Info;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_enterprise_visited Model对象
 * 开发公司:tianzhi
 * 开发人员:hollykunge
 * 创建时间:2013-05-03 10:34:39
 */
public class CloudEnterpriseVisited extends BaseModel
{
	// id
	protected Long  id;
	/**  访问企业ID  **/
	protected Long  visitentId;
	/**  被访问企业ID  **/
	protected Long  interventId;
	/**  访问时间  **/
	protected java.util.Date  visitdate;
	//外键
	protected Info visitEnterprise;
	public Info getVisitEnterprise() {
		return visitEnterprise;
	}
	public void setVisitEnterprise(Info visitEnterprise) {
		this.visitEnterprise = visitEnterprise;
	}
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
	public void setVisitentId(Long visitentId) 
	{
		this.visitentId = visitentId;
	}
	/**
	 * 返回 visitent_id
	 * @return
	 */
	public Long getVisitentId() 
	{
		return this.visitentId;
	}
	public void setInterventId(Long interventId) 
	{
		this.interventId = interventId;
	}
	/**
	 * 返回 intervent_id
	 * @return
	 */
	public Long getInterventId() 
	{
		return this.interventId;
	}
	public void setVisitdate(java.util.Date visitdate) 
	{
		this.visitdate = visitdate;
	}
	/**
	 * 返回 visitdate
	 * @return
	 */
	public java.util.Date getVisitdate() 
	{
		return this.visitdate;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CloudEnterpriseVisited)) 
		{
			return false;
		}
		CloudEnterpriseVisited rhs = (CloudEnterpriseVisited) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.visitentId, rhs.visitentId)
		.append(this.interventId, rhs.interventId)
		.append(this.visitdate, rhs.visitdate)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.visitentId) 
		.append(this.interventId) 
		.append(this.visitdate) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("visitentId", this.visitentId) 
		.append("interventId", this.interventId) 
		.append("visitdate", this.visitdate) 
		.toString();
	}
   
  

}