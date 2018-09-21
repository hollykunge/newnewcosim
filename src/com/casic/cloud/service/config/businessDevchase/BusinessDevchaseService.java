package com.casic.cloud.service.config.businessDevchase;

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
import com.casic.cloud.dao.config.businessDevchase.BusinessDevchaseDao;

/**
 *<pre>
 * 对象功能:cloud_business_devchase Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-05-03 11:14:28
 *</pre>
 */
@Service
public class BusinessDevchaseService extends BaseService<BusinessDevchase>
{
	@Resource
	private BusinessDevchaseDao dao;
	
	
	
	public BusinessDevchaseService()
	{
	}
	
	@Override
	protected IEntityDao<BusinessDevchase, Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<BusinessDevchase> getAllByEntid(String sqlKey,QueryFilter queryFilter) {
		return dao.getAllByEntid( sqlKey, queryFilter);
	}
	
	public List<BusinessDevchase> getMyChase(String sqlKey,QueryFilter queryFilter) {
		return dao.getMyChase( sqlKey, queryFilter);
	}
	
	
	
	public  List<BusinessDevchase>  getByIdAnd(Map<String, Object> m) {
		return dao.getByIdAnd(m);
	}
	public List<BusinessDevchase> getAllByType(String sqlKey,QueryFilter queryFilter) {
		return dao.getBySqlKey(sqlKey,queryFilter);
	}
}
