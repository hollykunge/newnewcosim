package com.hotent.platform.dao.bpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.BpmTaskAssignee;
/**
 * 对象功能:交办任务记录表 Dao类
 * 开发公司:宏天
 * 开发人员:hotent
 * 创建时间:2012-10-08 08:46:26
 */
@Repository
public class BpmTaskAssigneeDao extends BaseDao<BpmTaskAssignee>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return BpmTaskAssignee.class;
	}

	public List<BpmTaskAssignee> getAllMyTask(QueryFilter queryFilter) {
		return this.getBySqlKey("getAllMyTask", queryFilter);
	}

	public int getCountByTaskId(String taskId) {
		int count=(Integer)this.getOne("getCountByTaskId", taskId);
		return count;
	}

	public List<BpmTaskAssignee> getByTaskId(String taskId) {
		return this.getBySqlKey("getByTaskId", taskId);
	}

	public void delByTaskId(String taskId) {
		this.delBySqlKey("delByTaskId", taskId);
		
	}
	public void delByRunId(Long runId) {
		this.delBySqlKey("delByRunId", runId);
		
	}
	

}