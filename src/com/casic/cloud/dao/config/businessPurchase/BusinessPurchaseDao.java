package com.casic.cloud.dao.config.businessPurchase;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.businessMarketingchase.BusinessMarketingchase;
import com.casic.cloud.model.config.businessPproducechase.BusinessPproducechase;
import com.casic.cloud.model.config.businessPurchase.BusinessPurchase;
/**
 *<pre>
 * 对象功能:cloud_business_purchase Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-02 13:43:17
 *</pre>
 */
@Repository
public class BusinessPurchaseDao extends BaseDao<BusinessPurchase>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BusinessPurchase.class;
	}
	public List<BusinessPurchase> getAllByEntid(String sqlKey,QueryFilter queryFilter) {
		return  this.getBySqlKey( sqlKey, queryFilter);
	}
	public List<BusinessPurchase> getMyChase(String sqlKey,QueryFilter queryFilter) {
		return  this.getBySqlKey( sqlKey, queryFilter);
	}

}