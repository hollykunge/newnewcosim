package com.hotent.platform.controller.bpm;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.casic.cloud.model.tool.Tool;
import com.casic.cloud.model.tool.ToolBpmNode;
import com.casic.cloud.model.tool.ToolUser;
import com.casic.cloud.service.tool.ToolBpmNodeService;
import com.casic.cloud.service.tool.ToolService;
import com.casic.cloud.service.tool.ToolUserService;
import com.casic.cloud.toolEnvironment.util.FileUtil;
import com.casic.cloud.toolEnvironment.util.LinuxUtil;
import com.hotent.core.annotion.Action;
import com.hotent.core.bpm.graph.ShapeMeta;
import com.hotent.core.bpm.model.FlowNode;
import com.hotent.core.bpm.model.NodeCache;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.bpm.util.BpmUtil;
import com.hotent.core.model.TaskExecutor;

import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.bpm.ExecutionStack;
import com.hotent.platform.model.bpm.FormModel;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskNodeStatus;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.system.SysTemplate;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.BpmNodeUserService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.BpmTaskAssigneeService;
import com.hotent.platform.service.bpm.ExecutionStackService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.bpm.TaskUserService;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.form.BpmFormDefService;
import com.hotent.platform.service.system.SysTemplateService;
import com.hotent.platform.service.system.SysUserService;

/**
 * 对象功能:流程实例扩展控制器类 开发公司: 开发人员:csx 创建时间:2011-12-03 09:33:06
 */
@Controller
@RequestMapping("/platform/bpm/processRun/")
public class ProcessRunController extends BaseController {
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmService bpmService;
	@Resource
	private BpmNodeUserService bpmNodeUserService;
	@Resource
	private ExecutionStackService executionStackService;
	@Resource
	private TaskOpinionService taskOpinionService;
	@Resource
	private TaskService taskService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private BpmFormDefService bpmFormDefService;
	@Resource
	private SysTemplateService sysTemplateService;
	@Resource
	private HistoryService historyService;
	@Resource
	private BpmTaskAssigneeService bpmTaskAssigneeService;
	@Resource
	private TaskUserService taskUserService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private ToolBpmNodeService toolBpmNodeService;
	@Resource
	private ToolUserService toolUserService;
	@Resource
	private ToolService toolService;

