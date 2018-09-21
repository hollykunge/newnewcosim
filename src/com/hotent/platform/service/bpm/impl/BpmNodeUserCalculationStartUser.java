package com.hotent.platform.service.bpm.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.core.model.TaskExecutor;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation;
import com.hotent.platform.service.system.SysUserService;

/**
 * 根据节点用户设置为“发起人”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationStartUser implements IBpmNodeUserCalculation {

	@Resource
	private SysUserService sysUserService;

	@Override
	public String getTitle() {
		return "发起人";
	}

	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		Set<TaskExecutor> uIdSet = new HashSet<TaskExecutor>();
		Long startUserId = vars.startUserId;
		List<ISysUser> sysUsers = new ArrayList<ISysUser>();
		ISysUser sysUser = sysUserService.getById(startUserId);
		sysUsers.add(sysUser);
		for (ISysUser sysU : sysUsers) {
			uIdSet.add(TaskExecutor.getTaskUser(sysU.getUserId().toString(),
					sysU.getFullname()));
		}
		return uIdSet;
	}

}
