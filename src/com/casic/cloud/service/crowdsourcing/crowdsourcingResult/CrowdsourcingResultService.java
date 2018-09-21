package com.casic.cloud.service.crowdsourcing.crowdsourcingResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;

import org.activiti.engine.RuntimeService;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResult.CrowdsourcingResult;
import com.casic.cloud.dao.crowdsourcing.crowdsourcingResult.CrowdsourcingResultDao;
import com.casic.cloud.model.crowdsourcing.crowdsourcingResult.CrowdsourcingResultDetail;
import com.casic.cloud.dao.crowdsourcing.crowdsourcingResult.CrowdsourcingResultDetailDao;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.webservice.api.ProcessService;

/**
 *<pre>
 * 对象功能:cloud_crowdsourcing_result Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-07-09 17:15:19
 *</pre>
 */
@Service
public class CrowdsourcingResultService extends BaseService<CrowdsourcingResult>
{
	@Resource
	private CrowdsourcingResultDao dao;
	
	@Resource
	private TaskOpinionService taskOpinionService;
	
	@Resource
	private CrowdsourcingResultDetailDao crowdsourcingResultDetailDao;
	
	@Resource
	private ProcessService processService;
	
	
	@Resource 
	private RuntimeService runtimeService;
	
	public CrowdsourcingResultService()
	{
	}
	
	@Override
	protected IEntityDao<CrowdsourcingResult, Long> getEntityDao() 
	{
		return dao;
	}
	
	private void delByPk(Long id){
		crowdsourcingResultDetailDao.delByMainId(id);
	}
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public void addAll(CrowdsourcingResult crowdsourcingResult) throws Exception{
		add(crowdsourcingResult);
		addSubList(crowdsourcingResult);
	}
	
	public void updateAll(CrowdsourcingResult crowdsourcingResult) throws Exception{
		update(crowdsourcingResult);
		delByPk(crowdsourcingResult.getId());
		addSubList(crowdsourcingResult);
	}
	
	public void addSubList(CrowdsourcingResult crowdsourcingResult) throws Exception{
		List<CrowdsourcingResultDetail> crowdsourcingResultDetailList=crowdsourcingResult.getCrowdsourcingResultDetailList();
		if(BeanUtils.isNotEmpty(crowdsourcingResultDetailList)){
			for(CrowdsourcingResultDetail crowdsourcingResultDetail:crowdsourcingResultDetailList){
				crowdsourcingResultDetail.setResultId(crowdsourcingResult.getId());
				crowdsourcingResultDetailDao.add(crowdsourcingResultDetail);
			}
		}
	}
	
