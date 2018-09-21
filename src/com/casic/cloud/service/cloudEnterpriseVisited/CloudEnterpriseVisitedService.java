package com.casic.cloud.service.cloudEnterpriseVisited;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.cloudEnterpriseVisited.CloudEnterpriseVisited;
import com.casic.cloud.dao.cloudEnterpriseVisited.CloudEnterpriseVisitedDao;

/**
 *<pre>
 * 对象功能:cloud_enterprise_visited Service类
 * 开发公司:tianzhi
 * 开发人员:xingchi
 * 创建时间:2013-05-03 10:34:39
 *</pre>
 */
@Service
public class CloudEnterpriseVisitedService extends BaseService<CloudEnterpriseVisited>
{
	@Resource
	private CloudEnterpriseVisitedDao dao;
	
	
	
	public CloudEnterpriseVisitedService()
	{
	}
	
	@Override
	protected IEntityDao<CloudEnterpriseVisited, Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<CloudEnterpriseVisited> getByInterventId(Long intervent_id){
		return dao.getByInterventId(intervent_id);
	}
	
	
}
