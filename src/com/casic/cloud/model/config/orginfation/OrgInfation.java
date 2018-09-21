package com.casic.cloud.model.config.orginfation;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:sys_org_info Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 18:08:13
 */
public class OrgInfation extends BaseModel
{
	// SYS_ORG_INFO_ID
	protected Long  sysOrgInfoId;
	// EMAIL
	protected String  email;
	// NAME
	protected String  name;
	// INDUSTRY
	protected String  industry;
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
	// HOMEPHONE
	protected String  homephone;
	// LOGO
	protected String  logo;
	// INDUSTRY2
	protected String  industry2;
	// MEMBER
	protected String  member;
	// INFO
	protected String  info;
	// COUNTRY
	protected String  country;
	// PROVINCE
	protected String  province;
	// CITY
	protected String  city;
	// IS_PUBLIC
	protected Long  isPublic;
	// CODE
	protected String  code;
	// TYPE
	protected String  type;
	// MODEL
	protected String  model;
	// PRODUCT
	protected String  product;
	// WEBSITE
	protected String  website;
	// REGISTERTIME
	protected java.util.Date  registertime;
	public void setSysOrgInfoId(Long sysOrgInfoId) 
	{
		this.sysOrgInfoId = sysOrgInfoId;
	}
	/**
	 * 返回 SYS_ORG_INFO_ID
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
	public void setLogo(String logo) 
	{
		this.logo = logo;
	}
	/**
	 * 返回 LOGO
	 * @return
	 */
	public String getLogo() 
	{
		return this.logo;
	}
	public void setIndustry2(String industry2) 
	{
		this.industry2 = industry2;
	}
	/**
	 * 返回 INDUSTRY2
	 * @return
	 */
	public String getIndustry2() 
	{
		return this.industry2;
	}
	public void setMember(String member) 
	{
		this.member = member;
	}
	/**
	 * 返回 MEMBER
	 * @return
	 */
	public String getMember() 
	{
		return this.member;
	}
	public void setInfo(String info) 
	{
		this.info = info;
	}
	/**
	 * 返回 INFO
	 * @return
	 */
	public String getInfo() 
	{
		return this.info;
	}
	public void setCountry(String country) 
	{
		this.country = country;
	}
	/**
	 * 返回 COUNTRY
	 * @return
	 */
	public String getCountry() 
	{
		return this.country;
	}
	public void setProvince(String province) 
	{
		this.province = province;
	}
	/**
	 * 返回 PROVINCE
	 * @return
	 */
	public String getProvince() 
	{
		return this.province;
	}
	public void setCity(String city) 
	{
		this.city = city;
	}
	/**
	 * 返回 CITY
	 * @return
	 */
	public String getCity() 
	{
		return this.city;
	}
	public void setIsPublic(Long isPublic) 
	{
		this.isPublic = isPublic;
	}
	/**
	 * 返回 IS_PUBLIC
	 * @return
	 */
	public Long getIsPublic() 
	{
		return this.isPublic;
	}
	public void setCode(String code) 
	{
		this.code = code;
	}
	/**
	 * 返回 CODE
	 * @return
	 */
	public String getCode() 
	{
		return this.code;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 TYPE
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setModel(String model) 
	{
		this.model = model;
	}
	/**
	 * 返回 MODEL
	 * @return
	 */
	public String getModel() 
	{
		return this.model;
	}
	public void setProduct(String product) 
	{
		this.product = product;
	}
	/**
	 * 返回 PRODUCT
	 * @return
	 */
	public String getProduct() 
	{
		return this.product;
	}
	public void setWebsite(String website) 
	{
		this.website = website;
	}
	/**
	 * 返回 WEBSITE
	 * @return
	 */
	public String getWebsite() 
	{
		return this.website;
	}
	public void setRegistertime(java.util.Date registertime) 
	{
		this.registertime = registertime;
	}
	/**
	 * 返回 REGISTERTIME
	 * @return
	 */
	public java.util.Date getRegistertime() 
	{
		return this.registertime;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof OrgInfation)) 
		{
			return false;
		}
		OrgInfation rhs = (OrgInfation) object;
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
		.append(this.logo, rhs.logo)
		.append(this.industry2, rhs.industry2)
		.append(this.member, rhs.member)
		.append(this.info, rhs.info)
		.append(this.country, rhs.country)
		.append(this.province, rhs.province)
		.append(this.city, rhs.city)
		.append(this.isPublic, rhs.isPublic)
		.append(this.code, rhs.code)
		.append(this.type, rhs.type)
		.append(this.model, rhs.model)
		.append(this.product, rhs.product)
		.append(this.website, rhs.website)
		.append(this.registertime, rhs.registertime)
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
		.append(this.logo) 
		.append(this.industry2) 
		.append(this.member) 
		.append(this.info) 
		.append(this.country) 
		.append(this.province) 
		.append(this.city) 
		.append(this.isPublic) 
		.append(this.code) 
		.append(this.type) 
		.append(this.model) 
		.append(this.product) 
		.append(this.website) 
		.append(this.registertime) 
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
		.append("logo", this.logo) 
		.append("industry2", this.industry2) 
		.append("member", this.member) 
		.append("info", this.info) 
		.append("country", this.country) 
		.append("province", this.province) 
		.append("city", this.city) 
		.append("isPublic", this.isPublic) 
		.append("code", this.code) 
		.append("type", this.type) 
		.append("model", this.model) 
		.append("product", this.product) 
		.append("website", this.website) 
		.append("registertime", this.registertime) 
		.toString();
	}
   
  

}