
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>行业动态</title>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@include file="/commons/include/get.jsp"%>

<link href="${ctx}/styles/cloud/main.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/cloud/my_tabs.css" rel="stylesheet"
	type="text/css" />
</head>

<body>
	<!-- 顶部浮动层  开始 -->
	<%@include file="/commons/cloud/top.jsp"%>

	<!-- 主导航  结束 -->
	<!-- 页面主体  开始 -->
	<div id="main">
		<applet code="RadialGraphViewApplet"
			codebase="${ctx }/applet/showIndustry" ARCHIVE="RadialGraphView.jar"
			width=1024 height=768>
			<param name="url" value="data/${industry }.xml" />
			
			<param name="facet" value="name" />
	
		</applet>
	</div>
		<!-- 页面主体  结束 -->

		<!-- 底部版权区  开始 -->
		<%@ include file="/commons/cloud/foot.jsp"%>
		<!-- 底部版权区  结束 -->
</body>
</html>
