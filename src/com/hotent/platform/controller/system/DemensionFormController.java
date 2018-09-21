package com.hotent.platform.controller.system;

import java.util.HashMap;
import java.util.Map;

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

import com.hotent.platform.model.system.Demension;
import com.hotent.platform.service.system.DemensionService;

/**
 * 对象功能:SYS_DEMENSION 控制器类
 * 开发公司:
 * 开发人员:pkq
 * 创建时间:2011-12-01 14:23:26
 */
@Controller
@RequestMapping("/platform/system/demension/")
public class DemensionFormController extends BaseFormController
{
	@Resource
	private DemensionService demensionService;
	
	/**
	 * 添加或更新SYS_DEMENSION。
	 * @param request
	 * @param response
	 * @param demension 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新SYS_DEMENSION")
	public void save(HttpServletRequest request, HttpServletResponse response, Demension demension,BindingResult bindResult) throws Exception
	{
		
		ResultMessage resultMessage=validForm("demension", demension, bindResult, request);
		//add your custom validation rule here such as below code:
		//bindResult.rejectValue("name","errors.exist.student",new Object[]{"jason"},"重复姓名");
		if(resultMessage.getResult()==ResultMessage.Fail)
		{
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		String resultMsg=null;
		if(demension.getDemId()==null)
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("demName", demension.getDemName());
			boolean isTrue=demensionService.getNotExists(param);
			if(isTrue)
			{
				demension.setDemId(UniqueIdUtil.genId());
				demensionService.add(demension);
				resultMsg=getText("record.added","维度");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
			}
			else
			{
				resultMsg=getText("该维度已经存在！");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
			}
		}
		else
		{
			demensionService.update(demension);
			resultMsg=getText("record.updated","维度");
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}
		
	}
	
	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * @param demId
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @ModelAttribute
    protected Demension getFormObject(@RequestParam("demId") Long demId,Model model) throws Exception {
		logger.debug("enter Demension getFormObject here....");
		Demension demension=null;
		if(demId!=null){
			demension=demensionService.getById(demId);
		}else{
			demension= new Demension();
		}
		return demension;
    }

}
