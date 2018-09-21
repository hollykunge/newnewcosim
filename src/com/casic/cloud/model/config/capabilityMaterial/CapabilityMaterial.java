package com.casic.cloud.model.config.capabilityMaterial;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_capability_material Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 18:13:05
 */
public class CapabilityMaterial extends BaseModel
{
	// capability_id
	protected Long  capabilityId;
	// material_id
	protected Long  materialId;
	// id
	protected Long  id;
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


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CapabilityMaterial)) 
		{
			return false;
		}
		CapabilityMaterial rhs = (CapabilityMaterial) object;
		return new EqualsBuilder()
		.append(this.capabilityId, rhs.capabilityId)
		.append(this.materialId, rhs.materialId)
		.append(this.id, rhs.id)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.capabilityId) 
		.append(this.materialId) 
		.append(this.id) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("capabilityId", this.capabilityId) 
		.append("materialId", this.materialId) 
		.append("id", this.id) 
		.toString();
	}
   
  

}