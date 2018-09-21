
<%--
	time:2013-04-19 11:40:23
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>反馈单明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">反馈单详细信息</span>
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
				<th style="width: 120px">反馈单号:</th>
				<td colspan="3">${feedback.code}</td>
			</tr>
 
			<tr>
				<th style="width: 120px">反馈用户:</th>
				<td>${feedback.fdman}</td>
				<th style="width: 120px">反馈日期:</th>
				<td>
				<fmt:formatDate value="${feedback.fddate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th style="width: 120px">产品名称:</th>
				<td>${feedback.prodname}</td>
				<th style="width: 120px">产品型号:</th>
				<td>${feedback.prodcode}</td>
			</tr>
 
			<tr>
				<th style="width: 120px">买购人:</th>
				<td>${feedback.purchaseman}</td>
				<th style="width: 120px">购买日期:</th>
				<td>
				<fmt:formatDate value="${feedback.purchasedate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
			<tr>
				<th style="width: 120px">反馈问题:</th>
				<td colspan="3">${feedback.descn}</td>
			</tr>
 
			<tr>
				<th style="width: 120px">处理结果:</th>
				<td>${feedback.result}</td>
				<th style="width: 120px">厂商:</th>
				<td>${feedback.purenter_name}</td>
			</tr>
 
			<tr>
				<th style="width: 120px">状态:</th>
				<td colspan="3">${feedback.statu}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

