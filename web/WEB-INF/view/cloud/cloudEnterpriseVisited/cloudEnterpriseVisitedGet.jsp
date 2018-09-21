
<%--
	time:2013-05-03 10:34:39
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_enterprise_visited明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_enterprise_visited详细信息</span>
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
				<th width="20%">visitent_id:</th>
				<td>${cloudEnterpriseVisited.visitentId}</td>
			</tr>
 
			<tr>
				<th width="20%">intervent_id:</th>
				<td>${cloudEnterpriseVisited.interventId}</td>
			</tr>
 
			<tr>
				<th width="20%">visitdate:</th>
				<td>
				<fmt:formatDate value="${cloudEnterpriseVisited.visitdate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

