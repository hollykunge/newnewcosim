package com.casic.cloud.toolEnvironment.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.casic.cloud.toolEnvironment.bean.*;
@Entity
public class Cloud_tool_user  implements Serializable{
	private long toolUserId;
	private long userId;
	private Cloud_tool cloud_tool;
	private String mytoolAddress;
	private List<Cloud_tool_bpm_node> cloud_tool_bpm_nodes;
	private List<Cloud_tool_user_inputFile> cloud_tool_user_inputFiles;
	private List<Cloud_tool_user_outputFile> cloud_tool_user_outputFiles;
	private List<Cloud_tool_user_parasmapper> cloud_tool_user_parasmappers;
	@Id
	@GeneratedValue
	public long getToolUserId() {
		return toolUserId;
	}
	public void setToolUserId(long toolUserId) {
		this.toolUserId = toolUserId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	@ManyToOne(cascade = {CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "toolId")
	public Cloud_tool getCloud_tool() {
		return cloud_tool;
	}
	public void setCloud_tool(Cloud_tool cloud_tool) {
		this.cloud_tool = cloud_tool;
	}
	public String getMytoolAddress() {
		return mytoolAddress;
	}
	public void setMytoolAddress(String mytoolAddress) {
		this.mytoolAddress = mytoolAddress;
	}
	@OneToMany(mappedBy="cloud_tool_user",fetch=FetchType.LAZY)
	public List<Cloud_tool_bpm_node> getCloud_tool_bpm_nodes() {
		return cloud_tool_bpm_nodes;
	}
	public void setCloud_tool_bpm_nodes(
			List<Cloud_tool_bpm_node> cloud_tool_bpm_nodes) {
		this.cloud_tool_bpm_nodes = cloud_tool_bpm_nodes;
	}
	@OneToMany(mappedBy="cloud_tool_user",fetch=FetchType.LAZY)
	public List<Cloud_tool_user_inputFile> getCloud_tool_user_inputFiles() {
		return cloud_tool_user_inputFiles;
	}
	public void setCloud_tool_user_inputFiles(
			List<Cloud_tool_user_inputFile> cloud_tool_user_inputFiles) {
		this.cloud_tool_user_inputFiles = cloud_tool_user_inputFiles;
	}
	@OneToMany(mappedBy="cloud_tool_user",fetch=FetchType.EAGER)
	
	public List<Cloud_tool_user_parasmapper> getCloud_tool_user_parasmappers() {
		return cloud_tool_user_parasmappers;
	}
	public void setCloud_tool_user_parasmappers(
			List<Cloud_tool_user_parasmapper> cloud_tool_user_parasmappers) {
		this.cloud_tool_user_parasmappers = cloud_tool_user_parasmappers;
	}
	@OneToMany(mappedBy="cloud_tool_user",fetch=FetchType.LAZY)
	public List<Cloud_tool_user_outputFile> getCloud_tool_user_outputFiles() {
		return cloud_tool_user_outputFiles;
	}
	public void setCloud_tool_user_outputFiles(
			List<Cloud_tool_user_outputFile> cloud_tool_user_outputFiles) {
		this.cloud_tool_user_outputFiles = cloud_tool_user_outputFiles;
	}
	
	

}
