package com.hotent.platform.service.bpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmFormRun;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.dao.bpm.BpmDefinitionDao;
import com.hotent.platform.dao.bpm.BpmFormRunDao;
import com.hotent.platform.dao.bpm.BpmNodeSetDao;

/**
 * 对象功能:流程表单运行情况 Service类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2012-05-21 16:28:40
 */
@Service
public class BpmFormRunService extends BaseService<BpmFormRun>
{
	@Resource
	private BpmFormRunDao dao;
	
	@Resource
	private BpmNodeSetDao bpmNodeSetDao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmDefinitionDao bpmDefinitionDao;
	
	
	
	public BpmFormRunService()
	{
	}
	
	@Override
	protected IEntityDao<BpmFormRun, Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 添加表单运行情况。
	 * <pre>
	 * 将当前最新的表单配置信息添加到表单运行情况。
	 * 之后的流程表单从表单运行情况表中取值。
	 * </pre>
	 * @param actDefId	表单定义ID
	 * @param runId		process runID
	 * @param actInstanceId		流程实例ID
	 * @throws Exception
	 */
	public void addFormRun(String actDefId,Long runId,String actInstanceId) {
	    List<BpmNodeSet> list=bpmNodeSetDao.getOnlineFormByActDefId(actDefId);
	    for(BpmNodeSet bpmNodeSet:list){
	    	BpmFormRun bpmFormRun=getByBpmNodeSet(runId, actInstanceId, bpmNodeSet);
	    	dao.add(bpmFormRun);
	    }
	}
	
	/**
	 * 获取默认或起始的流程表单。
	 * @param list
	 * @param setType
	 * @return
	 */
	private BpmNodeSet getDefalutStartForm(List<BpmNodeSet> list,Short setType){
		BpmNodeSet bpmNodeSet=null;
		for(BpmNodeSet node:list){
			if(node.getSetType().equals(setType)){
				bpmNodeSet=node;
				break;
			}
		}
		return bpmNodeSet;
	}
	
	/**
	 * 获取开始节点的表单运行情况。
	 * @param list
	 * @param setType
	 * @return
	 */
	private BpmNodeSet getStartForm(List<BpmNodeSet> list){
		BpmNodeSet bpmNodeSet =getDefalutStartForm(list,BpmNodeSet.SetType_StartForm);
		return bpmNodeSet;
	}
	
	/**
	 * 获取开始节点的表单运行情况。
	 * @param list
	 * @param setType
	 * @return
	 */
	private BpmNodeSet getGlobalForm(List<BpmNodeSet> list){
		BpmNodeSet bpmNodeSet =getDefalutStartForm(list,BpmNodeSet.SetType_GloabalForm);
		return bpmNodeSet;
	}
	
	/**
	 * 获取节点运行的form列表。
	 * @param list
	 * @return
	 */
	public Map<String, BpmNodeSet> getTaskForm(List<BpmNodeSet> list ){
		Map<String, BpmNodeSet> map=new HashMap<String, BpmNodeSet>();
		for(BpmNodeSet node:list){
			if(node.getSetType().equals(BpmNodeSet.SetType_TaskNode)){
				map.put(node.getNodeId(), node);
			}
		}
		return map;
	}
	
	
	/**
	 * 根据BpmNodeSet对象构建BpmFormRun对象。
	 * @param actDefId		流程定义id。
	 * @param runId			流程运行ID。
	 * @param actInstanceId	流程实例ID。
	 * @param bpmNodeSet	 流程节点对象。
	 * @return
	 * @throws Exception
	 */
	private BpmFormRun getByBpmNodeSet(Long runId,String actInstanceId,BpmNodeSet bpmNodeSet) {
		BpmFormRun bpmFormRun=new BpmFormRun();
		bpmFormRun.setId(UniqueIdUtil.genId());
		bpmFormRun.setRunId(runId);
		bpmFormRun.setActInstanceId(actInstanceId);
		bpmFormRun.setActDefId(bpmNodeSet.getActDefId());
		bpmFormRun.setActNodeId(bpmNodeSet.getNodeId());
		bpmFormRun.setFormdefId(bpmNodeSet.getFormDefId());
		bpmFormRun.setFormdefKey(bpmNodeSet.getFormKey());
		bpmFormRun.setFormType(bpmNodeSet.getFormType());
		bpmFormRun.setFormUrl(bpmNodeSet.getFormUrl());
		bpmFormRun.setSetType(bpmNodeSet.getSetType());
		return bpmFormRun;
	}
	
	
	/**
	 * 
	 * @param actDefId
	 * @param toFirstNode
	 * @return
	 */
	public BpmNodeSet getStartBpmNodeSet(String actDefId,Short toFirstNode){
		String firstTaskName=processRunService.getFirstNodetByDefId(actDefId);
		List<BpmNodeSet> list=bpmNodeSetDao.getByActDefId(actDefId);
		BpmNodeSet bpmNodeSetStart=getStartForm(list); 
		BpmNodeSet bpmNodeSetGlobal=getGlobalForm(list); 
		Map<String, BpmNodeSet> taskMap= getTaskForm(list);
		BpmNodeSet firstBpmNodeSet=taskMap.get(firstTaskName);
		if(bpmNodeSetStart==null){
			//允许跳到下一个节点
			if(toFirstNode==1){
				//取得流程的第一个节点。
				if(firstBpmNodeSet!=null && firstBpmNodeSet.getFormType()!=null && firstBpmNodeSet.getFormType()!=-1 ){
					return firstBpmNodeSet;
				}
				//获取全局的表单数据
				else if(bpmNodeSetGlobal!=null){
					if(bpmNodeSetGlobal.getFormType()!=null  && bpmNodeSetGlobal.getFormType()!=-1 ){
						return bpmNodeSetGlobal;
					}
				}
			}
			//获取全局的表单数据
			else if(bpmNodeSetGlobal!=null){
				if(bpmNodeSetGlobal.getFormType()!=null && bpmNodeSetGlobal.getFormType()!=-1){
					return bpmNodeSetGlobal;
				}
			}
		}
		else{
			return bpmNodeSetStart;
		}
		return null;
	}
	
