package com.hotent.platform.service.bpm.listener;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.platform.dao.bpm.ExecutionDao;
import com.hotent.platform.dao.bpm.TaskDao;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.thread.TaskUserAssignService;
import com.hotent.platform.service.worktime.CalendarAssignService;

/**
 * 结束事件监听器。
 * @author ray
 *
 */
public class EndEventListener extends BaseNodeEventListener {

	@Override
	protected void execute(DelegateExecution execution, String actDefId,String nodeId) {
		ExecutionEntity ent=(ExecutionEntity)execution;
		if(!ent.isEnded()) return;
		
		//当前的excutionId和主线程相同时。
		if(ent.getId().equals(ent.getProcessInstanceId()) ){
			handEnd(ent);
		}
		
		//内嵌子流程的处理。
		if(ent.getActivity()!=null){
			String type=(String)ent.getActivity().getProperties().get("type");
			if("subProcess".equals(type)){
				TaskUserAssignService.clearFormUsers();
			}
		}
	}
	
	private void handEnd(ExecutionEntity ent){
		if(ent.getParentId()==null){
			//更新流程实例状态。
			updProcessRunStatus(ent);
			//删除知会任务。
			delNotifyTask(ent);
			
			//删除流程变量
			ExecutionDao executionDao=(ExecutionDao)AppUtil.getBean("executionDao");
			executionDao.delVariableByProcInstId(new Long( ent.getId()));
			executionDao.delSubExecutionByProcInstId(new Long( ent.getId()));
			
			//PROC_INST_ID_
		}
		
	}
	
	/**
	 * 流程终止时删除流程任务。
	 * <pre>
	 * 	1.删除流程实例任务。
	 *  2.删除任务的参与者。
	 * </pre>
	 * @param ent
	 */
	private void delNotifyTask(ExecutionEntity ent){
		Long instanceId=new Long( ent.getProcessInstanceId());
		TaskDao taskDao=(TaskDao)AppUtil.getBean("taskDao");
		//删除任务参与人
		taskDao.delCandidateByInstanceId(instanceId);
		//删除知会任务
		taskDao.delByInstanceId(instanceId);
	}
	
	
	/**
	 * 更新流程运行状态。
	 * <pre>
	 * 1.更新流程运行状态为完成。
	 * 2.计算流程过程的时间。
	 * </pre>
	 * @param ent
	 */
	private void updProcessRunStatus(ExecutionEntity ent){
		//流程结束后，需要更新流程实例的状态
		ProcessRunService processRunService=(ProcessRunService)AppUtil.getBean("processRunService");
		ProcessRun processRun =processRunService.getByActInstanceId(ent.getProcessInstanceId());
		if(BeanUtils.isEmpty(processRun)) return;
		//设置流程状态为完成。
		processRun.setStatus(ProcessRun.STATUS_FINISH);
		processRunService.update(processRun);
	}

	@Override
	protected Integer getScriptType() {
		
		return BpmConst.EndScript;
	}

}
