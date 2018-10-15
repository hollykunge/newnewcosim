package com.hotent.platform.controller.mobile;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.ldap.dao.LdapUserDao;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysUserService;


@Controller
@RequestMapping("/mobileLogin.ht")
public class MobileLoginController extends BaseController {
	
	@Resource
	SysUserService sysUserService;
	
	@Resource
	private LdapUserDao ldapUserDao;
	
	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager = null;
	
	@Resource
	private SessionAuthenticationStrategy sessionStrategy=new NullAuthenticatedSessionStrategy();
	
	@RequestMapping("*")
	@ResponseBody
	public Object login(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "username") String username,@RequestParam(value = "password") String password){
		
		Map responseMap = new HashMap();
        
		String validCodeEnabled=configproperties.getProperty("validCodeEnabled");

		boolean error=false;
		try{
//			if(validCodeEnabled!=null && "true".equals(validCodeEnabled)){
//				String validCode=(String)request.getSession().getAttribute(ValidCode.SessionName_Randcode);
//				String code=request.getParameter("validCode");
//				if(validCode==null || StringUtils.isEmpty(code) || !validCode.equals(code)){
//					request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "验证码不正确！");
//					error=true;
//					return responseMap();
//				}
//			}
//			if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
//				request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "用户名密码为空!");
//				error=true;
//				return;	
//			}
			
			ISysUser sysUser=sysUserService.getByAccount(username);
			String encrptPassword=EncryptUtil.encryptSha256(password);
			//ad 用户登录
			if(sysUser!=null&&sysUser.getFromType()==1){
				boolean authenticated= ldapUserAuthentication(username,password);
				if(!authenticated){
					error=true;
					responseMap.put("success", false);
					responseMap.put("msg", "用户名密码输入错误!");
					return responseMap;
				}else{
					if(!encrptPassword.equals(sysUser.getPassword())){
						sysUserService.updPwd(sysUser.getUserId(),password);
					}
				}
			}
			//通过sys_user 验证。
			else{
				if(sysUser==null || !encrptPassword.equals(sysUser.getPassword())){
					error=true;
					responseMap.put("success", false);
					responseMap.put("msg", "用户名密码输入错误!");
					return responseMap;
				}
			}
			
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
			authRequest.setDetails(new WebAuthenticationDetails(request));
			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication auth = authenticationManager.authenticate(authRequest);

			securityContext.setAuthentication(auth);
			
			responseMap.put("success", true);
			responseMap.put("msg", "用户登录成功!");
			responseMap.put("username", sysUser.getFullname());
			
			CookieUitl.addCookie("loginAction", "mobile", request, response);
			

			sessionStrategy.onAuthentication(auth, request, response);
//			
//			writeRememberMeCookie(request,response,username,encrptPassword);
		}catch (LockedException e) {
			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, username+":用户被锁定!");
			error=true;
		}catch (DisabledException e) {
			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, username+":用户被禁用!");
			error=true;
		}catch (AccountExpiredException e) {
			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, username+":用户已过期!");
			error=true;
		}finally{
			if(error==true){
//				javax.servlet.http.HttpSession session=request.getSession();
//				Integer tryCount=(Integer) session.getAttribute(TRY_MAX_COUNT);
//		    	if(tryCount==null){
//		    		session.setAttribute(TRY_MAX_COUNT, 1);
//		    	}else{
//		    		if(tryCount>maxTryCount-1){
//		    			//锁定账户 TODO
//		    			//session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new MaxTryLoginException("超过最大登录尝试次数"+maxTryCount+",用户已被锁定"));
//		    		}
//		    		session.setAttribute("tryMaxCount", tryCount+1);
//		    	}
		    }
		}
        
        return responseMap;
	}
	
	private boolean ldapUserAuthentication(String userId,String password){
		return ldapUserDao.authenticate(userId, password);
	}
	
}
