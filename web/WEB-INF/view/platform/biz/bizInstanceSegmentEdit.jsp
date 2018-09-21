<%--
	time:2013-04-11 11:48:44
	desc:edit the 业务环节实例
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 业务环节实例</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=bizInstanceSegment"></script>
	<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			valid(showRequest,showResponse);
			$("a.save").click(function() {
				$('#bizInstanceSegmentForm').submit(); 
			});
		});
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${bizInstanceSegment.bizInstanceSegmentId !=null}">
			        <span class="tbar-label">编辑业务环节实例</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加业务环节实例</span>
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
		<form id="bizInstanceSegmentForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">业务实例ID: </th>
					<td><input type="text" id="bizInstanceId" name="bizInstanceId" value="${bizInstanceSegment.bizInstanceId}" validate="{required:false,number:true }" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">业务环节ID: </th>
					<td><input type="text" id="bizDefSegmentId" name="bizDefSegmentId" value="${bizInstanceSegment.bizDefSegmentId}" validate="{required:false,number:true }" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">流程实例ID:  <span class="required">*</span></th>
					<td><input type="text" id="actInstId" name="actInstId" value="${bizInstanceSegment.actInstId}" validate="{required:true,number:true }" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">状态:: </th>
					<td><input type="text" id="status" name="status" value="${bizInstanceSegment.status}" validate="{required:false}" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">序号: </th>
					<td><input type="text" id="sortOrder" name="sortOrder" value="${bizInstanceSegment.sortOrder}" validate="{required:false,number:true }" class="inputText"/></td>
				</tr>
			</table>
			<input type="hidden" name="bizInstanceSegmentId" value="${bizInstanceSegment.bizInstanceSegmentId}" />					
		</form>
	</div>
</div>
</body>
</html>
