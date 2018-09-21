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
import com.hotent.platform.model.system.SysFile;
import com.hotent.platform.service.system.SysFileService;

@Controller
@RequestMapping("/platform/system/sysFile/")
public class SysFileFormController extends BaseFormController
{
	@Resource
	private SysFileService sysFileService;
	
	
	/**
	 * 添加或更新附件。
	 * @param request
	 * @param response
	 * @param sysFile 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, SysFile sysFile,BindingResult bindResult) throws Exception
	{
		
		ResultMessage resultMessage=validForm("sysFile", sysFile, bindResult, request);
		//add your custom validation rule here such as below code:
		//bindResult.rejectValue("name","errors.exist.student",new Object[]{"jason"},"重复姓名");
		if(resultMessage.getResult()==ResultMessage.Fail)
		{
			writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		String resultMsg=null;
		if(sysFile.getFileId()==null){
			add(sysFile);
			resultMsg=getText("record.added","附件");
		}else{
			upd(sysFile);
			resultMsg=getText("record.updated","附件");
		}
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
	
	/**
	 * 添加sysFile实体
	 * @param sysFile
	 * @throws Exception
	 */
	@Action(description="添加附件")
	public void add(SysFile sysFile) throws Exception 
	{
		sysFile.setFileId(UniqueIdUtil.genId());
		sysFileService.add(sysFile);
	}
	/**
	 * 更新sysFile实体
	 * @param sysFile
	 * @throws Exception
	 */
	@Action(description="更新附件")
	public void upd(SysFile sysFile) throws Exception 
	{
		sysFileService.update(sysFile);
	}
	
	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * @param fileId
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @ModelAttribute
    protected SysFile getFormObject(@RequestParam("fileId") Long fileId,Model model) throws Exception {
		logger.debug("enter SysFile getFormObject here....");
		SysFile sysFile=null;
		if(fileId!=null){
			sysFile=sysFileService.getById(fileId);
		}else{
			sysFile= new SysFile();
		}
		return sysFile;
    }
    
    
}
