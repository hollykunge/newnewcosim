package com.hotent.platform.controller.system;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.hotent.core.annotion.Action;

import com.hotent.core.customertable.SqlTypeConst;
import com.hotent.core.db.JdbcHelper;
import com.hotent.core.db.entity.SQLField;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.mybatis.dialect.DB2Dialect;
import com.hotent.core.mybatis.dialect.MySQLDialect;
import com.hotent.core.mybatis.dialect.OracleDialect;
import com.hotent.core.mybatis.dialect.SQLServer2005Dialect;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import com.hotent.platform.model.system.SysCustomDisplay;
import com.hotent.platform.model.system.SysDataSource;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.SysCustomDisplayService;
import com.hotent.platform.service.system.SysDataSourceService;
import com.hotent.core.web.ResultMessage;

/**
 * 对象功能:自定义显示 控制器类 
 * 开发公司:宏天 
 * 开发人员:Raise 
 * 创建时间:2012-10-23 17:59:41
 */
@Controller
@RequestMapping("/platform/system/sysCustomDisplay/")
public class SysCustomDisplayController extends BaseController {

	@Resource
	private SysCustomDisplayService sysCustomDisplayService;
	@Resource
	private SysDataSourceService sysDataSourceService;
	/**
	 * 添加或更新自定义显示。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新自定义显示")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		SysCustomDisplay sysCustomDisplay = getFormObject(request);
		try {
			if (sysCustomDisplay.getId() == null || sysCustomDisplay.getId() == 0) {
				sysCustomDisplay.setId(UniqueIdUtil.genId());
				String dspTemplate = sysCustomDisplayService.generateDspTemplate(sysCustomDisplay,new HashMap<String, Object>());
				sysCustomDisplay.setDspTemplate(dspTemplate);
				sysCustomDisplayService.add(sysCustomDisplay);
				resultMsg = getText("record.added", "自定义显示");
			} else {
				String dspTemplate = sysCustomDisplayService.generateDspTemplate(sysCustomDisplay,new HashMap<String, Object>());
				sysCustomDisplay.setDspTemplate(dspTemplate);
				sysCustomDisplayService.update(sysCustomDisplay);
				resultMsg = getText("record.updated", "自定义显示");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			e.printStackTrace();
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"自定义显示失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	/**
	 * 取得 SysCustomDisplay 实体
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected SysCustomDisplay getFormObject(HttpServletRequest request) throws Exception {

		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));

		String json = RequestUtil.getString(request, "json");
		json=json.replace("''","'");
		JSONObject obj = JSONObject.fromObject(json);
		
		SysCustomDisplay sysCustomDisplay = (SysCustomDisplay) JSONObject.toBean(obj, SysCustomDisplay.class);
		if(sysCustomDisplay.getPaged()!=1){
			sysCustomDisplay.setPageSize(0);
		}
		return sysCustomDisplay;
	}

	/**
	 * 取得自定义显示分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看自定义显示分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysCustomDisplay> list = sysCustomDisplayService.getAll(new QueryFilter(request, "sysCustomDisplayItem"));
		ModelAndView mv = this.getAutoView().addObject("sysCustomDisplayList", list);

		return mv;
	}

	/**
	 * 删除自定义显示
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除自定义显示")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			sysCustomDisplayService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除自定义显示成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑自定义显示
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑自定义显示")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String returnUrl = RequestUtil.getPrePage(request);
		SysCustomDisplay sysCustomDisplay = sysCustomDisplayService.getById(id);
		
		List<Map<String,Object>> templates =getTemplates();
		
		List<SysDataSource> dataSources = sysDataSourceService.getAll();
		return getAutoView()
				.addObject("sysCustomDisplay", sysCustomDisplay)
				.addObject("dataSources", dataSources)
				.addObject("returnUrl", returnUrl)
				.addObject("templates", templates);
	}

	private List<Map<String,Object>> getTemplates(){
		List<Map<String,Object>> templates=new ArrayList<Map<String,Object>>();
		
		String templatePath = FileUtil. getClassesPath() + "template" + File.separator +"custom" + File.separator;
		String xml= FileUtil.readFile(templatePath +"templates.xml");
		Document document=Dom4jUtil.loadXml(xml);
		Element root=document.getRootElement();
		List<Element> list=root.elements();
		for(Element element:list){
			Map<String,Object> template=new HashMap<String, Object>();
			String file=element.attributeValue("file");
			String name=element.attributeValue("name");
			String setting=element.attributeValue("setting");
			if(setting.endsWith(".jsp")){
				setting=setting.substring(0, setting.length()-3)+"ht";
			}
			template.put("file", file);
			template.put("name", name);
			template.put("setting", setting);
			templates.add(template);
		}
		return templates;
	}
	
	/**
	 * 取得自定义显示明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看自定义显示明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		SysCustomDisplay sysCustomDisplay = sysCustomDisplayService.getById(id);
		return getAutoView().addObject("sysCustomDisplay", sysCustomDisplay);
	}

	/**
	 * 预览自定义显示
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("preview")
	public ModelAndView preview(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv=getAutoView();
		Map<String,Object> params=RequestUtil.getQueryMap(request);
		Long id=RequestUtil.getLong(request, "__displayId");
//		String currentURI=request.getRequestURI();
//		String baseURL=currentURI.replace("/preview.ht", "/getDisplay.ht");
//		params.put("__baseDspURL", baseURL);
		params.put("__tic", "");
		String html = sysCustomDisplayService.getDisplay(id, params);
		mv.addObject("html", html);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("getDisplay")
	public Map<String,Object> getDisplay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		Map<String,Object> params=RequestUtil.getQueryMap(request);
		Long id=RequestUtil.getLong(request, "__displayId");
		String html = sysCustomDisplayService.getDisplay(id, params);
		map.put("html", html);
		return map;
	}

	/**
	 * 与选择自定义显示对话框配合使用，显示供选择的自定义对话框
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selector")
	@Action(description = "查看SYS_CUSTOM_DISPLY选择器")
	public ModelAndView selector(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysCustomDisplay> list = sysCustomDisplayService.getAll(new QueryFilter(request, "sysCustomDisplayItem"));
		ModelAndView mv = this.getAutoView().addObject("sysCustomDisplayList", list);
		return mv;
	}

	@RequestMapping("templateList")
	@Action(description = "模板列表")
	public ModelAndView templateList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		return mv;
	}
	
	/**
	 * 根据自定义显示的ID，取得相应的根据模板的生成的Html
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
//	@ResponseBody
//	@RequestMapping("getDisplay")
	public Map<String, Object> getDisply(HttpServletRequest request, HttpServletResponse response) {
		Long id = RequestUtil.getLong(request, "id");
		Map<String, Object> map = new HashMap<String, Object>();
		boolean status = true;
		String resultMsg = null;
		// 取得SysCustomDisplay（数据查询显示）对象
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String dataShowHtml = sysCustomDisplayService.getDisplay(id, params);
			map.put("customDisplay", dataShowHtml);
		} catch (Exception e) {
			status = false;
			resultMsg = e.getMessage();
		}
		map.put("status", status);
		map.put("resultMsg", resultMsg);
		return map;
	}




	
	/**
	 * 根据数据源获取JdbcHelper。
	 * 
	 * @param dsName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private JdbcHelper getJdbcHelper(String dsName) throws Exception {
		SysDataSource sysDataSource = sysDataSourceService.getByAlias(dsName);
		JdbcHelper jdbcHelper = JdbcHelper.getInstance();
		jdbcHelper.init(dsName, sysDataSource.getDriverName(), sysDataSource.getUrl(), sysDataSource.getUserName(), sysDataSource.getPassword());
		jdbcHelper.setCurrentDb(dsName);
		Dialect dialect = getDialect(sysDataSource.getDbType());
		jdbcHelper.setDialect(dialect);
		return jdbcHelper;
	}

	/**
	 * 获取方言。
	 * 
	 * @param dbType
	 * @return
	 * @throws Exception
	 */
	private Dialect getDialect(String dbType) throws Exception {
		Dialect dialect;
		if (dbType.equals(SqlTypeConst.ORACLE)) {
			dialect = new OracleDialect();
		} else if (dbType.equals(SqlTypeConst.SQLSERVER)) {
			dialect = new SQLServer2005Dialect();
		} else if (dbType.equals(SqlTypeConst.DB2)) {
			dialect = new DB2Dialect();
		} else if (dbType.equals(SqlTypeConst.MYSQL)) {
			dialect = new MySQLDialect();
		} else {
			throw new Exception("没有设置合适的数据库类型");
		}
		return dialect;

	}
	
