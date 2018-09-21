<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hotent.core.util.ContextUtil"%>
<%@page import="com.hotent.platform.model.system.SysOrgInfo"%>
<%
	SysOrgInfo sysOrgInfo = ContextUtil.getCurrentOrgInfoFromSession();
	if(sysOrgInfo.getState()<2){
		out.println("<script>alert('企业还未通过审核,不能开展业务');window.back();</script>");		
	}
%>