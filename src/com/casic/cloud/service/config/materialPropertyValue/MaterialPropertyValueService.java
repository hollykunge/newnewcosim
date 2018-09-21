package com.casic.cloud.service.config.materialPropertyValue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.config.capabilityPropertyValue.CapabilityPropertyValue;
import com.casic.cloud.model.config.materialPropertyValue.MaterialPropertyValue;
import com.casic.cloud.dao.config.materialPropertyValue.MaterialPropertyValueDao;

/**
 *<pre>
 * 对象功能:cloud_material_property_value Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 18:32:24
 *</pre>
 */
@Service
public class MaterialPropertyValueService extends BaseService<MaterialPropertyValue>
{
	@Resource
	private MaterialPropertyValueDao dao;
	
	
	
	public MaterialPropertyValueService()
	{
	}
	
	@Override
	protected IEntityDao<MaterialPropertyValue, Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<MaterialPropertyValue> getMaterialPropertyValues(Map<String,Object> params){
		return dao.getMaterialPropertyValues(params);
		
	}
	
	
}
