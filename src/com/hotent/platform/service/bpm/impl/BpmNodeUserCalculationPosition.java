package com.hotent.platform.service.bpm.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserPositionService;

/**
 * 根据节点用户设置为“岗位”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationPosition implements IBpmNodeUserCalculation {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private UserPositionService userPositionService;

	@Override
	public String getTitle() {
		return "岗位";
	}

	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		boolean extractUser = vars.extractUser;
		Set<TaskExecutor> userIdSet = new HashSet<TaskExecutor>();
		String uids = bpmNodeUser.getCmpIds();
		if (StringUtil.isEmpty(uids)) {
			return userIdSet;
		}

		// 抽取执行人
		if (extractUser) {
			List<UserPosition> userList = userPositionService
					.getUserByPosIds(bpmNodeUser.getCmpIds());
			for (UserPosition userPos : userList) {
				TaskExecutor taskExecutor = TaskExecutor.getTaskUser(userPos
						.getUserId().toString(), userPos.getFullname());
				userIdSet.add(taskExecutor);
			}
		}
		// 直接返回岗位
		else {
			String[] aryId = bpmNodeUser.getCmpIds().split("[,]");
			String[] aryName = bpmNodeUser.getCmpNames().split("[,]");
			for (int i = 0; i < aryId.length; i++) {
				TaskExecutor taskExecutor = TaskExecutor.getTaskPos(
						aryId[i].toString(), aryName[i]);
				userIdSet.add(taskExecutor);
			}
		}
		return userIdSet;
	}

}
