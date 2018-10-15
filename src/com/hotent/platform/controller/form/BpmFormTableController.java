package com.hotent.platform.controller.form;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.annotion.Action;
import com.hotent.core.customertable.BaseTableMeta;
import com.hotent.core.customertable.ColumnModel;
import com.hotent.core.customertable.IDbView;
import com.hotent.core.customertable.ITableOperator;
import com.hotent.core.customertable.TableModel;
import com.hotent.core.customertable.impl.TableMetaFactory;
import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.dao.form.BpmFormDefDao;
import com.hotent.platform.model.form.BpmFormField;
import com.hotent.platform.model.form.BpmFormRule;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.form.BpmFormTableIndex;
import com.hotent.platform.model.system.Identity;
import com.hotent.platform.model.system.SysDataSource;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.form.BpmFormFieldService;
import com.hotent.platform.service.form.BpmFormRuleService;
import com.hotent.platform.service.form.BpmFormTableService;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.platform.service.system.SysDataSourceService;
import com.hotent.platform.xml.util.MsgUtil;

/**
 * 对象功能:自定义表 控制器类 开发公司:广州宏天软件有限公司 开发人员:xwy 创建时间:2011-11-30 14:29:22
 */
@Controller
@RequestMapping("/platform/form/bpmFormTable/")
public class BpmFormTableController extends BaseController {
	@Resource
	private BpmFormTableService service;

	@Resource
	private BpmFormFieldService bpmFormFieldService;

	@Resource
	private SysDataSourceService sysDataSourceService;

	@Resource
	private BpmFormRuleService bpmFormRuleService;

	@Resource
	private IdentityService identityService;

	@Resource
	private FreemarkEngine freemarkEngine;

	@Resource
	private Properties configproperties;

	@Resource
	private BpmFormDefDao bpmFormDefDao;

	@Resource
	private ITableOperator tableOperator;

