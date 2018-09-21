
<%--
	time:2013-06-27 10:27:37
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_resource_approval明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_resource_approval详细信息</span>
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
				<th width="20%">企业ID:</th>
				<td>${approvalResource.enpId}</td>
			</tr>
 
			<tr>
				<th width="20%">人员ID:</th>
				<td>${approvalResource.userId}</td>
			</tr>
 
			<tr>
				<th width="20%">资源ID:</th>
				<td>${approvalResource.resId}</td>
			</tr>
 
			<tr>
				<th width="20%">资源名:</th>
				<td>${approvalResource.resName}</td>
			</tr>
 
			<tr>
				<th width="20%">申请日期:</th>
				<td>
				<fmt:formatDate value="${approvalResource.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">审批日期:</th>
				<td>
				<fmt:formatDate value="${approvalResource.approvalData}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">状态:</th>
				<td>${approvalResource.state}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

