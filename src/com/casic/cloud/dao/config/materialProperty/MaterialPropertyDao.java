package com.casic.cloud.dao.config.materialProperty;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.capabilityProperty.CapabilityProperty;
import com.casic.cloud.model.config.materialProperty.MaterialProperty;
/**
 *<pre>
 * 对象功能:cloud_material_property Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 18:50:26
 *</pre>
 */
@Repository
public class MaterialPropertyDao extends BaseDao<MaterialProperty>
{
	@Override
	public Class<?> getEntityClass()
	{
		return MaterialProperty.class;
	}
	
	public List<MaterialProperty> getMaterialProperty(String sqlKey,Map<String,Object> params){
		return this.getBySqlKey(sqlKey, params);
		
	}

}