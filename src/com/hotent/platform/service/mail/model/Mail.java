package com.hotent.platform.service.mail.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hotent.platform.controller.mail.MailAttachment;

public class Mail {
	protected 	long 	setId;
	//发件人地址
	protected	String	senderAddress;
	//发件人帐号
	protected	String	sender;
	//发件人邮箱地址
	protected	String	password;
	//邮件主题
	protected	String	subject;
	//邮件内容
	protected	String	content;
	//邮件发送时间
	protected	Date	sendDate;
	//收件人地址
	protected	String	receiverAddress;
	//抄送人地址
	protected	String	ccAddresses;
	//暗送人地址
	protected	String	bcCAddresses;
	//附件ID
	protected	String	fileIds;
	//发送服务器主机地址
	protected	String	senderHost;
	//发送服务器端口
	protected	String	senderPort;
	//接收服务器主机地址
	protected	String	receiverHost;
	//接收邮件服务器端口
	protected	String	receiverPort;
	//接收邮件服务器类型  共有两种类型 :pop3和imap
	protected	String	receiverType;
	//邮件附件
	public List<MailAttachment> files=new ArrayList<MailAttachment>();
	
	
	public long getSetId() {
		return setId;
	}
	public void setSetId(long setId) {
		this.setId = setId;
	}
	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getCcAddresses() {
		return ccAddresses;
	}
	public void setCcAddresses(String ccAddresses) {
		this.ccAddresses = ccAddresses;
	}
	public String getBcCAddresses() {
		return bcCAddresses;
	}
	public void setBcCAddresses(String bcCAddresses) {
		this.bcCAddresses = bcCAddresses;
	}
	public String getFileIds() {
		return fileIds;
	}
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	public String getSenderHost() {
		return senderHost;
	}
	public void setSenderHost(String senderHost) {
		this.senderHost = senderHost;
	}
	public String getSenderPort() {
		return senderPort;
	}
	public void setSenderPort(String senderPort) {
		this.senderPort = senderPort;
	}
	public String getReceiverHost() {
		return receiverHost;
	}
	public void setReceiverHost(String receiverHost) {
		this.receiverHost = receiverHost;
	}
	public String getReceiverPort() {
		return receiverPort;
	}
	public void setReceiverPort(String receiverPort) {
		this.receiverPort = receiverPort;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public List<MailAttachment> getFiles() {
		return files;
	}
	public void setFiles(List<MailAttachment> files) {
		this.files = files;
	}
	
	
	
}
