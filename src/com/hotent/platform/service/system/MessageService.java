package com.hotent.platform.service.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.platform.model.system.Message;
import com.hotent.platform.dao.system.MessageDao;

/**
 * 对象功能:消息设置 Service类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2012-02-08 16:45:56
 */
@Service
public class MessageService extends BaseService<Message>
{
	@Resource
	private MessageDao dao;
	
	public MessageService()
	{
	}
	
	@Override
	protected IEntityDao<Message, Long> getEntityDao() 
	{
		return dao;
	}
	/**
	 * 通过流程发布ID及节点id获取流程设置节点列表
	 * @param actDefId
	 * @param nodeId
	 * @return
	 */
	public List<Message> getListByActDefIdNodeId(String actDefId,String nodeId)
	{
		return dao.getListByActDefIdNodeId(actDefId, nodeId);
	}
	/**
	 * 通过流程发布ID及节点id获取流程设置节点Map对象。
	 * @param actDefId 流程定义id
	 * @param nodeId 流程节点ID
	 * @return 返回map对象，键为消息类型
	 */
	public Map<Integer,Message> getMapByActDefIdNodeId(String actDefId,String nodeId){
		List<Message> instList=this.getListByActDefIdNodeId(actDefId, nodeId);
		Map<Integer,Message> dataMap=new HashMap<Integer,Message>();
		if(BeanUtils.isEmpty(instList)) return dataMap;
		for(Message mesModel:instList){
			Integer messageType=mesModel.getMessageType();
			dataMap.put(messageType, mesModel);
		}
		return dataMap;
	}

}
