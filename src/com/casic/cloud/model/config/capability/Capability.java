package com.casic.cloud.model.config.capability;

import java.util.ArrayList;
import java.util.List;

import com.casic.cloud.model.config.info.Info;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_capability Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:23:23
 */
public class Capability extends BaseModel
{
	// id
	protected Long  id;
	// 能力名称
	protected String  name;
	// 能力分类
	protected Long  typeId;
	// 层级
	protected String  currentLevl;
	// 所属企业
	protected Long  entId;
	// 能力详情
	protected String  info;
	// 能力图片
	protected String  pic;
	// 发布人
	protected String  publisher;
	// 发布时间
	protected java.util.Date  publishDate;
	// 适用范围
	protected String  useScope;
	// 发布状态
	protected String  publishState;
	// 关键词
	protected String  prokey;
	// type_name
	protected String  typeName;
	// ent_name
	protected String  entName;
	protected Info entinfo;
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
	 * 返回 能力名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setTypeId(Long typeId) 
	{
		this.typeId = typeId;
	}
	/**
	 * 返回 能力分类
	 * @return
	 */
	public Long getTypeId() 
	{
		return this.typeId;
	}
	public void setCurrentLevl(String currentLevl) 
	{
		this.currentLevl = currentLevl;
	}
	/**
	 * 返回 层级
	 * @return
	 */
	public String getCurrentLevl() 
	{
		return this.currentLevl;
	}
	public void setEntId(Long entId) 
	{
		this.entId = entId;
	}
	/**
	 * 返回 所属企业
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
	 * 返回 能力详情
	 * @return
	 */
	public String getInfo() 
	{
		return this.info;
	}
	public void setPic(String pic) 
	{
		this.pic = pic;
	}
	/**
	 * 返回 能力图片
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
	 * 返回 发布人
	 * @return
	 */
	public String getPublisher() 
	{
		return this.publisher;
	}
	public void setPublishDate(java.util.Date publishDate) 
	{
		this.publishDate = publishDate;
	}
	/**
	 * 返回 发布时间
	 * @return
	 */
	public java.util.Date getPublishDate() 
	{
		return this.publishDate;
	}
	public void setUseScope(String useScope) 
	{
		this.useScope = useScope;
	}
	/**
	 * 返回 适用范围
	 * @return
	 */
	public String getUseScope() 
	{
		return this.useScope;
	}
	public void setPublishState(String publishState) 
	{
		this.publishState = publishState;
	}
	/**
	 * 返回 发布状态
	 * @return
	 */
	public String getPublishState() 
	{
		return this.publishState;
	}
	public void setProkey(String prokey) 
	{
		this.prokey = prokey;
	}
	/**
	 * 返回 关键词
	 * @return
	 */
	public String getProkey() 
	{
		return this.prokey;
	}
	public void setTypeName(String typeName) 
	{
		this.typeName = typeName;
	}
	/**
	 * 返回 type_name
	 * @return
	 */
	public String getTypeName() 
	{
		return this.typeName;
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
		if (!(object instanceof Capability)) 
		{
			return false;
		}
		Capability rhs = (Capability) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.typeId, rhs.typeId)
		.append(this.currentLevl, rhs.currentLevl)
		.append(this.entId, rhs.entId)
		.append(this.info, rhs.info)
		.append(this.pic, rhs.pic)
		.append(this.publisher, rhs.publisher)
		.append(this.publishDate, rhs.publishDate)
		.append(this.useScope, rhs.useScope)
		.append(this.publishState, rhs.publishState)
		.append(this.prokey, rhs.prokey)
		.append(this.typeName, rhs.typeName)
		.append(this.entName, rhs.entName)
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
		.append(this.typeId) 
		.append(this.currentLevl) 
		.append(this.entId) 
		.append(this.info) 
		.append(this.pic) 
		.append(this.publisher) 
		.append(this.publishDate) 
		.append(this.useScope) 
		.append(this.publishState) 
		.append(this.prokey) 
		.append(this.typeName) 
		.append(this.entName) 
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
		.append("typeId", this.typeId) 
		.append("currentLevl", this.currentLevl) 
		.append("entId", this.entId) 
		.append("info", this.info) 
		.append("pic", this.pic) 
		.append("publisher", this.publisher) 
		.append("publishDate", this.publishDate) 
		.append("useScope", this.useScope) 
		.append("publishState", this.publishState) 
		.append("prokey", this.prokey) 
		.append("typeName", this.typeName) 
		.append("entName", this.entName) 
		.toString();
	}
	public Info getEntinfo() {
		return entinfo;
	}
	public void setEntinfo(Info entinfo) {
		this.entinfo = entinfo;
	}
   
  

}