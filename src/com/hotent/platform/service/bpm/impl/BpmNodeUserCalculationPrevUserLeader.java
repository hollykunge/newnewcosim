package com.hotent.platform.service.bpm.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.core.model.TaskExecutor;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation;
import com.hotent.platform.service.system.UserUnderService;

/**
 * 根据节点用户设置为“上个任务执行人的领导”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationPrevUserLeader implements
		IBpmNodeUserCalculation {
	@Resource
	UserUnderService userUnderService;

	@Override
	public String getTitle() {
		return "上个任务执行人的领导";
	}

	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		Set<TaskExecutor> uIdSet = new HashSet<TaskExecutor>();
		Long prevUserId = vars.prevExecUserId;
		List<ISysUser> sysUsers = userUnderService.getMyLeaders(prevUserId);
		for (ISysUser sysUser : sysUsers) {
			uIdSet.add(TaskExecutor.getTaskUser(sysUser.getUserId().toString(),
					sysUser.getFullname()));
		}
		return uIdSet;
	}

}
