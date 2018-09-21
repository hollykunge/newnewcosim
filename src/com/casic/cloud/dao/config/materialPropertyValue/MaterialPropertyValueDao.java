package com.casic.cloud.dao.config.materialPropertyValue;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.config.capabilityPropertyValue.CapabilityPropertyValue;
import com.casic.cloud.model.config.materialPropertyValue.MaterialPropertyValue;
/**
 *<pre>
 * 对象功能:cloud_material_property_value Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 18:32:24
 *</pre>
 */
@Repository
public class MaterialPropertyValueDao extends BaseDao<MaterialPropertyValue>
{
	@Override
	public Class<?> getEntityClass()
	{
		return MaterialPropertyValue.class;
	}
	
	public List<MaterialPropertyValue> getMaterialPropertyValues(Map<String,Object> params){
		return this.getBySqlKey("getBymid", params);
		
	}
	
	public List<MaterialPropertyValue> getByMainId(Long materialId) {
		return this.getBySqlKey("getPropertyValueList", materialId);
	}
	
	public void delByMainId(Long materialId) {
		this.delBySqlKey("delByMainId", materialId);
	}
	
	
}