package com.casic.cloud.controller.config.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.config.business.BusinessChance;
import com.casic.cloud.model.config.businessDevchase.BusinessDevchase;
import com.casic.cloud.model.config.businessMarketingchase.BusinessMarketingchase;
import com.casic.cloud.model.config.businessPproducechase.BusinessPproducechase;
import com.casic.cloud.model.config.businessPurchase.BusinessPurchase;
import com.casic.cloud.model.config.businessServechase.BusinessServechase;
import com.casic.cloud.model.config.capability.Capability;
import com.casic.cloud.model.config.info.Info;
import com.casic.cloud.model.system.news.News;
import com.casic.cloud.pub.fusionChart.FusionChart;
import com.casic.cloud.pub.fusionChart.FusionChartFactory;
import com.casic.cloud.pub.fusionChart.LabelValue;
import com.casic.cloud.pub.util.DateUtil;
import com.casic.cloud.pub.util.MonthUtil;
import com.casic.cloud.service.compass.SearchServiceBean;
import com.casic.cloud.service.config.business.BusinessChanceService;
import com.casic.cloud.service.config.businessDevchase.BusinessDevchaseService;
import com.casic.cloud.service.config.businessMarketingchase.BusinessMarketingchaseService;
import com.casic.cloud.service.config.businessPproducechase.BusinessPproducechaseService;
import com.casic.cloud.service.config.businessPurchase.BusinessPurchaseService;
import com.casic.cloud.service.config.businessServechase.BusinessServechaseService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
/**
 *<pre>
 * 对象功能:商机 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:21:17
 *</pre>
 */
//@Controller
//@RequestMapping("/cloud/config/business/")
public class BusinessChanceController extends BaseController
{
	@Resource
	private BusinessChanceService businessChanceService;
	@Resource
	private SearchServiceBean<BusinessDevchase> searchServiceDev;
	
	@Resource
	private SearchServiceBean<BusinessMarketingchase> searchServiceMarketing;
	@Resource
	private SearchServiceBean<BusinessPproducechase> searchServicePproduce;
	@Resource
	private SearchServiceBean<BusinessPurchase> searchServicePurchase;
	@Resource
	private SearchServiceBean<BusinessServechase> searchServiceServer;
	