	/**
	 * 导入外部表。
	 * 
	 * @param request
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("defExtTable{viewName}")
	public ModelAndView defExtTable(HttpServletRequest request,
			@PathVariable String viewName) throws Exception {
		ModelAndView mv = this.getAutoView();
		// 选择表
		if (viewName.equals("1")) {
			List<SysDataSource> dsList = sysDataSourceService.getAll();
			mv.addObject("dsList", dsList);
		} else if (viewName.equals("2")) {
			String dataSource = RequestUtil.getString(request, "dataSource");
			String tableName = RequestUtil.getString(request, "tableName");
			mv.addObject("dataSource", dataSource);
			mv.addObject("tableName", tableName);
		}
		return mv;
	}

	/**
	 * 编辑自定义表。
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getAutoView();
		Long tableId = RequestUtil.getLong(request, "tableId");

		boolean canEditTbColName = true;
		// 无表单定义可以做任何的修改。
		if (tableId > 0) {
			//
			Long tmpTableId = tableId;

			BpmFormTable bpmFormTable = service.getById(tmpTableId);
			// 如果是子表的情况，先取得主表的ID,根据主表id判断是否可以编辑。
			if (bpmFormTable.getIsMain() == 0) {
				tmpTableId = bpmFormTable.getMainTableId();
			}
			if (tmpTableId != null && tmpTableId > 0)
				canEditTbColName = !bpmFormDefDao.isTableHasFormDef(tmpTableId);
		}
		List<BpmFormTable> mainTableList = service.getAllUnpublishedMainTable();
		mv.addObject("canEditTbColName", canEditTbColName)
				.addObject("tableId", tableId)
				.addObject("mainTableList", mainTableList);
		return mv;
	}

	/**
	 * 根据表的Id返回表和字段对象。
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getByTableId")
	public Map<String, Object> getByTableId(HttpServletRequest request) {
		Long tableId = RequestUtil.getLong(request, "tableId");
		Map<String, Object> map = new HashMap<String, Object>();
		BpmFormTable bpmFormTable = service.getById(tableId);
		List<BpmFormField> fieldList = bpmFormFieldService
				.getAllByTableId(tableId);
		map.put("bpmFormTable", bpmFormTable);
		map.put("fieldList", fieldList);
		return map;
	}

	/**
	 * 表选择对话框。
	 * 
	 * <pre>
	 *  只选择自定义表。
	 * </pre>
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dialog")
	public ModelAndView dialog(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getAutoView();

		QueryFilter queryFilter = new QueryFilter(request, "bpmFormTableItem");
		// 只查询自定义表。
		// queryFilter.addFilter("genByForm", 0);
		List<BpmFormTable> bpmFormTableList = service
				.getAllMainTable(queryFilter);
		mv.addObject("bpmFormTableList", bpmFormTableList);
		return mv;
	}

	/**
	 * 编辑列对话框。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("columnDialog")
	public ModelAndView columnDialog(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = this.getAutoView();
		int isAdd = RequestUtil.getInt(request, "isAdd", 0);
		int isMain = RequestUtil.getInt(request, "isMain", 0);
		// 验证规则
		List<BpmFormRule> validRuleList = bpmFormRuleService.getAll();
		// 获取流水号
		List<Identity> identityList = identityService.getAll();
		mv.addObject("validRuleList", validRuleList);
		mv.addObject("identityList", identityList);
		mv.addObject("isAdd", isAdd);
		mv.addObject("isMain", isMain);
		return mv;
	}

	/**
	 * 编辑索引。
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("editIndex")
	public ModelAndView editIndex(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getAutoView();
		Long tableId = RequestUtil.getLong(request, "tableId");
		String tableName = RequestUtil.getString(request, "tableName");
		List<BpmFormTableIndex> tableIndexes = tableOperator
				.getIndexesByTable(TableModel.CUSTOMER_TABLE_PREFIX + tableName);
		for (BpmFormTableIndex index : tableIndexes) {
			index.setIndexTable(index.getIndexTable().toUpperCase()
					.substring(TableModel.CUSTOMER_TABLE_PREFIX.length()));
			if (!index.isPkIndex()) {
				index.setIndexName(index.getIndexName().toUpperCase()
						.substring(TableModel.CUSTOMER_INDEX_PREFIX.length()));
			}
			List<String> fields = new ArrayList<String>();
			for (String field : index.getIndexFields()) {
				if (field.toUpperCase().startsWith(
						TableModel.CUSTOMER_COLUMN_PREFIX)) {
					fields.add(field.toUpperCase().substring(
							TableModel.CUSTOMER_COLUMN_PREFIX.length()));
				} else {
					fields.add(field);
				}
			}
			index.setIndexFields(fields);
		}
		BpmFormTable table = service.getById(tableId);
		mv.addObject("table", table).addObject("tableId", tableId)
				.addObject("tableIndexes", tableIndexes);
		return mv;
	}

	/**
	 * 编辑索引对话框。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("indexDialog")
	public ModelAndView indexDialog(HttpServletRequest request)
			throws Exception {
		int isAdd = RequestUtil.getInt(request, "isAdd", 0);
		Long tableId = RequestUtil.getLong(request, "tableId");
		String tableName = RequestUtil.getString(request, "tableName");
		String indexName = RequestUtil.getString(request, "indexName");
		boolean pkIndex = RequestUtil.getString(request, "pkIndex")
				.equalsIgnoreCase("true") ? true : false;

		BpmFormTableIndex index;
		if (isAdd == 0) {
			String preTableName = TableModel.CUSTOMER_TABLE_PREFIX + tableName;
			String preIndexName = indexName;
			if (!pkIndex) {
				preIndexName = TableModel.CUSTOMER_INDEX_PREFIX + indexName;
			}
			index = tableOperator.getIndex(preTableName, preIndexName);
			index.setIndexTable(index.getIndexTable().toUpperCase()
					.replaceFirst(TableModel.CUSTOMER_TABLE_PREFIX, ""));
			index.setIndexName(index.getIndexName().toUpperCase()
					.replaceFirst(TableModel.CUSTOMER_INDEX_PREFIX, ""));
			List<String> fields = new ArrayList<String>();
			for (String field : index.getIndexFields()) {
				fields.add(field.toUpperCase().replaceFirst(
						TableModel.CUSTOMER_COLUMN_PREFIX, ""));
			}
			index.setIndexFields(fields);
		} else {
			index = new BpmFormTableIndex();
			index.setIndexTable(tableName);
			index.setIndexFields(new ArrayList<String>());
		}
		List<BpmFormField> tableFileds = bpmFormFieldService
				.getByTableId(tableId);

		String selectedFields = JSONArray.fromObject(index.getIndexFields())
				.toString();

		ModelAndView mv = this.getAutoView();
		mv.addObject("dbType", getDbType());
		mv.addObject("index", index);
		mv.addObject("tableName", index.getIndexTable());
		mv.addObject("tableFileds", tableFileds);
		mv.addObject("selectedFields", selectedFields);
		mv.addObject("isAdd", isAdd);
		return mv;
	}

	/**
	 * 保存/新建索引
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveIndex")
	public void saveIndex(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int isAdd = RequestUtil.getInt(request, "isAdd", 0);
		// Long tableId = RequestUtil.getLong(request, "tableId");
		String tableName = RequestUtil.getString(request, "tableName");
		String indexType = RequestUtil.getString(request, "indexType");
		String indexName = RequestUtil.getString(request, "indexName");
		String oldIndexName = RequestUtil.getString(request, "oldIndexName");
		String[] indexFieldsAry = request.getParameterValues("indexFields");

		for (int i = 0; i < indexFieldsAry.length; i++) {
			indexFieldsAry[i] = TableModel.CUSTOMER_COLUMN_PREFIX
					+ indexFieldsAry[i];
		}
		List<String> indexFields = Arrays.asList(indexFieldsAry);

		ResultMessage resultMessage;
		BpmFormTableIndex index = new BpmFormTableIndex();
		index.setIndexName(indexName);
		index.setIndexType(indexType);
		index.setIndexFields(indexFields);
		index.setIndexTable(tableName);
		index.setIndexTable(TableModel.CUSTOMER_TABLE_PREFIX
				+ index.getIndexTable());
		index.setIndexName(TableModel.CUSTOMER_INDEX_PREFIX
				+ index.getIndexName());
		try {
			if (isAdd == 1) {
				tableOperator.createIndex(index);
				resultMessage = new ResultMessage(ResultMessage.Success,
						"添加索引成功！");
			} else {

				tableOperator.dropIndex(index.getIndexTable(),
						TableModel.CUSTOMER_INDEX_PREFIX + oldIndexName);
				tableOperator.createIndex(index);
				resultMessage = new ResultMessage(ResultMessage.Success,
						"修改索引成功！");
			}

		} catch (Exception e) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail, "修改索引失败:"
						+ str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
		writeResultMessage(response.getWriter(), resultMessage);
	}

	@RequestMapping("delIndex")
	public void delIndex(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String tableName = RequestUtil.getString(request, "tableName");
		String indexName = RequestUtil.getString(request, "indexName");

		ResultMessage resultMessage;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			tableName = TableModel.CUSTOMER_TABLE_PREFIX + tableName;
			indexName = TableModel.CUSTOMER_INDEX_PREFIX + indexName;
			tableOperator.dropIndex(tableName, indexName);
			resultMessage = new ResultMessage(ResultMessage.Success, "删除表索引成功!");
		} catch (Exception ex) {
			ex.printStackTrace();
			resultMessage = new ResultMessage(ResultMessage.Fail, "删除表索引失败!");
		}
		addMessage(resultMessage, request);
		response.sendRedirect(preUrl);
	}

	private String getDbType() {

		return tableOperator.getDbType();
	}

	/**
	 * 选择视图
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selectView")
	public ModelAndView selectView(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getAutoView();
		List<SysDataSource> dsList = sysDataSourceService.getAll();
		mv.addObject("dsList", dsList);
		return mv;
	}

	/**
	 * 根据视图名称显示视图字段。
	 * 
	 * @param dsName
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getModelByView")
	public ModelAndView getModelByView(HttpServletRequest request)
			throws Exception {
		String ds = RequestUtil.getString(request, "dataSource");
		String viewName = RequestUtil.getString(request, "selList");

		IDbView idbView = TableMetaFactory.getDbView(ds);
		TableModel tableModel = idbView.getModelByViewName(viewName);

		ModelAndView mv = getAutoView();
		mv.addObject("table", tableModel);

		return mv;
	}

	/**
	 * 编辑视图
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editView")
	public ModelAndView editView(HttpServletRequest request) throws Exception {
		String viewName = RequestUtil.getString(request, "viewName");
		String viewComment = RequestUtil.getString(request, "viewComment");
		String pkName = RequestUtil.getString(request, "pkName");
		String[] aryCol = request.getParameterValues("column");
		String[] aryDbType = request.getParameterValues("dbtype");
		String[] aryComment = request.getParameterValues("comment");
		TableModel tableModel = new TableModel();
		tableModel.setName(viewName);
		tableModel.setComment(viewComment);

		for (int i = 0; i < aryCol.length; i++) {
			ColumnModel colModel = new ColumnModel();
			colModel.setName(aryCol[i]);
			colModel.setColumnType(aryDbType[i]);
			colModel.setComment(aryComment[i]);
			tableModel.addColumnModel(colModel);
		}
		Map map = new HashMap();
		map.put("table", tableModel);
		map.put("pkName", pkName);
		String html = freemarkEngine.mergeTemplateIntoString("view.ftl", map);

		ModelAndView mv = getAutoView();
		mv.addObject("html", html);
		mv.addObject("viewName", viewName);
		return mv;

	}

	/**
	 * 保存视图。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("saveView")
	public void saveView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String viewpath = configproperties.getProperty("viewpath");
		String viewCharset = configproperties.getProperty("viewCharset");
		String viewName = RequestUtil.getString(request, "viewName");

		String content = request.getParameter("txtViewHtml");
		if (!viewpath.endsWith(File.separator))
			viewpath += File.separator;
		viewpath = viewpath + viewName + ".aspx";

		Pattern regex = Pattern.compile(
				"<div\\s*style=\"display:none\"\\s*>(.*?)</div>",
				Pattern.DOTALL | Pattern.CASE_INSENSITIVE
						| Pattern.UNICODE_CASE);
		Matcher regexMatcher = regex.matcher(content);
		while (regexMatcher.find()) {
			String tag = regexMatcher.group(0);
			String innerContent = regexMatcher.group(1);

			content = content.replace(tag, innerContent);
		}
		content = content.replace("&lt;", "<");
		content = content.replace("&gt;", ">");

		FileUtil.writeFile(viewpath, content, viewCharset);
		PrintWriter writer = response.getWriter();
		ResultMessage resultMessage = new ResultMessage(ResultMessage.Success,
				"保存视图信息成功!");
		writeResultMessage(writer, resultMessage);
	}

	/**
	 * 根据数据源和表的名称获取外部表数据。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getTableModel")
	public Map<String, Object> getTableModel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dataSource = RequestUtil.getString(request, "dataSource");
		String tableName = RequestUtil.getString(request, "tableName");
		BaseTableMeta meta = TableMetaFactory.getMetaData(dataSource);
		TableModel tableModel = meta.getTableByName(tableName);
		BpmFormTable table = new BpmFormTable();
		table.setTableName(tableName);
		table.setTableDesc(tableModel.getComment());
		// 取得字段列表
		List<BpmFormField> fieldList = convertFieldList(tableModel);

		List<Identity> identityList = identityService.getAll();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("identityList", identityList);
		map.put("table", table);
		map.put("fieldList", fieldList);

		return map;
	}

	/**
	 * 将表的列表转换为FieldLst。
	 * 
	 * @param tableModel
	 * @return
	 */
	private List<BpmFormField> convertFieldList(TableModel tableModel) {
		List<BpmFormField> fieldList = new ArrayList<BpmFormField>();
		List<ColumnModel> colList = tableModel.getColumnList();
		for (ColumnModel model : colList) {
			BpmFormField field = new BpmFormField();
			field.setIsPk(model.getIsPk() ? 1 : 0);
			field.setFieldName(model.getName());
			field.setFieldDesc(model.getComment());
			field.setCharLen(model.getCharLen());
			field.setIntLen(model.getIntLen());
			field.setDecimalLen(model.getDecimalLen());
			field.setFieldType(model.getColumnType());
			short isRequired = (short) (model.getIsNull() ? 0 : 1);
			field.setIsRequired(isRequired);
			fieldList.add(field);
		}
		return fieldList;
	}

