package com.casic.cloud.service.console.businessAreaGroup;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.console.businessAreaGroup.BusinessAreaGroup;
import com.casic.cloud.dao.console.businessAreaGroup.BusinessAreaGroupDao;

/**
 *<pre>
 * 对象功能:cloud_business_area_group Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-05 13:24:43
 *</pre>
 */
@Service
public class BusinessAreaGroupService extends BaseService<BusinessAreaGroup>
{
	@Resource
	private BusinessAreaGroupDao dao;
	
	
	
	public BusinessAreaGroupService()
	{
	}
	
	@Override
	protected IEntityDao<BusinessAreaGroup, Long> getEntityDao() 
	{
		return dao;
	}
	/**
	 * 获取所有商圈分组
	 * @return
	 */
	public List<BusinessAreaGroup> getAllByEntid(Map<String, Object> m){
		return dao.getAllByEntid(m);
		 
	}
	
}
