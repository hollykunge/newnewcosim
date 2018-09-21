package com.hotent.platform.service.bpm.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.FlowNode;
import com.hotent.core.bpm.model.NodeCache;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.bpm.util.BpmUtil;
import com.hotent.core.engine.IScript;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.service.bpm.BpmNodeUserService;
import com.hotent.platform.service.system.SysOrgService;


/**
 * 流程任务人员分配处理类
 * @author csx
 *
 */
@Service
public class TaskUserAssignService implements IScript {
	
	private Logger logger=LoggerFactory.getLogger(TaskUserAssignService.class);
	/**
	 * 目标节点人员授权绑定 里面存的值为nodeId,List userIds。
	 */
	private static ThreadLocal<Map<String,List<TaskExecutor>>> nodeUserMapLocal=new ThreadLocal<Map<String,List<TaskExecutor>>>();
	
	private static ThreadLocal<Map<String,List<TaskExecutor>>> nextNodeUserMapLocal=new ThreadLocal<Map<String,List<TaskExecutor>>>();
	
	/**
	 * 表单用户,节点未设置人员时获取该用户列表（所有后续节点用户相同）
	 */
	private static ThreadLocal<List<TaskExecutor>> formUsers=new ThreadLocal<List<TaskExecutor>>();
	/**
	 * 任务执行人。
	 */
	private static ThreadLocal<List<TaskExecutor>> taskExecutors=new ThreadLocal<List<TaskExecutor>>();
	
	
	@Resource
	private BpmNodeUserService bpmNodeUserService;
	
	@Resource 
	private SysOrgService sysOrgService;
	


	/**
	 * 动态设置下一步节点的执行人。
	 * <pre>
	 * 	可以设置多个节点，每一个节点对应一组人的ID。
	 *  节点数组大小和人员的数组大小必须一致，人员的id使用逗号分隔。
	 * </pre>
	 * @param nodeIds
	 * @param userIds
	 */
	public void setNodeUser(Map<String,List< TaskExecutor>> map){
		nodeUserMapLocal.set(map);
	}
	
	public void setNextNodeUser(Map<String,List< TaskExecutor>> map){
		nextNodeUserMapLocal.set(map);
	}
	
	/**
	 * 添加节点人员。
	 * @param nodeId
	 * @param userIds
	 */
	public void addNodeUser(String nodeId,List<TaskExecutor> executors){
		if(BeanUtils.isEmpty(executors)) return;
	
		Map<String,List<TaskExecutor>> nodeUserMap=nodeUserMapLocal.get();
		if(nodeUserMap==null) nodeUserMap=new HashMap<String,List<TaskExecutor>>();
		nodeUserMap.put(nodeId, executors);
		nodeUserMapLocal.set(nodeUserMap);
	}
	
	public void addNextNodeUser(String nodeId,List<TaskExecutor> executors){
		if(BeanUtils.isEmpty(executors)) return;
	
		Map<String,List<TaskExecutor>> nextNodeUserMap=nextNodeUserMapLocal.get();
		if(nextNodeUserMap==null) nextNodeUserMap=new HashMap<String,List<TaskExecutor>>();
		nextNodeUserMap.put(nodeId, executors);
		nextNodeUserMapLocal.set(nextNodeUserMap);
	}
	
	/**
	 * 设置某个节点的执行人。
	 * @param nodeId	节点ID。
	 * @param userIds	节点人员使用逗号进行分隔。
	 */
	public void addNodeUser(String nodeId,String userIds){
		if(StringUtil.isEmpty(userIds)) return;
		List<TaskExecutor> executorList=BpmUtil.getTaskExecutors(userIds);
		addNodeUser(nodeId,executorList);	
	}
	
