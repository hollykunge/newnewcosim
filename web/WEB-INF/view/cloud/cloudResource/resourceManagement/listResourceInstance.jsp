<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<link href="${ctx}/styles/cloud/softlist_style.css" rel="stylesheet"
	type="text/css" />

</head>
<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="border: #9db3c5 1px solid;">
	<c:forEach items="${resourceInstances}" var="resourceInstance">
	
		<table width='100%' border='0' cellspacing='0' cellpadding='0'	style='border: #9db3c5 1px solid;'>
			<tr>
				<td colspan='2'>
					<div class='flu_font01'>${resourceInstance.title}</div>
				</td>
			</tr>
			<tr>
				<td width='16%' rowspan='2'><div class='flu_image'>
						<img src="${ctx}${resourceInstance.urlPic}"  width='178' 
							height='167' />
					</div></td>
				<td width='84%'>
					<div class='flu_font02'>${resourceInstance.info}</div>
				</td>
			</tr>
			<tr>
				<td><div class='flu_font03'>
				
						<c:if test="${mode=='nologin'}">
							<a href='${ctx}/abc.ht' class='flu_link'>${resourceInstance.url1Name}</a>
						</c:if>
						<c:if test="${mode=='bat'}">
							<a href='${ctx}/cloud/cloudResource/approvalResource/checkResource.ht?resourceUrl=${resourceInstance.url1}&resourceID=${resourceInstance.id}&resourceName=${resourceInstance.title}' class='flu_link'>${resourceInstance.url1Name}</a>
						</c:if>
						<c:if test="${mode=='interact'}">
							<a href='${ctx}/cloud/cloudResource/approvalResource/checkResource.ht?resourceUrl=${resourceInstance.url2}&resourceID=${resourceInstance.id}&resourceName=${resourceInstance.title}' class='flu_link'>${resourceInstance.url2Name}</a>
						</c:if>
						
					</div></td>
			</tr>
		</table>
	</c:forEach>
	
					
</table>
</body>
</html>
