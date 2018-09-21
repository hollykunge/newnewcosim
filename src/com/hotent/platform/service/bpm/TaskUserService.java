package com.hotent.platform.service.bpm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Service;

import com.hotent.core.db.GenericDao;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.AuthenticateImpl;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.bpm.TaskUserDao;
import com.hotent.platform.dao.system.PositionDao;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.SysOrgInfoDao;
import com.hotent.platform.dao.system.SysRoleDao;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.dao.system.SysUserOrgDao;
import com.hotent.platform.dao.system.UserPositionDao;
import com.hotent.platform.dao.system.UserRoleDao;
import com.hotent.platform.model.bpm.TaskUser;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.SysOrgInfo;

@Service
public class TaskUserService extends BaseService<TaskUser>{	
	@Resource
	private TaskUserDao taskUserDao;
	@Resource
	private SysUserDao sysUserDao;
	
	@Resource
	private SysOrgDao sysOrgDao;
	@Resource
	private PositionDao positionDao;
	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private SysUserOrgDao sysUserOrgDao;
	@Resource
	private UserPositionDao userPositionDao;
	@Resource
	private UserRoleDao userRoleDao;
	
	@Resource 
	private AuthenticateImpl authenticateImpl;
	
	@Resource 
	private SysOrgInfoDao sysOrgInfoDao;
	
	@Resource
	BpmService bpmService;
	
	
	@Override
	protected IEntityDao<TaskUser, Long> getEntityDao(){
		return taskUserDao;
	}
	
	public List<TaskUser> getByTaskId(String taskId){
		return taskUserDao.getByTaskId(taskId);
	}
	
	/**
	 * 取得任务的候选用户
	 * @param taskId
	 * @return
	 */
	public Set<TaskExecutor> getCandidateExecutors(String taskId){
		Set<TaskExecutor> taskUserSet=new HashSet<TaskExecutor>();
		List<TaskUser> taskUsers=getByTaskId(taskId);
		for(TaskUser taskUser:taskUsers){
			if(taskUser.getUserId()!=null){
				ISysUser sysUser=sysUserDao.getById(new Long(taskUser.getUserId()));
				taskUserSet.add(TaskExecutor.getTaskUser(taskUser.getUserId(), sysUser.getFullname()));
			}else if(taskUser.getGroupId()!=null){
				String tmpId=taskUser.getGroupId();
				if(TaskExecutor.USER_TYPE_ORG.equals(taskUser.getType())){//组织下的用户
					ISysOrg sysOrg=sysOrgDao.getById(new Long(tmpId));
					taskUserSet.add(TaskExecutor.getTaskOrg(tmpId, sysOrg.getOrgName()));
				}else if(TaskExecutor.USER_TYPE_POS.equals(taskUser.getType())){//岗位下的用户
					Position position=positionDao.getById(new Long(tmpId));
					taskUserSet.add(TaskExecutor.getTaskPos(tmpId, position.getPosName()));
				}else if(TaskExecutor.USER_TYPE_ROLE.equals(taskUser.getType())){//角色下的用户
					ISysRole sysRole=sysRoleDao.getById(new Long(tmpId));
					taskUserSet.add(TaskExecutor.getTaskRole(tmpId, sysRole.getRoleName()));
				}else if(TaskExecutor.USER_TYPE_COMP.equals(taskUser.getType())){//角色下的用户
					SysOrgInfo sysOrgInfo=sysOrgInfoDao.getById(new Long(tmpId));
					taskUserSet.add(TaskExecutor.getTaskRole(tmpId, sysOrgInfo.getName()));
				}
			}
		}
		return taskUserSet;
	}
	
	/**
	 * 根据任务Id获取任务候选人。
	 * @param taskId
	 * @return
	 */
	public Set<ISysUser> getCandidateUsers(String taskId){
		Set<ISysUser> taskUserSet=new HashSet<ISysUser>();
		List<TaskUser> taskUsers=getByTaskId(taskId);
		for(TaskUser taskUser:taskUsers){
			if(taskUser.getUserId()!=null){
				ISysUser sysUser=sysUserDao.getById(Long.parseLong( taskUser.getUserId()));
				taskUserSet.add(sysUser);
			}else if(taskUser.getGroupId()!=null){
				Long tmpId=Long.parseLong(taskUser.getGroupId());
				if(TaskExecutor.USER_TYPE_ORG.equals(taskUser.getType())){//组织下的用户
					List<ISysUser> userList= sysUserDao.getByOrgId(tmpId);
					taskUserSet.addAll(userList);
				}else if(TaskExecutor.USER_TYPE_POS.equals(taskUser.getType())){//岗位下的用户
					List<ISysUser> userList= sysUserDao.getByPosId(tmpId);
					taskUserSet.addAll(userList);
				}else if(TaskExecutor.USER_TYPE_ROLE.equals(taskUser.getType())){//角色下的用户
					List<ISysUser> userList=sysUserDao.getByRoleId(tmpId);
					taskUserSet.addAll(userList);
				}
			}
		}
		return taskUserSet;
	}

