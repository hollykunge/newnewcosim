package com.casic.cloud.model.system.news;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:AUTH_NEWS Model对象
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2013-04-12 21:43:57
 */
public class News extends BaseModel
{
	// id
	protected Long  id;
	// title
	protected String  title;
	// content
	protected String  content;
	// publisher
	protected String  publisher;
	// publishtime
	protected java.util.Date  publishtime;
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
	public void setTitle(String title) 
	{
		this.title = title;
	}
	/**
	 * 返回 title
	 * @return
	 */
	public String getTitle() 
	{
		return this.title;
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
	public void setPublisher(String publisher) 
	{
		this.publisher = publisher;
	}
	/**
	 * 返回 publisher
	 * @return
	 */
	public String getPublisher() 
	{
		return this.publisher;
	}
	public void setPublishtime(java.util.Date publishtime) 
	{
		this.publishtime = publishtime;
	}
	/**
	 * 返回 publishtime
	 * @return
	 */
	public java.util.Date getPublishtime() 
	{
		return this.publishtime;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof News)) 
		{
			return false;
		}
		News rhs = (News) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.title, rhs.title)
		.append(this.content, rhs.content)
		.append(this.publisher, rhs.publisher)
		.append(this.publishtime, rhs.publishtime)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.title) 
		.append(this.content) 
		.append(this.publisher) 
		.append(this.publishtime) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("title", this.title) 
		.append("content", this.content) 
		.append("publisher", this.publisher) 
		.append("publishtime", this.publishtime) 
		.toString();
	}
   
  

}