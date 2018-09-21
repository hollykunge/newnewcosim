package com.casic.cloud.model.config.capabilityClass;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_capability_class Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:17:15
 */
public class CapabilityClass extends BaseModel
{
	// id
	protected Long  id;
	// 能力分类名称
	protected String  name;
	// 父级ID
	protected Long  parentId;
	// 层级
	protected Long  levels;
	// 父级
	protected String  parentName;
	//是否被用
		protected String  isused;
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
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 能力分类名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setParentId(Long parentId) 
	{
		this.parentId = parentId;
	}
	/**
	 * 返回 父级ID
	 * @return
	 */
	public Long getParentId() 
	{
		return this.parentId;
	}
	public void setLevels(Long levels) 
	{
		this.levels = levels;
	}
	/**
	 * 返回 层级
	 * @return
	 */
	public Long getLevels() 
	{
		return this.levels;
	}
	public void setParentName(String parentName) 
	{
		this.parentName = parentName;
	}
	/**
	 * 返回 父级
	 * @return
	 */
	public String getParentName() 
	{
		return this.parentName;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CapabilityClass)) 
		{
			return false;
		}
		CapabilityClass rhs = (CapabilityClass) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.parentId, rhs.parentId)
		.append(this.levels, rhs.levels)
		.append(this.parentName, rhs.parentName)
		.append(this.isused, rhs.isused)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.parentId) 
		.append(this.levels) 
		.append(this.parentName) 
			.append( this.isused) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("parentId", this.parentId) 
		.append("levels", this.levels) 
		.append("parentName", this.parentName) 
		.append("isused", this.isused) 
		.toString();
	}
	public String getIsused() {
		return isused;
	}
	public void setIsused(String isused) {
		this.isused = isused;
	}
 
   
  

}