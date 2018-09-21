package com.casic.cloud.model.config.businessServechase;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_business_servechase Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-03 11:13:52
 */
public class BusinessServechase extends BaseModel
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
	// image
	protected String  image;
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
	// publish_state
	protected String  publishState;
	// fwqy
	protected String  fwqy;
	// fwqy
		protected String  fwqy2;


	// classid
	protected String  classid;
	// dlsj
	protected java.util.Date  dlsj;
	// hzfwqyzzyq
	protected String  hzfwqyzzyq;
	protected String  type;
	protected String publishInfo;
	protected String industry;
	protected String industry2;
	
	
	public String getFwqy2() {
		return fwqy2;
	}
	public void setFwqy2(String fwqy2) {
		this.fwqy2 = fwqy2;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getIndustry2() {
		return industry2;
	}
	public void setIndustry2(String industry2) {
		this.industry2 = industry2;
	}
	public String getPublishInfo() {
		return publishInfo;
	}
	public void setPublishInfo(String publishInfo) {
		this.publishInfo = publishInfo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public void setFwqy(String fwqy) 
	{
		this.fwqy = fwqy;
	}
	/**
	 * 返回 fwqy
	 * @return
	 */
	public String getFwqy() 
	{
		return this.fwqy;
	}
	public void setClassid(String classid) 
	{
		this.classid = classid;
	}
	/**
	 * 返回 classid
	 * @return
	 */
	public String getClassid() 
	{
		return this.classid;
	}
	public void setDlsj(java.util.Date dlsj) 
	{
		this.dlsj = dlsj;
	}
	/**
	 * 返回 dlsj
	 * @return
	 */
	public java.util.Date getDlsj() 
	{
		return this.dlsj;
	}
	public void setHzfwqyzzyq(String hzfwqyzzyq) 
	{
		this.hzfwqyzzyq = hzfwqyzzyq;
	}
	/**
	 * 返回 hzfwqyzzyq
	 * @return
	 */
	public String getHzfwqyzzyq() 
	{
		return this.hzfwqyzzyq;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BusinessServechase)) 
		{
			return false;
		}
		BusinessServechase rhs = (BusinessServechase) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.content, rhs.content)
		.append(this.startTime, rhs.startTime)
		.append(this.endTime, rhs.endTime)
		.append(this.image, rhs.image)
		.append(this.companyId, rhs.companyId)
		.append(this.userId, rhs.userId)
		.append(this.operateTime, rhs.operateTime)
		.append(this.companyName, rhs.companyName)
		.append(this.userName, rhs.userName)
		.append(this.publishState, rhs.publishState)
		.append(this.fwqy, rhs.fwqy)
		.append(this.classid, rhs.classid)
		.append(this.dlsj, rhs.dlsj)
		.append(this.hzfwqyzzyq, rhs.hzfwqyzzyq)
		.append(this.publishInfo, rhs.publishInfo)
		.append(this.industry, rhs.industry)
		.append(this.industry2, rhs.industry2)
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
		.append(this.image) 
		.append(this.companyId) 
		.append(this.userId) 
		.append(this.operateTime) 
		.append(this.companyName) 
		.append(this.userName) 
		.append(this.publishState) 
		.append(this.fwqy) 
		.append(this.classid) 
		.append(this.dlsj) 
		.append(this.hzfwqyzzyq)
		.append(this.publishInfo)
		.append(this.industry)
		.append(this.industry2)
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
		.append("image", this.image) 
		.append("companyId", this.companyId) 
		.append("userId", this.userId) 
		.append("operateTime", this.operateTime) 
		.append("companyName", this.companyName) 
		.append("userName", this.userName) 
		.append("publishState", this.publishState) 
		.append("fwqy", this.fwqy) 
		.append("classid", this.classid) 
		.append("dlsj", this.dlsj) 
		.append("hzfwqyzzyq", this.hzfwqyzzyq) 
		.append("publishInfo",this.publishInfo)
		.append("industry",this.industry)
		.append("industry2",this.industry2)
		.toString();
	}
   
  

}