package com.casic.cloud.dao.crowdsourcing.crowdsourcingAgreement;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.casic.cloud.model.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreementDetail;
/**
 *<pre>
 * 对象功能:cloud_crowdsourcing_agreement_detail Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 16:38:30
 *</pre>
 */
@Repository
public class CrowdsourcingAgreementDetailDao extends BaseDao<CrowdsourcingAgreementDetail>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CrowdsourcingAgreementDetail.class;
	}

	public List<CrowdsourcingAgreementDetail> getByMainId(Long id) {
		return this.getBySqlKey("getCrowdsourcingAgreementDetailList", id);
	}
	
	public void delByMainId(Long id) {
		this.delBySqlKey("delByMainId", id);
	}
}