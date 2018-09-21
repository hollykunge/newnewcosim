package com.casic.cloud.casSessionFilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fr.report.core.A.r;

public class CasLogoutFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	
		//放注销信息
		HttpSession session = ((HttpServletRequest)request).getSession();
//		session.setAttribute("logout", "logout");
		session.invalidate();
		
		String url = "http://127.0.0.1:8080/casServer/logout";

		response.getWriter().write(
				"<script language='javascript'>top.location.href='" + url
						+ "'</script>");

		response.getWriter().flush();
		if (!response.isCommitted()) {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
