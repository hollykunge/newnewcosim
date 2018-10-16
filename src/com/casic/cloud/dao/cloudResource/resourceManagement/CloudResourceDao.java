package com.casic.cloud.dao.cloudResource.resourceManagement;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.cloudResource.resourceManagement.CloudResource;
/**
 *<pre>
 * 对象功能:cloud_resource_class Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-13 18:05:09
 *</pre>
 */
@Repository
public class CloudResourceDao extends BaseDao<CloudResource>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CloudResource.class;
	}

}