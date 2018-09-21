<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/cloud/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commons/cloud/meta.jsp"%>
<%@	include file="/commons/include/get.jsp"%>
 
<title>出库明细单</title>
</head>
<body>
 
	<div class="panel-body">
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="7" style="text-align: center">出库单明细</td>
			</tr>
			<tr>
				 
				<th>物品编码</th>
				<th>物品名称</th>
				<th>属性描述</th>
				<th>单位</th>
				<th>已发数量</th>
				<th>单价</th>
				<th>货架</th>
			</tr>	
			<c:forEach items="${warehouseOutDetailList}" var="warehouseOutDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${warehouseOutDetailItem.id}"  class="inputText"/>
						 
						<td style="text-align: center">${warehouseOutDetailItem.materialCode}</td>								
						<td style="text-align: center">${warehouseOutDetailItem.materialName}</td>								
						<td style="text-align: center">${warehouseOutDetailItem.attributeDes}</td>								
						<td style="text-align: center">${warehouseOutDetailItem.units}</td>								
						<td style="text-align: center">${warehouseOutDetailItem.sendNum}</td>								
						<td style="text-align: center">${warehouseOutDetailItem.price}</td>								
						<td style="text-align: center">${warehouseOutDetailItem.shelves}</td>	
				</tr>
			</c:forEach>
		</table>
		<div class="l-clear"></div>
	</div>
	</div>
	 
	</div>
 
</body>
</html>
