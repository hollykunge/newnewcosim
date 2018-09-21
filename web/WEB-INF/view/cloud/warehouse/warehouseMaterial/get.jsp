
<%--
	time:2013-06-20 10:18:32
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>物品入库明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">物品入库详细信息</span>
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
				<th width="20%">入库人:</th>
				<td>${warehouseMaterial.operator}</td>
			</tr>
 
			<tr>
				<th width="20%">入库时间:</th>
				<td>
				<fmt:formatDate value="${warehouseMaterial.arrivatedDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			 
 
			<tr>
				<th width="20%">仓库名称:</th>
				<td>${warehouseMaterial.warehouseName}</td>
			</tr>
 
		 
 
			<tr>
				<th width="20%">物品名称:</th>
				<td>${warehouseMaterial.materialName}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

