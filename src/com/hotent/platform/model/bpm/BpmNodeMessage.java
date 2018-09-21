package com.hotent.platform.model.bpm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:流程节点邮件 Model对象
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2012-02-08 18:07:00
 */
@XmlRootElement(name = "bpmNodeMessage")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNodeMessage extends BaseModel implements Cloneable
{
	//表名称
	public static final String TABLE_NAME="BPM_NODE_MESSAGE";
	// id
	@XmlAttribute
	protected Long id;
	// messageId
	@XmlAttribute
	protected Long messageId;
	// 流程定义ID
	@XmlAttribute
	protected String actDefId;
	// 流程节点ID
	@XmlAttribute
	protected String nodeId;

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

	public void setMessageId(Long messageId) 
	{
		this.messageId = messageId;
	}
	/**
	 * 返回 messageId
	 * @return
	 */
	public Long getMessageId() 
	{
		return messageId;
	}

	public void setActDefId(String actDefId) 
	{
		this.actDefId = actDefId;
	}
	/**
	 * 返回 流程定义ID
	 * @return
	 */
	public String getActDefId() 
	{
		return actDefId;
	}

	public void setNodeId(String nodeId) 
	{
		this.nodeId = nodeId;
	}
	/**
	 * 返回 流程节点ID
	 * @return
	 */
	public String getNodeId() 
	{
		return nodeId;
	}

   
   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BpmNodeMessage)) 
		{
			return false;
		}
		BpmNodeMessage rhs = (BpmNodeMessage) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.messageId, rhs.messageId)
		.append(this.actDefId, rhs.actDefId)
		.append(this.nodeId, rhs.nodeId)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.messageId) 
		.append(this.actDefId) 
		.append(this.nodeId) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("messageId", this.messageId) 
		.append("actDefId", this.actDefId) 
		.append("nodeId", this.nodeId) 
		.toString();
	}
   
	public Object clone()
	{
		BpmNodeMessage obj=null;
		try{
			obj=(BpmNodeMessage)super.clone();
		}catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
  

}