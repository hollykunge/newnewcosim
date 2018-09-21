<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录页面</title>
<%@include file="/commons/cloud/meta.jsp"%>
<link href="${ctx}/skins/validform.css"  rel="stylesheet"/>
<script type="text/javascript">
		if(top!=this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
			  top.location='<%=request.getContextPath()%>/loginCloud.ht';
		}
</script>
</head>
<body>
<div id="all">
<%@include file="/commons/cloud/top.jsp"%>
	<!--登录框-->
	<form action="${ctx}/loginSystemPost.ht" method="post">
	<div class="bggraybox">
		<h2>登录</h2>
		<div  class="userform userform_left" style="height:225px;">
			<form action="" method="post">
			<c:if test="${not empty errMsg}">  	
  				<span class="Validform_checktip Validform_wrong">${errMsg}</span>
  			</c:if>
			<table>
				<tr>
					<td align="left"><font>企业账号：</font></td>
					<td><input name="orgSn" type="text" class="text" id="orgSn" value="" /></td>	
				</tr>
				<tr>
					<td align="left"><font>用户名：</font></td>
					<td><input name="shortAccount" type="text" class="text" id="shortAccount" value="" /></td>
				</tr>
				<tr>
					<td align="left"><font>密码：</font></td>
					<td><input name="password" type="password" class="text" id="password" value="" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
					<label style="color:#666; font-size:12px"><input name="input" type="checkbox" value="" />&nbsp;记住账户名</label>
					<label style="color:#666; font-size:12px">
					<input name="input" type="checkbox" value="" />&nbsp;自动登录</label></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input name="button" type="image" class="btn" id="button" value="登  录" src="skins/btn_login.jpg"/></td>
				</tr>
			</table>
			</form>
		</div>
		
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
<div style="position:absolute;bottom:0px;width:100%;">
<%@include file="/commons/cloud/foot.jsp"%>
</div>
<!-- 底部版权区  结束 -->
</body>
</html>
