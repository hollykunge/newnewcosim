package com.hotent.platform.model.form;

/**
 * sql对象数据。
 * <pre>
 * 	包括SQL数据和对应的参数。
 * </pre>
 * @author ray
 *
 */
public class SqlModel {
	
	public SqlModel(){}
	
	public SqlModel(String sql,Object[] values){
		this.sql=sql;
		this.values=values;
	}
	
	/**
	 * sql语句
	 */
	private String sql="";
	
	/**
	 * sql值数组。
	 */
	private Object[] values;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}
	
	

}
