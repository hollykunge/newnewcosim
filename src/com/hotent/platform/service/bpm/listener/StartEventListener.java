package com.hotent.platform.service.bpm.listener;

import java.util.Date;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.bpm.BpmDefinitionDao;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmFormRunService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.thread.TaskThreadService;

/**
 * 开始事件监听器。
 * @author ray
 *
 */
public class StartEventListener extends BaseNodeEventListener {

	private final static Log logger=LogFactory.getLog(StartEventListener.class);
	
	@Override
	protected void execute(DelegateExecution execution, String actDefId,String nodeId) {
		logger.debug("nodeId" + nodeId);
		handExtSubProcess(execution);
	}
	
	/**
	 * 处理子流程。
	 * <pre>
	 * 1.如果流程变量当中有“innerPassVars”变量，那么这个是一个外部子流程的调用。
	 * 2.创建processrun记录。
	 * 3.准备流程实例id为初始化执行堆栈做准备。
	 * 4.处理运行表单。
	 * 5.通知任务执行人。
	 * </pre>
	 * @param execution
	 * @throws Exception 
	 */
	private void handExtSubProcess(DelegateExecution execution) {
		ExecutionEntity ent= (ExecutionEntity)execution;
		//非子流程调用直接返回。
		if( execution.getVariable(BpmConst.PROCESS_INNER_VARNAME)==null) return ;
		
		BpmFormRunService bpmFormRunService=(BpmFormRunService)AppUtil.getBean(BpmFormRunService.class);
		
		Map<String, Object> variables=(Map<String, Object>)ent.getVariable(BpmConst.PROCESS_INNER_VARNAME);
		//是否外部调用（这里指的是通过 webservice接口调用)
		Boolean isExtCall=(Boolean) variables.get(BpmConst.IS_EXTERNAL_CALL);
		String businessKey= (String)variables.get(BpmConst.FLOW_BUSINESSKEY);
		
		String instanceId= ent.getProcessInstanceId();
		//准备流程实例id为初始化执行堆栈做准备。
		TaskThreadService.addExtSubProcess( instanceId);
		
		String actDefId= ent.getProcessDefinitionId();
		//添加processRun。
		long runId=initProcessRun(actDefId,instanceId,variables);
		//设置流程运行变量
		execution.setVariable(BpmConst.FLOW_RUNID, runId);
		execution.setVariable(BpmConst.FLOW_BUSINESSKEY,businessKey);
		String subject=(String)variables.get(BpmConst.FLOW_RUN_SUBJECT);
		execution.setVariable(BpmConst.FLOW_RUN_SUBJECT,subject);
		
		//处理运行时表单。
		if(isExtCall!=null && !isExtCall){
			bpmFormRunService.addFormRun(actDefId, runId,instanceId);
		}
	}
	
	
	/**
	 * 插入流程运行记录。
	 * @param actDefId
	 * @param instanceId
	 * @param variables
	 */
	private Long initProcessRun(String actDefId,String instanceId,Map<String, Object> variables) {
		String businessKey=(String)variables.get(BpmConst.FLOW_BUSINESSKEY);
		Long parentRunId=(Long)variables.get(BpmConst.FLOW_RUNID);
		
		BpmDefinitionDao bpmDefinitionDao=(BpmDefinitionDao)AppUtil.getBean(BpmDefinitionDao.class);
		ProcessRunService processRunService=(ProcessRunService)AppUtil.getBean(ProcessRunService.class);
		BpmDefinition  bpmDefinition= bpmDefinitionDao.getByActDefId(actDefId);
		 
		ProcessRun processRun = new ProcessRun();
		ISysUser curUser = ContextUtil.getCurrentUser();
		processRun.setCreator(curUser.getFullname());
		processRun.setCreatorId(curUser.getUserId());
		
		processRun.setActDefId(bpmDefinition.getActDefId());
		processRun.setDefId(bpmDefinition.getDefId());
		processRun.setProcessName(bpmDefinition.getSubject());
		
		processRun.setActInstId(instanceId);
		
		String subject=(String)variables.get(BpmConst.FLOW_RUN_SUBJECT);
		processRun.setSubject(subject);
		processRun.setBusinessKey(businessKey);
		
		processRun.setCreatetime(new Date());
		processRun.setStatus(ProcessRun.STATUS_RUNNING);
		processRun.setRunId(UniqueIdUtil.genId());
		processRun.setParentId(parentRunId);
		
		
		processRunService.add(processRun);
		
		return processRun.getRunId();
	}

	@Override
	protected Integer getScriptType() {
		
		return BpmConst.StartScript;
	}

}
