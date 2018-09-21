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

import com.hotent.platform.model.biz.BizInstanceSegment;
import com.hotent.platform.service.biz.BizInstanceSegmentService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:业务环节实例 控制器类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 *</pre>
 */
@Controller
@RequestMapping("/platform/biz/bizInstanceSegment/")
public class BizInstanceSegmentController extends BaseController
{
	@Resource
	private BizInstanceSegmentService bizInstanceSegmentService;
	
	
	/**
	 * 添加或更新业务环节实例。
	 * @param request
	 * @param response
	 * @param bizInstanceSegment 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新业务环节实例")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		BizInstanceSegment bizInstanceSegment=getFormObject(request);
		try{
			if(bizInstanceSegment.getBizInstanceSegmentId()==null||bizInstanceSegment.getBizInstanceSegmentId()==0){
				bizInstanceSegment.setBizInstanceSegmentId(UniqueIdUtil.genId());
				bizInstanceSegmentService.add(bizInstanceSegment);
				resultMsg=getText("record.added","业务环节实例");
			}else{
			    bizInstanceSegmentService.update(bizInstanceSegment);
				resultMsg=getText("record.updated","业务环节实例");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 BizInstanceSegment 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected BizInstanceSegment getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		BizInstanceSegment bizInstanceSegment = (BizInstanceSegment)JSONObject.toBean(obj, BizInstanceSegment.class);
		
		return bizInstanceSegment;
    }
	
	/**
	 * 取得业务环节实例分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看业务环节实例分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<BizInstanceSegment> list=bizInstanceSegmentService.getAll(new QueryFilter(request,"bizInstanceSegmentItem"));
		ModelAndView mv=this.getAutoView().addObject("bizInstanceSegmentList",list);
		
		return mv;
	}
	
	/**
	 * 删除业务环节实例
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除业务环节实例")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "bizInstanceSegmentId");
			bizInstanceSegmentService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除业务环节实例成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑业务环节实例
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑业务环节实例")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long bizInstanceSegmentId=RequestUtil.getLong(request,"bizInstanceSegmentId");
		String returnUrl=RequestUtil.getPrePage(request);
		BizInstanceSegment bizInstanceSegment=bizInstanceSegmentService.getById(bizInstanceSegmentId);
		
		return getAutoView().addObject("bizInstanceSegment",bizInstanceSegment).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得业务环节实例明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看业务环节实例明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long bizInstanceSegmentId=RequestUtil.getLong(request,"bizInstanceSegmentId");
		BizInstanceSegment bizInstanceSegment = bizInstanceSegmentService.getById(bizInstanceSegmentId);	
		return getAutoView().addObject("bizInstanceSegment", bizInstanceSegment);
	}
	
}
