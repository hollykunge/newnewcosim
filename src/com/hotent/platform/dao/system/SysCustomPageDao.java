package com.hotent.platform.dao.system;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.system.SysCustomPage;
/**
 * 对象功能:自定义页面 Dao类
 * 开发公司:宏天
 * 开发人员:Raise
 * 创建时间:2012-11-05 09:07:38
 */
@Repository
public class SysCustomPageDao extends BaseDao<SysCustomPage>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return SysCustomPage.class;
	}

}