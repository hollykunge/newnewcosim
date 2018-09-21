package com.casic.cloud.model.compass.compassNews;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
/**
 * 对象功能:cloud_compass_news Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-20 15:16:04
 */

public class CompassNews extends BaseModel
{
	// newsid
	protected Long  newsid;
	// subject
	protected String  subject;
	// content
	protected String  content;
	// createtime
	protected java.util.Date  createtime;
	public void setNewsid(Long newsid) 
	{
		this.newsid = newsid;
	}
	/**
	 * 返回 newsid
	 * @return
	 */
	public Long getNewsid() 
	{
		return this.newsid;
	}
	public void setSubject(String subject) 
	{
		this.subject = subject;
	}
	/**
	 * 返回 subject
	 * @return
	 */
	public String getSubject() 
	{
		return this.subject;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	/**
	 * 返回 content
	 * @return
	 */
	public String getContent() 
	{
		return this.content;
	}
	public void setCreatetime(java.util.Date createtime) 
	{
		this.createtime = createtime;
	}
	/**
	 * 返回 createtime
	 * @return
	 */
	public java.util.Date getCreatetime() 
	{
		return this.createtime;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CompassNews)) 
		{
			return false;
		}
		CompassNews rhs = (CompassNews) object;
		return new EqualsBuilder()
		.append(this.newsid, rhs.newsid)
		.append(this.subject, rhs.subject)
		.append(this.content, rhs.content)
		.append(this.createtime, rhs.createtime)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.newsid) 
		.append(this.subject) 
		.append(this.content) 
		.append(this.createtime) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("newsid", this.newsid) 
		.append("subject", this.subject) 
		.append("content", this.content) 
		.append("createtime", this.createtime) 
		.toString();
	}
   
  

}