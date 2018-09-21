package com.casic.cloud.model.config.material;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_material Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-18 17:46:42
 */
public class Material extends BaseModel
{
	// id
	protected Long  id;
	// 物品名称
	protected String  name;
	// levl_type
	protected Long  levlType;
	// levl_seq
	protected Long  levlSeq;
	// ent_id
	protected Long  entId;
	// info
	protected String  info;
	// isinner
	protected Long  isinner;
	// pic
	protected String  pic;
	// publisher
	protected String  publisher;
	// publishdate
	protected java.util.Date  publishdate;
	// 单位
	protected String  unit;
	// 价格
	protected Long  price;
	// minsale
	protected Long  minsale;	
	// maxsale
	protected Long  maxsale;
	// 型号规格
	protected String  model;
	// catalog_id
	protected Long  catalogId;
	// code
	protected String  code;
	// industry_code
	protected String  industryCode;
	// industry_file
	protected String  industryFile;
	// publish_state
	protected String  publishState;
	// type_class
	protected String  typeClass;
	// ent_name
	protected String  entName;
	
	protected String catalogName;
	
	protected Long  parentId;
	protected String parentName;
	protected String remark1;
	protected String remark2;
	protected String remark3;
	protected String remark4;
	

	protected String type;  // 值为1时代表本企业为供应商，值为0时代表本企业为经销商
	
