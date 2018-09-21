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
		
		function submitForm() {
			var loginform=document.forms["loginform"];
			loginform.submit();
		} 
</script>
</head>
<body>
<div id="all">


		
	<!--登录框-->
<div class="liushi"> 	
<!-- <div class="gaishe"> 	 -->

		<form action="${ctx}/CA/CANormalLoginPost.ht" id="loginform" method="post">
			<div>
			</div>
				<input name="shortAccount" type="text" value="" style="margin-top: 322px; margin-left: 355px;height: 28px;width: 345px;font-size: 18px;border: 0;padding-left: 10px" />
			<div>
				<input name="password" type="password" value=""  style="margin-top: 50px;margin-left: 355px;height: 28px;width: 345px; font-size: 18px;border: 0;padding-left: 10px"/>
			</div>
			<input type="hidden" name="orgSn" id="orgSn" value="620001" /> 
			<div id="subBtn" style="margin-top: 55px;margin-left: 435px;cursor: pointer;height: 33px;width: 200px" onclick="submitForm() ">
			</div>
<%-- 		<div  class="userform userform_all" style="height:225px;">
		
			<c:if test="${not empty errMsg}">  	
  				<span class="Validform_checktip Validform_wrong">${errMsg}</span>
  			</c:if>
			<table>
				<tr>
					<td align="left"><font>单位：</font></td>
					
					<td>
					<select name="orgSn" id="orgSn"><option value="620001">二院</option><option value="620002">三院 </option><option value="620003">061基地 </option> </select>
					</td>
				</tr>
				<tr>
					<td align="left"><font>用户名：</font></td>
					<td><input name="shortAccount" type="text" class="shortInput" id="shortAccount" value="" /></td>
				</tr>
				<tr>
					<td align="left"><font>密码：</font></td>
					<td><input name="password" type="password" class="shortInput" id="password" value="" /></td>
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
			</div> --%>
			</form>
	</div>
		
	</div>
		<div class="clear"></div>
	</div>
	<!--登录框结束-->
	</div>

<!-- 底部版权区  开始 -->
<%-- <div style="position:absolute;bottom:0px;width:100%;">
	<%@include file="/commons/cloud/foot.jsp"%>
</div> --%>
<!-- 底部版权区  结束 -->
</body>
</html>
