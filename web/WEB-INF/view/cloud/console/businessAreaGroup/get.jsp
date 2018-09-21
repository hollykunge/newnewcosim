
<%--
	time:2013-06-05 13:24:43
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>商圈分组明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">商圈分组详细信息</span>
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
				<th width="20%">组名称:</th>
				<td>${businessAreaGroup.groupname}</td>
			</tr>
 
			<tr>
				<th width="20%">企业ID:</th>
				<td>${businessAreaGroup.entid}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

