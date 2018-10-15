package com.hotent.platform.webservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import com.hotent.core.bpm.model.FlowNode;
import com.hotent.core.bpm.model.NodeCache;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.TimeUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.bpm.BpmProStatusDao;
import com.hotent.platform.dao.bpm.TaskDao;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNode;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.BpmVar;
import com.hotent.platform.model.bpm.ExecutionStack;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskFork;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.BpmNodeSignService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ExecutionStackService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskApprovalItemsService;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.bpm.TaskSignDataService;
import com.hotent.platform.service.bpm.impl.BpmActService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.webservice.api.ProcessService;
import com.hotent.platform.webservice.model.BpmFinishTask;
import com.hotent.platform.webservice.model.BpmTaskOpinion;

/**
 * 流程对外服务接口实现类
 * @author csx
 *
 */

public class ProcessServiceImpl implements ProcessService {
	@Resource
	private ProcessRunService processRunService;
	
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private SysOrgService sysOrgService;
	
	@Resource
	private TaskOpinionService taskOpinionService;
	
	@Resource
	private BpmService bpmService;
	
	@Resource
	private TaskService taskService;
	@Resource
	private RuntimeService runtimeService;
	
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	
	@Resource
	BpmActService bpmActService;
	
	@Resource
	
	private TaskSignDataService taskSignDataService;
	@Resource
	private TaskApprovalItemsService taskApprovalItemsService;
	
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	
	@Resource
	private ExecutionStackService executionStackService;
	
	@Resource
	private BpmProStatusDao bpmProStatusDao;

	@Resource
	private TaskDao taskDao;
	@Resource
	private BpmNodeSignService bpmNodeSignService;
	
	@Resource
	private SysRoleService sysRoleService;
	/**
	 * 启动流程。
	 * <pre>
	 * 启动流程参数为ProcessCmd。
	 *  ProcessCmd：必须配置的参数如下：
	 *  actDefId：流程定义ID。
	 *  flowKey：流程定义key。
	 *  这两个参数必须设置一个。
	 *  
	 *  subject：流程启动的主题事项。
	 *  startUser：启动人员系统帐号。
	 *  
	 *  businessKey:主键数据，如果业务系统自己保存主键数据可以不必传入。
	 *  
	 *  informType：1,2 1:手机短信通知，2 邮件通知。。
	 *
	 * 示例：
	 * ProcessCmd cmd=new ProcessCmd();
	 * cmd.setActDefId("flowDemo:2:1111");
	 * cmd.setBusinessKey("1234");
	 * cmd.setSubject("流程demo");
	 * cmd.setInformType("1,2");
	 * cmd.setUserAccount("ray");
	 * 
	 * </pre>
	 */
	public ProcessRun start(ProcessCmd processCmd) throws Exception {
		
		ISysUser user= ContextUtil.getCurrentUser();
		if(user==null){
			ContextUtil.setCurrentUserAccount(processCmd.getUserAccount());
		}
		return processRunService.startProcess(processCmd);
	}
	
	
	
