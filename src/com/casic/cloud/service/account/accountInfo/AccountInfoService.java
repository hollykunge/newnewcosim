package com.casic.cloud.service.account.accountInfo;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.account.accountInfo.AccountInfo;
import com.casic.cloud.dao.account.accountInfo.AccountInfoDao;

/**
 *<pre>
 * 对象功能:cloud_account_info Service类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-09-05 10:22:50
 *</pre>
 */
@Service
public class AccountInfoService extends BaseService<AccountInfo>
{
	@Resource
	private AccountInfoDao dao;
	
	
	
	public AccountInfoService()
	{
	}
	
	@Override
	protected IEntityDao<AccountInfo, Long> getEntityDao() 
	{
		return dao;
	}
	
	
}
