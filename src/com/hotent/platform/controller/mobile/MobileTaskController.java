package com.hotent.platform.controller.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmTaskAssignee;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.mobile.MobileFormData;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.BpmTaskAssigneeService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.mobile.MobileTaskService;

@Controller
@RequestMapping("/platform/mobile/mobileTask/")
public class MobileTaskController extends BaseController {
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmService bpmService;
	@Resource
	private MobileTaskService mobileTaskService;
	@Resource
	private BpmTaskAssigneeService bpmTaskAssigneeService;
	
	
	@RequestMapping("myTask")
	@Action(description="我的待办")
	@ResponseBody
	public Object myTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String subject = request.getParameter("subject");
		
		QueryFilter filter = new QueryFilter(request, "");
		if(StringUtil.isNotEmpty(subject)){
			filter.addFilter("subject", "%"+subject+"%");
		}
		filter.addFilter("userId", ContextUtil.getCurrentUserId());
		List<TaskEntity> list = bpmService.getMyMobileTasks(filter);
		
		Map taskMap = new HashMap();
		taskMap.put("tasks", list);

		return taskMap;
	}
	
	@RequestMapping("getMyTaskForm")
	@Action(description="获取待办表单数据")
	@ResponseBody
	public  Object getMyTaskForm(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		String taskId=RequestUtil.getString(request, "taskId");
		MobileFormData formdata=mobileTaskService.getFormData(taskId,request.getContextPath());
		Map taskMap = new HashMap();
		taskMap.put("form", JSONObject.fromObject(formdata));
		taskMap.put("taskId", taskId);
		
		return taskMap;
	}
	
	@RequestMapping("submitForm")
	@Action(description="提交流程表单")
	@ResponseBody
	public Object submitForm(HttpServletRequest request,HttpServletResponse response) 
	{	
		Map responseMap = new HashMap();
		ISysUser sysUser=ContextUtil.getCurrentUser();
		String userAccount=sysUser.getAccount();
		String taskId=RequestUtil.getString(request, "taskId");
		String opinion=RequestUtil.getString(request, "opinion");
		String voteAgree=RequestUtil.getString(request, "voteAgree");
		try {
			mobileTaskService.complete(taskId, opinion,userAccount, voteAgree);
			responseMap.put("success", true);
			responseMap.put("msg", "任务执行成功");
		} catch (Exception e) {
			responseMap.put("success", false);
			responseMap.put("msg", "任务跳转失败"+e.getMessage());
		}
		return responseMap;
	}
	
	@RequestMapping("myAttend")
	@Action(description="已办事务")
	@ResponseBody
	public Object myAttend(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String subject = request.getParameter("subject");
		QueryFilter filter = new QueryFilter(request, "");
		if(StringUtil.isNotEmpty(subject)){
			filter.addFilter("subject", "%"+subject+"%");
		}
		filter.addFilter("assignee", ContextUtil.getCurrentUserId());
		List<ProcessRun> list = processRunService.getMyAttend(filter);
		
		Map taskMap = new HashMap();
		taskMap.put("tasks", list);

		return taskMap;
	}
	
	@RequestMapping("getMyAttendForm")
	@Action(description="已办事务")
	@ResponseBody
	public Object getMyAttendForm(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Long runId=RequestUtil.getLong(request, "runId");
		String ctxPath=request.getContextPath();
		MobileFormData formdata=mobileTaskService.getProcessData(runId,ContextUtil.getCurrentUserId(),ctxPath,"");
		Map taskMap = new HashMap();
		taskMap.put("form", JSONObject.fromObject(formdata));
		taskMap.put("runId", runId);
		return taskMap;
	}
	
	@RequestMapping("myAssignee")
	@Action(description="我收到的任务")
	@ResponseBody
	public Object myAssignee(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String subject = request.getParameter("subject");
		
		QueryFilter filter = new QueryFilter(request, "");
		if(StringUtil.isNotEmpty(subject)){
			filter.addFilter("subject", subject);
		}
		filter.addFilter("assigneeId", ContextUtil.getCurrentUserId());
		List<BpmTaskAssignee> list=bpmTaskAssigneeService.getAllMyTask(filter);
		
		Map taskMap = new HashMap();
		taskMap.put("tasks", list);

		return taskMap;
	}
	
	@RequestMapping("getMyAssignForm")
	@Action(description="我收到的任务表单")
	@ResponseBody
	public Object getMyAssignForm(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Long runId=RequestUtil.getLong(request, "runId");
		String taskId=RequestUtil.getString(request, "taskId");
		String ctxPath=request.getContextPath();
		MobileFormData formdata=mobileTaskService.getProcessData(runId,ContextUtil.getCurrentUserId(),ctxPath, taskId);
		Map taskMap = new HashMap();
		taskMap.put("form", JSONObject.fromObject(formdata));
		taskMap.put("runId", runId);
		return taskMap;
	}
	
	@RequestMapping("taskAssignee")
	@Action(description="我交办的任务")
	@ResponseBody
	public Object taskAssignee(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String subject = request.getParameter("subject");
		
		QueryFilter filter = new QueryFilter(request, "");
		if(StringUtil.isNotEmpty(subject)){
			filter.addFilter("subject", subject);
		}
		filter.addFilter("userId", ContextUtil.getCurrentUserId());
		List<BpmTaskAssignee> list=bpmTaskAssigneeService.getAllMyTask(filter);
		
		Map taskMap = new HashMap();
		taskMap.put("tasks", list);

		return taskMap;
	}
	
	@RequestMapping("getTaskAssignForm")
	@Action(description="我交办的任务表单")
	@ResponseBody
	public Object getTaskAssignForm(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Long runId=RequestUtil.getLong(request, "runId");
		String ctxPath=request.getContextPath();
		MobileFormData formdata=mobileTaskService.getProcessData(runId,ContextUtil.getCurrentUserId(),ctxPath,"");
		Map taskMap = new HashMap();
		taskMap.put("form", JSONObject.fromObject(formdata));
		taskMap.put("runId", runId);
		return taskMap;
	}
	
}
