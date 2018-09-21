package com.casic.cloud.controller.reg.register;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.appleframe.utils.file.FileUtils;
import com.casic.cloud.model.reg.register.Register;
import com.casic.cloud.service.mail.CloudMailService;
import com.casic.cloud.service.reg.register.RegisterService;
import com.hotent.core.web.ResultMessage;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgInfo;
/**
 *<pre>
 * 对象功能:cloud_user_register 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 11:27:33
 *</pre>
 */
@Controller
@RequestMapping("/cloud/reg/register/")
public class RegisterController extends BaseController
{
	@Resource
	private RegisterService registerService;
	@Resource
	private CloudMailService cloudMailService; 
	
	
	/**
	 * 添加或更新cloud_user_register。
	 * @param request
	 * @param response
	 * @param register 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新cloud_user_register")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{	
		SysOrgInfo sysOrgInfo=null;
		ModelAndView mav = new ModelAndView("/regSuccess.jsp");
		
		String resultMsg=null;		
		Register register=getFormObject(request);
		try{
			if(register.getId()==null||register.getId()==0){
				register.setId(UniqueIdUtil.genId());
				registerService.add(register);
				resultMsg=getText("record.added","cloud_user_register");
			}else{
			    registerService.update(register);
				resultMsg=getText("record.updated","cloud_user_register");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
			
			
			
			//发送邮件
			
			Properties  prop=(Properties)AppUtil.getBean("configproperties");
			//String serverUrl = prop.getProperty("serverUrl");
			String serverUrl= prop.getProperty("serverDNS");
			cloudMailService.sendSuccessfulRegMessageRegister(sysOrgInfo,register, request.getContextPath(), serverUrl);
			
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 Register 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected Register getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Register register = (Register)JSONObject.toBean(obj, Register.class);
		
		return register;
    }
	
	/**
	 * 取得cloud_user_register分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看cloud_user_register分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Register> list=registerService.getAll(new QueryFilter(request,"registerItem"));
		ModelAndView mv=this.getAutoView().addObject("registerList",list);
		
		return mv;
	}
	
	/**
	 * 删除cloud_user_register
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除cloud_user_register")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			registerService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除cloud_user_register成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑cloud_user_register
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑cloud_user_register")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		Register register=registerService.getById(id);
		
		return getAutoView().addObject("register",register).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得cloud_user_register明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看cloud_user_register明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		Register register = registerService.getById(id);	
		return getAutoView().addObject("register", register);
	}
	/**
	 * 取得cloud_user_register明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getUser")
	@Action(description="查看cloud_user_register明细")
	public ModelAndView getUser(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"userId");
		Register register = registerService.getById(id);	
		return getAutoView().addObject("register", register);
	}
}
