package com.casic.cloud.dao.config.capabilityPropertyValue;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.capabilityClass.CapabilityClass;
import com.casic.cloud.model.config.capabilityPropertyValue.CapabilityPropertyValue;
/**
 *<pre>
 * 对象功能:cloud_capability_property_value Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:00:33
 *</pre>
 */
@Repository
public class CapabilityPropertyValueDao extends BaseDao<CapabilityPropertyValue>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CapabilityPropertyValue.class;
	}
	public List<CapabilityPropertyValue> getCapabilityPropertyValues(Map<String,Object> params){
		return this.getBySqlKey("getCapabilityPropertyValues", params);
		
	}
	public void delByMainId(Long capabilityId) {
		this.delBySqlKey("delByCid", capabilityId);
	}

}