package com.casic.cloud.controller.tool;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.tool.ToolBpmDefinition;
import com.casic.cloud.service.tool.ToolBpmDefinitionService;
import com.hotent.core.annotion.Action;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;

@Controller
@RequestMapping("/cloud/toolBpmDef/")
public class ToolBpmDefinitionController extends BaseController {
	@Resource
	private ToolBpmDefinitionService toolBpmDefinitionService;

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
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		Long defId = RequestUtil.getLong(request, "defId");
		QueryFilter queryFilter = new QueryFilter(request, "toolBpmDefItem", true);
		List<ToolBpmDefinition> list = toolBpmDefinitionService.getAll(queryFilter);
		mv.addObject("toolBpmDefList", list).addObject("defId", defId);
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
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "toolDefId");
			toolBpmDefinitionService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除工具流程映射成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping(value = { "add" })
	@Action(description = "添加或更新映射")
	public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long defId = RequestUtil.getLong(request, "defId");
		Long toolIds[] = RequestUtil.getLongAryByStr(request, "toolIds");
		toolBpmDefinitionService.add(defId, toolIds);
		response.sendRedirect(RequestUtil.getPrePage(request));
	}
}
