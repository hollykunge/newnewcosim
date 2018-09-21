
<%--
	time:2014-03-06 18:01:30
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_tool_user_inputFile明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">输入文件详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht?cloudToolUserId=<%=request.getAttribute("cloudToolUserId") %>">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th width="20%">工具名称:</th>
				<td>${cloud_tool_user_inputFile.toolName}</td>
			</tr>
 
			<tr>
				<th width="20%">输入文件地址:</th>
				<td>${cloud_tool_user_inputFile.inputaddress}</td>
			</tr>
			
			<tr>
				<th width="20%">输入文件类型:</th>
				<td>${cloud_tool_user_inputFile.type}</td>
			</tr>
			
			<tr>
				<th width="20%">输入文件生成方案:</th>
				<td>${cloud_tool_user_inputFile.generateStrategy}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

