package com.hotent.platform.model.system;

import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:系统日志 Model对象
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2011-11-26 11:35:03
 */
public class SysAudit extends BaseModel{
	// auditId
	protected Long auditId;
	// 操作名称
	protected String opName;
	// 执行时间
	protected java.util.Date exeTime;
	// 执行人ID
	protected Long executorId;
	// 执行人
	protected String executor;
	// IP
	protected String fromIp;
	// 执行方法
	protected String exeMethod;
	// 请求URL
	protected String requestURI;
	// 请求参数
	protected String reqParams;

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}
	/**
	 * 返回 auditId
	 * @return
	 */
	public Long getAuditId() {
		return auditId;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}
	/**
	 * 返回 操作名称
	 * @return
	 */
	public String getOpName() {
		return opName;
	}

	public void setExeTime(java.util.Date exeTime) {
		this.exeTime = exeTime;
	}
	/**
	 * 返回 执行时间
	 * @return
	 */
	public java.util.Date getExeTime() {
		return exeTime;
	}

	public void setExecutorId(Long executorId) {
		this.executorId = executorId;
	}
	/**
	 * 返回 执行人ID
	 * @return
	 */
	public Long getExecutorId() {
		return executorId;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}
	/**
	 * 返回 执行人
	 * @return
	 */
	public String getExecutor() {
		return executor;
	}

	public void setFromIp(String fromIp) {
		this.fromIp = fromIp;
	}
	/**
	 * 返回 IP
	 * @return
	 */
	public String getFromIp() {
		return fromIp;
	}

	public void setExeMethod(String exeMethod) {
		this.exeMethod = exeMethod;
	}
	/**
	 * 返回 执行方法
	 * @return
	 */
	public String getExeMethod() {
		return exeMethod;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	/**
	 * 返回 请求URL
	 * @return
	 */
	public String getRequestURI() {
		return requestURI;
	}

	public void setReqParams(String reqParams) {
		this.reqParams = reqParams;
	}
	/**
	 * 返回 请求参数
	 * @return
	 */
	public String getReqParams() {
		return reqParams;
	}

   
   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysAudit)) {
			return false;
		}
		SysAudit rhs = (SysAudit) object;
		return new EqualsBuilder()
		.append(this.auditId, rhs.auditId)
		.append(this.opName, rhs.opName)
		.append(this.exeTime, rhs.exeTime)
		.append(this.executorId, rhs.executorId)
		.append(this.executor, rhs.executor)
		.append(this.fromIp, rhs.fromIp)
		.append(this.exeMethod, rhs.exeMethod)
		.append(this.requestURI, rhs.requestURI)
		.append(this.reqParams, rhs.reqParams)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.auditId) 
		.append(this.opName) 
		.append(this.exeTime) 
		.append(this.executorId) 
		.append(this.executor) 
		.append(this.fromIp) 
		.append(this.exeMethod) 
		.append(this.requestURI) 
		.append(this.reqParams) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("auditId", this.auditId) 
		.append("opName", this.opName) 
		.append("exeTime", this.exeTime) 
		.append("executorId", this.executorId) 
		.append("executor", this.executor) 
		.append("fromIp", this.fromIp) 
		.append("exeMethod", this.exeMethod) 
		.append("requestURI", this.requestURI) 
		.append("reqParams", this.reqParams) 
		.toString();
	}
   
  

}