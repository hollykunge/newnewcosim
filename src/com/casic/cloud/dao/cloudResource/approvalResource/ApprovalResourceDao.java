
package com.casic.cloud.dao.cloudResource.approvalResource;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.cloudResource.approvalResource.ApprovalResource;
/**
 *<pre>
 * 对象功能:cloud_resource_approval Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-27 10:27:37
 *</pre>
 */
@Repository
public class ApprovalResourceDao extends BaseDao<ApprovalResource>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ApprovalResource.class;
	}

}