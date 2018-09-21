package com.casic.cloud.model.research.filecheck;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_research_filecheck Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-21 16:04:50
 */
public class ResFilecheck extends BaseModel
{
	// 文档编码
	protected String  code;
	// ID
	protected Long  id;
	// 制单人
	protected Long  operatorId;
	// 制单人姓名
	protected String  operatorName;
	// 制单日期
	protected java.util.Date  operateDate;
	
	//当前任务执行人/企业
	protected String currentUserIds;
	protected String currentEnterpIds;
	
	public String getCurrentUserIds() {
		return currentUserIds;
	}
	public void setCurrentUserIds(String currentUserIds) {
		this.currentUserIds = currentUserIds;
	}
	public String getCurrentEnterpIds() {
		return currentEnterpIds;
	}
	public void setCurrentEnterpIds(String currentEnterpIds) {
		this.currentEnterpIds = currentEnterpIds;
	}
	
	public java.util.Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(java.util.Date operateDate) {
		this.operateDate = operateDate;
	}

	// 企业ID
	protected Long  enterpriseId;
	// 企业
	protected String  enterpriseName;
	// 文档审签状态
	protected String  status;
	// run_id
	protected Long  runId;
	//cloud_research_filecheck_fileinfo列表
	protected List<ResFilecheckDetail> resFilecheckDetailList=new ArrayList<ResFilecheckDetail>();
	//cloud_research_filecheck_opinions列表
	protected List<ResFilecheckOpinion> resFilecheckOpinionList=new ArrayList<ResFilecheckOpinion>();
	public void setCode(String code) 
	{
		this.code = code;
	}
	/**
	 * 返回 文档编码
	 * @return
	 */
	public String getCode() 
	{
		return this.code;
	}
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
	public void setOperatorId(Long operatorId) 
	{
		this.operatorId = operatorId;
	}
	/**
	 * 返回 制单人
	 * @return
	 */
	public Long getOperatorId() 
	{
		return this.operatorId;
	}
	public void setOperatorName(String operatorName) 
	{
		this.operatorName = operatorName;
	}
	/**
	 * 返回 制单人姓名
	 * @return
	 */
	public String getOperatorName() 
	{
		return this.operatorName;
	}
	public void setEnterpriseId(Long enterpriseId) 
	{
		this.enterpriseId = enterpriseId;
	}
	/**
	 * 返回 企业ID
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
	 * 返回 企业
	 * @return
	 */
	public String getEnterpriseName() 
	{
		return this.enterpriseName;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}
	/**
	 * 返回 文档审签状态
	 * @return
	 */
	public String getStatus() 
	{
		return this.status;
	}
	public void setRunId(Long runId) 
	{
		this.runId = runId;
	}
	/**
	 * 返回 run_id
	 * @return
	 */
	public Long getRunId() 
	{
		return this.runId;
	}
	public void setResFilecheckDetailList(List<ResFilecheckDetail> resFilecheckDetailList) 
	{
		this.resFilecheckDetailList = resFilecheckDetailList;
	}
	/**
	 * 返回 cloud_research_filecheck_fileinfo列表
	 * @return
	 */
	public List<ResFilecheckDetail> getResFilecheckDetailList() 
	{
		return this.resFilecheckDetailList;
	}
	public void setResFilecheckOpinionList(List<ResFilecheckOpinion> resFilecheckOpinionList) 
	{
		this.resFilecheckOpinionList = resFilecheckOpinionList;
	}
	/**
	 * 返回 cloud_research_filecheck_opinions列表
	 * @return
	 */
	public List<ResFilecheckOpinion> getResFilecheckOpinionList() 
	{
		return this.resFilecheckOpinionList;
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
		if (!(object instanceof ResFilecheck)) 
		{
			return false;
		}
		ResFilecheck rhs = (ResFilecheck) object;
		return new EqualsBuilder()
		.append(this.code, rhs.code)
		.append(this.id, rhs.id)
		.append(this.operatorId, rhs.operatorId)
		.append(this.operatorName, rhs.operatorName)
		.append(this.enterpriseId, rhs.enterpriseId)
		.append(this.enterpriseName, rhs.enterpriseName)
		.append(this.status, rhs.status)
		.append(this.runId, rhs.runId)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.code) 
		.append(this.id) 
		.append(this.operatorId) 
		.append(this.operatorName) 
		.append(this.enterpriseId) 
		.append(this.enterpriseName) 
		.append(this.status) 
		.append(this.runId) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("code", this.code) 
		.append("id", this.id) 
		.append("operatorId", this.operatorId) 
		.append("operatorName", this.operatorName) 
		.append("enterpriseId", this.enterpriseId) 
		.append("enterpriseName", this.enterpriseName) 
		.append("status", this.status) 
		.append("runId", this.runId) 
		.toString();
	}
   
  

}