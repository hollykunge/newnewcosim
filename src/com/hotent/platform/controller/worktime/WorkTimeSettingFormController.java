package com.hotent.platform.controller.worktime;

import java.util.List;

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
import com.hotent.platform.model.worktime.WorkTime;
import com.hotent.platform.model.worktime.WorkTimeSetting;
import com.hotent.platform.service.worktime.WorkTimeService;
import com.hotent.platform.service.worktime.WorkTimeSettingService;

/**
 * 对象功能:班次设置 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ray
 * 创建时间:2012-02-20 14:57:31
 */
@Controller
@RequestMapping("/platform/worktime/workTimeSetting/")
public class WorkTimeSettingFormController extends BaseFormController
{
	@Resource
	private WorkTimeSettingService workTimeSettingService;
	
	@Resource
	private WorkTimeService workTimeService;
	
	/**
	 * 添加或更新班次设置。
	 * @param request
	 * @param response
	 * @param workTimeSetting 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新班次设置")
	public void save(HttpServletRequest request, HttpServletResponse response, WorkTimeSetting workTimeSetting,BindingResult bindResult) throws Exception
	{
		
		ResultMessage resultMessage=validForm("workTimeSetting", workTimeSetting, bindResult, request);
		//add your custom validation rule here such as below code:
		//bindResult.rejectValue("name","errors.exist.student",new Object[]{"jason"},"重复姓名");
		if(resultMessage.getResult()==ResultMessage.Fail)
		{
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		String resultMsg=null;
		
		Long settingId;
		if(workTimeSetting.getId()==null){
			settingId = UniqueIdUtil.genId();
			workTimeSetting.setId(settingId);
			workTimeSettingService.add(workTimeSetting);
			resultMsg=getText("record.added","班次设置");
		}else{
			settingId = workTimeSetting.getId();
			workTimeSettingService.update(workTimeSetting);
			resultMsg=getText("record.updated","班次设置");
		}
		
		String[] startTime = (String[])request.getParameterValues("startTime");
		String[] endTime = (String[])request.getParameterValues("endTime");
		String[] memo = (String[])request.getParameterValues("desc");
		
		// 班次时间添加
		workTimeService.workTimeAdd(settingId, startTime, endTime, memo);
		
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
    protected WorkTimeSetting getFormObject(@RequestParam("id") Long id,Model model) throws Exception {
		logger.debug("enter WorkTimeSetting getFormObject here....");
		WorkTimeSetting workTimeSetting=null;
		if(id!=null){
			workTimeSetting=workTimeSettingService.getById(id);
		}else{
			workTimeSetting= new WorkTimeSetting();
		}
		return workTimeSetting;
    }

}
