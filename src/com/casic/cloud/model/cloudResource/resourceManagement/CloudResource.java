package com.casic.cloud.model.cloudResource.resourceManagement;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_resource_class Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-13 18:11:59
 */
public class CloudResource extends BaseModel
{
	// id
	protected Long  id;
	// 资源类名称
	protected String  name;
	// 父类ID
	protected Long  parentId;
	// 所属层级
	protected Long  levels;
	// 判断url打开的方式
	protected int openType;
	//设置url的链接地址
	protected String openUrl;
	
	public int getOpenType() {
		return openType;
	}
	public void setOpenType(int openType) {
		this.openType = openType;
	}
	public String getOpenUrl() {
		return openUrl;
	}
	public void setOpenUrl(String openUrl) {
		this.openUrl = openUrl;
	}

	//cloud_resource_instance列表
	protected List<CloudResourceInstance> cloudResourceInstanceList=new ArrayList<CloudResourceInstance>();
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
	 * 返回 资源类名称
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
	 * 返回 父类ID
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
	 * 返回 所属层级
	 * @return
	 */
	public Long getLevels() 
	{
		return this.levels;
	}
	public void setCloudResourceInstanceList(List<CloudResourceInstance> cloudResourceInstanceList) 
	{
		this.cloudResourceInstanceList = cloudResourceInstanceList;
	}
	/**
	 * 返回 cloud_resource_instance列表
	 * @return
	 */
	public List<CloudResourceInstance> getCloudResourceInstanceList() 
	{
		return this.cloudResourceInstanceList;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CloudResource)) 
		{
			return false;
		}
		CloudResource rhs = (CloudResource) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.parentId, rhs.parentId)
		.append(this.levels, rhs.levels)
		.append(this.openType, rhs.openType)
		.append(this.openUrl, rhs.openUrl)
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
		.append(this.openType)
		.append(this.openUrl)
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
		.append("openType", this.openType)
		.append("openUrl", this.openUrl)
		.toString();
	}



}