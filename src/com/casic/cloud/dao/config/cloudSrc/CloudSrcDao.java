package com.casic.cloud.dao.config.cloudSrc;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.businessDevchase.BusinessDevchase;
import com.casic.cloud.model.config.cloudSrc.CloudSrc;
/**
 *<pre>
 * 对象功能:cloud_src Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-07 17:41:47
 *</pre>
 */
@Repository
public class CloudSrcDao extends BaseDao<CloudSrc>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CloudSrc.class;
	}
	public List<CloudSrc> getCloudSrc(String sqlKey,QueryFilter queryFilter) {
		return  this.getBySqlKey( sqlKey, queryFilter);
	}
	
}