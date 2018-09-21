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
public class Cloud_tool_user_outputFile  implements Serializable{
	private long id;
	private Cloud_tool_user cloud_tool_user;
	private String outputAddress;
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@ManyToOne(cascade = { CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "cloud_tool_user_id")
	public Cloud_tool_user getCloud_tool_user() {
		return cloud_tool_user;
	}
	public void setCloud_tool_user(Cloud_tool_user cloud_tool_user) {
		this.cloud_tool_user = cloud_tool_user;
	}
	public String getOutputAddress() {
		return outputAddress;
	}
	public void setOutputAddress(String outputAddress) {
		this.outputAddress = outputAddress;
	}
	
	

}
