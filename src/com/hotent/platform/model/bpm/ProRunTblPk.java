package com.hotent.platform.model.bpm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hotent.core.model.BaseModel;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
/**
 * 对象功能:BPM_PRO_RUN_TBL_PK Model对象
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-16 11:25:27
 */
public class ProRunTblPk extends BaseModel
{	/**
	 * 为主表
	 */
	public static Short IS_MAIN_TABLE_YES = 1;
	/**
	 * 不是主表
	 */
	public static Short IS_MAIN_TABLE_NO =  0;
	// TBL_PK_ID
	protected Long  tblPkId;
	// RUN_ID
	protected Long  runId;
	// TABLE_ID
	protected Long  tableId;
	// PK_ID
	protected Long  pkId;
	// 主表ID
	protected Long  mainTableId;
	// 是否为主表:0-不是，2-是
	protected Short  isMainTable;
	// 主表主键值
	protected Long  mainTablePk;
	// 主键名
	protected String  pkName;
	// 表名名
	protected String  tableName;
	
	//
	protected LinkedHashMap<Long,List<ProRunTblPk>> subProRunTblPkMap = new LinkedHashMap<Long,List<ProRunTblPk>>();
	
	public LinkedHashMap<Long,List<ProRunTblPk>> getSubProRunTblPkMap() {
		return subProRunTblPkMap;
	}
	public void setSubProRunTblPkMap(LinkedHashMap<Long,List<ProRunTblPk>> subProRunTblPkMap) {
		this.subProRunTblPkMap = subProRunTblPkMap;
	}
	public void setTblPkId(Long tblPkId) 
	{
		this.tblPkId = tblPkId;
	}
	/**
	 * 返回 TBL_PK_ID
	 * @return
	 */
	public Long getTblPkId() 
	{
		return this.tblPkId;
	}
	public void setRunId(Long runId) 
	{
		this.runId = runId;
	}
	/**
	 * 返回 RUN_ID
	 * @return
	 */
	public Long getRunId() 
	{
		return this.runId;
	}
	public void setTableId(Long tableId) 
	{
		this.tableId = tableId;
	}
	/**
	 * 返回 TABLE_ID
	 * @return
	 */
	public Long getTableId() 
	{
		return this.tableId;
	}
	public void setPkId(Long pkId) 
	{
		this.pkId = pkId;
	}
	/**
	 * 返回 PK_ID
	 * @return
	 */
	public Long getPkId() 
	{
		return this.pkId;
	}
	public void setMainTableId(Long mainTableId) 
	{
		this.mainTableId = mainTableId;
	}
	/**
	 * 返回 主表ID
	 * @return
	 */
	public Long getMainTableId() 
	{
		return this.mainTableId;
	}
	public void setIsMainTable(Short isMainTable) 
	{
		this.isMainTable = isMainTable;
	}
	/**
	 * 返回 是否为主表:0-不是，2-是
	 * @return
	 */
	public Short getIsMainTable() 
	{
		return this.isMainTable;
	}
	public void setMainTablePk(Long mainTablePk) 
	{
		this.mainTablePk = mainTablePk;
	}
	/**
	 * 返回 主表主键值
	 * @return
	 */
	public Long getMainTablePk() 
	{
		return this.mainTablePk;
	}
	public void setPkName(String pkName) 
	{
		this.pkName = pkName;
	}
	/**
	 * 返回 主键名
	 * @return
	 */
	public String getPkName() 
	{
		return this.pkName;
	}
	public void setTableName(String tableName) 
	{
		this.tableName = tableName;
	}
	/**
	 * 返回 表名名
	 * @return
	 */
	public String getTableName() 
	{
		return this.tableName;
	}


   	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ProRunTblPk)) 
		{
			return false;
		}
		ProRunTblPk rhs = (ProRunTblPk) object;
		return new EqualsBuilder()
		.append(this.tblPkId, rhs.tblPkId)
		.append(this.runId, rhs.runId)
		.append(this.tableId, rhs.tableId)
		.append(this.pkId, rhs.pkId)
		.append(this.mainTableId, rhs.mainTableId)
		.append(this.isMainTable, rhs.isMainTable)
		.append(this.mainTablePk, rhs.mainTablePk)
		.append(this.pkName, rhs.pkName)
		.append(this.tableName, rhs.tableName)
		.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.tblPkId) 
		.append(this.runId) 
		.append(this.tableId) 
		.append(this.pkId) 
		.append(this.mainTableId) 
		.append(this.isMainTable) 
		.append(this.mainTablePk) 
		.append(this.pkName) 
		.append(this.tableName) 
		.toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("tblPkId", this.tblPkId) 
		.append("runId", this.runId) 
		.append("tableId", this.tableId) 
		.append("pkId", this.pkId) 
		.append("mainTableId", this.mainTableId) 
		.append("isMainTable", this.isMainTable) 
		.append("mainTablePk", this.mainTablePk) 
		.append("pkName", this.pkName) 
		.append("tableName", this.tableName) 
		.toString();
	}
   
  

}