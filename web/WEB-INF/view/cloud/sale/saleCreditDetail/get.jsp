
<%--
	time:2013-09-13 10:32:40
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>经销商赊销明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">经销商赊销详细信息</span>
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
				<th width="20%">单证号:</th>
				<td>${saleCreditDetail.code}</td>
			</tr>
 
			<tr>
				<th width="20%">供应企业名称:</th>
				<td>${saleCreditDetail.enterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">合作企业名称:</th>
				<td>${saleCreditDetail.coopenterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">总金额:</th>
				<td>${saleCreditDetail.totleMoney}</td>
			</tr>
 <!--
			<tr>
				<th width="20%">供应企业ID:</th>
				<td>${saleCreditDetail.enterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">合作企业ID:</th>
				<td>${saleCreditDetail.coopenterpriseId}</td>
			</tr>
 
			<tr>
				<th width="20%">id:</th>
				<td>${saleCreditDetail.id}</td>
			</tr>
 
			<tr>
				<th width="20%">remark2:</th>
				<td>${saleCreditDetail.remark2}</td>
			</tr>
 
			<tr>
				<th width="20%">remark3:</th>
				<td>${saleCreditDetail.remark3}</td>
			</tr>
			-->
		</table>
		</div>
		
	</div>
</body>
</html>

