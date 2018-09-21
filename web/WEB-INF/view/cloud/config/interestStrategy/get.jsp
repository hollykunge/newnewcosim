
<%--
	time:2013-09-24 17:29:56
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_interest_strategy明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">返利策略详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="listInterest.ht?currentEntid=${interestStrategy.enterpriseId}&materialId=${interestStrategy.materialId}">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr style="display: none;">
				<th width="20%">供应商id:</th>
				<td>${interestStrategy.enterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">供应商名称:</th>
				<td>${interestStrategy.enterpriseName}</td>
			</tr>
 
			<tr style="display: none;">
				<th width="20%">经销商id:</th>
				<td>${interestStrategy.coopenterpId}</td>
			</tr>
 
			<tr>
				<th width="20%">经销商名称:</th>
				<td>${interestStrategy.coopenterpName}</td>
			</tr>
 
			<tr style="display: none;">
				<th width="20%">物品id:</th>
				<td>${interestStrategy.materialId}</td>
			</tr>
 
			<tr>
				<th width="20%">物品名称:</th>
				<td>${interestStrategy.materialName}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="3" style="text-align: center">返利数量区间</td>
			</tr>
			<tr>
				<th>下限</th>
				<th>上限</th>
				<th>返利值</th>
			</tr>	
			<c:forEach items="${interestStrategyDetailList}" var="interestStrategyDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${interestStrategyDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${interestStrategyDetailItem.lowerNumber}</td>	
						<td style="text-align: center">${interestStrategyDetailItem.capsNumber}</td>							
						<td style="text-align: center">${interestStrategyDetailItem.interestValue}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

