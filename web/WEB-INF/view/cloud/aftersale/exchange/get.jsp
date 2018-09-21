
<%--
	time:2013-04-19 11:40:23
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>更换单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">更换单详细信息</span>
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
				<td colspan="3">${exChange.code}</td>
			</tr>
 
			<tr>
				<th style="width: 120px">制单人:</th>
				<td>${exChange.operator}</td>
 
				<th style="width: 120px">制单日期:</th>
				<td>
				<fmt:formatDate value="${exChange.operatedate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<th style="width: 120px">来源单据类型:</th>
				<td>${exChange.sourcetype}</td>
				
				<th style="width: 120px">来源单据号:</th>
				<td>${exChange.sourcecode}</td>
			</tr>
			
			<tr>
				<th style="width: 120px">来源单据ID:</th>
				<td colspan="3">${exChange.sourceid}</td>
			</tr>
            <tr>
				<th style="width: 120px">更换原因:</th>
				<td colspan="3">${exChange.reason}</td>
			</tr>
			
			
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="7" style="text-align: center">更换单明细</td>
			</tr>
			<tr>
				<th>序号</th>
				<th>产品编码</th>
				<th>产品名称</th>
				<th>更换价格</th>
				<th>更换数量</th>
				<th>更换等级</th>
				<th>合计</th>
			</tr>	
			<c:forEach items="${exChangeDetailList}" var="exChangeDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${exChangeDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${exChangeDetailItem.seq}</td>								
						<td style="text-align: center">${exChangeDetailItem.prodcode}</td>
						<td style="text-align: center">${exChangeDetailItem.prodname}</td>									
						<td style="text-align: center">${exChangeDetailItem.price}</td>								
						<td style="text-align: center">${exChangeDetailItem.nums}</td>								
						<td style="text-align: center">${exChangeDetailItem.rank}</td>								
						<td style="text-align: center">${exChangeDetailItem.sum}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

