package com.casic.cloud.dao.config.capability;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.capability.Capability;
/**
 *<pre>
 * 对象功能:cloud_capability Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:23:23
 *</pre>
 */
@Repository
public class CapabilityDao extends BaseDao<Capability>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Capability.class;
	}
	
	public List<Capability> getAllCg(String sqlKey,QueryFilter queryFilter){
		return this.getBySqlKey(sqlKey, queryFilter);
	}
	public List<Capability> getTop(Map<String, Object> m){
		return this.getBySqlKey("getLastCapabilitys",m);
	}
	
	/**
	 * 获取所有商机数量
	 * @return
	 */
	public Integer getAllCapabilityCount(Map<String, Object> m){
		Integer count  = (Integer) getOne("getAllCapabilityCount",m);
		return count;
	}
	
	
	public List<Capability> getAll_query(String sqlKey,QueryFilter queryFilter){
		return this.getBySqlKey(sqlKey, queryFilter);
	}
 
	
	
	 

}