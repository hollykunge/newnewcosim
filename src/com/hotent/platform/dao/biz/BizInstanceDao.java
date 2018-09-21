package com.hotent.platform.dao.biz;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.biz.BizInstance;
/**
 *<pre>
 * 对象功能:业务实例 Dao类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 *</pre>
 */
@Repository
public class BizInstanceDao extends BaseDao<BizInstance>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BizInstance.class;
	}

}