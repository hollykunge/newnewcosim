package com.casic.cloud.web.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;

import com.hotent.core.web.security.DirectUrlResolver;
import com.hotent.core.web.security.MultipleAuthenticationLoginEntry;

/**
 * 多用户入口实现
 * by cjj
 */
public class MultipleAuthenticationLoginEntry2 extends MultipleAuthenticationLoginEntry {

    private String defaultLoginUrl="/cloud/index.ht";  
    private List<DirectUrlResolver> directUrlResolvers = new ArrayList<DirectUrlResolver>();  
  
    /**
     * 根据输入路径与配置项得到跳转路径分别跳到不同登录页面
     */
    @Override  
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {  
        
    	String ctxPath = request.getContextPath();
    	for (DirectUrlResolver directUrlResolver : directUrlResolvers) {  
            if (directUrlResolver.support(request)) {  
                String loginUrl = directUrlResolver.directUrl();  
                response.sendRedirect(ctxPath+ loginUrl);  
                return;  
            }  
        }  
    	//将原本跳转的页面放入session
    	request.getSession().setAttribute("casUrl", request.getRequestURI());
        response.sendRedirect(ctxPath+defaultLoginUrl);  
    }  
  
    public void setDefaultLoginUrl(String defaultLoginUrl) {  
    	
        this.defaultLoginUrl = defaultLoginUrl;  
    }  
  
    public void setDirectUrlResolvers(List<DirectUrlResolver> directUrlResolvers) {  
        this.directUrlResolvers = directUrlResolvers;  
    }  

}
