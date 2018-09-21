package com.casic.cloud.pub.filter;

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

/**
 * 记录多租户saas=**用户
 * 
 * eg.在web.xml中设置
 * 	<filter>
 * 		<filter-name>saasFilter</filter-name>
 * 		<filter-class>com.casic.cloud.pub.filter.SAASFilter</filter-class>
 * 		<init-param>
 * 			<param-name>pages</param-name>
 * 			<param-value>index.ht</param-value>
 * 		</init-param>
 * 	</filter>
 * 	<filter-mapping>
 * 		<filter-name>saasFilter</filter-name>
 * 		<url-pattern>*.ht</url-pattern>
 * 	</filter-mapping>
 * 
 * @author zouping
 */
public class SAASFilter implements Filter {
	
	private static final String PARAM_PAGES = "pages";
	
	//需要个性化定制的页面
	private String pages = "";
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		//转换成HttpServletRequest
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		HttpSession session = request.getSession();
				
		//获取访问地址
		String url = request.getRequestURI();
		String[] p = pages.split(",");
		
		
		for(String s : p){
			if(url.indexOf(s)!=-1){//如果访问的是需要个性化的页面,自动给Url加上saas属性
				String saas = req.getParameter("saas");
				if(saas!=null){//存在saas,说明是第一次访问，用session记录saas
					session.setAttribute("saas", saas);
				}else{//给url加上saas属性
					if(session.getAttribute("saas")!=null){
						if(url.indexOf("?")!=-1){//存在?
							url += "&saas=" + session.getAttribute("saas");
						}else{//不存在?
							url += "?saas=" + session.getAttribute("saas");
						}
						
						//重新定位
						response.sendRedirect(url);
						return;
					}
				}
				continue;
			}
		}
		
		if(url.indexOf("logout")!=-1){//退出,注销session
			/*session.removeAttribute("saas");*/
		}
		chain.doFilter(req, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig filterConfig) {
		pages = filterConfig.getInitParameter(PARAM_PAGES);
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}
}
