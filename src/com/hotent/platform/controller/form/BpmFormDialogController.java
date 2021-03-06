package com.hotent.platform.controller.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.customertable.BaseTableMeta;
import com.hotent.core.customertable.IDbView;
import com.hotent.core.customertable.TableModel;
import com.hotent.core.customertable.impl.TableMetaFactory;
import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.form.BpmFormDialog;
import com.hotent.platform.model.form.DialogField;
import com.hotent.platform.model.system.SysDataSource;
import com.hotent.platform.service.form.BpmFormDialogService;
import com.hotent.platform.service.system.SysDataSourceService;

/**
 * 对象功能:通用表单对话框 控制器类 开发公司:广州宏天软件有限公司 开发人员:ray 创建时间:2012-06-25 11:05:09
 */
@Controller
@RequestMapping("/platform/form/bpmFormDialog/")
public class BpmFormDialogController extends BaseController {
	@Resource
	private BpmFormDialogService bpmFormDialogService;

	@Resource
	private SysDataSourceService sysDataSourceService;

	@Resource
	private FreemarkEngine freemarkEngine;
	
	private static String DEFAULT_ORDER_SEQ="DESC";

	/**
	 * 取得通用表单对话框分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看通用表单对话框分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BpmFormDialog> list = bpmFormDialogService.getAll(new QueryFilter(request, "bpmFormDialogItem"));
		ModelAndView mv = this.getAutoView().addObject("bpmFormDialogList", list);

		
		return mv;
	}

	/**
	 * 删除通用表单对话框
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除通用表单对话框")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			bpmFormDialogService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除通用表单对话框成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败:" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description = "编辑通用表单对话框")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String returnUrl = RequestUtil.getPrePage(request);
		BpmFormDialog bpmFormDialog = null;
		if (id != 0) {
			bpmFormDialog = bpmFormDialogService.getById(id);
		} else {
			bpmFormDialog = new BpmFormDialog();
		}
		List<SysDataSource> dsList = sysDataSourceService.getAll();

		return getAutoView().addObject("bpmFormDialog", bpmFormDialog)
							.addObject("returnUrl", returnUrl)
							.addObject("dsList", dsList);
	}

	/**
	 * 取得通用表单对话框明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看通用表单对话框明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		BpmFormDialog bpmFormDialog = bpmFormDialogService.getById(id);
		return getAutoView().addObject("bpmFormDialog", bpmFormDialog);
	}

	@RequestMapping("dialogObj")
	@Action(description = "查看通用表单对话框明细")
	@ResponseBody
	public Map<String, Object> dialogObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String alias = RequestUtil.getString(request, "alias");
		Map<String, Object> map = new HashMap<String, Object>();
		BpmFormDialog bpmFormDialog = bpmFormDialogService.getByAlias(alias);
		if (bpmFormDialog != null) {
			map.put("bpmFormDialog", bpmFormDialog);
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
			System.out.println("getByDsObjectName:" + ex.getMessage());
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

	/**
	 * 设置字段对话框。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setting")
	public ModelAndView setting(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		String dsName = "";
		String objectName = "";
		int istable = 0;
		int style = 0;
		ModelAndView mv = this.getAutoView();
		if (id == 0) {
			dsName = RequestUtil.getString(request, "dsName");
			objectName = RequestUtil.getString(request, "objectName");
			istable = RequestUtil.getInt(request, "istable");
			style = RequestUtil.getInt(request, "style");
		} else {
			BpmFormDialog bpmFormDialog = bpmFormDialogService.getById(id);
			dsName = bpmFormDialog.getDsalias();
			objectName = bpmFormDialog.getObjname();
			istable = bpmFormDialog.getIstable();
			style = bpmFormDialog.getStyle();
			mv.addObject("bpmFormDialog", bpmFormDialog);
		}

		TableModel tableModel;
		// 表
		if (istable == 1) {
			BaseTableMeta meta = TableMetaFactory.getMetaData(dsName);
			tableModel = meta.getTableByName(objectName);
		}
		// 视图处理
		else {
			IDbView dbView = TableMetaFactory.getDbView(dsName);
			tableModel = dbView.getModelByViewName(objectName);
		}

		mv.addObject("tableModel", tableModel).addObject("style", style);

		return mv;
	}

	/**
	 * 取得树形数据。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTreeData")
	@ResponseBody
	public List getTreeData(HttpServletRequest request) throws Exception {
		String alias = RequestUtil.getString(request, "alias");

		return bpmFormDialogService.getTreeData(alias);
	}

	/**
	 * 选择自定义对话框
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllDialogs")
	@ResponseBody
	public List<BpmFormDialog> getAllDialogs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BpmFormDialog> list= bpmFormDialogService.getAll();		
		return list;
	}

	/**
	 * 显示对话框。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("show")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map paramsMap = RequestUtil.getQueryMap(request);
		String alias = RequestUtil.getString(request, "dialog_alias_");
		String nextUrl = RequestUtil.getUrl(request);
		
		BpmFormDialog bpmFormDialog = bpmFormDialogService.getData(alias, paramsMap);
		ModelAndView mv = this.getAutoView();
		mv.addObject("bpmFormDialog", bpmFormDialog);
		//需要排序
		if(bpmFormDialog.getStyle()==0){
			String sortField=RequestUtil.getString(request, "sortField");
			String orderSeq=RequestUtil.getString(request, "orderSeq", DEFAULT_ORDER_SEQ);
			String newSortField=RequestUtil.getString(request, "newSortField");
			if(StringUtil.isNotEmpty(sortField)){
				paramsMap.put("sortField", sortField);
				paramsMap.put("orderSeq", orderSeq);
			}
			if(StringUtil.isEmpty(sortField)){
				DialogField dialogField = bpmFormDialog.getDisplayList().get(0);
				sortField = dialogField.getFieldName();
			}
			
			if(newSortField.equals(sortField)){
				if(orderSeq.equals("ASC")){
					orderSeq="DESC";
				}else{
					orderSeq="ASC";
				}
			}
			if(!StringUtil.isEmpty(newSortField)){
				sortField=newSortField;
			}
			
			Map<String,Object> parameters=new HashMap<String, Object>();
			parameters.put("sortField", StringUtil.isEmpty(newSortField)?sortField:newSortField);
			parameters.put("newSortField",null);
			parameters.put("orderSeq", orderSeq);
			nextUrl = addParametersToUrl(nextUrl, parameters);
			
			mv.addObject("sortField",sortField);
			mv.addObject("orderSeq",orderSeq);
			mv.addObject("baseHref", nextUrl);
			// 需要分页
			if (bpmFormDialog.getNeedpage() == 1) {
				PageBean pageBean = bpmFormDialog.getPageBean();
				Map pageModel = new HashMap();
				pageModel.put("tableIdCode", "");
				pageModel.put("pageBean", pageBean);
				pageModel.put("showExplain", true);
				pageModel.put("showPageSize", true);
				pageModel.put("baseHref", nextUrl);
				String pageHtml = freemarkEngine.mergeTemplateIntoString("page.ftl", pageModel);
				mv.addObject("pageHtml", pageHtml);
			}
		}
		return mv;
	}
	
	private static Map<String,Object> getQueryStringMap(String url){
		Map<String,Object> map = new  HashMap<String, Object>();
		int idx1=url.indexOf("?");
		if(idx1>0){
			String queryStr=url.substring(idx1+1);
			String[] queryNodeAry = queryStr.split("&");
			for(String queryNode:queryNodeAry){
				String[] strAry = queryNode.split("=");
				if(strAry.length>=2)
					map.put(strAry[0].trim(),strAry[1]);
			}
		}
		return map;
	}
	
	private static String addParametersToUrl(String url,Map<String, Object> params){
		StringBuffer sb=new StringBuffer();
		int idx1=url.indexOf("?");
		if(idx1>0){
			sb.append(url.substring(0, idx1));
		}
		sb.append("?");
		
		Map<String,Object> map=getQueryStringMap(url);
		map.putAll(params);
		
		for(Entry<String, Object> entry:map.entrySet()){
			if(BeanUtils.isEmpty(entry.getValue()))
				continue;
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			sb.append("&");
		}
		return sb.substring(0, sb.length()-1);
	}

}