	/**
	 * 添加节点用户。
	 * @param aryNodeId
	 * @param aryUserIds
	 */
	public void addNodeUser(String[] aryNodeId,String[] aryUserIds){
		if(BeanUtils.isEmpty(aryUserIds)) return;
		Map<String,List<TaskExecutor>> nodeUserMap=nodeUserMapLocal.get();
		if(nodeUserMap==null) nodeUserMap=new HashMap<String,List<TaskExecutor>>();
		for(int i=0;i<aryNodeId.length;i++){
			String nodeId=aryNodeId[i];
			String userIds=aryUserIds[i];
			if(userIds==null ) continue;
		
			List<TaskExecutor> executorList=BpmUtil.getTaskExecutors(userIds);
			nodeUserMap.put(nodeId, executorList);
		}
		nodeUserMapLocal.set(nodeUserMap);
	}
	
	public void addNextNodeUser(String[] aryNodeId,String[] aryUserIds){
		if(BeanUtils.isEmpty(aryUserIds)) return;
		Map<String,List<TaskExecutor>> nextNodeUserMap=nextNodeUserMapLocal.get();
		if(nextNodeUserMap==null) nextNodeUserMap=new HashMap<String,List<TaskExecutor>>();
		for(int i=0;i<aryNodeId.length;i++){
			String nodeId=aryNodeId[i];
			String userIds=aryUserIds[i];
			if(userIds==null ) continue;
		
			List<TaskExecutor> executorList=BpmUtil.getTaskExecutors(userIds);
			nextNodeUserMap.put(nodeId, executorList);
		}
		nextNodeUserMapLocal.set(nextNodeUserMap);
	}
	
	
	public Map<String,List<TaskExecutor>> getNodeUserMap(){
		return nodeUserMapLocal.get();
	}
	
	public void clearNodeUserMap(){
		nodeUserMapLocal.remove();
	}
	
	
	public Map<String,List<TaskExecutor>> getNextNodeUserMap(){
		return nextNodeUserMapLocal.get();
	}
	
	public void clearNextNodeUserMap(){
		nextNodeUserMapLocal.remove();
	}
	/**
	 * 获取多实例子流程的执行用户集合
	 *  <pre>
	 * 1.从流程变量获取用户集合。
	 * 2.如果用户获取到用户就直接返回。
	 * 
	 * </pre>
	 * @param execution
	 * @return
	 * @throws Execption
	 */
	public List<TaskExecutor> getMultipleUser(ActivityExecution execution) throws Exception{
		
		String nodeId=execution.getActivity().getId();
		ExecutionEntity executionEnt=(ExecutionEntity) execution;
		
		List<TaskExecutor> list=(List<TaskExecutor>)execution.getVariable(BpmConst.SUBPRO_MULTI_USERIDS);
		
		if(BeanUtils.isNotEmpty(list)) return list;
	
		Map<String,FlowNode> nodeMap= NodeCache.getByActDefId(executionEnt.getProcessDefinitionId());
		FlowNode subProcessNode=nodeMap.get(nodeId);
		FlowNode firstNode=subProcessNode.getSubFirstNode();
		FlowNode secondeNode=firstNode.getNextFlowNodes().get(0);
		//获取任务执行人。
		List<TaskExecutor> userList=getExecutors();
		
		if(BeanUtils.isEmpty(userList)){
			userList=nodeUserMapLocal.get().get(secondeNode.getNodeId());
			if(BeanUtils.isEmpty(userList)){
				if(BeanUtils.isNotEmpty(getFormUsers())){
					userList=getFormUsers();
				}
			}
		}
		logger.debug("userList size:" + userList.size());
		execution.setVariable(BpmConst.SUBPRO_MULTI_USERIDS, userList);
		return userList;
	}
	
	
	
