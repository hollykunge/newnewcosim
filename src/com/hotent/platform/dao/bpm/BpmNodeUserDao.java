/**
 * 对象功能:InnoDB free: 11264 kB Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:cjj
 * 创建时间:2011-12-05 17:20:40
 */
package com.hotent.platform.dao.bpm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.platform.model.bpm.BpmNodeUser;

@Repository
public class BpmNodeUserDao extends BaseDao<BpmNodeUser>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return BpmNodeUser.class;
	}
	
	/**
	 * 根据流程节点ID获得流程节点人员
	 * @param  setId
	 * @return
	 */
	public List<BpmNodeUser> getBySetId(Long setId){
		return getBySqlKey("getBySetId", setId);
	}
	
	/**
	 * 根据流程定义ID获得流程节点人员
	 * @param actDefId
	 * @return
	 */
	public List<BpmNodeUser> getByActDefId(String actDefId) {
		return getBySqlKey("getByActDefId", actDefId);
	} 
	
	/**
	 * 
	 * @param actDefId
	 */
	public void delByActDefId(String actDefId){
		delBySqlKey("delByActDefId", actDefId);
	}
	/**
	 * 根据流程定义id获得流程节点人员
	 * @param defId
	 * @return
	 */
	public List<BpmNodeUser> getBySetIdAndConditionId(Long setId,Long conditionId){
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("setId", setId);
		map.put("conditionId", conditionId);
		return (List<BpmNodeUser>)getBySqlKey("getBySetIdAndConditionId",map);
	}
	
	/**
	 * 
	 * @Methodname: delByConditionId
	 * @Discription: 
	 * @param conditionId
	 * @Author HH
	 * @Time 2012-12-19 下午7:34:42
	 */
	public void delByConditionId(Long conditionId)
	{
		getBySqlKey("delByConditionId",conditionId);
	}
	
	/**
	 * 修复数据
	 * @return
	 */
	public List<BpmNodeUser> selectNull(){
		return (List<BpmNodeUser>)getBySqlKey("selectNull");
	}


}