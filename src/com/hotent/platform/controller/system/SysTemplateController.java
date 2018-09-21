package com.hotent.platform.controller.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysTemplate;
import com.hotent.platform.service.system.SysTemplateService;

/**
 * 对象功能:模版管理 控制器类
 * 开发公司:
 * 开发人员:pkq
 * 创建时间:2011-12-28 14:04:30
 */
@Controller
@RequestMapping("/platform/system/sysTemplate/")
public class SysTemplateController extends BaseController
{
	@Resource
	private SysTemplateService sysTemplateService;
	
	/**
	 * 取得模版管理分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看模版管理分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<SysTemplate> list=sysTemplateService.getAll(new QueryFilter(request,"sysTemplateItem"));
		ModelAndView mv=this.getAutoView().addObject("sysTemplateList",list);
	
		return mv;
	}
	@RequestMapping("dialog")
	@Action(description="查看模版管理分页列表")
	public ModelAndView dialog(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<SysTemplate> list=sysTemplateService.getAll(new QueryFilter(request,"sysTemplateItem"));
		ModelAndView mv=this.getAutoView().addObject("sysTemplateList",list);
		return mv;
	}
	
	
	/**
	 * 删除模版管理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除模版管理")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultMessage message=null;
		String preUrl= RequestUtil.getPrePage(request);
		try {
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "templateId");
			
			for(Long id:lAryId){
				if(id==1||id==2||id==3){
					message=new ResultMessage(ResultMessage.Fail,"不能删除系统模板");
					addMessage(message, request);
					response.sendRedirect(preUrl);
					return;
				}
			}
			sysTemplateService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除模板成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.Fail,"删除模板失败,"+e.getMessage());
		}
		
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑模版管理")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long templateId=RequestUtil.getLong(request,"templateId");
		String returnUrl=RequestUtil.getPrePage(request);
		SysTemplate sysTemplate=null;
		if(templateId!=0){
			 sysTemplate= sysTemplateService.getById(templateId);
		}else{
			sysTemplate=new SysTemplate();
		}
		return getAutoView().addObject("sysTemplate",sysTemplate).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得模版管理明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看模版管理明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"templateId");
		SysTemplate sysTemplate = sysTemplateService.getById(id);		
		return getAutoView().addObject("sysTemplate", sysTemplate);
	}

	/**
	 * 更改默认模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("setDefault")
	@Action(description="设置每种模板类型的默认模板")
	public void setDefault(HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		long templateId=RequestUtil.getLong(request, "templateId");
		int tempType=RequestUtil.getInt(request, "tempType");
		try{
			sysTemplateService.setDefault(templateId,tempType);
			message=new ResultMessage(ResultMessage.Success, "设置成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "设置失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
}
