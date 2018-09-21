package com.hotent.platform.service.bpm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.activiti.engine.RuntimeService;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.dao.bpm.BpmNodeSetDao;
import com.hotent.platform.dao.bpm.BpmNodeUserDao;
import com.hotent.platform.dao.bpm.BpmUserConditionDao;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.BpmNodeUser;
import com.hotent.platform.model.bpm.BpmUserCondition;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.IBpmNodeUserCalculation.CalcVars;
import com.hotent.platform.service.form.BpmFormDefService;

/**
 * 对象功能:BpmNOdeUser Service类 开发公司: 开发人员:cjj 创建时间:2011-12-05 17:20:40
 */
@Service
public class BpmNodeUserService extends BaseService<BpmNodeUser> {
	@Resource
	private BpmNodeUserDao dao;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private BpmNodeSetDao bpmNodeSetDao;
	@Resource
	private GroovyScriptEngine groovyScriptEngine;
	@Resource
	private BpmUserConditionDao bpmUserConditionDao;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private BpmFormDefService bpmFormDefService;
	@Resource
	private BpmNodeUserCalculationSelector bpmNodeUserCalculationSelector;

	public BpmNodeUserService() {

	}

	@Override
	protected IEntityDao<BpmNodeUser, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 根据流程定义id获得流程节点人员
	 * 
	 * @param defId
	 * @return
	 */
	public List<BpmNodeUser> getBySetId(Long setId) {
		return dao.getBySetId(setId);
	}

	/**
	 * 取得某个流程实例上某个流程节点上的执行人员
	 * 
	 * @param actInstId
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<TaskExecutor>> getExeUserIdsByInstance(
			String actInstId, String nodeId, String preTaskUser,
			Map<String, Object> vars) {

		String startUserId = null;
		String actDefId = "";
		ProcessRun processRun = processRunDao.getByActInstanceId(actInstId);
		if (processRun != null) {
			startUserId = processRun.getCreatorId().toString();
			actDefId = processRun.getActDefId();
		}
		return getExeUserIds(actDefId, actInstId, nodeId, startUserId,
				preTaskUser, vars);
	}

	/**
	 * 
	 * @param operatorList
	 * @param returnVal
	 * @return
	 */
	private Boolean executeOperator(List<Map<String, Object>> operatorList,
			Boolean returnVal) {
		for (Integer k = 0; k < operatorList.size(); k++) {
			Map<String, Object> resultMap = operatorList.get(k);
			String operator = (String) resultMap.get("operator");
			if ("0".equals(operator)) { // 与运算
				returnVal = returnVal && ((Boolean) resultMap.get("result"));
			} else if ("2".equals(operator)) { // 取反运算
				returnVal = returnVal && (!((Boolean) resultMap.get("result")));
			} else { // 或运算
				returnVal = returnVal || ((Boolean) resultMap.get("result"));
			}
		}
		return returnVal;
	}

	/**
	 * 
	 * @param currentCondition
	 * @param formId
	 * @return
	 */
	private Boolean conditionCheck(BpmUserCondition currentCondition,
			Long formId, Map<String, Object> vars) {
		Boolean isPassCondition = true;
		Map<String, Object> formVars = new HashMap<String, Object>();
		Long conditionFormId = currentCondition.getTableId();
		// 与现有表单比较，如不相同直接返回true
		if (conditionFormId != null && !conditionFormId.equals(0L)
				&& conditionFormId.equals(formId)) {
			JSONArray jsonArry = JSONArray.parseArray(currentCondition
					.getCondition());
			List<Map<String, Object>> operatorList = new ArrayList<Map<String, Object>>();
			for (Integer k = 0; k < jsonArry.size(); k++) {
				if (vars.get("executionId") != null && formVars.isEmpty()) {
					try {
						formVars = runtimeService.getVariables((String) vars
								.get("executionId"));
					} catch (Exception e) {
						throw new RuntimeException("第一个节点不存在表单变量，无法校验表单规则。");
					}
				}
				JSONObject Jsonobject = JSONObject.fromObject(jsonArry.get(k));
				// 执行脚本
				String valName = (String) Jsonobject.get("key"); // 变量名
				String varValue = (String) Jsonobject.get("conditionValue");// 条件值
				String compare = (String) Jsonobject.get("compare"); // 比较符号
				String operator = (String) Jsonobject.get("operator");// 运算类型，0表示与，1表示或
				String formVal = (String) formVars.get(valName);
				if (formVal == null) {
					formVal = "";
				}
				String script = "";
				if (compare.contains("()")) {
					script = valName + compare.replace("()", "") + "('"
							+ varValue + "')";
				} else {
					script = valName + compare + "'" + varValue + "'";
				}
				// 执行结果记录到operatorList中
				Map<String, Object> scriptvars = new HashMap<String, Object>();
				scriptvars.put(valName, formVal);
				Boolean result = groovyScriptEngine.executeBoolean(script,
						scriptvars);
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("operator", operator);
				resultMap.put("result", result);
				operatorList.add(resultMap);
			}
			// 根据表单规则判断当前表单是否符合规则
			isPassCondition = executeOperator(operatorList, isPassCondition);
			// if(!isPassCondition) continue;

			// 获取人员，如有人员存在直接返回，如果没有就继续循环查询下一个
		}
		return isPassCondition;
	}

