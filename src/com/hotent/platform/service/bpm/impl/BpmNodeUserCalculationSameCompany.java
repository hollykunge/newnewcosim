package com.hotent.platform.service.bpm.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.runtime.ProcessInstance;

import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;

/**
 * 根据节点用户设置为“相同企业”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationSameCompany implements IBpmNodeUserCalculation {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private TaskOpinionService taskOpinionService;
	@Resource
	BpmService bpmService;
	@Resource 
	SysOrgInfoService sysOrgInfoService;

	@Override
	public String getTitle(){
		return "与其它节点相同企业";
	}
	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		Set<TaskExecutor> uIdSet = new HashSet<TaskExecutor>();

		String actInstId = vars.actInstId;
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
			Long compId = user.getOrgId();
			SysOrgInfo comp = sysOrgInfoService.getById(compId);
			uIdSet.add(new TaskExecutor(TaskExecutor.USER_TYPE_COMP,compId.toString(),comp.getName()));
		}

		return uIdSet;
	}
}
