package com.hotent.platform.service.bpm.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;

/**
 * 根据节点用户设置为“组织负责人”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationOrgCharge implements IBpmNodeUserCalculation {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysUserOrgService sysUserOrgService;

	@Override
	public String getTitle() {
		return "组织负责人";
	}

	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		Set<TaskExecutor> uIdSet = new HashSet<TaskExecutor>();
		
		List<ISysUser> sysUsers = new ArrayList<ISysUser>();
		if (!StringUtil.isEmpty(bpmNodeUser.getCmpIds())) {
			String[] orgIds = bpmNodeUser.getCmpIds().split("[,]");
			for (int i = 0; i < orgIds.length; i++) {
				List<SysUserOrg> sysUserOrgs = sysUserOrgService
						.getChargeByOrgId(new Long(orgIds[i]));
				for (SysUserOrg sysUserOrg : sysUserOrgs) {
					ISysUser sysUser = sysUserService.getById(sysUserOrg
							.getUserId());
					sysUsers.add(sysUser);
				}
			}
		}

		for (ISysUser sysUser : sysUsers) {
			uIdSet.add(TaskExecutor.getTaskUser(sysUser.getUserId().toString(),
					sysUser.getFullname()));
		}
		return uIdSet;
	}

}
