package com.hotent.platform.service.mail.model;
/**
 * 外部邮箱设置 实体
 * @author Administrator
 *
 */
public class MailSetting {
	//
	protected 	Long	id;
	//邮箱地址
	protected	String	mailAddress;
	//用户名
	protected	String	userName;
	//邮箱账号名称
	protected	String	userAccount;
	//
	protected	String	mailPass;
	//发送端主机
	protected	String	sendHost;
	//发送端服务器端口号
	protected 	String	sendPort;
	//接收端服务器类型
	protected 	String 	receiverType;
	//接收端服务器主机
	protected 	String	receiverHost;
	//接收端服务器端口号
	protected	String	receiverPort;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getMailPass() {
		return mailPass;
	}
	public void setMailPass(String mailPass) {
		this.mailPass = mailPass;
	}
	public String getSendHost() {
		return sendHost;
	}
	public void setSendHost(String sendHost) {
		this.sendHost = sendHost;
	}
	public String getSendPort() {
		return sendPort;
	}
	public void setSendPort(String sendPort) {
		this.sendPort = sendPort;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
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
	
	
}
