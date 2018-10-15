package com.hotent.platform.dao.bpm;

import org.springframework.stereotype.Repository;

import com.hotent.core.bpm.model.ProcessTaskHistory;
import com.hotent.core.db.GenericDao;

@Repository
public class TaskHistoryDao extends GenericDao<ProcessTaskHistory, String>
{
	@Override
	public Class getEntityClass()
	{
		return ProcessTaskHistory.class;
	}
	
	@Override
	public String getIbatisMapperNamespace()
	{
		return "com.hotent.core.bpm.model.ProcessTaskHistory";
	}
}
