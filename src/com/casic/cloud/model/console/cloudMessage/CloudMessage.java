package com.casic.cloud.model.console.cloudMessage;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.casic.cloud.model.config.info.Info;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:CLOUD_MESSAGE Model对象
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-19 13:32:09
 */
public class CloudMessage extends BaseModel
{
	// id
	protected Long  id;
	// sender_id
	protected String  senderId;
	// sendent_id
	protected Long  sendentId;
	// receiver_id
	protected String  receiverId;
	// receiveent_id
	protected Long  receiveentId;
	// sendtime
	protected java.util.Date  sendtime;
	// outtime
	protected java.util.Date  outtime;
	// result
	protected int  result;
	// link
	protected String  link;
	// type
	protected int  type;
	// content
	protected String  content;
	// source_id
	protected Long  sourceId;
	// title
	protected String  title;
	// readtime
	protected java.util.Date  readtime;
	//发送企业
	protected Info sendEnt;
	
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
	public void setSenderId(String senderId) 
	{
		this.senderId = senderId;
	}
	/**
	 * 返回 sender_id
	 * @return
	 */
	public String getSenderId() 
	{
		return this.senderId;
	}
	public void setSendentId(Long sendentId) 
	{
		this.sendentId = sendentId;
	}
	/**
	 * 返回 sendent_id
	 * @return
	 */
	public Long getSendentId() 
	{
		return this.sendentId;
	}
	public void setReceiverId(String receiverId) 
	{
		this.receiverId = receiverId;
	}
	/**
	 * 返回 receiver_id
	 * @return
	 */
	public String getReceiverId() 
	{
		return this.receiverId;
	}
	public void setReceiveentId(Long receiveentId) 
	{
		this.receiveentId = receiveentId;
	}
	/**
	 * 返回 receiveent_id
	 * @return
	 */
	public Long getReceiveentId() 
	{
		return this.receiveentId;
	}
	public void setSendtime(java.util.Date sendtime) 
	{
		this.sendtime = sendtime;
	}
	/**
	 * 返回 sendtime
	 * @return
	 */
	public java.util.Date getSendtime() 
	{
		return this.sendtime;
	}
	public void setOuttime(java.util.Date outtime) 
	{
		this.outtime = outtime;
	}
	/**
	 * 返回 outtime
	 * @return
	 */
	public java.util.Date getOuttime() 
	{
		return this.outtime;
	}
	public void setResult(int result) 
	{
		this.result = result;
	}
	/**
	 * 返回 result
	 * @return
	 */
	public int getResult() 
	{
		return this.result;
	}
	public void setLink(String link) 
	{
		this.link = link;
	}
	/**
	 * 返回 link
	 * @return
	 */
	public String getLink() 
	{
		return this.link;
	}
	public void setType(int type) 
	{
		this.type = type;
	}
	/**
	 * 返回 type
	 * @return
	 */
	public int getType() 
	{
		return this.type;
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
	public void setSourceId(Long sourceId) 
	{
		this.sourceId = sourceId;
	}
	/**
	 * 返回 source_id
	 * @return
	 */
	public Long getSourceId() 
	{
		return this.sourceId;
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
	public void setReadtime(java.util.Date readtime) 
	{
		this.readtime = readtime;
	}
	/**
	 * 返回 readtime
	 * @return
	 */
	public java.util.Date getReadtime() 
	{
		return this.readtime;
	}
	
   	public Info getSendEnt() {
		return sendEnt;
	}
	public void setSendEnt(Info sendEnt) {
		this.sendEnt = sendEnt;
	}
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CloudMessage)) 
		{
			return false;
		}
		CloudMessage rhs = (CloudMessage) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.senderId, rhs.senderId)
		.append(this.sendentId, rhs.sendentId)
		.append(this.receiverId, rhs.receiverId)
		.append(this.receiveentId, rhs.receiveentId)
		.append(this.sendtime, rhs.sendtime)
		.append(this.outtime, rhs.outtime)
		.append(this.result, rhs.result)
		.append(this.link, rhs.link)
		.append(this.type, rhs.type)
		.append(this.content, rhs.content)
		.append(this.sourceId, rhs.sourceId)
		.append(this.title, rhs.title)
		.append(this.readtime, rhs.readtime)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.senderId) 
		.append(this.sendentId) 
		.append(this.receiverId) 
		.append(this.receiveentId) 
		.append(this.sendtime) 
		.append(this.outtime) 
		.append(this.result) 
		.append(this.link) 
		.append(this.type) 
		.append(this.content) 
		.append(this.sourceId) 
		.append(this.title) 
		.append(this.readtime) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("senderId", this.senderId) 
		.append("sendentId", this.sendentId) 
		.append("receiverId", this.receiverId) 
		.append("receiveentId", this.receiveentId) 
		.append("sendtime", this.sendtime) 
		.append("outtime", this.outtime) 
		.append("result", this.result) 
		.append("link", this.link) 
		.append("type", this.type) 
		.append("content", this.content) 
		.append("sourceId", this.sourceId) 
		.append("title", this.title) 
		.append("readtime", this.readtime) 
		.toString();
	}
   
  

}