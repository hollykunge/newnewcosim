package com.casic.cloud.dao.config.capabilityMaterial;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.config.capabilityMaterial.CapabilityMaterial;
/**
 *<pre>
 * 对象功能:cloud_capability_material Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 18:13:05
 *</pre>
 */
@Repository
public class CapabilityMaterialDao extends BaseDao<CapabilityMaterial>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CapabilityMaterial.class;
	}
	
	public List<CapabilityMaterial> getCapabilityMaterials(Map<String,Object> params){
		return this.getBySqlKey("getCapabilityMaterials", params);
		
	}
	
	public void delByMainId(Long capabilityId) {
		this.delBySqlKey("delByMid", capabilityId);
	}
	

}