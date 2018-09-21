package com.casic.cloud.dao.config.businessMarketingchase;

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
 * 对象功能:cloud_business_marketingchase Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-02 18:07:15
 *</pre>
 */
@Repository
public class BusinessMarketingchaseDao extends BaseDao<BusinessMarketingchase>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BusinessMarketingchase.class;
	}
	public List<BusinessMarketingchase> getAllByEntid(String sqlKey,QueryFilter queryFilter) {
		return  this.getBySqlKey( sqlKey, queryFilter);
	}
	public List<BusinessMarketingchase> getMyChase(String sqlKey,QueryFilter queryFilter) {
		return  this.getBySqlKey( sqlKey, queryFilter);
	}

}