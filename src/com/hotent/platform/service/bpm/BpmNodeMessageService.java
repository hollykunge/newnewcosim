package com.hotent.platform.service.bpm;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.bpm.BpmNodeMessageDao;
import com.hotent.platform.dao.system.MessageDao;
import com.hotent.platform.model.bpm.BpmNodeMessage;
import com.hotent.platform.model.system.Message;

/**
 * 对象功能:流程节点邮件 Service类
 * 开发公司:
 * 开发人员:pkq
 * 创建时间:2012-01-04 09:31:50
 */
@Service
public class BpmNodeMessageService extends BaseService<BpmNodeMessage>
{
	@Resource
	private BpmNodeMessageDao dao;
	
	@Resource
	private MessageDao messageDao;
	
	public BpmNodeMessageService()
	{
	}
	
	/**
	 * 根据ACT流程定义id获取流程定义。
	 * @param actDefId
	 * @return
	 */
	public List<BpmNodeMessage> getByActDefId(String actDefId)
	{
		return dao.getByActDefId(actDefId);
	}
	
	@Override
	protected IEntityDao<BpmNodeMessage, Long> getEntityDao() 
	{
		return dao;
	}
	/**
	 * 通过流程发布ID及节点id获取流程设置节点列表
	 * @param actDefId
	 * @param nodeId
	 * @return
	 */
	public List<BpmNodeMessage> getListByActDefIdNodeId(String actDefId,String nodeId)
	{
		return dao.getMessageByActDefIdNodeId(actDefId, nodeId);
	}

	/**
	 * 对流程节点消息设置进行保存和更新操作（更新操作为：先删除原来的记录，再添加新的消息实体）	
	 * @param nodeId 流程发布ID
	 * @param nodeId 流程节点ID
	 * @param messages 消息实体集
	 * @throws Exception 
	 */
	public void saveAndEdit(String actDefId,String nodeId,List<Message> messages) throws Exception{
		messageDao.delByActdefidAndNodeid(actDefId,nodeId);		
		dao.delByActDefIdAndNodeId(actDefId,nodeId);
		BpmNodeMessage bpmMessage=new BpmNodeMessage();
		bpmMessage.setActDefId(actDefId);
		bpmMessage.setNodeId(nodeId);
		for(Message message:messages)
		{
			bpmMessage.setId(UniqueIdUtil.genId());
			bpmMessage.setMessageId(UniqueIdUtil.genId());
			message.setMessageId(bpmMessage.getMessageId());					
			dao.add(bpmMessage);
			messageDao.add(message);
		}
	}
	
}
