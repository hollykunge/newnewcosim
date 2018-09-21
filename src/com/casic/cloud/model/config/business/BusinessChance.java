package com.casic.cloud.model.config.business;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.casic.cloud.model.config.info.Info;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:cloud_business_chance Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-18 18:59:34
 */
public class BusinessChance extends BaseModel
{
	// id
	protected Long  id;
	// name
	protected String  name;
	// content
	protected String  content;
	// start_time
	protected java.util.Date  startTime;
	// end_time
	protected java.util.Date  endTime;
	// type
	protected String  type;
	// image
	protected String  image;
	// properties
	protected String  properties;
	// company_id
	protected Long  companyId;
	// user_id
	protected Long  userId;
	// operate_time
	protected java.util.Date  operateTime;
	// company_name
	protected String  companyName;
	// user_name
	protected String  userName;
	protected Info info;
	// publish_state
	protected String  publishState;
	
	public String getPublishState() 
	{
		return this.publishState;
	}
	public void setPublishState(String publishState) {
		this.publishState = publishState;
	}
	
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
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
	 * 返回 name
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	/**
	 * 返回 content
	 * @return
	 */
	public String getContent() 
	{
		return this.content;
	}
	public void setStartTime(java.util.Date startTime) 
	{
		this.startTime = startTime;
	}
	/**
	 * 返回 start_time
	 * @return
	 */
	public java.util.Date getStartTime() 
	{
		return this.startTime;
	}
	public void setEndTime(java.util.Date endTime) 
	{
		this.endTime = endTime;
	}
	/**
	 * 返回 end_time
	 * @return
	 */
	public java.util.Date getEndTime() 
	{
		return this.endTime;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 type
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setImage(String image) 
	{
		this.image = image;
	}
	/**
	 * 返回 image
	 * @return
	 */
	public String getImage() 
	{
		return this.image;
	}
	public void setProperties(String properties) 
	{
		this.properties = properties;
	}
	/**
	 * 返回 properties
	 * @return
	 */
	public String getProperties() 
	{
		return this.properties;
	}
	public void setCompanyId(Long companyId) 
	{
		this.companyId = companyId;
	}
	/**
	 * 返回 company_id
	 * @return
	 */
	public Long getCompanyId() 
	{
		return this.companyId;
	}
	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}
	/**
	 * 返回 user_id
	 * @return
	 */
	public Long getUserId() 
	{
		return this.userId;
	}
	public void setOperateTime(java.util.Date operateTime) 
	{
		this.operateTime = operateTime;
	}
	/**
	 * 返回 operate_time
	 * @return
	 */
	public java.util.Date getOperateTime() 
	{
		return this.operateTime;
	}
	public void setCompanyName(String companyName) 
	{
		this.companyName = companyName;
	}
	/**
	 * 返回 company_name
	 * @return
	 */
	public String getCompanyName() 
	{
		return this.companyName;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}
	/**
	 * 返回 user_name
	 * @return
	 */
	public String getUserName() 
	{
		return this.userName;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BusinessChance)) 
		{
			return false;
		}
		BusinessChance rhs = (BusinessChance) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.content, rhs.content)
		.append(this.startTime, rhs.startTime)
		.append(this.endTime, rhs.endTime)
		.append(this.type, rhs.type)
		.append(this.image, rhs.image)
		.append(this.properties, rhs.properties)
		.append(this.companyId, rhs.companyId)
		.append(this.userId, rhs.userId)
		.append(this.operateTime, rhs.operateTime)
		.append(this.companyName, rhs.companyName)
		.append(this.userName, rhs.userName)
		.append(this.publishState, rhs.publishState) 
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
		.append(this.content) 
		.append(this.startTime) 
		.append(this.endTime) 
		.append(this.type) 
		.append(this.image) 
		.append(this.properties) 
		.append(this.companyId) 
		.append(this.userId) 
		.append(this.operateTime) 
		.append(this.companyName) 
		.append(this.userName) 
		.append(this.publishState) 
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
		.append("content", this.content) 
		.append("startTime", this.startTime) 
		.append("endTime", this.endTime) 
		.append("type", this.type) 
		.append("image", this.image) 
		.append("properties", this.properties) 
		.append("companyId", this.companyId) 
		.append("userId", this.userId) 
		.append("operateTime", this.operateTime) 
		.append("companyName", this.companyName) 
		.append("userName", this.userName) 
		.append("publishState", this.publishState) 
		.toString();
	}
   
  

}