package com.casic.cloud.controller.console.cloudMessage;

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

import com.casic.cloud.model.console.cloudMessage.CloudMessage;
import com.casic.cloud.service.console.cloudMessage.CloudMessageService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:CLOUD_MESSAGE 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-19 13:32:09
 *</pre>
 */
@Controller
@RequestMapping("/cloud/console/cloudMessage/")
public class CloudMessageController extends BaseController
{
	@Resource
	private CloudMessageService cloudMessageService;
	
	
	/**
	 * 添加或更新CLOUD_MESSAGE。
	 * @param request
	 * @param response
	 * @param cloudMessage 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新CLOUD_MESSAGE")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CloudMessage cloudMessage=getFormObject(request);
		try{
			if(cloudMessage.getId()==null||cloudMessage.getId()==0){
				cloudMessage.setId(UniqueIdUtil.genId());
				cloudMessageService.add(cloudMessage);
				resultMsg=getText("record.added","CLOUD_MESSAGE");
			}else{
			    cloudMessageService.update(cloudMessage);
				resultMsg=getText("record.updated","CLOUD_MESSAGE");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 CloudMessage 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected CloudMessage getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		CloudMessage cloudMessage = (CloudMessage)JSONObject.toBean(obj, CloudMessage.class);
		
		return cloudMessage;
    }
	
	/**
	 * 取得CLOUD_MESSAGE分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看CLOUD_MESSAGE分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CloudMessage> list=cloudMessageService.getAll(new QueryFilter(request,"cloudMessageItem"));
		ModelAndView mv=this.getAutoView().addObject("cloudMessageList",list);
		
		return mv;
	}
	
	/**
	 * 删除CLOUD_MESSAGE
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除CLOUD_MESSAGE")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			cloudMessageService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除CLOUD_MESSAGE成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑CLOUD_MESSAGE
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑CLOUD_MESSAGE")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		CloudMessage cloudMessage=cloudMessageService.getById(id);
		
		return getAutoView().addObject("cloudMessage",cloudMessage).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得CLOUD_MESSAGE明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看CLOUD_MESSAGE明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		CloudMessage cloudMessage = cloudMessageService.getById(id);	
		return getAutoView().addObject("cloudMessage", cloudMessage);
	}
	
}
