package com.hotent.platform.model.system;

import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:模版管理 Model对象
 * 开发公司:
 * 开发人员:pkq
 * 创建时间:2011-12-28 14:04:30
 */
public class SysTemplate extends BaseModel
{
	//手机短信
	public static Integer SHORT_TEMP_TYPE=1;
	//邮件
	public static Integer MAIL_TEMP_TYPE=2;
	//内部消息
	public static Integer INNER_TEMP_TYPE=3;
	// 主键Id
	protected Long templateId;
	// 类型
	protected Integer templateType;
	// 模版名称
	protected String name;
	// 模版内容
	protected String content;
	//是否默认模板
	protected Integer isDefault;
	//是否系统模板(系统模板不能删除)
	protected Integer isSystemTemp;

	public void setTemplateId(Long templateId) 
	{
		this.templateId = templateId;
	}
	/**
	 * 返回 主键Id
	 * @return
	 */
	public Long getTemplateId() 
	{
		return templateId;
	}

	public void setTemplateType(Integer templateType) 
	{
		this.templateType = templateType;
	}
	/**
	 * 返回 类型
	 * @return
	 */
	public Integer getTemplateType() 
	{
		return templateType;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 模版名称
	 * @return
	 */
	public String getName() 
	{
		return name;
	}

	public void setContent(String content) 
	{
		this.content = content;
	}
	/**
	 * 返回 模版内容
	 * @return
	 */
	public String getContent() 
	{
		return content;
	}

   
   	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getIsSystemTemp() {
		return isSystemTemp;
	}
	public void setIsSystemTemp(Integer isSystemTemp) {
		this.isSystemTemp = isSystemTemp;
	}
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof SysTemplate)) 
		{
			return false;
		}
		SysTemplate rhs = (SysTemplate) object;
		return new EqualsBuilder()
		.append(this.templateId, rhs.templateId)
		.append(this.templateType, rhs.templateType)
		.append(this.name, rhs.name)
		.append(this.content, rhs.content)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.templateId) 
		.append(this.templateType) 
		.append(this.name) 
		.append(this.content) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("templateId", this.templateId) 
		.append("templateType", this.templateType) 
		.append("name", this.name) 
		.append("content", this.content) 
		.toString();
	}
   
  

}