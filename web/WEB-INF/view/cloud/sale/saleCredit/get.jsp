
<%--
	time:2013-09-13 10:30:32
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>经销商信用额度明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">经销商信用额度详细信息</span>
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
				<th width="20%">供应商名称:</th>
				<td>${saleCredit.enterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">合作企业名称:</th>
				<td>${saleCredit.coopenterpriseName}</td>
			</tr>
 
			<tr>
				<th width="20%">信用额度:</th>
				<td>${saleCredit.credit}</td>
			</tr>
 <!--
 
			<tr>
				<th width="20%">供应商ID:</th>
				<td>${saleCredit.enterpriseId}</td>
			</td>
			</td>	
				<th width="20%">合作企业ID:</th>
				<td>${saleCredit.coopenterpriseId}</td>
			</tr>
			<tr>
				<th width="20%">remark1:</th>
				<td>${saleCredit.remark1}</td>
			</tr>
 
			<tr>
				<th width="20%">remark2:</th>
				<td>${saleCredit.remark2}</td>
			</tr>
 
			<tr>
				<th width="20%">remark3:</th>
				<td>${saleCredit.remark3}</td>
			</tr>
			-->
		</table>
		</div>
		
	</div>
</body>
</html>

