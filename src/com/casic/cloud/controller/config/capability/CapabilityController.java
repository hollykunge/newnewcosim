package com.casic.cloud.controller.config.capability;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hdf.extractor.data.LVL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fr.report.core.A.e;
import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.page.PageBean;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.casic.cloud.model.compass.compassNews.CompassNews;
import com.casic.cloud.model.config.capability.Capability;
import com.casic.cloud.model.config.capabilityClass.CapabilityClass;
import com.casic.cloud.model.config.capabilityMaterial.CapabilityMaterial;
import com.casic.cloud.model.config.capabilityProperty.CapabilityProperty;
import com.casic.cloud.model.config.capabilityPropertyValue.CapabilityPropertyValue;
import com.casic.cloud.model.config.material.Material;
import com.casic.cloud.service.compass.SearchServiceBean;

import com.casic.cloud.service.config.capability.CapabilityService;
import com.casic.cloud.service.config.capabilityClass.CapabilityClassService;
import com.casic.cloud.service.config.capabilityMaterial.CapabilityMaterialService;
import com.casic.cloud.service.config.capabilityProperty.CapabilityPropertyService;
import com.casic.cloud.service.config.capabilityPropertyValue.CapabilityPropertyValueService;
import com.casic.cloud.service.config.material.MaterialService;
import com.hotent.core.web.ResultMessage;
import com.ibm.db2.jcc.am.le;
/**
 *<pre>
 * 对象功能:能力 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:23:24
 *</pre>
 */
//@Controller
//@RequestMapping("/cloud/config/capability/")
public class CapabilityController extends BaseController
{
	@Resource
	private CapabilityService capabilityService;
	@Resource
	private CapabilityPropertyService capabilityPropertyService;
	
	@Resource
	private CapabilityPropertyValueService capabilityPropertyValueService;
	
	@Resource
	private CapabilityMaterialService capabilityMaterialService;
	
	@Resource
	private MaterialService materialService;
	@Resource
	private CapabilityClassService capabilityClassService;
	
	
	@Resource
	private SearchServiceBean<Capability> searchService;
	

