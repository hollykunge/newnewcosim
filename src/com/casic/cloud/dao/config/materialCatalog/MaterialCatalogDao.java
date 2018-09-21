package com.casic.cloud.dao.config.materialCatalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.casic.cloud.model.config.materialCatalog.MaterialCatalog;
/**
 *<pre>
 * 对象功能:cloud_material_catalog Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-17 11:41:35
 *</pre>
 */
@Repository
public class MaterialCatalogDao extends BaseDao<MaterialCatalog>
{

	@Override
	public Class<?> getEntityClass()
	{
		return MaterialCatalog.class;
	}

	public List getChildByParentId(long parentId)
	{
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("parentId", parentId);
		m.put("ent_id", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		return this.getBySqlKey("getByParentId", m);
	}

}