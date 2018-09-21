package com.hotent.platform.service.bpm.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.system.SysOrgType;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysOrgTypeService;
import com.hotent.platform.service.system.SysUserOrgService;

/**
 * 根据节点用户设置为“部门的上级类型部门的负责人”，计算执行人员。
 * 
 * @author Raise
 */
public class BpmNodeUserCalculationPrevTypeUserLeader implements
		IBpmNodeUserCalculation {
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysOrgTypeService sysOrgTypeService;
	@Resource
	private SysUserOrgService sysUserOrgService;

	public List<ISysUser> getExecutors(BpmNodeUser bpmNodeUser, CalcVars vars) {
		Long startUserId = vars.startUserId;
		Long prevUserId = vars.prevExecUserId;
		// String actInstId = vars.actInstId;
		List<ISysUser> users = new ArrayList<ISysUser>();
		String expandParams = bpmNodeUser.getCmpIds();
		if (StringUtil.isEmpty(expandParams)) {
			return users;
		}
		JSONObject json = JSONObject.fromObject(expandParams);

		ISysOrg startSysOrg = null;
		if (startUserId == ContextUtil.getCurrentUserId()) {
			startSysOrg = ContextUtil.getCurrentOrg();
		} else {
			startSysOrg = sysOrgService.getDefaultOrgByUserId(startUserId);
		}

		Long currentOrgId = 0L;
		if (startSysOrg != null) {
			currentOrgId = startSysOrg.getOrgId();
		} else {
			return users;
		}

		if ("0".equals(json.get("orgSource"))) { // 部门从发起人取
			users = getPrveTypeLeader(startUserId,
					json.get("level").toString(), json.get("stategy")
							.toString(), currentOrgId);
		} else { // 部门从上一步执行人取
			users = getPrveTypeLeader(prevUserId, json.get("level").toString(),
					json.get("stategy").toString(), currentOrgId);
		}
		return users;
	}

	@Override
	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, CalcVars vars) {
		Set<TaskExecutor> uIdSet = new HashSet<TaskExecutor>();

		Long startUserId = vars.startUserId;
		Long prevUserId = vars.prevExecUserId;
		// String actInstId = vars.actInstId;
		List<ISysUser> sysUsers = new ArrayList<ISysUser>();
		String expandParams = bpmNodeUser.getCmpIds();
		if (!StringUtil.isEmpty(expandParams)) {

			JSONObject json = JSONObject.fromObject(expandParams);

			ISysOrg startSysOrg = null;
			if (startUserId == ContextUtil.getCurrentUserId()) {
				startSysOrg = ContextUtil.getCurrentOrg();
			} else {
				startSysOrg = sysOrgService.getDefaultOrgByUserId(startUserId);
			}

			Long currentOrgId = 0L;
			if (startSysOrg != null) {
				currentOrgId = startSysOrg.getOrgId();
				if ("0".equals(json.get("orgSource"))) { // 部门从发起人取
					sysUsers = getPrveTypeLeader(startUserId, json.get("level")
							.toString(), json.get("stategy").toString(),
							currentOrgId);
				} else { // 部门从上一步执行人取
					sysUsers = getPrveTypeLeader(prevUserId, json.get("level")
							.toString(), json.get("stategy").toString(),
							currentOrgId);
				}
			}

		}

		for (ISysUser sysUser : sysUsers) {
			uIdSet.add(TaskExecutor.getTaskUser(sysUser.getUserId().toString(),
					sysUser.getFullname()));
		}
		return uIdSet;
	}

	/**
	 * 获取指定人的部门的指定类型部门的负责人，一直获取下去直到最顶级为空或取得指定一类型的负责人。
	 * 
	 * @param userId
	 * @param level
	 * @param stategy
	 * @param orgId
	 * @return
	 */
	private List<ISysUser> getPrveTypeLeader(Long userId, String level,
			String stategy, Long orgId) {
		List<ISysUser> users = new ArrayList<ISysUser>();
		if (orgId.equals(0L))
			return users;
		ISysOrg sysOrg = sysOrgService.getById(orgId);
		SysOrgType sysOrgType = sysOrgTypeService
				.getById(Long.parseLong(level)); // 获取指定部门类型的类型记录
		users = getPrveleader(sysOrg, sysOrgType, stategy);
		return users;
	}

	/**
	 * 根据查找策略stategy=1只查指定类型部门，为stategy=0指定类型为空继续往上查询部门负责人
	 * 
	 * @param sysOrg
	 * @param sysOrgType
	 * @param stategy
	 * @return
	 */
	private List<ISysUser> getPrveleader(ISysOrg sysOrg, SysOrgType sysOrgType,
			String stategy) {
		List<ISysUser> users = new ArrayList<ISysUser>();
		ISysOrg parentOrg = sysOrgService.getParentWithTypeLevel(sysOrg,
				sysOrgType);
		if (parentOrg == null) {
			return users;
		}
		if ("1".equals(stategy)) { // 只查找指定类型一级的部门负责人
			SysOrgType currentSysOrgType = sysOrgTypeService.getById(parentOrg
					.getOrgType());
			if (sysOrgType.getId().equals(currentSysOrgType.getId())) {
				users = sysUserOrgService.getLeaderUserByOrgId(
						parentOrg.getOrgId(), false);
			}
		} else if ("0".equals(stategy)) {
			users = sysUserOrgService.getLeaderUserByOrgId(
					parentOrg.getOrgId(), false);
			if (BeanUtils.isEmpty(users)) {
				users = getPrveleader(parentOrg, sysOrgType, stategy);
			}
		}
		return users;
	}

	@Override
	public String getTitle() {
		return "部门的上级类型部门的负责人";
	}
}
