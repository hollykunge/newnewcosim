package com.hotent.platform.service.biz;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.biz.BizDef;
import com.hotent.platform.model.biz.BizDefSegment;
import com.hotent.platform.model.biz.BizInstance;
import com.hotent.platform.model.biz.BizInstanceSegment;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.dao.biz.BizDefDao;
import com.hotent.platform.dao.biz.BizDefSegmentDao;
import com.hotent.platform.dao.biz.BizInstanceDao;
import com.hotent.platform.dao.biz.BizInstanceSegmentDao;
import com.ibm.db2.jcc.am.ne;

/**
 *<pre>
 * 对象功能:业务实例 Service类
 * 开发公司:
 * 开发人员:Raise
 * 创建时间:2013-04-11 11:48:44
 *</pre>
 */
@Service
public class BizInstanceService extends BaseService<BizInstance>
{
	@Resource
	private BizInstanceDao dao;
	
	@Resource
	private BizInstanceSegmentService bizInstanceSegmentService;
	@Resource
	private BizInstanceSegmentDao bizInstanceSegmentDao;
	
	@Resource
	private BizDefDao bizDefDao;
	
	@Resource
	private BizDefSegmentDao bizDefSegmentDao;
	
	@Resource
	private ProcessRunService processRunService;
	
	@Resource
	private BizRuBariableService bizRuBariableService;
	
	public BizInstanceService()
	{
	}
	
	@Override
	protected IEntityDao<BizInstance, Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 创建业务实例
	 * @param bizDefId 业务定义ID
	 * @throws Exception 
	 */
	public void startBizInstance(Long bizDefId,BizCmd bizCmd) throws Exception{
		BizDef bizDef=bizDefDao.getById(bizDefId);
		List<BizDefSegment> bizDefSegmentList=bizDefSegmentDao.getByMainId(bizDefId);
		
		bizDef.setBizDefSegmentList(bizDefSegmentList);
		
		//new BizInstance
		BizInstance bizInstance = new BizInstance();
		bizInstance.setBizInstanceId(UniqueIdUtil.genId());
		bizInstance.setBizDefId(bizDefId);
		bizInstance.setCreateBy(bizCmd.getCurrentUser().getUserId());
		bizInstance.setStartTime(new Date());
		
//		bizCmd.setBizInstance(bizInstance);
		bizCmd.setBizDefId(bizDefId);
		bizCmd.setBizInstanceId(bizInstance.getBizInstanceId());
		
		BizInstanceSegment bizInstanceSegment =startFirstBizDefSegmentInstance(bizDef,bizInstance,bizCmd);
		bizInstance.setStatus(BizInstance.STATUS_RUNNING);
		
		dao.add(bizInstance);
	}

	/**
	 * 启动业务定义的第一个环节
	 * @param bizDef
	 * @param bizInstance
	 * @param bizCmd
	 * @return
	 * @throws Exception
	 */
	private BizInstanceSegment startFirstBizDefSegmentInstance(BizDef bizDef,BizInstance bizInstance,BizCmd bizCmd) throws Exception {
		BizDefSegment bizDefSegment = bizDef.getFirstDefSegment();
		if(bizDefSegment==null){
			throw new InternalError("内部数据错误，业务实例必须包含子环节！");
		}
		BizInstanceSegment bizInstanceSegment = bizInstanceSegmentService.start(bizInstance, bizDefSegment, bizCmd);
		
		return bizInstanceSegment;
	}
	
	
	/**
	 * 结束业务实例
	 * @param bizInstanceId 业务实例Id
	 * @param exception 是否标识业务实例为异常
	 */
	public void stopBizInstance(Long bizInstanceId,boolean exception){
		BizInstance bizInstance = dao.getById(bizInstanceId);
		if(bizInstance==null){
			throw new InternalError("找不到流程实例(流程实例ID："+bizInstanceId+")！");
		}
//		bizInstanceSegmentService.
	}
	
