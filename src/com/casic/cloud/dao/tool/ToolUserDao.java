package com.casic.cloud.dao.tool;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.casic.cloud.model.tool.ToolUser;
import com.hotent.core.db.BaseDao;

@Repository
public class ToolUserDao extends BaseDao<ToolUser> {
	@Override
	public Class<?> getEntityClass() {
		return ToolUser.class;
	}

	public ToolUser getToolUser(Long toolId, Long userId) {
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("userId", userId);
		param.put("toolId", toolId);
		ToolUser toolUser = (ToolUser) getUnique("getToolUser", param);
		return toolUser;
	}
}
