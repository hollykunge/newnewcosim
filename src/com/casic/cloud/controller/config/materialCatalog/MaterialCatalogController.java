package com.casic.cloud.controller.config.materialCatalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

import com.casic.cloud.model.config.materialCatalog.MaterialCatalog;
import com.casic.cloud.service.config.materialCatalog.MaterialCatalogService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:自定义分类 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-17 11:41:35
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/materialCatalog/")
public class MaterialCatalogController extends BaseController
{
	@Resource
	private MaterialCatalogService materialCatalogService;
	
	
	/**
	 * 添加或更新自定义分类。
	 * @param request
	 * @param response
	 * @param materialCatalog 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新自定义分类")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		MaterialCatalog materialCatalog=getFormObject(request);
		Long typeId=RequestUtil.getLong(request,"typeId");
		String typeName=RequestUtil.getString(request, "typeName");
		materialCatalog.setTypeId(typeId);
		materialCatalog.setTypeName(typeName);
		try{
			if(materialCatalog.getId()==null||materialCatalog.getId()==0){
				materialCatalog.setId(UniqueIdUtil.genId());
				if(materialCatalog.getParentId()==null||materialCatalog.getParentId()==0){
					
					materialCatalog.setParentId(0L);
				}
				materialCatalog.setEntId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
				materialCatalog.setEntName(ContextUtil.getCurrentOrgInfoFromSession().getName());
				materialCatalogService.add(materialCatalog);
				resultMsg=getText("record.added","自定义分类");
			}else{
			    materialCatalogService.update(materialCatalog);
				resultMsg=getText("record.updated","自定义分类");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 MaterialCatalog 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected MaterialCatalog getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		MaterialCatalog materialCatalog = (MaterialCatalog)JSONObject.toBean(obj, MaterialCatalog.class);
		
		return materialCatalog;
    }
	
	/**
	 * 取得自定义分类分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看自定义分类分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	QueryFilter queryFilter = new QueryFilter(request,"materialCatalogItem");
		queryFilter.getFilters().put("entId", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<MaterialCatalog> list=materialCatalogService.getAll(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("materialCatalogList",list);
		
		return mv;
	}
	
	/**
	 * 删除自定义分类
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除自定义分类")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			materialCatalogService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除自定义分类成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑自定义分类
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑自定义分类")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		MaterialCatalog materialCatalog=materialCatalogService.getById(id);
		
		return getAutoView().addObject("materialCatalog",materialCatalog).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得自定义分类明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看自定义分类明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		MaterialCatalog materialCatalog = materialCatalogService.getById(id);	
		return getAutoView().addObject("materialCatalog", materialCatalog);
	}
	
	@RequestMapping("tree")
	public ModelAndView tree(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		MaterialCatalog materialCatalog = materialCatalogService.getById(id);	
		return getAutoView().addObject("materialCatalog", materialCatalog);
	}
	
	@RequestMapping("getChildTreeData")
	@ResponseBody
	public List<MaterialCatalog> getChildTreeData(HttpServletRequest request, HttpServletResponse response)
			throws Exception
		{
			long posId = RequestUtil.getLong(request, "id", 0L);
			long parentId = RequestUtil.getLong(request, "parentId", 0L);
			List<MaterialCatalog>  list = new ArrayList<MaterialCatalog> ();
			if (parentId == 0L && posId == 0L)
			{
				list = materialCatalogService.getAllChildByParentId(posId);
				MaterialCatalog parent = materialCatalogService.getParentPositionByParentId(parentId);
				list.add(parent);
			} else
			{
				list = materialCatalogService.getChildByParentId(posId);
			}
			return list;
		}
	
	
	
}
