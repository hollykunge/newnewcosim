
<%--
	time:2013-04-13 19:06:38
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>采购优选单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">采购优选单详细信息</span>
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
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 
			<tr>
				<th width="20%">单证号:</th>
				<td>${purOptimize.code}</td>
			</tr>
 
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${purOptimize.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">制单人:</th>
				<td>${purOptimize.operatorName}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据类型:</th>
				<td>${purOptimize.sourceformType}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据ID:</th>
				<td>${purOptimize.sourceformId}</td>
			</tr>
 
			<tr>
				<th width="20%">来源单据号:</th>
				<td>${purOptimize.sourceformCode}</td>
			</tr>
 
			<tr>
				<th width="20%">处理截止日期:</th>
				<td>
				<fmt:formatDate value="${purOptimize.doofferEndtime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th width="20%">制单人ID:</th>
				<td>${purOptimize.operatorId}</td>
			</tr>
 
			<tr>
				<th width="20%">中标供应商名:</th>
				<td>${purOptimize.supplierName}</td>
			</tr>
 
			<tr>
				<th width="20%">中标供应商ID:</th>
				<td>${purOptimize.supplierId}</td>
			</tr>
 
			<tr>
				<th width="20%">中标报价单ID:</th>
				<td>${purOptimize.quoteformId}</td>
			</tr>
 
			<tr>
				<th width="20%">中标报价单code:</th>
				<td>${purOptimize.quoteformCode}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="19" style="text-align: center">采购优选单 :采购优选单详情</td>
			</tr>
			<tr>
				<th>供应商ID</th>
				<th>供应商</th>
				<th>行号</th>
				<th>物品ID</th>
				<th>物品编码</th>
				<th>物品名称</th>
				<th>物品分类</th>
				<th>属性说明</th>
				<th>单位</th>
				<th>订单数量</th>
				<th>单价</th>
				<th>总价</th>
				<th>要求发货截止日期</th>
				<th>预计发货截止日期</th>
				<th>中标结果</th>
				<th>供应商编码</th>
				<th>结果说明</th>
				<th>来源报价单ID</th>
				<th>来源报价单Code</th>
			</tr>	
			<c:forEach items="${purOptimizeDetailList}" var="purOptimizeDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${purOptimizeDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${purOptimizeDetailItem.supplierId}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.supplierName}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.materielIndex}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.materielId}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.materielCode}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.materielName}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.materielLev}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.attributeInfo}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.unit}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.orderNum}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.price}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.sumPrice}</td>								
						<td style="text-align: center"><fmt:formatDate value='${purOptimizeDetailItem.deliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center"><fmt:formatDate value='${purOptimizeDetailItem.predeliveryEnddate}' pattern='yyyy-MM-dd'/></td>								
						<td style="text-align: center">${purOptimizeDetailItem.resulte}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.supplierCode}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.resulteNote}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.quoteformId}</td>								
						<td style="text-align: center">${purOptimizeDetailItem.quoteformCode}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

