package com.hotent.platform.service.bpm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fr.data.core.db.dml.Table;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.bpm.util.BpmUtil;
import com.hotent.core.customertable.TableModel;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.jms.MessageProducer;
import com.hotent.core.model.InnerMessage;
import com.hotent.core.model.MailModel;
import com.hotent.core.model.SmsMobile;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.page.PageBean;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.TimeUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.bpm.BpmDefinitionDao;
import com.hotent.platform.dao.bpm.BpmNodeSetDao;
import com.hotent.platform.dao.bpm.BpmTaskAssigneeDao;
import com.hotent.platform.dao.bpm.ExecutionDao;
import com.hotent.platform.dao.bpm.ProRunTblPkDao;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.dao.bpm.TaskDao;
import com.hotent.platform.dao.bpm.TaskOpinionDao;
import com.hotent.platform.dao.form.BpmFormHandlerDao;
import com.hotent.platform.dao.form.BpmFormTableDao;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.bpm.BpmRunLog;
import com.hotent.platform.model.bpm.BpmTaskAssignee;
import com.hotent.platform.model.bpm.ExecutionStack;
import com.hotent.platform.model.bpm.ProRunTblPk;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskFork;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.form.BpmFormData;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.form.PkValue;
import com.hotent.platform.model.form.SubTable;
import com.hotent.platform.model.system.SysTemplate;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.service.biz.BizCmd;
import com.hotent.platform.service.biz.BizInstanceService;
import com.hotent.platform.service.biz.BizProcessVarsConst;
import com.hotent.platform.service.bpm.impl.BpmActService;
import com.hotent.platform.service.bpm.thread.TaskThreadService;
import com.hotent.platform.service.bpm.thread.TaskUserAssignService;
import com.hotent.platform.service.form.FormDataUtil;
import com.hotent.platform.service.system.SysTemplateService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.worktime.CalendarAssignService;

/**
 * 对象功能:流程实例扩展Service类 开发公司: 开发人员:csx 创建时间:2011-12-03 09:33:06
 */
@Service
public class ProcessRunService extends BaseService<ProcessRun> {
	
	protected Logger log = LoggerFactory.getLogger(ProcessRunService.class);
	@Resource
	private ProcessRunDao dao;

	@Resource
	private BpmDefinitionDao bpmDefinitionDao;

	@Resource
	private BpmService bpmService;

	@Resource
	private TaskSignDataService taskSignDataService;

	@Resource
	private BpmFormHandlerDao bpmFormHandlerDao;

	@Resource
	private BpmNodeSetDao bpmNodeSetDao;

	@Resource
	private TaskService taskService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskUserAssignService taskUserAssignService;

	@Resource
	private TaskUserService taskUserService;
	@Resource
	private TaskOpinionDao taskOpinionDao;
	@Resource
	private SysUserDao sysUserDao;
	//@Resource
	private MessageProducer messageProducer;
	@Resource
	private BpmFormRunService bpmFormRunService;
	@Resource
	private TaskDao taskDao;
	@Resource
	private BpmRunLogService bpmRunLogService;
	@Resource
	private SysTemplateService sysTemplateService;
	@Resource
	private BpmTaskAssigneeService bpmTaskAssigneeService;
	@Resource
	private CalendarAssignService calendarAssignService;
	@Resource
	private BpmTaskAssigneeDao bpmTaskAssigneeDao;
	@Resource
	private ExecutionDao executionDao;
	@Resource
	private ProRunTblPkDao proRunTblPkDao;
	@Resource
	private BpmFormTableDao bpmFormTableDao;
	
	/////ht
	@Resource 
	private BizInstanceService bizInstanceService;
	/////ht e
	
	public void setMessageProducer(MessageProducer producer){
		this.messageProducer=producer;
	}
	
	/**
	 * 流程跳转规则
	 */
	@Resource
	private JumpRule jumpRule;
	@Resource
	private BpmActService bpmActService;
	@Resource
	private ExecutionStackService executionStackService;
	@Resource
	private Properties configproperties;
	@Resource
	private BpmNodeUserService bpmNodeUserService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private SysUserService sysUserService;


	public ProcessRunService() {
	}

	@Override
	protected IEntityDao<ProcessRun, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 若下一任务分发任务节点，则指定下一任务的执行人员
	 * 
	 * @param processCmd
	 */
	private void setNextTaskUser(ProcessCmd processCmd) {
		//回退不设置用户。
		if( processCmd.isBack().intValue()==0){
			String[] nodeIds = processCmd.getLastDestTaskIds();
			String[] nodeUserIds = processCmd.getLastDestTaskUids();
			// 设置下一步产生任务的执行人员
			if (nodeIds != null && nodeUserIds != null) {
				taskUserAssignService.addNodeUser(nodeIds, nodeUserIds);
			}
		}
		//设置了任务执行人。
		if(BeanUtils.isNotEmpty(processCmd.getTaskExecutors())){
			taskUserAssignService.setExecutors(processCmd.getTaskExecutors());
		}
		TaskThreadService.setProcessCmd(processCmd);
	}
	
	/**
	 * 交办任务
	 * @param taskId 任务ID
	 * @param userId 接受交办的用户ID
	 * @param description 交办备注信息
	 */
	public void delegate(String taskId,String userId,String description){
		bpmService.updateTaskAssignee(taskId, userId);
		
		TaskEntity taskEntity = bpmService.getTask(taskId);
		ProcessRun processRun= processRunService.getByActInstanceId(taskEntity.getProcessInstanceId());
		Long runId=processRun.getRunId();
		
		ISysUser user = sysUserService.getById(Long.parseLong(userId));	
		ISysUser curUser =ContextUtil.getCurrentUser();
		Long curUserId = SystemConst.SYSTEMUSERID;
		String curUserName = SystemConst.SYSTEMUSERNAME;
		if(curUser!=null){
			curUserId = curUser.getUserId();
			curUserName= curUser.getFullname();
		}
		//添加交办记录
		BpmTaskAssignee bpmTaskAssignee=new BpmTaskAssignee();
		bpmTaskAssignee.setId(UniqueIdUtil.genId());
		bpmTaskAssignee.setTaskId(taskId);
		bpmTaskAssignee.setTaskName(taskEntity.getName());
		bpmTaskAssignee.setUserId(curUserId);
		bpmTaskAssignee.setUserName(curUserName);
		bpmTaskAssignee.setSubject(processRun.getSubject());
		bpmTaskAssignee.setTaskStatus(BpmTaskAssignee.TASK_NO_EXC);
		bpmTaskAssignee.setAssigneeId(Long.parseLong(userId));
		bpmTaskAssignee.setAssigneeTime(new Date());
		bpmTaskAssignee.setAssigneeName(user.getFullname());
		bpmTaskAssignee.setRunId(runId);
		bpmTaskAssignee.setMemo(description);
		bpmTaskAssigneeService.add(bpmTaskAssignee);
		String memo="流程:" +processRun.getSubject() +", 【"+ curUserName +"】将任务【"+taskEntity.getName()+"】交给【"+user.getFullname()+"】执行。";
		bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_DELEGATE, memo);
		