	/**
	 * 计算某个流程定义上某个节点的执行用户
	 * 
	 * @param actDefId
	 * @param nodeId
	 * @param startUserId
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<TaskExecutor>> getExeUserIds(String actDefId,
			String nodeId, String startUserId, Map<String, Object> vars) {
		return getExeUserIds(actDefId, null, nodeId, startUserId, "", vars);
	}

	/**
	 * 计算发起人启动某个流程后，某个节点应该由哪些用户来执行。
	 * 
	 * <pre>
	 * 	返回用户数据包括流程参与者和通知人。
	 *  1.参与者键为PARTICIPATION。BpmNodeUser.USER_TYPE_PARTICIPATION
	 *  2.通知人键为NOTIFY。BpmNodeUser.USER_TYPE_NOTIFY
	 * </pre>
	 * 
	 * @param actDefId
	 * @param actInstId
	 * @param nodeId
	 * @param startUserId
	 * @return 执行的用户ID列表
	 * @throws Exception
	 */
	public Map<String, List<TaskExecutor>> getExeUserIds(String actDefId,
			String actInstId, String nodeId, String startUserId,
			String preTaskUser, Map<String, Object> vars) {

		log.debug("startUserId:" + startUserId + ",preTaskUser:" + preTaskUser);
		Map<String, List<TaskExecutor>> map = new HashMap<String, List<TaskExecutor>>();

		BpmNodeSet bpmNodeSet = bpmNodeSetDao.getByActDefIdNodeId(actDefId,
				nodeId);

		if (bpmNodeSet == null)
			return map;
		// add ht 2013-1-4
		Long formId = 0L;
		// 获取现有表单Id,若与旧表单规则对应表单Id不一致则表单规则作废
		formId = bpmFormDefService.getCurrentTableId(bpmNodeSet);
		// 所获得的bpmUserConditionList已经是按照sn排序
		List<BpmUserCondition> bpmUserConditionList = bpmUserConditionDao
				.getBySetId(bpmNodeSet.getSetId());
		if (!BeanUtils.isNotEmpty(bpmUserConditionList))
			return map;

		Boolean isExistUser = false; // 判断是否取到用户，true时存在不再往下循环
		for (Integer i = 0; i < bpmUserConditionList.size(); i++) {
			Boolean isPass = true;
			// 已经取到用户时不继续循环
			if (isExistUser)
				break;
			BpmUserCondition currentCondition = bpmUserConditionList.get(i);
			// 表单条件比较判断
			isPass = conditionCheck(currentCondition, formId, vars);
			if (!isPass)
				continue;
			Map<String, List<BpmNodeUser>> nodeUserMap = getMapBySetId(
					bpmNodeSet.getSetId(), currentCondition.getId());

			List<BpmNodeUser> partUser = nodeUserMap
					.get(BpmNodeUser.USER_TYPE_PARTICIPATION);

			List<BpmNodeUser> notifyUser = nodeUserMap
					.get(BpmNodeUser.USER_TYPE_NOTIFY);
			if (BeanUtils.isNotEmpty(partUser)) {
				List<TaskExecutor> partUsers = getUsers(partUser, actDefId,
						actInstId, nodeId, startUserId, preTaskUser);
				map.put(BpmNodeUser.USER_TYPE_PARTICIPATION, partUsers);
				if (partUsers != null) {
					isExistUser = true;// 不为空可以不再遍历
				}
			}
			if (BeanUtils.isNotEmpty(notifyUser)) {
				List<TaskExecutor> notifyUsers = getUsers(notifyUser, actDefId,
						actInstId, nodeId, startUserId, preTaskUser);
				map.put(BpmNodeUser.USER_TYPE_NOTIFY, notifyUsers);
				if (notifyUsers != null) {
					isExistUser = true;// 不为空可以不再遍历
				}
			}

		}
		// end
		return map;
	}

