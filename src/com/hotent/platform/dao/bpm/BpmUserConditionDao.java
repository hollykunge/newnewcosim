package com.hotent.platform.dao.bpm;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.bpm.BpmUserCondition;



/**
 *<pre>
 * 对象功能: 节点下的人员的配置规则 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ray
 * 创建时间:2012-12-31 15:26:17
 *</pre>
 */
@Repository
public class BpmUserConditionDao extends BaseDao<BpmUserCondition>
{
	@Override
	public Class<?> getEntityClass()
	{
		return BpmUserCondition.class;
	}

	/**
	 * 根据流程set获得条件
	 * @param setId
	 * @return
	 */
	public List<BpmUserCondition> getBySetId(Long setId){
		return getBySqlKey("getBySetId", setId);
	}

	public List<BpmUserCondition> getByActDefId(String actDefId) {
		return getBySqlKey("getByActDefId", actDefId);
	}

	/**
	 * 根据act流程定义Id删除人员的配置规则
	 * @param actDefId
	 */
	public void delByActDefId(String actDefId){
		delBySqlKey("delByActDefId", actDefId);
	}
}