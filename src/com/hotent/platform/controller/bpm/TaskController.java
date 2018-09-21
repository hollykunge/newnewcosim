package com.hotent.platform.controller.bpm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.bpm.model.FlowNode;
import com.hotent.core.bpm.model.NodeCache;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.bpm.util.BpmUtil;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.form.BpmFormTableDao;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeButton;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.BpmNodeSign;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.bpm.BpmRunLog;
import com.hotent.platform.model.bpm.ExecutionStack;
import com.hotent.platform.model.bpm.FormModel;
import com.hotent.platform.model.bpm.NodeTranUser;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskFork;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.bpm.TaskSignData;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.system.SysUserAgent;
import com.hotent.platform.service.bpm.BpmAgentService;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmFormRunService;
import com.hotent.platform.service.bpm.BpmNodeButtonService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.BpmNodeSignService;
import com.hotent.platform.service.bpm.BpmNodeSignService.BpmNodePrivilegeType;
import com.hotent.platform.service.bpm.BpmNodeUserService;
import com.hotent.platform.service.bpm.BpmRunLogService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.BpmTaskAssigneeService;
import com.hotent.platform.service.bpm.ExecutionStackService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskApprovalItemsService;
import com.hotent.platform.service.bpm.TaskSignDataService;
import com.hotent.platform.service.bpm.TaskUserService;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.form.BpmFormDefService;
import com.hotent.platform.service.form.BpmFormHandlerService;
import com.hotent.platform.service.system.SysUserAgentService;
import com.hotent.platform.service.system.SysUserService;

/**
 * 后台任务管理控制类
 * 
 * @author csx
 * 
 */
@Controller
@RequestMapping("/platform/bpm/task/")
public class TaskController extends BaseController {
	private Log logger = LogFactory.getLog(TaskController.class);
	@Resource
	private BpmService bpmService;
	@Resource
	private TaskService taskService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private TaskSignDataService taskSignDataService;
	@Resource
	private BpmNodeSignService bpmNodeSignService;
	@Resource
	private ExecutionStackService executionStackService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysUserAgentService sysUserAgentService;
	@Resource
	private BpmAgentService bpmAgentService;
	@Resource
	private BpmFormHandlerService bpmFormHandlerService;

	@Resource
	private BpmFormDefService bpmFormDefService;

	@Resource
	private BpmNodeUserService bpmNodeUserService;

	@Resource
	private TaskUserService taskUserService;
	@Resource
	private BpmFormRunService bpmFormRunService;

	@Resource
	private TaskApprovalItemsService taskAppItemService;
	@Resource
	private BpmFormTableDao bpmFormTableDao;

	@Resource
	private BpmNodeButtonService bpmNodeButtonService;

	@Resource
	private BpmRunLogService bpmRunLogService;

	@Resource
	private BpmTaskAssigneeService bpmTaskAssigneeService;

