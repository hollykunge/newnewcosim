package com.casic.cloud.controller.config.businessServechase;

import java.util.Collection;
import java.util.Date;
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

import com.casic.cloud.model.config.businessMarketingchase.BusinessMarketingchase;
import com.casic.cloud.model.config.businessPurchase.BusinessPurchase;
import com.casic.cloud.model.config.businessServechase.BusinessServechase;
import com.casic.cloud.model.config.info.Info;
import com.casic.cloud.service.config.businessServechase.BusinessServechaseService;
import com.casic.cloud.service.config.info.InfoService;
import com.hotent.core.web.ResultMessage;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SubSystemService;
/**
 *<pre>
 * 对象功能:服务商机 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-03 11:13:52
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/businessServechase/")
public class BusinessServechaseController extends BaseController
{
	@Resource
	private BusinessServechaseService businessServechaseService;
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private InfoService infoService;
	
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
	
	/**
	 * 添加或更新服务商机。
	 * @param request
	 * @param response
	 * @param businessServechase 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新服务商机")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		BusinessServechase businessServechase=getFormObject(request);
		businessServechase.setType("4");
		
		
		try{
			if(businessServechase.getId()==null||businessServechase.getId()==0){
				businessServechase.setId(UniqueIdUtil.genId());
				businessServechase.setOperateTime(new Date());
				
				businessServechase.setCompanyId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
				businessServechase.setCompanyName(ContextUtil.getCurrentOrgInfoFromSession().getName());
				
				businessServechase.setUserId(ContextUtil.getCurrentUser().getUserId());
				businessServechase.setUserName(ContextUtil.getCurrentUser().getFullname());
				String content=RequestUtil.getString(request,"content");
			 
				String publishState=RequestUtil.getString(request,"publishState");
				businessServechase.setContent(publishState);
				businessServechase.setIndustry(RequestUtil.getString(request,"industry"));
				businessServechase.setIndustry2(RequestUtil.getString(request,"industry2"));
				businessServechase.setFwqy(RequestUtil.getString(request,"fwqy"));
				businessServechase.setFwqy2(RequestUtil.getString(request,"fwqy2"));
				businessServechase.setContent(content);
				String image=RequestUtil.getString(request, "image");
				if(image!=null&&image.length()>0){
					businessServechase.setImage(image);
				}else{
					//采购商机
					businessServechase.setImage("/images/product_img04.jpg"); 
					}
				businessServechaseService.add(businessServechase);
				resultMsg=getText("record.added","服务商机");
			}else{
				
				
				businessServechase.setContent(RequestUtil.getString(request,"publishState"));
				businessServechase.setContent(RequestUtil.getString(request,"content"));
				 
				businessServechase.setCompanyName(RequestUtil.getString(request,"companyName"));
				
				businessServechase.setUserName(RequestUtil.getString(request,"userName"));
			    businessServechaseService.update(businessServechase);
				resultMsg=getText("record.updated","服务商机");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 BusinessServechase 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected BusinessServechase getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		BusinessServechase businessServechase = (BusinessServechase)JSONObject.toBean(obj, BusinessServechase.class);
		
		return businessServechase;
    }
	
	/**
	 * 取得服务商机分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看服务商机分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	QueryFilter queryFilter=	new QueryFilter(request,"businessServechaseItem");
	 
	queryFilter.getFilters().put("company_id", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessServechase> list=businessServechaseService.getAllByEntid("getAllByEntid",queryFilter);
		 
		ModelAndView mv=this.getAutoView().addObject("businessServechaseList",list);
		
		return mv;
	}
	
	
	/**
	 * 转到我的商机页面
	 * @return
	 */
	@RequestMapping("toMyBusinessChase")
	public ModelAndView toMyBusinessChase(HttpServletRequest request) throws Exception
	{
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
		Long entid = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		Info info = infoService.getById(entid);
		
		ModelAndView mav = getAutoView()
				.addObject("type",type)
				.addObject("info", info);
		this.getResources(mav, request);
		return mav;
	}
	
	/**
	 * 我的商机分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myBusinessChase")
	@Action(description="我的商机分页列表")
	public ModelAndView myBusinessChase(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter=	new QueryFilter(request,"myBusinessChaseItem");
		 
		queryFilter.getFilters().put("company_id", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		queryFilter.getFilters().put("user_id", ContextUtil.getCurrentUser().getUserId());
		List<BusinessServechase> myBusinessChase=businessServechaseService.getMyChase("myChase",queryFilter);
		Long entid = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		Info info = infoService.getById(entid);
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
ModelAndView mav = getAutoView().addObject("myBusinessChase",myBusinessChase).addObject("info", info).addObject("type",type);
		this.getResources(mav, request);
		return mav;
	}
	
	/**
	 * 删除服务商机
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除服务商机")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			businessServechaseService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除服务商机成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	/**
	 * 编辑研发商机页面
	 * @return
	 */
	@RequestMapping("toEdit")
	public ModelAndView toEdit(HttpServletRequest request) throws Exception
	{
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
		Long entid = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		Info info = infoService.getById(entid);
		
		ModelAndView mav = getAutoView()
				.addObject("type",type)
				.addObject("info", info);
		this.getResources(mav, request);
		return mav;
	}
	/**
	 * 	编辑服务商机
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑服务商机")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		BusinessServechase businessServechase=businessServechaseService.getById(id);
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
		Long entid = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		Info info = infoService.getById(entid);
		
		
		ModelAndView mav = getAutoView().addObject("businessServechase",businessServechase)
				.addObject("type",type).addObject("returnUrl", returnUrl)
				.addObject("info", info);
		this.getResources(mav, request);
		return mav;
	}
	
	/**
	 * 提交商机
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("submits")
	@Action(description="提交商机")
	public void submits(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			long id=RequestUtil.getLong(request,"id");
			BusinessServechase businessServechase = businessServechaseService.getById(id);	
			businessServechase.setPublishState("未审核");
			businessServechaseService.update(businessServechase);
			message=new ResultMessage(ResultMessage.Success, "提交成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "提交失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 取得服务商机明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看服务商机明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		BusinessServechase businessServechase = businessServechaseService.getById(id);	
		return getAutoView().addObject("businessServechase", businessServechase);
	}
	 
	/**
	 * 取得服务商机明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mget")
	@Action(description="查看服务商机明细")
	public ModelAndView mget(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		BusinessServechase businessServechase = businessServechaseService.getById(id);	
		Long entid = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		Info info = infoService.getById(entid);
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
ModelAndView mav = getAutoView().addObject("businessServechase", businessServechase).addObject("info", info).addObject("type",type);
this.getResources(mav, request);
return mav;
	}
	
	/**
	 * 审核服务商机
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("change")
	@Action(description="审核")
	public ModelAndView change(HttpServletRequest request) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		BusinessServechase businessServechase = businessServechaseService.getById(id);	
		return getAutoView().addObject("businessServechase", businessServechase);
	}
	
}
