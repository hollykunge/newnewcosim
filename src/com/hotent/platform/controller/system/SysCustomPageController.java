package com.hotent.platform.controller.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.displaytag.util.ParamEncoder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;
import com.hotent.core.engine.FreemarkEngine;

import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.hotent.platform.model.system.SysCustomPage;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.SysCustomDisplayService;
import com.hotent.platform.service.system.SysCustomPageService;
import com.hotent.platform.service.system.SysTableManageService;
import com.hotent.core.web.ResultMessage;

import freemarker.template.TemplateException;

/**
 * 对象功能:自定义页面 控制器类
 * 开发者:云雀小组
 * 开发人员:Raise
 * 创建时间:2012-10-29 12:00:23
 */
@Controller
@RequestMapping("/platform/system/sysCustomPage/")
public class SysCustomPageController extends BaseController
{
	@Resource
	private SysCustomPageService sysCustomPageService;
	@Resource
	private SysCustomDisplayService sysCustomDisplayService;
	@Resource
	SysTableManageService sysTableManageService;
	
	/**
	 * 添加或更新自定义页面。
	 * @param request
	 * @param response
	 * @param sysCustomPage 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新自定义页面")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		SysCustomPage sysCustomPage=getFormObject(request);
		try{
			if(sysCustomPage.getId()==null||sysCustomPage.getId()==0){
				sysCustomPage.setId(UniqueIdUtil.genId());
				sysCustomPageService.add(sysCustomPage);
				resultMsg=getText("record.added","自定义页面");
			}else{
			    sysCustomPageService.update(sysCustomPage);
				resultMsg=getText("record.updated","自定义页面");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"自定义页面失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}
	
	/**
	 * 取得 SysCustomPage 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected SysCustomPage getFormObject(HttpServletRequest request) throws Exception {
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		SysCustomPage sysCustomPage = (SysCustomPage)JSONObject.toBean(obj, SysCustomPage.class);
		return sysCustomPage;
    }
	
	/**
	 * 取得自定义页面分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看自定义页面分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<SysCustomPage> list=sysCustomPageService.getAll(new QueryFilter(request,"sysCustomPageItem"));
		ModelAndView mv=this.getAutoView().addObject("sysCustomPageList",list);
		
		return mv;
	}
	
	/**
	 * 删除自定义页面
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除自定义页面")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			sysCustomPageService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除自定义页面成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑自定义页面
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑自定义页面")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		SysCustomPage sysCustomPage=sysCustomPageService.getById(id);
		
		return getAutoView().addObject("sysCustomPage",sysCustomPage).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得自定义页面明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看自定义页面明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		SysCustomPage sysCustomPage = sysCustomPageService.getById(id);	
		return getAutoView().addObject("sysCustomPage", sysCustomPage);
	}
	
	
	/**
	 * 预览自定义页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("show")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Long id=RequestUtil.getLong(request,"id",-1);
		String baseURL = request.getRequestURI();
		Map<String,Object> paramsMap = RequestUtil.getQueryMap(request);
		//第一次请打开页面时，id有值。在页面内导航，如对某个表翻页时，id的值已经不存在了，时间要通过__pageid__取得对应id值
		if(id==-1){
			id=Long.parseLong((String) paramsMap.get("__pageid__"));
		}else{
			paramsMap.put("__pageid__", id);
		}
		paramsMap.put("__baseURL", baseURL);
		String __baseDspURL=request.getContextPath()+"/platform/system/sysCustomDisplay/getDisplay.ht";
		paramsMap.put("__baseDspURL", __baseDspURL);
		SysCustomPage sysCustomPage = sysCustomPageService.getById(id);
		String content = replaceFreeMarkerTemplate(sysCustomPage.getTemplate(),paramsMap);
		sysCustomPage.setContent(content);
		ModelAndView mv=getAutoView();
		mv.addObject("sysCustomPage", sysCustomPage);
		return mv;
	}

	/**
	 * 根据在UEditor中编辑的模板中的元数据，将各个显示的组件进行对应的替换
	 * @param html
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private String replaceFreeMarkerTemplate(String html,Map<String,Object> map) throws Exception{
		String template = null;
		Document doc= Jsoup.parseBodyFragment(html);
		//自定义显示组件
		handleCustomDisplayComp(doc, map);
		//自定义表管理组件
		handleTableManageComp(doc, map);
		
		template=doc.body().html();
		template=StringUtil.unescapeHtml(template);
		return template;
	}

	/**
	 * 处理自定义显示组件
	 * @param doc
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	private Document handleCustomDisplayComp(Document doc,Map<String,Object> map) throws Exception{
		Elements list=doc.select("div[comptype=custom-display]");
		for(Iterator<Element> it= list.iterator();it.hasNext();){
			Element el=it.next();
			String displayId_str=el.attr("displayId");
			String customName=el.attr("customName");
			Long displayId=Long.parseLong(displayId_str);
//			ParamEncoder paramEncoder=new ParamEncoder(customName);
//			String  tableIdCode = paramEncoder.encodeParameterName("");
//			map.put("__tic", tableIdCode);
			String displyHtml=sysCustomDisplayService.getDisplay(displayId, map);
			el.html(displyHtml);
		}
		return doc;
	}

	
	/**
	 * 处理自定义表管理组件
	 * @param doc
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private Document handleTableManageComp(Document doc,Map<String,Object> map)throws Exception{
		Elements list=doc.select("div[comptype=custom-tablemanage]");
		for(Iterator<Element> it= list.iterator();it.hasNext();){
			Element el=it.next();
			String manageId_str=el.attr("manageId");
			String customName=el.attr("customName");
			Long manageId=Long.parseLong(manageId_str);
			ParamEncoder paramEncoder=new ParamEncoder(customName);
			String  tableIdCode = paramEncoder.encodeParameterName("");
			map.put("tic", tableIdCode);
			String displyHtml=sysTableManageService.getDisplay(manageId, map);
			el.text(displyHtml);
		}
		return doc;
	}
}
