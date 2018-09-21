
<%--
	time:2013-05-06 12:52:53
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>销售订单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">销售订单详细信息</span>
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
				<th style="width:120px">销售订单编号:</th>
				<td colspan="3">${saleOrder.code}</td>
			</tr>
            <tr>
				<th style="width:120px">制单人姓名:</th>
				<td>${saleOrder.operatorName}</td>
				<th style="width:120px">制单日期:</th>
				<td>
				<fmt:formatDate value="${saleOrder.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			
			<tr>
				<th style="width:120px">来源单据类型:</th>
				<td>${saleOrder.sourceformType}</td>
				<th style="width:120px">来源单据编号:</th>
				<td>${saleOrder.sourceformCode}</td>
			</tr>
 
			<tr>
				<th style="width:120px">预付款:</th>
				<td>${saleOrder.advancePay}</td>
				<th style="width:120px">总金额:</th>
				<td>${saleOrder.sumPrice}</td>
			</tr>
 
			<tr>
				<th style="width:120px">预付款说明:</th>
				<td colspan="3">${saleOrder.advancepayNotes}</td>
			</tr>
 
			
 
			<tr>
				<th style="width:120px">运费承担方:</th>
				<td>${saleOrder.freightBearer}</td>
				<th style="width:120px">币种:</th>
				<td>${saleOrder.currencyType}</td>
			</tr>
 
 
			<tr>
				<th style="width:120px">采购企业名称:</th>
				<td>${saleOrder.purenterpName}</td>
				<th style="width:120px">采购企业联系人姓名:</th>
				<td>${saleOrder.purenterpUsername}</td>
			</tr>
 
 
 
			<tr>
				<th style="width:120px">供应商企业名称:</th>
				<td>${saleOrder.suppenterpName}</td>
				<th style="width:120px">供应商联系人姓名:</th>
				<td>${saleOrder.suppenterpUsername}</td>
			</tr>
 
 
			<tr>
				<th style="width:120px">收货仓库名称:</th>
				<td>${saleOrder.receivewarehouseName}</td>
				<th style="width:120px">收货地址邮编:</th>
				<td>${saleOrder.postcode}</td>
			</tr>
 
		
 
			<tr>
				<th style="width:120px">收货人姓名:</th>
				<td>${saleOrder.receiverName}</td>
				<th style="width:120px">收货人手机号:</th>
				<td>${saleOrder.receiverPhone}</td>
			</tr>
 
			<tr>
				<th style="width:120px">收货人邮箱:</th>
				<td>${saleOrder.receiverMail}</td>
				<th style="width:120px">收货仓库详细地址:</th>
				<td>${saleOrder.receivewarehouseAddress}</td>
			</tr>
 
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="11" style="text-align: center">销售订单详情</td>
			</tr>
			<tr>
				   <th>物品编码</th>
					<th>物品名称</th>
					<th>物品分类</th>
					<th>物品属性说明</th>
					<th>单位</th>
					<th>订单数量</th>
					<th>单价</th>
					<th>总金额</th>
					<th>要求发货时间</th>
					<th>预计发货时间</th>
					<th>操作</th>
			</tr>	
			<c:forEach items="${saleOrderDetailList}" var="saleOrderDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${saleOrderDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${saleOrderDetailItem.materielId}</td>								
						<td style="text-align: center">${saleOrderDetailItem.materielName}</td>								
						<td style="text-align: center">${saleOrderDetailItem.materielLev}</td>								
						<td style="text-align: center">${saleOrderDetailItem.attributeInfo}</td>								
						<td style="text-align: center">${saleOrderDetailItem.unit}</td>								
						<td style="text-align: center">${saleOrderDetailItem.orderNum}</td>								
						<td style="text-align: center">${saleOrderDetailItem.price}</td>								
						<td style="text-align: center">${saleOrderDetailItem.sumPrice}</td>								
						<td style="text-align: center"><fmt:formatDate value='${saleOrderDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${saleOrderDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${saleOrderDetailItem.materielCode}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

