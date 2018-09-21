package com.casic.cloud.controller.config.enterprise.userOrg;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.model.system.SysOrgParam;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SysOrgParamService;
import com.hotent.platform.service.system.SysUserOrgService;

/**
 * 对象功能:用户所属组织或部门 控制器类
 * 开发公司:
 * 开发人员:pkq
 * 创建时间:2011-12-07 18:23:24
 */
@Controller
@RequestMapping("/cloud/config/enterprise/userOrg/")
public class EnterpriseUserOrgController extends BaseController
{
	@Resource
	private SysUserOrgService sysUserOrgService;
	@Resource
	private SysOrgDao sysOrgDao;
	@Resource
	private SysOrgParamService sysOrgParamService;
	
	/**
	 * 取得用户所属组织或部门分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看用户所属组织或部门分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<SysUserOrg> list=sysUserOrgService.getAll(new QueryFilter(request,"sysUserOrgItem"));
		ModelAndView mv=this.getAutoView().addObject("sysUserOrgList",list);
		return mv;
	}
	
	/**
	 * 删除用户所属组织或部门
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除用户所属组织或部门")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		Long[] lAryId =RequestUtil.getLongAryByStr(request, "userOrgId");
		sysUserOrgService.delByIds(lAryId);
		
		//SecurityUtil.removeAll();
		
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑用户所属组织或部门")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long userOrgId=RequestUtil.getLong(request,"userOrgId");
		String returnUrl=RequestUtil.getPrePage(request);
		SysUserOrg sysUserOrg=null;
		if(userOrgId!=null){
			 sysUserOrg= sysUserOrgService.getById(userOrgId);
		}else{
			sysUserOrg=new SysUserOrg();
		}
		return getAutoView().addObject("sysUserOrg",sysUserOrg).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得用户所属组织或部门明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看用户所属组织或部门明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"userOrgId");
		SysUserOrg sysUserOrg = sysUserOrgService.getById(id);		
		return getAutoView().addObject("sysUserOrg", sysUserOrg);
	}
	
	/**
	 * 取得组织下的所有用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userList")
	@Action(description="取得用户列表")
	public ModelAndView userList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv=this.getAutoView();
		mv.addObject("action", "global");
		return getUserByOrgId(request,mv);
	}
	
	private ModelAndView getUserByOrgId(HttpServletRequest request,ModelAndView mv){
		Long orgId=RequestUtil.getLong(request, "orgId");
		ISysOrg sysOrg= sysOrgDao.getById(orgId);
		
		if(sysOrg==null){
			mv.addObject("sysOrg",sysOrg);
		}
		else{
			QueryFilter filter=new QueryFilter(request,"sysOrgItem");
			filter.addFilter("path", sysOrg.getPath());
			List<SysUserOrg> list=sysUserOrgService.getUserByOrgId(filter);
			mv.addObject("sysOrgUserList",list)
			.addObject("orgId",orgId)
			.addObject("sysOrg",sysOrg);
		}
		return mv;
	}
	
	@RequestMapping("userGradeList")
	@Action(description="取得用户列表")
	public ModelAndView userGradeList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv=new ModelAndView();
		mv.addObject("action", "grade");
		mv.setViewName("/platform/system/sysUserOrgUserList.jsp");
		return getUserByOrgId(request,mv);
	}
	
	
	
	
	/**
	 * 加入人员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addOrgUser")
	public void addOrgUser(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Long[] userIds =RequestUtil.getLongAryByStr(request, "userIds");
		Long orgId=RequestUtil.getLong(request, "orgId");
		ResultMessage resultMessage=null;
		try {
			sysUserOrgService.addOrgUser(userIds, orgId);
			
			//SecurityUtil.removeAll();
			
			resultMessage=new ResultMessage(ResultMessage.Success,"在组织添加用户成功!");
		} catch (Exception e) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,"在组织添加用户中失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
		writeResultMessage(response.getWriter(), resultMessage);
	}
	
	
	/**
	 * 设置是否主岗位。
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("setIsPrimary")
	public void setIsPrimary(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Long userPosId=RequestUtil.getLong(request, "userPosId",0);
		ResultMessage message=null;
		String preUrl= RequestUtil.getPrePage(request);
		try{
			sysUserOrgService.setIsPrimary(userPosId);
			message=new ResultMessage(ResultMessage.Success, "设置组织成功");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "设置组织失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 设置是主管。
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("setIsCharge")
	public void setIsCharge(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Long userPosId=RequestUtil.getLong(request, "userPosId",0);
		ResultMessage message=null;
		String preUrl= RequestUtil.getPrePage(request);
		try{
			sysUserOrgService.setIsCharge(userPosId);
			message=new ResultMessage(ResultMessage.Success, "设置主管成功");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "设置主管失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 设置管理员。
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("setIsManage")
	public void setIsManage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Long userPosId=RequestUtil.getLong(request, "userPosId",0);
		ResultMessage message=null;
		String preUrl= RequestUtil.getPrePage(request);
		try{
			sysUserOrgService.setIsManage(userPosId);
			message=new ResultMessage(ResultMessage.Success, "设置管理员成功");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "设置管理员失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	

}
