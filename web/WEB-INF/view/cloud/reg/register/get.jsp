
<%--
	time:2013-07-09 13:53:40
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_user_register明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_user_register详细信息</span>
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
				<th width="20%">姓名:</th>
				<td>${register.name}</td>
			</tr>
 
			<tr>
				<th width="20%">性别:</th>
				<td>${register.sex}</td>
			</tr>
 
			<tr>
				<th width="20%">邮箱:</th>
				<td>${register.email}</td>
			</tr>
 
			<tr>
				<th width="20%">身份证号:</th>
				<td>${register.identity}</td>
			</tr>
 
			<tr>
				<th width="20%">手机号码:</th>
				<td>${register.tellphone}</td>
			</tr>
 
			<tr>
				<th width="20%">获奖证书:</th>
				<td>${register.credential}</td>
			</tr>
 
			<tr>
				<th width="20%">作品介绍:</th>
				<td>${register.introduce}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

