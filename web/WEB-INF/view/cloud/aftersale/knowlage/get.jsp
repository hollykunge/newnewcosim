
<%--
	time:2013-04-19 11:40:23
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_service_knowlage明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_service_knowlage详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th width="20%">故障现象:</th>
				<td>${knowLage.phenomenon}</td>
			</tr>
 
			<tr>
				<th width="20%">关键字:</th>
				<td>${knowLage.keyword}</td>
			</tr>
 
			<tr>
				<th width="20%">故障分类:</th>
				<td>${knowLage.type}</td>
			</tr>
 
			<tr>
				<th width="20%">解决措施:</th>
				<td>${knowLage.measure}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