	protected int sharedRepository; //是否共享库存
	
	
	public int getSharedRepository() {
		return sharedRepository;
	}
	public void setSharedRepository(int sharedRepository) {
		this.sharedRepository = sharedRepository;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 id
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 物品名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setLevlType(Long levlType) 
	{
		this.levlType = levlType;
	}
	/**
	 * 型号规格
	 * @return
	 */
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * 返回 levl_type
	 * @return
	 */
	public Long getLevlType() 
	{
		return this.levlType;
	}
	public void setLevlSeq(Long levlSeq) 
	{
		this.levlSeq = levlSeq;
	}
	/**
	 * 返回 levl_seq
	 * @return
	 */
	public Long getLevlSeq() 
	{
		return this.levlSeq;
	}
	public void setEntId(Long entId) 
	{
		this.entId = entId;
	}
	/**
	 * 返回 ent_id
	 * @return
	 */
	public Long getEntId() 
	{
		return this.entId;
	}
	public void setInfo(String info) 
	{
		this.info = info;
	}
	/**
	 * 返回 info
	 * @return
	 */
	public String getInfo() 
	{
		return this.info;
	}
	public void setIsinner(Long isinner) 
	{
		this.isinner = isinner;
	}
	/**
	 * 返回 isinner
	 * @return
	 */
	public Long getIsinner() 
	{
		return this.isinner;
	}
	public void setPic(String pic) 
	{
		this.pic = pic;
	}
	/**
	 * 返回 pic
	 * @return
	 */
	public String getPic() 
	{
		return this.pic;
	}
	public void setPublisher(String publisher) 
	{
		this.publisher = publisher;
	}
	/**
	 * 返回 publisher
	 * @return
	 */
	public String getPublisher() 
	{
		return this.publisher;
	}
	public void setPublishdate(java.util.Date publishdate) 
	{
		this.publishdate = publishdate;
	}
	/**
	 * 返回 publishdate
	 * @return
	 */
	public java.util.Date getPublishdate() 
	{
		return this.publishdate;
	}
	public void setUnit(String unit) 
	{
		this.unit = unit;
	}
	/**
	 * 返回 单位
	 * @return
	 */
	public String getUnit() 
	{
		return this.unit;
	}
	public void setPrice(Long price) 
	{
		this.price = price;
	}
	/**
	 * 返回 价格
	 * @return
	 */
	public Long getPrice() 
	{
		return this.price;
	}
	public void setMinsale(Long minsale) 
	{
		this.minsale = minsale;
	}
	/**
	 * 返回 minsale
	 * @return
	 */
	public Long getMinsale() 
	{
		return this.minsale;
	}
	public void setMaxsale(Long maxsale) 
	{
		this.maxsale = maxsale;
	}
	/**
	 * 返回 maxsale
	 * @return
	 */
	public Long getMaxsale() 
	{
		return this.maxsale;
	}
	public void setCatalogId(Long catalogId) 
	{
		this.catalogId = catalogId;
	}
	/**
	 * 返回 catalog_id
	 * @return
	 */
	public Long getCatalogId() 
	{
		return this.catalogId;
	}
	public void setCode(String code) 
	{
		this.code = code;
	}
	/**
	 * 返回 code
	 * @return
	 */
	public String getCode() 
	{
		return this.code;
	}
	public void setIndustryCode(String industryCode) 
	{
		this.industryCode = industryCode;
	}
	/**
	 * 返回 industry_code
	 * @return
	 */
	public String getIndustryCode() 
	{
		return this.industryCode;
	}
	public void setIndustryFile(String industryFile) 
	{
		this.industryFile = industryFile;
	}
	/**
	 * 返回 industry_file
	 * @return
	 */
	public String getIndustryFile() 
	{
		return this.industryFile;
	}
	public void setPublishState(String publishState) 
	{
		this.publishState = publishState;
	}
	/**
	 * 返回 publish_state
	 * @return
	 */
	public String getPublishState() 
	{
		return this.publishState;
	}
	public void setTypeClass(String typeClass) 
	{
		this.typeClass = typeClass;
	}
	/**
	 * 返回 type_class
	 * @return
	 */
	public String getTypeClass() 
	{
		return this.typeClass;
	}
	public void setEntName(String entName) 
	{
		this.entName = entName;
	}
	/**
	 * 返回 ent_name
	 * @return
	 */
	public String getEntName() 
	{
		return this.entName;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Material)) 
		{
			return false;
		}
		Material rhs = (Material) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.levlType, rhs.levlType)
		.append(this.levlSeq, rhs.levlSeq)
		.append(this.entId, rhs.entId)
		.append(this.info, rhs.info)
		.append(this.isinner, rhs.isinner)
		.append(this.pic, rhs.pic)
		.append(this.publisher, rhs.publisher)
		.append(this.publishdate, rhs.publishdate)
		.append(this.unit, rhs.unit)
		.append(this.price, rhs.price)
		.append(this.minsale, rhs.minsale)
		.append(this.maxsale, rhs.maxsale)
		.append(this.catalogId, rhs.catalogId)
		.append(this.code, rhs.code)
		.append(this.industryCode, rhs.industryCode)
		.append(this.industryFile, rhs.industryFile)
		.append(this.publishState, rhs.publishState)
		.append(this.typeClass, rhs.typeClass)
		.append(this.entName, rhs.entName)
		.append(this.remark1, rhs.remark1)
		.append(this.remark2, rhs.remark2)
		.append(this.remark3, rhs.remark3)
		.append(this.remark4, rhs.remark4)
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
		.append(this.levlType) 
		.append(this.levlSeq) 
		.append(this.entId) 
		.append(this.info) 
		.append(this.isinner) 
		.append(this.pic) 
		.append(this.publisher) 
		.append(this.publishdate) 
		.append(this.unit) 
		.append(this.price) 
		.append(this.minsale) 
		.append(this.maxsale) 
		.append(this.catalogId) 
		.append(this.code) 
		.append(this.industryCode) 
		.append(this.industryFile) 
		.append(this.publishState) 
		.append(this.typeClass) 
		.append(this.entName)
		.append(this.remark1)
		.append(this.remark2)
		.append(this.remark3)
		.append(this.remark4)
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
		.append("levlType", this.levlType) 
		.append("levlSeq", this.levlSeq) 
		.append("entId", this.entId) 
		.append("info", this.info) 
		.append("isinner", this.isinner) 
		.append("pic", this.pic) 
		.append("publisher", this.publisher) 
		.append("publishdate", this.publishdate) 
		.append("unit", this.unit) 
		.append("price", this.price) 
		.append("minsale", this.minsale) 
		.append("maxsale", this.maxsale) 
		.append("catalogId", this.catalogId) 
		.append("code", this.code) 
		.append("industryCode", this.industryCode) 
		.append("industryFile", this.industryFile) 
		.append("publishState", this.publishState) 
		.append("typeClass", this.typeClass) 
		.append("entName", this.entName) 
		.append("remark1",this.remark1)
		.append("remark2",this.remark2)
		.append("remark3",this.remark3)
		.append("remark4",this.remark4)
		.toString();
	}
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
   
  

}