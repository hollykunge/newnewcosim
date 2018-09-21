package com.casic.cloud.model.research.fileSign;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:CLOUD_RESEARCH_FILESIGN Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-18 14:33:03
 */
public class FileSign extends BaseModel
{
	// ID
	protected Long  id;
	// 审批号
	protected String  code;
	// 申请人ID
	protected Long  applicantId;
	// 申请人名称
	protected String  applicantName;
	// 发起时间
	protected java.util.Date  startTime;
	// 申请理由
	protected String  reason;
	// 审批人ID
	protected Long  verifierId;
	// 审批人
	protected String  verifier;
	// 审批时间
	protected java.util.Date  verifyTime;
	// 审批意见
	protected String  verifyContent;
	// 申请表ID
	protected Long  sourceId;
	// 流程ID
	protected Long  runId;
	//CLOUD_RESEARCH_FILESIGN_FILEINFO列表
	protected List<FileSignInfo> fileSignInfoList=new ArrayList<FileSignInfo>();
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
	 * 返回 申请人名称
	 * @return
	 */
	public String getApplicantName() 
	{
		return this.applicantName;
	}
	public void setStartTime(java.util.Date startTime) 
	{
		this.startTime = startTime;
	}
	/**
	 * 返回 发起时间
	 * @return
	 */
	public java.util.Date getStartTime() 
	{
		return this.startTime;
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
	public void setVerifier(String verifier) 
	{
		this.verifier = verifier;
	}
	/**
	 * 返回 审批人
	 * @return
	 */
	public String getVerifier() 
	{
		return this.verifier;
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
	public void setVerifyContent(String verifyContent) 
	{
		this.verifyContent = verifyContent;
	}
	/**
	 * 返回 审批意见
	 * @return
	 */
	public String getVerifyContent() 
	{
		return this.verifyContent;
	}
	public void setSourceId(Long sourceId) 
	{
		this.sourceId = sourceId;
	}
	/**
	 * 返回 申请表ID
	 * @return
	 */
	public Long getSourceId() 
	{
		return this.sourceId;
	}
	public void setRunId(Long runId) 
	{
		this.runId = runId;
	}
	/**
	 * 返回 流程ID
	 * @return
	 */
	public Long getRunId() 
	{
		return this.runId;
	}
	public void setFileSignInfoList(List<FileSignInfo> fileSignInfoList) 
	{
		this.fileSignInfoList = fileSignInfoList;
	}
	/**
	 * 返回 CLOUD_RESEARCH_FILESIGN_FILEINFO列表
	 * @return
	 */
	public List<FileSignInfo> getFileSignInfoList() 
	{
		return this.fileSignInfoList;
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
		if (!(object instanceof FileSign)) 
		{
			return false;
		}
		FileSign rhs = (FileSign) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.code, rhs.code)
		.append(this.applicantId, rhs.applicantId)
		.append(this.applicantName, rhs.applicantName)
		.append(this.startTime, rhs.startTime)
		.append(this.reason, rhs.reason)
		.append(this.verifierId, rhs.verifierId)
		.append(this.verifier, rhs.verifier)
		.append(this.verifyTime, rhs.verifyTime)
		.append(this.verifyContent, rhs.verifyContent)
		.append(this.sourceId, rhs.sourceId)
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
		.append(this.startTime) 
		.append(this.reason) 
		.append(this.verifierId) 
		.append(this.verifier) 
		.append(this.verifyTime) 
		.append(this.verifyContent) 
		.append(this.sourceId) 
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
		.append("startTime", this.startTime) 
		.append("reason", this.reason) 
		.append("verifierId", this.verifierId) 
		.append("verifier", this.verifier) 
		.append("verifyTime", this.verifyTime) 
		.append("verifyContent", this.verifyContent) 
		.append("sourceId", this.sourceId) 
		.append("runId", this.runId) 
		.toString();
	}
   
  

}