package com.hotent.platform.service.ldap;

import java.util.Date;
import java.util.List;
import com.hotent.platform.auth.ISysOrg;

public interface SysOrgSyncServiceMBean {
	
	public Date getLastSyncTime();
	
	public Long getLastSyncTakeTime() ;

	public List<ISysOrg> getNewFromLdapOrgList();

	public List<ISysOrg> getDeleteLocalOrgList() ;

	public List<ISysOrg> getUpdateLocalOrgList();
	
	void reset();
}
