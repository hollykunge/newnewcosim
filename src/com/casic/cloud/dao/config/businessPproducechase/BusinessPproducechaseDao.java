package com.casic.cloud.dao.config.businessPproducechase;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.businessMarketingchase.BusinessMarketingchase;
import com.casic.cloud.model.config.businessPproducechase.BusinessPproducechase;
/**
 *<pre>
 * 对象功能:cloud_business_producechase Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-03 11:13:09
 *</pre>
 */
@Repository
public class BusinessPproducechaseDao extends BaseDao<BusinessPproducechase>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BusinessPproducechase.class;
	}
	public List<BusinessPproducechase> getAllByEntid(String sqlKey,QueryFilter queryFilter) {
		return  this.getBySqlKey( sqlKey, queryFilter);
	}
	
	public List<BusinessPproducechase> getMyChase(String sqlKey,QueryFilter queryFilter) {
		return  this.getBySqlKey( sqlKey, queryFilter);
	}

}