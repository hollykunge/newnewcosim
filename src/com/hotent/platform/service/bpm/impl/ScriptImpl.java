package com.hotent.platform.service.bpm.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.engine.IScript;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.TimeUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.PositionDao;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.SysOrgTypeDao;
import com.hotent.platform.dao.system.SysRoleDao;
import com.hotent.platform.dao.system.SysUserParamDao;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.SysOrgType;
import com.hotent.platform.model.system.SysUserParam;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserUnderService;
import com.hotent.platform.webservice.api.ProcessService;


/**
 * 实现这个接口可以在groovy脚本中直接使用。
 * 
 * @author ray。
 * 
 */
public class ScriptImpl implements IScript {
	
	private static final Log logger = LogFactory.getLog(Dom4jUtil.class);
	@Resource
	TaskOpinionService taskOpinionService;
	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private SysUserOrgService sysUserOrgService;
	@Resource
	private SysOrgDao sysOrgDao;
	@Resource
	private PositionDao positionDao;
	@Resource
	private UserUnderService userUnderService;
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysOrgTypeDao sysOrgTypeDao;

	@Resource
	private SysUserParamDao sysUserParamDao;

	@Resource
	private ProcessService processService;
	
	@Resource
	private TaskService taskService;

	/**
	 * 取得当前登录用户id 。<br>
	 * 
	 * <pre>
	 * 脚本中使用方法:
	 * scriptImpl.getCurrentUserId()
	 * </pre>
	 * 
	 * @return
	 */
	public long getCurrentUserId() {
		return ContextUtil.getCurrentUserId();
	}

	/**
	 * 取得当前登录用户名称 。<br>
	 * 
	 * <pre>
	 * 脚本中使用方法:
	 * scriptImpl.getCurrentName()
	 * </pre>
	 * 
	 * @return
	 */
	public String getCurrentName() {
		return ContextUtil.getCurrentUser().getFullname();
	}

	/**
	 * 获取当前系统的用户。
	 * 
	 * <pre>
	 * 脚本中使用方法: scriptImpl.getCurrentUser();
	 * </pre>
	 * 
	 * @return 用户对象。
	 */
	public ISysUser getCurrentUser() {
		return ContextUtil.getCurrentUser();
	}

	/**
	 * 获取当前日期。
	 * 
	 * <pre>
	 * 脚本中使用方法:
	 * scriptImpl.getCurrentDate();
	 * 返回日期类型如下：
	 * 2002-11-06
	 * </pre>
	 * 
	 * @return
	 */
	public String getCurrentDate() {
		return TimeUtil.getCurrentDate();
	}

	/**
	 * 获取当前组织。
	 * 
	 * @return
	 */
	public ISysOrg getCurrentOrg() {
		ISysOrg sysOrg = ContextUtil.getCurrentOrg();
		return sysOrg;
	}

	/**
	 * 获取当前用户组织的ID
	 * 
	 * @return
	 */
	public Long getCurrentOrgId() throws Exception {
		ISysOrg sysOrg = ContextUtil.getCurrentOrg();
		if (sysOrg == null)
			return null;
		return sysOrg.getOrgId();
	}

	/**
	 * 取得当前用户主组织的名称。
	 * 
	 * @return
	 */
	public String getCurrentOrgName() {
		ISysOrg sysOrg = ContextUtil.getCurrentOrg();
		if (sysOrg == null)
			return null;
		return sysOrg.getOrgName();
	}

	/**
	 * 取得当前用户主组织名称
	 * 
	 * @return
	 */
	public String getCurrentPrimaryOrgName() {
		Long userId = ContextUtil.getCurrentUserId();
		ISysOrg sysOrg = sysOrgDao.getPrimaryOrgByUserId(userId);
		if (sysOrg == null)
			return null;
		return sysOrg.getOrgName();
	}

	/**
	 * 取得某用户主组织的Id。
	 * 
	 * @return
	 */
	public Long getUserOrgId(Long userId) {
		ISysOrg sysOrg;
		sysOrg = sysOrgDao.getPrimaryOrgByUserId(userId);
		if (sysOrg == null)
			return null;
		return sysOrg.getOrgId();
	}

