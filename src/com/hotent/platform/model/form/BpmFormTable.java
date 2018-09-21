package com.hotent.platform.model.form;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.dom4j.Document;
import org.dom4j.Element;

import com.hotent.core.model.BaseModel;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.StringUtil;
/**
 * 对象功能:自定义表 Model对象
 * 开发公司:
 * 开发人员:xwy
 * 创建时间:2011-12-05 11:42:29
 */
@XmlRootElement(name="table")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmFormTable extends BaseModel
{
	public static String ParElmName = "table";
	
	public static String SubElmName = "subTable";
	
	// tableId
	@XmlAttribute
	protected Long tableId=0L;
	// 表名
	@XmlAttribute
	protected String tableName = "";
	// 描述
	@XmlAttribute
	protected String tableDesc = "";
	// 是否主表 1-是 0-否
	@XmlAttribute
	protected Short isMain = 1;
	// 所属主表，该字段仅针对从表
	protected Long mainTableId=0L;
	// 是否已发布
	@XmlAttribute
	protected Short isPublished = 0;
	// 发布人
	protected String publishedBy = "";
	// 发布时间
	protected java.util.Date publishTime;
	//是否外部数据源
	protected int isExternal=0;
	//数据源别名
	protected String dsAlias="";
	//数据源名称
	protected String dsName="";
	//关键字段
	protected String relation="";
	//键类型
	protected Short keyType=0;
	//键值
	protected String keyValue="";
	//主键字段
	protected String pkField="";
	//列表模板
	protected String listTemplate="";
	//明细模板
	protected String detailTemplate="";
	
	protected Integer hasForm=0; 
	//是否有表单产生(0,表设计,1,由表单生成)
	//默认值为0
	protected Integer genByForm =0; 
	
	protected Set<String> hashSet=new HashSet<String>();
	//字段信息
	protected List<BpmFormField> fieldList=new ArrayList<BpmFormField>();
	//子表列表
	protected List<BpmFormTable> subTableList=new ArrayList<BpmFormTable>();
	
	protected Long parentId;
	
	protected Short isRoot;
	
	protected Map<String,String> variable=new HashMap<String, String>();
	
	
	
	public void setTableId(Long tableId) 
	{
		this.tableId = tableId;
	}
	/**
	 * 返回 tableId
	 * @return
	 */
	public Long getTableId() 
	{
		return tableId;
	}

	public void setTableName(String tableName) 
	{
		this.tableName = tableName;
	}
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Short getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(Short isRoot) {
		this.isRoot = isRoot;
	}
	
	public Map<String, String> getVariable() {
		return variable;
	}
	public void setVariable(Map<String, String> variable) {
		this.variable = variable;
	}
	/**
	 * 返回 表名
	 * @return
	 */
	public String getTableName() 
	{
		return tableName;
	}

	public void setTableDesc(String tableDesc) 
	{
		this.tableDesc = tableDesc;
	}
	/**
	 * 返回 描述
	 * @return
	 */
	public String getTableDesc() 
	{
		return tableDesc;
	}

	public void setIsMain(Short isMain) 
	{
		this.isMain = isMain;
	}
	/**
	 * 返回 是否主表 1-是 0-否
	 * @return
	 */
	public Short getIsMain() 
	{
		return isMain;
	}

	public void setMainTableId(Long mainTableId) 
	{
		this.mainTableId = mainTableId;
	}
	/**
	 * 返回 所属主表，该字段仅针对从表
	 * @return
	 */
	public Long getMainTableId() 
	{
		return mainTableId;
	}

	
	public void setIsPublished(Short isPublished) 
	{
		this.isPublished = isPublished;
	}
	/**
	 * 返回 是否已发布
	 * @return
	 */
	public Short getIsPublished() 
	{
		return isPublished;
	}

	public void setPublishedBy(String publishedBy) 
	{
		this.publishedBy = publishedBy;
	}
	/**
	 * 返回 发布人
	 * @return
	 */
	public String getPublishedBy() 
	{
		return publishedBy;
	}

	public void setPublishTime(java.util.Date publishTime) 
	{
		this.publishTime = publishTime;
	}
	/**
	 * 返回 发布时间
	 * @return
	 */
	public java.util.Date getPublishTime() 
	{
		return publishTime;
	}
	
	/**
	 * 是否外部表
	 * 0，本地表
	 * 1，外地表
	 * @return
	 */
	public int getIsExternal() {
		return isExternal;
	}
	public void setIsExternal(int isExternal) {
		this.isExternal = isExternal;
	}
	
	/**
	 * 数据源别名
	 * @return
	 */
	public String getDsAlias() {
		return dsAlias;
	}
	public void setDsAlias(String dsAlias) {
		this.dsAlias = dsAlias;
	}
	
	/**
	 * 数据源名称
	 * @return
	 */
	public String getDsName() {
		return dsName;
	}
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	
	/**
	 * 取得表间关联关系。
	 * @return
	 */
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	/**
	 * 取得键类型。
	 * @return
	 */
	public Short getKeyType() {
		return keyType;
	}
	public void setKeyType(Short keyType) {
		this.keyType = keyType;
	}
	
	/**
	 * 键值。
	 * @return
	 */
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	
	/**
	 * 主键字段
	 * @return
	 */
	public String getPkField() {
		return pkField;
	}
	public void setPkField(String pkField) {
		this.pkField = pkField;
	}
	
	
	public String getListTemplate()
	{
		return listTemplate;
	}
	public void setListTemplate(String listTemplate)
	{
		this.listTemplate = listTemplate;
	}
	public String getDetailTemplate()
	{
		return detailTemplate;
	}
	public void setDetailTemplate(String detailTemplate)
	{
		this.detailTemplate = detailTemplate;
	}
	
	
	public Integer getHasForm() {
		return hasForm;
	}
	public void setHasForm(Integer hasForm) {
		this.hasForm = hasForm;
	}
	/**
	 * 取得关联关系。
	 * @return
	 */
	public TableRelation getTableRelation()
	{
		if(this.isExternal==0) return null;
		if(StringUtil.isEmpty(relation)) return null;
		return BpmFormTable.getRelationsByXml(this.relation);
	}
	
	/**
	 * 根据xml取得关联关系。
	 * xml格式为：
	 * <pre>
	 * &lt;relation pk='主键表'>
	 * 		&lt;table name='外键表' fk="外键字段" />
	 * 		&lt;!--可以关联多个表-->
	 * &lt;/relation>
	 * </pre>
	 * @param relationXml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static TableRelation getRelationsByXml(String relationXml){
		if(StringUtil.isEmpty(relationXml)) return null;
		Document dom=Dom4jUtil.loadXml(relationXml);
		Element root=dom.getRootElement();
		String pk=root.attributeValue("pk");
		TableRelation tableRelation=new TableRelation(pk);
		Iterator<Element> tbIt= root.elementIterator();
		while(tbIt.hasNext()){
			Element  elTb=tbIt.next();
			String tbName=elTb.attributeValue("name");
			String fk=elTb.attributeValue("fk");
			tableRelation.addRelation(tbName, fk);
		}
		return tableRelation;
	}
	
	/**
	 * 修改xml字段。
	 * @param relationXml	关联的XML。
	 * @param tbName		表名。
	 * @return
	 */
	public static String removeTable(String relationXml,String tbName){
		Document dom=Dom4jUtil.loadXml(relationXml);
		Element root=dom.getRootElement();
		List<Element> list= root.elements();
		for(Element el:list){
			String name=el.attributeValue("name");
			if(name.equals(tbName)){
				root.remove(el);
				break;
			}
		}
		list= root.elements();
		if(list.size()==0)
			return "";
		return root.asXML();
	}
	
	/**
	 * 添加字段。
	 * <pre>
	 * 	1.做重复新检测。
	 *  2.重复字段返回失败。
	 * </pre>
	 * @param field
	 */
	public boolean addField(BpmFormField field){
		String fieldName=field.getFieldName().toLowerCase();
		short controlType=field.getControlType();
	
		if(hashSet.contains(fieldName)) {
			//如果字段类型为复选框或者单选按钮的时候可以重复。
			if(controlType==13 ||controlType==14){
				return true;
			}
			return false;
		} ;
		hashSet.add(fieldName);
		this.fieldList.add(field);	
		return true;
	}
	
	
   	public List<BpmFormField> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<BpmFormField> fieldList) {
		this.fieldList = fieldList;
	}
	public List<BpmFormTable> getSubTableList() {
		return subTableList;
	}
	public void setSubTableList(List<BpmFormTable> subTableList) {
		this.subTableList = subTableList;
	}
	public Integer getGenByForm() {
		return genByForm;
	}
	public void setGenByForm(Integer genByForm) {
		this.genByForm = genByForm;
	}
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BpmFormTable)) 
		{
			return false;
		}
		BpmFormTable rhs = (BpmFormTable) object;
		return new EqualsBuilder()
		.append(this.tableId, rhs.tableId)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.tableId) 
		.append(this.tableName) 
		.append(this.tableDesc) 
		.append(this.isMain) 
		.append(this.mainTableId) 
	
		.append(this.isPublished) 
		.append(this.publishedBy) 
		.append(this.publishTime) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("tableId", this.tableId) 
		.append("tableName", this.tableName) 
		.append("tableDesc", this.tableDesc) 
		.append("isMain", this.isMain) 
		.append("mainTableId", this.mainTableId) 
		
		.append("isPublished", this.isPublished) 
		.append("publishedBy", this.publishedBy) 
		.append("publishTime", this.publishTime) 
		.toString();
	}
   
  

}