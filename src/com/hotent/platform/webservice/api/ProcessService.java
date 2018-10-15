package com.hotent.platform.webservice.api;

import java.util.List;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.page.PageBean;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNode;
import com.hotent.platform.model.bpm.BpmVar;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.webservice.model.BpmFinishTask;
import com.hotent.platform.webservice.model.BpmTaskOpinion;

/**
 * 流程操作Web服务对外接口类
 * @author csx
 *
 */
@WebService
public interface ProcessService {
	
	
	
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
	 * &lt;start actDefId="ebuyOfjoy:1:27908" flowKey="" subject="webservice启动测试" ,<br /> startUser="admin" mailInform="true" smsInform="false">
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
	public String startByXml(String xml);
	
	/**
	 * 客户端查找某用户的待办任务<br>
	 * <pre>
	 * 待办任务参数包括：
	 * userId：用户ID myAccount:用户账号，两个参数可任意定义一个。
	 * processName:流程定义名称 。
	 * orderField：排序字段。
	 * pagesize：页数
	 * currentPage：当前页
	 * orderSeq：升序或降序 值有 asc 或 desc。
	 * &lt;mytask userId="" account="" taskName="" subject="" pagesize="" currentPage=""  processName="" orderField="" orderSeq="" >
	 * &lt;/mytask> 。
	 * 
	 * @return：待办事项列表
	 */
	public List<ProcessTask> getTasksByXml(String xml)throws Exception;
	/**
	 * 启动流程实例
	 * @param processCmd
	 */
	@WebMethod
	public ProcessRun start(ProcessCmd processCmd) throws Exception;
	
	/**
	 * 按任务ID结束流程实例
	 * @param taskId 当前任务Id
	 * @return
	 */
	@WebMethod
	public boolean endProcessByTaskId(String taskId);
	
	/**
	 * 执行下一步。
	 * <pre>
	 * 必填参数：
	 * taskId：任务ID 不能为空。
	 * userAccount：必须填写。
	 * voteAgree：1=同意 2=反对 0=弃权 3=驳回  若设置为值，流程回退
	 * voteContent：执行意见信息。
	 * </pre>
	 * @param processCmd
	 * @throws Exception
	 */
	@WebMethod
	public void doNext(ProcessCmd processCmd) throws Exception;
	
	/**
     * 客户端提交xml,执行流程往下跳转。
	 * <pre>
	 * 流程参数包括：
	 * taskId：任务ID 不能为空。
	 * userAccount：必须填写。
	 * voteAgree：1=同意 2=反对 0=弃权 3=驳回  若设置为值，流程回退
	 * voteContent：备注信息
	 * 流程变量数可以是0..*个。
	 * 流程变量的数据类型为 int,long,double,date,string。
	 * &lt;donext taskId="" userAccount="" voteAgree="" voteContent="" >
	 * 		&lt;var name="" value="" type="int,long,double,date,String"  />
	 * &lt;/donext> 。
	 * 
	 * 执行成功调用doNext方法进行下一步审批:
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	public String doNextByXml(String xml) throws Exception;
	
	/**
	 * 查找某用户的待办任务
	 * @param userId 用户ID 
	 * @param taskName 任务名称
	 * @param subject  任务标题 
	 * @param processName 流程定义名称
	 * @param orderField 排序字段
	 * @param orderSeq  升序或降序 值有 asc 或 desc
	 * @param pb 分页
	 * @return 流程任务实例
	 */
	@WebMethod
	public List<ProcessTask> getTasksByUserId(Long userId, String taskName, String subject, String processName, String orderField, String orderSeq, PageBean pb);
	
	/**
	 * 查找某用户的待办任务
	 * @param userAccount
	 * @param taskName
	 * @param subject
	 * @param processName
	 * @param orderField
	 * @param orderSeq
	 * @param pb
	 * @return
	 */
	@WebMethod
	public List<ProcessTask> getTasksByAccount(String userAccount, String taskName, String subject, String processName, String orderField, String orderSeq, PageBean pb);
	
	/**
	 * 取得流程运行实例
	 * @param runId
	 * @return
	 */
	@WebMethod
	public ProcessRun getProcessRun(Long runId);
	
	/**
	 * 取得某个运行流程实例对应的任务列表
	 * @param runId
	 * @return
	 */
	public List<ProcessTask> getTasksByRunId(Long runId);
	
	/**
	 * 按运行实例Id取得某个流程的审批历史
	 * @param runId  流程运行实例表BPM_PRO_RUN表主键
	 * @return
	 */
	public List<BpmTaskOpinion> getProcessOpinionByRunId(Long runId);
	
	/**
	 * 按Activiti流程实例Id取得某个流程的审批历史
	 * @param actInstId
	 * @return
	 */
	public List<BpmTaskOpinion> getProcessOpinionByActInstId(String actInstId);
	
