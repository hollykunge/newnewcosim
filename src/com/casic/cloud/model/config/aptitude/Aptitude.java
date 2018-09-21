package com.casic.cloud.model.config.aptitude;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:sys_org_info_aptitude Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-06 16:34:54
 */
public class Aptitude extends BaseModel
{
	// ID
	protected Long  id;
	// INFO_ID
	protected Long  infoId;
	// CATE_TYPE
	protected String  cateType;
	// CATE_ORG
	protected String  cateOrg;
	// INURE_DATE
	protected java.util.Date  inureDate;
	// END_DATE
	protected String  endDate;
	// CATE_PIC
	protected String  catePic;
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
	public void setInfoId(Long infoId) 
	{
		this.infoId = infoId;
	}
	/**
	 * 返回 INFO_ID
	 * @return
	 */
	public Long getInfoId() 
	{
		return this.infoId;
	}
	public void setCateType(String cateType) 
	{
		this.cateType = cateType;
	}
	/**
	 * 返回 CATE_TYPE
	 * @return
	 */
	public String getCateType() 
	{
		return this.cateType;
	}
	public void setCateOrg(String cateOrg) 
	{
		this.cateOrg = cateOrg;
	}
	/**
	 * 返回 CATE_ORG
	 * @return
	 */
	public String getCateOrg() 
	{
		return this.cateOrg;
	}
	public void setInureDate(java.util.Date inureDate) 
	{
		this.inureDate = inureDate;
	}
	/**
	 * 返回 INURE_DATE
	 * @return
	 */
	public java.util.Date getInureDate() 
	{
		return this.inureDate;
	}
	public void setEndDate(String endDate) 
	{
		this.endDate = endDate;
	}
	/**
	 * 返回 END_DATE
	 * @return
	 */
	public String getEndDate() 
	{
		return this.endDate;
	}
	public void setCatePic(String catePic) 
	{
		this.catePic = catePic;
	}
	/**
	 * 返回 CATE_PIC
	 * @return
	 */
	public String getCatePic() 
	{
		return this.catePic;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Aptitude)) 
		{
			return false;
		}
		Aptitude rhs = (Aptitude) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.infoId, rhs.infoId)
		.append(this.cateType, rhs.cateType)
		.append(this.cateOrg, rhs.cateOrg)
		.append(this.inureDate, rhs.inureDate)
		.append(this.endDate, rhs.endDate)
		.append(this.catePic, rhs.catePic)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.infoId) 
		.append(this.cateType) 
		.append(this.cateOrg) 
		.append(this.inureDate) 
		.append(this.endDate) 
		.append(this.catePic) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("infoId", this.infoId) 
		.append("cateType", this.cateType) 
		.append("cateOrg", this.cateOrg) 
		.append("inureDate", this.inureDate) 
		.append("endDate", this.endDate) 
		.append("catePic", this.catePic) 
		.toString();
	}
   
  

}