package com.casic.cloud.dao.config.capabilityClass;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.config.capabilityClass.CapabilityClass;
/**
 *<pre>
 * 对象功能:cloud_capability_class Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:17:15
 *</pre>
 */
@Repository
public class CapabilityClassDao extends BaseDao<CapabilityClass>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CapabilityClass.class;
	}
	
	
	public List<CapabilityClass> getChildren(Map<String,Object> params){
		return this.getBySqlKey("getChildren",params);
		
	}
	
	public List<CapabilityClass> getClasses(Map<String,Object> params){
		return this.getBySqlKey("getClasses", params);
		
	}

}