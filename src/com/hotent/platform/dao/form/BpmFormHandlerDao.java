/**
 * 对象功能:自定义表单数据处理
 * 开发公司:
 * 开发人员:xwy
 * 创建时间:2011-12-22 11:07:56
 */
package com.hotent.platform.dao.form;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hotent.core.customertable.TableModel;
import com.hotent.core.db.JdbcDao;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.mybatis.Dialect;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.TimeUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.dao.bpm.BpmSubtableRightsDao;
import com.hotent.platform.dao.system.SysDataSourceDao;
import com.hotent.platform.model.bpm.BpmSubtableRights;
import com.hotent.platform.model.form.BpmFormData;
import com.hotent.platform.model.form.BpmFormField;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.form.PkValue;
import com.hotent.platform.model.form.SqlModel;
import com.hotent.platform.model.form.SubTable;
import com.hotent.platform.service.form.FormDataUtil;

@Repository
public class BpmFormHandlerDao {

	private Log logger = LogFactory.getLog(BpmFormHandlerDao.class);

	@Resource
	private BpmFormTableDao bpmFormTableDao;
	@Resource
	private SysDataSourceDao sysDataSourceDao;
	@Resource
	private BpmFormFieldDao bpmFormFieldDao;
	@Resource
	private BpmSubtableRightsDao bpmSubtableRightsDao;
	@Resource
	private Dialect dialect;
	@Resource
	private JdbcDao jdbcDao;

	/**
	 * 处理动态表单数据
	 * 
	 * @param bpmFormData
	 * @throws Exception
	 */
	public void handFormData(BpmFormData bpmFormData,String actDefId,String nodeId) throws Exception {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		List<SqlModel> list = FormDataUtil.parseSql(bpmFormData,actDefId,nodeId);
		for (SqlModel sqlModel : list) {
			String sql = sqlModel.getSql();
			if (StringUtil.isEmpty(sql)) continue;
			Object[] obs = sqlModel.getValues();
			jdbcTemplate.update(sql, obs);
			
		}
	}

