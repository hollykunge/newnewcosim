package com.hotent.platform.service.bpm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.NodeCache;
import com.hotent.core.bpm.util.BpmUtil;
import com.hotent.core.bpmn20.entity.CallActivity;
import com.hotent.core.bpmn20.entity.FlowElement;
import com.hotent.core.bpmn20.entity.Process;
import com.hotent.core.bpmn20.entity.UserTask;
import com.hotent.core.bpmn20.entity.ht.BPMN20HtExtConst;
import com.hotent.core.bpmn20.util.BPMN20Util;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.page.PageBean;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.XmlBeanUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.dao.bpm.BpmAgentDao;
import com.hotent.platform.dao.bpm.BpmDao;
import com.hotent.platform.dao.bpm.BpmDefRightsDao;
import com.hotent.platform.dao.bpm.BpmDefVarDao;
import com.hotent.platform.dao.bpm.BpmDefinitionDao;
import com.hotent.platform.dao.bpm.BpmFormRunDao;
import com.hotent.platform.dao.bpm.BpmNodeButtonDao;
import com.hotent.platform.dao.bpm.BpmNodeMessageDao;
import com.hotent.platform.dao.bpm.BpmNodePrivilegeDao;
import com.hotent.platform.dao.bpm.BpmNodeRuleDao;
import com.hotent.platform.dao.bpm.BpmNodeScriptDao;
import com.hotent.platform.dao.bpm.BpmNodeSetDao;
import com.hotent.platform.dao.bpm.BpmNodeSignDao;
import com.hotent.platform.dao.bpm.BpmNodeUserDao;
import com.hotent.platform.dao.bpm.BpmNodeUserUplowDao;
import com.hotent.platform.dao.bpm.BpmNodeWebServiceDao;
import com.hotent.platform.dao.bpm.BpmProStatusDao;
import com.hotent.platform.dao.bpm.BpmTaskCommentDao;
import com.hotent.platform.dao.bpm.BpmUserConditionDao;
import com.hotent.platform.dao.bpm.ExecutionDao;
import com.hotent.platform.dao.bpm.ExecutionStackDao;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.dao.bpm.ReminderStateDao;
import com.hotent.platform.dao.bpm.TaskApprovalItemsDao;
import com.hotent.platform.dao.bpm.TaskDao;
import com.hotent.platform.dao.bpm.TaskOpinionDao;
import com.hotent.platform.dao.bpm.TaskReminderDao;
import com.hotent.platform.dao.bpm.TaskSignDataDao;
import com.hotent.platform.dao.form.BpmFormDefDao;
import com.hotent.platform.dao.system.GlobalTypeDao;
import com.hotent.platform.dao.system.MessageDao;
import com.hotent.platform.model.bpm.BpmAgent;
import com.hotent.platform.model.bpm.BpmDefRights;
import com.hotent.platform.model.bpm.BpmDefVar;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeButton;
import com.hotent.platform.model.bpm.BpmNodeMessage;
import com.hotent.platform.model.bpm.BpmNodeRule;
import com.hotent.platform.model.bpm.BpmNodeScript;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.BpmNodeSign;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.bpm.BpmNodeUserUplow;
import com.hotent.platform.model.bpm.BpmUserCondition;
import com.hotent.platform.model.bpm.TaskApprovalItems;
import com.hotent.platform.model.bpm.TaskReminder;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.system.GlobalType;
import com.hotent.platform.service.form.BpmFormDefService;
import com.hotent.platform.service.form.BpmFormTableService;
import com.hotent.platform.xml.bpm.BpmDefinitionXml;
import com.hotent.platform.xml.bpm.BpmDefinitionXmlList;
import com.hotent.platform.xml.form.BpmFormDefXml;
import com.hotent.platform.xml.table.BpmFormTableXml;
import com.hotent.platform.xml.util.MsgUtil;

/**
 * 对象功能:流程定义扩展 Service类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2011-11-21 22:50:46
 */
@Service
public class BpmDefinitionService extends BaseService<BpmDefinition>
{
	@Resource
	private BpmDefinitionDao dao;	
	@Resource
	private BpmNodeSetDao bpmNodeSetDao;	
	@Resource
	private BpmDefVarDao bpmDefVarDao;		
	@Resource
	private BpmService bpmService;
	
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private BpmNodeSignDao bpmNodeSignDao;
	@Resource
	private BpmNodeRuleDao bpmNodeRuleDao;
	@Resource
	private TaskSignDataDao taskSignDataDao;
	@Resource
	private BpmNodeMessageDao bpmNodeMessageDao;
	@Resource
	private MessageDao messageDao;
	@Resource
	private BpmDefVarDao bpmDefVarsDao;
	@Resource
	private ExecutionStackDao executionStackDao;
	@Resource
	private BpmNodeUserDao bpmNodeUserDao;
	@Resource
	private BpmTaskCommentDao bpmTaskCommentDao;
	@Resource
	private BpmNodeScriptDao bpmNodeScriptDao;

	@Resource
	private BpmAgentDao bpmAgentDao;
	@Resource
	private  BpmDefRightsDao bpmDefRightDao;
	@Resource
	private BpmNodeButtonDao bpmNodeButtonDao;
	@Resource
	private TaskApprovalItemsDao taskApprovalItemsDao;
	@Resource
	private TaskReminderDao taskReminderDao;
	@Resource
	private BpmDefRightsDao bpmDefRightsDao;
	@Resource
	private BpmNodeUserUplowDao bpmNodeUserUplowDao;
	@Resource
	private BpmUserConditionDao bpmUserConditionDao;
	@Resource
	private GlobalTypeDao globalTypeDao;
	@Resource
	private BpmDao bpmDao;
	@Resource
	private BpmFormDefService bpmFormDefService;
	@Resource
	private BpmFormDefDao bpmFormDefDao;
	@Resource
	private BpmFormTableService bpmFormTableService;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private BpmFormRunDao bpmFormRunDao;
	@Resource
	private BpmNodePrivilegeDao bpmNodePrivilegeDao;
	@Resource
	private BpmNodeWebServiceDao bpmNodeWebServiceDao;
	@Resource
	private BpmProStatusDao bpmProStatusDao;
	@Resource
	private TaskForkService taskForkService;
	@Resource
	private TaskOpinionDao taskOpinionDao;
	@Resource
	private ReminderStateDao reminderStateDao;
	@Resource
	private BpmAgentService bpmAgentService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private TaskDao taskDao;
	@Resource
	private ExecutionDao executionDao;
	
	public BpmDefinitionService()
	{
	}
	
	@Override
	protected IEntityDao<BpmDefinition, Long> getEntityDao() {
		return dao;
	}
	
	/**
	 * 发布流程数据。
	 * @param bpmDefinition
	 * @param actFlowDefXml
	 * @throws Exception 
	 */
	public void deploy(BpmDefinition bpmDefinition,String actFlowDefXml) throws Exception{
		Deployment deployment=bpmService.deploy(bpmDefinition.getSubject(), actFlowDefXml);
		ProcessDefinitionEntity ent= bpmService.getProcessDefinitionByDeployId(deployment.getId());
		bpmDefinition.setActDeployId(new Long(deployment.getId()));
		bpmDefinition.setActDefId(ent.getId());
		bpmDefinition.setActDefKey(ent.getKey());
		bpmDefinition.setStatus(BpmDefinition.STATUS_DEPLOYED);
		dao.update(bpmDefinition);
		
		saveOrUpdateNodeSet(actFlowDefXml,bpmDefinition,false);
	}
	
	
	/**
	 * 保存及更新流程定义
	 * @param bpmDefinition 流程定义实体
	 * @param isDeploy 是否发布新流程
	 * @param actFlowDefXml 流程定义bpmn文档
	 * @throws Exception
	 */
	public void saveOrUpdate(BpmDefinition bpmDefinition,boolean isDeploy,String actFlowDefXml) throws Exception
	{
		Long   oldDefId=bpmDefinition.getDefId();
		
		Long   newDefId=bpmDefinition.getDefId();
		
		boolean isUpdate=false;
		
		//新增加的流程
		if(bpmDefinition.getDefId()==null || bpmDefinition.getDefId()==0)
		{
			if(isDeploy)//发布定义
			{
				Deployment deployment=bpmService.deploy(bpmDefinition.getSubject(), actFlowDefXml);
				ProcessDefinitionEntity ent= bpmService.getProcessDefinitionByDeployId(deployment.getId());
				bpmDefinition.setActDeployId(new Long(deployment.getId()));
				bpmDefinition.setActDefId(ent.getId());
				bpmDefinition.setActDefKey(ent.getKey());
			}
			bpmDefinition.setVersionNo(1);
			
			bpmDefinition.setDefId(UniqueIdUtil.genId());
			//主版本
			bpmDefinition.setIsMain(BpmDefinition.MAIN);
			bpmDefinition.setCreatetime(new Date());
			bpmDefinition.setUpdatetime(new Date());
			Short status=isDeploy?BpmDefinition.STATUS_DEPLOYED:BpmDefinition.STATUS_NOTDEPLOYED;
			bpmDefinition.setStatus(status);
			add(bpmDefinition);
			
			if(isDeploy){
				//设置流程节点信息
				saveOrUpdateNodeSet(actFlowDefXml,bpmDefinition,true);
			}
		}
		//更新流程定义
		else{
			//发布了新的版本定义
			if(isDeploy){
				newDefId=UniqueIdUtil.genId();
				dao.updateSubVersions(newDefId,bpmDefinition.getDefKey());
				
				Deployment deployment=bpmService.deploy(bpmDefinition.getSubject(), actFlowDefXml);
				ProcessDefinitionEntity ent= bpmService.getProcessDefinitionByDeployId(deployment.getId());
				String actDefId=ent.getId();
				//原bpmFinition
				BpmDefinition newBpmDefinition=(BpmDefinition)bpmDefinition.clone();
				//增加版本号
				newBpmDefinition.setVersionNo(ent.getVersion());
				newBpmDefinition.setActDeployId(new Long(deployment.getId()));
				newBpmDefinition.setActDefId(actDefId);
				newBpmDefinition.setActDefKey(ent.getKey());
				//发布新版本后，需要生成新的发布记录
				newBpmDefinition.setDefId(newDefId);
				newBpmDefinition.setParentDefId(newDefId);
				newBpmDefinition.setUpdatetime(new Date());
				newBpmDefinition.setStatus(BpmDefinition.STATUS_DEPLOYED);
				//设置新的流程序为主版本
				newBpmDefinition.setIsMain(BpmDefinition.MAIN);
				//添加新版本的流程定义
				add(newBpmDefinition);
				
				isUpdate=true;
				//设置流程节点信息
				saveOrUpdateNodeSet(actFlowDefXml,newBpmDefinition,true);
				//同步起始节点全局表单的配置情况。
				syncStartGlobal(oldDefId,newDefId,actDefId);
			}
			else{
				//直接修改定义
				if(bpmDefinition.getActDeployId()!=null){
					bpmService.wirteDefXml(bpmDefinition.getActDeployId().toString(), actFlowDefXml);
					//设置流程节点信息
					saveOrUpdateNodeSet(actFlowDefXml,bpmDefinition,false);
					
					String actDefId=bpmDefinition.getActDefId();
					//清除节点的缓存
					NodeCache.clear(actDefId);
				}
				update(bpmDefinition);
			}
		}
		
		if(isUpdate){//发布了新的版本定义
			saveOrUpdateBpmDefSeting(newDefId,oldDefId,actFlowDefXml, bpmDefinition.getDefKey());
		}
	}
	
