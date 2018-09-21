package com.casic.cloud.toolEnvironment.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.casic.cloud.toolEnvironment.bean.*;

@Entity
public class Cloud_tool_bpm_node  implements Serializable{
	private long toolNodeId;
	private Cloud_tool_user cloud_tool_user;
	private long setId;
	@Id
	@GeneratedValue
	public long getToolNodeId() {
		return toolNodeId;
	}
	public void setToolNodeId(long toolNodeId) {
		this.toolNodeId = toolNodeId;
	}
	@ManyToOne(cascade = {CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "toolUserId")
	public Cloud_tool_user getCloud_tool_user() {
		return cloud_tool_user;
	}
	public void setCloud_tool_user(Cloud_tool_user cloud_tool_user) {
		this.cloud_tool_user = cloud_tool_user;
	}
	public long getSetId() {
		return setId;
	}
	public void setSetId(long setId) {
		this.setId = setId;
	}
	

}
