/**
 * 对象功能:流程任务评论 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:phl
 * 创建时间:2012-02-14 10:31:55
 */
package com.hotent.platform.dao.bpm;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.bpm.BpmTaskComment;

@Repository
public class BpmTaskCommentDao extends BaseDao<BpmTaskComment>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return BpmTaskComment.class;
	}
	/**
	 * 根据act流程定义id删除对应的流程任务评论
	 * @param actDefId
	 */
	public void delByactDefId(String actDefId){
		delBySqlKey("delByactDefId", actDefId);
	}
}