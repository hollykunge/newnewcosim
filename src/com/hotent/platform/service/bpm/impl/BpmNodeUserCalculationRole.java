package com.hotent.platform.service.bpm.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.system.UserRole;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserRoleService;

/**
 * 根据节点用户设置为“角色”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationRole implements IBpmNodeUserCalculation {
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private SysUserService sysUserService;

	@Override
	public String getTitle() {
		return "角色";
	}

	
	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		boolean extractUser=vars.extractUser;
		Set<TaskExecutor> userIdSet = new HashSet<TaskExecutor>();
		String uids=bpmNodeUser.getCmpIds();
		if(StringUtil.isEmpty(uids)){
			return userIdSet;
		}
		//抽取用户。
		if(extractUser){
			List<UserRole> userList = userRoleService.getUserByRoleIds(bpmNodeUser.getCmpIds());
			for (UserRole user : userList) {
				TaskExecutor taskExecutor=TaskExecutor.getTaskUser(user.getUserId().toString(),user.getFullname()) ;
				userIdSet.add(taskExecutor);
			}
		}
		//直接返回角色。
		else{
			String[] roleIds = bpmNodeUser.getCmpIds().split("[,]");
			String[] roleNames = bpmNodeUser.getCmpNames().split("[,]");
			for (int i = 0; i < roleIds.length; i++) {
				TaskExecutor taskExecutor=TaskExecutor.getTaskRole(roleIds[i],roleNames[i]) ;
				userIdSet.add(taskExecutor);
			}
		}
		
		return userIdSet;
	}
}
