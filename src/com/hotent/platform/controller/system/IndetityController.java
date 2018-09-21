package com.hotent.platform.controller.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.Identity;
import com.hotent.platform.service.system.IdentityService;

/**
 * 对象功能:流水号生成 控制器类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2012-02-03 14:40:59
 */
@Controller
@RequestMapping("/platform/system/indetity/")
public class IndetityController extends BaseController
{
	@Resource
	private IdentityService indetityService;
	
	/**
	 * 取得流水号生成分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看流水号生成分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Identity> list=indetityService.getAll(new QueryFilter(request,"indetityItem"));
		ModelAndView mv=this.getAutoView().addObject("indetityList",list);
		
		return mv;
	}
	
	/**
	 * 删除流水号生成
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除流水号生成")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			indetityService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除流水号生成成功!");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑流水号生成")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		Identity identity=null;
		if(id!=0){
			 identity= indetityService.getById(id);
		}else{
			identity=new Identity();
		}
		return getAutoView().addObject("indetity",identity).addObject("returnUrl", returnUrl);
	}
	/**
	 * 获取所有的流水号
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllIndetity")
	@ResponseBody
	public List<Identity> getAllIndetity(HttpServletRequest request) throws Exception
	{
		return indetityService.getAll();
	}

	/**
	 * 取得流水号生成明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看流水号生成明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		Identity identity = indetityService.getById(id);		
		return getAutoView().addObject("indetity", identity);
	}

}
