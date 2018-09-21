package com.casic.cloud.model.config.materialProperty;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:CLOUD_MATERIAL_PROPERTY Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 15:17:54
 */
public class MaterialProperty extends BaseModel
{
	// id
	protected Long  id;
	// class_id
	protected Long  classId;
	// property_sequence
	protected String  propertySequence;
	// property_name
	protected String  propertyName;
	// property_type
	protected String  propertyType;
	// value
	protected String  value;
	// class_name
	protected String  className;
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
	public void setClassId(Long classId) 
	{
		this.classId = classId;
	}
	/**
	 * 返回 class_id
	 * @return
	 */
	public Long getClassId() 
	{
		return this.classId;
	}
	public void setPropertySequence(String propertySequence) 
	{
		this.propertySequence = propertySequence;
	}
	/**
	 * 返回 property_sequence
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
	 * 返回 property_name
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
	 * 返回 property_type
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
	 * 返回 value
	 * @return
	 */
	public String getValue() 
	{
		return this.value;
	}
	public void setClassName(String className) 
	{
		this.className = className;
	}
	/**
	 * 返回 class_name
	 * @return
	 */
	public String getClassName() 
	{
		return this.className;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof MaterialProperty)) 
		{
			return false;
		}
		MaterialProperty rhs = (MaterialProperty) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.classId, rhs.classId)
		.append(this.propertySequence, rhs.propertySequence)
		.append(this.propertyName, rhs.propertyName)
		.append(this.propertyType, rhs.propertyType)
		.append(this.value, rhs.value)
		.append(this.className, rhs.className)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.classId) 
		.append(this.propertySequence) 
		.append(this.propertyName) 
		.append(this.propertyType) 
		.append(this.value) 
		.append(this.className) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("classId", this.classId) 
		.append("propertySequence", this.propertySequence) 
		.append("propertyName", this.propertyName) 
		.append("propertyType", this.propertyType) 
		.append("value", this.value) 
		.append("className", this.className) 
		.toString();
	}
   
  

}