	/**
	 * 设置流程节点属性。
	 * @param actFlowDefXml
	 * @param bpmDefinition
	 * @param isAdd
	 * @throws Exception
	 */
	private void saveOrUpdateNodeSet(String actFlowDefXml,BpmDefinition bpmDefinition,boolean isAdd) throws Exception{
		Long defId=bpmDefinition.getDefId();
		List<Process> processes = BPMN20Util.getProcess(actFlowDefXml);
		if(processes.size()==0){
			return;
		}
		@SuppressWarnings("unchecked")
		Class<FlowElement>[] classes= new Class[]{
				UserTask.class,
				CallActivity.class
		};
		List<FlowElement> flowElements = BPMN20Util.getFlowElementByType(processes.get(0),true,classes);
		if(isAdd){
			for(FlowElement flowElement:flowElements){
				addNodeSet(bpmDefinition,flowElement);
			}
		}else{
			Map<String,BpmNodeSet> nodeSetMap=bpmNodeSetDao.getMapByDefId(defId);
			//删除无用的节点
			delNodeSet(nodeSetMap,flowElements);
			//添加新增的节点
			updNodeSet(bpmDefinition,nodeSetMap,flowElements);
		}
	}
	
	/**
	 * 原来流程定义中没有的节点则添加流程节点定义。
	 * @param oldSetMap
	 * @param curNodeMap
	 * @param bpmDefinition
	 * @throws Exception
	 */
	private void updNodeSet(BpmDefinition bpmDefinition,Map<String,BpmNodeSet> oldSetMap,List<FlowElement> flowNodes) throws Exception{
		for(FlowElement flowElement:flowNodes){
			if(oldSetMap.containsKey(flowElement.getId())){
				Integer nodeOrder = 0;
				List<Object> extensions = BPMN20Util.getFlowElementExtension(flowElement, BPMN20HtExtConst._Order_QNAME);
				
				if(BeanUtils.isNotEmpty(extensions)){
					nodeOrder=(Integer) extensions.get(0);
				}
				BpmNodeSet bpmNodeSet=oldSetMap.get(flowElement.getId());
				bpmNodeSet.setNodeName(flowElement.getName());
				bpmNodeSet.setNodeOrder(nodeOrder);
				bpmNodeSetDao.update(bpmNodeSet);
			}else{
				addNodeSet(bpmDefinition, flowElement);
			}
		}
	}
	
	
	/**
	 * 删除当前流程定义中没有的节点。
	 * @param oldSetMap  原有流程节点
	 * @param flowNodes 新流程节点
	 */
	private void delNodeSet(Map<String,BpmNodeSet> oldSetMap,List<FlowElement> flowNodes){
		Iterator<String> keys=oldSetMap.keySet().iterator();
		while(keys.hasNext()){
			String nodeId=keys.next();
			boolean inflag=false;
			for(FlowElement flowNode:flowNodes){
				if(flowNode.getId().equals(nodeId)){
					inflag=true;
					break;
				}
			}
			if(inflag) continue;
			BpmNodeSet bpmNodeSet=oldSetMap.get(nodeId);
			bpmNodeSetDao.delById(bpmNodeSet.getSetId());
		}
	}
	
	/**
	 * 添加流程定义节点设置。
	 * @param bpmDefinition 流程定义
	 * @param flowNode 流程节点
	 * @throws Exception
	 */
	private void addNodeSet(BpmDefinition bpmDefinition,FlowElement flowNode) throws Exception{
		Long defId=bpmDefinition.getDefId();
		String actDefId=bpmDefinition.getActDefId();
		Integer nodeOrder = 0;
		List<Object> extensions = BPMN20Util.getFlowElementExtension(flowNode, BPMN20HtExtConst._Order_QNAME);
		
		if(BeanUtils.isNotEmpty(extensions)){
			nodeOrder=(Integer) extensions.get(0);
		}
		
		BpmNodeSet bpmNodeSet=new BpmNodeSet();
		bpmNodeSet.setSetId(UniqueIdUtil.genId());
		bpmNodeSet.setFormType((short)-1);
		bpmNodeSet.setActDefId(actDefId);
		bpmNodeSet.setDefId(defId);
		bpmNodeSet.setNodeId(flowNode.getId());
		bpmNodeSet.setNodeName(flowNode.getName());
		bpmNodeSet.setNodeOrder(nodeOrder);
		bpmNodeSetDao.add(bpmNodeSet);
	}
	
