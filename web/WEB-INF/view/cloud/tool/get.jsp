<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>工具明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">工具详细信息</span>
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
				<th width="20%">工具名称:</th>
				<td>${tool.toolName}</td>
			</tr>
			<tr>
				<th width="20%">工具类型:</th>
				<td>${tool.toolTypeAsString}</td>
			</tr>
			<tr>
				<th width="20%">用户默认工具地址:</th>
				<td>${tool.toolAddress}</td>
			</tr>
			<tr>
				<th width="20%">工具描述:</th>
				<td>${tool.toolDesc}</td>
			</tr>
			<tr>
				<th width="20%">创建时间:</th>
				<td><fmt:formatDate value="${tool.createtime}" pattern="yyyy-MM-dd HH:mm"/></td>
			</tr>
			<tr>
				<th width="20%">更新时间:</th>
				<td><fmt:formatDate value="${tool.updatetime}" pattern="yyyy-MM-dd HH:mm"/></td>
			</tr>			
		</table>
		</div>
		
	</div>
</body>
</html>

