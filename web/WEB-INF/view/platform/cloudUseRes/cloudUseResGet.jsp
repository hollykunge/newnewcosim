
<%--
	time:2013-05-16 17:40:22
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_use_res明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_use_res详细信息</span>
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
				<th width="20%">企业Id:</th>
				<td>${cloudUseRes.entid}</td>
			</tr>
 
			<tr>
				<th width="20%">资源名字:</th>
				<td>${cloudUseRes.resName}</td>
			</tr>
 
			<tr>
				<th width="20%">资源地址链接:</th>
				<td>${cloudUseRes.resLink}</td>
			</tr>
 
			<tr>
				<th width="20%">资源访问时间:</th>
				<td>
				<fmt:formatDate value="${cloudUseRes.resTime}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

