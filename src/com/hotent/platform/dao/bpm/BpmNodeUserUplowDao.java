/**
 * 对象功能: 用户节点的上下级 Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:csx
 * 创建时间:2012-03-02 10:07:45
 */
package com.hotent.platform.dao.bpm;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.bpm.BpmNodeUserUplow;

@Repository
public class BpmNodeUserUplowDao extends BaseDao<BpmNodeUserUplow>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return BpmNodeUserUplow.class;
	}
	
	/**
	 * 根据用户节点设置id删除上下级设置。
	 * @param nodeUserId
	 * @return
	 */
	public int delByNodeUserId(long nodeUserId){
		return this.delBySqlKey("delByNodeUserId", nodeUserId);
		
	}
	
	/**
	 * 根据用户获取上下级关系列表。
	 * @param userId
	 * @return
	 */
	public List<BpmNodeUserUplow> getByNodeUserId(long userId){
		return this.getBySqlKey("getByNodeUserId", userId);
	}
	
}