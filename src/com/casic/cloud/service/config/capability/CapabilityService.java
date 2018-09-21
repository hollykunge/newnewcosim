package com.casic.cloud.service.config.capability;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.capability.Capability;
import com.casic.cloud.dao.config.capability.CapabilityDao;
import com.casic.cloud.dao.config.capabilityMaterial.CapabilityMaterialDao;
import com.casic.cloud.dao.config.capabilityPropertyValue.CapabilityPropertyValueDao;

/**
 *<pre>
 * 对象功能:cloud_capability Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:23:23
 *</pre>
 */
@Service
public class CapabilityService extends BaseService<Capability>
{
	@Resource
	private CapabilityDao dao;
	@Resource
	private CapabilityPropertyValueDao capabilityPropertyValueDao;
	@Resource
	private CapabilityMaterialDao capabilityMaterialDao;
	
	public CapabilityService()
	{
	}
	
	@Override
	protected IEntityDao<Capability, Long> getEntityDao() 
	{
		return dao;
	}
	
	public void delByCid(Long capabilityId){
		capabilityPropertyValueDao.delByMainId(capabilityId);
	}
	
	public void delByMid(Long capabilityId){
		capabilityMaterialDao.delByMainId(capabilityId);
	}
	
	public List<Capability> getAllCg(String sqlKey,QueryFilter queryFilter){
		return dao.getAllCg(sqlKey,queryFilter);
	}
	public List<Capability> getTop(Map<String, Object> m){
		return dao.getTop(m);
	}
	
	public List<Capability> getAll_query(String sqlKey,QueryFilter queryFilter){
		return dao.getAll_query(sqlKey,queryFilter);
	}
  
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByCid(id);
			delByMid(id);
			dao.delById(id);	
		}	
	}
	
	/**
	 * 获取所有商机数量
	 * @return
	 */
	public Integer getAllCapabilityCount(Map<String, Object> m){
		return dao.getAllCapabilityCount(m);
	}
	
}
