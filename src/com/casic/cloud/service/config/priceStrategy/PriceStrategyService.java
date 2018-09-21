package com.casic.cloud.service.config.priceStrategy;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.config.capabilityClass.CapabilityClass;
import com.casic.cloud.model.config.priceStrategy.PriceStrategy;
import com.casic.cloud.dao.config.priceStrategy.PriceStrategyDao;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.webservice.api.ProcessService;

/**
 *<pre>
 * 对象功能:cloud_price_strategy Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-09-11 16:18:27
 *</pre>
 */
@Service
public class PriceStrategyService extends BaseService<PriceStrategy>
{
	@Resource
	private PriceStrategyDao dao;
	
	
	@Resource
	private ProcessService processService;
	
	public PriceStrategyService()
	{
	}
	
	@Override
	protected IEntityDao<PriceStrategy, Long> getEntityDao() 
	{
		return dao;
	}
	
	
	/**
	 * 设置 cmd 启动流程
	 * @param defKey 流程定义key
	 * @param businessKey  数据id
	 * @return
	 */
	public void setCmdtoStart(String defKey, String businessKey) throws Exception{
		ProcessCmd processCmd = new ProcessCmd();
		processCmd.setFlowKey(defKey);
		processCmd.setBusinessKey(businessKey);
		processService.start(processCmd);
	}
	
	/**
	 * 设置 cmd 完成任务
	 * @param taskId 任务id
	 * @param voteAgree 流程动作
	 * @return
	 */
	public void setCmdtoComplete(String taskId, String voteAgree) throws Exception{
		ProcessCmd processCmd = new ProcessCmd();
		processCmd.setTaskId(taskId);
		processCmd.setVoteAgree(new Short(voteAgree));
		processService.doNext(processCmd);
	}
	
	/**
	 * 根据供应商ID，经销商id，物品ID查询折扣
	 * @param params
	 * @return
	 */
	public List<PriceStrategy> getDiscount(Map<String,Object> params){
		return dao.getDiscount(params);
		
	}
	
	/**
	 * 根据供应商ID，经销商id，物品ID查询折扣
	 * @param params
	 * @return
	 */
	public List<PriceStrategy> checkExist(Map<String,Object> params){
		return dao.checkExist(params);
		
	}
}
