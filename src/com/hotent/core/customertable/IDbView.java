package com.hotent.core.customertable;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hotent.core.customertable.TableModel;
import com.hotent.core.page.PageBean;
import com.hotent.platform.model.system.SysDataSource;

/**
 * 视图接口定义类。
 * 1.获取数据库视图列表数据。
 * 2.获取某个视图的具体信息，数据保存到TableModel中。
 * @author ray
 *
 */
public interface IDbView {
	/**
	 * 设置数据源
	 * @param sysDataSource
	 */
	public void setDataSource(SysDataSource sysDataSource) throws Exception;
	
	/**
	 * 使用模糊匹配，获取系统视图名称。
	 * @return
	 */
	public List<String> getViews(String viewName) throws SQLException;
	
	/**
	 * 使用模糊匹配，获取系统视图名称。
	 * @return
	 * @throws Exception 
	 */
	List<String> getViews(String viewName, PageBean pageBean) throws SQLException, Exception;
	
	/**
	 * 根据视图名称，使用精确匹配，获取视图详细信息。
	 * @param viewName
	 * @return
	 */
	TableModel getModelByViewName(String viewName) throws SQLException;
	/**
	 * 根据视图名，使用模糊匹配，称获取视图详细信息。
	 * @param viewName
	 * @return
	 */
	List<TableModel> getViewsByName(String viewName, PageBean pageBean) throws Exception;

}
