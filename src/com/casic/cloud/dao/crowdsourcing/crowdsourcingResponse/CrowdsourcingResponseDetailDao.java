package com.casic.cloud.dao.crowdsourcing.crowdsourcingResponse;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResponse.CrowdsourcingResponseDetail;
/**
 *<pre>
 * 对象功能:cloud_crowdsourcing_response_detail Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:13:50
 *</pre>
 */
@Repository
public class CrowdsourcingResponseDetailDao extends BaseDao<CrowdsourcingResponseDetail>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CrowdsourcingResponseDetail.class;
	}

	public List<CrowdsourcingResponseDetail> getByMainId(Long id) {
		return this.getBySqlKey("getCrowdsourcingResponseDetailList", id);
	}
	
	public void delByMainId(Long id) {
		this.delBySqlKey("delByMainId", id);
	}
}