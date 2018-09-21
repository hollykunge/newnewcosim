package com.hotent.platform.controller.system;

import java.util.Date;
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
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.MessageReceiver;
import com.hotent.platform.model.system.MessageSend;
import com.hotent.platform.service.system.MessageReceiverService;
import com.hotent.platform.service.system.MessageSendService;

/**
 * 对象功能:发送消息 控制器类
 */
@Controller
@RequestMapping("/platform/system/messageSend/")
public class MessageSendFormController extends BaseFormController
{
	@Resource
	private MessageSendService messageSendService;
	
	/**
	 * 添加或更新发送消息。
	 * @param request
	 * @param response
	 * @param messageSend 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新发送消息")
	public void save(HttpServletRequest request, HttpServletResponse response, MessageSend messageSend,BindingResult bindResult) throws Exception
	{
		
		ResultMessage resultMessage=validForm("messageSend", messageSend, bindResult, request);
		//add your custom validation rule here such as below code:
		//bindResult.rejectValue("name","errors.exist.student",new Object[]{"jason"},"重复姓名");
		if(resultMessage.getResult()==ResultMessage.Fail)
		{
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		String receiverId=RequestUtil.getString(request, "receiverId");
		String receiverName=RequestUtil.getString(request, "receiverName");
		String receiverOrgId=RequestUtil.getString(request, "receiverOrgId");
		String receiverOrgName=RequestUtil.getString(request, "receiverOrgName");
		
		ISysUser curUser = ContextUtil.getCurrentUser();
		messageSendService.addMessageSend(messageSend, curUser,
				receiverId, receiverName, receiverOrgId, receiverOrgName);
		
		String resultMsg=null;
		
		if(messageSend.getId()==null)
		{
			resultMsg=getText("record.added","发送消息");
		}
		else
		{
			resultMsg=getText("record.updated","发送消息");
		}
		
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @ModelAttribute
    protected MessageSend getFormObject(@RequestParam("id") Long id,Model model) throws Exception {
		logger.debug("enter MessageSend getFormObject here....");
		MessageSend messageSend=null;
		if(id!=null){
			messageSend=messageSendService.getById(id);
		}else{
			messageSend= new MessageSend();
		}
		return messageSend;
    }

}
