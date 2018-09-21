package com.casic.cloud.service.tool;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.cloud.dao.tool.ToolBpmDefinitionDao;
import com.casic.cloud.model.tool.ToolBpmDefinition;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;

@Service
public class ToolBpmDefinitionService extends BaseService<ToolBpmDefinition> {
	@Resource
	private ToolBpmDefinitionDao dao;

	@Override
	protected IEntityDao<ToolBpmDefinition, Long> getEntityDao() {
		return dao;
	}

	public void add(Long defId, Long toolIds[]) throws Exception {
		if (defId == null || defId.longValue() == 0L || toolIds == null || toolIds.length == 0) {
			return;
		}
		for (int i = 0; i < toolIds.length; i++) {
			Long toolId = toolIds[i];
			ToolBpmDefinition toolDef = dao.getToolBpmDefinition(toolId, defId);
			if (toolDef == null) {
				long toolDefId = UniqueIdUtil.genId();
				toolDef = new ToolBpmDefinition();
				toolDef.setToolDefId(toolDefId);
				toolDef.setToolId(toolId);
				toolDef.setDefId(defId);
				dao.add(toolDef);
			}
		}

	}

	public ToolBpmDefinition getToolBpmDefinition(Long toolId, Long defId) {
		return dao.getToolBpmDefinition(toolId, defId);
	}

}
