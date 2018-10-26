package com.casic.cloud.controller.CA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
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
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.config.businessDevchase.BusinessDevchase;
import com.casic.cloud.model.config.info.Info;
import com.casic.cloud.model.console.businessAreaGroup.BusinessAreaGroup;
import com.casic.cloud.model.reg.register.Register;
import com.casic.cloud.model.system.news.News;
import com.casic.cloud.pub.util.PasswordUtil;
import com.casic.cloud.service.config.business.BusinessChanceService;
import com.casic.cloud.service.config.businessDevchase.BusinessDevchaseService;
import com.casic.cloud.service.config.capability.CapabilityService;
import com.casic.cloud.service.config.info.InfoService;
import com.casic.cloud.service.console.businessAreaGroup.BusinessAreaGroupService;
import com.casic.cloud.service.mail.CloudMailService;
import com.casic.cloud.service.reg.register.RegisterService;
import com.casic.cloud.service.system.auth.CloudSystemAuthenticationManager;
import com.casic.cloud.service.system.news.NewsService;
import com.hotent.core.annotion.Action;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.DictionaryService;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;

@Controller
@RequestMapping("/CA")
public class CAController extends BaseController {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager = null;
	@Resource
	private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();
	@Resource
	private NewsService newsService;
	@Resource
	private InfoService infoService;
	@Resource
	private BusinessDevchaseService businessDevchaseService;
	@Resource(name = "systemproperties")
	private Properties systemproperties;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private BusinessChanceService businessChanceService;
	@Resource
	private CapabilityService capabilityService;
	@Resource
	private CloudMailService cloudMailService;
	@Resource
	private SysOrgInfoService sysOrgInfoService;
	@Resource
	private BusinessAreaGroupService businessAreaGroupService;
	@Resource
	private CloudSystemAuthenticationManager cloudSystemAuthenticationManager;
	@Resource
	private RegisterService registerService;

	private String rememberPrivateKey = "cloudPrivateKey";

	@RequestMapping("CAPost")
	public ModelAndView CAPost(HttpServletRequest request,
							   HttpServletResponse response) throws Exception {
		// 用户身份证
		String userid = "";
		String dnname = ((HttpServletRequest) request).getHeader("dnname");
		String url = RequestUtil.getString(request, "url");

		// 模拟CA

		// dnname==null 则没有通过CA来进行登录
		if (dnname == null) {
			// System.out.println("normal");
			url = request.getContextPath() + "/loginCloud.ht";
		} else {
			// 通过CA进行登录
			System.out.println("CA");
			dnname = new String(dnname.getBytes("iso8859-1"), "gbk");
			String dnsplit[] = dnname.trim().split(",", 0);
			String cn, dc, t = null;
			for (String val : dnsplit) {
				val = val.trim();
				// cn dc t
				if (val.indexOf("t=") > -1 || val.indexOf("T=") > -1) {
					t = val.substring(2, val.length());
				}
			}
			userid = t;
			if (userid != null && userid != "") {
				// 进行验证
				// 设置到session中

//				ISysUser user = sysUserService.getByAccount("620001_" + userid);
				ISysUser user = sysUserService.getByMail(userid);//将用户信息表中的email信息用作身份证信息
				if (user != null) {


//				indexController.loginCloudPost(user);
					// 设置到session中
					UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
							user.getAccount(), user.getPassword());
					authRequest.setDetails(new WebAuthenticationDetails(request));
					SecurityContext securityContext = SecurityContextHolder
							.getContext();
					Authentication auth = authenticationManager
							.authenticate(authRequest);
					securityContext.setAuthentication(auth);
					request.getSession().setAttribute(WebAttributes.LAST_USERNAME,
							user.getAccount());

					sessionStrategy.onAuthentication(auth, request, response);
					// 从session中删除组织数据。
					ContextUtil.removeCurrentOrg(request, response);

					// 将用户设置到Session中
					ContextUtil.setCurrentUserAccount(user.getAccount());

					// 删除cookie。
					CookieUitl.delCookie("loginAction", request, response);

					writeRememberMeCookie(request, response, user.getAccount(),
							user.getPassword());

					CookieUitl.addCookie("loginAction", "cloud", request, response);

					// 重定向到我的页面中
//            response.sendRedirect(request.getContextPath() + "/cloud/console/home.ht");
					response.sendRedirect(request.getContextPath() + "/cloud/console/outline.ht?target=/datadriver/personaltask/mydashboard.ht");
					return null;
				}
				else {
					url = request.getContextPath() + "/loginCloud.ht";
				}
			} else {
				url = request.getContextPath() + "/loginCloud.ht";
			}
		}
		// 重定向到我的页面中
		response.sendRedirect(url);
		return null;
	}

	@RequestMapping("casLogin")
	@Action(description = "企业登录")
	public ModelAndView casLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String url = (String) request.getSession().getAttribute("casUrl");
