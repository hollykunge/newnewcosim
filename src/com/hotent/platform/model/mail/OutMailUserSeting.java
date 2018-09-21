package com.hotent.platform.model.mail;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:邮箱 Model对象
 * 开发公司:
 * 开发人员:zyp
 * 创建时间:2012-04-09 13:43:50
 */
public class OutMailUserSeting extends BaseModel
{
	// id
	protected Long id;
	// 用户ID
	protected Long userId;
	// 用户名称
	protected String userName;
	// 外部邮件地址
	protected String mailAddress;
	// 外部邮件密码
	protected String mailPass;
	// smt主机
	protected String smtpHost;
	// smt端口
	protected String smtpPort;
	// pop主机
	protected String popHost;
	// pop端口
	protected String popPort;
	// imap主机
	protected String imapHost;
	// imap端口
	protected String imapPort;
	// 是否默认
	protected Integer isDefault;
	//接收邮件服务器类型
	protected String mailType;
	
	

	protected Long parentId;
	// 是否父类,主要用于树的展示时用
	protected String isParent;
	// 是否叶子结点(0否,1是),主要用于数据库保存
	protected Integer isLeaf;
	
	protected String open="true";
	//邮件类型，页面用;1:收件箱;2:发件箱;3:草稿箱;4:垃圾箱
	protected Integer types;
	
	
	
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
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
		return id;
	}

	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}
	/**
	 * 返回 用户ID
	 * @return
	 */
	public Long getUserId() 
	{
		return userId;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
	}
	/**
	 * 返回 用户名称
	 * @return
	 */
	public String getUserName() 
	{
		return userName;
	}

	public void setMailAddress(String mailAddress) 
	{
		this.mailAddress = mailAddress;
	}
	/**
	 * 返回 外部邮件地址
	 * @return
	 */
	public String getMailAddress() 
	{
		return mailAddress;
	}

	public void setMailPass(String mailPass) 
	{
		this.mailPass = mailPass;
	}
	/**
	 * 返回 外部邮件密码
	 * @return
	 */
	public String getMailPass() 
	{
		return mailPass;
	}

	public void setSmtpHost(String smtpHost) 
	{
		this.smtpHost = smtpHost;
	}
	/**
	 * 返回 smt主机
	 * @return
	 */
	public String getSmtpHost() 
	{
		return smtpHost;
	}

	public void setSmtpPort(String smtpPort) 
	{
		this.smtpPort = smtpPort;
	}
	/**
	 * 返回 smt端口
	 * @return
	 */
	public String getSmtpPort() 
	{
		return smtpPort;
	}

	public void setPopHost(String popHost) 
	{
		this.popHost = popHost;
	}
	/**
	 * 返回 pop主机
	 * @return
	 */
	public String getPopHost() 
	{
		return popHost;
	}

	public void setPopPort(String popPort) 
	{
		this.popPort = popPort;
	}
	/**
	 * 返回 pop端口
	 * @return
	 */
	public String getPopPort() 
	{
		return popPort;
	}

	public void setImapHost(String imapHost) 
	{
		this.imapHost = imapHost;
	}
	/**
	 * 返回 imap主机
	 * @return
	 */
	public String getImapHost() 
	{
		return imapHost;
	}

	public void setImapPort(String imapPort) 
	{
		this.imapPort = imapPort;
	}
	/**
	 * 返回 imap端口
	 * @return
	 */
	public String getImapPort() 
	{
		return imapPort;
	}

	public void setIsDefault(Integer isDefault) 
	{
		this.isDefault = isDefault;
	}
	/**
	 * 返回 是否默认
	 * @return
	 */
	public Integer getIsDefault() 
	{
		return isDefault;
	}

   
   	public Integer getTypes() {
		return types;
	}
	public void setTypes(Integer types) {
		this.types = types;
	}
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof OutMailUserSeting)) 
		{
			return false;
		}
		OutMailUserSeting rhs = (OutMailUserSeting) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.userId, rhs.userId)
		.append(this.userName, rhs.userName)
		.append(this.mailAddress, rhs.mailAddress)
		.append(this.mailPass, rhs.mailPass)
		.append(this.smtpHost, rhs.smtpHost)
		.append(this.smtpPort, rhs.smtpPort)
		.append(this.popHost, rhs.popHost)
		.append(this.popPort, rhs.popPort)
		.append(this.imapHost, rhs.imapHost)
		.append(this.imapPort, rhs.imapPort)
		.append(this.isDefault, rhs.isDefault)
		.isEquals();
	}

	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.userId) 
		.append(this.userName) 
		.append(this.mailAddress) 
		.append(this.mailPass) 
		.append(this.smtpHost) 
		.append(this.smtpPort) 
		.append(this.popHost) 
		.append(this.popPort) 
		.append(this.imapHost) 
		.append(this.imapPort) 
		.append(this.isDefault) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("userId", this.userId) 
		.append("userName", this.userName) 
		.append("mailAddress", this.mailAddress) 
		.append("mailPass", this.mailPass) 
		.append("smtpHost", this.smtpHost) 
		.append("smtpPort", this.smtpPort) 
		.append("popHost", this.popHost) 
		.append("popPort", this.popPort) 
		.append("imapHost", this.imapHost) 
		.append("imapPort", this.imapPort) 
		.append("isDefault", this.isDefault) 
		.toString();
	}
   
  

}