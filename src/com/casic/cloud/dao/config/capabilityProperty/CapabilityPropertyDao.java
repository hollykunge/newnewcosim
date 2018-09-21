package com.casic.cloud.dao.config.capabilityProperty;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.capabilityProperty.CapabilityProperty;
/**
 *<pre>
 * 对象功能:cloud_capability_property Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 16:10:47
 *</pre>
 */
@Repository
public class CapabilityPropertyDao extends BaseDao<CapabilityProperty>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CapabilityProperty.class;
	}

	
	public List<CapabilityProperty> getCapabilityProperty(Map<String,Object> params){
		return this.getBySqlKey("getCapabilityProperty", params);
		
	}
}