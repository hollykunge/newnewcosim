package com.casic.cloud.dao.config.businessDevchase;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.business.BusinessChance;
import com.casic.cloud.model.config.businessDevchase.BusinessDevchase;
import com.casic.cloud.model.config.businessMarketingchase.BusinessMarketingchase;
/**
 *<pre>
 * 对象功能:cloud_business_devchase Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-03 11:14:28
 *</pre>
 */
@Repository
public class BusinessDevchaseDao extends BaseDao<BusinessDevchase>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BusinessDevchase.class;
	}

	public List<BusinessDevchase> getAllByEntid(String sqlKey,QueryFilter queryFilter) {
		return  this.getBySqlKey( sqlKey, queryFilter);
	}
	
	public List<BusinessDevchase> getMyChase(String sqlKey,QueryFilter queryFilter) {
		return  this.getBySqlKey( sqlKey, queryFilter);
	}
	
	
	 
	public  List<BusinessDevchase>  getByIdAnd(Map<String, Object> m) {
		return  this.getBySqlKey("getByIdAnd",m);
	}
	public List<BusinessDevchase> getAllByType(String sqlKey,QueryFilter queryFilter) {
		return this.getBySqlKey(sqlKey,queryFilter);
	}
}