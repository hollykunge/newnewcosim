package com.casic.cloud.controller.cloudResource.resourceManagement;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.casic.cloud.controller.cloudResource.approvalResource.ApprovalResourceController;
import com.casic.cloud.model.cloudResource.resourceManagement.CloudResource;
import com.casic.cloud.service.cloudResource.resourceManagement.CloudResourceService;
import com.casic.cloud.model.cloudResource.resourceManagement.CloudResourceInstance;
import com.hotent.core.web.ResultMessage;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SubSystemService;
/**
 *<pre>
 * 对象功能:cloud_resource_class 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:杜宇坤
 * 创建时间:2016-12-02 18:11:59
 *</pre>
 */
@Controller
@RequestMapping("/cloud/cloudResource/resourceManagement/")
public class CloudResourceController extends BaseController
{
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private CloudResourceService cloudResourceService;
	
	
	private static String lsf="";
	StringBuilder sb = new StringBuilder();
	static {
		//get Remote IP 
				InputStream fis =ApprovalResourceController.class.getClassLoader().getResourceAsStream("conf/custom.properties");
				Properties p = new Properties();
				try {
					p.load(fis);
					lsf=p.getProperty("engineframeAddress");
				} catch (IOException e) {
					e.printStackTrace();
				}
		
	}
	
	/**20161202
	 * 添加或更新cloud_resource_class。
	 * @param request
	 * @param response
	 * @param cloudResource 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新cloud_resource_class")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		CloudResource cloudResource=getFormObject(request);
		try{
			if(cloudResource.getId()==null||cloudResource.getId()==0){
				cloudResource.setId(UniqueIdUtil.genId());
				cloudResourceService.addAll(cloudResource);			
				resultMsg=getText("record.added","cloud_resource_class");
			}else{
			    cloudResourceService.updateAll(cloudResource);
				resultMsg=getText("record.updated","cloud_resource_class");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**20161202
	 * 取得 CloudResource 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected CloudResource getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("cloudResourceInstanceList", CloudResourceInstance.class);
		CloudResource cloudResource = (CloudResource)JSONObject.toBean(obj, CloudResource.class,map);
		
		return cloudResource;
    }
	
	/**
	 * 取得cloud_resource_class分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看cloud_resource_class分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CloudResource> list=cloudResourceService.getAll(new QueryFilter(request,"cloudResourceItem"));
		ModelAndView mv=this.getAutoView().addObject("cloudResourceList",list);
		
		return mv;
	}
	
	/**
	 * 删除cloud_resource_class
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除cloud_resource_class")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			cloudResourceService.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除cloud_resource_class及其从表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/** 20161202
	 * 	编辑cloud_resource_class
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑cloud_resource_class")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		CloudResource cloudResource=cloudResourceService.getById(id);
		List<CloudResourceInstance> cloudResourceInstanceList=cloudResourceService.getCloudResourceInstanceList(id);
		
		return getAutoView().addObject("cloudResource",cloudResource)
							.addObject("cloudResourceInstanceList",cloudResourceInstanceList)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得cloud_resource_class明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看cloud_resource_class明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		CloudResource cloudResource = cloudResourceService.getById(id);	
		List<CloudResourceInstance> cloudResourceInstanceList=cloudResourceService.getCloudResourceInstanceList(id);
		return getAutoView().addObject("cloudResource",cloudResource)
							.addObject("cloudResourceInstanceList",cloudResourceInstanceList);
	}

	/**
	 * 获取我的资源
	 * 
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
	
	
	/*点击某行业软件后，将参数带入下一页，用以单点登录及软件列表显示*/	
	@RequestMapping("showResource")
	public ModelAndView showResource(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String hpcUrl = lsf ;
		String type =request.getParameter("type");
		int isLogin = 1;
		if(ContextUtil.getCurrentUserId()==null){
			isLogin = 0;
			}
		long resourceID = Long.valueOf(request.getParameter("resourceID"));
		
		if(type.equals("software") ){
			ModelAndView mav = new ModelAndView("/cloud/cloudResource/resourceManagement/showSoftwareResource.jsp");
			mav.addObject("resourceID", resourceID)
			.addObject("lsf", lsf)
			.addObject("isLogin", isLogin);
			this.getResources(mav, request);
			return mav;
		}
		else if(type.equals("software_3d") ){
			ModelAndView mav = new ModelAndView("/cloud/cloudResource/resourceManagement/showSoftwareResource2.jsp");
			mav.addObject("resourceID", resourceID)
			.addObject("lsf", lsf)
			.addObject("isLogin", isLogin);
			this.getResources(mav, request);
			return mav;
		}
		else if(type.equals("computing")){
			if (request.getParameter("hpc")!=null){
				hpcUrl = request.getParameter("hpc");
			}
			ModelAndView mav = new ModelAndView("/cloud/cloudResource/resourceManagement/showComputingResource.jsp");
			mav.addObject("resourceID", resourceID)
			.addObject("lsf", hpcUrl)
			.addObject("isLogin", isLogin);
			this.getResources(mav, request);
			return mav;
		}
		else{
			return null;
		}
			
	}
	
