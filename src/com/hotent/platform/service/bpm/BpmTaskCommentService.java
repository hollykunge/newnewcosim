package com.hotent.platform.service.bpm;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.platform.model.bpm.BpmTaskComment;
import com.hotent.platform.dao.bpm.BpmTaskCommentDao;

/**
 * 对象功能:流程任务评论Service类
 * 开发公司:
 * 开发人员:phl
 * 创建时间:2012-02-14 10:31:55
 */
@Service
public class BpmTaskCommentService extends BaseService<BpmTaskComment>
{
	@Resource
	private BpmTaskCommentDao dao;
	
	public BpmTaskCommentService()
	{
	}
	
	@Override
	protected IEntityDao<BpmTaskComment, Long> getEntityDao() 
	{
		return dao;
	}

}
