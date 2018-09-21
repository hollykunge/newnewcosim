package com.casic.cloud.controller.config.capabilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.alibaba.fastjson.JSON;
import com.casic.cloud.model.config.capabilityClass.CapabilityClass;
import com.casic.cloud.service.config.business.BusinessChanceService;
import com.casic.cloud.service.config.capabilityClass.CapabilityClassService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:cloud_capability_class 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:17:15
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/capabilityClass/")
public class CapabilityClassController extends BaseController
{
	@Resource
	private CapabilityClassService capabilityClassService;
	
	@Resource
	private BusinessChanceService businessChanceService;
	/**
	 * 添加或更新cloud_capability_class。
	 * @param request
	 * @param response
	 * @param capabilityClass 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新cloud_capability_class")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CapabilityClass capabilityClass=getFormObject(request);
		try{
			if(capabilityClass.getId()==null||capabilityClass.getId()==0){
				capabilityClass.setId(UniqueIdUtil.genId());
				capabilityClassService.add(capabilityClass);
				resultMsg=getText("record.added","cloud_capability_class");
			}else{
			    capabilityClassService.update(capabilityClass);
				resultMsg=getText("record.updated","cloud_capability_class");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 CapabilityClass 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected CapabilityClass getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		CapabilityClass capabilityClass = (CapabilityClass)JSONObject.toBean(obj, CapabilityClass.class);
		
		return capabilityClass;
    }
	
	/**
	 * 取得cloud_capability_class分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看cloud_capability_class分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CapabilityClass> list=capabilityClassService.getAll(new QueryFilter(request,"capabilityClassItem"));
		ModelAndView mv=this.getAutoView().addObject("capabilityClassList",list);
		
		return mv;
	}
	
	/**
	 * 能力列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listClasses")
	@Action(description="能力列表")
	public ModelAndView listClasses(HttpServletRequest request,HttpServletResponse response) throws Exception
	{		String returnUrl=RequestUtil.getPrePage(request);
	    Map<String,Object> m1=new HashMap<String,Object>();
	    Map<String,Object> m2=new HashMap<String,Object>();
	    Map<String,Object> m3=new HashMap<String,Object>();
	    m1.put("levels", 1);
	    m2.put("levels", 2);
		m3.put("levels", 3);
		List<CapabilityClass> classes1 =  capabilityClassService.getClasses(m1);
		List<CapabilityClass> classes2 =  capabilityClassService.getClasses(m2);
		List<CapabilityClass> classes3 =  capabilityClassService.getClasses(m3);
				 
		return getAutoView().addObject("classes1", classes1).addObject("classes2", classes2).addObject("classes3", classes3).addObject("returnUrl", returnUrl);
	}
	
	/**
	 * 删除cloud_capability_class
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除cloud_capability_class")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			capabilityClassService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除cloud_capability_class成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑cloud_capability_class
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑cloud_capability_class")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		CapabilityClass capabilityClass=capabilityClassService.getById(id);
		
		return getAutoView().addObject("capabilityClass",capabilityClass).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得cloud_capability_class明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看cloud_capability_class明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		CapabilityClass capabilityClass = capabilityClassService.getById(id);	
		return getAutoView().addObject("capabilityClass", capabilityClass);
	}
	
	
 
	
	
	
	/**
	 * 根据当前层级ID获取下级目录
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCatalog")
	@Action(description="查询能力分类")
	@ResponseBody
	public Map<String,Object> getCatalog(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Map<String, Object> map=new HashMap<String, Object>();
		String id = RequestUtil.getString(request, "id");
		 Map<String,Object> m=new HashMap<String,Object>();
		    m.put("id", id);
		 
		List<CapabilityClass> list =  capabilityClassService.getChildren(m);
		JSON.toJSONString(list);
		
		map.put("list", list);
		return map;
	}
	
}
