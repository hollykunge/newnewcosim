package com.casic.cloud.controller.cloudEnterpriseVisited;

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

import com.casic.cloud.model.cloudEnterpriseVisited.CloudEnterpriseVisited;
import com.casic.cloud.service.cloudEnterpriseVisited.CloudEnterpriseVisitedService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:cloud_enterprise_visited 控制器类
 * 开发公司:tianzhi
 * 开发人员:xingchi
 * 创建时间:2013-05-03 10:34:39
 *</pre>
 */
@Controller
@RequestMapping("/cloud/cloudEnterpriseVisited/cloudEnterpriseVisited/")
public class CloudEnterpriseVisitedController extends BaseController
{
	@Resource
	private CloudEnterpriseVisitedService cloudEnterpriseVisitedService;
	
	
	/**
	 * 添加或更新cloud_enterprise_visited。
	 * @param request
	 * @param response
	 * @param cloudEnterpriseVisited 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新cloud_enterprise_visited")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CloudEnterpriseVisited cloudEnterpriseVisited=getFormObject(request);
		try{
			if(cloudEnterpriseVisited.getId()==null||cloudEnterpriseVisited.getId()==0){
				cloudEnterpriseVisited.setId(UniqueIdUtil.genId());
				cloudEnterpriseVisitedService.add(cloudEnterpriseVisited);
				resultMsg=getText("record.added","cloud_enterprise_visited");
			}else{
			    cloudEnterpriseVisitedService.update(cloudEnterpriseVisited);
				resultMsg=getText("record.updated","cloud_enterprise_visited");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 CloudEnterpriseVisited 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected CloudEnterpriseVisited getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		CloudEnterpriseVisited cloudEnterpriseVisited = (CloudEnterpriseVisited)JSONObject.toBean(obj, CloudEnterpriseVisited.class);
		
		return cloudEnterpriseVisited;
    }
	
	/**
	 * 取得cloud_enterprise_visited分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看cloud_enterprise_visited分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CloudEnterpriseVisited> list=cloudEnterpriseVisitedService.getAll(new QueryFilter(request,"cloudEnterpriseVisitedItem"));
		ModelAndView mv=this.getAutoView().addObject("cloudEnterpriseVisitedList",list);
		
		return mv;
	}
	
	/**
	 * 删除cloud_enterprise_visited
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除cloud_enterprise_visited")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			cloudEnterpriseVisitedService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除cloud_enterprise_visited成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑cloud_enterprise_visited
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑cloud_enterprise_visited")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		CloudEnterpriseVisited cloudEnterpriseVisited=cloudEnterpriseVisitedService.getById(id);
		
		return getAutoView().addObject("cloudEnterpriseVisited",cloudEnterpriseVisited).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得cloud_enterprise_visited明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看cloud_enterprise_visited明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		CloudEnterpriseVisited cloudEnterpriseVisited = cloudEnterpriseVisitedService.getById(id);	
		return getAutoView().addObject("cloudEnterpriseVisited", cloudEnterpriseVisited);
	}
	
}
