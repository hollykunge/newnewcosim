package com.casic.cloud.service.config.cloudSrc;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.businessDevchase.BusinessDevchase;
import com.casic.cloud.model.config.cloudSrc.CloudSrc;
import com.casic.cloud.dao.config.cloudSrc.CloudSrcDao;

/**
 *<pre>
 * 对象功能:cloud_src Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-07 17:41:47
 *</pre>
 */
@Service
public class CloudSrcService extends BaseService<CloudSrc>
{
	@Resource
	private CloudSrcDao dao;
	
	
	
	public CloudSrcService()
	{
	}
	
	@Override
	protected IEntityDao<CloudSrc, Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<CloudSrc> getCloudSrc(String sqlKey,QueryFilter queryFilter) {
		return dao.getCloudSrc( sqlKey, queryFilter);
	}
}
