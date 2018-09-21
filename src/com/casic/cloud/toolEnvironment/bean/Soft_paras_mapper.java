package com.casic.cloud.toolEnvironment.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Soft_paras_mapper implements Serializable{
	private long id;
	private Soft_config soft_config;
	private String name;
	private String form_mapper_name;
	private String chinese_mapper_name;
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
	public Soft_config getSoft_config() {
		return soft_config;
	}
	public void setSoft_config(Soft_config soft_config) {
		this.soft_config = soft_config;
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
	

}
