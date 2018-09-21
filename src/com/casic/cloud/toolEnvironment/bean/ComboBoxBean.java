package com.casic.cloud.toolEnvironment.bean;

public class ComboBoxBean {

	public ComboBoxBean(String address){
		this.address = address;
		String str[] = address.split("\\\\", 0);
		name = str[str.length-1];
	}
	private String name;
	private String address;
	
	public String toString(){
		return name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
