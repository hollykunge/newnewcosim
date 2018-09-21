package com.hotent.core.bpm.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 流程任务。
 * @author csx
 *
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ProcessTask
{
	//任务ID
	private String id;
	//任务名称
	private String name;
	//流程事项标题
	private String subject;
	//父任务Id
	private String parentTaskId;
	//任务描述
	private String description;
	//任务优先级
	private String priority;
	//创建时间
	private Date createTime;
	//任务持有人
	private String owner;
	//任务执行人
	private String assignee;
	//代理状态
	private String delegationState;
	//执行ID
	private String executionId;
	//流程实例ID
	private String processInstanceId;
	//流程定义ID
	private String processDefinitionId;
	//流程任务定义Key
	private String taskDefinitionKey;
	//到期时间
	private Date dueDate;
	//修正版本
	private Integer revision;
	
	//流程定义名称
	private String processName;
	
	//流程任务url
	private String taskUrl;
	
	public ProcessTask()
	{
		
	}
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getParentTaskId()
	{
		return parentTaskId;
	}
	public void setParentTaskId(String parentTaskId)
	{
		this.parentTaskId = parentTaskId;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getPriority()
	{
		return priority;
	}
	public void setPriority(String priority)
	{
		this.priority = priority;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	public String getOwner()
	{
		return owner;
	}
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	public String getAssignee()
	{
		return assignee;
	}
	public void setAssignee(String assignee)
	{
		this.assignee = assignee;
	}
	public String getDelegationState()
	{
		return delegationState;
	}
	public void setDelegationState(String delegationState)
	{
		this.delegationState = delegationState;
	}
	public String getExecutionId()
	{
		return executionId;
	}
	public void setExecutionId(String executionId)
	{
		this.executionId = executionId;
	}
	public String getProcessInstanceId()
	{
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId)
	{
		this.processInstanceId = processInstanceId;
	}
	public String getProcessDefinitionId()
	{
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId)
	{
		this.processDefinitionId = processDefinitionId;
	}
	public String getTaskDefinitionKey()
	{
		return taskDefinitionKey;
	}
	public void setTaskDefinitionKey(String taskDefinitionKey)
	{
		this.taskDefinitionKey = taskDefinitionKey;
	}
	public Date getDueDate()
	{
		return dueDate;
	}
	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public Integer getRevision()
	{
		return revision;
	}

	public void setRevision(Integer revision)
	{
		this.revision = revision;
	}

	public String getProcessName()
	{
		return processName;
	}

	public void setProcessName(String processName)
	{
		this.processName = processName;
	}

	public String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}
}
