package com.hotent.platform.service.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.TimeUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.biz.BizDefSegment;
import com.hotent.platform.model.biz.BizInstance;
import com.hotent.platform.model.biz.BizInstanceSegment;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.dao.biz.BizInstanceSegmentDao;

/**
 *<pre>
 * 对象功能:业务环节实例 Service类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 *</pre>
 */
@Service
public class BizInstanceSegmentService extends BaseService<BizInstanceSegment>
{
	@Resource
	private BizInstanceSegmentDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	
	
	
	public BizInstanceSegmentService()
	{
	}
	
	@Override
	protected IEntityDao<BizInstanceSegment, Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 启动业务实例环节 
	 * @param bizInstance
	 * @param bizDefSegment
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	public BizInstanceSegment start(BizInstance bizInstance,BizDefSegment bizDefSegment,BizCmd ctx) throws Exception{
		BizInstanceSegment bizInstanceSegment = new BizInstanceSegment();
		bizInstanceSegment.setBizInstanceSegmentId(UniqueIdUtil.genId());
		bizInstanceSegment.setBizDefSegmentId(bizDefSegment.getBizDefSegmentId());
		bizInstanceSegment.setBizInstanceId(bizInstance.getBizInstanceId());
		bizInstanceSegment.setCreateBy(bizInstance.getCreateBy());
		bizInstanceSegment.setCreatetime(new Date());
		bizInstanceSegment.setSortOrder(bizDefSegment.getSortOrder());
		ctx.setBizInstanceSegmentId(bizInstanceSegment.getBizInstanceSegmentId());
		ProcessRun processRun = startProcess(bizDefSegment,ctx);
		//new bizInstanceSegment
		ctx.setProcessRun(processRun);
		bizInstanceSegment.setActInstId(processRun.getRunId());
		bizInstanceSegment.setStatus(BizInstanceSegment.STATUS_RUNNING);
		dao.add(bizInstanceSegment);
		return bizInstanceSegment;
	}

	/**
	 * 启动业务实例环节 对应的流程
	 * @param bizDefSegment
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	private ProcessRun startProcess(BizDefSegment bizDefSegment,BizCmd ctx) throws Exception {
		ProcessCmd processCmd = ctx.getProcessCmd();
		if(processCmd==null){
			//判断流程定义是否被禁用
			BpmDefinition bpmDefinition = bpmDefinitionService.getMainDefByActDefKey(bizDefSegment.getActDefKey());
			if (bpmDefinition.getDisableStatus()==BpmDefinition.DISABLEStATUS_DA){
				throw new Exception("该流程已经被禁用");
			}
			processCmd = new ProcessCmd();
			processCmd.setFlowKey(bizDefSegment.getActDefKey());
			processCmd.setSubject(bizDefSegment.getSegmentName());
			processCmd.getVariables().put(BizProcessVarsConst.BIZ_CONTEXT,ctx);
			Long currentUserId = ctx.getCurrentUser().getUserId();
			processCmd.setCurrentUserId(String.valueOf(currentUserId));
			processCmd.setTaskExecutors(ctx.getExecutors());
		}
		processCmd.getVariables().put(BizProcessVarsConst.BIZ_CONTEXT,ctx);
		ProcessRun processRun=processRunService.startProcess(processCmd);
		return processRun;
	}
	
	/**
	 *  根据业务实例ID，获取业务实例环节列表
	 * @param bizInstanceId
	 * @return
	 */
	public List<BizInstanceSegment> getByBizInstanceId(Long bizInstanceId) {
		return dao.getByMainId(bizInstanceId);
	}
	
	/**
	 * 根据业务实例ID，删除业务实例环节
	 * @param bizInstanceId
	 */
	public void delByBizInstanceId(Long bizInstanceId) {
		dao.delByMainId(bizInstanceId);
	}
}
