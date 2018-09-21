package com.casic.cloud.dao.console.busiarea;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.mail.OutMailUserSeting;
import com.casic.cloud.model.console.busiarea.Busiarea;
/**
 *<pre>
 * 对象功能:CLOUD_BUSINESS_AREA Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:xingchi
 * 创建时间:2013-04-17 21:23:49
 *</pre>
 */
@Repository
public class BusiareaDao extends BaseDao<Busiarea>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Busiarea.class;
	}
	
	/**
	 * 根据当前用户ID获得外商友列表
	 * @param queryFilter
	 * @return
	 */
	public List<Busiarea> getAll(QueryFilter queryFilter) {
		return this.getBySqlKey("getAll", queryFilter);
	}
	
	
	/**
	 *  获取当前用户的未分组商友分页列表
	 * @param queryFilter
	 * @return
	 */
	public List<Busiarea> getAllNoGroup(QueryFilter queryFilter) {
		return this.getBySqlKey("getAllNoGroup", queryFilter);
	}
	
	public List<Busiarea> getByMainEntId(Long main_ent){
		Map params=new HashMap();
		params.put("main_ent", main_ent);
		return getBySqlKey("getByMainEntId", params);
	}	
	
	
	public List<Busiarea> getByGroupId(QueryFilter queryFilter){
		return this.getBySqlKey("getByGroupId", queryFilter);
		 
	}	
	

}