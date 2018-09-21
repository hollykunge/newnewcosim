<%--
	time:2013-04-11 11:48:44
	desc:edit the 业务实例
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 业务实例</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=bizInstance"></script>
	<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			valid(showRequest,showResponse);
			$("a.save").click(function() {
				$('#bizInstanceForm').submit(); 
			});
		});
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${bizInstance.bizInstanceId !=null}">
			        <span class="tbar-label">编辑业务实例</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加业务实例</span>
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
		<form id="bizInstanceForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">业务定义Id: </th>
					<td><input type="text" id="bizDefId" name="bizDefId" value="${bizInstance.bizDefId}" validate="{required:false,number:true }" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">状态:: </th>
					<td><input type="text" id="status" name="status" value="${bizInstance.status}" validate="{required:false}" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">开始时间: </th>
					<td><input type="text" id="startTime" name="startTime" value="<fmt:formatDate value='${bizInstance.startTime}' pattern='yyyy-MM-dd'/>" validate="{required:false,date:true}" class="inputText date"/></td>
				</tr>
				<tr>
					<th width="20%">结束时间: </th>
					<td><input type="text" id="endTime" name="endTime" value="<fmt:formatDate value='${bizInstance.endTime}' pattern='yyyy-MM-dd'/>" validate="{required:false,date:true}" class="inputText date"/></td>
				</tr>
				<tr>
					<th width="20%">创建人: </th>
					<td><input type="text" id="createBy" name="createBy" value="${bizInstance.createBy}" validate="{required:false,number:true }" class="inputText"/></td>
				</tr>
			</table>
			<input type="hidden" name="bizInstanceId" value="${bizInstance.bizInstanceId}" />					
		</form>
	</div>
</div>
</body>
</html>