	/**
	 * 根据任务Id获取任务候选人。
	 * @param taskId
	 * @param isComp 是否对企业作处理
	 * @return
	 */
	public Set<ISysUser> getCandidateUsers(String taskId,boolean isComp){
		if(!isComp){
			getCandidateExecutors(taskId);
		}
		Set<ISysUser> taskUserSet=new HashSet<ISysUser>();
		List<TaskUser> taskUsers=getByTaskId(taskId);
		
		List<TaskUser> roles = new ArrayList<TaskUser>();
		List<TaskUser> comps = new ArrayList<TaskUser>();
		for(TaskUser taskUser:taskUsers){
			if(TaskExecutor.USER_TYPE_ROLE.equals(taskUser.getType())){
				roles.add(taskUser);
			}
			if(TaskExecutor.USER_TYPE_COMP.equals(taskUser.getType())){
				comps.add(taskUser);
			}
		}
		
		for(TaskUser taskUser:taskUsers){
			if(taskUser.getUserId()!=null){
				ISysUser sysUser=sysUserDao.getById(Long.parseLong( taskUser.getUserId()));
				taskUserSet.add(sysUser);
			}else if(taskUser.getGroupId()!=null){
				Long tmpId=Long.parseLong(taskUser.getGroupId());
				if(TaskExecutor.USER_TYPE_ORG.equals(taskUser.getType())){//组织下的用户
					List<ISysUser> userList= sysUserDao.getByOrgId(tmpId);
					taskUserSet.addAll(userList);
				}else if(TaskExecutor.USER_TYPE_POS.equals(taskUser.getType())){//岗位下的用户
					List<ISysUser> userList= sysUserDao.getByPosId(tmpId);
					taskUserSet.addAll(userList);
				}
			}
		}
		Integer flag=1;
		if(BeanUtils.isEmpty(taskUserSet)){
			flag=3;
		}
		taskUserSet.addAll(getUserByRoleAndComp(taskId,roles,comps,flag));
		return taskUserSet;
	}
	
	
	
	
	
	/**
	 * 根据角色和企业，取得用户
	 * 1、如果角色为空，取企业系统管理
	 * 2、如果角色为
	 * @param roles
	 * @param comps
	 * @param flag
	 *  0:
	 *  	
	 * 	1：
	 *	  	1、如果角色为空，取企业系统管理员角色
	 *	2：
	 *	    2、如果企业为空，取当前企业
	 *  3：
	 *	  	1、如果角色为空，取企业系统管理员角色
	 *	    2、如果企业为空，取当前企业
	 * @return
	 */
	private List<ISysUser> getUserByRoleAndComp(String taskId,List<TaskUser> roles,List<TaskUser> comps,int flag){
		List<Long> compIds = new ArrayList<Long>();
		List<Long> roleIds = new ArrayList<Long>();

//		TaskEntity taskEntity =bpmService.getTask(taskId);
		
		for(TaskUser u:comps){
			compIds.add(Long.valueOf(u.getGroupId()));
		}
		
		for(TaskUser u:roles){
			roleIds.add(Long.valueOf(u.getGroupId()));
		}
		
		List<ISysUser> sysUsers = new ArrayList<ISysUser>();
		switch (flag) {
		case 0:
			break;
		case 1:
			if(BeanUtils.isEmpty(roleIds)){
				ISysRole role = sysRoleDao.getByAlias("cloud_ROLE_ORGADMIN");
				if(role==null){
					return new ArrayList<ISysUser>();
				}
				roleIds.add(role.getRoleId());
				TaskUser taskUser = new TaskUser();
				taskUser.setGroupId(role.getRoleId().toString());
				taskUser.setId(String.valueOf(UniqueIdUtil.genId()));
				taskUser.setReversion(1);
				taskUser.setTaskId(taskId);
				taskUser.setType(TaskExecutor.USER_TYPE_ROLE);
				add(taskUser);
			}
			break;
		case 2:
			if(BeanUtils.isEmpty(compIds)){
				ISysUser currentSysUser = ContextUtil.getCurrentUser();
				if(currentSysUser==null){
					return new ArrayList<ISysUser>();
				}else{
					if(currentSysUser.getOrgId()!=null){
						compIds.add(currentSysUser.getOrgId());
						TaskUser taskUser = new TaskUser();
						taskUser.setGroupId(currentSysUser.getOrgId().toString());
						taskUser.setId(String.valueOf(UniqueIdUtil.genId()));
						taskUser.setReversion(1);
						taskUser.setTaskId(taskId);
						taskUser.setType(TaskExecutor.USER_TYPE_COMP);
						add(taskUser);
					}
				}
			}
			break;
		case 3:
			if(BeanUtils.isEmpty(roleIds)){
				ISysRole role = sysRoleDao.getByAlias("cloud_ROLE_ORGADMIN");
				if(role==null){
					return new ArrayList<ISysUser>();
				}
				roleIds.add(role.getRoleId());
				TaskUser taskUser = new TaskUser();
				taskUser.setGroupId(role.getRoleId().toString());
				taskUser.setId(String.valueOf(UniqueIdUtil.genId()));
				taskUser.setReversion(1);
				taskUser.setTaskId(taskId);
				taskUser.setType(TaskExecutor.USER_TYPE_ROLE);
				add(taskUser);
			}
			
			if(BeanUtils.isEmpty(compIds)){
				ISysUser currentSysUser = ContextUtil.getCurrentUser();
				if(currentSysUser==null){
					return new ArrayList<ISysUser>();
				}else{
					if(currentSysUser.getOrgId()!=null){
						compIds.add(currentSysUser.getOrgId());
						TaskUser taskUser = new TaskUser();
						taskUser.setGroupId(currentSysUser.getOrgId().toString());
						taskUser.setId(String.valueOf(UniqueIdUtil.genId()));
						taskUser.setReversion(1);
						taskUser.setTaskId(taskId);
						taskUser.setType(TaskExecutor.USER_TYPE_COMP);
						add(taskUser);
					}
				}
			}
			break;
		}
		
		if(BeanUtils.isEmpty(compIds)){
			return sysUsers;
		}
		for(Long compId:compIds){
			List<ISysUser> list;
			list = authenticateImpl.getByCompAndRoles(compId, roleIds);
			sysUsers.addAll(list);
		}
		return sysUsers;
	}
	
}
