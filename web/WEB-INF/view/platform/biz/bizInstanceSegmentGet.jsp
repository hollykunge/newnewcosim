
<%--
	time:2013-04-11 11:48:44
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>业务环节实例明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">业务环节实例详细信息</span>
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
				<th width="20%">业务实例ID:</th>
				<td>${bizInstanceSegment.bizInstanceId}</td>
			</tr>
 
			<tr>
				<th width="20%">业务环节ID:</th>
				<td>${bizInstanceSegment.bizDefSegmentId}</td>
			</tr>
 
			<tr>
				<th width="20%">流程实例ID:</th>
				<td>${bizInstanceSegment.actInstId}</td>
			</tr>
 
			<tr>
				<th width="20%">状态::</th>
				<td>${bizInstanceSegment.status}</td>
			</tr>
 
			<tr>
				<th width="20%">序号:</th>
				<td>${bizInstanceSegment.sortOrder}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