	/**
	 * 添加或更新能力。
	 * @param request
	 * @param response
	 * @param capability 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新能力")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		
		
		Capability capability=getFormObject(request);
		try{
			if(capability.getId()==null||capability.getId()==0){
				capability.setId(UniqueIdUtil.genId());
				capability.setPublishDate(new Date());
				capability.setEntId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
				capability.setEntName(ContextUtil.getCurrentOrgInfoFromSession().getName());
				capability.setPublisher(ContextUtil.getCurrentUser().getFullname());
				 
				capabilityService.add(capability);
				Long typeId=RequestUtil.getLong(request,"typeId");
				CapabilityClass capabilityClass=capabilityClassService.getById(typeId);
				 
				capabilityClass.setIsused("yes");
				capabilityClassService.update(capabilityClass);
				
			 
				
				 Map<String,Object> m=new HashMap<String,Object>();
				    m.put("capability_class_id", typeId);
				List<CapabilityProperty> properties =capabilityPropertyService.getCapabilityProperty(m);
				
				for(CapabilityProperty property : properties){
					
					String propertyName = "property" + property.getId();
					String propertyValue = RequestUtil.getString(request,propertyName);
					CapabilityPropertyValue mpv = new CapabilityPropertyValue();
					mpv.setId(UniqueIdUtil.genId());
					mpv.setCapabilityId(capability.getId());
					mpv.setPropertyId(property.getId());
					mpv.setPropertyValue(propertyValue);
					mpv.setPropertyName(property.getPropertyName());
					capabilityPropertyValueService.add(mpv);
				}
				
				 
				Long[] materiel_ids =RequestUtil.getLongAry(request, "materiel_ids");
				if (materiel_ids != null) {
					
					for (int i = 0; i < materiel_ids.length; i++) {
						CapabilityMaterial capabilityMaterial=new CapabilityMaterial();
						capabilityMaterial.setId(UniqueIdUtil.genId());
						capabilityMaterial.setMaterialId(materiel_ids[i]);
						capabilityMaterial.setCapabilityId(capability.getId());
						capabilityMaterialService.add(capabilityMaterial);
						
					}
					}
				
				
				
				
				resultMsg=getText("record.added","能力");
			}else{
				capability.setPublishDate(new Date());
				capability.setEntId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
				capability.setEntName(ContextUtil.getCurrentOrgInfoFromSession().getName());
				capability.setPublisher(ContextUtil.getCurrentUser().getFullname());
			    capabilityService.update(capability);
			    
			    
			    Long typeId = capability.getTypeId();
			    
			    CapabilityClass capabilityClass=capabilityClassService.getById(typeId);
				 
				capabilityClass.setIsused("yes");
				capabilityClassService.update(capabilityClass);
				
				capabilityService.delByCid(capability.getId());
				
				 Map<String,Object> m=new HashMap<String,Object>();
				    m.put("capability_class_id", typeId);
				List<CapabilityProperty> properties =capabilityPropertyService.getCapabilityProperty(m);
				
				for(CapabilityProperty property : properties){
					
					String propertyName = "property" + property.getId();
					String propertyValue = RequestUtil.getString(request,propertyName);
					CapabilityPropertyValue mpv = new CapabilityPropertyValue();
					mpv.setId(UniqueIdUtil.genId());
					mpv.setCapabilityId(capability.getId());
					mpv.setPropertyId(property.getId());
					mpv.setPropertyValue(propertyValue);
					mpv.setPropertyName(property.getPropertyName());
					capabilityPropertyValueService.add(mpv);
				}
				
				capabilityService.delByMid(capability.getId());
				
				Long[] materiel_ids =RequestUtil.getLongAry(request, "materiel_ids");
				if (materiel_ids != null) {
					
					for (int i = 0; i < materiel_ids.length; i++) {
						CapabilityMaterial capabilityMaterial=new CapabilityMaterial();
						capabilityMaterial.setId(UniqueIdUtil.genId());
						capabilityMaterial.setMaterialId(materiel_ids[i]);
						capabilityMaterial.setCapabilityId(capability.getId());
						capabilityMaterialService.add(capabilityMaterial);
						
					}
					}
			    
				resultMsg=getText("record.updated","能力");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	
	
	/**
	 * 	编辑能力
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("toEdit")
	@Action(description="编辑能力")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		Capability capability=capabilityService.getById(id);
		
		Long typeId = capability.getTypeId();
		
		
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("capability_class_id", typeId);
		//属性信息
		List<CapabilityProperty> property =capabilityPropertyService.getCapabilityProperty(m);
		
		Map<String,Object> cm=new HashMap<String,Object>();
		cm.put("capability_id", id);
		//物品信息
		List<CapabilityMaterial> capabilityMaterials = capabilityMaterialService.getCapabilityMaterials(cm);
		List<Material> materiallist =new ArrayList<Material>();
		for(int i=0;i<capabilityMaterials.size();i++){
			CapabilityMaterial c=capabilityMaterials.get(i);
			Material material = materialService.getById(c.getMaterialId());
			materiallist.add(material);
			
		}
		String levl1 ="";
		String levl2 ="";
		String levl3 ="";
		
		if(typeId == null || typeId == 0){
			System.out.println("异常情况！");
		}else {
			CapabilityClass c3 = capabilityClassService.getById(typeId);
			CapabilityClass c2 = capabilityClassService.getById(c3.getParentId());
			CapabilityClass c1 = capabilityClassService.getById(c2.getParentId());
			
			levl1 = c1.getName();
			levl2 = c2.getName();
			levl3 = c3.getName();
		}
		request.setAttribute("levl1",levl1);
		request.setAttribute("levl2",levl2);
		request.setAttribute("levl3",levl3);
		
		return getAutoView().addObject("capability",capability).addObject("returnUrl", returnUrl)
				.addObject("capabilityMaterials", capabilityMaterials).addObject("property", property)
				.addObject("materiallist", materiallist);
	}
	
	
	/**
	 * 取得 Capability 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected Capability getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Capability capability = (Capability)JSONObject.toBean(obj, Capability.class);
		
		return capability;
    }
	
	/**
	 * 取得已发布分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看能力分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter = new QueryFilter(request,"capabilityItem");
		queryFilter.getFilters().put("publish_state", "发布");
		queryFilter.getFilters().put("ent_id", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<Capability> list=capabilityService.getAllCg("getAllCg",queryFilter);
		ModelAndView mv=this.getAutoView().addObject("capabilityList",list);
		
		return mv;
	}
	
	
	
	/**
	 * 取得在线能力已发布分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("capabilityList")
	@Action(description="查看在线能力分页列表")
	public ModelAndView capabilityList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter = new QueryFilter(request,"capabilityItem");
		
		queryFilter.getFilters().put("publish_state", "发布");
		 
		queryFilter.getFilters().put("type_id", RequestUtil.getLong(request,"type_id"));
		
		List<Capability> list=capabilityService.getAllCg("capabilityList",queryFilter);
		ModelAndView mv=this.getAutoView().addObject("capabilityList",list);
		
		return mv;
	}
	
	/**
	 * 各个企业更多能力分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("capabilityList_cur")
	@Action(description="更多能力分页列表")
	public ModelAndView capabilityList_cur(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter = new QueryFilter(request,"capabilityItem");
		queryFilter.getFilters().put("publish_state", "发布");
		queryFilter.getFilters().put("ent_id",RequestUtil.getLong(request, "EntId"));
		List<Capability> list=capabilityService.getAllCg("getAllCg",queryFilter);
		ModelAndView mv=this.getAutoView().addObject("capabilityList",list);
		
		return mv;
	}
	
	
	@RequestMapping("search")
	@Action(description="搜索")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		  request.setCharacterEncoding("UTF-8");
		  response.setCharacterEncoding("UTF-8");
		  logger.debug("search is runn ==========");
	      String keywords= RequestUtil.getString(request,"username");
	      
	      String[] searchFields = new String[] {"name","info", "useScope", "prokey", "typeName", "entName"};
	      
	      
	  	  QueryFilter queryFilter = new QueryFilter(request,"capabilityItem",true);		
	  	  //queryFilter.getFilters().put("keywords", keywords);
	  	  
	  	  queryFilter.addFilter("keywords", keywords);
	  	  queryFilter.getFilters().put("searchFields",searchFields);
	    		  
	      List<Capability> capabilities = searchService.search(Capability.class,queryFilter);
	     
	      for(int i=0; i<capabilities.size(); i++){
	    	  Capability c = capabilityService.getById(capabilities.get(i).getId());
	    	  capabilities.get(i).setPic(c.getPic());
	    	  
	      }
	      ModelAndView mv=this.getAutoView().addObject("capabilityList",capabilities);
	    
		  return mv;
	     
	}
	
	
	/**
	 * 取得草稿 分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listCg")
	@Action(description="草稿列表")
	public ModelAndView listCg(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		 
		QueryFilter queryFilter = new QueryFilter(request,"capabilityItem");
		queryFilter.getFilters().put("publish_state", "草稿");
		queryFilter.getFilters().put("ent_id", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<Capability> list=capabilityService.getAllCg("getAllCg",queryFilter);
		ModelAndView mv=this.getAutoView().addObject("capabilityList",list);
		
		return mv;
	}
	
	
	/**
	 * 取得能力搜索 分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_query")
	@Action(description="搜索列表")
	public ModelAndView list_query(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		 
		QueryFilter queryFilter = new QueryFilter(request,"capabilityItem");
		String name=RequestUtil.getString(request, "username");
		queryFilter.getFilters().put("name","%"+name+"%");
		
		List<Capability> list=capabilityService.getAll_query("getAll_query", queryFilter);
		ModelAndView mv=this.getAutoView().addObject("capabilityList",list);
		return mv;
	}
	
	
 
	/**
	 * 删除能力
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除能力")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			Capability capability=capabilityService.getById(lAryId[0]);
			capabilityService.delByIds(lAryId);
			QueryFilter queryFilter = new QueryFilter(request,"capabilityItem");
			
			 
			queryFilter.getFilters().put("type_id", capability.getTypeId());
			
			List<Capability> list=capabilityService.getAllCg("capabilityList",queryFilter);
			
			if(list.size()==0){
				CapabilityClass capabilityClass=capabilityClassService.getById(capability.getTypeId());
				 
				capabilityClass.setIsused("null");
				capabilityClassService.update(capabilityClass);
			}
			
			message=new ResultMessage(ResultMessage.Success, "删除能力成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	
	
	
	
	/**
	 * 取消发布
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("remove")
	@Action(description="取消发布")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 
		
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			for(int i=0;i<lAryId.length;i++){
				Capability capability=capabilityService.getById(lAryId[i]);
				
				capability.setPublishState("草稿");
				capabilityService.update(capability);
			}
			message=new ResultMessage(ResultMessage.Success, "操作成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "操作失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	
	

	/**
	 * 发布
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("dep")
	@Action(description="发布")
	public void dep(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 
		
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			for(int i=0;i<lAryId.length;i++){
				Capability capability=capabilityService.getById(lAryId[i]);
				
				capability.setPublishState("发布");
				capabilityService.update(capability);
			}
			message=new ResultMessage(ResultMessage.Success, "操作成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "操作失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 取得能力明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看能力明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		Capability capability = capabilityService.getById(id);	
		 Map<String,Object> m=new HashMap<String,Object>();
		  m.put("capability_id", id);
		List<CapabilityMaterial> capabilityMaterials=	capabilityMaterialService.getCapabilityMaterials(m);
		List<CapabilityPropertyValue> propertyValues =capabilityPropertyValueService.getCapabilityPropertyValues(m);
		List<Material> materiallist =new ArrayList<Material>();
		for(int i=0;i<capabilityMaterials.size();i++){
			CapabilityMaterial c=capabilityMaterials.get(i);
			Material material = materialService.getById(c.getMaterialId());
			materiallist.add(material);
			
		}
			
		request.setAttribute ("propertyValues",propertyValues);
		request.setAttribute("materiallist",materiallist);
		return getAutoView().addObject("capability", capability);
	}
	
	
	
	/**
	 * 取得能力明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("capabilityDetail")
	@Action(description="查看能力明细")
	public ModelAndView capabilityDetail(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		Capability capability = capabilityService.getById(id);	
		 Map<String,Object> m=new HashMap<String,Object>();
		  m.put("capability_id", id);
		List<CapabilityMaterial> capabilityMaterials=	capabilityMaterialService.getCapabilityMaterials(m);
		List<CapabilityPropertyValue> propertyValues =capabilityPropertyValueService.getCapabilityPropertyValues(m);
		List<Material> materiallist =new ArrayList<Material>();
		for(int i=0;i<capabilityMaterials.size();i++){
			CapabilityMaterial c=capabilityMaterials.get(i);
			Material material = materialService.getById(c.getMaterialId());
			materiallist.add(material);
			
		}
			
		request.setAttribute ("propertyValues",propertyValues);
		request.setAttribute("materiallist",materiallist);
		return getAutoView().addObject("capability", capability);
	}
	
	
	/**
	 * 能力分类页面
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("capabilityClass")
	@Action(description="能力分类页面")
	public ModelAndView capabilityClass(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		Capability capability=capabilityService.getById(id);
		
		return getAutoView().addObject("capability",capability).addObject("returnUrl", returnUrl);
	}
	
	
	/**
	 * 能力发布页面
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("add")
	@Action(description="能力发布页面")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 
		String returnUrl=RequestUtil.getPrePage(request);
		Long classid=RequestUtil.getLong(request,"classid");
		 
		 Map<String,Object> m=new HashMap<String,Object>();
		  m.put("capability_class_id", classid);
		List<CapabilityProperty> property =capabilityPropertyService.getCapabilityProperty(m);
		request.setAttribute("classid",RequestUtil.getLong(request,"classid"));
		request.setAttribute("property",property);
	
		request.setAttribute("levl1",RequestUtil.getString(request,"levl1"));
		request.setAttribute("levl2",RequestUtil.getString(request,"levl2"));
		request.setAttribute("levl3",RequestUtil.getString(request,"levl3"));
		request.setAttribute("className",RequestUtil.getString(request,"className"));
		
		return getAutoView().addObject("returnUrl", returnUrl);
	}
	
	
	
}
