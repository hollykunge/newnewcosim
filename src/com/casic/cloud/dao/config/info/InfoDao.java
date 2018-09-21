package com.casic.cloud.dao.config.info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.casic.cloud.model.config.info.Info;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
/**
 *<pre>
 * 对象功能:sys_org_info Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-17 19:28:40
 *</pre>
 */
@Repository
public class InfoDao extends BaseDao<Info>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Info.class;
	}

	public List<Info> getLastInfo() {
		// TODO Auto-generated method stub
		return this.getBySqlKey("getLastInfo");
	}
	
	
	public List<Info> getChildren(String sqlKey,Map<String,Object> params){
		return this.getBySqlKey(sqlKey, params);
		
	}
	
	public List<Info> getAllInfos(String sqlKey,QueryFilter queryFilter){
		return this.getBySqlKey(sqlKey, queryFilter);
	}
	
	/**
	 * 根据邮箱获取企业
	 * @param email
	 * @return
	 */
	public List<Info> getAllByEmail(String email){
		Map map = new HashMap();
		map.put("email", email);
		return this.getBySqlKey("getAllbyEmail", map);
	}
}