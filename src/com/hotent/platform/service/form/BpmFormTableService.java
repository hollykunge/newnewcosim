package com.hotent.platform.service.form;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.hotent.core.customertable.ColumnModel;
import com.hotent.core.customertable.ITableOperator;
import com.hotent.core.customertable.SqlTypeConst;
import com.hotent.core.customertable.TableModel;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.DateFormatUtil;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.XmlBeanUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.dao.form.BpmFormDefDao;
import com.hotent.platform.dao.form.BpmFormFieldDao;
import com.hotent.platform.dao.form.BpmFormHandlerDao;
import com.hotent.platform.dao.form.BpmFormTableDao;
import com.hotent.platform.dao.system.IdentityDao;
import com.hotent.platform.dao.system.SysDataSourceDao;
import com.hotent.platform.model.form.BpmFormField;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.form.TableRelation;
import com.hotent.platform.model.system.Identity;
import com.hotent.platform.model.system.SysDataSource;
import com.hotent.platform.xml.table.BpmFormTableXml;
import com.hotent.platform.xml.table.BpmFormTableXmlList;
import com.hotent.platform.xml.util.MsgUtil;

/**
 * 对象功能:自定义表 Service类 开发公司: 开发人员:xwy 创建时间:2011-11-30 14:29:22
 */
@Service
public class BpmFormTableService extends BaseService<BpmFormTable> {
	@Resource
	private BpmFormTableDao dao;

	@Resource
	private BpmFormFieldDao bmpFormFieldDao;

	@Resource
	private ITableOperator tableOperator;

	@Resource
	private SysDataSourceDao sysDataSourceDao;

	@Resource
	private BpmFormDefDao bpmFormDefDao;
	@Resource
	private BpmFormHandlerDao bpmFormHandlerDao;

	@Resource
	private IdentityDao identityDao;

	public BpmFormTableService() {
	}

	@Override
	protected IEntityDao<BpmFormTable, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 添加外部表。
	 * 
	 * @param table
	 * @param fields
	 * @throws Exception
	 */
	public void addExt(BpmFormTable table, List<BpmFormField> fields)
			throws Exception {
		long tableId = UniqueIdUtil.genId();
		// 添加表
		table.setTableId(tableId);
		table.setIsPublished((short) 1);
		table.setIsExternal(1);
		// 设置注释
		if (StringUtil.isEmpty(table.getTableDesc())) {
			table.setTableDesc(table.getTableName());
		}
		// 获取数据源名称。
		String dsAlias = table.getDsAlias();
		SysDataSource sysDataSource = sysDataSourceDao.getByAlias(dsAlias);
		table.setDsName(sysDataSource.getName());
		dao.add(table);
		// 添加字段。
		addFields(tableId, fields, true);
	}

	/**
	 * 添加表数据定义。
	 * 
	 * @param table
	 * @param fields
	 * @return -1 表示用户表字段已经存在。
	 * @throws Exception
	 */
	public int addFormTable(BpmFormTable table) throws Exception {
		List<BpmFormField> fields = table.getFieldList();
		// boolean isFieldExist=hasReserveFileds(fields);
		//
		// if(isFieldExist){
		// return -1;
		// }

		long tableId = UniqueIdUtil.genId();
		// 添加表
		table.setTableId(tableId);

		table.setIsExternal(0);
		// 设置注释
		if (StringUtil.isEmpty(table.getTableDesc())) {
			table.setTableDesc(table.getTableName());
		}
		dao.add(table);
		// 添加字段。
		addFields(tableId, fields, false);

		return 0;

	}

	/**
	 * 计算字段，这种情况用于在表字段有变化时获取变化的字段，包括添加的字段和删除的字段。
	 * 
	 * <pre>
	 * 需要注意的是：
	 * 	这种情况在表创建之后才会用到。
	 *  更新时：
	 *  数据库中已有的字段，字段名，字段类型不能修改。
	 *  可以添加字段。
	 *  返回值类型：Map&lt;String, List&lt;BpmFormField>>
	 *  
	 *  获取更新的字段：
	 *  Mapp&lt;String, Listp&lt;BpmFormField>> map= caculateFields(fields,orginFieldList);
	 *  
	 *  新添加的列：
	 *  Listp&lt;BpmFormField> addFields=map.get("add");
	 *  更新的列:
	 *  Listp&lt;BpmFormField> updFields=map.get("upd");
	 *  
	 *  更新列时将原来的tableid和fieldid放到新列中。
	 * 
	 * </pre>
	 * 
	 * @param fields
	 * @param orginFieldList
	 * @return
	 */
	private Map<String, List<BpmFormField>> caculateFields(
			List<BpmFormField> fields, List<BpmFormField> orginFieldList) {
		Map<String, BpmFormField> orginMap = new HashMap<String, BpmFormField>();
		Map<String, BpmFormField> curMap = new HashMap<String, BpmFormField>();

		Map<String, List<BpmFormField>> resultMap = new HashMap<String, List<BpmFormField>>();

		for (BpmFormField field : orginFieldList) {
			String fieldName = field.getFieldName().toLowerCase();
			orginMap.put(fieldName, field);
		}

		int i = 1;
		for (BpmFormField field : fields) {
			String fieldName = field.getFieldName().toLowerCase();

			curMap.put(fieldName, field);
			field.setSn(i);
			i++;
		}

		for (BpmFormField field : fields) {
			String fieldName = field.getFieldName().toLowerCase();
			if (orginMap.containsKey(fieldName)) {
				BpmFormField orginField = orginMap.get(fieldName);
				field.setFieldId(orginField.getFieldId());
				field.setTableId(orginField.getTableId());
				addField("upd", resultMap, field);
			} else {
				addField("add", resultMap, field);
			}
		}
		// 如果现有的字段中不包含上次的字段则将字段做删除标记。
		for (BpmFormField field : orginFieldList) {
			String fieldName = field.getFieldName().toLowerCase();
			if (!curMap.containsKey(fieldName)) {
				field.setIsDeleted((short) 1);
				addField("upd", resultMap, field);
			}
		}
		return resultMap;
	}

	private void addField(String key,
			Map<String, List<BpmFormField>> resultMap, BpmFormField field) {
		List<BpmFormField> list;
		if (resultMap.containsKey(key)) {
			list = resultMap.get(key);
		} else {
			list = new ArrayList<BpmFormField>();
			resultMap.put(key, list);
		}
		list.add(field);
	}

