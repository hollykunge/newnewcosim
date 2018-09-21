package com.hotent.platform.service.system;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.hotent.core.customertable.BaseTableMeta;
import com.hotent.core.customertable.ColumnModel;
import com.hotent.core.customertable.SqlTypeConst;
import com.hotent.core.customertable.TableModel;
import com.hotent.core.customertable.impl.TableMetaFactory;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.db.JdbcHelper;
import com.hotent.core.db.entity.SQLClause;
import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.mybatis.Dialect;
import com.hotent.core.mybatis.dialect.DB2Dialect;
import com.hotent.core.mybatis.dialect.H2Dialect;
import com.hotent.core.mybatis.dialect.MySQLDialect;
import com.hotent.core.mybatis.dialect.OracleDialect;
import com.hotent.core.mybatis.dialect.SQLServer2005Dialect;
import com.hotent.core.page.PageBean;
import com.hotent.core.page.PageUtils;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.TimeUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.model.form.BpmFormTemplate;
import com.hotent.platform.model.system.SysDataSource;
import com.hotent.platform.model.system.SysTableManage;
import com.hotent.platform.model.system.SysTableManage.Parameter;
import com.hotent.platform.service.form.BpmFormTemplateService;
import com.hotent.platform.dao.system.SysDataSourceDao;
import com.hotent.platform.dao.system.SysTableManageDao;

/**
 * 对象功能:自定义表管理 Service类 开发公司: 开发人员:Raise 创建时间:2012-06-25 11:05:09
 */
@Service
public class SysTableManageService extends BaseService<SysTableManage> {
	@Resource
	private SysTableManageDao dao;

	@Resource
	private SysDataSourceDao sysDataSourceDao;

	@Resource
	private GroovyScriptEngine groovyScriptEngine;

	@Resource
	private FreemarkEngine freemarkEngine;

	@Resource
	BpmFormTemplateService bpmFormTemplateService;
	public SysTableManageService() {
	}

	@Override
	protected IEntityDao<SysTableManage, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 检查模板别名是否唯一
	 * 
	 * @param alias
	 * @return
	 */
	public boolean isExistAlias(String alias) {
		return dao.isExistAlias(alias) > 0;
	}

	/**
	 * 检查模板别名是否唯一。
	 * 
	 * @param alias
	 * @return
	 */
	public boolean isExistAliasForUpd(Long id, String alias) {
		return dao.isExistAliasForUpd(id, alias) > 0;
	}

	/**
	 * 根据别名获取对话框对象。
	 * 
	 * @param alias
	 * @return
	 */
	public SysTableManage getByAlias(String alias) {
		return dao.getByAlias(alias);
	}

	/**
	 * 根据ID获取对应自定义表管理的数据。
	 * 
	 * @param id
	 *            自定义表管理ID
	 * @param params
	 *            参数集合。
	 * @return
	 * @throws Exception
	 */
	public SysTableManage getData(Long id, Map<String, Object> params) throws Exception {
		SysTableManage sysTableManage = dao.getById(id);
		return getData(sysTableManage, params);
	}

	/**
	 * 根据别名获取对应自定义表管理的数据。
	 * 
	 * @param alias
	 *            对话框别名。
	 * @param params
	 *            参数集合。
	 * @return
	 * @throws Exception
	 */
	public SysTableManage getData(String alias, Map<String, Object> params) throws Exception {
		SysTableManage sysTableManage = dao.getByAlias(alias);
		return getData(sysTableManage, params);
	}

