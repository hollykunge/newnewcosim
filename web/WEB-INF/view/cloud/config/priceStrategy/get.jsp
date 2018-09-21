
<%--
	time:2013-09-11 16:18:27
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_price_strategy明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_price_strategy详细信息</span>
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
				<th width="20%">供应商id:</th>
				<td>${priceStrategy.enterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">供应商名称:</th>
				<td>${priceStrategy.enterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">经销商id:</th>
				<td>${priceStrategy.coopenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">经销商名称:</th>
				<td>${priceStrategy.coopenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">物品id:</th>
				<td>${priceStrategy.materialId}</td>
			</tr>
 
			<tr>
				<th width="20%">物品名称:</th>
				<td>${priceStrategy.materialName}</td>
			</tr>
 
			<tr>
				<th width="20%">数量:</th>
				<td>${priceStrategy.number}</td>
			</tr>
 
			<tr>
				<th width="20%">季节:</th>
				<td>${priceStrategy.season}</td>
			</tr>
 
			<tr>
				<th width="20%">折扣:</th>
				<td>${priceStrategy.discount}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

