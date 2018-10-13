package com.casic.cloud.controller.cloudUseRes;

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

import com.casic.cloud.model.cloudUseRes.CloudUseRes;
import com.casic.cloud.service.cloudUseRes.CloudUseResService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:cloud_use_res 控制器类
 * 开发公司:tianzhi
 * 开发人员:hollykunge
 * 创建时间:2013-05-16 17:40:22
 *</pre>
 */
@Controller
@RequestMapping("/platform/cloudUseRes/cloudUseRes/")
public class CloudUseResController extends BaseController
{
	@Resource
	private CloudUseResService cloudUseResService;
	
	
	/**
	 * 添加或更新cloud_use_res。
	 * @param request
	 * @param response
	 * @param cloudUseRes 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新cloud_use_res")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CloudUseRes cloudUseRes=getFormObject(request);
		try{
			if(cloudUseRes.getId()==null||cloudUseRes.getId()==0){
				cloudUseRes.setId(UniqueIdUtil.genId());
				cloudUseResService.add(cloudUseRes);
				resultMsg=getText("record.added","cloud_use_res");
			}else{
			    cloudUseResService.update(cloudUseRes);
				resultMsg=getText("record.updated","cloud_use_res");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 CloudUseRes 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected CloudUseRes getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		CloudUseRes cloudUseRes = (CloudUseRes)JSONObject.toBean(obj, CloudUseRes.class);
		
		return cloudUseRes;
    }
	
	/**
	 * 取得cloud_use_res分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看cloud_use_res分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CloudUseRes> list=cloudUseResService.getAll(new QueryFilter(request,"cloudUseResItem"));
		ModelAndView mv=this.getAutoView().addObject("cloudUseResList",list);
		
		return mv;
	}
	
	/**
	 * 删除cloud_use_res
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除cloud_use_res")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			cloudUseResService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除cloud_use_res成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑cloud_use_res
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑cloud_use_res")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		CloudUseRes cloudUseRes=cloudUseResService.getById(id);
		
		return getAutoView().addObject("cloudUseRes",cloudUseRes).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得cloud_use_res明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看cloud_use_res明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		CloudUseRes cloudUseRes = cloudUseResService.getById(id);	
		return getAutoView().addObject("cloudUseRes", cloudUseRes);
	}
	
}
