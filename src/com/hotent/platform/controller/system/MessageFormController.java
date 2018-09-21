package com.hotent.platform.controller.system;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;

import com.hotent.platform.model.system.Message;
import com.hotent.platform.service.system.MessageService;

/**
 * 对象功能:消息设置 控制器类
 */
@Controller
@RequestMapping("/platform/system/message/")
public class MessageFormController extends BaseFormController
{
	@Resource
	private MessageService messageService;
	
	/**
	 * 添加或更新消息设置。
	 * @param request
	 * @param response
	 * @param message 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新消息设置")
	public void save(HttpServletRequest request, HttpServletResponse response, Message message,BindingResult bindResult) throws Exception
	{
		
		ResultMessage resultMessage=validForm("message", message, bindResult, request);
		//add your custom validation rule here such as below code:
		//bindResult.rejectValue("name","errors.exist.student",new Object[]{"jason"},"重复姓名");
		if(resultMessage.getResult()==ResultMessage.Fail)
		{
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		String resultMsg=null;
		if(message.getMessageId()==null){
			message.setMessageId(UniqueIdUtil.genId());
			messageService.add(message);
			resultMsg=getText("record.added","消息设置");
		}else{
			messageService.update(message);
			resultMsg=getText("record.updated","消息设置");
		}
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * @param messageId
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @ModelAttribute
    protected Message getFormObject(@RequestParam("messageId") Long messageId,Model model) throws Exception {
		logger.debug("enter Message getFormObject here....");
		Message message=null;
		if(messageId!=null){
			message=messageService.getById(messageId);
		}else{
			message= new Message();
		}
		return message;
    }

}
