
<%--
	time:2013-04-11 11:48:44
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>业务实例明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">业务实例详细信息</span>
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
				<th width="20%">业务定义Id:</th>
				<td>${bizInstance.bizDefId}</td>
			</tr>
 
			<tr>
				<th width="20%">状态::</th>
				<td>${bizInstance.status}</td>
			</tr>
 
			<tr>
				<th width="20%">开始时间:</th>
				<td>
				<fmt:formatDate value="${bizInstance.startTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">结束时间:</th>
				<td>
				<fmt:formatDate value="${bizInstance.endTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">创建人:</th>
				<td>${bizInstance.createBy}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

