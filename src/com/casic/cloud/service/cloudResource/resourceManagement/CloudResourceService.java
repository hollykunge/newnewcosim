package com.casic.cloud.service.cloudResource.resourceManagement;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.cloudResource.resourceManagement.CloudResource;
import com.casic.cloud.dao.cloudResource.resourceManagement.CloudResourceDao;
import com.casic.cloud.model.cloudResource.resourceManagement.CloudResourceInstance;
import com.casic.cloud.dao.cloudResource.resourceManagement.CloudResourceInstanceDao;

/**
 *<pre>
 * 对象功能:cloud_resource_class Service类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-06-13 18:05:09
 *</pre>
 */
@Service
public class CloudResourceService extends BaseService<CloudResource>
{
	@Resource
	private CloudResourceDao dao;
	
	@Resource
	private CloudResourceInstanceDao cloudResourceInstanceDao;
	
	
	public CloudResourceService()
	{
	}
	
	@Override
	protected IEntityDao<CloudResource, Long> getEntityDao() 
	{
		return dao;
	}
	
	private void delByPk(Long id){
		cloudResourceInstanceDao.delByMainId(id);
	}
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public void addAll(CloudResource cloudResource) throws Exception{
		add(cloudResource);
		addSubList(cloudResource);
	}
	
	public void updateAll(CloudResource cloudResource) throws Exception{
		update(cloudResource);
		delByPk(cloudResource.getId());
		addSubList(cloudResource);
	}
	
	public void addSubList(CloudResource cloudResource) throws Exception{
		List<CloudResourceInstance> cloudResourceInstanceList=cloudResource.getCloudResourceInstanceList();
		if(BeanUtils.isNotEmpty(cloudResourceInstanceList)){
			for(CloudResourceInstance cloudResourceInstance:cloudResourceInstanceList){
				cloudResourceInstance.setClassId(cloudResource.getId());
				cloudResourceInstance.setId(UniqueIdUtil.genId());
				cloudResourceInstanceDao.add(cloudResourceInstance);
			}
		}
	}
	
	public List<CloudResourceInstance> getCloudResourceInstanceList(Long id) {
		return cloudResourceInstanceDao.getByMainId(id);
	}
	
	public List<CloudResourceInstance> getAllResourceInstanceList(QueryFilter queryFilter) {
		return cloudResourceInstanceDao.getAllInstance(queryFilter);
	}
}
