package com.casic.cloud.dao.crowdsourcing.crowdsourcingAgreement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreement;
/**
 *<pre>
 * 对象功能:cloud_crowdsourcing_agreement Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 16:59:12
 *</pre>
 */
@Repository
public class CrowdsourcingAgreementDao extends BaseDao<CrowdsourcingAgreement>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CrowdsourcingAgreement.class;
	}

	
	public CrowdsourcingAgreement getBySourceId(Long sourceId) {
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("sourceformCrowdsourcingId", sourceId);
		return getUnique("getBySourceId", params);
	}
}