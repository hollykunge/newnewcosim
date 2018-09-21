
<%--
	time:2013-04-13 19:06:38
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>销售报价单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">销售报价单详细信息</span>
			</div>
			<div class="panel-toolbar"  style="display:none">
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
				<th style="width:120px">报价单编码:</th>
				<td>${saleQuote.code}</td>
				<th style="width:120px">制单日期:</th>
				<td><fmt:formatDate value="${saleQuote.operateDate}" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<th style="width:120px">制单人:</th>
				<td>${saleQuote.operatorName}</td>
				<th style="width:120px">报价截止日期:</th>
				<td>
				<fmt:formatDate value="${saleQuote.offerEndtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th style="width:120px">预付款:</th>
				<td>${saleQuote.advancePay}</td>
				<th style="width:120px">预付款说明:</th>
				<td>${saleQuote.advancepayNotes}</td>
			</tr>
 
			<tr>
				<th style="width:120px">来源单据类型:</th>
				<td>${saleQuote.sourceformType}</td>
				<th style="width:120px">来源单据编码:</th>
				<td>${saleQuote.sourceformCode}</td>
			</tr>
 
			<tr>
				<th style="width:120px">采购企业:</th>
				<td>${saleQuote.purenterpName}</td>
				<th style="width:120px">采购企业联系人姓名:</th>
				<td>${saleQuote.purenterpUsername}</td>
			</tr>
 
			<tr style="display:none">
				<th style="width:120px">采购企业联系人ID:</th>
				<td>${saleQuote.purenterpUserid}</td>
				<th style="width:120px">采购企业ID:</th>
				<td>${saleQuote.purenterpId}</td>
				<th style="width:120px">制单人ID:</th>
				<td>${saleQuote.operatorId}</td>
				<th style="width:120px">销售企业ID: </th>
				<td>${saleQuote.enterpriseId}</td>
			</tr>
 
			<tr>
				<th style="width:120px">币种:</th>
				<td>${saleQuote.currencyType}</td>
				<th style="width:120px">运费承担方:</th>
				<td>${saleQuote.freightBearer}</td>
			</tr>
 
			
			<tr>
				<th style="width:120px">销售企业: </th>
				<td>${saleQuote.enterpriseName}</td>
				<th style="width:120px">询价单发布日期:</th>
				<td><fmt:formatDate value="${saleQuote.purenqReleasedate}" pattern="yyyy-MM-dd"/></td>
			</tr>
			
			
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="11" style="text-align: center">销售报价单明细</td>
			</tr>
			<tr>
				<th style="display:none">物品ID</th>
				<th>物品名</th>
				<th style="display:none">物品分类</th>
				<th>物品规格</th>
				<th>单位</th>
				<th>订单数量</th>
				<th>单价</th>
				<th>总价</th>
				<th>要求发货时间</th>
				<th>预计发货时间</th>
				<th style="display:none">物品编码</th>
			</tr>	
			<c:forEach items="${saleQuoteDetailList}" var="saleQuoteDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${saleQuoteDetailItem.id}"  class="inputText"/>
						<td style="display:none">${saleQuoteDetailItem.materielId}</td>								
						<td style="text-align: center">${saleQuoteDetailItem.materielName}</td>								
						<td style="display:none">${saleQuoteDetailItem.materielLev}</td>								
						<td style="text-align: center">${saleQuoteDetailItem.model}</td>								
						<td style="text-align: center">${saleQuoteDetailItem.unit}</td>								
						<td style="text-align: center">${saleQuoteDetailItem.orderNum}</td>								
						<td style="text-align: center">${saleQuoteDetailItem.price}</td>								
						<td style="text-align: center">${saleQuoteDetailItem.sumPrice}</td>								
						<td style="text-align: center"><fmt:formatDate value='${saleQuoteDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${saleQuoteDetailItem.predeliverDate}' pattern='yyyy-MM-dd'/></td>								
						<td style="display:none">${saleQuoteDetailItem.materielCode}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

