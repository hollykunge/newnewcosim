<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<head>
	<title>桌面栏目管理表明细</title>
	<%@include file="/commons/include/get.jsp" %>
   	<link rel="stylesheet" href="${ctx}/js/jquery-ui-portlet/lib/themes/1.8/start/jquery-ui-1.8.5.custom.css" />
    <link rel="stylesheet" href="${ctx}/js/jquery-ui-portlet/css/jquery.portlet.css?v=1.1.3" />
    <script src="${ctx}/js/jquery-ui-portlet/lib/jquery-ui-1.8.14.custom.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/jquery-ui-portlet/script/jquery.portlet.js"></script>
    <script type="text/javascript" src="${ctx}/js/util/easyTemplate.js "></script>
   	<script type="text/javascript" src="${ctx}/js/hotent/platform/desktop/desktopManage.js" ></script>
    <script type="text/javascript">
    	$(function(){
    		var desk=new DesktopManage();
    		desk.init();
    	});
    	
    </script>    
</head>
<body>
<div class="panel-body">
	<div id="myPage" ></div>
	
</div>
</body>
</html>