	/*通过搜索找到某一软件资源后，列出可用信息*/	
	@RequestMapping("showResourceInstance")
	public ModelAndView showResourceInstance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long resourceInstanceID = Long.valueOf(request.getParameter("resourceInstanceID"));
		String mode ="";
		if(ContextUtil.getCurrentUserId()==null){
			mode = "nologin";
		}
		ModelAndView mav = this.getAutoView();
		mav.addObject("resourceInstanceID", resourceInstanceID)
		.addObject("lsf", lsf)
		.addObject("mode", mode);
		return mav;
	}
	
	/*根据资源ID（软硬件资源）列出下属的资源示例ID*/	
	@RequestMapping("listResourceInstance")
	public ModelAndView listResourceInstance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long resourceID = Long.valueOf(request.getParameter("resourceID"));
		List<CloudResourceInstance>  resourceInstances = cloudResourceService.getCloudResourceInstanceList(resourceID);
		
		String mode = request.getParameter("mode");
		List<CloudResourceInstance>  list = new LinkedList<CloudResourceInstance>();
		if(mode!=null ){
				for(CloudResourceInstance cloudResourceInstance:resourceInstances){
					if(cloudResourceInstance.getMode()==null){
						continue;
					}
					else if(cloudResourceInstance.getMode().equals(mode)){
						list.add(cloudResourceInstance);
					}
				}
				
		}
		ModelAndView mav = this.getAutoView();
		mav.addObject("resourceInstances", list)
		.addObject("mode", mode);
		return mav;

	}
	/**
	 * 取得cloud_resource_class分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("search")
	@Action(description="搜索cloud_resource_class分页列表")
	public ModelAndView search(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String name= RequestUtil.getString(request,"searchinput");
		QueryFilter queryFilter = new QueryFilter(request,"cloudResourceItem");
		queryFilter.getFilters().put("title", "%"+name+"%");
		List<CloudResourceInstance> list=cloudResourceService.getAllResourceInstanceList(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("cloudResourceList",list);
		
		return mv;
	}
	@RequestMapping("showSearch")
	@Action(description="搜索cloud_resource_class分页列表")
	public ModelAndView showSearch(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String name= RequestUtil.getString(request,"searchinput");
		ModelAndView mv=new ModelAndView("/cloud/cloudResource/resourceManagement/showSearch.jsp").addObject("searchinput",name);
		
		return mv;
	}
	/*点击搜索结果showSearch.jsp页面*/	
	@RequestMapping("showAllResource")
	public ModelAndView showAllResource(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mode =RequestUtil.getString(request,"mode");
		if(ContextUtil.getCurrentUserId()==null){
			mode = "nologin";
			}
		QueryFilter queryFilter = new QueryFilter(request,"cloudResourceItem");
		queryFilter.getFilters().put("mode", mode);
		List<CloudResourceInstance> list=cloudResourceService.getAllResourceInstanceList(queryFilter);
		ModelAndView mv=new ModelAndView("/cloud/cloudResource/resourceManagement/search.jsp").addObject("cloudResourceList",list);
		
		return mv;
	}
	/**
	 * 动态展示在线资源页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("resource")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int isLogin = 0;
		if(ContextUtil.getCurrentUserId()!=null){
			isLogin = 1;
			}
		
		QueryFilter queryFilter = new QueryFilter(request, "cloudResource");
		List<CloudResource> cloudResource = cloudResourceService.getAll();
		
		QueryFilter queryFilter1 = new QueryFilter(request, "cloudResource");
		queryFilter1.getFilters().put("levels",1);
		List<CloudResource> cloudResource1 = cloudResourceService.getAll(queryFilter1);
		
		QueryFilter queryFilter2 = new QueryFilter(request, "cloudResource",false);
		
		queryFilter2.getFilters().put("levels",3);
		List<CloudResource> cloudResource2 = cloudResourceService.getAll(queryFilter2);
		
		return getAutoView().addObject("isLogin",isLogin )
				.addObject("cloudResource",cloudResource )
				.addObject("cloudResource1",cloudResource1 )
				.addObject("cloudResource2",cloudResource2 );
	}
	
	
	
	@RequestMapping("moreResource")
	public ModelAndView moreResource(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type =RequestUtil.getString(request,"type");
		if(type.equals("3dprepare")){
			ModelAndView mav = new ModelAndView("/cloud/cloudResource/moreResource/3drp.jsp");
			return mav;
		}
		else if(type.equals("3dprint")){
			ModelAndView mav = new ModelAndView("/cloud/cloudResource/moreResource/3dprint.jsp");
			mav.addObject("fileId", RequestUtil.getString(request,"fileId"));
			return mav;
		}
		else if(type.equals("3dsuccess")){
			ModelAndView mav = new ModelAndView("/cloud/cloudResource/moreResource/3dsuccess.jsp");
			return mav;
		}
		else {
			return null;
		}
		
	}
	
	

}
