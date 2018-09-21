package com.casic.cloud.model.research.filecheck;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_research_filecheck_opinions Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-21 16:04:50
 */
public class ResFilecheckOpinion extends BaseModel
{
	// id
	protected Long  id;
	// 任务id
	protected String  taskId;
	// 任务名
	protected String  taskName;
	// 审签人id
	protected String  checkUserids;
	// 审签人
	protected String  checkUsernames;
	// 审签企业id
	protected String  checkEnterpriseids;
	// 审签企业
	protected String  checkEnterprisenames;
	// 审签意见id
	protected String  checkOpinionids;

	// filecheck_id
	protected Long  filecheckId;
	

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
	public void setTaskId(String taskId) 
	{
		this.taskId = taskId;
	}
	/**
	 * 返回 任务id
	 * @return
	 */
	public String getTaskId() 
	{
		return this.taskId;
	}
	public void setTaskName(String taskName) 
	{
		this.taskName = taskName;
	}
	/**
	 * 返回 任务名
	 * @return
	 */
	public String getTaskName() 
	{
		return this.taskName;
	}
	public void setCheckUserids(String checkUserids) 
	{
		this.checkUserids = checkUserids;
	}
	/**
	 * 返回 审签人id
	 * @return
	 */
	public String getCheckUserids() 
	{
		return this.checkUserids;
	}
	public void setCheckUsernames(String checkUsernames) 
	{
		this.checkUsernames = checkUsernames;
	}
	/**
	 * 返回 审签人
	 * @return
	 */
	public String getCheckUsernames() 
	{
		return this.checkUsernames;
	}
	public void setCheckEnterpriseids(String checkEnterpriseids) 
	{
		this.checkEnterpriseids = checkEnterpriseids;
	}
	/**
	 * 返回 审签企业id
	 * @return
	 */
	public String getCheckEnterpriseids() 
	{
		return this.checkEnterpriseids;
	}
	public void setCheckEnterprisenames(String checkEnterprisenames) 
	{
		this.checkEnterprisenames = checkEnterprisenames;
	}
	/**
	 * 返回 审签企业
	 * @return
	 */
	public String getCheckEnterprisenames() 
	{
		return this.checkEnterprisenames;
	}
	public void setCheckOpinionids(String checkOpinionids) 
	{
		this.checkOpinionids = checkOpinionids;
	}
	/**
	 * 返回 审签意见id
	 * @return
	 */
	public String getCheckOpinionids() 
	{
		return this.checkOpinionids;
	}

	public void setFilecheckId(Long filecheckId) 
	{
		this.filecheckId = filecheckId;
	}
	/**
	 * 返回 filecheck_id
	 * @return
	 */
	public Long getFilecheckId() 
	{
		return this.filecheckId;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ResFilecheckOpinion)) 
		{
			return false;
		}
		ResFilecheckOpinion rhs = (ResFilecheckOpinion) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.taskId, rhs.taskId)
		.append(this.taskName, rhs.taskName)
		.append(this.checkUserids, rhs.checkUserids)
		.append(this.checkUsernames, rhs.checkUsernames)
		.append(this.checkEnterpriseids, rhs.checkEnterpriseids)
		.append(this.checkEnterprisenames, rhs.checkEnterprisenames)
		.append(this.checkOpinionids, rhs.checkOpinionids)
		.append(this.filecheckId, rhs.filecheckId)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.taskId) 
		.append(this.taskName) 
		.append(this.checkUserids) 
		.append(this.checkUsernames) 
		.append(this.checkEnterpriseids) 
		.append(this.checkEnterprisenames) 
		.append(this.checkOpinionids) 
		.append(this.filecheckId) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("taskId", this.taskId) 
		.append("taskName", this.taskName) 
		.append("checkUserid", this.checkUserids) 
		.append("checkUsername", this.checkUsernames) 
		.append("checkEnterpriseid", this.checkEnterpriseids) 
		.append("checkEnterprisename", this.checkEnterprisenames) 
		.append("checkOpinionid", this.checkOpinionids) 
		.append("filecheckId", this.filecheckId) 
		.toString();
	}
   
  

}