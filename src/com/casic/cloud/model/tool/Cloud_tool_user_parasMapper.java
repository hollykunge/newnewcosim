package com.casic.cloud.model.tool;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_tool_user_parasMapper Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:maliang
 * 创建时间:2014-03-01 17:11:23
 */
public class Cloud_tool_user_parasMapper extends BaseModel
{
	// id
	protected Long  id;
	// 用户工具id
	protected Long  cloudToolUserId;
	// 参数名称
	protected String  name;
	// 参数表单映射名
	protected String  formMapperName;
	// 参数中文名
	protected String  chineseMapperName;
	//参数对应的类型
	protected String type;
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
	public void setCloudToolUserId(Long cloudToolUserId) 
	{
		this.cloudToolUserId = cloudToolUserId;
	}
	/**
	 * 返回 用户工具id
	 * @return
	 */
	public Long getCloudToolUserId() 
	{
		return this.cloudToolUserId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 参数名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setFormMapperName(String formMapperName) 
	{
		this.formMapperName = formMapperName;
	}
	/**
	 * 返回 参数表单映射名
	 * @return
	 */
	public String getFormMapperName() 
	{
		return this.formMapperName;
	}
	public void setChineseMapperName(String chineseMapperName) 
	{
		this.chineseMapperName = chineseMapperName;
	}
	/**
	 * 返回 参数中文名
	 * @return
	 */
	public String getChineseMapperName() 
	{
		return this.chineseMapperName;
	}


   	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Cloud_tool_user_parasMapper)) 
		{
			return false;
		}
		Cloud_tool_user_parasMapper rhs = (Cloud_tool_user_parasMapper) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.cloudToolUserId, rhs.cloudToolUserId)
		.append(this.name, rhs.name)
		.append(this.formMapperName, rhs.formMapperName)
		.append(this.chineseMapperName, rhs.chineseMapperName)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.cloudToolUserId) 
		.append(this.name) 
		.append(this.formMapperName) 
		.append(this.chineseMapperName) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("cloudToolUserId", this.cloudToolUserId) 
		.append("name", this.name) 
		.append("formMapperName", this.formMapperName) 
		.append("chineseMapperName", this.chineseMapperName) 
		.toString();
	}
   
  

}