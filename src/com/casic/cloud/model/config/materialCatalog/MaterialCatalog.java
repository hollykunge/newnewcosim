package com.casic.cloud.model.config.materialCatalog;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_material_catalog Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-17 11:41:35
 */
public class MaterialCatalog extends BaseModel
{	public static final long ROOT_PID = -1L;
public static final long ROOT_ID = 0L;
	// id
	protected Long  id;
	// 编码
	protected String  code;
	// 名称
	protected String  name;
	// 述描
	protected String  descn;
	// 父ID
	protected Long  parentId;
	// 亲级名称
	protected String  parentName;
	// 大类ID
	protected Long  typeId;
	// 类大名称
	protected String  typeName;
	// 所属层级
	protected Long  typeLevel;
	 
	// ent_id
	protected Long  entId;
	// ent_name
	protected String  entName;
	protected Integer isLeaf;
		
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
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
	 
	public void setCode(String code) 
	{
		this.code = code;
	}
	/**
	 * 返回 编码
	 * @return
	 */
	public String getCode() 
	{
		return this.code;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setDescn(String descn) 
	{
		this.descn = descn;
	}
	/**
	 * 返回 述描
	 * @return
	 */
	public String getDescn() 
	{
		return this.descn;
	}
	public void setParentId(Long parentId) 
	{
		this.parentId = parentId;
	}
	/**
	 * 返回 父ID
	 * @return
	 */
	public Long getParentId() 
	{
		return this.parentId;
	}
	public void setParentName(String parentName) 
	{
		this.parentName = parentName;
	}
	/**
	 * 返回 亲级名称
	 * @return
	 */
	public String getParentName() 
	{
		return this.parentName;
	}
	public void setTypeId(Long typeId) 
	{
		this.typeId = typeId;
	}
	/**
	 * 返回 大类ID
	 * @return
	 */
	public Long getTypeId() 
	{
		return this.typeId;
	}
	public void setTypeName(String typeName) 
	{
		this.typeName = typeName;
	}
	/**
	 * 返回 类大名称
	 * @return
	 */
	public String getTypeName() 
	{
		return this.typeName;
	}
	public void setTypeLevel(Long typeLevel) 
	{
		this.typeLevel = typeLevel;
	}
	/**
	 * 返回 所属层级
	 * @return
	 */
	public Long getTypeLevel() 
	{
		return this.typeLevel;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof MaterialCatalog)) 
		{
			return false;
		}
		MaterialCatalog rhs = (MaterialCatalog) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.code, rhs.code)
		.append(this.name, rhs.name)
		.append(this.descn, rhs.descn)
		.append(this.parentId, rhs.parentId)
		.append(this.parentName, rhs.parentName)
		.append(this.typeId, rhs.typeId)
		.append(this.typeName, rhs.typeName)
		.append(this.typeLevel, rhs.typeLevel)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.code) 
		.append(this.name) 
		.append(this.descn) 
		.append(this.parentId) 
		.append(this.parentName) 
		.append(this.typeId) 
		.append(this.typeName) 
		.append(this.typeLevel) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("code", this.code) 
		.append("name", this.name) 
		.append("descn", this.descn) 
		.append("parentId", this.parentId) 
		.append("parentName", this.parentName) 
		.append("typeId", this.typeId) 
		.append("typeName", this.typeName) 
		.append("typeLevel", this.typeLevel) 
		.toString();
	}
	public Long getEntId() {
		return entId;
	}
	public void setEntId(Long entId) {
		this.entId = entId;
	}
	public String getEntName() {
		return entName;
	}
	public void setEntName(String entName) {
		this.entName = entName;
	}
   
  

}