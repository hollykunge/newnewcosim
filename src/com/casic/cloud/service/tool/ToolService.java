package com.casic.cloud.service.tool;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.cloud.dao.tool.ToolDao;
import com.casic.cloud.model.tool.Tool;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;

@Service
public class ToolService extends BaseService<Tool> {
	@Resource
	private ToolDao dao;

	@Override
	protected IEntityDao<Tool, Long> getEntityDao() {
		
		return dao;
	}

}
