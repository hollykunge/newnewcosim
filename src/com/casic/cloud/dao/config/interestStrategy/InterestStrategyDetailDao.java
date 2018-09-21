package com.casic.cloud.dao.config.interestStrategy;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.casic.cloud.model.config.interestStrategy.InterestStrategyDetail;
/**
 *<pre>
 * 对象功能:cloud_interest_strategy_detail Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-09-24 17:29:56
 *</pre>
 */
@Repository
public class InterestStrategyDetailDao extends BaseDao<InterestStrategyDetail>
{
	@Override
	public Class<?> getEntityClass()
	{
		return InterestStrategyDetail.class;
	}

	public List<InterestStrategyDetail> getByMainId(Long strategyId) {
		return this.getBySqlKey("getInterestStrategyDetailList", strategyId);
	}
	
	public void delByMainId(Long strategyId) {
		this.delBySqlKey("delByMainId", strategyId);
	}
}