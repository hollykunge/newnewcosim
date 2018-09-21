package com.casic.cloud.model.research.fileVerify;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:CLOUD_RESEARCH_VERIFY_UPLOADINFO Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-14 21:16:24
 */
public class FileVerifyUpload extends BaseModel
{
	// ID
	protected Long  id;
	// 文件名
	protected String  fileName;
	// 文件信息
	protected String  fileInfo;
	// 链接地址
	protected String  fileUrl;
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
	 * 返回 文件信息
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
	 * 返回 链接地址
	 * @return
	 */
	public String getFileUrl() 
	{
		return this.fileUrl;
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
		if (!(object instanceof FileVerifyUpload)) 
		{
			return false;
		}
		FileVerifyUpload rhs = (FileVerifyUpload) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.fileName, rhs.fileName)
		.append(this.fileInfo, rhs.fileInfo)
		.append(this.fileUrl, rhs.fileUrl)
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
		.append(this.fileName) 
		.append(this.fileInfo) 
		.append(this.fileUrl) 
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
		.append("fileName", this.fileName) 
		.append("fileInfo", this.fileInfo) 
		.append("fileUrl", this.fileUrl) 
		.append("sourceId", this.sourceId) 
		.toString();
	}
   
  

}