	/**
	 * 根据自定义表管理实体获取对应自定义表管理的数据。
	 * 
	 * @param sysTableManage
	 *            自定义表管理实体
	 * @param params
	 *            参数集合。
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SysTableManage getData(SysTableManage sysTableManage, Map<String, Object> params) throws Exception {
		JdbcHelper jdbcHelper = getJdbcHelper(sysTableManage.getDsAlias());
		String tableIdCode = "";
		if (params.get("__tic") != null) {
			tableIdCode = (String) params.get("__tic");
		}
		// 是否需要分页。
		if (sysTableManage.getNeedPage() == 1) {
			int currentPage = 1;
			Object pageObj = params.get(tableIdCode + SysTableManage.PAGE);
			if (pageObj != null) {
				currentPage = Integer.parseInt(params.get(tableIdCode + SysTableManage.PAGE).toString());
			}
			int pageSize = sysTableManage.getPageSize();
			Object pageSizeObj = params.get(tableIdCode + SysTableManage.PAGESIZE);
			if (pageSizeObj != null) {
				pageSize = Integer.parseInt(params.get(tableIdCode + SysTableManage.PAGESIZE).toString());
			}
			String sql = getSQL(sysTableManage, params);

			Object oldPageSizeObj = params.get(tableIdCode + "oz");

			int oldPageSize = sysTableManage.getPageSize();
			if (oldPageSizeObj != null) {
				oldPageSize = Integer.parseInt(params.get(tableIdCode + "oz").toString());
			}
			if (pageSize != oldPageSize) {
				int first = PageUtils.getFirstNumber(currentPage, oldPageSize);
				currentPage = first / pageSize + 1;
			}
			PageBean pageBean = new PageBean(currentPage, pageSize);
			
			//params.putAll(sysTableManage.getParameterMap());
			List list = jdbcHelper.getPage(currentPage, pageSize, sql, params, pageBean);

			sysTableManage.setList(list);
			sysTableManage.setPageBean(pageBean);
		} else {
			String sql = getSQL(sysTableManage, params);
			List<Map<String, Object>> list = jdbcHelper.queryForList(sql, params);
			sysTableManage.setList(list);
		}


		return sysTableManage;

	}

	/**
	 * 
	 * @param id
	 * @param map
	 *            参数Map：<br/>
	 *            __tic: Table ID code __baseURL:排序、分页等的超连接的 BASE URL<br/>
	 *            __tic+s:排序字段 <br/>
	 *            __tic+__ns__:新的排序字段 <br/>
	 *            __tic+o:排序方向<br/>
	 */
	public String getDisplay(Long id, Map<String, Object> map) throws Exception {
		SysTableManage sysTableManage = getById(id);
		// 构建URL
		String tableIdCode = (String) map.get("__tic");
		if (tableIdCode == null) {
			tableIdCode = "";
			map.put("__tic", "");
		}
		String baseURL = (String) map.get("__baseURL");
		// 排序
		String sortField = null;
		String orderSeq = "DESC";
		String newSortField = null;
		if (map.get(tableIdCode + SysTableManage.SORTFIELD) != null) {
			sortField = (String) map.get(tableIdCode + SysTableManage.SORTFIELD);
		}
		if (map.get(tableIdCode + SysTableManage.ORDERSEQ) != null) {
			orderSeq = (String) map.get(tableIdCode + SysTableManage.ORDERSEQ);
		}
		if (map.get(tableIdCode + "__ns__") != null) {
			newSortField = (String) map.get(tableIdCode + "__ns__");
		}
		if (StringUtil.isNotEmpty(newSortField)) {
			if (newSortField.equals(sortField)) {
				if (orderSeq.equals("ASC")) {
					orderSeq = "DESC";
				} else {
					orderSeq = "ASC";
				}
			}
			sortField = newSortField;
			map.put(tableIdCode + SysTableManage.SORTFIELD, sortField);
			map.put(tableIdCode + SysTableManage.ORDERSEQ, orderSeq);
		}

		HttpServletRequest request = RequestUtil.getHttpServletRequest();
		// 自定义参数
		parseCustomParameterAsMap(sysTableManage, request);
		// 取得数据
		sysTableManage = this.getData(sysTableManage, map);

		List<String> excludes = new ArrayList<String>();
		for (String key : map.keySet()) {
			if (key.endsWith("__ns__")) {
				excludes.add(key);
			}
			if (key.endsWith("__pk__")) {
				excludes.add(key);
			}
		}
		excludes.add("rand");
		excludes.add("__baseURL");
		excludes.add("__tic");
		excludes.add("__displayId");
		String pageURL = addParametersToUrl(baseURL, map, excludes);
		String searchFormURL = baseURL;

		Map<String, Object> freeMarkerModel = new HashMap<String, Object>();

		// 第一次解析模板
		String dataTemplate = sysTableManage.getDspTemplate();
		if (StringUtil.isEmpty(dataTemplate)) {
			dataTemplate = generateDspTemplate(sysTableManage, map);
		}
		String pageHtml = "";
		// 需要分页
		if (sysTableManage.getNeedPage() == 1) {
			PageBean pageBean = sysTableManage.getPageBean();
			freeMarkerModel.put("tableIdCode", tableIdCode);
			freeMarkerModel.put("pageBean", pageBean);
			freeMarkerModel.put("showExplain", true);
			freeMarkerModel.put("showPageSize", true);
			freeMarkerModel.put("baseHref", pageURL);
			freeMarkerModel.put("pageURL", pageURL);
			freeMarkerModel.put("VarMap", sysTableManage.getParameterMap());// custom parameter
			pageHtml = freemarkEngine.mergeTemplateIntoString("pageAjax.ftl", freeMarkerModel);
		}
		// 第二次解析模板
		freeMarkerModel.clear();
		freeMarkerModel.put("sysTableManage", sysTableManage);
		freeMarkerModel.put("searchFormURL", searchFormURL);
		freeMarkerModel.put("pageHtml", pageHtml);
		freeMarkerModel.put("pageURL", pageURL);
		freeMarkerModel.put("sortField", sortField);
		freeMarkerModel.put("orderSeq", orderSeq);
		freeMarkerModel.put("tableIdCode", tableIdCode);
		freeMarkerModel.put("searchFormURL", searchFormURL);
		freeMarkerModel.put("VarMap", sysTableManage.getParameterMap());// custom
																	// parameter
		String display = freemarkEngine.parseByStringTemplate(freeMarkerModel, dataTemplate);
		return display;
	}