	@Override
	public String doNextByXml(String xml) throws Exception {
		Document doc= Dom4jUtil.loadXml(xml);
		Element root=doc.getRootElement();
		String taskId=root.attributeValue("taskId");
		String userAccount=root.attributeValue("userAccount");
		String voteAgree=root.attributeValue("voteAgree");
		String voteContent=root.attributeValue("voteContent");
		
		
		if(StringUtil.isEmpty(taskId)){
			String errorMesage=genErrorMessage("流程任务ID必须填写!");
			String rtnXml=genErrorMessage(errorMesage);
			return rtnXml;
		}
		
		ProcessCmd processCmd=new ProcessCmd();
		
		if(StringUtil.isNotEmpty(taskId)){
			processCmd.setTaskId(taskId);
		}
		//处理用户账号必填。
		if(StringUtil.isEmpty(userAccount)){
			String errorMesage=genErrorMessage("处理用户账号必填!");
			String rtnXml=genErrorMessage(errorMesage);
			return rtnXml;
			
		}
		
		processCmd.setUserAccount(userAccount);
		
		if(StringUtil.isNotEmpty(voteAgree)){
			processCmd.setVoteAgree(Short.parseShort(voteAgree));
		}
		
		if(StringUtil.isNotEmpty(voteContent)){
			processCmd.setVoteContent(voteContent);
		}
		
		//计算流程变量。
		List<Element> vars=root.elements();
		if(BeanUtils.isNotEmpty(vars)){
			Map<String,Object> variables=addVars(vars);
			processCmd.setVariables(variables);
		}
		
		try{
			doNext(processCmd);
			return "<result result=\"true\" message=\"流程执行成功\"/>";
		}
		catch(Exception ex){
			String rtnXml=genErrorMessage(ex.getMessage());
			return rtnXml;
		}
	}
	
	
	public List<ProcessTask> getTasksByXml(String xml)throws Exception {
		Document doc= Dom4jUtil.loadXml(xml);
		Element root=doc.getRootElement();
		String userId=root.attributeValue("userId"); //用户ID
		String account=root.attributeValue("account"); //用户账号
		String taskName =root.attributeValue("taskName"); //流程定义名称
		String subject=root.attributeValue("subject");//任务标题
		String processName=root.attributeValue("processName");//流程定义名称 
		String orderField=root.attributeValue("orderField");
		String orderSeq=root.attributeValue("orderSeq");
		
		String pageSize=root.attributeValue("pageSize");
		String currentPage=root.attributeValue("currentPage");
		
		
		//用户ID和用户账号二选一。
		if(StringUtil.isEmpty(userId) && StringUtil.isEmpty(account)){
			throw new Exception("用户ID和用户账号必须填写一个!");
		}
		
		if(StringUtils.isNotEmpty(account)){
			ISysUser sysUser=sysUserService.getByAccount(account);
			userId=sysUser.getUserId().toString();
		}
		
		PageBean pb=new PageBean();
		if(StringUtil.isNotEmpty(pageSize)){
			pb.setPagesize(new Integer(pageSize));
		}else{
			pb.setPagesize(20);
		}
		if(StringUtils.isNotEmpty(currentPage)){
			pb.setCurrentPage(new Integer(currentPage));
		}else{
			pb.setCurrentPage(1);
		}
		List<ProcessTask> tasks=getTasksByUserId(Long.parseLong(userId), taskName, subject, processName, orderField, orderSeq, pb);		
		return tasks;
			      
    }
	
	/**
	 * 按任务ID结束流程实例。
	 * @param taskId 当前任务Id
	 * @return
	 */
	public boolean endProcessByTaskId(String taskId) {
		bpmActService.endProcessByTaskId(taskId);
		return true;
	}
	
	
	public void doNext(ProcessCmd processCmd) throws Exception {
		ISysUser user= ContextUtil.getCurrentUser();
		if(user==null){
			ContextUtil.setCurrentUserAccount(processCmd.getUserAccount());
		}
		
		processRunService.nextProcess(processCmd);
	}
	
	/**
	 * 查询待办任务, 同时获取任务的表单数据。
	 * <pre>
	 * 根据用户id，任务名称，任务主题，流程名称，排序字段，排序，分页信息查询某人的待办任务。
	 * </pre>
	 */
	public List<ProcessTask> getTasksByUserId(Long userId, String taskName,String subject, String processName, String orderField, String orderSeq,PageBean pb) {
		List<ProcessTask> processTasks= taskDao.getTasks(userId, taskName, subject, processName, orderField, orderSeq,pb);
		constructTaskUrls(processTasks);
		return processTasks;
	}
	
	/**
	 * 根据用户帐号查询代办任务，同时获取任务的表单数据。
	 * <pre>
	 * 根据用户帐号，任务名称，任务主题，流程名称，排序字段，排序，分页信息查询某人的待办任务。
	 * </pre>
	 */
	@Override
	public List<ProcessTask> getTasksByAccount(String userAccount, String taskName,
			String subject, String processName, String orderField,
			String orderSeq, PageBean pb) {
		ISysUser sysUser=sysUserService.getByAccount(userAccount);
		List<ProcessTask> processTasks=taskDao.getTasks(sysUser.getUserId(), taskName, subject, processName, orderField, orderSeq,pb);
		constructTaskUrls(processTasks);
		return processTasks;
	}
	
	
	private void constructTaskUrl(ProcessTask processTask){
		if(processTask==null) return;
		BpmNodeSet bpmNodeSet=bpmNodeSetService.getByActDefIdNodeId(processTask.getProcessDefinitionId(), processTask.getTaskDefinitionKey());
		Map<String,Object> varsMap= taskService.getVariables(processTask.getId());
		
		if(bpmNodeSet==null) return;
	
		String formUrl=bpmNodeSet.getFormUrl();
		if(StringUtil.isNotEmpty(formUrl)){
			formUrl=StringUtil.formatParamMsg(formUrl, varsMap);
			processTask.setTaskUrl(formUrl);
		}
	}
	
