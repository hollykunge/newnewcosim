package com.hotent.platform.service.bpm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeButton;
import com.hotent.platform.dao.bpm.BpmDefinitionDao;
import com.hotent.platform.dao.bpm.BpmNodeButtonDao;

/**
 * 对象功能:自定义工具条 Service类 开发公司: 开发人员:ray 创建时间:2012-07-25 18:26:12
 */
@Service
public class BpmNodeButtonService extends BaseService<BpmNodeButton> {
	@Resource
	private BpmNodeButtonDao dao;

	@Resource
	private BpmDefinitionDao bpmDefinitionDao;

	@Resource
	private BpmService bpmService;

	public BpmNodeButtonService() {
	}

	@Override
	protected IEntityDao<BpmNodeButton, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 根据流程定义ID和节点ID获取操作按钮。
	 * 
	 * @param actDefId
	 * @param nodeId
	 * @return
	 */
	public List<BpmNodeButton> getByDefNodeId(Long defId, String nodeId) {
		return dao.getByDefNodeId(defId, nodeId);
	}

	/**
	 * 根据流程定义ID获取起始表单的操作按钮数据。
	 * 
	 * @param actDefId
	 * @return
	 */
	public List<BpmNodeButton> getByStartForm(Long defId) {
		List<BpmNodeButton> list = dao.getByStartForm(defId);
		return list;
	}

	/**
	 * 获取流程定义的按钮。
	 * 
	 * @param defId
	 * @return
	 */
	public Map<String, List<BpmNodeButton>> getMapByStartForm(Long defId) {
		Map<String, List<BpmNodeButton>> map = new HashMap<String, List<BpmNodeButton>>();
		List<BpmNodeButton> list = dao.getByStartForm(defId);
		if (BeanUtils.isEmpty(list)) {
			return map;
		}
		for (BpmNodeButton bpmNodeButton : list) {
			if (bpmNodeButton.getOperatortype() == 4
					|| bpmNodeButton.getOperatortype() == 5) {
				addItem(map, bpmNodeButton, "inform");
			} else {
				addItem(map, bpmNodeButton, "button");
			}
		}
		return map;
	}

	/**
	 * 根据流程定义ID和流程节点ID获取按钮。
	 * 
	 * @param defId
	 *            流程定义id。
	 * @param nodeId
	 *            流程节点id。
	 * @return
	 */
	public Map<String, List<BpmNodeButton>> getMapByDefNodeId(Long defId,
			String nodeId) {
		Map<String, List<BpmNodeButton>> map = new HashMap<String, List<BpmNodeButton>>();
		List<BpmNodeButton> list = dao.getByDefNodeId(defId, nodeId);
		if (BeanUtils.isEmpty(list)) {
			return map;
		}
		for (BpmNodeButton bpmNodeButton : list) {
			if (bpmNodeButton.getOperatortype() == 12
					|| bpmNodeButton.getOperatortype() == 13) {
				addItem(map, bpmNodeButton, "inform");
			} else {
				addItem(map, bpmNodeButton, "button");
			}
		}
		return map;
	}

	/**
	 * 根据流程定义ID获取获取节点和操作按钮的映射。
	 * 
	 * @param actDefId
	 * @return
	 */
	public Map<String, List<BpmNodeButton>> getMapByDefId(Long defId) {
		List<BpmNodeButton> list = dao.getByDefId(defId);
		Map<String, List<BpmNodeButton>> map = new HashMap<String, List<BpmNodeButton>>();
		for (BpmNodeButton bpmNodeButton : list) {
			if (bpmNodeButton.getIsstartform() == 1) {
				addItem(map, bpmNodeButton, "start");
			} else {
				addItem(map, bpmNodeButton, bpmNodeButton.getNodeid());
			}
		}
		return map;
	}

	/**
	 * 添加操作按钮到
	 * 
	 * @param map
	 * @param bpmNodeButton
	 * @param key
	 */
	private void addItem(Map<String, List<BpmNodeButton>> map,
			BpmNodeButton bpmNodeButton, String key) {
		if (map.containsKey(key)) {
			map.get(key).add(bpmNodeButton);
		} else {
			List<BpmNodeButton> list = new ArrayList<BpmNodeButton>();
			list.add(bpmNodeButton);
			map.put(key, list);
		}
	}

	/**
	 * 根据流程定义和节点操作类型判断是否已经存在。
	 * 
	 * @param bpmNodeButton
	 *            按钮操作。
	 * @return
	 */
	public boolean isOperatorExist(BpmNodeButton bpmNodeButton) {
		Long defId = bpmNodeButton.getDefId();
		Integer operatortype = bpmNodeButton.getOperatortype();
		if (bpmNodeButton.getIsstartform() == 1) {
			return dao.isStartFormExist(defId, operatortype) > 0;
		}
		String nodeId = bpmNodeButton.getNodeid();
		return dao.isExistByNodeId(defId, nodeId, operatortype) > 0;
	}

	/**
	 * 更新时根据流程定义和节点操作类型判断是否已经存在。
	 * 
	 * @param bpmNodeButton
	 * @return
	 */
	public boolean isOperatorExistForUpd(BpmNodeButton bpmNodeButton) {
		Long defId = bpmNodeButton.getDefId();
		Long id = bpmNodeButton.getId();
		Integer operatortype = bpmNodeButton.getOperatortype();
		if (bpmNodeButton.getIsstartform() == 1) {
			return dao.isStartFormExistForUpd(defId, operatortype, id) > 0;
		}
		String nodeId = bpmNodeButton.getNodeid();
		return dao.isExistByNodeIdForUpd(defId, nodeId, operatortype, id) > 0;
	}

