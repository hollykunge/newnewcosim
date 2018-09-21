package com.casic.cloud.controller.tool;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.ToolProvider;

import net.sf.json.JSONObject;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.tool.ToolBpmNode;
import com.casic.cloud.model.tool.ToolUser;
import com.casic.cloud.model.tool.input.Cloud_tool_user_inputFile;
import com.casic.cloud.model.tool.output.Cloud_tool_user_outputFile;
import com.casic.cloud.service.tool.ToolBpmNodeService;
import com.casic.cloud.service.tool.ToolUserService;
import com.casic.cloud.service.tool.input.Cloud_tool_user_inputFileService;
import com.casic.cloud.service.tool.output.Cloud_tool_user_outputFileService;
import com.hotent.core.annotion.Action;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.BpmService;

@Controller
@RequestMapping("/cloud/toolBpmNode/")
public class ToolBpmNodeController extends BaseController {
	@Resource
	private ToolBpmNodeService toolBpmNodeService;
	@Resource
	private BpmService bpmService;
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private ToolUserService toolUserService;
	@Resource
	private Cloud_tool_user_inputFileService cloud_tool_user_inputFileService;
	@Resource
	private Cloud_tool_user_outputFileService cloud_tool_user_outputFileService;

	/**
	 * 取得已发布分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看映射分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		Long setId = RequestUtil.getLong(request, "setId");
		QueryFilter queryFilter = new QueryFilter(request, "toolBpmNodeItem",
				true);
		List<ToolBpmNode> list = toolBpmNodeService.getAll(queryFilter);
		mv.addObject("toolBpmNodeList", list).addObject("setId", setId);
		return mv;
	}

	/**
	 * 删除映射公告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除映射")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "toolNodeId");
			toolBpmNodeService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除工具流程映射成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败"
					+ ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping(value = { "add" })
	@Action(description = "添加或更新映射")
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long setId = RequestUtil.getLong(request, "setId");
		Long toolIds[] = RequestUtil.getLongAryByStr(request, "toolIds");
		
		toolBpmNodeService.add(setId, toolIds);
		response.sendRedirect(RequestUtil.getPrePage(request));
	}

	@RequestMapping("edit")
	@Action(description = "编辑工具")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long toolNodeId = RequestUtil.getLong(request, "toolNodeId");
		String returnUrl = RequestUtil.getPrePage(request);
		ToolBpmNode tool = toolBpmNodeService.getById(toolNodeId);
		return getAutoView().addObject("tool", tool).addObject("returnUrl",
				returnUrl);
	}

	@RequestMapping("save")
	@Action(description = "添加或更新工具")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resultMsg = null;
		ToolBpmNode tool = getFormObject(request);
		try {
			toolBpmNodeService.update(tool);
			resultMsg = getText("record.updated", "工具");
			writeResultMessage(response.getWriter(), resultMsg,
					ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得 tool实体
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ToolBpmNode getFormObject(HttpServletRequest request)
			throws Exception {
		String json = RequestUtil.getString(request, "json");
		logger.debug("JSON=" + json);
		JSONObject obj = JSONObject.fromObject(json);
		ToolBpmNode tool = (ToolBpmNode) JSONObject.toBean(obj,
				ToolBpmNode.class);
		return tool;
	}

	@RequestMapping(value = { "taskTools" })
	public ModelAndView taskTools(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String inputParas = "";
		// 解析输入参数
		Map<String, String[]> parasMap = request.getParameterMap();
		Set<String> parasSet = parasMap.keySet();
		Iterator<String> iterator = parasSet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String[] values = parasMap.get(key);
			
			// System.out.println(key+"  "+values[0]);
			if (values != null && values.length > 0) {
				if (key.matches(".*:.*")) {
					String[] strs = key.split(":", 0);
					inputParas += "&" + strs[strs.length - 1] + "=";
					for (int i = 0; i < values.length; i++) {
						if (i != values.length - 1) {
							inputParas += values[i] + ",";
						} else {
							inputParas += values[i];
						}
					}

				}
			}
			if (key.equals("taskId")) {
				inputParas += "&" + key + "=";

				inputParas += values[0];

			}
		}

		// 将inputParas放入session
		request.getSession().setAttribute("inputParas", inputParas);

		String taskId = RequestUtil.getString(request, "taskId");
		TaskEntity taskEntity = bpmService.getTask(taskId);
		String nodeId = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(actDefId,
				nodeId);
		Long setId = bpmNodeSet.getSetId();
		QueryFilter queryFilter = new QueryFilter(request, "toolBpmNodeItem",
				true);
		queryFilter.getFilters().put("setId", setId);
		queryFilter.setSortColumns("toolUserId");
		List<ToolBpmNode> list = toolBpmNodeService.getAll(queryFilter);
		for (ToolBpmNode toolBpmNode : list) {
			QueryFilter queryFilter1 = new QueryFilter(request);
			queryFilter1.addFilter("cloudToolUserId",
					toolBpmNode.getToolUserId());

			List<Cloud_tool_user_inputFile> inputFiles = cloud_tool_user_inputFileService
					.getAll(queryFilter1);
			List<Cloud_tool_user_outputFile> outputFiles = cloud_tool_user_outputFileService
					.getAll(queryFilter1);
			toolBpmNode.setInputFiles(inputFiles);
			toolBpmNode.setOutputFiles(outputFiles);
		}
		ModelAndView mv = getAutoView();
		mv.addObject("toolBpmNodeList", list).addObject("taskId", taskId);
		return mv;
	}
}
