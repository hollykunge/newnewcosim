package com.hotent.platform.controller.system;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.system.MessageReceiver;
import com.hotent.platform.model.system.MessageSend;
import com.hotent.platform.service.system.MessageReceiverService;
import com.hotent.platform.service.system.MessageSendService;

/**
 * 对象功能:消息接收者 控制器类
 */
@Controller
@RequestMapping("/platform/system/messageReceiver/")
public class MessageReceiverController extends BaseController
{
	@Resource
	private MessageReceiverService receiverService;
	@Resource
	private MessageSendService sendService;
	
	/**
	 * 取得消息接收者分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看消息接收者分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter=new QueryFilter(request,"messageReceiverItem");
		queryFilter.addFilter("receiverId", ContextUtil.getCurrentUserId());
		List<MessageSend> list=sendService.getReceiverByUser(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("messageReceiverList",list).addObject("pam", queryFilter.getFilters());
		
		return mv;
	}
	
	/**
	 * 删除消息接收者
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除消息接收者")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			receiverService.delByIdsCascade(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除消息接收者成功!");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑消息接收者")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		MessageReceiver messageReceiver=null;
		if(id!=0){
			 messageReceiver= receiverService.getById(id);
		}else{
			messageReceiver=new MessageReceiver();
		}
		return getAutoView().addObject("messageReceiver",messageReceiver).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得消息接收者明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看消息接收者明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		MessageReceiver messageReceiver = receiverService.getById(id);		
		return getAutoView().addObject("messageReceiver", messageReceiver);
	}

}
