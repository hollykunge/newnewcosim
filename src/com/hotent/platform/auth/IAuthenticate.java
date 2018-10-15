package com.hotent.platform.auth;

import java.util.*;

import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysOrg;

/** 用户组织角色操作接口 */
public interface IAuthenticate {
	
	/**
	 * 获取ISysUser一个新实例
	 * @return
	 */
	public ISysUser getNewSysUser();
	
	/**
	 * 获取ISysOrg一个新实例
	 * @return
	 */
	public ISysOrg getNewSysOrg();
	
	/**
	 * 获取ISysRole一个新实例
	 * @return
	 */
	public ISysRole getNewSysRole();
	
	/**
	 * 添加
	 * @param sysUser
	 */
	public void add(Object entity);
	
	/**
	 * 更新
	 * @param sysUser
	 */
	public void update(Object entity);
	
	/**
	 * 通过ID删除
	 * @param userId
	 */
	public void delById(Class clazz, Long id);
	
	/**
	 * 通过ID数组删除
	 * @param clazz
	 * @param ids
	 */
	public void delByIds(Class clazz, Long[] ids);
	
	
	/**
	 * 判断账号是否已经存在
	 * 
	 * @param account
	 * @return
	 */
	public boolean isAccountExist(String account);
	
	/**
	 * 判断账号是否已经存在
	 * 
	 * @param account
	 * @return
	 */
	public boolean isAccountInCompanyExist(String shortAccount, Long orgSn);
	
	/**
	 * 判断要更新的用户账号是否存在
	 * @param userId
	 * @param accout
	 * @return
	 */
	public boolean isAccountExistForUpd(Long userId, String accout);
	
	/**
	 * 通过用户ID获取用户
	 * 
	 * @param userId 
	 */
	public ISysUser getUserByUserId(Long userId);

	/**
	 * 通过用户账号获取用户 
	 * 
	 * @param account 
	 */
	public ISysUser getUserByAccount(String account);

	/**
	 * 获取所有用户
	 * @return
	 */
	public List<ISysUser> getAllUser();

	/**
	 * 通过组织ID获取组织 
	 *  @param orgId 
	 */
	public ISysOrg getOrgByOrgId(Long orgId);
	
	/**
	 * 根据维度ID获取组织
	 * @param demId
	 * @return
	 */
	public List<ISysOrg> getOrgByDemId(Long demId);
	
	/**
	 * 根据组织路径获取组织
	 * @param path
	 * @return
	 */
	public List<ISysOrg> getOrgByPath(String path);
	
	/**
	 * 通过路径删除组织
	 * @param path
	 */
	public void delOrgByPath(String path);
	
	/**
	 * 根据用户ID取得用户主组织
	 * @param userId
	 * @return
	 */
	public ISysOrg getPrimaryOrgByUserId(Long userId);
	
	/**
	 * 获取组织的用户成员 
	 * @param orgId
	 */
	public List<ISysUser> getUsersInOrg(Long orgId);

	/**
	 * 通过角色ID获取角色 
	 * 
	 * @param roleId
	 */
	public ISysRole getRoleByRoleId(Long roleId);
	
	/**
	 * 通过子系统ID获取角色
	 * @param systemId
	 * @return
	 */
	public List<ISysRole> getRoleBySystemId(Long systemId);
	
	/**
	 * 通过子系统id和角色名获取角色
	 * @param systemAlias
	 * @param roleName
	 * @return
	 */
	public List<ISysRole> getRoleBySystemId(String systemId, String roleName);
	
	/**
	 * 判断角色别名是否已经存在
	 * @param roleAlias
	 * @return
	 */
	public boolean isExistRoleAlias(String roleAlias);
	
	/**
	 * 判断角色是否存在
	 * 用于更新时判断
	 * @param roleAlias
	 * @param roleId
	 * @return
	 */
	public boolean isExistRoleAliasForUpd(String roleAlias, Long roleId);
	
	/**
	 * 通过角色ID获取拥有该角色的用户 
	 *  
	 *  @param roleId 
	 */
	public List<ISysUser> getUserInRole(Long roleId);

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	public List<ISysRole> getAllRoles();

	/**
	 * 修改密码
	 * 
	 * @param userId 用户ID
	 * @param newPwd 新密码
	 */
	public void changePassword(Long userId, String newPwd);

	/**
	 * 通过用户ID获取用户所在的组织 
	 * 
	 * @param userId 
	 */
	public List<ISysOrg> getOrgsUserIn(Long userId);
	
	/**
	 * 获取所有的组织
	 * @return
	 */
	public List<ISysOrg> getAllOrgs();

	/**
	 * 通过用户ID获取用户拥有的角色 
	 * 
	 *  @param userId 
	 */
	public List<ISysRole> getRolesUserHas(Long userId);

	/**
	 * 通过查询条件获取用户
	 * @param filter
	 * @param pageBean
	 * @return
	 */
	public List<ISysUser> queryUser(QueryFilter queryFilter);
	
	/**
	 * 通过组织ID查询用户
	 * @param filter
	 * @param pageBean
	 * @return
	 */
	public List<ISysUser> queryUserByOrgId(QueryFilter queryFilter);
	
	/**
	 * 查询没有组织的用户
	 * @param filter
	 * @param pageBean
	 * @return
	 */
	public List<ISysUser> queryUserNoOrg(QueryFilter queryFilter);
	
