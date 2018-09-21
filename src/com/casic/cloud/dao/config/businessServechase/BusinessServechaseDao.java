package com.casic.cloud.dao.config.businessServechase;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.businessMarketingchase.BusinessMarketingchase;
import com.casic.cloud.model.config.businessPurchase.BusinessPurchase;
import com.casic.cloud.model.config.businessServechase.BusinessServechase;
/**
 *<pre>
 * 对象功能:cloud_business_servechase Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-03 11:13:52
 *</pre>
 */
@Repository
public class BusinessServechaseDao extends BaseDao<BusinessServechase>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BusinessServechase.class;
	}
	public List<BusinessServechase> getAllByEntid(String sqlKey,QueryFilter queryFilter) {
		return  this.getBySqlKey( sqlKey, queryFilter);
	}

	public List<BusinessServechase> getMyChase(String sqlKey,QueryFilter queryFilter) {
		return  this.getBySqlKey( sqlKey, queryFilter);
	}
}