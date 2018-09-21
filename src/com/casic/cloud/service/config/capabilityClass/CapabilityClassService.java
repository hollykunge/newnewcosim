package com.casic.cloud.service.config.capabilityClass;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.casic.cloud.model.config.capabilityClass.CapabilityClass;
import com.casic.cloud.dao.config.capabilityClass.CapabilityClassDao;

/**
 *<pre>
 * 对象功能:cloud_capability_class Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:17:15
 *</pre>
 */
@Service
public class CapabilityClassService extends BaseService<CapabilityClass>
{
	@Resource
	private CapabilityClassDao dao;
	
	
	
	public CapabilityClassService()
	{
	}
	
	@Override
	protected IEntityDao<CapabilityClass, Long> getEntityDao() 
	{
		return dao;
	}
	
	
	public List<CapabilityClass> getChildren(Map<String,Object> params){
		return dao.getChildren(params);
		
	}
	
	public List<CapabilityClass> getClasses(Map<String,Object> params){
		return dao.getClasses(params);
		
	}
	
 
	
	
	
	
}
