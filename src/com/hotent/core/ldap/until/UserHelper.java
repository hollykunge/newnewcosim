package com.hotent.core.ldap.until;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.ldap.dao.LdapUserDao;
import com.hotent.core.ldap.model.LdapUser;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.IAuthenticate;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;

/**
 * SysUser和LdapUser的用户帮助类
 * 
 * @author raise
 * 
 */
public class UserHelper {
	public static OrgHelper orgHelper;

	/**
	 * 从用户列表中，根据数据来源取得用户列表
	 * 
	 * @param sysUsers
	 * @param type
	 * @return
	 */
	public static List<ISysUser> getSysUsersByFromType(List<ISysUser> sysUsers, short type) {
		List<ISysUser> users = new ArrayList<ISysUser>();
		for (ISysUser sysUser : sysUsers) {
			if (sysUser.getFromType() == type) {
				users.add(sysUser);
			}
		}
		return users;
	}

	/**
	 * 通过账号获取用户
	 * 
	 * @param sysUsers
	 * @param account
	 * @return
	 */
	public static List<ISysUser> getSysUserByAccount(List<ISysUser> sysUsers, String account) {
		List<ISysUser> users = new ArrayList<ISysUser>();
		for (ISysUser sysUser : sysUsers) {
			if (account.equals(sysUser.getAccount())) {
				users.add(sysUser);
				break;
			}
		}
		return users;
	}

	/**
	 * 转换LdapUser到SysUser
	 * 
	 * @param ldapUsers
	 * @return
	 */
	public static List<ISysUser> covertLdapToSysUsrs(List<LdapUser> ldapUsers, List<ISysOrg> totalSysOrgs) {
		List<ISysUser> sysUsers = new ArrayList<ISysUser>();
		for (LdapUser ldapUser : ldapUsers) {
			if (orgHelper.getPathByDn(ldapUser.getDistinguishedName()).equals("")) {
				continue;
			}
			ISysUser sysUser = covertLdapToSysUsr(ldapUser, totalSysOrgs);
			sysUsers.add(sysUser);
		}
		return sysUsers;
	}

	/**
	 * 将一个LdapUser转为SysUser
	 * 
	 * @param ldapUser
	 * @return
	 */
	public static ISysUser covertLdapToSysUsr(LdapUser ldapUser, List<ISysOrg> totalSysOrgs) {
		IAuthenticate iAuthenticate = (IAuthenticate) AppUtil.getBean(IAuthenticate.class);
		ISysUser sysUser = iAuthenticate.getNewSysUser();
		sysUser.setAccount(ldapUser.getsAMAccountName());
		sysUser.setEmail(ldapUser.getMail());
		String givenName = ldapUser.getGivenName();
		String sn = ldapUser.getSn();
		String fullname = (sn == null ? "" : sn) + (givenName == null ? "" : givenName);
		fullname = fullname.equals("") ? null : fullname;
		sysUser.setFullname(fullname);
		sysUser.setPhone(ldapUser.getHomePhone());
		sysUser.setMobile(ldapUser.getTelephoneNumber());
		sysUser.setStatus((short) (ldapUser.isAccountDisable() ? SystemConst.STATUS_NO : SystemConst.STATUS_OK));
		sysUser.setIsExpired(SystemConst.UN_EXPIRED);
		sysUser.setIsLock(SystemConst.LOCKED);
		sysUser.setPassword("");
		ISysOrg sysOrg = OrgHelper.getOrgByDn(ldapUser.getDistinguishedName(), totalSysOrgs);
		if (sysOrg == null) {
			throw new InternalError("内部数据错误！");
		}
		sysUser.setUserId(UniqueIdUtil.genId());
		sysUser.setUserOrgId(sysOrg.getOrgId());
		sysUser.setOrgName(sysOrg.getOrgName());
		sysUser.setFromType(SystemConst.FROMTYPE_AD);
		return sysUser;
	}