	/**
	 * 更新排序字段。
	 * 
	 * @param ids
	 */
	public void sort(String ids) {
		String[] aryId = ids.split(",");
		for (int i = 0; i < aryId.length; i++) {
			Long id = Long.parseLong(aryId[i]);
			dao.updSn(id, (long) (i + 1));
		}
	}

	private void initStartForm(String actDefId, Long defId) throws Exception {
		dao.add(new BpmNodeButton(actDefId, defId, "启动流程", 1));
		dao.add(new BpmNodeButton(actDefId, defId, "保存表单", 6));
		dao.add(new BpmNodeButton(actDefId, defId, "流程示意图", 2));
		dao.add(new BpmNodeButton(actDefId, defId, "打印", 3));
		dao.add(new BpmNodeButton(actDefId, defId, "短信", 4));
		dao.add(new BpmNodeButton(actDefId, defId, "邮件", 5));

	}

	private void initNodeId(String actDefId, Long defId, String nodeId,
			boolean isSignTask) throws Exception {
		int nodetype = (isSignTask) ? 1 : 0;
		dao.add(new BpmNodeButton(actDefId, defId, nodeId, "同意", 1, nodetype));
		dao.add(new BpmNodeButton(actDefId, defId, nodeId, "反对", 2, nodetype));
		if (isSignTask) {
			dao.add(new BpmNodeButton(actDefId, defId, nodeId, "弃权", 3,
					nodetype));
			dao.add(new BpmNodeButton(actDefId, defId, nodeId, "补签", 7,
					nodetype));
		}

		dao.add(new BpmNodeButton(actDefId, defId, nodeId, "驳回", 4, nodetype));
		dao.add(new BpmNodeButton(actDefId, defId, nodeId, "驳回到发起人", 5,
				nodetype));
		dao.add(new BpmNodeButton(actDefId, defId, nodeId, "转交代办", 6, nodetype));
		dao.add(new BpmNodeButton(actDefId, defId, nodeId, "保存表单", 8, nodetype));
		dao.add(new BpmNodeButton(actDefId, defId, nodeId, "流程示意图", 9, nodetype));
		dao.add(new BpmNodeButton(actDefId, defId, nodeId, "打印", 10, nodetype));
		dao.add(new BpmNodeButton(actDefId, defId, nodeId, "审批历史", 11, nodetype));
		dao.add(new BpmNodeButton(actDefId, defId, nodeId, "短信", 12, nodetype));
		dao.add(new BpmNodeButton(actDefId, defId, nodeId, "邮件", 13, nodetype));
	}

	/**
	 * 初始化按钮。
	 * 
	 * @param defId
	 * @param nodeId
	 * @throws Exception
	 */
	public void init(Long defId, String nodeId) throws Exception {
		BpmDefinition bpmDefinition = bpmDefinitionDao.getById(defId);
		String actDefId = bpmDefinition.getActDefId();
		int isStartForm = StringUtil.isEmpty(nodeId) ? 1 : 0;
		if (isStartForm == 1) {
			dao.delByStartForm(defId);
			initStartForm(actDefId, defId);

		} else {
			dao.delByNodeId(defId, nodeId);
			boolean isSignTask = bpmService.isSignTask(actDefId, nodeId);
			initNodeId(actDefId, defId, nodeId, isSignTask);
		}
	}

	/**
	 * 初始化流程的全部按钮。
	 * 
	 * @param defId
	 *            流程定义ID
	 * @throws Exception
	 */
	public void initAll(Long defId) throws Exception {
		dao.delByDefId(defId);
		BpmDefinition bpmDefinition = bpmDefinitionDao.getById(defId);
		String actDefId = bpmDefinition.getActDefId();
		// 起始表单按钮。
		initStartForm(actDefId, defId);

		Map<String, Integer> taskMap = bpmService.getTaskType(actDefId);
		Set<Map.Entry<String, Integer>> set = taskMap.entrySet();
		for (Iterator<Map.Entry<String, Integer>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it
					.next();
			boolean isSignTask = entry.getValue() == 1;
			// 节点按钮。
			initNodeId(actDefId, defId, entry.getKey(), isSignTask);
		}
	}

	/**
	 * 根据流程定义ID删除按钮。
	 * 
	 * @param defId
	 * @throws Exception
	 */
	public void delByDefId(Long defId) throws Exception {
		dao.delByDefId(defId);
	}

	/**
	 * 删除节点或开始表单的节点按钮定义。
	 * 
	 * @param defId
	 * @param nodeId
	 */
	public void delByDefNodeId(Long defId, String nodeId) {
		if (StringUtil.isEmpty(nodeId)) {
			dao.delByStartForm(defId);
		} else {
			dao.delByNodeId(defId, nodeId);
		}
	}

	/**
	 * 根据流程定义ID获得操作按钮。
	 * 
	 * @param defId
	 * @return
	 */
	public List<BpmNodeButton> getByDefId(Long defId) {
		return dao.getByDefId(defId);
	}

}
