
<%--
	time:2013-04-13 19:06:38
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>采购询价单明细</title>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/commons/include/getById.jsp"%>

<script type="text/javascript">
	//放置脚本
	function getSaleQuote(id){
		var url = "${ctx}/cloud/sale/saleQuote/get.ht?id="+id;
		window.open(url);
	}	
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">采购询价单详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht">返回</a>
						<a class="link print" onclick="window.print();">打印</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<table class="table-detail" cellpadding="0" cellspacing="0"
				border="0">

				<tr>
					<th width="20%">询价单编码:</th>
					<td>${purEnquiry.code}</td>
				</tr>

				<tr>
					<th width="20%">制单日期:</th>
					<td><fmt:formatDate value="${purEnquiry.operateDate}"
							pattern="yyyy-MM-dd" /></td>
				</tr>
				<!-- 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${purEnquiry.operatorId}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据ID:</th>
				<td>${purEnquiry.sourceformId}</td>
			</tr>
 
			<tr>
				<th width="20%">采购企业ID:</th>
				<td>${purEnquiry.enterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">采购商联系人ID:</th>
				<td>${purEnquiry.purenterpUserid}</td>
			</tr>
  -->
				<tr>
					<th width="20%">制单人:</th>
					<td>${purEnquiry.operatorName}</td>
				</tr>
				<tr>
					<th width="20%">发布日期:</th>
					<td><fmt:formatDate value="${purEnquiry.releaseDate}"
							pattern="yyyy-MM-dd" /></td>
				</tr>
				<tr>
					<th width="20%">发布模式:</th>
					<td>${purEnquiry.releaseModel}</td>
				</tr>
				<tr>
					<th width="20%">来源单据类型:</th>
					<td>${purEnquiry.sourceformType}</td>
				</tr>

				<tr>
					<th width="20%">来源单据号:</th>
					<td>${purEnquiry.sourceformCode}</td>
				</tr>

				<tr>
					<th width="20%">报价截止时间:</th>
					<td><fmt:formatDate value="${purEnquiry.offerEndtime}"
							pattern="yyyy-MM-dd" /></td>
				</tr>

				<tr>
					<th width="20%">报价处理截止时间:</th>
					<td><fmt:formatDate value="${purEnquiry.doofferEndtime}"
							pattern="yyyy-MM-dd" /></td>
				</tr>

				<tr>
					<th width="20%">运费承担方:</th>
					<td>${purEnquiry.freightBearer}</td>
				</tr>

				<tr>
					<th width="20%">币种:</th>
					<td>${purEnquiry.currencyType}</td>
				</tr>

				<tr>
					<th width="20%">采购商联系人:</th>
					<td>${purEnquiry.purenterpUsername}</td>
				</tr>

				<tr>
					<th width="20%">采购企业:</th>
					<td>${purEnquiry.enterpriseName}</td>
				</tr>
			</table>
			<table class="table-grid table-list" cellpadding="1" cellspacing="1">
				<tr>
					<td colspan="9" style="text-align: center">采购询价单 :采购询价单详情</td>
				</tr>
				<tr>
					<th style="display: none">物品ID</th>
					<th>物品名</th>
				 	<th>物品编码</th>
				 	<th>物品规格</th>
					<th>单位</th>
					<th>订单数量</th>
					<th>预计单价</th>
					<th>要求发货日期</th>
				
				</tr>
				<c:forEach items="${purEnquiryDetailList}"
					var="purEnquiryDetailItem" varStatus="status">
					<tr>
						<input type="hidden" id="id" name="id"
							value="${purEnquiryDetailItem.id}" class="inputText" />
						<td style="display: none">${purEnquiryDetailItem.materielId}</td>
						<td style="text-align: center">${purEnquiryDetailItem.materielName}</td>
						<td style="text-align: center">${purEnquiryDetailItem.materielCode}</td>
						<td style="text-align: center">${purEnquiryDetailItem.model}</td>
						<td style="text-align: center">${purEnquiryDetailItem.unit}</td>
						<td style="text-align: center">${purEnquiryDetailItem.orderNum}</td>
						<td style="text-align: center">${purEnquiryDetailItem.price}</td>
						<td style="text-align: center"><fmt:formatDate
								value='${purEnquiryDetailItem.deliveryEnddate}'
								pattern='yyyy-MM-dd' /></td>
						
					</tr>
				</c:forEach>
			</table>
			<hotent:paging tableId="purEnquiryDetailList"/>
		</div>
		</br>	
	<div class="tbar-title">
		<span class="tbar-label">供应商报价明细</span>
	</div>
	<table class="table-detail" cellpadding="1" cellspacing="1" id="purOptimizeDetail" formType="window" type="sub">			
		<c:forEach items="${saleQuoteList}"  var="saleQuote" varStatus="s">
			<tr>
				<td colspan="6" style="text-align: left;padding-right: 6px;height: 32px;font-size: 12px;border: 1px solid #7babcf;">报价企业</td>
			</tr>
			<tr>
				<th>单证号:</th>
				<td>
					<a href="javascript:void(0)" onclick="getSaleQuote(${saleQuote.id});">${saleQuote.id}</a>
				</td>
				<th>制单日期:</th>
				<td>
					<fmt:formatDate value="${saleQuote.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<th>报价单号:</th>
				<td>${saleQuote.code}</td>
			</tr>
			<tr>
				<th>报价企业:</th>
				<td>${saleQuote.enterpriseName}</td>							
				<th>预付款:</th>
				<td>${saleQuote.advancePay}</td>
				<th>预付款说明:</th>
				<td>${saleQuote.advancepayNotes}</td>
			</tr>
		</c:forEach>
	</table>
			 
	</div>
</body>
</html>

