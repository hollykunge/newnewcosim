package com.hotent.platform.service.bpm;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.bpm.BpmNodeUserUplowDao;
import com.hotent.platform.model.bpm.BpmNodeUserUplow;

/**
 * 对象功能: 用户节点的上下级 Service类
 * 开发公司:
 * 开发人员:csx
 * 创建时间:2012-03-02 10:07:45
 */
@Service
public class BpmNodeUserUplowService extends BaseService<BpmNodeUserUplow>
{
	@Resource
	private BpmNodeUserUplowDao dao;
	
	public BpmNodeUserUplowService()
	{
	}
	
	@Override
	protected IEntityDao<BpmNodeUserUplow, Long> getEntityDao() 
	{
		return dao;
	}
	/**
	 * 更新上下级。
	 * @param nodeUserId
	 * @param uplowList
	 * @throws Exception
	 */
	public void upd(long nodeUserId,List<BpmNodeUserUplow> uplowList) throws Exception{
		dao.delByNodeUserId(nodeUserId);
		
		if(BeanUtils.isEmpty(uplowList)) return;
		
		for(BpmNodeUserUplow e:uplowList){
			e.setID(UniqueIdUtil.genId());
			e.setNodeUserId(nodeUserId);
			dao.add(e);
		}
		
		
	}
	public List<BpmNodeUserUplow> getByNodeUserId(long userId){
		return dao.getByNodeUserId(userId);
	}
	
}
