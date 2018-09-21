package com.casic.cloud.model.tool.output;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.casic.cloud.toolEnvironment.util.FileUtil;
import com.hotent.core.model.BaseModel;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_tool_user_outputFile Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:maliang
 * 创建时间:2014-03-06 18:01:30
 */
public class Cloud_tool_user_outputFile extends BaseModel
{
	// id
	protected Long  id;
	// 用户工具id
	protected Long  cloudToolUserId;
	// 输出文件地址
	protected String  outputaddress;
	private String name;
	private String toolName;
	private boolean exist = true;
	private long runId;
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
		String name = "";
		name = new File(outputaddress).getName();
		
		this.name = name;
		return this.outputaddress;
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
		if (!(object instanceof Cloud_tool_user_outputFile)) 
		{
			return false;
		}
		Cloud_tool_user_outputFile rhs = (Cloud_tool_user_outputFile) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.cloudToolUserId, rhs.cloudToolUserId)
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
		.append("outputaddress", this.outputaddress) 
		.toString();
	}
	public String getName() {
		String name = "";
		name = new File(outputaddress).getName();
		
		this.name = name;
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getExist() {
		File file = FileUtil.getExistInstanceStartWithRunId(new Long(runId).toString(), "", new File(outputaddress) , true);
		if(file==null){
			exist = false;
		}else if(file.exists()){
			exist = true;
		}else{
			exist = false;
		}
		
		return exist;
	}
	public void setExist(boolean exist) {
		
		this.exist = exist;
	}
	public long getRunId() {
		return runId;
	}
	public void setRunId(long runId) {
		this.runId = runId;
	}
   
	

}