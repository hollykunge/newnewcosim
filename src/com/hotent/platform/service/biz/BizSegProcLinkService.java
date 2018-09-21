package com.hotent.platform.service.biz;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.biz.BizSegProcLink;
import com.hotent.platform.dao.biz.BizSegProcLinkDao;

/**
 *<pre>
 * 对象功能:BIZ_SEG_PROC_LINK Service类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 *</pre>
 */
@Service
public class BizSegProcLinkService extends BaseService<BizSegProcLink>
{
	@Resource
	private BizSegProcLinkDao dao;
	
	
	
	public BizSegProcLinkService()
	{
	}
	
	@Override
	protected IEntityDao<BizSegProcLink, Long> getEntityDao() 
	{
		return dao;
	}
	
	
}
