package com.casic.cloud.dao.config.materialclass;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.capabilityClass.CapabilityClass;
import com.casic.cloud.model.config.materialclass.MaterialClass;
/**
 *<pre>
 * 对象功能:cloud_material_class Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 18:32:56
 *</pre>
 */
@Repository
public class MaterialClassDao extends BaseDao<MaterialClass>
{
	@Override
	public Class<?> getEntityClass()
	{
		return MaterialClass.class;
	}
	
	/**
	 * 查询所有的物品分类
	 * @param sqlKey sql语句
	 * @param params 返回值
	 * @return
	 */
	public List<MaterialClass> getChildren(String sqlKey,Map<String,Object> params){
		return this.getBySqlKey(sqlKey, params);
		
	}

}