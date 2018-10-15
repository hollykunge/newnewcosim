/**
 * 对象功能:消息设置 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:csx
 * 创建时间:2012-02-08 16:45:56
 */
package com.hotent.platform.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.system.Message;

@Repository
public class MessageDao extends BaseDao<Message>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return Message.class;
	}
	/**
	 * 通过流程发布ID及节点id获取流程设置节点
	 * @param actDefId
	 * @param nodeId
	 * @return
	 */
	public List<Message> getListByActDefIdNodeId(String actDefId,String nodeId)
	{
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("actDefId", actDefId);
		params.put("nodeId", nodeId);	
		return this.getBySqlKey("getListByActDefIdNodeId", params);
	}
	
	/**
	 * 根据actdefId和nodeId删除消息
	 * @param actdefId
	 * @param nodeId
	 */
	public void delByActdefidAndNodeid(String actdefId,String nodeId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("actdefId", actdefId);
		params.put("nodeId", nodeId);
		delBySqlKey("delByMessageId", params);
	}
	/**
	 * 通过流程发布ID获取流程设置节点
	 * @param actDefId
	 * @param nodeId
	 * @return
	 */
	public List<Message> getByActDefId(String actDefId)
	{	
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("actDefId", actDefId);
		return getBySqlKey("getByActDefId",params);
	}
		
}