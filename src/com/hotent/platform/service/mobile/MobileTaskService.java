package com.hotent.platform.service.mobile;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmFormRun;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.mobile.MobileFormData;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmFormRunService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.form.BpmFormDefService;
import com.hotent.platform.webservice.impl.ProcessServiceImpl;

/**
 * 处理手机流程业务
 * @author cjj
 *
 */
@Service
public class MobileTaskService extends BaseService<ProcessTask>{
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmService bpmService;
	@Resource
	private BpmFormDefService bpmFormDefService;
	@Resource
	private BpmFormRunService bpmFormRunService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private ProcessServiceImpl processService;
	@Resource
	private TaskOpinionService taskOpinionService;
	@Override
	protected IEntityDao<ProcessTask, Long> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取手机需要显示的表单数据
	 * @param taskId
	 * @param ctxPath
	 * @return 
	 * {
		taskId=10000011380027, 			任务id
		form={                          表单
				"emptyForm":true,       是否空表单
				"extForm":true,         是否url表单
				"fields":"",            主表字段
				"formDetailUrl":"",    	url明细表单
				"formEditUrl":"",       url编辑表单
				"options":{},           审批意见
				"signTask":false,       是否会签任务
				"subTableList":[],      子表字段
				"tableDesc":"",         主表描述
				"tableId":0,            主表id
				"tableName":""			主表名称
			}
	 * }
	 * @throws Exception
	 */
	public MobileFormData getFormData(String taskId,String ctxPath) throws Exception {
		TaskEntity taskEntity=bpmService.getTask(taskId);
		
		String instanceId = taskEntity.getProcessInstanceId();
		String nodeId = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		Long userId = ContextUtil.getCurrentUserId();
		
		ProcessRun processRun=processRunService.getByActInstanceId(instanceId);
		
		BpmNodeSet bpmNodeSet=bpmNodeSetService.getByActDefIdNodeId(actDefId, nodeId);
		if(BeanUtils.isEmpty(bpmNodeSet)||bpmNodeSet.getFormType()==-1){
			bpmNodeSet=bpmNodeSetService.getBySetType(processRun.getDefId(), BpmNodeSet.SetType_GloabalForm);
		}
		MobileFormData formData=getMobileForm(bpmNodeSet, processRun, taskId,ctxPath,nodeId,userId);
		formData.setSignTask(bpmService.isSignTask(taskEntity));// 是否会签任务
		return formData;
	}
	