	/**
	 * 根据岗位路径查询用户
	 * @param filter
	 * @param pageBean
	 * @return
	 */
	public List<ISysUser> queryUserByPosPath(QueryFilter queryFilter);
	
	/**
	 * 根据组织路径查询用户
	 * @param filter
	 * @param pageBean
	 * @return
	 */
	public List<ISysUser> queryUserByOrgPath(QueryFilter queryFilter);
	
	/**
	 * 通过角色ID查询用户
	 * @param filter
	 * @param pageBean
	 * @return
	 */
	public List<ISysUser> queryUserByRoleId(QueryFilter queryFilter);
	
	/*
	 *  modifyBy hotent.xianggang  添加对应方法  b
	 */
	
	/**
	 * 通过企业ID查询用户
	 * @param filter
	 * @param pageBean
	 * @return
	 */
	public List<ISysUser> getUserByEnterprise(QueryFilter queryFilter);
	
	
	/*
	 *  modifyBy hotent.xianggang  添加对应方法  e
	 */
	
	/**
	 * 通过条件查询角色
	 * @param filter
	 * @param pageBean
	 * @return
	 */
	public List<ISysRole> queryRole(QueryFilter queryFilter);
	
	/**
	 * 通过条件查询组织
	 * @param filter
	 * @param pageBean
	 * @return
	 */
	public List<ISysOrg> queryOrg(QueryFilter queryFilter);
	
	/**
	 * 根据用户属性查询用户
	 * @param userParam
	 * @return
	 */
	public List<ISysUser> queryUserByUserParam(Map<String, String> userParam);
	
	/**
	 * 根据组织属性查询用户
	 * @param userParam
	 * @return
	 */
	public List<ISysUser> queryUserByOrgParam(Map<String, String> userParam);
	
	/**
	 * 根据上下级关系取得用户。
	 * 
	 * <pre>
	 * 输入参数 :
	 * 	path 岗位路径。
     *	condition：上级或下级。
	 * </pre>
	 * @param p
	 * @return
	 */
	public List<ISysUser> getUserUpLowPost(Map<String, Object> p);
	
	/**
	 * 根据上下级组织取得用户列表。
	 * 
	 * <pre>
	 * 	输入参数：
	 * 		demensionId：维度ID。
	 * 		path:		路径。
	 * 		condition：	上级或下级。
	 * 		depth：		上几级或下几级。
	 * </pre>
	 * @param p
	 * @return
	 */
	public List<ISysUser> getUserUpLowOrg(Map<String, Object> p);
	
	/**
	 * 根据用户ID和维度ID获取组织
	 * @param userId
	 * @param demId
	 * @return
	 */
	public List<ISysOrg> getOrgByUserIdAndDemId(Long userId, Long demId);
	
	/**
	 * 通过用户ID集获取用户列表
	 * @param userIds
	 * @return
	 */
	public List<ISysUser> getUserByIdSet(Set userIds);
	
	/**
	 * 通过邮箱获取用户
	 * @param address
	 * @return
	 */
	public ISysUser getUserByMail(String address);
	
	/**
	 * 更新用户状态
	 * @param userId 用户ID
	 * @param status 用户状态
	 * @param isLock 用户是否锁定
	 */
	public void updUserStatus(Long userId, Short status, Short isLock);
	
	/**
	 * 根据用户来源类型获取用户列表
	 * @param type
	 * @return
	 */
	public List<ISysUser> getUserByFromType(Short type);
	
	/**
	 * 更新sn
	 * @param orgId
	 * @param sn
	 * @return
	 */
	public void updSn(Long orgId, long sn);
	
	/**
	 * 根据上级组织ID获取组织列表。
	 * @param orgSupId
	 * @return
	 */
	public List<ISysOrg> getOrgByOrgSupId(Long orgSupId);
	
	/**
	 * 根据上级组织ID获取组织列表。
	 * zouping
	 * @param orgSupId 上级组织Id
	 * @param compId 公司Id
	 * @return
	 */
	public List<ISysOrg> getOrgByOrgSupId(Long orgSupId, Long compId);
	
	/**
	 * 根据上级组织ID获取组织列表。
	 * @param orgSupId
	 * @return
	 */
	public ISysOrg getOrgByOrgSn(Long orgSn);
	
	/**
	 * 根据组织ID和用户帐号查询
	 * @param orgId
	 * @param account
	 * @return
	 */
	public ISysUser getSysUserByOrgIdAndAccount(Long orgId, String account);
	
	/**
	 * 根据组织SN和用户帐号查询
	 * @param orgSn
	 * @param account
	 * @return
	 */
	public ISysUser getSysUserByOrgSnAndAccount(Long orgSn, String account);
	
	////ht:raise add b
	/**
	 * 根据企事ID和角色ID，取得用户
	 * @param orgIds
	 * @param roles
	 * @return
	 */
	List<ISysUser> getByCompAndRoles(Long compId, List<Long> roleIds);
	
	/**
	 * 根据企业ID，取得默认用户（管理员）
	 * @param orgId
	 * @return
	 */
	List<ISysUser> getCompDefaultUser(Long compId);
	
	////ht:raise add e
	
	/////ht b
	public SysOrg getOrgBySn(Long sn);
	/////ht e
}