package com.casic.cloud.model.reg.register;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:cloud_user_register Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 13:53:40
 */
public class Register extends BaseModel
{
	// id
	protected Long  id;
	// 姓名
	protected String  name;
	// 性别
	protected Long  sex;
	// 邮箱
	protected String  email;
	// 身份证号
	protected String  identity;
	// 手机号码
	protected Long  tellphone;
	// 获奖证书
	protected String  credential;
	// 作品介绍
	protected String  introduce;
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
	 * 返回 姓名
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setSex(Long sex) 
	{
		this.sex = sex;
	}
	/**
	 * 返回 性别
	 * @return
	 */
	public Long getSex() 
	{
		return this.sex;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}
	/**
	 * 返回 邮箱
	 * @return
	 */
	public String getEmail() 
	{
		return this.email;
	}
	public void setIdentity(String identity) 
	{
		this.identity = identity;
	}
	/**
	 * 返回 身份证号
	 * @return
	 */
	public String getIdentity() 
	{
		return this.identity;
	}
	public void setTellphone(Long tellphone) 
	{
		this.tellphone = tellphone;
	}
	/**
	 * 返回 手机号码
	 * @return
	 */
	public Long getTellphone() 
	{
		return this.tellphone;
	}
	public void setCredential(String credential) 
	{
		this.credential = credential;
	}
	/**
	 * 返回 获奖证书
	 * @return
	 */
	public String getCredential() 
	{
		return this.credential;
	}
	public void setIntroduce(String introduce) 
	{
		this.introduce = introduce;
	}
	/**
	 * 返回 作品介绍
	 * @return
	 */
	public String getIntroduce() 
	{
		return this.introduce;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Register)) 
		{
			return false;
		}
		Register rhs = (Register) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.sex, rhs.sex)
		.append(this.email, rhs.email)
		.append(this.identity, rhs.identity)
		.append(this.tellphone, rhs.tellphone)
		.append(this.credential, rhs.credential)
		.append(this.introduce, rhs.introduce)
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
		.append(this.sex) 
		.append(this.email) 
		.append(this.identity) 
		.append(this.tellphone) 
		.append(this.credential) 
		.append(this.introduce) 
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
		.append("sex", this.sex) 
		.append("email", this.email) 
		.append("identity", this.identity) 
		.append("tellphone", this.tellphone) 
		.append("credential", this.credential) 
		.append("introduce", this.introduce) 
		.toString();
	}
   
  

}