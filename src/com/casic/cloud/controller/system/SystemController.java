package com.casic.cloud.controller.system;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.pub.fusionChart.CategoryList;
import com.casic.cloud.pub.fusionChart.DateSet;
import com.casic.cloud.pub.fusionChart.DateSet.Data;
import com.casic.cloud.pub.fusionChart.FusionChart;
import com.casic.cloud.pub.fusionChart.FusionChartFactory;
import com.casic.cloud.pub.util.DateUtil;
import com.casic.cloud.pub.util.MonthUtil;
import com.casic.cloud.service.config.business.BusinessChanceService;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SubSystemService;
@Controller
@RequestMapping("/cloud/system/")
public class SystemController extends BaseController{
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private BusinessChanceService businessChanceService;
	
	@RequestMapping("outline")
	public ModelAndView outline(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = this.getAutoView();
		this.getResources(mav, request);
		return mav;
	}
	
	private void getResources(ModelAndView mav,HttpServletRequest request){
		//查询资源菜单
		SubSystem currentSystem=subSystemService.getById(1L);
		ISysUser sysUser = ContextUtil.getCurrentUser();
		List<Resources> resourcesList=resourcesService.getCloudTopMenu(currentSystem,sysUser);
		Long resId = RequestUtil.getLong(request, "resId");
		if(resId.longValue()==0L && resourcesList.size()>0){
			resId = resourcesList.get(0).getResId();				
		}	
		if(resId.longValue()>0L){
			List<Resources> leftResourcesList = resourcesService.getChildrenByParentId(resId,true);
			mav.addObject("leftResourcesList", leftResourcesList);
		}
		mav.addObject("resourcesList", resourcesList);
	}
	
	/**
     * 业务量统计
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping("reportBusinessByMonthWithJson")
	@ResponseBody
	public FusionChart reportBusinessByMonthWithJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取统计类实例
		FusionChart fusionChart = FusionChartFactory.getInstance("Line2D");
		fusionChart.getChart().setCaption("业务量统计");
		fusionChart.getChart().setYaxisname("业务量");
		fusionChart.getChart().setXaxisname("月份");
		
		//增加标签显示,X轴			
		CategoryList categoryList = new CategoryList();
		for(int i=1; i<13; i++){
			categoryList.getCategory().add(categoryList.new Label(MonthUtil.getByInteger(i)));			
		}
		fusionChart.getCategories().add(categoryList);
		
		//采购业务量统计
		DateSet datasetPurchase = new DateSet();
		datasetPurchase.setSeriesname("采购业务量");
		for(int i=1; i<13; i++){
			Date beginTime = DateUtil.getBeginDateMonthByCurrentYear(i);
			Date endTime = DateUtil.getEndDateMonthByCurrentYear(i);
			
			//查询该月采购数量
			int count = businessChanceService.getPurchaseBusinessCount(beginTime, endTime, null);
			datasetPurchase.getData().add(datasetPurchase.new Data(String.valueOf(count)));			
		}
		fusionChart.getDataset().add(datasetPurchase);
		
		//营销业务量统计
		DateSet datasetSale = new DateSet();
		datasetSale.setSeriesname("营销业务量");
		for(int i=1; i<13; i++){
			Date beginTime = DateUtil.getBeginDateMonthByCurrentYear(i);
			Date endTime = DateUtil.getEndDateMonthByCurrentYear(i);
			
			//查询该月采购数量
			int count = businessChanceService.getSaleBusinessCount(beginTime, endTime, null);
			datasetSale.getData().add(datasetSale.new Data(String.valueOf(count)));			
		}
		fusionChart.getDataset().add(datasetSale);
		
		//生产业务量统计
		DateSet datasetManu = new DateSet();
		datasetManu.setSeriesname("生产业务量");
		for(int i=1; i<13; i++){
			Date beginTime = DateUtil.getBeginDateMonthByCurrentYear(i);
			Date endTime = DateUtil.getEndDateMonthByCurrentYear(i);
			
			//查询该月采购数量
			int count = businessChanceService.getManuBusinessCount(beginTime, endTime, null);
			datasetManu.getData().add(datasetManu.new Data(String.valueOf(count)));			
		}
		fusionChart.getDataset().add(datasetManu);
		
		//研发业务量统计
		DateSet datasetResearch = new DateSet();
		datasetResearch.setSeriesname("研发业务量");
		for(int i=1; i<13; i++){
			Date beginTime = DateUtil.getBeginDateMonthByCurrentYear(i);
			Date endTime = DateUtil.getEndDateMonthByCurrentYear(i);
			
			//查询该月采购数量
			int count = businessChanceService.getResearchBusinessCount(beginTime, endTime, null);
			datasetResearch.getData().add(datasetResearch.new Data(String.valueOf(count)));			
		}
		fusionChart.getDataset().add(datasetResearch);
		
		//服务业务量统计
		DateSet datasetService = new DateSet();
		datasetService.setSeriesname("服务业务量");
		for(int i=1; i<13; i++){
			Date beginTime = DateUtil.getBeginDateMonthByCurrentYear(i);
			Date endTime = DateUtil.getEndDateMonthByCurrentYear(i);
			
			//查询该月采购数量
			int count = businessChanceService.getServiceBusinessCount(beginTime, endTime, null);
			datasetService.getData().add(datasetService.new Data(String.valueOf(count)));			
		}
		fusionChart.getDataset().add(datasetService);
		
		return fusionChart;
	}
}