	/**
	 * 保存及更新流程定义的相关配置
	 * @param newDefId
	 * @param oldDefId
	 * @throws Exception 
	 */
	private void saveOrUpdateBpmDefSeting(Long newDefId,Long oldDefId,String actFlowDefXml,String defKey) throws Exception{
		
		if(oldDefId==null || oldDefId<=0) return;
		
		BpmDefinition newDef =getById(newDefId);
		BpmDefinition oldDef =getById(oldDefId);
		
		String newActDefId=newDef.getActDefId();
		String oldActDefId=oldDef.getActDefId();
		if(oldActDefId==null) return;
		
		
		//BPM_AGENT	流程代理配置
//		List<BpmAgent> agentList=bpmAgentDao.getByDefKey(defKey);
//		if(BeanUtils.isNotEmpty(agentList)){
//			for(BpmAgent o:agentList){
//				BpmAgent n=(BpmAgent)o.clone();
//				n.setId(UniqueIdUtil.genId());
//				n.setDefKey(defKey);
//				bpmAgentDao.add(n);
//			}
//		}
		
		//BPM_DEF_RIGHT	流程定义权限
//		List<BpmDefRights> defRight=bpmDefRightDao.getByDefKey(defKey);
//		if(BeanUtils.isNotEmpty(defRight)){
//			for(BpmDefRights o:defRight){
//				BpmDefRights n=(BpmDefRights)o.clone();
//				n.setId(UniqueIdUtil.genId());
//				n.setDefKey(defKey);
//				bpmDefRightDao.add(n);
//			}
//		}
		
		//BPM_DEF_VARS	流程变量定义 OK
		List<BpmDefVar> defVarList=bpmDefVarsDao.getByDefId(oldDefId);
		if(BeanUtils.isNotEmpty(defVarList)){
			for(BpmDefVar o:defVarList){
				BpmDefVar n=(BpmDefVar)o.clone();
				n.setVarId(UniqueIdUtil.genId());
				n.setDefId(newDefId);
				bpmDefVarsDao.add(n);
			}
		}

		//BPM_NODE_SCRIPT	流程开始结束节点事件脚本 OK
		List<BpmNodeScript> nodeScripts= bpmNodeScriptDao.getByActDefId(oldActDefId);
		Map<String, Map<String, String>> taskActivitysMap = BpmUtil.getTaskActivitys(actFlowDefXml);
		Map<String,String> startActivitysMap = taskActivitysMap.get("开始节点");
		Map<String,String> endActivitysMap = taskActivitysMap.get("结束节点");
		Map<String,String> seActivitysMap=new HashMap<String, String>();
		if(!BeanUtils.isEmpty(startActivitysMap)){
			seActivitysMap.putAll(startActivitysMap);
		}
		if(!BeanUtils.isEmpty(endActivitysMap)){
			seActivitysMap.putAll(endActivitysMap);
		}
		Iterator<String> seNodeIds=seActivitysMap.keySet().iterator();
		while(seNodeIds.hasNext()){
			String nodeId=seNodeIds.next();
			for(BpmNodeScript script:nodeScripts){
				if(script.getNodeId().equals(nodeId)){
					BpmNodeScript newScript=(BpmNodeScript)script.clone();
					newScript.setId(UniqueIdUtil.genId());
					newScript.setActDefId(newActDefId);
					bpmNodeScriptDao.add(newScript);
				}
			}
		}
		
		
		//BPM_APPROVAL_ITEM 全局常用语
		TaskApprovalItems globalTApproval=taskApprovalItemsDao.getFlowApproval(oldActDefId, TaskApprovalItems.global);
		if(BeanUtils.isNotEmpty(globalTApproval)){
			globalTApproval.setActDefId(newActDefId);
			globalTApproval.setItemId(UniqueIdUtil.genId());
			taskApprovalItemsDao.add(globalTApproval);
		}
		
		//BPM_NODE_SET	流程节点配置 OK
		List<BpmNodeSet> newNodeSetList= bpmNodeSetDao.getByDefId(newDefId);
		Map<String, BpmNodeSet> oldNodeSetMap= bpmNodeSetDao.getMapByDefId(oldDefId);
		if(BeanUtils.isEmpty(newNodeSetList) || BeanUtils.isEmpty(oldNodeSetMap)) return ;
		for(BpmNodeSet bpmNodeSet:newNodeSetList){
			String nodeId=bpmNodeSet.getNodeId();
			if(!oldNodeSetMap.containsKey(nodeId)) continue;
			
			BpmNodeSet oldBpmNodeSet=oldNodeSetMap.get(nodeId);
			Long oldSetId=oldBpmNodeSet.getSetId();
			
			//更新当前的节点配置信息
			bpmNodeSet.setAfterHandler(oldBpmNodeSet.getAfterHandler());
			bpmNodeSet.setBeforeHandler(oldBpmNodeSet.getBeforeHandler());
			bpmNodeSet.setFormDefId(oldBpmNodeSet.getFormDefId());
			bpmNodeSet.setFormDefName(oldBpmNodeSet.getFormDefName());
			bpmNodeSet.setFormKey(oldBpmNodeSet.getFormKey());
			bpmNodeSet.setFormType(oldBpmNodeSet.getFormType());
			bpmNodeSet.setFormUrl(oldBpmNodeSet.getFormUrl());
			bpmNodeSet.setIsHideOption(oldBpmNodeSet.getIsHideOption());
			bpmNodeSet.setIsHidePath(oldBpmNodeSet.getIsHidePath());
			bpmNodeSet.setIsJumpForDef(oldBpmNodeSet.getIsJumpForDef());
			bpmNodeSet.setJoinTaskKey(oldBpmNodeSet.getJoinTaskKey());
			bpmNodeSet.setJoinTaskName(oldBpmNodeSet.getJoinTaskName());
			bpmNodeSet.setJumpType(oldBpmNodeSet.getJumpType());
			bpmNodeSet.setOldFormKey(oldBpmNodeSet.getOldFormKey());
			bpmNodeSetDao.update(bpmNodeSet);
	
			
			//BPM_NODE_RULE	流程节点规则 OK
			List<BpmNodeRule> nodeRuleList=bpmNodeRuleDao.getByDefIdNodeId(oldActDefId, nodeId);
			if(BeanUtils.isNotEmpty(nodeRuleList)){
				for(BpmNodeRule oR:nodeRuleList){
					BpmNodeRule nR=(BpmNodeRule)oR.clone();
					nR.setRuleId(UniqueIdUtil.genId());
					nR.setActDefId(newActDefId);
					bpmNodeRuleDao.add(nR);
				}
			}
			
			//BPM_NODE_SCRIPT	流程节点事件脚本 OK
			List<BpmNodeScript> nodeScriptList= bpmNodeScriptDao.getByBpmNodeScriptId(nodeId,oldActDefId);
			if(BeanUtils.isNotEmpty(nodeScriptList)){
				for(BpmNodeScript oS:nodeScriptList){
					BpmNodeScript nS=(BpmNodeScript)oS.clone();
					nS.setId(UniqueIdUtil.genId());
					nS.setActDefId(newActDefId);
					bpmNodeScriptDao.add(nS);
				}
			}
			
			//BPM_USER_CONDITION 流程节点人员规则 OK
			List<BpmUserCondition> userConditionList = bpmUserConditionDao.getBySetId(oldSetId);
			if(BeanUtils.isNotEmpty(userConditionList)){
				for(BpmUserCondition oC:userConditionList){
					BpmUserCondition nC=(BpmUserCondition)oC.clone();
					nC.setId(UniqueIdUtil.genId());
					nC.setActdefid(newActDefId);
					nC.setSetId(bpmNodeSet.getSetId());
					bpmUserConditionDao.add(nC);
					//BPM_NODE_USER 	流程节点人员 OK
					List<BpmNodeUser> nodeUserList = bpmNodeUserDao.getBySetIdAndConditionId(oC.getSetId(), oC.getId());
					if(BeanUtils.isNotEmpty(nodeUserList)){
						for(BpmNodeUser oU:nodeUserList){
							BpmNodeUser nU=(BpmNodeUser)oU.clone();
							
							nU.setNodeUserId(UniqueIdUtil.genId());
							nU.setActDefId(newActDefId);
							nU.setSetId(bpmNodeSet.getSetId());
							nU.setConditionId(nC.getId());
							bpmNodeUserDao.add(nU);
						}
					}
				}
			}
			
			//BPM_NODE_MESSAGE	流程消息节点 OK
			List<BpmNodeMessage> nodeMessageList=bpmNodeMessageDao.getByActDefId(oldActDefId);
			if(BeanUtils.isNotEmpty(nodeMessageList)){
				for(BpmNodeMessage oM:nodeMessageList){
					BpmNodeMessage nM=(BpmNodeMessage)oM.clone();
					nM.setId(UniqueIdUtil.genId());
					nM.setActDefId(newActDefId);
					bpmNodeMessageDao.add(nM);
				}
			}
			
			//BPM_NODE_SIGN	任务会签设置 OK
			BpmNodeSign nodeSign= bpmNodeSignDao.getByDefIdAndNodeId(oldActDefId,nodeId);
			if(BeanUtils.isNotEmpty(nodeSign)){
				BpmNodeSign newSign=(BpmNodeSign)nodeSign.clone();
				newSign.setSignId(UniqueIdUtil.genId());
				newSign.setActDefId(newActDefId);
				bpmNodeSignDao.add(newSign);
			}
			
			//BPM_NODE_BTN 操作按钮节点设置
			List<BpmNodeButton> nodeButtonList = bpmNodeButtonDao.getByDefNodeId(oldDefId, nodeId);
			if(BeanUtils.isNotEmpty(nodeButtonList)){
				for(BpmNodeButton oB:nodeButtonList){
					BpmNodeButton nB=oB;
					nB.setId(UniqueIdUtil.genId());
					nB.setActdefid(newActDefId);
					nB.setDefId(newDefId);
					bpmNodeButtonDao.add(nB);
				}
			}
			//BPM_APPROVAL_ITEM 非全局常用语
			TaskApprovalItems approvalItems=taskApprovalItemsDao.getTaskApproval(oldActDefId, nodeId, TaskApprovalItems.notGlobal);
			if(BeanUtils.isNotEmpty(approvalItems)){
				approvalItems.setActDefId(newActDefId);
				approvalItems.setSetId(bpmNodeSet.getSetId());
				approvalItems.setItemId(UniqueIdUtil.genId());	
				taskApprovalItemsDao.add(approvalItems);
			}
			//BPM_TASK_DUE 催办
			
		//	TaskReminder taskReminder = taskReminderDao.getByActDefAndNodeId(oldActDefId, nodeId);
//			TaskReminder taskReminder = null;
			List<TaskReminder> taskReminders = taskReminderDao.getByActDefAndNodeId(oldActDefId, nodeId);
			for(TaskReminder taskReminder:taskReminders){
				taskReminder.setActDefId(newActDefId);
				taskReminder.setTaskDueId(UniqueIdUtil.genId());
				taskReminderDao.add(taskReminder);
			}
		}
		
	}
	
	/**
	 * 同步起始节点表单的配置情况。
	 * @param oldDefId
	 * @param newDefId
	 * @param newActDefId
	 * @throws Exception
	 */
	private void  syncStartGlobal(Long oldDefId,Long newDefId,String newActDefId) throws Exception{
		//同步流程起始表单和默认表单配置。
		List<BpmNodeSet> list=bpmNodeSetDao.getByStartGlobal(oldDefId);
		for(BpmNodeSet nodeSet:list){
			nodeSet.setSetId(UniqueIdUtil.genId());
			nodeSet.setDefId(newDefId);
			nodeSet.setActDefId(newActDefId);
			bpmNodeSetDao.add(nodeSet);
		}
	}
	
	

	
	/**
	 * 取得某个流程下的所有历史版本的定义
	 * @param defId
	 * @return
	 */
	public List<BpmDefinition> getAllHistoryVersions(Long defId)
	{
		return dao.getByParentDefIdIsMain(defId,BpmDefinition.NOT_MAIN);
	}
	
