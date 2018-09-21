package com.hotent.platform.service.bpm.listener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.bpm.BpmNodeScript;
import com.hotent.platform.service.bpm.BpmNodeScriptService;

/**
 * 任务事件基类，采用了模版模式。
 * @author ray
 *
 */
public abstract class BaseTaskListener implements org.activiti.engine.delegate.TaskListener{
	
	protected Logger logger=LoggerFactory.getLogger(BaseTaskListener.class);
	
	@Override
	public void notify(DelegateTask delegateTask) {
		
		logger.debug("enter the baseTaskListener notify method...");
		
		TaskEntity taskEnt=(TaskEntity)delegateTask;
		String nodeId=taskEnt.getExecution().getActivityId();
		String actDefId=taskEnt.getProcessDefinitionId();
		try{
			//执行子类业务逻辑
			execute(delegateTask,actDefId,nodeId);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
		//获取脚本类型
		int scriptType=getScriptType();
		//执行事件脚本
		exeEventScript(delegateTask,scriptType,actDefId,nodeId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行子类业务逻辑
	 * @param delegateTask
	 * @param actDefId
	 * @param nodeId
	 */
	protected abstract void execute(DelegateTask delegateTask,String actDefId,String nodeId);
	
	/**
	 * 获取脚本类型
	 * @return
	 */
	protected abstract int getScriptType();

	/**
	 * 执行事件脚本
	 * @param delegateTask
	 * @param scriptType
	 * @param actDefId
	 * @param nodeId
	 */
	private void exeEventScript(DelegateTask delegateTask,int scriptType,String actDefId,String nodeId ){
		try{
			logger.debug("enter the baseTaskListener exeEventScript method...");
			
			BpmNodeScriptService bpmNodeScriptService=(BpmNodeScriptService)AppUtil.getBean("bpmNodeScriptService");
			BpmNodeScript model=bpmNodeScriptService.getScriptByType(nodeId, actDefId,scriptType);			
			
			if(model==null) return;
			
			String script=model.getScript();
			if(StringUtil.isEmpty(script)) return;
			
			GroovyScriptEngine scriptEngine=(GroovyScriptEngine)AppUtil.getBean("scriptEngine");
			Map<String, Object> vars=delegateTask.getVariables();
			
			vars.put("task", delegateTask);
			scriptEngine.execute(script, vars);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
}
