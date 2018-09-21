package com.hotent.platform.service.system;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.system.SysCustomPage;
import com.hotent.platform.dao.system.SysCustomPageDao;

/**
 * 对象功能:自定义页面 Service类
 * 开发公司:宏天
 * 开发人员:Raise
 * 创建时间:2012-11-05 09:07:38
 */
@Service
public class SysCustomPageService extends BaseService<SysCustomPage>
{
	@Resource
	private SysCustomPageDao dao;
	
	public SysCustomPageService()
	{
	}
	
	@Override
	protected IEntityDao<SysCustomPage, Long> getEntityDao() 
	{
		return dao;
	}
	
}
