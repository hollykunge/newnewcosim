/**
 * 对象功能:模版管理 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:pkq
 * 创建时间:2011-12-28 14:04:30
 */
package com.hotent.platform.dao.system;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.system.SysTemplate;

@Repository
public class SysTemplateDao extends BaseDao<SysTemplate>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return SysTemplate.class;
	}
	
	/**
	 * 设置默认模板
	 * @param id
	 */
	public int setDefault(long id){
		return this.update("updateDefault", id);
	}
	
	/**
	 * 设置某类型的所有模板为非默认状态
	 * @param tempType
	 */
	public int setNotDefaultByTempType(Integer tempType){
		return this.update("updateNotDefaultByTempType", tempType);
	}
	
	/**
	 * 获取某类型模板的默认模板
	 * @param tempType
	 * @return
	 */
	public SysTemplate getDefaultByType(Integer tempType)
	{
		return this.getUnique("getDefaultByType", tempType);
	}
	
	/**
	 * 获取某类型模板的系统模板
	 * @param tempType
	 * @return
	 */
	public SysTemplate getSystemByType(Integer tempType)
	{
		return this.getUnique("getSystemByType", tempType);
	}
}