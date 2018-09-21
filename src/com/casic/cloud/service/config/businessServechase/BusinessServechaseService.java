package com.casic.cloud.service.config.businessServechase;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.businessMarketingchase.BusinessMarketingchase;
import com.casic.cloud.model.config.businessPurchase.BusinessPurchase;
import com.casic.cloud.model.config.businessServechase.BusinessServechase;
import com.casic.cloud.dao.config.businessServechase.BusinessServechaseDao;

/**
 *<pre>
 * 对象功能:cloud_business_servechase Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-03 11:13:52
 *</pre>
 */
@Service
public class BusinessServechaseService extends BaseService<BusinessServechase>
{
	@Resource
	private BusinessServechaseDao dao;
	
	
	
	public BusinessServechaseService()
	{
	}
	
	@Override
	protected IEntityDao<BusinessServechase, Long> getEntityDao() 
	{
		return dao;
	}
	public List<BusinessServechase> getAllByEntid(String sqlKey,QueryFilter queryFilter) {
		return dao.getAllByEntid( sqlKey, queryFilter);
	}
	
	public List<BusinessServechase> getMyChase(String sqlKey,QueryFilter queryFilter) {
		return dao.getMyChase( sqlKey, queryFilter);
	}
	
	
}
