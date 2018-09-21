<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@ include file="/commons/cloud/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单物品列表</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
<div class="panel">
	<div class="panel-top">
	<div class="tbar-title">
				<span class="tbar-label">销售情况查询结果列表</span>
			</div>
	<div class="panel-body">    	
			<table name="saleOrderList" id="saleOrderItem" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<thead>
					<th style="display:none">id</th>
					<th>订单号</th>
					<th>制单日期</th>
					<th>采购商名称</th>
					<th>供应商名称</th>
					<th style="display:none">物品id</th>
					<th>物品名称</th>
					<th>价格</th>
					<th>销售数量</th>
				</thead>
				<tbody>
					<c:forEach items="${saleOrderList}" var="saleOrderItem">
						<c:forEach items="${saleOrderItem.saleOrderDetailList}" var="saleOrderDetailItem">
							<tr>
								<td name="id" style="display:none">${saleOrderItem.id}</td>
								<td name="code">${saleOrderItem.code}</td>
								<td name="operateDate" style="text-align: center"><fmt:formatDate value="${saleOrderItem.operateDate}" pattern="yyyy-MM-dd"/></td>
								<td name="purenterpName">${saleOrderItem.purenterpName}</td>
								<td name="suppenterpName" style="text-align: center">${saleOrderItem.suppenterpName}</td>
								<td name="materielId" style="display:none">${saleOrderDetailItem.materielId}</td>
								<td name="materielName" style="text-align: center">${saleOrderDetailItem.materielName}</td>
								<td name="price" style="text-align: center">${saleOrderDetailItem.price}</td>
								<td name="orderNum" style="text-align: center">${saleOrderDetailItem.orderNum}</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>
			<hotent:paging tableId="saleOrderItem"/>
		</div>
		</div>
		</div>
</body>
</html>