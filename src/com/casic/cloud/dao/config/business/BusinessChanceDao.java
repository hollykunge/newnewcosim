package com.casic.cloud.dao.config.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.casic.cloud.model.config.business.BusinessChance;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
/**
 *<pre>
 * 对象功能:cloud_business_chance Dao类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:21:17
 *</pre>
 */
@Repository
public class BusinessChanceDao extends BaseDao<BusinessChance>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BusinessChance.class;
	}

	public List<BusinessChance> getType(String sqlKey,QueryFilter queryFilter) {
		return this.getBySqlKey(sqlKey,queryFilter);
	}

	public List<BusinessChance> getByIdAnd(Map<String, Object> m) {
		// TODO Auto-generated method stub
		return  this.getBySqlKey("getByIdAnd",m);
	}
	
	public List<BusinessChance> getAllByType(String sqlKey,QueryFilter queryFilter) {
		return this.getBySqlKey(sqlKey,queryFilter);
	}
	
	public List<BusinessChance> getAll_query(String sqlKey,QueryFilter queryFilter){
		return this.getBySqlKey(sqlKey,queryFilter);
	}
	
	/**
	 * 获取所有商机数量
	 * @return
	 */
	public Integer getAllChanceCount(){
		Integer count  = (Integer) getOne("getAllChangeCount",null);
		return count;
	}
	
	/**
	 * 获取协同采购商机数量
	 * @param beginTime
	 * @param endTime
	 * @param compId
	 * @return
	 */
	public Integer getPurchaseBusinessCount(Date beginTime,Date endTime,Long compId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginTime", beginTime);
		params.put("entTime", endTime);
		params.put("compId", compId);
		
		Integer count  = (Integer) getOne("getPurchaseBusinessCount",params);
		return count;
	}
	
	/**
	 * 获取协同营销业务数量
	 * @param beginTime
	 * @param endTime
	 * @param compId
	 * @return
	 */
	public Integer getSaleBusinessCount(Date beginTime,Date endTime,Long compId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginTime", beginTime);
		params.put("entTime", endTime);
		params.put("compId", compId);
		
		Integer count  = (Integer) getOne("getSaleBusinessCount",params);
		return count;
	}
	
	/**
	 * 获取协同业务商机数量
	 * @param beginTime
	 * @param endTime
	 * @param compId
	 * @return
	 */
	public Integer getManuBusinessCount(Date beginTime,Date endTime,Long compId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginTime", beginTime);
		params.put("entTime", endTime);
		params.put("compId", compId);
		
		Integer count  = (Integer) getOne("getManuBusinessCount",params);
		return count;
	}
	
	/**
	 * 获取协同研发业务数量
	 * @param beginTime
	 * @param endTime
	 * @param compId
	 * @return
	 */
	public Integer getResearchBusinessCount(Date beginTime,Date endTime,Long compId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginTime", beginTime);
		params.put("entTime", endTime);
		params.put("compId", compId);
		
		Integer count  = (Integer) getOne("getResearchBusinessCount",params);
		return count;
	}
	
	/**
	 * 获取协同服务业务数量
	 * @param beginTime
	 * @param endTime
	 * @param compId
	 * @return
	 */
	public Integer getServiceBusinessCount(Date beginTime,Date endTime,Long compId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginTime", beginTime);
		params.put("entTime", endTime);
		params.put("compId", compId);
		
		Integer count  = (Integer) getOne("getServiceBusinessCount",params);
		return count;
	}
}