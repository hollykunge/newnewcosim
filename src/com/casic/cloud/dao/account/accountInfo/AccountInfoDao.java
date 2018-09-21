package com.casic.cloud.dao.account.accountInfo;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.account.accountInfo.AccountInfo;
/**
 *<pre>
 * 对象功能:cloud_account_info Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-09-05 10:22:50
 *</pre>
 */
@Repository
public class AccountInfoDao extends BaseDao<AccountInfo>
{
	@Override
	public Class<?> getEntityClass()
	{
		return AccountInfo.class;
	}

}