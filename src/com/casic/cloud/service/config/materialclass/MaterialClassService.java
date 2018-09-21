package com.casic.cloud.service.config.materialclass;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.capabilityClass.CapabilityClass;
import com.casic.cloud.model.config.materialclass.MaterialClass;
import com.casic.cloud.dao.config.materialclass.MaterialClassDao;

/**
 *<pre>
 * 对象功能:cloud_material_class Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 18:32:56
 *</pre>
 */
@Service
public class MaterialClassService extends BaseService<MaterialClass>
{
	@Resource
	private MaterialClassDao dao;
	
	
	
	public MaterialClassService()
	{
	}
	
	@Override
	protected IEntityDao<MaterialClass, Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<MaterialClass> getChildren(String sqlKey,Map<String,Object> params){
		return dao.getChildren(sqlKey, params);
		
	}
	
}
