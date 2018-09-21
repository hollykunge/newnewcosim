package com.casic.cloud.dao.crowdsourcing.crowdsourcingResult;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResult.CrowdsourcingResult;
/**
 *<pre>
 * 对象功能:cloud_crowdsourcing_result Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:15:19
 *</pre>
 */
@Repository
public class CrowdsourcingResultDao extends BaseDao<CrowdsourcingResult>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CrowdsourcingResult.class;
	}

}