package com.casic.cloud.model.crowdsourcing.crowdsourcingResponse;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_crowdsourcing_response Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:13:50
 */
public class CrowdsourcingResponse extends BaseModel
{
	// 表单ID
	protected Long  id;
	// 表单CODE
	protected String  code;
	// 制表人ID
	protected Long  operaterId;
	// 制表人名称
	protected String  operaterName;
	// 制表企业ID
	protected Long  operaterEnterpId;
	// 制表企业名称
	protected String  operaterEnterpName;
	// 制表日期
	protected java.util.Date  operateDate;
	// 发布状态
	protected String  publishStatus;
	// 来源众包需求表ID
	protected Long  sourceformCrowdsourcingId;
	// 来源众包需求表CODE
	protected String  sourceformCrowdsourcingCode;
	// 研发物品ID
	protected Long  materialId;
	// 研发物品CODE
	protected String  materialCode;
	// 研发物品名称
	protected String  materialName;
	// 研发物品分类
	protected String  materialType;
	// 研发物品BOM层级
	protected String  materialBomLevel;
	// 币种
	protected String  currency;
	// 报价
	protected Double  quote;
	// 预计完成时间
	protected java.util.Date  forecastCompleteTime;
	//cloud_crowdsourcing_response_detail列表
	protected List<CrowdsourcingResponseDetail> crowdsourcingResponseDetailList=new ArrayList<CrowdsourcingResponseDetail>();
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 表单ID
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setCode(String code) 
	{
		this.code = code;
	}
	/**
	 * 返回 表单CODE
	 * @return
	 */
	public String getCode() 
	{
		return this.code;
	}
	public void setOperaterId(Long operaterId) 
	{
		this.operaterId = operaterId;
	}
	/**
	 * 返回 制表人ID
	 * @return
	 */
	public Long getOperaterId() 
	{
		return this.operaterId;
	}
	public void setOperaterName(String operaterName) 
	{
		this.operaterName = operaterName;
	}
	/**
	 * 返回 制表人名称
	 * @return
	 */
	public String getOperaterName() 
	{
		return this.operaterName;
	}
	public void setOperaterEnterpId(Long operaterEnterpId) 
	{
		this.operaterEnterpId = operaterEnterpId;
	}
	/**
	 * 返回 制表企业ID
	 * @return
	 */
	public Long getOperaterEnterpId() 
	{
		return this.operaterEnterpId;
	}
	public void setOperaterEnterpName(String operaterEnterpName) 
	{
		this.operaterEnterpName = operaterEnterpName;
	}
	/**
	 * 返回 制表企业名称
	 * @return
	 */
	public String getOperaterEnterpName() 
	{
		return this.operaterEnterpName;
	}
	public void setOperateDate(java.util.Date operateDate) 
	{
		this.operateDate = operateDate;
	}
	/**
	 * 返回 制表日期
	 * @return
	 */
	public java.util.Date getOperateDate() 
	{
		return this.operateDate;
	}
	public void setPublishStatus(String publishStatus) 
	{
		this.publishStatus = publishStatus;
	}
	/**
	 * 返回 发布状态
	 * @return
	 */
	public String getPublishStatus() 
	{
		return this.publishStatus;
	}
	public void setSourceformCrowdsourcingId(Long sourceformCrowdsourcingId) 
	{
		this.sourceformCrowdsourcingId = sourceformCrowdsourcingId;
	}
	/**
	 * 返回 来源众包需求表ID
	 * @return
	 */
	public Long getSourceformCrowdsourcingId() 
	{
		return this.sourceformCrowdsourcingId;
	}
	public void setSourceformCrowdsourcingCode(String sourceformCrowdsourcingCode) 
	{
		this.sourceformCrowdsourcingCode = sourceformCrowdsourcingCode;
	}
	/**
	 * 返回 来源众包需求表CODE
	 * @return
	 */
	public String getSourceformCrowdsourcingCode() 
	{
		return this.sourceformCrowdsourcingCode;
	}
	public void setMaterialId(Long materialId) 
	{
		this.materialId = materialId;
	}
	/**
	 * 返回 研发物品ID
	 * @return
	 */
	public Long getMaterialId() 
	{
		return this.materialId;
	}
	public void setMaterialCode(String materialCode) 
	{
		this.materialCode = materialCode;
	}
	/**
	 * 返回 研发物品CODE
	 * @return
	 */
	public String getMaterialCode() 
	{
		return this.materialCode;
	}
	public void setMaterialName(String materialName) 
	{
		this.materialName = materialName;
	}
	/**
	 * 返回 研发物品名称
	 * @return
	 */
	public String getMaterialName() 
	{
		return this.materialName;
	}
	public void setMaterialType(String materialType) 
	{
		this.materialType = materialType;
	}
	/**
	 * 返回 研发物品分类
	 * @return
	 */
	public String getMaterialType() 
	{
		return this.materialType;
	}
	public void setMaterialBomLevel(String materialBomLevel) 
	{
		this.materialBomLevel = materialBomLevel;
	}
	/**
	 * 返回 研发物品BOM层级
	 * @return
	 */
	public String getMaterialBomLevel() 
	{
		return this.materialBomLevel;
	}
	public void setCurrency(String currency) 
	{
		this.currency = currency;
	}
	/**
	 * 返回 币种
	 * @return
	 */
	public String getCurrency() 
	{
		return this.currency;
	}
	public void setQuote(Double quote) 
	{
		this.quote = quote;
	}
	/**
	 * 返回 报价
	 * @return
	 */
	public Double getQuote() 
	{
		return this.quote;
	}
	public void setForecastCompleteTime(java.util.Date forecastCompleteTime) 
	{
		this.forecastCompleteTime = forecastCompleteTime;
	}
	/**
	 * 返回 预计完成时间
	 * @return
	 */
	public java.util.Date getForecastCompleteTime() 
	{
		return this.forecastCompleteTime;
	}
	public void setCrowdsourcingResponseDetailList(List<CrowdsourcingResponseDetail> crowdsourcingResponseDetailList) 
	{
		this.crowdsourcingResponseDetailList = crowdsourcingResponseDetailList;
	}
	/**
	 * 返回 cloud_crowdsourcing_response_detail列表
	 * @return
	 */
	public List<CrowdsourcingResponseDetail> getCrowdsourcingResponseDetailList() 
	{
		return this.crowdsourcingResponseDetailList;
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
		if (!(object instanceof CrowdsourcingResponse)) 
		{
			return false;
		}
		CrowdsourcingResponse rhs = (CrowdsourcingResponse) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.code, rhs.code)
		.append(this.operaterId, rhs.operaterId)
		.append(this.operaterName, rhs.operaterName)
		.append(this.operaterEnterpId, rhs.operaterEnterpId)
		.append(this.operaterEnterpName, rhs.operaterEnterpName)
		.append(this.operateDate, rhs.operateDate)
		.append(this.publishStatus, rhs.publishStatus)
		.append(this.sourceformCrowdsourcingId, rhs.sourceformCrowdsourcingId)
		.append(this.sourceformCrowdsourcingCode, rhs.sourceformCrowdsourcingCode)
		.append(this.materialId, rhs.materialId)
		.append(this.materialCode, rhs.materialCode)
		.append(this.materialName, rhs.materialName)
		.append(this.materialType, rhs.materialType)
		.append(this.materialBomLevel, rhs.materialBomLevel)
		.append(this.currency, rhs.currency)
		.append(this.quote, rhs.quote)
		.append(this.forecastCompleteTime, rhs.forecastCompleteTime)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.code) 
		.append(this.operaterId) 
		.append(this.operaterName) 
		.append(this.operaterEnterpId) 
		.append(this.operaterEnterpName) 
		.append(this.operateDate) 
		.append(this.publishStatus) 
		.append(this.sourceformCrowdsourcingId) 
		.append(this.sourceformCrowdsourcingCode) 
		.append(this.materialId) 
		.append(this.materialCode) 
		.append(this.materialName) 
		.append(this.materialType) 
		.append(this.materialBomLevel) 
		.append(this.currency) 
		.append(this.quote) 
		.append(this.forecastCompleteTime) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("code", this.code) 
		.append("operaterId", this.operaterId) 
		.append("operaterName", this.operaterName) 
		.append("operaterEnterpId", this.operaterEnterpId) 
		.append("operaterEnterpName", this.operaterEnterpName) 
		.append("operateDate", this.operateDate) 
		.append("publishStatus", this.publishStatus) 
		.append("sourceformCrowdsourcingId", this.sourceformCrowdsourcingId) 
		.append("sourceformCrowdsourcingCode", this.sourceformCrowdsourcingCode) 
		.append("materialId", this.materialId) 
		.append("materialCode", this.materialCode) 
		.append("materialName", this.materialName) 
		.append("materialType", this.materialType) 
		.append("materialBomLevel", this.materialBomLevel) 
		.append("currency", this.currency) 
		.append("quote", this.quote) 
		.append("forecastCompleteTime", this.forecastCompleteTime) 
		.toString();
	}
   
  

}