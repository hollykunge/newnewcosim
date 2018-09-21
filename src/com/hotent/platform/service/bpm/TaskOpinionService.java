package com.hotent.platform.service.bpm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessExecution;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.page.PageBean;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.dao.bpm.ExecutionDao;
import com.hotent.platform.dao.bpm.TaskOpinionDao;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.webservice.model.BpmFinishTask;

/**
 * 对象功能:流程任务审批意见 Service类 开发公司: 开发人员:csx 创建时间:2012-01-11 16:06:11
 */
@Service
public class TaskOpinionService extends BaseService<TaskOpinion> {
	@Resource
	private TaskOpinionDao dao;

	@Resource
	private ExecutionDao executionDao;

	public TaskOpinionService() {
	}

	@Override
	protected IEntityDao<TaskOpinion, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 取得对应该任务的执行
	 * 
	 * @param taskId
	 * @return
	 */
	public TaskOpinion getByTaskId(Long taskId) {
		return dao.getByTaskId(taskId);
	}

	/**
	 * 取得某个任务的所有审批意见
	 * 
	 * @param actInstId
	 * @return
	 */
	public List<TaskOpinion> getByActInstId(String actInstId) {
		// 根据actInstId获取所以相关的流程实例ID
		List<String> actInstIds = new ArrayList<String>();
		putRelateActInstIdIntoList(actInstIds, actInstId);
		return dao.getByActInstId(actInstIds.toArray(new String[actInstIds.size()]));
	}

	/**
	 * 递归获取所有关联的流程实例ID.(根据子流程获取所有父流程ID)
	 * 
	 * @param actInstIds
	 * @param actInstId
	 */
	private void putRelateActInstIdIntoList(List<String> actInstIds, String actInstId) {
		actInstIds.add(actInstId);
		ProcessExecution execution = executionDao.getById(actInstId);
		if (execution != null && !StringUtil.isEmpty(execution.getSuperExecutionId())) {
			ProcessExecution superExecution = executionDao.getById(execution.getSuperExecutionId());
			if (superExecution != null) {
				putRelateActInstIdIntoList(actInstIds, superExecution.getProcessInstanceId());
			}
		}
	}

	/**
	 * 根据流程实例Id及任务定义Key取得审批列表
	 * 
	 * @param actInstId
	 * @param taskKey
	 * @return
	 */
	public List<TaskOpinion> getByActInstIdTaskKey(String actInstId, String taskKey) {
		return dao.getByActInstIdTaskKey(actInstId, taskKey);
	}

	/**
	 * 取到最新的某个节点的审批记录
	 * 
	 * @param actInstId
	 * @param taskKey
	 * @return
	 */
	public TaskOpinion getLatestTaskOpinion(String actInstId, String taskKey) {
		List<TaskOpinion> list = getByActInstIdTaskKey(actInstId, taskKey);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 取得某个流程实例中，某用户最新的完成的审批记录
	 * 
	 * @param actInstId
	 * @param exeUserId
	 * @return
	 */
	public TaskOpinion getLatestUserOpinion(String actInstId, Long exeUserId) {
		List<TaskOpinion> taskOpinions = dao.getByActInstIdExeUserId(actInstId, exeUserId);
		if (taskOpinions.size() == 0)
			return null;
		return taskOpinions.get(0);
	}

	/**
	 * 按任务ID删除
	 * 
	 * @param taskId
	 */
	public void delByTaskId(Long taskId) {
		dao.delByTaskId(taskId);
	}

	/**
	 * 取得已经审批完成的任务
	 * 
	 * @param userId
	 * @param subject
	 *            事项名
	 * @param taskName
	 *            任务名
	 * @param pb
	 *            分页
	 * @return
	 */
	public List<BpmFinishTask> getByFinishTask(Long userId, String subject, String taskName, PageBean pb) {
		return dao.getByFinishTask(userId, subject, taskName, pb);
	}

}
