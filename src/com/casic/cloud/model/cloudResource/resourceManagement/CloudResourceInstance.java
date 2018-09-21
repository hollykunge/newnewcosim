package com.casic.cloud.model.cloudResource.resourceManagement;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_resource_instance Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-06-13 18:11:59
 */
public class CloudResourceInstance extends BaseModel
{
	// id
	protected Long  id;
	// 资源示例所属类
	protected Long  classId;
	// 资源名称
	protected String  title;
	// 资源信息
	protected String  info;
	// 图片地址
	protected String  urlPic;
	// 链接1地址
	protected String  url1;
	// 链接1名称
	protected String  url1Name;
	// 链接2地址
	protected String  url2;
	// 链接2名称
	protected String  url2Name;
	// blank1
	protected String  blank1;
	// blank2
	protected String  blank2;
	// commit_info
	protected String  mode;
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
	public void setClassId(Long classId) 
	{
		this.classId = classId;
	}
	/**
	 * 返回 资源示例所属类
	 * @return
	 */
	public Long getClassId() 
	{
		return this.classId;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	/**
	 * 返回 资源名称
	 * @return
	 */
	public String getTitle() 
	{
		return this.title;
	}
	public void setInfo(String info) 
	{
		this.info = info;
	}
	/**
	 * 返回 资源信息
	 * @return
	 */
	public String getInfo() 
	{
		return this.info;
	}
	public void setUrlPic(String urlPic) 
	{
		this.urlPic = urlPic;
	}
	/**
	 * 返回 图片地址
	 * @return
	 */
	public String getUrlPic() 
	{
		return this.urlPic;
	}
	public void setUrl1(String url1) 
	{
		this.url1 = url1;
	}
	/**
	 * 返回 链接1地址
	 * @return
	 */
	public String getUrl1() 
	{
		return this.url1;
	}
	public void setUrl1Name(String url1Name) 
	{
		this.url1Name = url1Name;
	}
	/**
	 * 返回 链接1名称
	 * @return
	 */
	public String getUrl1Name() 
	{
		return this.url1Name;
	}
	public void setUrl2(String url2) 
	{
		this.url2 = url2;
	}
	/**
	 * 返回 链接2地址
	 * @return
	 */
	public String getUrl2() 
	{
		return this.url2;
	}
	public void setUrl2Name(String url2Name) 
	{
		this.url2Name = url2Name;
	}
	/**
	 * 返回 链接2名称
	 * @return
	 */
	public String getUrl2Name() 
	{
		return this.url2Name;
	}
	public void setBlank1(String blank1) 
	{
		this.blank1 = blank1;
	}
	/**
	 * 返回 blank1
	 * @return
	 */
	public String getBlank1() 
	{
		return this.blank1;
	}
	public void setBlank2(String blank2) 
	{
		this.blank2 = blank2;
	}
	/**
	 * 返回 blank2
	 * @return
	 */
	public String getBlank2() 
	{
		return this.blank2;
	}
	public void setMode(String mode) 
	{
		this.mode = mode;
	}
	/**
	 * 返回 commit_info
	 * @return
	 */
	public String getMode() 
	{
		return this.mode;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CloudResourceInstance)) 
		{
			return false;
		}
		CloudResourceInstance rhs = (CloudResourceInstance) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.classId, rhs.classId)
		.append(this.title, rhs.title)
		.append(this.info, rhs.info)
		.append(this.urlPic, rhs.urlPic)
		.append(this.url1, rhs.url1)
		.append(this.url1Name, rhs.url1Name)
		.append(this.url2, rhs.url2)
		.append(this.url2Name, rhs.url2Name)
		.append(this.blank1, rhs.blank1)
		.append(this.blank2, rhs.blank2)
		.append(this.mode, rhs.mode)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.classId) 
		.append(this.title) 
		.append(this.info) 
		.append(this.urlPic) 
		.append(this.url1) 
		.append(this.url1Name) 
		.append(this.url2) 
		.append(this.url2Name) 
		.append(this.blank1) 
		.append(this.blank2) 
		.append(this.mode) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("classId", this.classId) 
		.append("title", this.title) 
		.append("info", this.info) 
		.append("urlPic", this.urlPic) 
		.append("url1", this.url1) 
		.append("url1Name", this.url1Name) 
		.append("url2", this.url2) 
		.append("url2Name", this.url2Name) 
		.append("blank1", this.blank1) 
		.append("blank2", this.blank2) 
		.append("mode", this.mode) 
		.toString();
	}
   
  

}