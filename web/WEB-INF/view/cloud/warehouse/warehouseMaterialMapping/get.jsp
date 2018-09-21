
<%--
	time:2013-05-11 14:43:06
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_material_mapping明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">物品映射详细信息</span>
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
			 
			<tr style="display:none">
				<th width="20%">源企业ID:</th>
				<td>${warehouseMaterialMapping.srcEntId}</td>
			</tr>
 
			<tr>
				<th width="20%">源企业名称:</th>
				<td>${warehouseMaterialMapping.srcEntName}</td>
			</tr>
 
			<tr>
				<th width="20%">源物品ID:</th>
				<td>${warehouseMaterialMapping.srcProdId}</td>
			</tr>
 
			<tr>
				<th width="20%">源物品编码:</th>
				<td>${warehouseMaterialMapping.srcProdCode}</td>
			</tr>
 
			<tr>
				<th width="20%">源物品名称:</th>
				<td>${warehouseMaterialMapping.srcProdName}</td>
			</tr>
 
			<tr>
				<th width="20%">目标企业ID:</th>
				<td>${warehouseMaterialMapping.tgtEntId}</td>
			</tr>
 
			<tr>
				<th width="20%">目标企业名称:</th>
				<td>${warehouseMaterialMapping.tgtEntName}</td>
			</tr>
 
			<tr>
				<th width="20%">目标物品ID:</th>
				<td>${warehouseMaterialMapping.tgtProdId}</td>
			</tr>
 
			<tr>
				<th width="20%">目标物品编码:</th>
				<td>${warehouseMaterialMapping.tgtProdCode}</td>
			</tr>
 
			<tr>
				<th width="20%">目标物品名称:</th>
				<td>${warehouseMaterialMapping.tgtProdName}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

