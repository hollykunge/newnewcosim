package com.casic.cloud.controller.config.capabilityMaterial;

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

import com.casic.cloud.model.config.capabilityMaterial.CapabilityMaterial;
import com.casic.cloud.service.config.capabilityMaterial.CapabilityMaterialService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:cloud_capability_material 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 18:13:05
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/capabilityMaterial/")
public class CapabilityMaterialController extends BaseController
{
	@Resource
	private CapabilityMaterialService capabilityMaterialService;
	
	
	/**
	 * 添加或更新cloud_capability_material。
	 * @param request
	 * @param response
	 * @param capabilityMaterial 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新cloud_capability_material")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CapabilityMaterial capabilityMaterial=getFormObject(request);
		try{
			if(capabilityMaterial.getId()==null||capabilityMaterial.getId()==0){
				capabilityMaterial.setId(UniqueIdUtil.genId());
				capabilityMaterialService.add(capabilityMaterial);
				resultMsg=getText("record.added","cloud_capability_material");
			}else{
			    capabilityMaterialService.update(capabilityMaterial);
				resultMsg=getText("record.updated","cloud_capability_material");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 CapabilityMaterial 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected CapabilityMaterial getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		CapabilityMaterial capabilityMaterial = (CapabilityMaterial)JSONObject.toBean(obj, CapabilityMaterial.class);
		
		return capabilityMaterial;
    }
	
	/**
	 * 取得cloud_capability_material分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看cloud_capability_material分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CapabilityMaterial> list=capabilityMaterialService.getAll(new QueryFilter(request,"capabilityMaterialItem"));
		ModelAndView mv=this.getAutoView().addObject("capabilityMaterialList",list);
		
		return mv;
	}
	
	/**
	 * 删除cloud_capability_material
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除cloud_capability_material")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			capabilityMaterialService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除cloud_capability_material成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑cloud_capability_material
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑cloud_capability_material")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		CapabilityMaterial capabilityMaterial=capabilityMaterialService.getById(id);
		
		return getAutoView().addObject("capabilityMaterial",capabilityMaterial).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得cloud_capability_material明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看cloud_capability_material明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		CapabilityMaterial capabilityMaterial = capabilityMaterialService.getById(id);	
		return getAutoView().addObject("capabilityMaterial", capabilityMaterial);
	}
	
}