	/**
	 * 对FreeMarker数据模板进行第一次解析，生成使用表/视图的元数据构造的数据展示框架。
	 * 
	 * @param sysTableManage
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String generateDspTemplate(SysTableManage sysTableManage, Map<String, Object> map) throws Exception {
		String deleteBaseURL = (String) map.get("__deleteBaseURL");
		String editBaseURL = (String) map.get("__editBaseURL");
		TableModel tableModel = getTableModel(sysTableManage);
		List<ColumnModel> pkCols = tableModel.getPrimayKey();
		// 第一次解析模板
		Map<String, Object> freemarkerMap = new HashMap<String, Object>();
		freemarkerMap.put("sysTableManage", sysTableManage);
		freemarkerMap.put("pkcols", pkCols);
		freemarkerMap.put("editBaseURL", editBaseURL);
		freemarkerMap.put("deleteBaseURL", deleteBaseURL);
		freemarkerMap.put("editable", sysTableManage.getEditable() == 1);
		
		//String dataTemplate = freemarkEngine.mergeTemplateIntoString("tableManage/ListTemplate.ftl", freemarkerMap);
		BpmFormTemplate bpmFormTemplate=bpmFormTemplateService.getById(sysTableManage.getTemplateId());
		String dataTemplate = freemarkEngine.parseByStringTemplate(freemarkerMap, bpmFormTemplate.getHtml());
		return dataTemplate;

	}
	
	public TableModel getTableModel(SysTableManage tableManage) throws Exception {
		BaseTableMeta meta = TableMetaFactory.getMetaData(tableManage.getDsAlias());
		return meta.getTableByName(tableManage.getObjName());
	}

	/**
	 * @param url
	 * @param params
	 *            附加的参数
	 * @param exclude
	 *            排除的参数
	 * @return
	 */
	public static String addParametersToUrl(String url, Map<String, Object> params, List<String> exclude) {
		StringBuffer sb = new StringBuffer();
		int idx1 = url.indexOf("?");
		if (idx1 > 0) {
			sb.append(url.substring(0, idx1));
		} else {
			sb.append(url);
		}
		sb.append("?");

		Map<String, Object> map = getQueryStringMap(url);
		if (BeanUtils.isNotEmpty(params)) {
			map.putAll(params);
		}
		if (exclude != null) {
			for (String ex : exclude) {
				map.remove(ex);
			}
		}

		for (Entry<String, Object> entry : map.entrySet()) {
			if (BeanUtils.isEmpty(entry.getValue()))
				continue;
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			sb.append("&");
		}
		return sb.substring(0, sb.length() - 1);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void deleteData(Long id, Map<String, Object> params) throws Exception {
		SysTableManage sysTableManage = getById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		for (Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			if (key.startsWith("__pk__")) {
				map.put(key.substring(6), params.get(key));
			}
		}

		if (map.size() < 0) {
			return;
		}

		TableModel tableModel = getTableModel(sysTableManage);
		List<ColumnModel> pkColumnModels = tableModel.getPrimayKey();
		int m = pkColumnModels.size();
		int n = map.size();
		if (m != n) {
			throw new Exception("数据错误！");
		}
		boolean flag = false;
		for (ColumnModel column : pkColumnModels) {
			for (String key : map.keySet()) {
				if (key.equalsIgnoreCase(column.getName())) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				throw new Exception("数据错误！");
			}
		}

		String delSQL = "DELETE FROM " + sysTableManage.getObjName() + " WHERE 1=1 ";
		for (String key : map.keySet()) {
			delSQL += " AND " + key + "=:" + key;
		}
		JdbcHelper jdbcHelper = getJdbcHelper(sysTableManage.getDsAlias());
		jdbcHelper.execute(delSQL, map);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveData(SysTableManage sysTableManage, Map<String, Object> map, Map<String, Object> pksMap) throws Exception {
		if (BeanUtils.isEmpty(map) || BeanUtils.isEmpty(pksMap)) {
			return;
		}
		String saveSQL = "UPDATE " + sysTableManage.getObjName() + " SET ";
		for (String key : map.keySet()) {
			saveSQL += key + "=:" + key + " ,";
		}
		saveSQL = saveSQL.substring(0, saveSQL.length() - 1);
		saveSQL += " WHERE 1=1";
		for (String key : pksMap.keySet()) {
			saveSQL += " AND " + key + "=:" + key;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.putAll(map);
		params.putAll(pksMap);
		saveSQL.substring(0, saveSQL.length() - 1);
		JdbcHelper jdbcHelper = getJdbcHelper(sysTableManage.getDsAlias());
		jdbcHelper.execute(saveSQL, params);

	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> getSingleData(SysTableManage sysTableManage, Map<String, Object> map) throws Exception {
		String selectSQL = "SELECT a.* FROM " + sysTableManage.getObjName() +" a";
		selectSQL += " WHERE 1=1 ";
		for (String key : map.keySet()) {
			selectSQL += "AND " + key + "=:" + key + " ";
		}
		JdbcHelper jdbcHelper = getJdbcHelper(sysTableManage.getDsAlias());
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcHelper.getJdbcTemplate());
		return template.queryForMap(selectSQL, map);
	}

	/**
	 * 解析脚本条件
	 * 
	 * @param sysTableManage
	 * @param paramMap
	 * @return 条件子句
	 */
	public String parseConditionScript(SysTableManage sysTableManage, Map<String, Object> paramMap) {
		int type = sysTableManage.getConditionType();
		if (type != 2) {
			return null;
		}
		String script = sysTableManage.getConditionField();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("VarMap", sysTableManage.getParameterMap());
		String sql = groovyScriptEngine.executeString(script, map);
		return sql;
	}

	/**
	 * 解析自定义参数。
	 * 
	 * @param sysTableManage
	 * @param request
	 * @return 自定义的参数的Map
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> parseCustomParameterAsMap(SysTableManage sysTableManage, HttpServletRequest request) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Map<String, Object> queryMap = RequestUtil.getQueryMap(request);
		List<Parameter> parameterList = sysTableManage.getParameterList();
		for (Parameter parameter : parameterList) {
			int valueFrom = parameter.getValueFrom();
			String name = parameter.getName();
			String type = parameter.getType();
			Object value = null;
			switch (valueFrom) {
			case 1:// 输入
				value = queryMap.get(name);
				break;
			case 2:// 固定值
				value = getObjValue(type,(String) parameter.getValue());
				break;
			case 3:// 脚本
				String script = (String) parameter.getValue();
				if (StringUtil.isNotEmpty(script)) {
					value = groovyScriptEngine.executeObject(script, null);
				}
				break;
			}
			parameters.put(name, value);
		}
		sysTableManage.setParameterMap(parameters);
		return parameters;
	}

//	public List<Parameter> parseCustomParameterAsList(SysTableManage sysTableManage){
//		List<Parameter> parameterList=new ArrayList<Parameter>();
//		if (StringUtil.isEmpty(sysTableManage.getParameters())) {
//			return parameterList;
//		}
//		JSONArray jsonArray = JSONArray.fromObject(sysTableManage.getParameters());
//		for(int i=0;i<jsonArray.size();i++){
//			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//			Parameter field= sysTableManage.new Parameter();
//			field.setName(jsonObject.getString("na"));
//			field.setComment(jsonObject.getString("cm"));
//			field.setType(jsonObject.getString("ty"));
//			field.setValue(jsonObject.get("va"));
//			field.setValueFrom(jsonObject.getInt("vf"));
//			if(StringUtil.isEmpty(field.getComment())){
//				field.setComment(field.getName());
//			}
//			parameterList.add(field);
//		}
//		sysTableManage.setParameterList(parameterList);
//		return parameterList;
//	}
	
	private static Map<String, Object> getQueryStringMap(String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		int idx1 = url.indexOf("?");
		if (idx1 > 0) {
			String queryStr = url.substring(idx1 + 1);
			String[] queryNodeAry = queryStr.split("&");
			for (String queryNode : queryNodeAry) {
				String[] strAry = queryNode.split("=");
				if (strAry.length == 1) {
					map.put(strAry[0], null);
				} else {
					map.put(strAry[0].trim(), strAry[1]);
				}
			}
		}
		return map;
	}

	private static Object getObjValue(String type,String paramValue)
    {
    	Object value=null;
    	//字符串
    	if("S".equals(type)){
			value=paramValue;
		}
    	//长整型
    	else if("L".equals(type)){
			value=new Long(paramValue);
		}
    	//整型
    	else if("N".equals(type)){
    		value=new Integer(paramValue);
		}
    	else if("DB".equals(type)){
    		value=new Double(paramValue);
		}
    	//decimal
    	else if("BD".equals(type)){
			value=new BigDecimal(paramValue);
		}
    	//FLOAT
    	else if("FT".equals(type)){
			value=new Float(paramValue);
		}
    	//short
    	else if("SN".equals(type)){
			value=new Short(paramValue);
		}
    	//date
    	else if("D".equals(type)){
    		value=TimeUtil.convertString(paramValue,"yyyy-MM-dd");
		}
    	//date Range
    	else if("DR".equals(type)){
    		String[] values=paramValue.split(",");
    		Date[] dates=new Date[2];
    		if(StringUtil.isNotEmpty(values[0])){
    			dates[0]=TimeUtil.convertString(values[0],"yyyy-MM-dd");
    		}
    		if(StringUtil.isNotEmpty(values[1])){
    			dates[0]=TimeUtil.convertString(values[1],"yyyy-MM-dd");
    		}
    		value=dates;
		}
    	//date begin
    	else if("DL".equals(type)){
			value=TimeUtil.convertString(paramValue,"yyyy-MM-dd");
		}
    	//date end
    	else if("DG".equals(type)){
			value=TimeUtil.getNextDays(TimeUtil.convertString(paramValue,"yyyy-MM-dd"),1);
		}else{
			value=paramValue;
		}
    	return value;
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
		}  else if (dbType.equals(SqlTypeConst.H2)) {
			dialect = new H2Dialect();
		}else {
			throw new Exception("没有设置合适的数据库类型");
		}
		return dialect;
	
	}

	/**
	 * {"id":"id","pid":"fatherId","displayName":"name"}
	 * 
	 * @param displayField
	 * @param objName
	 * @return
	 */
	private String getTreeSql(SysTableManage sysTableManage, Map<String, Object> params) {
		String displayField = sysTableManage.getDisplayField();
		String objName = sysTableManage.getObjName();
		// 获取条件的SQL语句
		String sqlWhere = getWhereSQL(sysTableManage, params);
		JSONObject jsonObj = JSONObject.fromObject(displayField);
		String id = jsonObj.getString("id");
		String pid = jsonObj.getString("pid");
		String displayName = jsonObj.getString("displayName");
		String sql = "SELECT " + id + "," + pid + "," + displayName + " FROM " + objName + sqlWhere;
	
		return sql;
	}

	/**
	 * 根据传入的参数，计算Order By 语句
	 * 
	 * @param sysTableManage
	 * @param params
	 * @return
	 */
	private String getSQL(SysTableManage sysTableManage, Map<String, Object> params) {
		// table id code
		String tableIdCode = "";
		if (params.get("__tic") != null) {
			tableIdCode = (String) params.get("__tic");
		}
	
		String sql = "SELECT a.* FROM " + sysTableManage.getObjName() +" a";
		// where sql
		String where = null;
		// there two condition build type
		if (sysTableManage.getConditionType() == 1) {
			where = getWhereSQL(sysTableManage, params);
		} else {
			where = parseConditionScript(sysTableManage, params);
		}
		if (StringUtil.isNotEmpty(where)) {
			sql += " WHERE " + where;
		}
		// order sql
		String orderBy = " ORDER BY ";
		if (params.containsKey(tableIdCode + SysTableManage.SORTFIELD)) {
			orderBy += params.get(tableIdCode + SysTableManage.SORTFIELD);
		} else {
			return sql;
		}
	
		if (params.containsKey(tableIdCode + SysTableManage.ORDERSEQ)) {
			orderBy += " " + params.get(tableIdCode + SysTableManage.ORDERSEQ);
		} else {
			orderBy += " ASC";
		}
		sql = sql + orderBy;
		return sql;
	
	}

	/**
	 * 根据条件列表，计算Where SQL 语句
	 * 
	 * @param conditions
	 * @param params
	 * @return
	 */
	private String getWhereSQL(SysTableManage sysTableManage, Map<String, Object> params) {
		StringBuffer sb = new StringBuffer();
		List<SQLClause> conditions = sysTableManage.getConditionList();
		for (SQLClause condition : conditions) {
			getCluaseSQL(sysTableManage,condition, params, sb);
		}
		if (sb.length() > 0) {
			return " (1=1 " + sb.toString() + ") ";
		}
		return "";
	}

	private void getCluaseSQL(SysTableManage sysTableManage, SQLClause condition, Map<String, Object> params, StringBuffer sb) {
		String field = condition.getName();
		String operate = condition.getOperate();
		int valueFrom = condition.getValueFrom();
		String joinType = condition.getJoinType();
		String type = condition.getType();
		joinType = " " + joinType + " ";
		Object value = null;
		switch (valueFrom) {
		case 1:// 输入
			if (params.containsKey(field)) {
				value = params.get(field);
			}
			break;
		case 2:// 固定值
			value = condition.getValue();
			break;
		case 3:// 脚本
			String script = (String) condition.getValue();
			if (StringUtil.isNotEmpty(script)) {
				value = groovyScriptEngine.executeObject(script, null);
			}
			break;
		case 4:// 自定义变量
			value = sysTableManage.getParameterMap().get(condition.getValue().toString());
			break;
		}
		if (BeanUtils.isEmpty(value))
			return;
		if (type.equals(ColumnModel.COLUMNTYPE_VARCHAR)) {
			if (operate.equalsIgnoreCase("=")) {
				sb.append(joinType + field + "='" + value.toString() + "' ");
			} else if (operate.equalsIgnoreCase("LIKE")) {
				sb.append(joinType + field + " LIKE '%" + value.toString() + "%' ");
			} else {
				sb.append(joinType + field + " LIKE '" + value.toString() + "%' ");
			}
		} else if (type.equals(ColumnModel.COLUMNTYPE_DATE)) {
			if (operate.equalsIgnoreCase("=")) {
				if (valueFrom == 1) {
					if (params.containsKey(field)) {
						sb.append(joinType + field + "=:" + field + " ");
					}
				} else {
					sb.append(joinType + field + "=:" + field + " ");
					params.put(field, value);
				}
	
			} else if (operate.equalsIgnoreCase(">=")) {
				if (valueFrom == 1) {
					if (params.containsKey(field)) {
						sb.append(joinType + field + ">=:" + field + " ");
					}
				} else {
					sb.append(joinType + field + ">=:" + field + " ");
					params.put(field, value);
				}
			} else if (operate.equals("<=")) {
				if (valueFrom == 1) {
					if (params.containsKey(field)) {
						sb.append(joinType + field + "<=:" + field + " ");
					}
				} else {
					sb.append(joinType + field + "<=:" + field + " ");
					params.put(field, value);
				}
			}
		} else {
			if (valueFrom == 1) {
				if (params.containsKey(field)) {
					sb.append(joinType + field + operate + ":" + field + " ");
				}
			} else {
				sb.append(joinType + field + operate + ":" + field + " ");
				params.put(field, value);
			}
		}
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
		SysDataSource sysDataSource = sysDataSourceDao.getByAlias(dsName);
		JdbcHelper jdbcHelper = JdbcHelper.getInstance();
		jdbcHelper.init(dsName, sysDataSource.getDriverName(), sysDataSource.getUrl(), sysDataSource.getUserName(), sysDataSource.getPassword());
		jdbcHelper.setCurrentDb(dsName);
		Dialect dialect = getDialect(sysDataSource.getDbType());
		jdbcHelper.setDialect(dialect);
		return jdbcHelper;
	}
}