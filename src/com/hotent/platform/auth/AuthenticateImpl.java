package com.hotent.platform.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.SysRoleDao;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SecurityUtil;

/**
 * 用户、组织、角色的验证实现类
 * 
 * <pre>
 * 在用户、组织、角色的dao层和service层之间使用<br />实现了IAuthenticate的类来解耦，
 * 在实现不同的用户、组织和角色时只需替换IAuthenticate接口的实现类。
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class AuthenticateImpl implements IAuthenticate {
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private SysOrgDao sysOrgDao;
	
	@Resource(name="systemproperties")
	private Properties systemproperties;

	public boolean isAccountExist(String account) {
		return sysUserDao.isAccountExist(account);
	}
	
	/**
	 * zouping
	 * 2013-05-14
	 * 判断同一企业账号是否存在
	 */
	public boolean isAccountInCompanyExist(String shortAccount,Long orgSn) {
		return sysUserDao.isAccountInCompanyExist(shortAccount, orgSn);
	}

	public boolean isAccountExistForUpd(Long userId, String account) {
		
		return sysUserDao.isAccountExistForUpd(userId, account);
	}

	public ISysUser getUserByUserId(Long userId) {
		return sysUserDao.getById(userId);
	}

	public ISysUser getUserByAccount(String account) {
		return sysUserDao.getByAccount(account);
	}

	public List<ISysUser> getAllUser() {
		return sysUserDao.getAll();
	}

	public ISysOrg getOrgByOrgId(Long orgId) {
		return sysOrgDao.getById(orgId);
	}

	public List<ISysOrg> getOrgByDemId(Long demId) {
		return sysOrgDao.getOrgsByDemIdOrAll(demId);
	}

	public List<ISysOrg> getOrgByPath(String path) {
		return sysOrgDao.getByOrgPath(path);
	}

	public void delOrgByPath(String path) {
		sysOrgDao.delByPath(path);
	}

	public ISysOrg getPrimaryOrgByUserId(Long userId) {
		return sysOrgDao.getPrimaryOrgByUserId(userId);
	}

	public List<ISysUser> getUsersInOrg(Long orgId) {
		return sysUserDao.getByOrgId(orgId);
	}

	public ISysRole getRoleByRoleId(Long roleId) {
		return sysRoleDao.getById(roleId);
	}

	public List<ISysRole> getRoleBySystemId(Long systemId) {
		return sysRoleDao.getBySystemId(systemId);
	}


	public boolean isExistRoleAlias(String roleAlias) {
		return sysRoleDao.isExistRoleAlias(roleAlias);
	}

	public boolean isExistRoleAliasForUpd(String roleAlias, Long roleId) {
		return sysRoleDao.isExistRoleAliasForUpd(roleAlias, roleId);
	}

	public List<ISysUser> getUserInRole(Long roleId) {
		return sysUserDao.getByRoleId(roleId);
	}

	public List<ISysRole> getAllRoles() {
		return sysRoleDao.getAll();
	}

	public void changePassword(Long userId, String newPwd) {
		sysUserDao.updPwd(userId, newPwd);
	}

	public List<ISysOrg> getOrgsUserIn(Long userId) {
		return sysOrgDao.getOrgsByUserId(userId);
	}

	public List<ISysRole> getRolesUserHas(Long userId) {
		return sysRoleDao.getByUserId(userId);
	}

	public List<ISysUser> queryUser(QueryFilter queryFilter) {
		return sysUserDao.getUserByQuery(queryFilter);
	}

	public List<ISysUser> queryUserByOrgId(QueryFilter queryFilter) {
		return sysUserDao.getUserByOrgId(queryFilter);
	}

	public List<ISysUser> queryUserNoOrg(QueryFilter queryFilter) {
		return sysUserDao.getUserNoOrg(queryFilter);
	}

	public List<ISysUser> queryUserByPosPath(QueryFilter queryFilter) {
		return sysUserDao.getDistinctUserByPosPath(queryFilter);
	}

	public List<ISysUser> queryUserByOrgPath(QueryFilter queryFilter) {
		return sysUserDao.getDistinctUserByOrgPath(queryFilter);
	}

	public List<ISysUser> queryUserByRoleId(QueryFilter queryFilter) {
		return sysUserDao.getUserByRoleId(queryFilter);
	}
	/*
	 *  modifyBy hotent.xianggang  添加对应方法  b
	 */
	public List<ISysUser> getUserByEnterprise(QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		return sysUserDao.getUserByEnterprise(queryFilter);
	}
	/*
	 *  modifyBy hotent.xianggang  添加对应方法  e
	 */

	public List<ISysRole> queryRole(QueryFilter queryFilter) {
		return sysRoleDao.getRole(queryFilter);
	}

	public List<ISysOrg> queryOrg(QueryFilter queryFilter) {
		return sysOrgDao.getOrgByOrgId(queryFilter);
	}

	public List<ISysUser> queryUserByUserParam(Map<String, String> userParam) {
		return sysUserDao.getByUserOrParam(userParam);
	}

	public List<ISysUser> queryUserByOrgParam(Map<String, String> userParam) {
		return sysUserDao.getByOrgOrParam(userParam);
	}

	public List<ISysUser> getUserUpLowPost(Map<String, Object> p) {
		return sysUserDao.getUpLowPost(p);
	}

	public List<ISysUser> getUserUpLowOrg(Map<String, Object> p) {
		return sysUserDao.getUpLowOrg(p);
	}

	public List<ISysOrg> getOrgByUserIdAndDemId(Long userId, Long demId) {
		return sysOrgDao.getByUserIdAndDemId(userId, demId);
	}

	public List<ISysUser> getUserByIdSet(Set userIds) {
		return sysUserDao.getByIdSet(userIds);
	}

	public ISysUser getUserByMail(String address) {
		return sysUserDao.getByMail(address);
	}

	public void updUserStatus(Long userId, Short status, Short isLock) {
		sysUserDao.updStatus(userId, status, isLock);
	}

	public List<ISysUser> getUserByFromType(Short type) {
		return sysUserDao.getByFromType(type);
	}

	public ISysOrg getNewSysOrg() {	
		return new SysOrg();
	}

	public ISysUser getNewSysUser() {
		return new SysUser();
	}

	public ISysRole getNewSysRole() {
		return new SysRole();
	}

	public void add(Object entity) {
		String className = entity.getClass().getName();
		
		if(BeanUtils.isInherit(entity.getClass(),ISysUser.class))
			sysUserDao.add((ISysUser)entity);
		if(BeanUtils.isInherit(entity.getClass(),ISysOrg.class))
			sysOrgDao.add((ISysOrg)entity);
		if(BeanUtils.isInherit(entity.getClass(),ISysRole.class))
			sysRoleDao.add((ISysRole)entity);
	}

	public void update(Object entity) {
		String className = entity.getClass().getName();
		
		if(BeanUtils.isInherit(entity.getClass(),ISysUser.class))
			sysUserDao.update((ISysUser)entity);
		if(BeanUtils.isInherit(entity.getClass(),ISysOrg.class))
			sysOrgDao.update((ISysOrg)entity);
		if(BeanUtils.isInherit(entity.getClass(),ISysRole.class))
			sysRoleDao.update((ISysRole)entity);
	}

	public void delById(Class clazz, Long id) {
		if(BeanUtils.isInherit(clazz,ISysUser.class)){
			sysUserDao.delById(id);
			//删除用户角色的缓存。
			SecurityUtil.removeUserRoleCache(id);
		}
			
		if(BeanUtils.isInherit(clazz,ISysOrg.class))
			sysOrgDao.delById(id);
		if(BeanUtils.isInherit(clazz,ISysRole.class))
			sysRoleDao.delById(id);
	}

	public void delByIds(Class clazz, Long[] ids) {
		if(BeanUtils.isEmpty(ids)) return;
		for(Long id:ids){
			delById(clazz,id);
		}
	}

	public List<ISysOrg> getAllOrgs() {
		return sysOrgDao.getAll();
	}

	@Override
	public List<ISysRole> getRoleBySystemId(String systemId, String roleName) {
		return  sysRoleDao.getRoleBySystemId(new Long( systemId), roleName);
		
	}

	@Override
	public void updSn(Long orgId, long sn) {
		sysOrgDao.updSn(orgId, sn);
		
	}

	@Override
	public List<ISysOrg> getOrgByOrgSupId(Long orgSupId) {
		// TODO Auto-generated method stub
		return sysOrgDao.getOrgByOrgSupId(orgSupId);
	}
	
	@Override
	public List<ISysOrg> getOrgByOrgSupId(Long orgSupId,Long compId) {
		return sysOrgDao.getOrgByOrgSupId(orgSupId, compId);
	}
	
	public ISysUser getSysUserByOrgIdAndAccount(Long orgId,String shortAccount){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orgId", orgId);
		params.put("shortAccount", shortAccount);
		List<ISysUser> sysUserList = sysUserDao.getBySqlKey("getSysUserByOrgIdAndAccount", params);
		if(sysUserList.size()>0)
			return sysUserList.get(0);
		return null;
	}
	public ISysUser getSysUserByOrgSnAndAccount(Long orgSn,String shortAccount){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orgSn", orgSn);
		params.put("shortAccount", shortAccount);
		List<ISysUser> sysUserList = sysUserDao.getBySqlKey("getSysUserByOrgSnAndAccount", params);
		if(sysUserList.size()>0)
			return sysUserList.get(0);
		return null;
	}
	////ht:raise add b
	@Override
	public List<ISysUser> getByCompAndRoles(Long compId,List<Long> roleIds){
		return sysUserDao.getByCompAndRoles(compId, roleIds);
	}
	@Override
	public List<ISysUser> getCompDefaultUser(Long compId){
		String enterpriseAdminRoleAlias=systemproperties.getProperty("enterpriseAdminRoleAlias");
		ISysRole role = sysRoleDao.getByAlias(enterpriseAdminRoleAlias);
		if(role==null){
			return new ArrayList<ISysUser>();
		}
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(role.getRoleId());
		List<ISysUser> sysUsers =  sysUserDao.getByCompAndRoles(compId, roleIds);
		return sysUsers;
	}
	////ht:raise add e

	@Override
	public ISysOrg getOrgByOrgSn(Long orgSn) {
		List<ISysOrg> sysOrgList = this.sysOrgDao.getBySqlKey("getOrgBySn", orgSn);
		if(sysOrgList.size()>0){
			return sysOrgList.get(0);
		}
		return null;
	}

	@Override
	public SysOrg getOrgBySn(Long sn){
		List<ISysOrg> sysOrgList = this.sysOrgDao.getBySqlKey("getOrgBySn", sn);
		if(sysOrgList.size()>0){
			return (SysOrg)sysOrgList.get(0);
		}
		return null;
	}
	
}
