package com.hotent.platform.controller.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.system.SysParam;
import com.hotent.platform.service.system.SysParamService;

/**
 * 对象功能:组织或人员参数属性 控制器类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2012-02-23 17:43:35
 */
@Controller
@RequestMapping("/platform/system/sysParam/")
public class SysParamController extends BaseController
{
	@Resource
	private SysParamService sysParamService;
	
	/**
	 * 取得组织或人员参数属性分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看组织或人员参数属性分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<SysParam> list=sysParamService.getAll(new QueryFilter(request,"sysParamItem"));
		ModelAndView mv=this.getAutoView().addObject("sysParamList",list).addObject("dataTypeMap",SysParam.DATA_TYPE_MAP);
		return mv;
	}
	
	/**
	 * 删除组织或人员参数属性
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除组织或人员参数属性")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "paramId");
			sysParamService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除组织或人员参数属性成功!");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑组织或人员参数属性")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long paramId=RequestUtil.getLong(request,"paramId");
		String returnUrl=RequestUtil.getPrePage(request);
		SysParam sysParam=null;
		if(paramId!=0){
			 sysParam= sysParamService.getById(paramId);
		}else{
			sysParam=new SysParam();
		}
		return getAutoView().addObject("sysParam",sysParam).addObject("returnUrl", returnUrl).addObject("dataTypeMap",SysParam.DATA_TYPE_MAP);
	}

	/**
	 * 取得组织或人员参数属性明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看组织或人员参数属性明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"paramId");
		SysParam sysParam = sysParamService.getById(id);		
		return getAutoView().addObject("sysParam", sysParam).addObject("dataTypeMap",SysParam.DATA_TYPE_MAP);
	}

}
