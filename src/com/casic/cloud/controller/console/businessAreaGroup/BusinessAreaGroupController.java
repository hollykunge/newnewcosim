package com.casic.cloud.controller.console.businessAreaGroup;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
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

import com.casic.cloud.model.cloudUseRes.CloudUseRes;
import com.casic.cloud.model.config.info.Info;
import com.casic.cloud.model.console.busiarea.Busiarea;
import com.casic.cloud.model.console.businessAreaGroup.BusinessAreaGroup;
import com.casic.cloud.model.system.news.News;
import com.casic.cloud.service.config.info.InfoService;
import com.casic.cloud.service.console.busiarea.BusiareaService;
import com.casic.cloud.service.console.businessAreaGroup.BusinessAreaGroupService;
import com.hotent.core.web.ResultMessage;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SubSystemService;
/**
 *<pre>
 * 对象功能:分组 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-05 13:24:43
 *</pre>
 */
@Controller
@RequestMapping("/cloud/console/businessAreaGroup/")
public class BusinessAreaGroupController extends BaseController
{
	@Resource
	private BusinessAreaGroupService businessAreaGroupService;
	 
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private BusiareaService busiareaService;
	 
	/**
	 * 获取商机类型
	 * @return
	 */
	public String getBusinessChangeType(){
		//判断当前用户角色
		Collection<GrantedAuthority> authorities = ContextUtil.getCurrentUser().getAuthorities();
		String type="";
		if(authorities.contains(new GrantedAuthorityImpl("cloud_ROLE_PUR_MANAGER"))  ){
			//采购主管
			type="采购商机";				
		}else if( authorities.contains(new GrantedAuthorityImpl("cloud_ROLE_SALE_MANAGER"))){
			//营销主管
			type="营销商机";
		}else if( authorities.contains(new GrantedAuthorityImpl("cloud_ROLE_MANUF_MANAGER"))){
			//生产主管
			type="生产商机";
		}else if( authorities.contains(new GrantedAuthorityImpl("cloud_ROLE_AFTERSALE"))){
			//服务主管
			type="服务商机";
		}else if( authorities.contains(new GrantedAuthorityImpl("cloud_ROLE_RESEARCH"))){
			//研发主管
			type="研发商机";
		}else{
			type="其它";
			
		}
		return type;
	}
	/**
	 * 添加或更新分组。
	 * @param request
	 * @param response
	 * @param businessAreaGroup 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新分组")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		BusinessAreaGroup businessAreaGroup=getFormObject(request);
		
		try{
			if(businessAreaGroup.getId()==null||businessAreaGroup.getId()==0){
				businessAreaGroup.setId(UniqueIdUtil.genId());
				businessAreaGroup.setEntid(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
				businessAreaGroupService.add(businessAreaGroup);
				resultMsg=getText("操作成功");
			}else{
				 
					businessAreaGroupService.update(businessAreaGroup);
					resultMsg=getText("操作成功");
				 
			    
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 BusinessAreaGroup 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected BusinessAreaGroup getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		BusinessAreaGroup businessAreaGroup = (BusinessAreaGroup)JSONObject.toBean(obj, BusinessAreaGroup.class);
		
		return businessAreaGroup;
    }
	
	/**
	 * 取得分组分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看分组分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<BusinessAreaGroup> list=businessAreaGroupService.getAll(new QueryFilter(request,"businessAreaGroupItem"));
		ModelAndView mv=this.getAutoView().addObject("businessAreaGroupList",list);
		
		return mv;
	}
	
	/**
	 * 删除分组
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@ResponseBody
	public Map<String, String>  del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 
		 
		Map<String, String> dataMap = new HashMap<String, String>();
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			
			QueryFilter queryFilter = new QueryFilter(request, "busiareaList");
			queryFilter.addFilter("groupid", lAryId[0]);
			 List<Busiarea> busiareaList=busiareaService.getByGroupId(queryFilter);
			for(int i=0;i<busiareaList.size();i++){
				Busiarea busiarea=busiareaList.get(i);
				busiarea.setGroupid(null);
				busiareaService.update(busiarea);
				
			}
			businessAreaGroupService.delByIds(lAryId);
			dataMap.put("choseGroup", "true");
		}catch(Exception e){
			dataMap.put("delGroup", "false");
			e.printStackTrace();
		}
		return dataMap;
	}
	
	
	
	
	/**
	 * 	未分组
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("noGroup")
	@Action(description="编辑分组")
	public ModelAndView noGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 
				 Map<String,Object> m=new HashMap<String,Object>();
				 //商圈分组
				    m.put("entid", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
				List<BusinessAreaGroup> businessAreaGroupList=businessAreaGroupService.getAllByEntid(m);

				QueryFilter queryFilter = new QueryFilter(request, "busiareaList");
				queryFilter.getFilters().put("mainEnt", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
				queryFilter.addFilter("state", 1);
				List<Busiarea> busiareaList = busiareaService.getAllNoGroup(queryFilter);
				
			 
				
				ModelAndView mav = this.getAutoView()
						.addObject("user",ContextUtil.getCurrentUser())
						.addObject("businessAreaGroupList",businessAreaGroupList)
						 
						.addObject("busiareaList",busiareaList)
						.addObject("type",getBusinessChangeType());
	this.getResources(mav, request);
	return mav;
	}
	
	
	/**
	 * 未分组
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("tonoGroup")
	public ModelAndView tonoGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Long id=RequestUtil.getLong(request,"id");
		BusinessAreaGroup businessAreaGroup=businessAreaGroupService.getById(id);
		 Map<String,Object> m=new HashMap<String,Object>();
		 //商圈分组
		    m.put("entid", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessAreaGroup> businessAreaGroupList=businessAreaGroupService.getAllByEntid(m);
		QueryFilter queryFilter = new QueryFilter(request, "busiareaList");
		queryFilter.addFilter("groupid", id);
		 List<Busiarea> busiareaList=busiareaService.getByGroupId(queryFilter);
		
		
		
		
		ModelAndView mav = this.getAutoView()
							.addObject("user",ContextUtil.getCurrentUser())
							.addObject("businessAreaGroupList",businessAreaGroupList)
							.addObject("businessAreaGroup",businessAreaGroup)
							.addObject("busiareaList",busiareaList)
							.addObject("type",getBusinessChangeType());
		this.getResources(mav, request);
		return mav;
	}
	/**
	 * 	编辑分组
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("toEdit")
	@Action(description="编辑分组")
	public ModelAndView toEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Long id=RequestUtil.getLong(request,"id");
		BusinessAreaGroup businessAreaGroup=businessAreaGroupService.getById(id);
		 Map<String,Object> m=new HashMap<String,Object>();
		 //商圈分组
		    m.put("entid", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessAreaGroup> businessAreaGroupList=businessAreaGroupService.getAllByEntid(m);
		QueryFilter queryFilter = new QueryFilter(request, "busiareaList");
		queryFilter.addFilter("groupid", id);
		 List<Busiarea> busiareaList=busiareaService.getByGroupId(queryFilter);
		
		
		
		
		ModelAndView mav = this.getAutoView()
							.addObject("user",ContextUtil.getCurrentUser())
							.addObject("businessAreaGroupList",businessAreaGroupList)
							.addObject("businessAreaGroup",businessAreaGroup)
							.addObject("busiareaList",busiareaList)
							.addObject("type",getBusinessChangeType());
		this.getResources(mav, request);
		return mav;
	}
	/**
	 * 	编辑分组
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑分组")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id=RequestUtil.getLong(request,"id");
		BusinessAreaGroup businessAreaGroup=businessAreaGroupService.getById(id);
		 Map<String,Object> m=new HashMap<String,Object>();
		 //商圈分组
		    m.put("entid", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessAreaGroup> businessAreaGroupList=businessAreaGroupService.getAllByEntid(m);
		QueryFilter queryFilter = new QueryFilter(request, "busiareaList");
		queryFilter.addFilter("groupid", id);
		 List<Busiarea> busiareaList=busiareaService.getByGroupId(queryFilter);
		
		
		
		
		ModelAndView mav = this.getAutoView()
							.addObject("user",ContextUtil.getCurrentUser())
							.addObject("businessAreaGroupList",businessAreaGroupList)
							.addObject("businessAreaGroup",businessAreaGroup)
							.addObject("busiareaList",busiareaList)
							.addObject("type",getBusinessChangeType());
		this.getResources(mav, request);
		return mav;
	
	}

	/**
	 * 取得分组明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看分组明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		BusinessAreaGroup businessAreaGroup = businessAreaGroupService.getById(id);	
		return getAutoView().addObject("businessAreaGroup", businessAreaGroup);
	}
	/**
	 * 获取我的资源
	 * @param mav
	 * @param request
	 */
	public void getResources(ModelAndView mav, HttpServletRequest request) {
		// 查询资源菜单
		SubSystem currentSystem = subSystemService.getById(1L);
		ISysUser sysUser = ContextUtil.getCurrentUser();
		List<Resources> resourcesList = resourcesService.getCloudTopMenu(
				currentSystem, sysUser);
		Long resId = RequestUtil.getLong(request, "resId");
		if (resId.longValue() == 0L && resourcesList.size() > 0) {
			resId = resourcesList.get(0).getResId();
		}
		if (resId.longValue() > 0L) {
			List<Resources> leftResourcesList = resourcesService
					.getChildrenByParentId(resId, true);
			mav.addObject("leftResourcesList", leftResourcesList);
		}
		mav.addObject("resourcesList", resourcesList);
	}
}
