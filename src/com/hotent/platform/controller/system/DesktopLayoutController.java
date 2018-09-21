package com.hotent.platform.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hotent.platform.model.system.DesktopLayout;
import com.hotent.platform.service.system.DesktopLayoutService;

/**
 * 对象功能:桌面布局 控制器类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2012-03-20 16:39:01
 */
@Controller
@RequestMapping("/platform/system/desktopLayout/")
public class DesktopLayoutController extends BaseController
{
	@Resource
	private DesktopLayoutService desktopLayoutService;
	/**
	 * 取得桌面布局分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看桌面布局分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<DesktopLayout> list=desktopLayoutService.getAll(new QueryFilter(request,"desktopLayoutItem"));
		ModelAndView mv=this.getAutoView().addObject("desktopLayoutList",list);
		return mv;
	}
	
	/**
	 * 删除桌面布局
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除桌面布局")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			desktopLayoutService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除桌面布局成功!");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑桌面布局
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑桌面布局")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		ModelAndView mv=getAutoView();
		DesktopLayout desktopLayout=null;
		if(id!=0){
			desktopLayout= desktopLayoutService.getById(id);
			String[] aryWidth=desktopLayout.getWidth().split(",");
			mv.addObject("aryWidth",aryWidth);
		}else{
			desktopLayout=new DesktopLayout();
		}
		return mv.addObject("desktopLayout",desktopLayout).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得桌面布局明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看桌面布局明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		DesktopLayout desktopLayout = desktopLayoutService.getById(id);		
		return getAutoView().addObject("desktopLayout", desktopLayout);
	}
	
	/**
	 * 桌面浏览
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("show")
	@Action(description = "桌面浏览")
	public ModelAndView news(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		DesktopLayout bean = desktopLayoutService.getById(id);
		Map<String,String> desktopLayoutmap=new HashMap<String,String>();
		desktopLayoutmap.put("cols",""+bean.getCols());
		desktopLayoutmap.put("widths", bean.getWidth());
		desktopLayoutmap.put("id",""+id);
		return getAutoView().addObject("desktopLayoutmap", desktopLayoutmap);
	}
	
	/**
	 * 更改默认桌面布局;
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("setDefault")
	@Action(description="设置默认桌面布局")
	public void setupDefault(HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		long id=RequestUtil.getLong(request, "id");
		try{
			desktopLayoutService.setDefault(id);
			message=new ResultMessage(ResultMessage.Success, "设置成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "设置失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}	
}