	/**
	 * 取得某用户主组织的名称。
	 * 
	 * @return
	 */
	public String getUserOrgName(Long userId) {
		ISysOrg sysOrg;
		sysOrg = sysOrgDao.getPrimaryOrgByUserId(userId);
		if (sysOrg == null)
			return null;
		return sysOrg.getOrgName();
	}

	/**
	 * 返回当前组织的类型。
	 * 
	 * @return
	 */
	public SysOrgType getCurrentOrgType() {
		ISysOrg sysOrg = ContextUtil.getCurrentOrg();
		if (sysOrg == null)
			return null;
		Long orgType = sysOrg.getOrgType();
		if (orgType == null)
			return null;
		SysOrgType sysOrgType = sysOrgTypeDao.getById(orgType);
		return sysOrgType;
	}

	/**
	 * 返回当前组织类型的名称。
	 * 
	 * @return
	 */
	public String getCurrentOrgTypeName() {
		ISysOrg sysOrg = ContextUtil.getCurrentOrg();
		if (sysOrg == null)
			return "";
		Long orgType = sysOrg.getOrgType();
		if (orgType == null)
			return "";
		SysOrgType sysOrgType = sysOrgTypeDao.getById(orgType);
		return sysOrgType.getName();
	}

	/**
	 * 判断当前用户是否属于该角色。
	 * 
	 * <pre>
	 * 在脚本中使用方法:
	 * scriptImpl.hasRole(alias)
	 * </pre>
	 * 
	 * @param alias
	 *            角色别名
	 * @return
	 */
	public boolean hasRole(String alias) {
		long userId = ContextUtil.getCurrentUserId();
		List<ISysRole> roleList = sysRoleDao.getByUserId(userId);
		for (ISysRole role : roleList) {
			if (role.getAlias().equals(alias)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前用户所属角色。
	 * 
	 * <pre>
	 * 在脚本中使用方法: scriptImpl.getCurrentUserRoles();
	 * </pre>
	 * 
	 * @return 返回角色列表。
	 */
	public List<ISysRole> getCurrentUserRoles() {
		long userId = ContextUtil.getCurrentUserId();
		List<ISysRole> list = sysRoleDao.getByUserId(userId);
		return list;
	}

	/**
	 * 获取某用户所属角色。
	 * 
	 * <pre>
	 * 在脚本中使用方法: scriptImpl.getUserRoles(strUserId);
	 * </pre>
	 * 
	 * @return 返回角色列表。
	 * @param strUserId
	 *            用户ID
	 * @return
	 * 获取发起人用户所属角色则可用scriptImpl.getUserRoles(String.valueOf(startUser))
	 */
	public List<ISysRole> getUserRoles(String strUserId) {
		Long userId = Long.parseLong(strUserId);
		List<ISysRole> list = sysRoleDao.getByUserId(userId);
		return list;
	}

	/**
	 * 判断某用户是否属于某角色。
	 * 
	 * @param userId
	 *            用户id。
	 * @param role
	 *            角色别名
	 * 在脚本中使用方法: scriptImpl.isUserInRole(userId,role);
	 * @return
	 */
	public boolean isUserInRole(String userId, String role) {
		Long lUserId = Long.parseLong(userId);
		List<ISysRole> list = sysRoleDao.getByUserId(lUserId);
		if (BeanUtils.isEmpty(list))
			return false;
		for (ISysRole sysRole : list) {
			if (sysRole.getAlias().equals(role)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断某用户是否属于某组织。
	 * 
	 * @param userId
	 *            用户id。
	 * @param org
	 *            组织名 
	 * 在脚本中使用方法: scriptImpl.isUserInOrg(String userId, String org);
	 * @return
	 */
	public boolean isUserInOrg(String userId, String org) {
		Long lUserId = Long.parseLong(userId);
		List<ISysOrg> list = sysOrgDao.getOrgsByUserId(lUserId);
		if (BeanUtils.isEmpty(list))
			return false;
		for (ISysOrg sysOrg : list) {
			if (sysOrg.getOrgName().equals(org)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断当前用户是否属于某组织。
	 * 
	 * @param userId
	 *            用户id。
	 * @param org
	 *            组织名 
	 *            在脚本中使用方法: scriptImpl.isCurUserInOrg(String org);
	 * @return
	 */
	public boolean isCurUserInOrg(String org) {
		Long userId = ContextUtil.getCurrentUserId();
		return isUserInOrg(userId.toString(), org);
	}

	/**
	 * 获取用户的主岗位名称
	 * 
	 * <pre>
	 * 脚本中使用方法:
	 * scriptImpl.getUserPos(Long userId);
	 * </pre>
	 * 
	 * @param userId
	 *            用户ID
	 * @return 主岗位名称
	 */
	public String getUserPos(Long userId) {
		String posName = "";
		Position position = positionDao.getPosByUserId(userId);
		if (!BeanUtils.isEmpty(position)) {
			posName = position.getPosName();
		}
		return posName;
	}

	/**
	 * 根据当前用户取得指定参数key的参数值。
	 * 脚本中使用方法:
	 * scriptImpl.getParaValue(String paramKey);
	 * @param paraName
	 *            参数名称
	 * @return
	 */
	public Object getParaValue(String paramKey) {
		Long currentUserId = ContextUtil.getCurrentUserId();

		return getParaValueByUser(currentUserId, paramKey);
	}

	/**
	 * 根据用户ID和参数key获取参数值。
	 * 脚本中使用方法:
	 * scriptImpl.getParaValueByUser(Long userId, String paramKey);
	 * @param userId
	 *            用户ID
	 * @param paramKey
	 *            参数键
	 * @return
	 */
	public Object getParaValueByUser(Long userId, String paramKey) {
		SysUserParam sysUserParam = sysUserParamDao.getByParaUserId(userId,
				paramKey);
		if (sysUserParam == null) {
			return null;
		}
		String dataType = sysUserParam.getDataType();
		if ("String".equals(dataType)) {
			return sysUserParam.getParamValue();
		} else if ("Integer".equals(dataType)) {
			return sysUserParam.getParamIntValue();
		} else {
			return sysUserParam.getParamDateValue();
		}
	}

	/**
	 * 获取当前用户的主岗位名称。
	 * 
	 * <pre>
	 * 脚本中使用方法: scriptImpl.getCurUserPos();
	 * </pre>
	 * 
	 * @return
	 */
	public String getCurUserPos() {
		long userId = ContextUtil.getCurrentUserId();
		String posName = getUserPos(userId);
		return posName;
	}

	/**
	 * 获取流程当前用户直属领导的主岗位名称。
	 * 
	 * <pre>
	 * 脚本中使用方法:
	 * scriptImpl.getCurDirectLeaderPos();
	 * </pre>
	 * 
	 * @param userId
	 *            用户ID
	 * @return 主岗位名称
	 */
	public String getCurDirectLeaderPos() {
		String userId = ContextUtil.getCurrentUserId().toString();
		String posName = getDirectLeaderPosByUserId(userId);
		return posName;
	}

	/**
	 * 获取用户的组织的直属领导岗位。
	 * 
	 * <pre>
	 * 1.当前人是普通员工，则获取部门负责人，如果找不到，往上级查询负责人岗位。
	 * 2.当前人员是部门负责人，则获取上级部门负责人，如果找不到则往上级查询负责人岗位。
	 * <br>
	 * 脚本使用方法：
	 * scriptImpl.getDirectLeaderPosByUserId(String userId);
	 * </pre>
	 * 
	 * @param userId
	 * @return 如果有负责人则返回，没有返回null。
	 */
	public String getDirectLeaderPosByUserId(String userId) {
		String posName = "";
		posName = sysUserOrgService
				.getLeaderPosByUserId(Long.parseLong(userId));
		return posName;
	}

	/**
	 * 获取用户的组织的直属领导。
	 * 
	 * <pre>
	 * 1.当前人是普通员工，则获取部门负责人，如果找不到，往上级查询负责人。
	 * 2.当前人员是部门负责人，则获取上级部门负责人，如果找不到则往上级查询负责人。
	 * <br>
	 * 脚本使用方法：
	 * scriptImpl.getDirectLeaderByUserId(String userId);
	 * </pre>
	 * 
	 * @param userId
	 * @return 如果有负责人则返回，没有返回null。
	 */
	/*返回TaskExecutor 会报错，先改成String,根据脚本计算执行人员会用到，BpmNodeUserCalculationScript.java
	 * public Set<TaskExecutor> getDirectLeaderByUserId(String userId) {
		Set<TaskExecutor> userSet = new HashSet<TaskExecutor>();
		List<TaskExecutor> userList = sysUserOrgService.getLeaderByUserId(Long
				.parseLong(userId));
		// 获取直属上司为空。

		if (BeanUtils.isEmpty(userList))
			return userSet;
		for (TaskExecutor user : userList) {
			userSet.add(user);
		}

		return userSet;
	}
	*/
	public Set<String> getDirectLeaderByUserId(String userId) {
		Set<String> userSet = new HashSet<String>();
		List<TaskExecutor> userList = sysUserOrgService.getLeaderByUserId(Long
				.parseLong(userId));
		// 获取直属上司为空。

		if (BeanUtils.isEmpty(userList))
			return userSet;
		for (TaskExecutor user : userList) {
			userSet.add(user.getExecuteId());
		}

		return userSet;
	}
	/**
	 * 判断用户是否该部门的负责人
	 * 脚本使用方法：
	 * scriptImpl.isDepartmentLeader(String userId, String orgId) ;
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            部门ID
	 * @return
	 */
	public boolean isDepartmentLeader(String userId, String orgId) {
		// 根据部门ID获取部门负责人
		List<TaskExecutor> leaders = sysUserOrgService.getLeaderByOrgId(Long
				.parseLong(orgId));
		for (TaskExecutor leader : leaders) {
			if (userId.equals(leader.getExecuteId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取用户领导id集合。
	 * 脚本使用方法：
	 * scriptImpl.getMyLeader(Long userId);
	 * @param userId
	 * @return
	 */
	public Set<String> getMyLeader(Long userId) {
		Set<TaskExecutor> set = userUnderService.getMyLeader(userId);
		Set<String> userSet = new HashSet<String>();
		for (Iterator<TaskExecutor> it = set.iterator(); it.hasNext();) {
			userSet.add(it.next().getExecuteId());
		}
		return userSet;
	}

	/**
	 * 获取用户下属用户ID集合
	 * 脚本使用方法：
	 * scriptImpl.getMyUnderUserId(Long userId);
	 * @param userId
	 * @return
	 */
	public Set<String> getMyUnderUserId(Long userId) {
		return userUnderService.getMyUnderUserId(userId);
	}

	/**
	 * 通过用户账号设置任务执行人
	 * 脚本使用方法：
	 * scriptImpl.setAssigneeByAccount(TaskEntity task, String userAccout);
	 * @param task
	 * @param userAccout
	 */
	public void setAssigneeByAccount(TaskEntity task, String userAccout) {
		String[] aryAccount = userAccout.split(",");
		List<String> userIds = new ArrayList<String>();
		for (String str : aryAccount) {
			ISysUser sysUser = sysUserService.getByAccount(str);
			userIds.add(sysUser.getUserId().toString());
		}
		if (userIds.size() == 0)
			return;
		// 传进来一个用户则直接设置为执行人
		if (userIds.size() == 1) {
			task.setAssignee(userIds.get(0));
		}
		// 传进来多个用户则添加到候选人
		else {
			task.addCandidateUsers(userIds);
		}

	}

	/**
	 * 启动某个流程。
	 * 脚本使用方法：
	 * scriptImpl.startFlow(String flowKey, String businnessKey,Map<String, Object> vars);
	 * @param flowKey
	 *            流程定义key。
	 * @param businnessKey
	 *            业务主键
	 * @param vars
	 *            流程变量。
	 * @return
	 * @throws Exception
	 */
	public ProcessRun startFlow(String flowKey, String businnessKey,
			Map<String, Object> vars) throws Exception {
		ProcessCmd processCmd = new ProcessCmd();
		processCmd.setFlowKey(flowKey);
		processCmd.setBusinessKey(businnessKey);
		if (BeanUtils.isNotEmpty(vars)) {
			processCmd.setVariables(vars);
		}
		return processService.start(processCmd);
	}
	
	/**
	 * 判断是存在流程变量
	 * @param taskId
	 * @param varname
	 * @return
	 */
	public boolean isVariableExist(String taskId,String varname){
		Map map = taskService.getVariables(taskId);
		return map.containsKey(varname);
	}

}
