package com.casic.cloud.model.config.capabilityPropertyValue;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_capability_property_value Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 19:23:27
 */
public class CapabilityPropertyValue extends BaseModel
{
	// id
	protected Long  id;
	// capability_id
	protected Long  capabilityId;
	// property_id
	protected Long  propertyId;
	// 属性值
	protected String  propertyValue;
	// 属性名称
	protected String  propertyName;
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
	public void setCapabilityId(Long capabilityId) 
	{
		this.capabilityId = capabilityId;
	}
	/**
	 * 返回 capability_id
	 * @return
	 */
	public Long getCapabilityId() 
	{
		return this.capabilityId;
	}
	public void setPropertyId(Long propertyId) 
	{
		this.propertyId = propertyId;
	}
	/**
	 * 返回 property_id
	 * @return
	 */
	public Long getPropertyId() 
	{
		return this.propertyId;
	}
	public void setPropertyValue(String propertyValue) 
	{
		this.propertyValue = propertyValue;
	}
	/**
	 * 返回 属性值
	 * @return
	 */
	public String getPropertyValue() 
	{
		return this.propertyValue;
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


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CapabilityPropertyValue)) 
		{
			return false;
		}
		CapabilityPropertyValue rhs = (CapabilityPropertyValue) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.capabilityId, rhs.capabilityId)
		.append(this.propertyId, rhs.propertyId)
		.append(this.propertyValue, rhs.propertyValue)
		.append(this.propertyName, rhs.propertyName)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.capabilityId) 
		.append(this.propertyId) 
		.append(this.propertyValue) 
		.append(this.propertyName) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("capabilityId", this.capabilityId) 
		.append("propertyId", this.propertyId) 
		.append("propertyValue", this.propertyValue) 
		.append("propertyName", this.propertyName) 
		.toString();
	}
   
  

}