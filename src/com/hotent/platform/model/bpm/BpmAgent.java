package com.hotent.platform.model.bpm;

import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:流程代理 Model对象
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2012-01-07 17:31:47
 */
public class BpmAgent extends BaseModel implements Cloneable
{
	// id
	protected Long id;
	// agentid
	protected Long agentid;
	// 流程定义KEY
	protected String defKey;
	// agentuserid
	protected Long agentuserid;
	// touserid
	protected Long touserid;
	// actdefid
	protected String actdefid;
	// 流程标题冗余,展示信息时用
	protected String subject;
	
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
		return id;
	}

	public void setAgentid(Long agentid) 
	{
		this.agentid = agentid;
	}
	/**
	 * 返回 agentid
	 * @return
	 */
	public Long getAgentid() 
	{
		return agentid;
	}

	public void setDefKey(String defKey) 
	{
		this.defKey = defKey;
	}
	/**
	 * 返回 流程定义KEY
	 * @return
	 */
	public String getDefKey() 
	{
		return defKey;
	}

	public void setAgentuserid(Long agentuserid) 
	{
		this.agentuserid = agentuserid;
	}
	/**
	 * 返回 agentuserid
	 * @return
	 */
	public Long getAgentuserid() 
	{
		return agentuserid;
	}

	public void setTouserid(Long touserid) 
	{
		this.touserid = touserid;
	}
	/**
	 * 返回 touserid
	 * @return
	 */
	public Long getTouserid() 
	{
		return touserid;
	}

	public void setActdefid(String actdefid) 
	{
		this.actdefid = actdefid;
	}
	/**
	 * 返回 actdefid
	 * @return
	 */
	public String getActdefid() 
	{
		return actdefid;
	}

   
   	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BpmAgent)) 
		{
			return false;
		}
		BpmAgent rhs = (BpmAgent) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.agentid, rhs.agentid)
		.append(this.defKey, rhs.defKey)
		.append(this.agentuserid, rhs.agentuserid)
		.append(this.touserid, rhs.touserid)
		.append(this.actdefid, rhs.actdefid)
		.append(this.subject, rhs.subject)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.agentid) 
		.append(this.defKey) 
		.append(this.agentuserid) 
		.append(this.touserid) 
		.append(this.actdefid) 
		.append(this.subject) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("agentid", this.agentid) 
		.append("defid", this.defKey) 
		.append("agentuserid", this.agentuserid) 
		.append("touserid", this.touserid) 
		.append("actdefid", this.actdefid) 
		.append("subject", this.subject) 
		.toString();
	}
	
	public Object clone()
	{
		BpmAgent obj=null;
		try{
			obj=(BpmAgent)super.clone();
		}catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

}