	public List<CrowdsourcingResultDetail> getCrowdsourcingResultDetailList(Long id) {
		return crowdsourcingResultDetailDao.getByMainId(id);
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

	public void  saveFromProcess(ProcessCmd cmd) throws Exception{
		//意见  是否同意 0=弃权票 1=同意 2=拒绝
		Short  d = cmd.getVoteAgree();
		CrowdsourcingResult crowdsourcingResult  = getFormObject(cmd);
		TaskOpinion p = taskOpinionService.getByTaskId(Long.valueOf(cmd.getTaskId()));	
		if(d==1){
			try{
				if(crowdsourcingResult==null) return;
				if(crowdsourcingResult.getId()==null||crowdsourcingResult.getId()==0){
				
					crowdsourcingResult.setId(UniqueIdUtil.genId());
					Date now = new Date(); 
					crowdsourcingResult.setCreatetime(now);
					crowdsourcingResult.setUpdatetime(now);
			    	ProcessRun processRun =cmd.getProcessRun();  	
			    	runtimeService.setVariable(processRun.getActInstId(), "resultId", crowdsourcingResult.getId());
					addAllProcess(crowdsourcingResult);		
					System.out.println("保存研发结果成功");
				}else{
					Date now = new Date(); 
					crowdsourcingResult.setUpdatetime(now);
			    	ProcessRun processRun =cmd.getProcessRun();  
					runtimeService.setVariable(processRun.getActInstId(), "resultId", crowdsourcingResult.getId());
					List<CrowdsourcingResultDetail> crowdsourcingResultDetailList = crowdsourcingResult.getCrowdsourcingResultDetailList();
					for(CrowdsourcingResultDetail crowdsourcingResultDetail:crowdsourcingResultDetailList){
						crowdsourcingResultDetail.setId(UniqueIdUtil.genId());
					}
				    updateAll(crowdsourcingResult);
				    System.out.println("更新研发结果成功");
				}
			}catch(Exception e){
					e.printStackTrace();
			}
		}else if(d==3){
			CrowdsourcingResultDetail crowdsourcingResultDetail=new CrowdsourcingResultDetail();
			crowdsourcingResultDetail.setId(UniqueIdUtil.genId());
			crowdsourcingResultDetail.setResultName(crowdsourcingResult.getResultName());
			crowdsourcingResultDetail.setResultAttachmentIds(crowdsourcingResult.getResultAttachmentIds());
			crowdsourcingResultDetail.setResultInfo(crowdsourcingResult.getResultInfo());
			crowdsourcingResultDetail.setAuditType(crowdsourcingResult.getAuditType());
			Date now = new Date(); 
			crowdsourcingResultDetail.setAuditTime(now);
			crowdsourcingResultDetail.setSubmitTime(crowdsourcingResult.getUpdatetime());
			crowdsourcingResultDetail.setResultId(crowdsourcingResult.getId());
			crowdsourcingResultDetail.setAuditOpinion(crowdsourcingResult.getAuditOpinion());
			crowdsourcingResultDetail.setAuditOpinion(p.getOpinion());
			crowdsourcingResultDetail.setAuditType("验证审核");
			crowdsourcingResultDetailDao.add(crowdsourcingResultDetail);
		}else{
			System.out.println("弃权/不同意");
		}
	}   
	public void addAllProcess(CrowdsourcingResult crowdsourcingResult) throws Exception{
		add(crowdsourcingResult);
		addSubList(crowdsourcingResult);
	}
	protected CrowdsourcingResult getFormObject(ProcessCmd cmd) throws Exception {
    	net.sf.json.util.JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    	//该函数得到流程定义绑定的表单对象
    	Map<String,String> maps=cmd.getFormDataMap();
    	if(maps!=null){
    		String json=maps.get("json");
    		JSONObject obj = JSONObject.fromObject(json);
    		Map<String,  Class> map=new HashMap<String,  Class>();
    		map.put("crowdsourcingResultDetailList", CrowdsourcingResultDetail.class);
    		CrowdsourcingResult crowdsourcingResult = (CrowdsourcingResult)JSONObject.toBean(obj, CrowdsourcingResult.class,map);
    		return crowdsourcingResult;
    	}
		return null;
    }private String getStatus(Short t){
		String s = "";
		if(t==0){
			s="弃权";
		}
		if(t==1){
			s="同意";
		}
		if(t==2){
			s="反对";
		}
		if(t==3){
			s="驳回";
		}
		if(t==4){
			s="追回";
		}
		
		return s;
		
	}

	public void startProcess(CrowdsourcingResult crowdsourcingResult) throws Exception {
		ProcessCmd processCmd = new ProcessCmd();
		//设置下一任务的多个执行人
		processCmd.setFlowKey("prepublish");
		processCmd.setSubject("预发布模式【" + crowdsourcingResult.getCode() + "】");
	
		String checkerRoleID = crowdsourcingResult.getSourceformAgreementId().toString();
		String checkerEntID = crowdsourcingResult.getSourceformCrowdsourcingId().toString();
				

		List<TaskExecutor> executors=new ArrayList<TaskExecutor>();
		executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_COMP,checkerEntID,"企业"));
		executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_ROLE,checkerRoleID,""));
		
		processCmd.setTaskExecutors(executors);
		
		Map<String,Object> vars=new HashMap<String,Object>();	
		vars.put("id", crowdsourcingResult.getId().toString());		
		//设置流程变量
		processCmd.setVariables(vars);		
		processCmd.setBusinessKey(crowdsourcingResult.getId().toString());
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		ProcessRun processRun=processService.start(processCmd);
		add(crowdsourcingResult);
		addSubList(crowdsourcingResult);
	}
}
