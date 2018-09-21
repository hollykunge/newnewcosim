package com.casic.cloud.service.crowdsourcing.crowdsourcingRequire;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequire;
import com.casic.cloud.dao.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequireDao;
import com.casic.cloud.model.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequireDetail;
import com.casic.cloud.dao.crowdsourcing.crowdsourcingRequire.CrowdsourcingRequireDetailDao;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.webservice.api.ProcessService;

/**
 *<pre>
 * 对象功能:cloud_crowdsourcing_require Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:06:02
 *</pre>
 */
@Service
public class CrowdsourcingRequireService extends BaseService<CrowdsourcingRequire>
{
	@Resource
	private CrowdsourcingRequireDao dao;
	
	@Resource
	private CrowdsourcingRequireDetailDao crowdsourcingRequireDetailDao;
	
	@Resource
	private ProcessService processService;
	
	@Resource
	private ProcessEngine processEngine;
	
	public CrowdsourcingRequireService()
	{
	}
	
	@Override
	protected IEntityDao<CrowdsourcingRequire, Long> getEntityDao() 
	{
		return dao;
	}
	
	private void delByPk(Long id){
		crowdsourcingRequireDetailDao.delByMainId(id);
	}
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public void addAll(CrowdsourcingRequire crowdsourcingRequire) throws Exception{
		ProcessCmd processCmd = new ProcessCmd();
		//设置下一任务的多个执行人
		processCmd.setFlowKey("crowdresearch1");
		processCmd.setSubject("研发众包模式【" + crowdsourcingRequire.getCode() + "】");
	
		List<TaskExecutor> executors=new ArrayList<TaskExecutor>();
	
		//executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_COMP,crowdsourcingRequire.getEnterpriseId().toString(),"企业"));
		
		//executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_ROLE,"10000009600071",""));
		
		String []users = crowdsourcingRequire.getInvitedUserIds().split(",");
		
		for(String user:users){
			executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_USER,user,""));
		}
		
		processCmd.setTaskExecutors(executors);
		
		Map<String,Object> vars=new HashMap<String,Object>();	
		vars.put("id", crowdsourcingRequire.getId().toString());		
		//设置流程变量
		processCmd.setVariables(vars);		
		processCmd.setBusinessKey(crowdsourcingRequire.getId().toString());
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		ProcessRun processRun=processService.start(processCmd);
		crowdsourcingRequire.setRunId(processRun.getRunId());
		add(crowdsourcingRequire);
		addSubList(crowdsourcingRequire);
	}
	
	public void updateAll(CrowdsourcingRequire crowdsourcingRequire) throws Exception{
		update(crowdsourcingRequire);
		delByPk(crowdsourcingRequire.getId());
		addSubList(crowdsourcingRequire);
	}
	
	public void addSubList(CrowdsourcingRequire crowdsourcingRequire) throws Exception{
		List<CrowdsourcingRequireDetail> crowdsourcingRequireDetailList=crowdsourcingRequire.getCrowdsourcingRequireDetailList();
		if(BeanUtils.isNotEmpty(crowdsourcingRequireDetailList)){
			for(CrowdsourcingRequireDetail crowdsourcingRequireDetail:crowdsourcingRequireDetailList){
				crowdsourcingRequireDetail.setRequireId(crowdsourcingRequire.getId());
				crowdsourcingRequireDetail.setId(UniqueIdUtil.genId());
				crowdsourcingRequireDetailDao.add(crowdsourcingRequireDetail);
			}
		}
	}
	
	public List<CrowdsourcingRequireDetail> getCrowdsourcingRequireDetailList(Long id) {
		return crowdsourcingRequireDetailDao.getByMainId(id);
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
	 * 置状态
	 * @param cmd
	 * @throws Exception
	 */
	public void  state(ProcessCmd cmd) throws Exception{
		CrowdsourcingRequire entity = this.getById(Long.parseLong(cmd.getBusinessKey()));
		if(entity!=null){
			//获取当前状态
			HistoricTaskInstance hti = processEngine.getHistoryService().createHistoricTaskInstanceQuery()
				.processInstanceId(cmd.getProcessRun().getActInstId())
				.unfinished()
				.singleResult();
				
			String state = "";
			
			//任务被追回
			if(hti==null){
				state = "已经结束";
			}else{
				state = hti.getName();
				if(cmd.isRecover()){
					state += "[追回]";
				}else if(cmd.isBack()==1){
					state += "[驳回]";
				}else if(cmd.isBack()==2){
					state += "[驳回到发起人]";
				}
			}
						
			entity.setStatus(state);
			this.update(entity);
		}
	}
}
