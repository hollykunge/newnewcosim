package com.casic.cloud.dao.config.priceStrategy;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.config.priceStrategy.PriceStrategy;
/**
 *<pre>
 * 对象功能:cloud_price_strategy Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-09-11 16:18:27
 *</pre>
 */
@Repository
public class PriceStrategyDao extends BaseDao<PriceStrategy>
{
	@Override
	public Class<?> getEntityClass()
	{
		return PriceStrategy.class;
	}
	
	/**
	 * 根据供应商ID，经销商id，物品ID查询折扣
	 * @param params
	 * @return
	 */
	public List<PriceStrategy> getDiscount(Map<String,Object> params){
		return this.getBySqlKey("getDiscount",params);
		
	}
	public List<PriceStrategy> checkExist(Map<String,Object> params){
		return this.getBySqlKey("checkExist",params);
		
	}
}