//		url="http://localhost:9090/tianzhi/cloud/console/home.ht";
		String password = (String) request.getAttribute("password");
//		String orgSn = (String) request.getAttribute("e_id");
		String shortAccount = (String) request.getAttribute("shortAccount");
		String enPassword = EncryptUtil.encryptSha256(password);
//		String account = (String) request.getAttribute("account");
		QueryFilter queryFilter = new QueryFilter(request);
		queryFilter.addFilter("shortAccount", shortAccount);
		List<ISysUser> users = sysUserService.getAll(queryFilter);
		if(users.size()>0){
			ISysUser user = users.get(0);
			ISysOrg sysOrg = sysOrgService.getOrgBySn(user.getOrgSn());
			// 设置到session中
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					user.getAccount(), user.getPassword());
			authRequest.setDetails(new WebAuthenticationDetails(request));
			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication auth = authenticationManager.authenticate(authRequest);
			securityContext.setAuthentication(auth);
			request.getSession().setAttribute(WebAttributes.LAST_USERNAME,
					user.getAccount());

			sessionStrategy.onAuthentication(auth, request, response);
			// 从session中删除组织数据。
			ContextUtil.removeCurrentOrg(request, response);

			// 将用户设置到Session中
			ContextUtil.setCurrentUserAccount(user.getAccount());

			// 删除cookie。
			CookieUitl.delCookie("loginAction", request, response);

			writeRememberMeCookie(request, response, user.getAccount(),
					user.getPassword());

			CookieUitl.addCookie("loginAction", "cloud", request, response);
			
		}
		
		// 重定向到请求的页面
		response.sendRedirect(url);
		return null;
	}

	@RequestMapping("changeUser")
	public ModelAndView changeUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 用户身份证
		String org = RequestUtil.getString(request, "org");
		String shortAccount = RequestUtil.getString(request, "shortAccount");
		String url = RequestUtil.getString(request, "url");
		String userid = shortAccount;
		// 通过CA进行登录
		if (userid != null && userid != "") {
			// 进行验证
			// 设置到session中
			ISysUser user = sysUserService.getByAccount(org + "_" + userid);
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					user.getAccount(), user.getPassword());
			authRequest.setDetails(new WebAuthenticationDetails(request));
			SecurityContext securityContext = SecurityContextHolder
					.getContext();
			Authentication auth = authenticationManager
					.authenticate(authRequest);
			securityContext.setAuthentication(auth);
			request.getSession().setAttribute(WebAttributes.LAST_USERNAME,
					org + userid);
			sessionStrategy.onAuthentication(auth, request, response);
			// 从session中删除组织数据。
			ContextUtil.removeCurrentOrg(request, response);
			// 将用户设置到Session中
			ContextUtil.setCurrentUserAccount(org + userid);
			// 删除cookie。
			CookieUitl.delCookie("loginAction", request, response);
			writeRememberMeCookie(request, response, org + userid,
					user.getPassword());
			CookieUitl.addCookie("loginAction", "cloud", request, response);
		} else {
			url = request.getContextPath() + "/loginCloud.ht";
		}
		// 重定向到我的页面中
		response.sendRedirect(url);
		return null;
	}

	/**
	 * 加上用户登录的remember me 的cookie
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @param enPassword
	 */
	private void writeRememberMeCookie(HttpServletRequest request,
			HttpServletResponse response, String username, String enPassword) {
		String rememberMe = request
				.getParameter("_spring_security_remember_me");
		if ("1".equals(rememberMe)) {
			long tokenValiditySeconds = 1209600; // 14 days
			long tokenExpiryTime = System.currentTimeMillis()
					+ (tokenValiditySeconds * 1000);
			String signatureValue = DigestUtils.md5Hex(username + ":"
					+ tokenExpiryTime + ":" + enPassword + ":"
					+ rememberPrivateKey);
			String tokenValue = username + ":" + tokenExpiryTime + ":"
					+ signatureValue;
			String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue
					.getBytes()));
			Cookie cookie = new Cookie(
					TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY,
					tokenValueBase64);
			cookie.setMaxAge(60 * 60 * 24 * 365 * 5); // 5 years
			cookie.setPath(org.springframework.util.StringUtils
					.hasLength(request.getContextPath()) ? request
					.getContextPath() : "/");
			response.addCookie(cookie);
		}
	}
}
