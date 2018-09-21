package com.casic.cloud.service.research.filecheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.stereotype.Service;

import org.activiti.engine.TaskService;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;

import com.casic.cloud.model.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreement;
import com.casic.cloud.model.crowdsourcing.crowdsourcingAgreement.CrowdsourcingAgreementDetail;
import com.casic.cloud.model.research.filecheck.ResFilecheck;
import com.casic.cloud.dao.research.filecheck.ResFilecheckDao;
import com.casic.cloud.model.research.filecheck.ResFilecheckDetail;
import com.casic.cloud.dao.research.filecheck.ResFilecheckDetailDao;
import com.casic.cloud.model.research.filecheck.ResFilecheckOpinion;
import com.casic.cloud.dao.research.filecheck.ResFilecheckOpinionDao;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;


import com.hotent.platform.webservice.api.ProcessService;

/**
 *<pre>
 * 对象功能:cloud_research_filecheck Service类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-21 16:04:50
 *</pre>
 */
@Service
public class ResFilecheckService extends BaseService<ResFilecheck>
{
	@Resource
	private ResFilecheckDao dao;
	
	@Resource
	private ResFilecheckDetailDao resFilecheckDetailDao;
	@Resource
	private ResFilecheckOpinionDao resFilecheckOpinionDao;
	
	@Resource
	private ProcessService processService;
	
	@Resource
	private TaskService taskService;
	

	
	public ResFilecheckService()
	{
	}
	
	@Override
	protected IEntityDao<ResFilecheck, Long> getEntityDao() 
	{
		return dao;
	}
	
	private void delByPk(Long id){
		resFilecheckDetailDao.delByMainId(id);
		resFilecheckOpinionDao.delByMainId(id);
	}
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public void addAll(ResFilecheck resFilecheck) throws Exception{
		
		ProcessCmd processCmd = new ProcessCmd();
		//设置下一任务的多个执行人
		processCmd.setFlowKey("checkFile");
		processCmd.setSubject("文档审签单【" + resFilecheck.getCode() + "】");

		String[] userIds = resFilecheck.getResFilecheckOpinionList().get(0).getCheckUserids().split(",");
		
		List<TaskExecutor> executors=new ArrayList<TaskExecutor>();
		for(String userId:userIds){
		    executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_USER, userId,"校对人员"));
		}

		processCmd.setTaskExecutors(executors);
		
		Map<String,Object> vars=new HashMap<String,Object>();
		processCmd.setVariables(vars);		
		processCmd.setBusinessKey(resFilecheck.getId().toString());			
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());		
		ProcessRun processRun=processService.start(processCmd);
		resFilecheck.setRunId(processRun.getRunId());

		add(resFilecheck);
		addSubList(resFilecheck);
	
	}
	

	
	public void updateAll(ResFilecheck resFilecheck) throws Exception{
		update(resFilecheck);
		delByPk(resFilecheck.getId());
		addSubList(resFilecheck);
	}
	
	public void addSubList(ResFilecheck resFilecheck) throws Exception{
		List<ResFilecheckDetail> resFilecheckDetailList=resFilecheck.getResFilecheckDetailList();
		if(BeanUtils.isNotEmpty(resFilecheckDetailList)){
			for(ResFilecheckDetail resFilecheckDetail:resFilecheckDetailList){
				resFilecheckDetail.setFilecheckId(resFilecheck.getId());
				resFilecheckDetail.setId(UniqueIdUtil.genId());
				resFilecheckDetailDao.add(resFilecheckDetail);
			}
		}
		List<ResFilecheckOpinion> resFilecheckOpinionList=resFilecheck.getResFilecheckOpinionList();
		if(BeanUtils.isNotEmpty(resFilecheckOpinionList)){
			for(ResFilecheckOpinion resFilecheckOpinion:resFilecheckOpinionList){
				resFilecheckOpinion.setFilecheckId(resFilecheck.getId());
				resFilecheckOpinion.setId(UniqueIdUtil.genId());
				resFilecheckOpinionDao.add(resFilecheckOpinion);
			}
		}
	}
	
	public List<ResFilecheckDetail> getResFilecheckDetailList(Long id) {
		return resFilecheckDetailDao.getByMainId(id);
	}
	public List<ResFilecheckOpinion> getResFilecheckOpinionList(Long id) {
		return resFilecheckOpinionDao.getByMainId(id);
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
	
	
	protected ResFilecheck getFormObject(ProcessCmd cmd) throws Exception {
		 net.sf.json.util.JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
	   	Map<String,String> maps=cmd.getFormDataMap();
	   	if(maps!=null){
	   		String json=maps.get("json");
	   		JSONObject obj = JSONObject.fromObject(json);
	   		Map<String,  Class> map=new HashMap<String,  Class>();
	   		map.put("resFilecheckDetailList", ResFilecheckDetail.class);
	   		map.put("resFilecheckOpinionList", ResFilecheckOpinion.class);
	   		ResFilecheck resFilecheck = (ResFilecheck)JSONObject.toBean(obj, ResFilecheck.class,map);
	   		resFilecheck.setOperatorId(ContextUtil.getCurrentUserId());
	   		return resFilecheck;
	   	}
		return null;
	   }
	/*
	 * 前置处理器调用函数
	 */
	
	public void saveFromProcess(ProcessCmd cmd) throws Exception{
		
		ResFilecheck resFilecheck=getFormObject(cmd);
		Short  d = cmd.getVoteAgree();
				
			try{
				if(resFilecheck==null) return;
				if(resFilecheck.getId()==null||resFilecheck.getId()==0){
					resFilecheck.setId(UniqueIdUtil.genId());
					addAll(resFilecheck);			
					System.out.println("保存成功");
				}else{
				    updateAll(resFilecheck);
				    System.out.println("更新成功");
				}
				
			}catch(Exception e){
				System.out.println("保存失败");
			}
	}

		public void addAll_prepublish(ResFilecheck resFilecheck) throws Exception{
		
		ProcessCmd processCmd = new ProcessCmd();
		//设置下一任务的多个执行人
		processCmd.setFlowKey("pre_publish");
		processCmd.setSubject("预发布【" + resFilecheck.getCode() + "】");
		
		List<TaskExecutor> executors=new ArrayList<TaskExecutor>();
		String checkerIds = resFilecheck.getCurrentUserIds();
		String[] ent_roles = checkerIds.split(";");
		for (String ent_role:ent_roles){
			String[] s = ent_role.split(",");
			if (s.length==0){
				break;
			}
			executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_COMP,s[0],"企业"));
			executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_ROLE,s[1],"角色"));
		}

		processCmd.setTaskExecutors(executors);
		
		Map<String,Object> vars=new HashMap<String,Object>();
		processCmd.setVariables(vars);		
		processCmd.setBusinessKey(resFilecheck.getId().toString());			
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());		
		ProcessRun processRun=processService.start(processCmd);
		resFilecheck.setRunId(processRun.getRunId());

		add(resFilecheck);
		addSubList(resFilecheck);
	
	}
	
}
