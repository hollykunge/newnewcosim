/**
 * 对象功能:CG_RSS Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:pkq
 * 创建时间:2012-05-03 18:12:26
 */
package com.hotent.platform.dao.info;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.info.Rss;

@Repository
public class RssDao extends BaseDao<Rss>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return Rss.class;
	}
}