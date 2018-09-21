package com.casic.cloud.dao.crowdsourcing.crowdsourcingResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponse;
/**
 *<pre>
 * 对象功能:cloud_crowdsourcing_response Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:13:50
 *</pre>
 */
@Repository
public class CrowdsourcingResponseDao extends BaseDao<CrowdsourcingResponse>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CrowdsourcingResponse.class;
	}
	public List<CrowdsourcingResponse> getBySourceId(Long sourceformCrowdsourcingId){
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("sourceformCrowdsourcingId", sourceformCrowdsourcingId);
		return getBySqlKey("getfromSourceId", params);
	}
}