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
public class Cloud_tool_user_parasmapper  implements Serializable{

	private long id;
	private Cloud_tool_user cloud_tool_user;
	private String name;
	private String form_mapper_name;
	private String chinese_mapper_name;
	private String type;
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
	@JoinColumn(name="cloud_tool_user_id")
	public Cloud_tool_user getCloud_tool_user() {
		return cloud_tool_user;
	}
	public void setCloud_tool_user(Cloud_tool_user cloud_tool_user) {
		this.cloud_tool_user = cloud_tool_user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getForm_mapper_name() {
		return form_mapper_name;
	}
	public void setForm_mapper_name(String form_mapper_name) {
		this.form_mapper_name = form_mapper_name;
	}
	public String getChinese_mapper_name() {
		return chinese_mapper_name;
	}
	public void setChinese_mapper_name(String chinese_mapper_name) {
		this.chinese_mapper_name = chinese_mapper_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
