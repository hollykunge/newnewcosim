package com.casic.cloud.dao.compass.compassNews;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.compass.compassNews.CompassNews;
/**
 *<pre>
 * 对象功能:cloud_compass_news Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-20 15:21:22
 *</pre>
 */
@Repository
public class CompassNewsDao extends BaseDao<CompassNews>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CompassNews.class;
	}

}