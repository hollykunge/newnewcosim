package com.casic.cloud.model.account.accountInfo;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_account_info Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-09-05 10:22:50
 */
public class AccountInfo extends BaseModel
{
	// ID
	protected Long  id;
	// 制单日期
	protected java.util.Date  operateDate;
	// 制单人ID
	protected Long  operaterId;
	// 制单人
	protected String  operaterName;
	// 银行卡号
	protected String  bankCode;
	// 开户行
	protected String  openBank;
	// 开户企业ID
	protected Long  enterId;
	// 开户企业名称
	protected String  enterName;
	// 开户人
	protected String  accountOp;
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
	public void setOperateDate(java.util.Date operateDate) 
	{
		this.operateDate = operateDate;
	}
	/**
	 * 返回 制单日期
	 * @return
	 */
	public java.util.Date getOperateDate() 
	{
		return this.operateDate;
	}
	public void setOperaterId(Long operaterId) 
	{
		this.operaterId = operaterId;
	}
	/**
	 * 返回 制单人ID
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
	 * 返回 制单人
	 * @return
	 */
	public String getOperaterName() 
	{
		return this.operaterName;
	}
	public void setBankCode(String bankCode) 
	{
		this.bankCode = bankCode;
	}
	/**
	 * 返回 银行卡号
	 * @return
	 */
	public String getBankCode() 
	{
		return this.bankCode;
	}
	public void setOpenBank(String openBank) 
	{
		this.openBank = openBank;
	}
	/**
	 * 返回 开户行
	 * @return
	 */
	public String getOpenBank() 
	{
		return this.openBank;
	}
	public void setEnterId(Long enterId) 
	{
		this.enterId = enterId;
	}
	/**
	 * 返回 开户企业ID
	 * @return
	 */
	public Long getEnterId() 
	{
		return this.enterId;
	}
	public void setEnterName(String enterName) 
	{
		this.enterName = enterName;
	}
	/**
	 * 返回 开户企业名称
	 * @return
	 */
	public String getEnterName() 
	{
		return this.enterName;
	}
	public void setAccountOp(String accountOp) 
	{
		this.accountOp = accountOp;
	}
	/**
	 * 返回 开户人
	 * @return
	 */
	public String getAccountOp() 
	{
		return this.accountOp;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof AccountInfo)) 
		{
			return false;
		}
		AccountInfo rhs = (AccountInfo) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.operateDate, rhs.operateDate)
		.append(this.operaterId, rhs.operaterId)
		.append(this.operaterName, rhs.operaterName)
		.append(this.bankCode, rhs.bankCode)
		.append(this.openBank, rhs.openBank)
		.append(this.enterId, rhs.enterId)
		.append(this.enterName, rhs.enterName)
		.append(this.accountOp, rhs.accountOp)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.operateDate) 
		.append(this.operaterId) 
		.append(this.operaterName) 
		.append(this.bankCode) 
		.append(this.openBank) 
		.append(this.enterId) 
		.append(this.enterName) 
		.append(this.accountOp) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("operateDate", this.operateDate) 
		.append("operaterId", this.operaterId) 
		.append("operaterName", this.operaterName) 
		.append("bankCode", this.bankCode) 
		.append("openBank", this.openBank) 
		.append("enterId", this.enterId) 
		.append("enterName", this.enterName) 
		.append("accountOp", this.accountOp) 
		.toString();
	}
   
  

}