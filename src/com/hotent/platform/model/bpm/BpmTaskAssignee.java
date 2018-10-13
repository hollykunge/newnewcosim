package com.hotent.platform.model.bpm;

import java.util.ArrayList;
import java.util.List;
import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:交办任务记录表 Model对象
 * 开发者:云雀小组
 * 开发人员:hotent
 * 创建时间:2012-10-08 08:46:26
 */
public class BpmTaskAssignee extends BaseModel
{
	// ID
	protected Long  id;
	// 任务所属人ID
	protected Long  userId;
	//任务交办人姓名
	protected String userName;
	// 交办人ID
	protected Long  assigneeId;
	// 任务ID
	protected String  taskId;
	// 任务名称
	protected String taskName;
	// 流程定义标题
	protected String  subject;
	// 任务执行状态
	protected Short  taskStatus;
	// 交办人姓名
	protected String assigneeName;
	//交办时间
	protected java.util.Date assigneeTime;
	//流程运行ID
	protected Long runId;
	//备注
	protected String memo;
	
	public static final Short TASK_NO_EXC=0;
	public static final Short TASK_EXC=1;
	public void setId(Long id) 
	{
		this.id = id;
	}
	/**
	 * 返回 ID
	 * @return
	 */
	public Long getId() 
	{
		return this.id;
	}
	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}
	/**
	 * 返回 任务所属人ID
	 * @return
	 */
	public Long getUserId() 
	{
		return this.userId;
	}
	public void setAssigneeId(Long assigneeId) 
	{
		this.assigneeId = assigneeId;
	}
	/**
	 * 返回 交办人ID
	 * @return
	 */
	public Long getAssigneeId() 
	{
		return this.assigneeId;
	}
	public void setTaskId(String taskId) 
	{
		this.taskId = taskId;
	}
	
	/**
	 * 返回 交办人 姓名
	 * @return
	 */
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	
	/**
	 * 返回 任务ID
	 * @return
	 */
	public String getTaskId() 
	{
		return this.taskId;
	}
	public void setSubject(String subject) 
	{
		this.subject = subject;
	}
	
	/**
	 * 返回任务名称
	 * @return
	 */
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 返回 流程定义ID
	 * @return
	 */
	public String getSubject() 
	{
		return this.subject;
	}
	public void setTaskStatus(Short taskStatus) 
	{
		this.taskStatus = taskStatus;
	}
	/**
	 * 返回 任务执行状态
	 * @return
	 */
	public Short getTaskStatus() 
	{
		return this.taskStatus;
	}
	
	/**
	 * 返回 交办时间
	 * @return
	 */
   	public java.util.Date getAssigneeTime() {
		return assigneeTime;
	}
	public void setAssigneeTime(java.util.Date assigneeTime) {
		this.assigneeTime = assigneeTime;
	}
	
	/**
	 * 返回  流程运行ID
	 * @return
	 */
	public Long getRunId() {
		return runId;
	}
	public void setRunId(Long runId) {
		this.runId = runId;
	}
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BpmTaskAssignee)) 
		{
			return false;
		}
		BpmTaskAssignee rhs = (BpmTaskAssignee) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.userId, rhs.userId)
		.append(this.assigneeId, rhs.assigneeId)
		.append(this.taskId, rhs.taskId)
		.append(this.subject, rhs.subject)
		.append(this.taskStatus, rhs.taskStatus)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.userId) 
		.append(this.assigneeId) 
		.append(this.taskId) 
		.append(this.subject) 
		.append(this.taskStatus) 
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
		.append("assigneeId", this.assigneeId) 
		.append("taskId", this.taskId) 
		.append("subject", this.subject) 
		.append("taskStatus", this.taskStatus) 
		.toString();
	}
   
  

}