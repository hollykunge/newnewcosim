package com.hotent.platform.service.bpm.listener;

import java.util.Date;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.commons.lang.StringUtils;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.bpm.util.BpmUtil;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.bpm.BpmProStatusDao;
import com.hotent.platform.dao.bpm.ExecutionStackDao;
import com.hotent.platform.dao.bpm.TaskDao;
import com.hotent.platform.model.bpm.ExecutionStack;
import com.hotent.platform.model.bpm.TaskFork;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.bpm.thread.TaskThreadService;

/**
 * 任务完成时执行的事件。
 * @author ray
 *
 */
public class TaskCompleteListener extends BaseTaskListener {

	TaskOpinionService taskOpinionService=(TaskOpinionService) AppUtil.getBean(TaskOpinionService.class);
	BpmProStatusDao bpmProStatusDao=(BpmProStatusDao) AppUtil.getBean(BpmProStatusDao.class);
	ExecutionStackDao executionStackDao=(ExecutionStackDao)AppUtil.getBean(ExecutionStackDao.class);
	TaskDao taskDao=(TaskDao)AppUtil.getBean(TaskDao.class);
	
	@Override
	protected void execute(DelegateTask delegateTask, String actDefId,String nodeId) {
		
		String taskAssignee=delegateTask.getAssignee();
		//将当前任务的人员保留下来,在TaskCreateListener中获取，作为计算用户使用。
		setPreUser(taskAssignee);
		//获取当前任务的分发的令牌, value as T_1 or T_1_2
		String token=(String)delegateTask.getVariableLocal(TaskFork.TAKEN_VAR_NAME);
		if(token!=null){
			//放置会签线程，以使得在后续产生的任务中使用
			TaskThreadService.setToken(token);
		}
		
		//为了解决在任务自由跳转或回退时，若流程实例有多个相同Key的任务，会把相同的任务删除。
		ProcessCmd processCmd=TaskThreadService.getProcessCmd();
		if(processCmd!=null && (processCmd.isBack()>0 || StringUtils.isNotEmpty(processCmd.getDestTask()))){
			taskDao.updateNewTaskDefKeyByInstIdNodeId(delegateTask.getTaskDefinitionKey() + "_1",delegateTask.getTaskDefinitionKey(),delegateTask.getProcessInstanceId());
		}
		//更新执行堆栈里的执行人员及完成时间等
		updateExecutionStack(delegateTask.getProcessInstanceId(),delegateTask.getTaskDefinitionKey(),token);
		//更新任务意见。
		updTaskOpinion(delegateTask);
		//更新流程节点状态。
		updNodeStatus(nodeId,delegateTask);
	}
	
	/**
	 * 更新执行堆栈里的执行人员及完成时间等
	 * @param instanceId 流程实例ID
	 * @param nodeId 节点IDeas
	 * @param token　令牌
	 */
	private void updateExecutionStack(String instanceId,String nodeId,String token){
		ExecutionStack executionStack=executionStackDao.getLastestStack(instanceId, nodeId, token);
		if(executionStack!=null){
			ISysUser curUser=ContextUtil.getCurrentUser();
//			if(StringUtils.isNotEmpty(executionStack.getAssignees())){
//				executionStack.setAssignees(executionStack.getAssignees() + "," + curUser.getUserId());
//			}else{
				executionStack.setAssignees(curUser.getUserId().toString());
//			}
			executionStack.setEndTime(new Date());
			executionStackDao.update(executionStack);
		}
		
	}
	
	/**
	 * 根据流程节点的状态。
	 * @param nodeId
	 * @param delegateTask
	 */
	private void updNodeStatus(String nodeId,DelegateTask delegateTask){
		boolean isMuliti=BpmUtil.isMultiTask(delegateTask);
		//非会签节点,更新节点的状态。
		if(!isMuliti){
			String actInstanceId=delegateTask.getProcessInstanceId();
			//更新节点状态。
			Short approvalStatus=(Short)delegateTask.getVariable(BpmConst.NODE_APPROVAL_STATUS + "_"+delegateTask.getTaskDefinitionKey());
			if(approvalStatus==null){
				approvalStatus = (Short) TaskThreadService.getVariables().get(BpmConst.NODE_APPROVAL_STATUS + "_"+delegateTask.getTaskDefinitionKey());
			}
			bpmProStatusDao.updStatus(new Long( actInstanceId), nodeId, approvalStatus);
		}
	}
	
	/**
	 * 更新任务意见。
	 * @param delegateTask
	 */
	private void updTaskOpinion(DelegateTask  delegateTask){
		TaskOpinion taskOpinion=taskOpinionService.getByTaskId(new Long(delegateTask.getId()));
		
		if(taskOpinion==null) return ;
		
		String nodeId=delegateTask.getTaskDefinitionKey();
	
		Short approvalStatus=(Short)delegateTask.getVariable(BpmConst.NODE_APPROVAL_STATUS + "_"+nodeId);
		String approvalContent=(String)delegateTask.getVariable(BpmConst.NODE_APPROVAL_CONTENT + "_"+nodeId);
		
		ISysUser sysUser=ContextUtil.getCurrentUser();
		Long userId = SystemConst.SYSTEMUSERID;
		String userName = SystemConst.SYSTEMUSERNAME;
		if(sysUser!=null){
			userId = sysUser.getUserId();
			userName = sysUser.getFullname();
		}

		taskOpinion.setExeUserId(userId);
		taskOpinion.setExeFullname(userName);
		if(approvalStatus==null){
			taskOpinion.setCheckStatus(TaskOpinion.STATUS_AGREE);
		}else{
			taskOpinion.setCheckStatus(approvalStatus);
		}
		taskOpinion.setOpinion(approvalContent);
		taskOpinion.setEndTime(new Date());
		taskOpinion.setDurTime(taskOpinion.getEndTime().getTime()-taskOpinion.getStartTime().getTime());
		
		taskOpinionService.update(taskOpinion);
		
	}
	
	/**
	 * 在任务完成之时，将当前任务执行人放到上下文中保存起来。
	 * 以便下一个任务获取到上次任务的执行人。
	 * @param assignee
	 */
	private void setPreUser(String assignee){
		TaskThreadService.cleanTaskUser();
		if(StringUtil.isNotEmpty(assignee)){
			TaskThreadService.setPreTaskUser(assignee);
		}
	}

	@Override
	protected int getScriptType() {
		return BpmConst.EndScript;
	}

}
