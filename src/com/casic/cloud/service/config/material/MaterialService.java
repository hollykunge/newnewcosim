package com.casic.cloud.service.config.material;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.material.Material;
import com.casic.cloud.model.config.materialPropertyValue.MaterialPropertyValue;
import com.casic.cloud.service.config.materialPropertyValue.MaterialPropertyValueService;
import com.casic.cloud.dao.config.material.MaterialDao;
import com.casic.cloud.dao.config.materialPropertyValue.MaterialPropertyValueDao;

/**
 *<pre>
 * 对象功能:CLOUD_MATERIAL Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 15:38:14
 *</pre>
 */
@Service
public class MaterialService extends BaseService<Material>
{
	@Resource
	private MaterialDao dao;
	@Resource
	private MaterialPropertyValueDao materialPropertyValueDao;
	
	
	public MaterialService()
	{
	}
	
	@Override
	protected IEntityDao<Material, Long> getEntityDao() 
	{
		return dao;
	}
	
	private void delByPk(Long id){
		materialPropertyValueDao.delByMainId(id);
	}
	
	public void delByMaterial(Long mid){
		materialPropertyValueDao.delByMainId(mid);
	}
	
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public List<MaterialPropertyValue> getPropertyValueList(Long id) {
		return materialPropertyValueDao.getByMainId(id);
	}
	
	public List<Material> getByCatalogId(String sqlKey,QueryFilter queryFilter){
		return dao.getByCatalogId(sqlKey, queryFilter);
	}
	
	public int changeToAdd(String sqlKey,Object params){
		return dao.changeToAdd(sqlKey, params);
	}
	public int changeToOff(String sqlKey,Object params){
		return dao.changeToOff(sqlKey, params);
	}
}
