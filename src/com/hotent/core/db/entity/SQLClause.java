package com.hotent.core.db.entity;

public class SQLClause {
	/**
	 * 来自输入
	 */
	public static short VALUEFROM_INPUT=1;
	/**
	 * 固定值
	 */
	public static short VALUEFROM_FIX=2;
	/**
	 * 脚本
	 */
	public static short VALUEFROM_SCRIPT=3;
	
	private String name;
	private String qualifiedName;
	private String label;
	private String comment;
	private String type;
	private String joinType;
	private String operate;
	private Object value;
	private int valueFrom;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQualifiedName() {
		return qualifiedName;
	}
	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getJoinType() {
		return joinType;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public int getValueFrom() {
		return valueFrom;
	}
	public void setValueFrom(int valueFrom) {
		this.valueFrom = valueFrom;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}