	/**
	 * 根据用户类型获取某个节点配置的用户。
	 * 
	 * @param userType
	 *            用户类型（PARTICIPATION，NOTIFY）
	 * @param actDefId
	 *            流程定义id。
	 * @param actInstId
	 *            流程实例id
	 * @param nodeId
	 *            任务节点ID。
	 * @param startUserId
	 *            流程发起人
	 * @param preTaskUser
	 *            上个任务节点执行人。
	 * @return
	 */
	public List<TaskExecutor> getUsers(String userType, String actDefId,
			String actInstId, String nodeId, String startUserId,
			String preTaskUser) {
		BpmNodeSet bpmNodeSet = bpmNodeSetDao.getByActDefIdNodeId(actDefId,
				nodeId);
		if (bpmNodeSet == null)
			return new ArrayList<TaskExecutor>();
		Map<String, List<BpmNodeUser>> nodeUserMap = getMapBySetId(bpmNodeSet
				.getSetId());
		// 获取一个节点定义的用户列表。
		List<BpmNodeUser> users = nodeUserMap.get(userType);
		if (BeanUtils.isEmpty(users))
			return new ArrayList<TaskExecutor>();
		List<TaskExecutor> userList = getUsers(users, actDefId, actInstId,
				nodeId, startUserId, preTaskUser);
		return userList;

	}

	/**
	 * 取得任务执行人。
	 * 
	 * @param bpmNodeUser
	 *            节点执行人。
	 * @param extractUser
	 *            是否抽取用户。
	 * @param startUserIdString
	 *            流程发起人。
	 * @param preTaskUser
	 *            上一个节点执行人。
	 * @param actInstId
	 *            流程实例ID。
	 * @return
	 */
	private Set<TaskExecutor> getByBpmNodeUser(BpmNodeUser bpmNodeUser,
			boolean extractUser, String startUserIdString, String preTaskUserIdString,
			String actInstId) {
		Long startUserIdLong = null;
		Long preTaskUserIdLong = null; 
		if (!StringUtil.isEmpty(startUserIdString)) {
			startUserIdLong = Long.parseLong(startUserIdString); 
		} 
		if (!StringUtil.isEmpty(preTaskUserIdString)) {
			preTaskUserIdLong = Long.parseLong(preTaskUserIdString);
		}

		CalcVars vars = new CalcVars(startUserIdLong, preTaskUserIdLong,
				actInstId, extractUser);
		IBpmNodeUserCalculation calculation = bpmNodeUserCalculationSelector
				.getByKey(bpmNodeUser.getAssignType());
		return calculation.getExecutor(bpmNodeUser, vars);
	}

	/**
	 * 计算获取用户。
	 * 
	 * @param nodeUsers
	 * @param actDefId
	 * @param actInstId
	 * @param nodeId
	 * @param startUserId
	 * @param preTaskUser
	 * @return
	 */
	private List<TaskExecutor> getUsers(List<BpmNodeUser> nodeUsers,
			String actDefId, String actInstId, String nodeId,
			String startUserId, String preTaskUser) {
		List<TaskExecutor> list = new ArrayList<TaskExecutor>();
		// 在一个节点上只配置了一类用户的情况，不对用户进行抽取。
		if (nodeUsers.size() == 1) {
			BpmNodeUser bpmNodeUser = nodeUsers.get(0);
			Set<TaskExecutor> uIdSet = getByBpmNodeUser(bpmNodeUser, false,
					startUserId, preTaskUser, actInstId);
			list.addAll(uIdSet);
			return list;
		} else {
			Set<TaskExecutor> userIdSet = new HashSet<TaskExecutor>();
			for (BpmNodeUser bpmNodeUser : nodeUsers) {
				Set<TaskExecutor> uIdSet = getByBpmNodeUser(bpmNodeUser, true,
						startUserId, preTaskUser, actInstId);
				if (userIdSet.size() == 0) {
					userIdSet = uIdSet;
				} else {
					userIdSet = computeUserSet(bpmNodeUser.getCompType(),
							userIdSet, uIdSet);
				}
			}
			list.addAll(userIdSet);
		}

		return list;
	}

