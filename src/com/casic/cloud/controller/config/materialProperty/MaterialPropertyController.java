package com.casic.cloud.controller.config.materialProperty;

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

import com.casic.cloud.model.config.materialProperty.MaterialProperty;
import com.casic.cloud.service.config.materialProperty.MaterialPropertyService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:物品属性 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 18:50:26
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/materialProperty/")
public class MaterialPropertyController extends BaseController
{
	@Resource
	private MaterialPropertyService materialPropertyService;
	
	
	/**
	 * 添加或更新物品属性。
	 * @param request
	 * @param response
	 * @param materialProperty 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新物品属性")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		MaterialProperty materialProperty=getFormObject(request);
		try{
			if(materialProperty.getId()==null||materialProperty.getId()==0){
				materialProperty.setId(UniqueIdUtil.genId());
				materialPropertyService.add(materialProperty);
				resultMsg=getText("record.added","物品属性");
			}else{
			    materialPropertyService.update(materialProperty);
				resultMsg=getText("record.updated","物品属性");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 MaterialProperty 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected MaterialProperty getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		MaterialProperty materialProperty = (MaterialProperty)JSONObject.toBean(obj, MaterialProperty.class);
		
		return materialProperty;
    }
	
	/**
	 * 取得物品属性分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看物品属性分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<MaterialProperty> list=materialPropertyService.getAll(new QueryFilter(request,"materialPropertyItem"));
		ModelAndView mv=this.getAutoView().addObject("materialPropertyList",list);
		
		return mv;
	}
	
	/**
	 * 删除物品属性
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除物品属性")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			materialPropertyService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除物品属性成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑物品属性
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑物品属性")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		MaterialProperty materialProperty=materialPropertyService.getById(id);
		
		return getAutoView().addObject("materialProperty",materialProperty).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得物品属性明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看物品属性明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		MaterialProperty materialProperty = materialPropertyService.getById(id);	
		return getAutoView().addObject("materialProperty", materialProperty);
	}
	
}
