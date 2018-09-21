<%-- <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@include file="/commons/cloud/global.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<c:forEach items="${warehouseAccounts}" var="warehouseAccount">
		<div style="font-size:30px">产品${warehouseAccount.productname}在企业${companyName}中的库存数量为:${warehouseAccount.nums}</div>
	</c:forEach>
</body>
</html> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@ include file="/commons/cloud/global.jsp"%>
<html>
<head>
<title>cloud_price_strategy管理</title>
<%@include file="/commons/include/get.jsp" %>
<%@ include file="/commons/cloud/meta.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">库存查询表</span>
			</div>
		</div>
		<div class="panel-body">
			<table name="priceStrategyList" id="priceStrategyItem" sort="external" cellpadding="1" cellspacing="1" export="true"  class="table-grid">
				<thead>
					<th>企业名称</th>
					<th>产品名称</th>
					<th>库存数量</th>
				</thead>
				<tbody>
					<c:forEach items="${warehouseAccounts}" var="warehouseAccount">
						<tr>
							<td name="id" style="text-align: center">${companyName}</td>
							<td name="enterpriseId"  style="text-align: center">${warehouseAccount.productname}</td>
							<td name="enterpriseName" style="text-align: center">${warehouseAccount.nums}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>





