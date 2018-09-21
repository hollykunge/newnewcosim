package com.casic.cloud.service.config.capabilityMaterial;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.casic.cloud.model.config.capabilityMaterial.CapabilityMaterial;
import com.casic.cloud.dao.config.capabilityMaterial.CapabilityMaterialDao;

/**
 *<pre>
 * 对象功能:cloud_capability_material Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 18:13:05
 *</pre>
 */
@Service
public class CapabilityMaterialService extends BaseService<CapabilityMaterial>
{
	@Resource
	private CapabilityMaterialDao dao;
	
	
	
	public CapabilityMaterialService()
	{
	}
	
	@Override
	protected IEntityDao<CapabilityMaterial, Long> getEntityDao() 
	{
		return dao;
	}
	
	
	public List<CapabilityMaterial> getCapabilityMaterials(Map<String,Object> params){
		return dao.getCapabilityMaterials(params);
		
	}
	
}
