package com.hotent.platform.service.bpm.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang.StringUtils;

import com.hotent.core.bpm.model.FlowNode;
import com.hotent.core.bpm.model.NodeCache;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.bpm.util.BpmUtil;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.bpm.BpmNodeSetDao;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.bpm.TaskFork;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.service.bpm.BpmNodeUserService;
import com.hotent.platform.service.bpm.BpmProStatusService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.TaskForkService;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.bpm.thread.TaskThreadService;
import com.hotent.platform.service.bpm.thread.TaskUserAssignService;
import com.hotent.platform.service.system.SysUserService;

/**
 * 一般任务开始事件监听器。
 * <pre>
 * 1.携带上一任务的分发令牌。
 * 2.记录新产生的任务，为后续的回退作准备。
 * 3.添加流程意见。
 * 4.启动流程时添加或保存状态数据。
 * 5.如果当前节点是分发节点则进行任务分发，并直接返回，否则往下执行。
 * 6.从上下文中获取分配人员的数据，进行分配，如果有人员则进行分配，如果没有则往下执行。
 * 7.从数据库中加载人员进行人员分配。
 * 
 * </pre>
 * @author ray
 *
 */
public class TaskCreateListener extends BaseTaskListener {
	
	private TaskOpinionService taskOpinionService=(TaskOpinionService) AppUtil.getBean(TaskOpinionService.class);

	private TaskUserAssignService taskUserAssignService=(TaskUserAssignService)AppUtil.getBean(TaskUserAssignService.class);
	
	private TaskForkService taskForkService=(TaskForkService)AppUtil.getBean(TaskForkService.class);
	
	private BpmNodeSetDao bpmNodeSetDao=(BpmNodeSetDao)AppUtil.getBean(BpmNodeSetDao.class);
	
	private BpmService bpmService=(BpmService)AppUtil.getBean(BpmService.class);
	
	private BpmProStatusService bpmProStatusService=(BpmProStatusService)AppUtil.getBean(BpmProStatusService.class);

	private SysUserService sysUserService=(SysUserService)AppUtil.getBean(SysUserService.class);
	
	@Override
	protected void execute(DelegateTask delegateTask,String actDefId,String nodeId)  {
		
		//携带上一任务的分发令牌
		String token=TaskThreadService.getToken();
		if(token!=null){
			delegateTask.setVariableLocal(TaskFork.TAKEN_VAR_NAME, token);
		}
		//记录新产生的任务，为后续的回退作准备
		TaskThreadService.addTask((TaskEntity)delegateTask);
		//生成任务签批意见
		addOpinion(token,delegateTask);
		
		Long actInstanceId=new Long ( delegateTask.getProcessInstanceId());
		
		//启动流程时添加或保存状态数据。
		bpmProStatusService.addOrUpd(actDefId, actInstanceId,nodeId);
		
		Map<String,List<TaskExecutor>> nodeUserMap=taskUserAssignService.getNodeUserMap();
		//处理任务分发。
		boolean isHandForkTask=handlerForkTask(actDefId,nodeId,nodeUserMap,delegateTask);
		if(isHandForkTask) return;
		
		boolean isSubProcess=handSubProcessUser(delegateTask);
		if(isSubProcess) return;
		//处理外部子流程用户。
		boolean isHandExtUser= handExtSubProcessUser(delegateTask);
		if(isHandExtUser) return;
		
		//在上一步中指定了该任务的执行人员
		if(nodeUserMap!=null && nodeUserMap.get(nodeId)!=null){
			List<TaskExecutor> executorIds=nodeUserMap.get(nodeId);
			//肖莹莹做了修改 2013-05-13 21:31
			if(executorIds != null && executorIds.size()>0){
				assignUser(delegateTask,executorIds);
				return;
			}
//			assignUser(delegateTask,executorIds);
//			return;
		}
		
		List<TaskExecutor> executorUsers = taskUserAssignService.getExecutors();
		//当前执行人。
		if(BeanUtils.isNotEmpty(executorUsers)){
			assignUser(delegateTask,executorUsers);
			return;
		}
		
		List<TaskExecutor> formUsers = taskUserAssignService.getFormUsers();
		//表单中设置了下一步的所属人
		if(BeanUtils.isNotEmpty(formUsers)){
			assignUser(delegateTask,formUsers);
			return;
		}
		//处理从数据库加载用户，并进行分配。
		handAssignUserFromDb(actDefId,nodeId,delegateTask);
	}
	
