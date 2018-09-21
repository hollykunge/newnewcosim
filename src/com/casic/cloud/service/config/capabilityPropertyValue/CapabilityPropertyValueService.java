package com.casic.cloud.service.config.capabilityPropertyValue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.casic.cloud.model.config.capabilityPropertyValue.CapabilityPropertyValue;
import com.casic.cloud.dao.config.capabilityPropertyValue.CapabilityPropertyValueDao;

/**
 *<pre>
 * 对象功能:cloud_capability_property_value Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:00:34
 *</pre>
 */
@Service
public class CapabilityPropertyValueService extends BaseService<CapabilityPropertyValue>
{
	@Resource
	private CapabilityPropertyValueDao dao;
	
	
	
	public CapabilityPropertyValueService()
	{
	}
	
	@Override
	protected IEntityDao<CapabilityPropertyValue, Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<CapabilityPropertyValue> getCapabilityPropertyValues(Map<String,Object> params){
		return dao.getCapabilityPropertyValues(params);
		
	}
	
	
	
}
