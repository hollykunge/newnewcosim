package com.casic.cloud.dao.crowdsourcing.crowdsourcingRequire;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequire;
/**
 *<pre>
 * 对象功能:cloud_crowdsourcing_require Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:06:02
 *</pre>
 */
@Repository
public class CrowdsourcingRequireDao extends BaseDao<CrowdsourcingRequire>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CrowdsourcingRequire.class;
	}

}