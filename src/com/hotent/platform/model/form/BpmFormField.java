package com.hotent.platform.model.form;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.customertable.ColumnModel;
import com.hotent.core.model.BaseModel;
import com.hotent.core.util.StringUtil;
/**
 * 对象功能:bpm_form_field Model对象
 * 开发公司:
 * 开发人员:xwy
 * 创建时间:2012-02-06 15:49:21
 */
@XmlRootElement(name="field")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmFormField extends BaseModel  implements Cloneable 
{
	public static short VALUE_FROM_FORM = 0;
	public static short VALUE_FROM_SCRIPT_SHOW = 1;
	public static short VALUE_FROM_SCRIPT_HIDDEN = 2;
	public static short VALUE_FROM_IDENTITY = 3;
	
	
	/**
	 * 条件值来源类型，来自表单输入
	 */
	public static short COND_TYPE_FORM                  = 0;
	/**
	 * 条件值来源类型，来自script
	 */
	public static short COND_TYPE_SCRIPT                = 1;
	/**
	 * 条件值来源类型，来自固定值
	 */
	public static short COND_TYPE_FIX                   = 2;
	
	//隐藏字段 +ID。
	public static final String FieldHidden="ID";
	//人员选择器(单选)
	public static final short Selector_User=4;
	//人员选择器(多选)
	public static final short Selector_UserMulti=8;
	//角色选择器
	public static final short Selector_Role=5;
	//组织选择器
	public static final short Selector_Org=6;
	//岗位选择器
	public static final short Selector_Position=7;
	
	//附件
	public static final short ATTACHEMENT=9;
	
	public static String ElmName = "field";
	
	// fieldId
	@XmlAttribute
	protected Long fieldId=0L;
	// tableId
	@XmlAttribute
	protected Long tableId=0L;
	// 字段名称
	@XmlAttribute
	protected String fieldName = "";
	// 字段类型
	@XmlAttribute
	protected String fieldType = "";
	// 是否必填
	@XmlAttribute
	protected Short isRequired;
	// 是否列表显示
	@XmlAttribute
	protected Short isList;
	// 是否查询显示
	@XmlAttribute
	protected Short isQuery;
	// 说明
	@XmlAttribute
	protected String fieldDesc = "";
	// 字符长度，仅针对字符类型
	@XmlAttribute
	protected Integer charLen;
	// 整数长度，仅针对数字类型
	@XmlAttribute
	protected Integer intLen;
	// 小数长度，仅针对数字类型
	@XmlAttribute
	protected Integer decimalLen;
	// 数据字典的类别，仅针对数据字典类型
	protected String dictType = "";
	// 是否删除
	@XmlAttribute
	protected Short isDeleted = 0;
	// 验证规则
	@XmlAttribute
	protected String validRule = "";
	// 字段原名
	@XmlAttribute
	protected String originalName = "";
	// 排列顺序
	protected Integer sn;
	// 值来源 0-表单 1-脚本
	@XmlAttribute
	protected Short valueFrom = 0;
	// 脚本
	@XmlAttribute
	protected String script = "";
	// 控件类型
	@XmlAttribute
	protected Short controlType;
	// 是否隐藏域
	@XmlAttribute
	protected Short isHidden = 0;
	//是否流程变量
	@XmlAttribute
	protected Short isFlowVar = 0;
	//流水号别名
	@XmlAttribute
	protected String identity = "";
	//下拉框
	@XmlAttribute
	protected String options = "";
	//控件属性
	@XmlAttribute
	protected String ctlProperty="";
	//新添加的列
	protected boolean isAdded=false;
	//支持手机客户端显示
	protected Short	isAllowMobile=0;
	//文字为日期格式
	protected Short	isDateString=0;
	//文字取当前日期
	protected Short	isCurrentDateStr=0;
	//控件样式
	protected String  style;


	
	//该字段是否主键 
	//1为主键
	protected Integer isPk=0;

	public void setFieldId(Long fieldId) 
	{
		this.fieldId = fieldId;
	}
	/**
	 * 返回 fieldId
	 * @return
	 */
	public Long getFieldId() 
	{
		return fieldId;
	}

	public void setTableId(Long tableId) 
	{
		this.tableId = tableId;
	}
	
	public Short getIsAllowMobile() {
		return isAllowMobile;
	}
	
	public void setIsAllowMobile(Short isAllowMobile) {
		this.isAllowMobile = isAllowMobile;
	}
	
	/**
	 * 返回 tableId
	 * @return
	 */
	public Long getTableId() 
	{
		return tableId;
	}

	public void setFieldName(String fieldName) 
	{
		this.fieldName = fieldName;
	}
	/**
	 * 返回 字段名称
	 * @return
	 */
	public String getFieldName() 
	{
		return fieldName;
	}

	public void setFieldType(String fieldType) 
	{
		this.fieldType = fieldType;
	}
	/**
	 * 返回 字段类型
	 * @return
	 */
	public String getFieldType() 
	{
		return fieldType;
	}

	public void setIsRequired(Short isRequired) 
	{
		this.isRequired = isRequired;
	}
	/**
	 * 返回 是否必填
	 * @return
	 */
	public Short getIsRequired() 
	{
		return isRequired;
	}

	public void setIsList(Short isList) 
	{
		this.isList = isList;
	}
	/**
	 * 返回 是否列表显示
	 * @return
	 */
	public Short getIsList() 
	{
		return isList;
	}

	public void setIsQuery(Short isQuery) 
	{
		this.isQuery = isQuery;
	}
	/**
	 * 返回 是否查询显示
	 * @return
	 */
	public Short getIsQuery() 
	{
		return isQuery;
	}

	public void setFieldDesc(String fieldDesc) 
	{
		this.fieldDesc = fieldDesc;
	}
	/**
	 * 返回 说明
	 * @return
	 */
	public String getFieldDesc() 
	{
		return fieldDesc;
	}

	public void setCharLen(Integer charLen) 
	{
		this.charLen = charLen;
	}
	/**
	 * 返回 字符长度，仅针对字符类型
	 * @return
	 */
	public Integer getCharLen() 
	{
		return charLen;
	}

	public void setIntLen(Integer intLen) 
	{
		this.intLen = intLen;
	}
	/**
	 * 返回 整数长度，仅针对数字类型
	 * @return
	 */
	public Integer getIntLen() 
	{
		return intLen;
	}

	public void setDecimalLen(Integer decimalLen) 
	{
		this.decimalLen = decimalLen;
	}
	/**
	 * 返回 小数长度，仅针对数字类型
	 * @return
	 */
	public Integer getDecimalLen() 
	{
		return decimalLen;
	}

	public void setDictType(String dictType) 
	{
		this.dictType = dictType;
	}
	/**
	 * 返回 数据字典的类别，仅针对数据字典类型
	 * @return
	 */
	public String getDictType() 
	{
		return dictType;
	}

	public void setIsDeleted(Short isDeleted) 
	{
		this.isDeleted = isDeleted;
	}
	/**
	 * 返回 是否删除
	 * @return
	 */
	public Short getIsDeleted() 
	{
		return isDeleted;
	}

	public void setValidRule(String validRule) 
	{
		this.validRule = validRule;
	}
	/**
	 * 返回 验证规则
	 * @return
	 */
	public String getValidRule() 
	{
		return validRule;
	}

	public void setOriginalName(String originalName) 
	{
		this.originalName = originalName;
	}
	/**
	 * 返回 字段原名
	 * @return
	 */
	public String getOriginalName() 
	{
		return originalName;
	}

	public void setSn(Integer sn) 
	{
		this.sn = sn;
	}
	/**
	 * 返回 排列顺序
	 * @return
	 */
	public Integer getSn() 
	{
		return sn;
	}

	public void setValueFrom(Short valueFrom) 
	{
		this.valueFrom = valueFrom;
	}
	/**
	 * 返回 值来源 0-表单 1-脚本
	 * @return
	 */
	public Short getValueFrom() 
	{
		return valueFrom;
	}

	public void setScript(String script) 
	{
		this.script = script;
	}
	/**
	 * 返回 脚本
	 * @return
	 */
	public String getScript() 
	{
		return script;
	}

	public void setControlType(Short controlType) 
	{
		this.controlType = controlType;
	}
	/**
	 * 返回 控件类型
	 * @return
	 */
	public Short getControlType() 
	{
		return controlType;
	}
	
   	public Short getIsHidden()
	{
		return isHidden;
	}
	public void setIsHidden(Short isHidden)
	{
		this.isHidden = isHidden;
	}
	
	
	public Short getIsFlowVar()
	{
		return isFlowVar;
	}
	public void setIsFlowVar(Short isFlowVar)
	{
		this.isFlowVar = isFlowVar;
	}
	public String getIdentity()
	{
		return identity;
	}
	public void setIdentity(String identity)
	{
		this.identity = identity;
	}
	public Integer getIsPk()
	{
		return isPk;
	}
	public void setIsPk(Integer isPk)
	{
		this.isPk = isPk;
	}
	
	
	public String getOptions() {
		return this.options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	
	public String[] getAryOptions(){
		return this.options.split("\n");
	}
	
	public boolean isAdded() {
		return isAdded;
	}
	public void setAdded(boolean isAdded) {
		this.isAdded = isAdded;
	}
	
	
	public Short getIsDateString() {
		return isDateString;
	}
	public void setIsDateString(Short isDateString) {
		this.isDateString = isDateString;
	}
	public Short getIsCurrentDateStr() {
		return isCurrentDateStr;
	}
	public void setIsCurrentDateStr(Short isCurrentDateStr) {
		this.isCurrentDateStr = isCurrentDateStr;
	}
	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * 获取控件属性
	 * @return
	 */
	public String getCtlProperty() {
		return ctlProperty;
	}
	public void setCtlProperty(String ctlProperty) {
		this.ctlProperty = ctlProperty;
	}
	
	/**
	 * 将控件属性转换成map输出。
	 * @return
	 */
	public Map<String,String> getPropertyMap(){
		Map<String,String> map=new HashMap<String, String>();
		if(StringUtil.isEmpty(this.ctlProperty)){
			return map;
		}
		try{
			JSONObject jsonObject=JSONObject.fromObject(this.ctlProperty);
			Iterator it= jsonObject.keys();
			while(it.hasNext()){
				String key=it.next().toString();
				String value=jsonObject.getString(key);
				map.put(key, value);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return map;
	}
	
	
	public String getFieldTypeDisplay(){
		if("varchar".equals(this.fieldType)){
			return "字符串,varchar(" + this.charLen +")";
		}
		else if("number".equals(this.fieldType)){
			if(this.decimalLen==0){
				return "数字,number(" + this.intLen +")";
			}
			return  "数字,number(" + this.intLen +"," +this.decimalLen + ")";
		}
		else if("date".equals(this.fieldType)){
			return "日期,date";
		}
		else if("clob".equals(this.fieldType)){
			return "大文本";
		}
		return "字符串";
	}
	
	
	
	
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BpmFormField)) 
		{
			return false;
		}
		BpmFormField rhs = (BpmFormField) object;
		return new EqualsBuilder()
		.append(this.fieldName, rhs.fieldName)
		.append(this.fieldDesc, rhs.fieldDesc)
		.append(this.fieldType, rhs.fieldType)
		.append(this.charLen, rhs.charLen)
		.append(this.intLen, rhs.intLen)
		.append(this.decimalLen, rhs.decimalLen)
		.isEquals();
	}
	
	
	public Object clone()
	{
		BpmFormField obj=null;
		try{
			obj=(BpmFormField)super.clone();
		}catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.tableId) 
		.append(this.fieldName) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("fieldName", this.fieldName) 
		.toString();
	}
	
  

}