package com.hotent.platform.service.bpm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.model.TaskExecutor;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation;
import com.hotent.platform.service.system.SysUserService;

/**
 * 根据节点用户设置为“脚本”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationScript implements IBpmNodeUserCalculation {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private GroovyScriptEngine groovyScriptEngine;

	@Override
	public String getTitle() {
		return "脚本";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		Set<TaskExecutor> uIdSet = new HashSet<TaskExecutor>();

		Long startUserId = vars.startUserId;
		Long prevUserId = vars.prevExecUserId;

		String script = bpmNodeUser.getCmpNames();

		List<ISysUser> sysUsers = new ArrayList<ISysUser>();

		Map<String, Object> grooVars = new HashMap<String, Object>();
		grooVars.put(BpmConst.StartUser, String.valueOf(startUserId));
		grooVars.put(BpmConst.PrevUser, prevUserId);
		Object result = groovyScriptEngine.executeObject(script, grooVars);
		if (result != null) {

			Set<String> set = (Set<String>) result;
			for (Iterator<String> it = set.iterator(); it.hasNext();) {
				String userId = it.next();
				ISysUser sysUser = sysUserService.getById(Long
						.parseLong(userId));
				sysUsers.add(sysUser);
			}

			for (ISysUser sysUser : sysUsers) {
				uIdSet.add(TaskExecutor.getTaskUser(sysUser.getUserId()
						.toString(), sysUser.getFullname()));
			}
		}

		return uIdSet;
	}

}