	/**
	 * 
	 * @param setId
	 * @return
	 */
	private Map<String, List<BpmNodeUser>> getMapBySetId(Long setId) {
		Map<String, List<BpmNodeUser>> map = new HashMap<String, List<BpmNodeUser>>();
		List<BpmNodeUser> userList = dao.getBySetId(setId);
		for (BpmNodeUser bpmNodeUser : userList) {
			if (bpmNodeUser.getAssignUseType() == BpmNodeUser.ASSIGN_USE_TYPE_PARTICIPATION) {
				addMap(map, BpmNodeUser.USER_TYPE_PARTICIPATION, bpmNodeUser);
			} else {
				addMap(map, BpmNodeUser.USER_TYPE_NOTIFY, bpmNodeUser);
			}
		}
		return map;
	}

	/**
	 * 
	 * @param setId
	 * @param conditionId
	 * @return
	 */
	private Map<String, List<BpmNodeUser>> getMapBySetId(Long setId,
			Long conditionId) {
		Map<String, List<BpmNodeUser>> map = new HashMap<String, List<BpmNodeUser>>();
		List<BpmNodeUser> userList = dao.getBySetIdAndConditionId(setId,
				conditionId);
		for (BpmNodeUser bpmNodeUser : userList) {
			if (bpmNodeUser.getAssignUseType() == BpmNodeUser.ASSIGN_USE_TYPE_PARTICIPATION) {
				addMap(map, BpmNodeUser.USER_TYPE_PARTICIPATION, bpmNodeUser);
			} else {
				addMap(map, BpmNodeUser.USER_TYPE_NOTIFY, bpmNodeUser);
			}
		}
		return map;
	}

	private void addMap(Map<String, List<BpmNodeUser>> map, String type,
			BpmNodeUser bpmNodeUser) {
		if (map.containsKey(type)) {
			map.get(type).add(bpmNodeUser);
		} else {
			List<BpmNodeUser> list = new ArrayList<BpmNodeUser>();
			list.add(bpmNodeUser);
			map.put(type, list);
		}
	}

	/*---------------------取用户方法-----------------------*/

	/**
	 * 计算两个集合的交集或合集或排除
	 * 
	 * @param computeType
	 * @param userIdSet
	 *            原集合
	 * @param newUserSet
	 *            新集合
	 * @return
	 */
	private Set<TaskExecutor> computeUserSet(short computeType,
			Set<TaskExecutor> userIdSet, Set<TaskExecutor> newUserSet) {
		if (newUserSet == null)
			return userIdSet;
		if (BpmNodeUser.COMP_TYPE_AND == computeType) {// 交集
			Set<TaskExecutor> orLastSet = new HashSet<TaskExecutor>();
			Iterator<TaskExecutor> uIt = userIdSet.iterator();
			while (uIt.hasNext()) {
				TaskExecutor key = uIt.next();
				if (newUserSet.contains(key)) {
					orLastSet.add(key);
				}
			}
			return orLastSet;
		} else if (BpmNodeUser.COMP_TYPE_OR == computeType) {
			userIdSet.addAll(newUserSet);
		} else {// 排除
			for (TaskExecutor newUserId : newUserSet) {
				userIdSet.remove(newUserId);
			}
		}
		return userIdSet;
	}

	/**
	 * 根据流程定义setid和规则Id获得流程节点人员
	 * 
	 * @param defId
	 * @return
	 */
	public List<BpmNodeUser> getBySetIdAndConditionId(Long setId,
			Long conditionId) {
		return dao.getBySetIdAndConditionId(setId, conditionId);
	}

	/**
	 * 根据对应模板conditionId删除记录
	 * 
	 * @Methodname: delByTemplateIds
	 * @Discription:
	 * @param conditionId
	 * @Author HH
	 * @Time 2012-12-19 下午7:33:50
	 */
	public void delByConditionId(Long conditionId) {
		dao.delByConditionId(conditionId);
	}

	/**
	 * 修复数据
	 * 
	 * @return
	 */
	public List<BpmNodeUser> selectNull() {
		return dao.selectNull();
	}

	/**
	 * 根据流程节点的用户设置，取得相应的用户列表
	 * 
	 * @param bpmNodeUser
	 *            流程节点的用户设置
	 * @param startUserId
	 *            流程发起人
	 * @param preTaskUserId
	 *            前一个节点的执行人
	 * @param actInstId
	 *            流程实例ID
	 * @return 用户列表
	 */
	public Set<TaskExecutor> getNodeUser(BpmNodeUser bpmNodeUser,
			Long startUserId, Long preTaskUserId, String actInstId,
			Boolean extractUser) {
		CalcVars vars = new CalcVars(startUserId, preTaskUserId, actInstId,
				extractUser);
		IBpmNodeUserCalculation calculation = bpmNodeUserCalculationSelector
				.getByKey(bpmNodeUser.getAssignType());
		return calculation.getExecutor(bpmNodeUser, vars);
	}
}
