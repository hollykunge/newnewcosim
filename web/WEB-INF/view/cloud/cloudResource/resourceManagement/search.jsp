<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源搜索列表</title>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@include file="/commons/include/get.jsp"%>

<link href="${ctx}/styles/cloud/softlist_style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
</head>

<body>
<div id="all">
	<!-- 顶部浮动层  开始 -->
	<%-- <%@include file="/commons/cloud/top.jsp"%> --%>

	<!-- 主导航  结束 -->
	<!-- 页面主体  开始 -->
	<div id="main">
		<table width="84.5%" border="0" cellspacing="0" cellpadding="0"
			style="border: #9db3c5 1px solid;">
			<c:forEach items="${cloudResourceList}" var="c1">
			<table width='84.5%' border='0' cellspacing='0' cellpadding='0'
				style='border: #9db3c5 1px solid;'>

				<tr>
					<td colspan='2'><div class='flu_font01'>${c1.title}</div></td>
				</tr>
				<tr>
					<td width='16%' rowspan='2'>
						<div class='flu_image'>
							<img src="${ctx}${c1.urlPic}" onError="this.src='${ctx}/images/default-chance.jpg'" width="178" height="167" /> 
						</div>
					</td>
					<td width='84%'><div class='flu_font02'>${c1.info }</div></td>
				</tr>
				<tr>
					<td><div class='flu_font03'>
							<c:if test="${c1.mode=='bat'}">
							<a href='${ctx}/cloud/cloudResource/approvalResource/checkResource.ht?resourceUrl=${c1.url1}&resourceID=${c1.id}&resourceName=${c1.title}' class='flu_link'>${c1.url1Name}</a>
						</c:if>
						<c:if test="${c1.mode=='interact'}">
							<a href='${ctx}/cloud/cloudResource/approvalResource/checkResource.ht?resourceUrl=${c1.url2}&resourceID=${c1.id}&resourceName=${c1.title}' class='flu_link'>${c1.url2Name}</a>
						</c:if>
						</div></td>
				</tr>
			</table>
		 </c:forEach>
		</table>


		<div class="pub_tab">
			<div class="fenye">
				<hotent:paging tableId="cloudResourceItem" />
			</div>
		</div>
	</div>
	
	
	<!-- 页面主体  结束 -->
	
</div>

<!-- 底部版权区  开始 -->
<%-- <%@ include file="/commons/cloud/foot.jsp"%> --%>
<!-- 底部版权区  结束 -->
</body>
</html>
