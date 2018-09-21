package com.hotent.platform.controller.biz;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.hotent.platform.model.biz.BizSegProcLink;
import com.hotent.platform.service.biz.BizSegProcLinkService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:BIZ_SEG_PROC_LINK 控制器类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 *</pre>
 */
@Controller
@RequestMapping("/platform/biz/bizSegProcLink/")
public class BizSegProcLinkController extends BaseController
{
	@Resource
	private BizSegProcLinkService bizSegProcLinkService;
	
	
	/**
	 * 添加或更新BIZ_SEG_PROC_LINK。
	 * @param request
	 * @param response
	 * @param bizSegProcLink 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新BIZ_SEG_PROC_LINK")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		BizSegProcLink bizSegProcLink=getFormObject(request);
		try{
			if(bizSegProcLink.getBizSegProLinkId()==null||bizSegProcLink.getBizSegProLinkId()==0){
				bizSegProcLink.setBizSegProLinkId(UniqueIdUtil.genId());
				bizSegProcLinkService.add(bizSegProcLink);
				resultMsg=getText("record.added","BIZ_SEG_PROC_LINK");
			}else{
			    bizSegProcLinkService.update(bizSegProcLink);
				resultMsg=getText("record.updated","BIZ_SEG_PROC_LINK");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 BizSegProcLink 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected BizSegProcLink getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		BizSegProcLink bizSegProcLink = (BizSegProcLink)JSONObject.toBean(obj, BizSegProcLink.class);
		
		return bizSegProcLink;
    }
	
	/**
	 * 取得BIZ_SEG_PROC_LINK分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看BIZ_SEG_PROC_LINK分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<BizSegProcLink> list=bizSegProcLinkService.getAll(new QueryFilter(request,"bizSegProcLinkItem"));
		ModelAndView mv=this.getAutoView().addObject("bizSegProcLinkList",list);
		
		return mv;
	}
	
	/**
	 * 删除BIZ_SEG_PROC_LINK
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除BIZ_SEG_PROC_LINK")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "bizSegProLinkId");
			bizSegProcLinkService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除BIZ_SEG_PROC_LINK成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑BIZ_SEG_PROC_LINK
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑BIZ_SEG_PROC_LINK")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long bizSegProLinkId=RequestUtil.getLong(request,"bizSegProLinkId");
		String returnUrl=RequestUtil.getPrePage(request);
		BizSegProcLink bizSegProcLink=bizSegProcLinkService.getById(bizSegProLinkId);
		
		return getAutoView().addObject("bizSegProcLink",bizSegProcLink).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得BIZ_SEG_PROC_LINK明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看BIZ_SEG_PROC_LINK明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long bizSegProLinkId=RequestUtil.getLong(request,"bizSegProLinkId");
		BizSegProcLink bizSegProcLink = bizSegProcLinkService.getById(bizSegProLinkId);	
		return getAutoView().addObject("bizSegProcLink", bizSegProcLink);
	}
	
}
