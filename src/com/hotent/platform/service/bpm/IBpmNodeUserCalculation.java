package com.hotent.platform.service.bpm;

import java.util.Set;

import com.hotent.core.model.TaskExecutor;
import com.hotent.platform.model.bpm.BpmNodeUser;

/**
 * 计算人员接口。 人员计算使用策略模式，方便对新增的策略进行扩展。
 * 
 * @author raise.
 * 
 */
public interface IBpmNodeUserCalculation {

	/**
	 * 根据规则进行计算时的参数
	 * 
	 * @author Raise
	 * 
	 */
	public class CalcVars {
		/**
		 * 构造函数
		 * 
		 * @param startUserId
		 *            流程发起人ID
		 * @param preExecUserId
		 *            上个执行人员的ID
		 * @param actInstId
		 *            流程实例ID
		 * @param extractUser
		 *            是否抽取用户
		 */
		public CalcVars(Long startUserId, Long preExecUserId, String actInstId,
				boolean extractUser) {
			this.startUserId = startUserId;
			this.prevExecUserId = preExecUserId;
			this.actInstId = actInstId;
			this.extractUser = extractUser;
		}

		/**
		 * 流程发起人ID
		 */
		public Long startUserId;
		/**
		 * 上个执行人员的ID
		 */
		public Long prevExecUserId;
		/**
		 * 流程实例ID
		 */
		public String actInstId;
		/**
		 * 是否抽取用户
		 */
		public boolean extractUser;
	};

	/**
	 * 根据流程节点人员设置规则，计算任务的执行者。</br> 如果参数extractUser为false，直接返回任务的执行者，不进行用户抽取，如
	 * 直接返回部门，而不计算部门人员
	 * 
	 * @param bpmNodeUser
	 *            流程节点用户设置
	 * @param extractUser
	 *            抽取用户标志
	 * @return 任务的执行者
	 */
	Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars calcVars);

	/**
	 * 流程节点人员设置规则的标题
	 * 
	 * @return
	 */
	String getTitle();
}
