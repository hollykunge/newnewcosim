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
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.service.system.DemensionService;

/**
 * 对象功能:SYS_DEMENSION 控制器类
 * 开发公司:
 * 开发人员:pkq
 * 创建时间:2011-12-01 14:23:26
 */
@Controller
@RequestMapping("/platform/system/demension/")
public class DemensionController extends BaseController
{
	@Resource
	private DemensionService demensionService;
	
	/**
	 * 取得SYS_DEMENSION分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看SYS_DEMENSION分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Demension> list=demensionService.getDemenByQuery(new QueryFilter(request,"demensionItem"));
		ModelAndView mv=this.getAutoView().addObject("demensionList",list);
		
		return mv;
	}
	
	/**
	 * 删除SYS_DEMENSION
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除SYS_DEMENSION")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultMessage message=null;
		String preUrl= RequestUtil.getPrePage(request);
		try {
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "demId");
			demensionService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除维度成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.Fail,"删除维度失败");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑SYS_DEMENSION")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long demId=RequestUtil.getLong(request,"demId");
		Demension demension=null;
		if(demId!=0){
			 demension= demensionService.getById(demId);
		}else{
			demension=new Demension();
		}
		return getAutoView().addObject("demension",demension)
				.addObject("demId", demId);
	}

	/**
	 * 取得SYS_DEMENSION明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看SYS_DEMENSION明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"demId");
		Demension demension = demensionService.getById(id);		
		return getAutoView().addObject("demension", demension);
	}

}
