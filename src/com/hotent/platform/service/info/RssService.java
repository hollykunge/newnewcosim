package com.hotent.platform.service.info;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.platform.model.info.Rss;
import com.hotent.platform.dao.info.RssDao;

/**
 * 对象功能:CG_RSS Service类
 * 开发公司:
 * 开发人员:pkq
 * 创建时间:2012-05-03 18:12:26
 */
@Service
public class RssService extends BaseService<Rss>
{
	@Resource
	private RssDao dao;
	
	public RssService()
	{
	}
	
	@Override
	protected IEntityDao<Rss, Long> getEntityDao() 
	{
		return dao;
	}
}
