<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<title>重置密码</title>
</head>
<body>
<div id="all">
<%@include file="/commons/cloud/top.jsp"%>
<!-- 页面主体  开始 -->
<div class="bggraybox">
	<h2>重置密码</h2>
    	<div class="n2success">
        <div class="n2-1success">
          <div class="n2-2">
          	<c:if test="${not empty resultMessage}">
          		<div class="j01-2success">
          			<font color="red";>${resultMessage}</font>
          		</div>
          		<div class="j01-4">
          			<div class="msg_normalsuccess2">
          			<input name="button" type="image" class="btn" id="button"  onClick="location.href='${ctx}/toRegPass.ht'"  value="返  回" src="skins/btn_back.jpg"/>
          			
          				<!-- <input name="button" type="submit" onclick="javascript:history.back(-1);" class="btn" id="button"  value="返  回" /> -->
          			<!-- <a href="javascript:history.back(-1);" class="link09">点击进入我的企业</a> -->
          			</div>
          		</div>
          	</c:if>
          	<c:if test="${empty resultMessage}">
	            <div class="j01-1success"><img src="${ctx}/images/success.jpg" width="62" height="49" /></div>
	            <div class="j01-2success">
	            	恭喜您: 密码重置成功！您可以直接登录平台,请牢记以下信息<br/>
	            	企业账号 : ${orgSn}<br/>
	            	企业管理员账号 : ${user}<br/>
	            	企业密码 : ${dePassword}<br/>
	            </div>
	            <div class="j01-4">
	              <div class="msg_normalsuccess">重置信息已经发送至您的邮箱，为了保证您的账号安全，请立即验证邮箱！</div>
	              <div class="msg_normalsuccess2"><a href="${ctx}/loginCloud.ht" class="link09">点击进入我的企业</a></div>
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
