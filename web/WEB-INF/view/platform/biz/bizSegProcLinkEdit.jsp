<%--
	time:2013-04-11 11:48:44
	desc:edit the BIZ_SEG_PROC_LINK
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 BIZ_SEG_PROC_LINK</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=bizSegProcLink"></script>
	<script type="text/javascript">
		$(function() {
			function showRequest(formData, jqForm, options) { 
				return true;
			} 
			valid(showRequest,showResponse);
			$("a.save").click(function() {
				$('#bizSegProcLinkForm').submit(); 
			});
		});
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${bizSegProcLink.bizSegProLinkId !=null}">
			        <span class="tbar-label">编辑BIZ_SEG_PROC_LINK</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加BIZ_SEG_PROC_LINK</span>
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
		<form id="bizSegProcLinkForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">业务环节ID:  <span class="required">*</span></th>
					<td><input type="text" id="bizDefSegmentId" name="bizDefSegmentId" value="${bizSegProcLink.bizDefSegmentId}" validate="{required:true,number:true }" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">企业ID（即组织）:  <span class="required">*</span></th>
					<td><input type="text" id="orgId" name="orgId" value="${bizSegProcLink.orgId}" validate="{required:true,number:true }" class="inputText"/></td>
				</tr>
				<tr>
					<th width="20%">流程定义Key: </th>
					<td><input type="text" id="actDefKey" name="actDefKey" value="${bizSegProcLink.actDefKey}" validate="{required:false,maxlength:768}" class="inputText"/></td>
				</tr>
			</table>
			<input type="hidden" name="bizSegProLinkId" value="${bizSegProcLink.bizSegProLinkId}" />					
		</form>
	</div>
</div>
</body>
</html>
