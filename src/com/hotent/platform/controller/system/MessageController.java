package com.hotent.platform.controller.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.Message;
import com.hotent.platform.service.system.MessageService;

/**
 * 对象功能:消息设置 控制器类
 */
@Controller
@RequestMapping("/platform/system/message/")
public class MessageController extends BaseController
{
	@Resource
	private MessageService messageService;
	
	/**
	 * 取得消息设置分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看消息设置分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Message> list=messageService.getAll(new QueryFilter(request,"messageItem"));
		ModelAndView mv=this.getAutoView().addObject("messageList",list);
		
		return mv;
	}
	
	/**
	 * 删除消息设置
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除消息设置")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "messageId");
			messageService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除消息设置成功!");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑消息设置")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long messageId=RequestUtil.getLong(request,"messageId");
		String returnUrl=RequestUtil.getPrePage(request);
		Message message=null;
		if(messageId!=0){
			 message= messageService.getById(messageId);
		}else{
			message=new Message();
		}
		return getAutoView().addObject("message",message).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得消息设置明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看消息设置明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"messageId");
		Message message = messageService.getById(id);		
		return getAutoView().addObject("message", message);
	}

}
