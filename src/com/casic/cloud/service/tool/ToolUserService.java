package com.casic.cloud.service.tool;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.cloud.dao.tool.ToolUserDao;
import com.casic.cloud.model.tool.ToolUser;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;

@Service
public class ToolUserService extends BaseService<ToolUser> {
	@Resource
	private ToolUserDao dao;

	@Override
	protected IEntityDao<ToolUser, Long> getEntityDao() {
		return dao;
	}

	public void add(Long userId, Long toolIds[]) throws Exception {
		if (userId == null || userId.longValue() == 0L || toolIds == null || toolIds.length == 0) {
			return;
		}
		for (int i = 0; i < toolIds.length; i++) {
			Long toolId = toolIds[i];
			ToolUser toolUser = dao.getToolUser(toolId, userId);
			if (toolUser == null) {
				long toolUserId = UniqueIdUtil.genId();
				toolUser = new ToolUser();
				toolUser.setToolUserId(toolUserId);
				toolUser.setToolId(toolId);
				toolUser.setUserId(userId);
				dao.add(toolUser);
			}
		}

	}

	public ToolUser getToolUser(Long toolId, Long userId) {
		return dao.getToolUser(toolId, userId);
	}

}
