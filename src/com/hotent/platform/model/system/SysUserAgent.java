package com.hotent.platform.model.system;

import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:SYS_USER_AGENT Model对象
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2011-12-27 11:54:22
 */
public class SysUserAgent extends BaseModel
{
	/**
	 * 是否全部权限1=是
	 */
	public static short IS_ALL_FLAG = 1;
	/**
	 * 是否全部权限0=否
	 */
	public static short IS_NOT_ALL_FLAG = 0;
	
	// agentid
	protected Long agentid;
	// agentuserid
	protected Long agentuserid;
	// touserid
	protected Long touserid;
	// tofullname
	protected String tofullname;
	// starttime
	protected java.util.Date starttime;
	// endtime
	protected java.util.Date endtime;
	// 1=是

	protected Short isall;
	// 1=是

	protected Short isvalid;

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

	public void setTofullname(String tofullname) 
	{
		this.tofullname = tofullname;
	}
	/**
	 * 返回 tofullname
	 * @return
	 */
	public String getTofullname() 
	{
		return tofullname;
	}

	public void setStarttime(java.util.Date starttime) 
	{
		this.starttime = starttime;
	}
	/**
	 * 返回 starttime
	 * @return
	 */
	public java.util.Date getStarttime() 
	{
		return starttime;
	}

	public void setEndtime(java.util.Date endtime) 
	{
		this.endtime = endtime;
	}
	/**
	 * 返回 endtime
	 * @return
	 */
	public java.util.Date getEndtime() 
	{
		return endtime;
	}

	public void setIsall(Short isall) 
	{
		this.isall = isall;
	}
	/**
	 * 返回 1=是

	 * @return
	 */
	public Short getIsall() 
	{
		return isall;
	}

	public void setIsvalid(Short isvalid) 
	{
		this.isvalid = isvalid;
	}
	/**
	 * 返回 1=是

	 * @return
	 */
	public Short getIsvalid() 
	{
		return isvalid;
	}

   
   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof SysUserAgent)) 
		{
			return false;
		}
		SysUserAgent rhs = (SysUserAgent) object;
		return new EqualsBuilder()
		.append(this.agentid, rhs.agentid)
		.append(this.agentuserid, rhs.agentuserid)
		.append(this.touserid, rhs.touserid)
		.append(this.tofullname, rhs.tofullname)
		.append(this.starttime, rhs.starttime)
		.append(this.endtime, rhs.endtime)
		.append(this.isall, rhs.isall)
		.append(this.isvalid, rhs.isvalid)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.agentid) 
		.append(this.agentuserid) 
		.append(this.touserid) 
		.append(this.tofullname) 
		.append(this.starttime) 
		.append(this.endtime) 
		.append(this.isall) 
		.append(this.isvalid) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("agentid", this.agentid) 
		.append("agentuserid", this.agentuserid) 
		.append("touserid", this.touserid) 
		.append("tofullname", this.tofullname) 
		.append("starttime", this.starttime) 
		.append("endtime", this.endtime) 
		.append("isall", this.isall) 
		.append("isvalid", this.isvalid) 
		.toString();
	}
   
  

}