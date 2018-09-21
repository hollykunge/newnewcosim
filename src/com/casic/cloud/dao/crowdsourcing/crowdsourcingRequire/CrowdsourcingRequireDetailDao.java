package com.casic.cloud.dao.crowdsourcing.crowdsourcingRequire;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.casic.cloud.model.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequireDetail;
/**
 *<pre>
 * 对象功能:cloud_crowdsourcing_require_detail Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:06:02
 *</pre>
 */
@Repository
public class CrowdsourcingRequireDetailDao extends BaseDao<CrowdsourcingRequireDetail>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CrowdsourcingRequireDetail.class;
	}

	public List<CrowdsourcingRequireDetail> getByMainId(Long id) {
		return this.getBySqlKey("getCrowdsourcingRequireDetailList", id);
	}
	
	public void delByMainId(Long id) {
		this.delBySqlKey("delByMainId", id);
	}
}