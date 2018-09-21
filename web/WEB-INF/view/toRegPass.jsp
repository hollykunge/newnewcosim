<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>密码重置页面</title>
<%@include file="/commons/cloud/meta.jsp"%>
<script type="text/javascript">
		if(top!=this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
			  top.location='<%=request.getContextPath()%>/loginCloud.ht';
		}
		
		function butReg(){
			var code = $("#orgSn").val();
			var post = $("#shortAccount").val();
			
			if(code != '' && post != ''){
				
				$.ajax({
					type : 'post',
					dataType : 'json',
					url : '${ctx}/regPass.ht',
					data : {orgSn : code , ps : post},
					success : function(retcode){
						if(retcode != 0){
							alert("您输入的企业账号和邮箱不匹配。请查证后，重新输入！");
						}else{
							alert("恭喜您，密码重置成功，详细信息已经发送到您的邮箱中，请查阅！")
						}
					}
				});
				
			}else{
				alert("账号和邮箱不允许为空！");
			}
		}
</script>
</head>
<body>
<div id="all">
<%@include file="/commons/cloud/top.jsp"%>

		
	<!--登录框-->
	<form action="${ctx}/regPass.ht" method="post">
	<div class="bggraybox">
		<h2>重置密码</h2>
		<c:if test="${not empty errMsg}">  	
  		<span class="Validform_checktip Validform_wrong">${errMsg}</span>
  	</c:if>
		<div  class="userform userform_left" style="height:225px;">
			<form action="" method="post">
			<table>
				<tr>
					<td align="left"><font>企业账号:</font></td>
					<td><input name="orgSn" type="text" class="text" id="orgSn" value="" /></td>	
				</tr>
				<tr>
					<td align="left"><font>企业邮箱:</font></td>
					<td><input name="ps" type="text" class="text" id="ps" value="" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input name="button" type="image" class="btn" id="button" value="提  交" src="skins/btn_submit.jpg"/></td>
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
<%@include file="/commons/cloud/foot.jsp"%>
<!-- 底部版权区  结束 -->
</body>
</html>
