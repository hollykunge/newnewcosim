package com.casic.cloud.controller.config.materialclass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.casic.cloud.model.config.materialclass.MaterialClass;
import com.casic.cloud.service.config.materialclass.MaterialClassService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.system.Dictionary;
/**
 *<pre>
 * 对象功能:CLOUD_MATERIAL_CLASS 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 10:56:35
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/materialclass/")
public class MaterialClassController extends BaseController
{
	@Resource
	private MaterialClassService materialClassService;
	
	
	/**
	 * 添加或更新CLOUD_MATERIAL_CLASS。
	 * @param request
	 * @param response
	 * @param materialClass 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新CLOUD_MATERIAL_CLASS")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		MaterialClass materialClass=getFormObject(request);
		try{
			if(materialClass.getId()==null||materialClass.getId()==0){
				materialClass.setId(UniqueIdUtil.genId());
				materialClassService.add(materialClass);
				resultMsg=getText("record.added","CLOUD_MATERIAL_CLASS");
			}else{
			    materialClassService.update(materialClass);
				resultMsg=getText("record.updated","CLOUD_MATERIAL_CLASS");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 MaterialClass 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected MaterialClass getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		MaterialClass materialClass = (MaterialClass)JSONObject.toBean(obj, MaterialClass.class);
		
		return materialClass;
    }
	
	/**
	 * 取得CLOUD_MATERIAL_CLASS分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看CLOUD_MATERIAL_CLASS分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<MaterialClass> list=materialClassService.getAll(new QueryFilter(request,"materialClassItem"));
		ModelAndView mv=this.getAutoView().addObject("materialClassList",list);
		
		return mv;
	}
	
	/**
	 * 删除CLOUD_MATERIAL_CLASS
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除CLOUD_MATERIAL_CLASS")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			materialClassService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除CLOUD_MATERIAL_CLASS成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑CLOUD_MATERIAL_CLASS
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑CLOUD_MATERIAL_CLASS")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		MaterialClass materialClass=materialClassService.getById(id);
		
		return getAutoView().addObject("materialClass",materialClass).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得CLOUD_MATERIAL_CLASS明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看CLOUD_MATERIAL_CLASS明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		MaterialClass materialClass = materialClassService.getById(id);	
		return getAutoView().addObject("materialClass", materialClass);
	}
	
	/**
	 * 根据当前层级ID获取下级目录
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getClasses")
	@Action(description="查询物品分类")
	@ResponseBody
	public Map<String,Object> getClasses(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Map<String, Object> map=new HashMap<String, Object>();
		String id = RequestUtil.getString(request, "id");
		Map<String,Object> m=new HashMap<String,Object>();
		
			m.put("id", id);
		
		
	    	
//		QueryFilter queryFilter = new QueryFilter(request,"");
//		queryFilter.getFilters().put("id", id);
			
		List<MaterialClass> list =  materialClassService.getChildren("getChildren_mc",m);
		JSON.toJSONString(list);
		
		map.put("list", list);
		return map;
	}



}