	/**
	 * 取得起始表单。
	 * 
	 * @param bpmNodeSet
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private FormModel getForm(BpmNodeSet bpmNodeSet, String businessKey,
			String actDefId, String ctxPath) throws Exception {
		FormModel formModel = new FormModel();
		if (bpmNodeSet == null)
			return formModel;
		if (bpmNodeSet.getFormType() == BpmConst.OnLineForm) {
			Long formKey = bpmNodeSet.getFormKey();
			if (formKey != null && formKey > 0) {
				BpmFormDef bpmFormDef = bpmFormDefService
						.getDefaultPublishedByFormKey(formKey);
				if (bpmFormDef != null) {
					BpmFormTable bpmFormTable = bpmFormTableDao.getById(bpmFormDef.getTableId());
					bpmFormDef.setTableName(bpmFormTable.getTableName());

					String formHtml = bpmFormHandlerService.doObtainHtml(bpmFormDef, ContextUtil.getCurrentUserId(),businessKey, bpmFormDef.getTableId(),"", actDefId, "", ctxPath,null);
					formModel.setFormHtml(formHtml);
				}
			}
		} else {
			String formUrl = bpmNodeSet.getFormUrl();
			// 替换主键。
			formUrl = formUrl.replaceFirst(BpmConst.FORM_PK_REGEX, businessKey);
			if (!formUrl.startsWith("http")) {
				formUrl = ctxPath + formUrl;
			}
			formModel.setFormType(BpmConst.UrlForm);
			formModel.setFormUrl(formUrl);
		}
		return formModel;
	}

	/**
	 * 跳转到启动流程页面。<br/>
	 * 
	 * <pre>
	 * 传入参数流程定义id：defId。 
	 * 实现方法： 
	 * 1.根据流程对应ID查询流程定义。 
	 * 2.获取流程定义的XML。
	 * 3.获取流程定义的第一个任务节点。
	 * 4.获取任务节点的流程表单定义。 
	 * 5.显示启动流程表单页面。
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("startFlowForm")
	@Action(description = "跳至启动流程页面")
	public ModelAndView startFlowForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long defId = RequestUtil.getLong(request, "defId");

		String businessKey = RequestUtil.getString(request, "businessKey", "");

		Map paraMap = RequestUtil.getParameterValueMap(request, false, false);
		paraMap.remove("businessKey");
		paraMap.remove("defId");

		BpmDefinition bpmDefinition = bpmDefinitionService.getById(defId);
		if (bpmDefinition.getDisableStatus()==BpmDefinition.DISABLEStATUS_DA)
			throw new Exception("该流程已经被禁用");
//		if (bpmDefinition.getDisableStatus()==BpmDefinition.DISABLEStATUS_DA)
//		{
//		ResultMessage message = new ResultMessage(ResultMessage.Fail, "该流程已经被禁用");
//		addMessage(message, request);
//		return new ModelAndView("redirect:list.ht");
//		}
		
		String actDefId = bpmDefinition.getActDefId();

		short toFirstNode = bpmDefinition.getToFirstNode();
		// 获取表单节点
		BpmNodeSet bpmNodeSet = bpmFormRunService.getStartBpmNodeSet(actDefId,
				toFirstNode);

		String ctxPath = request.getContextPath();
		FormModel formModel = getForm(bpmNodeSet, businessKey, actDefId,
				ctxPath);

		// 是外部表单
		boolean isFormEmpty = formModel.isFormEmpty();
		Boolean isExtForm = formModel.getFormType() > 0;
		String form = "";
		if (isExtForm) {
			form = formModel.getFormUrl();
		} else if (formModel.getFormType() == 0) {
			form = formModel.getFormHtml();
		}

		// 获取按钮
		Map<String, List<BpmNodeButton>> mapButton = bpmNodeButtonService
				.getMapByStartForm(defId);

		ModelAndView mv = getAutoView()
				.addObject("bpmDefinition", bpmDefinition)
				.addObject("form", form).addObject("defId", defId)
				.addObject("isExtForm", isExtForm)
				.addObject("isFormEmpty", isFormEmpty)
				.addObject("mapButton", mapButton)
				.addObject("businessKey", businessKey)
				.addObject("paraMap", paraMap);

		return mv;
	}

	/**
	 * 启动流程。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("startFlow")
	@Action(description = "启动流程")
	public void startFlow(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			ProcessCmd processCmd = BpmUtil.getProcessCmd(request);
			Long userId = ContextUtil.getCurrentUserId();
			processCmd.setCurrentUserId(userId.toString());
			processRunService.startProcess(processCmd);

			ResultMessage resultMessage = new ResultMessage(
					ResultMessage.Success, "启动流程成功!");
			out.print(resultMessage);
		} catch (Exception ex) {
			logger.debug("startFlow:" + ex.getMessage());
			ex.printStackTrace();
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(
						ResultMessage.Fail, "启动流程失败:\r\n" + str);
				out.print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				ResultMessage resultMessage = new ResultMessage(
						ResultMessage.Fail, message);
				out.print(resultMessage);
			}
		}
	}

	@RequestMapping("saveOpinion")
	@Action(description = "保存流程意见")
	public void saveOpinion(HttpServletRequest request,
			HttpServletResponse response, TaskOpinion taskOpinion)
			throws Exception {
		PrintWriter out = response.getWriter();
		taskOpinion.setOpinionId(UniqueIdUtil.genId());
		ISysUser sysUser = ContextUtil.getCurrentUser();
		ResultMessage resultMessage = null;
		try {
			TaskEntity taskEntity = bpmService.getTask(taskOpinion.getTaskId()
					.toString());
			ProcessRun processRun = processRunService
					.getByActInstanceId(taskEntity.getProcessInstanceId());

			taskOpinion.setActDefId(taskEntity.getProcessDefinitionId());
			taskOpinion.setActInstId(taskEntity.getProcessInstanceId());
			taskOpinion.setStartTime(new Date());
			taskOpinion.setExeUserId(sysUser.getUserId());
			taskOpinion.setExeFullname(sysUser.getUsername());
			taskOpinion.setTaskKey(taskEntity.getTaskDefinitionKey());
			taskOpinion.setTaskName(taskEntity.getName());
			taskOpinion.setCheckStatus(TaskOpinion.STATUS_NOTIFY);
			processRunService.saveOpinion(taskEntity.getId(), taskOpinion);

			Long runId = processRun.getRunId();

			String memo = "在:【" + processRun.getSubject() + "】,节点【"
					+ taskEntity.getName() + "】,意见:" + taskOpinion.getOpinion();
			bpmRunLogService.addRunLog(runId,
					BpmRunLog.OPERATOR_TYPE_ADDOPINION, memo);

			resultMessage = new ResultMessage(ResultMessage.Success, "添加意见成功!");
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail, "添加意见失败:"
						+ str);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
			}
		}
		out.print(resultMessage);
	}

	/**
	 * 保存表单数据。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("saveData")
	@Action(description = "保存表单数据")
	public void saveData(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String taskId = RequestUtil.getString(request, "taskId", "");
		Long defId = RequestUtil.getLong(request, "defId", 0L);
		String businessKey = RequestUtil.getString(request, "businessKey", "");

		String formData = request.getParameter("formData");
		String userId = ContextUtil.getCurrentUserId().toString();
		ResultMessage resultMessage = null;
		try {
			businessKey = processRunService.saveData(formData, userId, taskId,
					defId, businessKey);
			resultMessage = new ResultMessage(ResultMessage.Success,
					businessKey);
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,
						"保存表单数据失败:" + str);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
			}
		}
		out.print(resultMessage);
	}

	/**
	 * 显示任务回退的执行路径
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("back")
	public ModelAndView back(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");

		if (StringUtils.isEmpty(taskId))
			return getAutoView();

		TaskEntity taskEntity = bpmService.getTask(taskId);
		String taskToken = (String) taskService.getVariableLocal(
				taskEntity.getId(), TaskFork.TAKEN_VAR_NAME);
		ExecutionStack executionStack = executionStackService.getLastestStack(
				taskEntity.getProcessInstanceId(),
				taskEntity.getTaskDefinitionKey(), taskToken);
		if (executionStack != null && executionStack.getParentId() != null
				&& executionStack.getParentId() != 0) {
			ExecutionStack parentStack = executionStackService
					.getById(executionStack.getParentId());
			String assigneeNames = "";
			if (StringUtils.isNotEmpty(parentStack.getAssignees())) {
				String[] uIds = parentStack.getAssignees().split("[,]");
				int i = 0;
				for (String uId : uIds) {
					ISysUser sysUser = sysUserService.getById(new Long(uId));
					if (sysUser == null)
						continue;
					if (i++ > 0) {
						assigneeNames += ",";
					}
					assigneeNames += sysUser.getFullname();
				}
			}
			request.setAttribute("assigneeNames", assigneeNames);
			request.setAttribute("parentStack", parentStack);
		}

		request.setAttribute("taskId", taskId);

		return getAutoView();
	}

	/**
	 * 任务回退
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("jumpBack")
	public ModelAndView jumpBack(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProcessCmd processCmd = BpmUtil.getProcessCmd(request);
		processCmd.setCurrentUserId(ContextUtil.getCurrentUserId().toString());
		processRunService.nextProcess(processCmd);
		return new ModelAndView("redirect:list.ht");
	}

	/**
	 * 跳至会签页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toSign")
	public ModelAndView toSign(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		ModelAndView modelView = getAutoView();

		if (StringUtils.isNotEmpty(taskId)) {
			TaskEntity taskEntity = bpmService.getTask(taskId);
			String nodeId = bpmService
					.getExecution(taskEntity.getExecutionId()).getActivityId();
			String actInstId = taskEntity.getProcessInstanceId();

			List<TaskSignData> signDataList = taskSignDataService
					.getByNodeAndInstanceId(actInstId, nodeId);

			// 获取会签规则
			BpmNodeSign bpmNodeSign = bpmNodeSignService.getByDefIdAndNodeId(
					taskEntity.getProcessDefinitionId(), nodeId);

			modelView.addObject("signDataList", signDataList);
			modelView.addObject("task", taskEntity);
			modelView.addObject("curUser", ContextUtil.getCurrentUser());
			modelView.addObject("bpmNodeSign", bpmNodeSign);
		}

		return modelView;
	}

	/**
	 * 补签
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addSign")
	@ResponseBody
	public String addSign(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		String signUserIds = request.getParameter("signUserIds");
		if (StringUtils.isNotEmpty(taskId)
				&& StringUtils.isNotEmpty(signUserIds)) {
			taskSignDataService.addSign(signUserIds, taskId);
		}
		return SUCCESS;
	}

	/**
	 * 任务自由跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("jump")
	@ResponseBody
	public String jump(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String taskId = request.getParameter("taskId");
		String destTask = request.getParameter("destTask");
		bpmService.transTo(taskId, destTask);

		return SUCCESS;
	}

	/**
	 * 跳至会签页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveSign")
	@ResponseBody
	public String saveSign(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		String isAgree = request.getParameter("isAgree");
		String content = request.getParameter("content");

		taskSignDataService.signVoteTask(taskId, content, new Short(isAgree));

		return SUCCESS;
	}

	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "taskItem");
		List<TaskEntity> list = bpmService.getTasks(filter);
		request.getSession().setAttribute("isAdmin", true);
		ModelAndView mv = getAutoView().addObject("taskList", list);
		
		return mv;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("forMe")
	public ModelAndView forMe(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "taskItem");
		List<?> list = bpmService.getMyTasks(filter);
		Map<String, String> candidateMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		if (BeanUtils.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				ProcessTask task = (ProcessTask) list.get(i);
				if (i == 0) {
					sb.append(task.getId());
				} else {
					sb.append("," + task.getId());
				}
			}
			List<Map> userMapList = bpmService.getHasCandidateExecutor(sb
					.toString());
			for (Iterator<Map> it = userMapList.iterator(); it.hasNext();) {
				Map map = it.next();
				candidateMap.put(map.get("TASKID").toString(), "1");
			}
		}
		ModelAndView mv = getAutoView().addObject("taskList", list).addObject(
				"candidateMap", candidateMap);
		return mv;
	}

	/**
	 * 待办事项 flex 返回格式eg: [ { "id":"10000005210157", // 项id "type":"1", //
	 * 类型，如任务、消息 "startTime":"12/07/2012 00:00:00 AM", // 开始时间
	 * "endTime":"12/08/2012 00:00:00 AM", // 结束时间
	 * "title":"测试流程变量-admin-2012-10-17 11:55:07", // 标题 } ]
	 * 
	 * @throws Exception
	 */
	@RequestMapping("myEvent")
	public void myEvent(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("mode", RequestUtil.getString(request, "mode"));
		param.put("startDate", RequestUtil.getString(request, "startDate"));
		param.put("endDate", RequestUtil.getString(request, "endDate"));
		response.getWriter().print(bpmService.getMyEvents(param));
	}