	/**
	 * 将一个LdapUser转为SysUser,转换后的SysUser未设置组织属性
	 * 
	 * @param ldapUser
	 * @return
	 */
	public static ISysUser covertLdapToSysUsr(LdapUser ldapUser) {
		IAuthenticate iAuthenticate = (IAuthenticate) AppUtil.getBean(IAuthenticate.class);
		ISysUser sysUser = iAuthenticate.getNewSysUser();
		sysUser.setAccount(ldapUser.getsAMAccountName());
		sysUser.setEmail(ldapUser.getMail());
		String givenName = ldapUser.getGivenName();
		String sn = ldapUser.getSn();
		String fullname = (sn == null ? "" : sn) + (givenName == null ? "" : givenName);
		fullname = fullname.equals("") ? null : fullname;
		sysUser.setFullname(fullname);
		sysUser.setPhone(ldapUser.getHomePhone());
		sysUser.setMobile(ldapUser.getTelephoneNumber());
		sysUser.setStatus((short) (ldapUser.isAccountDisable() ? SystemConst.STATUS_NO : SystemConst.STATUS_OK));
		sysUser.setIsExpired(SystemConst.UN_EXPIRED);
		sysUser.setIsLock(SystemConst.LOCKED);
		sysUser.setUserId(UniqueIdUtil.genId());
		sysUser.setFromType(SystemConst.FROMTYPE_AD);
		return sysUser;
	}

