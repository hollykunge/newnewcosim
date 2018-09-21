package com.hotent.platform.controller.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.hotent.core.annotion.Action;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.page.PageBean;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.BpmNodeScript;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.system.Dictionary;
import com.hotent.platform.model.system.Script;
import com.hotent.platform.model.system.Seal;
import com.hotent.platform.service.system.DictionaryService;
import com.hotent.platform.service.system.ScriptService;
import com.hotent.platform.service.system.SysFileService;

/**
 * 对象功能:脚本管理 控制器类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2012-01-05 12:01:20
 */
@Controller
@RequestMapping("/platform/system/script/")
public class ScriptController extends BaseController
{
	@Resource
	private ScriptService scriptService;
	
	
	/**
	 * 取得脚本管理分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看脚本管理分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		//List<Dictionary> dicList= dictionaryService.getByNodeKey(Dictionary.ScriptType);
		List<String> categoryList=scriptService.getDistinctCategory();
		List<Script> list=scriptService.getAll(new QueryFilter(request,"scriptItem"));
		ModelAndView mv=this.getAutoView().addObject("scriptList",list);
		mv.addObject("categoryList", categoryList);
		
		return mv;
	}
	
	/**
	 * 脚本选择对话框
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dialog")
	@Action(description="脚本选择")
	public ModelAndView dialog(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv=this.getAutoView();
		return mv;
	}
	
	/**
	 * 与doalog页面一起使用。通过Iframe，在Dialog页面中显示脚本，供选择。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selector")
	@Action(description="脚本选择器")
	public ModelAndView selector(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<String> categoryList=scriptService.getDistinctCategory();
		QueryFilter queryFilter=new QueryFilter(request,"scriptItem");
		PageBean pageBean=queryFilter.getPageBean();
		pageBean.setPagesize(10);
		List<Script> list=scriptService.getAll(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("scriptList",list);
		mv.addObject("categoryList", categoryList);
		
		return mv;
	}
	
	@RequestMapping("getScripts")
	@ResponseBody
	public List<Script> getScripts(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Script> list=scriptService.getAll();		
		return list;
	}
	
	/**
	 * 删除脚本管理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除脚本管理")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultMessage message=null;
		String preUrl= RequestUtil.getPrePage(request);
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			scriptService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除脚本成功!");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑脚本管理")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		List<String> categoryList=scriptService.getDistinctCategory();
		String returnUrl=RequestUtil.getPrePage(request);
		Script script=null;
		if(id!=0){
			 script= scriptService.getById(id);
		}else{
			script=new Script();
		}
		return getAutoView().addObject("script",script)
				.addObject("categoryList", categoryList)
				.addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得脚本管理明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看脚本管理明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		Script script = scriptService.getById(id);		
		return getAutoView().addObject("script", script);
	}
	
	@RequestMapping("export")
	@Action(description="导出脚本")
	public void export(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
		if(BeanUtils.isNotEmpty(lAryId)){
			Calendar now=Calendar.getInstance();				
			String localTime=now.get(Calendar.YEAR)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.DATE);
			String strXml=scriptService.exportXml(lAryId);
			response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition","attachment;filename=Script"+localTime+".xml");
	        response.getWriter().write(strXml);
	        response.getWriter().flush();
	        response.getWriter().close();			
		}		
	}
	
	/**
	 * 导入表的XML。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("importXml")
	@Action(description = "导入自定义表",operateType="自定义表")
	public void importXml(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {	
		MultipartFile fileLoad = request.getFile("xmlFile");
		scriptService.importXml(fileLoad.getInputStream());
		ResultMessage message=null;		
		message=new ResultMessage(ResultMessage.Success,"导入成功!");
		writeResultMessage(response.getWriter(), message);
	}
	
}
