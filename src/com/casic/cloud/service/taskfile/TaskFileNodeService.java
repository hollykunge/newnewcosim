package com.casic.cloud.service.taskfile;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.cloud.dao.taskfile.TaskFileNodeDao;
import com.casic.cloud.model.taskfile.TaskFileNode;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;

@Service
public class TaskFileNodeService extends BaseService<TaskFileNode> {
	@Resource
	private TaskFileNodeDao dao;

	@Override
	protected IEntityDao<TaskFileNode, Long> getEntityDao() {
		return dao;
	}

	public void add(Long taskfileId, Long[] setIds) throws Exception {
		if (taskfileId == null || taskfileId.longValue() == 0L || setIds == null || setIds.length == 0) {
			return;
		}
		for (int i = 0; i < setIds.length; i++) {
			Long setId = setIds[i];
			TaskFileNode tfNode = dao.getTaskFileNode(taskfileId, setId);
			if (tfNode == null) {
				long id = UniqueIdUtil.genId();
				tfNode = new TaskFileNode();
				tfNode.setTaskfileNodeId(id);
				tfNode.setSetId(setId);
				tfNode.setTaskfileId(taskfileId);
				dao.add(tfNode);
			}
		}

	}

	public TaskFileNode getToolBpmNode(Long taskfileId, Long setId) {
		return dao.getTaskFileNode(taskfileId, setId);
	}

	public List<TaskFileNode> getTaskFileNodesByDefId(Long defId) {
		return dao.getTaskFileNodesByDefId(defId);
	}

	public List<TaskFileNode> getTaskFileNodesByTaskfileId(Long taskfileId) {
		return dao.getTaskFileNodesByTaskfileId(taskfileId);
	}

	public void updateRights(Long taskfileId, Long[] setIds) {
		List<TaskFileNode> fileNodes = getTaskFileNodesByTaskfileId(taskfileId);
		List<Long> ids = Lists.transform(fileNodes, new Function<TaskFileNode, Long>() {
			@Override
			public Long apply(TaskFileNode node) {
				return node.getTaskfileNodeId();
			}
		});
		delByIds(ids.toArray(new Long[0]));
		for (Long setId : setIds) {
			TaskFileNode tfn = new TaskFileNode();
			tfn.setTaskfileNodeId(UniqueIdUtil.genId());
			tfn.setSetId(setId);
			tfn.setTaskfileId(taskfileId);
			add(tfn);
		}
	}
}
