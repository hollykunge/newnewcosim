package com.casic.cloud.model.config.businessDevchase;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 对象功能:cloud_business_devchase Model对象 开发公司:中国航天科工集团 开发人员:hollykunge
 * 创建时间:2013-05-03 17:26:48
 */
public class BusinessDevchase extends BaseModel {
	// id
	protected Long id;
	// name
	protected String name;
	// content
	protected String content;
	// start_time
	protected java.util.Date startTime;
	// end_time
	protected java.util.Date endTime;
	// image
	protected String image;
	// company_id
	protected Long companyId;
	// user_id
	protected Long userId;
	// operate_time
	protected java.util.Date operateTime;
	// company_name
	protected String companyName;
	// user_name
	protected String userName;
	// publish_state
	protected String publishState;
	// classid
	protected String classid;
	// yfhbzzyq
	protected String yfhbzzyq;
	// type
	protected String type;

	protected String publishInfo;
	// fwqy
	protected String fwqy;

	// dlsj
	protected java.util.Date dlsj;
	// hzfwqyzzyq
	protected String hzfwqyzzyq;

	// purnum
	protected Long purnum;

	// jhqyq
	protected String jhqyq;
	// gyszzyq
	protected String gyszzyq;
	// scgyyq
	protected String scgyyq;

	// scgm
	protected String scgm;

	// 国家
	protected String country;
	// 省
	protected String province;
	// 市
	protected String city;
	// 认证/资质
	protected String flaglogo;
	// gjsbyq
	protected String gjsbyq;
	// cpzlyq
	protected String cpzlyq;
	// hzjgqyzzyq
	protected String hzjgqyzzyq;

	// dlsjyq
	protected String dlsjyq;
	// yxhbzzyq
	protected String yxhbzzyq;
	// dlqy
	protected String dlqy;
	protected String dlqy2;
	protected String fwqy2;
	protected String industry;
	protected String industry2;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFlaglogo() {
		return flaglogo;
	}

	public void setFlaglogo(String flaglogo) {
		this.flaglogo = flaglogo;
	}

	public String getFwqy() {
		return fwqy;
	}

	public void setFwqy(String fwqy) {
		this.fwqy = fwqy;
	}

	public java.util.Date getDlsj() {
		return dlsj;
	}

	public void setDlsj(java.util.Date dlsj) {
		this.dlsj = dlsj;
	}

	public String getHzfwqyzzyq() {
		return hzfwqyzzyq;
	}

	public void setHzfwqyzzyq(String hzfwqyzzyq) {
		this.hzfwqyzzyq = hzfwqyzzyq;
	}

	public Long getPurnum() {
		return purnum;
	}

	public void setPurnum(Long purnum) {
		this.purnum = purnum;
	}

	public String getJhqyq() {
		return jhqyq;
	}

	public void setJhqyq(String jhqyq) {
		this.jhqyq = jhqyq;
	}

	public String getGyszzyq() {
		return gyszzyq;
	}

	public void setGyszzyq(String gyszzyq) {
		this.gyszzyq = gyszzyq;
	}

	public String getScgyyq() {
		return scgyyq;
	}

	public void setScgyyq(String scgyyq) {
		this.scgyyq = scgyyq;
	}

	public String getScgm() {
		return scgm;
	}

	public void setScgm(String scgm) {
		this.scgm = scgm;
	}

	public String getGjsbyq() {
		return gjsbyq;
	}

	public void setGjsbyq(String gjsbyq) {
		this.gjsbyq = gjsbyq;
	}

	public String getCpzlyq() {
		return cpzlyq;
	}

	public void setCpzlyq(String cpzlyq) {
		this.cpzlyq = cpzlyq;
	}

	public String getHzjgqyzzyq() {
		return hzjgqyzzyq;
	}

	public void setHzjgqyzzyq(String hzjgqyzzyq) {
		this.hzjgqyzzyq = hzjgqyzzyq;
	}

	public String getDlqy() {
		return dlqy;
	}

