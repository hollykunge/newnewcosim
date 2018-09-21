
<%--
	time:2013-04-19 13:31:05
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>CLOUD_BUSINESS_AREA明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">CLOUD_BUSINESS_AREA详细信息</span>
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
				<th width="20%">role_id:</th>
				<td>${busiarea.roleId}</td>
			</tr>
 
			<tr>
				<th width="20%">main_ent:</th>
				<td>${busiarea.mainEnt}</td>
			</tr>
 
			<tr>
				<th width="20%">corp_ent:</th>
				<td>${busiarea.corpEnt}</td>
			</tr>
 
			<tr>
				<th width="20%">state:</th>
				<td>${busiarea.state}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