	/**
	 * 构建表单的执行URL。
	 * @param processTask
	 */
	private void constructTaskUrls(List<ProcessTask> processTaskList){
		for(ProcessTask processTask :processTaskList){
			constructTaskUrl(processTask);
		}
	}
	
	/**
	 * 根据流程runId 获取流程运行对象。
	 */
	@Override
	public ProcessRun getProcessRun(Long runId) {
		return processRunService.getById(runId);
	}
	
	/**
	 * 根据流程运行id获取任务列表。
	 */
	public List<ProcessTask> getTasksByRunId(Long runId) {
		List<ProcessTask> tasksList=taskDao.getTasksByRunId(runId);
		constructTaskUrls(tasksList);
		return tasksList;
	}
	
	/**
	 * 根据流程实例Id获取流程意见列表。
	 */
	public List<BpmTaskOpinion> getProcessOpinionByActInstId(String actInstId) {
		List<BpmTaskOpinion> taskOpList=new ArrayList<BpmTaskOpinion>();
		List<TaskOpinion> taskOpinionList=taskOpinionService.getByActInstId(actInstId);
		for(TaskOpinion op:taskOpinionList){
			BpmTaskOpinion opinion=new BpmTaskOpinion();
			BeanUtils.copyProperties(opinion, op);
			taskOpList.add(opinion);
		}
		return taskOpList;
	}
	
	/**
	 * 根据流程运行id获取流程意见列表。
	 */
	public List<BpmTaskOpinion> getProcessOpinionByRunId(Long runId) {
		List<BpmTaskOpinion> taskOpList=new ArrayList<BpmTaskOpinion>();
		ProcessRun processRun=processRunService.getById(runId);
		List<TaskOpinion> taskOpinionList= taskOpinionService.getByActInstId(processRun.getActInstId());
		
		for(TaskOpinion op:taskOpinionList){
			BpmTaskOpinion opinion=new BpmTaskOpinion();
			BeanUtils.copyProperties(opinion, op);
			taskOpList.add(opinion);
		}
		return taskOpList;
	}
	
	
	
	public void lockTask(String taskId, String userAccount) {
		ISysUser sysUser=sysUserService.getByAccount(userAccount);
		bpmService.assignTask(taskId,sysUser.getUserId().toString());
	}
	
	/**
	 * 解锁任务
	 * @param taskId 任务ID	 
	 */
	@Override
	public void unlockTask(String taskId){
		bpmService.updateTaskAssigneeNull(taskId);
	}
	
