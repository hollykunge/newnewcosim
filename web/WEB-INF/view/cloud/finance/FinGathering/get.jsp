
<%--
	time:2013-08-23 10:02:06
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_finance_gathering明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">应收单据详细信息</span>
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
				<td>${finGathering.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${finGathering.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">制单人:</th>
				<td>${finGathering.operaterName}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据类型:</th>
				<td>${finGathering.sourceformType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据号:</th>
				<td>${finGathering.sourceformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">收款类型:</th>
				<td>${finGathering.payeeenterpType}</td>
			</tr>
 
			<tr>
				<th width="20%">收款企业名称:</th>
				<td>${finGathering.payeeenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">收款人:</th>
				<td>${finGathering.payeeenterpUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">付款企业名称:</th>
				<td>${finGathering.payenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">付款人:</th>
				<td>${finGathering.payenterpUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">付款日期:</th>
				<td>
				<fmt:formatDate value="${finGathering.payeeenterpDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">付款方式:</th>
				<td>${finGathering.payenterpModel}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${finGathering.currencyType}</td>
			</tr>
 
			<tr>
				<th width="20%">汇率:</th>
				<td>${finGathering.exchangeRate}</td>
			</tr>
 
			<tr>
				<th width="20%">收款银行:</th>
				<td>${finGathering.gatheringBank}</td>
			</tr>
 
			<tr>
				<th width="20%">收款账号:</th>
				<td>${finGathering.gatheringCode}</td>
			</tr>
 
			<tr>
				<th width="20%">付款银行:</th>
				<td>${finGathering.paymentBank}</td>
			</tr>
 
			<tr>
				<th width="20%">付款账号:</th>
				<td>${finGathering.paymentCode}</td>
			</tr>
 
			<tr>
				<th width="20%">票据号:</th>
				<td>${finGathering.ticketBill}</td>
			</tr>
 
			<tr>
				<th width="20%">付款金额:</th>
				<td>${finGathering.gatheringPrice}</td>
			</tr>
 
			<tr>
				<th width="20%">备注:</th>
				<td>${finGathering.remark}</td>
			</tr>
 <!--
			<tr>
				<th width="20%">收款企业ID:</th>
				<td>${finGathering.payeeenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">付款企业ID:</th>
				<td>${finGathering.payenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据ID:</th>
				<td>${finGathering.sourceformId}</td>
			</tr>
 
			<tr>
				<th width="20%">收款人ID:</th>
				<td>${finGathering.payeeenterpUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">付款人id:</th>
				<td>${finGathering.payenterpUserid}</td>
			</tr>
 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${finGathering.operaterId}</td>
			</tr>
			-->
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="20" style="text-align: center">应收单据详细信息</td>
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
				<th style="display:none">已收金额</th>
				<th style="display:none">未收金额</th>
				<th style="display:none">本次收款额</th>
				<th style="display:none">发货日期</th>
				<th>到货日期</th>
				<th style="display:none">销售订单号</th>
				<th style="display:none">结算单号</th>
				<th style="display:none">到货单号</th>
				<th style="display:none">备注</th>
			</tr>	
			<c:forEach items="${finGatheringDetailList}" var="finGatheringDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${finGatheringDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${finGatheringDetailItem.invoice}</td>								
						<td style="text-align: center">${finGatheringDetailItem.product}</td>								
						<td style="text-align: center">${finGatheringDetailItem.spec}</td>								
						<td style="text-align: center">${finGatheringDetailItem.unite}</td>								
						<td style="display:none">${finGatheringDetailItem.consignmentNum}</td>								
						<td style="text-align: center">${finGatheringDetailItem.receiveNum}</td>								
						<td style="display:none">${finGatheringDetailItem.checkNum}</td>								
						<td style="text-align: center">${finGatheringDetailItem.price}</td>								
						<td style="text-align: center">${finGatheringDetailItem.sumPrice}</td>								
						<td style="display:none">${finGatheringDetailItem.balancePrice}</td>								
						<td style="display:none">${finGatheringDetailItem.invoicePrice}</td>								
						<td style="display:none">${finGatheringDetailItem.receivePrice}</td>								
						<td style="display:none">${finGatheringDetailItem.nonreceivePrice}</td>								
						<td style="display:none">${finGatheringDetailItem.currentGetprice}</td>								
						<td style="display:none"><fmt:formatDate value='${finGatheringDetailItem.consignmentDate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${finGatheringDetailItem.arriveDate}' pattern='yyyy-MM-dd'/></td>								
						<td style="display:none">${finGatheringDetailItem.sellBill}</td>								
						<td style="display:none">${finGatheringDetailItem.balanceBill}</td>								
						<td style="display:none">${finGatheringDetailItem.arriveBill}</td>								
						<td style="display:none">${finGatheringDetailItem.remark}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

