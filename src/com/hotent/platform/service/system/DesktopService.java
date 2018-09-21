package com.hotent.platform.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.db.JdbcHelper;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.dao.bpm.TaskDao;
import com.hotent.platform.dao.mail.OutMailDao;
import com.hotent.platform.dao.system.MessageSendDao;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.mail.OutMail;
import com.hotent.platform.model.system.MessageSend;

public class DesktopService {
	@Resource
	private MessageSendDao messageSendDao;
	@Resource
	private TaskDao taskDao;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private OutMailDao outMailDao;

	/**
	 * 个人信息
	 * 
	 * @return
	 */
	public ISysUser getUser() {
		ISysUser u = ContextUtil.getCurrentUser();
		return u;

	}

	/**
	 * 内部消息
	 * 
	 * @return
	 */
	public List<?> getMessage() {
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		List<MessageSend> list = messageSendDao.getNotReadMsgByUserId(
				ContextUtil.getCurrentUserId(), pb);
		return list;
	}

	/**
	 * 待办任务
	 * 
	 * @return
	 */
	public List<ProcessTask> forMe() {
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		List<ProcessTask> list = new ArrayList<ProcessTask>();
		list = taskDao.getTasks(ContextUtil.getCurrentUserId(), null, null,
				null, null, "desc", pb);
		return list;
	}

	/**
	 * 我的审批的流程
	 * 
	 * @return
	 */
	public List<ProcessRun> myAttend() {
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		//去掉进行分页的总记录数的查询
		pb.setShowTotal(false);
		List<ProcessRun> list = processRunDao.getMyAttend(
				ContextUtil.getCurrentUserId(), null, pb);
		return list;
	}

	/**
	 * 我发起的流程
	 * 
	 * @return
	 */
	public List<ProcessRun> myStart() {
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		List<ProcessRun> list = processRunDao.myStart(
				ContextUtil.getCurrentUserId(), pb);
		return list;
	}

	/**
	 * 获取用户未读邮件。
	 * 以时间降序排序，最多取10条。
	 * @return 用户未读邮件对象列表
	 */
	public List<OutMail> myNewMail() {
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		List<OutMail> list = outMailDao.getMailByUserId(
				ContextUtil.getCurrentUserId(), pb);
		return list;

	}

	
	/**
	 * 我的代理任务
	 * 
	 * @return
	 */
	public List<TaskEntity> forAgent() {
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		List<TaskEntity> list = taskDao.getAllAgentTask(
				ContextUtil.getCurrentUserId(), pb);
		return list;

	}

}