	/**
	 * 获取多实例外部子流程的用户数据。
	 * <pre>
	 * 这个接口由流程引擎调用， 获取并行和串行用户数据。
	 * </pre>
	 * @param execution
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<TaskExecutor> getExtSubProcessMultipleUser(ActivityExecution execution) throws Exception{
		
		String nodeId=execution.getActivity().getId();
		String nodeName=execution.getCurrentActivityName();
		ExecutionEntity executionEnt=(ExecutionEntity) execution;
		
		String multiInstance=(String) executionEnt.getActivity().getProperty("multiInstance");
		
		String varName=executionEnt.getActivityId() +"_" +BpmConst.SUBPRO_EXT_MULTI_USERIDS;
		//串行会签。
		if("sequential".equals(multiInstance)){
			List<TaskExecutor> userIds=(List<TaskExecutor>)executionEnt.getParent().getVariable(varName);
			if(userIds!=null) return userIds;
		}
		else{
			List<TaskExecutor> userIds=(List<TaskExecutor>)execution.getVariable(varName);
			if(userIds!=null) return userIds;
		}
		
		//获取流程的节点集合。
		Map<String,FlowNode> nodeMap= NodeCache.getByActDefId(executionEnt.getProcessDefinitionId());
		//获取子流程节点。
		FlowNode subProcessNode=nodeMap.get(nodeId);
		//获取子流程第一个流程节点。
		Map<String,FlowNode> subProcessNodesMap=subProcessNode.getSubProcessNodes();
		
		FlowNode startNode= NodeCache.getStartNode(subProcessNodesMap);
		//第二个节点。
		FlowNode secodeNode=startNode.getNextFlowNodes().get(0);
		
		List<TaskExecutor> userList=getExecutors();
		
		if(BeanUtils.isEmpty(userList)){
			userList=getNodeUserMap().get(secodeNode.getNodeId());
			if(userList==null){
				userList=new ArrayList<TaskExecutor>();
			}
		}
		
		if(multiInstance!=null && BeanUtils.isEmpty(userList)){
			MessageUtil.addMsg("请设置子流程:[" + nodeName  +"]的人员!");
		}
		
		logger.debug("userList size:" + userList.size());
		
		executionEnt.setVariable(varName, userList);
		
		return userList;
	}
	
	
	
	/**
	 * 获取会签用户，用于会签节点。
	 * <pre>
	 * 在流程定义中
	 * &lt;xsl:attribute name="activiti:collection">${taskUserAssignService.getSignUser(execution)}&lt;/xsl:attribute>
	 * 1.从上下文指定的用户中获取会签用户。
	 * 2.如果获取不到，并且该节点是串行会签的节点，则从会签数据表当中获取。
	 * 3.如果再获取不到，那么则从节点配置的数据库中获取用户数据。
	 * </pre>
	 * @param execution
	 * @return
	 * @throws Exception
	 */
	public List<TaskExecutor> getSignUser(ActivityExecution execution) throws Exception {
		String nodeId=execution.getActivity().getId();
		String nodeName=(String)execution.getActivity().getProperty("name");
		String multiInstance=(String)execution.getActivity().getProperty("multiInstance");
		
		List<TaskExecutor> userIds=null;
		
		String varName=nodeId +"_" +BpmConst.SIGN_USERIDS;
		//串行会签人员首先从流程变量中获取。
		if("sequential".equals(multiInstance)){
			userIds=(List<TaskExecutor>)execution.getVariable(varName);
			if(userIds!=null) return userIds;
		}
		
		userIds=getExecutors();
		/////ht b
		/////ht del b
//		if(BeanUtils.isNotEmpty(userIds)){
//			saveExecutorVar(execution,userIds);
//			return userIds;
//		}
		/////ht del e
		if(BeanUtils.isNotEmpty(userIds)){
			userIds = excludeByType(userIds,TaskExecutor.USER_TYPE_ROLE);
			if(BeanUtils.isEmpty(userIds)){
				ISysUser curSysUser = ContextUtil.getCurrentUser();
				ISysOrg curSysOrg = sysOrgService.getById(curSysUser.getOrgId());
				TaskExecutor executor = new TaskExecutor(TaskExecutor.USER_TYPE_COMP, curSysUser.getOrgId().toString(), curSysOrg.getOrgName());
				getExecutors().add(executor);
				userIds.add(executor);
			}
			saveExecutorVar(execution,userIds);
			return userIds;
		}
		/////ht e
		Map<String,List<TaskExecutor>> nodeUserMap=nodeUserMapLocal.get();
		
		//会签任务用户来自前台的用户选择
		if(nodeUserMap!=null && BeanUtils.isNotEmpty(nodeUserMap.get(nodeId))){
			userIds=nodeUserMap.get(nodeId);
			if(taskExecutors.get()==null){
				setExecutors(userIds);
			}
			saveExecutorVar(execution,userIds);
			return userIds;
		}
		//从表单中获取
		List<TaskExecutor> formUsers=getFormUsers();
		if(BeanUtils.isNotEmpty(formUsers)){
			setExecutors(formUsers);
			//肖莹莹做了修改 2013-05-18
			formUsers = excludeByType(formUsers,TaskExecutor.USER_TYPE_ROLE);		    	
			saveExecutorVar(execution,formUsers);
			return formUsers;
		}
		
		//从数据库配置中获取。
		ExecutionEntity ent=(ExecutionEntity)execution;
		String actDefId=ent.getProcessDefinitionId();
		//获取发起用户。
		String startUserId=(String)execution.getVariable(BpmConst.StartUser);
		Map<String,Object> vars=new HashMap<String, Object>();
		vars.put("executionId", execution.getId());
		List<TaskExecutor> list=bpmNodeUserService.getExeUserIds(actDefId, nodeId, startUserId,vars).get(BpmNodeUser.USER_TYPE_PARTICIPATION);
		
		setExecutors(list);
		
		if( BeanUtils.isEmpty(list)){
			MessageUtil.addMsg("请设置会签节点:[" + nodeName  +"]的人员!");
		}
		if(BeanUtils.isNotEmpty(list)){
			saveExecutorVar(execution,list);
		}
		
		return list;
			
	}
	
