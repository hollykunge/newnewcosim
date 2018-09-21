package com.casic.cloud.service.config.materialProperty;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.capabilityProperty.CapabilityProperty;
import com.casic.cloud.model.config.materialProperty.MaterialProperty;
import com.casic.cloud.dao.config.materialProperty.MaterialPropertyDao;

/**
 *<pre>
 * 对象功能:cloud_material_property Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 18:50:26
 *</pre>
 */
@Service
public class MaterialPropertyService extends BaseService<MaterialProperty>
{
	@Resource
	private MaterialPropertyDao dao;
	
	
	
	public MaterialPropertyService()
	{
	}
	
	@Override
	protected IEntityDao<MaterialProperty, Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<MaterialProperty> getMaterialProperty(String sqlKey,Map<String,Object> params){
		return dao.getMaterialProperty(sqlKey, params);
		
	}
	
}
