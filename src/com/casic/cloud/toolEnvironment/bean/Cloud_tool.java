package com.casic.cloud.toolEnvironment.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cloud_tool  implements Serializable{
	private long toolId;
	private String toolName;
	private int toolType;
	private String toolAddress;
	private String toolDesc;
	private Timestamp createTime;
	private Timestamp updateTime;
	private List<Cloud_tool_user> cloud_tool_users;
	@Id
	@GeneratedValue
	public long getToolId() {
		return toolId;
	}
	public void setToolId(long toolId) {
		this.toolId = toolId;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public int getToolType() {
		return toolType;
	}
	public void setToolType(int toolType) {
		this.toolType = toolType;
	}
	public String getToolAddress() {
		return toolAddress;
	}
	public void setToolAddress(String toolAddress) {
		this.toolAddress = toolAddress;
	}
	public String getToolDesc() {
		return toolDesc;
	}
	public void setToolDesc(String toolDesc) {
		this.toolDesc = toolDesc;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	@OneToMany(mappedBy="cloud_tool",fetch=FetchType.LAZY)
	public List<Cloud_tool_user> getCloud_tool_users() {
		return cloud_tool_users;
	}
	public void setCloud_tool_users(List<Cloud_tool_user> cloud_tool_users) {
		this.cloud_tool_users = cloud_tool_users;
	}
	
	

}
