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
<%@include file="/commons/cloud/top.jsp"%>
<!-- 页面主体  开始 -->
<div id="main">
	<div class="login_title">注册成功</div>
    	<div class="n2success" style="height:250px;">
        <div class="n2-1success">
          <div class="n2-2">
          	<c:if test="${not empty resultMessage}">${resultMessage}</c:if>
          	<c:if test="${empty resultMessage}">
	            <div class="j01-1success"><img src="${ctx}/images/success.jpg" width="62" height="49" /></div>
	            <div class="j01-2success">
	            	恭喜您: 注册成功！您可以直接登录平台,请牢记以下信息<br/>
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
	            	保存您的信息！
	            </div>
	            <div class="j01-4">	              
	              <div class="msg_normalsuccess2"><a href="${ctx}/cloud/console/home.ht" class="link09">点击进入我的企业</a></div>
	            </div>
            </c:if>
          </div>
        </div>
  </div>
</div>
<!-- 页面主体  结束 -->
<%@include file="/commons/cloud/foot.jsp"%>
</body>
</html>
