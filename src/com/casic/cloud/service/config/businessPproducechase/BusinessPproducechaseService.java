package com.casic.cloud.service.config.businessPproducechase;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.businessMarketingchase.BusinessMarketingchase;
import com.casic.cloud.model.config.businessPproducechase.BusinessPproducechase;
import com.casic.cloud.dao.config.businessPproducechase.BusinessPproducechaseDao;

/**
 *<pre>
 * 对象功能:cloud_business_producechase Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-03 11:13:09
 *</pre>
 */
@Service
public class BusinessPproducechaseService extends BaseService<BusinessPproducechase>
{
	@Resource
	private BusinessPproducechaseDao dao;
	
	
	
	public BusinessPproducechaseService()
	{
	}
	
	@Override
	protected IEntityDao<BusinessPproducechase, Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<BusinessPproducechase> getAllByEntid(String sqlKey,QueryFilter queryFilter) {
		return dao.getAllByEntid( sqlKey, queryFilter);
	}
	public List<BusinessPproducechase> getMyChase(String sqlKey,QueryFilter queryFilter) {
		return dao.getMyChase( sqlKey, queryFilter);
	}
	
}
