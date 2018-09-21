package com.casic.cloud.dao.cloudResource.resourceManagement;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.cloudResource.resourceManagement.CloudResourceInstance;
/**
 *<pre>
 * 对象功能:cloud_resource_instance Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-06-13 18:05:09
 *</pre>
 */
@Repository
public class CloudResourceInstanceDao extends BaseDao<CloudResourceInstance>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CloudResourceInstance.class;
	}

	public List<CloudResourceInstance> getByMainId(Long classId) {
		return this.getBySqlKey("getCloudResourceInstanceList", classId);
	}
	public List<CloudResourceInstance> getAllInstance(QueryFilter queryFilter) {
		return this.getBySqlKey("getAllResourceInstanceList", queryFilter);
	}
	public void delByMainId(Long classId) {
		this.delBySqlKey("delByMainId", classId);
	}
}