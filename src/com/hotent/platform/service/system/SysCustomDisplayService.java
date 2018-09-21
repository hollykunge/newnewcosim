package com.hotent.platform.service.system;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLUnionQuery;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.util.JdbcUtils;
import com.hotent.core.customertable.ColumnModel;
import com.hotent.core.customertable.SqlTypeConst;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.db.JdbcHelper;
import com.hotent.core.db.entity.SQLClause;
import com.hotent.core.db.entity.SQLField;
import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.mybatis.Dialect;
import com.hotent.core.mybatis.dialect.DB2Dialect;
import com.hotent.core.mybatis.dialect.MySQLDialect;
import com.hotent.core.mybatis.dialect.OracleDialect;
import com.hotent.core.mybatis.dialect.SQLServer2005Dialect;
import com.hotent.core.page.PageBean;
import com.hotent.core.page.PageUtils;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.tag.PageTag;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.dao.system.SysCustomDisplayDao;
import com.hotent.platform.dao.system.SysDataSourceDao;
import com.hotent.platform.model.system.SysCustomDisplay;
import com.hotent.platform.model.system.SysDataSource;
import com.hotent.platform.model.system.SysManageField;
import com.hotent.platform.model.system.SysTableManage;

import freemarker.template.TemplateException;

/**
 * 对象功能:SYS_CUSTOM_DISPLAY Service类 开发公司:宏天 开发人员:Raise 创建时间:2012-10-23 17:59:41
 */
@Service
public class SysCustomDisplayService extends BaseService<SysCustomDisplay> {
	@Resource
	private SysCustomDisplayDao dao;
	@Resource
	private FreemarkEngine freemarkEngine;
	@Resource
	private SysDataSourceDao sysDataSourceDao;
	@Resource
	GroovyScriptEngine groovyScriptEngine;

	private String getDataURL = "/platform/system/sysCustomDisplay/getData.ht";

	public SysCustomDisplayService() {
	}

	@Override
	protected IEntityDao<SysCustomDisplay, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 取得自定义显示生成的Html
	 * 
	 * @param id
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getDisplay(Long id, Map<String, Object> params) throws Exception {
		SysCustomDisplay sysCustomDisplay = getById(id);
		return getDisplay(sysCustomDisplay, params);
	}

