package com.casic.cloud.controller.tool;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.tool.Tool;
import com.casic.cloud.model.tool.ToolUser;
import com.casic.cloud.service.tool.ToolService;
import com.casic.cloud.service.tool.ToolUserService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;

@Controller
@RequestMapping("/cloud/tool/")
public class ToolController extends BaseController {
	private Log logger = LogFactory.getLog(ToolController.class);
	@Resource
	private ToolService toolService;
	@Resource
	private ToolUserService toolUserService;
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
	@Action(description = "查看工具分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "toolItem");
		List<Tool> list = toolService.getAll(queryFilter);
		
		ModelAndView mv = this.getAutoView().addObject("toolList", list);
		return mv;
	}

	/**
	 * 取得已发布分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toolList")
	@Action(description = "查看工具分页列表")
	public ModelAndView toolList(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		QueryFilter queryFilter = new QueryFilter(request, "toolItem");
		List<Tool> list = toolService.getAll(queryFilter);
		ModelAndView mv = this.getAutoView().addObject("toolList", list);
		return mv;
	}

	/**
	 * 删除工具公告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除工具")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			toolService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除工具成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 取得工具明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看工具明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		Tool tool = toolService.getById(id);
		return getAutoView().addObject("tool", tool);
	}

	/**
	 * 编辑新闻公告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑工具")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String returnUrl = RequestUtil.getPrePage(request);
		Tool tool = toolService.getById(id);
		return getAutoView().addObject("tool", tool).addObject("returnUrl", returnUrl);
	}

	/**
	 * 添加或更新工具。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新工具")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		Tool tool = getFormObject(request);
		try {	

			// 当前更新时间
			tool.setUpdatetime(new Date());
			if (tool.getId() == null) {
				tool.setId(UniqueIdUtil.genId());
				tool.setCreatetime(new Date());
				toolService.add(tool);
				resultMsg = getText("record.added", "工具");
			} else {
				toolService.update(tool);
				resultMsg = getText("record.updated", "工具");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得 tool实体
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Tool getFormObject(HttpServletRequest request) throws Exception {
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyyMMddHHmmss" })));
		String json = RequestUtil.getString(request, "json");
		logger.debug("JSON=" + json);
		JSONObject obj = JSONObject.fromObject(json);
		Tool tool = (Tool) JSONObject.toBean(obj, Tool.class);
		return tool;
	}

	@RequestMapping(value = { "chooseTools" })
	public ModelAndView chooseTools(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		return mv;
	}
}
