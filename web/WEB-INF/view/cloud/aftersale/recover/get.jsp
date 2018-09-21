
<%--
	time:2013-05-06 17:14:39
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>回收单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">回收单详细信息</span>
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
				<th style="width:120px">单证号:</th>
				<td colspan="3">${recover.code}</td>
			</tr>
 
 
			<tr>
				<th style="width:120px">制单人:</th>
				<td colspan="3">${recover.operator}</td>
			</tr>
			<tr>
			    <th style="width:120px">来源单据ID:</th>
				<td>${recover.sourceid}</td>
				<th style="width:120px">制单日期:</th>
				<td>
				<fmt:formatDate value="${recover.operatedate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				
				<th style="width:120px">来源单据类型:</th>
				<td>${recover.sourcetype}</td>
				<th style="width:120px">来源单据号:</th>
				<td>${recover.sourcecode}</td>
			</tr>
            
            <tr>
				<th style="width:120px">回收原因:</th>
				<td colspan="3">${recover.reason}</td>
			</tr>
		</table>
		<table class="table-grid table-list" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="6" style="text-align: center">回收单明细</td>
			</tr>
			<tr>
				<th>序号</th>
				<th>产品编码</th>
				<th>回收价格</th>
				<th>回收价格</th>
				<th>回收等级</th>
				<th>合计</th>
			</tr>	
			<c:forEach items="${recoverDetailList}" var="recoverDetailItem" varStatus="status">
				<tr>
						<input type="hidden" id="id" name="id" value="${recoverDetailItem.id}"  class="inputText"/>
						<td style="text-align: center">${recoverDetailItem.seq}</td>								
						<td style="text-align: center">${recoverDetailItem.prodcode}</td>								
						<td style="text-align: center">${recoverDetailItem.price}</td>								
						<td style="text-align: center">${recoverDetailItem.nums}</td>								
						<td style="text-align: center">${recoverDetailItem.rank}</td>								
						<td style="text-align: center">${recoverDetailItem.sum}</td>								
				</tr>
			</c:forEach>
		</table>
		</div>
		
	</div>
</body>
</html>

