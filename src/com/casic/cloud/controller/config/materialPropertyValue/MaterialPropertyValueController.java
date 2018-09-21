package com.casic.cloud.controller.config.materialPropertyValue;

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

import com.casic.cloud.model.config.materialPropertyValue.MaterialPropertyValue;
import com.casic.cloud.service.config.materialPropertyValue.MaterialPropertyValueService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:cloud_material_property_value 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 18:32:24
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/materialPropertyValue/")
public class MaterialPropertyValueController extends BaseController
{
	@Resource
	private MaterialPropertyValueService materialPropertyValueService;
	
	
	/**
	 * 添加或更新cloud_material_property_value。
	 * @param request
	 * @param response
	 * @param materialPropertyValue 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新cloud_material_property_value")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		MaterialPropertyValue materialPropertyValue=getFormObject(request);
		try{
			if(materialPropertyValue.getId()==null||materialPropertyValue.getId()==0){
				materialPropertyValue.setId(UniqueIdUtil.genId());
				materialPropertyValueService.add(materialPropertyValue);
				resultMsg=getText("record.added","cloud_material_property_value");
			}else{
			    materialPropertyValueService.update(materialPropertyValue);
				resultMsg=getText("record.updated","cloud_material_property_value");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 MaterialPropertyValue 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected MaterialPropertyValue getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		MaterialPropertyValue materialPropertyValue = (MaterialPropertyValue)JSONObject.toBean(obj, MaterialPropertyValue.class);
		
		return materialPropertyValue;
    }
	
	/**
	 * 取得cloud_material_property_value分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看cloud_material_property_value分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<MaterialPropertyValue> list=materialPropertyValueService.getAll(new QueryFilter(request,"materialPropertyValueItem"));
		ModelAndView mv=this.getAutoView().addObject("materialPropertyValueList",list);
		
		return mv;
	}
	
	/**
	 * 删除cloud_material_property_value
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除cloud_material_property_value")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			materialPropertyValueService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除cloud_material_property_value成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑cloud_material_property_value
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑cloud_material_property_value")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		MaterialPropertyValue materialPropertyValue=materialPropertyValueService.getById(id);
		
		return getAutoView().addObject("materialPropertyValue",materialPropertyValue).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得cloud_material_property_value明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看cloud_material_property_value明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		MaterialPropertyValue materialPropertyValue = materialPropertyValueService.getById(id);	
		return getAutoView().addObject("materialPropertyValue", materialPropertyValue);
	}
	
}
