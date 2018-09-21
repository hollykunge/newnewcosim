package com.hotent.platform.model.system;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hotent.core.db.entity.SQLClause;
import com.hotent.core.db.entity.SQLField;
import com.hotent.core.model.BaseModel;
import com.hotent.core.page.PageBean;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:SYS_custom_display Model对象
 * 开发公司:宏天
 * 开发人员:Raise
 * 创建时间:2012-11-19 17:54:51
 */
public class SysCustomDisplay extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 分页
	 */
	public static final int NEEDPAGE_YES = 1;
	/**
	 * 不分布
	 */
	public static final int NEEDPAGE_NO = -1;
	
	public static final String PAGE										="p";
	public static final String PAGESIZE									="z";
	public static final String ORDERSEQ									="o";
	public static final String SORTFIELD							    ="s";
	/**
	 * 脚本类型 SQL
	 */
	public static final int SCRIPTTYPE_SQL							    =1;
	/**
	 * 脚本类型 GROOVY
	 */
	public static final int SCRIPTTYPE_GROOVY							=2;
	
	// ID
	protected Long  id;
	// 名称
	protected String  name;
	// 模板
	protected String  template;
	// 是否分页
	protected short  paged=-1;
	// 分页大小
	protected int  pageSize;
	// 描述
	protected String  description;
	// 脚本
	protected String  script;
	// 脚本类型 1:SQL 2:Groovy
	protected short scriptType=1;
	// 数据源名
	protected String  dsName;
	// 条件字段
	protected String  conditionField;
	// 字段设置
	protected String fieldSetting;
	// 设置
	protected String setting;
	// 显示模板
	private String dspTemplate;
	
	protected PageBean pageBean=new PageBean(1,20);
	
	public String getSetting() {
		return setting;
	}
	public void setSetting(String setting) {
		this.setting = setting;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
	public short getScriptType() {
		return scriptType;
	}
	
	public void setScriptType(short scriptType) {
		this.scriptType = scriptType;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getFieldSetting() {
		return fieldSetting;
	}
	
	public void setFieldSetting(String fieldSetting) {
		this.fieldSetting = fieldSetting;
	}
	
	public String getConditionField() {
		return conditionField;
	}

	public void setConditionField(String conditionField) {
		this.conditionField = conditionField;
	}

	public Short getPaged() {
		return paged;
	}
	public void setPaged(Short paged) {
		this.paged = paged;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof SysCustomDisplay)) 
		{
			return false;
		}
		SysCustomDisplay rhs = (SysCustomDisplay) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.template, rhs.template)
		.append(this.setting, rhs.setting)
		.append(this.paged, rhs.paged)
		.append(this.pageSize, rhs.pageSize)
		.append(this.description, rhs.description)
		.append(this.script, rhs.script)
		.append(this.scriptType, rhs.scriptType)
		.append(this.dsName, rhs.dsName)
		.append(this.conditionField, rhs.conditionField)
		.append(this.dspTemplate, rhs.dspTemplate)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.template) 
		.append(this.setting) 
		.append(this.paged) 
		.append(this.pageSize) 
		.append(this.description) 
		.append(this.script)
		.append(this.scriptType)
		.append(this.dsName) 
		.append(this.conditionField) 
		.append(this.dspTemplate) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("template", this.template) 
		.append("setting", this.setting) 
		.append("paged", this.paged) 
		.append("pagesize", this.pageSize) 
		.append("description", this.description) 
		.append("script", this.script) 
		.append("scriptType", this.scriptType) 
		.append("dsname", this.dsName) 
		.append("conditionfield", this.conditionField) 
		.append("dspTemplate", this.dspTemplate) 
		.toString();
	}

	public List<SQLField> getDisplayFields() {
		JSONArray jsonArray = JSONArray.fromObject(fieldSetting);
		List<SQLField> displayFields=new ArrayList<SQLField>();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			SQLField field=new SQLField();
			Boolean display=jsonObject.getBoolean("display");
			if(display==true){
				field.setComment(jsonObject.getString("comment"));
				field.setIndex(jsonObject.getInt("index"));
				field.setLabel(jsonObject.getString("label"));
				field.setName(jsonObject.getString("name"));
				field.setQualifiedName(jsonObject.getString("qualifiedName"));
				field.setTable(jsonObject.getString("table"));
				field.setType(jsonObject.getString("type"));
//				if(SUPSUBSELECT_YES==supSubSelect && !SQLField.COLUMNTYPE_CLOB.equalsIgnoreCase(field.getType())){
//					field.setSortable(true);
//				}
				displayFields.add(field);
			}
		}
		return displayFields;
	}
	
	public List<SQLClause> getConditionFields() {
		JSONArray jsonArray = JSONArray.fromObject(conditionField);
		List<SQLClause> conditionFields=new ArrayList<SQLClause>();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			SQLClause field=new SQLClause();
			field.setJoinType(jsonObject.getString("joinType"));
			field.setQualifiedName(jsonObject.getString("qualifiedName"));
			field.setName(jsonObject.getString("name"));
			field.setComment(jsonObject.getString("comment"));
			field.setType(jsonObject.getString("type"));
			field.setValue(jsonObject.get("value"));
			field.setValueFrom(jsonObject.getInt("valueFrom"));
			field.setOperate(jsonObject.getString("operate"));
			conditionFields.add(field);
		}
		return conditionFields;
	}
	public String getDspTemplate() {
		return dspTemplate;
	}
	public void setDspTemplate(String dspTemplate) {
		this.dspTemplate = dspTemplate;
	}
}