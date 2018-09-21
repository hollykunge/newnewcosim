package com.casic.cloud.service.cloudResource.approvalResource;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.cloudResource.approvalResource.ApprovalResource;
import com.casic.cloud.dao.cloudResource.approvalResource.ApprovalResourceDao;

/**
 *<pre>
 * 对象功能:cloud_resource_approval Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-27 10:27:37
 *</pre>
 */
@Service
public class ApprovalResourceService extends BaseService<ApprovalResource>
{
	@Resource
	private ApprovalResourceDao dao;
	
	
	
	public ApprovalResourceService()
	{
	}
	
	@Override
	protected IEntityDao<ApprovalResource, Long> getEntityDao() 
	{
		return dao;
	}
	
	
}
