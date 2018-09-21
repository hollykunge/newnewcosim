package com.casic.cloud.model.config.interestStrategy;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_interest_strategy_detail Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-09-24 17:29:55
 */
public class InterestStrategyDetail extends BaseModel
{
	// id
	protected Long  id;
	// 上限
	protected Long  capsNumber;
	// 下限
	protected Long  lowerNumber;
	// 返利值
	protected Double  interestValue;
	// 返利策略id
	protected Long  strategyId;
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
	public void setCapsNumber(Long capsNumber) 
	{
		this.capsNumber = capsNumber;
	}
	/**
	 * 返回 上限
	 * @return
	 */
	public Long getCapsNumber() 
	{
		return this.capsNumber;
	}
	public void setLowerNumber(Long lowerNumber) 
	{
		this.lowerNumber = lowerNumber;
	}
	/**
	 * 返回 下限
	 * @return
	 */
	public Long getLowerNumber() 
	{
		return this.lowerNumber;
	}
	public void setInterestValue(Double interestValue) 
	{
		this.interestValue = interestValue;
	}
	/**
	 * 返回 返利值
	 * @return
	 */
	public Double getInterestValue() 
	{
		return this.interestValue;
	}
	public void setStrategyId(Long strategyId) 
	{
		this.strategyId = strategyId;
	}
	/**
	 * 返回 返利策略id
	 * @return
	 */
	public Long getStrategyId() 
	{
		return this.strategyId;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof InterestStrategyDetail)) 
		{
			return false;
		}
		InterestStrategyDetail rhs = (InterestStrategyDetail) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.capsNumber, rhs.capsNumber)
		.append(this.lowerNumber, rhs.lowerNumber)
		.append(this.interestValue, rhs.interestValue)
		.append(this.strategyId, rhs.strategyId)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.capsNumber) 
		.append(this.lowerNumber) 
		.append(this.interestValue) 
		.append(this.strategyId) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("capsNumber", this.capsNumber) 
		.append("lowerNumber", this.lowerNumber) 
		.append("interestValue", this.interestValue) 
		.append("strategyId", this.strategyId) 
		.toString();
	}
   
  

}