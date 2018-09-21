package com.casic.cloud.controller.system.enterprise;

import java.util.LinkedList;

import java.util.List;

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

import com.casic.cloud.model.config.business.BusinessChance;
import com.casic.cloud.model.config.material.Material;
import com.casic.cloud.service.compass.SearchServiceBean;
import com.casic.cloud.model.config.info.Info;
import com.casic.cloud.service.config.info.InfoService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysOrgType;
import com.hotent.platform.service.system.DemensionService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysOrgTypeService;
import com.hotent.platform.service.system.SysUserOrgService;

//@Controller
//@RequestMapping("/cloud/system/enterprises/")
public class EnterprisesController extends BaseController{
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private DemensionService demensionService;
	@Resource
	private SysOrgTypeService sysOrgTypeService;
	@Resource
	private IAuthenticate iAuthenticate;
	@Resource
	private SysUserOrgService sysUserOrgService;
	@Resource
	private SearchServiceBean<Info> searchService;
	@Resource
	private InfoService infoService;
	
	@RequestMapping("org/list")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request,"sysOrgItem");
		queryFilter.addFilter("orgSupId",1L );
		List<ISysOrg> list=sysOrgService.getAll(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("sysOrgList",list);		
		return mv;
	}
	
	@RequestMapping("org/edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv=getAutoView();
		mv.addObject("scope", "global");
		return getEditMv(request,mv);
	}
	
	@RequestMapping("editGrade")
	public ModelAndView editGrade(HttpServletRequest request) throws Exception {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/cloud/system/enterprises/org/edit.jsp");
		mv.addObject("scope", "grade");
		return getEditMv(request,mv);
	}
	
	/**
	 * 取得组织架构明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("org/get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv=this.getAutoView();
		mv.addObject("action", "global");
		return getByOrgId(request,mv);

	}
	
	/**
	 * 添加或更新组织架构
	 * @param request
	 * @param response
	 * @param sysOrg 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("org/save")
	@Action(description="添加或更新组织架构")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		SysOrg sysOrg=getFormObject(request);
		try{
			if(sysOrg.getOrgId()==null||sysOrg.getOrgId()==0){
				sysOrg.setOrgId(UniqueIdUtil.genId());
				sysOrgService.add(sysOrg);
				resultMsg=getText("record.added","组织架构");
			}else{
			    sysOrgService.update(sysOrg);
				resultMsg=getText("record.updated","组织架构");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
    protected SysOrg getFormObject(HttpServletRequest request) throws Exception {
        
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		SysOrg sysOrg = (SysOrg)JSONObject.toBean(obj, SysOrg.class);
		
		return sysOrg;
    }
	
	private ModelAndView getByOrgId(HttpServletRequest request,ModelAndView mv) throws Exception{
		long orgId = RequestUtil.getLong(request, "orgId");
		List<SysOrgType> sysOrgTypelist=null;
		String ownerName = "";
		ISysOrg sysOrg = sysOrgService.getById(orgId);
		if (sysOrg != null) {
			ISysOrg charge = sysUserOrgService.getChageNameByOrgId(orgId);
			Long demId = sysOrg.getDemId();
			sysOrgTypelist=sysOrgTypeService.getByDemId(demId);
			if (sysOrg.getDemId() != 0) {
				sysOrg.setDemName(demensionService.getById(demId).getDemName());
				ownerName = charge.getOwnUserName();
			
			}
		}

		return mv.addObject("sysOrg", sysOrg)
				.addObject("userNameCharge", ownerName)
				.addObject("orgId", orgId)
				.addObject("sysOrgTypelist",sysOrgTypelist);
	}
	
	
	/**
	 * ajax获取Email
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("emailValid")
	@Action(description="查询注册email是否重名")
	@ResponseBody
	public String emailValid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String istf = "";
		
		String email = RequestUtil.getString(request, "email");
		
		QueryFilter queryFilter = new QueryFilter(request,"InfoItem");
		
		
		queryFilter.addFilter("email", email);
		
		String sqlKey = "getAllbyEmail";
		
		List<Info> infos=infoService.getAllInfos(sqlKey, queryFilter);
		
		
		if (infos.size()>0) {
			istf = "false";
		}else {
			istf = "true";
		}
		
		return istf;

	}
	
	
	
	/**
	 * ajax获取name
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("nameValid")
	@Action(description="查询注册企业名称是否重名")
	@ResponseBody
	public String nameValid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String istf = "";
		
		String name = RequestUtil.getString(request, "name");
		
		QueryFilter queryFilter = new QueryFilter(request,"InfoItem");
		
		
		queryFilter.addFilter("name", name);
		
		String sqlKey = "getAllbyname";
		
		List<Info> infos= null;
		
		infos = infoService.getAllInfos(sqlKey, queryFilter);
		
			if(infos == null || infos.size() == 0){
				istf = "true";
			}else {
				istf = "false";
			}
		
		
		
		
		return istf;

	}
	
	
	/**
	 * 获取编辑界面的modelandview
	 * @param request
	 * @param mv
	 * @return
	 */
	private ModelAndView getEditMv(HttpServletRequest request,ModelAndView mv){
		Long demId = RequestUtil.getLong(request, "demId", 0);
		Long orgId = RequestUtil.getLong(request, "orgId");
		String action = RequestUtil.getString(request, "action");
		ISysOrg sysOrg = null;
		Long parentOrgId=0L;
		// 当前维度
		if(demId.longValue()==0L){
			demId=1L;
		}
		Demension demension = demensionService.getById(demId);
		List<SysOrgType> sysOrgTypelist=sysOrgTypeService.getByDemId(demId);
		List<SysOrgType> returnSysOrgTypelist=new LinkedList<SysOrgType>();
		SysOrgType subSysOrgType=null;
		if ("add".equals(action)) {// 新增
			sysOrg = iAuthenticate.getNewSysOrg();
			ISysOrg supSysOrg = sysOrgService.getById(orgId);
			
			if (supSysOrg == null) { // 从维度上新建组织
				sysOrg.setOrgSupId(demId);
				returnSysOrgTypelist=sysOrgTypelist;
			} else {
				supSysOrg = sysOrgService.getById(orgId);
				sysOrg.setOrgSupId(supSysOrg.getOrgId());
				sysOrg.setOrgSupName(supSysOrg.getOrgName());
				Long supSysOrgId=supSysOrg.getOrgType();
				if(supSysOrgId!=null){
					 subSysOrgType=sysOrgTypeService.getById(supSysOrg.getOrgType());
				}		
			}
		} else {// 编辑
			sysOrg = sysOrgService.getById(orgId);
			ISysOrg charge = sysUserOrgService.getChageNameByOrgId(orgId);
			sysOrg.setOwnUser(charge.getOwnUser());
			sysOrg.setOwnUserName(charge.getOwnUserName());
			 parentOrgId=sysOrg.getOrgSupId();
			
			if(sysOrg.getOrgType()!=null)
				subSysOrgType=sysOrgTypeService.getById(sysOrg.getOrgType());
		
			 if(subSysOrgType==null){
				 
				 ISysOrg parentOrg= sysOrgService.getParentWithType(sysOrg);  //取得有分类的父极节点
				 if(parentOrg!=null)
					 subSysOrgType=sysOrgTypeService.getById(parentOrg.getOrgType());
				 else
					 returnSysOrgTypelist=sysOrgTypelist;
			 }
		}
		if(subSysOrgType!=null && !parentOrgId.equals(1L)){
			for(int i=0;i<sysOrgTypelist.size();i++){
				if(subSysOrgType.getLevels()<=sysOrgTypelist.get(i).getLevels())
					returnSysOrgTypelist.add(sysOrgTypelist.get(i));
			}
		}
		else if(parentOrgId.equals(1L)){
			returnSysOrgTypelist=sysOrgTypelist;
		}
			
		return mv.addObject("sysOrg", sysOrg)
				.addObject("demension", demension)
				.addObject("action", action)
				.addObject("sysOrgTypelist",returnSysOrgTypelist);
	}
	
	@RequestMapping("search")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		  request.setCharacterEncoding("UTF-8");
		  response.setCharacterEncoding("UTF-8");
	      String keywords= RequestUtil.getString(request,"username");
	      
	      String[] searchFields = new String[] {"name","industry", "scale", "address", "connecter"};
	  	  QueryFilter queryFilter = new QueryFilter(request,"sysOrgInfoItem",true);		
	  	  //queryFilter.getFilters().put("keywords", keywords);
	  	  
	  	  queryFilter.addFilter("keywords", keywords);
	  	  queryFilter.getFilters().put("searchFields",searchFields);
	    		  
	      List<Info> sysOrgInfo = searchService.search(Info.class,queryFilter);
	     
	      ModelAndView mv=this.getAutoView().addObject("sysOrgInfoList",sysOrgInfo);
	    
		  return mv;
	     
	}
	
	@RequestMapping("search_ind")
	public ModelAndView search_ind(HttpServletRequest request, HttpServletResponse response) throws Exception
	{		
	      QueryFilter queryFilter = new QueryFilter(request,"sysOrgInfoItem");
	      List<Info> sysOrgInfo = infoService.getAllInfos("getAll", queryFilter);
	      ModelAndView mv=this.getAutoView().addObject("sysOrgInfoList",sysOrgInfo);
		  return mv;
	}
}
