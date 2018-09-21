package com.casic.cloud.service.config.businessMarketingchase;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.business.BusinessChance;
import com.casic.cloud.model.config.businessDevchase.BusinessDevchase;
import com.casic.cloud.model.config.businessMarketingchase.BusinessMarketingchase;
import com.casic.cloud.dao.config.businessMarketingchase.BusinessMarketingchaseDao;

/**
 *<pre>
 * 对象功能:cloud_business_marketingchase Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-02 18:07:15
 *</pre>
 */
@Service
public class BusinessMarketingchaseService extends BaseService<BusinessMarketingchase>
{
	@Resource
	private BusinessMarketingchaseDao dao;
	
	
	
	public BusinessMarketingchaseService()
	{
	}
	
	@Override
	protected IEntityDao<BusinessMarketingchase, Long> getEntityDao() 
	{
		return dao;
	}
	public List<BusinessMarketingchase> getAllByEntid(String sqlKey,QueryFilter queryFilter) {
		return dao.getAllByEntid( sqlKey, queryFilter);
	}
	public List<BusinessMarketingchase> getMyChase(String sqlKey,QueryFilter queryFilter) {
		return dao.getMyChase( sqlKey, queryFilter);
	}
	
	
}