	/**
	 * 判断指定的表是否有数据。
	 * 
	 * @param tableName
	 *            数据库表名
	 */
	public boolean getHasData(String tableName) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		int rtn = jdbcTemplate.queryForInt("select count(*) from " + tableName);
		return rtn > 0;
	}

	/**
	 * 根据主键查询列表数据。
	 * 
	 * @param tableId
	 * @param pkValue
	 * @return
	 * @throws Exception
	 */
	public BpmFormData getByKey(long tableId, String pkValue) throws Exception {
		return getByKey(tableId, pkValue, null, null);
	}
	
	/**
	 * 根据流程定义id，节点id和表ID获取子表显示条件的SQL片段。
	 * @param tableId
	 * @param actDefId
	 * @param nodeId
	 * @return
	 */
	private String getLimitSql(Long tableId, String actDefId,String nodeId){
		if(StringUtil.isEmpty(actDefId) || StringUtil.isEmpty(nodeId)) return "";
		String limitSql = "";
		
		BpmSubtableRights rule = bpmSubtableRightsDao.getByDefIdAndNodeId(actDefId, nodeId, tableId);
		if(rule==null) return "";
		// 处理权限,生成sql约束片段.
		switch (rule.getPermissiontype().intValue()) {
			case 0:// 简单配置:判断用户
				Long userId = ContextUtil.getCurrentUserId();
				limitSql = " and " + TableModel.CUSTOMER_COLUMN_CURRENTUSERID + " = " + userId;
				break;
			case 1:// 简单配置:判断组织
				ISysOrg org = ContextUtil.getCurrentOrg();
				if (org == null) {
					limitSql = " and 1 = 2";// 强制查询不到
				} else {
					limitSql = " and " + TableModel.CUSTOMER_COLUMN_CURRENTORGID + " = " + org.getOrgId();
				}
				break;
			case 2:// 脚本
				GroovyScriptEngine scriptEngine = (GroovyScriptEngine) AppUtil.getBean(GroovyScriptEngine.class);
				limitSql = scriptEngine.executeString(rule.getPermissionseting(), new HashMap<String, Object>());// 不需流程变量入参
				break;
			default:
				break;
		}
		return limitSql;
		
	}

	/**
	 * 根据主键查询列表数据。如果找不对应的数据，返回NULL
	 * 
	 * @param tableId
	 * @param pkValue
	 * @param defId
	 *            当前节点流程定义ID
	 * @param nodeId
	 *            当前节点ID
	 * @return
	 * @throws Exception
	 */
	public BpmFormData getByKey(long tableId, String pkValue, String actDefId, String nodeId) throws Exception {
		BpmFormTable mainFormTableDef = bpmFormTableDao.getById(tableId);

		List<BpmFormTable> formTableList = bpmFormTableDao.getSubTableByMainTableId(tableId);
		// 获取jdbctemplate对象。
		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		String tableName = mainFormTableDef.getTableName();
		String pkField = TableModel.PK_COLUMN_NAME;
		int isExternal = mainFormTableDef.getIsExternal();
		if (isExternal == 1) {
			pkField = mainFormTableDef.getPkField();
		} else {
			tableName = TableModel.CUSTOMER_TABLE_PREFIX + tableName;
		}
		PkValue pk = new PkValue(pkField, pkValue);
		// 取得主表的数据
		Map<String, Object> mainData = getByKey(tableName, pk, tableId);
		if(BeanUtils.isEmpty(mainData)){
			return null;
		}

		// 取子表的数据
		BpmFormData bpmFormData = new BpmFormData();
		for (BpmFormTable table : formTableList) {
			SubTable subTable = new SubTable();

			String fk = TableModel.FK_COLUMN_NAME;
			String subPk = TableModel.PK_COLUMN_NAME;
						
			List<Map<String, Object>> list = getByFk(table, pk.getValue().toString(), actDefId,nodeId);

			subTable.setTableName(table.getTableName().toLowerCase());
			subTable.setDataList(list);
			subTable.setFkName(fk);
			subTable.setPkName(subPk);
			bpmFormData.addSubTable(subTable);
		}

		bpmFormData.setTableId(tableId);
		bpmFormData.setTableName(tableName);
		bpmFormData.setPkValue(pk);
		bpmFormData.addMainFields(mainData);

		return bpmFormData;
	}

	/**
	 * 根据主键查询一条数据。如果找不对应的数据，返回NULL。
	 * 
	 * @param tableName
	 *            需要查询的表名
	 * @param pk
	 *            主键
	 * @param tableId
	 *            表ID
	 * @return
	 */
	public Map<String, Object> getByKey(String tableName, PkValue pk, Long tableId) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		String sql = "select a.* from " + tableName + " a where " + pk.getName() + "=" + pk.getValue();
		String sqlCount = "select count(*) from " + tableName + " a where " + pk.getName() + "=" + pk.getValue();
		int count = jdbcTemplate.queryForInt(sqlCount);
		if(count<=0){
			return null;
		}
		List<BpmFormField> fieldList = bpmFormFieldDao.getByTableId(tableId);
		Map<String, BpmFormField> fieldMap = convertToMap(fieldList);
		Map<String, Object> map = null;
		try {
			map = jdbcTemplate.queryForMap(sql);
			map = handMap(fieldMap, map);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			map = new HashMap<String, Object>();
		}
		return map;
	}
	
	public Map<String, Object> getByKey(String tableName, String pk) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		String sql = "select a.* from " + tableName + " a where id="  + pk;
		
		Map<String, Object> map = null;
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception ex) {
			map = new HashMap<String, Object>();
		}
		return map;
	}

	/**
	 * 将list转换为map对象。
	 * 
	 * @param fieldList
	 * @return
	 */
	private Map<String, BpmFormField> convertToMap(List<BpmFormField> fieldList) {
		Map<String, BpmFormField> map = new HashMap<String, BpmFormField>();
		for (BpmFormField field : fieldList) {
			String fieldName = field.getFieldName().toLowerCase();
			map.put(fieldName, field);
		}
		return map;
	}

	/**
	 * 根据外键查询列表数据。
	 * 
	 * @param tableName
	 * @param fkValue
	 * @param tableId
	 * @return
	 */
	public List<Map<String, Object>> getByFk(BpmFormTable table, String fkValue, Long tableId) {
		return getByFk(table, fkValue, null, null);
	}

	/**
	 * 根据外键查询列表数据。
	 * 
	 * @param tableName
	 *            表名
	 * @param fkValue
	 *            外键值
	 * @param tableId
	 *            子表ID
	 * @param limitSql
	 *            数据限制SQL(片段,以AND开头)
	 * @return
	 */
	public List<Map<String, Object>> getByFk(BpmFormTable table, String fkValue, String actDefId,String nodeId) {
		String subTableName = table.getTableName();
		Long tableId=table.getTableId();
		// if (isExternal == 1) {
		// TableRelation tableRelation =
		// mainFormTableDef.getTableRelation();
		// fk = tableRelation.getRelations().get(table.getTableName());
		// subPk = table.getPkField();
		// } else {
		subTableName = TableModel.CUSTOMER_TABLE_PREFIX + subTableName;
		// }
		
		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		String fk = TableModel.FK_COLUMN_NAME;
		String sql = "select a.* from " + subTableName + " a where " + fk + "=" + fkValue;
		
		String limitSql=getLimitSql(tableId, actDefId, nodeId);
		
		// 处理子表权限.2013-1-17,by wwz.
		if (!StringUtil.isEmpty(limitSql)) {
			sql += " " + limitSql;
		}

		List<BpmFormField> fieldList = bpmFormFieldDao.getByTableId(tableId);
		Map<String, BpmFormField> fieldMap = convertToMap(fieldList);

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> map : list) {
			Map<String, Object> obj = handMap(fieldMap, map);
			rtnList.add(obj);
		}
		return rtnList;
	}

	/**
	 * 处理返回的map数据 将key转换成小写。 如果是本地数据库，则替换字段前缀。 如果数据是日期类型，则转换数据格式。
	 * 
	 * @param isExternal
	 * @param fieldMap
	 * @param map
	 */
	private Map<String, Object> handMap(Map<String, BpmFormField> fieldMap, Map<String, Object> map) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String fieldName = entry.getKey().toLowerCase();
			if ("createtime".equalsIgnoreCase(fieldName)) {
				continue;
			}
			// String
			// key=fieldName.replaceFirst(TableModel.CUSTOMER_COLUMN_PREFIX.toLowerCase(),
			// "");
			String key = fieldName;
			if (fieldName.indexOf(TableModel.CUSTOMER_COLUMN_PREFIX.toLowerCase()) == 0) {
				key = fieldName.replaceFirst(TableModel.CUSTOMER_COLUMN_PREFIX.toLowerCase(), "");
			}
			Object obj = entry.getValue();
			if (obj == null) {
				rtnMap.put(key, "");
				continue;
			}
			// 对时间字段单独处理。
			if (obj instanceof Date) {
				BpmFormField bpmFormField = fieldMap.get(key);
				String format = bpmFormField.getPropertyMap().get("format");
				if (StringUtil.isEmpty(format)) {
					format = "yyyy-MM-dd";
				}
				String str = TimeUtil.getDateTimeString((Date) obj, format);
				rtnMap.put(key, str);
			} else {
				BpmFormField bpmFormField = fieldMap.get(key);
				//附件替换
				if(BeanUtils.isNotEmpty(bpmFormField) && BeanUtils.isNotEmpty(bpmFormField.getControlType()) && bpmFormField.getControlType().shortValue() ==BpmFormField.ATTACHEMENT ){
					obj = ((String) obj).replace("\"", "￥@@￥");
				}		
				rtnMap.put(key, obj);
			}
		}
		return rtnMap;
	}

	/**
	 * 查找
	 * 
	 * @param tableName
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getAll(Long tableId, Map<String, Object> param) throws Exception {
		BpmFormTable table = bpmFormTableDao.getById(tableId);
		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");

		// 字段名与数据
		StringBuffer where = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		for (Map.Entry<String, Object> entry : param.entrySet()) {
			where.append(entry.getKey()).append(" LIKE ? AND ");
			values.add(entry.getValue());
		}

		String tableName = table.getTableName();
		if (table.getIsExternal() != 1) {
			tableName = TableModel.CUSTOMER_TABLE_PREFIX + tableName;
		}

		// sql
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.* FROM ");
		sql.append(tableName + " a");
		if (where.length() > 0) {
			sql.append(" WHERE ");
			sql.append(where.substring(0, where.length() - 5));
		}

		RowMapper<Map<String, Object>> rowMapper = new RowMapper<Map<String, Object>>() {

			@Override
			public Map<String, Object> mapRow(ResultSet rs, int num) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				ResultSetMetaData rsm = rs.getMetaData();
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					String name = rsm.getColumnName(i);
					Object value = rs.getObject(name);
					map.put(name, value);
				}
				return map;
			}
		};

		// 执行
		return jdbcTemplate.query(sql.toString(), values.toArray(), rowMapper);

	}

}