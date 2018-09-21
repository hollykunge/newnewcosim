package com.casic.cloud.casSessionFilter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.system.SysUserService;
import com.ibm.db2.jcc.am.lo;
import com.ibm.db2.jcc.t4.ac;

public class CasClientFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		Object se = session.getAttribute("_const_cas_assertion_");
		String account = "", shortAccount = "", password = "", e_id = "";
		
		if (se instanceof Assertion
				&& session.getAttribute("filterShortAccount") == null) {
			Assertion assertion = (Assertion) se;
			Map<String, Object> attrs = assertion.getPrincipal()
					.getAttributes();
			e_id = attrs.get("e_id").toString();
			account = attrs.get("account").toString();
			shortAccount = attrs.get("username").toString();
			password = attrs.get("password").toString();
			// String enPassword = EncryptUtil.encryptSha256(password);

			account = URLDecoder.decode(account, "UTF-8");
			shortAccount = URLDecoder.decode(shortAccount, "UTF-8");
			session.setAttribute("filterShortAccount", shortAccount);

//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html;charset=UTF-8");
//			response.setCharacterEncoding("UTF-8");
//			// 得到服务器地址
//			String deal = request.getScheme();
//			String serverName = request.getServerName();
//			int port = request.getServerPort();
//			String contextPath = ((HttpServletRequest) request)
//					.getContextPath();
//			String path = deal + "://" + serverName + ":" + port + contextPath
//					+ "/";
//			request.setAttribute("url", ((HttpServletRequest)request).getRequestURI());
//			request.setAttribute("e_id", e_id);
//			request.setAttribute("account", e_id+"_"+account);
			request.setAttribute("shortAccount", shortAccount);
			request.setAttribute("password", password);
			request.getRequestDispatcher("/CA/casLogin.ht").forward(request, response);
			
//			// path+="loginCloudPost.ht?orgSn="+e_id+"&shortAccount="+shortAccount+"&password="+password;
//			path += "loginCloudPost.ht";
//			request.setAttribute("shortAccount", shortAccount);
//			request.setAttribute("orgSn", e_id);
//			request.setAttribute("password", password);
//			response.getWriter().write("<html>");
//
//			response.getWriter().write(
//					"<form id='form1' method='post' action='" + path + "'>");
//			response.getWriter().write(
//					"<input name='orgSn' type='hidden' class='text' id='orgSn' value='"
//							+ e_id + "' />");
//			response.getWriter()
//					.write("<input name='shortAccount' type='hidden' class='text' id='shortAccount' value='"
//							+ shortAccount + "' />");
//			response.getWriter().write(
//					"<input name='password' type='hidden' class='text' id='password' value='"
//							+ password + "' />");
//			response.getWriter().write("</form>");
//			response.getWriter().write("</html>");
//			response.getWriter().write("<script type='text/javascript'>");
//			response.getWriter().write(
//					"document.getElementById('form1').submit();");
//			response.getWriter().write("</script>");
//			response.getWriter().flush();

		}

		if (!response.isCommitted()) {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