	/**
	 * 根据ACT流程定义id获取流程定义。
	 * @param actDefId
	 * @return
	 */
	public BpmDefinition getByActDefId(String actDefId)
	{
		return dao.getByActDefId(actDefId);
	}
	
	/**
	 * 根据分类Id得到流程定义
	 * @param typeId
	 * @return
	 */
	public List<BpmDefinition> getByTypeId(Long typeId)
	{
		return dao.getByTypeId(typeId);
	}
	
	/**
	 * 用于查询管理员下的所有流程
	 * @param queryFilter
	 * @return
	 */
	public List<BpmDefinition> getAllForAdmin(QueryFilter queryFilter)
	{
		return dao.getAllForAdmin(queryFilter);
	}
	
	/**
	 * 设置标题规则。
	 * @param defId
	 * @param taskNameRule
	 * @return
	 */
	public int saveParam(BpmDefinition bpmDefinition){
		return dao.saveParam(bpmDefinition);
	}
	/**
	 * 删除流程定义
	 * @param flowDefId
	 * @param isOnlyVersion 是否仅删除本版本，不包括其他历史版本
	 */
	public void delDefbyDeployId(Long flowDefId,boolean isOnlyVersion){
		
		 if(BeanUtils.isEmpty(flowDefId))return;
	        
        BpmDefinition definition=dao.getById(flowDefId);
        //若该版本尚没有发布
        if(definition.getActDeployId()==null){
        	delById(definition.getDefId());
        	return;
        }
        
        if(isOnlyVersion){//仅删除该版本
        	delBpmDefinition(definition);
        	return;
        }
        
        String actFlowKey=definition.getActDefKey();
        
        List<BpmDefinition> list=dao.getByActDefKey(actFlowKey);
        
       
		//删除流程系统表
		for(BpmDefinition bpmDefinition:list){
		    
			delBpmDefinition(bpmDefinition);
		}
        
		
	}
	
	private void delBpmDefinition(BpmDefinition bpmDefinition){
		Long actDeployId=bpmDefinition.getActDeployId();	
		Long defId=bpmDefinition.getDefId();
		String actDefId=bpmDefinition.getActDefId();
		if(StringUtil.isNotEmpty(actDefId)){
			
			//删除流程运行实例表单数据BPM_FORM_RUN 
			bpmFormRunDao.delByActDefId(actDefId);
			//删除节点操作的按钮BPM_NODE_BTN
			bpmNodeButtonDao.delByActDefId(actDefId);
			//删除节点消息BPM_NODE_MESSAGE
    		bpmNodeMessageDao.delByActDefId(actDefId);
			//删除节点特权BPM_NODE_PRIVILEGE 
			bpmNodePrivilegeDao.delByActDefId(actDefId);
			//删除节点规则BPM_NODE_RULE
    		bpmNodeRuleDao.delByActDefId(actDefId);
    		//删除节点运行脚本BPM_NODE_SCRIPT
    		bpmNodeScriptDao.delByActDefId(actDefId);
    		//删除webservice节点设置BPM_NODE_WEBSERVICE
    		bpmNodeWebServiceDao.delByActDefId(actDefId);
    		//删除流程节点状态BPM_PRO_STATUS
    		bpmProStatusDao.delByActDefId(actDefId);
    		//删除定义催办信息BPM_TASK_DUE
    		taskReminderDao.delByActDefId(actDefId);
    		//删除分发实体BPM_TASK_FORK
    		taskForkService.delByActDefId(actDefId);
    		//删除流程运行实例BPM_PRO_RUN同时删除交办任务BPM_TASK_ASSIGNEE,BPM_PRO_RUN_HIS,ACT_RU_IDENTITYLINK
    		//ACT_RU_TASK,ACT_RU_VARIABLE,ACT_RU_EXECUTION
			processRunService.delByActDefId(actDefId);
			//删除流程意见信息BPM_TASK_OPINION 
			taskOpinionDao.delByActDefId(actDefId);
			//删除任务提醒状态数据BPM_TASK_REMINDERSTATE 
			reminderStateDao.delByActDefId(actDefId);
			//删除节点人员规则BPM_USER_CONDITION 
			bpmUserConditionDao.delByActDefId(actDefId);
			//删除流程代理BPM_AGENT，SYS_USER_AGENT
			bpmAgentService.delByActDefId(actDefId);
    		//删除BPM_EXE_STACK
    		executionStackDao.delByActDefId(actDefId);
    		//删除BPM_TASK_COMMENT
    		bpmTaskCommentDao.delByactDefId(actDefId);
    		//删除BPM_TKSIGN_DATA
    		taskSignDataDao.delByIdActDefId(actDefId);
		}
		if(actDeployId!=null && actDeployId>0){
			//删除删除流程定义数据表ACT_GE_BYTEARRAY，ACT_RE_DEPLOYMENT，ACT_RE_PROCDEF
		    dao.delProcDefByActDeployId(actDeployId);
		}
		//删除bpmDefRight
		bpmDefRightDao.delByDefKey(bpmDefinition.getDefKey());
		
		//删除bpm_def_vars
		bpmDefVarsDao.delByDefId(defId);
		//删除节点数据BPM_NODE_SET 
		bpmNodeSetDao.delByDefId(defId);
		
		dao.delById(defId);
	}
	
	/**
	 * 导入数据库
	 * 
	 * @param inputStream
	 * @throws Exception
	 */
	public void importXml(InputStream inputStream) throws Exception {
		Document doc = Dom4jUtil.loadXml(inputStream);
		Element root = doc.getRootElement();
		// 验证格式是否正确
		checkXMLFormat(root);

		String xmlStr = root.asXML();
		BpmDefinitionXmlList bpmDefinitionXmlList = (BpmDefinitionXmlList) XmlBeanUtil
				.unmarshall(xmlStr, BpmDefinitionXmlList.class);
		List<BpmDefinitionXml> list = bpmDefinitionXmlList
				.getBpmDefinitionXmlList();
		// 保存相关信息
		for (BpmDefinitionXml bpmDefinitionXml : list) {
			this.importBpmDefinitionXml(bpmDefinitionXml);
			MsgUtil.addSplit();
		}

	}

	/**
	 * 检查XML格式是否正确
	 * 
	 * @param root
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void checkXMLFormat(Element root) throws Exception {
		String msg = "导入文件格式不对";
		if (!root.getName().equals("bpm"))
			throw new Exception(msg);
		List<Element> itemLists = root.elements();
		for (Element elm : itemLists) {
			if (!elm.getName().equals("bpmDefinitions"))
				throw new Exception(msg);
		}
	}

	/**
	 * 解析相关信息，导入相关list并保持数据库
	 * 
	 * @param bpmDefinitionXml
	 * @throws Exception
	 */
	private void importBpmDefinitionXml(BpmDefinitionXml bpmDefinitionXml)
			throws Exception {

		// 导入自定义表
		List<BpmFormTableXml> bpmFormTableXmlList = bpmDefinitionXml
				.getBpmFormTableXmlList();
		if (BeanUtils.isNotEmpty(bpmFormTableXmlList)) {
			for (BpmFormTableXml bpmFormTableXml : bpmFormTableXmlList) {
				bpmFormTableService.importBpmFormTable(bpmFormTableXml,
						new Long(0));
			}
		}

		// 导入自定义表单
		List<BpmFormDefXml> bpmFormDefXmlList = bpmDefinitionXml
				.getBpmFormDefXmlList();
		if (BeanUtils.isNotEmpty(bpmFormDefXmlList))
			for (BpmFormDefXml bpmFormDefXml : bpmFormDefXmlList) {
				bpmFormDefService.importBpmFormDef(bpmFormDefXml,
						BpmFormDef.IS_DEFAULT);
			}
		// 导入流程定义
		this.importBpmDefinition(bpmDefinitionXml, null);
	}