	/**
	 * 取得组织单元以ID和名字组成的路径（如:/hotent/develpment)组成的Map的列表。<br/>
	 * key=id，value=组织ID；<br/>
	 * key=path，value=名字组成的路径
	 * 
	 * @return
	 */
	public static List<Map<String, Object>> getOrgsNamePathsMap(List<ISysOrg> orgs, List<ISysOrg> searchOrgs) {
		if (searchOrgs == null) {
			searchOrgs = orgs;
		}
		List<Map<String, Object>> namePahts = new ArrayList<Map<String, Object>>();
		for (ISysOrg org : orgs) {
			String namePath = "";
			String[] ids = org.getPath().split("\\.");
			for (int i = 1; i < ids.length; i++) {
				String id = ids[i];
				if (!StringUtil.isEmpty(id)) {
					ISysOrg org2 = getSysOrgById(searchOrgs, Long.parseLong(id));
					if (org2 == null) {
						throw new InternalError("内部数据错误！");
					}
					namePath += "/" + org2.getOrgName();

				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", org.getOrgId());
			map.put("path", namePath);
			namePahts.add(map);
		}
		return namePahts;
	}

	/**
	 * 根据id，从SysOrg的List中取得相应的SysOrg。
	 * 
	 * @param orgs
	 * @param id
	 * @return
	 */
	public static ISysOrg getSysOrgById(List<ISysOrg> orgs, Long id) {
		for (ISysOrg org : orgs) {
			if (org.getOrgId().longValue() == id.longValue()) {
				return org;
			}
		}
		return null;
	}

	/**
	 * 忽略组织相关的信息，判断一个SysUser与LdapUser的信息是否相等。
	 * 
	 * @param sysUser
	 * @param ldapUser
	 * @return
	 */
	public static boolean isSysLdapUsrEqualIngoreOrg(ISysUser sysUser, LdapUser ldapUser) {
		if (sysUser == null || ldapUser == null) {
			return false;
		}
		String givenName = ldapUser.getGivenName();
		String sn = ldapUser.getSn();
		String fullname = (sn == null ? "" : sn) + (givenName == null ? "" : givenName);
		fullname = fullname.equals("") ? null : fullname;
		return new EqualsBuilder().append(sysUser.getAccount(), ldapUser.getsAMAccountName()).append(sysUser.getEmail(), ldapUser.getMail())
				.append(sysUser.getFullname(), fullname).append(sysUser.getPhone(), ldapUser.getHomePhone()).append(sysUser.getMobile(), ldapUser.getTelephoneNumber())
				.append(sysUser.getStatus().shortValue(), (short) (ldapUser.isAccountDisable() ? 0 : 1)).isEquals();
	}

	public static boolean isSysLdapUsrEqual(ISysUser sysUser, LdapUser ldapUser, List<ISysOrg> totalSysOrgs) {
		if (sysUser == null || ldapUser == null) {
			return false;
		}
		ISysOrg sysOrg = orgHelper.getOrgByDn(ldapUser.getDistinguishedName(), totalSysOrgs);
		if (sysOrg == null) {
			throw new InternalError("内部数据错误！");
		}
		String givenName = ldapUser.getGivenName();
		String sn = ldapUser.getSn();
		String fullname = (sn == null ? "" : sn) + (givenName == null ? "" : givenName);
		fullname = fullname.equals("") ? null : fullname;
		return new EqualsBuilder().append(sysUser.getAccount(), ldapUser.getsAMAccountName()).append(sysUser.getEmail(), ldapUser.getMail())
				.append(sysUser.getFullname(), fullname).append(sysUser.getPhone(), ldapUser.getHomePhone())
				.append(sysUser.getStatus().shortValue(), ldapUser.isAccountDisable() ? SystemConst.STATUS_NO.shortValue() : SystemConst.STATUS_OK.shortValue())
				.append(sysUser.getUserOrgId(), sysOrg.getOrgId())
				.append(sysUser.getOrgName(), sysOrg.getOrgName())
				.isEquals();
	}

	/**
	 * 判断SysUser是否存在于List<LdapUser>
	 * 
	 * @param sysUser
	 * @param ldapUsers
	 * @return
	 */
	public static boolean isContains(ISysUser sysUser, List<LdapUser> ldapUsers) {
		for (LdapUser ldapUser : ldapUsers) {
			if (sysUser.getAccount().equals(ldapUser.getsAMAccountName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断SysUser是否存在于List<LdapUser>
	 * 
	 * @param sysUser
	 * @param ldapUsers
	 * @return
	 */
	public static LdapUser getEqualUserByAccount(ISysUser sysUser, List<LdapUser> ldapUsers) {

		for (LdapUser ldapUser : ldapUsers) {
			if (sysUser.getAccount().equals(ldapUser.getsAMAccountName())) {
				return ldapUser;
			}
		}
		return null;

	}

	/**
	 * 判断LdapUser是否存在于List<ISysUser>
	 * 
	 * @param ldapUser
	 * @param sysUsers
	 * @return
	 */
	public static boolean isContains(LdapUser ldapUser, List<ISysUser> sysUsers) {
		for (ISysUser sysUser : sysUsers) {
			if (ldapUser.getsAMAccountName().equals(sysUser.getAccount())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 取得属于ldapUsers而不属于sysUsers的用户
	 * 
	 * @param ldapUsers
	 * @param sysUsers
	 * @return
	 */
	public static List<LdapUser> execptLdapSysUsers(List<LdapUser> ldapUsers, List<ISysUser> sysUsers) {
		List<LdapUser> users = new ArrayList<LdapUser>();
		for (LdapUser ldapUser : ldapUsers) {
			if (!isContains(ldapUser, sysUsers)) {
				users.add(ldapUser);
			}
		}
		return users;
	}

	/**
	 * 取得属于sysUsers而不属于ldapUsers的用户
	 * 
	 * @param sysUsers
	 * @param ldapUsers
	 * @return
	 */
	public static List<ISysUser> execptSysLdapUsers(List<ISysUser> sysUsers, List<LdapUser> ldapUsers) {
		List<ISysUser> users = new ArrayList<ISysUser>();
		for (ISysUser sysUser : sysUsers) {
			if (!isContains(sysUser, ldapUsers)) {
				users.add(sysUser);
			}
		}
		return users;
	}

	/**
	 * 取得属于sysUsers而又属于ldapUsers的用户
	 * 
	 * @param ldapUsers
	 * @param sysUsers
	 * @return
	 */
	public static List<LdapUser> intersectLdapSysUser(List<LdapUser> ldapUsers, List<ISysUser> sysUsers) {
		List<LdapUser> users = new ArrayList<LdapUser>();
		for (LdapUser ldapUser : ldapUsers) {
			if (isContains(ldapUser, sysUsers)) {
				users.add(ldapUser);
			}
		}
		return users;
	}

	/**
	 * 取得属于sysUsers而又属于ldapUsers的用户
	 * 
	 * @param sysUsers
	 * @param ldapUsers
	 * @return
	 */
	public static List<ISysUser> intersectSysLdapUser(List<ISysUser> sysUsers, List<LdapUser> ldapUsers) {
		List<ISysUser> users = new ArrayList<ISysUser>();
		for (ISysUser sysUser : sysUsers) {
			if (isContains(sysUser, ldapUsers)) {
				users.add(sysUser);
			}
		}
		return users;
	}
}
