<%--
	time:2013-05-16 17:40:22
	desc:edit the cloud_use_res
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_use_res</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=cloudUseRes"></script>
	<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			valid(showRequest,showResponse);
			$("a.save").click(function() {
				$('#cloudUseResForm').submit(); 
			});
		});
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${cloudUseRes.id !=null}">
			        <span class="tbar-label">编辑cloud_use_res</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_use_res</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back " href="list.ht">返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="cloudUseResForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">企业Id: </th>
					<td><input type="text" id="entid" name="entid" value="${cloudUseRes.entid}" validate="{required:false,number:true }" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">资源名字: </th>
					<td><input type="text" id="resName" name="resName" value="${cloudUseRes.resName}" validate="{required:false,maxlength:384}" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">资源地址链接: </th>
					<td><input type="text" id="resLink" name="resLink" value="${cloudUseRes.resLink}" validate="{required:false,maxlength:384}" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">资源访问时间: </th>
					<td><input type="text" id="resTime" name="resTime" value="<fmt:formatDate value='${cloudUseRes.resTime}' pattern='yyyy-MM-dd'/>" validate="{required:false,date:true}" class="inputText date"/></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${cloudUseRes.id}" />					
		</form>
	</div>
</div>
</body>
</html>