	/**
	 * 判断添加字段中是否有必填的字段。
	 * 
	 * @param list
	 * @return
	 */
	private boolean hasNotNullField(List<BpmFormField> list) {
		for (BpmFormField field : list) {
			if (field.getIsRequired() == 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 更新表的设计。
	 * 
	 * @param table
	 * @param fields
	 * @return -1 表示当前字段中存在字段 curentUserId。-2 有数据字段不能设置为非空。
	 * @throws Exception
	 */
	public int upd(BpmFormTable table) throws Exception {
		List<BpmFormField> fields = table.getFieldList();

		Long tableId = table.getTableId();
		String tableName = table.getTableName();
		// 获取表定义。
		BpmFormTable originTable = dao.getById(tableId);

		Long mainTableId = tableId;
		int isMain = table.getIsMain();
		if (isMain == 0) {
			mainTableId = table.getMainTableId();
		}
		// 修改之前的字段列表
		List<BpmFormField> originFieldList = bmpFormFieldDao
				.getAllByTableId(tableId);

		// 设置注释
		if (StringUtil.isEmpty(table.getTableDesc())) {
			table.setTableDesc(tableName);
		}
		// 该表已经有表单定义了。
		boolean hasFormDef = false;
		if (mainTableId > 0) {
			hasFormDef = bpmFormDefDao.isTableHasFormDef(mainTableId);
		}
		// 已经有表单定义，
		// 1.不能删除字段
		// 2.可以更新字段
		// 3.可以添加字段。
		if (hasFormDef) {
			// 判断表中是否有数据
			boolean hasData = bpmFormHandlerDao
					.getHasData(TableModel.CUSTOMER_TABLE_PREFIX + tableName);

			Map<String, List<BpmFormField>> resultMap = caculateFields(fields,
					originFieldList);

			// 处理新增的字段
			List<BpmFormField> addList = resultMap.get("add");
			if (BeanUtils.isNotEmpty(addList)) {
				addList = convertFields(addList, false);
				if (hasData) {
					// 判断添加的字段中是否有非空的字段。
					boolean rtn = hasNotNullField(addList);
					if (rtn) {
						return -2;
					}
				}
			}
			// 更新表
			dao.update(table);
			// 需要更新的字段
			List<BpmFormField> updList = resultMap.get("upd");
			for (BpmFormField field : updList) {
				if(field.getControlType()==4 || field.getControlType()==6){
					BpmFormField colFieldId = bmpFormFieldDao.getFieldByTidFna(tableId, field.getFieldName()+"ID");
					colFieldId.setControlType(field.getControlType());
					colFieldId.setCtlProperty(field.getCtlProperty());
					bmpFormFieldDao.update(colFieldId);
				}
				bmpFormFieldDao.update(field);
			}

			if (BeanUtils.isEmpty(addList))
				return 0;
			int i = updList.size();
			int k = 0;
			// 添加字段
			for (BpmFormField field : addList) {
				k++;
				field.setFieldId(UniqueIdUtil.genId());
				field.setTableId(tableId);
				field.setSn(k + i);
				bmpFormFieldDao.add(field);
				ColumnModel columnModel = getByField(field, 2);
				tableOperator.addColumn(TableModel.CUSTOMER_TABLE_PREFIX
						+ tableName, columnModel);
			}
		}
		// 没有表单定义的情况。
		else {
			if (table.getIsPublished() == 1) {
				// 主表的情况
				if (table.getIsMain() == 1) {
					// 获取所有的主表
					List<BpmFormTable> tableList = dao
							.getSubTableByMainTableId(tableId);
					// 删除子表的外键
					for (BpmFormTable subTable : tableList) {
						String tabName = TableModel.CUSTOMER_TABLE_PREFIX
								+ subTable.getTableName();
						tableOperator.dropForeignKey(tabName, "fk_" + tabName);
					}
					dao.update(table);
					// 删除列的定义
					bmpFormFieldDao.delByTableId(tableId);
					// 添加表列的定义
					addFields(tableId, fields, false);

					// 删除原来的表
					tableOperator.dropTable(TableModel.CUSTOMER_TABLE_PREFIX
							+ originTable.getTableName());

					// 重新创建物理表
					List<BpmFormField> fieldList = convertFields(fields, false);
					table.setFieldList(fieldList);
					createTable(table);

					String pkTableName = TableModel.CUSTOMER_TABLE_PREFIX
							+ table.getTableName();
					// 重新添加子表外键
					for (BpmFormTable subTable : tableList) {
						String tabName = TableModel.CUSTOMER_TABLE_PREFIX
								+ subTable.getTableName();
						tableOperator.addForeignKey(pkTableName, tabName,
								TableModel.PK_COLUMN_NAME,
								TableModel.FK_COLUMN_NAME);
					}
				} else {
					// 更新表定义，添加表列的定义。
					// 删除物理表重新创建表。
					dao.update(table);
					// 删除列的定义
					bmpFormFieldDao.delByTableId(tableId);
					// 添加表列的定义
					addFields(tableId, fields, false);
					// 删除原来的表
					tableOperator.dropTable(TableModel.CUSTOMER_TABLE_PREFIX
							+ originTable.getTableName());
					// 重新创建物理表
					List<BpmFormField> fieldList = convertFields(fields, false);

					table.setFieldList(fieldList);
					createTable(table);
				}
			}
			// 未发布
			else {
				// 直接该表的定义。
				dao.update(table);
				// 删除字段，重新加入字段
				bmpFormFieldDao.delByTableId(tableId);
				// 添加字段
				addFields(tableId, fields, false);
			}
		}
		return 0;
	}

	/**
	 * 添加自定义表字段。
	 * 
	 * @param tableId
	 *            表名。
	 * @param fields
	 *            字段数组
	 * @param isExternal
	 *            是否外部表。
	 * @throws Exception
	 */
	private void addFields(Long tableId, List<BpmFormField> fields,
			boolean isExternal) throws Exception {
		List<BpmFormField> fieldList = convertFields(fields, isExternal);
		for (int i = 0; i < fieldList.size(); i++) {
			BpmFormField field = fieldList.get(i);
			Long fieldId = UniqueIdUtil.genId();
			field.setFieldId(fieldId);
			field.setSn(i);
			field.setTableId(tableId);
			bmpFormFieldDao.add(field);
		}
	}

	/**
	 * 转换字段。
	 * 
	 * <pre>
	 * 增加隐藏字段，并设置字段的主键，数据类型，名称，注释信息。
	 * </pre>
	 * 
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	private List<BpmFormField> convertFields(List<BpmFormField> fields,
			boolean isExternal) throws Exception {
		List<BpmFormField> list = new ArrayList<BpmFormField>();
		int i = 1;
		for (BpmFormField field : fields) {
			field.setSn(i);
			i++;
			if (StringUtil.isEmpty(field.getFieldDesc())) {
				field.setFieldDesc(field.getFieldName());
			}
			list.add(field);
			if (isExternal)
				continue;

			if (field.getControlType() == null) {
				field.setControlType((short) 1);
				continue;
			}

			if (field.getControlType().equals(BpmFormField.Selector_User)
					|| field.getControlType().equals(
							BpmFormField.Selector_UserMulti)
					|| field.getControlType().equals(BpmFormField.Selector_Org)
					|| field.getControlType().equals(
							BpmFormField.Selector_Position)
					|| field.getControlType()
							.equals(BpmFormField.Selector_Role)) {
				BpmFormField fieldHidden = (BpmFormField) field.clone();
				fieldHidden.setFieldId(UniqueIdUtil.genId());

				fieldHidden.setFieldName(fieldHidden.getFieldName()
						+ BpmFormField.FieldHidden);
				fieldHidden.setFieldDesc(field.getFieldDesc() + "ID");
				fieldHidden.setIsHidden((short) 1);

				list.add(fieldHidden);
			}
		}
		return list;
	}

	/**
	 * 根据表名判断是否该表在系统中已经定义。
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean isTableNameExisted(String tableName) {
		return dao.isTableNameExisted(tableName);
	}

	/**
	 * 判断表是否已存在，在更新时使用。
	 * 
	 * @param tableId
	 * @param tableName
	 * @return
	 */
	public boolean isTableNameExistedForUpd(Long tableId, String tableName) {
		return dao.isTableNameExistedForUpd(tableId, tableName);
	}

	/**
	 * 判断表名是否存在。
	 * 
	 * @param tableName
	 * @param dsAlias
	 * @return
	 */
	public boolean isTableNameExternalExisted(String tableName, String dsAlias) {
		return dao.isTableNameExternalExisted(tableName, dsAlias);
	}

	/**
	 * 通过mainTableId获得所有字表
	 * 
	 * @param mainTableId
	 * @return
	 */
	public List<BpmFormTable> getSubTableByMainTableId(Long mainTableId) {
		return dao.getSubTableByMainTableId(mainTableId);
	}

	/**
	 * 获得所有未分配的子表
	 * 
	 * @param tableName
	 * @return
	 */
	public List<BpmFormTable> getAllUnassignedSubTable() {
		return dao.getAllUnassignedSubTable();
	}

	/**
	 * 根据数据源名称取得子表。
	 * 
	 * @param dsName
	 * @return
	 */
	public List<BpmFormTable> getByDsSubTable(String dsName) {
		return dao.getByDsSubTable(dsName);
	}

	/**
	 * 将表定义进行发布。
	 * 
	 * @param tableId
	 *            表定义ID
	 * @param operator
	 *            发布人
	 * @throws Exception
	 */
	public void generateTable(Long tableId, String operator) throws Exception {
		BpmFormTable mainTable = dao.getById(tableId);
		// 将主表设为已发布
		publish(tableId, operator);

		// 将主表设为默认版本
		List<BpmFormField> mainFields = bmpFormFieldDao
				.getAllByTableId(tableId);
		// 添加
		mainFields = convertFields(mainFields, false);
		mainTable.setFieldList(mainFields);

		createTable(mainTable);

		List<BpmFormTable> subTables = dao.getSubTableByMainTableId(tableId);
		for (BpmFormTable subTable : subTables) {
			// 将子表设为已发布
			publish(subTable.getTableId(), operator);

			List<BpmFormField> subFields = bmpFormFieldDao
					.getAllByTableId(subTable.getTableId());
			subFields = convertFields(subFields, false);
			subTable.setFieldList(subFields);
			// 改表结构
			createTable(subTable);
		}
	}

	/**
	 * 设置表为发布状态。
	 * 
	 * @param tableId
	 * @param operator
	 */
	private void publish(Long tableId, String operator) {
		BpmFormTable table = new BpmFormTable();
		table.setTableId(tableId);
		table.setPublishedBy(operator);
		table.setIsPublished((short) 1);
		table.setPublishTime(new Date());
		dao.updPublished(table);
	}

	/**
	 * 关联子表。
	 * 
	 * @param mainTableId
	 * @param subTableId
	 * @throws Exception
	 */
	public void linkSubtable(Long mainTableId, Long subTableId)
			throws Exception {
		// 获取主表
		BpmFormTable mainTable = dao.getById(mainTableId);
		BpmFormTable subTable = dao.getById(subTableId);
		// 主表已经发布，则生成子表。
		if (mainTable.getIsPublished() == 1) {
			subTable.setMainTableId(mainTableId);
			dao.update(subTable);
			// 如果表已生成则先删除表。
			if (subTable.getIsPublished() == 1) {
				tableOperator.dropTable(TableModel.CUSTOMER_TABLE_PREFIX
						+ subTable.getTableName());
			}
			// 发布子表
			publish(subTableId, mainTable.getPublishedBy());
			List<BpmFormField> subFields = bmpFormFieldDao
					.getByTableId(subTableId);
			subFields = convertFields(subFields, false);
			subTable.setFieldList(subFields);
			// 生成实体表
			createTable(subTable);
		}
		// 未发布修改字段。
		else {
			subTable.setMainTableId(mainTableId);
			dao.update(subTable);
		}
	}

	/**
	 * 取消关联
	 * 
	 * @param subTableId
	 */
	public void unlinkSubTable(Long subTableId) {
		BpmFormTable table = dao.getById(subTableId);
		table.setMainTableId(null);
		dao.update(table);
	}

	/**
	 * 获取可以关联的主表。 1.没有关联表单。 2.可以是发布或未发布的。
	 * 
	 * @return
	 */
	public List<BpmFormTable> getAssignableMainTable() {

		List<BpmFormTable> list = dao.getAssignableMainTable();

		return list;
	}

	/**
	 * 获取未发布的主表。
	 * 
	 * @return
	 */
	public List<BpmFormTable> getAllUnpublishedMainTable() {
		List<BpmFormTable> list = dao.getAllUnpublishedMainTable();
		return list;
	}

	/**
	 * 查找默认主表列表
	 * 
	 * @param queryFilter
	 * @return
	 */
	public List<BpmFormTable> getAllMainTable(QueryFilter queryFilter) {
		return dao.getAllMainTable(queryFilter);
	}
	public List<BpmFormTable> getMainTables(String tableName){
		return dao.getMainTables(tableName);
	}
	public BpmFormTable getByTableName(String tableName){
		return dao.getByTableName(tableName);
	}
	
	/**
	 * 根据表字段获取列定义对象。 columnType: 1.主键 2.一般列 3.外键 4.用户字段 5.流程RUN字段 6.流程定义ID
	 * 7.获取默认时间字段 8.组织字段
	 * 
	 * @param field
	 * @return
	 */
	private ColumnModel getByField(BpmFormField field, int columnType) {
		ColumnModel columnModel = new ColumnModel();
		switch (columnType) {
		// 主键
		case 1:
			columnModel.setName(TableModel.PK_COLUMN_NAME);
			columnModel.setColumnType(ColumnModel.COLUMNTYPE_INT);
			columnModel.setIntLen(20);
			columnModel.setComment("主键");
			columnModel.setIsPk(true);
			columnModel.setIsNull(false);
			break;
		// 一般列
		case 2:
			columnModel.setName(TableModel.CUSTOMER_COLUMN_PREFIX
					+ field.getFieldName());
			columnModel.setColumnType(field.getFieldType());

			columnModel.setCharLen(field.getCharLen().intValue());
			columnModel.setIntLen(field.getIntLen().intValue());
			columnModel.setDecimalLen(field.getDecimalLen().intValue());
			columnModel.setIsNull(field.getIsRequired() == 0);
			columnModel.setComment(field.getFieldDesc());
			break;
		// 外键
		case 3:
			columnModel.setName(TableModel.FK_COLUMN_NAME);
			columnModel.setColumnType(ColumnModel.COLUMNTYPE_INT);
			columnModel.setIntLen(20);
			columnModel.setComment("外键");
			columnModel.setIsFk(true);
			columnModel.setIsNull(false);
			break;
		// 用户字段
		case 4:
			columnModel.setName(TableModel.CUSTOMER_COLUMN_CURRENTUSERID);
			columnModel.setColumnType(ColumnModel.COLUMNTYPE_INT);
			columnModel.setIntLen(20);
			columnModel.setComment("用户字段");
			columnModel.setIsFk(false);
			columnModel.setIsNull(true);
			break;
		// 流程RUN字段。
		case 5:
			columnModel.setName(TableModel.CUSTOMER_COLUMN_FLOWRUNID);
			columnModel.setColumnType(ColumnModel.COLUMNTYPE_INT);
			columnModel.setIntLen(20);
			columnModel.setComment("流程RUN字段");
			columnModel.setIsFk(false);
			columnModel.setIsNull(true);
			break;
		// 流程定义ID。
		case 6:
			columnModel.setName(TableModel.CUSTOMER_COLUMN_DEFID);
			columnModel.setColumnType(ColumnModel.COLUMNTYPE_INT);
			columnModel.setIntLen(20);
			columnModel.setComment("流程定义ID");
			columnModel.setIsFk(false);
			columnModel.setIsNull(true);
			break;
		// 获取默认时间字段
		case 7:
			String defaultValue = getDefaultCurrentTime();
			columnModel.setName(TableModel.CUSTOMER_COLUMN_CREATETIME);
			columnModel.setColumnType(ColumnModel.COLUMNTYPE_DATE);
			// columnModel.setIntLen(20);
			columnModel.setComment("记录插入时间");
			columnModel.setIsFk(false);
			columnModel.setIsNull(true);
			columnModel.setDefaultValue(defaultValue);
			break;
		// 组织字段
		case 8:
			columnModel.setName(TableModel.CUSTOMER_COLUMN_CURRENTORGID);
			columnModel.setColumnType(ColumnModel.COLUMNTYPE_INT);
			columnModel.setIntLen(20);
			columnModel.setComment("组织字段");
			columnModel.setIsFk(false);
			columnModel.setIsNull(true);
		}

		return columnModel;
	}

	/**
	 * 根据表和表字段创建实体表。
	 * 
	 * @param table
	 *            表对象
	 * @param fields
	 *            表字段
	 * @throws SQLException
	 */
	private void createTable(BpmFormTable table) throws SQLException {
		TableModel tableModel = getTableModelByBpmFormTable(table);
		tableOperator.createTable(tableModel);
		// 当前表为子表，添加表关联。
		if (table.getIsMain() == 0) {
			Long mainTableId = table.getMainTableId();
			BpmFormTable bpmFormTable = dao.getById(mainTableId);
			// 添加外键
			tableOperator.addForeignKey(TableModel.CUSTOMER_TABLE_PREFIX
					+ bpmFormTable.getTableName(),
					TableModel.CUSTOMER_TABLE_PREFIX + table.getTableName(),
					TableModel.PK_COLUMN_NAME, TableModel.FK_COLUMN_NAME);
		}
	}

	/**
	 * 根据BpmFormTable获取TableModel对象。
	 * 
	 * @param table
	 */
	private TableModel getTableModelByBpmFormTable(BpmFormTable table) {
		TableModel tableModel = new TableModel();
		tableModel.setName(TableModel.CUSTOMER_TABLE_PREFIX
				+ table.getTableName());
		tableModel.setComment(table.getTableDesc());

		// 给表添加主键
		ColumnModel pkModel = getByField(null, 1);
		tableModel.addColumnModel(pkModel);

		// 用户字段。
		ColumnModel userColumnModel = getByField(null, 4);
		tableModel.addColumnModel(userColumnModel);
		// 组织字段。
		ColumnModel orgColumnModel = getByField(null, 8);
		tableModel.addColumnModel(orgColumnModel);

		if (table.getIsMain() == 1) {
			// 添加一个运行列
			ColumnModel runColumnModel = getByField(null, 5);
			tableModel.addColumnModel(runColumnModel);
			// 流程定义ID
			ColumnModel defColumnModel = getByField(null, 6);
			tableModel.addColumnModel(defColumnModel);
			// 默认时间。
			ColumnModel createTimeColumnModel = getByField(null, 7);
			tableModel.addColumnModel(createTimeColumnModel);
		}

		// 添加自定义表的字段。
		for (BpmFormField field : table.getFieldList()) {
			field.setFieldName(StringUtil.trim(field.getFieldName(), " "));
			ColumnModel columnModel = getByField(field, 2);
			tableModel.addColumnModel(columnModel);
		}
		// 如果表定义为子表的情况。
		// 添加一个外键的列定义。
		if (table.getIsMain() == 0) {
			ColumnModel fkModel = getByField(null, 3);
			tableModel.addColumnModel(fkModel);
		}
		return tableModel;

	}

	/**
	 * 根据数据库获取时间默认值函数用于创建数据库表。
	 * 
	 * @return
	 */
	private String getDefaultCurrentTime() {
		String dbType = tableOperator.getDbType();
		if (SqlTypeConst.ORACLE.equals(dbType)) {
			return "SYSDATE";
		} else if (SqlTypeConst.MYSQL.equals(dbType)) {
			return "CURRENT_TIMESTAMP";
		} else if (SqlTypeConst.SQLSERVER.equals(dbType)) {
			return "getdate()";
		} else if (SqlTypeConst.DB2.equals(dbType)) {
			return "CURRENT DATE";
		}
		return "";
	}

	/**
	 * 保存关联关系。
	 * 
	 * @param dataSource
	 *            数据源
	 * @param tableName
	 *            表名
	 * @param relations
	 *            关联关系格式(表名:主键id,外键Id#表名:主键id,外键Id)
	 * 
	 */
	public void saveRelation(String dataSource, String tableName,
			String relationXml) {
		BpmFormTable bpmFormTable = new BpmFormTable();
		bpmFormTable.setDsAlias(dataSource);
		bpmFormTable.setTableName(tableName);
		bpmFormTable.setRelation(relationXml);
		dao.updateRelations(bpmFormTable);

		BpmFormTable mainTable = dao.getByDsTablename(dataSource, tableName);

		// 清空子表的主表字段。
		dao.updateMainEmpty(mainTable.getTableId());

		// 取得外键表
		TableRelation bableRelation = BpmFormTable
				.getRelationsByXml(relationXml);
		if (bableRelation == null)
			return;
		Map<String, String> mapRelation = bableRelation.getRelations();
		Set<Map.Entry<String, String>> setRelation = mapRelation.entrySet();

		// 更新子表tableid
		for (Iterator<Map.Entry<String, String>> tableIt = setRelation
				.iterator(); tableIt.hasNext();) {
			Map.Entry<String, String> obj = tableIt.next();
			String key = obj.getKey();
			BpmFormTable entity = new BpmFormTable();
			entity.setDsAlias(dataSource);
			entity.setMainTableId(mainTable.getTableId());
			entity.setTableName(key);
			dao.updateMain(entity);
		}
	}

	/**
	 * 根据数据源和表名获取表对象。
	 * 
	 * @param dsName
	 * @param tableName
	 * @return
	 */
	public BpmFormTable getByDsTablename(String dsName, String tableName) {
		return this.dao.getByDsTablename(dsName, tableName);
	}

	/**
	 * 根据表的Id删除外部表定义。
	 * 
	 * @param tableId
	 */
	public void delExtTableById(Long tableId) {
		BpmFormTable bpmFormTable = this.getById(tableId);
		int isExternal = bpmFormTable.getIsExternal();
		// 自定义表直接返回
		if (isExternal == 0)
			return;
		// 子表的处理情况
		if (bpmFormTable.getIsMain() == 0) {
			Long mainTableId = bpmFormTable.getMainTableId();
			if (BeanUtils.isNotEmpty(mainTableId) && mainTableId > 0) {
				BpmFormTable mainTable = this.getById(mainTableId);
				String relation = mainTable.getRelation();
				if (StringUtil.isNotEmpty(relation)) {
					// 修改关系的XML。
					relation = BpmFormTable.removeTable(relation,
							bpmFormTable.getTableName());
					mainTable.setRelation(relation);
					dao.update(mainTable);
				}
			}
		}
		this.dao.delById(tableId);
	}

	/**
	 * 根据表ID删除表定义。
	 * 
	 * <pre>
	 * 1.如果该表是主表，那么先删除其所有的关联的子表。
	 * 2.根据表的ID删除表定义。
	 * </pre>
	 * 
	 * @param tableId
	 */
	public void delByTableId(Long tableId) {
		BpmFormTable bpmFormTable = this.getById(tableId);
		String tableName = bpmFormTable.getTableName();
		if (bpmFormTable.getIsMain() == 1) {
			List<BpmFormTable> subTableList = getSubTableByMainTableId(tableId);
			if (BeanUtils.isNotEmpty(subTableList)) {
				for (BpmFormTable subTable : subTableList) {
					// 删除实体表
					tableOperator.dropTable(TableModel.CUSTOMER_TABLE_PREFIX
							+ subTable.getTableName());
					dao.delById(subTable.getTableId());
				}
			}
		}
		// 删除实体表。
		tableOperator.dropTable(TableModel.CUSTOMER_TABLE_PREFIX + tableName);
		dao.delById(tableId);
	}

	/**
	 * 导入自定义表XML
	 * 
	 * @param fileStr
	 * @throws Exception
	 */
	public void importXml(InputStream inputStream) throws Exception {
		Document doc = Dom4jUtil.loadXml(inputStream);
		Element root = doc.getRootElement();
		// 验证格式是否正确
		this.checkXMLFormat(root);

		String xmlStr = root.asXML();
		BpmFormTableXmlList bpmFormTableXmlList = (BpmFormTableXmlList) XmlBeanUtil
				.unmarshall(xmlStr, BpmFormTableXmlList.class);

		List<BpmFormTableXml> list = bpmFormTableXmlList
				.getBpmFormTableXmlList();
		for (BpmFormTableXml bpmFormTableXml : list) {
			// 导入表，并解析相关信息
			this.importBpmFormTable(bpmFormTableXml, new Long(0));
		}
	}

	public void importBpmFormTable(BpmFormTableXml bpmFormTableXml,
			Long mainTableId) throws Exception {
		String isMain = (mainTableId.longValue() == 0L ? "主" : "从");
		// 导入表
		BpmFormTable bpmFormTable = bpmFormTableXml.getBpmFormTable();
		Long tableId = bpmFormTable.getTableId();
		// 导入表
		Boolean flag = this.importFormTable(bpmFormTable, tableId, mainTableId,
				isMain);
		if (!flag)
			return;
		// 导入字段
		List<BpmFormField> bpmFormFieldList = bpmFormTableXml
				.getBpmFormFieldList();
		if (BeanUtils.isNotEmpty(bpmFormFieldList)) {
			for (BpmFormField bpmFormField : bpmFormFieldList) {
				this.importFormField(bpmFormField, tableId);
			}
		}
		// 导入流水号
		List<Identity> identityList = bpmFormTableXml.getIdentityList();
		if (BeanUtils.isNotEmpty(identityList)) {
			for (Identity identity : identityList) {
				this.importIdentity(identity);
			}
		}

		// 如果存在子表，递归导入子表
		List<BpmFormTableXml> tableXmlList = bpmFormTableXml
				.getBpmFormTableXmlList();
		if (BeanUtils.isNotEmpty(tableXmlList)) {
			for (BpmFormTableXml tableXml : tableXmlList) {
				if (BeanUtils.isNotEmpty(tableXml.getBpmFormTable())) {
					this.importBpmFormTable(tableXml, tableId);
				}
			}
		}
	}

	/**
	 * 
	 * @param root
	 * @throws Exception
	 */
	private void checkXMLFormat(Element root) throws Exception {
		String msg = "导入文件格式不对";
		if (!root.getName().equals("bpm"))
			throw new Exception(msg);
		@SuppressWarnings("unchecked")
		List<Element> itemLists = root.elements();
		for (Element elm : itemLists) {
			if (!elm.getName().equals("tables"))
				throw new Exception(msg);
		}

	}

	/**
	 * 导入自定义表时插入 BpmFormTable
	 * 
	 * @param t
	 * 
	 * @param xmlStr
	 * @param tableId
	 * @throws Exception
	 */
	private Boolean importFormTable(BpmFormTable bpmFormTable, Long tableId,
			Long mainTableId, String isMain) throws Exception {
		if (BeanUtils.isEmpty(bpmFormTable)
				|| BeanUtils.isEmpty(bpmFormTable.getTableName()))
			return true;
		BpmFormTable bft = dao.getByTableName(bpmFormTable.getTableName());
		if (BeanUtils.isEmpty(bft)) {
			bpmFormTable.setTableId(tableId);
			bpmFormTable
					.setMainTableId(bpmFormTable.getIsMain() == 1 ? new Long(0)
							: mainTableId);
			bpmFormTable.setPublishedBy(ContextUtil.getCurrentUser()
					.getFullname());
			bpmFormTable.setPublishTime(new Date());
			bpmFormTable.setIsPublished(new Short("0"));
			bpmFormTable.setIsExternal(0);
			bpmFormTable.setKeyType(new Short("0"));
			dao.add(bpmFormTable);
			MsgUtil.addMsg(MsgUtil.SUCCESS,
					isMain + "表名为‘" + bpmFormTable.getTableName() + "’，该表成功导入！");
			return true;
		} else {
			MsgUtil.addMsg(MsgUtil.WARN,
					isMain + "表名为‘" + bpmFormTable.getTableName() + "’的已经存在，该表终止导入！");
			return false;
		}

	}

	/**
	 * 导入自定义表时插入 BpmFormField
	 * 
	 * @param xmlStr
	 * @param tableId
	 * @throws Exception
	 */
	private void importFormField(BpmFormField field, Long tableId)
			throws Exception {
		field.setFieldId(UniqueIdUtil.genId());
		field.setTableId(tableId);
		// 脚本
		field.setScript(convertLine(field.getScript(), false));
		// 下拉框
		field.setOptions(convertLine(field.getOptions(), false));
		bmpFormFieldDao.add(field);
	}

	/**
	 * 导入流水号时插入Identity
	 * 
	 * @param identity
	 * @return
	 * @throws Exception
	 */
	private void importIdentity(Identity identity) throws Exception {
		Identity isExist = identityDao.getByAlias(identity.getAlias());
		if (isExist == null) {
			identity.setId(UniqueIdUtil.genId());
			identity.setCurDate(DateFormatUtil.format(new Date(), "yyyy-MM-dd"));
			identityDao.add(identity);
		}
	}

	/**
	 * 导出自定义表XML
	 * 
	 * @param Long
	 *            [] tableIds 导出的tableId
	 * @param map
	 * @return
	 */
	public String exportXml(Long[] tableIds, Map<String, Boolean> map)
			throws Exception {
		BpmFormTableXmlList bpmFormTableXmls = new BpmFormTableXmlList();
		List<BpmFormTableXml> list = new ArrayList<BpmFormTableXml>();
		for (int i = 0; i < tableIds.length; i++) {
			BpmFormTable formTable = dao.getById(tableIds[i]);
			BpmFormTableXml bpmFormTableXml = this.exportTable(formTable, map);
			list.add(bpmFormTableXml);
		}
		bpmFormTableXmls.setBpmFormTableXmlList(list);
		return XmlBeanUtil
				.marshall(bpmFormTableXmls, BpmFormTableXmlList.class);
	}
	
	/**
	 * 获取默认的导出的Map
	 * @param map
	 * @return
	 */
	public Map<String, Boolean>  getDefaultExportMap(Map<String, Boolean> map){
		if(BeanUtils.isEmpty(map)){
			map = new HashMap<String, Boolean>();
			map.put("bpmFormTable", true);
			map.put("bpmFormField", true);
			map.put("subTable",true);
			map.put("identity", true);
		}
		return map;
	}
	
	
	/**
	 * 导出表的信息
	 * 
	 * @param formTable
	 * @param map
	 * @return
	 */
	public BpmFormTableXml exportTable(BpmFormTable formTable,
			Map<String, Boolean> map) {
		map = this.getDefaultExportMap(map);
		
		BpmFormTableXml bpmFormTableXml = new BpmFormTableXml();
		// 字段列表
		List<BpmFormField> bpmFormFieldList = new ArrayList<BpmFormField>();
		// 流水号列表
		List<Identity> identityList = new ArrayList<Identity>();
		// 子表
		List<BpmFormTableXml> bpmFormTableXmlList = new ArrayList<BpmFormTableXml>();
		Long tableId = formTable.getTableId();
		if (BeanUtils.isNotEmpty(tableId)) {
			List<BpmFormField> formFieldList = new ArrayList<BpmFormField>();
			//字段
			 if (BeanUtils.isNotEmpty(map)
						&& BeanUtils.isNotEmpty(map.get("bpmFormField"))) {
				formFieldList = bmpFormFieldDao.getByTableId(tableId);
				exportBpmFormTable(formFieldList, bpmFormFieldList);
			}
			//流水号
			 if (BeanUtils.isNotEmpty(map)
						&& BeanUtils.isNotEmpty(map.get("identity"))) 
				 exportIdentity(formFieldList, identityList);
			// 有子表，递归
			 if (BeanUtils.isNotEmpty(map)
						&& BeanUtils.isNotEmpty(map.get("subTable")))
				 exportSubTable(tableId,map, bpmFormTableXmlList);
		}

		bpmFormTableXml.setBpmFormTable(formTable);
		if (BeanUtils.isNotEmpty(bpmFormFieldList))
			bpmFormTableXml.setBpmFormFieldList(bpmFormFieldList);
		if (BeanUtils.isNotEmpty(identityList))
			bpmFormTableXml.setIdentityList(identityList);
		if (BeanUtils.isNotEmpty(bpmFormTableXmlList))
			bpmFormTableXml.setBpmFormTableXmlList(bpmFormTableXmlList);
		return bpmFormTableXml;
	}

	/**
	 * 导出字段
	 * @param formFieldList
	 * @param bpmFormFieldList
	 */
	private void exportBpmFormTable(List<BpmFormField> formFieldList,
			List<BpmFormField> bpmFormFieldList) {
		for (BpmFormField bpmFormField : formFieldList) {
			// 脚本
			bpmFormField.setScript(convertLine(bpmFormField.getScript(), true));
			// 下拉框
			bpmFormField
					.setOptions(convertLine(bpmFormField.getOptions(), true));
			bpmFormFieldList.add(bpmFormField);
		}
	}
	
	/**
	 * 导出流水号
	 * @param formFieldList
	 * @param identityList
	 */
	private void exportIdentity(List<BpmFormField> formFieldList,
			List<Identity> identityList) {
		for (BpmFormField bpmFormField : formFieldList) {
			// 流水号
			if (StringUtil.isNotEmpty(bpmFormField.getIdentity())) {
				Identity identity = identityDao.getByAlias(bpmFormField
						.getIdentity());
				identityList.add(identity);
			}
		}
		
	}
	
	/**
	 * 导出子表
	 * @param tableId
	 * @param map
	 * @param bpmFormTableXmlList
	 */
	private void exportSubTable(Long tableId, Map<String, Boolean> map, List<BpmFormTableXml> bpmFormTableXmlList) {
		List<BpmFormTable> subTables = dao.getSubTableByMainTableId(tableId);
		if (BeanUtils.isNotEmpty(subTables)) {
			for (BpmFormTable bpmFormTable : subTables) {
				bpmFormTableXmlList
						.add(this.exportTable(bpmFormTable, map));
			}
		}	
	}


	/**
	 * 转换换行符
	 * 
	 * @param arg
	 *            要转换的字符串
	 * @param flag
	 *            标志是正向转换（true），还是逆向转换（false）
	 * @return
	 */
	private static String convertLine(String arg, Boolean flag) {
		if (StringUtil.isEmpty(arg))
			return arg;
		String origStr = "\n", targStr = "/n";
		if (!flag) {
			origStr = "/n";
			targStr = "\n";
		}
		String[] args = arg.split(origStr);
		String temp = "";
		if (args.length > 1) {
			for (String str : args) {
				temp += str + targStr;
			}
			temp = temp.substring(0, temp.length() - 2);
		} else {
			temp = args[0];
		}
		return temp;
	}

	/**
	 * 根据表id获取表的表和表字段的信息。
	 * 
	 * <pre>
	 * 	如果输入的是主表id。
	 *  那么将返回主表的信息和字段信息，同时返回子表和字段的信息。
	 *  字段数据不包含删除的字段和隐藏的字段。
	 * </pre>
	 * 
	 * @param tableId
	 * @return
	 */
	public BpmFormTable getTableById(Long tableId) {
		BpmFormTable bpmFormTable = dao.getById(tableId);
		if (bpmFormTable == null)
			return null;
		List<BpmFormField> fieldList = bmpFormFieldDao.getByTableId(tableId);
		bpmFormTable.setFieldList(fieldList);
		if (bpmFormTable.getIsMain() == 0)
			return bpmFormTable;

		List<BpmFormTable> subTableList = dao.getSubTableByMainTableId(tableId);
		if (BeanUtils.isEmpty(subTableList))
			return bpmFormTable;

		for (BpmFormTable table : subTableList) {
			List<BpmFormField> subFieldList = bmpFormFieldDao
					.getByTableId(table.getTableId());
			table.setFieldList(subFieldList);
		}
		bpmFormTable.setSubTableList(subTableList);

		return bpmFormTable;
	}

	/**
	 * 根据表的ID取得表的列名是否可以编辑。
	 * 
	 * @param tableId
	 * @return
	 */
	public boolean getCanEditColumnNameTypeByTableId(Long tableId) {
		if (tableId == 0)
			return true;
		BpmFormTable bpmFormTable = dao.getById(tableId);
		if (bpmFormTable == null)
			return true;
		boolean hasData = bpmFormHandlerDao
				.getHasData(TableModel.CUSTOMER_TABLE_PREFIX
						+ bpmFormTable.getTableName());
		if (hasData) {
			return false;
		}
		int formAmount = bpmFormDefDao.getFormDefAmount(tableId);
		if (formAmount > 1) {
			return false;
		}
		return true;
	}

	/**
	 * 保存数据库表。
	 * 
	 * <pre>
	 *  bpmFormTable 需要指定表的主键id，如果没有指定，那么当作新表处理。
	 * 	1.BpmFormTable 表的表id为0。
	 * 		1.isPublish为true，生成表。
	 * 		2.isPublish为false，不生成表。
	 *  2.已经保存了表单数据。
	 *  	1.已经发布。
	 *  		1.是否生成数据。
	 *  			更新表。
	 *  		2.表单数量大于1个的情况。
	 *  			更新表。
	 *  		  否则:
	 *  			删除表定义。
	 * 				删除表。
	 * 				添加表定义。
	 * 				重新生成表。
	 *  	2.未发布。
	 *  		1.删除表定义。
	 *  		2.添加表。
	 *  		3.如果为发布。
	 *  			生成物理表。
	 * </pre>
	 * 
	 * @param bpmFormTable
	 * @throws Exception
	 */
	public Long saveTable(BpmFormTable bpmFormTable) throws Exception {
		Long mainTableId = bpmFormTable.getTableId();
		// 新表的情况。
		if (mainTableId == 0) {
			// 添加表信息
			Long tableId = addTable(bpmFormTable);
			// 生成表。
			genTable(bpmFormTable);
			return tableId;
		}
		// 定义已经添加。
		else {
			// 获取原表定义。
			BpmFormTable orginTable = getTableById(mainTableId);
			// 判断表中是否已有数据。
			boolean hasData = bpmFormHandlerDao
					.getHasData(TableModel.CUSTOMER_TABLE_PREFIX
							+ orginTable.getTableName());
			// 如果这个表定义了多个表单的情况，那么不能对表做重新创建的的操作
			// 有数据
			if (hasData) {
				// 更新表
				updTable(bpmFormTable, orginTable);
			}
			// 无数据的情况
			else {
				// 取得表单数量
				int formAmount = bpmFormDefDao.getFormDefAmount(mainTableId);
				if (formAmount > 1) {
					// 更新表。
					updTable(bpmFormTable, orginTable);
				}
				// 1.删除表定义信息
				// 2.删除表
				// 3.添加表
				// 4.添加实体表
				else {
					delTable(orginTable);
					dropTable(orginTable);
					Long tableId = addTable(bpmFormTable);
					genTable(bpmFormTable);
					return tableId;
				}
			}
		}
		return mainTableId;
	}

	/**
	 * 添加表。
	 * 
	 * <pre>
	 * 	根据表对象，添加主表和子表。
	 * </pre>
	 * 
	 * @param bpmFormTable
	 * @return
	 * @throws Exception
	 */
	private Long addTable(BpmFormTable bpmFormTable) throws Exception {
		// 保存主表数据
		Long mainTableId = UniqueIdUtil.genId();
		bpmFormTable.setTableId(mainTableId);
		bpmFormTable.setGenByForm(1);
		bpmFormTable.setMainTableId(0L);
		bpmFormTable.setIsMain((short) 1);
		// 设置发布字段
		bpmFormTable.setIsPublished((short) 1);
		bpmFormTable.setGenByForm(1);

		// 添加主表
		dao.add(bpmFormTable);

		// 添加子表列数据。
		List<BpmFormField> fields = bpmFormTable.getFieldList();
		List<BpmFormField> mainFields = convertFields(fields, false);
		// 重新设置字段。
		bpmFormTable.setFieldList(mainFields);

		for (int i = 0; i < mainFields.size(); i++) {
			BpmFormField field = mainFields.get(i);
			if (field.getFieldId() == 0) {
				field.setFieldId(UniqueIdUtil.genId());
			}
			field.setSn(i + 1);
			field.setTableId(mainTableId);
			bmpFormFieldDao.add(field);
		}
		// 子表列表。
		List<BpmFormTable> subTableList = bpmFormTable.getSubTableList();

		if (BeanUtils.isEmpty(subTableList)) {
			return mainTableId;
		}
		// 添加子表
		for (int i = 0; i < subTableList.size(); i++) {
			BpmFormTable subTable = subTableList.get(i);
			// 生成子表主键。
			Long subTableId = UniqueIdUtil.genId();
			subTable.setTableId(subTableId);
			subTable.setMainTableId(mainTableId);
			subTable.setGenByForm(1);
			subTable.setIsMain((short) 0);
			subTable.setIsPublished((short) 1);

			dao.add(subTable);

			List<BpmFormField> subfields = subTable.getFieldList();
			// 字段转换。
			List<BpmFormField> subfieldList = convertFields(subfields, false);

			subTable.setFieldList(subfieldList);

			for (int k = 0; k < subfieldList.size(); k++) {
				BpmFormField field = subfieldList.get(k);

				if (field.getFieldId() == 0) {
					field.setFieldId(UniqueIdUtil.genId());
				}

				field.setSn(k + 1);
				field.setTableId(subTableId);
				bmpFormFieldDao.add(field);
			}
		}

		return mainTableId;
	}

	/**
	 * 删除表定义。
	 * 
	 * <pre>
	 * 	删除表定义，如果有子表定义，将子表定义一并删除。
	 * </pre>
	 * 
	 * @param bpmFormTable
	 */
	public void delTable(BpmFormTable bpmFormTable) {
		Long tableId = bpmFormTable.getTableId();
		// 删除子表
		List<BpmFormTable> subTableList = bpmFormTable.getSubTableList();
		if (BeanUtils.isNotEmpty(subTableList)) {
			for (BpmFormTable subTable : subTableList) {
				Long subTableId = subTable.getTableId();
				bmpFormFieldDao.delByTableId(subTableId);
				dao.delById(subTableId);
			}
		}
		bmpFormFieldDao.delByTableId(tableId);
		dao.delById(tableId);
	}

	/**
	 * 根据BpmFormTable一次性生成表。
	 * 
	 * <pre>
	 * 同时创建主表和子表。
	 * 并创建外键。
	 * </pre>
	 * 
	 * @param table
	 * @throws SQLException
	 */
	private void genTable(BpmFormTable table) throws SQLException {
		// 创建主表
		TableModel mainTableModel = getTableModelByBpmFormTable(table);
		tableOperator.createTable(mainTableModel);
		for (BpmFormTable subTable : table.getSubTableList()) {
			TableModel subTableModel = getTableModelByBpmFormTable(subTable);
			tableOperator.createTable(subTableModel);
			tableOperator.addForeignKey(TableModel.CUSTOMER_TABLE_PREFIX
					+ table.getTableName(), TableModel.CUSTOMER_TABLE_PREFIX
					+ subTable.getTableName(), TableModel.PK_COLUMN_NAME,
					TableModel.FK_COLUMN_NAME);
		}
	}

	/**
	 * 一次性删除表。
	 * 
	 * @param table
	 * @throws SQLException
	 */
	public void dropTable(BpmFormTable table) throws SQLException {
		for (BpmFormTable subTable : table.getSubTableList()) {
			String subTableName = TableModel.CUSTOMER_TABLE_PREFIX
					+ subTable.getTableName();
			tableOperator.dropTable(subTableName);
		}
		tableOperator.dropTable(TableModel.CUSTOMER_TABLE_PREFIX
				+ table.getTableName());
	}

	/**
	 * 更新表。 bpmFormTable:不需要指定主键信息。
	 * 
	 * <pre>
	 * 	1.更新表数据。
	 * 	2.更新字段数据。
	 * 		1.如果字段为添加。
	 * 			1.添加列定义。
	 * 			2.添加字段。
	 * 		2.更新字段。
	 * 			1.如果删除字段，字段不做真正的删除，只是做删除标记。
	 * 			2.更新字段信息，不更新实体表信息。
	 * 	3.更新子表数据信息。
	 * 		1.添加子表。
	 * 			添加子表定义。
	 * 			添加子表字段定义。
	 * 			添加是体表
	 * 		2.更新表。
	 * 			1.更新列。
	 * 			2.添加列定义，添加列实体列。
	 * 
	 *   注意事项：
	 *   bpmFormTable必须指定tableId。
	 *   子表对象也需要指定。
	 * </pre>
	 * 
	 * @param bpmFormTable
	 *            新表
	 * @param orginTable
	 *            原表。
	 * @throws Exception
	 */
	private void updTable(BpmFormTable bpmFormTable, BpmFormTable orginTable)
			throws Exception {
		Long mainTableId = orginTable.getTableId();
		String tableName = bpmFormTable.getTableName();
		// 设置主键相同
		bpmFormTable.setTableId(mainTableId);
		bpmFormTable.setGenByForm(1);
		bpmFormTable.setIsPublished((short) 1);
		bpmFormTable.setPublishedBy(orginTable.getPublishedBy());
		bpmFormTable.setPublishTime(orginTable.getPublishTime());

		dao.update(bpmFormTable);

		List<BpmFormField> fields = bpmFormTable.getFieldList();
		// 获取原表字段。
		List<BpmFormField> orginFields = orginTable.getFieldList();
		Map<String, List<BpmFormField>> mainFieldMap = caculateFields(fields,
				orginFields);
		List<BpmFormField> updList = mainFieldMap.get("upd");
		List<BpmFormField> addList = mainFieldMap.get("add");

		// 更新列
		for (int i = 0; i < updList.size(); i++) {
			BpmFormField bpmFormField = updList.get(i);
			bmpFormFieldDao.update(bpmFormField);
		}
		if (BeanUtils.isNotEmpty(addList)) {
			addList = convertFields(addList, false);
			// 添加列
			for (int i = 0; i < addList.size(); i++) {
				BpmFormField bpmFormField = addList.get(i);
				// 设置字段id。
				if (bpmFormField.getFieldId() == 0) {
					bpmFormField.setFieldId(UniqueIdUtil.genId());
				}

				bpmFormField.setTableId(mainTableId);
				bmpFormFieldDao.add(bpmFormField);
				ColumnModel columnModel = getByField(bpmFormField, 2);
				tableOperator.addColumn(TableModel.CUSTOMER_TABLE_PREFIX
						+ tableName, columnModel);
			}
		}

		// 子表处理
		// 获取原子表
		List<BpmFormTable> originTableList = orginTable.getSubTableList();
		Map<String, BpmFormTable> originTableMap = new HashMap<String, BpmFormTable>();
		for (BpmFormTable orginSubTable : originTableList) {
			originTableMap.put(orginSubTable.getTableName().toLowerCase(),
					orginSubTable);
		}
		// 子表的处理
		List<BpmFormTable> subTableList = bpmFormTable.getSubTableList();
		if (BeanUtils.isEmpty(subTableList))
			return;

		for (int i = 0; i < subTableList.size(); i++) {
			BpmFormTable subTable = subTableList.get(i);
			String subTableName = subTable.getTableName().toLowerCase();
			if (originTableMap.containsKey(subTableName)) {
				BpmFormTable orginSubTable = originTableMap.get(subTableName);
				List<BpmFormField> orginSubFields = orginSubTable
						.getFieldList();

				Long orginTableId = orginSubTable.getTableId();
				// 更新表的信息。
				subTable.setTableId(orginTableId);
				subTable.setMainTableId(mainTableId);
				subTable.setIsPublished((short) 1);
				subTable.setPublishedBy(orginSubTable.getPublishedBy());
				subTable.setPublishTime(orginSubTable.getPublishTime());

				dao.update(subTable);
				List<BpmFormField> subFields = subTable.getFieldList();
				Map<String, List<BpmFormField>> subFieldMap = caculateFields(
						subFields, orginSubFields);
				List<BpmFormField> updSubList = subFieldMap.get("upd");
				List<BpmFormField> addSubList = subFieldMap.get("add");

				// 更新列
				for (int k = 0; k < updSubList.size(); k++) {
					BpmFormField bpmFormField = updSubList.get(k);
					bmpFormFieldDao.update(bpmFormField);
				}
				// 添加列不为空
				if (BeanUtils.isNotEmpty(addList)) {
					addSubList = convertFields(addSubList, false);
					// 添加列
					for (int k = 0; k < addSubList.size(); k++) {

						BpmFormField bpmFormField = addSubList.get(k);
						if (bpmFormField.getFieldId() == 0) {
							bpmFormField.setFieldId(UniqueIdUtil.genId());
						}
						bpmFormField.setTableId(orginTableId);
						// 设置表
						bmpFormFieldDao.add(bpmFormField);
						// 添加字段
						ColumnModel columnModel = getByField(bpmFormField, 2);
						tableOperator
								.addColumn(TableModel.CUSTOMER_TABLE_PREFIX
										+ subTableName, columnModel);
					}
				}
			}
			// 新加的子表。
			else {
				Long newTableId = UniqueIdUtil.genId();
				subTable.setTableId(newTableId);
				subTable.setIsMain((short) 0);
				subTable.setMainTableId(mainTableId);
				subTable.setIsPublished((short) 1);
				dao.add(subTable);

				List<BpmFormField> subFields = subTable.getFieldList();
				subFields = convertFields(subFields, false);
				for (BpmFormField field : subFields) {
					Long newFieldId = UniqueIdUtil.genId();
					field.setFieldId(newFieldId);
					field.setTableId(newTableId);

					bmpFormFieldDao.add(field);
				}
				TableModel subTableModel = getTableModelByBpmFormTable(subTable);
				tableOperator.createTable(subTableModel);
				tableOperator.addForeignKey(TableModel.CUSTOMER_TABLE_PREFIX
						+ tableName, TableModel.CUSTOMER_TABLE_PREFIX
						+ subTable.getTableName(), TableModel.PK_COLUMN_NAME,
						TableModel.FK_COLUMN_NAME);
			}
		}
	}

}