	/**
	 * 处理内部子流程的人员分配。
	 * <pre>
	 * 	将任务的执行变量人取出，指定给任务执行人。
	 * </pre>
	 * @param delegateTask
	 * @return
	 */
	private boolean handSubProcessUser(DelegateTask delegateTask){
		FlowNode flowNode=NodeCache.getByActDefId(delegateTask.getProcessDefinitionId()).get(delegateTask.getTaskDefinitionKey());
		boolean isMultipleNode=flowNode.getIsMultiInstance();
		if(!isMultipleNode) return false;
		//若为多实例子流程中的任务，则从线程中的人员取出，并且把该人员从线程中删除
		TaskExecutor taskExecutor=(TaskExecutor)delegateTask.getVariable("assignee");
		if(taskExecutor!=null){
			//分配任务执行人。
			assignUser(delegateTask,taskExecutor);
			
			int completeInstance=(Integer)delegateTask.getVariable("nrOfCompletedInstances");
			int nrOfInstances=(Integer)delegateTask.getVariable("nrOfInstances");
			//清空该人员集合
			if( completeInstance==nrOfInstances){
				delegateTask.removeVariable(BpmConst.SUBPRO_MULTI_USERIDS);
			}
		}
		return true;
	}
	
	/**
	 * 外部子流程流程多实例任务人员分配。
	 * @param delegateTask
	 * @return
	 * @throws Exception 
	 */
	private boolean handExtSubProcessUser(DelegateTask delegateTask) {
		ExecutionEntity executionEnt=(ExecutionEntity) delegateTask.getExecution() ;
		//没有父流程
		if(executionEnt.getSuperExecution()==null) return false;
		if(! BpmUtil.isMuiltiExcetion(executionEnt.getSuperExecution())) return false;
		String actDefId=executionEnt.getSuperExecution().getProcessDefinitionId();
		Map<String, FlowNode> mapParent= NodeCache.getByActDefId(actDefId);
		
		String parentNodeId=executionEnt.getSuperExecution().getActivityId();
		String curentNodeId=executionEnt.getActivityId();
		
		FlowNode parentFlowNode= mapParent.get(parentNodeId);
		Map<String, FlowNode> subNodeMap=parentFlowNode.getSubProcessNodes();
		FlowNode startNode= NodeCache.getStartNode(subNodeMap);
		
		if(startNode.getNextFlowNodes().size()==1){
			FlowNode nextNode=startNode.getNextFlowNodes().get(0);
			if(nextNode.getNodeId().equals(curentNodeId)){
				TaskExecutor taskExecutor=(TaskExecutor) executionEnt.getSuperExecution().getVariable("assignee");
				if(taskExecutor!=null){
					assignUser(delegateTask,taskExecutor);
				}
				return true;
			}
			return false;
		}
		logger.debug("多实例外部调用子流程起始节点后只能跟一个任务节点");
		return false;
		
	}
	
	
	
	/**
	 * 添加流程意见。
	 * @param token
	 * @param delegateTask
	 */
	private void addOpinion(String token,DelegateTask delegateTask){
		TaskOpinion taskOpinion=new TaskOpinion(delegateTask);
		taskOpinion.setOpinionId(UniqueIdUtil.genId());
		taskOpinion.setTaskToken(token);
		taskOpinionService.add(taskOpinion);
		logger.debug(taskOpinion.toString());
	}
	
	/**
	 * 从数据库加载人员并分配用户。
	 * @param actDefId
	 * @param nodeId
	 * @param delegateTask
	 */
	private void handAssignUserFromDb(String actDefId,String nodeId,DelegateTask delegateTask){
		BpmNodeUserService userService=(BpmNodeUserService) AppUtil.getBean(BpmNodeUserService.class);
		
		ProcessInstance processInstance=bpmService.getProcessInstance(delegateTask.getProcessInstanceId());
		List<TaskExecutor> users=null; 
		Map<String,Object> vars=new HashMap<String, Object>();
		vars.put("executionId", delegateTask.getExecutionId());
		if(processInstance!=null){
			//获取上个任务的执行人，这个执行人在上一个流程任务的完成事件中进行设置。
			//代码请参考TaskCompleteListener。
			String preTaskUser=TaskThreadService.getPreTaskUser();
			if(StringUtils.isEmpty(preTaskUser)){
				preTaskUser=(String)delegateTask.getVariable(BpmConst.StartUser);
			}
			users=userService.getExeUserIdsByInstance(delegateTask.getProcessInstanceId(),nodeId,preTaskUser,vars).get(BpmNodeUser.USER_TYPE_PARTICIPATION);
		}
		//流程启动时任务实例还未产生出来。
		else{
			//上个节点的任务执行人
			String startUserId=(String)delegateTask.getVariable(BpmConst.StartUser);
			users=userService.getExeUserIds(actDefId, null, nodeId, startUserId, startUserId,vars).get(BpmNodeUser.USER_TYPE_PARTICIPATION);
		}
		if(users!=null){
			assignUser(delegateTask,users);
		}
	}
	