		memo="流程:" +processRun.getSubject() +", 【"+curUserName+"】将任务【"+taskEntity.getName()+"】交给【"+user.getFullname()+"】执行。";
		bpmRunLogService.addRunLog(user, runId, BpmRunLog.OPERATOR_TYPE_DELEGATE, memo);
	}

	/**
	 * 代理任务执行，临时修改当前执行人。
	 * 
	 * @param taskEntity
	 * @param processCmd
	 * @param runId
	 */
	private void setAgentUser(TaskEntity taskEntity, ProcessCmd processCmd,Long runId) {
		ISysUser curUser = ContextUtil.getCurrentUser();
		ISysUser sysUser = sysUserDao.getById(new Long(taskEntity.getAssignee()));
		String memo = "代理执行了【" + sysUser.getUsername() + "】在任务节点【"+ taskEntity.getName() + "】的任务";
		// 记录日志
		bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_AGENT, memo);
		memo = "在节点【" + taskEntity.getName() + "】的任务，被【"+ curUser.getUsername() + "】代理执行了";
		bpmRunLogService.addRunLog(sysUser, runId,BpmRunLog.OPERATOR_TYPE_AGENT, memo);
	}

	/**
	 * 设置流程变量。
	 * @param taskId
	 * @param processCmd
	 */
	private void setVariables(String taskId,ProcessCmd processCmd){
		Map<String,Object> vars=processCmd.getVariables();
		if(BeanUtils.isNotEmpty(vars)) {
			for(Iterator<Entry<String, Object>> it=vars.entrySet().iterator();it.hasNext();){
				Entry<String, Object> obj=it.next();
				taskService.setVariable(taskId, obj.getKey(), obj.getValue());
			}
		}
		Map<String,Object> formVars=taskService.getVariables(taskId);
		formVars.put(BpmConst.IS_EXTERNAL_CALL, processCmd.isInvokeExternal());
		formVars.put(BpmConst.FLOW_RUN_SUBJECT, processCmd.getSubject());
		//设置线程变量。
		TaskThreadService.setVariables(formVars);
		TaskThreadService.getVariables().putAll(processCmd.getVariables());
	}

	/**
	 * 流程启动下一步
	 * 
	 * @param processCmd 流程执行命令实体。
	 * @return ProcessRun 流程实例
	 * @throws Exception
	 */
	public ProcessRun nextProcess(ProcessCmd processCmd) throws Exception {
		ProcessRun processRun = null;
		String taskId = processCmd.getTaskId();
		TaskEntity taskEntity = bpmService.getTask(taskId);
		if (taskEntity.getExecutionId() == null && "通知任务".equals(taskEntity.getDescription())) {
			return null;
		}
		
		// 当下一节点为条件同步节点时，可以指定执行路径
		Object nextPathObj = processCmd.getFormDataMap().get("nextPathId");
		if (nextPathObj != null){
			bpmService.setTaskVariable(taskId, "NextPathId",nextPathObj.toString());
		}
		String parentNodeId = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		String instanceId = taskEntity.getProcessInstanceId();
		String executionId = taskEntity.getExecutionId();

		processRun = dao.getByActInstanceId(instanceId);
		processCmd.setProcessRun(processRun);
		// 是否代理别人完成任务
		if (processCmd.isAgentTask() && StringUtils.isNotEmpty(taskEntity.getAssignee())) {
			setAgentUser(taskEntity, processCmd, processRun.getRunId());
		}
		// 该任务是否为别人交办任务
		boolean isAssigneeTask = isAssigneeTask(taskId);

		try {
			// 取到当前任务是否带有分发令牌
			String taskToken = (String) taskService.getVariableLocal(taskId,TaskFork.TAKEN_VAR_NAME);
			BpmNodeSet bpmNodeSet = bpmNodeSetDao.getByActDefIdNodeId(actDefId,parentNodeId);

			// 设置下一步包括分发任务的用户
			setNextTaskUser(processCmd);
			// 1.处理在线表单的业务数据
			Map<String, String> optionsMap = null;
			if (!processCmd.isInvokeExternal()) {
				BpmFormData bpmFormData=handlerFormData(processCmd, processRun,parentNodeId);
				if(bpmFormData!=null){
					optionsMap=bpmFormData.getOptions();
				}
			}
			// 调用前置处理器
			if (!processCmd.isSkipPreHandler()) {
				invokeHandler(processCmd, bpmNodeSet, true);
			}

			// 3.流程回退处理的前置处理，查找该任务节点其回退的堆栈树父节点，以便其跳转时，能跳回该节点上
			ExecutionStack parentStack = backPrepare(processCmd, taskEntity,	taskToken);

			if (parentStack != null) {
				parentNodeId = parentStack.getNodeId();
			}
			// 设置会签用户或处理会签意见
			signUsersOrSignVoted(processCmd, taskEntity);
			//设置流程变量。
			processCmd.setSubject(processRun.getSubject());
			setVariables(taskId,processCmd);
			// 是否仅完成当前任务
			if (processCmd.isOnlyCompleteTask()) {
				bpmService.onlyCompleteTask(taskId);
			}
			// 自由跳转或回退
			else if (StringUtils.isNotEmpty(processCmd.getDestTask())) {
				bpmService.transTo(taskId, processCmd.getDestTask());
			} 
			else { // 正常流程跳转
				ExecutionEntity execution = bpmActService.getExecution(taskEntity.getExecutionId());
				// 从规则中获取跳转
				TaskThreadService.setObject(processCmd.getVoteAgree());
				String jumpTo = jumpRule.evaluate(execution,bpmNodeSet.getIsJumpForDef());
				TaskThreadService.removeObject();
				bpmService.transTo(taskId, jumpTo);
			}
			// 调用后置处理器这里
			if (!processCmd.isSkipAfterHandler()) {
				invokeHandler(processCmd, bpmNodeSet, false);
			}
			//如果在流程运行主键为空，并且在processCmd不为空的情况下，我们更新流程流程运行的主键。
			if(StringUtil.isEmpty(processRun.getBusinessKey()) && StringUtil.isNotEmpty(processCmd.getBusinessKey())){
				processRun.setBusinessKey(processCmd.getBusinessKey());
				//设置流程主键变量。
				runtimeService.setVariable(executionId, BpmConst.FLOW_BUSINESSKEY, processCmd.getBusinessKey());
			}

			if (processCmd.isBack() > 0 && parentStack != null) {// 任务回退时，弹出历史执行记录的堆栈树节点
				executionStackService.pop(parentStack, processCmd.isRecover());
			}
			else {
				// 记录执行执行的堆栈
				List<String> map = TaskThreadService.getExtSubProcess();
				if (BeanUtils.isEmpty(map)) {
					executionStackService.addStack(instanceId, parentNodeId,taskToken);
				} else {
					// 初始化外部子流程。
					initExtSubProcessStack();
				}
			}
			/*
			 * 为了解决在任务自由跳转或回退时，若流程实例有多个相同Key的任务，会把相同的任务删除。
			 * 与TaskCompleteListner里的以下代码对应使用 if(processCmd!=null &&
			 * (processCmd.isBack()>0 ||
			 * StringUtils.isNotEmpty(processCmd.getDestTask()))){
			 * taskDao.updateNewTaskDefKeyByInstIdNodeId
			 * (delegateTask.getTaskDefinitionKey() +
			 * "_1",delegateTask.getTaskDefinitionKey
			 * (),delegateTask.getProcessInstanceId()); }
			 */
			if (processCmd.isBack() > 0 || StringUtils.isNotEmpty(processCmd.getDestTask())) {
				// 更新其相对对应的key
				taskDao.updateOldTaskDefKeyByInstIdNodeId(
						taskEntity.getTaskDefinitionKey() + "_1",
						taskEntity.getTaskDefinitionKey(),
						taskEntity.getProcessInstanceId());
			}
			// 更改交办任务状态
			if (isAssigneeTask) {
				List<BpmTaskAssignee> list = bpmTaskAssigneeService.getByTaskId(taskId);
				for (BpmTaskAssignee bta : list) {
					bta.setTaskStatus(BpmTaskAssignee.TASK_EXC);
					bpmTaskAssigneeService.update(bta);
				}
			}
			// 产生通知任务。
			genNotifyTasks();
			// 记录意见
			updOption(optionsMap, taskId);
			// 处理信息
			notify(TaskThreadService.getNewTasks(), processCmd.getInformType(),processRun.getSubject(), null);
			// 记录流程执行日志。
			recordLog(processCmd, taskEntity.getName(), processRun.getRunId());

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// 清空在线程中绑定的用户等信息.
			clearThreadLocal();
		}
		return processRun;
	}

	/**
	 * 判断 是否为别人交办任务
	 * 
	 * @param taskId
	 * @return
	 */
	public boolean isAssigneeTask(String taskId) {
		int count = bpmTaskAssigneeService.getCountByTaskId(taskId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 初始化子流程任务堆栈。
	 * 
	 * <pre>
	 * 子流程处理当作新的流程进行处理，进行初始化处理。
	 * </pre>
	 */
	private void initExtSubProcessStack() {
		List<String> list = TaskThreadService.getExtSubProcess();
		if (BeanUtils.isEmpty(list))
			return;
		List<Task> taskList = TaskThreadService.getNewTasks();
		Map<String, List<Task>> map = getMapByTaskList(taskList);
		for (String instanceId : list) {
			List<Task> tmpList = map.get(instanceId);
			executionStackService.initStack(instanceId, tmpList);
		}
	}

	private Map<String, List<Task>> getMapByTaskList(List<Task> taskList) {
		Map<String, List<Task>> map = new HashMap<String, List<Task>>();
		for (Task task : taskList) {
			String instanceId = task.getProcessInstanceId();
			if (map.containsKey(instanceId)) {
				map.get(instanceId).add(task);
			} else {
				List<Task> list = new ArrayList<Task>();
				list.add(task);
				map.put(instanceId, list);
			}
		}
		return map;
	}

	private void clearThreadLocal() {
		// 清空线程中空任务
		TaskThreadService.clearAll();
		TaskUserAssignService.clearAll();
	}

	/**
	 * 产生通知任务。
	 * 
	 * @param processCmd
	 * @param instanceId
	 */
	private void genNotifyTasks() {
		List<Task> taskList = TaskThreadService.getNewTasks();
		if (BeanUtils.isEmpty(taskList))
			return;

		String preTaskUser = TaskThreadService.getPreTaskUser();
		for (Task task : taskList) {
			String actDefId = task.getProcessDefinitionId();
			String nodeId = task.getTaskDefinitionKey();
			String instanceId = task.getProcessInstanceId();
			String startUserId = (String) runtimeService.getVariable(instanceId, BpmConst.StartUser);
			List<TaskExecutor> userList = bpmNodeUserService.getUsers(BpmNodeUser.USER_TYPE_NOTIFY, actDefId, instanceId, nodeId,startUserId, preTaskUser);
			bpmService.genNotifyTask((DelegateTask)task, userList);
		}
	}

	/**
	 * 保存知会意见，并删除只会任务。
	 * 
	 * @param taskId
	 * @param taskOpinion
	 */
	public void saveOpinion(String taskId, TaskOpinion taskOpinion) {
		taskOpinionDao.add(taskOpinion);
		taskService.deleteTask(taskId);
	}

	/**
	 * 流程操作日志记录
	 * 
	 * @param processCmd
	 * @throws Exception
	 */
	private void recordLog(ProcessCmd processCmd, String taskName, Long runId)
			throws Exception {
		String memo = "";
		Integer type = -1;
		// 追回
		if (processCmd.isRecover()) {
			type = BpmRunLog.OPERATOR_TYPE_RETRIEVE;
			memo = "用户在任务节点[" + taskName + "]执行了追回操作。";
		}
		// 驳回
		else if (BpmConst.TASK_BACK.equals(processCmd.isBack())) {
			type = BpmRunLog.OPERATOR_TYPE_REJECT;
			memo = "用户在任务节点[" + taskName + "]执行了驳回操作。";
		}
		// 驳回到发起人
		else if (BpmConst.TASK_BACK_TOSTART.equals(processCmd.isBack())) {
			type = BpmRunLog.OPERATOR_TYPE_REJECT2SPONSOR;
			memo = "用户在任务节点[" + taskName + "]执行了驳回到发起人操作。";
		} else {
			// 同意
			if (TaskOpinion.STATUS_AGREE.equals(processCmd.getVoteAgree())) {
				type = BpmRunLog.OPERATOR_TYPE_AGREE;
				memo = "用户在任务节点[" + taskName + "]执行了同意操作。";
			}
			// 反对
			else if (TaskOpinion.STATUS_REFUSE
					.equals(processCmd.getVoteAgree())) {
				type = BpmRunLog.OPERATOR_TYPE_OBJECTION;
				memo = "用户在任务节点[" + taskName + "]执行了反对操作。";
			}
			// 弃权
			else if (TaskOpinion.STATUS_ABANDON.equals(processCmd
					.getVoteAgree())) {
				type = BpmRunLog.OPERATOR_TYPE_ABSTENTION;
				memo = "用户在任务节点[" + taskName + "]执行了弃权操作。";
			}
			// 更改执行路径
			if (TaskOpinion.STATUS_CHANGEPATH.equals(processCmd.getVoteAgree())) {
				type = BpmRunLog.OPERATOR_TYPE_CHANGEPATH;
				memo = "用户在任务节点[" + taskName + "]执行了更改执行路径操作。";
			}
		}
		if (type == -1)
			return;

		bpmRunLogService.addRunLog(runId, type, memo);
	}

	/**
	 * 回退准备。
	 * 
	 * @param processCmd
	 * @param taskEntity
	 * @param taskToken
	 * @param backToNodeId
	 * @return
	 */
	private ExecutionStack backPrepare(ProcessCmd processCmd,
			TaskEntity taskEntity, String taskToken) {
		if (processCmd.isBack() == 0)
			return null;
		String aceDefId = taskEntity.getProcessDefinitionId();
		String instanceId = taskEntity.getProcessInstanceId();
		String backToNodeId = bpmDefinitionDao.getByActDefId(aceDefId).getStartFirstNode();
		ExecutionStack parentStack = null;

		// 驳回
		if (processCmd.isBack().equals(BpmConst.TASK_BACK)) {
			parentStack = executionStackService.backPrepared(processCmd,taskEntity, taskToken);
		}
		// 驳回到发起人
		else if (processCmd.isBack() == BpmConst.TASK_BACK_TOSTART) {
			// 获取发起节点。
			if (StringUtil.isEmpty(backToNodeId)) {
				backToNodeId = getFirstNodetByDefId(aceDefId);
			}
			if (StringUtil.isNotEmpty(backToNodeId)) {
				parentStack = executionStackService.getLastestStack(instanceId,backToNodeId, null);
				if (parentStack != null) {
					processCmd.setDestTask(parentStack.getNodeId());
					taskUserAssignService.addNodeUser(parentStack.getNodeId(),parentStack.getAssignees());
				}
			}
		}
		return parentStack;
	}

	private void pushUser(Map<ISysUser, List<Task>> users, ISysUser user,
			Task task) {
		if (users.containsKey(user)) {
			users.get(user).add(task);
		} else {
			List<Task> list = new ArrayList<Task>();
			list.add(task);
			users.put(user, list);
		}
	}

	/**
	 * 获取默认的消息模板字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> getDefaultTemp() throws Exception {
		SysTemplate innerTemp = sysTemplateService.getDefaultByType(SysTemplate.INNER_TEMP_TYPE);
		if (innerTemp == null)
			throw new Exception("模板中未找到内部消息的默认模板或系统模板");

		SysTemplate mailTemp = sysTemplateService.getDefaultByType(SysTemplate.MAIL_TEMP_TYPE);
		if (mailTemp == null)
			throw new Exception("模板中未找到邮件的默认模板或系统模板");

		SysTemplate shortTemp = sysTemplateService.getDefaultByType(SysTemplate.SHORT_TEMP_TYPE);
		if (shortTemp == null)
			throw new Exception("模板中未找到手机短信的默认模板或系统模板");

		Map<String, String> map = new HashMap<String, String>();
		map.put("inner", innerTemp.getContent());
		map.put("mail", mailTemp.getContent());
		map.put("shortmsg", shortTemp.getContent());

		return map;
	}

	/**
	 * 发送短信或者邮件通知。
	 * 
	 * @param instanceId
	 *            流程实例ID
	 * @param processCmd
	 *            通知类型。
	 * @param title
	 *            消息标题
	 * @param msgTempMap
	 *            消息模板
	 * @throws Exception
	 */
	public void notify(List<Task> taskList, String informTypes, String title,Map<String, String> msgTempMap) throws Exception {
		if(messageProducer==null){
			log.debug("notify messageProducer is null ");
			return;
		}
		
		if (msgTempMap == null)
			msgTempMap = getDefaultTemp();
		if (taskList == null) return;
		Map<ISysUser, List<Task>> users = new HashMap<ISysUser, List<Task>>();

		for (Task task : taskList) {
			// 存在指定的用户
			if (StringUtil.isNotEmpty(task.getAssignee())) {
				ISysUser user = sysUserDao.getById(Long.parseLong(task.getAssignee()));
				pushUser(users, user, task);
			}
			// 获取该任务的候选用户列表
			else {
				/////ht:raise add b
				/****del
				Set<ISysUser> cUIds = taskUserService.getCandidateUsers(task.getId());
				******/
				Set<ISysUser> cUIds = taskUserService.getCandidateUsers(task.getId(),true);
				////ht:raise add e
				
				for (ISysUser user : cUIds) {
					pushUser(users, user, task);
				}
			}
		}

		for (ISysUser user : users.keySet()) {
			List<Task> tasks = users.get(user);
			for (Task task : tasks) {
				// 内部消息
				sendInnerMessage(user, title, task.getId(),msgTempMap.get("inner"));
				// 通知类型为空。
				if (StringUtil.isEmpty(informTypes))
					continue;
				// 手机短信
				if (informTypes.contains("1")) {
					sendShortMessage(user, title, msgTempMap.get("shortmsg"));
				}
				// 邮件
				if (informTypes.contains("2")) {
					sendMail(user, title, task.getId(),msgTempMap.get("mail"));
				}
			}
		}
	}

	/**
	 * 发送内部消息。
	 * 
	 * @param receiverUser
	 *            收件人
	 * @param subject
	 *            事项名称
	 * @param taskId
	 *            任务ID
	 * @param tempStr
	 *            消息模板
	 * @param title
	 *            消息标题
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sendInnerMessage(ISysUser receiverUser, String subject,String taskId, String tempStr) throws Exception {
		
		String url = configproperties.get("serverUrl")+ "/platform/bpm/task/toStart.ht?taskId=" + taskId;

		tempStr = tempStr.replace("${收件人}", receiverUser.getFullname())
				.replace("${发件人}", "系统消息").replace("${跳转地址}", url)
				.replace("${事项名称}", subject);

		InnerMessage innerMessage = new InnerMessage();
		innerMessage.setSubject(subject);
		innerMessage.setFrom("0");// 设置系统消息发送人ID为0
		innerMessage.setFromName("系统消息");
		innerMessage.setCanReply(new Short("0"));
		innerMessage.setContent(tempStr);
		innerMessage.setTo(receiverUser.getUserId().toString());
		innerMessage.setToName(receiverUser.getFullname());
		innerMessage.setSendDate(new Date());
		
		messageProducer.send(innerMessage);
		
		
	}

	/**
	 * 发送手机短信
	 * 
	 * @param receiverUser
	 *            收件人
	 * @param subject
	 *            事项名称
	 * @param tempStr
	 *            消息模板
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void sendShortMessage(ISysUser receiverUser, String subject,String tempStr) throws Exception {
	
		
		String phone = receiverUser.getMobile();
		if (phone == null) return;

		tempStr = tempStr.replace("${收件人}", receiverUser.getFullname())
				.replace("${发件人}", "系统消息").replace("${跳转地址}", "【跳转地址】")
				.replace("${事项名称}", subject);

		SmsMobile smsMobile = new SmsMobile();
		smsMobile.setPhoneNumber(receiverUser.getMobile());
		smsMobile.setSmsContent(tempStr);
		
		messageProducer.send(smsMobile);
		
		
	}

	/**
	 * 发送邮件
	 * 
	 * @param receiverUser	收件人
	 * @param subject	事项名称
	 * @param taskId	任务ID
	 * @param tempStr	消息模板
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void sendMail(ISysUser receiverUser, String subject, String taskId,
			String tempStr) throws Exception {
		
		
		if (receiverUser.getEmail().isEmpty())	return;
		String url = configproperties.get("serverUrl")+ "/platform/bpm/task/toStart.ht?taskId=" + taskId;

		tempStr = tempStr.replace("${收件人}", receiverUser.getFullname())
				.replace("${发件人}", "系统消息").replace("${跳转地址}", url)
				.replace("${事项名称}", subject);

		MailModel mailModel = new MailModel();
		mailModel.setSubject(subject);
		String[] sendTos = { receiverUser.getEmail() };
		mailModel.setTo(sendTos);
		mailModel.setContent(tempStr);
		mailModel.setSendDate(new Date());
		
		
		messageProducer.send(mailModel);
		
	}

	/**
	 * 更新意见。
	 * 
	 * <pre>
	 * 1.首先根据任务id查询意见，应为意见在task创建时这个意见就被产生出来，所以能直接获取到该意见。
	 * 2.获取意见，注意一个节点只允许一个意见填写，不能在同一个任务节点上同时允许两个意见框的填写，比如同时科员意见，局长意见等。
	 * 3.更新意见。
	 * </pre>
	 * 
	 * @param optionsMap
	 * @param taskId
	 */
	private void updOption(Map<String, String> optionsMap, String taskId) {
		if (BeanUtils.isEmpty(optionsMap)) return;

		Long lTaskId = Long.parseLong(taskId);
		TaskOpinion taskOpinion = taskOpinionDao.getByTaskId(lTaskId);

		if (taskOpinion == null) return;

		Set<String> set = optionsMap.keySet();
		String key = set.iterator().next();
		String value = optionsMap.get(key);

		taskOpinion.setFieldName(key);   
		taskOpinion.setOpinion(value);

		taskOpinionDao.update(taskOpinion);

	}

	/**
	 * 会签用户的设置及会签投票的处理。
	 * 
	 * <pre>
	 * 		1.从上下文中获取会签人员数据，如果会签人员数据不为空，则把人员绑定到线程，供会签任务节点产生会签用户使用。
	 * 		2.如果从上下文中获取了投票的数据。
	 * 			1.进行投票操作。
	 * 			2.设置流程状态，设置会签的意见，状态。
	 * </pre>
	 * 
	 * @param processCmd
	 * @param taskId
	 * @param taskDefKey
	 */
	private void signUsersOrSignVoted(ProcessCmd processCmd,TaskEntity taskEntity) {
		// 处理后续的节点若有为多实例时，把页面中提交过来的多实例的人员放置至线程中，在后续的任务创建中进行获取
		String nodeId = taskEntity.getTaskDefinitionKey();
		String taskId=taskEntity.getId();
		// 判断当前任务是否会多实例会签任务
		boolean isSignTask = bpmService.isSignTask(taskEntity);
		//是会签任务将人员取出并设置会签人员。
		if(isSignTask){
			Map<String,List<TaskExecutor>> executorMap=processCmd.getTaskExecutor();
			if(executorMap!=null && executorMap.containsKey(nodeId)){
				List<TaskExecutor> executorList = executorMap.get(nodeId);
				taskUserAssignService.setExecutors(executorList);
			}
		}
		
		if (processCmd.getVoteAgree() != null) {// 加入任务的处理结果及意见
			if (isSignTask) {// 加上会签投票
				taskSignDataService.signVoteTask(taskId,processCmd.getVoteContent(), processCmd.getVoteAgree());
			}
			processCmd.getVariables().put(BpmConst.NODE_APPROVAL_STATUS + "_" + nodeId,processCmd.getVoteAgree());
			processCmd.getVariables().put(BpmConst.NODE_APPROVAL_CONTENT + "_" + nodeId,processCmd.getVoteContent());
		}

	}


	/**
	 * 获取流程定义
	 * 
	 * @param processCmd
	 * @return
	 */
	private BpmDefinition getBpmDefinitionProcessCmd(ProcessCmd processCmd) {
		BpmDefinition bpmDefinition = null;
		if (processCmd.getActDefId() != null) {
			bpmDefinition = bpmDefinitionDao.getByActDefId(processCmd.getActDefId());
		} else {
			bpmDefinition = bpmDefinitionDao.getByActDefKeyIsMain(processCmd.getFlowKey());
		}
		return bpmDefinition;
	}

	/**
	 * 启动流程。
	 * 
	 * @param processCmd
	 * @param userId
	 * @return
	 */
	private ProcessInstance startWorkFlow(ProcessCmd processCmd) {
		String businessKey = processCmd.getBusinessKey();
		String userId = ContextUtil.getCurrentUserId().toString();
		ProcessInstance processInstance = null;
		processCmd.getVariables().put(BpmConst.FLOW_BUSINESSKEY, businessKey);
		// 设置流程变量[startUser]。
		Authentication.setAuthenticatedUserId(userId);
		if (processCmd.getActDefId() != null) {
			processInstance = bpmService.startFlowById(processCmd.getActDefId(), businessKey,processCmd.getVariables());
		} else {
			processInstance = bpmService.startFlowByKey(processCmd.getFlowKey(), businessKey,processCmd.getVariables());
		}
		Authentication.setAuthenticatedUserId(null);
		return processInstance;
	}

	/**
	 * 取得流程第一个节点。
	 * 
	 * @param actDefId
	 * @return
	 */
	public String getFirstNodetByDefId(String actDefId) {
		String bpmnXml = bpmService.getDefXmlByProcessDefinitionId(actDefId);
		// 取得第一个任务节点
		String firstTaskNode = BpmUtil.getFirstTaskNode(bpmnXml);
		return firstTaskNode;
	}

	/**
	 * 执行前后处理器。 0 代表失败 1代表成功，-1代表不需要执行
	 * 
	 * @param processCmd
	 * @param bpmNodeSet
	 * @param isBefore
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private void invokeHandler(ProcessCmd processCmd, BpmNodeSet bpmNodeSet,
			boolean isBefore) throws Exception {
		if (bpmNodeSet == null)
			return;
		String handler = "";
		if (isBefore) {
			handler = bpmNodeSet.getBeforeHandler();
		} else {
			handler = bpmNodeSet.getAfterHandler();
		}
		if (StringUtil.isEmpty(handler))
			return;
		
		
		String[] aryHandler = handler.split("[.]");
		if (aryHandler != null) {
			String beanId = aryHandler[0];
			String method = aryHandler[1];
			// 触发该Bean下的业务方法
			Object serviceBean = AppUtil.getBean(beanId);
			if (serviceBean != null) {
				Method invokeMethod = serviceBean.getClass().getDeclaredMethod(
						method, new Class[] { ProcessCmd.class });
				invokeMethod.invoke(serviceBean, processCmd);
			}
		}
	}

	/**
	 * 获取起始节点的BpmNodeSet。
	 * 
	 * @param defId
	 * @param toFirstNode
	 * @param firstBpmNodeSet
	 * @return
	 */
	public BpmNodeSet getStartBpmNodeSet(Long defId, String actDefId,
			String nodeId, Short toFirstNode) {

		BpmNodeSet bpmNodeSetStart = bpmNodeSetDao.getBySetType(defId,BpmNodeSet.SetType_StartForm);
		BpmNodeSet bpmNodeSetGlobal = bpmNodeSetDao.getBySetType(defId,BpmNodeSet.SetType_GloabalForm);
		if (bpmNodeSetStart == null) {
			// 允许跳到下一个节点
			if (toFirstNode == 1) {
				BpmNodeSet firstBpmNodeSet = bpmNodeSetDao.getByDefIdNodeId(
						defId, nodeId);
				// 取得流程的第一个节点。
				if (firstBpmNodeSet.getFormType() != null) {
					return firstBpmNodeSet;
				}
				// 获取全局的表单数据
				else if (bpmNodeSetGlobal != null) {
					if (bpmNodeSetGlobal.getFormType() != null) {
						return bpmNodeSetGlobal;
					}
				}
			}
			// 获取全局的表单数据
			else if (bpmNodeSetGlobal != null) {
				if (bpmNodeSetGlobal.getFormType() != null) {
					return bpmNodeSetGlobal;
				}
			}
		} else {
			return bpmNodeSetStart;
		}
		return null;
	}

	/**
	 * 启动流程。<br>
	 * 
	 * <pre>
	 * 步骤： 
	 * 1.表单数据保存。 
	 * 2.启动流程。 
	 * 3.记录流程运行情况。 
	 * 4.记录流程执行堆栈。 
	 * 5.根据流程的实例ID，查询任务ID。
	 * 6.取得任务Id，并完成该任务。 
	 * 7.记录流程堆栈。
	 * </pre>
	 * 
	 * @param processCmd
	 * @return
	 * @throws Exception
	 */
	public ProcessRun startProcess(ProcessCmd processCmd) throws Exception {
		BpmDefinition bpmDefinition = getBpmDefinitionProcessCmd(processCmd);
		// 通过webservice启动流程时，传入的actDefId或者flowKey不正确时抛出这个异常
		if (bpmDefinition == null)
			throw new Exception("没有该流程的定义");
		if (bpmDefinition.getDisableStatus()==BpmDefinition.DISABLEStATUS_DA)
			throw new Exception("该流程已经被禁用");
		// 是否跳转到第一个流程节点
		Long defId = bpmDefinition.getDefId();
		// 是否跳过第一个任务节点，当为1 时，启动流程后完成第一个任务。
		Short toFirstNode = bpmDefinition.getToFirstNode();
		String actDefId = bpmDefinition.getActDefId();

		String nodeId = getFirstNodetByDefId(actDefId);

		// 开始节点
		BpmNodeSet bpmNodeSet = getStartBpmNodeSet(defId, actDefId, nodeId,toFirstNode);

		ISysUser sysUser = ContextUtil.getCurrentUser();
		ProcessRun processRun = null;
		try {
			// 如果第一步跳转，那么设置发起人为任务执行人。
			if (toFirstNode == 1) {
				List<TaskExecutor> excutorList=new ArrayList<TaskExecutor>();
				excutorList.add( TaskExecutor.getTaskUser(sysUser.getUserId().toString(), sysUser.getFullname()));
				taskUserAssignService.addNodeUser(nodeId,excutorList);
			}
			
			// 设置下一步包括分发任务的用户
			setNextTaskUser(processCmd);
			// 初始流程运行记录。
			processRun = initProcessRun(bpmDefinition);
			// 添加流程runId。
			processCmd.getVariables().put(BpmConst.FLOW_RUNID,processRun.getRunId());

			// 在线表单数据处理
			String businessKey = "";
			// 外部调用不执行表单字段处理。
			if (!processCmd.isInvokeExternal()) {
				BpmFormData bpmFormData= handlerFormData(processCmd,processRun ,"");
				if(bpmFormData!=null){
					businessKey=processCmd.getBusinessKey();
				}else{
					
				}
			}
		
			// 调用前置处理器
			if (!processCmd.isSkipPreHandler()) {
				invokeHandler(processCmd, bpmNodeSet, true);
			}
		
			if (StringUtil.isEmpty(businessKey)) {
				businessKey = processCmd.getBusinessKey();
			}
			// 生成流程定义标题
			String subject = getSubject(bpmDefinition, processCmd);
	
			ProcessInstance processInstance = startWorkFlow(processCmd);

			String processInstanceId = processInstance.getProcessInstanceId();
			processRun.setBusinessKey(businessKey);

			processRun.setActInstId(processInstanceId);
			processRun.setSubject(subject);
			ISysOrg sysOrg=ContextUtil.getCurrentOrg();
			if(sysOrg!=null){
				processRun.setStartOrgId(sysOrg.getOrgId());
				processRun.setStartOrgName(sysOrg.getOrgName());
			}

			this.add(processRun);
			List<Task> taskList = TaskThreadService.getNewTasks();
			// 初始化执行的堆栈树
			executionStackService.initStack(processInstanceId, taskList);

			processCmd.setProcessRun(processRun);
			// 后置处理器
			if (!processCmd.isSkipAfterHandler()) {
				invokeHandler(processCmd, bpmNodeSet, false);
			}

			// 获取下一步的任务并完成跳转。
			if (toFirstNode == 1) {
				handJumpOverFirstNode(processInstanceId, processCmd);
			}
			// 添加运行期表单，外部调用时不对表单做处理。
			if (!processCmd.isInvokeExternal()) {
				bpmFormRunService.addFormRun(actDefId, processRun.getRunId(),processInstanceId);
			}
			// 更新通知人员任务的流程实例id。
			genNotifyTasks(processInstanceId, ContextUtil.getCurrentUserId().toString());
			// 处理信息
			notify(TaskThreadService.getNewTasks(), processCmd.getInformType(),subject, null);
			// 添加到流程运行日志
			String memo = "启动流程:" + subject;
			bpmRunLogService.addRunLog(processRun.getRunId(),BpmRunLog.OPERATOR_TYPE_START, memo);
		
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// 清空在线程中绑定的用户等信息.
			clearThreadLocal();
		}

		return processRun;
	}

	/**
	 * 产生通知任务。
	 * 
	 * @param instanceId
	 *            流程实例id。
	 * @param processCmd
	 * @param taskList
	 */
	private void genNotifyTasks(String instanceId, String startUserId) {
		List<Task> taskList = TaskThreadService.getNewTasks();
		if (taskList == null)
			return;
		for (Task task : taskList) {
			String actDefId = task.getProcessDefinitionId();
			String nodeId = task.getTaskDefinitionKey();
			List<TaskExecutor> userList = bpmNodeUserService.getUsers(BpmNodeUser.USER_TYPE_NOTIFY, actDefId, instanceId, nodeId,startUserId, startUserId);
			bpmService.genNotifyTask((DelegateTask) task, userList);
		}
	}

	/**
	 * 处理任务跳转。
	 * 
	 * @param processInstanceId
	 * @param processCmd
	 * @throws Exception
	 */
	private void handJumpOverFirstNode(String processInstanceId,
			ProcessCmd processCmd) throws Exception {
		TaskThreadService.clearNewTasks();
		List<ProcessTask> taskList = bpmService.getTasks(processInstanceId);
		ProcessTask taskEntity = taskList.get(0);
		String taskId = taskEntity.getId();
		String parentNodeId = taskEntity.getTaskDefinitionKey();
		// 填写第一步意见。
		processCmd.getVariables().put(BpmConst.NODE_APPROVAL_STATUS + "_" + parentNodeId,TaskOpinion.STATUS_AGREE);
		processCmd.getVariables().put(BpmConst.NODE_APPROVAL_CONTENT + "_" + parentNodeId, "填写表单");
		setVariables(taskId, processCmd);
		// 进行流程跳转。
		bpmService.transTo(taskId, "" );
		executionStackService.addStack(taskEntity.getProcessInstanceId(),parentNodeId, "");
	}

	/**
	 * 处理在线表单数据。
	 * 
	 * @param processRun
	 * @param processCmd
	 * @return
	 * @throws Exception
	 */
	private BpmFormData handlerFormData(ProcessCmd processCmd,ProcessRun processRun,String nodeId)
			throws Exception {
		BpmFormData bpmFormData = null;
		
		String json = processCmd.getFormData();
		
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		
		String businessKey="";
		//判断节点Id是否为空，为空表示开始节点。
		boolean isStartFlow=false;
		if(StringUtil.isEmpty(nodeId)){
			businessKey = processCmd.getBusinessKey();
			isStartFlow=true;
		}
		else{
			businessKey = processRun.getBusinessKey();
		}
		//【HT】，改为在这里就构造了
		bpmFormData = FormDataUtil.parseJson(json);
//		bpmFormData.getSubTableList().get(1).getPkName();
		
		
		//取出主表名和主键的Map
		Map<Long,ProRunTblPk> tableAndPk = (Map<Long,ProRunTblPk>)processCmd.getVariables().get(BpmConst.TABLE_AND_PK);
		if(tableAndPk==null){
			tableAndPk = new HashMap<Long,ProRunTblPk>();
		}
		//当前表ID
		String currentTableName = bpmFormData.getTableName();
		Long currentTableId = bpmFormData.getTableId();		
		if(currentTableId!=null){
			ProRunTblPk pk = tableAndPk.get(currentTableId);
			if(pk==null ||  !businessKey.equals(pk.getPkId())){//表示换表了
				//更新businessKey
				businessKey = pk==null?null:pk.getPkId().toString();
			}
		}
		
		// 有主键的情况，表示数据已经存在。
		if (StringUtil.isNotEmpty(businessKey)) {
			PkValue pkValue = new PkValue(TableModel.PK_COLUMN_NAME,businessKey);
			pkValue.setIsAdd(false);
			bpmFormData.setPkValue(pkValue);
			
			Map<String,Object> map=bpmFormHandlerDao.getByKey(bpmFormData.getTableName(), businessKey);
			
			if(BeanUtils.isEmpty(map.get(TableModel.CUSTOMER_COLUMN_FLOWRUNID))){
				//设置上下文数据。
				setContextMainFields(bpmFormData,processRun);
			}
		} else {			
			//设置上下文数据。
			setContextMainFields(bpmFormData,processRun);
		}
		
		processCmd.putVariables(bpmFormData.getVariables());
		// 生成的主键
		PkValue pkValue = bpmFormData.getPkValue();
		businessKey = pkValue.getValue().toString();

		//【HT】更新businessKey到map中
		processRun.setBusinessKey(businessKey);
		processRun.setTableName(currentTableName);
		processRun.setTableId(currentTableId);
		processCmd.setBusinessKey(businessKey);
		
		//启动流程。
		if(isStartFlow){
			// 保存表单数据,存取表单数据
			bpmFormHandlerDao.handFormData(bpmFormData,"","");
		}
		else{
			bpmFormHandlerDao.handFormData(bpmFormData,processRun.getActDefId(),nodeId);
			this.update(processRun);
		}
		
		
		ProRunTblPk mainPk = new ProRunTblPk();
		mainPk.setPkId(Long.valueOf(bpmFormData.getPkValue().getValue().toString()));
		mainPk.setIsMainTable(ProRunTblPk.IS_MAIN_TABLE_YES);
		mainPk.setPkName(bpmFormData.getPkValue().getName());
		mainPk.setTableId(bpmFormData.getTableId());
		mainPk.setTableName(bpmFormData.getTableName());
		mainPk.setRunId(processRun.getRunId());
		mainPk.setTblPkId(UniqueIdUtil.genId());
		
		tableAndPk.put(currentTableId, mainPk);
		
		if(bpmFormData.getSubTableList()!=null){
			for(SubTable sub:bpmFormData.getSubTableList()){
				List<ProRunTblPk> subPks = new ArrayList<ProRunTblPk>();
				BpmFormTable subBpmFormTable = bpmFormTableDao.getByTableName(sub.getTableName().replaceFirst(TableModel.CUSTOMER_TABLE_PREFIX, ""));
				if(subBpmFormTable!=null){
					for(Map<String,Object> map:sub.getDataList()){ 
						ProRunTblPk subPk = new ProRunTblPk();
						String key=TableModel.PK_COLUMN_NAME.toLowerCase();
						Object subPkValue = map.get(key);
						subPk.setPkId(Long.valueOf(subPkValue.toString()));
						subPk.setIsMainTable(ProRunTblPk.IS_MAIN_TABLE_NO);
						subPk.setPkName(sub.getPkName());
						subPk.setTableId(subBpmFormTable.getTableId());
						subPk.setTableName(sub.getTableName());
						subPk.setRunId(processRun.getRunId());
						subPk.setMainTableId(currentTableId);
						subPk.setMainTablePk(mainPk.getPkId());
						subPk.setTblPkId(UniqueIdUtil.genId());
						subPks.add(subPk);
					}
					mainPk.getSubProRunTblPkMap().put(subBpmFormTable.getTableId(), subPks);
				}
			}
		}
		
		
		proRunTblPkDao.delBySqlKey("delByRunId",processRun.getRunId());
		for(Entry<Long, ProRunTblPk> main:tableAndPk.entrySet()){
			proRunTblPkDao.addCascade(main.getValue());
		}
		
		//【HT】设置参数到流程变量中
		processCmd.getVariables().put(BpmConst.TABLE_AND_PK, tableAndPk);
		
		return bpmFormData;
	}
	
	
	private Map<String,Object> getBizVariables(Map<String,Object> allVariables){
		Map<String,Object> bizVariables = new HashMap<String,Object>();
		for(Iterator<String> it = allVariables.keySet().iterator();it.hasNext();){		
			String key = it.next();
			if(key.equals("startUser") || key.equals("flowRunId") || key.equals("businessKey") || key.startsWith("HT_")){
				continue;
			}else{
				bizVariables.put(key, allVariables.get(key));
			}
		}
		return bizVariables;
	}
	/**
	 * 设置上下文数据，添加数据是有效。
	 * @param bpmFormData
	 * @param processRun
	 */
	private void setContextMainFields(BpmFormData  bpmFormData,ProcessRun processRun ){
		String userId = ContextUtil.getCurrentUserId().toString();
		// 添加用户数据
		bpmFormData.addMainFields(TableModel.CUSTOMER_COLUMN_CURRENTUSERID,userId);
		// 添加当前组织
		ISysOrg currentOrg = ContextUtil.getCurrentOrg();
		if (currentOrg != null) {
			bpmFormData.addMainFields(TableModel.CUSTOMER_COLUMN_CURRENTORGID, currentOrg.getOrgId());
		}
		// 添加流程运行ID。
		bpmFormData.addMainFields(TableModel.CUSTOMER_COLUMN_FLOWRUNID,processRun.getRunId());
		// 设置流程定义Id。
		bpmFormData.addMainFields(TableModel.CUSTOMER_COLUMN_DEFID,processRun.getDefId());
	}
	
	/**
	 * 处理在线表单数据。
	 * 
	 * @param processCmd
	 * @param processRun
	 * @return
	 * @throws Exception
	 */
//	private Map<String, String> handlerFormData(ProcessCmd processCmd,
//			ProcessRun processRun,String nodeId) throws Exception {
//		String json = processCmd.getFormData();
//		Map<String, String> optionsMap = null;
//		if (StringUtils.isNotEmpty(json)) {
//			PkValue pkValue = new PkValue(processRun.getPkName(),processRun.getBusinessKey());
//			BpmFormData bpmFormData = FormDataUtil.parseJson(json, pkValue);
//			processCmd.setVariables(bpmFormData.getVariables());
//			String actDefId=processRun.getActDefId();
//			bpmFormHandlerDao.handFormData(bpmFormData,actDefId,nodeId);
//			optionsMap = bpmFormData.getOptions();
//		}
//		return optionsMap;
//	}

	/**
	 * 保存表单数据。
	 * 
	 * @param json
	 *            json数据。
	 * @param userId
	 *            用户ID。
	 * @param taskId
	 *            任务ID。
	 * @param defId
	 *            流程定义ID。
	 * @return
	 * @throws Exception
	 */
	public String saveData(String json, String userId, String taskId,
			Long defId, String bizKey) throws Exception {
		if (StringUtil.isEmpty(json)) {
			return "-1";
		}
		String businessKey = "";
		if (StringUtil.isNotEmpty(taskId)) {
			TaskEntity taskEntity = bpmService.getTask(taskId);
			ProcessRun processRun = dao.getByActInstanceId(taskEntity
					.getProcessInstanceId());
			PkValue pkValue = new PkValue(TableModel.PK_COLUMN_NAME,processRun.getBusinessKey());
			BpmFormData bpmFormData = FormDataUtil.parseJson(json, pkValue);
//			//修复保存表单bu
//			businessKey = processRun.getBusinessKey();
//			// 有主键的情况，表示数据已经存在。
//			if (StringUtil.isNotEmpty(businessKey)) {
//				pkValue.setIsAdd(false);
//				bpmFormData.setPkValue(pkValue);
//				
//				Map<String,Object> map=bpmFormHandlerDao.getByKey(bpmFormData.getTableName(), businessKey);
//				
//				if(BeanUtils.isEmpty(map.get(TableModel.CUSTOMER_COLUMN_FLOWRUNID))){
//					//设置上下文数据。
//					setContextMainFields(bpmFormData,processRun);
//				}
//			} else {			
//				//设置上下文数据。
//				pkValue.setIsAdd(true);
//				setContextMainFields(bpmFormData,processRun);
//			}
			bpmFormHandlerDao.handFormData(bpmFormData, processRun.getActDefId(), taskEntity.getTaskDefinitionKey());
			businessKey = processRun.getBusinessKey();
		} else {
			BpmFormData bpmFormData = null;
			if (StringUtil.isNotEmpty(bizKey)) {
				PkValue pkValue = new PkValue(TableModel.PK_COLUMN_NAME, bizKey);
				bpmFormData = FormDataUtil.parseJson(json, pkValue);
			} else {
				bpmFormData = FormDataUtil.parseJson(json);
			}

			// 添加用户数据
			bpmFormData.addMainFields(TableModel.CUSTOMER_COLUMN_CURRENTUSERID,
					userId);
			// 添加流程定义ID
			bpmFormData.addMainFields(TableModel.CUSTOMER_COLUMN_DEFID, defId);
			// 保存表单数据,存取表单数据
			bpmFormHandlerDao.handFormData(bpmFormData,"","");
			PkValue pkValue = bpmFormData.getPkValue();
			businessKey = pkValue.getValue().toString();
		}
		return businessKey;

	}

	/**
	 * 获取流程标题。
	 * 
	 * @param bpmDefinition
	 * @param processCmd
	 * @return
	 */
	private String getSubject(BpmDefinition bpmDefinition, ProcessCmd processCmd) {
		// 若设置了标题，则直接返回该标题，否则按后台的标题规则返回
		if (StringUtils.isNotEmpty(processCmd.getSubject())) {
			return processCmd.getSubject();
		}

		String rule = bpmDefinition.getTaskNameRule();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", bpmDefinition.getSubject());
		ISysUser user = ContextUtil.getCurrentUser();
		map.put(BpmConst.StartUser, user.getUsername());
		map.put("startDate", TimeUtil.getCurrentDate());
		map.put("startTime", TimeUtil.getCurrentTime());
		map.put("businessKey", processCmd.getBusinessKey());
		map.putAll(processCmd.getVariables());
		rule = BpmUtil.getTitleByRule(rule, map);
		return rule;
	}

	/**
	 * 初始化ProcessRun.
	 * 
	 * @return
	 */
	private ProcessRun initProcessRun(BpmDefinition bpmDefinition) {
		ProcessRun processRun = new ProcessRun();
		ISysUser curUser = ContextUtil.getCurrentUser();
		processRun.setCreator(curUser.getFullname());
		processRun.setCreatorId(curUser.getUserId());

		processRun.setActDefId(bpmDefinition.getActDefId());
		processRun.setDefId(bpmDefinition.getDefId());
		processRun.setProcessName(bpmDefinition.getSubject());

		processRun.setCreatetime(new Date());
		processRun.setStatus(ProcessRun.STATUS_RUNNING);
		try {
			processRun.setRunId(UniqueIdUtil.genId());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return processRun;
	}

	/**
	 * 按流程实例ID获取ProcessRun实体
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public ProcessRun getByActInstanceId(String processInstanceId) {
		return dao.getByActInstanceId(processInstanceId);
	}

	/**
	 * 获取历史实例
	 * 
	 * @param queryFilter
	 * @return
	 */
	public List<ProcessRun> getAllHistory(QueryFilter queryFilter) {
		return dao.getAllHistory(queryFilter);
	}

	/**
	 * 查看我参与审批流程列表
	 * 
	 * @param filter
	 * @return
	 */
	public List<ProcessRun> getMyAttend(QueryFilter filter) {
//		return dao.getMyAttend(filter);
		return dao.getMyAttend(filter);
	}

	

	@Override
	public void delByIds(Long[] ids) {
		if (ids == null || ids.length == 0)
			return;
		for (Long uId : ids) {
			ProcessRun processRun = getById(uId);
			Short procStatus = processRun.getStatus();
			if (ProcessRun.STATUS_FINISH != procStatus) {
				processRun.setStatus(ProcessRun.STATUS_MANUAL_FINISH);
				/////ht
				//更新业务实例状态
				/////ht del
//				deleteProcessInstance(processRun);
				/////ht del e
				if(ProcessRun.STATUS_MANUAL_FINISH!=procStatus){
					BizCmd bizCmd=(BizCmd) runtimeService.getVariable(processRun.getActInstId(), BizProcessVarsConst.BIZ_CONTEXT);
					if(bizCmd!=null){
						bizCmd.setProcessRun(processRun);
						Map<String,Object> vars = runtimeService.getVariables(processRun.getActInstId());
						bizCmd.getBizVars().putAll(vars);
						bizCmd.getBizVars().remove(BizProcessVarsConst.BIZ_CONTEXT);
					}
					deleteProcessInstance(processRun);
					if(bizCmd!=null){
						bizInstanceService.updBizInstance(bizCmd);
					}
				}else{
					deleteProcessInstance(processRun);
				}
				/////ht e
			}else{
				// 流程操作日志
				String memo = "用户删除了流程实例[" + processRun.getProcessName() + "]。";
				bpmRunLogService.addRunLog(processRun.getRunId(),
						BpmRunLog.OPERATOR_TYPE_DELETEINSTANCE, memo);
				delById(uId);
			}
		}
	}

	public List<ProcessRun> getMyProcessRun(Long creatorId, String subject,
			Short status, PageBean pb) {
		return dao.getMyProcessRun(creatorId, subject, status, pb);
	}

	
	@Override
	public void add(ProcessRun entity) {
		super.add(entity);
		ProcessRun history = (ProcessRun) entity.clone();
		dao.addHistory(history);
	}
	
	@Override
	public void update(ProcessRun entity) {
//		ProcessRun history = dao.getByIdHistory(entity.getRunId());
		ProcessRun history = (ProcessRun) entity.clone();
		if(ProcessRun.STATUS_MANUAL_FINISH == entity.getStatus()||ProcessRun.STATUS_FINISH == entity.getStatus()){
			Date endDate = new Date();
			Date startDate=history.getCreatetime();
			long userId=history.getCreatorId();
			long duration=calendarAssignService.getTaskMillsTime(startDate, endDate, userId);
			history.setEndTime(endDate);
			history.setDuration(duration);
			
			/////ht
			//更新业务实例状态
			/////ht del
//			dao.updateHistory(history);
//			dao.delById(entity.getRunId());
			/////ht del e
			BizCmd bizCmd=(BizCmd) runtimeService.getVariable(entity.getActInstId(), BizProcessVarsConst.BIZ_CONTEXT);
			if(bizCmd!=null){
				bizCmd.setProcessRun(entity);
				Map<String,Object> vars = runtimeService.getVariables(entity.getActInstId());
				bizCmd.getBizVars().putAll(vars);
				bizCmd.getBizVars().remove(BizProcessVarsConst.BIZ_CONTEXT);
			}
			dao.updateHistory(history);
			dao.delById(entity.getRunId());
			if(bizCmd!=null){
				bizInstanceService.updBizInstance(bizCmd);
			}
			/////ht e
		}else{
			dao.updateHistory(history);
			super.update(entity);
		}
	}
	

	/**
	 * 根据流程运行Id,删除流程运行实体。<br/>
	 * 些方法不会级联删除相关信息。
	 * @see com.hotent.core.service.GenericService#delById(java.io.Serializable)
	 */
	@Override
	public void delById(Long id) {
		dao.delById(id);
		dao.delByIdHistory(id);
	}
	
	
	/**
	 * 根据Act流程定义ID，获取流程实例
	 * @param actDefId
	 * @param pb
	 * @return
	 */
	public List<ProcessRun> getByActDefId(String actDefId,PageBean pb){
		return dao.getByActDefId(actDefId, pb);
	}
	
	/**
	 * 级联删除流程实例扩展
	 * @param processRun
	 */
	private void deleteProcessRunCasade(ProcessRun processRun){
		List<ProcessInstance> childrenProcessInstance = runtimeService.createProcessInstanceQuery().superProcessInstanceId(processRun.getActInstId()).list();
		for(ProcessInstance instance:childrenProcessInstance){
			ProcessRun pr = getByActInstanceId(instance.getProcessInstanceId());
			if(pr!=null){
				deleteProcessRunCasade(pr);
			}
		}
		long procInstId = Long.parseLong(processRun.getActInstId());
		Short procStatus = processRun.getStatus();
		if (ProcessRun.STATUS_FINISH != procStatus) {
			executionDao.delVariableByProcInstId(procInstId);
			taskDao.delCandidateByInstanceId(procInstId);
			taskDao.delByInstanceId(procInstId);
			executionDao.delExecutionByProcInstId(procInstId);
		}
		String memo = "用户删除了流程实例[" + processRun.getProcessName() + "]。";
		bpmRunLogService.addRunLog(processRun,
				BpmRunLog.OPERATOR_TYPE_DELETEINSTANCE, memo);
		delById(processRun.getRunId());
	}

	/**
	 * 获取流程扩展的根流程扩展（最顶层的父流程扩展）
	 * @param processRun
	 * @return
	 */
	private ProcessRun getRootProcessRun(ProcessRun processRun){
		ProcessInstance parentProcessInstance = runtimeService.createProcessInstanceQuery().subProcessInstanceId(processRun.getActInstId()).singleResult();
		if(parentProcessInstance!=null){
			//Get parent ProcessRun
			ProcessRun parentProcessRun = getByActInstanceId(parentProcessInstance.getProcessInstanceId());
			//Get Parent ProcessInstance sub ProcessInstance
			return getRootProcessRun(parentProcessRun);
		}
		return processRun;
	}
	
	
	/**
	 * 删除流程运行实体（ProcessRun），级联删除Act流程实例以及父流程
	 * @param processRun
	 */
	private void deleteProcessInstance(ProcessRun processRun){
		ProcessRun rootProcessRun = getRootProcessRun(processRun);
		deleteProcessRunCasade(rootProcessRun);
	}
	
	public void delByActDefId(String actDefId){
		List<ProcessRun> list = dao.getbyActDefId(actDefId);
		for(ProcessRun processRun:list){
			bpmTaskAssigneeDao.delByRunId(processRun.getRunId());
			/////ht
			//更新业务实例状态
			/////ht del
//			deleteProcessInstance(processRun);
			/////ht del e
			BizCmd bizCmd=(BizCmd) runtimeService.getVariable(processRun.getActInstId(), BizProcessVarsConst.BIZ_CONTEXT);
			if(bizCmd!=null){
				bizCmd.setProcessRun(processRun);
				Map<String,Object> vars = runtimeService.getVariables(processRun.getActInstId());
				bizCmd.getBizVars().putAll(vars);
				bizCmd.getBizVars().remove(BizProcessVarsConst.BIZ_CONTEXT);
			}
			deleteProcessInstance(processRun);
			if(bizCmd!=null){
				bizInstanceService.updBizInstance(bizCmd);
			}
			/////ht e
		}
		dao.delHistroryByActDefId(actDefId);
	}
}
