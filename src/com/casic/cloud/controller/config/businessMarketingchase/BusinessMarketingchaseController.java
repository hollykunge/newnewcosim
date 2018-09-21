package com.casic.cloud.controller.config.businessMarketingchase;

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

import com.casic.cloud.model.config.businessDevchase.BusinessDevchase;
import com.casic.cloud.model.config.businessMarketingchase.BusinessMarketingchase;
import com.casic.cloud.model.config.businessPurchase.BusinessPurchase;
import com.casic.cloud.model.config.businessServechase.BusinessServechase;
import com.casic.cloud.model.config.info.Info;
import com.casic.cloud.service.config.businessMarketingchase.BusinessMarketingchaseService;
import com.casic.cloud.service.config.info.InfoService;
import com.hotent.core.web.ResultMessage;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SubSystemService;
/**
 *<pre>
 * 对象功能:营销商机 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-02 18:07:15
 *</pre>
 */
@Controller
@RequestMapping("/cloud/config/businessMarketingchase/")
public class BusinessMarketingchaseController extends BaseController
{
	@Resource
	private BusinessMarketingchaseService businessMarketingchaseService;
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
	 * 添加或更新营销商机。
	 * @param request
	 * @param response
	 * @param businessMarketingchase 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新营销商机")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		BusinessMarketingchase businessMarketingchase=getFormObject(request);
		businessMarketingchase.setType("2");
		
		try{
			if(businessMarketingchase.getId()==null||businessMarketingchase.getId()==0){
				businessMarketingchase.setId(UniqueIdUtil.genId());
				
				businessMarketingchase.setOperateTime(new Date());
				
				businessMarketingchase.setCompanyId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
				businessMarketingchase.setCompanyName(ContextUtil.getCurrentOrgInfoFromSession().getName());
				
				businessMarketingchase.setUserId(ContextUtil.getCurrentUser().getUserId());
				businessMarketingchase.setUserName(ContextUtil.getCurrentUser().getFullname());
				String content=RequestUtil.getString(request,"content");
				String classid=RequestUtil.getString(request,"classid");
				String publishState=RequestUtil.getString(request,"publishState");
				
				businessMarketingchase.setDlqy(RequestUtil.getString(request,"dlqy"));
				businessMarketingchase.setDlqy2(RequestUtil.getString(request,"dlqy2"));
				businessMarketingchase.setContent(publishState);
				businessMarketingchase.setClassid(classid);
				businessMarketingchase.setContent(content);
				String image=RequestUtil.getString(request, "image");
				if(image!=null&&image.length()>0){
					businessMarketingchase.setImage(image);
				}else{
					//采购商机
					businessMarketingchase.setImage("/images/product_img02.jpg");
					}
				businessMarketingchaseService.add(businessMarketingchase);
				resultMsg=getText("record.added","营销商机");
			}else{
				
				
				businessMarketingchase.setContent(RequestUtil.getString(request,"publishState"));
				businessMarketingchase.setContent(RequestUtil.getString(request,"content"));
				 
				businessMarketingchase.setCompanyName(RequestUtil.getString(request,"companyName"));
				
				businessMarketingchase.setUserName(RequestUtil.getString(request,"userName"));
			    businessMarketingchaseService.update(businessMarketingchase);
				resultMsg=getText("record.updated","营销商机");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 BusinessMarketingchase 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected BusinessMarketingchase getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		BusinessMarketingchase businessMarketingchase = (BusinessMarketingchase)JSONObject.toBean(obj, BusinessMarketingchase.class);
		
		return businessMarketingchase;
    }
	
	/**
	 * 取得营销商机分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看营销商机分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter=	new QueryFilter(request,"businessMarketingchaseItem");
		 
		queryFilter.getFilters().put("company_id", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		 
		List<BusinessMarketingchase> list=businessMarketingchaseService.getAllByEntid("getAllByEntid",queryFilter);
		ModelAndView mv=this.getAutoView().addObject("businessMarketingchaseList",list);
		
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
		List<BusinessMarketingchase> myBusinessChase=businessMarketingchaseService.getMyChase("myChase",queryFilter);
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
	 * 删除营销商机
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除营销商机")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			businessMarketingchaseService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除营销商机成功!");
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
	 * 	编辑营销商机
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑营销商机")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		BusinessMarketingchase businessMarketingchase=businessMarketingchaseService.getById(id);
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
				.addObject("businessMarketingchase",businessMarketingchase)
				.addObject("type",type)
				.addObject("returnUrl", returnUrl)
				.addObject("info", info);
		this.getResources(mav, request);
		return mav;
	}

	/**
	 * 取得营销商机明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看营销商机明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		BusinessMarketingchase businessMarketingchase = businessMarketingchaseService.getById(id);	
		return getAutoView().addObject("businessMarketingchase", businessMarketingchase);
	}
	 
	/**
	 * 取得营销商机明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mget")
	@Action(description="查看营销商机明细")
	public ModelAndView mget(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		BusinessMarketingchase businessMarketingchase = businessMarketingchaseService.getById(id);	
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
ModelAndView mav = getAutoView().addObject("businessMarketingchase",businessMarketingchase).addObject("info", info).addObject("type",type);
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
			BusinessMarketingchase businessMarketingchase = businessMarketingchaseService.getById(id);
			businessMarketingchase.setPublishState("未审核");
			businessMarketingchaseService.update(businessMarketingchase);
			message=new ResultMessage(ResultMessage.Success, "提交成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "提交失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 审核营销商机
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
		BusinessMarketingchase businessMarketingchase= businessMarketingchaseService.getById(id);	
		return getAutoView().addObject("businessMarketingchase", businessMarketingchase);
	}
	
}
