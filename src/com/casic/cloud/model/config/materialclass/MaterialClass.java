package com.casic.cloud.model.config.materialclass;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:CLOUD_MATERIAL_CLASS Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 10:56:35
 */
public class MaterialClass extends BaseModel
{
	// id
	protected Long  id;
	// name
	protected String  name;
	// parentId
	protected Long  parentid;
	// levels
	protected Long  levels;
	// parentName
	protected String  parentname;
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
	 * 返回 name
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setParentid(Long parentid) 
	{
		this.parentid = parentid;
	}
	/**
	 * 返回 parentId
	 * @return
	 */
	public Long getParentid() 
	{
		return this.parentid;
	}
	public void setLevels(Long levels) 
	{
		this.levels = levels;
	}
	/**
	 * 返回 levels
	 * @return
	 */
	public Long getLevels() 
	{
		return this.levels;
	}
	public void setParentname(String parentname) 
	{
		this.parentname = parentname;
	}
	/**
	 * 返回 parentName
	 * @return
	 */
	public String getParentname() 
	{
		return this.parentname;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof MaterialClass)) 
		{
			return false;
		}
		MaterialClass rhs = (MaterialClass) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.parentid, rhs.parentid)
		.append(this.levels, rhs.levels)
		.append(this.parentname, rhs.parentname)
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
		.append(this.parentid) 
		.append(this.levels) 
		.append(this.parentname) 
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
		.append("parentid", this.parentid) 
		.append("levels", this.levels) 
		.append("parentname", this.parentname) 
		.toString();
	}
   
  

}