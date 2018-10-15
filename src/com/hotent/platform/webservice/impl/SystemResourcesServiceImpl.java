package com.hotent.platform.webservice.impl;

import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.hotent.core.cache.ICache;
import com.hotent.core.util.AppUtil;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.dao.system.SubSystemDao;
import com.hotent.platform.model.system.ResourcesUrlExt;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.webservice.api.SystemResourcesService;

/**
 * 取得子系统资源。
 * @author Administrator
 *
 */
//@WebService
public class SystemResourcesServiceImpl implements SystemResourcesService {
	@Resource
	SysRoleService sysRoleService;
	

	/**
	 * 取得子系统url资源
	 * @param defaultUrl
	 * @return
	 */
	@Override
	public List<ResourcesUrlExt> loadSecurityUrl(String systemId) {
		List<ResourcesUrlExt> urlList=sysRoleService.getUrlRightMap(new Long(systemId));
		return urlList;
	}
	
	/**
	 * 取得子系统功能资源
	 * @param defaultUrl
	 * @return
	 */
	@Override
	public List<ResourcesUrlExt> loadSecurityFunction(String systemId) {
		
		List<ResourcesUrlExt>  funcList=sysRoleService.getFunctionRoleList(new Long(systemId));
		return funcList;
	}
	
	/**
	 *取得子系统角色资源
	 * @param defaultUrl
	 * @return
	 */
	@Override
	public List<ISysRole> loadSecurityRole(String systemId,String roleName ){		
		List<ISysRole> roleList=sysRoleService.loadSecurityRole(systemId, roleName);
		return roleList;
	}

	@Override
	public boolean getSystemResIsUpd(String systemId) {
		String key=SecurityUtil.SystemFlag + systemId;
		ICache iCache=(ICache)AppUtil.getBean(ICache.class);
		String flag=(String) iCache.getByKey(key);
		if(flag!=null)
			return false;
		iCache.add(key, key);
		return true;
	}
}
