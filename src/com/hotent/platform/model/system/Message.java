package com.hotent.platform.model.system;

import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 对象功能:消息设置 Model对象 开发公司: 开发人员:csx 创建时间:2012-02-08 16:45:56
 */
public class Message extends BaseModel {
	// 表名称
	public static final String TABLE_NAME = "SYS_MESSAGE";
	/**
	 * 消息的类型
	 */
	public final static Integer MAIL_TYPE = 1;// 邮件信息
	public final static Integer MOBILE_TYPE = 2;// 手机短信
	public final static Integer INNER_TYPE = 3;// 站内消息
	// messageId
	protected Long messageId;
	// 标题
	protected String subject;
	// 收件人
	protected String receiver;
	// 抄送
	protected String copyTo;
	// 秘密抄送
	protected String bcc;
	// 发件人
	protected String fromUser;
	// 内容模版
	protected Long templateId;
	// 消息类型
	protected Integer messageType;
	// 跳转地址
	protected String jumpUrl;
	// 剩余时间
	protected String remainTime;
	//消息内容
	protected String content;
	// 模板内容
	protected SysTemplate sysTemplate;
	
	// 是否发送给流程发起人
	protected Integer sendToStartUser;

	protected String actDefId;
	protected String nodeId;

	public String getRemainTime() {
		if(remainTime==null)
			return "";
		return remainTime;
	}

	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}

	public String getJumpUrl() {
		if(jumpUrl==null)
			return "";
		return jumpUrl;
	}
	
	
	public Integer getSendToStartUser() {
		return sendToStartUser;
	}

	public void setSendToStartUser(Integer sendToStartUser) {
		this.sendToStartUser = sendToStartUser;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public SysTemplate getSysTemplate() {
		return sysTemplate;
	}

	public void setSysTemplate(SysTemplate sysTemplate) {
		this.sysTemplate = sysTemplate;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	/**
	 * 返回 messageId
	 * 
	 * @return
	 */
	public Long getMessageId() {
		return messageId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 返回 主题
	 * 
	 * @return
	 */
	public String getSubject() {
		return subject;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * 返回 收件人
	 * 
	 * @return
	 */
	public String getReceiver() {
		return receiver;
	}

	public void setCopyTo(String copyTo) {
		this.copyTo = copyTo;
	}

	/**
	 * 返回 抄送
	 * 
	 * @return
	 */
	public String getCopyTo() {
		return copyTo;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 返回 秘密抄送
	 * 
	 * @return
	 */
	public String getBcc() {
		return bcc;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	/**
	 * 返回 发件人
	 * 
	 * @return
	 */
	public String getFromUser() {
		return fromUser;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	/**
	 * 返回 内容模版
	 * 
	 * @return
	 */
	public Long getTemplateId() {
		return templateId;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	/**
	 * 返回 消息类型
	 * 
	 * @return
	 */
	public Integer getMessageType() {
		return messageType;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Message)) {
			return false;
		}
		Message rhs = (Message) object;
		return new EqualsBuilder().append(this.messageId, rhs.messageId).append(this.subject, rhs.subject).append(this.receiver, rhs.receiver)
				.append(this.copyTo, rhs.copyTo).append(this.bcc, rhs.bcc).append(this.fromUser, rhs.fromUser).append(this.templateId, rhs.templateId)
				.append(this.messageType, rhs.messageType).isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.messageId).append(this.subject).append(this.receiver).append(this.copyTo)
				.append(this.bcc).append(this.fromUser).append(this.templateId).append(this.messageType).toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("messageId", this.messageId).append("subject", this.subject).append("receiver", this.receiver)
				.append("copyTo", this.copyTo).append("bcc", this.bcc).append("fromUser", this.fromUser).append("templateId", this.templateId)
				.append("messageType", this.messageType).toString();
	}

}