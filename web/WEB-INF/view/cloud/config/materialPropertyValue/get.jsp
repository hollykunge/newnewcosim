
<%--
	time:2013-04-17 18:35:03
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_material_property_value明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_material_property_value详细信息</span>
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
				<th width="20%">物品名称:</th>
				<td>${materialPropertyValue.materialId}</td>
			</tr>
 
			<tr>
				<th width="20%">属性名称:</th>
				<td>${materialPropertyValue.propertyId}</td>
			</tr>
 
			<tr>
				<th width="20%">属性值:</th>
				<td>${materialPropertyValue.propertyValue}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

