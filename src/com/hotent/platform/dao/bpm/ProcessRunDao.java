/**
 * <pre>
 * 对象功能:流程实例扩展Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:csx
 * 创建时间:2011-12-03 09:33:06
 * </pre>
 */
package com.hotent.platform.dao.bpm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hotent.core.db.BaseDao;
import com.hotent.core.model.BaseModel;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;

@Repository
public class ProcessRunDao extends BaseDao<ProcessRun>
{
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass()
	{
		return ProcessRun.class;
	}
	
	@Override
	public ProcessRun getById(Long primaryKey) {
		ProcessRun processRun =  super.getById(primaryKey);
		if(processRun==null){
			return getByIdHistory(primaryKey);
		}else{
			return processRun;
		}
	}

	/**
	 * 通过Act的流程实例Id获取ProcessRun实体
	 * @param processInstanceId
	 * @return
	 */
	public ProcessRun getByActInstanceId(String processInstanceId)
	{
		ProcessRun processRun =  getUnique("getByActInstanceId", processInstanceId);
		if(processRun==null){
			return getByActInstanceIdHistory(processInstanceId);
		}else{
			return processRun;
		}
	}
	
	/**
	 * 获取历史实例
	 * @param queryFilter
	 * @return
	 */
	public List<ProcessRun> getAllHistory(QueryFilter queryFilter)
	{
		return getBySqlKey("getAllFinish", queryFilter);
	}
	
	/**
	 * 更新流程名称
	 * @param defId
	 * @param processName
	 * @return
	 */
//	public int updateProcessNameByDefId(Long defId,String processName)
//	{
//		Map<String, Object> params=new HashMap<String, Object>();
//		params.put("defId", defId);
//		params.put("processName", processName);
//		return update("updateProcessNameByDefId", params);
//	}

	/**
	 * 查看我参与审批流程列表
	 * @param filter
	 * @return
	 */
	public List<ProcessRun> getMyAttend(QueryFilter filter)
	{
		return getBySqlKey("getMyAttend", filter);
	}
	
	public List<ProcessRun> getMyProcessRun(Long creatorId,String subject,Short status,PageBean pb){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("creatorId", creatorId);
		params.put("subject", subject);
		params.put("status", status);
		return getBySqlKey("getMyProcessRun", params, pb);
	}
	
	/**
	 * 工作台显示我参与审批 流程
	 * @param assignee
	 * @param status
	 * @param pb
	 * @return
	 */
	public List<ProcessRun> getMyAttend(Long assignee,Short status,PageBean pb){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("assignee", assignee);
		params.put("status", status);
		return getBySqlKey("getMyAttend", params, pb);
	}

	/**
	 * 工作台显示我发起的流程
	 * @param creatorId
	 * @param pb
	 * @return
	 */
	public List<ProcessRun> myStart(Long creatorId,PageBean pb){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("creatorId", creatorId);
		return getBySqlKey("getAll", params, pb);
	}
	
	
	/**
	 * 根据Act流程定义ID，获取流程实例
	 * @param actDefId
	 * @param pb
	 * @return
	 */
	public List<ProcessRun> getByActDefId(String actDefId,PageBean pb){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("actDefId", actDefId);
		return getBySqlKey("getAll", params, pb);
	}
	
	/**
	 * 添加流程运行实例历史记录
	 * @param entity
	 */
	public void addHistory(ProcessRun entity) {
		String addStatement=getIbatisMapperNamespace() + ".addHistory";
		if(entity instanceof BaseModel)
		{
			BaseModel baseModel=((BaseModel) entity);
			baseModel.setCreatetime(new Date());
			baseModel.setUpdatetime(new Date());
			//添加更新人ID以及修改人ID
			Long curUserId=ContextUtil.getCurrentUserId();
			if(curUserId!=null){
				baseModel.setCreateBy(curUserId);
				baseModel.setUpdateBy(curUserId);
			}
		}
		getSqlSessionTemplate().insert(addStatement, entity);
	}
	
	
	/**
	 * 更新对象。
	 * @return 返回更新的记录数
	 */
	public int updateHistory(ProcessRun entity)
	{
		String updStatement=getIbatisMapperNamespace() + ".updateHistory";
		
		if(entity instanceof BaseModel)
		{
			BaseModel baseModel=((BaseModel) entity);
			baseModel.setUpdatetime(new Date());
			//添加更新人ID以及修改人ID
			Long curUserId=ContextUtil.getCurrentUserId();
			if(curUserId!=null){
				baseModel.setUpdateBy(curUserId);
			}
		}
		
		int affectCount = getSqlSessionTemplate().update(updStatement, entity);
		return affectCount;
	}
	


	/**
	 * 删除流程历史
	 * @param id 流程历史记录ID
	 * @return
	 */
	public int delByIdHistory(Long id){
		String delStatement=getIbatisMapperNamespace() + ".delByIdHistory";
		int affectCount = getSqlSessionTemplate().delete(delStatement, id);
		return affectCount;
	}
	/**
	 * 到历史表查询流程实例
	 * @param primaryKey
	 * @return
	 */
	public ProcessRun getByIdHistory(Long primaryKey) {
		String getStatement= getIbatisMapperNamespace() + ".getByIdHistory";
		return  (ProcessRun) getSqlSessionTemplate().selectOne(getStatement, primaryKey);
	}
	/**
	 * 通过Act的流程实例Id获取流程ProcessRun实体
	 * @param processInstanceId
	 * @return
	 */
	public ProcessRun getByActInstanceIdHistory(String actInstId) {
		return getUnique("getByActInstanceIdHistory", actInstId);
	}
	
	@Override
	public void add(ProcessRun entity) {
		super.add(entity);
	}
	
	/**
	 * 根据act流程定义Id获取流程运行实例
	 * @param actDefId
	 */
	public List<ProcessRun> getbyActDefId(String actDefId){
		return getBySqlKey("getbyActDefId",actDefId);
	}
	/**
	 * 根据act流程定义Id删除流程运行实例
	 * @param actDefId
	 */
	public void delByActDefId(String actDefId){
		delBySqlKey("delByActDefId", actDefId);
	}
	/**
	 * 根据act流程定义Id删除流程历史
	 * @param actDefId
	 */
	public void delHistroryByActDefId(String actDefId){
		delBySqlKey("delHistroryByActDefId", actDefId);
	}
	/*
	@Override
	public int update(ProcessRun entity) {
		return super.update(entity);
	}
	
	@Override
	public int update(String sqlKey, Object params) {
		return super.update(sqlKey, params);
	}
	@Override
	public int delById(Long id) {
		return super.delById(id);
	}
	@Override
	public int delBySqlKey(String sqlKey, Object params) {
		return super.delBySqlKey(sqlKey, params);
	}
	*/
}