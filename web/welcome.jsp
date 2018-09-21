<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>跳转到首页</title>
<link href="${ctx}/css/main.css" rel="stylesheet" type="text/css" />
</head>
<%
	
	response.sendRedirect(request.getContextPath()+ "/loginCloud.ht");
 %>
</html>
