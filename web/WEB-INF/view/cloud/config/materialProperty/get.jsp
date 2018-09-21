
<%--
	time:2013-04-16 18:50:26
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>cloud_material_property明细</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">cloud_material_property详细信息</span>
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
				<th width="20%">属性名称:</th>
				<td>${materialProperty.propertyName}</td>
			</tr>
			
			<tr>
				<th width="20%">所属分类:</th>
				<td>${materialProperty.className}</td>
			</tr>
 
			<tr>
				<th width="20%">分类排序:</th>
				<td>${materialProperty.propertySequence}</td>
			</tr>
			
			<tr>
				<th width="20%">属性值类型:</th>
				<td>
				
				 
				
				
				<c:if test="${materialProperty.propertyType==1}">
						文本框
					 </c:if>
					 <c:if test="${materialProperty.propertyType==2}">
						下拉选
					 </c:if>
				
				</td>
			</tr>
 
			<tr>
				<th width="20%">属性值:</th>
				<td>${materialProperty.value}</td>
			</tr>
		</table>
		</div>
		
	</div>
</body>
</html>