	@Resource
	private BusinessDevchaseService businessDevchaseService;
	@Resource
	private BusinessMarketingchaseService businessMarketingService;
	@Resource
	private BusinessPproducechaseService businessPproduceService;
	@Resource
	private BusinessPurchaseService businessPurchaseService;
	@Resource
	private BusinessServechaseService businessServeService;
	 
	
	/**
	 * 添加或更新商机。
	 * @param request
	 * @param response
	 * @param businessChance 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新商机")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		String resultMsg=null;		
		BusinessChance businessChance=getFormObject(request);
		try{
			if(businessChance.getId()==null||businessChance.getId()==0){
				businessChance.setId(UniqueIdUtil.genId());
			
				businessChance.setOperateTime(new Date());
				businessChance.setCompanyId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
				businessChance.setCompanyName(ContextUtil.getCurrentOrgInfoFromSession().getName());
				
				businessChance.setUserId(ContextUtil.getCurrentUser().getUserId());
				businessChance.setUserName(ContextUtil.getCurrentUser().getFullname());
				businessChance.setPublishState("未审核");
				String type=RequestUtil.getString(request,"type");
				String content=RequestUtil.getString(request,"content");
				businessChance.setContent(content);
				String image=RequestUtil.getString(request, "image");
				if(image!=null&&image.length()>0){
					businessChance.setImage(image);
				}else{
					if(type.equals("采购商机")){
						businessChance.setImage("/images/product_img01.jpg");
					}else if(type.equals("营销商机")){
						businessChance.setImage("/images/product_img02.jpg");
					}else if(type.equals("生产商机")){
						businessChance.setImage("/images/product_img03.jpg");
					}else if(type.equals("服务商机")){
						businessChance.setImage("/images/product_img04.jpg");
					}else if(type.equals("研发商机")){
						businessChance.setImage("/images/product_img05.jpg");	
					}else {
						businessChance.setImage("/images/default-chance.jpg");
					}
					
					
				}
			
				
				businessChanceService.add(businessChance);
				resultMsg=getText("record.added","商机");
				
			}else{
			 
				businessChance.setCompanyName(ContextUtil.getCurrentOrgInfoFromSession().getName());
				
				 
				businessChance.setUserName(ContextUtil.getCurrentUser().getFullname());
			    businessChanceService.update(businessChance);
				resultMsg=getText("record.updated","商机");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 BusinessChance 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected BusinessChance getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		BusinessChance businessChance = (BusinessChance)JSONObject.toBean(obj, BusinessChance.class);
		
		return businessChance;
    }
	
	/**
	 * 取得商机分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("businessChance_list")
	@Action(description="查看商机列表")
	public ModelAndView businessChance_list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	QueryFilter queryFilter=	new QueryFilter(request,"businessChanceItem");
		queryFilter.getPageBean().setPagesize(8);
		List<BusinessChance> businessChanceList=businessChanceService.getAllBusinessChance("getAll",queryFilter);
		return getAutoView().addObject("businessChanceList", businessChanceList);
	}
	
	
	
	/**
	 * 取得商机分页列表
	 * @param request
	 * @param responsegetAllList.jsp
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_all_bcs")
	@Action(description="查看商机列表")
	public ModelAndView list_all_bcs(HttpServletRequest request,HttpServletResponse response) throws Exception
	
	{	
		QueryFilter queryFilter=	new QueryFilter(request,"businessChanceItem");
	
		 
		 
		 
		List<BusinessDevchase> businessChanceList=businessDevchaseService.getAllByEntid("getAllTT",queryFilter);
		 
		
		return getAutoView().addObject("businessChanceList", businessChanceList);
	}
	
	
	
	
	/**
	 * 取得商机分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看商机列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<BusinessChance> list=businessChanceService.getAll(new QueryFilter(request,"businessChanceItem"));
		ModelAndView mv=this.getAutoView().addObject("businessChanceList",list);
		
		return mv;
	}
	
	/**
	 * 删除商机
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除商机")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			businessChanceService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除商机成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑商机
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑商机")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		long typeid=RequestUtil.getLong(request,"type");
		String type=null;
		if(typeid==1){
			type="采购商机";
			
		}else if(typeid==2){
			type="营销商机";
			
		}else if(typeid==3){
			type="生产商机";
			
		}else if(typeid==4){
			type="服务商机";
			
		}else if(typeid==5){
			type="研发商机";
			
		}
		 
		BusinessChance businessChance=new BusinessChance();
		
		if(id!=0){
			businessChance=businessChanceService.getById(id);
		}else{
			businessChance.setType(type);
		}
		
		
		
		
		return getAutoView().addObject("businessChance",businessChance).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得商机明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看商机明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		
		
		BusinessChance businessChance = businessChanceService.getById(id);	
		return getAutoView().addObject("businessChance", businessChance);
	}
	@RequestMapping("getview")
	@Action(description="查看商机明细")
	public ModelAndView getview(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id=RequestUtil.getString(request,"id");
		String type =RequestUtil.getString(request,"type");
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("id", id);
		m.put("type", type);
		List<BusinessDevchase>  businessChanceList=businessDevchaseService.getByIdAnd(m);
		 
		BusinessDevchase businessDevchase=businessChanceList.get(0);
		return getAutoView().addObject("businessDevchase", businessDevchase);
	}
	
	
	
	@RequestMapping("purchaseList")
	@Action(description="查看采购商机列表")
	public ModelAndView purchaseList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter=	new QueryFilter(request,"businessChanceItem");
		queryFilter.getFilters().put("type", "采购商机");
		queryFilter.getFilters().put("company_id", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessChance> list=businessChanceService.getType("getType",queryFilter);
		ModelAndView mv=this.getAutoView().addObject("businessChanceList",list);
		
		return mv;
	}
	
	@RequestMapping("marketingList")
	@Action(description="查看营销商机列表")
	public ModelAndView marketingList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter=	new QueryFilter(request,"businessChanceItem");
		queryFilter.getFilters().put("type", "营销商机");
		queryFilter.getFilters().put("company_id", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessChance> list=businessChanceService.getType("getType",queryFilter);
		ModelAndView mv=this.getAutoView().addObject("businessChanceList",list);
		
		return mv;
	}
	
	@RequestMapping("produceList")
	@Action(description="查看生产商机列表")
	public ModelAndView produceList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter=	new QueryFilter(request,"businessChanceItem");
		queryFilter.getFilters().put("type", "生产商机");
		queryFilter.getFilters().put("company_id", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessChance> list=businessChanceService.getType("getType",queryFilter);
		ModelAndView mv=this.getAutoView().addObject("businessChanceList",list);
		
		return mv;
	}
	
	@RequestMapping("serveList")
	@Action(description="查看服务商机列表")
	public ModelAndView serveList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter=	new QueryFilter(request,"businessChanceItem");
		queryFilter.getFilters().put("type", "服务商机");
		queryFilter.getFilters().put("company_id", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessChance> list=businessChanceService.getType("getType",queryFilter);
		ModelAndView mv=this.getAutoView().addObject("businessChanceList",list);
		
		return mv;
	}
	
	@RequestMapping("developmentList")
	@Action(description="查看研发商机列表")
	public ModelAndView developmentList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter=	new QueryFilter(request,"businessChanceItem");
		queryFilter.getFilters().put("type", "研发商机");
		queryFilter.getFilters().put("company_id", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessChance> list=businessChanceService.getType("getType",queryFilter);
		ModelAndView mv=this.getAutoView().addObject("businessChanceList",list);
		
		return mv;
	}
	
	@RequestMapping("indexview")
	@Action(description="查看商机明细")
	public ModelAndView indexview(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("businessChance_id", id);
		List<BusinessChance> businessChance = businessChanceService.getByIdAnd(m);	
		return getAutoView().addObject("businessChance", businessChance);
	}
	
	@RequestMapping("getAllList")
	@Action(description="查看商机列表")
	public ModelAndView getAllList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	QueryFilter queryFilter=	new QueryFilter(request,"businessChanceItem");
		List<BusinessChance> businessChanceList=businessChanceService.getAllBusinessChance("getAllList",queryFilter);
		return getAutoView().addObject("businessChanceList", businessChanceList);
	}
	
	@RequestMapping("change")
	@Action(description="审核")
	public ModelAndView change(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		long typeid=RequestUtil.getLong(request,"type");
		String type=null;
		if(typeid==1){
			type="采购商机";
			
		}else if(typeid==2){
			type="营销商机";
			
		}else if(typeid==3){
			type="生产商机";
			
		}else if(typeid==4){
			type="服务商机";
			
		}else if(typeid==5){
			type="研发商机";
			
		}
		 
		BusinessChance businessChance=new BusinessChance();
		
		if(id!=0){
			businessChance=businessChanceService.getById(id);
		}else{
			businessChance.setType(type);
		}
		
		return getAutoView().addObject("businessChance",businessChance).addObject("returnUrl", returnUrl);
	}
	
	@RequestMapping("add")
	@Action(description="发布商机")
	public ModelAndView add(HttpServletRequest request) throws Exception
	{
		 
		String returnUrl=RequestUtil.getPrePage(request);
		
		//判断当前用户角色
		Collection<GrantedAuthority> authorities = ContextUtil.getCurrentUser().getAuthorities();
		 
		 
		BusinessChance businessChance=new BusinessChance();
		if(authorities.contains(new GrantedAuthorityImpl("cloud_ROLE_PUR_MANAGER"))  ){
			//采购主管
			businessChance.setType("采购商机");				
		}else if( authorities.contains(new GrantedAuthorityImpl("cloud_ROLE_SALE_MANAGER"))){
			//营销主管
			businessChance.setType("营销商机");
		}else if( authorities.contains(new GrantedAuthorityImpl("cloud_ROLE_MANUF_MANAGER"))){
			//生产主管
			businessChance.setType("生产商机");
		}else if( authorities.contains(new GrantedAuthorityImpl("cloud_ROLE_AFTERSALE"))){
			//服务主管
			businessChance.setType("服务商机");
		}else if( authorities.contains(new GrantedAuthorityImpl("cloud_ROLE_RESEARCH"))){
			//研发主管
			businessChance.setType("研发商机");
		}
		
		
		return getAutoView().addObject("businessChance",businessChance).addObject("returnUrl", returnUrl);
	}
	
	
	/**
	 * 取得商机搜索 分页列表
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
		 
		QueryFilter queryFilter = new QueryFilter(request,"businessChanceItem");
		String name=RequestUtil.getString(request, "username");
		queryFilter.getFilters().put("name","%"+name+"%");
		
		List<BusinessChance> businessChanceList=businessChanceService.getAll_query("getAll_query", queryFilter);
		return getAutoView().addObject("businessChanceList", businessChanceList);
	}
	
	@RequestMapping("moreBusinessChance")
	public ModelAndView moreBusinessChance(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int type=RequestUtil.getInt(request, "type");
		List<BusinessChance> businessChanceList=new ArrayList<BusinessChance>();
		QueryFilter queryFilter=	new QueryFilter(request,"businessChanceItem");
		if(type==1){
			//采购商机
			
			queryFilter.getFilters().put("type", "采购商机");
			businessChanceList=businessChanceService.getAllByType("getAllByType",queryFilter);
			
		}
		if(type==2){

			//营销商机
			
			queryFilter.getFilters().put("type", "营销商机");
			businessChanceList=businessChanceService.getAllByType("getAllByType",queryFilter);
			
		}
		if(type==3){

			//生产商机
			
			queryFilter.getFilters().put("type", "生产商机");
			businessChanceList=businessChanceService.getAllByType("getAllByType",queryFilter);
			
		}
		if(type==4){

			//服务商机
			
			queryFilter.getFilters().put("type", "服务商机");
			businessChanceList=businessChanceService.getAllByType("getAllByType",queryFilter);
			
		}
		
		
		
		if(type==5){

			//研发商机
			
			queryFilter.getFilters().put("type", "研发商机");
			businessChanceList=businessChanceService.getAllByType("getAllByType",queryFilter);
			
		}
		 
		return getAutoView().addObject("businessChanceList", businessChanceList);

	
		
	}
	@RequestMapping("search")
	@Action(description="搜索")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		  request.setCharacterEncoding("UTF-8");
		  response.setCharacterEncoding("UTF-8");
	      String keywords= RequestUtil.getString(request,"username");
          String businessChanceSelect=RequestUtil.getString(request, "select");
	
          List<BusinessDevchase> businessChance1=new ArrayList<BusinessDevchase>();
          List<BusinessMarketingchase> businessChance2=new ArrayList<BusinessMarketingchase>();
          List<BusinessPproducechase> businessChance3=new ArrayList<BusinessPproducechase>();
          List<BusinessPurchase> businessChance4=new ArrayList<BusinessPurchase>();
          List<BusinessServechase> businessChance5=new ArrayList<BusinessServechase>();
	      
	  //研发商机搜索
	  if(businessChanceSelect.equals("yanfa")){
	    
	      String[] searchFields1 = new String[] {"name", "content", "type","classid" ,"companyName", "userName"};
	  	  QueryFilter queryFilter1=	new QueryFilter(request,"businessChanceItem1");
	  	  queryFilter1.addFilter("keywords", keywords);
	  	  queryFilter1.getFilters().put("searchFields",searchFields1);
	      List<BusinessDevchase> temp1 = searchServiceDev.search(BusinessDevchase.class,queryFilter1);
	     
	      for(int i=0;i<temp1.size();i++){
	  		BusinessDevchase  t1 = businessDevchaseService.getById(temp1.get(i).getId());  		 			  
	  		businessChance1.add(t1);
	      }

	  }else 
	   if(businessChanceSelect.equals("yingxiao")){	      
	   //营销商机	     
	      String[] searchFields2 = new String[] {"name", "content", "type","classid" ,"companyName", "userName"};
	  	  QueryFilter queryFilter2=	new QueryFilter(request,"businessChanceItem2");
	  	  queryFilter2.addFilter("keywords", keywords);
	  	  queryFilter2.getFilters().put("searchFields",searchFields2);

	      List<BusinessMarketingchase> temp2 = searchServiceMarketing.search(BusinessMarketingchase.class,queryFilter2);
	      for(int i=0;i<temp2.size();i++){
	  		BusinessMarketingchase  t2 = businessMarketingService.getById(temp2.get(i).getId());  			  
	  			businessChance2.add(t2);
	  		}
	  }else 
	   if(businessChanceSelect.equals("shengchan")){      
	   //生产商机	    
	      String[] searchFields3 = new String[] {"name", "content", "type","classid" ,"companyName", "userName"};
	  	  QueryFilter queryFilter3=	new QueryFilter(request,"businessChanceItem3");
	  	  queryFilter3.addFilter("keywords", keywords);
	  	  queryFilter3.getFilters().put("searchFields",searchFields3);

	      List<BusinessPproducechase> temp3 = searchServicePproduce.search(BusinessPproducechase.class,queryFilter3);
	      for(int i=0;i<temp3.size();i++){

	  		BusinessPproducechase  t3 = businessPproduceService.getById(temp3.get(i).getId()); 			  
	  			businessChance3.add(t3);
	      }   
      }else 
       if(businessChanceSelect.equals("fuwu")){	      
	   //服务商机
	  	  String[] searchFields5 = new String[] {"name", "content", "type","classid","companyName", "userName"};
	  	  QueryFilter queryFilter5=	new QueryFilter(request,"businessChanceItem5");
	  	  queryFilter5.addFilter("keywords", keywords);
	  	  queryFilter5.getFilters().put("searchFields",searchFields5);

	  	  List<BusinessServechase> temp5 = searchServiceServer.search(BusinessServechase.class,queryFilter5);
	  	      
	  	  for(int i=0;i<temp5.size();i++){	
	  	  	BusinessServechase  t5 = businessServeService.getById(temp5.get(i).getId());			  
	  	  	    businessChance5.add(t5);

	  	  }
            
	  }else 
	   if(businessChanceSelect.equals("caigou")){	      
	   //采购商机	     
	      String[] searchFields4 = new String[] {"name", "content", "type","classid" ,"companyName", "userName"};
	  	  QueryFilter queryFilter4=	new QueryFilter(request,"businessChanceItem4");
	  	  queryFilter4.addFilter("keywords", keywords);
	  	  queryFilter4.getFilters().put("searchFields",searchFields4);

	      List<BusinessPurchase> temp4 = searchServicePurchase.search(BusinessPurchase.class,queryFilter4);
	      for(int i=0;i<temp4.size();i++){
	  		BusinessPurchase  t4 = businessPurchaseService.getById(temp4.get(i).getId());			  
	  			businessChance4.add(t4);
	      }    
	  }
	      
	  ModelAndView mv=this.getAutoView().addObject("businessChanceList1",businessChance1)
	    		                            .addObject("businessChanceList2",businessChance2)
	    		                            .addObject("businessChanceList3",businessChance3)
	    		                            .addObject("businessChanceList4",businessChance4)
	    		                            .addObject("businessChanceList5",businessChance5)
	    		  							.addObject("keywords",keywords);
	  return mv;
	}
		
	/**
	 * 某一年度工时分析图,不能跨年度(由前台判断)
	 * 供fusionchart去调用
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("reportWorkByMonthWithJson")
	@ResponseBody
	public FusionChart reportBusinessMonthWithJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return null;
	}
}
