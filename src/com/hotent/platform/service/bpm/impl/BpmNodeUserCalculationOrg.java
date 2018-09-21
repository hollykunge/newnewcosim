package com.hotent.platform.service.bpm.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;

/**
 * 根据节点用户设置为“组织”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationOrg implements IBpmNodeUserCalculation {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysUserOrgService sysUserOrgService;


	@Override
	public String getTitle() {
		return "组织";
	}
	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		Set<TaskExecutor> userIdSet = new HashSet<TaskExecutor>();
		boolean extractUser=vars.extractUser;
		String uids=bpmNodeUser.getCmpIds();
		if(StringUtil.isEmpty(uids)){
			return userIdSet;
		}
		if(extractUser){
			List<SysUserOrg> userOrgList= sysUserOrgService.getUserByOrgIds(bpmNodeUser.getCmpIds());
			for (SysUserOrg sysUserOrg : userOrgList) {
				TaskExecutor taskExecutor=TaskExecutor.getTaskUser(sysUserOrg.getUserId().toString(),sysUserOrg.getUserName()) ;
				userIdSet.add(taskExecutor);
			}
		}
		//直接返回组织的执行人。
		else{
			String[] orgIds = bpmNodeUser.getCmpIds().split("[,]");
			String[] orgNames = bpmNodeUser.getCmpNames().split("[,]");
			for (int i = 0; i < orgIds.length; i++) {
				TaskExecutor taskExecutor=TaskExecutor.getTaskOrg(orgIds[i].toString(),orgNames[i]) ;
				userIdSet.add(taskExecutor);
			}
		}
		
		return userIdSet;
	}
}
