package com.casic.cloud.model.config.materialPropertyValue;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_material_property_value Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-18 17:49:50
 */
public class MaterialPropertyValue extends BaseModel
{
	// id
	protected Long  id;
	// material_id
	protected Long  materialId;
	// property_id
	protected Long  propertyId;
	// property_value
	protected String  propertyValue;
	// property_name
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
	public void setMaterialId(Long materialId) 
	{
		this.materialId = materialId;
	}
	/**
	 * 返回 material_id
	 * @return
	 */
	public Long getMaterialId() 
	{
		return this.materialId;
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
	 * 返回 property_value
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
	 * 返回 property_name
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
		if (!(object instanceof MaterialPropertyValue)) 
		{
			return false;
		}
		MaterialPropertyValue rhs = (MaterialPropertyValue) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.materialId, rhs.materialId)
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
		.append(this.materialId) 
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
		.append("materialId", this.materialId) 
		.append("propertyId", this.propertyId) 
		.append("propertyValue", this.propertyValue) 
		.append("propertyName", this.propertyName) 
		.toString();
	}
   
  

}