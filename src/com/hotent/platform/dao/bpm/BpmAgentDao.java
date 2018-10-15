/**
 * 对象功能:流程代理 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:csx
 * 创建时间:2012-01-07 17:31:47
 */
package com.hotent.platform.dao.bpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.bpm.BpmAgent;
import com.hotent.platform.model.system.UserPosition;

@Repository
public class BpmAgentDao extends BaseDao<BpmAgent>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return BpmAgent.class;
	}

	public List<BpmAgent> getByAgentId(Long agentid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentid", agentid);
		List<BpmAgent> list= this.getSqlSessionTemplate().selectList(this.getIbatisMapperNamespace() + ".getAll", params);
		return list;
	}

	public void delByAgentId(Long agentid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentid", agentid);
		getBySqlKey("delByAgentId",params);
	}
	
	public List<BpmAgent> getByDefKey(String defKey) {
		List<BpmAgent> list= this.getSqlSessionTemplate().selectList(this.getIbatisMapperNamespace() + ".getByDefKey", defKey);
		return list;
	}
	
	public List<String> getNotInByAgentId(Long agentid){		
		List list= getBySqlKey("getNotInByAgentId",agentid);
		return list;
	}
	
	public List<BpmAgent> getByActDefId(String actDefId) {
		List<BpmAgent> list= getBySqlKey("getByActDefId", actDefId);
		return list;
	}
	/**
	 * 根据act流程定义Id删除流程代理
	 * @param actDefId
	 */
	public void delByActDefId(String actDefId){
		delBySqlKey("delByActDefId", actDefId);
	}
}