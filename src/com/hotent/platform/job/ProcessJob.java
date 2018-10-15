package com.hotent.platform.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.scheduler.BaseJob;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.webservice.api.ProcessService;

public class ProcessJob extends BaseJob {

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		ProcessService processService = (ProcessService) AppUtil.getBean(ProcessService.class);
		
		List<TaskExecutor> executors = new ArrayList<TaskExecutor>();
		executors.add(new TaskExecutor("comp", "10000000110000", "企业"));
		executors.add(new TaskExecutor("comp", "10000003880010", "企业"));
		executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_ROLE, "10000000040005", "销售经理"));
		executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_ROLE, "10000000270000", "采购经理"));
		
		ProcessCmd processCmd = new ProcessCmd();
		processCmd.setFlowKey("bxhq");
		processCmd.setTaskExecutors(executors);
		Map<String,Object> vars=new HashMap<String,Object>();
		processCmd.setVariables(vars);
		processCmd.setUserAccount("10000003880010_system");
		processCmd.setBusinessKey(String.valueOf(UniqueIdUtil.genId()));
		ProcessRun processRun = processService.start(processCmd);
		

	}
}
