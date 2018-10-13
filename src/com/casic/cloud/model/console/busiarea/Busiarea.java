package com.casic.cloud.model.console.busiarea;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.casic.cloud.model.config.info.Info;
import com.casic.cloud.model.console.businessAreaGroup.BusinessAreaGroup;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:CLOUD_BUSINESS_AREA Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-19 13:31:05
 */
public class Busiarea extends BaseModel
{
	// id
	protected Long  id;
	// role_id
	protected String  roleId;
	// main_ent
	protected Long  mainEnt;
	// corp_ent
	protected Long  corpEnt;
	// state
	protected int  state;
	protected Long  groupid;
	//外键
	protected Info corpEnterprise;
	
	protected int type;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Info getCorpEnterprise() {
		return corpEnterprise;
	}
	public void setCorpEnterprise(Info corpEnterprise) {
		this.corpEnterprise = corpEnterprise;
	}
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
	public void setRoleId(String roleId) 
	{
		this.roleId = roleId;
	}
	/**
	 * 返回 role_id
	 * @return
	 */
	public String getRoleId() 
	{
		return this.roleId;
	}
	public void setMainEnt(Long mainEnt) 
	{
		this.mainEnt = mainEnt;
	}
	/**
	 * 返回 main_ent
	 * @return
	 */
	public Long getMainEnt() 
	{
		return this.mainEnt;
	}
	public void setCorpEnt(Long corpEnt) 
	{
		this.corpEnt = corpEnt;
	}
	/**
	 * 返回 corp_ent
	 * @return
	 */
	public Long getCorpEnt() 
	{
		return this.corpEnt;
	}
	public void setState(int state) 
	{
		this.state = state;
	}
	/**
	 * 返回 state
	 * @return
	 */
	public int getState() 
	{
		return this.state;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Busiarea)) 
		{
			return false;
		}
		Busiarea rhs = (Busiarea) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.roleId, rhs.roleId)
		.append(this.mainEnt, rhs.mainEnt)
		.append(this.corpEnt, rhs.corpEnt)
		.append(this.state, rhs.state)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.roleId) 
		.append(this.mainEnt) 
		.append(this.corpEnt) 
		.append(this.state) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("roleId", this.roleId) 
		.append("mainEnt", this.mainEnt) 
		.append("corpEnt", this.corpEnt) 
		.append("state", this.state) 
		.toString();
	}
 
	public Long getGroupid() {
		return groupid;
	}
	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}
   
  

}