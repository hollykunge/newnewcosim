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
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.platform.model.system.Seal;
import com.hotent.platform.service.system.SealService;
import com.hotent.platform.service.system.SysFileService;

/**
 * 对象功能:电子印章 控制器类
 * 开发公司:
 * 开发人员:raise
 * 创建时间:2012-08-29 11:26:00
 */
@Controller
@RequestMapping("/platform/system/seal/")
public class SealFormController extends BaseFormController
{
	@Resource
	private SealService sealService;
	
	
	@Resource
	private SysFileService sysFileService;
	
	
	
	/**
	 * 添加或更新电子印章。
	 * @param request
	 * @param response
	 * @param seal 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新电子印章")
	public void save(HttpServletRequest request, HttpServletResponse response, Seal seal,BindingResult bindResult) throws Exception
	{
		Long userId=ContextUtil.getCurrentUserId();
		seal.setBelongId(userId);
		seal.setSealPath(sysFileService.getById(seal.getAttachmentId()).getFilePath());
		
		ResultMessage resultMessage=validForm("seal", seal, bindResult, request);
		//add your custom validation rule here such as below code:
		//bindResult.rejectValue("name","errors.exist.student",new Object[]{"jason"},"重复姓名");
		if(resultMessage.getResult()==ResultMessage.Fail)
		{
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		String resultMsg=null;
		if(seal.getSealId()==null){
			seal.setSealId(UniqueIdUtil.genId());
			sealService.add(seal);
			resultMsg=getText("record.added","电子印章");
		}else{
			sealService.update(seal);
			resultMsg=getText("record.updated","电子印章");
		}
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * @param sealid
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @ModelAttribute
    protected Seal getFormObject(@RequestParam("sealId") Long sealId,Model model) throws Exception {
		logger.debug("enter Seal getFormObject here....");
		Seal seal=null;
		if(sealId!=null){
			seal=sealService.getById(sealId);
		}else{
			seal= new Seal();
		}
		return seal;
    }

}
