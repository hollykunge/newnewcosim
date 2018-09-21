<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@	include file="/commons/include/get.jsp"%>
 
<title>入库明细单</title>
</head>
<body>
 
	<div class="panel-body">
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
		<div class="l-clear"></div>
	</div>
	</div>
	 
	</div>
 
</body>
</html>
