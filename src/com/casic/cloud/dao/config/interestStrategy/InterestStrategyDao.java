package com.casic.cloud.dao.config.interestStrategy;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.config.interestStrategy.InterestStrategy;
import com.casic.cloud.model.config.priceStrategy.PriceStrategy;
/**
 *<pre>
 * 对象功能:cloud_interest_strategy Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-09-24 17:29:56
 *</pre>
 */
@Repository
public class InterestStrategyDao extends BaseDao<InterestStrategy>
{
	@Override
	public Class<?> getEntityClass()
	{
		return InterestStrategy.class;
	}
	public List<InterestStrategy> checkExist(Map<String,Object> params){
		return this.getBySqlKey("checkExist",params);
	}
}