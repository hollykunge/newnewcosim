<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册成功</title>
<link href="${ctx}/styles/cloud/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="all">
<%@include file="/commons/cloud/top.jsp"%>

		
	<!--登录框-->
	<form action="${ctx}/regPass.ht" method="post">
	<div class="bggraybox">
		<h2>注册成功</h2>
		<c:if test="${not empty resultMessage}">${resultMessage}</c:if>
		<c:if test="${empty resultMessage}">
		<div  class="userform userform_left" style="height:225px;">
			<form action="" method="post">
			<table>
				<tr>
					<td align="left"><img src="${ctx}/images/success.jpg" width="62" height="49" /></td>
					<td>恭喜您: 注册成功！您可以直接登录平台,请牢记以下信息<br/>
	            	企业账号 : ${sysOrg.sn}<br/>
	            	企业管理员账号 : ${sysOrg.sysUser.shortAccount}<br/>
	            	企业密码 : ${dePassword}<br/>
	            	注册信息已经发送至您的邮箱，为了保证您的账号安全，请立即
	            	<c:if test="${not empty mailLoginUrl}">
	            		<a href="${mailLoginUrl}" target="_blank">登录邮箱</a>
	            	</c:if>
	            	<c:if test="${empty mailLoginUrl}">
	            		登录邮箱
	            	</c:if>
	            	保存您的信息！</td>	
				</tr>
				<tr>
					<td align="left"></td>
					<td><a href="${ctx}/cloud/console/home.ht" class="link09">点击进入我的企业</a></td>
				</tr>
			</table>
			</form>
		</div>
		</c:if>
		<div class="userform_right">
			<p><strong>还不是用户?</strong></p>
			<p>现在免费注册成为用户，即刻共享云资源。</p>
			<p><a href="${ctx}/reg.ht" target="_blank"><img src="skins/btn_reg.jpg" /></a> <a href="${ctx}/toRegPass.ht" target="_blank"><img src="skins/btn_reg2.jpg" /></a></p><br />
			<p><img src="${ctx}/images/banner2.jpg" width="335" height="92" /></p>
		</div>
		<div class="clear"></div>
	</div>
	<!--登录框结束-->
	</div>

<!-- 底部版权区  开始 -->
<%@include file="/commons/cloud/foot.jsp"%>
<!-- 底部版权区  结束 -->
</body>
</html>
