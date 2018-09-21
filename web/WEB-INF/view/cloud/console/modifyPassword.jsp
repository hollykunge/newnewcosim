<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人主页</title>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@	include file="/commons/include/get.jsp"%>
<!-- *****************************@自动弹出样式************************** -->
<style type="text/css">
/*
 * user auto tips css
 */
.recipients-tips {
	font-family: "宋体",Tahoma, Arial;
	position: absolute;
	background: #ffffff;
	z-index: 2147483647;
	padding: 10px;
	border: 2px solid #594c6d;
	display: none;
	overflow: auto;
	max-height: 265px;
}

.recipients-tips li a {
	font-size: 14px;
	display: block;
	padding: 5px;
	font-weight: bold;
	cursor: pointer;
	font-family: "宋体",Tahoma, Arial;
	padding-top:10px;
	height:27px;
	width:200px;
}

.autoSelected {
	background: #2a5caa;
	color: #FFFFFF;
	font-family: "宋体",Tahoma, Arial;
}

.panel-page {
	border: 1px #8dc2e3 solid;
	border-top: none
	height: 28px;
	width: 598px;
	margin-top: -5px;
	background: #dff5fd;
	/*background: url(../images/tool_bg.jpg) repeat-x;*/
}
</style>

</head>
<body>
<div id="all">
	<%@include file="/commons/cloud/top_console.jsp"%>
	<!-- 页面主体  开始 -->	
	<div id="manager">
			<%@include file="/commons/cloud/console_left.jsp"%>
		<div id="manager_right">
			<iframe src="${ctx}/platform/system/sysUser/modifyPwdView.ht?userId=${user.userId}" style="width:865px;height:600px;border:0"></iframe>
		</div>
		<div class="clear"></div>
	</div>
	<!-- 页面主体  结束 -->
</div>

	<!-- 底部版权区  开始 -->
	<%@ include file="/commons/cloud/foot.jsp"%>
	<!-- 底部版权区  结束 -->
</body>
</html>
