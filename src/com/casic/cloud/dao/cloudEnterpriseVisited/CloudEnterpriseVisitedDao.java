package com.casic.cloud.dao.cloudEnterpriseVisited;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.cloudEnterpriseVisited.CloudEnterpriseVisited;
import com.casic.cloud.model.console.busiarea.Busiarea;
/**
 *<pre>
 * 对象功能:cloud_enterprise_visited Dao类
 * 开发公司:tianzhi
 * 开发人员:xingchi
 * 创建时间:2013-05-03 10:34:39
 *</pre>
 */
@Repository
public class CloudEnterpriseVisitedDao extends BaseDao<CloudEnterpriseVisited>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CloudEnterpriseVisited.class;
	}
	
	public List<CloudEnterpriseVisited> getByInterventId(Long intervent_id){
		Map params=new HashMap();
		params.put("intervent_id", intervent_id);
		return getBySqlKey("getByInterventId", params);
	}	
	
	

}