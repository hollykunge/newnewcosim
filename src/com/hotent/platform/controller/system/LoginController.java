package com.hotent.platform.controller.system;

import java.io.IOException;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.ldap.dao.LdapUserDao;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.servlet.ValidCode;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysUserService;

/**
 * 登录访问控制器，用于扩展Spring Security 缺省的登录处理器
 *
 * @author csx
 *
 */
@Controller
@RequestMapping("/login.ht")
public class LoginController extends BaseController {
	@Resource
	private SysUserService sysUserService;
	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager = null;
	@Resource
	private Properties configproperties;

	@Resource
	private LdapUserDao ldapUserDao;

	@Resource
	private SessionAuthenticationStrategy sessionStrategy=new NullAuthenticatedSessionStrategy();

//	@Resource
//	HtSecurityMetadataSource securityMetadataSource;
	//must the same as <remember-me key="bpm3PrivateKey"/> of app-security.xml
	private String rememberPrivateKey="bpm3PrivateKey";
	public final static String TRY_MAX_COUNT="tryMaxCount";
	public final static int maxTryCount=5;

	/**
	 * 登录成功跳转的路径
	 */
	private String succeedUrl="/platform/console/main.ht";

	@RequestMapping("*")
	public void login(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "username") String username,@RequestParam(value = "password") String password)
			throws IOException {
		String validCodeEnabled=configproperties.getProperty("validCodeEnabled");
		boolean error=false;
		try{
			if(validCodeEnabled!=null && "true".equals(validCodeEnabled)){
				String validCode=(String)request.getSession().getAttribute(ValidCode.SessionName_Randcode);
				String code=request.getParameter("validCode");
				if(validCode==null || StringUtils.isEmpty(code) || !validCode.equals(code)){
					request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "验证码不正确！");
					error=true;
					return;
				}
			}

			if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
				request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "用户名或密码均不能为空!");
				error=true;
				return;
			}
			////ht:raie del b
			/**
			ISysUser sysUser=sysUserService.getByAccount(username);
			 */
			////ht:raie del b
			////ht:raie add b
			//ISysUser sysUser=sysUserService.getSysUserByOrgSnAndAccount(Long.valueOf(sysOrgNo),username);
			ISysUser sysUser=sysUserService.getByAccount(username);
			////ht:raie add e

			String encrptPassword=EncryptUtil.encryptSha256(password);
			//ad 用户登录
			if(sysUser!=null&&sysUser.getFromType()==1){
				boolean authenticated= ldapUserAuthentication(username,password);
				if(!authenticated){
					request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "用户名密码输入错误!");
					error=true;
					return;
				}else{
					if(!encrptPassword.equals(sysUser.getPassword())){
						sysUserService.updPwd(sysUser.getUserId(),password);
					}
				}
			}
			//通过sys_user 验证。
			else{
				if(sysUser==null || !encrptPassword.equals(sysUser.getPassword())){
					request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "用户名密码输入错误!");
					error=true;
					return;
				}
			}

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
			authRequest.setDetails(new WebAuthenticationDetails(request));
			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication auth = authenticationManager.authenticate(authRequest);

			securityContext.setAuthentication(auth);


			request.getSession().setAttribute(WebAttributes.LAST_USERNAME, sysUser.getAccount());
			//登陆时移除管理员标识
			request.getSession().removeAttribute("isAdmin");

			sessionStrategy.onAuthentication(auth, request, response);
			//从session中删除组织数据。
			ContextUtil.removeCurrentOrg(request,response);


			//删除cookie。
			CookieUitl.delCookie("loginAction", request, response);

			writeRememberMeCookie(request,response,username,encrptPassword);
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
				javax.servlet.http.HttpSession session=request.getSession();
				Integer tryCount=(Integer) session.getAttribute(TRY_MAX_COUNT);
		    	if(tryCount==null){
		    		session.setAttribute(TRY_MAX_COUNT, 1);
		    	}else{
		    		if(tryCount>maxTryCount-1){
		    			//锁定账户 TODO
		    			//session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new MaxTryLoginException("超过最大登录尝试次数"+maxTryCount+",用户已被锁定"));
		    		}
		    		session.setAttribute("tryMaxCount", tryCount+1);
		    	}
		    }
			response.sendRedirect(request.getContextPath() + succeedUrl);
		}


	}


	/**
	 * 加上用户登录的remember me 的cookie
	 * @param request
	 * @param response
	 * @param username
	 * @param enPassword
	 */
	private void writeRememberMeCookie(HttpServletRequest request,HttpServletResponse response,String username,String enPassword){
		String rememberMe=request.getParameter("_spring_security_remember_me");
		if("1".equals(rememberMe)){
			long tokenValiditySeconds = 1209600; // 14 days
			long tokenExpiryTime = System.currentTimeMillis() + (tokenValiditySeconds * 1000);
			String signatureValue = DigestUtils.md5Hex(username + ":" + tokenExpiryTime + ":" + enPassword + ":" + rememberPrivateKey);
			String tokenValue = username + ":" + tokenExpiryTime + ":" + signatureValue;
			String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue.getBytes()));
			Cookie cookie = new Cookie(TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, tokenValueBase64);
			cookie.setMaxAge(60 * 60 * 24 * 365 * 5); // 5 years
			cookie.setPath(org.springframework.util.StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
			response.addCookie(cookie);
		}
	}



	private boolean ldapUserAuthentication(String userId,String password){
		return ldapUserDao.authenticate(userId, password);
	}

}
