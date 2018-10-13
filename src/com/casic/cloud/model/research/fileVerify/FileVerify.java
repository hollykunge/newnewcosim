package com.casic.cloud.model.research.fileVerify;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:CLOUD_RESEARCH_VERIFY Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-16 20:56:58
 */
public class FileVerify extends BaseModel
{
	// ID
	protected Long  id;
	// 审批号
	protected String  code;
	// 申请人ID
	protected Long  applicantId;
	// 申请人
	protected String  applicantName;
	// 申请时间
	protected java.util.Date  time;
	// 申请理由
	protected String  reason;
	// 审批结果
	protected String  result;
	// 申请企业
	protected String  verifyEnterpIds;
	// 流程
	protected Long  runId;
	//CLOUD_RESEARCH_VERIFY_RESULT列表
	protected List<FileVerifyResult> fileVerifyResultList=new ArrayList<FileVerifyResult>();
	//CLOUD_RESEARCH_VERIFY_UPLOADINFO列表
	protected List<FileVerifyUpload> fileVerifyUploadList=new ArrayList<FileVerifyUpload>();
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
	public void setCode(String code) 
	{
		this.code = code;
	}
	/**
	 * 返回 审批号
	 * @return
	 */
	public String getCode() 
	{
		return this.code;
	}
	public void setApplicantId(Long applicantId) 
	{
		this.applicantId = applicantId;
	}
	/**
	 * 返回 申请人ID
	 * @return
	 */
	public Long getApplicantId() 
	{
		return this.applicantId;
	}
	public void setApplicantName(String applicantName) 
	{
		this.applicantName = applicantName;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getApplicantName() 
	{
		return this.applicantName;
	}
	public void setTime(java.util.Date time) 
	{
		this.time = time;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public java.util.Date getTime() 
	{
		return this.time;
	}
	public void setReason(String reason) 
	{
		this.reason = reason;
	}
	/**
	 * 返回 申请理由
	 * @return
	 */
	public String getReason() 
	{
		return this.reason;
	}
	public void setResult(String result) 
	{
		this.result = result;
	}
	/**
	 * 返回 审批结果
	 * @return
	 */
	public String getResult() 
	{
		return this.result;
	}
	public void setVerifyEnterpIds(String verifyEnterpIds) 
	{
		this.verifyEnterpIds = verifyEnterpIds;
	}
	/**
	 * 返回 VERIFY_ENTERP_IDS
	 * @return
	 */
	public String getVerifyEnterpIds() 
	{
		return this.verifyEnterpIds;
	}
	public void setRunId(Long runId) 
	{
		this.runId = runId;
	}
	/**
	 * 返回 流程
	 * @return
	 */
	public Long getRunId() 
	{
		return this.runId;
	}
	public void setFileVerifyResultList(List<FileVerifyResult> fileVerifyResultList) 
	{
		this.fileVerifyResultList = fileVerifyResultList;
	}
	/**
	 * 返回 CLOUD_RESEARCH_VERIFY_RESULT列表
	 * @return
	 */
	public List<FileVerifyResult> getFileVerifyResultList() 
	{
		return this.fileVerifyResultList;
	}
	public void setFileVerifyUploadList(List<FileVerifyUpload> fileVerifyUploadList) 
	{
		this.fileVerifyUploadList = fileVerifyUploadList;
	}
	/**
	 * 返回 CLOUD_RESEARCH_VERIFY_UPLOADINFO列表
	 * @return
	 */
	public List<FileVerifyUpload> getFileVerifyUploadList() 
	{
		return this.fileVerifyUploadList;
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
		if (!(object instanceof FileVerify)) 
		{
			return false;
		}
		FileVerify rhs = (FileVerify) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.code, rhs.code)
		.append(this.applicantId, rhs.applicantId)
		.append(this.applicantName, rhs.applicantName)
		.append(this.time, rhs.time)
		.append(this.reason, rhs.reason)
		.append(this.result, rhs.result)
		.append(this.verifyEnterpIds, rhs.verifyEnterpIds)
		.append(this.runId, rhs.runId)
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
		.append(this.applicantId) 
		.append(this.applicantName) 
		.append(this.time) 
		.append(this.reason) 
		.append(this.result) 
		.append(this.verifyEnterpIds) 
		.append(this.runId) 
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
		.append("applicantId", this.applicantId) 
		.append("applicantName", this.applicantName) 
		.append("time", this.time) 
		.append("reason", this.reason) 
		.append("result", this.result) 
		.append("verifyEnterpIds", this.verifyEnterpIds) 
		.append("runId", this.runId) 
		.toString();
	}
   
  

}