	/**
	 * 处理任务分发。
	 * <pre>
	 * 	1.根据指定的用户产生新的任务，并指定了相应的excution，任务历史数据。
	 * 		支持用户独立的往下执行，不像会签的方式需要等待其他的任务完成才往下执行。
	 *  2.产生分发记录。
	 *   
	 * </pre>
	 * @param actDefId			流程定义ID
	 * @param nodeId			流程节点ID
	 * @param nodeUserMap		上下文指定的分发用户。
	 * @param delegateTask		任务对象。
	 * @return
	 */
	private boolean handlerForkTask(String actDefId,String nodeId,Map<String,List<TaskExecutor>> nodeUserMap,DelegateTask delegateTask){
		//若任务进行回退至分发任务节点上，则不再进行任务分发
		ProcessCmd processCmd=TaskThreadService.getProcessCmd();
		if(processCmd!=null && BpmConst.TASK_BACK.equals(processCmd.isBack())) return false;
		BpmNodeSet bpmNodeSet=bpmNodeSetDao.getByActDefIdNodeId(actDefId, nodeId);
		//当前任务为分发任务,即根据当前分发要求进行生成分发任务
		if(bpmNodeSet!=null && BpmNodeSet.NODE_TYPE_FORK.equals(bpmNodeSet.getNodeType())){
			List<TaskExecutor> taskExecutors=taskUserAssignService.getExecutors();
			//若当前的线程里包含了该任务对应的执行人员列表，则任务的分发用户来自于此
			if(BeanUtils.isEmpty(taskExecutors)){
				//若当前的线程里包含了该任务对应的执行人员列表，则任务的分发用户来自于此
				if(nodeUserMap!=null && nodeUserMap.get(nodeId)!=null){
					taskExecutors=nodeUserMap.get(nodeId);
				}
				//否则，从数据库获取人员设置
				else{
					BpmNodeUserService userService=(BpmNodeUserService) AppUtil.getBean(BpmNodeUserService.class);
					ProcessInstance processInstance=bpmService.getProcessInstance(delegateTask.getProcessInstanceId());
					if(processInstance!=null){
						Map<String,Object> vars=new HashMap<String, Object>();
						vars.put("executionId", delegateTask.getExecutionId());
						String preTaskUser=TaskThreadService.getPreTaskUser();
						if(StringUtils.isEmpty(preTaskUser)){
							preTaskUser=(String)delegateTask.getVariable(BpmConst.StartUser);
						}
						taskExecutors = userService.getExeUserIdsByInstance(delegateTask.getProcessInstanceId(),nodeId,preTaskUser,vars).get(BpmNodeUser.USER_TYPE_PARTICIPATION);
					}
				}
			}
			if(BeanUtils.isNotEmpty(taskExecutors)){
				bpmService.newForkTasks((TaskEntity)delegateTask, taskExecutors);
				taskForkService.newTaskFork(delegateTask,bpmNodeSet.getJoinTaskName(), bpmNodeSet.getJoinTaskKey(), taskExecutors.size());
			}
			else{
				MessageUtil.addMsg("请设置分发人员");
			}
			
			return true;	
		}
		return false;
	}
	
	
	
	/**
	 * 分配用户执行人或候选人组。
	 * @param delegateTask
	 * @param taskExecutor
	 */
	private void assignUser(DelegateTask delegateTask, TaskExecutor taskExecutor){
		if(TaskExecutor.USER_TYPE_USER.equals(taskExecutor.getType())){
			delegateTask.setAssignee(taskExecutor.getExecuteId());
		}
		else{
			delegateTask.addGroupIdentityLink(taskExecutor.getExecuteId(), taskExecutor.getType());
		}
	}
	
	/**
	 * 分配任务执行人。
	 * @param delegateTask
	 * @param users
	 */
	private void assignUser(DelegateTask delegateTask, List<TaskExecutor> executors){
		if(BeanUtils.isEmpty(executors)) return;
		//只有一个人的情况。
		if(executors.size()==1){
			TaskExecutor taskExecutor=executors.get(0);
			
			if(TaskExecutor.USER_TYPE_USER.equals(taskExecutor.getType())){
				delegateTask.setOwner(taskExecutor.getExecuteId());
				delegateTask.setAssignee(taskExecutor.getExecuteId());
				
				TaskOpinion taskOpinion= taskOpinionService.getByTaskId(new Long(delegateTask.getId()));
				if(taskOpinion!=null){
					ISysUser sysUser=sysUserService.getById(new Long(delegateTask.getAssignee()));
					Long userId = SystemConst.SYSTEMUSERID;
					String userName = SystemConst.SYSTEMUSERNAME;
					if(sysUser!=null){
						userId = sysUser.getUserId();
						userName = sysUser.getFullname();
					}
					taskOpinion.setExeUserId(userId);
					taskOpinion.setExeFullname(userName);
					taskOpinionService.update(taskOpinion);
				}
			}
			else{
				delegateTask.addGroupIdentityLink(taskExecutor.getExecuteId(), taskExecutor.getType());
			}
			
		}
		else{
			for(TaskExecutor taskExecutor:executors){
				if(TaskExecutor.USER_TYPE_USER.equals(taskExecutor.getType())){
					delegateTask.addCandidateUser(taskExecutor.getExecuteId());
				}
				else{
					delegateTask.addGroupIdentityLink(taskExecutor.getExecuteId(), taskExecutor.getType());
				}
			}
		}
	}

	@Override
	protected int getScriptType() {
		return BpmConst.StartScript;
	}

}
