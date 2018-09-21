
<%--
	time:2014-03-01 17:44:17
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>工具参数映射明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">工具参数映射详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht?cloudToolUserId=${cloud_tool_user_parasMapper.cloudToolUserId}">返回</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			 <!-- 
			 <tr>
				<th width="20%">用户工具id:</th>
				<td>${cloud_tool_user_parasMapper.cloudToolUserId}</td>
			</tr>
			  -->
			
 
			<tr>
				<th width="20%">参数名称:</th>
				<td>${cloud_tool_user_parasMapper.name}</td>
			</tr>
 
			<tr>
				<th width="20%">参数表单映射名:</th>
				<td>${cloud_tool_user_parasMapper.formMapperName}</td>
			</tr>
 
			<tr>
				<th width="20%">参数中文名:</th>
				<td>${cloud_tool_user_parasMapper.chineseMapperName}</td>
			</tr>
			
			<tr>
				<th width="20%">参数分类:</th>
				<td>${cloud_tool_user_parasMapper.type}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

