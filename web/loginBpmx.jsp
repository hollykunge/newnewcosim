<%--<%@page import="org.springframework.security.authentication.AuthenticationServiceException"%>--%>
<%--<%@page import="org.springframework.security.authentication.AccountExpiredException"%>--%>
<%--<%@page import="org.springframework.security.authentication.DisabledException"%>--%>
<%--<%@page import="org.springframework.security.authentication.LockedException"%>--%>
<%--<%@page import="org.springframework.security.authentication.BadCredentialsException"%>--%>
<%--<%@page import="java.util.Enumeration"%>--%>
<%--<%@page import="com.hotent.core.util.AppUtil"%>--%>
<%--<%@page import="java.util.Properties"%>--%>
<%--<%@ page pageEncoding="UTF-8" %>--%>
<%--<%@page import="org.springframework.security.web.WebAttributes"%>--%>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<c:set var="ctx" value="${pageContext.request.contextPath}"/>--%>
<%--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">--%>
<%--<html xmlns="http://www.w3.org/1999/xhtml">--%>
<%--<head>--%>
<%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />--%>
<%--<title>后台管理--登录</title>--%>
<%--<link type="text/css" rel="stylesheet" href="${ctx}/styles/default/css/login.css" />--%>
<%--<%--%>
	<%--Properties configProperties=(Properties)AppUtil.getBean("configproperties");--%>
	<%--String validCodeEnabled=configProperties.getProperty("validCodeEnabled");--%>
<%--%>--%>
<%--<script type="text/javascript">--%>
		<%--if(top!=this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面--%>
			  <%--top.location='<%=request.getContextPath()%>/loginBpmx.jsp';--%>
		<%--}--%>
<%--</script>--%>
<%--</head>--%>
<%--<body>--%>
        <%--<div class="second_body">--%>
        	<%--<form id="form-login" action="${ctx}/login.ht" method="post">--%>
        			<%--<table width="300" border="0">--%>
                          <%--<tr>--%>
                            <%--<td width="85"><span>用户帐号：</span></td>--%>
                            <%--<td colspan="2"><input type="text" name="username" class="login" /></td>--%>
                          <%--</tr>--%>
                          <%--<tr>--%>
                            <%--<td style="letter-spacing:0.5em;">密码：</td>--%>
                            <%--<td colspan="2"><input type="password" name="password" class="login" /></td>--%>
                          <%--</tr>--%>
                          <%--<%--%>
                          <%--if(validCodeEnabled!=null && "true".equals(validCodeEnabled)){--%>
                          <%--%>--%>
                          <%--<tr>--%>
                            <%--<td>验证码：</td>--%>
                            <%--<td width="128"><input type="text" name="validCode" class="login2" /></td>--%>
                            <%--<td width="103"><img src="${ctx}/servlet/ValidCode" /></td>--%>
                          <%--</tr>--%>
                          <%--<%--%>
                          <%--}--%>
                          <%--%>--%>
                          <%--<tr>--%>
                            <%--<td>&nbsp;</td>--%>
                            <%--<td colspan="2"><input type="checkbox" name="_spring_security_remember_me" value="1"/><span>系统记住我</span></td>--%>
                          <%--</tr>--%>
                          <%--<tr align="center">--%>
                            <%--<td colspan="3"><input type="submit" value="登录" class="login_button"/><input type="button" value="重置" class="reset_botton" onclick="this.form.reset();"></td>--%>
                          <%--</tr>--%>
                        	 <%--<%--%>
							<%--Object loginError=(Object)request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);--%>
							<%----%>
							<%--if(loginError!=null ){--%>
								<%--String msg="";--%>
								<%--if(loginError instanceof BadCredentialsException){--%>
									<%--msg="密码输入错误";--%>
								<%--}--%>
								<%--else if(loginError instanceof AuthenticationServiceException){--%>
									<%--AuthenticationServiceException ex=(AuthenticationServiceException)loginError;--%>
									<%--msg=ex.getMessage();--%>
								<%--}--%>
								<%--else{--%>
									<%--msg=loginError.toString();--%>
								<%--}--%>
							<%--%>--%>
							<%--<tr>--%>
								<%--<td align="center" colspan="3">--%>
										<%--<font color="red"><%=msg %></font>--%>
								<%--</td>--%>
							<%--</tr>--%>
							<%--<%--%>
								<%--}--%>
							<%--%>--%>
                    <%--</table>--%>
				<%--</form>--%>
        <%--</div>--%>
<%--</body>--%>
<%--</html>--%>
