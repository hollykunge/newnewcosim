package com.casic.cloud.dao.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.casic.cloud.model.tool.ToolBpmNode;
import com.hotent.core.db.BaseDao;

@Repository
public class ToolBpmNodeDao extends BaseDao<ToolBpmNode> {

	@Override
	public Class<?> getEntityClass() {
		return ToolBpmNode.class;
	}

	public ToolBpmNode getToolBpmNode(Long toolId, Long setId) {
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("setId", setId);
		param.put("toolId", toolId);
		ToolBpmNode toolDef = (ToolBpmNode) getUnique("getToolBpmNode", param);
		return toolDef;
	}

	public List<ToolBpmNode> getToolBpmNodesByDefId(Long defId) {
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("defId", defId);
		List<ToolBpmNode> toolNodes = super.getList("getToolBpmNodesByDefId", param);
		return toolNodes;
	}
}