	/**
	 * 根据表名获取数据库列表。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getTableList")
	@ResponseBody
	public Map getTableList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Map resultMap = new HashMap();
		try {
			String ds = RequestUtil.getString(request, "ds");
			String table = RequestUtil.getString(request, "table");
			BaseTableMeta meta = TableMetaFactory.getMetaData(ds);
			Map<String, String> map = meta.getTablesByName(table);
			resultMap.put("success", "true");
			resultMap.put("tables", map);
		} catch (Exception ex) {
			resultMap.put("success", "false");
		}
		return resultMap;
	}

	@RequestMapping("getTableById")
	@ResponseBody
	public Map<String, Object> getTableById(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("tableId") Long tableId)
			throws Exception {
		Map<String, Object> fields = new HashMap<String, Object>();
		BpmFormTable mainTable = service.getTableById(tableId);
		fields.put("table", mainTable);
		return fields;
	}

	/**
	 * 取得视图列表。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getViewList")
	@ResponseBody
	public Map getViewList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map resultMap = new HashMap();
		try {
			String ds = RequestUtil.getString(request, "ds");
			String table = RequestUtil.getString(request, "table");
			IDbView idbView = TableMetaFactory.getDbView(ds);
			List list = idbView.getViews(table);
			resultMap.put("success", "true");
			resultMap.put("views", list);
		} catch (Exception ex) {
			resultMap.put("success", "false");
		}
		return resultMap;
	}

	/**
	 * 设置表关系
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("setRelation")
	public ModelAndView setRelation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = this.getAutoView();
		long tableId = RequestUtil.getLong(request, "tableId");

		String dsName = RequestUtil.getString(request, "dsName");
		/**
		 * 获取字段
		 */
		List<BpmFormField> fieldList = bpmFormFieldService
				.getByTableId(tableId);
		/**
		 * 获取可以关联的表
		 */
		List<BpmFormTable> tables = service.getByDsSubTable(dsName);

