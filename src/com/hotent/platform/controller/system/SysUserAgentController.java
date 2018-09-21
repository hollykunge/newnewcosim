package com.hotent.platform.controller.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.bpm.BpmAgent;
import com.hotent.platform.model.system.SysUserAgent;
import com.hotent.platform.service.bpm.BpmAgentService;
import com.hotent.platform.service.system.SysUserAgentService;


/**
 * 对象功能:SYS_USER_AGENT 控制器类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2011-12-27 11:54:23
 */
@Controller
@RequestMapping("/platform/system/sysUserAgent/")
public class SysUserAgentController extends BaseController
{
	@Resource
	private SysUserAgentService sysUserAgentService;
	@Resource
	private BpmAgentService bpmAgentService;
	
	/**
	 * 取得SYS_USER_AGENT分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看SYS_USER_AGENT分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter filter = new QueryFilter(request,"sysUserAgentItem");
		filter.addFilter("agentuserid", ContextUtil.getCurrentUserId());
		List<SysUserAgent> list=sysUserAgentService.getAll(filter);
		ModelAndView mv=this.getAutoView().addObject("sysUserAgentList",list);
		return mv;
	}
	
	/**
	 * 删除SYS_USER_AGENT
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除SYS_USER_AGENT")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "agentid");
			sysUserAgentService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除代理授权成功！");
		}catch (Exception e) {
			message=new ResultMessage(ResultMessage.Fail,"删除代理授权失败："+e.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑SYS_USER_AGENT")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		ModelAndView mv=getAutoView();
		Long agentid=RequestUtil.getLong(request,"agentid");
		String returnUrl=RequestUtil.getPrePage(request);
		SysUserAgent sysUserAgent=null;
		if(agentid!=0){
			 sysUserAgent= sysUserAgentService.getById(agentid);
			 List<BpmAgent> bpmAgentList= bpmAgentService.getByAgentId(agentid);
			 mv.addObject("bpmAgentList", bpmAgentList);
		}else{
			sysUserAgent=new SysUserAgent();
		}
		return mv.addObject("sysUserAgent",sysUserAgent).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得SYS_USER_AGENT明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看SYS_USER_AGENT明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"agentid");
		SysUserAgent sysUserAgent = sysUserAgentService.getById(id);		
		return getAutoView().addObject("sysUserAgent", sysUserAgent);
	}

}
