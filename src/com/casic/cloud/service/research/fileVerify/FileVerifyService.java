package com.casic.cloud.service.research.fileVerify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.research.fileVerify.FileVerify;
import com.casic.cloud.controller.pub.RoleConst;
import com.casic.cloud.dao.research.fileVerify.FileVerifyDao;
import com.casic.cloud.model.research.fileVerify.FileVerifyResult;
import com.casic.cloud.dao.research.fileVerify.FileVerifyResultDao;
import com.casic.cloud.model.research.fileVerify.FileVerifyUpload;
import com.casic.cloud.dao.research.fileVerify.FileVerifyUploadDao;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.webservice.api.ProcessService;

/**
 *<pre>
 * 对象功能:CLOUD_RESEARCH_VERIFY Service类
 * 开发公司:中国航天科工集团
 * 开发人员:wangqi
 * 创建时间:2013-05-14 21:16:24
 *</pre>
 */
@Service
public class FileVerifyService extends BaseService<FileVerify>
{
	@Resource
	private FileVerifyDao dao;
	
	@Resource
	private FileVerifyResultDao fileVerifyResultDao;
	@Resource
	private FileVerifyUploadDao fileVerifyUploadDao;
	
	@Resource
	private ProcessService processService;
	
	public FileVerifyService()
	{
	}
	
	@Override
	protected IEntityDao<FileVerify, Long> getEntityDao() 
	{
		return dao;
	}
	
	private void delByPk(Long id){
		fileVerifyResultDao.delByMainId(id);
		fileVerifyUploadDao.delByMainId(id);
	}
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public void addAll(FileVerify fileVerify) throws Exception{
		ProcessCmd processCmd = new ProcessCmd();
		//设置下一任务的多个执行人
		processCmd.setFlowKey("fileVerify1");
		processCmd.setSubject("文档审签表【" + fileVerify.getCode() + "】");
		
		String[] verifyEnterpriseIds=fileVerify.getVerifyEnterpIds().split("[,]");
		
		List<TaskExecutor> executors=new ArrayList<TaskExecutor>();
		for(String eId:verifyEnterpriseIds){
			executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_COMP,eId,"企业"));
			//executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_ROLE,"1",""));
		}
		executors.add(new TaskExecutor(TaskExecutor.USER_TYPE_ROLE,"10000004000223","研发主管"));

		processCmd.setTaskExecutors(executors);
		
		Map<String,Object> vars=new HashMap<String,Object>();

		
/*		vars.put("enquiry_id", fileVerify.getId().toString());
		vars.put("subDataNum", 2);
		List<Object> list = new LinkedList<Object>() ; 
		list.add("物品1");
		list.add("物品2");
		vars.put("dtl", list);*/
		
		//设置流程变量
	/*	processCmd.setVariables(vars);*/
		
		processCmd.setBusinessKey(fileVerify.getId().toString());
		
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		
		ProcessRun processRun=processService.start(processCmd);
		fileVerify.setRunId(processRun.getRunId());
		
		add(fileVerify);
		addSubList(fileVerify);
	}
	
	public void updateAll(FileVerify fileVerify) throws Exception{
		update(fileVerify);
		delByPk(fileVerify.getId());
		addSubList(fileVerify);
	}
	
	public void addSubList(FileVerify fileVerify) throws Exception{
		List<FileVerifyResult> fileVerifyResultList=fileVerify.getFileVerifyResultList();
		if(BeanUtils.isNotEmpty(fileVerifyResultList)){
			for(FileVerifyResult fileVerifyResult:fileVerifyResultList){
				fileVerifyResult.setSourceId(fileVerify.getId());
				fileVerifyResult.setId(UniqueIdUtil.genId());
				fileVerifyResultDao.add(fileVerifyResult);
			}
		}
		List<FileVerifyUpload> fileVerifyUploadList=fileVerify.getFileVerifyUploadList();
		if(BeanUtils.isNotEmpty(fileVerifyUploadList)){
			for(FileVerifyUpload fileVerifyUpload:fileVerifyUploadList){
				fileVerifyUpload.setSourceId(fileVerify.getId());
				fileVerifyUpload.setId(UniqueIdUtil.genId());
				fileVerifyUploadDao.add(fileVerifyUpload);
			}
		}
	}
	
	public List<FileVerifyResult> getFileVerifyResultList(Long id) {
		return fileVerifyResultDao.getByMainId(id);
	}
	public List<FileVerifyUpload> getFileVerifyUploadList(Long id) {
		return fileVerifyUploadDao.getByMainId(id);
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
}
