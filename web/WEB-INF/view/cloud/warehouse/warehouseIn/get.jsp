
<%--
	time:2013-05-11 14:43:06
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>入库单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">入库单详细信息</span>
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
				<th width="20%">入库单号:</th>
				<td>${warehouseIn.code}</td>
			</tr>
 
			<tr>
				<th width="20%">入库类型:</th>
				<td>${warehouseIn.inType}</td>
			</tr>
 
 
 
			<tr>
				<th width="20%">来源单据号:</th>
				<td>${warehouseIn.inOrderCode}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人:</th>
				<td>${warehouseIn.operator}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${warehouseIn.operatedate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">到货日期:</th>
				<td>
				<fmt:formatDate value="${warehouseIn.arrivatedDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
 
			<tr>
				<th width="20%">仓库名称:</th>
				<td>${warehouseIn.warehouseName}</td>
			</tr>
 
			<tr>
				<th width="20%">仓库详细地址:</th>
				<td>${warehouseIn.warehouseAddress}</td>
			</tr>
 
		 
 
		 
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="7" style="text-align: center">入库单明细</td>
			</tr>
			<tr>
				 
				<th>物品编码</th>
				<th>物品名称</th>
				<th>属性描述</th>
				<th>单位</th>
				<th>已收数量</th>
				<th>单价</th>
				<th>货架</th>
			</tr>	
			<c:forEach items="${warehouseInDetailList}" var="warehouseInDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${warehouseInDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${warehouseInDetailItem.materialcode}</td>								
						<td style="text-align: center">${warehouseInDetailItem.materialname}</td>								
						<td style="text-align: center">${warehouseInDetailItem.attributedes}</td>								
						<td style="text-align: center">${warehouseInDetailItem.units}</td>								
						<td style="text-align: center">${warehouseInDetailItem.receivedamount}</td>								
						<td style="text-align: center">${warehouseInDetailItem.price}</td>
						<td style="text-align: center">${warehouseInDetailItem.shelves}</td>																
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