	/**
	 * 取得流程实例扩展分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看流程实例扩展分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<ProcessRun> list = processRunService.getAll(new QueryFilter(
				request, "processRunItem"));
		ModelAndView mv = this.getAutoView().addObject("processRunList", list);
		return mv;
	}

	/**
	 * 任务追回,检查当前正在运行的任务是否允许进行追回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("recover")
	public void recover(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		String failResult = "{success:false,result:0}";
		String successResult = "{success:true,result:1}";
		Long runId = RequestUtil.getLong(request, "runId");
		ProcessRun processRun = processRunService.getById(runId);
		List<ProcessTask> taskList = bpmService.getTasks(processRun
				.getActInstId());

		// 1.查找当前用户最新审批的任务
		TaskOpinion taskOpinion = taskOpinionService.getLatestUserOpinion(
				processRun.getActInstId(), ContextUtil.getCurrentUserId());
		if (taskOpinion == null) {
			out.println(failResult);
			return;
		}

		// 2.查找执行的堆栈，若发现该用户审批为正在运行的任务父节点，则允许进行追回操作。否则不允许追回,TODO 加上令牌处理
		ExecutionStack executionStack = executionStackService.getLastestStack(
				processRun.getActInstId(), taskOpinion.getTaskKey());
		if (executionStack == null) {
			out.println(failResult);
			return;
		}

		// 3.查找该栈节点下的所有任务，看是否有已经完成的，若有，则不允许追回
		List<ExecutionStack> subStackList = executionStackService
				.getByParentIdAndEndTimeNotNull(executionStack.getStackId());
		if (subStackList.size() > 0) {
			out.println(failResult);
			return;
		}

		// 4.在当前运行任务中，查找其父节点为以上树的，则允许追回
		for (ProcessTask taskEntity : taskList) {
			ExecutionStack curTaskExecution = executionStackService
					.getLastestStack(processRun.getActInstId(),
							taskEntity.getTaskDefinitionKey());
			// 找到需要追回的任务
			if (curTaskExecution.getParentId().equals(
					executionStack.getStackId())) {
				// 加上回退状态，使其通过任务完成事件中记录其是追回的状态
				taskService.setVariable(
						taskEntity.getId(),
						BpmConst.NODE_APPROVAL_STATUS + "_"
								+ taskEntity.getTaskDefinitionKey(),
						TaskOpinion.STATUS_RECOVER);
				ProcessCmd processCmd = new ProcessCmd();
				processCmd.setTaskId(taskEntity.getId());
				// 设置为追回
				processCmd.setRecover(true);
				// 进行回退处理
				processRunService.nextProcess(processCmd);
				// 判断是否为别人交办的任务 如果是从交办记录表中删除记录
				boolean isAssigneeTask = processRunService
						.isAssigneeTask(taskOpinion.getTaskId().toString());
				if (isAssigneeTask) {
					bpmTaskAssigneeService.delByTaskId(taskOpinion.getTaskId()
							.toString());
				}
				out.println(successResult);
				return;
			}
		}
		out.println(failResult);
		return;
	}

	/**
	 * 取得流程实例扩展分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("history")
	@Action(description = "查看流程实例扩展分页列表")
	public ModelAndView history(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "processRunItem");
		List<ProcessRun> list = processRunService.getAllHistory(queryFilter);
		ModelAndView mv = this.getAutoView().addObject("processRunList", list);
		return mv;
	}

	/**
	 * 取得流程实例扩展分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myStart")
	@Action(description = "查看我发起的流程列表")
	public ModelAndView myStart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "processRunItem");
		filter.addFilter("creatorId", ContextUtil.getCurrentUserId());
		List<ProcessRun> list = processRunService.getAll(filter);

		ModelAndView mv = this.getAutoView().addObject("processRunList", list);

		return mv;
	}

	/**
	 * 催促执行人、所属人（优先催促执行人，没有执行人就催促所属人）
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("urgeOwner")
	@Action(description = "打开催办界面")
	public ModelAndView urgeOwner(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actInstId = RequestUtil.getString(request, "actInstId");
		String inner = "";
		String mail = "";
		String shortmsg = "";
		ProcessRun processRun = processRunService.getByActInstanceId(actInstId);

		SysTemplate innerTemp = sysTemplateService
				.getDefaultByType(SysTemplate.INNER_TEMP_TYPE);
		if (innerTemp != null)
			inner = innerTemp.getContent();
		SysTemplate mailTemp = sysTemplateService
				.getDefaultByType(SysTemplate.MAIL_TEMP_TYPE);
		if (mailTemp != null)
			mail = mailTemp.getContent();
		SysTemplate shortTemp = sysTemplateService
				.getDefaultByType(SysTemplate.SHORT_TEMP_TYPE);
		if (shortTemp != null)
			shortmsg = shortTemp.getContent();
		ModelAndView mv = this.getAutoView()
				.addObject("processSubject", processRun.getSubject())
				.addObject("actInstId", actInstId).addObject("inner", inner)
				.addObject("mail", mail).addObject("shortMsg", shortmsg);
		return mv;
	}

	/**
	 * 执行催办动作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("urgeSubmit")
	@Action(description = "执行催办")
	public void urgeSubmit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String instanceId = RequestUtil.getString(request, "actInstId");
			String ifShort = RequestUtil.getString(request, "short");
			String ifMail = RequestUtil.getString(request, "mail");
			String subject = RequestUtil.getString(request, "subject");
			String processSubject = RequestUtil.getString(request,
					"processSubject");
			String inner_msg = RequestUtil.getString(request, "inner_msg");
			String short_msg = RequestUtil.getString(request, "short_msg");
			String mail_msg = RequestUtil.getString(request, "mail_msg");

			Boolean userProcessSubject = RequestUtil.getBoolean(request,
					"userProcessSubject");
			if (userProcessSubject || StringUtils.isEmpty(subject))
				subject = processSubject;

			Map<String, String> map = new HashMap<String, String>();
			map.put("inner", inner_msg);
			map.put("mail", mail_msg);
			map.put("shortmsg", short_msg);

			String informType = ifShort + "," + ifMail;
			ProcessCmd processCmd = new ProcessCmd();
			processCmd.setInformType(informType);
			List<Task> taskList = taskService.createTaskQuery()
					.processInstanceId(instanceId).list();
			processRunService.notify(taskList, informType, subject, map);
			String successResult = "催办信息已发送成功";
			writeResultMessage(response.getWriter(), successResult,
					ResultMessage.Success);
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(
						ResultMessage.Fail, "催办信息已发送失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				ResultMessage resultMessage = new ResultMessage(
						ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	/**
	 * 查看我参与审批流程列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myAttend")
	@Action(description = "查看我参与审批流程列表")
	public ModelAndView myAttend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "processRunItem");
		filter.addFilter("assignee", ContextUtil.getCurrentUserId().toString());
		List<ProcessRun> list = processRunService.getMyAttend(filter);
		for (ProcessRun processRun : list) {
			if (processRun.getStatus().shortValue() != ProcessRun.STATUS_FINISH
					.shortValue()) {
				// 1.查找当前用户是否有最新审批的任务
				TaskOpinion taskOpinion = taskOpinionService
						.getLatestUserOpinion(processRun.getActInstId(),
								ContextUtil.getCurrentUserId());
				if (BeanUtils.isNotEmpty(taskOpinion))
					processRun.setRecover(ProcessRun.RECOVER);
			}
		}
		ModelAndView mv = this.getAutoView().addObject("processRunList", list);
		return mv;
	}

	/**
	 * 删除流程实例扩展
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除流程实例扩展")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		// search all node and all tools to delete the instances
		Long[] ids = RequestUtil.getLongAryByStr(request, "runId");
		if (ids != null && ids.length != 0)
			for (Long uId : ids) {
				ProcessRun processRun = processRunService.getById(uId);
				// BpmDefinition bpmDefinition =
				// bpmDefinitionService.getById(processRun.getDefId());
				if (processRun != null) {
					List<BpmNodeSet> nodeSets = bpmNodeSetService
							.getByDefId(processRun.getDefId());
					for (BpmNodeSet nodeSet : nodeSets) {
						QueryFilter queryFilter2 = new QueryFilter(request,
								"toolBpmNodeItem");
						queryFilter2.addFilter("setId", nodeSet.getSetId());
						List<ToolBpmNode> toolBpmNodes = toolBpmNodeService
								.getAll(queryFilter2);
						for (ToolBpmNode toolBpmNode : toolBpmNodes) {
							ToolUser toolUser = toolUserService
									.getById(toolBpmNode.getToolUserId());
							Tool tool = toolService.getById(toolUser
									.getToolId());
							String toolAddress = toolUser.getMyToolAddress();
							if (toolAddress == null || toolAddress == "") {
								toolAddress = tool.getToolAddress();
							}
							File templateDir = FileUtil
									.getTemplateDirStartWithTemplate(new File(
											toolAddress));
							if (templateDir != null) {
								try{
									
									File instanceDir = FileUtil
											.getExistDirStartWithRunId(
													uId.toString(), "",
													templateDir, true);
									
									if (instanceDir != null && instanceDir.exists()) {
										//delete on webserver
										FileUtil.delFile(instanceDir.getAbsolutePath());
										//delete on cluster
										Set<String> slaveIps = LinuxUtil.slaveServers.keySet();
										Iterator<String> iterator = slaveIps.iterator();
										if (iterator.hasNext()) {
											String ip = iterator.next();
											String userNameAndPassword = LinuxUtil.slaveServers.get(ip);
											String userName = userNameAndPassword.split(",", 0)[0];
											String password = userNameAndPassword.split(",", 0)[1];
											LinuxUtil.executeShellCommand(ip, userName, password, 22, "rm -r -f "+instanceDir.getAbsolutePath());
										}
									}
								}catch(Exception e){
									e.printStackTrace();
								}
								
							}
						}
					}
				}

			}
		//-----------------del instance dir over-----------------------//
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "runId");
			processRunService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除流程实例成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.Fail, "删除流程实例失败");
		}

		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑流程实例
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑流程实例扩展")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long runId = RequestUtil.getLong(request, "runId");
		String returnUrl = RequestUtil.getPrePage(request);
		ProcessRun processRun = null;
		if (runId != 0) {
			processRun = processRunService.getById(runId);
		} else {
			processRun = new ProcessRun();
		}
		return getAutoView().addObject("processRun", processRun).addObject(
				"returnUrl", returnUrl);
	}

	/**
	 * 取得流程实例扩展明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看流程实例扩展明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long runId = RequestUtil.getLong(request, "runId", 0L);
		String taskId = RequestUtil.getString(request, "taskId");
		String preUrl = RequestUtil.getPrePage(request);
		ProcessRun processRun = null;
		// String actInstId="";
		if (runId != 0L) {
			processRun = processRunService.getById(runId);
		} else {
			TaskEntity task = bpmService.getTask(taskId);
			processRun = processRunService.getByActInstanceId(task
					.getProcessInstanceId());
		}

		List<HistoricTaskInstance> hisTasks = bpmService
				.getHistoryTasks(processRun.getActInstId());
		return getAutoView().addObject("processRun", processRun)
				.addObject("hisTasks", hisTasks).addObject("returnUrl", preUrl);
	}

	/**
	 * 取得流程实例扩展明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail")
	@Action(description = "查看流程实例扩展明细")
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long runId = RequestUtil.getLong(request, "runId", 0L);
		String preUrl = RequestUtil.getPrePage(request);
		ProcessRun processRun = null;
		// String actInstId="";
		if (runId != 0L) {
			processRun = processRunService.getById(runId);
		}

		List<HistoricTaskInstance> hisTasks = bpmService
				.getHistoryTasks(processRun.getActInstId());
		return getAutoView().addObject("processRun", processRun).addObject(
				"returnUrl", preUrl);
	}

	/**
	 * 显示任务流程图中的每个任务节点的信息及其人员信息,需要传入runId或processInstanceId
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userImage")
	public ModelAndView userImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = getAutoView();
		String action = request.getParameter("action");
		String processDefinitionId = null;
		String returnUrl = RequestUtil.getPrePage(request);
		String defXml = null;
		// 这三个参数任意传入一个均可
		String taskId = request.getParameter("taskId");
		String runId = request.getParameter("runId");
		String actInstanceId = request.getParameter("processInstanceId");
		TaskEntity taskEntity = null;
		ProcessRun processRun = null;
		while (StringUtils.isEmpty(defXml)) {
			if (StringUtils.isNotEmpty(taskId)) {
				taskEntity = bpmService.getTask(taskId);
				processDefinitionId = taskEntity.getProcessDefinitionId();
				actInstanceId = taskEntity.getProcessInstanceId();
				processRun = processRunService
						.getByActInstanceId(actInstanceId);
				defXml = bpmService
						.getDefXmlByProcessDefinitionId(processDefinitionId);
				continue;
			}
			if (StringUtils.isNotEmpty(runId)) {
				processRun = processRunService.getById(new Long(runId));
				request.setAttribute("processInstanceId",
						processRun.getActInstId());
				actInstanceId = processRun.getActInstId();
				processDefinitionId = processRun.getActDefId();
				defXml = bpmService
						.getDefXmlByProcessDefinitionId(processDefinitionId);
				continue;
			}
			if (StringUtils.isNotEmpty(actInstanceId)) {
				processRun = processRunService
						.getByActInstanceId(actInstanceId);
				defXml = bpmService
						.getDefXmlByProcessProcessInanceId(actInstanceId);
				continue;
			}
		}

		// 查找父流程
		if (taskEntity != null) {
			ExecutionEntity executionEntity = bpmService
					.getExecutionByTaskId(taskEntity.getId());
			if (executionEntity != null
					&& executionEntity.getSuperExecutionId() != null) {
				ExecutionEntity superExecutionEntity = bpmService
						.getExecution(executionEntity.getSuperExecutionId());
				modelAndView.addObject("superInstanceId",
						superExecutionEntity.getProcessInstanceId());
			}
		}

		ShapeMeta shapeMeta = BpmUtil.transGraph(defXml);
		modelAndView
				.addObject("notShowTopBar",
						request.getParameter("notShowTopBar"))
				.addObject("defXml", defXml)
				.addObject("processInstanceId", actInstanceId)
				.addObject("shapeMeta", shapeMeta)
				.addObject("returnUrl", returnUrl)
				.addObject("taskEntity", taskEntity)
				.addObject("processRun", processRun)
				.addObject("action", action);
		return modelAndView;
	}

	/**
	 * 任务办理页面的 流程示意图对话框
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("processImage")
	public ModelAndView processImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = getAutoView();
		String taskId = request.getParameter("taskId");
		String actInstanceId = request.getParameter("processInstanceId");
		Long runId = RequestUtil.getLong(request, "runId", 0L);
		String defXml = "";
		TaskEntity taskEntity = null;
		ProcessRun processRun = null;
		if (StringUtil.isNotEmpty(taskId)) {
			taskEntity = bpmService.getTask(taskId);
			actInstanceId = taskEntity.getProcessInstanceId();
			processRun = processRunService.getByActInstanceId(actInstanceId);
			String processDefinitionId = taskEntity.getProcessDefinitionId();
			defXml = bpmService
					.getDefXmlByProcessDefinitionId(processDefinitionId);
		}
		if (StringUtil.isNotEmpty(actInstanceId)) {
			processRun = processRunService.getByActInstanceId(actInstanceId);
			defXml = bpmService.getDefXmlByProcessDefinitionId(processRun
					.getActDefId());
		}
		if (runId != 0) {
			processRun = processRunService.getById(runId);
			defXml = bpmService.getDefXmlByProcessDefinitionId(processRun
					.getActDefId());
			actInstanceId = processRun.getActInstId();
		}

		// 查找父流程
		if (taskEntity != null) {
			ExecutionEntity executionEntity = bpmService
					.getExecutionByTaskId(taskEntity.getId());
			if (executionEntity != null
					&& executionEntity.getSuperExecutionId() != null) {
				ExecutionEntity superExecutionEntity = bpmService
						.getExecution(executionEntity.getSuperExecutionId());
				modelAndView.addObject("superInstanceId",
						superExecutionEntity.getProcessInstanceId());
			}
		}

		ShapeMeta shapeMeta = BpmUtil.transGraph(defXml);
		modelAndView
				.addObject("notShowTopBar",
						request.getParameter("notShowTopBar"))
				.addObject("defXml", defXml)
				.addObject("processInstanceId", actInstanceId)
				.addObject("shapeMeta", shapeMeta)
				.addObject("taskEntity", taskEntity)
				.addObject("processRun", processRun);
		return modelAndView;
	}

	/**
	 * 查看子流程。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("subFlowImage")
	public ModelAndView subFlowImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String subProcessDefinitionId = null;
		List<String> subProcessInstanceId = new ArrayList<String>();
		String subDefXml = null;
		String actInstanceId = request.getParameter("processInstanceId");
		String processDefinitionId = request
				.getParameter("processDefinitionId");
		String nodeId = request.getParameter("nodeId");
		// 子流程是否已经运行 0-未运行，1-已运行
		int subProcessRun = 0;
		// 取得子外部子流程的key.
		String subFlowKey = null;
		String actDefId = null;
		if (StringUtil.isNotEmpty(actInstanceId)) {
			actDefId = processRunService.getByActInstanceId(actInstanceId)
					.getActDefId();
		} else if (StringUtil.isNotEmpty(processDefinitionId)) {
			actDefId = processDefinitionId;
		}

		Map<String, FlowNode> flowNodeMap = NodeCache.getByActDefId(actDefId);
		Iterator<Entry<String, FlowNode>> entrySet = flowNodeMap.entrySet()
				.iterator();
		while (entrySet.hasNext()) {
			Entry<String, FlowNode> entry = entrySet.next();
			String flowNodeId = entry.getKey();
			if (flowNodeId.equals(nodeId)) {
				FlowNode flowNode = entry.getValue();
				subFlowKey = flowNode.getAttribute("subFlowKey");
				break;
			}
		}
		// 取得外部子流程的定义
		BpmDefinition subBpmDefinition = bpmDefinitionService
				.getMainDefByActDefKey(subFlowKey);
		if (subBpmDefinition.getActDeployId() != null) {
			subDefXml = bpmService.getDefXmlByDeployId(subBpmDefinition
					.getActDeployId().toString());
		} else {
			subDefXml = BpmUtil
					.transform(subBpmDefinition.getDefKey(),
							subBpmDefinition.getSubject(),
							subBpmDefinition.getDefXml());
		}

		// 取得所有的子流程实例
		List<HistoricProcessInstance> historicProcessInstances = historyService
				.createHistoricProcessInstanceQuery()
				.superProcessInstanceId(actInstanceId).list();
		if (BeanUtils.isNotEmpty(historicProcessInstances)) {
			// 筛选当选节点的子流程
			for (HistoricProcessInstance instance : historicProcessInstances) {
				String procDefId = instance.getProcessDefinitionId();
				BpmDefinition bpmDef = bpmDefinitionService
						.getByActDefId(procDefId);
				if (bpmDef.getDefKey().equals(subFlowKey)) {
					subProcessInstanceId.add(instance.getId());
					subProcessRun = 1;
				}
			}
		}
		if (subProcessRun == 0) {
			subProcessDefinitionId = subBpmDefinition.getActDefId();
		}

		ShapeMeta subShapeMeta = BpmUtil.transGraph(subDefXml);
		ModelAndView modelAndView = getAutoView();
		modelAndView.addObject("defXml", subDefXml);
		modelAndView.addObject("subProcessRun", subProcessRun);
		if (subProcessRun == 0) {
			modelAndView.addObject("processDefinitionId",
					subProcessDefinitionId);
		} else {
			modelAndView.addObject("processInstanceIds", subProcessInstanceId);
		}
		modelAndView.addObject("shapeMeta", subShapeMeta);
		return modelAndView;

	}

	/**
	 * 根据流程实例id获取流程的状态。
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getFlowStatusByInstanceId")
	@ResponseBody
	public List<TaskNodeStatus> getFlowStatusByInstanceId(
			HttpServletRequest request) {
		String instanceId = RequestUtil.getString(request, "instanceId");
		List<TaskNodeStatus> list = bpmService
				.getNodeCheckStatusInfo(instanceId);
		return list;
	}

	/**
	 * 显示流程实例任务中某个任务节点上的执行人员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("taskUser")
	public ModelAndView taskUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 当前用户执行人员ID
		Set<TaskExecutor> assignees = new HashSet<TaskExecutor>();
		// 当前节点的候选执行人员ID
		Set<TaskExecutor> candidateUsers = new HashSet<TaskExecutor>();

		String nodeId = request.getParameter("nodeId");
		String processInstanceId = request.getParameter("processInstanceId");

		ModelAndView modelAndView = getAutoView();
		modelAndView.addObject("assignees", assignees).addObject(
				"candidateUsers", candidateUsers);
		boolean found = false;

		// 1.检查该流程实例中的当前任务列表，若nodeId对应的任务存在，即直接从该任务里获取指定的人员或候选用户
		List<ProcessTask> taskList = bpmService.getTasks(processInstanceId);
		// nodeId对应的任务节点是否有任务实例
		for (ProcessTask task : taskList) {
			if (task.getTaskDefinitionKey().equals(nodeId)) {
				found = true;
				if (task.getAssignee() != null) {// 存在指定的用户
					Long assignee = Long.parseLong(task.getAssignee());
					ISysUser sysUser = sysUserService.getById(assignee);
					assignees.add(TaskExecutor.getTaskUser(task.getAssignee(),
							sysUser.getFullname()));
				} else {// 获取该任务的候选用户列表
					Set<TaskExecutor> cUIds = taskUserService
							.getCandidateExecutors(task.getId());
					for (TaskExecutor taskExecutor : cUIds) {
						candidateUsers.add(taskExecutor);
					}
				}
			}
		}
		if (found)
			return modelAndView;
		// 2.检查审批历史表里是否存在该节点的审核人
		List<TaskOpinion> list = taskOpinionService.getByActInstIdTaskKey(
				processInstanceId, nodeId);
		for (TaskOpinion taskOpinion : list) {
			if (taskOpinion.getExeUserId() != null) {
				assignees.add(TaskExecutor.getTaskUser(taskOpinion
						.getExeUserId().toString(), taskOpinion
						.getExeFullname()));
			}
		}

		if (assignees.size() > 0)
			return modelAndView;

		// 3.若没有找到该任务定义对应的实例 ，则直接从流程的节点人员配置中获取执行人员
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("executionId", "0");
		List<TaskExecutor> configUsers = bpmNodeUserService
				.getExeUserIdsByInstance(processInstanceId, nodeId, "", vars)
				.get(BpmNodeUser.USER_TYPE_PARTICIPATION);
		if (configUsers != null) {
			candidateUsers.addAll(configUsers);
		}

		return modelAndView;
	}

	/**
	 * 返回流程表单列表包括全局表单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("nodeForms")
	public ModelAndView nodeForms(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long runId = RequestUtil.getLong(request, "runId", 0L);
		String taskId = RequestUtil.getString(request, "taskId");

		String preUrl = RequestUtil.getPrePage(request);
		ProcessRun processRun = null;
		String actDefId = "";
		if (runId != 0L) {
			processRun = processRunService.getById(new Long(runId));
			actDefId = processRun.getActDefId();
		} else {
			TaskEntity task = bpmService.getTask(taskId);
			actDefId = task.getProcessDefinitionId();
			processRun = processRunService.getByActInstanceId(task
					.getProcessInstanceId());
		}
		BpmDefinition bpmDefinition = bpmDefinitionService
				.getByActDefId(actDefId);
		List<BpmNodeSet> bpmNodeSetList = bpmNodeSetService
				.getByDefId(bpmDefinition.getDefId());
		List<HistoricTaskInstance> historyTasks = bpmService
				.getHistoryTasks(processRun.getActInstId());
		
		// historyTasks.get(0).getName()
		// historyTasks.get(0).getTaskDefinitionKey()
		ModelAndView view = this.getAutoView()
				.addObject("bpmDefinition", bpmDefinition)
				.addObject("bpmNodeSetList", bpmNodeSetList)
				.addObject("processRun", processRun)
				.addObject("preUrl", preUrl)
				.addObject("historyTasks", historyTasks);
		return view;
	}

	/**
	 * 返回流程表单的详细信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("formHtml")
	public ModelAndView formHtml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long curUserId = ContextUtil.getCurrentUserId();
		String runId = request.getParameter("runId");
		ProcessRun processRun = processRunService.getById(new Long(runId));

		String nodeId = request.getParameter("nodeId");
		String ctxPath = request.getContextPath();
		String taskId = request.getParameter("taskId");
		// 查找任务节点
		TaskEntity taskEntity = bpmService.getTask(taskId);
		HistoricTaskInstanceEntity historyTask = null;
		if (taskEntity == null) {
			historyTask = bpmService.getHistoricTaskInstanceEntity(taskId);
		}
		Map<String, Object> variables = taskService.getVariables(taskId);

		System.out.println("!!!!!!!!!!" + variables);
		FormModel formMap = null;
		try {
			formMap = bpmFormDefService.doGetForm(processRun, nodeId,
					curUserId, ctxPath, variables);
		} catch (ActivitiException ex) {
			throw new Exception("该流程实例已经结束!");
		}

		Boolean isExtForm = (Boolean) (formMap.getFormType() == 1);

		String form = "";
		if (formMap.getFormType() == 0) {
			form = formMap.getFormHtml();
		} else if (formMap.getFormType() == 1) {
			form = formMap.getFormUrl();
		}

		BpmDefinition bpmDefinition = bpmDefinitionService.getById(processRun
				.getDefId());

		ModelAndView view = this.getAutoView()
				.addObject("bpmDefinition", bpmDefinition)
				.addObject("processRun", processRun).addObject("form", form)
				.addObject("isExtForm", isExtForm);

		return view;

	}

}
