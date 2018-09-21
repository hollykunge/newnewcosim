package com.casic.cloud.service.compass.compassNews;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.compass.compassNews.CompassNews;
import com.casic.cloud.dao.compass.compassNews.CompassNewsDao;

/**
 *<pre>
 * 对象功能:cloud_compass_news Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-20 15:21:22
 *</pre>
 */
@Service
public class CompassNewsService extends BaseService<CompassNews>
{
	@Resource
	private CompassNewsDao dao;
	
	
	
	public CompassNewsService()
	{
	}
	
	@Override
	protected IEntityDao<CompassNews, Long> getEntityDao() 
	{
		return dao;
	}
	
	
}
