package com.casic.cloud.dao.config.material;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.capability.Capability;
import com.casic.cloud.model.config.material.Material;
/**
 *<pre>
 * 对象功能:CLOUD_MATERIAL Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 15:38:14
 *</pre>
 */
@Repository
public class MaterialDao extends BaseDao<Material>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Material.class;
	}
	public List<Material> getByCatalogId(String sqlKey,QueryFilter queryFilter){
		return this.getBySqlKey(sqlKey, queryFilter);
	}
	public int changeToAdd(String sqlKey,Object params){
		return this.update(sqlKey, params);
	}
	public int changeToOff(String sqlKey,Object params){
		return this.update(sqlKey, params);
	}

}