package com.casic.cloud.dao.system.news;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.casic.cloud.model.system.news.News;
import com.hotent.core.db.BaseDao;
/**
 *<pre>
 * 对象功能:NEWS Dao类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2013-04-12 21:43:57
 *</pre>
 */
@Repository
public class NewsDao extends BaseDao<News>
{
	@Override
	public Class<?> getEntityClass()
	{
		return News.class;
	}
	public List<News> getLastNews(){
		return this.getBySqlKey("getLastNews");
	}
}