<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/commons/cloud/meta.jsp"%>
<title>汽车行业软件</title>
<script type="text/javascript">
function loginSubmit()
{
	var loginform=document.forms["loginform"];
	loginform._username.value="${userName}";
	loginform._password.value="${userPw}";
	var returnURL="${returnURL}";
	
	if(returnURL.length > 0 ){
	//	alert("未登录");
	}
	else{
	//	alert("正在进行单点登录");
		loginform.submit();
	}
}
</script>
</head>
<body  onload="loginSubmit();">
<form action="${url}"  method="post" name="loginform">
	<input name="_username" type="hidden" />
	<input name="_password" type="hidden" />
</form>
</body>
</html>