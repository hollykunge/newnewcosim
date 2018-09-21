package com.hotent.platform.service.mobile;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.mobile.MobileUserInfo;
import com.hotent.platform.dao.mobile.MobileUserInfoDao;

/**
 *<pre>
 * 对象功能:MOBILE_USER_INFO Service类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2013-01-06 11:55:09
 *</pre>
 */
@Service
public class MobileUserInfoService extends BaseService<MobileUserInfo>
{
	@Resource
	private MobileUserInfoDao dao;
	
	public MobileUserInfoService()
	{
	}
	
	@Override
	protected IEntityDao<MobileUserInfo, Long> getEntityDao() 
	{
		return dao;
	}
	
}
