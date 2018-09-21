package com.casic.cloud.model.config.capabilityProperty;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_capability_property Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 16:10:47
 */
public class CapabilityProperty extends BaseModel
{
	// id
	protected Long  id;
	// 能力分类ID
	protected Long  capabilityClassId;
	// 序号
	protected String  propertySequence;
	// 属性名称
	protected String  propertyName;
	// 属性类别
	protected String  propertyType;
	// 属性值
	protected String  value;
	// 能力分类
	protected String  capabilityClassName;
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
	public void setCapabilityClassId(Long capabilityClassId) 
	{
		this.capabilityClassId = capabilityClassId;
	}
	/**
	 * 返回 能力分类ID
	 * @return
	 */
	public Long getCapabilityClassId() 
	{
		return this.capabilityClassId;
	}
	public void setPropertySequence(String propertySequence) 
	{
		this.propertySequence = propertySequence;
	}
	/**
	 * 返回 序号
	 * @return
	 */
	public String getPropertySequence() 
	{
		return this.propertySequence;
	}
	public void setPropertyName(String propertyName) 
	{
		this.propertyName = propertyName;
	}
	/**
	 * 返回 属性名称
	 * @return
	 */
	public String getPropertyName() 
	{
		return this.propertyName;
	}
	public void setPropertyType(String propertyType) 
	{
		this.propertyType = propertyType;
	}
	/**
	 * 返回 属性类别
	 * @return
	 */
	public String getPropertyType() 
	{
		return this.propertyType;
	}
	public void setValue(String value) 
	{
		this.value = value;
	}
	/**
	 * 返回 属性值
	 * @return
	 */
	public String getValue() 
	{
		return this.value;
	}
	public void setCapabilityClassName(String capabilityClassName) 
	{
		this.capabilityClassName = capabilityClassName;
	}
	/**
	 * 返回 能力分类
	 * @return
	 */
	public String getCapabilityClassName() 
	{
		return this.capabilityClassName;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CapabilityProperty)) 
		{
			return false;
		}
		CapabilityProperty rhs = (CapabilityProperty) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.capabilityClassId, rhs.capabilityClassId)
		.append(this.propertySequence, rhs.propertySequence)
		.append(this.propertyName, rhs.propertyName)
		.append(this.propertyType, rhs.propertyType)
		.append(this.value, rhs.value)
		.append(this.capabilityClassName, rhs.capabilityClassName)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.capabilityClassId) 
		.append(this.propertySequence) 
		.append(this.propertyName) 
		.append(this.propertyType) 
		.append(this.value) 
		.append(this.capabilityClassName) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("capabilityClassId", this.capabilityClassId) 
		.append("propertySequence", this.propertySequence) 
		.append("propertyName", this.propertyName) 
		.append("propertyType", this.propertyType) 
		.append("value", this.value) 
		.append("capabilityClassName", this.capabilityClassName) 
		.toString();
	}
   
  

}