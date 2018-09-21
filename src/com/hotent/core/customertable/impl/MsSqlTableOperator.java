package com.hotent.core.customertable.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hotent.core.customertable.AbstractTableOperator;
import com.hotent.core.customertable.ColumnModel;
import com.hotent.core.customertable.ITableOperator;
import com.hotent.core.customertable.TableModel;
import com.hotent.platform.model.form.BpmFormTableIndex;

/**
 * sqlserver 2000数据表操作的实现。目前未实现。
 * @author ray
 *
 */
public class MsSqlTableOperator extends AbstractTableOperator {

	@Override
	public void setJdbcTemplate(JdbcTemplate template) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createTable(TableModel model) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTableComment(String tableName, String comment)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addColumn(String tableName, ColumnModel model)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateColumn(String tableName, String columnName,
			ColumnModel model) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropTable(String tableName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addForeignKey(String pkTableName, String fkTableName, String pkField,
			String fkField) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropForeignKey(String tableName, String keyName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getDbType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dropIndex(String tableName, String indexName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BpmFormTableIndex getIndex(String tableName, String indexName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BpmFormTableIndex> getIndexesByTable(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BpmFormTableIndex> getIndexesByFuzzyMatching(String tableName, String indexName,Boolean getDDL) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rebuildIndex(String tableName, String indexName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void createIndex(BpmFormTableIndex index) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