	/**
	 * @param sysCustomDisplay
	 * @param params
	 *            __tic：Table Id Code __baseDspURL：请求自定义显示组件的URL
	 * @return
	 * @throws Exception
	 */
/*********************************
	public String getDisplay(SysCustomDisplay sysCustomDisplay, Map<String, Object> params) throws Exception {
		String html = "";
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		String tableIdCode = (String) params.get("__tic");
		if (tableIdCode == null) {
			tableIdCode = "";
			params.put("__tic", tableIdCode);
		}
		String baseURL = (String) params.get("__baseDspURL");
		// 排序
		String sortField = null;
		String orderSeq = "DESC";
		String newSortField = null;
		if (params.get(tableIdCode + SysCustomDisplay.SORTFIELD) != null) {
			sortField = (String) params.get(tableIdCode + SysCustomDisplay.SORTFIELD);
		}
		if (params.get(tableIdCode + SysCustomDisplay.ORDERSEQ) != null) {
			orderSeq = (String) params.get(tableIdCode + SysCustomDisplay.ORDERSEQ);
		}
		if (params.get(tableIdCode + "__ns__") != null) {
			newSortField = (String) params.get(tableIdCode + "__ns__");
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
			params.put(tableIdCode + SysCustomDisplay.SORTFIELD, sortField);
			params.put(tableIdCode + SysCustomDisplay.ORDERSEQ, orderSeq);
		}

		List<String> excludes = new ArrayList<String>();
		for (String key : params.keySet()) {
			if (key.endsWith("__ns__")) {
				excludes.add(key);
			}
		}
		excludes.add("rand");
		excludes.add("__baseURL");
		excludes.add("__tic");
		excludes.add("__displayId");
		excludes.add("__baseDspURL");
		String pageURL = addParametersToUrl(baseURL, params, excludes);
		// 搜索表单的URL，不包含 tableIdCode + "__id__"
		String searchFormURL = baseURL;

		List<Map<String, Object>> dataList = getData(sysCustomDisplay, params);
		String pageHtml = "";
		// 需要分页
//		if (sysCustomDisplay.getNeedPage() == SysCustomDisplay.NEEDPAGE_YES) {
		if(true){
			Map<String, Object> pageModel = new HashMap<String, Object>();
			pageModel.put("tableIdCode", tableIdCode);
			pageModel.put("pageBean", sysCustomDisplay.getPageBean());
			pageModel.put("showExplain", true);
			pageModel.put("showPageSize", true);
			pageModel.put("baseHref", pageURL);
			pageModel.put("pageURL", pageURL);
			pageHtml = freemarkEngine.mergeTemplateIntoString("pageAjax.ftl", pageModel);
		}
		// 第一次解析FreeMarker模板，以SQL和设置的元数据生成显示模板
		Map<String, Object> freeMarkerModel = new HashMap<String, Object>();
		freeMarkerModel.put("displayFields", sysCustomDisplay.getDisplayFields());
		freeMarkerModel.put("conditionFields", sysCustomDisplay.getConditionFields());
		freeMarkerModel.put("tbarTitle", sysCustomDisplay.getName());
		String dataTemplate = freemarkEngine.mergeTemplateIntoString("custom/customDataTemplate.ftl", freeMarkerModel);
		// 第二次解析FreeMarker模板，填充数据
		freeMarkerModel.clear();
		freeMarkerModel.put("dataList", dataList);
		freeMarkerModel.put("displayId", sysCustomDisplay.getId());
		freeMarkerModel.put("searchFormURL", searchFormURL);
		freeMarkerModel.put("pageHtml", pageHtml);
		freeMarkerModel.put("pageURL", pageURL);
		freeMarkerModel.put("sortField", sortField);
		freeMarkerModel.put("orderSeq", orderSeq);
		freeMarkerModel.put("tableIdCode", tableIdCode);
		freeMarkerModel.put("searchFormURL", searchFormURL);
		html = freemarkEngine.parseByStringTemplate(freeMarkerModel, dataTemplate);
		return html;
	}
*********************************/
	public String getDisplay(SysCustomDisplay sysCustomDisplay, Map<String, Object> params) throws Exception {
		// 第一次解析FreeMarker模板，以SQL和设置的元数据生成显示模板
		Map<String, Object> freeMarkerModel = new HashMap<String, Object>();
		freeMarkerModel.put("sysCustomDisplay",sysCustomDisplay);
//		String dataTemplate = freemarkEngine.mergeTemplateIntoString("custom/"+sysCustomDisplay.getTemplate(), freeMarkerModel);
		String dataTemplate=sysCustomDisplay.getDspTemplate();
		if(StringUtil.isEmpty(dataTemplate)){
			dataTemplate=generateDspTemplate(sysCustomDisplay, params);
		}
		
		String tableIdCode="";
		if(params.containsKey("__tic")){
			tableIdCode=(String) params.get("__tic");
		}
		//取排序信息
		String sortField = null;
		String orderSeq = "DESC";
		String newSortField = null;
		if (params.get(tableIdCode + SysCustomDisplay.SORTFIELD) != null) {
			sortField = (String) params.get(tableIdCode + SysCustomDisplay.SORTFIELD);
		}
		if (params.get(tableIdCode + SysCustomDisplay.ORDERSEQ) != null) {
			orderSeq = (String) params.get(tableIdCode + SysCustomDisplay.ORDERSEQ);
		}
		if (params.get(tableIdCode + "__ns__") != null) {
			newSortField = (String) params.get(tableIdCode + "__ns__");
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
			params.put(tableIdCode + SysCustomDisplay.SORTFIELD, sortField);
			params.put(tableIdCode + SysCustomDisplay.ORDERSEQ, orderSeq);
		}
		
		//取数据集
		List<Map<String, Object>> dataMaps = getDataMaps(sysCustomDisplay, params);
		
		//数据集
		freeMarkerModel.put("dataMaps", dataMaps);
		//分页信息
		if(sysCustomDisplay.getPaged()==1){
			freeMarkerModel.put("paged",true);
			freeMarkerModel.put("pageBean", sysCustomDisplay.getPageBean());
		}else{
			freeMarkerModel.put("paged",false);
		}

		//排序信息
		freeMarkerModel.put("sorted", sortField!=null);
		if(sortField!=null){
			freeMarkerModel.put("sortField", sortField);
			freeMarkerModel.put("orderSeq", orderSeq);
		}
		//附加-----页面路径信息
		//上下文路径
		freeMarkerModel.put("ctx", RequestUtil.getHttpServletRequest().getContextPath());
		//当前页面的BaseURL
		String baseURL=RequestUtil.getHttpServletRequest().getRequestURI();
		freeMarkerModel.put("baseURL", baseURL);
		//当前页面的带查询参数的URL
		List<String> excludes = new ArrayList<String>();
		for (String key : params.keySet()) {
			if (key.endsWith("__ns__")) {
				excludes.add(key);
			}
		}
		excludes.add("rand");
		excludes.add("__baseURL");
		excludes.add("__tic");
		excludes.add("__displayId");
		String pageURL = addParametersToUrl(baseURL, params, excludes);
		freeMarkerModel.put("pageURL", pageURL);
		//当前请求的查询参数
		freeMarkerModel.put("pageParams", getQueryParameter(params, excludes));
		
		//进行二次模板解析
		dataTemplate=freemarkEngine.parseByStringTemplate(freeMarkerModel, dataTemplate);
		
		return dataTemplate;
	}
	