	/**
	 * 解析相关信息，导入相关list并保持数据库
	 * 
	 * <pre>
	 * 导入以下信息:
	 * ■ 流程定义 bpmDefinition
	 * ■ 历史版本 bpmDefinitionHistory
	 * 	
	 * ■ 流程节点设置 bpmNodeSet
	 * ■ 节点下的人员设置  bpmNodeUser
	 * ■ 节点下的人员的配置规则 bpmUserCondition
	 * ■ 节点下的人员上下级设置 bpmNodeUserUplow
	 * 	
	 * ■ 流程定义权限 bpmDefRights
	 * ■ 常用语设置 taskApprovalItems
	 * 	
	 * ■ 流程跳转规则  bpmNodeRule
	 * ■ 流程事件脚本  bpmNodeScript
	 * 	
	 * ■ 流程操作按钮设置 bpmNodeButton
	 * ■ 流程变量  bpmDefVar
	 * 	 
	 * ■ 流程消息  bpmNodeMessage
	 * ■ 流程会签规则  bpmNodeSign
	 * 
	 * ■ 任务节点催办时间设置 taskReminder
	 * ■ 内（外）部子流程 subBpmDefinition
	 * </pre>
	 * 
	 * @param bpmDefinitionXml
	 *            导入的流程定义的xml
	 * @param parentDefId
	 *            主流程定义的id
	 * @throws Exception
	 */
	private void importBpmDefinition(BpmDefinitionXml bpmDefinitionXml,
			Long parentDefId) throws Exception {

		BpmDefinition bpmDefinition = bpmDefinitionXml.getBpmDefinition();
		BpmDefinition definition = dao.getById(bpmDefinition.getDefId());
		if (BeanUtils.isEmpty(definition)) {
			// 流程定义
			this.importDefinition(bpmDefinition, parentDefId);
		} else {
			MsgUtil.addMsg(MsgUtil.WARN, "流程定义,ID:" + bpmDefinition.getDefId()
					+ "已经存在,该记录终止导入！");
			return;
		}
		String actDefId = bpmDefinition.getActDefId();
		Long defId = bpmDefinition.getDefId();
		// Long actDeployId = bpmDefinition.getActDeployId();
		// 流程定义历史版本
		List<BpmDefinitionXml> bpmDefinitionXmlList = bpmDefinitionXml
				.getBpmDefinitionXmlList();
		if (BeanUtils.isNotEmpty(bpmDefinitionXmlList)) {
			// 历史版本版本导入
			for (BpmDefinitionXml definitionXml : bpmDefinitionXmlList) {
				this.importBpmDefinition(definitionXml, defId);
			}
		}

		// 含有子流程
		List<BpmDefinitionXml> subBpmDefinitionXmlList = bpmDefinitionXml
				.getSubBpmDefinitionXmlList();

		if (BeanUtils.isNotEmpty(subBpmDefinitionXmlList)) {
			// 子流程导入
			for (BpmDefinitionXml definitionXml : subBpmDefinitionXmlList) {
				this.importBpmDefinition(definitionXml, null);
			}
		}

		// 表单权限
		List<BpmDefRights> bpmDefRightsList = bpmDefinitionXml
				.getBpmDefRightsList();
		if (BeanUtils.isNotEmpty(bpmDefRightsList))
			this.importBpmDefRights(bpmDefRightsList, defId);

		// 流程跳转规则
		List<BpmNodeRule> bpmNodeRuleList = bpmDefinitionXml
				.getBpmNodeRuleList();
		if (BeanUtils.isNotEmpty(bpmNodeRuleList))
			this.importBpmNodeRule(bpmNodeRuleList, actDefId);

		// 流程事件脚本
		List<BpmNodeScript> bpmNodeScriptList = bpmDefinitionXml
				.getBpmNodeScriptList();
		if (BeanUtils.isNotEmpty(bpmNodeScriptList))
			this.importBpmNodeScript(bpmNodeScriptList, actDefId);

		// 流程变量
		List<BpmDefVar> bpmDefVarList = bpmDefinitionXml.getBpmDefVarList();
		if (BeanUtils.isNotEmpty(bpmDefVarList))
			this.importBpmDefVar(bpmDefVarList, defId);

		// 流程会签规则
		List<BpmNodeSign> bpmNodeSignList = bpmDefinitionXml
				.getBpmNodeSignList();
		if (BeanUtils.isNotEmpty(bpmNodeSignList))
			this.importBpmNodeSign(bpmNodeSignList, actDefId);

		// 流程消息
		List<BpmNodeMessage> bpmNodeMessageList = bpmDefinitionXml
				.getBpmNodeMessageList();
		this.importBpmNodeMessage(bpmNodeMessageList, actDefId);
		// List<Message> messageList =
		// messageDao.getByActDefId(actDefId);
		// 常用语设置
		List<TaskApprovalItems> taskApprovalItemsList = bpmDefinitionXml
				.getTaskApprovalItemsList();
		// 节点下的人员设置
		List<BpmNodeUser> bpmNodeUserList = bpmDefinitionXml
				.getBpmNodeUserList();
		// 用户节点的上下级设置
		List<BpmNodeUserUplow> bpmNodeUserUplowList = bpmDefinitionXml
				.getBpmNodeUserUplowList();
		List<BpmUserCondition> bpmUserConditionList = bpmDefinitionXml
				.getBpmUserConditionList();
		if (BeanUtils.isNotEmpty(bpmUserConditionList))
			this.importBpmUserCondition(bpmUserConditionList, defId, actDefId);
		// 流程节点设置
		List<BpmNodeSet> bpmNodeSetList = bpmDefinitionXml.getBpmNodeSetList();
		if (BeanUtils.isNotEmpty(bpmNodeSetList))
			this.importBpmNodeSet(bpmNodeSetList, bpmNodeUserList,
					bpmNodeUserUplowList, taskApprovalItemsList, defId,
					actDefId);

		// 流程操作按钮设置
		List<BpmNodeButton> bpmNodeButtonList = bpmDefinitionXml
				.getBpmNodeButtonList();
		if (BeanUtils.isNotEmpty(bpmNodeButtonList))
			this.importBpmNodeButton(bpmNodeButtonList, defId, actDefId);

		// 任务节点催办时间设置
		List<TaskReminder> taskReminderList = bpmDefinitionXml
				.getTaskReminderList();
		if (BeanUtils.isNotEmpty(taskReminderList))
			this.importTaskReminder(taskReminderList, actDefId);
	}

	/**
	 * 导入流程定义
	 * 
	 * @param bpmDefinition
	 * 
	 * @return
	 */
	private void importDefinition(BpmDefinition bpmDefinition, Long parentDefId)
			throws Exception {
		String actFlowDefXml = BpmUtil.transform(bpmDefinition.getDefKey(),
				bpmDefinition.getSubject(), bpmDefinition.getDefXml());
		this.saveBpmDefinition(bpmDefinition, actFlowDefXml);
	}

	/**
	 * 保存及更新流程定义
	 * 
	 * @param bpmDefinition
	 *            流程定义实体
	 * @param actFlowDefXml
	 *            流程定义bpmn文档
	 * @throws Exception
	 */
	private void saveBpmDefinition(BpmDefinition bpmDefinition,
			String actFlowDefXml) throws Exception {
		Long id = bpmDefinition.getDefId();
		BpmDefinition definition = getById(id);

		if (BeanUtils.isEmpty(definition)) {
			// 新增加的流程
			Deployment deployment = bpmService.deploy(
					bpmDefinition.getSubject(), actFlowDefXml);
			ProcessDefinitionEntity ent = bpmService
					.getProcessDefinitionByDeployId(deployment.getId());
			// 设置分类
			bpmDefinition = this.setTypeId(bpmDefinition);
			bpmDefinition.setActDeployId(new Long(deployment.getId()));
			bpmDefinition.setActDefId(ent.getId());
			bpmDefinition.setActDefKey(ent.getKey());
			add(bpmDefinition);
			MsgUtil.addMsg(MsgUtil.SUCCESS, "流程定义,ID:" + id + ",该记录成功导入！");
		} else {
			MsgUtil.addMsg(MsgUtil.WARN, "流程定义,ID:" + id + "已经存在,该记录终止导入！");

		}

	}

	/**
	 * 设置分类
	 * 
	 * @param bpmFormDef
	 * @return
	 */
	private BpmDefinition setTypeId(BpmDefinition bpmDefinition)
			throws Exception {
		if (BeanUtils.isEmpty(bpmDefinition.getTypeId()))
			return bpmDefinition;
		GlobalType globalType = globalTypeDao
				.getById(bpmDefinition.getTypeId());
		if (BeanUtils.isEmpty(globalType))
			bpmDefinition.setTypeId(null);
		return bpmDefinition;
	}

