package com.hotent.platform.dao.bpm;

import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

@Repository
public class HistoryActivityDao extends BaseDao<HistoricActivityInstanceEntity> {

	@Override
	public Class getEntityClass() {
		return HistoricActivityInstanceEntity.class;
	}

	
	
}
