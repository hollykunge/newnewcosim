package com.hotent.platform.controller.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.system.MessageLog;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.MessageLogService;

/**
 * <pre>
 * 对象功能:消息日志 控制器类
 * </pre>
 */
@Controller
@RequestMapping("/platform/system/messageLog/")
public class MessageLogController extends BaseController {
	@Resource
	private MessageLogService messageLogService;

	/**
	 * 添加或更新消息日志。
	 * 
	 * @param request
	 * @param response
	 * @param messageLog
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新消息日志")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resultMsg = null;
		MessageLog messageLog = getFormObject(request);
		try {
			if (messageLog.getId() == null || messageLog.getId() == 0) {
				messageLog.setId(UniqueIdUtil.genId());
				messageLogService.add(messageLog);
				resultMsg = getText("record.added", "消息日志");
			} else {
				messageLogService.update(messageLog);
				resultMsg = getText("record.updated", "消息日志");
			}
			writeResultMessage(response.getWriter(), resultMsg,ResultMessage.Success);
		} catch (Exception e) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"添加或更新消息日志失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	/**
	 * 取得 MessageLog 实体
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected MessageLog getFormObject(HttpServletRequest request)
			throws Exception {

		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher((new String[] { "yyyy-MM-dd" })));

		String json = RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);

		MessageLog messageLog = (MessageLog) JSONObject.toBean(obj,
				MessageLog.class);

		return messageLog;
	}

	/**
	 * 取得消息日志分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看消息日志分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<MessageLog> list = messageLogService.getAll(new QueryFilter(
				request, "messageLogItem"));
		ModelAndView mv = this.getAutoView().addObject("messageLogList", list);

		return mv;
	}

	/**
	 * 删除消息日志
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除消息日志")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			messageLogService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除消息日志成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败"
					+ ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑消息日志
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑消息日志")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String returnUrl = RequestUtil.getPrePage(request);
		MessageLog messageLog = messageLogService.getById(id);

		return getAutoView().addObject("messageLog", messageLog).addObject(
				"returnUrl", returnUrl);
	}

	/**
	 * 取得消息日志明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看消息日志明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		MessageLog messageLog = messageLogService.getById(id);
		return getAutoView().addObject("messageLog", messageLog);
	}
}
