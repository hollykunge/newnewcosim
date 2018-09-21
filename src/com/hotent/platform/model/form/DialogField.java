package com.hotent.platform.model.form;

import com.hotent.core.customertable.ColumnModel;

/**
 * 对话框字段。
 * @author ray
 *
 */
public class DialogField {
	
	/**
	 * 字段名称
	 */
	private String fieldName="";
	/**
	 * 字段注释
	 */
	private String comment="";
	/**
	 * 条件
	 */
	private String condition="";
	/**
	 * 字段类型
	 */
	private String fieldType="";
	/**
	 * 默认类型。
	 * 1.表单输入。
	 * 2.固定值。
	 * 3.脚本。
	 */
	private int defaultType=1;
	
	/**
	 * 默认值。
	 */
	private String defaultValue="";
	
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public int getDefaultType() {
		return defaultType;
	}
	public void setDefaultType(int defaultType) {
		this.defaultType = defaultType;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	


}