	/**
	 * 判断是否可以直接启动。
	 * @param defId
	 * @return
	 */
	public boolean getCanDirectStart(Long defId){
		BpmDefinition bpmDefinition=bpmDefinitionDao.getById(defId);
		
		String actDefId=bpmDefinition.getActDefId();
		Short toFirstNode=bpmDefinition.getToFirstNode();
		Short needStartForm=bpmDefinition.getNeedStartForm();
	
		List<BpmNodeSet> list=bpmNodeSetDao.getByActDefId(actDefId);
		
		
		BpmNodeSet bpmNodeSetStart=getStartForm(list); 
		BpmNodeSet bpmNodeSetGlobal=getGlobalForm(list); 
		
		boolean hasStart=hasForm(bpmNodeSetStart);
		boolean hasGlobal=hasForm(bpmNodeSetGlobal);
		
		boolean canDirectStart=false;
		//不需要开始表单
		if(needStartForm==0){
			//不允许跳到下一个节点
			if(toFirstNode==0){
				//没有起始表单和全局表单
				if(hasStart==false && hasGlobal==false)
					return true;
			}
		}
		return canDirectStart;
	}
	
	/**
	 * 是否有表单。
	 * @param nodeSet
	 * @return
	 */
	private boolean hasForm(BpmNodeSet nodeSet){
		if(nodeSet==null || nodeSet.getFormType()==-1)
			return false;
		return true;
	}
	
	/**
	 * 取得流程运行表单情况。
	 * @param actInstanceId
	 * @param actNodeId
	 * @return
	 */
	public BpmFormRun getByInstanceAndNode(String actInstanceId,String actNodeId){
		//根据流程实例id和任务节点id获取表单。
		BpmFormRun bpmFormRun=  dao.getByInstanceAndNode(actInstanceId, actNodeId);
		if(bpmFormRun!=null && bpmFormRun.getFormType()!=null && bpmFormRun.getFormType()!=-1){
			return bpmFormRun;
		}
		else{
			//没有获取到则再获取全局表单。
			bpmFormRun=dao.getGlobalForm(actInstanceId);
			if(bpmFormRun!=null && bpmFormRun.getFormType()!=null && bpmFormRun.getFormType()!=-1){
				return bpmFormRun;
			}
			return null;
		}
	}
	
	
	/**
	 * 取得流程运行表单情况，与getByInstanceAndNode不同，此方法不对取得的表单做任务判断和处理。
	 * @param actInstanceId
	 * @param actNodeId
	 * @return
	 */
	public BpmFormRun getByInstanceAndNodeId(String actInstanceId,String actNodeId){
		//根据流程实例id和任务节点id获取表单。
		BpmFormRun bpmFormRun=  dao.getByInstanceAndNode(actInstanceId, actNodeId);
		return bpmFormRun;
	}
	
	/**
	 * 根据流程实例ID，流程实例的运行表单列表
	 * @param actInstanceId
	 * @return
	 */
	public List<BpmFormRun> getByInstanceId(String actInstanceId){
		return dao.getByInstanceId(actInstanceId);
	}
}


