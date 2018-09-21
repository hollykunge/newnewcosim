package com.casic.cloud.dao.tool;

import org.springframework.stereotype.Repository;

import com.casic.cloud.model.tool.Tool;
import com.hotent.core.db.BaseDao;

@Repository
public class ToolDao extends BaseDao<Tool> {

	@Override
	public Class<?> getEntityClass() {
		return Tool.class;
	}

}
