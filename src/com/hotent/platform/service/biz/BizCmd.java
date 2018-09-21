package com.hotent.platform.service.biz;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.AppUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.biz.BizDef;
import com.hotent.platform.model.biz.BizInstance;
import com.hotent.platform.model.biz.BizInstanceSegment;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.system.SysUserService;

/**
 * 简单业务上下文。
 * @author Raise
 *
 */
public class BizCmd implements Serializable {
	private static final long serialVersionUID = 1L;
	//发起人ID
	private Long startUserId;
	transient private ISysUser startUser;
	//当前用户
	transient private ISysUser currentUser;
	//当前业务定义ID
	private Long bizDefId;
	transient private BizDef bizDef;
	//当前业务实例
	private Long bizInstanceId;
	transient private BizInstance Instance;
	//当前业务实例环节实例
	private Long bizInstanceSegmentId;
	transient private BizInstanceSegment InstanceSegment;
	transient private List<TaskExecutor> executors;
	//流程扩展实例
	transient private ProcessRun processRun;
	transient private ProcessCmd processCmd;
	
	transient private Map<String,Object> bizVars = new HashMap<String, Object>();
	
	public Long getStartUserId() {
		return startUserId;
	}
	public void setStartUserId(Long startUserId) {
		this.startUserId = startUserId;
		SysUserService sysUserService = (SysUserService) AppUtil.getBean(SysUserService.class);
		this.startUser = sysUserService.getById(startUserId);
	}
	public ISysUser getStartUser() {
		return startUser;
	}
	public void setStartUser(ISysUser startUser) {
		this.startUser = startUser;
		this.startUserId = startUser.getUserId();
	}
	public ISysUser getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(ISysUser currentUser) {
		this.currentUser = currentUser;
	}
	public Long getBizDefId() {
		return bizDefId;
	}
	public void setBizDefId(Long bizDefId) {
		this.bizDefId = bizDefId;
		BizDefService bizDefService = (BizDefService) AppUtil.getBean(BizDefService.class);
		this.bizDef = bizDefService.getById(bizDefId);
	}
	public Long getBizDef() {
		return bizDefId;
	}
	public void setBizDef(BizDef bizDef) {
		this.bizDef = bizDef;
		this.bizDefId = this.bizDef.getBizDefId();
	}
	
	public BizInstance getBizInstance() {
		return Instance;
	}
	public void setBizInstance(BizInstance Instance) {
		this.Instance = Instance;
	}
	public BizInstanceSegment getBizInstanceSegment() {
		return InstanceSegment;
	}
	public void setBizInstanceSegment(BizInstanceSegment InstanceSegment) {
		this.InstanceSegment = InstanceSegment;
	}
	
	public Long getBizInstanceId() {
		return bizInstanceId;
	}
	public void setBizInstanceId(Long bizInstanceId) {
		this.bizInstanceId = bizInstanceId;
	}
	public Long getBizInstanceSegmentId() {
		return bizInstanceSegmentId;
	}
	public void setBizInstanceSegmentId(Long bizInstanceSegmentId) {
		this.bizInstanceSegmentId = bizInstanceSegmentId;
	}
	public List<TaskExecutor> getExecutors() {
		return executors;
	}
	public void setExecutors(List<TaskExecutor> executors) {
		this.executors=executors;
	}
	public ProcessRun getProcessRun() {
		return processRun;
	}
	public void setProcessRun(ProcessRun processRun) {
		this.processRun = processRun;
	}
	public ProcessCmd getProcessCmd() {
		return processCmd;
	}
	public void setProcessCmd(ProcessCmd processCmd) {
		this.processCmd = processCmd;
	}
	public Map<String,Object> getBizVars() {
		if(bizVars==null){
			bizVars = new HashMap<String, Object>();
		}
		return bizVars;
	}
	public void setBizVars(Map<String,Object> bizVars) {
		this.bizVars = bizVars;
	}
	
}
