package com.hotent.platform.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.displaytag.util.ParamEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hotent.core.annotion.Action;
import com.hotent.core.customertable.BaseTableMeta;
import com.hotent.core.customertable.ColumnModel;
import com.hotent.core.customertable.IDbView;
import com.hotent.core.customertable.TableModel;
import com.hotent.core.customertable.impl.TableMetaFactory;
import com.hotent.core.db.entity.SQLField;
import com.hotent.core.engine.GroovyScriptEngine;

import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.page.PageBean;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.form.BpmFormTemplate;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysTableManage;
import com.hotent.platform.model.system.SysDataSource;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SubSystemUtil;
import com.hotent.platform.service.system.SysTableManageService;
import com.hotent.platform.service.form.BpmFormTemplateService;
import com.hotent.platform.service.system.SysDataSourceService;
import com.hotent.core.web.ResultMessage;


/**
 * 对象功能:自定义表管理 控制器类 开发公司: 开发人员:ray 创建时间:2012-06-25 11:05:09
 */
@Controller
@RequestMapping("/platform/system/sysTableManage/")
public class SysTableManageController extends BaseController {
	@Resource
	private SysTableManageService sysTableManageService;
	@Resource
	private SysDataSourceService sysDataSourceService;
	@Resource
	private BpmFormTemplateService bpmFormTemplateService;
	@Resource
	private SubSystemService subSystemService;

	
	/**
	 * 添加或更新通用表单对话框。
	 * @param request
	 * @param response
	 * @param sysTableManage 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新自定义表管理")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg="";
		SysTableManage sysTableManage = getFormObject(request);
		if(StringUtil.isEmpty(sysTableManage.getDisplayField())){
			resultMsg=getText("未设置显示的字段");
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
			return;
		}		
		if(sysTableManage.getId()==0){
			sysTableManage.setId(UniqueIdUtil.genId());
			String alias=sysTableManage.getAlias();
			boolean isExist=sysTableManageService.isExistAlias(alias);
			if(isExist){
				resultMsg=getText("该别名已经存在！");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
				return;
			}
			//第一次解析数据模板
			String __deleteBaseURL = request.getRequestURI().replace("/save.ht", "/deleteData.ht");
			String __editBaseURL = request.getRequestURI().replace("/save.ht", "/editData.ht");
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("__deleteBaseURL", __deleteBaseURL);
			paramMap.put("__editBaseURL", __editBaseURL);
			String dspTemplate = sysTableManageService.generateDspTemplate(sysTableManage,paramMap);
			
			sysTableManage.setDspTemplate(dspTemplate);
			sysTableManageService.add(sysTableManage);
			resultMsg=getText("record.added","自定义表管理");
			
		}else{
			String alias=sysTableManage.getAlias();
			Long id=sysTableManage.getId();
			boolean isExist=sysTableManageService.isExistAliasForUpd(id, alias);
			if(isExist){
				resultMsg=getText("该别名已经存在！");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
				return;
			}
			//第一次解析数据模板
			String __deleteBaseURL = request.getRequestURI().replace("/save.ht", "/deleteData.ht");
			String __editBaseURL = request.getRequestURI().replace("/save.ht", "/editData.ht");
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("__deleteBaseURL", __deleteBaseURL);
			paramMap.put("__editBaseURL", __editBaseURL);
			String dspTemplate = sysTableManageService.generateDspTemplate(sysTableManage,paramMap);
			sysTableManage.setDspTemplate(dspTemplate);
			sysTableManageService.update(sysTableManage);
			resultMsg=getText("record.updated","自定义表管理");
		}
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}

	/**
	 * 取得自定义表管理分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看自定义表管理分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysTableManage> list = sysTableManageService.getAll(new QueryFilter(request, "sysTableManageItem"));
		ModelAndView mv = this.getAutoView().addObject("sysTableManageList", list);
		return mv;
	}

	/**
	 * 删除自定义表管理
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除自定义表管理")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			sysTableManageService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除自定义表管理成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description = "编辑自定义表管理")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String returnUrl = RequestUtil.getPrePage(request);
		SysTableManage sysTableManage = null;
		if (id != 0) {
			sysTableManage = sysTableManageService.getById(id);
		} else {
			sysTableManage = new SysTableManage();
		}
		List<SysDataSource> dsList = sysDataSourceService.getAll();
		
		List<BpmFormTemplate> templates = bpmFormTemplateService.getAllTableManageTemplate();

		return getAutoView()
				.addObject("sysTableManage", sysTableManage)
				.addObject("returnUrl", returnUrl)
				.addObject("dsList", dsList)
				.addObject("templates", templates);
	}

	/**
	 * 取得自定义表管理明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看自定义表管理明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		SysTableManage sysTableManage = sysTableManageService.getById(id);
		return getAutoView().addObject("sysTableManage", sysTableManage);
	}

	@RequestMapping("dialogObj")
	@Action(description = "查看自定义表管理明细")
	@ResponseBody
	public Map<String, Object> dialogObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String alias = RequestUtil.getString(request, "alias");
		Map<String, Object> map = new HashMap<String, Object>();
		SysTableManage sysTableManage = sysTableManageService.getByAlias(alias);
		if (sysTableManage != null) {
			map.put("sysTableManage", sysTableManage);
			map.put("success", 1);
		} else {
			map.put("success", 0);
		}

		return map;
	}

	/**
	 * 根据数据源，输入的对象类型，对象名称获取对象列表。
	 * 
	 * <pre>
	 *  1.对象类型为表。
	 *  	返回表的map对象。
	 *  2.对象为视图
	 *  	返回视图列表对象。
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("getByDsObjectName")
	@Action(description = "根据对象名称对象类型")
	@ResponseBody
	public Map getByDsObjectName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dsName = RequestUtil.getString(request, "dsName");
		String objectName = RequestUtil.getString(request, "objectName");
		int istable = RequestUtil.getInt(request, "istable");
		Map map = new HashMap();
		try {
			if (istable == 1) {
				BaseTableMeta meta = TableMetaFactory.getMetaData(dsName);
				Map<String, String> tableMap = meta.getTablesByName(objectName);
				map.put("tables", tableMap);
			} else {
				IDbView dbView = TableMetaFactory.getDbView(dsName);
				List<String> views = dbView.getViews(objectName);
				map.put("views", views);
			}
			map.put("success", "true");
		} catch (Exception ex) {
			map.put("success", "false");
		}
		return map;
	}

	/**
	 * 取得表或者视图的元数据对象。
	 * 
	 * <pre>
	 * 	根据数据源，对象名称，是否视图获取表或者视图的元数据对象。
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getObjectByDsObjectName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dsName = RequestUtil.getString(request, "dsName");
		String objectName = RequestUtil.getString(request, "objectName");
		int istable = RequestUtil.getInt(request, "istable");
		Map map = new HashMap();
		TableModel tableModel;
		try {
			// 加载表
			if (istable == 1) {
				BaseTableMeta meta = TableMetaFactory.getMetaData(dsName);
				tableModel = meta.getTableByName(objectName);
			} else {
				IDbView dbView = TableMetaFactory.getDbView(dsName);
				tableModel = dbView.getModelByViewName(objectName);
			}
			map.put("tableModel", tableModel);
			map.put("success", "true");
		} catch (Exception ex) {
			map.put("success", "false");
		}
		return map;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping("preview")
	public ModelAndView preview(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv=getAutoView();
		//取得当前页URL,如有参数则带参数
		String __baseURL = request.getRequestURI().replace("/preview.ht", "/getDisplay.ht");
		String __deleteBaseURL = request.getRequestURI().replace("/preview.ht", "/deleteData.ht");
		String __editBaseURL = request.getRequestURI().replace("/preview.ht", "/editData.ht");
		Map<String,Object> paramsMap = RequestUtil.getQueryMap(request);
		//取得传入参数ID
		Long id = RequestUtil.getLong(request,"__displayId__");
		paramsMap.put("__baseURL", __baseURL);
		paramsMap.put("__deleteBaseURL", __deleteBaseURL);
		paramsMap.put("__editBaseURL", __editBaseURL);
		String html =sysTableManageService.getDisplay(id, paramsMap);
		mv.addObject("html", html);
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("getDisplay")
	public Map<String,Object> getDisplay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		Map<String,Object> params=RequestUtil.getQueryMap(request);
		Long id=RequestUtil.getLong(request, "__displayId");
		String currentURI=request.getRequestURI();
		String __deleteBaseURL=currentURI.replace("/getDisplay.ht", "/deleteData.ht");
		String __editBaseURL=currentURI.replace("/getDisplay.ht", "/editData.ht");
		params.put("__baseURL", currentURI);
		params.put("__deleteBaseURL", __deleteBaseURL);
		params.put("__editBaseURL", __editBaseURL);
		String html = sysTableManageService.getDisplay(id, params);
		map.put("html", html);
		return map;
	}
	
	/**
	 * 取得 SysTableManage 实体
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected SysTableManage getFormObject(HttpServletRequest request) throws Exception {
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		String json = RequestUtil.getString(request, "json");
		json = json.replace("''", "'");
		JSONObject obj = JSONObject.fromObject(json);
		SysTableManage sysTableManage = (SysTableManage) JSONObject.toBean(obj,SysTableManage.class);
		return sysTableManage;
	}
	
	/**
	 * 自定义表管理选择对话框
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dialog")
	public ModelAndView dialog(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv=getAutoView();
		return mv;
	}
	
	/**
	 * 自定义表管理选择器
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selector")
	public ModelAndView selector(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysTableManage> list = sysTableManageService.getAll(new QueryFilter(request, "sysTableManageItem"));
		ModelAndView mv = this.getAutoView().addObject("sysTableManageList", list);
		return mv;
	}
	
	/**
	 * 编辑显示模板
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editDspTemplate")
	public ModelAndView editDspTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		SysTableManage sysTableManage = null;
		sysTableManage = sysTableManageService.getById(id);
		return getAutoView().addObject("sysTableManage", sysTableManage);
	}
	
	/**
	 * 保存模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveTemplate")
	@Action(description="添加或更新自定义表管理的模板")
	public void saveTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resultMsg="";
		Long id = RequestUtil.getLong(request, "id");
		String dspTemplate=RequestUtil.getString(request, "dspTemplate");
		SysTableManage sysTableManage = sysTableManageService.getById(id);
		sysTableManage.setDspTemplate(dspTemplate);
		sysTableManageService.update(sysTableManage);
		resultMsg=getText("record.updated","自定义表管理显示模板");
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
	
	/**
	 * 选择表/视图
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selectTable")
	public ModelAndView selectTable(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String dsName=RequestUtil.getString(request, "dsName");
		ModelAndView mv=getAutoView();

		mv.addObject("dsName", dsName);
		return mv;
	}
	
	/**
	 * 选择表/视图 选择器
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selectTableSelector")
	public ModelAndView getSelectTableSelector(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String dsName=RequestUtil.getString(request, "dsName");
		String tableName=RequestUtil.getString(request, "tableName");
		String viewName=RequestUtil.getString(request, "viewName");
		String tabItem=RequestUtil.getString(request, "tabItem","table");
		ModelAndView mv=getAutoView();
		
		QueryFilter tableQueryFilter=new QueryFilter(request,"tables");
		PageBean tablePageBean =tableQueryFilter.getPageBean();
		//Set Default Page Size;
		ParamEncoder paramEncoder = new ParamEncoder("tables");
		String tableIdCode=paramEncoder.encodeParameterName("");
		int pageSize=RequestUtil.getInt(request, tableIdCode+"z",-1);
		if(pageSize==-1){
			tablePageBean.setPagesize(10);
		}
		
		tableQueryFilter.setForWeb();
		BaseTableMeta meta = TableMetaFactory.getMetaData(dsName);
		List<TableModel> tableMap = meta.getTablesByName(tableName, tablePageBean);
		
		IDbView dbView = TableMetaFactory.getDbView(dsName);
		//Get pageBean and set paging information in request 
		QueryFilter viewQueryFilter=new QueryFilter(request,"views");
		PageBean viewPageBean =viewQueryFilter.getPageBean();
		
		//Set Default Page Size;
		paramEncoder = new ParamEncoder("views");
		tableIdCode=paramEncoder.encodeParameterName("");
		pageSize=RequestUtil.getInt(request, tableIdCode+"z",-1);
		if(pageSize==-1){
			tablePageBean.setPagesize(10);
		}
		
		viewQueryFilter.setForWeb();
		
		List<String> views = dbView.getViews(viewName,viewPageBean);
		
		mv.addObject("tableMap", tableMap);
		mv.addObject("views", views);
		mv.addObject("dsName", dsName);
		mv.addObject("tabItem", tabItem);
		return mv;
	}
	

	/**
	 * 设置字段对话框。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTableColumns")
	@ResponseBody
	public Map<String,Object> getTableColumns(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		String dsName = RequestUtil.getString(request, "dsName");
		String tableName = RequestUtil.getString(request, "tableName");
		String tableType = RequestUtil.getString(request, "tableType");


		TableModel tableModel;
		// 表
		if (tableType.equalsIgnoreCase("table")) {
			BaseTableMeta meta = TableMetaFactory.getMetaData(dsName);
			tableModel = meta.getTableByName(tableName);
		}
		// 视图处理
		else {
			IDbView dbView = TableMetaFactory.getDbView(dsName);
			tableModel = dbView.getModelByViewName(tableName);
		}
		
		List<Map<String,Object>> columns=new ArrayList<Map<String,Object>>();
		for(ColumnModel column:tableModel.getColumnList()){
			Map<String,Object> colmap=new HashMap<String, Object>();
			colmap.put("na", column.getName());
			colmap.put("ty", column.getColumnType());
			colmap.put("cm", column.getComment());
			columns.add(colmap);
		}
		map.put("columns", columns);
		return map;
	}
	
	/**
	 * 删除数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("deleteData")
	public void deleteData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		Map<String,Object> params=RequestUtil.getQueryMap(request);
		Long sysTableManageId=RequestUtil.getLong(request, "__displayId__");
		try {
			sysTableManageService.deleteData(sysTableManageId,params);
			message = new ResultMessage(ResultMessage.Success, "删除成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 编辑表数据。<br/>
	 * 主码不能编辑，在未显示的字段不能编辑。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editData")
	public ModelAndView editData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long sysTableManageId=RequestUtil.getLong(request, "__displayId__");
		String returnUrl = RequestUtil.getPrePage(request);
		SysTableManage sysTableManage=sysTableManageService.getById(sysTableManageId);
		List<SQLField> displayFiedls = sysTableManage.getDisplayList();
		TableModel tableModel=sysTableManageService.getTableModel(sysTableManage);
		List<ColumnModel> columnModels=tableModel.getColumnList();
		List<ColumnModel> displayColumns=new ArrayList<ColumnModel>();
		for(ColumnModel column:columnModels){
			for(SQLField field:displayFiedls){
				if(field.getName().equalsIgnoreCase(column.getName())){
					column.setComment(field.getComment());
					displayColumns.add(column);
					break;
				}
			}
		}
		
		Map<String,Object> pksMap=new HashMap<String, Object>();
		List<ColumnModel> pkColumns=tableModel.getPrimayKey();
		for(ColumnModel column:pkColumns){
			String colName = column.getName();
			String colType=column.getColumnType();
			String key="__pk__"+colName;
			Object value=null;
			if(colType.equalsIgnoreCase(ColumnModel.COLUMNTYPE_INT)){
				value=RequestUtil.getInt(request, key);
			}else if(colType.equalsIgnoreCase(ColumnModel.COLUMNTYPE_NUMBER)){
				value=RequestUtil.getLong(request, key);
			}else if(colType.equalsIgnoreCase(ColumnModel.COLUMNTYPE_VARCHAR)){
				value=RequestUtil.getString(request, key);
			}else if(colType.equalsIgnoreCase(ColumnModel.COLUMNTYPE_DATE)){
				value=RequestUtil.getDate(request, key);
			}else{
				value=RequestUtil.getString(request, key);
			}
			pksMap.put(colName, value);
		}
		
		Map<String,Object> dataMap = sysTableManageService.getSingleData(sysTableManage, pksMap);
		
		ModelAndView mv=getAutoView();
		mv.addObject("returnUrl", returnUrl);
		mv.addObject("sysTableManageId", sysTableManageId);
		mv.addObject("displayColumns", displayColumns);
		mv.addObject("pkColumns", pkColumns);
		mv.addObject("dataMap", dataMap);
		return mv;
	}
	
	/**
	 * 保存数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveData")
	@Action(description="更新表数据记录")
	public void saveData(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg="";
		Long sysTableManageId=RequestUtil.getLong(request, "sysTableManageId");
		SysTableManage sysTableManage = sysTableManageService.getById(sysTableManageId);
		
		TableModel tableModel = sysTableManageService.getTableModel(sysTableManage);
		List<ColumnModel> columns = tableModel.getColumnList();
		List<SQLField> displayFiedls = sysTableManage.getDisplayList();
		Map<String,Object> map=new HashMap<String, Object>();
		//取得所有要更新的字段名和值
		for(ColumnModel column:columns){
			//如果是主键
			if(column.getIsPk()){
				continue;
			}
			for(SQLField field:displayFiedls){
				if(field.getName().equalsIgnoreCase(column.getName())){
					String colName = column.getName();
					String colType=column.getColumnType();
					Object value=null;
					if(colType.equalsIgnoreCase(ColumnModel.COLUMNTYPE_INT)){
						value=RequestUtil.getInt(request, colName);
					}else if(colType.equalsIgnoreCase(ColumnModel.COLUMNTYPE_NUMBER)){
						value=RequestUtil.getLong(request, colName);
					}else if(colType.equalsIgnoreCase(ColumnModel.COLUMNTYPE_VARCHAR)){
						value=RequestUtil.getString(request, colName);
					}else if(colType.equalsIgnoreCase(ColumnModel.COLUMNTYPE_DATE)){
						value=RequestUtil.getDate(request, colName);
					}else{
						value=RequestUtil.getString(request, colName);
					}
					map.put(colName, value);
					break;
				}
			}
		}
		
		Map<String,Object> pksMap=new HashMap<String, Object>();
		List<ColumnModel> pkColumns=tableModel.getPrimayKey();
		for(ColumnModel column:pkColumns){
			String colName = column.getName();
			String colType=column.getColumnType();
			String key="__pk__"+colName;
			Object value;
			if(colType.equalsIgnoreCase(ColumnModel.COLUMNTYPE_INT)){
				value=RequestUtil.getInt(request, key);
			}else if(colType.equalsIgnoreCase(ColumnModel.COLUMNTYPE_NUMBER)){
				value=RequestUtil.getLong(request, key);
			}else if(colType.equalsIgnoreCase(ColumnModel.COLUMNTYPE_VARCHAR)){
				value=RequestUtil.getString(request, key);
			}else if(colType.equalsIgnoreCase(ColumnModel.COLUMNTYPE_DATE)){
				value=RequestUtil.getDate(request, key);
			}else{
				value=RequestUtil.getString(request,key);
			}
			pksMap.put(colName, value);
		}
		sysTableManageService.saveData(sysTableManage, map, pksMap);
		resultMsg=getText("record.updated","数据表记录");
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		
		
	}
	
	/**
	 * 将自定义表管理 添加为菜单资源
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addToResource")
	public ModelAndView addToResource(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv=getAutoView();
		
		List<SubSystem> subSystemList = subSystemService.getAll();
		Long currentSystemId = SubSystemUtil.getCurrentSystemId(request);
		mv.addObject("subSystemList", subSystemList).addObject("currentSystemId", currentSystemId);
		
		return mv;
	}
	
	/**
	 * 解析Groovy 脚本
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("parseConditionScript")
	@ResponseBody
	public Map<String,Object> parseConditionScript(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map=new HashMap<String, Object>();
		boolean status=true;
		String message="";
		String wh="";
		try{
			String script=request.getParameter("script");
			String parameters=request.getParameter("parameters");
			
			SysTableManage sysTableManage=new SysTableManage();
			sysTableManage.setParameters(parameters);
			sysTableManage.setConditionType(2);
			sysTableManage.setConditionField(script);
			
			sysTableManageService.parseCustomParameterAsMap(sysTableManage, request);
			GroovyScriptEngine groovyScriptEngine=(GroovyScriptEngine) AppUtil.getBean(GroovyScriptEngine.class);
			map.put("VarMap", sysTableManage.getParameterMap());
			wh = groovyScriptEngine.executeString(script, map);
		}catch (Exception e) {
			status=false;
			message=e.getMessage();
		}
		map.put("status", status);
		map.put("message", message);
		map.put("result",wh);
		return map;
	}
}