package com.casic.cloud.service.config.business;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.casic.cloud.model.config.business.BusinessChance;
import com.casic.cloud.model.config.capability.Capability;
import com.casic.cloud.pub.util.MonthUtil;
import com.casic.cloud.dao.config.business.BusinessChanceDao;

/**
 *<pre>
 * 对象功能:cloud_business_chance Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-04-16 17:21:17
 *</pre>
 */
@Service
public class BusinessChanceService extends BaseService<BusinessChance>
{
	@Resource
	private BusinessChanceDao dao;
	
	
	
	public BusinessChanceService()
	{
	}
	
	@Override
	protected IEntityDao<BusinessChance, Long> getEntityDao() 
	{
		return dao;
	}

	public List<BusinessChance> getType(String sqlKey,QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		return dao.getType(sqlKey, queryFilter);
	}
	
	public List<BusinessChance> getAllBusinessChance(String sqlKey,QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		return dao.getType(sqlKey, queryFilter);
	}

	public List<BusinessChance> getByIdAnd(Map<String, Object> m) {
		return dao.getByIdAnd(m);
	}
	
	public List<BusinessChance> getAllByType(String sqlKey,QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		return dao.getAllByType(sqlKey, queryFilter);
	}
	public List<BusinessChance> getAll_query(String sqlKey,QueryFilter queryFilter){
		return dao.getAll_query(sqlKey,queryFilter);
	}

	/**
	 * 获取所有商机数量
	 * @return
	 */
	public Integer getAllChanceCount(){
		return dao.getAllChanceCount();
	}
	
	/**
	 * 获取所有业务数量
	 * @return
	 */
	public Integer getAllBusinessCount(){
		int count = this.getPurchaseBusinessCount(null, null, null) +
		this.getSaleBusinessCount(null, null, null) +
		this.getManuBusinessCount(null, null, null) + 
		this.getResearchBusinessCount(null, null, null) +
		this.getServiceBusinessCount(null, null, null);
		return count;
	}
	
	
	/**
	 * 成本分析图
	 * @param startDate
	 * @param endDate
	 * @param faultType
	 * @return
	 */
	public Map<String, Integer> reportYwlCost(Date startDate,Date endDate,Long compId){
	 
		
		//按月度汇总
		Map<String, Integer> costCountByMonth = new HashMap<String, Integer>();
		//初始化Map
		for(int i=1; i<13; i++)
			costCountByMonth.put(MonthUtil.getByInteger(i), 0);
		
	 
			 
				 
				Calendar calendarOperate = Calendar.getInstance();
				
				int month = calendarOperate.get(Calendar.MONTH) + 1;
				 
					 
						int sum = this.getPurchaseBusinessCount(startDate, endDate,compId) +
								this.getSaleBusinessCount(startDate, endDate,compId) +
								this.getManuBusinessCount(startDate, endDate,compId) + 
								this.getResearchBusinessCount(startDate, endDate,compId) +
								this.getServiceBusinessCount(startDate, endDate,compId);
					    costCountByMonth.put(MonthUtil.getByInteger(month), costCountByMonth.get(MonthUtil.getByInteger(month))+sum);
					 
				 
			 
		 
		
		return costCountByMonth;
	}
	
	/**
	 * 获取协同采购业务数量
	 * @param beginTime
	 * @param endTime
	 * @param compId
	 * @return
	 */
	public Integer getPurchaseBusinessCount(Date beginTime,Date endTime,Long compId){
		return dao.getPurchaseBusinessCount(beginTime, endTime, compId);
	}
	
	/**
	 * 获取协同销售业务数量
	 * @param beginTime
	 * @param endTime
	 * @param compId
	 * @return
	 */
	public Integer getSaleBusinessCount(Date beginTime,Date endTime,Long compId){
		return dao.getSaleBusinessCount(beginTime, endTime, compId);
	}
	
	/**
	 * 获取协同生产业务数量
	 * @param beginTime
	 * @param endTime
	 * @param compId
	 * @return
	 */
	public Integer getManuBusinessCount(Date beginTime,Date endTime,Long compId){
		return dao.getManuBusinessCount(beginTime, endTime, compId);
	}
	
	/**
	 * 获取协同服务业务数量
	 * @param beginTime
	 * @param endTime
	 * @param compId
	 * @return
	 */
	public Integer getServiceBusinessCount(Date beginTime,Date endTime,Long compId){
		return dao.getServiceBusinessCount(beginTime, endTime, compId);
	}
	
	/**
	 * 获取协同研发业务数量
	 * @param beginTime
	 * @param endTime
	 * @param compId
	 * @return
	 */
	public Integer getResearchBusinessCount(Date beginTime,Date endTime,Long compId){
		return dao.getResearchBusinessCount(beginTime, endTime, compId);
	}
}
