package com.casic.cloud.service.config.aptitude;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.casic.cloud.dao.config.aptitude.AptitudeDao;
import com.casic.cloud.model.config.aptitude.Aptitude;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;

/**
 *<pre>
 * 对象功能:sys_org_info_aptitude Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-06 16:34:55
 *</pre>
 */
@Service
public class AptitudeService extends BaseService<Aptitude>
{
	@Resource
	private AptitudeDao dao;
	
	
	
	public AptitudeService()
	{
	}
	
	@Override
	protected IEntityDao<Aptitude, Long> getEntityDao() 
	{
		return dao;
	}
	
	
}
