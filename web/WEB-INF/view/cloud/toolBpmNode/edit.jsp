<%@page import="com.casic.cloud.model.tool.ToolType"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>编辑工具</title>
<%@include file="/commons/include/form.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript">
	$(function() {
		var options = {};
		if (showResponse) {
			options.success = showResponse;
		}
		var frm = $('#toolForm').form();
		$("a.save").click(function() {
			frm.setData();
			frm.ajaxForm(options);
			if (frm.valid()) {
				$('#toolForm').submit();
			}
		});
	});

	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		if (obj.isSuccess()) {
			$.ligerMessageBox.success("操作成功",obj.getMessage());
		} else {
			$.ligerMessageBox.error("提示信息", obj.getMessage());
		}
	}
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<c:choose>
					<c:when test="${tool.toolNodeId !=null}">
						<span class="tbar-label">编辑工具</span>
					</c:when>
					<c:otherwise>
						<span class="tbar-label">添加工具</span>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="javascript:void(0)">保存</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link back" href="list.ht?setId=${tool.setId}">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="toolForm" method="post" action="save.ht">
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
					<tr>
						<th width="20%">工具名称:</th>
						<td>${tool.toolName}
							<input type="hidden" id="toolName" name="toolName" value="${tool.toolName}"/></td>
					</tr>
					<tr>
						<th width="20%">工具类型:</th>
						<td>${tool.toolTypeAsString}
							<input type="hidden" id="toolType" value="${tool.toolType}"/>
						</td>
					</tr>
					<tr>
						<th width="20%">缺省工具地址:</th>
						<td>${tool.toolAddress}
							<input type="hidden" id="toolAddress" name="toolAddress" value="${tool.toolAddress}" /></td>
					</tr>
					<tr>
						<th width="20%">我的工具地址:</th>
						<td><input type="text" id="myToolAddress" name="myToolAddress" value="${tool.myToolAddress}" class="inputText" style="width:600px"/></td>
					</tr>										
					<tr>
						<th width="20%">工具描述:</th>
						<td>${fn:escapeXml(tool.toolDesc)}
							<input type="hidden" name="toolDesc" value="${fn:escapeXml(tool.toolDesc)}"/></td>
					</tr>
				</table>
				<input type="hidden" id="toolNodeId" name="toolNodeId" value="${tool.toolNodeId}" />
				<input type="hidden" id="toolId" name="toolId" value="${tool.toolId}" />
				<input type="hidden" id="setId" name="setId" value="${tool.setId}" />
			</form>
		</div>
	</div>
</body>
</html>
