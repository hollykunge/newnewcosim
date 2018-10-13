package com.casic.cloud.model.research.filecheck;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_research_filecheck_fileinfo Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-21 16:04:50
 */
public class ResFilecheckDetail extends BaseModel
{
	// 文件名
	protected String  fileName;
	// id
	protected Long  id;
	// 文件路径
	protected String  fileUrl;
	// 文件描述
	protected String  fileComment;
	// 文件类型
	protected String  fileType;
	// 上传者ID
	protected Long  uploadUserid;
	// 上传人
	protected String  uploadUsername;
	// 上传时间
	protected java.util.Date  uploadTime;
	// 文件版本
	protected String  fileVersion;
	// filecheck_id
	protected Long  filecheckId;
	// 管理ID
	protected Long  managementId;
	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}
	/**
	 * 返回 文件名
	 * @return
	 */
	public String getFileName() 
	{
		return this.fileName;
	}
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
	public void setFileUrl(String fileUrl) 
	{
		this.fileUrl = fileUrl;
	}
	/**
	 * 返回 文件路径
	 * @return
	 */
	public String getFileUrl() 
	{
		return this.fileUrl;
	}
	public void setFileComment(String fileComment) 
	{
		this.fileComment = fileComment;
	}
	/**
	 * 返回 文件描述
	 * @return
	 */
	public String getFileComment() 
	{
		return this.fileComment;
	}
	public void setFileType(String fileType) 
	{
		this.fileType = fileType;
	}
	/**
	 * 返回 文件类型
	 * @return
	 */
	public String getFileType() 
	{
		return this.fileType;
	}
	public void setUploadUserid(Long uploadUserid) 
	{
		this.uploadUserid = uploadUserid;
	}
	/**
	 * 返回 上传者ID
	 * @return
	 */
	public Long getUploadUserid() 
	{
		return this.uploadUserid;
	}
	public void setUploadUsername(String uploadUsername) 
	{
		this.uploadUsername = uploadUsername;
	}
	/**
	 * 返回 上传人
	 * @return
	 */
	public String getUploadUsername() 
	{
		return this.uploadUsername;
	}
	public void setUploadTime(java.util.Date uploadTime) 
	{
		this.uploadTime = uploadTime;
	}
	/**
	 * 返回 上传时间
	 * @return
	 */
	public java.util.Date getUploadTime() 
	{
		return this.uploadTime;
	}
	public void setFileVersion(String fileVersion) 
	{
		this.fileVersion = fileVersion;
	}
	/**
	 * 返回 文件版本
	 * @return
	 */
	public String getFileVersion() 
	{
		return this.fileVersion;
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
	public void setManagementId(Long managementId) 
	{
		this.managementId = managementId;
	}
	/**
	 * 返回 管理ID
	 * @return
	 */
	public Long getManagementId() 
	{
		return this.managementId;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ResFilecheckDetail)) 
		{
			return false;
		}
		ResFilecheckDetail rhs = (ResFilecheckDetail) object;
		return new EqualsBuilder()
		.append(this.fileName, rhs.fileName)
		.append(this.id, rhs.id)
		.append(this.fileUrl, rhs.fileUrl)
		.append(this.fileComment, rhs.fileComment)
		.append(this.fileType, rhs.fileType)
		.append(this.uploadUserid, rhs.uploadUserid)
		.append(this.uploadUsername, rhs.uploadUsername)
		.append(this.uploadTime, rhs.uploadTime)
		.append(this.fileVersion, rhs.fileVersion)
		.append(this.filecheckId, rhs.filecheckId)
		.append(this.managementId, rhs.managementId)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.fileName) 
		.append(this.id) 
		.append(this.fileUrl) 
		.append(this.fileComment) 
		.append(this.fileType) 
		.append(this.uploadUserid) 
		.append(this.uploadUsername) 
		.append(this.uploadTime) 
		.append(this.fileVersion) 
		.append(this.filecheckId) 
		.append(this.managementId) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("fileName", this.fileName) 
		.append("id", this.id) 
		.append("fileUrl", this.fileUrl) 
		.append("fileComment", this.fileComment) 
		.append("fileType", this.fileType) 
		.append("uploadUserid", this.uploadUserid) 
		.append("uploadUsername", this.uploadUsername) 
		.append("uploadTime", this.uploadTime) 
		.append("fileVersion", this.fileVersion) 
		.append("filecheckId", this.filecheckId) 
		.append("managementId", this.managementId) 
		.toString();
	}
   
  

}