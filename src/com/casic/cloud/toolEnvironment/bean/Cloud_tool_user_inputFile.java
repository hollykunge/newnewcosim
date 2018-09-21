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
public class Cloud_tool_user_inputFile  implements Serializable{
	private long id;
	private Cloud_tool_user cloud_tool_user;
	private String inputAddress;
	private String type;
	private String generateStrategy;
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@ManyToOne(cascade = {CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "cloud_tool_user_id")
	public Cloud_tool_user getCloud_tool_user() {
		return cloud_tool_user;
	}
	public void setCloud_tool_user(Cloud_tool_user cloud_tool_user) {
		this.cloud_tool_user = cloud_tool_user;
	}
	public String getInputAddress() {
		return inputAddress;
	}
	public void setInputAddress(String inputAddress) {
		this.inputAddress = inputAddress;
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
