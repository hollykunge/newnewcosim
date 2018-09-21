
<%--
	time:2013-04-19 11:40:23
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>退货单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">退货单详细信息</span>
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
				<th style="width: 120px">单证号:</th>
				<td colspan="3">${returnM.code}</td>
			</tr>
			 <tr>
			    <th style="width:120px">制单人:</th>
				<td>${returnM.operator}</td>
				<th style="width:120px">制单日期:</th>
				<td>
				<fmt:formatDate value="${returnM.operatedate}" pattern="yyyy-MM-dd"/>
				</td>
				
			</tr>
            
			<tr>
				<th style="width: 120px">制单人姓名:</th>
				<td>${returnM.operatename}</td>
				<th style="width: 120px">来源单据类型:</th>
				<td>${returnM.souecetype}</td>
			</tr>
 
            <tr>
				<th style="width: 120px">来源单据号:</th>
				<td>${returnM.sourcecode}</td>
				<th style="width: 120px">来源单据ID:</th>
				<td>${returnM.sourceid}</td>
			</tr>
 
			
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="7" style="text-align: center">退货单明细</td>
			</tr>
			<tr>
				<th>序号</th>
				<th>产品编码</th>
				<th>退货单价</th>
				<th>退货数量</th>
				<th>退货等级</th>
				<th>退货原因</th>
				<th>合计</th>
			</tr>	
			<c:forEach items="${returnDetailList}" var="returnDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${returnDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${returnDetailItem.seq}</td>								
						<td style="text-align: center">${returnDetailItem.prodcode}</td>								
						<td style="text-align: center">${returnDetailItem.price}</td>								
						<td style="text-align: center">${returnDetailItem.nums}</td>								
						<td style="text-align: center">${returnDetailItem.rank}</td>								
						<td style="text-align: center">${returnDetailItem.reason}</td>								
						<td style="text-align: center">${returnDetailItem.sum}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

