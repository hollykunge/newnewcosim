package com.hotent.platform.service.bpm.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.core.model.TaskExecutor;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;

/**
 * 根据节点用户设置为“与发起人相同部门”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationSameDepartment implements
		IBpmNodeUserCalculation {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysUserOrgService sysUserOrgService;


	@Override
	public String getTitle() {
		return "与发起人相同部门";
	}

	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		Set<TaskExecutor> uIdSet = new HashSet<TaskExecutor>();

		Long startUserId = vars.startUserId;
		List<ISysUser> sysUsers = new ArrayList<ISysUser>();
		List<SysUserOrg> orgIds = sysUserOrgService.getOrgByUserId(startUserId);
		for (SysUserOrg sysUserOrg : orgIds) {
			List<SysUserOrg> sysUserOrgs = sysUserOrgService
					.getByOrgId(sysUserOrg.getOrgId());
			for (SysUserOrg userOrg : sysUserOrgs) {
				ISysUser user = sysUserService.getById(userOrg.getUserId());
				sysUsers.add(user);
			}
		}

		for (ISysUser sysUser : sysUsers) {
			uIdSet.add(TaskExecutor.getTaskUser(sysUser.getUserId().toString(),
					sysUser.getFullname()));
		}
		return uIdSet;
	}
}
