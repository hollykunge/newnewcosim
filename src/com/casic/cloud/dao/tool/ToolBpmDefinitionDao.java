package com.casic.cloud.dao.tool;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.casic.cloud.model.tool.ToolBpmDefinition;
import com.hotent.core.db.BaseDao;

@Repository
public class ToolBpmDefinitionDao extends BaseDao<ToolBpmDefinition> {

	@Override
	public Class<?> getEntityClass() {
		return ToolBpmDefinition.class;
	}

	public ToolBpmDefinition getToolBpmDefinition(Long toolId, Long defId) {
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("defId", defId);
		param.put("toolId", toolId);
		ToolBpmDefinition toolDef = (ToolBpmDefinition) getUnique("getToolBpmDefinition", param);
		return toolDef;
	}
}