	@RequestMapping("detail")
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long taskId = RequestUtil.getLong(request, "taskId");
		Task task = taskService.createTaskQuery().taskId(taskId.toString())
				.singleResult();
		String returnUrl = RequestUtil.getPrePage(request);
		// get the process run instance from task
		ProcessRun processRun = processRunService.getByActInstanceId(task
				.getProcessInstanceId());

		BpmDefinition processDefinition = bpmDefinitionService
				.getByActDefId(processRun.getActDefId());
		ModelAndView modelView = getAutoView();
		modelView.addObject("taskEntity", task)
				.addObject("processRun", processRun)
				.addObject("processDefinition", processDefinition)
				.addObject("returnUrl", returnUrl);
		if (StringUtils.isNotEmpty(processRun.getBusinessUrl())) {
			String businessUrl = StringUtil.formatParamMsg(
					processRun.getBusinessUrl(), processRun.getBusinessKey())
					.toString();
			modelView.addObject("businessUrl", businessUrl);
		}
		return modelView;
	}

	/**
	 * 启动任务界面。 根据任务ID获取流程实例，根据流程实例获取表单数据。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toStart")
	public ModelAndView toStart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ctxPath = request.getContextPath();
		String taskId = RequestUtil.getString(request, "taskId");
		// 查找任务节点
		TaskEntity taskEntity = bpmService.getTask(taskId);
		
		if (taskEntity == null) {
			return new ModelAndView("redirect:notExist.ht");
		}

		boolean isClaim = true;
		String assignee = taskEntity.getAssignee();
		Boolean isAdmin = (Boolean) request.getSession()
				.getAttribute("isAdmin");
		if ((StringUtil.isEmpty(assignee)) && isAdmin == null) {
			isClaim = false;
		}

		String instanceId = taskEntity.getProcessInstanceId();
		String nodeId = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		Long userId = ContextUtil.getCurrentUserId();

		BpmDefinition bpmDefinition = bpmDefinitionService
				.getByActDefId(actDefId);
		ProcessRun processRun = processRunService
				.getByActInstanceId(instanceId);

		Long defId = bpmDefinition.getDefId();

		/**
		 * 使用API调用的时候获取表单的url进行跳转。
		 */
		if (bpmDefinition.getIsUseOutForm() == 1) {
			String formUrl = bpmFormDefService.getFormUrl(taskId, defId,
					nodeId, processRun.getBusinessKey(), ctxPath);
			response.sendRedirect(formUrl);
		}

		BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(actDefId,
				nodeId);

		String toBackNodeId = bpmDefinition.getStartFirstNode();
		String form = "";

		Map<String, Object> variables = taskService.getVariables(taskId);
		
		FormModel formModel = bpmFormDefService.doGetForm(processRun, nodeId,
				userId, ctxPath, variables);
		String detailUrl = formModel.getDetailUrl();

		Boolean isExtForm = (Boolean) (formModel.getFormType() > 0);

		if (formModel.getFormType() == 0)
			form = formModel.getFormHtml();
		else
			form = formModel.getFormUrl();

		Boolean isEmptyForm = formModel.isFormEmpty();

		ModelAndView mv = this.getAutoView();
		// 是否会签任务
		boolean isSignTask = bpmService.isSignTask(taskEntity);
		if (isSignTask)
			handleSignTask(mv, instanceId, nodeId, actDefId, userId);
		// 是否回退
		boolean isCanBack = !isSignTask ? getIsAllowBackByTask(taskEntity): false;
		// 是否执行隐藏路径
		boolean isHidePath = getIsHidePath(bpmNodeSet.getIsHidePath());

		// 获取页面显示的按钮
		Map<String, List<BpmNodeButton>> mapButton = bpmNodeButtonService
				.getMapByDefNodeId(defId, nodeId);
		
		// 取常用语
		List<String> taskAppItems = taskAppItemService.getApprovalByActDefId(
				taskEntity.getProcessDefinitionId(),
				taskEntity.getTaskDefinitionKey());
		
		return mv.addObject("task", taskEntity)
				.addObject("bpmNodeSet", bpmNodeSet)
				.addObject("processRun", processRun)
				.addObject("bpmDefinition", bpmDefinition)
				.addObject("isSignTask", isSignTask)
				.addObject("isCanBack", isCanBack)
				.addObject("isHidePath", isHidePath)
				.addObject("toBackNodeId", toBackNodeId)
				.addObject("form", form).addObject("isExtForm", isExtForm)
				.addObject("isEmptyForm", isEmptyForm)
				.addObject("taskAppItems", taskAppItems)
				.addObject("mapButton", mapButton)
				.addObject("isClaim", isClaim)
				.addObject("detailUrl", detailUrl);
	}

	/**
	 * 是否执行隐藏路径
	 * 
	 * @param isHidePath
	 * @return
	 */
	private boolean getIsHidePath(Short isHidePath) {
		if (BeanUtils.isEmpty(isHidePath))
			return false;
		if (BpmNodeSet.HIDE_PATH.shortValue() == isHidePath.shortValue())
			return true;
		return false;
	}

	/**
	 * 处理会签
	 * 
	 * @param mv
	 * @param instanceId
	 * @param nodeId
	 * @param actDefId
	 * @param userId
	 *            当前用户
	 */
	private void handleSignTask(ModelAndView mv, String instanceId,
			String nodeId, String actDefId, Long userId) {

		List<TaskSignData> signDataList = taskSignDataService
				.getByNodeAndInstanceId(instanceId, nodeId);
		// 获取会签规则
		BpmNodeSign bpmNodeSign = bpmNodeSignService.getByDefIdAndNodeId(
				actDefId, nodeId);

		mv.addObject("signDataList", signDataList);
		mv.addObject("bpmNodeSign", bpmNodeSign);
		mv.addObject("curUser", ContextUtil.getCurrentUser());
		// 获取当前组织
		Long orgId = ContextUtil.getCurrentOrgId();

		// "允许直接处理"特权
		boolean isAllowDirectExecute = bpmNodeSignService
				.checkNodeSignPrivilege(actDefId, nodeId,
						BpmNodePrivilegeType.ALLOW_DIRECT, userId, orgId);
		// "允许补签"特权
		boolean isAllowRetoactive = bpmNodeSignService.checkNodeSignPrivilege(
				actDefId, nodeId, BpmNodePrivilegeType.ALLOW_RETROACTIVE,
				userId, orgId);
		// "一票决断"特权
		boolean isAllowOneVote = bpmNodeSignService.checkNodeSignPrivilege(
				actDefId, nodeId, BpmNodePrivilegeType.ALLOW_ONE_VOTE, userId,
				orgId);
		mv.addObject("isAllowDirectExecute", isAllowDirectExecute)
				.addObject("isAllowRetoactive", isAllowRetoactive)
				.addObject("isAllowOneVote", isAllowOneVote);

	}

	/**
	 * 从再次节点设置取得表单设置，并更新到运行表单设置中
	 * 
	 * @param processRun
	 */
	// private boolean updateFormRunFormNodeSet(ProcessRun processRun,String
	// nodeId){
	// String actInstId=processRun.getActInstId();
	// String actDefId=processRun.getActDefId();
	// Long procDefId=processRun.getDefId();
	// Long runId=processRun.getRunId();
	// //取bpmFormRun
	// BpmFormRun bpmFormRun=bpmFormRunService.getByInstanceAndNodeId(actInstId,
	// nodeId);
	// boolean isUpdate=true;
	// if(bpmFormRun==null){
	// bpmFormRun=new BpmFormRun();
	// bpmFormRun.setId(UniqueIdUtil.genId());
	// bpmFormRun.setActDefId(actDefId);
	// bpmFormRun.setActInstanceId(actInstId);
	// bpmFormRun.setActNodeId(nodeId);
	// bpmFormRun.setRunId(runId);
	// isUpdate=false;
	// }
	//
	// //第一步，取节点设置的表单,如果再获取不到则获取全局表单。
	// BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(actDefId,
	// nodeId);
	// if(isFormEmpty(bpmNodeSet)){
	// BpmNodeSet
	// globalForm=bpmNodeSetService.getBySetType(procDefId,BpmNodeSet.SetType_GloabalForm);
	// if(isFormEmpty(globalForm)){
	// return false;
	// }
	// else{
	// bpmFormRun.setFormdefKey(globalForm.getFormKey());
	// bpmFormRun.setFormType(globalForm.getFormType());
	// bpmFormRun.setFormUrl(globalForm.getFormUrl());
	// }
	// }
	// else{
	// bpmFormRun.setFormdefKey(bpmNodeSet.getFormKey());
	// bpmFormRun.setFormType(bpmNodeSet.getFormType());
	// bpmFormRun.setFormUrl(bpmNodeSet.getFormUrl());
	// }
	// //在线表单的情况。
	// if(bpmFormRun.getFormType()==0 ){
	// //获取默认的表单。
	// BpmFormDef bpmFormDef =
	// bpmFormDefService.getDefaultVersionByFormKey(bpmFormRun.getFormdefKey());
	// bpmFormRun.setFormdefId(bpmFormDef.getFormDefId());
	// }
	//
	// if(isUpdate){
	// bpmFormRunService.update(bpmFormRun);
	// }
	// else{
	// bpmFormRunService.add(bpmFormRun);
	// }
	//
	// return true;
	// }

	@RequestMapping("getForm")
	public ModelAndView getForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ctxPath = request.getContextPath();
		String taskId = RequestUtil.getString(request, "taskId");
		String returnUrl = RequestUtil.getPrePage(request);
		// 查找任务节点
		TaskEntity taskEntity = bpmService.getTask(taskId);
		String action = RequestUtil.getString(request, "action", "");
		if (taskEntity == null) {
			return new ModelAndView("redirect:notExist.ht");
		}
		String instanceId = taskEntity.getProcessInstanceId();
		String taskName = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		Long userId = ContextUtil.getCurrentUserId();

		BpmDefinition bpmDefinition = bpmDefinitionService
				.getByActDefId(actDefId);

		ProcessRun processRun = processRunService
				.getByActInstanceId(instanceId);

		String form = "";
		Boolean isExtForm = false;
		Boolean isEmptyForm = false;

		Map<String, Object> variables = taskService.getVariables(taskId);

		if (bpmDefinition != null) {
			FormModel formModel = bpmFormDefService.doGetForm(processRun,
					taskName, userId, ctxPath, variables);

			isExtForm = formModel.getFormType() > 0;
			if (formModel.getFormType() == 0) {
				form = formModel.getFormHtml();
			} else if (formModel.getFormType() == 1) {
				form = formModel.getFormUrl();
			}

			isEmptyForm = formModel.isFormEmpty();
		}

		return getAutoView().addObject("task", taskEntity)
				.addObject("form", form)
				.addObject("bpmDefinition", bpmDefinition)
				.addObject("isExtForm", isExtForm)
				.addObject("isEmptyForm", isEmptyForm)
				.addObject("action", action)
				.addObject("processRun", processRun)
				.addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得任务是否可以回退。
	 * 
	 * @param taskEntity
	 * @return
	 */
	private boolean getIsAllowBackByTask(TaskEntity taskEntity) {
		boolean isAllowBack = false;
		String taskToken = (String) taskService.getVariableLocal(
				taskEntity.getId(), TaskFork.TAKEN_VAR_NAME);
		// 设置了允许回退处理
		ExecutionStack executionStack = executionStackService.getLastestStack(
				taskEntity.getProcessInstanceId(),
				taskEntity.getTaskDefinitionKey(), taskToken);
		if (executionStack == null)
			return isAllowBack;
		if (BeanUtils.isEmpty(executionStack.getParentId()))
			return isAllowBack;
		Map<String, FlowNode> map = NodeCache.getByActDefId(executionStack
				.getActDefId());
		FlowNode flowNode = map.get(executionStack.getNodeId());
		List<FlowNode> preFlowNodeList = flowNode.getPreFlowNodes();
		if (!getAllowBack(preFlowNodeList, false))
			return isAllowBack;

		ExecutionStack parentStack = executionStackService
				.getById(executionStack.getParentId());
		if (parentStack != null) {
			// 判断上一个节点是否是多实例
			if (parentStack.getIsMultiTask().shortValue() == ExecutionStack.MULTI_TASK
					.shortValue())
				return isAllowBack;
			isAllowBack = true;
		}
		return isAllowBack;
	}

	/**
	 * 获取允许回退的判断
	 * 判断上一个节点是否是网关节点；自动任务节点 比如脚本、消息、webService等节点；是否是内部子流程；是否是外部子流程
	 * 
	 * @param preFlowNodeList
	 *            上一个节点的List
	 * @param isSubProcess
	 *            是否是子流程
	 * @return
	 */
	private boolean getAllowBack(List<FlowNode> preFlowNodeList,
			boolean isSubProcess) {
		if (BeanUtils.isEmpty(BeanUtils.isNotEmpty(preFlowNodeList)))
			return false;
		boolean isAllowBack = false;
		// 如果上一个节点是多个节点则返回
		FlowNode preFlowNode = new FlowNode();
		if (BeanUtils.isNotEmpty(preFlowNodeList) && preFlowNodeList.size() > 1) {
			if (!isSubProcess)
				return isAllowBack;
			preFlowNode = preFlowNodeList.get(preFlowNodeList.size() - 1);
		} else {
			preFlowNode = preFlowNodeList.get(0);
		}
		// 判断上一个节点是否是网关节点；自动任务节点 比如脚本、消息、webService等节点；是否是内部子流程；是否是外部子流程
		if (preFlowNode.getNodeType().indexOf("Gateway") != -1
				|| preFlowNode.getNodeType().equals("serviceTask")
				|| preFlowNode.getNodeType().equals("subProcess")
				|| preFlowNode.getNodeType().equals("callActivity")) {
			if (preFlowNode.getNodeType().equals("subProcess")) {// 如果是内部子流程，还进行特殊处理
				isAllowBack = getAllowBack(preFlowNode.getSubFlowNodes(), true);
			}else if(preFlowNode.getNodeType().equals("exclusiveGateway")){
				isAllowBack = true;
			}
		} else {
			isAllowBack = true;
		}
		return isAllowBack;
	}

	/**
	 * 完成任务
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("complete")
	public void complete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("任务完成跳转....");
		PrintWriter out = response.getWriter();
		ResultMessage resultMessage = null;
		String taskId = RequestUtil.getString(request, "taskId");
		TaskEntity task = bpmService.getTask(taskId);
		Long userId = ContextUtil.getCurrentUserId();
		Boolean isAdmin = (Boolean) request.getSession()
				.getAttribute("isAdmin");
		String assignee = task.getAssignee();
		ProcessCmd taskCmd = BpmUtil.getProcessCmd(request);
		if (assignee != null && !task.getAssignee().equals(userId.toString())
				&& isAdmin == null && !taskCmd.isAgentTask()) {

			resultMessage = new ResultMessage(ResultMessage.Fail, "该任务已被其他人锁定");
		} else {
			try {
				processRunService.nextProcess(taskCmd);
				resultMessage = new ResultMessage(ResultMessage.Success,"任务成功完成!");
			} catch (Exception ex) {
				String str = MessageUtil.getMessage();
				if (StringUtil.isNotEmpty(str)) {
					resultMessage = new ResultMessage(ResultMessage.Fail, str);
				} else {
					String message = ExceptionUtil.getExceptionMessage(ex);
					resultMessage = new ResultMessage(ResultMessage.Fail,
							message);
				}
			}
		}
		out.print(resultMessage);
	}

	/**
	 * 锁定任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("claim")
	@Action(description = "锁定任务")
	public void claim(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		int isAgent = RequestUtil.getInt(request, "isAgent");
		String preUrl = RequestUtil.getPrePage(request);
		String assignee = ContextUtil.getCurrentUserId().toString();
		// 代理任务，则设置锁定的assignee为代理人
		if (isAgent == 1) {
			// List<TaskUser> candidates = bpmService.getCandidateUsers(taskId);
			/////ht:raise add b
			/****del
			Set<ISysUser> candidates = taskUserService.getCandidateUsers(taskId);
			******/
			Set<ISysUser> candidates = taskUserService.getCandidateUsers(taskId,true);
			////ht:raise add e
			List<Long> agents = bpmService.getAgentIdByTaskId(taskId, assignee);
			for (Iterator<ISysUser> it = candidates.iterator(); it.hasNext();) {
				Long userId = it.next().getUserId();
				if (agents.contains(userId)) {
					assignee = userId.toString();
					break;
				}
			}
		}
		try {
			TaskEntity taskEntity = bpmService.getTask(taskId);
			ProcessRun processRun = processRunService
					.getByActInstanceId(taskEntity.getProcessInstanceId());
			Long runId = processRun.getRunId();
			String assignee1 = taskEntity.getAssignee();
			taskService.claim(taskId, assignee);
			String memo = "流程:" + processRun.getSubject() + ",锁定任务，节点【"
					+ taskEntity.getName() + "】";
			bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_LOCK,
					memo);
			saveSuccessResultMessage(request.getSession(), "成功锁定任务!");
		} catch (Exception ex) {
			ex.printStackTrace();
			saveSuccessResultMessage(request.getSession(), "任务已经完成或被其他用户锁定!");
		}
		response.sendRedirect(preUrl);
	}

	/**
	 * 解锁任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("unlock")
	@Action(description = "解锁任务")
	public ModelAndView unlock(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");

		if (StringUtils.isNotEmpty(taskId)) {
			TaskEntity taskEntity = bpmService.getTask(taskId);
			ProcessRun processRun = processRunService
					.getByActInstanceId(taskEntity.getProcessInstanceId());
			Long runId = processRun.getRunId();
			bpmService.updateTaskAssigneeNull(taskId);
			String memo = "流程:" + processRun.getSubject() + ",解锁任务，节点【"
					+ taskEntity.getName() + "】";
			bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_UNLOCK,
					memo);
			saveSuccessResultMessage(request.getSession(), "任务已经成功解锁!");
		}
		return new ModelAndView("redirect:forMe.ht");
	}

	/**
	 * 任务跳转窗口显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("freeJump")
	public ModelAndView freeJump(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		Map<String, Map<String, String>> jumpNodesMap = bpmService
				.getJumpNodes(taskId);
		ModelAndView view = this.getAutoView();
		view.addObject("jumpNodeMap", jumpNodesMap);
		return view;
	}

	/**
	 * 获取某个流程实例上某个节点的配置执行人员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTaskUsers")
	@ResponseBody
	public String getTaskUsers(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 目标节点Id
		String nodeId = request.getParameter("nodeId");
		// 任务Id
		String taskId = request.getParameter("taskId");

		TaskEntity taskEntity = bpmService.getTask(taskId);
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("executionId", taskEntity.getExecutionId());
		List<TaskExecutor> taskExecutorList = bpmNodeUserService
				.getExeUserIdsByInstance(taskEntity.getProcessInstanceId(),
						nodeId, "", vars).get(
						BpmNodeUser.USER_TYPE_PARTICIPATION);
		StringBuffer sb = new StringBuffer("[");

		for (int i = 0; i < taskExecutorList.size(); i++) {
			TaskExecutor taskExecutor = taskExecutorList.get(i);
			if (i > 0) {
				sb.append(",");
			}
			sb.append("{userId:").append(taskExecutor.getExecuteId())
					.append(",fullname:'").append(taskExecutor.getExecutor())
					.append("'}");

		}

		sb.append("]");
		return sb.toString();
	}

	/**
	 * 指派任务所属人
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Action(description = "任务指派所属人")
	@RequestMapping("assign")
	public ModelAndView assign(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskIds = request.getParameter("taskIds");
		String userId = request.getParameter("userId");

		if (StringUtils.isNotEmpty(taskIds)) {
			String[] tIds = taskIds.split("[,]");
			if (tIds != null) {
				for (String tId : tIds) {
					bpmService.assignTask(tId, userId);
				}
			}
		}
		saveSuccessResultMessage(request.getSession(), "成功为指定任务任务分配执行人员!");
		return new ModelAndView("redirect:list.ht");
	}

	/**
	 * 任务交办设置任务的执行人。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delegate")
	@Action(description = "任务交办")
	public void delegate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		String taskId = request.getParameter("taskId");
		String userId = request.getParameter("userId");
		String delegateDesc = request.getParameter("memo");
		ResultMessage message = null;
		if (StringUtils.isNotEmpty(taskId) && StringUtil.isNotEmpty(userId)) {
			processRunService.delegate(taskId, userId, delegateDesc);
			message = new ResultMessage(ResultMessage.Success, "任务交办成功!");
		} else {
			message = new ResultMessage(ResultMessage.Fail, "没有传入必要的参数");
		}
		writer.print(message);
	}

	@RequestMapping("setAssignee")
	@Action(description = "任务指派")
	public void setAssignee(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		String taskId = request.getParameter("taskId");
		String userId = request.getParameter("userId");
		ResultMessage message = null;
		if (StringUtils.isNotEmpty(taskId) && StringUtil.isNotEmpty(userId)) {
			bpmService.updateTaskAssignee(taskId, userId);

			// 记录日志。
			TaskEntity taskEntity = bpmService.getTask(taskId);
			ProcessRun processRun = processRunService
					.getByActInstanceId(taskEntity.getProcessInstanceId());
			Long runId = processRun.getRunId();

			String originUserId = taskEntity.getAssignee();
			String originUserName = "";
			if (StringUtil.isNotEmpty(originUserId)) {
				ISysUser originUser = sysUserService.getById(Long
						.parseLong(originUserId));
				originUserName = originUser.getFullname();

			}

			ISysUser user = sysUserService.getById(Long.parseLong(userId));
			ISysUser curUser = ContextUtil.getCurrentUser();

			String memo = "流程:" + processRun.getSubject() + ", 【"
					+ curUser.getFullname() + "】将任务【" + taskEntity.getName()
					+ "】";
			if (StringUtil.isNotEmpty(originUserName)) {
				memo += ",原执行人为:【" + originUserName + "】,";
			}
			memo += "现指派给【" + user.getFullname() + "】执行。";
			bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_ASSIGN,
					memo);

			memo = "流程:" + processRun.getSubject() + ", 【"
					+ curUser.getFullname() + "】将任务【" + taskEntity.getName()
					+ "】 ";
			if (StringUtil.isNotEmpty(originUserName)) {
				memo += ",原执行人为:【" + originUserName + "】,";
			}
			;
			memo += "指派给【" + user.getFullname() + "】执行。";
			bpmRunLogService.addRunLog(user, runId,
					BpmRunLog.OPERATOR_TYPE_ASSIGN, memo);

			message = new ResultMessage(ResultMessage.Success, "任务指派成功!");

		} else {
			message = new ResultMessage(ResultMessage.Fail, "没有传入必要的参数");
		}
		writer.print(message);
	}

	/**
	 * 设置任务的所有人。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setOwner")
	@Action(description = "任务指派所属人")
	public void setOwner(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		String taskId = request.getParameter("taskId");
		String userId = request.getParameter("userId");
		ResultMessage message = null;
		if (StringUtils.isNotEmpty(taskId) && StringUtil.isNotEmpty(userId)) {
			bpmService.updateTaskOwner(taskId, userId);

			TaskEntity taskEntity = bpmService.getTask(taskId);
			ProcessRun processRun = processRunService
					.getByActInstanceId(taskEntity.getProcessInstanceId());
			Long runId = processRun.getRunId();

			ISysUser user = sysUserService.getById(Long.parseLong(userId));
			ISysUser curUser = ContextUtil.getCurrentUser();

			String memo = "流程:" + processRun.getSubject() + ", 【"
					+ curUser.getUsername() + "】设定任务【" + taskEntity.getName()
					+ "】的所有人为【" + user.getFullname() + "】。";
			bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_SETOWNER,
					memo);

			memo = "流程:" + processRun.getSubject() + ", 【"
					+ curUser.getUsername() + "】将任务【" + taskEntity.getName()
					+ "】的所有人为【" + user.getFullname() + "】。";
			bpmRunLogService.addRunLog(user, runId,
					BpmRunLog.OPERATOR_TYPE_SETOWNER, memo);

			message = new ResultMessage(ResultMessage.Success, "指定所属人成功!");

		} else {
			message = new ResultMessage(ResultMessage.Fail, "没有传入必要的参数");
		}
		writer.print(message);
	}

	/**
	 * 设置任务的执行人
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setDueDate")
	@Action(description = "设置任务到期时间")
	public ModelAndView setDueDate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskIds = request.getParameter("taskIds");
		String dueDates = request.getParameter("dueDates");
		if (StringUtils.isNotEmpty(taskIds) && StringUtils.isNotEmpty(dueDates)) {
			String[] tIds = taskIds.split("[,]");
			String[] dates = dueDates.split("[,]");
			if (tIds != null) {
				for (int i = 0; i < dates.length; i++) {
					if (StringUtils.isNotEmpty(dates[i])) {
						Date dueDate = DateUtils.parseDate(dates[i],
								new String[] { "yyyy-MM-dd HH:mm:ss" });
						bpmService.setDueDate(tIds[i], dueDate);
					}
				}
			}
		}
		return new ModelAndView("redirect:list.ht");
	}

	/**
	 * 删除任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delete")
	@Action(description = "删除任务")
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String taskId = request.getParameter("taskId");
			String[] taskIds = request.getParameterValues("id");
			if (StringUtils.isNotEmpty(taskId)) {
				bpmService.deleteTask(taskId);

				TaskEntity task = bpmService.getTask(taskId);
				ProcessRun processRun = processRunService
						.getByActInstanceId(task.getProcessInstanceId());
				String memo = "用户删除了任务[" + task.getName() + "],该任务属于["
						+ processRun.getProcessName() + "]流程。";
				bpmRunLogService.addRunLog(processRun.getRunId(),
						BpmRunLog.OPERATOR_TYPE_DELETETASK, memo);
				taskService.deleteTask(taskId);

			} else if (taskIds != null && taskIds.length != 0) {
				bpmService.deleteTasks(taskIds);
				for (int i = 0; i < taskIds.length; i++) {
					String id = taskIds[i];
					TaskEntity task = bpmService.getTask(id);
					ProcessRun processRun = processRunService
							.getByActInstanceId(task.getProcessInstanceId());
					String memo = "用户删除了任务[" + task.getName() + "],该任务属于["
							+ processRun.getProcessName() + "]流程。";
					bpmRunLogService.addRunLog(processRun.getRunId(),
							BpmRunLog.OPERATOR_TYPE_DELETETASK, memo);
					taskService.deleteTask(id);
				}
			}
			message = new ResultMessage(ResultMessage.Success, "删除任务成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.Fail, "删除任务失败");
		}
		addMessage(message, request);
		return new ModelAndView("redirect:list.ht");
	}

	/**
	 * 返回某个某个用户代理给当前用户的任务列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("forAgent")
	public ModelAndView forAgent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		Long userId = RequestUtil.getLong(request, "userId");
		QueryFilter filter = new QueryFilter(request, "taskItem");
		Calendar cal = Calendar.getInstance();
		Date curTime = cal.getTime();
		cal.add(Calendar.DATE, -1);
		Date yesterday = cal.getTime();

		filter.addFilter("curTime", curTime);
		filter.addFilter("yesterday", yesterday);
		List<TaskEntity> list = null;
		SysUserAgent sysUserAgent = null;
		// 具体人员的代理
		if (userId != 0) {
			sysUserAgent = sysUserAgentService.getById(userId);
		}
		if (sysUserAgent != null) {

			// 代理设置是否过期
			if (sysUserAgent.getStarttime() != null) {
				int result = sysUserAgent.getStarttime().compareTo(curTime);
				if (result > 0) {
					list = new ArrayList<TaskEntity>();
					mv = getAutoView().addObject("taskList", list).addObject(
							"userId", userId);
					return mv;
				}
			}
			if (sysUserAgent.getEndtime() != null) {
				cal.add(Calendar.DATE, -1);
				int result = sysUserAgent.getEndtime().compareTo(yesterday);
				if (result <= 0) {
					list = new ArrayList<TaskEntity>();
					mv = getAutoView().addObject("taskList", list).addObject(
							"userId", userId);
					return mv;
				}
			}
			if (sysUserAgent.getIsall().intValue() == SysUserAgent.IS_ALL_FLAG) {// 全部代理
				list = bpmService.getTaskByUserId(
						sysUserAgent.getAgentuserid(), filter);
			} else {// 部分代理
				StringBuffer actDefId = new StringBuffer("");
				List<String> notInBpmAgentlist = bpmAgentService
						.getNotInByAgentId(sysUserAgent.getAgentid());
				for (String ba : notInBpmAgentlist) {
					actDefId.append("'").append(ba).append("',");
				}
				if (notInBpmAgentlist.size() > 0) {
					actDefId.deleteCharAt(actDefId.length() - 1);
				}
				list = bpmService.getAgentTasks(sysUserAgent.getAgentuserid(),
						actDefId.toString(), filter);
			}
		} else {
			list = bpmService.getAllAgentTask(ContextUtil.getCurrentUserId(),
					filter);
		}
		mv = getAutoView().addObject("taskList", list).addObject("userId",
				userId);

		return mv;
	}

	/**
	 * 返回目标节点及其节点的处理人员映射列表。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("tranTaskUserMap")
	public ModelAndView tranTaskUserMap(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int isStart = RequestUtil.getInt(request, "isStart", 0);
		String taskId = request.getParameter("taskId");
		String actDefId = request.getParameter("actDefId");
		int selectPath = RequestUtil.getInt(request, "selectPath", 1);
		boolean canChoicePath = bpmService.getCanChoicePath(actDefId, taskId);

		Long startUserId = ContextUtil.getCurrentUserId();
		List<NodeTranUser> nodeTranUserList = null;
		if (isStart == 1) {
			Map<String, Object> vars = new HashMap<String, Object>();
			nodeTranUserList = bpmService.getStartNodeUserMap(actDefId,
					startUserId, vars);
		} else {
			nodeTranUserList = bpmService.getNodeTaskUserMap(taskId,
					startUserId, canChoicePath);
		}

		return getAutoView().addObject("nodeTranUserList", nodeTranUserList)
				.addObject("selectPath", selectPath)
				.addObject("canChoicePath", canChoicePath);
	}

	/**
	 * 结合前台任务管理列表，点击某行任务时，显示的任务简单明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("miniDetail")
	public ModelAndView miniDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");

		TaskEntity taskEntity = bpmService.getTask(taskId);

		if (taskEntity == null) {
			return new ModelAndView("/platform/bpm/taskNotExist.jsp");
		}

		// 取到任务的侯选人员
		Set<TaskExecutor> candidateUsers = taskUserService
				.getCandidateExecutors(taskId);

		ProcessRun processRun = processRunService.getByActInstanceId(taskEntity
				.getProcessInstanceId());

		BpmDefinition definition = bpmDefinitionService
				.getByActDefId(taskEntity.getProcessDefinitionId());

		List<ProcessTask> curTaskList = bpmService.getTasks(taskEntity
				.getProcessInstanceId());

		return getAutoView().addObject("taskEntity", taskEntity)
				.addObject("processRun", processRun)
				.addObject("candidateUsers", candidateUsers)
				.addObject("processDefinition", definition)
				.addObject("curTaskList", curTaskList);
	}

	/**
	 * 准备更新任务的路径
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("changePath")
	public ModelAndView changePath(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		TaskEntity taskEntity = bpmService.getTask(taskId);
		Map<String, String> taskNodeMap = bpmService.getTaskNodes(
				taskEntity.getProcessDefinitionId(),
				taskEntity.getTaskDefinitionKey());
		return this.getAutoView().addObject("taskEntity", taskEntity)
				.addObject("taskNodeMap", taskNodeMap)
				.addObject("curUser", ContextUtil.getCurrentUser());
	}

	/**
	 * 保存变更路径
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveChangePath")
	@ResponseBody
	public String saveChangePath(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProcessCmd processCmd = BpmUtil.getProcessCmd(request);
		processRunService.nextProcess(processCmd);
		saveSuccessResultMessage(request.getSession(), "更改任务执行的路径!");
		return SUCCESS;
	}

	/**
	 * 结束流程任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("end")
	public void end(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage resultMessage = null;
		try {
			String taskId = request.getParameter("taskId");
			TaskEntity taskEntity = bpmService.getTask(taskId);
			ProcessRun processRun = processRunService
					.getByActInstanceId(taskEntity.getProcessInstanceId());
			String voteContent = "由"
					+ ContextUtil.getCurrentUser().getFullname() + "进行完成操作！";
			ProcessCmd cmd = new ProcessCmd();
			cmd.setTaskId(taskId);
			cmd.setVoteAgree((short) 0);
			cmd.setVoteContent(voteContent);
			cmd.setOnlyCompleteTask(true);
			processRunService.nextProcess(cmd);
			Long runId = processRun.getRunId();
			String memo = "结束了:" + processRun.getSubject();
			bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_ENDTASK,
					memo);
			resultMessage = new ResultMessage(ResultMessage.Success,
					"成功完成了该任务!");
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail, "完成任务失败:"
						+ str);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
			}
		}
		response.getWriter().print(resultMessage);
	}

	/**
	 * 根据任务结束流程实例。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("endProcess")
	public void endProcess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String prePage = RequestUtil.getPrePage(request);
		Long taskId = RequestUtil.getLong(request, "taskId");

		TaskEntity taskEnt = bpmService.getTask(taskId.toString());

		String instanceId = taskEnt.getProcessInstanceId();
		ResultMessage message = null;
		try {
			ProcessRun processRun = bpmService.endProcessByInstanceId(new Long(
					instanceId));
			String memo = "结束了:" + processRun.getSubject();
			bpmRunLogService.addRunLog(processRun.getRunId(),
					BpmRunLog.OPERATOR_TYPE_ENDTASK, memo);
			message = new ResultMessage(ResultMessage.Success, "结束流程实例成功!");
		} catch (Exception ex) {
			ex.printStackTrace();
			message = new ResultMessage(ResultMessage.Success, "结束流程实例失败!");
		}
		addMessage(message, request);

		response.sendRedirect(prePage);
	}

}
