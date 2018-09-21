<%--
	time:2013-05-03 10:34:39
	desc:edit the cloud_enterprise_visited
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 cloud_enterprise_visited</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=cloudEnterpriseVisited"></script>
	<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			valid(showRequest,showResponse);
			$("a.save").click(function() {
				$('#cloudEnterpriseVisitedForm').submit(); 
			});
		});
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${cloudEnterpriseVisited.id !=null}">
			        <span class="tbar-label">编辑cloud_enterprise_visited</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加cloud_enterprise_visited</span>
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
		<form id="cloudEnterpriseVisitedForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">visitent_id: </th>
					<td><input type="text" id="visitentId" name="visitentId" value="${cloudEnterpriseVisited.visitentId}" validate="{required:false,number:true }" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">intervent_id: </th>
					<td><input type="text" id="interventId" name="interventId" value="${cloudEnterpriseVisited.interventId}" validate="{required:false,number:true }" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">visitdate: </th>
					<td><input type="text" id="visitdate" name="visitdate" value="<fmt:formatDate value='${cloudEnterpriseVisited.visitdate}' pattern='yyyy-MM-dd'/>" validate="{required:false,date:true}" class="inputText date"/></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${cloudEnterpriseVisited.id}" />					
		</form>
	</div>
</div>
</body>
</html>
