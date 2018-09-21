package com.casic.cloud.model.config.cloudSrc;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_src Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-07 17:51:21
 */
public class CloudSrc extends BaseModel
{
	// id
	protected Long  id;
	// 资源名称
	protected String  name;
	// 资源图片
	protected String  pic;
	// 链接地址
	protected String  path;
	// 资源详情
	protected String  info;
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
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 资源名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setPic(String pic) 
	{
		this.pic = pic;
	}
	/**
	 * 返回 资源图片
	 * @return
	 */
	public String getPic() 
	{
		return this.pic;
	}
	public void setPath(String path) 
	{
		this.path = path;
	}
	/**
	 * 返回 图片路径
	 * @return
	 */
	public String getPath() 
	{
		return this.path;
	}
	public void setInfo(String info) 
	{
		this.info = info;
	}
	/**
	 * 返回 资源详情
	 * @return
	 */
	public String getInfo() 
	{
		return this.info;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CloudSrc)) 
		{
			return false;
		}
		CloudSrc rhs = (CloudSrc) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.pic, rhs.pic)
		.append(this.path, rhs.path)
		.append(this.info, rhs.info)
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
		.append(this.pic) 
		.append(this.path) 
		.append(this.info) 
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
		.append("pic", this.pic) 
		.append("path", this.path) 
		.append("info", this.info) 
		.toString();
	}
   
  

}