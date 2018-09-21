
<%--
	time:2013-09-05 10:22:51
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_account_info明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">银行账户详细信息</span>
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
				<th width="20%">银行卡号:</th>
				<td>${AccountInfo.bankCode}</td>
			</tr>
 
			<tr>
				<th width="20%">开户行:</th>
				<td>${AccountInfo.openBank}</td>
			</tr>
 			
 			<tr>
				<th width="20%">开户人:</th>
				<td>${AccountInfo.accountOp}</td>
			</tr>
 			
			<tr>
				<th width="20%">开户企业名称:</th>
				<td>${AccountInfo.enterName}</td>
			</tr>
 
			
			
			<tr>
				<th width="20%">制单日期:</th>
				<td>
				<fmt:formatDate value="${AccountInfo.operateDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
 
 
			<tr>
				<th width="20%">制单人:</th>
				<td>${AccountInfo.operaterName}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

