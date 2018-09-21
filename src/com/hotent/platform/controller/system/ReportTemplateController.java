package com.hotent.platform.controller.system;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.util.DateUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.system.GlobalType;
import com.hotent.platform.model.system.ReportTemplate;
import com.hotent.platform.service.system.GlobalTypeService;
import com.hotent.platform.service.system.ReportTemplateService;

/**
 * 对象功能:报表模板 控制器类
 * 开发公司:Deloitte
 * 开发人员:cjj
 * 创建时间:2012-04-12 09:59:47
 */
@Controller
@RequestMapping("/platform/system/reportTemplate/")
public class ReportTemplateController extends BaseController
{
	@Resource
	private ReportTemplateService reportTemplateService;
	
	@Resource
	private GlobalTypeService globalTypeService;
	
	/**
	 * 取得报表模板分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看报表模板分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<ReportTemplate> list=reportTemplateService.getAll(new QueryFilter(request,"reportTemplateItem"));
		ModelAndView mv=this.getAutoView().addObject("reportTemplateList",list);
		
		return mv;
	}
	
	/**
	 * 删除报表模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除报表模板")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "reportId");
			reportTemplateService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除报表模板成功!");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑报表模板")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long reportId=RequestUtil.getLong(request,"reportId");
		String returnUrl=RequestUtil.getPrePage(request);
		ReportTemplate reportTemplate=null;
		if(reportId!=0){
			reportTemplate= reportTemplateService.getById(reportId);
		}else{
			reportTemplate=new ReportTemplate();
		}
		List<GlobalType> list=globalTypeService.getByCatKey(GlobalType.CAT_REPORT, true);
		return getAutoView().addObject("reportTemplate",reportTemplate).addObject("returnUrl", returnUrl)
				.addObject("typelist", list);
	}

	/**
	 * 取得报表模板明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看报表模板明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"reportId");
		ReportTemplate reportTemplate = reportTemplateService.getById(id);
		return getAutoView().addObject("reportTemplate", reportTemplate);
	}
	
	/**
	 * 添加或更新报表模板
	 * @param request
	 * @param response
	 * @param reportTemplate 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新流程任务评论")
	public void save(MultipartHttpServletRequest request, HttpServletResponse response, ReportTemplate reportTemplate) throws Exception
	{
		String createTime = RequestUtil.getString(request, "tmpCreateTime");
		String st = "";
		if(StringUtil.isNotEmpty(createTime)){
			st = DateUtil.timeStrToDateStr(createTime);
		}
		
		Map<String, MultipartFile> files = request.getFileMap();
		Iterator<MultipartFile> it = files.values().iterator();
		
		if(it.hasNext()) {
			MultipartFile f = it.next();
			String oriFileName = f.getOriginalFilename();
		    
			//开始写入物理文件
			String filePath = FileUtil.getRootPath()+"WEB-INF\\"+ReportTemplate.targetPath+"\\"+oriFileName.replace("\\", File.separator);	
			FileUtil.writeByte(filePath, f.getBytes()) ;
			
		    // 向数据库中添加数据 
			reportTemplateService.saveReportTemplate(reportTemplate,
					"\\WEB-INF\\"+ReportTemplate.targetPath+"\\"+oriFileName.replace("\\", File.separator),
					st.length()>0?DateUtil.parseDate(st):new Date());
		}
		
		PrintWriter writer =  response.getWriter();
		String result = "{\"result\":"+ResultMessage.Success+",\"message\":\"上传报表模板成功\""+"}";
		writer.print(result);
	}

}
