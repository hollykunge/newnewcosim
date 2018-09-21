package com.casic.cloud.model.cloudUseRes;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_use_res Model对象
 * 开发公司:tianzhi
 * 开发人员:xingchi
 * 创建时间:2013-05-16 17:40:22
 */
public class CloudUseRes extends BaseModel
{
	// id
	protected Long  id;
	// 企业Id
	protected Long  entid;
	// 资源名字
	protected String  resName;
	// 资源地址链接
	protected String  resLink;
	// 资源访问时间
	protected java.util.Date  resTime;
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
	public void setEntid(Long entid) 
	{
		this.entid = entid;
	}
	/**
	 * 返回 企业Id
	 * @return
	 */
	public Long getEntid() 
	{
		return this.entid;
	}
	public void setResName(String resName) 
	{
		this.resName = resName;
	}
	/**
	 * 返回 资源名字
	 * @return
	 */
	public String getResName() 
	{
		return this.resName;
	}
	public void setResLink(String resLink) 
	{
		this.resLink = resLink;
	}
	/**
	 * 返回 资源地址链接
	 * @return
	 */
	public String getResLink() 
	{
		return this.resLink;
	}
	public void setResTime(java.util.Date resTime) 
	{
		this.resTime = resTime;
	}
	/**
	 * 返回 资源访问时间
	 * @return
	 */
	public java.util.Date getResTime() 
	{
		return this.resTime;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CloudUseRes)) 
		{
			return false;
		}
		CloudUseRes rhs = (CloudUseRes) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.entid, rhs.entid)
		.append(this.resName, rhs.resName)
		.append(this.resLink, rhs.resLink)
		.append(this.resTime, rhs.resTime)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.entid) 
		.append(this.resName) 
		.append(this.resLink) 
		.append(this.resTime) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("entid", this.entid) 
		.append("resName", this.resName) 
		.append("resLink", this.resLink) 
		.append("resTime", this.resTime) 
		.toString();
	}
   
  

}