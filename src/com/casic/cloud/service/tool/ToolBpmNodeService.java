package com.casic.cloud.service.tool;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.cloud.dao.tool.ToolBpmNodeDao;
import com.casic.cloud.model.tool.ToolBpmNode;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;

@Service
public class ToolBpmNodeService extends BaseService<ToolBpmNode> {
	@Resource
	private ToolBpmNodeDao dao;

	@Override
	protected IEntityDao<ToolBpmNode, Long> getEntityDao() {
		return dao;
	}

	public void add(Long setId, Long toolIds[]) throws Exception {
		
		if (setId == null || setId.longValue() == 0L || toolIds == null || toolIds.length == 0) {
			return;
		}
		for (int i = 0; i < toolIds.length; i++) {
			Long toolId = toolIds[i];
			ToolBpmNode toolNode = dao.getToolBpmNode(toolId, setId);
			if (toolNode == null) {
				long toolDefId = UniqueIdUtil.genId();
				toolNode = new ToolBpmNode();
				toolNode.setToolNodeId(toolDefId);
				toolNode.setToolUserId(toolId);
				toolNode.setSetId(setId);
				dao.add(toolNode);
			}
		}

	}

	public ToolBpmNode getToolBpmNode(Long toolId, Long defId) {
		return dao.getToolBpmNode(toolId, defId);
	}

	public List<ToolBpmNode> getToolBpmNodeByDefId(Long defId) {
		return dao.getToolBpmNodesByDefId(defId);
	}

}