	public List<TaskExecutor> excludeByType(Collection<TaskExecutor> executors,String type){
		List<TaskExecutor> list = new ArrayList<TaskExecutor>();
		for(TaskExecutor taskExecutor:executors){
			if(!taskExecutor.getType().equals(type)){
				list.add(taskExecutor);
			}
		}
		return list;
	}
	
	
	/**
	 * 
	 * @param execution
	 * @param userIds
	 */
	private void saveExecutorVar(ActivityExecution execution,List<TaskExecutor> userIds){
		String multiInstance=(String)execution.getActivity().getProperty("multiInstance");
		if("sequential".equals(multiInstance)){
			String nodeId=execution.getActivity().getId();
			String varName=nodeId +"_" +BpmConst.SIGN_USERIDS;
			execution.setVariable(varName, userIds);
		}
		
	}
	
	
	
	/**
	 * 设置会签用户。
	 * @param users
	 */
	public void setExecutors(String users){
		if(StringUtil.isEmpty(users)){
			return ;
		}
		String[] aryUsers=users.split(",");
		
		List list = Arrays.asList(aryUsers);
		taskExecutors.set(list);
	}

	/**
	 * 设置设置任务执行人。
	 * @param users
	 */
	public void setExecutors(List<TaskExecutor> users) {
		taskExecutors.set(users);
	}
	
	/**
	 * 获取任务执行人。
	 * @return
	 */
	public List<TaskExecutor> getExecutors() {
		return taskExecutors.get(); 
	}
	
	public void clearExecutors() {
		taskExecutors.remove();
	}
	

	/**
	 * 获取表单用户。
	 * @return
	 */
	public List<TaskExecutor> getFormUsers() {
		return formUsers.get();
	}
	
	
	public static void setFormUsers(List<TaskExecutor> formUsers_) {
		formUsers.set(formUsers_);
		
	}
	
	/**
	 * 添加表单用户
	 * @param formUsers_
	 */
	public static void addFormUsers(List<TaskExecutor> formUsers_){
		if(formUsers.get()==null){
			formUsers.set(formUsers_);
		}
		else{
			formUsers.get().addAll(formUsers_);
		}
		
	}

	public static void clearFormUsers(){
		formUsers.remove();
	}
	
	/**
	 * 清除所有的线程变量。
	 */
	public static void clearAll(){
		formUsers.remove();
		taskExecutors.remove();
		nodeUserMapLocal.remove();
		nextNodeUserMapLocal.remove();
	}

}
