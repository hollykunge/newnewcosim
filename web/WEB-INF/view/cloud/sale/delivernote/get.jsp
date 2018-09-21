
<%--
	time:2013-05-06 12:24:45
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title> 发货通知单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label"> 发货通知单详细信息</span>
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
				<th style="width:120px">出货通知单流水号:</th>
				<td colspan="3">${delivernote.code}</td>
			</tr>
            <tr>
				<th style="width:120px">制单人姓名:</th>
				<td>${delivernote.operatorName}</td>
				<th style="width:120px">制单日期:</th>
				<td>
				<fmt:formatDate value="${delivernote.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			
 
			<tr>
				<th style="width:120px">来源单据类型:</th>
				<td>${delivernote.sourceformType}</td>
				<th style="width:120px">来源单据编号:</th>
				<td>${delivernote.sourceformCode}</td>
			</tr>
 
			<tr>
				<th style="width:120px">采购企业名称:</th>
				<td>${delivernote.purenterpName}</td>
				<th style="width:120px">采购商联系人姓名:</th>
				<td>${delivernote.purenterpUsername}</td>
			</tr>
 
			<tr>
				<th style="width:120px">供应企业名称:</th>
				<td>${delivernote.suppenterpName}</td>
				<th style="width:120px">供应企业联系人姓名:</th>
				<td>${delivernote.suppenterpUsername}</td>
			</tr>
 
 
			<tr>
				<th style="width:120px">收货仓库名称:</th>
				<td>${delivernote.receivewarehouseName}</td>
				<th style="width:120px">收货仓库详细地址:</th>
				<td>${delivernote.receivewarehouseAddress}</td>
			</tr>
 
			<tr>
				<th style="width:120px">收货人姓名:</th>
				<td>${delivernote.receiverName}</td>
				<th style="width:120px">收货人手机号:</th>
				<td>${delivernote.receiverPhone}</td>
			</tr>
 
			<tr>
				<th style="width:120px">收货人邮箱:</th>
				<td>${delivernote.receiverMail}</td>
				<th style="width:120px">收货地址邮编:</th>
				<td>${delivernote.receiverPostcode}</td>
			</tr>
 
 
			<tr>
				<th style="width:120px">发货仓库名称:</th>
				<td>${delivernote.deliverwarehouseName}</td>
				<th style="width:120px">发货仓库详细地址:</th>
				<td>${delivernote.deliverwarehouseAddress}</td>
			</tr>
 
 
			<tr>
				<th style="width:120px">发货人姓名:</th>
				<td>${delivernote.delivererName}</td>
				<th style="width:120px">发货人手机号:</th>
				<td>${delivernote.delivererPhone}</td>
			</tr>
 
			<tr>
				<th style="width:120px">发货人邮箱:</th>
				<td>${delivernote.delivererMail}</td>
				<th style="width:120px">发货人邮编:</th>
				<td>${delivernote.delivererPostcode}</td>
			</tr>
 
			<tr>
				<th style="width:120px">运费承担方:</th>
				<td colspan="3">${delivernote.freightBearer}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="11" style="text-align: center"> 发货通知单详情</td>
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
			</tr>	
			<c:forEach items="${delivernoteDetailList}" var="delivernoteDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${delivernoteDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${delivernoteDetailItem.materielId}</td>								
						<td style="text-align: center">${delivernoteDetailItem.materielName}</td>								
						<td style="text-align: center">${delivernoteDetailItem.materielLev}</td>								
						<td style="text-align: center">${delivernoteDetailItem.attributeInfo}</td>								
						<td style="text-align: center">${delivernoteDetailItem.unit}</td>								
						<td style="text-align: center">${delivernoteDetailItem.orderNum}</td>								
						<td style="text-align: center">${delivernoteDetailItem.price}</td>								
						<td style="text-align: center">${delivernoteDetailItem.sumPrice}</td>								
						<td style="text-align: center"><fmt:formatDate value='${delivernoteDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${delivernoteDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

