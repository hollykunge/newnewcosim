/**
 * 
 */
package com.casic.cloud.service.system.auth;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Service;

import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserService;

/**
 * @author Administrator
 *
 */
@Service
public class CloudSystemAuthenticationManager {
	@Resource
	SysRoleService sysRoleService;
	@Resource
	SysUserService sysUserService;
	
	static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		ISysUser sysUser = sysUserService.getByAccount(auth.getName());
		List<ISysRole> roles = sysRoleService.getByUserId(sysUser.getUserId());
		for(ISysRole role : roles){
			AUTHORITIES.add(new GrantedAuthorityImpl(role.getAlias()));
		}
		UsernamePasswordAuthenticationToken authentication =  new UsernamePasswordAuthenticationToken(sysUser,
	        auth.getCredentials(), AUTHORITIES);
		return authentication;
	}
}
