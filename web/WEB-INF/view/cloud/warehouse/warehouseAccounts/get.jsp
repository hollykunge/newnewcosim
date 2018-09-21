
<%--
	time:2013-05-11 14:43:06
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_warehouse_accounts明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">库存查询详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht?warehouseid=${warehouseAccounts.warehouseid}">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th width="20%">公司ID:</th>
				<td>${warehouseAccounts.companyid}</td>
			</tr>
 
			<tr>
				<th width="20%">批号/编号:</th>
				<td>${warehouseAccounts.batchnumber}</td>
			</tr>
 
			<tr>
				<th width="20%">物品编号:</th>
				<td>${warehouseAccounts.productcode}</td>
			</tr>
 
			<tr>
				<th width="20%">物品名称:</th>
				<td>${warehouseAccounts.productname}</td>
			</tr>
 
			<tr>
				<th width="20%">物品规格:</th>
				<td>${warehouseAccounts.specifications}</td>
			</tr>
 
			<tr>
				<th width="20%">单位:</th>
				<td>${warehouseAccounts.units}</td>
			</tr>
			<tr>
				<th width="20%">仓库名称:</th>
				<td>${warehouseAccounts.warehousename}</td>
			</tr>
 
			<tr>
				<th width="20%">货架:</th>
				<td>${warehouseAccounts.shelves}</td>
			</tr>
 
			<tr>
				<th width="20%">数量:</th>
				<td>${warehouseAccounts.nums}</td>
			</tr>
		 
		</table>
		 
		</div>
		
	</div>
</body>
</html>