	/**
	 * 获取mobileFormData 实例
	 * @param bpmNodeSet
	 * @param processRun
	 * @param taskId
	 * @param ctxPath
	 * @param nodeId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private MobileFormData getMobileForm(BpmNodeSet bpmNodeSet,ProcessRun processRun,
			String taskId, String ctxPath,String nodeId,Long userId) throws Exception
	{
		MobileFormData mobileFormData=new MobileFormData();
		String actInstId=processRun.getActInstId();
		String pkValue=processRun.getBusinessKey();
		Long defId=processRun.getDefId();
		boolean isEmptyForm=false;
		boolean isExtForm=false;
		String formUrl="";
		String detailUrl="";
		if(bpmNodeSet.getFormKey()==null||bpmNodeSet.getFormKey()==0){
			isEmptyForm=true;
		}
		if(bpmNodeSet.getFormType()==1){
			isExtForm=true;
			formUrl=bpmFormDefService.getFormUrl(taskId, defId, nodeId, processRun.getBusinessKey(), ctxPath);
			if(StringUtil.isNotEmpty(bpmNodeSet.getDetailUrl())){
				detailUrl=bpmNodeSet.getDetailUrl().replaceFirst(BpmConst.FORM_PK_REGEX, pkValue);
				if(!detailUrl.startsWith("http")){
					detailUrl=ctxPath + detailUrl;
				}
			}
		}
		
		mobileFormData.setEmptyForm(isEmptyForm);
		mobileFormData.setExtForm(isExtForm);
		mobileFormData.setFormEditUrl(formUrl);
		mobileFormData.setFormDetailUrl(detailUrl);
		
		if(!isExtForm)
		{
			BpmFormDef bpmFormDef=null;
			if(bpmNodeSet.getFormDefId()!=0){
				bpmFormDef=bpmFormDefService.getById(bpmNodeSet.getFormDefId());
			}else{
				bpmFormDef=bpmFormDefService.getById(bpmNodeSet.getFormKey());
			}
			Long tableId = bpmFormDef.getTableId();
			mobileFormData.setFormData(tableId, pkValue, actInstId);
		}
		return mobileFormData;
	}
	
	/**
	 * 根据流程运行Id 获取对应的表单数据
	 * @param runId 实例id
	 * @param userId 用户id
	 * @param ctxPath 项目根目录
	 * @param taskId 任务id
	 * @return 
	 * {
		taskId=10000011380027, 			任务id
		form={                          表单
				"emptyForm":true,       是否空表单
				"extForm":true,         是否url表单
				"fields":"",            主表字段
				"formDetailUrl":"",    	url明细表单
				"formEditUrl":"",       url编辑表单
				"options":{},           审批意见
				"signTask":false,       是否会签任务
				"subTableList":[],      子表字段
				"tableDesc":"",         主表描述
				"tableId":0,            主表id
				"tableName":""			主表名称
			}
	 * }
	 */
	public MobileFormData getProcessData(Long runId,Long userId,String ctxPath, String taskId) throws Exception {
		MobileFormData mobileFormData=new MobileFormData();
		ProcessRun processRun=processRunService.getById(runId);
		String pkValue=processRun.getBusinessKey();
		String actInstId=processRun.getActInstId();
		List<BpmFormRun> bpmFormRunList=bpmFormRunService.getByInstanceId(processRun.getActInstId());
		Long tableId=0L;
		boolean isEmptyForm=false;
		boolean	isExtForm=false;
		String formUrl="";
		if(BeanUtils.isNotEmpty(bpmFormRunList)){
			for(BpmFormRun bpmFormRun:bpmFormRunList){
				if(bpmFormRun.getFormdefId()!=0){
					BpmFormDef bpmFormDef=bpmFormDefService.getById(bpmFormRun.getFormdefId());
					tableId=bpmFormDef.getTableId();
					break;
				}
			}
		}
		if(tableId!=0){
			mobileFormData.setFormData(tableId,pkValue,actInstId);
		}else{
			BpmDefinition bpmDefinition=bpmDefinitionService.getByActDefId(processRun.getActDefId());
			String url=bpmDefinition.getFormDetailUrl();
			if(StringUtil.isNotEmpty(url)){
				isExtForm=true;
				formUrl=url;
				formUrl=formUrl.replaceFirst(BpmConst.FORM_PK_REGEX, pkValue);
				if(!formUrl.startsWith("http")){
					formUrl=ctxPath + formUrl;
				}
			}else{
				isEmptyForm=true;
			}
		}
		mobileFormData.setEmptyForm(isEmptyForm);
		mobileFormData.setExtForm(isExtForm);
		mobileFormData.setFormDetailUrl(formUrl);
		
		if(StringUtil.isNotEmpty(taskId)){
			mobileFormData.setSignTask(bpmService.isSignTask(bpmService.getTask(taskId)));// 是否会签任务
		}
		
		return mobileFormData;
	}
	
	/**
	 * 完成任务
	 * @param taskId
	 * @param opinion
	 * @param userAccount
	 * @throws Exception
	 */
	public void complete(String taskId,String opinion,String userAccount, String voteAgree) throws Exception{
		ProcessCmd processCmd=new ProcessCmd();
		processCmd.setTaskId(taskId);
		processCmd.setVoteAgree(Short.parseShort(voteAgree));
		processCmd.setUserAccount(userAccount);
		processService.doNext(processCmd);
		//更新任务意见
		if(BeanUtils.isEmpty(opinion)){
			return;
		}
		Long lTaskId = Long.parseLong(taskId);
		TaskOpinion taskOpinion = taskOpinionService.getByTaskId(lTaskId);
		if(BeanUtils.isEmpty(taskOpinion)){
				return;
		}
		JSONObject jsonObj=JSONObject.fromObject(opinion);
		Set<String> set = jsonObj.keySet();
		String key = set.iterator().next();
		String value = (String)jsonObj.get(key);

		taskOpinion.setFieldName(key);   
		taskOpinion.setOpinion(value);

		taskOpinionService.update(taskOpinion);
		
	}
	
}
