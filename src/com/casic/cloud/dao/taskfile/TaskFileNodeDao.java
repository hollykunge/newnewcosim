package com.casic.cloud.dao.taskfile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.casic.cloud.model.taskfile.TaskFileNode;
import com.hotent.core.db.BaseDao;

/**
 * @author FangYun
 * 
 */
@Repository
public class TaskFileNodeDao extends BaseDao<TaskFileNode> {

	@Override
	public Class<?> getEntityClass() {
		return TaskFileNode.class;
	}

	public TaskFileNode getTaskFileNode(Long taskfileId, Long setId) {
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("setId", setId);
		param.put("taskfileId", taskfileId);
		TaskFileNode tfNode = (TaskFileNode) getUnique("getTaskFileNode", param);
		return tfNode;
	}

	public List<TaskFileNode> getTaskFileNodesByDefId(Long defId) {
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("defId", defId);
		List<TaskFileNode> fileNodes = super.getList("getTaskFileNodesByDefId", param);
		return fileNodes;
	}

	public List<TaskFileNode> getTaskFileNodesByTaskfileId(Long taskfileId) {
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("taskfileId", taskfileId);
		List<TaskFileNode> fileNodes = super.getList("getTaskFileNodesByTaskfileId", param);
		return fileNodes;
	}
}
