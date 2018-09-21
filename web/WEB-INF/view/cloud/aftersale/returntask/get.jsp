
<%--
	time:2013-04-23 09:39:31
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>返厂维修单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">返厂维修单详细信息</span>
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
				<th style="width:120px;">返厂维修单号:</th>
				<td colspan="3">${returntask.code}</td>
			</tr>
 
			<tr>
			    <th style="width:120px;">制单人:</th>
				<td>${returntask.operator}</td>
				<th style="width:120px;">制单时间:</th>
				<td>
				<fmt:formatDate value="${returntask.operatedate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<th style="width:120px;">维修厂商:</th>
				<td>${returntask.purenterid}</td>
				<th style="width:120px;">送修厂:</th>
				<td>${returntask.currentpurenter}</td>
			</tr>
 
			<tr>
				<th style="width:120px;">返厂原因:</th>
				<td>${returntask.reason}</td>
				<th style="width:120px;">备注:</th>
				<td>${returntask.remark}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="6" style="text-align: center">返厂维修单明细</td>
			</tr>
			<tr>
				<th>序号</th>
				<th>产品编码</th>
				<th>产品名称</th>
				<th>规格型号</th>
				<th>计量单位</th>
				<th>参考价格</th>
			</tr>	
			<c:forEach items="${returntaskDetailList}" var="returntaskDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${returntaskDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${returntaskDetailItem.code}</td>								
						<td style="text-align: center">${returntaskDetailItem.prodcdoe}</td>								
						<td style="text-align: center">${returntaskDetailItem.prodname}</td>								
						<td style="text-align: center">${returntaskDetailItem.model}</td>								
						<td style="text-align: center">${returntaskDetailItem.unit}</td>								
						<td style="text-align: center">${returntaskDetailItem.price}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