	/**
	 * 设置流程变量
	 * @param taskId 任务Id
	 * @param varName 流程变量名
	 * @param varVal 流程变量值
	 */
	public void setVariable(String taskId,String varName,Object varVal){
		taskService.setVariable(taskId,varName, varVal);
	}
	/**
	 * 通过runId获取流程变量
	 * @param runId
	 * @return
	 */
	public List<BpmVar> getVariablesByRunId(Long runId){
		ProcessRun processRun=processRunService.getById(runId);
		Map<String,Object> varMap=runtimeService.getVariables(processRun.getActInstId().toString());
		return getListFromMap(varMap);
	}
	/**
	 * 通过taskId获取该流程的所有变量
	 * @param taskId
	 * @return
	 */
	public List<BpmVar> getVariablesByTaskId(String taskId){
		Task task=bpmService.getTask(taskId);
		Map<String,Object> varMap=runtimeService.getVariables(task.getExecutionId());
		return getListFromMap(varMap);
	}
	/**
	 * 把map中的变量转成BpmVar的List对象
	 * @param varMap
	 * @return
	 */
	private List<BpmVar> getListFromMap(Map<String,Object> varMap){
		List<BpmVar> list=new ArrayList<BpmVar>();
		Iterator<Map.Entry<String, Object>> it= varMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Object> entry=it.next();
			list.add(new BpmVar(entry.getKey(),entry.getValue()));
		}
		return list;
	}
	
	@Override
	public List<ProcessRun> getMyProcessRun(String userAccount, String subject,
			Short status, PageBean pb) {
		ISysUser sysUser=sysUserService.getByAccount(userAccount);
		return processRunService.getMyProcessRun(sysUser.getUserId(), subject, status, pb);
	}
	
	@Override
	public String getTaskFormUrl(String taskId) {
		TaskEntity taskEntity=bpmService.getTask(taskId);
		Map<String,Object> variables=taskEntity.getVariables();
		String bussinessKey=(String)taskService.getVariable(taskId, "bussinessKey");
		String form="";
		if(taskEntity==null) return null;
		BpmNodeSet bpmNodeSet=bpmNodeSetService.getByActDefIdNodeId(taskEntity.getProcessDefinitionId(), taskEntity.getTaskDefinitionKey());
		if(bpmNodeSet==null) return null;
		String formUrl=bpmNodeSet.getFormUrl();
		//表单的URL和表单key不为空。
		if(StringUtil.isNotEmpty(formUrl) && StringUtil.isNotEmpty(bussinessKey)){
			//替换表单的主键。
			//例如：get.ht?id={pk}&flowRunId={flowRunId}
			form=formUrl.replaceFirst(BpmConst.FORM_PK_REGEX, bussinessKey);
			if(variables!=null){
				Pattern regex = Pattern.compile("\\{(.*?)\\}");
				Matcher regexMatcher = regex.matcher(form);
				while (regexMatcher.find()) {
					String toreplace=regexMatcher.group(0);
					String varName=regexMatcher.group(1);
					if(!variables.containsKey(varName)) continue;
					form=form.replace(toreplace,variables.get(varName).toString());
				}	
			}	
		}
		return form;
	}
	
	/**
	 * 按任务名称或任务ID数组取得我的待办任务
	 * @param userAccount 账号
	 * @param taskName 任务名称
	 * @param taskIds 任务IDS
	 * @param pb 分页对象
	 * @return
	 */
	public List<ProcessTask> getByTasksByNameOrIds(String userAccount,String taskName,String[] taskIds,PageBean pb){
		ISysUser sysUser=sysUserService.getByAccount(userAccount);
		String taskIdsStr=StringUtil.getArrayAsString(taskIds);
		if(StringUtils.isEmpty(taskIdsStr)){
			taskIdsStr="-1";
		}
		List<ProcessTask> taskList=taskDao.getByTaskNameOrTaskIds(sysUser.getUserId().toString(), taskName, taskIdsStr, pb);
		constructTaskUrls(taskList);
		return taskList;
	}
	
	
	/**
	 * 客户端提交xml启动流程。<br>
	 * <pre>
	 * 流程参数包括：
	 * actDefId：流程定义ID flowKey:流程key，两个参数可任意定义一个。
	 * startUser：必须填写。
	 * mailInform:是否发送邮件通知
	 * smsInform:是否发送短信通知
	 * 流程变量数可以是0..*个。
	 * 流程变量的数据类型为 int,long,double,date,string。
	 * 构建的xml参数如下所示
	 * &lt;start actDefId="ebuyOfjoy:1:27908" flowKey="" subject="webservice启动测试" 
	 *  startUser="admin" mailInform="true" smsInform="false">
	 *  &lt;var name="" value="" type="int,long,double,date,String"/>
	 * &lt;/start>
	 * 
	 * 启动成功返回:
	 * &lt;result result="true" subject='' runId='' instanceId=''/>
	 * subject为主题。
	 * runId 为运行id和流程实例Id一一对应。
	 * instanceId：流程实例Id。
	 * 失败返回：
	 * &lt;result result="flase" message=''/>
	 * message:启动失败消息。
	 * </pre>
	 * @param xml
	 * @return 
	 */
	@Override
	public String startByXml(String xml) {
		Document doc= Dom4jUtil.loadXml(xml);
		
		Element root=doc.getRootElement();
		String actDefId=root.attributeValue("actDefId");
		String flowKey=root.attributeValue("flowKey");
		String subject=root.attributeValue("subject");
		String startUser=root.attributeValue("startUser");
		String mailInform=root.attributeValue("mailInform");
		String smsInform=root.attributeValue("smsInform");
		
		//流程key和定义id二选一。
		if(StringUtil.isEmpty(actDefId) && StringUtil.isEmail(flowKey)){
			String errorMesage=genErrorMessage("流程定义ID和流程key必须填写一个!");
			return errorMesage;
		}
		//启动账户必填。
		if(StringUtil.isEmpty(startUser)){
			String errorMesage=genErrorMessage("启动用户必填!");
			return errorMesage;
		}
		
		ProcessCmd processCmd=new ProcessCmd();
		
		if(StringUtil.isNotEmpty(actDefId)){
			processCmd.setActDefId(actDefId);
		}
		
		if(StringUtil.isNotEmpty(flowKey)){
			processCmd.setFlowKey(flowKey);
		}
		
		if(StringUtil.isNotEmpty(subject)){
			processCmd.setSubject(subject);
		}
		
		String informType="";
		//是否邮件通知
		if(StringUtil.isNotEmpty(mailInform)){
			if(mailInform.equals("true")){
				informType += "2";
			}
		}
		//是否短信通知
		if(StringUtil.isNotEmpty(smsInform)){
			if(smsInform.equals("true")){
				informType += "1";
			}
		}
		Map formDataMap=new HashMap();
		formDataMap.put("informType", informType);
		processCmd.setFormDataMap(formDataMap);
		processCmd.setUserAccount(startUser);
		
		//计算流程变量。
		List<Element> vars=root.elements();
		if(BeanUtils.isNotEmpty(vars)){
			Map<String,Object> variables=addVars(vars);
			processCmd.setVariables(variables);
		}
		
		
		try{
			ProcessRun processRun = this.start(processCmd);
			String rtnXml=getReturnXml(processRun);
			return rtnXml;
		}
		catch (Exception e) {
			//返回错误消息。
			return genErrorMessage(e.getMessage());
		}
		
	}
	
	/**
	 * 返回xml数据。
	 * @param processRun
	 * @return
	 */
	private String getReturnXml(ProcessRun processRun){
		String subject=processRun.getSubject();
		Long runId=processRun.getRunId();
		String instanceId=processRun.getActInstId();
		String result="<result result=\"true\" subject=\""+subject +"\" runId=\""+runId+"\" instanceId=\""+instanceId +"\"/>";
		return result;
	}
	
	/**
	 * 加入流程变量。
	 * @param vars
	 * @return
	 */
	private Map<String,Object> addVars(List<Element> vars){
		Map<String,Object> varMap=new HashMap<String, Object>();
		for(Element var :vars){
			String name=var.attributeValue("name");
			String value=var.attributeValue("value");
			String dataType=var.attributeValue("type");
			Object obj=convertType(value, dataType);
			varMap.put(name, obj);
		}
		return varMap;
	}
	
	/**
	 * 将数据进行转型。
	 * @param value
	 * @param dataType
	 * @return
	 */
	private Object convertType(String value,String dataType){
		if("int".equals(dataType)){
			return new Integer(value);
		}else if("long".equals(dataType)){
			return new Long(value);
		}else if("double".equals(dataType)){
			return new Double(value);
		}else if("date".equals(dataType)){
			return TimeUtil.convertString(value);
		}
		return value;
	}
	
	/**
	 * 返回错误消息数据。
	 */
	private String genErrorMessage(String message){
		String result="<result result=\"false\" message=\""+message +"\"/>";
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hotent.platform.webservice.api.ProcessService#getTaskOutNodes(java.lang.String)
	 */
	public List<BpmNode> getTaskOutNodes(String taskId) {
		TaskEntity taskEntity=bpmService.getTask(taskId);
		
		List<BpmNode> bpmNodes=new ArrayList<BpmNode>();
		FlowNode flowNode=NodeCache.getByActDefId(taskEntity.getProcessDefinitionId()).get(taskEntity.getTaskDefinitionKey());
		
		for(FlowNode fnode:flowNode.getNextFlowNodes()){
			BpmNode bpmNode=new BpmNode();
			bpmNode.setNodeId(fnode.getNodeId());
			bpmNode.setNodeName(fnode.getNodeName());
			bpmNode.setNodeType(fnode.getNodeType());
			bpmNodes.add(bpmNode);
		}
		return bpmNodes;
	}
	
	/**
	 * 获取当前任务节点的任务节点定义
	 * @param taskId
	 * @return
	 */
	public BpmNode getTaskNode(String taskId){
		TaskEntity taskEntity=bpmService.getTask(taskId);
		ProcessDefinitionEntity pde= bpmService.getProcessDefinitionEntity(taskEntity.getProcessDefinitionId());
	    ActivityImpl curAct=pde.findActivity(taskEntity.getTaskDefinitionKey());
	   
		String nodeName=(String)curAct.getProperty("name");
		String nodeType=(String)curAct.getProperty("type");
    	BpmNode bpmNode=new BpmNode();
    	bpmNode.setNodeId(curAct.getId());
    	bpmNode.setNodeType(nodeType);
    	bpmNode.setNodeName(nodeName);
    	
    	return bpmNode;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hotent.platform.webservice.api.ProcessService#getDestNodeHandleUsers(java.lang.String, java.lang.String)
	 */
	public Set<TaskExecutor> getDestNodeHandleUsers(String taskId, String destNodeId) {
		TaskEntity taskEntity=bpmService.getTask(taskId);
		String instanceId=taskEntity.getProcessInstanceId();
		Map<String,Object> vars=new HashMap<String, Object>();
		vars.put("executionId", taskEntity.getExecutionId());
		Set<TaskExecutor> userSet=bpmService.getNodeHandlerUsers(instanceId, destNodeId,vars);
		
		return userSet;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hotent.platform.webservice.api.ProcessService#setTaskVar(java.lang.String, com.hotent.platform.model.bpm.BpmVar)
	 */
	public void setTaskVar(String taskId, BpmVar bpmVar) {
		taskService.setVariable(taskId, bpmVar.getVarName(), bpmVar.getVarVal());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hotent.platform.webservice.api.ProcessService#setTaskVars(java.lang.String, java.util.List)
	 */
	public void setTaskVars(String taskId, List<BpmVar> bpmVars) {
		for(BpmVar var:bpmVars){
			setTaskVar(taskId,var);
		}
	}
	
	
	
	@Override
	public List<String> getApprovalItems(String taskId) {
		TaskEntity taskEntity =bpmService.getTask(taskId);
		ProcessRun processRun=processRunService.getByActInstanceId(taskEntity.getProcessInstanceId());
		String actDefId=processRun.getActDefId();
		String nodeId=taskEntity.getTaskDefinitionKey();
		List<String> list=taskApprovalItemsService.getApprovalByActDefId(actDefId, nodeId);
		return list;
	}
	
	@Override
	public ProcessTask getTask(String taskId) {
		ProcessTask processTask=taskDao.getByTaskId(taskId);
		constructTaskUrl(processTask);
		return processTask;
	}
	
	public Boolean getIsAllowBask(String taskId){
		TaskEntity taskEntity =bpmService.getTask(taskId);
		boolean isAllowBack=false;
		String taskToken=(String)taskService.getVariableLocal(taskEntity.getId(),TaskFork.TAKEN_VAR_NAME);
		// 设置了允许回退处理
		ExecutionStack executionStack = executionStackService.getLastestStack(taskEntity.getProcessInstanceId(), taskEntity.getTaskDefinitionKey(), taskToken);
		if(executionStack==null) return  false;
		
		if (executionStack.getParentId() != null && executionStack.getParentId() != 0) {
			ExecutionStack parentStack = executionStackService.getById(executionStack.getParentId());
			if (parentStack != null) {
				isAllowBack = true;
			}
		}
		return isAllowBack;
	}
	
	@Override
	public Integer getStatusByRunidNodeid(String runId, String nodeId) {
		return bpmProStatusDao.getStatusByRunidNodeid(runId, nodeId);
	}
	
	@Override
	public String getTaskNameByTaskId(String taskId) {
		ProcessTask processTask = taskDao.getByTaskId(taskId);
		if(processTask!=null)
			return processTask.getName();
		return null;
	}
	
	@Override
	public List<BpmFinishTask> getByFinishTask(Long userId, String subject,
			String taskName, PageBean pb) {
		return taskOpinionService.getByFinishTask(userId, subject, taskName, pb);
	}
	
	/**
	 * 返回某个任务审批的明细表单地址
	 * @param actInstId
	 * @param nodeKey
	 * @return
	 */
	public String getFinishTaskDetailUrl(String actInstId,String nodeKey){
		ProcessRun processRun =processRunService.getByActInstanceId(actInstId);
		//processRun.
		BpmNodeSet bpmNodeSet=bpmNodeSetService.getByActDefIdNodeId(processRun.getActDefId(), nodeKey);
		
		String detailUrl=bpmNodeSet.getDetailUrl();
		if(StringUtils.isNotEmpty(detailUrl)){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("pk", processRun.getBusinessKey());
			detailUrl=StringUtil.formatParamMsg(detailUrl, map);
		}
		return detailUrl;
	}



	
	public Boolean getIsAllowAddSign(Long userId,String taskId){
		TaskEntity taskEntity =bpmService.getTask(taskId);
		boolean isAllowAddSign=false;
		ProcessRun processRun=processRunService.getByActInstanceId(taskEntity.getProcessInstanceId());
		boolean isSignTask=bpmService.isSignTask(taskEntity);
		if(isSignTask){
			ISysOrg sysOrg = sysOrgService.getDefaultOrgByUserId(userId);
			if(sysOrg!=null){
				Long orgId = sysOrg.getOrgId();
				isAllowAddSign = bpmNodeSignService.checkNodeSignPrivilege(processRun.getActDefId(),taskEntity.getTaskDefinitionKey(), BpmNodeSignService.BpmNodePrivilegeType.ALLOW_RETROACTIVE, userId, orgId);
			}
		}
		return isAllowAddSign;
	}
	
	@WebMethod
	public Boolean getIsSelectedUser(String taskId){
		boolean isHidePath=false;
		TaskEntity taskEntity =bpmService.getTask(taskId);
		String nodeId = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(actDefId, nodeId);
		if(BpmNodeSet.HIDE_PATH.equals(bpmNodeSet.getIsHidePath())){
			isHidePath=true;
		}
		return isHidePath;
	}
	
	public void addSignUsers(String signUserIds,String taskId){
		if (StringUtils.isNotEmpty(taskId) && StringUtils.isNotEmpty(signUserIds)) {
			taskSignDataService.addSign(signUserIds, taskId);
			
		}
	}
	
	public ProcessRun getProcessRunByTaskId(String taskId){
		TaskEntity taskEntity =bpmService.getTask(taskId);
		ProcessRun processRun=processRunService.getByActInstanceId(taskEntity.getProcessInstanceId());
		return processRun;
	}



	@Override
	public List<BpmDefinition> getBpmDefinition(Long userId, PageBean pb) {
		//设置过滤参数
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("status", BpmDefinition.STATUS_DEPLOYED);
		params.put("disableStatus", BpmDefinition.DISABLEStATUS_EN);

		// 取得当前用户所有的角色Ids
		String roleIds = sysRoleService.getRoleIdsByUserId(userId);
		if (StringUtils.isNotEmpty(roleIds)) {
			params.put("roleIds", roleIds);
		}
		// 取得当前组织
		String orgIds = sysOrgService.getOrgIdsByUserId(userId);
		if (StringUtils.isNotEmpty(orgIds)) {
			params.put("orgIds", orgIds);
		}
		// 非超级管理员需要按权限过滤
		params.put("userId", userId);
		return bpmDefinitionService.getByUserId(userId,params,pb);
	}

	@Override
	public List<ProcessRun> getProcessRunByActDefId(String actDefId, PageBean pb) {
		return processRunService.getByActDefId(actDefId, pb);
	}
	
	
}
