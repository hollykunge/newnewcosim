package com.casic.cloud.service.system.news;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.cloud.dao.system.news.NewsDao;
import com.casic.cloud.model.system.news.News;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;

/**
 *<pre>
 * 对象功能:AUTH_NEWS Service类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2013-04-12 21:43:57
 *</pre>
 */
@Service
public class NewsService extends BaseService<News>
{
	@Resource
	private NewsDao dao;
	
	
	
	public NewsService()
	{
	}
	
	@Override
	protected IEntityDao<News, Long> getEntityDao() 
	{
		return dao;
	}
	

	public List<News> getLastNews(){
		return dao.getLastNews();
	}
	
}
