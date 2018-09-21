package com.casic.cloud.controller.tool;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotent.core.annotion.Action;

import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.page.PageBean;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.casic.cloud.model.tool.Cloud_tool_user_parasMapper;
import com.casic.cloud.service.tool.Cloud_tool_user_parasMapperService;
import com.hotent.core.web.ResultMessage;

/**
 * <pre>
 * 对象功能:cloud_tool_user_parasMapper 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:maliang
 * 创建时间:2014-03-01 17:11:23
 * </pre>
 */
@Controller
@RequestMapping("/cloud/tool/parasMapper/")
public class Cloud_tool_user_parasMapperController extends BaseController {
	@Resource
	private Cloud_tool_user_parasMapperService cloud_tool_user_parasMapperService;

	/**
	 * 添加或更新cloud_tool_user_parasMapper。
	 * 
	 * @param request
	 * @param response
	 * @param cloud_tool_user_parasMapper
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新cloud_tool_user_parasMapper")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String resultMsg = null;
		Cloud_tool_user_parasMapper cloud_tool_user_parasMapper = getFormObject(request);
		request.setAttribute("cloudToolUserId", request.getParameter("cloudToolUserId"));
		try {
			if (cloud_tool_user_parasMapper.getId() == null
					|| cloud_tool_user_parasMapper.getId() == 0) {
				cloud_tool_user_parasMapper.setId(UniqueIdUtil.genId());
				cloud_tool_user_parasMapperService
						.add(cloud_tool_user_parasMapper);
				resultMsg = getText("record.added", "parasMapper");
			} else {
				cloud_tool_user_parasMapperService
						.update(cloud_tool_user_parasMapper);
				resultMsg = getText("record.updated", "parasMapper");
			}
			System.out.println(":::::::::::::::::::::::::::" + resultMsg);
			writeResultMessage(response.getWriter(), resultMsg,
					ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 取得 Cloud_tool_user_parasMapper 实体
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Cloud_tool_user_parasMapper getFormObject(
			HttpServletRequest request) throws Exception {

		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher((new String[] { "yyyy-MM-dd" })));

		String json = RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);

		Cloud_tool_user_parasMapper cloud_tool_user_parasMapper = (Cloud_tool_user_parasMapper) JSONObject
				.toBean(obj, Cloud_tool_user_parasMapper.class);

		return cloud_tool_user_parasMapper;
	}

	/**
	 * 取得cloud_tool_user_parasMapper分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看cloud_tool_user_parasMapper分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<Cloud_tool_user_parasMapper> list = cloud_tool_user_parasMapperService
				.getAll(new QueryFilter(request,
						"cloud_tool_user_parasMapperItem"));
		ModelAndView mv = this.getAutoView()
				.addObject("cloud_tool_user_parasMapperList", list)
				.addObject("cloudToolUserId", request.getParameter("cloudToolUserId"));

		return mv;
	}

	/**
	 * 删除cloud_tool_user_parasMapper
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除cloud_tool_user_parasMapper")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		request.setAttribute("cloudToolUserId", request.getParameter("cloudToolUserId"));
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			cloud_tool_user_parasMapperService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success,
					"删除cloud_tool_user_parasMapper成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败"
					+ ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑cloud_tool_user_parasMapper
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑cloud_tool_user_parasMapper")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String returnUrl = RequestUtil.getPrePage(request);
		Cloud_tool_user_parasMapper cloud_tool_user_parasMapper = cloud_tool_user_parasMapperService
				.getById(id);

		return getAutoView().addObject("cloud_tool_user_parasMapper",
				cloud_tool_user_parasMapper).addObject("returnUrl", returnUrl).addObject("cloudToolUserId", request.getParameter("cloudToolUserId"));
	}

	/**
	 * 取得cloud_tool_user_parasMapper明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看cloud_tool_user_parasMapper明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		Cloud_tool_user_parasMapper cloud_tool_user_parasMapper = cloud_tool_user_parasMapperService
				.getById(id);
		return getAutoView().addObject("cloud_tool_user_parasMapper",
				cloud_tool_user_parasMapper);
	}

}
