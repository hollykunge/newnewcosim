package com.casic.cloud.model.research.fileVerify;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:CLOUD_RESEARCH_VERIFY_RESULT Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-14 21:16:24
 */
public class FileVerifyResult extends BaseModel
{
	// ID
	protected Long  id;
	// 审批人ID
	protected Long  verifierId;
	// 审批人
	protected String  verifierName;
	// 审批时间
	protected java.util.Date  verifyTime;
	// 审批结果
	protected String  verifyResult;
	// 审批申请ID
	protected Long  sourceId;
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
	public void setVerifierId(Long verifierId) 
	{
		this.verifierId = verifierId;
	}
	/**
	 * 返回 审批人ID
	 * @return
	 */
	public Long getVerifierId() 
	{
		return this.verifierId;
	}
	public void setVerifierName(String verifierName) 
	{
		this.verifierName = verifierName;
	}
	/**
	 * 返回 审批人
	 * @return
	 */
	public String getVerifierName() 
	{
		return this.verifierName;
	}
	public void setVerifyTime(java.util.Date verifyTime) 
	{
		this.verifyTime = verifyTime;
	}
	/**
	 * 返回 审批时间
	 * @return
	 */
	public java.util.Date getVerifyTime() 
	{
		return this.verifyTime;
	}
	public void setVerifyResult(String verifyResult) 
	{
		this.verifyResult = verifyResult;
	}
	/**
	 * 返回 审批结果
	 * @return
	 */
	public String getVerifyResult() 
	{
		return this.verifyResult;
	}
	public void setSourceId(Long sourceId) 
	{
		this.sourceId = sourceId;
	}
	/**
	 * 返回 审批申请ID
	 * @return
	 */
	public Long getSourceId() 
	{
		return this.sourceId;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof FileVerifyResult)) 
		{
			return false;
		}
		FileVerifyResult rhs = (FileVerifyResult) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.verifierId, rhs.verifierId)
		.append(this.verifierName, rhs.verifierName)
		.append(this.verifyTime, rhs.verifyTime)
		.append(this.verifyResult, rhs.verifyResult)
		.append(this.sourceId, rhs.sourceId)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.verifierId) 
		.append(this.verifierName) 
		.append(this.verifyTime) 
		.append(this.verifyResult) 
		.append(this.sourceId) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("verifierId", this.verifierId) 
		.append("verifierName", this.verifierName) 
		.append("verifyTime", this.verifyTime) 
		.append("verifyResult", this.verifyResult) 
		.append("sourceId", this.sourceId) 
		.toString();
	}
   
  

}