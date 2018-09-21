package com.casic.cloud.toolEnvironment.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Soft_config  implements Serializable{
	
	private long id;
	private String name;
	private String soft_path;
	private String inputfile_path;
	private String outputfile_path;
	private List<Soft_paras_mapper> soft_paras_mappers;
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@OneToMany(mappedBy="soft_config",fetch=FetchType.EAGER)
	public List<Soft_paras_mapper> getSoft_paras_mappers() {
		return soft_paras_mappers;
	}

	public void setSoft_paras_mappers(List<Soft_paras_mapper> soft_paras_mappers) {
		this.soft_paras_mappers = soft_paras_mappers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSoft_path() {
		return soft_path;
	}

	public void setSoft_path(String soft_path) {
		this.soft_path = soft_path;
	}

	public String getInputfile_path() {
		return inputfile_path;
	}

	public void setInputfile_path(String inputfile_path) {
		this.inputfile_path = inputfile_path;
	}

	public String getOutputfile_path() {
		return outputfile_path;
	}

	public void setOutputfile_path(String outputfile_path) {
		this.outputfile_path = outputfile_path;
	}

	
	
	

}
