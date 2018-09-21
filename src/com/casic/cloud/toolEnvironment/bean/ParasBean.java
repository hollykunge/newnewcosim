package com.casic.cloud.toolEnvironment.bean;

import java.io.Serializable;
/**
 * 参数数据
 * @author ml
 *
 */
public class ParasBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String realName = "";
	private String value = "";
	private String chineseName = "";
	public ParasBean(String realName,String value,String chineseName){
		this.realName = realName;
		this.value = value;
		this.chineseName = chineseName;
		
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	

}
