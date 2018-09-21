package com.hotent.platform.service.bpm;

import java.util.List;
import javax.annotation.Resource;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.bpm.BpmTaskAssigneeDao;
import com.hotent.platform.model.bpm.BpmRunLog;
import com.hotent.platform.model.bpm.BpmTaskAssignee;
import com.hotent.platform.model.bpm.ProcessRun;

/**
 * 对象功能:交办任务记录表 Service类
 * 开发公司:宏天
 * 开发人员:hotent
 * 创建时间:2012-10-08 08:46:26
 */
@Service
public class BpmTaskAssigneeService extends BaseService<BpmTaskAssignee>
{
	@Resource
	private BpmTaskAssigneeDao dao;
	@Resource
	private BpmService bpmService;	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmRunLogService bpmRunLogService;
	

	public BpmTaskAssigneeService()
	{
	}
	
	@Override
	protected IEntityDao<BpmTaskAssignee, Long> getEntityDao() 
	{
		return dao;
	}

	public List<BpmTaskAssignee> getAllMyTask(QueryFilter queryFilter) {
		return dao.getAllMyTask(queryFilter);
	}

	public int getCountByTaskId(String taskId) {
		return dao.getCountByTaskId(taskId);
	}
	
	public List<BpmTaskAssignee> getByTaskId(String taskId){
		return dao.getByTaskId(taskId);
	}

	public void delByTaskId(String taskId) {
		dao.delByTaskId(taskId);
	}
	
	/**
	 * 当前用户将 交办出去的任务收回。
	 * @param params
	 */
	public void back(Long id,ISysUser sysUser) {
		BpmTaskAssignee taskAssignee=dao.getById(id);
		String taskId=taskAssignee.getTaskId();
		Long runId=taskAssignee.getRunId();
		bpmService.updateTaskAssignee(taskId,sysUser.getUserId().toString());
		//在交办任务中删除该记录
		delById(id);
		//记录日志
		ProcessRun processRun=processRunService.getById(runId);
		String userName=sysUser.getFullname();
		TaskEntity taskEntity=bpmService.getTask(taskId);
		String memo="流程:" +processRun.getSubject() +", 【"+ userName+"】将任务【"+taskEntity.getName()+"】 收回";
		bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_BACK, memo);
	}
	
}
