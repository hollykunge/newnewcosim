package com.casic.cloud.CA.webservice.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.bpmn.data.SimpleDataInputAssociation;
import org.apache.cxf.binding.corba.utils.ContextUtils;

import com.casic.cloud.CA.webservice.api.CAService;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.bpm.TaskDao;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.system.SysUserService;

public class CAServiceImpl implements CAService {
	@Resource
	private TaskService taskService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private TaskDao taskDao;

	@Override
	public String getTasksXMLByShortAccount(String shortAccount) {
		// TODO Auto-generated method stub
		String account = "620001_" + shortAccount;
		ISysUser sysUser = sysUserService.getByAccount(account);
		List<ProcessTask> processTasks = taskDao.getTasks(sysUser.getUserId(),
				null, null, null, null, null, null);
		constructTaskUrls(processTasks);
		StringBuffer xml = new StringBuffer("<messages>");
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		for (ProcessTask task : processTasks) {
			xml.append("<message>");
			xml.append("<syscode>001</syscode>");
			xml.append("<msgtype>待办事项</msgtype>");
			xml.append("<msgtitle>");
			xml.append(task.getProcessName() + "流程-" + task.getName() + "任务");
			xml.append("</msgtitle>");
			xml.append("<sender></sender>");
			xml.append("<sendtime>");
			xml.append(dateFormat.format(task.getCreateTime()));
			xml.append("</sendtime>");
			xml.append("<msgurl>");
			try {
				xml.append("http://localhost:9090/tianzhi" + "/CA/CAPost.ht?url="
						+ "http://localhost:9090/tianzhi"
						+ "/platform/bpm/task/toStart.ht?taskId="
						+ task.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			xml.append("</msgurl>");
			xml.append("</message>");
		}
		xml.append("</messages>");
		return xml.toString();
	}

	private void constructTaskUrl(ProcessTask processTask) {
		if (processTask == null)
			return;
		BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(
				processTask.getProcessDefinitionId(),
				processTask.getTaskDefinitionKey());
		Map<String, Object> varsMap = taskService.getVariables(processTask
				.getId());

		if (bpmNodeSet == null)
			return;

		String formUrl = bpmNodeSet.getFormUrl();
		if (StringUtil.isNotEmpty(formUrl)) {
			formUrl = StringUtil.formatParamMsg(formUrl, varsMap);
			processTask.setTaskUrl(formUrl);
		}
	}

	private void constructTaskUrls(List<ProcessTask> processTaskList) {
		for (ProcessTask processTask : processTaskList) {
			constructTaskUrl(processTask);
		}
	}

}
