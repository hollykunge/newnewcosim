package com.hotent.platform.service.bpm.listener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.bpm.TaskSignData;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.service.bpm.BpmProStatusService;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.bpm.TaskSignDataService;
import com.hotent.platform.service.bpm.thread.TaskThreadService;
import com.hotent.platform.service.bpm.thread.TaskUserAssignService;

/**
 * 会签任务监听器。
 * @author hotent
 *
 */
public class TaskSignCreateListener extends BaseTaskListener {
	
	private Logger logger=LoggerFactory.getLogger(TaskSignCreateListener.class);

	private TaskSignDataService taskSignDataService=(TaskSignDataService)AppUtil.getBean(TaskSignDataService.class);
	private TaskOpinionService taskOpinionService=(TaskOpinionService) AppUtil.getBean(TaskOpinionService.class);
	private TaskUserAssignService taskUserAssignService=(TaskUserAssignService)AppUtil.getBean(TaskUserAssignService.class);
	private BpmProStatusService bpmProStatusService=(BpmProStatusService)AppUtil.getBean(BpmProStatusService.class);
	
	

	@Override
	protected void execute(DelegateTask delegateTask, String actDefId,String nodeId) {
		
		TaskOpinion taskOpinion=new TaskOpinion(delegateTask);
		taskOpinion.setOpinionId(UniqueIdUtil.genId());
		taskOpinionService.add(taskOpinion);
		
		//获取任务执行人。
		TaskExecutor taskExecutor=(TaskExecutor) delegateTask.getVariable("assignee");
		
		//分配用户
		assignTaskExecutor(delegateTask,taskExecutor);
		
		//把新生成的任务加至执行堆栈中
		TaskThreadService.addTask((TaskEntity)delegateTask);
		
		String processInstanceId=delegateTask.getProcessInstanceId();

		logger.debug("enter the signuser listener notify method, taskId:"+ delegateTask.getId() + " assignee:"+ delegateTask.getAssignee());
		
		Integer instanceOfNumbers=(Integer)delegateTask.getVariable("nrOfInstances");
		Integer loopCounter=(Integer)delegateTask.getVariable("loopCounter");

		if(loopCounter==null) loopCounter=0;
		
		logger.debug("instance of numbers:"+ instanceOfNumbers + " loopCounters:"+ loopCounter);
		
		if(loopCounter==0){//第一次进入执行
			addSignData(delegateTask, nodeId, processInstanceId, instanceOfNumbers);
		}
		
		//添加流程状态。
		bpmProStatusService.addOrUpd(actDefId,new Long(processInstanceId), nodeId);
		
		//更新会签数据的任务ID。
		updTaskSignData(processInstanceId,nodeId,taskExecutor,delegateTask.getId());
			
	}
	
	/**
	 * 更新会签数据的任务Id。
	 * @param processInstanceId
	 * @param nodeId
	 * @param taskExecutor
	 * @param taskId
	 */
	private void updTaskSignData(String processInstanceId,String nodeId,TaskExecutor taskExecutor,String taskId){
		String executorId= taskExecutor.getExecuteId();
		TaskSignData taskSignData =taskSignDataService.getUserTaskSign(processInstanceId, nodeId, new Long( executorId));
		taskSignData.setTaskId(taskId);
		taskSignDataService.update(taskSignData);
	}

	/**
	 * 添加会签数据。
	 * @param delegateTask
	 * @param nodeId
	 * @param processInstanceId
	 * @param instanceOfNumbers
	 */
	private void addSignData(DelegateTask delegateTask, String nodeId,
			String processInstanceId, Integer instanceOfNumbers) {
		List<TaskExecutor> signUserList=taskUserAssignService.getExecutors();
		
		for(int i=0;i<instanceOfNumbers;i++){
			int sn=i + 1;
			//生成投票的数据
			TaskSignData signData=new TaskSignData();
			signData.setActInstId(processInstanceId);
			
			signData.setNodeName(delegateTask.getName());
			signData.setNodeId(nodeId);
			signData.setSignNums(sn);
			signData.setIsCompleted(TaskSignData.NOT_COMPLETED);
		

			TaskExecutor signUser=signUserList.get(i);
			if(signUser!=null){
				signData.setVoteUserId(new Long(signUser.getExecuteId()));
				signData.setVoteUserName(signUser.getExecutor());
			}
			signData.setDataId(UniqueIdUtil.genId());
			
			taskSignDataService.add(signData);
		}
	}
	
	/**
	 * 分配任务执行人。
	 * @param delegateTask
	 */
	private void assignTaskExecutor(DelegateTask delegateTask,TaskExecutor taskExecutor){
		/////ht b
		/////ht del b
//		if(TaskExecutor.USER_TYPE_USER.equals(taskExecutor.getType())){
//			delegateTask.setAssignee(taskExecutor.getExecuteId());
//		}
//		else{
//			delegateTask.addGroupIdentityLink(taskExecutor.getExecuteId(), taskExecutor.getType());
//		}
		//////ht del e
		
		List<TaskExecutor> signUserList=taskUserAssignService.getExecutors();
		signUserList = getByType(signUserList,TaskExecutor.USER_TYPE_ROLE);
		if(!signUserList.contains(taskExecutor)){
			signUserList.add(taskExecutor);
		}
		if(signUserList.size()==1){
			TaskExecutor executor=signUserList.get(0);
			
			if(TaskExecutor.USER_TYPE_USER.equals(executor.getType())){
				delegateTask.setOwner(executor.getExecuteId());
				delegateTask.setAssignee(executor.getExecuteId());
			}
			else{
				delegateTask.addGroupIdentityLink(executor.getExecuteId(), executor.getType());
			}
			
		}else{
			for(TaskExecutor executor:signUserList){
				if(TaskExecutor.USER_TYPE_USER.equals(executor.getType())){
					delegateTask.addCandidateUser(executor.getExecuteId());
				}
				else{
					delegateTask.addGroupIdentityLink(executor.getExecuteId(), executor.getType());
				}
			}
		}
		
		//////ht e
	}
	
	
	public List<TaskExecutor> getByType(Collection<TaskExecutor> executors,String type){
		List<TaskExecutor> list = new ArrayList<TaskExecutor>();
		if(executors==null){
			return list;
		}
		for(TaskExecutor taskExecutor:executors){
			if(taskExecutor.getType().equals(type)){
				list.add(taskExecutor);
			}
		}
		return list;
	}
	
	/**
	 * 添加流程执行状态。
	 * @param actDefId
	 * @param processInstanceId
	 * @param nodeId
	 */
	

	@Override
	protected int getScriptType() {
		
		return BpmConst.StartScript;
	}

}
