package com.hotent.platform.controller.form;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotent.core.annotion.Action;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.TimeUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.form.BpmFormDefService;
import com.hotent.platform.service.form.FormUtil;


/**
 * 对象功能:BPM_FORM_DEF 控制器类 
 * 开发公司:广州宏天软件有限公司 
 * 开发人员:xwy 
 * 创建时间:2011-12-22 11:07:56
 */
@Controller
@RequestMapping("/platform/form/bpmFormDef/")
public class BpmFormDefFormController extends BaseFormController
{
	@Resource
	private BpmFormDefService service;
	
	/**
	 * 添加或更新自定义表单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新自定义表单" ,operateType="自定义表单")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 表定义
		String data = request.getParameter("data");
		// 权限
		String permission = request.getParameter("permission");
		JSONObject formDefJson=JSONObject.fromObject(data);
		
		String publishTime= formDefJson.getString("publishTime");
		if(StringUtil.isEmpty(publishTime)){
			formDefJson.put("publishTime", TimeUtil.getCurrentTime());
		}
		
		BpmFormDef bpmFormDef = (BpmFormDef) JSONObject.toBean(formDefJson, BpmFormDef.class);
		JSONObject jsonObject =JSONObject.fromObject(permission);		
		String html=bpmFormDef.getHtml();
		html=html.replace("？", "");
		String template=FormUtil.getFreeMarkerTemplate(html);		
		bpmFormDef.setTemplate(template);

		try{
			if (bpmFormDef.getFormDefId() == 0){
				service.addForm(bpmFormDef,jsonObject);
				String msg = getText("record.added", "自定义表单");
				writeResultMessage(response.getWriter(), msg, ResultMessage.Success);
			}
			else{
				service.updateForm(bpmFormDef,jsonObject);
				String msg = getText("record.updated", "自定义表单");
				writeResultMessage(response.getWriter(), msg, ResultMessage.Success);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"保存自定义表单数据失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}
	
	
}
