package com.casic.cloud.model.research.fileSign;

import java.util.ArrayList;
import java.util.List;

import com.casic.cloud.model.research.fileVerify.FileVerifyUpload;
import com.hotent.core.model.BaseModel;
import com.hotent.core.util.StringUtil;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:CLOUD_RESEARCH_FILESIGN_FILEINFO Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-18 14:33:03
 */
public class FileSignInfo extends BaseModel
{
	// ID
	protected Long  id;
	// 文件名
	protected String  fileName;
	// 文件详情
	protected String  fileInfo;
	// 文件链接
	protected String  fileUrl;
	// 评审意见
	protected String  fileComment;
	// 文件会签ID
	protected Long  filesignId;
	
	public FileSignInfo(){
		
	}
	public FileSignInfo(FileVerifyUpload fileUpload){
		if(StringUtil.isNotEmpty(fileUpload.getFileName())){
			this.fileName = fileUpload.getFileName();
		}
		if(StringUtil.isNotEmpty(fileUpload.getFileInfo())){
			this.fileInfo = fileUpload.getFileInfo();
		}
		if(StringUtil.isNotEmpty(fileUpload.getFileUrl())){
			this.fileUrl = fileUpload.getFileUrl();
		}
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
	public void setFileInfo(String fileInfo) 
	{
		this.fileInfo = fileInfo;
	}
	/**
	 * 返回 文件详情
	 * @return
	 */
	public String getFileInfo() 
	{
		return this.fileInfo;
	}
	public void setFileUrl(String fileUrl) 
	{
		this.fileUrl = fileUrl;
	}
	/**
	 * 返回 文件链接
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
	 * 返回 评审意见
	 * @return
	 */
	public String getFileComment() 
	{
		return this.fileComment;
	}
	public void setFilesignId(Long filesignId) 
	{
		this.filesignId = filesignId;
	}
	/**
	 * 返回 文件会签ID
	 * @return
	 */
	public Long getFilesignId() 
	{
		return this.filesignId;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof FileSignInfo)) 
		{
			return false;
		}
		FileSignInfo rhs = (FileSignInfo) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.fileName, rhs.fileName)
		.append(this.fileInfo, rhs.fileInfo)
		.append(this.fileUrl, rhs.fileUrl)
		.append(this.fileComment, rhs.fileComment)
		.append(this.filesignId, rhs.filesignId)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.fileName) 
		.append(this.fileInfo) 
		.append(this.fileUrl) 
		.append(this.fileComment) 
		.append(this.filesignId) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("fileName", this.fileName) 
		.append("fileInfo", this.fileInfo) 
		.append("fileUrl", this.fileUrl) 
		.append("fileComment", this.fileComment) 
		.append("filesignId", this.filesignId) 
		.toString();
	}
   
  

}