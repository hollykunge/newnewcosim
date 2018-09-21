package com.casic.cloud.dao.console.businessAreaGroup;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.console.businessAreaGroup.BusinessAreaGroup;
/**
 *<pre>
 * 对象功能:cloud_business_area_group Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-05 13:24:43
 *</pre>
 */
@Repository
public class BusinessAreaGroupDao extends BaseDao<BusinessAreaGroup>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BusinessAreaGroup.class;
	}
	
	/**
	 * 获取所有商圈分组
	 * @return
	 */
	public List<BusinessAreaGroup> getAllByEntid(Map<String, Object> m){
		return this.getBySqlKey("getAllByEntid",m);
		 
	}

}