	/**
	 * 任务节点催办时间设置
	 * 
	 * @param taskReminderList
	 * @param actDefId
	 */
	private void importTaskReminder(List<TaskReminder> taskReminderList,
			String actDefId) throws Exception {
		for (TaskReminder taskReminder : taskReminderList) {
			Long id = taskReminder.getTaskDueId();
			TaskReminder reminder = taskReminderDao.getById(id);
			if (BeanUtils.isEmpty(reminder)) {
				taskReminder.setActDefId(actDefId);
				taskReminderDao.add(taskReminder);
				MsgUtil.addMsg(MsgUtil.SUCCESS, "任务节点催办时间设置,ID:" + id
						+ ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(MsgUtil.WARN, "任务节点催办时间设置,ID:" + id
						+ "已经存在,该记录终止导入！");
			}
		}
	}

	/**
	 * 流程操作按钮设置
	 * 
	 * @param bpmNodeButtonList
	 * @param defId
	 * @param actDefId
	 */
	private void importBpmNodeButton(List<BpmNodeButton> bpmNodeButtonList,
			Long defId, String actDefId) throws Exception {
		for (BpmNodeButton bpmNodeButton : bpmNodeButtonList) {
			Long id = bpmNodeButton.getId();
			BpmNodeButton nodeButton = bpmNodeButtonDao.getById(id);
			if (BeanUtils.isEmpty(nodeButton)) {
				bpmNodeButton.setDefId(defId);
				bpmNodeButton.setActdefid(actDefId);
				bpmNodeButtonDao.add(bpmNodeButton);
				MsgUtil.addMsg(MsgUtil.SUCCESS, "流程操作按钮设置,ID:" + id
						+ ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(MsgUtil.WARN, "流程操作按钮设置,ID:" + id
						+ "已经存在,该记录终止导入！");
			}
		}
	}

	/**
	 * 流程节点设置
	 * 
	 * @param bpmNodeSetList
	 * @param bpmNodeUserList
	 * @param bpmNodeUserUplowList
	 * @param taskApprovalItemsList
	 * @param defId
	 * @param actDefId
	 * @param formKeyMap
	 */
	private void importBpmNodeSet(List<BpmNodeSet> bpmNodeSetList,
			List<BpmNodeUser> bpmNodeUserList,
			List<BpmNodeUserUplow> bpmNodeUserUplowList,
			List<TaskApprovalItems> taskApprovalItemsList, Long defId,
			String actDefId) throws Exception {

		List<TaskApprovalItems> approvalItemsList = new ArrayList<TaskApprovalItems>();
		if (BeanUtils.isNotEmpty(taskApprovalItemsList)) {
			for (TaskApprovalItems taskApprovalItems : taskApprovalItemsList) {
				if (BeanUtils.isNotEmpty(taskApprovalItems.getSetId()))
					approvalItemsList.add(taskApprovalItems);
				else
					this.importTaskApprovalItems(taskApprovalItems, actDefId,
							null);
			}
		}

		for (BpmNodeSet bpmNodeSet : bpmNodeSetList) {
			Long setId = bpmNodeSet.getSetId();
			// 流程节点设置
			Long nodeSetId = this.importBpmNodeSet(bpmNodeSet, defId, actDefId,
					setId);

			for (BpmNodeUser bpmNodeUser : bpmNodeUserList) {
				if (setId.longValue() == bpmNodeUser.getSetId().longValue()) {
					Long userId = bpmNodeUser.getNodeUserId();
					// 节点下的人员设置
					Long nodeUserId = this.importBpmNodeUser(bpmNodeUser,
							actDefId, nodeSetId);
					if(bpmNodeUserUplowList!=null){
						for (BpmNodeUserUplow bpmNodeUserUplow : bpmNodeUserUplowList) {
							if (userId.longValue() == bpmNodeUserUplow
									.getNodeUserId().longValue()) {
								// 用户节点的上下级设置
								this.importBpmNodeUserUplow(bpmNodeUserUplow,
										nodeUserId);
							}
						}
					}
				}
			}
			// 常用语设置
			for (TaskApprovalItems taskApprovalItems : approvalItemsList) {
				if (setId.longValue() == taskApprovalItems.getSetId()
						.longValue()) {
					this.importTaskApprovalItems(taskApprovalItems, actDefId,
							nodeSetId);
				}
			}
		}
	}

	/**
	 * 常用语设置
	 * 
	 * @param taskApprovalItems
	 * @param actDefId
	 * @param setId
	 */
	private void importTaskApprovalItems(TaskApprovalItems taskApprovalItems,
			String actDefId, Long setId) throws Exception {
		Long id = taskApprovalItems.getItemId();
		TaskApprovalItems approvalItems = taskApprovalItemsDao.getById(id);
		if (BeanUtils.isEmpty(approvalItems)) {
			taskApprovalItems.setSetId(setId);
			taskApprovalItems.setActDefId(actDefId);
			taskApprovalItemsDao.add(taskApprovalItems);
			MsgUtil.addMsg(MsgUtil.SUCCESS, "常用语设置,ID:" + id + ",该记录成功导入！");
		} else {
			MsgUtil.addMsg(MsgUtil.WARN, "常用语设置,ID:" + id + "已经存在,该记录终止导入！");
		}
	}

	/**
	 * 流程节点设置
	 * 
	 * @param bpmNodeSet
	 * @param defId
	 * @param actDefId
	 * @param setId
	 * @return
	 */
	private Long importBpmNodeSet(BpmNodeSet bpmNodeSet, Long defId,
			String actDefId, Long setId) throws Exception {

		Long id = bpmNodeSet.getSetId();
		BpmNodeSet nodeSet = bpmNodeSetDao.getById(id);
		if (BeanUtils.isEmpty(nodeSet)) {
			bpmNodeSet.setDefId(defId);
			bpmNodeSet.setActDefId(actDefId);
			bpmNodeSetDao.add(bpmNodeSet);
			MsgUtil.addMsg(MsgUtil.SUCCESS, "流程节点设置,ID:" + id + ",该记录成功导入！");
		} else {
			MsgUtil.addMsg(MsgUtil.WARN, "流程节点设置,ID:" + id + "已经存在,该记录终止导入！");
		}
		return id;
	}

	/**
	 * 流程节点设置
	 * 
	 * @param bpmUserConditionList
	 * @param defId
	 * @param actDefId
	 * @return
	 */
	private void importBpmUserCondition(
			List<BpmUserCondition> bpmUserConditionList, Long defId,
			String actDefId) throws Exception {
		for (BpmUserCondition bpmUserCondition : bpmUserConditionList) {
			Long id = bpmUserCondition.getId();
			BpmUserCondition userCondition = bpmUserConditionDao.getById(id);
			if (BeanUtils.isEmpty(userCondition)) {
				bpmUserCondition.setActdefid(actDefId);
				bpmUserConditionDao.add(bpmUserCondition);
				MsgUtil.addMsg(MsgUtil.SUCCESS, "流程节点设置,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(MsgUtil.WARN, "流程节点设置,ID:" + id
						+ "已经存在,该记录终止导入！");
			}
		}

	}

	/**
	 * 节点下的人员设置
	 * 
	 * @param bpmNodeUser
	 * @param actDefId
	 * @param setId
	 * @return
	 */
	private Long importBpmNodeUser(BpmNodeUser bpmNodeUser, String actDefId,
			Long setId) throws Exception {
		Long id = bpmNodeUser.getNodeUserId();
		BpmNodeUser nodeUser = bpmNodeUserDao.getById(id);
		if (BeanUtils.isEmpty(nodeUser)) {
			bpmNodeUser.setActDefId(actDefId);
			bpmNodeUser.setSetId(setId);
			bpmNodeUserDao.add(bpmNodeUser);
			MsgUtil.addMsg(MsgUtil.SUCCESS, "节点下的人员设置,ID:" + id + ",该记录成功导入！");
		} else {
			MsgUtil.addMsg(MsgUtil.WARN, "节点下的人员设置,ID:" + id + "已经存在,该记录终止导入！");
		}
		return id;
	}

	/**
	 * 用户节点的上下级设置
	 * 
	 * @param bpmNodeUserUplow
	 * @param nodeUserId
	 */
	private void importBpmNodeUserUplow(BpmNodeUserUplow bpmNodeUserUplow,
			Long nodeUserId) throws Exception {
		Long id = bpmNodeUserUplow.getID();
		BpmNodeUserUplow nodeUserUplow = bpmNodeUserUplowDao.getById(id);
		if (BeanUtils.isEmpty(nodeUserUplow)) {
			bpmNodeUserUplow.setNodeUserId(nodeUserId);
			bpmNodeUserUplowDao.add(bpmNodeUserUplow);
			MsgUtil.addMsg(MsgUtil.SUCCESS, "用户节点的上下级设置,ID:" + id + ",该记录成功导入！");
		} else {
			MsgUtil.addMsg(MsgUtil.WARN, "用户节点的上下级设置,ID:" + id
					+ "已经存在,该记录终止导入！");
		}
	}

	/**
	 * 流程消息设置
	 * 
	 * @param bpmNodeMessageList
	 * @param actDefId
	 */
	private void importBpmNodeMessage(List<BpmNodeMessage> bpmNodeMessageList,
			String actDefId) throws Exception {
		for (BpmNodeMessage bpmNodeMessage : bpmNodeMessageList) {
			Long id = bpmNodeMessage.getId();
			BpmNodeMessage nodeMessage = bpmNodeMessageDao.getById(id);
			if (BeanUtils.isEmpty(nodeMessage)) {
				bpmNodeMessage.setActDefId(actDefId);
				bpmNodeMessageDao.add(bpmNodeMessage);
				MsgUtil.addMsg(MsgUtil.SUCCESS, "流程消息设置,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(MsgUtil.WARN, "流程消息设置,ID:" + id
						+ "已经存在,该记录终止导入！");
			}
		}

	}

	/**
	 * 流程会签规则
	 * 
	 * @param bpmNodeSignList
	 * @param actDefId
	 */
	private void importBpmNodeSign(List<BpmNodeSign> bpmNodeSignList,
			String actDefId) throws Exception {
		for (BpmNodeSign bpmNodeSign : bpmNodeSignList) {
			Long id = bpmNodeSign.getSignId();
			BpmNodeSign nodeSign = bpmNodeSignDao.getById(id);
			if (BeanUtils.isEmpty(nodeSign)) {
				bpmNodeSign.setActDefId(actDefId);
				bpmNodeSignDao.add(bpmNodeSign);
				MsgUtil.addMsg(MsgUtil.SUCCESS, "流程会签规则,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(MsgUtil.WARN, "流程会签规则,ID:" + id
						+ "已经存在,该记录终止导入！");
			}
		}

	}

	/**
	 * 流程变量
	 * 
	 * @param bpmDefVarList
	 * @param defId
	 */
	private void importBpmDefVar(List<BpmDefVar> bpmDefVarList, Long defId)
			throws Exception {
		for (BpmDefVar bpmDefVar : bpmDefVarList) {
			Long id = bpmDefVar.getVarId();
			BpmDefVar defVar = bpmDefVarDao.getById(id);
			if (BeanUtils.isEmpty(defVar)) {
				bpmDefVar.setDefId(defId);
				bpmDefVarDao.add(bpmDefVar);
				MsgUtil.addMsg(MsgUtil.SUCCESS, "流程变量,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(MsgUtil.WARN, "流程变量,ID:" + id + "已经存在,该记录终止导入！");
			}
		}

	}

	/**
	 * 流程事件脚本
	 * 
	 * @param bpmNodeScriptList
	 * @param actDefId
	 */
	private void importBpmNodeScript(List<BpmNodeScript> bpmNodeScriptList,
			String actDefId) throws Exception {
		for (BpmNodeScript bpmNodeScript : bpmNodeScriptList) {
			Long id = bpmNodeScript.getId();
			BpmNodeScript nodeScript = bpmNodeScriptDao.getById(id);
			if (BeanUtils.isEmpty(nodeScript)) {
				bpmNodeScript.setActDefId(actDefId);
				bpmNodeScriptDao.add(bpmNodeScript);
				MsgUtil.addMsg(MsgUtil.SUCCESS, "流程事件脚本,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(MsgUtil.WARN, "流程事件脚本,ID:" + id
						+ "已经存在,该记录终止导入！");
			}
		}

	}

	/**
	 * 流程跳转规则
	 * 
	 * @param bpmNodeRuleList
	 * @param actDefId
	 */
	private void importBpmNodeRule(List<BpmNodeRule> bpmNodeRuleList,
			String actDefId) throws Exception {
		for (BpmNodeRule bpmNodeRule : bpmNodeRuleList) {
			Long id = bpmNodeRule.getRuleId();
			BpmNodeRule nodeRule = bpmNodeRuleDao.getById(id);
			if (BeanUtils.isEmpty(nodeRule)) {
				bpmNodeRule.setActDefId(actDefId);
				bpmNodeRuleDao.add(bpmNodeRule);
				MsgUtil.addMsg(MsgUtil.SUCCESS, "流程跳转规则,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(MsgUtil.WARN, "流程跳转规则,ID:" + id
						+ "已经存在,该记录终止导入！");
			}
		}
	}

	/**
	 * 流程定义权限
	 * 
	 * @param bpmDefRightsList
	 * @param defId
	 */
	private void importBpmDefRights(List<BpmDefRights> bpmDefRightsList,
			Long defId) throws Exception {
		for (BpmDefRights bpmDefRights : bpmDefRightsList) {
			Long id = bpmDefRights.getId();
			BpmDefRights defRights = bpmDefRightsDao.getById(id);
			if (BeanUtils.isEmpty(defRights)) {
				bpmDefRights.setDefKey(String.valueOf(defId) );
				bpmDefRightsDao.add(bpmDefRights);
				MsgUtil.addMsg(MsgUtil.SUCCESS, "表单权限,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(MsgUtil.WARN, "表单权限,ID:" + id + "已经存在,该记录终止导入！");
			}
		}
	}

	/**
	 * 导出XML。
	 * 
	 * <pre>
	 * 导出以下 信息:
	 * 	
	 * ■ 流程定义 bpmDefinition
	 * ■ 历史版本 bpmDefinitionHistory
	 * 	
	 * ■ 流程节点设置 bpmNodeSet
	 * ■ 节点下的人员的配置规则 bpmUserCondition
	 * ■ 节点下的人员设置  bpmNodeUser
	 * ■ 节点下的人员上下级设置 bpmNodeUserUplow
	 * 	
	 * ■ 流程定义权限 bpmDefRights
	 * ■ 常用语设置 taskApprovalItems
	 * 	
	 * ■ 流程跳转规则  bpmNodeRule
	 * ■ 流程事件脚本  bpmNodeScript
	 * 	
	 * ■ 流程操作按钮设置 bpmNodeButton
	 * ■ 流程变量  bpmDefVar
	 * 	 
	 * ■ 流程消息  bpmNodeMessage
	 * ■ 流程会签规则  bpmNodeSign
	 * 
	 * ■ 任务节点催办时间设置 taskReminder
	 * ■ 内（外）部子流程 subBpmDefinition
	 * </pre>
	 * 
	 * @param Long
	 *            [] bpmDefIds
	 * @param map
	 * @return
	 */
	public String exportXml(Long[] bpmDefIds, Map<String, Boolean> map)
			throws Exception {
		BpmDefinitionXmlList bpmDefinitionXmlList = new BpmDefinitionXmlList();
		List<BpmDefinitionXml> list = new ArrayList<BpmDefinitionXml>();
		for (int i = 0; i < bpmDefIds.length; i++) {
			// 流程定义
			BpmDefinition definition = dao.getById(bpmDefIds[i]);
			BpmDefinitionXml bpmDefinitionXml = this.exportBpmDefinition(
					definition, BpmDefinition.MAIN, map);

			list.add(bpmDefinitionXml);
		}
		bpmDefinitionXmlList.setBpmDefinitionXmlList(list);
		return XmlBeanUtil.marshall(bpmDefinitionXmlList,
				BpmDefinitionXmlList.class);
	}

	/**
	 * 导出流程定义
	 * 
	 * @param definition
	 * @param isMain
	 * @return
	 */
	private BpmDefinitionXml exportBpmDefinition(BpmDefinition definition,
			Short isMain, Map<String, Boolean> map) throws Exception {
		BpmDefinitionXml bpmDefinitionXml = new BpmDefinitionXml();
		if (BeanUtils.isEmpty(definition))
			return bpmDefinitionXml;
		if (BeanUtils.isEmpty(map))
			return bpmDefinitionXml;

		Long defId = definition.getDefId();
		String actDefId = definition.getActDefId();
		Long actDeployId = definition.getActDeployId();
		// 流程定义
		bpmDefinitionXml.setBpmDefinition(definition);

		// 设置流程定义 历史版本
		if (map.get("bpmDefinitionHistory")
				&& isMain.shortValue() == BpmDefinition.MAIN.shortValue())
			this.exportBpmDefinitionHistory(defId, bpmDefinitionXml, map);

		// 内（外）部子流程
		if (map.get("subBpmDefinition"))
			this.exportSubBpmDefinition(actDeployId, bpmDefinitionXml, map);

		// 流程定义权限
		if (map.get("bpmDefRights")) {
			List<BpmDefRights> bpmDefRightsList = bpmDefRightsDao
					.getByDefKey(String.valueOf(defId));
			bpmDefinitionXml.setBpmDefRightsList(bpmDefRightsList);
		}

		// 流程跳转规则
		if (map.get("bpmNodeRule")) {
			List<BpmNodeRule> bpmNodeRuleList = bpmNodeRuleDao
					.getByDefIdNodeId(actDefId, "");
			bpmDefinitionXml.setBpmNodeRuleList(bpmNodeRuleList);
		}

		// 流程事件脚本
		if (map.get("bpmNodeScript")) {
			List<BpmNodeScript> bpmNodeScriptList = bpmNodeScriptDao
					.getByBpmNodeScriptId("", actDefId);
			bpmDefinitionXml.setBpmNodeScriptList(bpmNodeScriptList);
		}

		// 流程变量
		if (map.get("bpmDefVar")) {
			List<BpmDefVar> bpmDefVarList = bpmDefVarDao.getByDefId(defId);
			bpmDefinitionXml.setBpmDefVarList(bpmDefVarList);
		}
		// 流程会签规则
		if (map.get("bpmNodeSign")) {
			List<BpmNodeSign> bpmNodeSignList = bpmNodeSignDao
					.getByActDefId(actDefId);
			bpmDefinitionXml.setBpmNodeSignList(bpmNodeSignList);
		}

		// 流程消息
		if (map.get("bpmNodeMessage")) {
			List<BpmNodeMessage> bpmNodeMessageList = bpmNodeMessageDao
					.getByActDefId(actDefId);
			bpmDefinitionXml.setBpmNodeMessageList(bpmNodeMessageList);
		}

		// List<Message> messageList =
		// messageDao.getByActDefId(actDefId);

		// 流程节点设置
		if (map.get("bpmNodeSet")) {
			// 流程节点设置
			List<BpmNodeSet> bpmNodeSetList = bpmNodeSetDao.getAllByDefId(defId);
			if(!map.get("bpmFormDef")){
				for(BpmNodeSet bpmNodeSet:bpmNodeSetList){
					bpmNodeSet.setFormKey(0l);
					bpmNodeSet.setFormDefName("");
				}
			}
			bpmDefinitionXml.setBpmNodeSetList(bpmNodeSetList);

			// 节点下的人员设置
			if (map.get("bpmNodeUser")) {
				List<BpmNodeUser> bpmNodeUserList = bpmNodeUserDao
						.getByActDefId(actDefId);
				bpmDefinitionXml.setBpmNodeUserList(bpmNodeUserList);

				// 节点下的人员上下级设置
					for (BpmNodeUser bpmNodeUser : bpmNodeUserList) {
						Long nodeUserId = bpmNodeUser.getNodeUserId();
						List<BpmNodeUserUplow> bpmNodeUserUplowList = bpmNodeUserUplowDao
								.getByNodeUserId(nodeUserId);
						if(BeanUtils.isNotEmpty(bpmNodeUserUplowList))
							bpmDefinitionXml
								.setBpmNodeUserUplowList(bpmNodeUserUplowList);
					}
					// 节点下的人员的配置规则
					List<BpmUserCondition> bpmUserConditionList = bpmUserConditionDao
							.getByActDefId(actDefId);
					if(BeanUtils.isNotEmpty(bpmUserConditionList))
						bpmDefinitionXml.setBpmUserConditionList(bpmUserConditionList);
			}

			// 自定义表单
			if (map.get("bpmFormDef")
					&& BeanUtils.isNotEmpty(bpmNodeSetList)) {
				Set<Long> tableIdSet = this.exportBpmFormDef(bpmNodeSetList,
						bpmDefinitionXml);

				// 自定义表
				if (map.get("bpmFormTable")
						&& BeanUtils.isNotEmpty(tableIdSet))
					this.exportBpmFormTable(tableIdSet, bpmDefinitionXml);
			}
		}

		// 流程操作按钮设置
		if (map.get("bpmNodeButton")) {
			List<BpmNodeButton> bpmNodeButtonList = bpmNodeButtonDao
					.getByDefId(defId);
			bpmDefinitionXml.setBpmNodeButtonList(bpmNodeButtonList);
		}

		// 常用语设置
		if (map.get("taskApprovalItems")) {
			List<TaskApprovalItems> taskApprovalItemsList = taskApprovalItemsDao
					.getByActDefId(actDefId);
			bpmDefinitionXml.setTaskApprovalItemsList(taskApprovalItemsList);
		}
		// 任务节点催办时间设置
		if (map.get("taskReminder")) {
			List<TaskReminder> taskReminderList = taskReminderDao
					.getByActDefId(actDefId);
			bpmDefinitionXml.setTaskReminderList(taskReminderList);
		}

		return bpmDefinitionXml;
	}

	/**
	 * 导出自定义表
	 * 
	 * @param tableIdSet
	 * @param bpmDefinitionXml
	 */
	private void exportBpmFormTable(Set<Long> tableIdSet,
			BpmDefinitionXml bpmDefinitionXml) {
		Map<String, Boolean> map = bpmFormTableService
				.getDefaultExportMap(null);
		List<BpmFormTableXml> bpmFormTableXmlList = new ArrayList<BpmFormTableXml>();
		for (Long tableId : tableIdSet) {
			BpmFormTable formTable = bpmFormTableService.getById(tableId);
			BpmFormTableXml bpmFormTableXml = bpmFormTableService.exportTable(
					formTable, map);
			bpmFormTableXmlList.add(bpmFormTableXml);
		}
		if (BeanUtils.isNotEmpty(bpmFormTableXmlList))
			bpmDefinitionXml.setBpmFormTableXmlList(bpmFormTableXmlList);
	}

	/**
	 * 导出自定义表单
	 * 
	 * @param bpmNodeSetList
	 * @param bpmDefinitionXml
	 * @return
	 */
	private Set<Long> exportBpmFormDef(List<BpmNodeSet> bpmNodeSetList,
			BpmDefinitionXml bpmDefinitionXml) {
		Map<String, Boolean> map = bpmFormDefService.getDefaultExportMap(null);
		// 不出现重复的formKey,取得流程定义key
		Set<Long> formKeySet = new HashSet<Long>();
		for (BpmNodeSet bpmNodeSet : bpmNodeSetList) {
			if(BeanUtils.isNotEmpty(bpmNodeSet.getFormKey()) && bpmNodeSet.getFormKey().longValue() > 0L)
				formKeySet.add(bpmNodeSet.getFormKey());
		}

		Set<Long> tableIdSet = new HashSet<Long>();
		// 自定义表单
		List<BpmFormDefXml> bpmFormDefXmlList = new ArrayList<BpmFormDefXml>();
		for (Long formKey : formKeySet) {
			// 设置自定义表单
			List<BpmFormDef> bpmFormDefList = bpmFormDefDao
					.getByFormKeyIsDefault(formKey, BpmFormDef.IS_DEFAULT);
			if (BeanUtils.isNotEmpty(bpmFormDefList)) {
				BpmFormDef bpmFormDef = bpmFormDefList.get(0);
				BpmFormDefXml bpmFormDefXml = bpmFormDefService
						.exportBpmFormDef(bpmFormDef, BpmFormDef.IS_DEFAULT,
								map);
				bpmFormDefXmlList.add(bpmFormDefXml);
				// 关联的自定义表ID
				if (BeanUtils.isNotEmpty(bpmFormDef)
						&& BeanUtils.isNotEmpty(bpmFormDef.getTableId())) {
					tableIdSet.add(bpmFormDef.getTableId());
				}
			}
		}
		if (BeanUtils.isNotEmpty(bpmFormDefXmlList))
			bpmDefinitionXml.setBpmFormDefXmlList(bpmFormDefXmlList);
		return tableIdSet;
	}

	/**
	 * 导出内（外）部子流程
	 * 
	 * @param actDeployId
	 * @param bpmDefinitionXml
	 * @param map
	 * @throws Exception
	 */
	private void exportSubBpmDefinition(Long actDeployId,
			BpmDefinitionXml bpmDefinitionXml, Map<String, Boolean> map)
			throws Exception {
		if (BeanUtils.isEmpty(actDeployId))
			return;
		List<BpmDefinitionXml> subBpmDefinitionXmlList = new ArrayList<BpmDefinitionXml>();
		String xml = bpmDao.getDefXmlByDeployId(actDeployId.toString());
		Set<String> keySet = NodeCache.parseXmlBySubKey(xml);
		for (String flowKey : keySet) {
			BpmDefinition bpmDefinition = dao.getByActDefKeyIsMain(flowKey);
			subBpmDefinitionXmlList.add(exportBpmDefinition(bpmDefinition,
					BpmDefinition.MAIN, map));
		}

		if (BeanUtils.isNotEmpty(subBpmDefinitionXmlList))
			bpmDefinitionXml
					.setSubBpmDefinitionXmlList(subBpmDefinitionXmlList);

	}

	/**
	 * 导出流程定义历史版本
	 * 
	 * @param defId
	 * @param bpmDefinitionXml
	 * @param map
	 * @throws Exception
	 */
	private void exportBpmDefinitionHistory(Long defId,
			BpmDefinitionXml bpmDefinitionXml, Map<String, Boolean> map)
			throws Exception {
		List<BpmDefinition> bpmDefinitionList = this
				.getAllHistoryVersions(defId);
		if (BeanUtils.isEmpty(bpmDefinitionList))
			return;
		List<BpmDefinitionXml> bpmDefinitionXmlList = new ArrayList<BpmDefinitionXml>();
		for (BpmDefinition bpmDefinition : bpmDefinitionList) {
			BpmDefinitionXml definitionXml = exportBpmDefinition(bpmDefinition,
					BpmDefinition.NOT_MAIN, map);
			bpmDefinitionXmlList.add(definitionXml);
		}
		bpmDefinitionXml.setBpmDefinitionXmlList(bpmDefinitionXmlList);

	}
	
	/**
	 * 根据流程key取得流程定义数据。
	 * @param flowKey
	 * @return
	 */
	public BpmDefinition getMainDefByActDefKey(String actDefKey){
		return dao.getByActDefKeyIsMain(actDefKey);
	}
	
	public List<BpmDefinition> getByUserId(QueryFilter queryFilter)
	{
		List<BpmDefinition> list= dao.getByUserId(queryFilter);
		return list;
	}
	
	/**
	 * 按用户Id及查询参数查找我能访问的所有流程定义
	 * @param queryFilter
	 * @return
	 */
	public List<BpmDefinition> getByUserIdFilter(QueryFilter queryFilter){
		return dao.getByUserIdFilter(queryFilter);
	}
	
	/**
	 * 判断流程key是否存在。
	 * @param key
	 * @return
	 */
	public boolean isActDefKeyExists(String key){
		return dao.isActDefKeyExists(key);
	}
	
	/**
	 * 通过标题模糊查询所有发布的、默认版本的流程
	 * @param subject
	 * @return
	 */
	public List<BpmDefinition> getAllPublished(String subject){
		return dao.getAllPublished(subject);
	}
	
	/**
	 * 通过类型ID查询所有发布的、默认版本的流程
	 * @param typeId
	 * @return
	 */
	public List<BpmDefinition> getPublishedByTypeId(String typeId){
		return dao.getPublishedByTypeId(typeId);
	}
	
	/**
	 * 根据流程定义key获得当前最新版本的流程定义
	 * @param defkey 
	 * @return
	 */
	public BpmDefinition getMainByDefKey(String defkey){
		return dao.getMainByDefKey(defkey);
	}
	
	/**
	 * 更新流程启动状态
	 * @param defId
	 * @param disableStatus
	 * @return
	 */
	public int updateDisableStatus(Long defId,Short disableStatus)
	{
		return dao.updateDisableStatus(defId, disableStatus);

	}

	/**
	 * 根据用户ID，获该用户所创建的流程定义
	 * @param userId 用户ID
	 * @param pb 分页Bean
	 * @return
	 */
	public List<BpmDefinition> getByUserId(Long userId,Map<String,Object> params,PageBean pb) {
		return dao.getByUserId(userId,params,pb);
		
	}
	
}
