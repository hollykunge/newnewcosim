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

import com.hotent.platform.model.system.SysParam;
import com.hotent.platform.service.system.SysParamService;

/**
 * 对象功能:系统参数属性 控制器类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2012-02-23 17:43:35
 */
@Controller
@RequestMapping("/platform/system/sysParam/")
public class SysParamFormController extends BaseFormController
{
	@Resource
	private SysParamService sysParamService;
	
	/**
	 * 添加或更新系统参数属性。
	 * @param request
	 * @param response
	 * @param sysParam 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新系统参数属性")
	public void save(HttpServletRequest request, HttpServletResponse response, SysParam sysParam,BindingResult bindResult) throws Exception
	{
		
		ResultMessage resultMessage=validForm("sysParam", sysParam, bindResult, request);
		//add your custom validation rule here such as below code:
		//bindResult.rejectValue("name","errors.exist.student",new Object[]{"jason"},"重复姓名");
		if(resultMessage.getResult()==ResultMessage.Fail)
		{
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		String resultMsg=null;
		if(sysParam.getParamId()==null){
			sysParam.setParamId(UniqueIdUtil.genId());
			sysParamService.add(sysParam);
			resultMsg=getText("record.added","系统参数属性");
		}else{
			sysParamService.update(sysParam);
			resultMsg=getText("record.updated","系统参数属性");
		}
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * @param paramId
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @ModelAttribute
    protected SysParam getFormObject(@RequestParam("paramId") Long paramId,Model model) throws Exception {
		logger.debug("enter SysParam getFormObject here....");
		SysParam sysParam=null;
		if(paramId!=null){
			sysParam=sysParamService.getById(paramId);
		}else{
			sysParam= new SysParam();
		}
		return sysParam;
    }

}
