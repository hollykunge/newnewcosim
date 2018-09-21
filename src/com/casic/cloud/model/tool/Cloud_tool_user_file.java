package com.casic.cloud.model.tool;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_tool_user_file Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:maliang
 * 创建时间:2014-03-03 09:47:37
 */
public class Cloud_tool_user_file extends BaseModel
{
	// id
	protected Long  id;
	// cloud_tool_user_id
	protected Long  cloudToolUserId;
	// 输入文件地址
	protected String  inputaddress;
	// 输出文件地址
	protected String  outputaddress;
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
	 * 返回 cloud_tool_user_id
	 * @return
	 */
	public Long getCloudToolUserId() 
	{
		return this.cloudToolUserId;
	}
	public void setInputaddress(String inputaddress) 
	{
		this.inputaddress = inputaddress;
	}
	/**
	 * 返回 输入文件地址
	 * @return
	 */
	public String getInputaddress() 
	{
		return this.inputaddress;
	}
	public void setOutputaddress(String outputaddress) 
	{
		this.outputaddress = outputaddress;
	}
	/**
	 * 返回 输出文件地址
	 * @return
	 */
	public String getOutputaddress() 
	{
		return this.outputaddress;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Cloud_tool_user_file)) 
		{
			return false;
		}
		Cloud_tool_user_file rhs = (Cloud_tool_user_file) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.cloudToolUserId, rhs.cloudToolUserId)
		.append(this.inputaddress, rhs.inputaddress)
		.append(this.outputaddress, rhs.outputaddress)
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
		.append(this.inputaddress) 
		.append(this.outputaddress) 
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
		.append("inputaddress", this.inputaddress) 
		.append("outputaddress", this.outputaddress) 
		.toString();
	}
   
  

}