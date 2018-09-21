<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册成功</title>
<link href="../css/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="/common/top.jsp"%>
<!-- 页面主体  开始 -->
<div id="main">
	<div class="login_title">注册成功</div>
    	<div class="n2success">
        <div class="n2-1success">
          <div class="n2-2">
            <div class="j01-1success"><img src="../images/success.jpg" width="62" height="49" /></div>
            <div class="j01-2success">
            	恭喜您: 注册成功！您可以直接登录平台,请牢记以下信息<br/>
            	企业账号 : ${info.eid}<br/>
            	企业管理员账号 : ${info.sysadmin}<br/>
            	企业密码 : ${info.password}<br/>
            </div>
            <div class="j01-4">
              <div class="msg_normalsuccess">为了保证您的账号安全，请立即验证邮箱！</div>
              <div class="msg_normalsuccess2"><a href="${ctx}/j_spring_security_check?target=console/mypage.action" class="link09">点击进入我的企业</a></div>
            </div>
          </div>
        </div>
  </div>
</div>
<!-- 页面主体  结束 -->
<%@include file="/common/foot.jsp"%>
</body>
</html>
