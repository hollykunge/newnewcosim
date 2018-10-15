package com.hotent.platform.dao.system;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.system.SysCustomDisplay;
/**
 * 对象功能:SYS_CUSTOM_DISPLAY Dao类
 * 开发公司:宏天
 * 开发人员:Raise
 * 创建时间:2012-10-23 17:59:41
 */
@Repository
public class SysCustomDisplayDao extends BaseDao<SysCustomDisplay>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return SysCustomDisplay.class;
	}

}