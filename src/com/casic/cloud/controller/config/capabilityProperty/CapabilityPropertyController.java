package com.casic.cloud.controller.config.capabilityProperty;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.casic.cloud.model.config.capabilityProperty.CapabilityProperty;
import com.casic.cloud.service.config.capabilityProperty.CapabilityPropertyService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:能力属性 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 16:10:48
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/capabilityProperty/")
public class CapabilityPropertyController extends BaseController
{
	@Resource
	private CapabilityPropertyService capabilityPropertyService;
	
	
	/**
	 * 添加或更新能力属性。
	 * @param request
	 * @param response
	 * @param capabilityProperty 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新能力属性")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CapabilityProperty capabilityProperty=getFormObject(request);
		try{
			if(capabilityProperty.getId()==null||capabilityProperty.getId()==0){
				capabilityProperty.setId(UniqueIdUtil.genId());
				capabilityPropertyService.add(capabilityProperty);
				resultMsg=getText("record.added","能力属性");
			}else{
			    capabilityPropertyService.update(capabilityProperty);
				resultMsg=getText("record.updated","能力属性");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 CapabilityProperty 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected CapabilityProperty getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		CapabilityProperty capabilityProperty = (CapabilityProperty)JSONObject.toBean(obj, CapabilityProperty.class);
		
		return capabilityProperty;
    }
	
	/**
	 * 取得能力属性分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看能力属性分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CapabilityProperty> list=capabilityPropertyService.getAll(new QueryFilter(request,"capabilityPropertyItem"));
		ModelAndView mv=this.getAutoView().addObject("capabilityPropertyList",list);
		
		return mv;
	}
	
	/**
	 * 删除能力属性
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除能力属性")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			capabilityPropertyService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除能力属性成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑能力属性
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑能力属性")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		CapabilityProperty capabilityProperty=capabilityPropertyService.getById(id);
		
		return getAutoView().addObject("capabilityProperty",capabilityProperty).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得能力属性明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看能力属性明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		CapabilityProperty capabilityProperty = capabilityPropertyService.getById(id);	
		return getAutoView().addObject("capabilityProperty", capabilityProperty);
	}
	
}
