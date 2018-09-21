
<%--
	time:2014-03-03 09:47:37
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_tool_user_file明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">工具输入输出详细信息</span>
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
				<th width="20%">输入文件地址:</th>
				<td>${cloud_tool_user_file.inputaddress}</td>
			</tr>
 
			<tr>
				<th width="20%">输出文件地址:</th>
				<td>${cloud_tool_user_file.outputaddress}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

