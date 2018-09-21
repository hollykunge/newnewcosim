package com.hotent.platform.service.bpm.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation;
import com.hotent.platform.service.system.SysUserService;

/**
 * 根据节点用户设置为“用户”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationUser implements IBpmNodeUserCalculation { //实现IBpmNodeUserCalculation接口
	@Resource
	private SysUserService sysUserService;//注入SysUserService

	@Override
	public String getTitle() {//实现于  IBpmNodeUserCalculation 接口  的方法       获取用户类型名称
		return "用户";
	}

	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {//继承的方法
		Set<TaskExecutor> uIdSet = new HashSet<TaskExecutor>();
		List<ISysUser> sysUsers = new ArrayList<ISysUser>();
		String uids = bpmNodeUser.getCmpIds();
		if (!StringUtil.isEmpty(uids)) {
			String[] aryUid = uids.split("[,]");
			Set<String> uidSet = new HashSet<String>(Arrays.asList(aryUid));
			sysUsers = sysUserService.getByIdSet(uidSet);
			for (ISysUser sysUser : sysUsers) {
				uIdSet.add(TaskExecutor.getTaskUser(sysUser.getUserId()
						.toString(), sysUser.getFullname()));
			}
		}

		return uIdSet;
	}
}
