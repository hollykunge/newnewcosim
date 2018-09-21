
<%--
	time:2013-06-17 11:41:35
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>自定义分类明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">自定义分类详细信息</span>
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
				<th width="20%">分类名称:</th>
				<td>${materialCatalog.name}</td>
			</tr>
			<tr>
				<th width="20%">分类编码:</th>
				<td>${materialCatalog.code}</td>
			</tr>
		 
 
			<tr>
				<th width="20%">父级名称:</th>
				<td>${materialCatalog.parentName}</td>
			</tr>
 
		 
 
			<tr>
				<th width="20%">所属大类:</th>
				<td>${materialCatalog.typeName}</td>
			</tr>
 
		 
			<tr>
					<th width="20%">分类述描: </th>
					<td>
				 
					
					<textarea id="descn" name="descn" cols="50" rows="3" validate="{required:false,maxlength:100}" >${materialCatalog.descn}</textarea>
					</td>
				</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

