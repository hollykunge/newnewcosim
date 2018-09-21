package com.casic.cloud.dao.cloudUseRes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.casic.cloud.model.cloudUseRes.CloudUseRes;
/**
 *<pre>
 * 对象功能:cloud_use_res Dao类
 * 开发公司:tianzhi
 * 开发人员:xingchi
 * 创建时间:2013-05-16 17:40:22
 *</pre>
 */
@Repository
public class CloudUseResDao extends BaseDao<CloudUseRes>
{
	@Override
	public Class<?> getEntityClass()
	{
		return CloudUseRes.class;
	}
	
	public List<CloudUseRes> getUseRes(Long id){
		Map params=new HashMap();
		params.put("entid", id);
		return getBySqlKey("getUseRes", params);
	}

}