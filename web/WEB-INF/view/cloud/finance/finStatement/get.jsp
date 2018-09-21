
<%--
	time:2013-08-16 13:45:02
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_finance_statement明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">结算单详细信息</span>
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
				<th width="20%">单证号: </th>
				<td>${finStatement.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${finStatement.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">制单人:</th>
				<td>${finStatement.operaterName}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据类型:</th>
				<td>${finStatement.sourceformType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据号:</th>
				<td>${finStatement.sourceformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">总金额:</th>
				<td>${finStatement.sumPrise}</td>
			</tr>
 
			<tr>
				<th width="20%">币种:</th>
				<td>${finStatement.currencyType}</td>
			</tr>
 
			<tr>
				<th width="20%">付款企业名称:</th>
				<td>${finStatement.payenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">付款企业用户名称:</th>
				<td>${finStatement.payenterpUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">收款企业名称:</th>
				<td>${finStatement.payeeenterpName}</td>
			</tr>
 
			<tr>
				<th width="20%">收款企业用户名称:</th>
				<td>${finStatement.payeeenterpUsername}</td>
			</tr>
 
			<tr>
				<th width="20%">备注:</th>
				<td>${finStatement.remark1}</td>
			</tr>
 <!--
 
 
			<tr>
				<th width="20%">状态:</th>
				<td>${finStatement.status}</td>
			</tr>
			<tr>
				<th width="20%">payeeenterp_userid:</th>
				<td>${finStatement.payeeenterpUserid}</td>
			</tr>
			<tr>
				<th width="20%">sourceform_id:</th>
				<td>${finStatement.sourceformId}</td>
			</tr>
			<tr>
				<th width="20%">operater_id:</th>
				<td>${finStatement.operaterId}</td>
			</tr>
			<tr>
				<th width="20%">payenterp_id:</th>
				<td>${finStatement.payenterpId}</td>
			</tr>
			<tr>
				<th width="20%">payenterp_userid:</th>
				<td>${finStatement.payenterpUserid}</td>
			</tr>
			<tr>
				<th width="20%">payeeenterp_id:</th>
				<td>${finStatement.payeeenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">remark2:</th>
				<td>${finStatement.remark2}</td>
			</tr>
			<tr>
				<th width="20%">remark3:</th>
				<td>${finStatement.remark3}</td>
			</tr>
			<tr>
				<th width="20%">remark4:</th>
				<td>${finStatement.remark4}</td>
			</tr>
			-->
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="22" style="text-align: center">结算单详细信息</td>
			</tr>
			<tr>
					<th style="display:none">单证号</th>
					<th style="display:none">制单日期</th>
					<th style="display:none">制单人ID</th>
					<th style="display:none">制单人</th>
					<th style="display:none">订单ID</th>
					<th style="display:none">订单号</th>
					<th style="display:none">订单类型</th>
					<th style="display:none">物品ID</th>
					<th>物品编码</th>
					<th>物品名称</th>
					<th>物品分类</th>
					<th>属性说明</th>
					<th style="display:none">订货数量</th>
					<th style="display:none">本次计算数量</th>
					<th style="display:none">已结算数量</th>
					<th style="display:none">收获入库数量</th>
					<th style="display:none">退货数量</th>
					<th style="display:none">未结算数量</th>
					<th>单位</th>
					<th>单价</th>
					<th>数量</th>
					<th>总金额</th>
			</tr>	
			<c:forEach items="${finStatementDetailList}" var="finStatementDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${finStatementDetailItem.id}"  class="inputText"/>
						<td style="display:none">${finStatementDetailItem.code}</td>								
						<td style="display:none"><fmt:formatDate value='${finStatementDetailItem.operateDate}' pattern='yyyy-MM-dd'/></td>								
						<td style="display:none">${finStatementDetailItem.operaterId}</td>								
						<td style="display:none">${finStatementDetailItem.operaterName}</td>								
						<td style="display:none">${finStatementDetailItem.orderId}</td>								
						<td style="display:none">${finStatementDetailItem.orderCode}</td>								
						<td style="display:none">${finStatementDetailItem.orderType}</td>								
						<td style="display:none">${finStatementDetailItem.materielId}</td>								
						<td style="text-align: center">${finStatementDetailItem.materielCode}</td>								
						<td style="text-align: center">${finStatementDetailItem.materielName}</td>								
						<td style="text-align: center">${finStatementDetailItem.materielLev}</td>								
						<td style="text-align: center">${finStatementDetailItem.attributeNote}</td>								
						<td style="display:none">${finStatementDetailItem.orderNum}</td>								
						<td style="display:none">${finStatementDetailItem.currentstatementNum}</td>								
						<td style="display:none">${finStatementDetailItem.statedNum}</td>								
						<td style="display:none">${finStatementDetailItem.normaltowarehourseNum}</td>								
						<td style="display:none">${finStatementDetailItem.returnNum}</td>								
						<td style="display:none">${finStatementDetailItem.unstatementNum}</td>								
						<td style="text-align: center">${finStatementDetailItem.unite}</td>								
						<td style="text-align: center">${finStatementDetailItem.price}</td>								
						<td style="text-align: center">${finStatementDetailItem.orderNum2}</td>								
						<td style="text-align: center">${finStatementDetailItem.sumPrise}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

