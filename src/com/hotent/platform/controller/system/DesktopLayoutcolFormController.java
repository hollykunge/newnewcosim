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

import com.hotent.platform.model.system.DesktopLayoutcol;
import com.hotent.platform.service.system.DesktopLayoutcolService;

/**
 * 对象功能:桌面栏目管理表 控制器类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2012-03-20 16:39:01
 */
@Controller
@RequestMapping("/platform/system/desktopLayoutcol/")
public class DesktopLayoutcolFormController extends BaseFormController
{
	@Resource
	private DesktopLayoutcolService desktopLayoutcolService;
	
	/**
	 * 添加或更新桌面栏目管理表。
	 * @param request
	 * @param response
	 * @param desktopLayoutcol 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新桌面栏目管理表")
	public void save(HttpServletRequest request, HttpServletResponse response, DesktopLayoutcol desktopLayoutcol,BindingResult bindResult) throws Exception
	{
		ResultMessage resultMessage=validForm("desktopLayoutcol", desktopLayoutcol, bindResult, request);
		if(resultMessage.getResult()==ResultMessage.Fail)
		{	writeResultMessage(response.getWriter(),resultMessage);
			return;
		}
		String resultMsg=null;
		if(desktopLayoutcol.getId()==null){
			desktopLayoutcol.setId(UniqueIdUtil.genId());
			desktopLayoutcolService.add(desktopLayoutcol);
			resultMsg=getText("record.added","桌面栏目管理表");
		}else{
			desktopLayoutcolService.update(desktopLayoutcol);
			resultMsg=getText("record.updated","桌面栏目管理表");
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
    protected DesktopLayoutcol getFormObject(@RequestParam("id") Long id,Model model) throws Exception {
		logger.debug("enter DesktopLayoutcol getFormObject here....");
		DesktopLayoutcol desktopLayoutcol=null;
		if(id!=null){
			desktopLayoutcol=desktopLayoutcolService.getById(id);
		}else{
			desktopLayoutcol= new DesktopLayoutcol();
		}
		return desktopLayoutcol;
    }

}
