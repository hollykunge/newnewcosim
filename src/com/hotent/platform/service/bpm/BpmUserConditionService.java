package com.hotent.platform.service.bpm;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.platform.dao.bpm.BpmUserConditionDao;
import com.hotent.platform.model.bpm.BpmUserCondition;

/**
 * <pre>
 * 对象功能:节点下的人员的配置规则   Service类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2012-12-31 15:26:17
 * </pre>
 */
@Service
public class BpmUserConditionService extends BaseService<BpmUserCondition> {
	@Resource
	private BpmUserConditionDao dao;

	public BpmUserConditionService() {
	}

	@Override
	protected IEntityDao<BpmUserCondition, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 根据流程set获得条件
	 * 
	 * @param setId
	 * @return
	 */
	public List<BpmUserCondition> getBySetId(Long setId) {
		return dao.getBySetId(setId);
	}

	/**
	 * 通过流程定义ID获得用户设置条件
	 * 
	 * @param actDefId 流程定义ID
	 * @return
	 */
	public List<BpmUserCondition> getByActDefId(String actDefId) {
		return dao.getByActDefId(actDefId);
	}
}
