package com.hotent.platform.controller.form;

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
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseFormController;

import com.hotent.platform.model.form.BpmFormDialog;
import com.hotent.platform.service.form.BpmFormDialogService;

/**
 * 对象功能:通用表单对话框 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ray
 * 创建时间:2012-06-25 11:05:09
 */
@Controller
@RequestMapping("/platform/form/bpmFormDialog/")
public class BpmFormDialogFormController extends BaseFormController
{
	@Resource
	private BpmFormDialogService bpmFormDialogService;
	
	/**
	 * 添加或更新通用表单对话框。
	 * @param request
	 * @param response
	 * @param bpmFormDialog 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新通用表单对话框")
	public void save(HttpServletRequest request, HttpServletResponse response, BpmFormDialog bpmFormDialog,BindingResult bindResult) throws Exception
	{
		ResultMessage resultMessage=validForm("bpmFormDialog", bpmFormDialog, bindResult, request);
		if(resultMessage.getResult()==ResultMessage.Fail){
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		String resultMsg="";
		
		if(StringUtil.isEmpty(bpmFormDialog.getDisplayfield())){
			resultMsg=getText("未设置显示的字段");
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
			return;
		}
		if(StringUtil.isEmpty(bpmFormDialog.getResultfield())){
			resultMsg=getText("未设置返回的字段");
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
			return;
		}		
		if(bpmFormDialog.getId()==0){
			bpmFormDialog.setId(UniqueIdUtil.genId());
			String alias=bpmFormDialog.getAlias();
			boolean isExist=bpmFormDialogService.isExistAlias(alias);
			if(isExist){
				resultMsg=getText("该别名已经存在！");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
				return;
			}
			
			bpmFormDialogService.add(bpmFormDialog);
			resultMsg=getText("record.added","通用表单对话框");
			
		}else{
			String alias=bpmFormDialog.getAlias();
			Long id=bpmFormDialog.getId();
			boolean isExist=bpmFormDialogService.isExistAliasForUpd(id, alias);
			if(isExist){
				resultMsg=getText("该别名已经存在！");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
				return;
			}
			bpmFormDialogService.update(bpmFormDialog);
			resultMsg=getText("record.updated","通用表单对话框");
		}
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * @param ID
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @ModelAttribute
    protected BpmFormDialog getFormObject(@RequestParam("id") Long id,Model model) throws Exception {
		logger.debug("enter BpmFormDialog getFormObject here....");
		BpmFormDialog bpmFormDialog=null;
		if(id>0){
			bpmFormDialog=bpmFormDialogService.getById(id);
		}else{
			bpmFormDialog= new BpmFormDialog();
		}
		return bpmFormDialog;
    }

}