	@ResponseBody
	@RequestMapping("validateScript")
	public Map<String,Object> validateScript(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map=new HashMap<String, Object>();
		boolean status=true;
		String resultMsg="";
		boolean supportSubSelect=true;
		String script=RequestUtil.getString(request, "script");
		script=script.replaceAll("''", "'");
		String dsName=RequestUtil.getString(request, "dsName");
		List<SQLField> sqlFields=null;
		try{
			sqlFields=sysCustomDisplayService.getSQLFieldsBySQL(dsName, script);
			supportSubSelect = checkSupportSubSelect(dsName, script);
		}catch (Exception e) {
			e.printStackTrace();
			status=false;
			resultMsg=e.getMessage();
		}
		map.put("status", status);
		map.put("resultMsg", resultMsg);
		map.put("supportSubSelect", supportSubSelect);
		map.put("sqlFields", sqlFields);
		return map;
	}
	
	
	/**
	 * 判断SQL是否支持作为子查询。
	 * @param dsName
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	private boolean checkSupportSubSelect(String dsName,String sql) throws Exception{
		SysDataSource sysDataSource = sysDataSourceService.getByAlias(dsName);
		String dbType = sysDataSource.getDbType();
		Dialect dialect = getDialect(dbType);
		JdbcHelper jdbcHelper=getJdbcHelper(dsName);
		JdbcTemplate jdbcTemplate=jdbcHelper.getJdbcTemplate();
		try{
			String totalSQL  = "SELECT a.* FROM (" + sql + ")  a";
			jdbcTemplate.execute(totalSQL);
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("getData")
	@ResponseBody
	@Action(description="获取数据")
	Map<String,Object> getData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		Map<String,Object> params=RequestUtil.getQueryMap(request);
		Long displayId=RequestUtil.getLong(request, "__displayId");
		List<Map<String, Object>> dataMaps=sysCustomDisplayService.getDataMaps(displayId, params);
		String json = JSONArray.fromObject(dataMaps).toString();
		map.put("dataMaps", json);
		return map;
	}
	
	@RequestMapping("editDspTemplate")
	public ModelAndView editDspTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		SysCustomDisplay sysCustomDisplay = null;
		sysCustomDisplay = sysCustomDisplayService.getById(id);
		return getAutoView().addObject("sysCustomDisplay", sysCustomDisplay);
	}
	
	@RequestMapping("saveTemplate")
	@Action(description="修改自定义显示的显示的模板")
	public void saveTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resultMsg="";
		Long id = RequestUtil.getLong(request, "id");
		String dspTemplate=request.getParameter("dspTemplate");
		SysCustomDisplay sysCustomDisplay = sysCustomDisplayService.getById(id);
		sysCustomDisplay.setDspTemplate(dspTemplate);
		sysCustomDisplayService.update(sysCustomDisplay);
		resultMsg=getText("record.updated","自定义表显示 显示模板");
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
//	public SysCustomDisplay initCustomDisplay(RequestContext context){
//		Long id = context.getRequestScope().getLong("id",0L);
//		
//		SysCustomDisplay sysCustomDisplay=null;
//		if(id==0){
//			sysCustomDisplay=new SysCustomDisplay();
//			sysCustomDisplay.setName("hello world");
//		}else{
//			sysCustomDisplay=sysCustomDisplayService.getById(id);
//		}
//		return sysCustomDisplay;
//	}
//	
//	public String editBase(RequestContext context){
//		return "";
//	}
//	
//	public String edit(RequestContext context){
//		return "";
//	}
}
