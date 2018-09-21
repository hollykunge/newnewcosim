package com.hotent.platform.model.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:查看表格业务数据的模板 Model对象
 * 开发公司:
 * 开发人员:heyifan
 * 创建时间:2012-05-22 14:58:08
 */
@XmlRootElement(name = "bpmTableTemplate")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmTableTemplate extends BaseModel
{	
	public static final int AUTHOR_TYPE_ALL = 1;	//可以查看所有数据
	public static final int AUTHOR_TYPE_SELF = 2;	//可以查看本人数据
	public static final int AUTHOR_TYPE_UNDER = 3;	//可以查看本人及下属数据
	public static final int AUTHOR_TYPE_ORG = 4;	//可以查看本组织的数据
	
	// 主键
	@XmlAttribute
	protected Long  id=0L;
	// 表ID
	@XmlAttribute
	protected Long  tableId;
	// 表 
	@XmlAttribute
	protected Long  categoryId;
	// 列表html模板
	@XmlAttribute
	protected String  htmlList;
	// 详情的html模板
	@XmlAttribute
	protected String  htmlDetail;
	/**
	 *  模板名
	 */
	@XmlAttribute
	protected String  templateName;
	/**
	 * 表名
	 */
	@XmlAttribute
	protected String tableName="";
	/**
	 * 分类名称。
	 */
	@XmlAttribute
	protected String categoryName="";
	/**
	 * 授权类型
	 */
	@XmlAttribute
	protected int authorType;
	/**
	 * 表单key。
	 */
	@XmlAttribute
	protected Long formKey=0L;
	
	/**
	 * 备注
	 */
	@XmlAttribute
	protected String memo="";
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 主键
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	
	public void setTableId(Long tableId) 
	{
		this.tableId = tableId;
	}
	/**
	 * 返回 表ID
	 * @return
	 */
	public Long getTableId() 
	{
		return this.tableId;
	}
	public void setCategoryId(Long categoryId) 
	{
		this.categoryId = categoryId;
	}
	/**
	 * 返回 表 
	 * @return
	 */
	public Long getCategoryId() 
	{
		return this.categoryId;
	}
	public void setHtmlList(String htmlList) 
	{
		this.htmlList = htmlList;
	}
	/**
	 * 返回 列表html模板
	 * @return
	 */
	public String getHtmlList() 
	{
		return this.htmlList;
	}
	public void setHtmlDetail(String htmlDetail) 
	{
		this.htmlDetail = htmlDetail;
	}
	/**
	 * 返回 详情的html模板
	 * @return
	 */
	public String getHtmlDetail() 
	{
		return this.htmlDetail;
	}
	public void setTemplateName(String templateName) 
	{
		this.templateName = templateName;
	}
	/**
	 * 返回 列表模板名
	 * @return
	 */
	public String getTemplateName() 
	{
		return this.templateName;
	}

   
   	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 返回授权类型
	 * @return
	 */
	public int getAuthorType() {
		return authorType;
	}
	/**
	 * 设置授权类型 1:所有人；2：被授权人；3：被授权人及其上级
	 * @param authorType
	 */
	public void setAuthorType(int authorType) {
		this.authorType = authorType;
	}
	
	public Long getFormKey() {
		return formKey;
	}
	public void setFormKey(Long formKey) {
		this.formKey = formKey;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BpmTableTemplate)) 
		{
			return false;
		}
		BpmTableTemplate rhs = (BpmTableTemplate) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.tableId, rhs.tableId)
		.append(this.categoryId, rhs.categoryId)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.tableId) 
		.append(this.categoryId) 
	
		.append(this.templateName) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("tableId", this.tableId) 
		.append("categoryId", this.categoryId) 
		
		.append("templateName", this.templateName) 
		.toString();
	}
   
  

}