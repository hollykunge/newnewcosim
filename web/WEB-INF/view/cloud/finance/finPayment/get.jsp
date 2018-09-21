
<%--
	time:2013-08-23 10:32:22
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_finance_paymentbill明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">应付单据详细信息</span>
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
				<th width="20%">单证号:</th>
				<td>${finPayment.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${finPayment.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">制单人:</th>
				<td>${finPayment.operaterName}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据类型:</th>
				<td>${finPayment.sourceformType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据号:</th>
				<td>${finPayment.sourceformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">付款类型:</th>
				<td>${finPayment.payType}</td>
			</tr>
 
			<tr>
				<th width="20%">付款企业名称:</th>
				<td>${finPayment.payenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">付款人:</th>
				<td>${finPayment.payenterpUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">收款企业名称:</th>
				<td>${finPayment.payeeenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">收款人:</th>
				<td>${finPayment.payeeenterpUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">付款日期:</th>
				<td>
				<fmt:formatDate value="${finPayment.payDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">付款方式:</th>
				<td>${finPayment.payMode}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${finPayment.currencyType}</td>
			</tr>
 
			<tr>
				<th width="20%">汇率:</th>
				<td>${finPayment.exchangeRate}</td>
			</tr>
 
			<tr>
				<th width="20%">收款银行:</th>
				<td>${finPayment.gatherBank}</td>
			</tr>
 
			<tr>
				<th width="20%">收款账号:</th>
				<td>${finPayment.gatherNumber}</td>
			</tr>
 
			<tr>
				<th width="20%">付款银行:</th>
				<td>${finPayment.payenterpBank}</td>
			</tr>
 
			<tr>
				<th width="20%">付款账号:</th>
				<td>${finPayment.payenterpNumber}</td>
			</tr>
 
			<tr>
				<th width="20%">票据号:</th>
				<td>${finPayment.noteCode}</td>
			</tr>
 
			<tr>
				<th width="20%">付款金额:</th>
				<td>${finPayment.payPrice}</td>
			</tr>
 
			<tr>
				<th width="20%">备注:</th>
				<td>${finPayment.remark}</td>
			</tr>
			<!--
 
			<tr>
				<th width="20%">收款人ID:</th>
				<td>${finPayment.payeeenterpUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">收款企业ID:</th>
				<td>${finPayment.payeeenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">付款人id:</th>
				<td>${finPayment.payenterpUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">付款企业ID:</th>
				<td>${finPayment.payenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据ID:</th>
				<td>${finPayment.sourceformId}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${finPayment.operaterId}</td>
			</tr>
			-->
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="20" style="text-align: center">应付单据详细信息</td>
			</tr>
			<tr>
				<th>发票号</th>
				<th>产品</th>
				<th>规格</th>
				<th>单位</th>
				<th style="display:none">发货数量</th>
				<th>接收数量</th>
				<th style="display:none">合格品数量</th>
				<th>单价</th>
				<th>金额</th>
				<th style="display:none">结算金额</th>
				<th style="display:none">开票金额</th>
				<th style="display:none">已付金额</th>
				<th style="display:none">未付金额</th>
				<th style="display:none">本次付款额</th>
				<th style="display:none">发货日期</th>
				<th>到货日期</th>
				<th style="display:none">销售订单号</th>
				<th style="display:none">结算单号</th>
				<th style="display:none">到货单号</th>
				<th style="display:none">备注</th>
			</tr>	
			<c:forEach items="${finPaymentDetailList}" var="finPaymentDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${finPaymentDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${finPaymentDetailItem.invoice}</td>								
						<td style="text-align: center">${finPaymentDetailItem.product}</td>								
						<td style="text-align: center">${finPaymentDetailItem.spec}</td>								
						<td style="text-align: center">${finPaymentDetailItem.unite}</td>								
						<td style="display:none">${finPaymentDetailItem.consignmentNum}</td>								
						<td style="text-align: center">${finPaymentDetailItem.receiveNum}</td>								
						<td style="display:none">${finPaymentDetailItem.checkNum}</td>								
						<td style="text-align: center">${finPaymentDetailItem.price}</td>								
						<td style="text-align: center">${finPaymentDetailItem.sumPrice}</td>								
						<td style="display:none">${finPaymentDetailItem.balancePrice}</td>								
						<td style="display:none">${finPaymentDetailItem.invoicePrice}</td>								
						<td style="display:none">${finPaymentDetailItem.disbursePrice}</td>								
						<td style="display:none">${finPaymentDetailItem.nonPay}</td>								
						<td style="display:none">${finPaymentDetailItem.currentPrice}</td>								
						<td style="display:none"><fmt:formatDate value='${finPaymentDetailItem.consignmentDate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${finPaymentDetailItem.arriveDate}' pattern='yyyy-MM-dd'/></td>								
						<td style="display:none">${finPaymentDetailItem.sellBill}</td>								
						<td style="display:none">${finPaymentDetailItem.balanceBill}</td>								
						<td style="display:none">${finPaymentDetailItem.arriveBill}</td>								
						<td style="display:none">${finPaymentDetailItem.remark}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

