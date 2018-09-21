package com.hotent.platform.service.util;

import java.util.List;
import java.util.Map;

import com.hotent.core.customertable.ColumnModel;
import com.hotent.core.customertable.SqlTypeConst;
import com.hotent.core.db.JdbcHelper;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.mybatis.Dialect;
import com.hotent.core.mybatis.dialect.DB2Dialect;
import com.hotent.core.mybatis.dialect.DmDialect;
import com.hotent.core.mybatis.dialect.H2Dialect;
import com.hotent.core.mybatis.dialect.MySQLDialect;
import com.hotent.core.mybatis.dialect.OracleDialect;
import com.hotent.core.mybatis.dialect.SQLServer2005Dialect;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.dao.system.SysDataSourceDao;
import com.hotent.platform.model.form.DialogField;
import com.hotent.platform.model.system.SysDataSource;

public class ServiceUtil {

	/**
	 * 根据数据源获取JdbcHelper。
	 * 
	 * @param dsName
	 * @return
	 * @throws Exception
	 */
	public static JdbcHelper getJdbcHelper(String dsName) throws Exception {
		SysDataSourceDao sysDataSourceDao = (SysDataSourceDao) AppUtil
				.getBean(SysDataSourceDao.class);
		SysDataSource sysDataSource = sysDataSourceDao.getByAlias(dsName);
		JdbcHelper jdbcHelper = JdbcHelper.getInstance();
		jdbcHelper.init(dsName, sysDataSource.getDriverName(),
				sysDataSource.getUrl(), sysDataSource.getUserName(),
				sysDataSource.getPassword());
		jdbcHelper.setCurrentDb(dsName);
		Dialect dialect = getDialect(sysDataSource.getDbType());
		jdbcHelper.setDialect(dialect);
		return jdbcHelper;
	}

	/**
	 * 获取列表的sql语句
	 * 
	 * @param objectName
	 *            表名或视图名称。
	 * @param displayList
	 *            显示字段列表。
	 * @param conditionList
	 *            条件字段列表。
	 * @param params
	 *            传入的条件列表。
	 * @return
	 */
	public static String getSql(String objectName,
			List<DialogField> retrunList, List<DialogField> displayList,
			List<DialogField> conditionList, Map params) {
		String sql = "select ";
		if (BeanUtils.isEmpty(retrunList)) {
			sql = "select a.* from " + objectName + " a";
		} else {
			String returnStr = "";
			for (DialogField dialogField : retrunList) {
				returnStr += "," + dialogField.getFieldName();
			}
			returnStr = returnStr.replaceFirst(",", "");
			sql += returnStr + " from " + objectName;
		}
		String where = getWhere(conditionList, params);

		if (BeanUtils.isEmpty(displayList)) {
			return sql + where;
		}
		String orderBy = " order by ";
		if (params.containsKey("sortField")) {
			orderBy += params.get("sortField");
		} else {
			orderBy += displayList.get(0).getFieldName();
		}
		if (params.containsKey("orderSeq")) {
			orderBy += " " + params.get("orderSeq");
		} else {
			orderBy += " ASC";
		}
		sql = sql + where + orderBy;
		return sql;
	}

	/**
	 * 取得where 条件。
	 * 
	 * @param conditionMap
	 *            条件map。
	 * @param params
	 *            传入的参数
	 * @return
	 */
	public static String getWhere(List<DialogField> conditionList,
			Map<String, Object> params) {
		StringBuffer sb = new StringBuffer();

		for (DialogField dialogField : conditionList) {
			getStringByDialogField(dialogField, params, sb);
		}
		if (sb.length() > 0) {
			return " where 1=1 " + sb.toString();
		}
		return "";
	}

	/**
	 * 根据参数配置，上下文参数计算SQL语句。
	 * 
	 * @param dialogField
	 * @param params
	 * @param sb
	 */
	private static void getStringByDialogField(DialogField dialogField,
			Map<String, Object> params, StringBuffer sb) {
		String field = dialogField.getFieldName();
		String condition = dialogField.getCondition();
		int conditionType = dialogField.getDefaultType();
		Object value = null;
		switch (conditionType) {
		case 1:
			if (params.containsKey(field)) {
				value = (String) params.get(field);
			}
			break;
		case 2:
			value = dialogField.getDefaultValue();
			break;
		case 3:
			String script = dialogField.getDefaultValue();
			if (StringUtil.isNotEmpty(script)) {
				GroovyScriptEngine groovyScriptEngine = (GroovyScriptEngine) AppUtil
						.getBean(GroovyScriptEngine.class);
				value = groovyScriptEngine.executeObject(script, null);
			}
		}
		if (BeanUtils.isEmpty(value))
			return;

		if (dialogField.getFieldType().equals(ColumnModel.COLUMNTYPE_VARCHAR)) {
			if (condition.equals("=")) {
				sb.append(" and " + field + "='" + value.toString() + "' ");
			} else if (condition.equals("like")) {
				sb.append(" and " + field + " like '%" + value.toString()
						+ "%' ");
			} else {
				sb.append(" and " + field + " like '" + value.toString()
						+ "%' ");
			}
		} else if (dialogField.getFieldType().equals(
				ColumnModel.COLUMNTYPE_DATE)) {
			if (dialogField.getCondition().equals("=")) {
				sb.append(" and " + field + "=:" + field + " ");
				if (!params.containsKey(field)) {
					params.put(field, value);
				}
			} else if (dialogField.getCondition().equals(">=")) {
				if (conditionType == 1) {
					if (params.containsKey("start" + field)) {
						sb.append(" and " + field + ">=:start" + field + " ");
					}
				} else {
					sb.append(" and " + field + ">=:start" + field + " ");
					params.put("start" + field, value);
				}
			} else if (dialogField.getCondition().equals("<=")) {
				if (conditionType == 1) {
					if (params.containsKey("end" + field)) {
						sb.append(" and " + field + "<=:end" + field + " ");
					}
				} else {
					sb.append(" and " + field + "<=:end" + field + " ");
					params.put("end" + field, value);
				}
			}
		} else {
			if (conditionType == 1) {
				if (params.containsKey(field)) {
					sb.append(" and " + field + dialogField.getCondition()
							+ ":" + field + " ");
				}
			} else {
				sb.append(" and " + field + dialogField.getCondition() + ":"
						+ field + " ");
				params.put(field, value);
			}
		}
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
		} else if (dbType.equals(SqlTypeConst.H2)) {
			dialect = new H2Dialect();
		} else if (dbType.equals(SqlTypeConst.DM)) {
			dialect = new DmDialect();
		} else {
			throw new Exception("没有设置合适的数据库类型");
		}
		return dialect;

	}

}
