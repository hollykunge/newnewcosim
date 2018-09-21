package com.casic.cloud.service.config.interestStrategy;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.config.interestStrategy.InterestStrategy;
import com.casic.cloud.dao.config.interestStrategy.InterestStrategyDao;
import com.casic.cloud.model.config.interestStrategy.InterestStrategyDetail;
import com.casic.cloud.model.config.priceStrategy.PriceStrategy;
import com.casic.cloud.dao.config.interestStrategy.InterestStrategyDetailDao;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.webservice.api.ProcessService;

/**
 *<pre>
 * 对象功能:cloud_interest_strategy Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-09-24 17:29:56
 *</pre>
 */
@Service
public class InterestStrategyService extends BaseService<InterestStrategy>
{
	@Resource
	private InterestStrategyDao dao;
	
	@Resource
	private InterestStrategyDetailDao interestStrategyDetailDao;
	
	@Resource
	private ProcessService processService;
	
	public InterestStrategyService()
	{
	}
	
	@Override
	protected IEntityDao<InterestStrategy, Long> getEntityDao() 
	{
		return dao;
	}
	
	private void delByPk(Long id){
		interestStrategyDetailDao.delByMainId(id);
	}
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public void addAll(InterestStrategy interestStrategy) throws Exception{
		add(interestStrategy);
		addSubList(interestStrategy);
	}
	
	public void updateAll(InterestStrategy interestStrategy) throws Exception{
		update(interestStrategy);
		delByPk(interestStrategy.getId());
		addSubList(interestStrategy);
	}
	
	public void addSubList(InterestStrategy interestStrategy) throws Exception{
		List<InterestStrategyDetail> interestStrategyDetailList=interestStrategy.getInterestStrategyDetailList();
		if(BeanUtils.isNotEmpty(interestStrategyDetailList)){
			for(InterestStrategyDetail interestStrategyDetail:interestStrategyDetailList){
				interestStrategyDetail.setStrategyId(interestStrategy.getId());
				interestStrategyDetail.setId(UniqueIdUtil.genId());
				interestStrategyDetailDao.add(interestStrategyDetail);
			}
		}
	}
	
	public List<InterestStrategyDetail> getInterestStrategyDetailList(Long id) {
		return interestStrategyDetailDao.getByMainId(id);
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
	public List<InterestStrategy> checkExist(Map<String,Object> params){
		return dao.checkExist(params);
		
	}
}
