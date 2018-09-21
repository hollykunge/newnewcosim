package com.casic.cloud.controller.config.cloudSrc;

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

import com.casic.cloud.model.config.capability.Capability;
import com.casic.cloud.model.config.cloudSrc.CloudSrc;
import com.casic.cloud.service.compass.SearchServiceBean;
import com.casic.cloud.service.config.cloudSrc.CloudSrcService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:资源 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-07 17:41:47
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/cloudSrc/")
public class CloudSrcController extends BaseController
{
	@Resource
	private CloudSrcService cloudSrcService;
 
	@RequestMapping("search")
	@Action(description="搜索")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 
	      String name= RequestUtil.getString(request,"username");
	  	  QueryFilter queryFilter = new QueryFilter(request,"cloudSrcItem",true);
	  	 
	  	queryFilter.getFilters().put("name","%"+name+"%");
	    		  
	      List<CloudSrc> cloudSrcs = cloudSrcService.getCloudSrc("search",queryFilter);
	    
	      ModelAndView mv=this.getAutoView().addObject("cloudSrcList",cloudSrcs);
	    
		  return mv;
	     
	}
	/**
	 * 添加或更新资源。
	 * @param request
	 * @param response
	 * @param cloudSrc 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新资源")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CloudSrc cloudSrc=getFormObject(request);
		try{
			if(cloudSrc.getId()==null||cloudSrc.getId()==0){
				cloudSrc.setId(UniqueIdUtil.genId());
				cloudSrcService.add(cloudSrc);
				resultMsg=getText("record.added","资源");
			}else{
			    cloudSrcService.update(cloudSrc);
				resultMsg=getText("record.updated","资源");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 CloudSrc 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected CloudSrc getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		CloudSrc cloudSrc = (CloudSrc)JSONObject.toBean(obj, CloudSrc.class);
		
		return cloudSrc;
    }
	
	/**
	 * 取得资源分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看资源分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CloudSrc> list=cloudSrcService.getAll(new QueryFilter(request,"cloudSrcItem"));
		ModelAndView mv=this.getAutoView().addObject("cloudSrcList",list);
		
		return mv;
	}
	
	/**
	 * 删除资源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除资源")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			cloudSrcService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除资源成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑资源
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑资源")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		CloudSrc cloudSrc=cloudSrcService.getById(id);
		
		return getAutoView().addObject("cloudSrc",cloudSrc).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得资源明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看资源明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		CloudSrc cloudSrc = cloudSrcService.getById(id);	
		return getAutoView().addObject("cloudSrc", cloudSrc);
	}
	
}
