package com.hotent.platform.service.bpm.impl;

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
 * 根据节点用户设置为“用户属性”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationUserAttr implements IBpmNodeUserCalculation {
	@Resource
	private SysUserService sysUserService;

	@Override
	public String getTitle() {
		return "用户属性";
	}

	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		Set<TaskExecutor> uIdSet = new HashSet<TaskExecutor>();
		try {
			List<ISysUser> sysUsers = sysUserService.getByUserParam(bpmNodeUser
					.getCmpIds());
			for (ISysUser sysUser : sysUsers) {
				uIdSet.add(TaskExecutor.getTaskUser(sysUser.getUserId()
						.toString(), sysUser.getFullname()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uIdSet;
	}

}
