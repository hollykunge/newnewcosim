package com.casic.cloud.model.tool.input;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_tool_user_inputFile Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:maliang
 * 创建时间:2014-03-06 18:01:30
 */
public class Cloud_tool_user_inputFile extends BaseModel
{
	// id
	protected Long  id;
	// cloud_tool_user_id
	protected Long  cloudToolUserId;
	// 输入文件地址
	protected String  inputaddress;
	private String toolName;
	protected String type;
	protected String generateStrategy;
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


   	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Cloud_tool_user_inputFile)) 
		{
			return false;
		}
		Cloud_tool_user_inputFile rhs = (Cloud_tool_user_inputFile) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.cloudToolUserId, rhs.cloudToolUserId)
		.append(this.inputaddress, rhs.inputaddress)
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
		.toString();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGenerateStrategy() {
		return generateStrategy;
	}
	public void setGenerateStrategy(String generateStrategy) {
		this.generateStrategy = generateStrategy;
	}
   
  

}