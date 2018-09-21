package com.casic.cloud.model.console.businessAreaGroup;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_business_area_group Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-05 13:24:43
 */
public class BusinessAreaGroup extends BaseModel
{
	// 商圈组
	protected Long  id;
	// 组名称
	protected String  groupname;
	// 企业ID
	protected Long  entid;
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 商圈组
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setGroupname(String groupname) 
	{
		this.groupname = groupname;
	}
	/**
	 * 返回 组名称
	 * @return
	 */
	public String getGroupname() 
	{
		return this.groupname;
	}
	public void setEntid(Long entid) 
	{
		this.entid = entid;
	}
	/**
	 * 返回 企业ID
	 * @return
	 */
	public Long getEntid() 
	{
		return this.entid;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BusinessAreaGroup)) 
		{
			return false;
		}
		BusinessAreaGroup rhs = (BusinessAreaGroup) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.groupname, rhs.groupname)
		.append(this.entid, rhs.entid)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.groupname) 
		.append(this.entid) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("groupname", this.groupname) 
		.append("entid", this.entid) 
		.toString();
	}
   
  

}