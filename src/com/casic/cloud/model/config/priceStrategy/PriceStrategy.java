package com.casic.cloud.model.config.priceStrategy;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_price_strategy Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-09-11 16:18:27
 */
public class PriceStrategy extends BaseModel
{
	// id
	protected Long  id;
	// 供应商id
	protected Long  enterpriseId;
	// 供应商名称
	protected String  enterpriseName;
	// 经销商id
	protected Long  coopenterpId;
	// 经销商名称
	protected String  coopenterpName;
	// 物品id
	protected Long  materialId;
	// 物品名称
	protected String  materialName;
	// 数量
	protected Long  number;
	// 季节
	protected String  season;
	// 折扣
	protected Double  discount;
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
	public void setEnterpriseId(Long enterpriseId) 
	{
		this.enterpriseId = enterpriseId;
	}
	/**
	 * 返回 供应商id
	 * @return
	 */
	public Long getEnterpriseId() 
	{
		return this.enterpriseId;
	}
	public void setEnterpriseName(String enterpriseName) 
	{
		this.enterpriseName = enterpriseName;
	}
	/**
	 * 返回 供应商名称
	 * @return
	 */
	public String getEnterpriseName() 
	{
		return this.enterpriseName;
	}
	public void setCoopenterpId(Long coopenterpId) 
	{
		this.coopenterpId = coopenterpId;
	}
	/**
	 * 返回 经销商id
	 * @return
	 */
	public Long getCoopenterpId() 
	{
		return this.coopenterpId;
	}
	public void setCoopenterpName(String coopenterpName) 
	{
		this.coopenterpName = coopenterpName;
	}
	/**
	 * 返回 经销商名称
	 * @return
	 */
	public String getCoopenterpName() 
	{
		return this.coopenterpName;
	}
	public void setMaterialId(Long materialId) 
	{
		this.materialId = materialId;
	}
	/**
	 * 返回 物品id
	 * @return
	 */
	public Long getMaterialId() 
	{
		return this.materialId;
	}
	public void setMaterialName(String materialName) 
	{
		this.materialName = materialName;
	}
	/**
	 * 返回 物品名称
	 * @return
	 */
	public String getMaterialName() 
	{
		return this.materialName;
	}
	public void setNumber(Long number) 
	{
		this.number = number;
	}
	/**
	 * 返回 数量
	 * @return
	 */
	public Long getNumber() 
	{
		return this.number;
	}
	public void setSeason(String season) 
	{
		this.season = season;
	}
	/**
	 * 返回 季节
	 * @return
	 */
	public String getSeason() 
	{
		return this.season;
	}
	public void setDiscount(Double discount) 
	{
		this.discount = discount;
	}
	/**
	 * 返回 折扣
	 * @return
	 */
	public Double getDiscount() 
	{
		return this.discount;
	}

	protected Long runid;
	protected Short runStatus=0;
	protected Long actInstId;
	
	public Long getRunid() {
		return runid;
	}
	public void setRunid(Long runid) {
		this.runid = runid;
	}
	public Short getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(Short runStatus) {
		this.runStatus = runStatus;
	}
	
	public Long getActInstId() {
		return actInstId;
	}
	public void setActInstId(Long actInstId) {
		this.actInstId = actInstId;
	}

   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof PriceStrategy)) 
		{
			return false;
		}
		PriceStrategy rhs = (PriceStrategy) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.enterpriseId, rhs.enterpriseId)
		.append(this.enterpriseName, rhs.enterpriseName)
		.append(this.coopenterpId, rhs.coopenterpId)
		.append(this.coopenterpName, rhs.coopenterpName)
		.append(this.materialId, rhs.materialId)
		.append(this.materialName, rhs.materialName)
		.append(this.number, rhs.number)
		.append(this.season, rhs.season)
		.append(this.discount, rhs.discount)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.enterpriseId) 
		.append(this.enterpriseName) 
		.append(this.coopenterpId) 
		.append(this.coopenterpName) 
		.append(this.materialId) 
		.append(this.materialName) 
		.append(this.number) 
		.append(this.season) 
		.append(this.discount) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("enterpriseId", this.enterpriseId) 
		.append("enterpriseName", this.enterpriseName) 
		.append("coopenterpId", this.coopenterpId) 
		.append("coopenterpName", this.coopenterpName) 
		.append("materialId", this.materialId) 
		.append("materialName", this.materialName) 
		.append("number", this.number) 
		.append("season", this.season) 
		.append("discount", this.discount) 
		.toString();
	}
   
  

}