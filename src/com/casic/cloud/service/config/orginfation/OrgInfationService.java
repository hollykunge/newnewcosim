package com.casic.cloud.service.config.orginfation;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.config.orginfation.OrgInfation;
import com.casic.cloud.dao.config.orginfation.OrgInfationDao;

/**
 *<pre>
 * 对象功能:sys_org_info Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 18:08:13
 *</pre>
 */
@Service
public class OrgInfationService extends BaseService<OrgInfation>
{
	@Resource
	private OrgInfationDao dao;
	
	
	
	public OrgInfationService()
	{
	}
	
	@Override
	protected IEntityDao<OrgInfation, Long> getEntityDao() 
	{
		return dao;
	}
	
	
}
