package com.hotent.platform.model.bpm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.hotent.core.model.BaseModel;


/**
 * 对象功能:BPM_USER_CONDITION  Model对象
 * 开发者:云雀小组
 * 开发人员:ray
 * 创建时间:2012-12-31 15:18:11
 */
@XmlRootElement(name = "bpmUserCondition")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmUserCondition extends BaseModel implements Cloneable
{
	//等于
	public static final  short OPERATE_TYPE_EQUAL= 0; 
	//不等于
	public static final  short OPERATE_TYPE_UNEQUAL= 1; 
	//大于
	public static final  short OPERATE_TYPE_MORE_THAN = 2; 
	//大于等于
	public static final  short OPERATE_TYPE_MORE_EQUAL_THAN = 3; 
	//小于
	public static final  short OPERATE_TYPE_LESS_THAN = 4; 
	//小于
	public static final  short OPERATE_TYPE_LESS_EQUAL_THAN = 5; 
	//字符串的等于
	public static final  short OPERATE_TYPE_LIKE = 6;
	//字符串的等于
	public static final  short OPERATE_TYPE_UNLIKE = 7;
	
	//变量为数字类型
	public static final  String VARIABLE_TYPE_NUMBER = "number";
	//变量为日期类型
	public static final  String VARIABLE_TYPE_DATE = "date";
	//变量为日期类型
	public static final  String VARIABLE_TYPE_STRING= "varchar";
	// ID
	@XmlAttribute
	protected Long  id;
	// ACTDEFID
	@XmlAttribute
	protected String  actdefid;
	// NODEID
	@XmlAttribute
	protected String  nodeid;
	// 条件规则
	@XmlAttribute
	protected String  condition;
	// SN
	@XmlAttribute
	protected Long  sn;
	//表单变量Id
	@XmlAttribute
	protected Long  tableId;
	//规则名称
	@XmlAttribute
	protected String  conditionname;
	//规则显示字段
	@XmlAttribute
	protected String  conditionShow ;
	//setId
	@XmlAttribute
	protected Long  setId;

	
	public String getConditionShow() {
		return conditionShow;
	}
	public void setConditionShow(String conditionShow) {
		this.conditionShow = conditionShow;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 ID
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setActdefid(String actdefid) 
	{
		this.actdefid = actdefid;
	}
	/**
	 * 返回 ACTDEFID
	 * @return
	 */
	public String getActdefid() 
	{
		return this.actdefid;
	}
	public void setNodeid(String nodeid) 
	{
		this.nodeid = nodeid;
	}
	/**
	 * 返回 NODEID
	 * @return
	 */
	public String getNodeid() 
	{
		return this.nodeid;
	}
	public void setCondition(String condition) 
	{
		this.condition = condition;
	}

   
   	public String getCondition() {
		return condition;
	}
	public Long getSn() {
		return sn;
	}
	public void setSn(Long sn) {
		this.sn = sn;
	}
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public String getConditionname() {
		return conditionname;
	}
	public void setConditionname(String conditionname) {
		this.conditionname = conditionname;
	}
	public Long getSetId() {
		return setId;
	}
	public void setSetId(Long setId) {
		this.setId = setId;
	}
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BpmUserCondition)) 
		{
			return false;
		}
		BpmUserCondition rhs = (BpmUserCondition) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.actdefid, rhs.actdefid)
		.append(this.nodeid, rhs.nodeid)
		.append(this.condition, rhs.condition)
		.append(this.sn, rhs.sn)
		.append(this.tableId, rhs.tableId)
		.append(this.conditionname, rhs.conditionname)
		.append(this.conditionShow, rhs.conditionShow)
		.append(this.setId, rhs.setId)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.actdefid) 
		.append(this.nodeid) 
		.append(this.condition) 
		.append(this.sn) 
		.append(this.tableId)
		.append(this.conditionname)
			.append(this.conditionShow)
				.append(this.setId)
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("actdefid", this.actdefid) 
		.append("nodeid", this.nodeid) 
		.append("condition", this.condition) 
		.append("sn", this.sn) 
		.append("tableId",this.tableId)
		.append("conditionname",this.conditionname)
		.append("conditionShow",this.conditionShow)
		.append("setId",this.setId)
		.toString();
	}

	@Override
	public Object clone() {
		BpmUserCondition obj = null;
		try {
			obj = (BpmUserCondition) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
  

}