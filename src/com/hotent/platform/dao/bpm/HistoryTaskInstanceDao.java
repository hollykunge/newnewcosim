package com.hotent.platform.dao.bpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

@Repository
public class HistoryTaskInstanceDao extends  BaseDao<HistoricTaskInstanceEntity>{

	@Override
	public Class getEntityClass() {
		return HistoricTaskInstanceEntity.class;
	}
	
	/**
	 * 通过流程定义ID和节点ID获取历史任务记录
	 * @param actInstanceId
	 * @param nodeId
	 * @return
	 */
	public List<HistoricTaskInstanceEntity> getByInstanceIdAndNodeId(String actInstanceId,String nodeId){
		Map<String,String> map = new HashMap<String,String>();
		map.put("actInstanceId",actInstanceId);
		map.put("nodeId",nodeId);
		return this.getBySqlKey("getByInstanceIdAndNodeId", map);
	}
}