	public void setDlqy(String dlqy) {
		this.dlqy = dlqy;
	}

	public String getDlsjyq() {
		return dlsjyq;
	}

	public void setDlsjyq(String dlsjyq) {
		this.dlsjyq = dlsjyq;
	}

	public String getYxhbzzyq() {
		return yxhbzzyq;
	}

	public void setYxhbzzyq(String yxhbzzyq) {
		this.yxhbzzyq = yxhbzzyq;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 返回 id
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
	 * 返回 name
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 返回 content
	 * 
	 * @return
	 */
	public String getContent() {
		return this.content;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 返回 start_time
	 * 
	 * @return
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 返回 end_time
	 * 
	 * @return
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 返回 image
	 * 
	 * @return
	 */
	public String getImage() {
		return this.image;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * 返回 company_id
	 * 
	 * @return
	 */
	public Long getCompanyId() {
		return this.companyId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 返回 user_id
	 * 
	 * @return
	 */
	public Long getUserId() {
		return this.userId;
	}

	public void setOperateTime(java.util.Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * 返回 operate_time
	 * 
	 * @return
	 */
	public java.util.Date getOperateTime() {
		return this.operateTime;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 返回 company_name
	 * 
	 * @return
	 */
	public String getCompanyName() {
		return this.companyName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 返回 user_name
	 * 
	 * @return
	 */
	public String getUserName() {
		return this.userName;
	}

	public void setPublishState(String publishState) {
		this.publishState = publishState;
	}

	/**
	 * 返回 publish_state
	 * 
	 * @return
	 */
	public String getPublishState() {
		return this.publishState;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	/**
	 * 返回 classid
	 * 
	 * @return
	 */
	public String getClassid() {
		return this.classid;
	}

	public void setYfhbzzyq(String yfhbzzyq) {
		this.yfhbzzyq = yfhbzzyq;
	}

	/**
	 * 返回 yfhbzzyq
	 * 
	 * @return
	 */
	public String getYfhbzzyq() {
		return this.yfhbzzyq;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 返回 type
	 * 
	 * @return
	 */
	public String getType() {
		return this.type;
	}

	public String getPublishInfo() {
		return publishInfo;
	}

	public void setPublishInfo(String publishInfo) {
		this.publishInfo = publishInfo;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BusinessDevchase)) {
			return false;
		}
		BusinessDevchase rhs = (BusinessDevchase) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.name, rhs.name).append(this.content, rhs.content)
				.append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime)
				.append(this.image, rhs.image)
				.append(this.companyId, rhs.companyId)
				.append(this.userId, rhs.userId)
				.append(this.operateTime, rhs.operateTime)
				.append(this.companyName, rhs.companyName)
				.append(this.userName, rhs.userName)
				.append(this.publishState, rhs.publishState)
				.append(this.classid, rhs.classid)
				.append(this.yfhbzzyq, rhs.yfhbzzyq)
				.append(this.type, rhs.type)
				.append(this.publishInfo, rhs.publishInfo).isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.name).append(this.content).append(this.startTime)
				.append(this.endTime).append(this.image).append(this.companyId)
				.append(this.userId).append(this.operateTime)
				.append(this.companyName).append(this.userName)
				.append(this.publishState).append(this.classid)
				.append(this.yfhbzzyq).append(this.type)
				.append(this.publishInfo).toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("name", this.name).append("content", this.content)
				.append("startTime", this.startTime)
				.append("endTime", this.endTime).append("image", this.image)
				.append("companyId", this.companyId)
				.append("userId", this.userId)
				.append("operateTime", this.operateTime)
				.append("companyName", this.companyName)
				.append("userName", this.userName)
				.append("publishState", this.publishState)
				.append("classid", this.classid)
				.append("yfhbzzyq", this.yfhbzzyq).append("type", this.type)
				.append("publishInfo", this.publishInfo).toString();
	}

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

	public String getDlqy2() {
		return dlqy2;
	}

	public void setDlqy2(String dlqy2) {
		this.dlqy2 = dlqy2;
	}

}