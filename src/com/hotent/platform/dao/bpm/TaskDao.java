package com.hotent.platform.dao.bpm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.db.BaseDao;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.DateUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.TaskUser;

@Repository
public class TaskDao extends BaseDao<TaskEntity>
{
	@Override
	public Class<TaskEntity> getEntityClass()
	{
		return TaskEntity.class;
	}
	
	@Override
	public String getIbatisMapperNamespace()
	{
		return "com.hotent.core.bpm.model.ProcessTask";
	}
	
	public void updTaskExecution(String taskId){
		this.update("updTaskExecution", taskId);
	}
	
	/**
	 * 获取我的任务
	 * @param queryFilter
	 * @return
	 */
	public List<TaskEntity> getMyTasks(Long userId ,QueryFilter queryFilter)
	{
		String statmentName="getAllMyTask";
		queryFilter.getFilters().put("userId",userId);
		return getBySqlKey(statmentName, queryFilter);
	}
	
	/**
	 * 获取我的手机任务列表
	 * @param filter
	 * @return
	 */
	public List<TaskEntity> getMyMobileTasks(QueryFilter filter) {
		String statmentName="getMyMobileTask";
		return getBySqlKey(statmentName, filter);
	}
	
	/**
	 * 查找某用户的待办任务
	 * @param userId 用户ID 
	 * @param taskName 任务名称
	 * @param subject  任务标题 
	 * @param processName 流程定义名称
	 * @param orderField 排序字段
	 * @param orderSeq  升序或降序 值有 asc 或 desc
	 * @return
	 */
	public List<ProcessTask> getTasks(Long userId,String taskName,String subject,String processName,String orderField,String orderSeq,PageBean pb){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("name", taskName);
		params.put("subject", subject);
		params.put("processName", processName);
		params.put("orderField", orderField);
		params.put("orderSeq", orderSeq);
		List list=getBySqlKey("getAllMyTask",params,pb);
		return list;
	}
	
	
	/**
	 * 获取我的任务
	 * @param userId
	 * @return
	 */
	public List<TaskEntity> getMyEvents(Map param){
		
		Log logger = LogFactory.getLog(getClass());
		Map map = (Map)param;
		String mode = (String)map.get("mode");
		String sDate = (String)map.get("startDate");
		String eDate = (String)map.get("endDate");
		
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate=null;
		Date endDate=null;
		
		if("month".equals(mode)){
			
			try{
				Date reqDate=DateUtils.parseDate(sDate, new String[]{"MM/dd/yyyy"});
				Calendar cal=Calendar.getInstance();
				cal.setTime(reqDate);
				startDate=DateUtil.setStartDay(cal).getTime();
				reqDate=DateUtils.parseDate(eDate, new String[]{"MM/dd/yyyy"});
				cal.setTime(reqDate);
				endDate=DateUtil.setEndDay(cal).getTime();
				
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
			
		}else if("day".equals(mode)){

			try{
				Date reqDay=DateUtils.parseDate(sDate, new String[]{"MM/dd/yyyy"});
				
				Calendar cal=Calendar.getInstance();
				cal.setTime(reqDay);
			
				//开始日期为本月1号 00时00分00秒
				startDate=DateUtil.setStartDay(cal).getTime();
				
				cal.add(Calendar.MONTH, 1);
				cal.add(Calendar.DAY_OF_MONTH, -1);
				
				//结束日期为本月最后一天的23时59分59秒
				endDate=DateUtil.setEndDay(cal).getTime();
				
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
			
		}else if("week".equals(mode)){
			
			try{
				Date reqStartWeek=DateUtils.parseDate(sDate, new String[]{"MM/dd/yyyy"});
				Date reqEndWeek=DateUtils.parseDate(eDate, new String[]{"MM/dd/yyyy"});
				Calendar cal=Calendar.getInstance();
				
				cal.setTime(reqStartWeek);
				
				startDate=DateUtil.setStartDay(cal).getTime();
				cal.setTime(reqEndWeek);

				endDate=DateUtil.setEndDay(cal).getTime();
				
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
			
		}else if("workweek".equals(mode)){
			try{
				Date reqStartWeek=DateUtils.parseDate(sDate, new String[]{"MM/dd/yyyy"});
				Date reqEndWeek=DateUtils.parseDate(eDate, new String[]{"MM/dd/yyyy"});
				Calendar cal=Calendar.getInstance();
				
				cal.setTime(reqStartWeek);
				
				startDate=DateUtil.setStartDay(cal).getTime();
				cal.setTime(reqEndWeek);

				endDate=DateUtil.setEndDay(cal).getTime();
				
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", ContextUtil.getCurrentUserId());
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		return getBySqlKey("getAllMyEvent", params);
	}
	
	/**
	 * 获取我的任务
	 * @param queryFilter
	 * @return
	 */
	public List<TaskEntity> getAgentTasks(Long userId,String actDefId,QueryFilter queryFilter)
	{
		String statmentName="getByAgent";
		queryFilter.getFilters().put("userId",userId);
		queryFilter.getFilters().put("actDefId",actDefId);
		return getBySqlKey(statmentName, queryFilter);
	}
	
	/**
	 * 根据taskId和受代理人ID获取的代理人ID
	 * @param queryFilter
	 * @return
	 */
	public List<Long> getAgentIdByTaskId(String taskId,String userId){
		Map map=new HashMap();
		map.put("taskId", taskId);
		map.put("userId", userId);
		List list= getBySqlKey("getAgentIdByTaskId",map);
		return list;
	}
	
	/**
	 * 设置任务的到期日期
	 * @param taskId
	 * @param dueDate
	 * @return
	 */
	public int setDueDate(String taskId,Date dueDate)
	{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("taskId",taskId);
		params.put("dueDate", dueDate);
		return update("setDueDate", params);
	}
	
	/**
	 * 生成任务
	 * @param task
	 */
	public void insertTask(ProcessTask task)
	{
		String statement=getIbatisMapperNamespace()+".add";
		getSqlSessionTemplate().insert(statement, task);
	}
	
	/**
	 * 取得任务的候选用户
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TaskUser> getCandidateUsers(String taskId){
		List list=getBySqlKey("getCandidateUsers", taskId);
		return list;
	}

	public List<TaskEntity> getAllAgentTask(Long userId, QueryFilter filter) {
		String statmentName="getAllAgent";
		filter.getFilters().put("userId",userId);
		return getBySqlKey(statmentName, filter);
	}
     
	/**
	 * 取得未到期的任务。
	 * @return
	 */
	public List<ProcessTask> getReminderTask(){
		Date curDate=new Date(System.currentTimeMillis());
		List<ProcessTask> list=this.getSqlSessionTemplate().selectList("getReminderTask", curDate);
		return list;
	}
	
	/**
	 * 取得某个流程实例下的任务列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<ProcessTask> getTasksByRunId(Long runId){
		List list=getBySqlKey("getTasksByRunId",runId);
		return list;
	}
	
	/**
	 * 更新任务的执行人
	 * @param taskId
	 * @param userId
	 */
	public void updateTaskAssignee(String taskId,String userId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("taskId", taskId);
		params.put("userId", userId);
		update("updateTaskAssignee", params);
	}
	
	public void updateTaskAssigneeNull(String taskId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("taskId", taskId);
		update("updateTaskAssigneeNull", params);
	}
	/**
	 * 更新任务的所属人
	 * @param taskId
	 * @param userId
	 */
	public void updateTaskOwner(String taskId,String userId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("taskId", taskId);
		params.put("userId", userId);
		update("updateTaskOwner", params);
	}
	
	/**
	 * 按任务名称或任务Id列表取到某一用户的待办事项列表
	 * @param userId
	 * @param taskName
	 * @param taskIds
	 * @param pb
	 * @return
	 */
	public List<ProcessTask> getByTaskNameOrTaskIds(String userId,String taskName, String taskIds,PageBean pb){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("taskName", taskName);
		params.put("taskIds", taskIds);
		List list= getBySqlKey("getByTaskNameOrTaskIds", params, pb);
		return list;
	}
	/**
	 * 按TaskId返回任务实体
	 * @param taskId
	 * @return
	 */
	public ProcessTask getByTaskId(String taskId){
		return (ProcessTask)getOne("getByTaskId", taskId);
	}
	
	/**
	 * 根据流程实例ID获取任务。
	 * @param taskId
	 * @return
	 */
	public List<ProcessTask> getByInstanceId(String instanceId){
		List list=getBySqlKey("getByInstanceId",instanceId);
		return list;
	}
	
	/**
	 * 根据流程实例删除任务。
	 * @param instanceId
	 */
	public void delByInstanceId(Long instanceId){
		this.update("delByInstanceId",instanceId);
	}
	
	/**
	 * 工作台显示我的代理任务
	 * @param creatorId
	 * @param pb
	 * @return
	 */
	public List<TaskEntity> getAllAgentTask(Long userId,PageBean pb){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		return getBySqlKey("getByAgent", params, pb);
	}
	
	/**
	 * 根据流程实例ID删除任务候选人。
	 * @param instanceId
	 */
	public void delCandidateByInstanceId(Long instanceId){
		this.update("delCandidateByInstanceId", instanceId);
	}
	
	public void updateNewTaskDefKeyByInstIdNodeId(String newTaskDefKey,String oldTaskDefKey,String actInstId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("newTaskDefKey", newTaskDefKey);
		params.put("oldTaskDefKey", oldTaskDefKey);
		params.put("actInstId", actInstId);
		this.update("updateNewTaskDefKeyByInstIdNodeId",params);
	}
	
	public void updateOldTaskDefKeyByInstIdNodeId(String newTaskDefKey,String oldTaskDefKey,String actInstId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("newTaskDefKey", StringUtil.isNotEmpty(newTaskDefKey)?(newTaskDefKey+"%"):newTaskDefKey);
		params.put("oldTaskDefKey", oldTaskDefKey);
		params.put("actInstId", actInstId);
		this.update("updateOldTaskDefKeyByInstIdNodeId",params);
	}

	/**
	 * 根据任务id获取是否有候选人。
	 * @param taskIds
	 * @return
	 */
	public List<Map> getHasCandidateExecutor(String taskIds){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("taskIds",taskIds);
		String statement= getIbatisMapperNamespace() + ".getHasCandidateExecutor" ;
		List<Map> list=getSqlSessionTemplate().selectList(statement,params);
		return list;
	}
	
	/**
	 * 根据流程actDefId删除任务。
	 * @param instanceId
	 */
	public void delByActDefId(String actDefId){
		this.update("delByActDefId",actDefId);
	}
	
	/**
	 * 根据流程actDefId删除任务候选人。
	 * @param instanceId
	 */
	public void delCandidateByActDefId(String actDefId){
		this.update("delCandidateByActDefId", actDefId);
	}
}