		BpmFormTable bpmFormTable = service.getById(tableId);

		String tableName = bpmFormTable.getTableName();

		tables.remove(tableName);
		String pkField = bpmFormTable.getPkField();
		if (StringUtil.isNotEmpty(pkField))
			pkField = "";
		mv.addObject("pkField", bpmFormTable.getPkField());
		mv.addObject("tables", tables);
		mv.addObject("fieldList", fieldList);

		mv.addObject("tableName", tableName);
		mv.addObject("dataSource", dsName);
		mv.addObject("relation", bpmFormTable.getTableRelation());

		return mv;
	}

	/**
	 * 保存表之间的关系
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveRelation")
	public void saveRelation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		try {
			String relation = request.getParameter("relation");
			String tablename = RequestUtil.getString(request, "tablename");
			String dataSource = RequestUtil.getString(request, "dataSource");

			service.saveRelation(dataSource, tablename, relation);

			this.writeResultMessage(writer, "设置成功", ResultMessage.Success);
		} catch (Exception e) {
			e.printStackTrace();
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(
						ResultMessage.Fail, "设置失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				ResultMessage resultMessage = new ResultMessage(
						ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	/**
	 * 判断表名是否存在。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("isTableNameExternalExisted")
	public void isTableNameExternalExisted(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		String tablename = RequestUtil.getString(request, "tablename");
		String dataSource = RequestUtil.getString(request, "dataSource");
		boolean rtn = service.isTableNameExternalExisted(tablename, dataSource);
		writer.print(rtn);
	}

	/**
	 * 取得自定义表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看自定义表分页列表", operateType = "自定义表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "page", defaultValue = "1") int page)
			throws Exception {
		List<BpmFormTable> list = service.getAll(new QueryFilter(request,
				"bpmFormTableItem"));
		ModelAndView mv = this.getAutoView()
				.addObject("bpmFormTableList", list);

		return mv;
	}

	/**
	 * 编辑外部表。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editExt")
	@Action(description = "编辑外部表", operateType = "自定义表")
	public ModelAndView editExt(HttpServletRequest request) throws Exception {
		Long tableId = RequestUtil.getLong(request, "tableId");
		String returnUrl = RequestUtil.getPrePage(request);
		return getAutoView().addObject("tableId", tableId).addObject(
				"returnUrl", returnUrl);
	}

	/**
	 * 删除外部表定义。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("delTableById")
	@Action(description = "删除外部表定义表", operateType = "自定义表")
	public void delTableById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage resultMessage;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long tableId = RequestUtil.getLong(request, "tableId");
			service.delExtTableById(tableId);
			resultMessage = new ResultMessage(ResultMessage.Success, "删除表定义成功!");
		} catch (Exception ex) {
			resultMessage = new ResultMessage(ResultMessage.Fail, "删除表定义失败!");
		}
		addMessage(resultMessage, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 取得自定义表明细
	 * 
	 * @param request
	 * @param response
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看自定义表明细", operateType = "自定义表")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long tableId = RequestUtil.getLong(request, "tableId");
		ModelAndView mv = getAutoView();
		BpmFormTable table = service.getById(tableId);
		List<BpmFormField> fields = bpmFormFieldService.getByTableId(tableId);
		mv.addObject("table", table).addObject("fields", fields);
		String mainTable = "未分配";
		if (table.getIsMain() == 1) {
			List<BpmFormTable> subList = service
					.getSubTableByMainTableId(tableId);
			mv.addObject("subList", subList);
		} else {
			Long mainTableId = table.getMainTableId();
			if (mainTableId > 0) {
				BpmFormTable tb = service.getById(mainTableId);
				mainTable = tb.getTableName();
				mv.addObject("mainTable", mainTable);
			}
		}
		return mv;

	}

	/**
	 * 生成表。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("generateTable")
	@Action(description = "生成表", operateType = "自定义表")
	public void publish(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultMessage resultMessage = null;
		PrintWriter out = response.getWriter();
		Long tableId = RequestUtil.getLong(request, "tableId");
		try {
			service.generateTable(tableId, ContextUtil.getCurrentUser()
					.getFullname());
			resultMessage = new ResultMessage(ResultMessage.Success, null);
		} catch (Exception e) {
			e.printStackTrace();
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,
						"生成自定义表失败:" + str);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
			}
		}
		out.print(resultMessage);
	}

	/**
	 * 查看子表
	 * 
	 * @param request
	 * @param response
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("subTable")
	@Action(description = "查看子表", operateType = "自定义表")
	public ModelAndView subTable(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("tableId") Long tableId)
			throws Exception {

		BpmFormTable table = service.getById(tableId);
		List<BpmFormTable> subTables = service
				.getSubTableByMainTableId(tableId);

		return getAutoView().addObject("table", table).addObject("subTables",
				subTables);
	}

	/**
	 * 关联子表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("linkSubtable")
	public void linkSubtable(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResultMessage resultMessage = null;
		PrintWriter out = response.getWriter();
		try {
			Long mainTableId = RequestUtil.getLong(request, "mainTableId");
			Long subTableId = RequestUtil.getLong(request, "subTableId");
			service.linkSubtable(mainTableId, subTableId);
			resultMessage = new ResultMessage(ResultMessage.Success, "关联表成功!");
		} catch (Exception e) {
			e.printStackTrace();
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail, "关联表失败:"
						+ str);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
			}
		}
		out.print(resultMessage);
	}

	/**
	 * 移除子表关系
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("unlinkSubTable")
	@Action(description = "移除子表关系", operateType = "自定义表")
	public void unlinkSubTable(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("subTableId") Long subTableId) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		service.unlinkSubTable(subTableId);
		response.sendRedirect(preUrl);
	}

	@ResponseBody
	@RequestMapping("getExtTableByTableId")
	public Map<String, Object> getExtTableByTableId(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("tableId") Long tableId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		BpmFormTable table = service.getById(tableId);
		// 取得字段列表
		List<BpmFormField> fieldList = bpmFormFieldService
				.getAllByTableId(tableId);

		List<Identity> identityList = identityService.getAll();
		// 验证规则
		List<BpmFormRule> validRuleList = bpmFormRuleService.getAll();
		// 通过子表获得可关联的主表
		map.put("table", table);
		map.put("fieldList", fieldList);

		map.put("identityList", identityList);

		map.put("validRuleList", validRuleList);

		return map;
	}

	/**
	 * 获得所有未分配的子表
	 * 
	 * @param request
	 * @param response
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getAllUnassignedSubTable")
	public List<BpmFormTable> getAllUnassignedSubTable(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("tableName") String tableName) throws Exception {
		List<BpmFormTable> allUnassignedSubTable = service
				.getAllUnassignedSubTable();
		return allUnassignedSubTable;
	}

	/**
	 * 删除自定义表。 如果表已经定义表单，那么表不可以删除。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delByTableId")
	public void delByTableId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long tableId = RequestUtil.getLong(request, "tableId");
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		boolean rtn = bpmFormDefDao.isTableHasFormDef(tableId);
		if (rtn) {
			message = new ResultMessage(ResultMessage.Fail, "该表已定义表单不能删除!");
		} else {
			service.delByTableId(tableId);
			message = new ResultMessage(ResultMessage.Success, "表定义已成功删除!");
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 删除扩展表。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delExtTableById")
	public void delExtTableById(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long tableId = RequestUtil.getLong(request, "tableId");
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		boolean rtn = bpmFormDefDao.isTableHasFormDef(tableId);
		if (rtn) {
			message = new ResultMessage(ResultMessage.Fail, "该表已定义表单不能删除!");
			addMessage(message, request);
		} else {
			service.delExtTableById(tableId);
			message = new ResultMessage(ResultMessage.Success, "表定义已成功删除!");
			addMessage(message, request);
		}
		response.sendRedirect(preUrl);
	}

	/**
	 * 分配主表。 取得未分配的主表。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("assignMainTable")
	public ModelAndView assignMainTable(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long subTableId = RequestUtil.getLong(request, "subTableId");
		List<BpmFormTable> mainTableList = service.getAssignableMainTable();
		ModelAndView mv = getAutoView();
		mv.addObject("mainTableList", mainTableList).addObject("subTableId",
				subTableId);
		return mv;

	}

	/**
	 * 导出选择导出xml
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("export")
	@Action(description = "导出选择导出xml", operateType = "自定义表")
	public ModelAndView export(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tableIds = RequestUtil.getString(request, "tableIds");
		
		ModelAndView mv = this.getAutoView();
		mv.addObject("tableIds", tableIds);
		return mv;
	}
	
	/**
	 * 导出表定义xml。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("exportXml")
	@Action(description = "导出自定义表", operateType = "自定义表")
	public void exportXml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long[] tableIds = RequestUtil.getLongAryByStr(request, "tableIds");
		Map<String,Boolean> map =  new HashMap<String, Boolean>();
		map.put("bpmFormTable", true);
		map.put("bpmFormField", true);
		map.put("subTable", RequestUtil.getBoolean(request, "subTable"));
		map.put("identity", RequestUtil.getBoolean(request, "identity"));

		if (BeanUtils.isNotEmpty(tableIds)) {
			String strXml = service.exportXml(tableIds,map);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ tableIds[0].toString() + ".xml");
			response.getWriter().write(strXml);
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	/**
	 * 导入表的XML。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("importXml")
	@Action(description = "导入自定义表", operateType = "自定义表")
	public void importXml(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MultipartFile fileLoad = request.getFile("xmlFile");
		ResultMessage message = null;
		try {
			service.importXml(fileLoad.getInputStream());
			message = new ResultMessage(ResultMessage.Success,
					MsgUtil.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
			message = new ResultMessage(ResultMessage.Fail,"导入出错了，请检查导入格式是否正确或者导入的数据是否有问题！" );
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 获取流程变量树
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getSubTree")
	@ResponseBody
	public String getSubTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer sb = new StringBuffer("[");
		Long tableId = RequestUtil.getLong(request, "tableId");
		if(tableId.longValue() == 0L){
			sb.append("]");
			return sb.toString();	
		}
		List<BpmFormTable> list = service.getSubTableByMainTableId(tableId);
		
		for (BpmFormTable bpmFormTable : list) {
			List<BpmFormField> bpmFormFieldList = bpmFormFieldService.getByTableId(bpmFormTable.getTableId());
			
			sb.append("{id:").append(bpmFormTable.getTableId())
					.append(",isTable:").append("1").append("")
					.append(",name:\"").append(bpmFormTable.getTableName()).append("\"")
					.append(",icon:\"").append("").append("\"")
					.append(",showName: \"").append(bpmFormTable.getTableName())
					.append("(" + bpmFormTable.getTableDesc() + ")")
					.append("\",children:[");
			
			// 用户
			sb.append("{id:").append(0)
			.append(",isTable:").append("0").append("")
			.append(",fieldType:\"").append("number").append("\"")
			.append(",name:\"").append("CURENTUSERID_").append("\"")
			.append(",icon:\"").append("").append("\"")
			.append(",showName: \"").append("CURENTUSERID_")
			.append("(当前用户)")
			.append("\"},");
			
			// 组织
			sb.append("{id:").append(1)
			.append(",isTable:").append("0").append("")
			.append(",fieldType:\"").append("number").append("\"")
			.append(",name:\"").append("CURENTORGID_").append("\"")
			.append(",icon:\"").append("").append("\"")
			.append(",showName: \"").append("CURENTORGID_")
			.append("(当前组织)")
			.append("\"},");
			
			for (BpmFormField bpmFormField : bpmFormFieldList) {
				sb.append("{id:").append(bpmFormField.getFieldId())
				.append(",isTable:").append("0").append("")
				.append(",fieldType:\"").append(bpmFormField.getFieldType()).append("\"")
				.append(",name:\"").append(bpmFormField.getFieldName()).append("\"")
				.append(",icon:\"").append("").append("\"")
				.append(",showName: \"").append(bpmFormField.getFieldName())
				.append("(" + bpmFormField.getFieldDesc() + ")")
				.append("\"},");
			}
			
			if (!list.isEmpty()) {
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("]},");
			
		}
		if (!list.isEmpty()) {
			sb.deleteCharAt(sb.length() - 1);
		}

		sb.append("]");
		
//		System.out.println(sb);
		return sb.toString();

	}

	/**
	 * 比较两个列表是否相等。在比较两个列表的元素时，比较的方式为(o==null ? e==null : o.equals(e)).
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	private boolean isListEqual(List list1, List list2) {
		if (list1.size() != list2.size()) {
			return false;
		}
		if (list1.containsAll(list2)) {
			return true;
		}
		return false;
	}
}
