
<%--
	time:2013-04-17 18:13:05
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_capability_material明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_capability_material详细信息</span>
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
				<th width="20%">capability_id:</th>
				<td>${capabilityMaterial.capabilityId}</td>
			</tr>
 
			<tr>
				<th width="20%">material_id:</th>
				<td>${capabilityMaterial.materialId}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

