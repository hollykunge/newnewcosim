package com.hotent.platform.model.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpUtils;

import com.hotent.core.db.entity.SQLClause;
import com.hotent.core.db.entity.SQLField;
import com.hotent.core.model.BaseModel;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 对象功能:通用表单对话框 Model对象 开发公司: 开发人员:ray 创建时间:2012-06-25 11:05:08
 */
public class SysTableManage extends BaseModel {
	/**
	 * 分页常量
	 */
	public static final String PAGE = "p";
	/**
	 * 分页大小常量。
	 */
	public static final String PAGESIZE = "z";
	/**
	 * 排序常量
	 */
	public static final String SORTFIELD="s";
	/**
	 * 排序方向常量
	 */
	public static final String ORDERSEQ="o";
	
	/**
	 * 显示样式 列表
	 */
	public static final  int 	STYLE_LIST							= 0;
	/**
	 * 显示样式 树型
	 */
	public static final  int 	STYLE_TREE							= 1;
	
	
	// 主键ID
	protected Long id = 0L;
	// 名称
	protected String name = "";
	// 名
	protected String alias = "";
	// 显示样式 0,列表1,树形
	protected Integer style = 0;
	// 是否分页
	protected Integer needPage = 1;
	// 是否为表 0,视图 1,数据库表
	protected Integer isTable = 1;
	// 对象名称
	protected String objName = "";
	// 需要显示的字段
	protected String displayField = "";
	// 条件字段
	protected String conditionField = "";
	// 数据源名称
	protected String dsName = "";
	// 数据源别名
	protected String dsAlias = "";
	// 分页数据
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	// 分页bean。
	private PageBean pageBean = new PageBean(1, 20);
	// 分页大小
	private Integer pageSize = 10;
	// 模板ID
	private Long templateId;
	// 显示模板
	private String dspTemplate;
	// 是否可编辑 0-不可编辑，1-可编辑
	private Integer editable=0;
	// 条件类型 1-JSON类型， 2-脚本类型
	private Integer conditionType=1;
	
	
	private List<Parameter> parameterList=new ArrayList<Parameter>();
	private Map<String,Object> parameterMap=new HashMap<String, Object>();
	
	/** 自定义参数 JSON对象
	 * {
	 * 	na,//参数名
	 *  cm,//显示名
	 * 	ty,//参数类型
	 * 	vf,//参数值来源
	 * 	va,//参数值
	 * }
	 */
	private String parameters;

	
	/**
	 * 取得tree字段。
	 * 
	 * @return
	 */
	public Map<String, String> getTreeField() {
		if (this.style == 0) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		JSONObject jsonObject = JSONObject.fromObject(this.displayField);
		map.put("id", jsonObject.get("id").toString());
		map.put("pid", jsonObject.get("pid").toString());
		map.put("displayName", jsonObject.get("displayName").toString());
		return map;
	}


