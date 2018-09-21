package com.hotent.platform.controller.system;

import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.util.Pingyin;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysTypeKey;
import com.hotent.platform.service.system.SysTypeKeyService;

/**
 * 对象功能:系统分类键定义 控制器类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2012-03-10 10:18:36
 */
@Controller
@RequestMapping("/platform/system/sysTypeKey/")
public class SysTypeKeyController extends BaseController
{
	@Resource
	private SysTypeKeyService sysTypeKeyService;
	
	/**
	 * 取得系统分类键定义分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看系统分类键定义分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter=new QueryFilter(request,"sysTypeKeyItem",false);
		List<SysTypeKey> list=sysTypeKeyService.getAll(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("sysTypeKeyList",list);
		
		return mv;
	}
	
	
	@RequestMapping("getPingyinByName")
	@Action(description="根据汉字获取对应的拼音")
	public void getPingyinByName(HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter out=response.getWriter();
		String typeName=RequestUtil.getString(request, "typeName");
		String str=Pingyin.getFirstSpell(typeName);
		out.print(str);
	}
	
	/**
	 * 删除系统分类键定义
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除系统分类键定义")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "typeId");
			sysTypeKeyService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除系统分类键定义成功!");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑系统分类键定义")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long typeId=RequestUtil.getLong(request,"typeId");
		String returnUrl=RequestUtil.getPrePage(request);
		SysTypeKey sysTypeKey=null;
		if(typeId!=0){
			 sysTypeKey= sysTypeKeyService.getById(typeId);
		}else{
			sysTypeKey=new SysTypeKey();
		}
		return getAutoView().addObject("sysTypeKey",sysTypeKey).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得系统分类键定义明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看系统分类键定义明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"typeId");
		SysTypeKey sysTypeKey = sysTypeKeyService.getById(id);		
		return getAutoView().addObject("sysTypeKey", sysTypeKey);
	}
	
	@RequestMapping("saveSequence")
	@Action(description="排序操作")
	public void saveSequence(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResultMessage message;
		try{
			Long[] ids=RequestUtil.getLongAryByStr(request, "ids");
			sysTypeKeyService.saveSequence(ids);
			message=new ResultMessage(ResultMessage.Success, "分类排序成功!");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "分类排序失败!");
		}
		writeResultMessage(response.getWriter(), message);
	}

}