	private String getQueryParameter(Map<String,Object> params,List<String> excludes){
		String querys="";
		for(String key:params.keySet()){
			if(!excludes.contains(key)){
				querys+=key+"="+params.get(key)+"&";
			}
		}
		if(StringUtil.isNotEmpty(querys)){
			querys=querys.substring(0, querys.length()-1);
		}
		return querys;
	}
	
	
	/**
	 * 根据SQL语句，取得该SQL语句所选择的数据列
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<SQLField> getSQLFieldsBySQL(String dsName, String sql) throws Exception {
		final String finalsql = sql;
		JdbcHelper jdbcHelper = getJdbcHelper(dsName);
		JdbcTemplate jdbcTemplate = jdbcHelper.getJdbcTemplate();
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(finalsql);
				preparedStatement.setMaxRows(1);
				return preparedStatement;
			}
		};
		ResultSetExtractor<List<SQLField>> rse = new ResultSetExtractor<List<SQLField>>() {
			@Override
			public List<SQLField> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ResultSetMetaData resultSetMetaData = rs.getMetaData();
				List<SQLField> sqlFields = new ArrayList<SQLField>();
				int count = resultSetMetaData.getColumnCount();
				for (int i = 1; i <= count; i++) {
					SQLField field = new SQLField();
					field.setName(resultSetMetaData.getColumnName(i));
					field.setType(mapColumnType(resultSetMetaData.getColumnType(i)));
					field.setTable(resultSetMetaData.getTableName(i));
					field.setLabel(resultSetMetaData.getColumnLabel(i));
					field.setIndex(i);
					
					field.setQualifiedName(field.getName());
					field.setComment(field.getName());
					
					sqlFields.add(field);
				}
				return sqlFields;
			}
		};
		List<SQLField> sqlFields = jdbcTemplate.query(psc, rse);
//		setQualifiedName(sqlFields, sql , dsName);
		return sqlFields;
	}
	
	public List<Map<String,Object>> getDataMaps(Long id,Map<String,Object> params) throws Exception {
		SysCustomDisplay sysCustomDisplay=getById(id);
		return getDataMaps(sysCustomDisplay, params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String,Object>> getDataMaps(SysCustomDisplay sysCustomDisplay,Map<String,Object> params) throws Exception {
		String tableIdCode = "";
		if(params.containsKey("__tic")){
			tableIdCode = (String) params.get("__tic");
		}
		String sql=null;
		PageBean pageBean = null;
		// 当前页
		if(sysCustomDisplay.getPaged()==1){
			int currentPage = 1;
			Object pageObj = params.get(tableIdCode + SysCustomDisplay.PAGE);
			if (pageObj != null) {
				currentPage = Integer.parseInt(params.get(tableIdCode + SysCustomDisplay.PAGE).toString());
			}
			// 分页大小
			int pageSize = sysCustomDisplay.getPageSize();
//			int pageSize = 0;
			Object pageSizeObj = params.get(tableIdCode + SysCustomDisplay.PAGESIZE);
			if (pageSizeObj != null) {
				pageSize = Integer.parseInt(params.get(tableIdCode + SysCustomDisplay.PAGESIZE).toString());
			}
			
			int oldPageSize = sysCustomDisplay.getPageSize();
//			int oldPageSize=0;
			// 前分页大小
			Object oldPageSizeObj = params.get(tableIdCode + "oz");
			if (oldPageSizeObj != null) {
				oldPageSize = Integer.parseInt(params.get(tableIdCode + "oz").toString());
			}
			
			int first = PageUtils.getFirstNumber(currentPage, oldPageSize);
			currentPage = first / pageSize + 1;
			pageBean = new PageBean(currentPage, pageSize);
		
		}
		sql = getSQL_(sysCustomDisplay, params);
		
		JdbcHelper jdbcHelper = getJdbcHelper(sysCustomDisplay.getDsName());
		List<Map<String,Object>> dataMaps = jdbcHelper.queryForList(sql, params,pageBean);
		
		// 是否需要分页。
		sysCustomDisplay.setPageBean(pageBean);
		return dataMaps;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, Object>> getData(SysCustomDisplay sysCustomDisplay, Map<String, Object> params) throws Exception {
		String tableIdCode = (String) params.get("__tic");
		String sql=null;
		PageBean pageBean = null;
		if(sysCustomDisplay.getPaged() == SysCustomDisplay.NEEDPAGE_YES){
			// 当前页
			int currentPage = 1;
			Object pageObj = params.get(tableIdCode + SysCustomDisplay.PAGE);
			if (pageObj != null) {
				currentPage = Integer.parseInt(params.get(tableIdCode + SysCustomDisplay.PAGE).toString());
			}
			// 分页大小
			int pageSize = sysCustomDisplay.getPageSize();
			Object pageSizeObj = params.get(tableIdCode + SysCustomDisplay.PAGESIZE);
			if (pageSizeObj != null) {
				pageSize = Integer.parseInt(params.get(tableIdCode + SysCustomDisplay.PAGESIZE).toString());
			}
			Object oldPageSizeObj = params.get(tableIdCode + "oz");
			int oldPageSize =sysCustomDisplay.getPageSize();
			if (oldPageSizeObj != null) {
				oldPageSize = Integer.parseInt(params.get(tableIdCode + "oz").toString());
			}
			if (pageSize != oldPageSize) {
				int first = PageUtils.getFirstNumber(currentPage, oldPageSize);
				currentPage = first / pageSize + 1;
			}
			pageBean = new PageBean(currentPage, pageSize);
		}
		
		sql = getSQL(sysCustomDisplay, params);
		
		JdbcHelper jdbcHelper = getJdbcHelper(sysCustomDisplay.getDsName());
		ResultSetExtractor<List<Map<String, Object>>> rse = new ResultSetExtractor<List<Map<String, Object>>>() {
			@Override
			public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				while (rs.next()) {
					Map<String, Object> row = new HashMap<String, Object>();
					for (int i = 1; i <= count; i++) {
						row.put("" + i, rs.getObject(i));
					}
					rows.add(row);
				}
				return rows;
			}
		};
		// 是否需要分页。
		List<Map<String, Object>> datas = (List<Map<String, Object>>) jdbcHelper.getPage(sql, rse, pageBean, params);
		sysCustomDisplay.setPageBean(pageBean);
		return datas;
	}

	/**
	 * JDBC Types类型与Column 中的Type映射
	 * 
	 * @param columnType
	 * @return
	 */
	public static String mapColumnType(int columnType) {
		String type = null;
		switch (columnType) {
		// varchar
		case Types.CHAR:
		case Types.LONGNVARCHAR:
		case Types.LONGVARBINARY:
		case Types.LONGVARCHAR:
		case Types.NCHAR:
		case Types.VARCHAR:
		case Types.NVARCHAR:
			type = SQLField.COLUMNTYPE_VARCHAR;
			break;
		// data
		case Types.TIME:
		case Types.TIMESTAMP:
		case Types.DATE:
			type = SQLField.COLUMNTYPE_DATE;
			break;
		// numeric
		case Types.DECIMAL:
		case Types.DOUBLE:
		case Types.FLOAT:
		case Types.NUMERIC:
		case Types.REAL:
			type = SQLField.COLUMNTYPE_NUMBER;
			break;
		// int
		case Types.BIGINT:
		case Types.BOOLEAN:
		case Types.BIT:
		case Types.INTEGER:
		case Types.SMALLINT:
		case Types.TINYINT:
			type = SQLField.COLUMNTYPE_INT;
			break;
		// blob
		case Types.BLOB:
		case Types.CLOB:
		case Types.NCLOB:
		case Types.SQLXML:
			type = SQLField.COLUMNTYPE_CLOB;
			break;
		// ---
		case Types.ARRAY:
		case Types.BINARY:
		case Types.DATALINK:
		case Types.DISTINCT:
		case Types.JAVA_OBJECT:
		case Types.NULL:
		case Types.OTHER:
		case Types.REF:
		case Types.ROWID:
		case Types.STRUCT:
		case Types.VARBINARY:
			type = SQLField.COLUMNTYPE_VARCHAR;
			break;
		}
		return type;
	}