	/**
	 * 锁定任务,这个任务有指定帐号的用户执行。
	 * @param taskId 当前任务ID
	 * @param userAccount 授权的用户账号
	 */
	public void lockTask(String taskId, String userAccount);
	
	/**
	 * 解锁任务。
	 * <pre>
	 * 释放任务，这个任务可以由别人锁定处理。
	 * </pre>
	 * @param taskId 当前任务ID	 
	 */
	public void unlockTask(String taskId);
	
	
	/**
	 * 通过runId获取流程变量
	 * @param runId
	 * @return
	 */
	public List<BpmVar> getVariablesByRunId(Long runId);
	/**
	 * 通过taskId获取该流程的所有变量
	 * @param taskId
	 * @return
	 */
	public List<BpmVar> getVariablesByTaskId(String taskId);
	
	/**
	 * 设置流程变量
	 * @param taskId 当前任务iD
	 * @param bpmVar
	 */
	public void setTaskVar(String taskId, BpmVar bpmVar);
	/**
	 * 设置一组流程变量
	 * @param taskId 当前任务ID
	 * @param bpmVars 
	 */
	public void setTaskVars(String taskId, List<BpmVar> bpmVars);
	
	
	/**
	 * 取得流程实例
	 * @param account 用户账号
	 * @param subject 流程标题
	 * @param status 流程实例状态  1=正在运行 ,2=运行结束
	 * @param pb   分页对象。
	 * @return
	 */
	public List<ProcessRun> getMyProcessRun(String account, String subject, Short status, PageBean pb);
	
	/**
	 * 返回用户任务对应的导航URL
	 * @param taskId
	 * @return
	 */
	public String getTaskFormUrl(String taskId);
	
	/**
	 * 取到当前任务节点的后续节点
	 * @param taskId 当前任务id
	 * @return
	 */
	public List<BpmNode> getTaskOutNodes(String taskId);
	
	/**
	 * 取到当前任务的下一任务对应的所有执行人员列表
	 * @param taskId  当前任务id
	 * @param destNodeId 目标节点Id
	 * @return
	 */
	public Set<TaskExecutor> getDestNodeHandleUsers(String taskId, String destNodeId);
	
	/**
	 * 取得当前任务预先设置好的审批用语。
	 * @param taskId
	 * 
	 * @return
	 */
	public List<String> getApprovalItems(String taskId);
	
	
	/**
	 * 按任务ID取得任务实体
	 * @param taskId
	 * @return
	 */
	public ProcessTask getTask(String taskId);
	
	
	/**
	 * 获取当前任务节点的任务节点定义
	 * @param taskId
	 * @return
	 */
	public BpmNode getTaskNode(String taskId);
	
	/**
	 * 
	 */
	public void addSignUsers(String signUserIds, String taskId);
	
	/**
	 * 获取已经审批完成的任务信息列表
	 * @param userId
	 * @param subject
	 * @param taskName
	 * @param pb
	 * @return
	 */
	public List<BpmFinishTask> getByFinishTask(Long userId, String subject, String taskName, PageBean pb);
	
	 /**
	 * 返回某个任务审批的明细表单地址
	 * @param actInstId
	 * @param nodeKey
	 * @return
	 */
	@WebMethod
	public String getFinishTaskDetailUrl(String actInstId, String nodeKey);

	/**
	 * 任务是否允许回退
	 */
	@WebMethod
	public Boolean getIsAllowBask(String taskId);
	
	/**
	 * 根据runId和taskId获取任务的状态
	 * @param runId
	 * @param taskId
	 * @return
	 */
	@WebMethod
	public Integer getStatusByRunidNodeid(String runId, String nodeId);
	
	/**
	 * 根据taskid获取taskName
	 * @param taskId
	 * @return
	 */
	@WebMethod
	public String getTaskNameByTaskId(String taskId);
	
	/**
	 * 是否允许补签
	 * @param userId
	 * @param taskId
	 * @return
	 */
	@WebMethod
	public Boolean getIsAllowAddSign(Long userId, String taskId);
	
	/**
	 * 获取某个节点是否允许出现选择人员。
	 */
	@WebMethod
	public Boolean getIsSelectedUser(String taskId);
	
	@WebMethod
	public ProcessRun getProcessRunByTaskId(String taskId);
	
	/**
	 * 根据用户ID,按权限设置，获取流程定义
	 * @param userId 用户ID
	 * @param pb 分页Bean
	 * @return 流程定义列表
	 */
	@WebMethod
	public List<BpmDefinition> getBpmDefinition(Long userId, PageBean pb);
	
	/**
	 * 根据ACT流程定义id获取该流程的所有流程实例。
	 * @param actDefId ACT流程定义id 
	 * @return 流程运行实例列表
	 */
	@WebMethod
	public List<ProcessRun> getProcessRunByActDefId(String actDefI, PageBean pb);
	
}