	/**
	 * 更新业务实例状态。
	 * 
	 * @param bizCmd
	 */
	public void updBizInstance(BizCmd bizCmd){
		ProcessRun processRun = bizCmd.getProcessRun();
		Long bizDefId = bizCmd.getBizDefId();
		Long bizInstanceId = bizCmd.getBizInstanceId();
		Long bizInstanceSegmentId = bizCmd.getBizInstanceSegmentId();
		BizDef bizDef = bizDefDao.getById(bizDefId);
		if(bizDef==null){
			return;
		}
		List<BizDefSegment> bizDefSegmentList = bizDefSegmentDao.getByMainId(bizDefId);
		bizDef.setBizDefSegmentList(bizDefSegmentList);
		BizInstance bizInstance = getById(bizInstanceId);
		BizInstanceSegment bizInstanceSegment = bizInstanceSegmentService.getById(bizInstanceSegmentId);
		//get next BizDefSegment
		if(ProcessRun.STATUS_FINISH == processRun.getStatus()){
			bizInstanceSegment.setStatus(BizInstanceSegment.STATUS_STOP);
			bizInstanceSegmentService.update(bizInstanceSegment);
			Long currentSortOrder = bizInstanceSegment.getSortOrder();
			if(bizDef.getBizDefSegmentList().size()<=currentSortOrder){
				bizInstance.setStatus(BizInstance.STATUS_STOP);
				bizInstance.setEndTime(new Date());
				update(bizInstance);
				bizRuBariableService.delByBizInstId(bizCmd.getBizInstanceId());
			}else{
				BizDefSegment nextBizDefSegment = bizDef.getBizDefSegmentList().get(currentSortOrder.intValue());
				try {
					BizCmd nextBizCmd = new BizCmd();
					nextBizCmd.setBizDefId(bizCmd.getBizDefId());
					nextBizCmd.setBizInstanceId(bizCmd.getBizInstanceId());
					nextBizCmd.setBizInstanceSegmentId(nextBizDefSegment.getBizDefSegmentId());
					nextBizCmd.setCurrentUser(ContextUtil.getCurrentUser());
					ProcessCmd nextProcessCmd = new ProcessCmd();
					nextProcessCmd.setFlowKey(nextBizDefSegment.getActDefKey());
					nextProcessCmd.setSubject(nextBizDefSegment.getSegmentName());
					nextProcessCmd.getVariables().put(BizProcessVarsConst.BIZ_CONTEXT,nextBizCmd);
					Long currentUserId = nextBizCmd.getCurrentUser().getUserId();
					nextProcessCmd.setCurrentUserId(String.valueOf(currentUserId));
					nextProcessCmd.setBusinessKey(String.valueOf(UniqueIdUtil.genId()));
					bizInstanceSegmentService.start(bizInstance, nextBizDefSegment, nextBizCmd);
					bizRuBariableService.addVariable(bizCmd.getBizInstanceId(), bizCmd.getBizInstanceSegmentId(), bizCmd.getBizVars());
				} catch (Exception e) {
					e.printStackTrace();
					throw new Error("Create business instance segment error!");
				}
			}
			return;
		}else if(ProcessRun.STATUS_MANUAL_FINISH == processRun.getStatus()){
			
			bizInstanceSegment.setStatus(BizInstanceSegment.STATUS_EXCEPTION);
			bizInstanceSegmentService.update(bizInstanceSegment);
			bizInstance.setStatus(BizInstance.STATUS_EXCEPTION);
			bizInstance.setEndTime(new Date());
			
			update(bizInstance);
			return;
		}
		
	}
	
	/**
	 * 删除业务实例，级联删除相应的环节实例和流程实例
	 */
	public void delById(Long id) {
		BizInstance bizInstance = getById(id);
		List<BizInstanceSegment>  bizInstanceSegments= bizInstanceSegmentDao.getByMainId(bizInstance.getBizInstanceId());
		for(BizInstanceSegment bizInstanceSegment:bizInstanceSegments){
			processRunService.delById(bizInstanceSegment.getActInstId());
			bizInstanceSegmentService.update(bizInstanceSegment);
		}
		dao.delById(id);
	}

}