	private String getSQL(SysCustomDisplay sysCustomDisplay, Map<String, Object> params){
		StringBuffer newsql=new StringBuffer();
		String sql = sysCustomDisplay.getScript();
//		if (sysCustomDisplay.getSupSubSelect() != SysCustomDisplay.SUPSUBSELECT_YES) {
//			return sql;
//		}
		
		String dbType=getDbTypeByDataSourceName(sysCustomDisplay.getDsName());
		SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, dbType);
		SQLSelectStatement sqlStatement = (SQLSelectStatement) parser.parseStatementList().get(0);
		SQLSelectQuery sqlSelectQuery = sqlStatement.getSelect().getQuery();
		
		if(sqlSelectQuery instanceof SQLUnionQuery){
			return sql;
		}else{
			StringBuffer select = new StringBuffer();
			StringBuffer from = new StringBuffer();
			StringBuffer where = new StringBuffer();
			StringBuffer groupby = new StringBuffer();
			SQLSelectQueryBlock query = (SQLSelectQueryBlock) sqlSelectQuery;
			SQLASTOutputVisitor selectVisitor = SQLUtils.createFormatOutputVisitor(select, parser.parseStatementList(), dbType);
			SQLASTOutputVisitor whereVisitor = SQLUtils.createFormatOutputVisitor(where, parser.parseStatementList(), dbType);
			SQLASTOutputVisitor fromVisitor = SQLUtils.createFormatOutputVisitor(from, parser.parseStatementList(), dbType);
			SQLASTOutputVisitor groupbyVisitor = SQLUtils.createFormatOutputVisitor(from, parser.parseStatementList(), dbType);
			for (SQLSelectItem item : query.getSelectList()) {
				item.accept(selectVisitor);
				select.append(", ");
			}
			select.delete(select.length() - 2, select.length());
			if (query.getFrom() != null) {
				query.getFrom().accept(fromVisitor);
			}
			if (query.getWhere() != null) {
				query.getWhere().accept(whereVisitor);
			}
			if (query.getGroupBy() != null) {
				query.getGroupBy().accept(groupbyVisitor);
			}
	
			String whereSQL = getWhereSQL(sysCustomDisplay.getConditionFields(), params);
	
			if (StringUtil.isNotEmpty(whereSQL)) {
				if (where.length() > 0) {
					where.append(" and ").append(whereSQL);
				} else {
					where.append(whereSQL);
				}
			}
			newsql.append("SELECT ");
			newsql.append(select);
			if (from.length() > 0) {
				newsql.append(" FROM ");
				newsql.append(from);
			}
			if (where.length() > 0) {
				newsql.append(" WHERE ");
				newsql.append(where);
			}
			if (groupby.length() > 0) {
				newsql.append("GROUP BY");
				newsql.append(groupby);
			}
			String orderSQL = getOrderSQL(params);
			newsql.append(" " + orderSQL);
			return newsql.toString();
		}		
	}
	
	private String getSQL_(SysCustomDisplay sysCustomDisplay,Map<String,Object> params){
		String script=sysCustomDisplay.getScript();
		String whereSQL = getWhereSQL(sysCustomDisplay.getConditionFields(), params);
		String sql="SELECT a.* FROM ("+script+") a";
		if (StringUtil.isNotEmpty(whereSQL)) {
			sql+=" WHERE "+whereSQL;
		}
		String orderSQL = getOrderSQL(params);
		sql +=" " +orderSQL;
		return sql;
	}
	

	private String getWhereSQL(List<SQLClause> conditions, Map<String, Object> params) {
		StringBuffer sb = new StringBuffer();
		for (SQLClause condition : conditions) {
			getCluaseSQL(condition, params, sb);
		}
		if (sb.length() > 0) {
			return " (1=1 " + sb.toString() + ") ";
		}
		return "";
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

	/**
	 * 获取方言。
	 * 
	 * @param dbType
	 * @return
	 * @throws Exception
	 */
	private static Dialect getDialect(String dbType) throws Exception {
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

	private void setQualifiedName(List<SQLField> sqlFields, String sql ,String dsName) {
		SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql,getDbTypeByDataSourceName(dsName));
		SQLSelectStatement sqlStatement = (SQLSelectStatement) parser.parseStatementList().get(0);
		SQLSelectQuery sqlSelectQuery = sqlStatement.getSelect().getQuery();
		if(sqlSelectQuery instanceof SQLUnionQuery){
			for (SQLField field : sqlFields) {
				field.setQualifiedName(field.getName());
			}
			return;
		}
		SQLSelectQueryBlock query = (SQLSelectQueryBlock) sqlSelectQuery;
		List<SQLSelectItem> selectItems = query.getSelectList();
		if (selectItems.size() == 1) {
			StringBuffer buf = new StringBuffer();
			selectItems.get(0).getExpr().output(buf);
			if (buf.toString().equals("*")) {
				for (SQLField field : sqlFields) {
					field.setQualifiedName(field.getName());
				}
				return;
			}

		}
		for (int i = 0; i < selectItems.size(); i++) {
			StringBuffer qualifiedName = new StringBuffer();
			selectItems.get(i).getExpr().output(qualifiedName);
			sqlFields.get(i).setQualifiedName(qualifiedName.toString());
		}
	}

	private static String getOrderSQL(Map<String, Object> params) {
		if (BeanUtils.isEmpty(params)) {
			return "";
		}
		String tableIdCode = "";
		if (params.get("__tic") != null) {
			tableIdCode = (String) params.get("__tic");
		}

		String orderBy = " ORDER BY ";
		if (params.containsKey(tableIdCode + SysCustomDisplay.SORTFIELD)) {
			orderBy += params.get(tableIdCode + SysTableManage.SORTFIELD);
		} else {
			return "";
		}
		if (params.containsKey(tableIdCode + SysTableManage.ORDERSEQ)) {
			orderBy += " " + params.get(tableIdCode + SysTableManage.ORDERSEQ);
		} else {
			orderBy += " ASC";
		}
		return orderBy;
	}

	private void getCluaseSQL(SQLClause condition, Map<String, Object> params, StringBuffer sb) {
		String field = condition.getQualifiedName();
		String operate = condition.getOperate();
		int valueFrom = condition.getValueFrom();
		String joinType = condition.getJoinType();
		String type = condition.getType();
		joinType = " " + joinType + " ";
		Object value = null;
		switch (valueFrom) {
		case 1:
			if (params.containsKey(field)) {
				value = params.get(field);
			}
			break;
		case 2:
			value = condition.getValue();
			break;
		case 3:
			String script = (String) condition.getValue();
			if (StringUtil.isNotEmpty(script)) {
				value = groovyScriptEngine.executeObject(script, null);
			}
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
					if (params.containsKey("START" + field)) {
						sb.append(joinType + field + ">=:START" + field + " ");
					}
				} else {
					sb.append(joinType + field + ">=:START" + field + " ");
					params.put("START" + field, value);
				}
			} else if (operate.equals("<=")) {
				if (valueFrom == 1) {
					if (params.containsKey("END" + field)) {
						sb.append(joinType + field + "<=:END" + field + " ");
					}
				} else {
					sb.append(joinType + field + "<=:END" + field + " ");
					params.put("END" + field, value);
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
	
	private String getDbTypeByDataSourceName(String dsName){
		SysDataSource sysDataSource = sysDataSourceDao.getByAlias(dsName);
		String dbType = sysDataSource.getDbType();
		String parserDbType="";
		if(dbType.equals(SqlTypeConst.MYSQL)){
			parserDbType=JdbcUtils.MYSQL;
		}else if(dbType.equals(SqlTypeConst.ORACLE)){
			parserDbType=JdbcUtils.ORACLE;
		}else if(dbType.equals(SqlTypeConst.SQLSERVER)){
			parserDbType=JdbcUtils.ORACLE;
		}else if(dbType.equals(SqlTypeConst.DB2)){
			parserDbType=JdbcUtils.DB2;
		}else{
			parserDbType=dbType;
		}
		return parserDbType;
	}

	public String generateDspTemplate(SysCustomDisplay sysCustomDisplay,Map<String,Object> params) throws IOException, TemplateException {
		// 第一次解析FreeMarker模板，以SQL和设置的元数据生成显示模板
		Map<String, Object> freeMarkerModel = new HashMap<String, Object>();
		freeMarkerModel.put("sysCustomDisplay",sysCustomDisplay);
		String dataTemplate = freemarkEngine.mergeTemplateIntoString("custom/"+sysCustomDisplay.getTemplate(), freeMarkerModel);
		return dataTemplate;
	}
	
	public String getSQLInDisplay(SysCustomDisplay sysCustomDisplay,Map<String,Object> paramMap){
		String sql="";
		String script=sysCustomDisplay.getScript();
		int type=sysCustomDisplay.getScriptType();
		switch (type) {
		case SysCustomDisplay.SCRIPTTYPE_SQL:
			sql=script;
			break;
		case SysCustomDisplay.SCRIPTTYPE_GROOVY:
			sql=groovyScriptEngine.executeString(script, paramMap);
			break;
		default:
			break;
		}
		return sql;
	}
}
