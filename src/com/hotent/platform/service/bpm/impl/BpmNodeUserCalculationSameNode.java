package com.hotent.platform.service.bpm.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.ContextUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.system.SysUserService;

/**
 * 根据节点用户设置为“与其他节点相同执行人”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationSameNode implements IBpmNodeUserCalculation {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private TaskOpinionService taskOpinionService;


	
	@Override
	public String getTitle() {
		return "与其他节点相同执行人";
	}

	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		Set<TaskExecutor> uIdSet = new HashSet<TaskExecutor>();

		String actInstId = vars.actInstId;
		List<ISysUser> sysUsers = new ArrayList<ISysUser>();
		String nodeId = bpmNodeUser.getCmpIds();
		TaskOpinion taskOpinion = taskOpinionService.getLatestTaskOpinion(
				actInstId, nodeId);
		if (taskOpinion != null) {
			Long userId =taskOpinion.getExeUserId();
			if(userId==null){
				userId = ContextUtil.getCurrentUserId();
			}
			if(userId==null){
				return uIdSet;
			}
			ISysUser user = sysUserService.getById(userId);
			sysUsers.add(user);
		}

		for (ISysUser sysUser : sysUsers) {
			uIdSet.add(TaskExecutor.getTaskUser(sysUser.getUserId().toString(),
					sysUser.getFullname()));
		}
		return uIdSet;
	}

}
