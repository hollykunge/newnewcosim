package com.hotent.platform.service.ldap;

import java.util.Date;
import java.util.List;

import com.hotent.platform.auth.ISysUser;


public interface SysUserSyncServiceMBean {
	
	public Date getLastSyncTime();
	
	public Long getLastSyncTakeTime() ;

	public List<ISysUser> getNewFromLdapUserList();

	public List<ISysUser> getDeleteLocalUserList() ;

	public List<ISysUser> getUpdateLocalUserList();
	
	void reset();
}
