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
			$.ligerMessageBox
					.confirm(
							"提示信息",
							obj.getMessage() + ",是否继续操作",
							function(rtn) {
								if (rtn) {
									this.close();
									if($("#toolId").val()==""){
										$("#toolForm").resetForm();
									}
								} else {
									window.location.href = "${ctx}/cloud/tool/list.ht";
								}
							});
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
					<c:when test="${tool.id !=null}">
						<span class="tbar-label">编辑工具模版</span>
					</c:when>
					<c:otherwise>
						<span class="tbar-label">添加工具模版</span>
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
						<a class="link back" href="list.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="toolForm" method="post" action="save.ht">
				<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
					<tr>
						<th width="20%">工具名称:</th>
						<td><input type="text" id="toolName" name="toolName" value="${tool.toolName}" class="inputText" validate="{required:true,maxlength:128}" /></td>
					</tr>
					<tr>
						<th width="20%">工具类型:</th>
						<td>
						<c:set var="LOCAL"><%=ToolType.LOCAL.getCode() %></c:set>
						<c:set var="NETWORK"><%=ToolType.NETWORK.getCode() %></c:set>
						<c:set var="CLUSTER"><%=ToolType.CLUSTER.getCode() %></c:set>
						<select name="toolType" class="select" style="width:245px !important">
								<option value="${LOCAL}" <c:if test="${tool.toolType==LOCAL}">selected</c:if> ><%=ToolType.LOCAL.getDescription() %></option>
								<option value="${NETWORK}" <c:if test="${tool.toolType==NETWORK}">selected</c:if> ><%=ToolType.NETWORK.getDescription() %></option>
								<option value="${CLUSTER}" <c:if test="${tool.toolType==CLUSTER}">selected</c:if> ><%=ToolType.CLUSTER.getDescription() %></option>
							</select></td>
					</tr>
					<tr>
						<th width="20%">用户默认工具地址:</th>
						<td><input type="text" id="toolAddress" name="toolAddress" value="${tool.toolAddress}" class="inputText" style="width:600px" validate="{required:true,maxlength:512}" /></td>
					</tr>					
					<tr>
						<th width="20%">工具描述:</th>
						<td><textarea name="toolDesc" cols="120" rows="20" validate="{maxlength:512}">${fn:escapeXml(tool.toolDesc)}</textarea>
						</td>
					</tr>
				</table>
				<input type="hidden" id="toolId" name="id" value="${tool.id}" />
				<input type="hidden" name="createtime" value="<fmt:formatDate value='${tool.createtime}' pattern='yyyyMMddHHmmss'/>" />
			</form>
		</div>
	</div>
</body>
</html>
