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
import com.hotent.platform.model.system.SysAcceptIp;
import com.hotent.platform.service.system.SysAcceptIpService;

/**
 * 对象功能:可访问地址 控制器类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2012-02-20 17:35:46
 */
@Controller
@RequestMapping("/platform/system/sysAcceptIp/")
public class SysAcceptIpController extends BaseController
{
	@Resource
	private SysAcceptIpService sysAcceptIpService;
	
	/**
	 * 取得可访问地址分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看可访问地址分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<SysAcceptIp> list=sysAcceptIpService.getAll(new QueryFilter(request,"sysAcceptIpItem"));
		ModelAndView mv=this.getAutoView().addObject("sysAcceptIpList",list);
		
		return mv;
	}
	
	/**
	 * 删除可访问地址
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除可访问地址")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "acceptId");
			sysAcceptIpService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除可访问地址成功!");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑可访问地址")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long acceptId=RequestUtil.getLong(request,"acceptId");
		String returnUrl=RequestUtil.getPrePage(request);
		SysAcceptIp sysAcceptIp=null;
		if(acceptId!=0){
			 sysAcceptIp= sysAcceptIpService.getById(acceptId);
		}else{
			sysAcceptIp=new SysAcceptIp();
		}
		return getAutoView().addObject("sysAcceptIp",sysAcceptIp).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得可访问地址明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看可访问地址明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"acceptId");
		SysAcceptIp sysAcceptIp = sysAcceptIpService.getById(id);		
		return getAutoView().addObject("sysAcceptIp", sysAcceptIp);
	}

}
