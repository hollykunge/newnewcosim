package com.hotent.platform.model.system;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:SYS_ORG_INFO Model对象
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2013-04-11 23:51:26
 */


public class SysOrgInfo extends BaseModel
{
	// 组织ID
	protected Long  sysOrgInfoId;
	// EMAIL
	protected String  email;
	// NAME
	protected String  name;
	// INDUSTRY
	protected String  industry;
	protected String  industry2;
	// SCALE
	protected String  scale;
	// ADDRESS
	protected String  address;
	// POSTCODE
	protected String  postcode;
	// CONNECTER
	protected String  connecter;
	// TEL
	protected String  tel;
	// FAX
	protected String  fax;
	// homephone
	protected String  homephone;
	// country
	protected String  country;
	// HOMEPHONE
	protected String  flaglogo;
	// HOMEPHONE
	protected int  state;
	// 注册时间
	protected Date registtime;
	//套餐设置
	private Long setid;
	//推荐企业
	private String recommendedEnt;
		
	public void setSysOrgInfoId(Long sysOrgInfoId) 
	{
		this.sysOrgInfoId = sysOrgInfoId;
	}
	/**
	 * 返回 组织ID
	 * @return
	 */
	public Long getSysOrgInfoId() 
	{
		return this.sysOrgInfoId;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}
	/**
	 * 返回 EMAIL
	 * @return
	 */
	public String getEmail() 
	{
		return this.email;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 NAME
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setIndustry(String industry) 
	{
		this.industry = industry;
	}
	/**
	 * 返回 INDUSTRY
	 * @return
	 */
	public String getIndustry() 
	{
		return this.industry;
	}
	/**
	 * 返回 INDUSTRY2
	 * @return
	 */
	public String getIndustry2() {
		return industry2;
	}
	public void setIndustry2(String industry2) {
		this.industry2 = industry2;
	}
	public void setScale(String scale) 
	{
		this.scale = scale;
	}
	/**
	 * 返回 SCALE
	 * @return
	 */
	public String getScale() 
	{
		return this.scale;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}
	/**
	 * 返回 ADDRESS
	 * @return
	 */
	public String getAddress() 
	{
		return this.address;
	}
	public void setPostcode(String postcode) 
	{
		this.postcode = postcode;
	}
	/**
	 * 返回 POSTCODE
	 * @return
	 */
	public String getPostcode() 
	{
		return this.postcode;
	}
	public void setConnecter(String connecter) 
	{
		this.connecter = connecter;
	}
	/**
	 * 返回 CONNECTER
	 * @return
	 */
	public String getConnecter() 
	{
		return this.connecter;
	}
	public void setTel(String tel) 
	{
		this.tel = tel;
	}
	/**
	 * 返回 TEL
	 * @return
	 */
	public String getTel() 
	{
		return this.tel;
	}
	public void setFax(String fax) 
	{
		this.fax = fax;
	}
	/**
	 * 返回 FAX
	 * @return
	 */
	public String getFax() 
	{
		return this.fax;
	}
	public void setHomephone(String homephone) 
	{
		this.homephone = homephone;
	}
	/**
	 * 返回 HOMEPHONE
	 * @return
	 */
	public String getHomephone() 
	{
		return this.homephone;
	}
	/**
	 * 返回 国家
	 * @return
	 */
   	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * 返回 国旗
	 * @return
	 */
	public String getFlaglogo() {
		return flaglogo;
	}
	public void setFlaglogo(String flaglogo) {
		this.flaglogo = flaglogo;
	}
	/**
	 * 注册时间
	 * @return
	 */
	public Date getRegisttime() {
		return registtime;
	}
	public void setRegisttime(Date registtime) {
		this.registtime = registtime;
	}
	/**
	 * 返回 状态
	 * @return
	 */
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public Long getSetid() {
		return setid;
	}
	public void setSetid(Long setid) {
		this.setid = setid;
	}
	public String getRecommendedEnt() {
		return recommendedEnt;
	}
	public void setRecommendedEnt(String recommendedEnt) {
		this.recommendedEnt = recommendedEnt;
	}
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof SysOrgInfo)) 
		{
			return false;
		}
		SysOrgInfo rhs = (SysOrgInfo) object;
		return new EqualsBuilder()
		.append(this.sysOrgInfoId, rhs.sysOrgInfoId)
		.append(this.email, rhs.email)
		.append(this.name, rhs.name)
		.append(this.industry, rhs.industry)
		.append(this.scale, rhs.scale)
		.append(this.address, rhs.address)
		.append(this.postcode, rhs.postcode)
		.append(this.connecter, rhs.connecter)
		.append(this.tel, rhs.tel)
		.append(this.fax, rhs.fax)
		.append(this.homephone, rhs.homephone)
		.append(this.country, rhs.country)
		.append(this.flaglogo, rhs.flaglogo)
		.append(this.state, rhs.state)		
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.sysOrgInfoId) 
		.append(this.email) 
		.append(this.name) 
		.append(this.industry) 
		.append(this.scale) 
		.append(this.address) 
		.append(this.postcode) 
		.append(this.connecter) 
		.append(this.tel) 
		.append(this.fax) 
		.append(this.homephone) 
		.append(this.country) 
		.append(this.flaglogo)
		.append(this.state)
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("sysOrgInfoId", this.sysOrgInfoId) 
		.append("email", this.email) 
		.append("name", this.name) 
		.append("industry", this.industry) 
		.append("scale", this.scale) 
		.append("address", this.address) 
		.append("postcode", this.postcode) 
		.append("connecter", this.connecter) 
		.append("tel", this.tel) 
		.append("fax", this.fax) 
		.append("homephone", this.homephone)
		.append("state", this.state)
		.toString();
	}
   
  

}