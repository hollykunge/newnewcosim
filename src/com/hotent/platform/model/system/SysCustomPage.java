package com.hotent.platform.model.system;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:自定义页面 Model对象
 * 开发公司:宏天
 * 开发人员:Raise
 * 创建时间:2012-11-05 09:07:38
 */
public class SysCustomPage extends BaseModel
{
	// ID
	protected Long  id;
	// 名称
	protected String  name;
	// 标题
	protected String  title;
	// 内容
	protected String  content;
	// 描述
	protected String  description;
	// 模板
	protected String  template;
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
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	/**
	 * 返回 标题
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
	 * 返回 内容
	 * @return
	 */
	public String getContent() 
	{
		return this.content;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	/**
	 * 返回 描述
	 * @return
	 */
	public String getDescription() 
	{
		return this.description;
	}
	public void setTemplate(String template) 
	{
		this.template = template;
	}
	/**
	 * 返回 模板
	 * @return
	 */
	public String getTemplate() 
	{
		return this.template;
	}

   
   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof SysCustomPage)) 
		{
			return false;
		}
		SysCustomPage rhs = (SysCustomPage) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.title, rhs.title)
		.append(this.content, rhs.content)
		.append(this.description, rhs.description)
		.append(this.template, rhs.template)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.title) 
		.append(this.content) 
		.append(this.description) 
		.append(this.template) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("title", this.title) 
		.append("content", this.content) 
		.append("description", this.description) 
		.append("template", this.template) 
		.toString();
	}
   
  

}