	/**
	 * 获取显示字段
	 * @return
	 */
	public List<SQLField> getDisplayList() {
		if (this.style == 1) {
			return null;
		}
		if (StringUtil.isEmpty(this.displayField)) {
			return new ArrayList<SQLField>();
		}
		List<SQLField> displayFields = new ArrayList<SQLField>();
		JSONArray jsonArray = JSONArray.fromObject(this.displayField);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			SQLField field=new SQLField();
			Boolean display=jsonObject.getBoolean("ds");
			if(display==true){
				field.setComment(jsonObject.getString("cm"));
				field.setName(jsonObject.getString("na"));
				field.setType(jsonObject.getString("ty"));
				if(!SQLField.COLUMNTYPE_CLOB.equalsIgnoreCase(field.getType())){
					field.setSortable(true);
				}
				displayFields.add(field);
			}
		}
		return displayFields;
	}


	/**
	 * 取得条件字段
	 * @return
	 */
	public List<SQLClause> getConditionList() {
		if(conditionType==2){
			return new ArrayList<SQLClause>();
		}
		
		if (StringUtil.isEmpty(this.conditionField)) {
			return new ArrayList<SQLClause>();
		}
		JSONArray jsonArray = JSONArray.fromObject(conditionField);
		List<SQLClause> conditionFields=new ArrayList<SQLClause>();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			SQLClause field=new SQLClause();
			field.setJoinType(jsonObject.getString("jt"));
			field.setName(jsonObject.getString("na"));
			field.setComment(jsonObject.getString("cm"));
			field.setType(jsonObject.getString("ty"));
			field.setValue(jsonObject.get("va"));
			field.setValueFrom(jsonObject.getInt("vf"));
			field.setOperate(jsonObject.getString("op"));
			conditionFields.add(field);
		}
		return conditionFields;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 返回 主键ID
	 * 
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 对话框名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 返回 对话框别名
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	/**
	 * 返回 显示样式 0,列表 1,树形
	 * 
	 * @return
	 */
	public Integer getStyle() {
		return this.style;
	}

	public void setNeedPage(Integer needPage) {
		this.needPage = needPage;
	}

	/**
	 * 返回 是否分页
	 * 
	 * @return
	 */
	public Integer getNeedPage() {
		return this.needPage;
	}

	public void setIsTable(Integer isTable) {
		this.isTable = isTable;
	}

	/**
	 * 返回 是否为表 0,视图 1,数据库表
	 * 
	 * @return
	 */
	public Integer getIsTable() {
		return this.isTable;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	/**
	 * 返回 对象名称
	 * 
	 * @return
	 */
	public String getObjName() {
		return this.objName;
	}

	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}

	/**
	 * 返回 需要显示的字段
	 * 
	 * @return
	 */
	public String getDisplayField() {
		return this.displayField;
	}

	public void setConditionField(String conditionField) {
		this.conditionField = conditionField;
	}

	/**
	 * 返回 条件字段
	 * 
	 * @return
	 */
	public String getConditionField() {
		return this.conditionField;
	}
	

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	/**
	 * 返回 数据源名称
	 * 
	 * @return
	 */
	public String getDsName() {
		return this.dsName;
	}

	public void setDsAlias(String dsAlias) {
		this.dsAlias = dsAlias;
	}

	/**
	 * 返回 数据源别名
	 * 
	 * @return
	 */
	public String getDsAlias() {
		return this.dsAlias;
	}

	/**
	 * 返回 分页大小
	 * 
	 * @return
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * 
	 * @return
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Integer getConditionType() {
		return conditionType;
	}


	public void setConditionType(Integer conditionType) {
		this.conditionType = conditionType;
	}


	public String getDspTemplate() {
		return dspTemplate;
	}

	public void setDspTemplate(String dspTemplate) {
		this.dspTemplate = dspTemplate;
	}

	public Integer getEditable() {
		return editable;
	}


	public void setEditable(Integer editable) {
		this.editable = editable;
	}


	public String getParameters() {
		return parameters;
	}


	public void setParameters(String parameters) {
		this.parameters = parameters;
	}


	/**
	 * 获取自定义变量的Map表示形式。在调用此方法前请使用
	 * {@link com.hotent.platform.service.system.SysTableManageService.parseCustomParameterAsMap}
	 * 对自定义变量进行解析
	 * @return
	 */
	public Map<String,Object> getParameterMap() {
		return parameterMap;
	}


	public void setParameterMap(Map<String,Object> parameterMap) {
		this.parameterMap = parameterMap;
	}


	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
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
	public boolean equals(Object object) {
		if (!(object instanceof SysTableManage)) {
			return false;
		}
		SysTableManage rhs = (SysTableManage) object;
		return new EqualsBuilder().append(this.id, rhs.id).append(this.name, rhs.name).append(this.alias, rhs.alias).append(this.style, rhs.style)
				.append(this.needPage, rhs.needPage)
				.append(this.isTable, rhs.isTable).append(this.objName, rhs.objName).append(this.displayField, rhs.displayField)
				.append(this.conditionField, rhs.conditionField).append(this.dsName, rhs.dsName)
				.append(this.dsAlias, rhs.dsAlias).isEquals();
	}
	
	

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.name).append(this.alias).append(this.style)
				.append(this.needPage).append(this.isTable).append(this.objName).append(this.displayField)
				.append(this.conditionField).append(this.dsName).append(this.dsAlias).toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("alias", this.alias).append("style", this.style)
				.append("needPage", this.needPage)
				.append("isTable", this.isTable).append("objName", this.objName).append("displayField", this.displayField)
				.append("conditionField", this.conditionField).append("dsName", this.dsName)
				.append("dsAlias", this.dsAlias).toString();
	}
	
	
	public List<Parameter> getParameterList() {
		List<Parameter> parameterList=new ArrayList<Parameter>();
		if (StringUtil.isEmpty(this.parameters)) {
			return parameterList;
		}
		JSONArray jsonArray = JSONArray.fromObject(this.parameters);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			Parameter field= new Parameter();
			field.setName(jsonObject.getString("na"));
			field.setComment(jsonObject.getString("cm"));
			field.setType(jsonObject.getString("ty"));
			field.setValue(jsonObject.get("va"));
			field.setValueFrom(jsonObject.getInt("vf"));
			if(StringUtil.isEmpty(field.getComment())){
				field.setComment(field.getName());
			}
			parameterList.add(field);
		}
		return parameterList;
	}


	public void setParameterList(List<Parameter> parameterList) {
		this.parameterList = parameterList;
	}


	public class Parameter{
		private String name;
		private String comment;
		private String type;
		private int valueFrom;
		private Object value;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getValueFrom() {
			return valueFrom;
		}
		public void setValueFrom(int valueFrom) {
			this.valueFrom = valueFrom;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
	}
}