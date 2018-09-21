package com.casic.cloud.service.config.capabilityProperty;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.casic.cloud.model.config.capabilityProperty.CapabilityProperty;
import com.casic.cloud.dao.config.capabilityProperty.CapabilityPropertyDao;

/**
 *<pre>
 * 对象功能:cloud_capability_property Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 16:10:48
 *</pre>
 */
@Service
public class CapabilityPropertyService extends BaseService<CapabilityProperty>
{
	@Resource
	private CapabilityPropertyDao dao;
	
	
	
	public CapabilityPropertyService()
	{
	}
	
	@Override
	protected IEntityDao<CapabilityProperty, Long> getEntityDao() 
	{
		return dao;
	}
	public List<CapabilityProperty> getCapabilityProperty(Map<String,Object> params){
		return dao.getCapabilityProperty(params);
		
	}
	
	
	
}
