package com.hotent.platform.controller.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.Seal;
import com.hotent.platform.service.system.SealService;
import com.hotent.platform.service.system.SysFileService;
import com.hotent.core.web.ResultMessage;

/**
 * 对象功能:电子印章 控制器类
 * 开发公司:
 * 开发人员:raise
 * 创建时间:2012-08-29 11:26:00
 */
@Controller
@RequestMapping("/platform/system/seal/")
public class SealController extends BaseController
{
	@Resource
	private SealService sealService;
	
	
	@Resource 
	private SysFileService sysFileService;
	
	/**
	 * 取得电子印章分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看电子印章分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Seal> list=sealService.getAll(new QueryFilter(request,"sealItem"));
		ModelAndView mv=this.getAutoView().addObject("sealList",list);
		
		return mv;
	}
	
	/**
	 * 选择一个签章，进行盖章
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dialog")
	@Action(description="选择一个签章，进行盖章")
	public ModelAndView dialog(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ISysUser currentUser= ContextUtil.getCurrentUser();
		ModelAndView mv=this.getAutoView();
		mv.addObject("user", currentUser);
		return mv;
	}
	
	/**
	 * 与doalog页面一起使用。通过Iframe，在Dialog页面中显示印章，供选择。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selector")
	@Action(description="签章选择器")
	public ModelAndView selector(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Seal> list=sealService.getAll(new QueryFilter(request,"sealItem"));
		ModelAndView mv=this.getAutoView().addObject("sealList",list);
		
		return mv;
	}
	
	/**
	 * 删除电子印章
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除电子印章")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "sealId");
			sealService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除电子印章成功!");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑电子印章")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long sealId=RequestUtil.getLong(request,"sealId");
		String returnUrl=RequestUtil.getPrePage(request);
		Seal seal=null;
		if(sealId!=0){
			 seal= sealService.getById(sealId);
		}else{
			seal=new Seal();
		}
		return getAutoView().addObject("seal",seal).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得电子印章明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看电子印章明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"sealId");
		Seal seal = sealService.getById(id);		
		return getAutoView().addObject("seal", seal);
	}

}
