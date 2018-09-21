package com.casic.cloud.service.config.businessPurchase;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.businessMarketingchase.BusinessMarketingchase;
import com.casic.cloud.model.config.businessPproducechase.BusinessPproducechase;
import com.casic.cloud.model.config.businessPurchase.BusinessPurchase;
import com.casic.cloud.dao.config.businessPurchase.BusinessPurchaseDao;

/**
 *<pre>
 * 对象功能:cloud_business_purchase Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-02 13:43:17
 *</pre>
 */
@Service
public class BusinessPurchaseService extends BaseService<BusinessPurchase>
{
	@Resource
	private BusinessPurchaseDao dao;
	
	
	
	public BusinessPurchaseService()
	{
	}
	
	@Override
	protected IEntityDao<BusinessPurchase, Long> getEntityDao() 
	{
		return dao;
	}
	public List<BusinessPurchase> getAllByEntid(String sqlKey,QueryFilter queryFilter) {
		return dao.getAllByEntid( sqlKey, queryFilter);
	}
	
	public List<BusinessPurchase> getMyChase(String sqlKey,QueryFilter queryFilter) {
		return dao.getMyChase( sqlKey, queryFilter);
	}
	
}
